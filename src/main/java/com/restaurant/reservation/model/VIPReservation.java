package com.restaurant.reservation.model;

//inheritance
public class VIPReservation extends Reservation {

    public VIPReservation(String reservationId, String userId,
                          String username, String tableId,
                          String reservationDate, String timeSlot) {
        super(reservationId, userId, username, tableId,
                reservationDate, timeSlot, "Active", 0.0, "VIP");
    }

    //polymorphism
    @Override
    public double calculateCancellationFee() {
        return 0.00;  // VIP users get free cancellation
    }
}