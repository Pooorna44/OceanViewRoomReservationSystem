package lk.icbt.oceanview.oceanviewroomreservationsystem.model;

import java.time.LocalDateTime;

public class User extends BaseEntity {

    private int userId;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String role;
    private boolean isActive;
    private LocalDateTime lastLogin;

    public User() {
        super();
        this.isActive = true;
        this.role = "STAFF";
    }


    public User(int userId, String username, String fullName) {
        super(userId);
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.isActive = true;
        this.role = "STAFF";
    }


    public User(String username, String password, String fullName, String email, String role) {
        super();
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.isActive = true;
    }


    @Override
    public boolean validate() {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        if (username.length() < 3 || username.length() > 50) {
            return false;
        }

        if (isNew() && (password == null || password.length() < 6)) {
            return false;
        }

        if (fullName == null || fullName.trim().isEmpty()) {
            return false;
        }

        if (role == null || (!role.equals("ADMIN") && !role.equals("STAFF"))) {
            return false;
        }

        if (email != null && !email.trim().isEmpty()) {
            if (!email.contains("@") || !email.contains(".")) {
                return false;
            }
        }

        return true;
    }


    @Override
    public String getDisplayName() {
        return fullName + " (" + username + ")";
    }


    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(role);
    }

    public void updateLastLogin() {
        this.lastLogin = LocalDateTime.now();
        this.updateTimestamp();
    }

    public void activate() {
        this.isActive = true;
        this.updateTimestamp();
    }

    public void deactivate() {
        this.isActive = false;
        this.updateTimestamp();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
        this.setId(userId);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", isActive=" + isActive +
                ", lastLogin=" + lastLogin +
                "} " + super.toString();
    }
}
