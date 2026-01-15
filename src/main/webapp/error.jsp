<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error | Ocean View Resort</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body class="bg-gradient-to-br from-orange-50 to-orange-100 min-h-screen flex items-center justify-center p-4">
<div class="max-w-2xl w-full bg-white rounded-2xl shadow-2xl overflow-hidden">
    <!-- Header -->
    <div class="bg-gradient-to-r from-orange-600 to-orange-700 p-8 text-center">
        <i class="fas fa-exclamation-circle text-white text-6xl mb-4"></i>
        <h1 class="text-4xl font-bold text-white mb-2">An Error Occurred</h1>
        <p class="text-orange-100 text-lg">Something unexpected happened</p>
    </div>

    <!-- Content -->
    <div class="p-8 text-center">
        <h2 class="text-2xl font-semibold text-gray-800 mb-4">We encountered an error</h2>
        <p class="text-gray-600 mb-6">
            An unexpected error occurred while processing your request.
            Please try again or contact support if the problem persists.
        </p>

        <!-- Error Details -->
        <%
            Throwable ex = exception;
            if (ex == null) {
                ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
            }

            Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
            String message = (String) request.getAttribute("javax.servlet.error.message");
            String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        %>

        <div class="bg-orange-50 border border-orange-200 rounded-lg p-4 mb-6 text-left">
            <% if (statusCode != null) { %>
            <p class="text-sm text-gray-700 mb-2">
                <span class="font-semibold">Status Code:</span> <%= statusCode %>
            </p>
            <% } %>

            <% if (message != null && !message.isEmpty()) { %>
            <p class="text-sm text-gray-700 mb-2">
                <span class="font-semibold">Message:</span> <%= message %>
            </p>
            <% } %>

            <% if (requestUri != null) { %>
            <p class="text-sm text-gray-700 mb-2">
                <span class="font-semibold">Request URI:</span> <%= requestUri %>
            </p>
            <% } %>

            <% if (ex != null) { %>
            <p class="text-sm text-gray-700">
                <span class="font-semibold">Exception:</span> <%= ex.getClass().getSimpleName() %>
            </p>
            <% if (ex.getMessage() != null) { %>
            <p class="text-xs text-gray-600 mt-1 font-mono break-all">
                <%= ex.getMessage() %>
            </p>
            <% } %>
            <% } %>
        </div>

        <!-- Troubleshooting Tips -->
        <div class="bg-blue-50 border border-blue-200 rounded-lg p-4 mb-6">
            <p class="text-sm font-semibold text-blue-800 mb-2">
                <i class="fas fa-info-circle mr-2"></i>Troubleshooting Tips
            </p>
            <ul class="text-sm text-blue-700 text-left space-y-1">
                <li>• Check if all required fields are filled correctly</li>
                <li>• Ensure you have a valid session (try logging in again)</li>
                <li>• Verify the data you submitted is in the correct format</li>
                <li>• Check your internet connection</li>
                <li>• Try clearing your browser cache</li>
            </ul>
        </div>

        <!-- Action Buttons -->
        <div class="flex flex-col sm:flex-row gap-4 justify-center">
            <button onclick="window.history.back()"
                    class="bg-orange-600 hover:bg-orange-700 text-white font-semibold py-3 px-6 rounded-lg transition duration-300 inline-flex items-center justify-center">
                <i class="fas fa-arrow-left mr-2"></i>
                Go Back
            </button>
            <a href="<%= request.getContextPath() %>/dashboard"
               class="bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3 px-6 rounded-lg transition duration-300 inline-flex items-center justify-center">
                <i class="fas fa-home mr-2"></i>
                Go to Dashboard
            </a>
            <a href="<%= request.getContextPath() %>/login"
               class="bg-gray-200 hover:bg-gray-300 text-gray-800 font-semibold py-3 px-6 rounded-lg transition duration-300 inline-flex items-center justify-center">
                <i class="fas fa-sign-in-alt mr-2"></i>
                Login Again
            </a>
        </div>

        <!-- Support Info -->
        <div class="mt-8 pt-8 border-t border-gray-200">
            <p class="text-sm text-gray-600 mb-4">Still having trouble?</p>
            <div class="flex flex-wrap gap-4 justify-center text-sm">
                <a href="<%= request.getContextPath() %>/help" class="text-blue-600 hover:text-blue-800">
                    <i class="fas fa-question-circle mr-1"></i> Visit Help Center
                </a>
                <span class="text-gray-400">|</span>
                <span class="text-gray-600">
                        <i class="fas fa-envelope mr-1"></i> support@oceanview.lk
                    </span>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <div class="bg-gray-50 px-8 py-4 text-center text-sm text-gray-600">
        <p>&copy; 2026 Ocean View Resort. All rights reserved.</p>
        <p class="text-xs text-gray-500 mt-1">Error ID: <%= System.currentTimeMillis() %>
        </p>
    </div>
</div>
</body>
</html>
