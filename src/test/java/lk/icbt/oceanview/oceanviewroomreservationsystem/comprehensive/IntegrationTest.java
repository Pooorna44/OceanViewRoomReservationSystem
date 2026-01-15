package lk.icbt.oceanview.oceanviewroomreservationsystem.comprehensive;

import lk.icbt.oceanview.oceanviewroomreservationsystem.dao.ReservationDAO;
import lk.icbt.oceanview.oceanviewroomreservationsystem.dao.UserDAO;
import lk.icbt.oceanview.oceanviewroomreservationsystem.dao.impl.ReservationDAOImpl;
import lk.icbt.oceanview.oceanviewroomreservationsystem.dao.impl.UserDAOImpl;
import lk.icbt.oceanview.oceanviewroomreservationsystem.model.Reservation;
import lk.icbt.oceanview.oceanviewroomreservationsystem.model.User;
import lk.icbt.oceanview.oceanviewroomreservationsystem.service.AuthenticationService;
import lk.icbt.oceanview.oceanviewroomreservationsystem.service.ReservationService;
import lk.icbt.oceanview.oceanviewroomreservationsystem.service.impl.AuthenticationServiceImpl;
import lk.icbt.oceanview.oceanviewroomreservationsystem.service.impl.ReservationServiceImpl;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IntegrationTest {

    private UserDAO userDAO;
    private ReservationDAO reservationDAO;
    private AuthenticationService authService;
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        userDAO = new UserDAOImpl();
        reservationDAO = new ReservationDAOImpl();
        authService = new AuthenticationServiceImpl();
        reservationService = new ReservationServiceImpl();
    }


    @Test
    @Order(48)
    @DisplayName("Test 48: End-to-end login authentication flow")
    void testLoginAuthenticationFlow() {

        String username = "staff1";
        String plainPassword = "staff123";

        User authenticatedUser = authService.authenticate(username, plainPassword);

        assertNotNull(authenticatedUser, "Authentication should succeed");
        assertEquals(username, authenticatedUser.getUsername());
        assertEquals("STAFF", authenticatedUser.getRole());
    }

    @Test
    @Order(49)
    @DisplayName("Test 49: Failed login with wrong password")
    void testFailedLoginFlow() {
        String username = "staff1";
        String wrongPassword = "wrongpassword";

        User authenticatedUser = authService.authenticate(username, wrongPassword);

        assertNull(authenticatedUser, "Authentication should fail with wrong password");
    }


    @Test
    @Order(50)
    @DisplayName("Test 50: Complete reservation creation workflow")
    void testReservationCreationWorkflow() {
        try {

            User user = authService.authenticate("admin", "admin123");
            assertNotNull(user, "User must be authenticated");


            Reservation reservation = new Reservation();
            reservation.setGuestName("Integration Test Guest");
            reservation.setAddress("123 Test Street");
            reservation.setContactNumber("0771234567");
            reservation.setEmail("integration@test.com");
            reservation.setRoomTypeId(1);
            reservation.setCheckInDate(LocalDate.now().plusDays(10));
            reservation.setCheckOutDate(LocalDate.now().plusDays(13));
            reservation.setNumberOfGuests(2);
            reservation.setStatus("CONFIRMED");


            Reservation created = reservationService.createReservation(reservation, user.getUserId());


            assertNotNull(created, "Reservation should be created");
            assertNotNull(created.getReservationNumber(), "Reservation number should be generated");
            assertTrue(created.getReservationNumber().startsWith("RES-"));


            Reservation retrieved = reservationService.getReservationByNumber(created.getReservationNumber());
            assertNotNull(retrieved, "Should retrieve created reservation");
            assertEquals(created.getReservationNumber(), retrieved.getReservationNumber());

        } catch (Exception e) {
            fail("Integration test should not throw exception: " + e.getMessage());
        }
    }
}
