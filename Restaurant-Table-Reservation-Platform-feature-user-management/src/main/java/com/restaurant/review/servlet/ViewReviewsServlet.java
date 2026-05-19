package com.restaurant.review.servlet;

import com.restaurant.review.model.Review;
import com.restaurant.review.util.ReviewFileManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/viewReviews")
public class ViewReviewsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Read all reviews from the text file
        List<Review> reviewList = ReviewFileManager.getAllReviews();
        
        // Attach the list to the request so the JSP can access it
        request.setAttribute("reviewList", reviewList);
        
        // Forward to the JSP page
        request.getRequestDispatcher("reviews.jsp").forward(request, response);
    }
}