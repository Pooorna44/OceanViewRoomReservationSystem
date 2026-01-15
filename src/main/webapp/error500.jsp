<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>500 - Server Error | Ocean View Resort</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body class="bg-gradient-to-br from-red-50 to-red-100 min-h-screen flex items-center justify-center p-4">
<div class="max-w-2xl w-full bg-white rounded-2xl shadow-2xl overflow-hidden">
    <!-- Header -->
    <div class="bg-gradient-to-r from-red-600 to-red-700 p-8 text-center">
        <i class="fas fa-server text-white text-6xl mb-4"></i>
        <h1 class="text-5xl font-bold text-white mb-2">500</h1>
        <p class="text-red-100 text-xl">Internal Server Error</p>
    </div>

    <!-- Content -->
    <div class="p-8 text-center">
        <h2 class="text-2xl font-semibold text-gray-800 mb-4">Something went wrong!</h2>
        <p class="text-gray-600 mb-6">
            We're sorry, but something unexpected happened on our server.
            Our team has been notified and is working to fix the issue.
        </p>

        <!-- Error Details (only show in development) -->
        <% if (exception != null && request.getAttribute("javax.servlet.error.exception") != null) { %>
        <div class="bg-red-50 border border-red-200 rounded-lg p-4 mb-6 text-left">
            <p class="text-sm font-semibold text-red-800 mb-2">Error Details:</p>
            <p class="text-xs text-red-700 font-mono break-all">
                <%= exception.getClass().getName() %>: <%= exception.getMessage() %>
            </p>
        </div>
        <% } %>

        <!-- What to do next -->
        <div class="bg-yellow-50 border border-yellow-200 rounded-lg p-4 mb-6">
            <p class="text-sm font-semibold text-yellow-800 mb-2">
                <i class="fas fa-lightbulb mr-2"></i>What can you do?
            </p>
            <ul class="text-sm text-yellow-700 text-left space-y-1">
                <li>• Try refreshing the page</li>
                <li>• Go back and try again</li>
                <li>• Return to the dashboard</li>
                <li>• Contact support if the problem persists</li>
            </ul>
        </div>

        <!-- Action Buttons -->
        <div class="flex flex-col sm:flex-row gap-4 justify-center">
            <button onclick="location.reload()"
                    class="bg-red-600 hover:bg-red-700 text-white font-semibold py-3 px-6 rounded-lg transition duration-300 inline-flex items-center justify-center">
                <i class="fas fa-redo mr-2"></i>
                Refresh Page
            </button>
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

        <!-- Support Info -->
        <div class="mt-8 pt-8 border-t border-gray-200">
            <p class="text-sm text-gray-600 mb-2">Need help?</p>
            <a href="<%= request.getContextPath() %>/help" class="text-blue-600 hover:text-blue-800 text-sm">
                <i class="fas fa-question-circle mr-1"></i> Visit Help Center
            </a>
        </div>
    </div>

    <!-- Footer -->
    <div class="bg-gray-50 px-8 py-4 text-center text-sm text-gray-600">
        <p>&copy; 2026 Ocean View Resort. All rights reserved.</p>
    </div>
</div>

<script>
    // Auto-hide error details after 10 seconds in production
    setTimeout(() => {
        const errorDetails = document.querySelector('.bg-red-50');
        if (errorDetails) {
            errorDetails.style.display = 'none';
        }
    }, 10000);
</script>
</body>
</html>
