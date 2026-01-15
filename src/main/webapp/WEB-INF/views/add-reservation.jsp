<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Reservation - Ocean View Resort</title>

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
<div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 pb-12">

    <!-- Page Header -->
    <div class="bg-white rounded-lg shadow-lg p-6 mb-8">
        <h2 class="text-3xl font-bold text-gray-800 mb-2">
            <i class="fas fa-plus-circle text-indigo-600 mr-3"></i>
            Add New Reservation
        </h2>
        <p class="text-gray-600">Fill in the guest details and room preferences</p>
    </div>

    <!-- Error Messages -->
    <c:if test="${not empty error}">
        <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-lg mb-6 flex items-center">
            <i class="fas fa-exclamation-circle mr-3"></i>
            <p>${error}</p>
        </div>
    </c:if>

    <!-- Reservation Form -->
    <div class="bg-white rounded-lg shadow-lg p-8">
        <form action="${pageContext.request.contextPath}/reservation" method="post" id="reservationForm">
            <input type="hidden" name="action" value="create">

            <!-- Guest Information Section -->
            <div class="mb-8">
                <h3 class="text-xl font-bold text-gray-800 mb-4 flex items-center">
                    <i class="fas fa-user text-indigo-600 mr-2"></i>
                    Guest Information
                </h3>
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">

                    <!-- Guest Name -->
                    <div>
                        <label for="guestName" class="block text-gray-700 font-semibold mb-2">
                            Guest Name <span class="text-red-500">*</span>
                        </label>
                        <input
                                type="text"
                                id="guestName"
                                name="guestName"
                                required
                                maxlength="100"
                                class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
                                placeholder="Enter guest full name"
                                value="${param.guestName}"
                        >
                    </div>

                    <!-- Contact Number -->
                    <div>
                        <label for="contactNumber" class="block text-gray-700 font-semibold mb-2">
                            Contact Number <span class="text-red-500">*</span>
                        </label>
                        <input
                                type="tel"
                                id="contactNumber"
                                name="contactNumber"
                                required
                                pattern="[0-9+\-\s()]+"
                                maxlength="20"
                                class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
                                placeholder="e.g., +94 77 123 4567"
                                value="${param.contactNumber}"
                        >
                    </div>

                    <!-- Email -->
                    <div>
                        <label for="email" class="block text-gray-700 font-semibold mb-2">
                            Email Address
                        </label>
                        <input
                                type="email"
                                id="email"
                                name="email"
                                maxlength="100"
                                class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
                                placeholder="guest@example.com"
                                value="${param.email}"
                        >
                    </div>

                    <!-- Number of Guests -->
                    <div>
                        <label for="numberOfGuests" class="block text-gray-700 font-semibold mb-2">
                            Number of Guests <span class="text-red-500">*</span>
                        </label>
                        <input
                                type="number"
                                id="numberOfGuests"
                                name="numberOfGuests"
                                required
                                min="1"
                                max="10"
                                class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
                                placeholder="Number of guests"
                                value="${param.numberOfGuests != null ? param.numberOfGuests : 1}"
                        >
                    </div>

                    <!-- Address (Full Width) -->
                    <div class="md:col-span-2">
                        <label for="address" class="block text-gray-700 font-semibold mb-2">
                            Address <span class="text-red-500">*</span>
                        </label>
                        <textarea
                                id="address"
                                name="address"
                                required
                                rows="3"
                                maxlength="255"
                                class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
                                placeholder="Enter full address"
                        >${param.address}</textarea>
                    </div>

                </div>
            </div>

            <!-- Room & Booking Details Section -->
            <div class="mb-8">
                <h3 class="text-xl font-bold text-gray-800 mb-4 flex items-center">
                    <i class="fas fa-bed text-indigo-600 mr-2"></i>
                    Room & Booking Details
                </h3>
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">

                    <!-- Room Type -->
                    <div class="md:col-span-2">
                        <label for="roomTypeId" class="block text-gray-700 font-semibold mb-2">
                            Room Type <span class="text-red-500">*</span>
                        </label>
                        <select
                                id="roomTypeId"
                                name="roomTypeId"
                                required
                                onchange="updateRoomTypeInfo()"
                                class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
                        >
                            <option value="">-- Select Room Type --</option>
                            <c:forEach var="roomType" items="${roomTypes}">
                                <option value="${roomType.roomTypeId}"
                                        data-price="${roomType.pricePerNight}"
                                        data-capacity="${roomType.capacity}"
                                        data-description="${roomType.description}"
                                    ${param.roomTypeId == roomType.roomTypeId ? 'selected' : ''}>
                                        ${roomType.typeName} - LKR ${roomType.pricePerNight}/night
                                    (Capacity: ${roomType.capacity})
                                </option>
                            </c:forEach>
                        </select>
                        <div id="roomTypeInfo" class="mt-2 p-3 bg-blue-50 rounded-lg hidden">
                            <p class="text-sm text-blue-800"></p>
                        </div>
                    </div>

                    <!-- Check-In Date -->
                    <div>
                        <label for="checkInDate" class="block text-gray-700 font-semibold mb-2">
                            Check-In Date <span class="text-red-500">*</span>
                        </label>
                        <input
                                type="date"
                                id="checkInDate"
                                name="checkInDate"
                                required
                                onchange="calculateNights()"
                                class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
                                value="${param.checkInDate}"
                        >
                    </div>

                    <!-- Check-Out Date -->
                    <div>
                        <label for="checkOutDate" class="block text-gray-700 font-semibold mb-2">
                            Check-Out Date <span class="text-red-500">*</span>
                        </label>
                        <input
                                type="date"
                                id="checkOutDate"
                                name="checkOutDate"
                                required
                                onchange="calculateNights()"
                                class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
                                value="${param.checkOutDate}"
                        >
                    </div>

                    <!-- Estimated Price -->
                    <div class="md:col-span-2">
                        <div id="priceEstimate" class="p-4 bg-green-50 rounded-lg border border-green-200 hidden">
                            <p class="text-sm text-green-800">
                                <i class="fas fa-calculator mr-2"></i>
                                <strong>Estimated Total:</strong>
                                <span id="estimatedPrice" class="text-lg font-bold">LKR 0.00</span>
                                <span id="nightsInfo" class="ml-2"></span>
                            </p>
                        </div>
                    </div>

                </div>
            </div>

            <!-- Special Requests Section -->
            <div class="mb-8">
                <h3 class="text-xl font-bold text-gray-800 mb-4 flex items-center">
                    <i class="fas fa-comment-alt text-indigo-600 mr-2"></i>
                    Special Requests (Optional)
                </h3>
                <textarea
                        id="specialRequests"
                        name="specialRequests"
                        rows="4"
                        maxlength="500"
                        class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
                        placeholder="Any special requests or requirements..."
                >${param.specialRequests}</textarea>
            </div>

            <!-- Submit Buttons -->
            <div class="flex items-center justify-end space-x-4">
                <a href="${pageContext.request.contextPath}/dashboard"
                   class="px-6 py-3 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50 transition duration-200">
                    <i class="fas fa-times mr-2"></i>Cancel
                </a>
                <button
                        type="submit"
                        class="px-6 py-3 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg transition duration-200 shadow-lg">
                    <i class="fas fa-save mr-2"></i>Create Reservation
                </button>
            </div>

        </form>
    </div>

