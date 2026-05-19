package com.restaurant.review.servlet;

import com.restaurant.review.model.PublicReview;
import com.restaurant.review.model.Review;
import com.restaurant.review.util.ReviewFileManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/manageReview")
public class ManageReviewServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String reviewId = request.getParameter("reviewId");

        if ("delete".equals(action)) {
            ReviewFileManager.deleteReview(reviewId);
            
        } else if ("update".equals(action)) {
            // Reconstruct the updated review from form parameters
            String username = request.getParameter("username");
            int rating = Integer.parseInt(request.getParameter("rating"));
            String comment = request.getParameter("comment");
            String date = request.getParameter("date");
            
            // Note: For simplicity, treating updates as PublicReviews here. 
            // In a full implementation, you'd retain the Verified status if it had one.
            Review updatedReview = new PublicReview(reviewId, username, rating, comment, date);
            ReviewFileManager.updateReview(updatedReview);
        }

        // Redirect back to the admin panel or user profile
        response.sendRedirect("admin-reviews.jsp"); 
    }
}