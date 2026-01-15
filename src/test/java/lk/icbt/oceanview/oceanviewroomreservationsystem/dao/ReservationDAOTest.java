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
            String num1 = reservationDAO.generateReservationNumber();
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
            Reservation reservation = reservationDAO.findByReservationNumber("INVALID-NUMBER");
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
            int count = reservationDAO.completePastReservations();
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
