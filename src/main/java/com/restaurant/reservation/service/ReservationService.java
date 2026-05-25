package com.restaurant.reservation.service;

import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.model.RegularReservation;
import com.restaurant.reservation.model.VIPReservation;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.user.model.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository repo;

    // Hardcoded tables since Table Management is not ready yet.
    // Replace these with Malitha's TableService later.
    public static final List<String> AVAILABLE_TABLES = List.of(
            "T01 - Window Seat (2 persons)",
            "T02 - Corner Table (4 persons)",
            "T03 - Garden View (2 persons)",
            "T04 - Centre Hall (6 persons)",
            "T05 - Private Booth (4 persons)",
            "T06 - Rooftop Table (2 persons)",
            "T07 - VIP Lounge (8 persons)",
            "T08 - Bar Side (2 persons)"
    );

    public static final List<String> TIME_SLOTS = List.of(
            "11:00 AM - 12:30 PM",
            "12:30 PM - 02:00 PM",
            "02:00 PM - 03:30 PM",
            "06:00 PM - 07:30 PM",
            "07:30 PM - 09:00 PM",
            "09:00 PM - 10:30 PM"
    );

    public ReservationService(ReservationRepository repo) {
        this.repo = repo;
    }

    // Make a new reservation — takes the full User object from session
    public void reserve(User user, String tableId,
                        String date, String timeSlot) throws IOException {
        if (!isAvailable(tableId, date, timeSlot)) {
            throw new IllegalArgumentException(
                    "This table is already booked for that date and time slot. Please choose another.");
        }

        String id = "R" + System.currentTimeMillis();

        Reservation r = "VIP".equalsIgnoreCase(user.getMembershipType())
                ? new VIPReservation(id, user.getUserId(), user.getUsername(), tableId, date, timeSlot)
                : new RegularReservation(id, user.getUserId(), user.getUsername(), tableId, date, timeSlot);

        repo.save(r);
    }

    // Cancel a reservation — applies fee based on subclass type
    public void cancel(String reservationId) throws IOException {
        Reservation r = repo.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found."));

        if ("Cancelled".equals(r.getStatus())) {
            throw new IllegalArgumentException("This reservation is already cancelled.");
        }

        r.setStatus("Cancelled");
        r.setCancellationFee(r.calculateCancellationFee());
        repo.update(r);
    }

    // Delete a completed reservation record
    public void delete(String reservationId) throws IOException {
        repo.delete(reservationId);
    }

    // Get all reservations (admin view)
    public List<Reservation> getAllReservations() throws IOException {
        return repo.findAll();
    }

    // Get reservations for the logged-in user only
    public List<Reservation> getByUserId(String userId) throws IOException {
        return repo.findByUserId(userId);
    }

    // Get a single reservation by ID
    public Optional<Reservation> getById(String id) throws IOException {
        return repo.findById(id);
    }

    // Check if a table is free for a given date + time slot
    public boolean isAvailable(String tableId, String date, String timeSlot) throws IOException {
        return repo.findAll().stream()
                .noneMatch(r -> r.getTableId().equals(tableId)
                        && r.getReservationDate().equals(date)
                        && r.getTimeSlot().equals(timeSlot)
                        && "Active".equals(r.getStatus()));
    }
}