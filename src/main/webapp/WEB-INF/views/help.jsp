<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Help & Support - Ocean View Resort</title>

    <!-- Tailwind CSS CDN -->
    <script src="https://cdn.tailwindcss.com"></script>

    <!-- Font Awesome CDN -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body class="bg-gray-100">

<!-- Navigation Bar -->
<nav class="bg-white shadow-lg mb-8">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between items-center h-16">
            <div class="flex items-center">
                <a href="${pageContext.request.contextPath}/dashboard" class="flex items-center">
                    <i class="fas fa-umbrella-beach text-3xl text-indigo-600 mr-3"></i>
                    <div>
                        <h1 class="text-xl font-bold text-gray-800">Ocean View Resort</h1>
                        <p class="text-xs text-gray-600">Room Reservation System</p>
                    </div>
                </a>
            </div>
            <div class="flex items-center space-x-4">
                <a href="${pageContext.request.contextPath}/dashboard"
                   class="text-gray-600 hover:text-gray-800">
                    <i class="fas fa-arrow-left mr-2"></i>Back to Dashboard
                </a>
                <span class="text-gray-400">|</span>
                <a href="${pageContext.request.contextPath}/"
                   class="bg-indigo-500 hover:bg-indigo-600 text-white px-4 py-2 rounded-lg transition duration-200 text-sm">
                    <i class="fas fa-home mr-2"></i>Home
                </a>
                <a href="${pageContext.request.contextPath}/logout"
                   class="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded-lg transition duration-200 text-sm">
                    <i class="fas fa-sign-out-alt mr-2"></i>Logout
                </a>
            </div>
        </div>
    </div>
</nav>

