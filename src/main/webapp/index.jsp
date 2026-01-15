<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome - Ocean View Resort</title>

    <!-- Tailwind CSS CDN -->
    <script src="https://cdn.tailwindcss.com"></script>

    <!-- Font Awesome CDN -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <style>
        .hero-gradient {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }

        .feature-card {
            transition: all 0.3s ease;
        }

        .feature-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.2);
        }

        .wave {
            animation: wave 3s ease-in-out infinite;
        }

        @keyframes wave {
            0%, 100% {
                transform: translateY(0);
            }
            50% {
                transform: translateY(-10px);
            }
        }

        .fade-in {
            animation: fadeIn 1s ease-in;
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
    </style>
</head>
<body class="bg-gray-50">

<!-- Hero Section -->
<section class="hero-gradient min-h-screen flex items-center justify-center relative overflow-hidden">

    <!-- Animated Background Elements -->
    <div class="absolute inset-0 overflow-hidden">
        <div class="absolute top-20 left-10 w-64 h-64 bg-white opacity-10 rounded-full blur-3xl"></div>
        <div class="absolute bottom-20 right-10 w-96 h-96 bg-white opacity-10 rounded-full blur-3xl"></div>
    </div>

    <!-- Content -->
    <div class="container mx-auto px-4 relative z-10">
        <div class="text-center text-white fade-in">

            <!-- Logo/Icon -->
            <div class="mb-8 wave">
                <i class="fas fa-umbrella-beach text-8xl md:text-9xl mb-4 drop-shadow-2xl"></i>
            </div>

            <!-- Main Title -->
            <h1 class="text-5xl md:text-7xl font-bold mb-6 drop-shadow-lg">
                Ocean View Resort
            </h1>

            <!-- Subtitle -->
            <p class="text-2xl md:text-3xl mb-4 text-indigo-100">
                Where Paradise Meets Luxury
            </p>

            <p class="text-lg md:text-xl mb-12 text-indigo-200 max-w-2xl mx-auto">
                Experience the perfect blend of comfort and elegance at our premium beachfront resort.
                Book your dream vacation with our easy-to-use reservation system.
            </p>

            <!-- CTA Buttons -->
            <div class="flex flex-col sm:flex-row gap-4 justify-center items-center mb-12">
                <a href="${pageContext.request.contextPath}/login"
                   class="bg-white text-indigo-600 px-8 py-4 rounded-lg font-bold text-lg shadow-2xl hover:bg-indigo-50 transition duration-300 transform hover:scale-105 w-64">
                    <i class="fas fa-sign-in-alt mr-2"></i>Staff Login
                </a>
                <a href="#features"
                   class="bg-transparent border-2 border-white text-white px-8 py-4 rounded-lg font-bold text-lg hover:bg-white hover:text-indigo-600 transition duration-300 transform hover:scale-105 w-64">
                    <i class="fas fa-info-circle mr-2"></i>Learn More
                </a>
            </div>

            <!-- Scroll Indicator -->
            <div class="mt-16">
                <a href="#features" class="inline-block animate-bounce">
                    <i class="fas fa-chevron-down text-4xl text-white opacity-75"></i>
                </a>
            </div>

        </div>
    </div>
</section>

<!-- Features Section -->
<section id="features" class="py-20 bg-white">
    <div class="container mx-auto px-4">

        <!-- Section Header -->
        <div class="text-center mb-16">
            <h2 class="text-4xl md:text-5xl font-bold text-gray-800 mb-4">
                Why Choose Ocean View Resort?
            </h2>
            <p class="text-xl text-gray-600 max-w-3xl mx-auto">
                Discover the perfect destination for your next vacation with world-class amenities and exceptional
                service
            </p>
        </div>

        <!-- Features Grid -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8 mb-16">

            <!-- Feature 1 -->
            <div class="feature-card bg-white rounded-xl shadow-lg p-8 text-center border-t-4 border-blue-500">
                <div class="bg-blue-100 w-20 h-20 rounded-full flex items-center justify-center mx-auto mb-6">
                    <i class="fas fa-water text-4xl text-blue-600"></i>
                </div>
                <h3 class="text-2xl font-bold text-gray-800 mb-3">Beachfront Location</h3>
                <p class="text-gray-600">
                    Direct access to pristine beaches with stunning ocean views from every room
                </p>
            </div>

            <!-- Feature 2 -->
            <div class="feature-card bg-white rounded-xl shadow-lg p-8 text-center border-t-4 border-green-500">
                <div class="bg-green-100 w-20 h-20 rounded-full flex items-center justify-center mx-auto mb-6">
                    <i class="fas fa-concierge-bell text-4xl text-green-600"></i>
                </div>
                <h3 class="text-2xl font-bold text-gray-800 mb-3">Premium Service</h3>
                <p class="text-gray-600">
                    24/7 dedicated staff ready to make your stay unforgettable
                </p>
            </div>

            <!-- Feature 3 -->
            <div class="feature-card bg-white rounded-xl shadow-lg p-8 text-center border-t-4 border-purple-500">
                <div class="bg-purple-100 w-20 h-20 rounded-full flex items-center justify-center mx-auto mb-6">
                    <i class="fas fa-utensils text-4xl text-purple-600"></i>
                </div>
                <h3 class="text-2xl font-bold text-gray-800 mb-3">Gourmet Dining</h3>
                <p class="text-gray-600">
                    World-class restaurants serving international and local cuisine
                </p>
            </div>

            <!-- Feature 4 -->
            <div class="feature-card bg-white rounded-xl shadow-lg p-8 text-center border-t-4 border-yellow-500">
                <div class="bg-yellow-100 w-20 h-20 rounded-full flex items-center justify-center mx-auto mb-6">
                    <i class="fas fa-spa text-4xl text-yellow-600"></i>
                </div>
                <h3 class="text-2xl font-bold text-gray-800 mb-3">Luxury Spa</h3>
                <p class="text-gray-600">
                    Rejuvenate your body and mind with our premium spa treatments
                </p>
            </div>

        </div>

    </div>
</section>

<!-- Room Types Section -->
<section class="py-20 bg-gray-100">
    <div class="container mx-auto px-4">

        <div class="text-center mb-16">
            <h2 class="text-4xl md:text-5xl font-bold text-gray-800 mb-4">
                Our Premium Accommodations
            </h2>
            <p class="text-xl text-gray-600">
                Choose from our selection of elegantly designed rooms and suites
            </p>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-3 gap-8">

            <!-- Standard Room -->
            <div class="bg-white rounded-xl shadow-lg overflow-hidden transform hover:scale-105 transition duration-300">
                <div class="bg-gradient-to-br from-blue-400 to-blue-600 h-48 flex items-center justify-center">
                    <i class="fas fa-bed text-white text-6xl"></i>
                </div>
                <div class="p-6">
                    <h3 class="text-2xl font-bold text-gray-800 mb-3">Standard Room</h3>
                    <p class="text-gray-600 mb-4">
                        Comfortable and cozy rooms with modern amenities and beautiful views
                    </p>
                    <ul class="space-y-2 text-gray-700">
                        <li><i class="fas fa-check text-green-500 mr-2"></i>King/Queen Bed</li>
                        <li><i class="fas fa-check text-green-500 mr-2"></i>Ocean View</li>
                        <li><i class="fas fa-check text-green-500 mr-2"></i>Free WiFi</li>
                    </ul>
                </div>
            </div>

            <!-- Deluxe Room -->
            <div class="bg-white rounded-xl shadow-lg overflow-hidden transform hover:scale-105 transition duration-300 ring-4 ring-purple-500">
                <div class="bg-gradient-to-br from-purple-400 to-purple-600 h-48 flex items-center justify-center">
                    <i class="fas fa-crown text-white text-6xl"></i>
                </div>
                <div class="p-6">
                    <div class="flex items-center justify-between mb-3">
                        <h3 class="text-2xl font-bold text-gray-800">Deluxe Room</h3>
                        <span class="bg-purple-500 text-white text-xs px-2 py-1 rounded-full">POPULAR</span>
                    </div>
                    <p class="text-gray-600 mb-4">
                        Enhanced comfort with premium features and spacious layout
                    </p>
                    <ul class="space-y-2 text-gray-700">
                        <li><i class="fas fa-check text-green-500 mr-2"></i>Luxury King Bed</li>
                        <li><i class="fas fa-check text-green-500 mr-2"></i>Private Balcony</li>
                        <li><i class="fas fa-check text-green-500 mr-2"></i>Premium Amenities</li>
                    </ul>
                </div>
            </div>

            <!-- Suite -->
            <div class="bg-white rounded-xl shadow-lg overflow-hidden transform hover:scale-105 transition duration-300">
                <div class="bg-gradient-to-br from-yellow-400 to-yellow-600 h-48 flex items-center justify-center">
                    <i class="fas fa-hotel text-white text-6xl"></i>
                </div>
                <div class="p-6">
                    <h3 class="text-2xl font-bold text-gray-800 mb-3">Luxury Suite</h3>
                    <p class="text-gray-600 mb-4">
                        Ultimate luxury with separate living area and premium services
                    </p>
                    <ul class="space-y-2 text-gray-700">
                        <li><i class="fas fa-check text-green-500 mr-2"></i>Master Bedroom</li>
                        <li><i class="fas fa-check text-green-500 mr-2"></i>Living Room</li>
                        <li><i class="fas fa-check text-green-500 mr-2"></i>VIP Services</li>
                    </ul>
                </div>
            </div>

        </div>

    </div>
</section>

<!-- CTA Section -->
<section class="py-20 hero-gradient">
    <div class="container mx-auto px-4 text-center">
        <h2 class="text-4xl md:text-5xl font-bold text-white mb-6">
            Ready to Experience Paradise?
        </h2>
        <p class="text-xl text-indigo-100 mb-8 max-w-2xl mx-auto">
            Our reservation system makes booking your dream vacation quick and easy
        </p>
        <a href="${pageContext.request.contextPath}/login"
           class="inline-block bg-white text-indigo-600 px-12 py-4 rounded-lg font-bold text-xl shadow-2xl hover:bg-indigo-50 transition duration-300 transform hover:scale-105">
            <i class="fas fa-calendar-check mr-2"></i>Access Reservation System
        </a>
    </div>
</section>

<!-- Footer -->
<footer class="bg-gray-900 text-white py-12">
    <div class="container mx-auto px-4">
        <div class="grid grid-cols-1 md:grid-cols-3 gap-8 mb-8">

            <!-- About -->
            <div>
                <h3 class="text-xl font-bold mb-4 flex items-center">
                    <i class="fas fa-umbrella-beach mr-2 text-indigo-400"></i>
                    Ocean View Resort
                </h3>
                <p class="text-gray-400">
                    Your premier destination for luxury beachfront accommodations.
                    Creating unforgettable memories since 2020.
                </p>
            </div>

            <!-- Contact -->
            <div>
                <h3 class="text-xl font-bold mb-4">Contact Us</h3>
                <ul class="space-y-2 text-gray-400">
                    <li><i class="fas fa-phone mr-2 text-indigo-400"></i>+94 11 234 5678</li>
                    <li><i class="fas fa-envelope mr-2 text-indigo-400"></i>info@oceanviewresort.lk</li>
                    <li><i class="fas fa-map-marker-alt mr-2 text-indigo-400"></i>Colombo, Sri Lanka</li>
                </ul>
            </div>

            <!-- Quick Links -->
            <div>
                <h3 class="text-xl font-bold mb-4">Staff Access</h3>
                <ul class="space-y-2">
                    <li>
                        <a href="${pageContext.request.contextPath}/login"
                           class="text-gray-400 hover:text-white transition">
                            <i class="fas fa-sign-in-alt mr-2"></i>Login
                        </a>
                    </li>
                    <li>
                        <a href="#features"
                           class="text-gray-400 hover:text-white transition">
                            <i class="fas fa-info-circle mr-2"></i>About System
                        </a>
                    </li>
                </ul>
            </div>

        </div>

        <div class="border-t border-gray-800 pt-8 text-center text-gray-400">
            <p>&copy; 2026 Ocean View Resort. All rights reserved.</p>
            <p class="mt-2 text-sm">CIS6003 Advanced Programming Project</p>
        </div>
    </div>
</footer>

<!-- Smooth Scroll -->
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
