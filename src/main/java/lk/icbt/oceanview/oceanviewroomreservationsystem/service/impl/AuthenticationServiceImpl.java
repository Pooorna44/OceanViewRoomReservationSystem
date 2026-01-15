package lk.icbt.oceanview.oceanviewroomreservationsystem.service.impl;

import lk.icbt.oceanview.oceanviewroomreservationsystem.dao.UserDAO;
import lk.icbt.oceanview.oceanviewroomreservationsystem.factory.DAOFactory;
import lk.icbt.oceanview.oceanviewroomreservationsystem.model.User;
import lk.icbt.oceanview.oceanviewroomreservationsystem.service.AuthenticationService;
import lk.icbt.oceanview.oceanviewroomreservationsystem.util.PasswordUtil;

import java.sql.SQLException;

public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDAO userDAO;

    public AuthenticationServiceImpl() {
        this.userDAO = DAOFactory.getUserDAO();
    }

    @Override
    public User authenticate(String username, String plainPassword) {
        try {

            if (username == null || username.trim().isEmpty() ||
                    plainPassword == null || plainPassword.trim().isEmpty()) {
                return null;
            }


            String hashedPassword = PasswordUtil.hashPassword(plainPassword);


            User user = userDAO.authenticate(username, hashedPassword);

            if (user != null) {

                userDAO.updateLastLogin(user.getUserId());
            }

            return user;

        } catch (SQLException e) {
            System.err.println("[AuthService] Authentication error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean isValidCredentials(String username, String plainPassword) {
        return authenticate(username, plainPassword) != null;
    }

    @Override
    public boolean isAdmin(User user) {
        return user != null && "ADMIN".equalsIgnoreCase(user.getRole());
    }

    @Override
    public boolean isValidSession(User user) {
        return user != null && user.isActive();
    }

    @Override
    public boolean updateLastLogin(int userId) {
        try {
            return userDAO.updateLastLogin(userId);
        } catch (SQLException e) {
            System.err.println("[AuthService] Update last login error: " + e.getMessage());
            return false;
        }
    }
}
