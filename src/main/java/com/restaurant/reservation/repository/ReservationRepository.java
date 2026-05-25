package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.model.RegularReservation;
import com.restaurant.reservation.model.VIPReservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationRepository {

    private static final Logger log = LoggerFactory.getLogger(ReservationRepository.class);

    // File format:
    // reservationId|userId|username|tableId|reservationDate|timeSlot|status|cancellationFee|membershipType
    private static final Path FILE_PATH = Paths.get("data", "reservations.txt");

    public ReservationRepository() {
        log.info("ReservationRepository using file: {}", FILE_PATH.toAbsolutePath());
    }

    // Read all reservations
    public List<Reservation> findAll() throws IOException {
        List<Reservation> list = new ArrayList<>();
        if (Files.notExists(FILE_PATH)) return list;

        try (BufferedReader reader = Files.newBufferedReader(FILE_PATH, StandardCharsets.UTF_8)) {
            String line;
            int lineNum = 0;
            while ((line = reader.readLine()) != null) {
                lineNum++;
                if (!line.isBlank()) {
                    try {
                        list.add(parseLine(line));
                    } catch (Exception e) {
                        log.warn("Skipping malformed line {}: {} — {}", lineNum, line, e.getMessage());
                    }
                }
            }
        }
        return list;
    }

    // Find by reservationId
    public Optional<Reservation> findById(String id) throws IOException {
        return findAll().stream()
                .filter(r -> r.getReservationId().equals(id))
                .findFirst();
    }

    // Find all reservations for a specific user
    public List<Reservation> findByUserId(String userId) throws IOException {
        return findAll().stream()
                .filter(r -> r.getUserId().equals(userId))
                .toList();
    }

    // Save a new reservation (append)
    public synchronized void save(Reservation r) throws IOException {
        ensureFileExists();
        try (BufferedWriter writer = Files.newBufferedWriter(
                FILE_PATH, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            writer.write(serialize(r));
            writer.newLine();
            writer.flush();
        }
        log.info("Saved reservation {} for user {}", r.getReservationId(), r.getUserId());
    }

    // Update a reservation (rewrite whole file)
    public synchronized void update(Reservation updated) throws IOException {
        List<Reservation> list = findAll();
        try (BufferedWriter writer = Files.newBufferedWriter(
                FILE_PATH, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Reservation r : list) {
                writer.write(r.getReservationId().equals(updated.getReservationId())
                        ? serialize(updated) : serialize(r));
                writer.newLine();
            }
        }
        log.info("Updated reservation {}", updated.getReservationId());
    }

    // Delete a reservation
    public synchronized void delete(String id) throws IOException {
        List<Reservation> list = findAll();
        try (BufferedWriter writer = Files.newBufferedWriter(
                FILE_PATH, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Reservation r : list) {
                if (!r.getReservationId().equals(id)) {
                    writer.write(serialize(r));
                    writer.newLine();
                }
            }
        }
        log.info("Deleted reservation {}", id);
    }

    // Convert line → Reservation object
    private Reservation parseLine(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 9) {
            throw new IllegalArgumentException(
                    "Expected 9 fields but got " + p.length);
        }
        // p[0]=reservationId, p[1]=userId, p[2]=username, p[3]=tableId,
        // p[4]=reservationDate, p[5]=timeSlot, p[6]=status,
        // p[7]=cancellationFee, p[8]=membershipType
        Reservation r;
        if ("VIP".equals(p[8])) {
            r = new VIPReservation(p[0], p[1], p[2], p[3], p[4], p[5]);
        } else {
            r = new RegularReservation(p[0], p[1], p[2], p[3], p[4], p[5]);
        }
        r.setStatus(p[6]);
        r.setCancellationFee(Double.parseDouble(p[7]));
        return r;
    }

    // Convert Reservation → pipe-separated line
    private String serialize(Reservation r) {
        return String.join("|",
                r.getReservationId(),
                r.getUserId(),
                r.getUsername(),
                r.getTableId(),
                r.getReservationDate(),
                r.getTimeSlot(),
                r.getStatus(),
                String.valueOf(r.getCancellationFee()),
                r.getMembershipType());
    }

    private void ensureFileExists() throws IOException {
        Path parent = FILE_PATH.getParent();
        if (parent != null && Files.notExists(parent)) {
            Files.createDirectories(parent);
        }
        if (Files.notExists(FILE_PATH)) {
            Files.createFile(FILE_PATH);
            log.info("Created reservations file at {}", FILE_PATH.toAbsolutePath());
        }
    }
}