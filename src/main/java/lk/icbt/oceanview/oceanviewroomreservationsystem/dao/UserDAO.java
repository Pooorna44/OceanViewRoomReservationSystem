package lk.icbt.oceanview.oceanviewroomreservationsystem.dao;

import lk.icbt.oceanview.oceanviewroomreservationsystem.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {

    User findById(int userId) throws SQLException;

    User findByUsername(String username) throws SQLException;

    List<User> findAll() throws SQLException;

    List<User> findAllActive() throws SQLException;

    List<User> findByRole(String role) throws SQLException;

    int save(User user) throws SQLException;

    boolean update(User user) throws SQLException;

    boolean delete(int userId) throws SQLException;

    User authenticate(String username, String hashedPassword) throws SQLException;

    boolean updateLastLogin(int userId) throws SQLException;

    boolean usernameExists(String username) throws SQLException;
}
