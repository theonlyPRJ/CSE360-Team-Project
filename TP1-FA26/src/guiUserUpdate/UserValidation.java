package guiUserUpdate;

// This is a helper class to validate first/last name, and email address.
public class UserValidation {

	/**
     * Validates First / Last Name:
     * - 1–32 characters
     * - Alphabetic characters, spaces, and hyphens only
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        String nameRegex = "^[a-zA-Z -]{1,32}$";
        return name.trim().matches(nameRegex);
    }

    /**
     * Validates Email:
     * - 6–320 characters
     * - Valid RFC 5322 standard structure (username@domain.extension)
     */
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        int length = email.trim().length();
        if (length < 6 || length > 320) {
            return false;
        }
        
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.trim().matches(emailRegex);
    }
}