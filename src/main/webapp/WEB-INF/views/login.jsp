<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Ocean View Resort</title>

    <!-- Tailwind CSS CDN -->
    <script src="https://cdn.tailwindcss.com"></script>

    <!-- Font Awesome CDN -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <style>
        .bg-ocean {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }

        .glass-effect {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(10px);
        }
    </style>
</head>
<body class="bg-ocean min-h-screen flex items-center justify-center p-4">

<div class="glass-effect rounded-2xl shadow-2xl w-full max-w-md p-8">

    <!-- Logo/Header -->
    <div class="text-center mb-8">
        <i class="fas fa-umbrella-beach text-6xl text-indigo-600 mb-4"></i>
        <h1 class="text-3xl font-bold text-gray-800 mb-2">Ocean View Resort</h1>
        <p class="text-gray-600">Room Reservation System</p>
    </div>

    <!-- Error Message -->
    <c:if test="${not empty error}">
        <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-lg mb-6 flex items-start">
            <i class="fas fa-exclamation-circle mt-0.5 mr-3"></i>
            <div>
                <p class="font-semibold">Login Failed</p>
                <p class="text-sm">${error}</p>
            </div>
        </div>
    </c:if>

    <!-- Success Message (for logout) -->
    <c:if test="${not empty message}">
        <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded-lg mb-6 flex items-start">
            <i class="fas fa-check-circle mt-0.5 mr-3"></i>
            <div>
                <p class="text-sm">${message}</p>
            </div>
        </div>
    </c:if>

    <!-- Login Form -->
    <form action="${pageContext.request.contextPath}/login" method="post" id="loginForm">

        <!-- Username Field -->
        <div class="mb-6">
            <label for="username" class="block text-gray-700 font-semibold mb-2">
                <i class="fas fa-user mr-2"></i>Username
            </label>
            <input
                    type="text"
                    id="username"
                    name="username"
                    required
                    class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition duration-200"
                    placeholder="Enter your username"
                    value="${param.username}"
            >
        </div>

        <!-- Password Field -->
        <div class="mb-6">
            <label for="password" class="block text-gray-700 font-semibold mb-2">
                <i class="fas fa-lock mr-2"></i>Password
            </label>
            <div class="relative">
                <input
                        type="password"
                        id="password"
                        name="password"
                        required
                        class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition duration-200"
                        placeholder="Enter your password"
                >
                <button
                        type="button"
                        onclick="togglePassword()"
                        class="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-500 hover:text-gray-700"
                >
                    <i class="fas fa-eye" id="toggleIcon"></i>
                </button>
            </div>
        </div>

        <!-- Submit Button -->
        <button
                type="submit"
                class="w-full bg-indigo-600 hover:bg-indigo-700 text-white font-bold py-3 px-4 rounded-lg transition duration-200 transform hover:scale-105 shadow-lg"
        >
            <i class="fas fa-sign-in-alt mr-2"></i>Login
        </button>

    </form>

    <!-- Demo Credentials Info -->
    <div class="mt-8 p-4 bg-blue-50 rounded-lg border border-blue-200">
        <p class="text-sm text-blue-800 font-semibold mb-2">
            <i class="fas fa-info-circle mr-2"></i>Demo Credentials:
        </p>
        <div class="text-xs text-blue-700 space-y-1">
            <p><strong>Admin:</strong> admin / admin123</p>
            <p><strong>Staff:</strong> staff / staff123</p>
        </div>
    </div>

    <!-- Footer -->
    <div class="mt-6 text-center text-sm text-gray-600">
        <p>&copy; 2026 Ocean View Resort. All rights reserved.</p>
        <p class="mt-1">CIS6003 Advanced Programming Project</p>
    </div>

</div>

<!-- JavaScript -->
<script>
    // Toggle password visibility
    function togglePassword() {
        const passwordInput = document.getElementById('password');
        const toggleIcon = document.getElementById('toggleIcon');

        if (passwordInput.type === 'password') {
            passwordInput.type = 'text';
            toggleIcon.classList.remove('fa-eye');
            toggleIcon.classList.add('fa-eye-slash');
        } else {
            passwordInput.type = 'password';
            toggleIcon.classList.remove('fa-eye-slash');
            toggleIcon.classList.add('fa-eye');
        }
    }

    // Form validation
    document.getElementById('loginForm').addEventListener('submit', function (e) {
        const username = document.getElementById('username').value.trim();
        const password = document.getElementById('password').value;

        if (username === '') {
            e.preventDefault();
            alert('Please enter your username');
            document.getElementById('username').focus();
            return false;
        }

        if (password === '') {
            e.preventDefault();
            alert('Please enter your password');
            document.getElementById('password').focus();
            return false;
        }

        if (password.length < 6) {
            e.preventDefault();
            alert('Password must be at least 6 characters long');
            document.getElementById('password').focus();
            return false;
        }
    });

    // Auto-focus on username field
    document.addEventListener('DOMContentLoaded', function () {
        document.getElementById('username').focus();
    });
</script>

</body>
</html>
