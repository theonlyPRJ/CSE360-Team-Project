package guiContributor;

import entityClasses.LessonLearned;
import entityClasses.User;

/*******
 * <p> Title: ContributorValidator Class. </p>
 * 
 * <p> Description: Provides static input validation and role ownership verification methods
 * for Contributor Lesson Learned operations in Phase Two (TP2). Evaluates syntactic boundaries
 * for title, description, category, author, and search query inputs, and enforces ownership
 * authorization before update or delete operations are executed. </p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2026 </p>
 * 
 * @author Sanidhya Tiwari
 * @version 2.00 2026-10-23 TP2 Contributor input validation and authorization utility.
 */
public class ContributorValidator {

    /** Approved course categories for classifying lessons learned. */
    public static final String[] APPROVED_CATEGORIES = {
        "GUI",
        "Architecture",
        "Database",
        "Testing",
        "Version Control",
        "General"
    };

    /**
     * Default constructor.
     */
    public ContributorValidator() {
    }

    /*****
     * <p> Method: validateTitle(String title) </p>
     * 
     * <p> Description: Validates that a lesson title is non-null, not blank, and between
     * 4 and 64 characters in length after trimming whitespace (TP2-REQ-02, US-HW2-01). </p>
     * 
     * @param title candidate title string provided by the contributor
     * @return empty string if valid, or a descriptive error message if invalid
     */
    public static String validateTitle(String title) {
        // Check for null or whitespace-only input first so the user gets specific blank-field guidance
        if (title == null || title.trim().isEmpty()) {
            return "Error: Title cannot be blank. Please provide a concise summary title (4 to 64 characters).";
        }
        int length = title.trim().length();
        // Enforce the lower boundary of 4 characters to prevent ambiguous titles
        if (length < 4) {
            return "Error: Title is too short (" + length + " chars). Title must be between 4 and 64 characters.";
        }
        // Enforce the upper boundary of 64 characters to fit UI table columns and database limits
        if (length > 64) {
            return "Error: Title is too long (" + length + " chars). Title must be between 4 and 64 characters.";
        }
        return "";
    }

    /*****
     * <p> Method: validateDescription(String description) </p>
     * 
     * <p> Description: Validates that a lesson description body is non-null, not blank, and between
     * 10 and 1000 characters in length after trimming whitespace (TP2-REQ-03, US-HW2-01). </p>
     * 
     * @param description candidate description body text
     * @return empty string if valid, or a descriptive error message if invalid
     */
    public static String validateDescription(String description) {
        // Reject null or empty descriptions before attempting collection or database persistence
        if (description == null || description.trim().isEmpty()) {
            return "Error: Description cannot be empty. Please explain the technical issue and resolution (10 to 1000 characters).";
        }
        int length = description.trim().length();
        // Require at least 10 characters so entries contain meaningful troubleshooting context
        if (length < 10) {
            return "Error: Description is too short (" + length + " chars). Description must be between 10 and 1000 characters.";
        }
        // Cap description length at 1000 characters to prevent buffer overflow and UI layout distortion
        if (length > 1000) {
            return "Error: Description is too long (" + length + " chars). Description must be between 10 and 1000 characters.";
        }
        return "";
    }

    /*****
     * <p> Method: validateCategory(String category) </p>
     * 
     * <p> Description: Validates that the selected category is non-null, non-blank, not left on
     * the default placeholder, and matches one of the approved technical categories (TP2-REQ-04). </p>
     * 
     * @param category candidate category string
     * @return empty string if valid, or a descriptive error message if invalid
     */
    public static String validateCategory(String category) {
        // Reject null, empty, or unselected dropdown placeholder values
        if (category == null || category.trim().isEmpty() || "Select Category".equalsIgnoreCase(category.trim())) {
            return "Error: Category is required. Please select an approved technical category.";
        }
        String trimmed = category.trim();
        for (String approved : APPROVED_CATEGORIES) {
            if (approved.equalsIgnoreCase(trimmed)) {
                return "";
            }
        }
        return "Error: Invalid category '" + trimmed + "'. Allowed categories: GUI, Architecture, Database, Testing, Version Control, General.";
    }

