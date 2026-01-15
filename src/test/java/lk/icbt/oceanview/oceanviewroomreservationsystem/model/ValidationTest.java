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
        User user = new User();
        user.setUsername("");
        user.setPassword("validhash");
        user.setFullName("Test User");
        user.setEmail("test@example.com");

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
        LocalDate checkIn = LocalDate.of(2026, 1, 20);
        LocalDate checkOut = LocalDate.of(2026, 1, 15);

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
        LocalDate pastDate = LocalDate.now().minusDays(1);

        assertFalse(DateValidator.isNotPast(pastDate),
                "Past check-in date should be invalid");
    }

    @Test
    @Order(28)
    @DisplayName("Test 28: Valid date range")
    void testValidDateRange() {
        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = LocalDate.now().plusDays(5);

        assertTrue(DateValidator.isValidReservationDates(checkIn, checkOut),
                "Valid future date range should be accepted");
    }

    @Test
    @Order(29)
    @DisplayName("Test 29: Maximum stay duration validation")
    void testMaximumStayDuration() {
        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = checkIn.plusDays(100);  // Exceeds max 90 days
        long nights = DateValidator.calculateNights(checkIn, checkOut);

        assertTrue(nights > 90, "Should calculate more than 90 nights");
    }


    @Test
    @Order(30)
    @DisplayName("Test 30: Valid email format")
    void testValidEmailFormat() {
        assertTrue(InputSanitizer.isValidEmail("user@example.com"));
        assertTrue(InputSanitizer.isValidEmail("test.user@domain.co.uk"));
    }

    @Test
    @Order(31)
    @DisplayName("Test 31: Invalid email format")
    void testInvalidEmailFormat() {
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
        String maliciousInput = "'; DROP TABLE users; --";
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
        String malicious = "<script>alert('XSS')</script>";
        String sanitized = InputSanitizer.sanitizeForHTML(malicious);
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
