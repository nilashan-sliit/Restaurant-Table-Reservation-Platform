<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.restaurant.review.model.Review" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Reviews</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 min-h-screen py-10">

    <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between items-center mb-8">
            <h1 class="text-3xl font-extrabold text-gray-900">What Our Customers Say</h1>
            <a href="submit-review.jsp" class="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md shadow-sm text-white bg-green-600 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500">
                Write a Review
            </a>
        </div>

        <div class="space-y-6">
            <% 
                List<Review> reviews = (List<Review>) request.getAttribute("reviewList");
                if (reviews != null && !reviews.isEmpty()) {
                    for (Review review : reviews) {
            %>
            <div class="bg-white shadow overflow-hidden sm:rounded-lg p-6">
                <div class="flex items-center justify-between mb-2">
                    <div class="flex items-center">
                        <span class="font-semibold text-gray-900"><%= review.getUsername() %></span>
                        <% if (review instanceof com.restaurant.review.model.VerifiedReview) { %>
                            <span class="ml-2 inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-green-100 text-green-800">
                                Verified Diner
                            </span>
                        <% } %>
                    </div>
                    <span class="text-sm text-gray-500"><%= review.getDate() %></span>
                </div>
                <div class="flex items-center mb-4">
                    <span class="text-yellow-400 font-bold text-lg"><%= review.getRating() %> / 5 Stars</span>
                </div>
                <p class="text-gray-700"><%= review.getComment() %></p>
            </div>
            <% 
                    }
                } else { 
            %>
            <div class="text-center py-10 bg-white shadow rounded-lg">
                <p class="text-gray-500 text-lg">No reviews yet. Be the first to leave one!</p>
            </div>
            <% } %>
        </div>
    </div>

</body>
</html>