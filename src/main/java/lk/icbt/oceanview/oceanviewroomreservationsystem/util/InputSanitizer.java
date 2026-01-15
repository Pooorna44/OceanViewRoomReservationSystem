package lk.icbt.oceanview.oceanviewroomreservationsystem.util;


public class InputSanitizer {

    private static final int MAX_INPUT_LENGTH = 1000;


    private InputSanitizer() {
        throw new IllegalStateException("Utility class - cannot be instantiated");
    }

    public static String sanitizeForHTML(String input) {
        if (input == null) {
            return "";
        }

        return input.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#x27;").replace("/", "&#x2F;");
    }

    public static String stripHTMLTags(String input) {
        if (input == null) {
            return "";
        }

        return input.replaceAll("<[^>]*>", "");
    }


    public static String cleanInput(String input) {
        if (input == null) {
            return "";
        }

        String cleaned = stripHTMLTags(input);
        return sanitizeForHTML(cleaned).trim();
    }


    public static boolean containsSQLInjectionPattern(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        String lowerInput = input.toLowerCase();

        String[] sqlKeywords = {"select ", "insert ", "update ", "delete ", "drop ", "union ", "exec ", "execute ", "script ", "javascript:", "onerror", "onload", "<script", "--", "/*", "*/", "xp_", "sp_", "';", "\";", "or 1=1", "or '1'='1"};

        for (String keyword : sqlKeywords) {
            if (lowerInput.contains(keyword)) {
                return true;
            }
        }

        return false;
    }


    public static boolean isSafeInput(String input) {
        return !containsSQLInjectionPattern(input);
    }


    public static boolean isNullOrEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }


    public static boolean isValidLength(String input, int maxLength) {
        if (isNullOrEmpty(input)) {
            return false;
        }
        return input.length() <= maxLength;
    }

    public static boolean isValidLength(String input) {
        return isValidLength(input, MAX_INPUT_LENGTH);
    }


    public static String truncate(String input, int maxLength) {
        if (input == null) {
            return "";
        }

        if (input.length() <= maxLength) {
            return input;
        }

        return input.substring(0, maxLength);
    }


    public static boolean isValidEmail(String email) {
        if (isNullOrEmpty(email)) {
            return false;
        }


        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }


    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (isNullOrEmpty(phoneNumber)) {
            return false;
        }


        String cleaned = phoneNumber.replaceAll("[\\s-]", "");


        return cleaned.matches("^0\\d{9}$") || cleaned.matches("^\\d{10}$");
    }

    public static String formatPhoneNumber(String phoneNumber) {
        if (isNullOrEmpty(phoneNumber)) {
            return "";
        }

        String cleaned = phoneNumber.replaceAll("\\D", "");

        if (cleaned.length() == 10) {

            return cleaned.substring(0, 3) + "-" + cleaned.substring(3, 6) + "-" + cleaned.substring(6);
        }

        return phoneNumber;
    }

    public static boolean isAlphanumeric(String input) {
        if (isNullOrEmpty(input)) {
            return false;
        }
        return input.matches("^[a-zA-Z0-9]+$");
    }

    public static boolean isAlphabetic(String input) {
        if (isNullOrEmpty(input)) {
            return false;
        }
        return input.matches("^[a-zA-Z]+$");
    }

    public static boolean isNumeric(String input) {
        if (isNullOrEmpty(input)) {
            return false;
        }
        return input.matches("^\\d+$");
    }
}
