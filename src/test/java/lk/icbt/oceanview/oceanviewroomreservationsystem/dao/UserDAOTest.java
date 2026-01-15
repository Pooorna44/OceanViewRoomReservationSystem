package lk.icbt.oceanview.oceanviewroomreservationsystem.dao;

import lk.icbt.oceanview.oceanviewroomreservationsystem.dao.impl.UserDAOImpl;
import lk.icbt.oceanview.oceanviewroomreservationsystem.model.User;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDAOTest {

    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        userDAO = new UserDAOImpl();
    }


    @Test
    @Order(1)
    @DisplayName("Test 1: Valid admin authentication")
    void testValidAdminAuthentication() {
        try {
            User user = userDAO.authenticate("admin", "240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9");
            assertNotNull(user, "User should not be null for valid credentials");
            assertEquals("admin", user.getUsername());
            assertEquals("ADMIN", user.getRole());
        } catch (SQLException e) {
            fail("SQLException should not occur: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    @DisplayName("Test 2: Valid staff authentication")
    void testValidStaffAuthentication() {
        try {
            User user = userDAO.authenticate("staff1", "10176e7b7b24d317acfcf8d2064cfd2f24e154f7b5a96603077d5ef813d6a6b6");
            assertNotNull(user, "User should not be null for valid credentials");
            assertEquals("staff1", user.getUsername());
            assertEquals("STAFF", user.getRole());
        } catch (SQLException e) {
            fail("SQLException should not occur: " + e.getMessage());
        }
    }

    @Test
    @Order(3)
    @DisplayName("Test 3: Invalid password authentication")
    void testInvalidPasswordAuthentication() {
        try {
            User user = userDAO.authenticate("admin", "wronghash123");
            assertNull(user, "User should be null for invalid password");
        } catch (SQLException e) {
            fail("SQLException should not occur: " + e.getMessage());
        }
    }

    @Test
    @Order(4)
    @DisplayName("Test 4: Non-existent user authentication")
    void testNonExistentUserAuthentication() {
        try {
            User user = userDAO.authenticate("nonexistent", "anyhash");
            assertNull(user, "User should be null for non-existent username");
        } catch (SQLException e) {
            fail("SQLException should not occur: " + e.getMessage());
        }
    }

    @Test
    @Order(5)
    @DisplayName("Test 5: Empty username authentication")
    void testEmptyUsernameAuthentication() {
        try {
            User user = userDAO.authenticate("", "anyhash");
            assertNull(user, "User should be null for empty username");
        } catch (SQLException e) {
            fail("SQLException should not occur: " + e.getMessage());
        }
    }

    @Test
    @Order(6)
    @DisplayName("Test 6: Null username authentication")
    void testNullUsernameAuthentication() {
        try {
            User user = userDAO.authenticate(null, "anyhash");
            assertNull(user, "User should be null for null username");
        } catch (SQLException e) {
            fail("SQLException should not occur: " + e.getMessage());
        }
    }


    @Test
    @Order(7)
    @DisplayName("Test 7: Find user by valid ID")
    void testFindUserByValidId() {
        try {
            User user = userDAO.findById(1);
            assertNotNull(user, "User should not be null for valid ID");
            assertEquals(1, user.getUserId());
        } catch (SQLException e) {
            fail("SQLException should not occur: " + e.getMessage());
        }
    }

    @Test
    @Order(8)
    @DisplayName("Test 8: Find user by invalid ID")
    void testFindUserByInvalidId() {
        try {
            User user = userDAO.findById(99999);
            assertNull(user, "User should be null for invalid ID");
        } catch (SQLException e) {
            fail("SQLException should not occur: " + e.getMessage());
        }
    }

    @Test
    @Order(9)
    @DisplayName("Test 9: Find user by username")
    void testFindUserByUsername() {
        try {
            User user = userDAO.findByUsername("admin");
            assertNotNull(user, "User should not be null for existing username");
            assertEquals("admin", user.getUsername());
        } catch (SQLException e) {
            fail("SQLException should not occur: " + e.getMessage());
        }
    }

    @Test
    @Order(10)
    @DisplayName("Test 10: Find all users")
    void testFindAllUsers() {
        try {
            var users = userDAO.findAll();
            assertNotNull(users, "User list should not be null");
            assertTrue(users.size() >= 4, "Should have at least 4 users (admin, staff1, staff2, manager)");
        } catch (SQLException e) {
            fail("SQLException should not occur: " + e.getMessage());
        }
    }
}
