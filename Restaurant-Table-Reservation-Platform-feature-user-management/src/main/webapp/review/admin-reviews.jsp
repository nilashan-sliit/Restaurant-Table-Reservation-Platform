<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.restaurant.review.model.Review" %>
<%@ page import="com.restaurant.review.util.ReviewFileManager" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin - Review Moderation</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen py-10">

    <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8">
        <h1 class="text-3xl font-bold text-gray-900 mb-6">Review Moderation Panel</h1>

        <div class="bg-white shadow overflow-hidden sm:rounded-md">
            <ul class="divide-y divide-gray-200">
                <% 
                    // Fetching directly for the admin panel demonstration
                    List<Review> allReviews = ReviewFileManager.getAllReviews();
                    if (allReviews != null && !allReviews.isEmpty()) {
                        for (Review review : allReviews) {
                %>
                <li class="p-6 flex flex-col sm:flex-row justify-between items-start sm:items-center">
                    <div class="mb-4 sm:mb-0">
                        <p class="text-sm font-medium text-blue-600 truncate">
                            <%= review.getDisplayFormat("Admin") %>
                        </p>
                        <p class="mt-1 flex items-center text-sm text-gray-500">
                            Submitted on: <%= review.getDate() %>
                        </p>
                    </div>
                    
                    <div class="flex space-x-2">
                        <form action="manageReview" method="POST" onsubmit="return confirm('Are you sure you want to delete this review?');">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="reviewId" value="<%= review.getReviewId() %>">
                            <button type="submit" class="inline-flex items-center px-3 py-1.5 border border-transparent text-xs font-medium rounded shadow-sm text-white bg-red-600 hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500">
                                Delete
                            </button>
                        </form>
                    </div>
                </li>
                <% 
                        }
                    } else { 
                %>
                <li class="p-6 text-center text-gray-500">No reviews found in the system.</li>
                <% } %>
            </ul>
        </div>
        <div class="mt-6">
            <a href="index.jsp" class="text-blue-600 hover:underline">&larr; Back to Dashboard</a>
        </div>
    </div>

</body>
</html>