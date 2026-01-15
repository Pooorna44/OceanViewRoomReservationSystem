<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Ocean View Resort</title>

    <!-- Tailwind CSS CDN -->
    <script src="https://cdn.tailwindcss.com"></script>

    <!-- Font Awesome CDN -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <style>
        .card-hover {
            transition: all 0.3s ease;
        }

        .card-hover:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body class="bg-gray-100">

<!-- Navigation Bar -->
<nav class="bg-white shadow-lg">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between items-center h-16">

            <!-- Logo -->
            <div class="flex items-center">
                <i class="fas fa-umbrella-beach text-3xl text-indigo-600 mr-3"></i>
                <div>
                    <h1 class="text-xl font-bold text-gray-800">Ocean View Resort</h1>
                    <p class="text-xs text-gray-600">Room Reservation System</p>
                </div>
            </div>

            <!-- User Info -->
            <div class="flex items-center space-x-4">
                <div class="text-right mr-4">
                    <p class="text-sm font-semibold text-gray-800">
                        <i class="fas fa-user-circle mr-2"></i>${user.fullName}
                    </p>
                    <p class="text-xs text-gray-600">Role: ${user.role}</p>
                </div>
                <a href="${pageContext.request.contextPath}/logout"
                   class="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded-lg transition duration-200 text-sm">
                    <i class="fas fa-sign-out-alt mr-2"></i>Logout
                </a>
            </div>

        </div>
    </div>
</nav>

