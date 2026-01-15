package lk.icbt.oceanview.oceanviewroomreservationsystem.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class PasswordUtil {

    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_PASSWORD_LENGTH = 50;


    private PasswordUtil() {
        throw new IllegalStateException("Utility class - cannot be instantiated");
    }


    public static String hashPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        try {

            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);


            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));


            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }

        String hashedInput = hashPassword(plainPassword);
        return hashedInput.equals(hashedPassword);
    }


    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }


        if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            return false;
        }


        if (password.trim().length() != password.length()) {
            return false;
        }

        return true;
    }

    public static boolean isStrongPassword(String password) {
        if (!isValidPassword(password)) {
            return false;
        }

        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpperCase = true;
            if (Character.isLowerCase(c)) hasLowerCase = true;
            if (Character.isDigit(c)) hasDigit = true;
        }


        return password.length() >= 8 && hasUpperCase && hasLowerCase && hasDigit;
    }

    public static String getPasswordValidationError(String password) {
        if (password == null || password.isEmpty()) {
            return "Password is required";
        }

        if (password.length() < MIN_PASSWORD_LENGTH) {
            return "Password must be at least " + MIN_PASSWORD_LENGTH + " characters";
        }

        if (password.length() > MAX_PASSWORD_LENGTH) {
            return "Password must not exceed " + MAX_PASSWORD_LENGTH + " characters";
        }

        if (password.trim().length() != password.length()) {
            return "Password cannot start or end with whitespace";
        }

        return "";
    }

    public static String generateSecurePassword(int length) {
        if (length < 8) {
            length = 8;
        }

        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "!@#$%^&*";
        String allChars = upperCase + lowerCase + digits + special;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        password.append(upperCase.charAt(random.nextInt(upperCase.length())));
        password.append(lowerCase.charAt(random.nextInt(lowerCase.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));

        for (int i = 3; i < length; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        char[] passwordArray = password.toString().toCharArray();
        for (int i = passwordArray.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[j];
            passwordArray[j] = temp;
        }

        return new String(passwordArray);
    }

    public static String generateTemporaryPassword() {
        return generateSecurePassword(8);
    }
}
