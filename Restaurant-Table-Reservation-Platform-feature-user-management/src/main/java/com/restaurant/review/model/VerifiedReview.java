package com.restaurant.review.model;

public class VerifiedReview extends Review {
    private String reservationId;

    public VerifiedReview(String reviewId, String username, int rating, String comment, String date, String reservationId) {
        super(reviewId, username, rating, comment, date);
        this.reservationId = reservationId;
    }

    public String getReservationId() { return reservationId; }
    public void setReservationId(String reservationId) { this.reservationId = reservationId; }

    @Override
    public String getDisplayFormat(String userRole) {
        if ("Admin".equals(userRole)) {
            return "[VERIFIED - Res: " + reservationId + "] ID: " + getReviewId() + " | " + getUsername() + " rated " + getRating() + "/5: " + getComment();
        }
        return getUsername() + " (Verified Diner) rated " + getRating() + " stars: " + getComment();
    }

    @Override
    public String toFileString() {
        // Format: ID|Type|Username|Rating|Comment|Date|ReservationId
        return getReviewId() + "|VERIFIED|" + getUsername() + "|" + getRating() + "|" + getComment() + "|" + getDate() + "|" + reservationId;
    }
}