<!-- Main Content -->
<div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">

    <!-- Welcome Section -->
    <div class="bg-gradient-to-r from-indigo-500 to-purple-600 rounded-lg shadow-lg p-8 mb-8 text-white">
        <h2 class="text-3xl font-bold mb-2">Welcome back, ${user.fullName}!</h2>
        <p class="text-indigo-100">Manage room reservations and generate bills with ease.</p>
    </div>

    <!-- Success/Error Messages -->
    <c:if test="${not empty message}">
        <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded-lg mb-6 flex items-center">
            <i class="fas fa-check-circle mr-3"></i>
            <p>${message}</p>
        </div>
    </c:if>

    <c:if test="${not empty error}">
        <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-lg mb-6 flex items-center">
            <i class="fas fa-exclamation-circle mr-3"></i>
            <p>${error}</p>
        </div>
    </c:if>

    <!-- Quick Actions -->
    <div class="mb-8">
        <h3 class="text-2xl font-bold text-gray-800 mb-6">Quick Actions</h3>

        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">

            <!-- Add Reservation Card -->
            <a href="${pageContext.request.contextPath}/reservation?action=new"
               class="card-hover bg-white rounded-lg shadow-md p-6 text-center hover:shadow-xl">
                <div class="bg-green-100 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
                    <i class="fas fa-plus-circle text-3xl text-green-600"></i>
                </div>
                <h4 class="text-lg font-semibold text-gray-800 mb-2">Add Reservation</h4>
                <p class="text-sm text-gray-600">Create new booking</p>
            </a>

            <!-- View Reservations Card -->
            <a href="${pageContext.request.contextPath}/reservation?action=search"
               class="card-hover bg-white rounded-lg shadow-md p-6 text-center hover:shadow-xl">
                <div class="bg-blue-100 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
                    <i class="fas fa-search text-3xl text-blue-600"></i>
                </div>
                <h4 class="text-lg font-semibold text-gray-800 mb-2">View Reservations</h4>
                <p class="text-sm text-gray-600">Search bookings</p>
            </a>

            <!-- Generate Bill Card -->
            <a href="${pageContext.request.contextPath}/bill"
               class="card-hover bg-white rounded-lg shadow-md p-6 text-center hover:shadow-xl">
                <div class="bg-yellow-100 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
                    <i class="fas fa-file-invoice-dollar text-3xl text-yellow-600"></i>
                </div>
                <h4 class="text-lg font-semibold text-gray-800 mb-2">Generate Bill</h4>
                <p class="text-sm text-gray-600">Create invoice</p>
            </a>

            <!-- Help Card -->
            <a href="${pageContext.request.contextPath}/help"
               class="card-hover bg-white rounded-lg shadow-md p-6 text-center hover:shadow-xl">
                <div class="bg-purple-100 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
                    <i class="fas fa-question-circle text-3xl text-purple-600"></i>
                </div>
                <h4 class="text-lg font-semibold text-gray-800 mb-2">Help & Support</h4>
                <p class="text-sm text-gray-600">User guide</p>
            </a>

        </div>
    </div>

    <!-- Recent Reservations -->
    <div class="bg-white rounded-lg shadow-lg p-6">
        <h3 class="text-2xl font-bold text-gray-800 mb-6 flex items-center">
            <i class="fas fa-calendar-alt mr-3 text-indigo-600"></i>
            Recent Reservations
        </h3>

        <c:choose>
            <c:when test="${not empty recentReservations}">
                <div class="overflow-x-auto">
                    <table class="min-w-full divide-y divide-gray-200">
                        <thead class="bg-gray-50">
                        <tr>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                Reservation #
                            </th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                Guest Name
                            </th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                Check-In
                            </th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                Check-Out
                            </th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                Status
                            </th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                Actions
                            </th>
                        </tr>
                        </thead>
                        <tbody class="bg-white divide-y divide-gray-200">
                        <c:forEach var="reservation" items="${recentReservations}">
                            <tr class="hover:bg-gray-50">
                                <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                                        ${reservation.reservationNumber}
                                </td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700">
                                        ${reservation.guestName}
                                </td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700">
                                        ${reservation.checkInDate}
                                </td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700">
                                        ${reservation.checkOutDate}
                                </td>
                                <td class="px-6 py-4 whitespace-nowrap">
                                    <c:choose>
                                        <c:when test="${reservation.status == 'CONFIRMED'}">
                                                    <span class="px-2 py-1 text-xs font-semibold rounded-full bg-green-100 text-green-800">
                                                        <i class="fas fa-check-circle mr-1"></i>Confirmed
                                                    </span>
                                        </c:when>
                                        <c:when test="${reservation.status == 'CANCELLED'}">
                                                    <span class="px-2 py-1 text-xs font-semibold rounded-full bg-red-100 text-red-800">
                                                        <i class="fas fa-times-circle mr-1"></i>Cancelled
                                                    </span>
                                        </c:when>
                                        <c:otherwise>
                                                    <span class="px-2 py-1 text-xs font-semibold rounded-full bg-blue-100 text-blue-800">
                                                        <i class="fas fa-clock mr-1"></i>${reservation.status}
                                                    </span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm">
                                    <a href="${pageContext.request.contextPath}/reservation?action=search&reservationNumber=${reservation.reservationNumber}"
                                       class="text-indigo-600 hover:text-indigo-900 mr-3">
                                        <i class="fas fa-eye mr-1"></i>View
                                    </a>
                                    <a href="${pageContext.request.contextPath}/bill?reservationNumber=${reservation.reservationNumber}"
                                       class="text-green-600 hover:text-green-900">
                                        <i class="fas fa-file-invoice mr-1"></i>Bill
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <div class="text-center py-12">
                    <i class="fas fa-inbox text-6xl text-gray-300 mb-4"></i>
                    <p class="text-gray-500 text-lg mb-4">No recent reservations found</p>
                    <a href="${pageContext.request.contextPath}/reservation?action=new"
                       class="bg-indigo-600 hover:bg-indigo-700 text-white px-6 py-3 rounded-lg transition duration-200 inline-block">
                        <i class="fas fa-plus-circle mr-2"></i>Create First Reservation
                    </a>
                </div>
            </c:otherwise>
        </c:choose>

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

</body>
</html>
