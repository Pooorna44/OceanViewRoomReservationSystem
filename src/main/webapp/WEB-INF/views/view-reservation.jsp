<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Reservation - Ocean View Resort</title>

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
            <i class="fas fa-search text-indigo-600 mr-3"></i>
            View Reservation
        </h2>
        <p class="text-gray-600">Search for a reservation by reservation number</p>
    </div>

    <!-- Search Form -->
    <div class="bg-white rounded-lg shadow-lg p-6 mb-8">
        <form action="${pageContext.request.contextPath}/reservation" method="get" class="flex gap-4">
            <input type="hidden" name="action" value="search">
            <div class="flex-1">
                <input
                        type="text"
                        name="reservationNumber"
                        placeholder="Enter Reservation Number (e.g., RES20260113001)"
                        class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
                        value="${param.reservationNumber}"
                        required
                >
            </div>
            <button
                    type="submit"
                    class="px-6 py-3 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg transition duration-200 shadow-lg">
                <i class="fas fa-search mr-2"></i>Search
            </button>
        </form>
    </div>

    <!-- Messages -->
    <c:if test="${param.success == 'true'}">
        <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded-lg mb-6 flex items-center">
            <i class="fas fa-check-circle mr-3"></i>
            <p><strong>Success!</strong> Reservation created successfully.</p>
        </div>
    </c:if>

    <c:if test="${param.cancelled == 'true'}">
        <div class="bg-yellow-100 border border-yellow-400 text-yellow-700 px-4 py-3 rounded-lg mb-6 flex items-center">
            <i class="fas fa-exclamation-triangle mr-3"></i>
            <p><strong>Cancelled!</strong> Reservation has been cancelled successfully.</p>
        </div>
    </c:if>

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

    <!-- Reservation Details -->
    <c:if test="${not empty reservation}">
        <div class="bg-white rounded-lg shadow-lg overflow-hidden">

            <!-- Status Banner -->
            <div class="px-6 py-4 ${reservation.status == 'CONFIRMED' ? 'bg-green-500' : reservation.status == 'CANCELLED' ? 'bg-red-500' : 'bg-blue-500'}">
                <div class="flex items-center justify-between text-white">
                    <div>
                        <p class="text-sm font-semibold uppercase">Reservation Status</p>
                        <p class="text-2xl font-bold">${reservation.status}</p>
                    </div>
                    <i class="fas ${reservation.status == 'CONFIRMED' ? 'fa-check-circle' : reservation.status == 'CANCELLED' ? 'fa-times-circle' : 'fa-clock'} text-5xl opacity-50"></i>
                </div>
            </div>

            <!-- Details Content -->
            <div class="p-6">

                <!-- Reservation Number -->
                <div class="mb-6 pb-6 border-b border-gray-200">
                    <h3 class="text-2xl font-bold text-gray-800 mb-2">
                        Reservation #${reservation.reservationNumber}
                    </h3>
                    <p class="text-sm text-gray-600">
                        Created on: ${reservation.createdAt}
                    </p>
                </div>

                <!-- Guest Information -->
                <div class="mb-6">
                    <h4 class="text-xl font-bold text-gray-800 mb-4 flex items-center">
                        <i class="fas fa-user text-indigo-600 mr-2"></i>
                        Guest Information
                    </h4>
                    <div class="grid grid-cols-1 md:grid-cols-2 gap-4 bg-gray-50 p-4 rounded-lg">
                        <div>
                            <p class="text-sm text-gray-600">Guest Name</p>
                            <p class="text-lg font-semibold text-gray-800">${reservation.guestName}</p>
                        </div>
                        <div>
                            <p class="text-sm text-gray-600">Contact Number</p>
                            <p class="text-lg font-semibold text-gray-800">${reservation.contactNumber}</p>
                        </div>
                        <div>
                            <p class="text-sm text-gray-600">Email</p>
                            <p class="text-lg font-semibold text-gray-800">${not empty reservation.email ? reservation.email : 'N/A'}</p>
                        </div>
                        <div>
                            <p class="text-sm text-gray-600">Number of Guests</p>
                            <p class="text-lg font-semibold text-gray-800">${reservation.numberOfGuests} guest(s)</p>
                        </div>
                        <div class="md:col-span-2">
                            <p class="text-sm text-gray-600">Address</p>
                            <p class="text-lg font-semibold text-gray-800">${reservation.address}</p>
                        </div>
                    </div>
                </div>

                <!-- Room Information -->
                <div class="mb-6">
                    <h4 class="text-xl font-bold text-gray-800 mb-4 flex items-center">
                        <i class="fas fa-bed text-indigo-600 mr-2"></i>
                        Room Information
                    </h4>
                    <div class="bg-indigo-50 p-6 rounded-lg">
                        <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
                            <div>
                                <p class="text-sm text-indigo-600 font-semibold">Room Type</p>
                                <p class="text-xl font-bold text-gray-800">${reservation.roomType.typeName}</p>
                            </div>
                            <div>
                                <p class="text-sm text-indigo-600 font-semibold">Room Capacity</p>
                                <p class="text-xl font-bold text-gray-800">${reservation.roomType.capacity} guests</p>
                            </div>
                            <div>
                                <p class="text-sm text-indigo-600 font-semibold">Rate per Night</p>
                                <p class="text-xl font-bold text-gray-800">LKR <fmt:formatNumber
                                        value="${reservation.roomType.pricePerNight}" pattern="#,##0.00"/></p>
                            </div>
                        </div>
                        <div class="mt-4 pt-4 border-t border-indigo-200">
                            <p class="text-sm text-indigo-600 font-semibold mb-2">Description</p>
                            <p class="text-gray-700">${reservation.roomType.description}</p>
                        </div>
                    </div>
                </div>

                <!-- Booking Dates -->
                <div class="mb-6">
                    <h4 class="text-xl font-bold text-gray-800 mb-4 flex items-center">
                        <i class="fas fa-calendar-alt text-indigo-600 mr-2"></i>
                        Booking Dates
                    </h4>
                    <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
                        <div class="bg-green-50 p-4 rounded-lg">
                            <p class="text-sm text-green-600 font-semibold mb-1">Check-In</p>
                            <p class="text-2xl font-bold text-gray-800">
                                    ${reservation.checkInDate}
                            </p>
                            <p class="text-sm text-gray-600">
                                    ${reservation.checkInDate.dayOfWeek}
                            </p>
                        </div>
                        <div class="bg-red-50 p-4 rounded-lg">
                            <p class="text-sm text-red-600 font-semibold mb-1">Check-Out</p>
                            <p class="text-2xl font-bold text-gray-800">
                                    ${reservation.checkOutDate}
                            </p>
                            <p class="text-sm text-gray-600">
                                    ${reservation.checkOutDate.dayOfWeek}
                            </p>
                        </div>
                        <div class="bg-blue-50 p-4 rounded-lg">
                            <p class="text-sm text-blue-600 font-semibold mb-1">Duration</p>
                            <p class="text-2xl font-bold text-gray-800">
                                    ${reservation.calculateNumberOfNights()} night(s)
                            </p>
                            <p class="text-sm text-gray-600">Stay Period</p>
                        </div>
                    </div>
                </div>

                <!-- Special Requests -->
                <c:if test="${not empty reservation.specialRequests}">
                    <div class="mb-6">
                        <h4 class="text-xl font-bold text-gray-800 mb-4 flex items-center">
                            <i class="fas fa-comment-alt text-indigo-600 mr-2"></i>
                            Special Requests
                        </h4>
                        <div class="bg-yellow-50 p-4 rounded-lg border border-yellow-200">
                            <p class="text-gray-700">${reservation.specialRequests}</p>
                        </div>
                    </div>
                </c:if>

                <!-- Action Buttons -->
                <div class="flex flex-wrap gap-4 pt-6 border-t border-gray-200">
                    <a href="${pageContext.request.contextPath}/bill?reservationNumber=${reservation.reservationNumber}"
                       class="px-6 py-3 bg-green-600 hover:bg-green-700 text-white rounded-lg transition duration-200 shadow-lg">
                        <i class="fas fa-file-invoice-dollar mr-2"></i>Generate Bill
                    </a>

                    <c:if test="${reservation.status == 'CONFIRMED'}">
                        <form action="${pageContext.request.contextPath}/reservation" method="post" class="inline"
                              onsubmit="return confirm('Are you sure you want to cancel this reservation?');">
                            <input type="hidden" name="action" value="cancel">
                            <input type="hidden" name="reservationId" value="${reservation.reservationId}">
                            <button
                                    type="submit"
                                    class="px-6 py-3 bg-red-600 hover:bg-red-700 text-white rounded-lg transition duration-200 shadow-lg">
                                <i class="fas fa-times-circle mr-2"></i>Cancel Reservation
                            </button>
                        </form>
                    </c:if>

                    <button
                            onclick="window.print()"
                            class="px-6 py-3 bg-gray-600 hover:bg-gray-700 text-white rounded-lg transition duration-200 shadow-lg">
                        <i class="fas fa-print mr-2"></i>Print Details
                    </button>
                </div>

            </div>
        </div>
    </c:if>

    <!-- No Results Message -->
    <c:if test="${empty reservation && not empty param.reservationNumber}">
        <div class="bg-white rounded-lg shadow-lg p-12 text-center">
            <i class="fas fa-search text-6xl text-gray-300 mb-4"></i>
            <h3 class="text-2xl font-bold text-gray-800 mb-2">No Reservation Found</h3>
            <p class="text-gray-600 mb-6">The reservation number "${param.reservationNumber}" does not exist in our
                system.</p>
            <a href="${pageContext.request.contextPath}/reservation?action=search"
               class="inline-block px-6 py-3 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg transition duration-200">
                <i class="fas fa-redo mr-2"></i>Try Again
            </a>
        </div>
    </c:if>

</div>

<!-- Print Styles -->
<style media="print">
    nav, button, .no-print {
        display: none !important;
    }

    body {
        background: white;
    }
</style>

</body>
</html>
