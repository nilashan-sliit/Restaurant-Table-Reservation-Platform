package com.restaurant.review.model;

public abstract class Review {
    private String reviewId;
    private String username;
    private int rating;
    private String comment;
    private String date;

    public Review(String reviewId, String username, int rating, String comment, String date) {
        this.reviewId = reviewId;
        this.username = username;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    // Encapsulation: Getters and Setters
    public String getReviewId() { return reviewId; }
    public void setReviewId(String reviewId) { this.reviewId = reviewId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    // Polymorphism: Abstract methods to be implemented by subclasses
    public abstract String getDisplayFormat(String userRole);
    public abstract String toFileString();
}