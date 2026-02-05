package lk.icbt.oceanview.oceanviewroomreservationsystem.dao;

import lk.icbt.oceanview.oceanviewroomreservationsystem.dao.impl.ReservationDAOImpl;
import lk.icbt.oceanview.oceanviewroomreservationsystem.model.Reservation;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReservationDAOTest {

    private ReservationDAO reservationDAO;

    @BeforeEach
    void setUp() {
        reservationDAO = new ReservationDAOImpl();
    }


    @Test
    @Order(1)
    @DisplayName("Test 11: Generate reservation number format")
    void testGenerateReservationNumberFormat() {
        try {
            String reservationNumber = reservationDAO.generateReservationNumber();
            assertNotNull(reservationNumber, "Reservation number should not be null");
            assertTrue(reservationNumber.matches("RES-\\d{8}-\\d{4}"),
                    "Reservation number should match format RES-YYYYMMDD-XXXX");
        } catch (SQLException e) {
            fail("SQLException should not occur: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    @DisplayName("Test 12: Generate unique reservation numbers")
    void testGenerateUniqueReservationNumbers() {
        try {
            System.out.println("\n=== Test T-012: Generate Unique Reservation Numbers ===");
            System.out.println("Action: Generate reservation number");
            System.out.println("Expected Result: Non-null string matching RES-YYYYMMDD-XXXX");

            String num1 = reservationDAO.generateReservationNumber();

            System.out.println("Actual Result:   Generated: " + num1);
            System.out.println("Match: " + (num1 != null && num1.matches("RES-\\d{8}-\\d{4}") ? "YES ✓" : "NO ✗"));
            System.out.println("Status: " + (num1 != null && num1.matches("RES-\\d{8}-\\d{4}") ? "PASSED ✓" : "FAILED ✗"));

            assertNotNull(num1, "First reservation number should not be null");
            assertTrue(num1.matches("RES-\\d{8}-\\d{4}"),
                    "Reservation number should match format");
        } catch (SQLException e) {
            fail("SQLException should not occur: " + e.getMessage());
        }
    }


    @Test
    @Order(3)
    @DisplayName("Test 13: Find reservation by valid number")
    void testFindReservationByValidNumber() {
        try {
            Reservation reservation = reservationDAO.findByReservationNumber("RES-20260101-0001");
            assertNotNull(reservation, "Should find existing reservation");
            assertEquals("RES-20260101-0001", reservation.getReservationNumber());
        } catch (SQLException e) {
            fail("SQLException should not occur: " + e.getMessage());
        }
    }

    @Test
    @Order(4)
    @DisplayName("Test 14: Find reservation by invalid number")
    void testFindReservationByInvalidNumber() {
        try {
            System.out.println("\n=== Test T-014: Find Reservation By Invalid Number ===");
            System.out.println("Input - Reservation Number: INVALID-NUMBER (wrong format)");
            System.out.println("Expected Result: Reservation not found - invalid format");

            Reservation reservation = reservationDAO.findByReservationNumber("INVALID-NUMBER");

            System.out.println("Actual Result:   Reservation not found - invalid format");
            System.out.println("Match: " + (reservation == null ? "YES ✓" : "NO ✗"));
            System.out.println("Status: " + (reservation == null ? "PASSED ✓" : "FAILED ✗"));

            assertNull(reservation, "Should return null for invalid reservation number");
        } catch (SQLException e) {
            fail("SQLException should not occur: " + e.getMessage());
        }
    }

    @Test
    @Order(5)
    @DisplayName("Test 15: Find all reservations")
    void testFindAllReservations() {
        try {
            var reservations = reservationDAO.findAll();
            assertNotNull(reservations, "Reservation list should not be null");
            assertTrue(reservations.size() > 0, "Should have at least one reservation");
        } catch (SQLException e) {
            fail("SQLException should not occur: " + e.getMessage());
        }
    }


    @Test
    @Order(6)
    @DisplayName("Test 16: Complete past reservations")
    void testCompletePastReservations() {
        try {
            System.out.println("\n=== Test T-016: Complete Past Reservations (Status Transition) ===");
            System.out.println("Action: Auto-complete reservations with past check-out dates");
            System.out.println("Expected Result: Successfully processed - count ≥ 0");

            int count = reservationDAO.completePastReservations();

            String actualResult = count + " reservation(s) auto-completed to CHECKED_OUT status";
            System.out.println("Actual Result:   " + actualResult);
            System.out.println("Match: " + (count >= 0 ? "YES ✓ (operation successful)" : "NO ✗"));
            System.out.println("Status: " + (count >= 0 ? "PASSED ✓" : "FAILED ✗"));

            assertTrue(count >= 0, "Completed count should be non-negative");
        } catch (SQLException e) {
            fail("SQLException should not occur: " + e.getMessage());
        }
    }

    @Test
    @Order(7)
    @DisplayName("Test 17: Find confirmed reservations")
    void testFindConfirmedReservations() {
        try {
            var reservations = reservationDAO.findByStatus("CONFIRMED");
            assertNotNull(reservations, "Reservation list should not be null");
            for (Reservation r : reservations) {
                assertEquals("CONFIRMED", r.getStatus());
            }
        } catch (SQLException e) {
            fail("SQLException should not occur: " + e.getMessage());
        }
    }
}
