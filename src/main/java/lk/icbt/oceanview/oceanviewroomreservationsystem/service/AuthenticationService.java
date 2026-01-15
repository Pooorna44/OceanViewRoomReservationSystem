package lk.icbt.oceanview.oceanviewroomreservationsystem.service;

import lk.icbt.oceanview.oceanviewroomreservationsystem.model.User;


public interface AuthenticationService {

    User authenticate(String username, String plainPassword);

    boolean isValidCredentials(String username, String plainPassword);

    boolean isAdmin(User user);

    boolean isValidSession(User user);

    boolean updateLastLogin(int userId);
}