    /*****
     * <p> Method: validateAuthor(String author) </p>
     * 
     * <p> Description: Validates that the author username is non-null, non-blank, starts with
     * a letter, and contains between 4 and 32 alphanumeric characters (TP2-REQ-04). </p>
     * 
     * @param author candidate contributor username
     * @return empty string if valid, or a descriptive error message if invalid
     */
    public static String validateAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            return "Error: Author username is required and cannot be blank.";
        }
        String trimmed = author.trim();
        if (trimmed.length() < 4 || trimmed.length() > 32) {
            return "Error: Author username must be between 4 and 32 characters.";
        }
        if (!Character.isLetter(trimmed.charAt(0))) {
            return "Error: Author username must start with an alphabetic character.";
        }
        for (int i = 0; i < trimmed.length(); i++) {
            char c = trimmed.charAt(i);
            if (!Character.isLetterOrDigit(c)) {
                return "Error: Author username must contain only alphanumeric characters.";
            }
        }
        return "";
    }

    /*****
     * <p> Method: validateSearchKeyword(String keyword) </p>
     * 
     * <p> Description: Validates search query strings to ensure they do not exceed the maximum
     * allowed length of 64 characters (TP2-REQ-06, US-HW2-02). Blank keywords are permitted and
     * return the full collection. </p>
     * 
     * @param keyword candidate search query string
     * @return empty string if valid, or a descriptive error message if invalid
     */
    public static String validateSearchKeyword(String keyword) {
        if (keyword != null && keyword.trim().length() > 64) {
            return "Error: Search keyword exceeds the maximum length of 64 characters.";
        }
        return "";
    }

    /*****
     * <p> Method: validateLessonFields(String title, String description, String author, String category) </p>
     * 
     * <p> Description: Evaluates all fields required to create or update a LessonLearned entity
     * in sequential priority order (TP2-REQ-01 through TP2-REQ-04). </p>
     * 
     * @param title candidate title
     * @param description candidate description
     * @param author candidate author username
     * @param category candidate category
     * @return empty string if all fields are valid, or the first encountered error message
     */
    public static String validateLessonFields(String title, String description, String author, String category) {
        String err = validateTitle(title);
        if (!err.isEmpty()) return err;

        err = validateDescription(description);
        if (!err.isEmpty()) return err;

        err = validateAuthor(author);
        if (!err.isEmpty()) return err;

        err = validateCategory(category);
        if (!err.isEmpty()) return err;

        return "";
    }

    /*****
     * <p> Method: verifyModifyPermission(LessonLearned lesson, User actingUser) </p>
     * 
     * <p> Description: Verifies whether the active logged-in User has permission to update or
     * delete the specified LessonLearned record (TP2-REQ-09, TP2-REQ-11, US-HW2-03, US-HW2-04).
     * Modification is permitted only if the acting user is the original author or holds the
     * Administrator role. </p>
     * 
     * @param lesson target LessonLearned record to modify or delete
     * @param actingUser currently authenticated User attempting the operation
     * @return empty string if authorized, or a descriptive authorization error message
     */
    public static String verifyModifyPermission(LessonLearned lesson, User actingUser) {
        if (lesson == null) {
            return "Error: Target lesson record does not exist.";
        }
        if (actingUser == null || actingUser.getUserName() == null || actingUser.getUserName().trim().isEmpty()) {
            return "Error: No authenticated user session found.";
        }
        // Administrators have global governance privileges to edit or remove any lesson
        if (actingUser.getAdminRole()) {
            return "";
        }
        // Contributors may only update or delete lessons whose author matches their username
        if (lesson.getAuthor() != null && lesson.getAuthor().equalsIgnoreCase(actingUser.getUserName().trim())) {
            return "";
        }
        return "Unauthorized: User '" + actingUser.getUserName()
                + "' cannot modify or delete a lesson authored by '" + lesson.getAuthor() + "'.";
    }
}
