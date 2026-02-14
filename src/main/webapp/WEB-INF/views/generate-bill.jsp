<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Generate Bill - Ocean View Resort</title>

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
    <div class="bg-white rounded-lg shadow-lg p-6 mb-8">
        <h2 class="text-3xl font-bold text-gray-800 mb-2">
            <i class="fas fa-file-invoice-dollar text-yellow-600 mr-3"></i>
            Generate Bill
        </h2>
        <p class="text-gray-600">Select a reservation to generate a bill</p>
    </div>

    <!-- Messages -->
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

    <!-- Reservations List -->
    <div class="bg-white rounded-lg shadow-lg overflow-hidden">
        <div class="p-6">
            <h3 class="text-xl font-semibold text-gray-800 mb-4">
                <i class="fas fa-calendar-check text-indigo-600 mr-2"></i>
                Active Reservations
            </h3>

            <c:choose>
                <c:when test="${empty reservations}">
                    <div class="text-center py-12">
                        <i class="fas fa-inbox text-6xl text-gray-300 mb-4"></i>
                        <p class="text-xl text-gray-600 mb-2">No Active Reservations</p>
                        <p class="text-gray-500 mb-6">There are no confirmed reservations available to generate
                            bills.</p>
                        <a href="${pageContext.request.contextPath}/reservation?action=add"
                           class="inline-block px-6 py-3 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg transition duration-200">
                            <i class="fas fa-plus mr-2"></i>Add Reservation
                        </a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="overflow-x-auto">
                        <table class="w-full">
                            <thead class="bg-gray-50 border-b-2 border-gray-200">
                            <tr>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    Reservation #
                                </th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    Guest Name
                                </th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    Room Type
                                </th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    Check-in
                                </th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    Check-out
                                </th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    Status
                                </th>
                                <th class="px-6 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    Action
                                </th>
                            </tr>
                            </thead>
                            <tbody class="bg-white divide-y divide-gray-200">
                            <c:forEach items="${reservations}" var="reservation">
                                <tr class="hover:bg-gray-50">
                                    <td class="px-6 py-4 whitespace-nowrap">
                                        <span class="text-sm font-medium text-gray-900">${reservation.reservationNumber}</span>
                                    </td>
                                    <td class="px-6 py-4 whitespace-nowrap">
                                        <div class="text-sm text-gray-900">${reservation.guestName}</div>
                                        <div class="text-sm text-gray-500">${reservation.contactNumber}</div>
                                    </td>
                                    <td class="px-6 py-4 whitespace-nowrap">
                                        <span class="text-sm text-gray-900">${reservation.roomType.typeName}</span>
                                    </td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                                            ${reservation.checkInDate}
                                    </td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                                            ${reservation.checkOutDate}
                                    </td>
                                    <td class="px-6 py-4 whitespace-nowrap">
                                                <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full 
                                                    <c:choose>
                                                        <c:when test="${reservation.status == 'CONFIRMED'}">bg-green-100 text-green-800</c:when>
                                                        <c:when test="${reservation.status == 'COMPLETED'}">bg-blue-100 text-blue-800</c:when>
                                                        <c:when test="${reservation.status == 'CANCELLED'}">bg-red-100 text-red-800</c:when>
                                                        <c:otherwise>bg-gray-100 text-gray-800</c:otherwise>
                                                    </c:choose>">
                                                        ${reservation.status}
                                                </span>
                                    </td>
                                    <td class="px-6 py-4 whitespace-nowrap text-center">
                                        <form action="${pageContext.request.contextPath}/bill" method="post"
                                              class="inline">
                                            <input type="hidden" name="action" value="generate">
                                            <input type="hidden" name="reservationId"
                                                   value="${reservation.reservationId}">
                                            <button type="submit"
                                                    class="px-4 py-2 bg-yellow-500 hover:bg-yellow-600 text-white rounded-lg transition duration-200 text-sm">
                                                <i class="fas fa-file-invoice mr-2"></i>Generate Bill
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- Quick Links -->
    <div class="mt-8 text-center">
        <div class="inline-flex gap-4">
            <a href="${pageContext.request.contextPath}/reservation?action=view"
               class="px-6 py-3 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg transition duration-200">
                <i class="fas fa-search mr-2"></i>View Reservations
            </a>
            <a href="${pageContext.request.contextPath}/reservation?action=add"
               class="px-6 py-3 bg-green-600 hover:bg-green-700 text-white rounded-lg transition duration-200">
                <i class="fas fa-plus mr-2"></i>Add Reservation
            </a>
        </div>
    </div>

</div>

<!-- Footer -->
<footer class="bg-white shadow-lg mt-12 py-6">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center text-gray-600">
        <p>&copy; 2026 Ocean View Resort. All rights reserved.</p>
        <p class="text-sm mt-2">Room Reservation System - CIS6003 Advanced Programming</p>
    </div>
</footer>

</body>
</html>
