<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 - Page Not Found | Ocean View Resort</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body class="bg-gradient-to-br from-blue-50 to-blue-100 min-h-screen flex items-center justify-center p-4">
<div class="max-w-2xl w-full bg-white rounded-2xl shadow-2xl overflow-hidden">
    <!-- Header -->
    <div class="bg-gradient-to-r from-blue-600 to-blue-700 p-8 text-center">
        <i class="fas fa-exclamation-triangle text-white text-6xl mb-4"></i>
        <h1 class="text-5xl font-bold text-white mb-2">404</h1>
        <p class="text-blue-100 text-xl">Page Not Found</p>
    </div>

    <!-- Content -->
    <div class="p-8 text-center">
        <h2 class="text-2xl font-semibold text-gray-800 mb-4">Oops! We can't find that page</h2>
        <p class="text-gray-600 mb-6">
            The page you're looking for doesn't exist or has been moved.
            Don't worry, you can navigate back to our system.
        </p>

        <!-- Error Details (if needed) -->
        <div class="bg-gray-50 rounded-lg p-4 mb-6 text-left">
            <p class="text-sm text-gray-600">
                <span class="font-semibold">Requested URL:</span>
                <%= request.getAttribute("javax.servlet.error.request_uri") != null
                        ? request.getAttribute("javax.servlet.error.request_uri")
                        : "Unknown" %>
            </p>
        </div>

        <!-- Action Buttons -->
        <div class="flex flex-col sm:flex-row gap-4 justify-center">
            <a href="<%= request.getContextPath() %>/dashboard"
               class="bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3 px-6 rounded-lg transition duration-300 inline-flex items-center justify-center">
                <i class="fas fa-home mr-2"></i>
                Go to Dashboard
            </a>
            <button onclick="window.history.back()"
                    class="bg-gray-200 hover:bg-gray-300 text-gray-800 font-semibold py-3 px-6 rounded-lg transition duration-300 inline-flex items-center justify-center">
                <i class="fas fa-arrow-left mr-2"></i>
                Go Back
            </button>
        </div>

        <!-- Helpful Links -->
        <div class="mt-8 pt-8 border-t border-gray-200">
            <p class="text-sm text-gray-600 mb-4">Quick Links:</p>
            <div class="flex flex-wrap gap-4 justify-center text-sm">
                <a href="<%= request.getContextPath() %>/reservation" class="text-blue-600 hover:text-blue-800">
                    <i class="fas fa-calendar-plus mr-1"></i> Add Reservation
                </a>
                <a href="<%= request.getContextPath() %>/bill" class="text-blue-600 hover:text-blue-800">
                    <i class="fas fa-file-invoice mr-1"></i> Generate Bill
                </a>
                <a href="<%= request.getContextPath() %>/help" class="text-blue-600 hover:text-blue-800">
                    <i class="fas fa-question-circle mr-1"></i> Help
                </a>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <div class="bg-gray-50 px-8 py-4 text-center text-sm text-gray-600">
        <p>&copy; 2026 Ocean View Resort. All rights reserved.</p>
    </div>
</div>
</body>
</html>