<!-- Main Content -->
<div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 pb-12">

    <!-- Page Header -->
    <div class="bg-gradient-to-r from-indigo-500 to-purple-600 rounded-lg shadow-lg p-8 mb-8 text-white">
        <h2 class="text-4xl font-bold mb-2">
            <i class="fas fa-question-circle mr-3"></i>
            Help & Support
        </h2>
        <p class="text-indigo-100 text-lg">Learn how to use the Ocean View Resort Room Reservation System</p>
    </div>

    <!-- Quick Links -->
    <div class="grid grid-cols-1 md:grid-cols-4 gap-4 mb-8">
        <a href="#getting-started" class="bg-white p-4 rounded-lg shadow hover:shadow-lg transition text-center">
            <i class="fas fa-rocket text-3xl text-indigo-600 mb-2"></i>
            <p class="font-semibold text-gray-800">Getting Started</p>
        </a>
        <a href="#features" class="bg-white p-4 rounded-lg shadow hover:shadow-lg transition text-center">
            <i class="fas fa-list text-3xl text-green-600 mb-2"></i>
            <p class="font-semibold text-gray-800">Features</p>
        </a>
        <a href="#faq" class="bg-white p-4 rounded-lg shadow hover:shadow-lg transition text-center">
            <i class="fas fa-comments text-3xl text-yellow-600 mb-2"></i>
            <p class="font-semibold text-gray-800">FAQ</p>
        </a>
        <a href="#contact" class="bg-white p-4 rounded-lg shadow hover:shadow-lg transition text-center">
            <i class="fas fa-envelope text-3xl text-red-600 mb-2"></i>
            <p class="font-semibold text-gray-800">Contact</p>
        </a>
    </div>

    <!-- Getting Started Section -->
    <div id="getting-started" class="bg-white rounded-lg shadow-lg p-8 mb-8">
        <h3 class="text-2xl font-bold text-gray-800 mb-6 flex items-center">
            <i class="fas fa-rocket text-indigo-600 mr-3"></i>
            Getting Started
        </h3>

        <div class="space-y-6">
            <div>
                <h4 class="text-xl font-semibold text-gray-800 mb-3">1. Logging In</h4>
                <p class="text-gray-700 mb-2">Access the system using your credentials:</p>
                <ul class="list-disc list-inside text-gray-700 space-y-1 ml-4">
                    <li>Navigate to the login page</li>
                    <li>Enter your username and password</li>
                    <li>Click the "Login" button</li>
                    <li>You will be redirected to the dashboard upon successful login</li>
                </ul>
            </div>

            <div>
                <h4 class="text-xl font-semibold text-gray-800 mb-3">2. Dashboard Overview</h4>
                <p class="text-gray-700 mb-2">The dashboard provides quick access to all system features:</p>
                <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mt-4">
                    <div class="bg-green-50 p-4 rounded-lg border border-green-200">
                        <p class="font-semibold text-green-800 mb-1">Add Reservation</p>
                        <p class="text-sm text-gray-700">Create new guest bookings</p>
                    </div>
                    <div class="bg-blue-50 p-4 rounded-lg border border-blue-200">
                        <p class="font-semibold text-blue-800 mb-1">View Reservations</p>
                        <p class="text-sm text-gray-700">Search and view existing bookings</p>
                    </div>
                    <div class="bg-yellow-50 p-4 rounded-lg border border-yellow-200">
                        <p class="font-semibold text-yellow-800 mb-1">Generate Bill</p>
                        <p class="text-sm text-gray-700">Create invoices for reservations</p>
                    </div>
                    <div class="bg-purple-50 p-4 rounded-lg border border-purple-200">
                        <p class="font-semibold text-purple-800 mb-1">Help & Support</p>
                        <p class="text-sm text-gray-700">Access user documentation</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Features Section -->
    <div id="features" class="bg-white rounded-lg shadow-lg p-8 mb-8">
        <h3 class="text-2xl font-bold text-gray-800 mb-6 flex items-center">
            <i class="fas fa-list text-green-600 mr-3"></i>
            System Features
        </h3>

        <div class="space-y-8">

            <!-- Feature 1: Add Reservation -->
            <div class="border-l-4 border-green-500 pl-6">
                <h4 class="text-xl font-semibold text-gray-800 mb-3">
                    <i class="fas fa-plus-circle text-green-600 mr-2"></i>
                    Adding a New Reservation
                </h4>
                <ol class="list-decimal list-inside text-gray-700 space-y-2 ml-4">
                    <li>Click on "Add Reservation" from the dashboard</li>
                    <li>Fill in guest information (name, contact, address)</li>
                    <li>Select room type from the dropdown menu</li>
                    <li>Choose check-in and check-out dates</li>
                    <li>Specify number of guests</li>
                    <li>Add any special requests (optional)</li>
                    <li>Review the estimated price</li>
                    <li>Click "Create Reservation" to confirm</li>
                </ol>
                <div class="mt-4 p-3 bg-blue-50 rounded-lg">
                    <p class="text-sm text-blue-800">
                        <i class="fas fa-lightbulb mr-2"></i>
                        <strong>Tip:</strong> The system automatically calculates the number of nights and displays an
                        estimated total price.
                    </p>
                </div>
            </div>

            <!-- Feature 2: View Reservation -->
            <div class="border-l-4 border-blue-500 pl-6">
                <h4 class="text-xl font-semibold text-gray-800 mb-3">
                    <i class="fas fa-search text-blue-600 mr-2"></i>
                    Viewing Reservations
                </h4>
                <ol class="list-decimal list-inside text-gray-700 space-y-2 ml-4">
                    <li>Click on "View Reservations" from the dashboard</li>
                    <li>Enter the reservation number in the search field</li>
                    <li>Click "Search" to retrieve the reservation details</li>
                    <li>Review all guest and booking information</li>
                    <li>Use action buttons to generate bill or cancel reservation</li>
                </ol>
                <div class="mt-4 p-3 bg-blue-50 rounded-lg">
                    <p class="text-sm text-blue-800">
                        <i class="fas fa-lightbulb mr-2"></i>
                        <strong>Tip:</strong> Reservation numbers follow the format RES20260113001 (RES + Date +
                        Sequential Number).
                    </p>
                </div>
            </div>

            <!-- Feature 3: Generate Bill -->
            <div class="border-l-4 border-yellow-500 pl-6">
                <h4 class="text-xl font-semibold text-gray-800 mb-3">
                    <i class="fas fa-file-invoice-dollar text-yellow-600 mr-2"></i>
                    Generating Bills
                </h4>
                <ol class="list-decimal list-inside text-gray-700 space-y-2 ml-4">
                    <li>Click on "Generate Bill" from the dashboard</li>
                    <li>Enter the reservation number</li>
                    <li>Click "Generate Bill" to create the invoice</li>
                    <li>Review the billing details and total amount</li>
                    <li>Click "Print Invoice" to print or save as PDF</li>
                </ol>
                <div class="mt-4 p-3 bg-blue-50 rounded-lg">
                    <p class="text-sm text-blue-800">
                        <i class="fas fa-lightbulb mr-2"></i>
                        <strong>Tip:</strong> Bills can also be generated directly from the reservation view page.
                    </p>
                </div>
            </div>

            <!-- Feature 4: Room Types -->
            <div class="border-l-4 border-purple-500 pl-6">
                <h4 class="text-xl font-semibold text-gray-800 mb-3">
                    <i class="fas fa-bed text-purple-600 mr-2"></i>
                    Available Room Types
                </h4>
                <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mt-4">
                    <div class="bg-gray-50 p-4 rounded-lg">
                        <p class="font-semibold text-gray-800">Standard Room</p>
                        <p class="text-sm text-gray-600">Basic amenities, comfortable stay</p>
                    </div>
                    <div class="bg-gray-50 p-4 rounded-lg">
                        <p class="font-semibold text-gray-800">Deluxe Room</p>
                        <p class="text-sm text-gray-600">Enhanced comfort with premium features</p>
                    </div>
                    <div class="bg-gray-50 p-4 rounded-lg">
                        <p class="font-semibold text-gray-800">Suite</p>
                        <p class="text-sm text-gray-600">Luxury accommodation with separate living area</p>
                    </div>
                    <div class="bg-gray-50 p-4 rounded-lg">
                        <p class="font-semibold text-gray-800">Family Room</p>
                        <p class="text-sm text-gray-600">Spacious room for families with multiple beds</p>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <!-- FAQ Section -->
    <div id="faq" class="bg-white rounded-lg shadow-lg p-8 mb-8">
        <h3 class="text-2xl font-bold text-gray-800 mb-6 flex items-center">
            <i class="fas fa-comments text-yellow-600 mr-3"></i>
            Frequently Asked Questions
        </h3>

        <div class="space-y-6">

            <!-- FAQ Item -->
            <div class="border-b border-gray-200 pb-6">
                <h4 class="text-lg font-semibold text-gray-800 mb-2">
                    Q: How do I create a new reservation?
                </h4>
                <p class="text-gray-700">
                    A: Click on "Add Reservation" from the dashboard, fill in all required guest and booking details,
                    then click "Create Reservation" to save.
                </p>
            </div>

            <div class="border-b border-gray-200 pb-6">
                <h4 class="text-lg font-semibold text-gray-800 mb-2">
                    Q: Can I cancel a reservation?
                </h4>
                <p class="text-gray-700">
                    A: Yes, you can cancel a confirmed reservation from the "View Reservation" page by clicking the
                    "Cancel Reservation" button. Please note that cancellation policies may apply.
                </p>
            </div>

            <div class="border-b border-gray-200 pb-6">
                <h4 class="text-lg font-semibold text-gray-800 mb-2">
                    Q: How is the bill calculated?
                </h4>
                <p class="text-gray-700">
                    A: The bill is calculated based on: Number of nights × Room rate per night. The system may apply
                    different pricing strategies (Standard, Seasonal, or Weekend) depending on the booking dates.
                </p>
            </div>

            <div class="border-b border-gray-200 pb-6">
                <h4 class="text-lg font-semibold text-gray-800 mb-2">
                    Q: What is the reservation number format?
                </h4>
                <p class="text-gray-700">
                    A: Reservation numbers follow the format: RES + Date (YYYYMMDD) + Sequential Number.
                    Example: RES20260113001 (first reservation on January 13, 2026).
                </p>
            </div>

            <div class="border-b border-gray-200 pb-6">
                <h4 class="text-lg font-semibold text-gray-800 mb-2">
                    Q: Can I print invoices?
                </h4>
                <p class="text-gray-700">
                    A: Yes, on the bill page, click "Print Invoice" to print or save the invoice as a PDF using
                    your browser's print dialog.
                </p>
            </div>

            <div class="pb-6">
                <h4 class="text-lg font-semibold text-gray-800 mb-2">
                    Q: What if I forget my password?
                </h4>
                <p class="text-gray-700">
                    A: Please contact your system administrator or IT support to reset your password. For security
                    reasons, password reset functionality is managed by administrators only.
                </p>
            </div>

        </div>
    </div>

    <!-- Contact Section -->
    <div id="contact" class="bg-white rounded-lg shadow-lg p-8">
        <h3 class="text-2xl font-bold text-gray-800 mb-6 flex items-center">
            <i class="fas fa-envelope text-red-600 mr-3"></i>
            Contact & Support
        </h3>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">

            <div class="bg-indigo-50 p-6 rounded-lg border border-indigo-200">
                <h4 class="font-semibold text-indigo-800 mb-4 flex items-center">
                    <i class="fas fa-headset mr-2"></i>
                    Technical Support
                </h4>
                <div class="space-y-2 text-gray-700">
                    <p><i class="fas fa-phone mr-2 text-indigo-600"></i>+94 11 234 5678</p>
                    <p><i class="fas fa-envelope mr-2 text-indigo-600"></i>support@oceanviewresort.lk</p>
                    <p><i class="fas fa-clock mr-2 text-indigo-600"></i>Mon-Fri: 9:00 AM - 6:00 PM</p>
                </div>
            </div>

            <div class="bg-green-50 p-6 rounded-lg border border-green-200">
                <h4 class="font-semibold text-green-800 mb-4 flex items-center">
                    <i class="fas fa-hotel mr-2"></i>
                    Resort Information
                </h4>
                <div class="space-y-2 text-gray-700">
                    <p><i class="fas fa-phone mr-2 text-green-600"></i>+94 11 987 6543</p>
                    <p><i class="fas fa-envelope mr-2 text-green-600"></i>info@oceanviewresort.lk</p>
                    <p><i class="fas fa-globe mr-2 text-green-600"></i>www.oceanviewresort.lk</p>
                </div>
            </div>

        </div>

        <div class="mt-8 p-6 bg-yellow-50 rounded-lg border border-yellow-200">
            <p class="text-gray-700">
                <i class="fas fa-info-circle text-yellow-600 mr-2"></i>
                <strong>System Version:</strong> 1.0 |
                <strong>Last Updated:</strong> January 2026 |
                <strong>Developed for:</strong> CIS6003 Advanced Programming
            </p>
        </div>
    </div>

</div>

<!-- Footer -->
<footer class="bg-white shadow-lg mt-12">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
        <div class="text-center text-sm text-gray-600">
            <p>&copy; 2026 Ocean View Resort. All rights reserved.</p>
            <p class="mt-1">CIS6003 Advanced Programming Project</p>
        </div>
    </div>
</footer>

<!-- Smooth Scroll Script -->
<script>
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
            }
        });
    });
</script>

</body>
</html>
