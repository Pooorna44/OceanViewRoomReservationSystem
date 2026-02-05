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
        System.out.println("\n=== Test T-048: End-to-End Login Authentication Flow ===");
        System.out.println("Input - Username: staff1");
        System.out.println("Input - Password: staff123");
        System.out.println("Expected Result: User(username='staff1', role='STAFF')");

        String username = "staff1";
        String plainPassword = "staff123";

        User authenticatedUser = authService.authenticate(username, plainPassword);

        String actualResult = (authenticatedUser != null ? "User(username='" + authenticatedUser.getUsername() + "', role='" + authenticatedUser.getRole() + "')" : "null");
        System.out.println("Actual Result:   " + actualResult);
        System.out.println("Match: " + (authenticatedUser != null && username.equals(authenticatedUser.getUsername()) && "STAFF".equals(authenticatedUser.getRole()) ? "YES ✓" : "NO ✗"));
        System.out.println("Status: " + (authenticatedUser != null ? "PASSED ✓" : "FAILED ✗"));

        assertNotNull(authenticatedUser, "Authentication should succeed");
        assertEquals(username, authenticatedUser.getUsername());
        assertEquals("STAFF", authenticatedUser.getRole());
    }

    @Test
    @Order(49)
    @DisplayName("Test 49: Failed login with wrong password")
    void testFailedLoginFlow() {
        System.out.println("\n=== Test T-049: Failed Login With Wrong Password ===");
        System.out.println("Input - Username: staff1");
        System.out.println("Input - Password: wrongpassword (INVALID)");
        System.out.println("Expected Result: Login denied - invalid credentials");

        String username = "staff1";
        String wrongPassword = "wrongpassword";

        User authenticatedUser = authService.authenticate(username, wrongPassword);

        System.out.println("Actual Result:   Login denied - invalid credentials");
        System.out.println("Match: " + (authenticatedUser == null ? "YES ✓" : "NO ✗"));
        System.out.println("Status: " + (authenticatedUser == null ? "PASSED ✓" : "FAILED ✗"));

        assertNull(authenticatedUser, "Authentication should fail with wrong password");
    }


    @Test
    @Order(50)
    @DisplayName("Test 50: Complete reservation creation workflow")
    void testReservationCreationWorkflow() {
        try {
            System.out.println("\n=== Test T-050: Complete Reservation Creation Workflow ===");
            System.out.println("Step 1: Authenticating user...");

            User user = authService.authenticate("admin", "admin123");
            assertNotNull(user, "User must be authenticated");
            System.out.println("  ✓ User authenticated: " + user.getUsername());

            System.out.println("\nStep 2: Creating reservation...");
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

            System.out.println("  Input - Guest: Integration Test Guest");
            System.out.println("  Input - Check-in: " + LocalDate.now().plusDays(10));
            System.out.println("  Input - Check-out: " + LocalDate.now().plusDays(13));

            Reservation created = reservationService.createReservation(reservation, user.getUserId());

            System.out.println("\nStep 3: Validating created reservation...");
            assertNotNull(created, "Reservation should be created");
            assertNotNull(created.getReservationNumber(), "Reservation number should be generated");
            assertTrue(created.getReservationNumber().startsWith("RES-"));
            System.out.println("  ✓ Reservation created: " + created.getReservationNumber());

            System.out.println("\nStep 4: Retrieving reservation...");
            Reservation retrieved = reservationService.getReservationByNumber(created.getReservationNumber());
            assertNotNull(retrieved, "Should retrieve created reservation");
            assertEquals(created.getReservationNumber(), retrieved.getReservationNumber());
            System.out.println("  ✓ Reservation retrieved successfully");
            System.out.println("\nStatus: ✓ PASSED - Complete workflow executed successfully");

        } catch (Exception e) {
            fail("Integration test should not throw exception: " + e.getMessage());
        }
    }
}
