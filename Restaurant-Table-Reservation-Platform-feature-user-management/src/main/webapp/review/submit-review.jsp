<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Submit a Review - Restaurant Reservation</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen flex flex-col justify-center items-center py-10">

    <div class="max-w-md w-full bg-white rounded-lg shadow-md p-8">
        <h2 class="text-2xl font-bold text-center text-gray-800 mb-6">Rate Your Experience</h2>
        
        <form action="submitReview" method="POST" class="space-y-6">
            <div>
                <label for="rating" class="block text-sm font-medium text-gray-700">Rating (1 to 5 Stars)</label>
                <select id="rating" name="rating" required class="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
                    <option value="5">5 - Excellent</option>
                    <option value="4">4 - Good</option>
                    <option value="3">3 - Average</option>
                    <option value="2">2 - Poor</option>
                    <option value="1">1 - Terrible</option>
                </select>
            </div>

            <div>
                <label for="comment" class="block text-sm font-medium text-gray-700">Your Review</label>
                <textarea id="comment" name="comment" rows="4" required class="mt-1 block w-full border border-gray-300 rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm" placeholder="Tell us about the food and service..."></textarea>
            </div>

            <div>
                <label for="reservationId" class="block text-sm font-medium text-gray-700">Reservation ID (Optional)</label>
                <input type="text" id="reservationId" name="reservationId" class="mt-1 block w-full border border-gray-300 rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm" placeholder="e.g., RES-10293">
                <p class="mt-1 text-xs text-gray-500">Provide your reservation ID to get a 'Verified Diner' badge!</p>
            </div>

            <div>
                <button type="submit" class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500">
                    Submit Review
                </button>
            </div>
        </form>
        
        <div class="mt-4 text-center">
            <a href="viewReviews" class="text-sm text-blue-600 hover:text-blue-500">View All Reviews</a>
        </div>
    </div>

</body>
</html>