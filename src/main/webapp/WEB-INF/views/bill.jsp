<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bill - Ocean View Resort</title>

    <!-- Tailwind CSS CDN -->
    <script src="https://cdn.tailwindcss.com"></script>

    <!-- Font Awesome CDN -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <style>
        @media print {
            .no-print {
                display: none !important;
            }

            body {
                background: white;
            }

            .print-section {
                box-shadow: none !important;
            }
        }
    </style>
</head>
<body class="bg-gray-100">

<!-- Navigation Bar (No Print) -->
<nav class="bg-white shadow-lg mb-8 no-print">
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
<div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 pb-12">

    <!-- Page Header (No Print) -->
    <div class="bg-white rounded-lg shadow-lg p-6 mb-8 no-print">
        <h2 class="text-3xl font-bold text-gray-800 mb-2">
            <i class="fas fa-file-invoice-dollar text-indigo-600 mr-3"></i>
            Generate Bill
        </h2>
        <p class="text-gray-600">Enter reservation number to generate billing invoice</p>
    </div>

    <!-- Search Form (No Print) -->
    <c:if test="${empty bill}">
        <div class="bg-white rounded-lg shadow-lg p-6 mb-8 no-print">
            <form action="${pageContext.request.contextPath}/bill" method="get" class="flex gap-4">
                <div class="flex-1">
                    <input
                            type="text"
                            name="reservationNumber"
                            placeholder="Enter Reservation Number"
                            class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
                            value="${param.reservationNumber}"
                            required
                    >
                </div>
                <button
                        type="submit"
                        class="px-6 py-3 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg transition duration-200 shadow-lg">
                    <i class="fas fa-calculator mr-2"></i>Generate Bill
                </button>
            </form>
        </div>
    </c:if>

    <!-- Messages (No Print) -->
    <c:if test="${not empty error}">
        <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-lg mb-6 flex items-center no-print">
            <i class="fas fa-exclamation-circle mr-3"></i>
            <p>${error}</p>
        </div>
    </c:if>

    <!-- Bill Invoice -->
    <c:if test="${not empty bill}">
        <div class="bg-white rounded-lg shadow-lg print-section">

            <!-- Invoice Header -->
            <div class="bg-gradient-to-r from-indigo-600 to-purple-600 text-white p-8 rounded-t-lg">
                <div class="flex justify-between items-start">
                    <div>
                        <h1 class="text-4xl font-bold mb-2">INVOICE</h1>
                        <p class="text-indigo-100">Ocean View Resort</p>
                        <p class="text-indigo-100 text-sm">Room Reservation System</p>
                    </div>
                    <div class="text-right">
                        <i class="fas fa-umbrella-beach text-6xl opacity-50"></i>
                    </div>
                </div>
            </div>

            <!-- Bill Details -->
            <div class="p-8">

                <!-- Bill and Reservation Info -->
                <div class="grid grid-cols-2 gap-8 mb-8">
                    <div>
                        <h3 class="text-sm font-semibold text-gray-600 uppercase mb-2">Bill Information</h3>
                        <div class="space-y-1">
                            <p class="text-lg"><strong>Bill ID:</strong> ${bill.billId}</p>
                            <p><strong>Generated Date:</strong> ${bill.createdAt}</p>
                        </div>
                    </div>
                    <div>
                        <h3 class="text-sm font-semibold text-gray-600 uppercase mb-2">Reservation Details</h3>
                        <div class="space-y-1">
                            <p class="text-lg"><strong>Reservation #:</strong> ${bill.reservationNumber}</p>
                            <p><strong>Status:</strong>
                                <span class="px-2 py-1 text-xs font-semibold rounded ${reservation.status == 'CONFIRMED' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'}">
                                        ${reservation.status}
                                </span>
                            </p>
                        </div>
                    </div>
                </div>

                <!-- Guest Information -->
                <div class="mb-8 pb-8 border-b border-gray-200">
                    <h3 class="text-sm font-semibold text-gray-600 uppercase mb-3">Guest Information</h3>
                    <div class="bg-gray-50 p-4 rounded-lg">
                        <p class="text-xl font-bold text-gray-800 mb-2">${reservation.guestName}</p>
                        <p class="text-gray-700">${reservation.address}</p>
                        <p class="text-gray-700 mt-1">
                            <i class="fas fa-phone mr-2"></i>${reservation.contactNumber}
                            <c:if test="${not empty reservation.email}">
                                <span class="ml-4"><i class="fas fa-envelope mr-2"></i>${reservation.email}</span>
                            </c:if>
                        </p>
                    </div>
                </div>

                <!-- Billing Breakdown -->
                <div class="mb-8">
                    <h3 class="text-sm font-semibold text-gray-600 uppercase mb-4">Billing Details</h3>

                    <div class="space-y-3">
                        <!-- Room Type -->
                        <div class="flex justify-between items-center py-3 border-b border-gray-200">
                            <div>
                                <p class="font-semibold text-gray-800">Room Type</p>
                                <p class="text-sm text-gray-600">${bill.roomType}</p>
                            </div>
                            <p class="font-semibold text-gray-800">
                                LKR <fmt:formatNumber value="${bill.ratePerNight}" pattern="#,##0.00"/>
                            </p>
                        </div>

                        <!-- Check-In/Out Dates -->
                        <div class="flex justify-between items-center py-3 border-b border-gray-200">
                            <div>
                                <p class="font-semibold text-gray-800">Stay Period</p>
                                <p class="text-sm text-gray-600">
                                        ${bill.checkInDate}
                                    to
                                        ${bill.checkOutDate}
                                </p>
                            </div>
                            <p class="font-semibold text-gray-800">${bill.numberOfNights} night(s)</p>
                        </div>

                        <!-- Number of Guests -->
                        <div class="flex justify-between items-center py-3 border-b border-gray-200">
                            <div>
                                <p class="font-semibold text-gray-800">Number of Guests</p>
                            </div>
                            <p class="font-semibold text-gray-800">${reservation.numberOfGuests} guest(s)</p>
                        </div>

                        <!-- Subtotal -->
                        <div class="flex justify-between items-center py-3">
                            <p class="text-lg font-semibold text-gray-800">Subtotal</p>
                            <p class="text-lg font-semibold text-gray-800">
                                LKR <fmt:formatNumber value="${bill.subtotal}" pattern="#,##0.00"/>
                            </p>
                        </div>

                        <!-- Tax/Service Charge (if applicable) -->
                        <c:if test="${bill.taxAmount != null && bill.taxAmount > 0}">
                            <div class="flex justify-between items-center py-2">
                                <p class="text-gray-700">Tax & Service Charge</p>
                                <p class="text-gray-800">
                                    LKR <fmt:formatNumber value="${bill.taxAmount}" pattern="#,##0.00"/>
                                </p>
                            </div>
                        </c:if>

                        <!-- Discount (if applicable) -->
                        <c:if test="${bill.discountAmount != null && bill.discountAmount > 0}">
                            <div class="flex justify-between items-center py-2 text-green-600">
                                <p>Discount</p>
                                <p>- LKR <fmt:formatNumber value="${bill.discountAmount}" pattern="#,##0.00"/></p>
                            </div>
                        </c:if>
                    </div>
                </div>

                <!-- Total Amount -->
                <div class="bg-gradient-to-r from-indigo-50 to-purple-50 p-6 rounded-lg border-2 border-indigo-200">
                    <div class="flex justify-between items-center">
                        <div>
                            <p class="text-gray-600 text-sm font-semibold uppercase mb-1">Total Amount Due</p>
                            <p class="text-gray-600 text-xs">(${bill.numberOfNights} nights × LKR <fmt:formatNumber
                                    value="${bill.ratePerNight}" pattern="#,##0.00"/>)</p>
                        </div>
                        <p class="text-4xl font-bold text-indigo-600">
                            LKR <fmt:formatNumber value="${bill.totalAmount}" pattern="#,##0.00"/>
                        </p>
                    </div>
                </div>

                <!-- Payment Instructions -->
                <div class="mt-8 pt-8 border-t border-gray-200">
                    <h3 class="text-sm font-semibold text-gray-600 uppercase mb-3">Payment Instructions</h3>
                    <div class="bg-yellow-50 p-4 rounded-lg border border-yellow-200">
                        <ul class="space-y-2 text-sm text-gray-700">
                            <li><i class="fas fa-check-circle text-green-600 mr-2"></i>Payment is due at check-in</li>
                            <li><i class="fas fa-check-circle text-green-600 mr-2"></i>We accept cash, credit cards, and
                                bank transfers
                            </li>
                            <li><i class="fas fa-check-circle text-green-600 mr-2"></i>Cancellation policy: Full refund
                                if cancelled 48 hours before check-in
                            </li>
                        </ul>
                    </div>
                </div>

                <!-- Footer Notes -->
                <div class="mt-8 text-center text-sm text-gray-500">
                    <p>Thank you for choosing Ocean View Resort!</p>
                    <p class="mt-2">For inquiries, please contact: +94 11 234 5678 | info@oceanviewresort.lk</p>
                    <p class="mt-4 text-xs">This is a computer-generated invoice. No signature required.</p>
                </div>

                <!-- Action Buttons (No Print) -->
                <div class="mt-8 pt-8 border-t border-gray-200 flex justify-center gap-4 no-print">
                    <button
                            onclick="window.print()"
                            class="px-6 py-3 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg transition duration-200 shadow-lg">
                        <i class="fas fa-print mr-2"></i>Print Invoice
                    </button>
                    <a href="${pageContext.request.contextPath}/reservation?action=search&reservationNumber=${reservation.reservationNumber}"
                       class="px-6 py-3 bg-gray-600 hover:bg-gray-700 text-white rounded-lg transition duration-200 shadow-lg">
                        <i class="fas fa-eye mr-2"></i>View Reservation
                    </a>
                    <a href="${pageContext.request.contextPath}/bill"
                       class="px-6 py-3 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50 transition duration-200">
                        <i class="fas fa-plus mr-2"></i>New Bill
                    </a>
                </div>

            </div>
        </div>
    </c:if>

</div>

</body>
</html>