</div>

<!-- JavaScript -->
<script>
    // Set minimum date to today
    document.addEventListener('DOMContentLoaded', function () {
        const today = new Date().toISOString().split('T')[0];
        document.getElementById('checkInDate').setAttribute('min', today);
        document.getElementById('checkOutDate').setAttribute('min', today);
    });

    // Update minimum check-out date when check-in changes
    document.getElementById('checkInDate').addEventListener('change', function () {
        const checkInDate = this.value;
        document.getElementById('checkOutDate').setAttribute('min', checkInDate);
        calculateNights();
    });

    // Calculate nights and estimated price
    function calculateNights() {
        const checkInDate = document.getElementById('checkInDate').value;
        const checkOutDate = document.getElementById('checkOutDate').value;
        const roomTypeSelect = document.getElementById('roomTypeId');

        if (checkInDate && checkOutDate && roomTypeSelect.value) {
            const checkIn = new Date(checkInDate);
            const checkOut = new Date(checkOutDate);
            const nights = Math.ceil((checkOut - checkIn) / (1000 * 60 * 60 * 24));

            if (nights > 0) {
                const selectedOption = roomTypeSelect.options[roomTypeSelect.selectedIndex];
                const pricePerNight = parseFloat(selectedOption.getAttribute('data-price'));
                const totalPrice = nights * pricePerNight;

                document.getElementById('nightsInfo').textContent = `(${nights} night${nights > 1 ? 's' : ''})`;
                document.getElementById('estimatedPrice').textContent = `LKR ${totalPrice.toLocaleString('en-US', {minimumFractionDigits: 2})}`;
                document.getElementById('priceEstimate').classList.remove('hidden');
            } else {
                document.getElementById('priceEstimate').classList.add('hidden');
                alert('Check-out date must be after check-in date');
            }
        }
    }

    // Update room type info
    function updateRoomTypeInfo() {
        const roomTypeSelect = document.getElementById('roomTypeId');
        const infoDiv = document.getElementById('roomTypeInfo');

        if (roomTypeSelect.value) {
            const selectedOption = roomTypeSelect.options[roomTypeSelect.selectedIndex];
            const description = selectedOption.getAttribute('data-description');
            const capacity = selectedOption.getAttribute('data-capacity');
            const price = selectedOption.getAttribute('data-price');

            infoDiv.querySelector('p').innerHTML =
                `<strong>Details:</strong> ${description} | <strong>Capacity:</strong> ${capacity} guests | <strong>Rate:</strong> LKR ${price}/night`;
            infoDiv.classList.remove('hidden');
            calculateNights();
        } else {
            infoDiv.classList.add('hidden');
        }
    }

    // Form validation
    document.getElementById('reservationForm').addEventListener('submit', function (e) {
        const checkInDate = new Date(document.getElementById('checkInDate').value);
        const checkOutDate = new Date(document.getElementById('checkOutDate').value);
        const numberOfGuests = parseInt(document.getElementById('numberOfGuests').value);
        const roomTypeSelect = document.getElementById('roomTypeId');

        // Validate dates
        if (checkOutDate <= checkInDate) {
            e.preventDefault();
            alert('Check-out date must be after check-in date');
            return false;
        }

        // Validate guest capacity
        if (roomTypeSelect.value) {
            const selectedOption = roomTypeSelect.options[roomTypeSelect.selectedIndex];
            const capacity = parseInt(selectedOption.getAttribute('data-capacity'));

            if (numberOfGuests > capacity) {
                e.preventDefault();
                alert(`Selected room type can accommodate maximum ${capacity} guests. Please select a different room type or reduce number of guests.`);
                return false;
            }
        }

        return true;
    });
</script>

</body>
</html>
