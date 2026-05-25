package com.restaurant.reservation.model;

public class RegularReservation extends Reservation {

    public RegularReservation(String reservationId, String userId,
                              String username, String tableId,
                              String reservationDate, String timeSlot) {
        super(reservationId, userId, username, tableId,
                reservationDate, timeSlot, "Active", 0.0, "Regular");
    }

    @Override
    public double calculateCancellationFee() {
        return 500.00;  // flat fee for regular users
    }
}