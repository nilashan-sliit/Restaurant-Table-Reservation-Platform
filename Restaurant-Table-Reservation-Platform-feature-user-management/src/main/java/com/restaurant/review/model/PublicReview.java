package com.restaurant.review.model;

public class PublicReview extends Review {

    public PublicReview(String reviewId, String username, int rating, String comment, String date) {
        super(reviewId, username, rating, comment, date);
    }

    @Override
    public String getDisplayFormat(String userRole) {
        if ("Admin".equals(userRole)) {
            return "[PUBLIC] ID: " + getReviewId() + " | " + getUsername() + " rated " + getRating() + "/5: " + getComment();
        }
        return getUsername() + " rated " + getRating() + " stars: " + getComment();
    }

    @Override
    public String toFileString() {
        // Format: ID|Type|Username|Rating|Comment|Date
        return getReviewId() + "|PUBLIC|" + getUsername() + "|" + getRating() + "|" + getComment() + "|" + getDate();
    }
}