package com.restaurant.review.servlet;

import com.restaurant.review.model.PublicReview;
import com.restaurant.review.model.Review;
import com.restaurant.review.model.VerifiedReview;
import com.restaurant.review.util.ReviewFileManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@WebServlet("/submitReview")
public class SubmitReviewServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        // Assuming Component 01 stores "username" in session upon login
        String username = (String) session.getAttribute("username"); 
        if (username == null) {
            username = "Anonymous"; // Fallback if not logged in
        }

        int rating = Integer.parseInt(request.getParameter("rating"));
        String comment = request.getParameter("comment");
        String reservationId = request.getParameter("reservationId"); // From the form
        String date = LocalDate.now().toString();
        String reviewId = "REV-" + UUID.randomUUID().toString().substring(0, 8);

        Review newReview;
        // Logic to determine if it's a verified review or public review
        if (reservationId != null && !reservationId.trim().isEmpty()) {
            newReview = new VerifiedReview(reviewId, username, rating, comment, date, reservationId);
        } else {
            newReview = new PublicReview(reviewId, username, rating, comment, date);
        }

        ReviewFileManager.saveReview(newReview);
        response.sendRedirect("viewReviews"); // Redirect to the read servlet
    }
}