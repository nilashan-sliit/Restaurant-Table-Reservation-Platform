package com.restaurant.reservation.model;

public class Reservation {

    private String reservationId;
    private String userId;
    private String username;        // stored for display purposes
    private String tableId;
    private String reservationDate;
    private String timeSlot;
    private String status;          // "Active" or "Cancelled"
    private double cancellationFee;
    private String membershipType;  // "Regular" or "VIP" — drives fee calculation

    public Reservation(String reservationId, String userId, String username,
                       String tableId, String reservationDate, String timeSlot,
                       String status, double cancellationFee, String membershipType) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.username = username;
        this.tableId = tableId;
        this.reservationDate = reservationDate;
        this.timeSlot = timeSlot;
        this.status = status;
        this.cancellationFee = cancellationFee;
        this.membershipType = membershipType;
    }

    // Polymorphism — overridden in subclasses
    public double calculateCancellationFee() {
        return 0.0;
    }

    // Getters
    public String getReservationId()    { return reservationId; }
    public String getUserId()           { return userId; }
    public String getUsername()         { return username; }
    public String getTableId()          { return tableId; }
    public String getReservationDate()  { return reservationDate; }
    public String getTimeSlot()         { return timeSlot; }
    public String getStatus()           { return status; }
    public double getCancellationFee()  { return cancellationFee; }
    public String getMembershipType()   { return membershipType; }

    // Setters
    public void setStatus(String status)                    { this.status = status; }
    public void setCancellationFee(double cancellationFee)  { this.cancellationFee = cancellationFee; }
    public void setReservationId(String reservationId)      { this.reservationId = reservationId; }
}