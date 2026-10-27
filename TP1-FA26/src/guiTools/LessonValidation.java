package guiTools;

public class LessonValidation {

    public static String validate(String title, String description, String author, String category) {
        if (title == null || title.trim().length() < 4 || title.trim().length() > 64) {
            return "Validation Error: Title must be between 4 and 64 characters.";
        }
        if (description == null || description.trim().length() < 10 || description.trim().length() > 1000) {
            return "Validation Error: Description must be between 10 and 1000 characters.";
        }
        if (author == null || author.trim().isEmpty()) {
            return "Validation Error: Author cannot be empty.";
        }
        if (category == null || category.trim().isEmpty()) {
            return "Validation Error: Category cannot be empty.";
        }
        return null; // Valid
    }
}