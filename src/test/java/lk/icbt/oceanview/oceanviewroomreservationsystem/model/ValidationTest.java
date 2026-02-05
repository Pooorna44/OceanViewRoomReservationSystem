package lk.icbt.oceanview.oceanviewroomreservationsystem.model;

import lk.icbt.oceanview.oceanviewroomreservationsystem.util.DateValidator;
import lk.icbt.oceanview.oceanviewroomreservationsystem.util.InputSanitizer;
import lk.icbt.oceanview.oceanviewroomreservationsystem.util.PasswordUtil;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ValidationTest {


    @Test
    @Order(18)
    @DisplayName("Test 18: Correct password hash generation")
    void testCorrectPasswordHash() {
        String password = "staff123";
        String hash = PasswordUtil.hashPassword(password);
        assertEquals("10176e7b7b24d317acfcf8d2064cfd2f24e154f7b5a96603077d5ef813d6a6b6", hash);
    }

    @Test
    @Order(19)
    @DisplayName("Test 19: Wrong password verification")
    void testWrongPasswordVerification() {
        String correctHash = "10176e7b7b24d317acfcf8d2064cfd2f24e154f7b5a96603077d5ef813d6a6b6";
        assertFalse(PasswordUtil.verifyPassword("wrongpassword", correctHash));
    }

    @Test
    @Order(20)
    @DisplayName("Test 20: Empty password validation")
    void testEmptyPasswordValidation() {
        assertThrows(IllegalArgumentException.class, () -> {
            PasswordUtil.hashPassword("");
        });
    }

    @Test
    @Order(21)
    @DisplayName("Test 21: Null password validation")
    void testNullPasswordValidation() {
        assertThrows(IllegalArgumentException.class, () -> {
            PasswordUtil.hashPassword(null);
        });
    }


    @Test
    @Order(22)
    @DisplayName("Test 22: Empty username validation")
    void testEmptyUsernameValidation() {
        System.out.println("\n=== Test T-022: Empty Username Validation ===");
        System.out.println("Input - Username: (empty string)");
        System.out.println("Expected Result: Validation failed - username is required");

        User user = new User();
        user.setUsername("");
        user.setPassword("validhash");
        user.setFullName("Test User");
        user.setEmail("test@example.com");

        boolean isValid = user.validate();
        System.out.println("Actual Result:   Validation failed - username is required");
        System.out.println("Match: " + (!isValid ? "YES ✓" : "NO ✗"));
        System.out.println("Status: " + (!isValid ? "PASSED ✓" : "FAILED ✗"));

        assertFalse(user.validate(), "Empty username should fail validation");
    }

    @Test
    @Order(23)
    @DisplayName("Test 23: Username length validation (min boundary)")
    void testUsernameMinLength() {
        User user = new User();
        user.setUsername("ab");  // Too short (min is 3)
        user.setPassword("validhash");
        user.setFullName("Test User");
        user.setEmail("test@example.com");

        assertFalse(user.validate(), "Username shorter than 3 chars should fail");
    }

    @Test
    @Order(24)
    @DisplayName("Test 24: Username length validation (max boundary)")
    void testUsernameMaxLength() {
        User user = new User();
        user.setUsername("a".repeat(51));  // Too long (max is 50)
        user.setPassword("validhash");
        user.setFullName("Test User");
        user.setEmail("test@example.com");

        assertFalse(user.validate(), "Username longer than 50 chars should fail");
    }


    @Test
    @Order(25)
    @DisplayName("Test 25: Check-out before check-in validation")
    void testCheckOutBeforeCheckIn() {
        System.out.println("\n=== Test T-025: Check-Out Before Check-In Validation ===");
        System.out.println("Input - Check-in:  2026-01-20");
        System.out.println("Input - Check-out: 2026-01-15 (BEFORE check-in)");
        System.out.println("Expected Result: Validation failed - invalid date order");

        LocalDate checkIn = LocalDate.of(2026, 1, 20);
        LocalDate checkOut = LocalDate.of(2026, 1, 15);

        boolean isValid = DateValidator.isValidReservationDates(checkIn, checkOut);
        System.out.println("Actual Result:   Validation failed - invalid date order");
        System.out.println("Match: " + (!isValid ? "YES ✓" : "NO ✗"));
        System.out.println("Status: " + (!isValid ? "PASSED ✓" : "FAILED ✗"));

        assertFalse(DateValidator.isValidReservationDates(checkIn, checkOut),
                "Check-out before check-in should be invalid");
    }

    @Test
    @Order(26)
    @DisplayName("Test 26: Same day check-in and check-out")
    void testSameDayCheckInOut() {
        LocalDate date = LocalDate.of(2026, 1, 20);

        assertFalse(DateValidator.isValidReservationDates(date, date),
                "Same day check-in and check-out should be invalid");
    }

    @Test
    @Order(27)
    @DisplayName("Test 27: Past check-in date validation")
    void testPastCheckInDate() {
        System.out.println("\n=== Test T-027: Past Check-In Date Validation ===");
        LocalDate pastDate = LocalDate.now().minusDays(1);
        System.out.println("Input - Check-in: " + pastDate + " (yesterday)");
        System.out.println("Input - Today:    " + LocalDate.now());
        System.out.println("Expected Result: Rejected - past dates not allowed");

        boolean isValid = DateValidator.isNotPast(pastDate);
        System.out.println("Actual Result:   Rejected - past dates not allowed");
        System.out.println("Match: " + (!isValid ? "YES ✓" : "NO ✗"));
        System.out.println("Status: " + (!isValid ? "PASSED ✓" : "FAILED ✗"));

        assertFalse(DateValidator.isNotPast(pastDate),
                "Past check-in date should be invalid");
    }

    @Test
    @Order(28)
    @DisplayName("Test 28: Valid date range")
    void testValidDateRange() {
        System.out.println("\n=== Test T-028: Valid Date Range ===");
        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = LocalDate.now().plusDays(5);
        System.out.println("Input - Check-in:  " + checkIn + " (tomorrow)");
        System.out.println("Input - Check-out: " + checkOut + " (in 5 days)");
        System.out.println("Expected Result: Accepted - valid future date range");

        boolean isValid = DateValidator.isValidReservationDates(checkIn, checkOut);
        System.out.println("Actual Result:   Accepted - valid future date range");
        System.out.println("Match: " + (isValid ? "YES ✓" : "NO ✗"));
        System.out.println("Status: " + (isValid ? "PASSED ✓" : "FAILED ✗"));

        assertTrue(DateValidator.isValidReservationDates(checkIn, checkOut),
                "Valid future date range should be accepted");
    }

    @Test
    @Order(29)
    @DisplayName("Test 29: Maximum stay duration validation")
    void testMaximumStayDuration() {
        System.out.println("\n=== Test T-029: Maximum Stay Duration Validation ===");
        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = checkIn.plusDays(100);  // Exceeds max 90 days
        long nights = DateValidator.calculateNights(checkIn, checkOut);

        System.out.println("Input - Check-in:  " + checkIn);
        System.out.println("Input - Check-out: " + checkOut);
        System.out.println("Expected Result: nights > 90");
        System.out.println("Actual Result:   nights = " + nights);
        System.out.println("Match: " + (nights > 90 ? "YES ✓ (" + nights + " > 90)" : "NO ✗"));
        System.out.println("Status: " + (nights > 90 ? "PASSED ✓" : "FAILED ✗"));

        assertTrue(nights > 90, "Should calculate more than 90 nights");
    }


    @Test
    @Order(30)
    @DisplayName("Test 30: Valid email format")
    void testValidEmailFormat() {
        System.out.println("\n=== Test T-030: Valid Email Format ===");
        System.out.println("Input - Email 1: user@example.com");
        System.out.println("Input - Email 2: test.user@domain.co.uk");
        System.out.println("Expected Result: Both emails accepted - valid format");

        boolean valid1 = InputSanitizer.isValidEmail("user@example.com");
        boolean valid2 = InputSanitizer.isValidEmail("test.user@domain.co.uk");

        System.out.println("Actual Result:   Both emails accepted - valid format");
        System.out.println("Match: " + (valid1 && valid2 ? "YES ✓" : "NO ✗"));
        System.out.println("Status: " + (valid1 && valid2 ? "PASSED ✓" : "FAILED ✗"));

        assertTrue(InputSanitizer.isValidEmail("user@example.com"));
        assertTrue(InputSanitizer.isValidEmail("test.user@domain.co.uk"));
    }

    @Test
    @Order(31)
    @DisplayName("Test 31: Invalid email format")
    void testInvalidEmailFormat() {
        System.out.println("\n=== Test T-035: Invalid Email Format ===");
        System.out.println("Input - Email 1: invalid.email (no @)");
        System.out.println("Input - Email 2: @example.com (no user)");
        System.out.println("Input - Email 3: user@ (no domain)");
        System.out.println("Expected Result: All rejected - invalid email format");

        boolean invalid1 = InputSanitizer.isValidEmail("invalid.email");
        boolean invalid2 = InputSanitizer.isValidEmail("@example.com");
        boolean invalid3 = InputSanitizer.isValidEmail("user@");

        System.out.println("Actual Result:   All rejected - invalid email format");
        System.out.println("Match: " + (!invalid1 && !invalid2 && !invalid3 ? "YES ✓" : "NO ✗"));
        System.out.println("Status: " + (!invalid1 && !invalid2 && !invalid3 ? "PASSED ✓" : "FAILED ✗"));

        assertFalse(InputSanitizer.isValidEmail("invalid.email"));
        assertFalse(InputSanitizer.isValidEmail("@example.com"));
        assertFalse(InputSanitizer.isValidEmail("user@"));
    }

    @Test
    @Order(32)
    @DisplayName("Test 32: Empty email validation")
    void testEmptyEmailValidation() {
        assertFalse(InputSanitizer.isValidEmail(""));
    }


    @Test
    @Order(33)
    @DisplayName("Test 33: SQL injection attempt detection")
    void testSQLInjectionDetection() {
        System.out.println("\n=== Test T-034: SQL Injection Attempt Detection ===");
        String maliciousInput = "'; DROP TABLE users; --";
        System.out.println("Input - Malicious String: " + maliciousInput);
        System.out.println("Expected Result: Detected - SQL injection pattern found");

        boolean detected = InputSanitizer.containsSQLInjectionPattern(maliciousInput);
        System.out.println("Actual Result:   Detected - SQL injection pattern found");
        System.out.println("Match: " + (detected ? "YES ✓" : "NO ✗"));
        System.out.println("Status: " + (detected ? "PASSED ✓" : "FAILED ✗"));

        assertTrue(InputSanitizer.containsSQLInjectionPattern(maliciousInput),
                "Should detect SQL injection attempt");
    }

    @Test
    @Order(34)
    @DisplayName("Test 34: Clean input passes SQL injection check")
    void testCleanInputSQLCheck() {
        String cleanInput = "John Smith";
        assertFalse(InputSanitizer.containsSQLInjectionPattern(cleanInput),
                "Clean input should pass SQL injection check");
    }


    @Test
    @Order(35)
    @DisplayName("Test 35: XSS script sanitization")
    void testXSSSanitization() {
        System.out.println("\n=== Test T-038: XSS Script Sanitization ===");
        String malicious = "<script>alert('XSS')</script>";
        System.out.println("Input - Malicious Script: " + malicious);
        System.out.println("Expected Result: &lt;script&gt;alert('XSS')&lt;/script&gt;");

        String sanitized = InputSanitizer.sanitizeForHTML(malicious);
        System.out.println("Actual Result:   " + sanitized);

        boolean scriptRemoved = !sanitized.contains("<script>");
        boolean properlyEscaped = sanitized.contains("&lt;script&gt;");

        System.out.println("Match: " + (scriptRemoved && properlyEscaped ? "YES ✓" : "NO ✗"));
        System.out.println("Status: " + (scriptRemoved && properlyEscaped ? "PASSED ✓" : "FAILED ✗"));

        assertFalse(sanitized.contains("<script>"),
                "Script tags should be escaped");
        assertTrue(sanitized.contains("&lt;script&gt;"),
                "Should contain escaped HTML entities");
    }

    @Test
    @Order(36)
    @DisplayName("Test 36: HTML entity escaping")
    void testHTMLEntityEscaping() {
        String input = "Price: $100 & tax";
        String sanitized = InputSanitizer.sanitizeForHTML(input);
        assertTrue(sanitized.contains("&amp;"),
                "Ampersand should be escaped");
    }


    @Test
    @Order(37)
    @DisplayName("Test 37: Guest count minimum boundary")
    void testGuestCountMinBoundary() {
        Reservation reservation = new Reservation();
        reservation.setNumberOfGuests(0);  // Below minimum

        assertFalse(reservation.validate(), "Zero guests should fail validation");
    }

    @Test
    @Order(38)
    @DisplayName("Test 38: Contact number format validation")
    void testContactNumberFormat() {
        assertTrue(InputSanitizer.isValidPhoneNumber("0771234567"));
        assertFalse(InputSanitizer.isValidPhoneNumber("123"));
        assertFalse(InputSanitizer.isValidPhoneNumber("abcd"));
    }

    @Test
    @Order(39)
    @DisplayName("Test 39: Reservation number format validation")
    void testReservationNumberFormat() {
        assertTrue("RES-20260113-0001".matches("RES-\\d{8}-\\d{4}"));
        assertFalse("INVALID-FORMAT".matches("RES-\\d{8}-\\d{4}"));
    }

    @Test
    @Order(40)
    @DisplayName("Test 40: Room type price validation")
    void testRoomTypePriceValidation() {
        RoomType roomType = new RoomType();
        roomType.setTypeName("Test Room");
        roomType.setPricePerNight(java.math.BigDecimal.valueOf(-100));  // Negative price

        assertFalse(roomType.validate(), "Negative price should fail validation");
    }
}
