package guiFirstAdmin;

import java.sql.SQLException;
import database.Database;
import entityClasses.User;
import javafx.stage.Stage;

/*******
 * <p> Title: ControllerFirstAdmin Class. </p>
 * 
 * <p> Description: ControllerFirstAdmin class provides the controller actions based on the user's
 *  use of the JavaFX GUI widgets defined by the View class. Updated with full input validation
 *  for username and password constraints.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * @version 1.01 2026-09 Updated with Input Validation
 */
public class ControllerFirstAdmin {

	private static String adminUsername = "";
	private static String adminPassword1 = "";
	private static String adminPassword2 = "";		
	protected static Database theDatabase = applicationMain.FoundationsMain.database;		

	public ControllerFirstAdmin() {
	}

	protected static void setAdminUsername() {
		adminUsername = ViewFirstAdmin.text_AdminUsername.getText();
	}
	
	protected static void setAdminPassword1() {
		adminPassword1 = ViewFirstAdmin.text_AdminPassword1.getText();
		ViewFirstAdmin.label_PasswordsDoNotMatch.setText("");
	}
	
	protected static void setAdminPassword2() {
		adminPassword2 = ViewFirstAdmin.text_AdminPassword2.getText();		
		ViewFirstAdmin.label_PasswordsDoNotMatch.setText("");
	}
	
	/**********
	 * <p> Method: doSetupAdmin() </p>
	 * 
	 * <p> Description: Validates username and passwords against project requirements 
	 * before registering the first Admin in the database.</p>
	 */
	protected static void doSetupAdmin(Stage ps, int r) {
		// Ensure current fields are captured
		setAdminUsername();
		setAdminPassword1();
		setAdminPassword2();

		// 1. Validate Username (4-32 chars, starts with a letter, alphanumeric only)
		String usernameError = validateUsername(adminUsername);
		if (!usernameError.isEmpty()) {
			ViewFirstAdmin.label_PasswordsDoNotMatch.setText(usernameError);
			return;
		}

		// 2. Validate Password Evaluation Criteria (8-32 chars, upper, lower, digit, special)
		String passwordError = validatePassword(adminPassword1);
		if (!passwordError.isEmpty()) {
			ViewFirstAdmin.label_PasswordsDoNotMatch.setText(passwordError);
			return;
		}

		// 3. Verify Passwords Match
		if (adminPassword1.compareTo(adminPassword2) != 0) {
			ViewFirstAdmin.text_AdminPassword1.setText("");
			ViewFirstAdmin.text_AdminPassword2.setText("");
			ViewFirstAdmin.label_PasswordsDoNotMatch.setText(
					"The two passwords must match. Please try again!");
			return;
		}

		// If all validations pass, create the Admin user and register
		User user = new User(adminUsername, adminPassword1, "", "", "", "", "", true, false, false);
		try {
			theDatabase.register(user);
		} catch (SQLException e) {
			System.err.println("*** ERROR *** Database error trying to register a user: " + e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
		
		// Navigate to the User Update Page
		guiUserUpdate.ViewUserUpdate.displayUserUpdate(ViewFirstAdmin.theStage, user);
	}
	
	/**********
	 * <p> Method: validateUsername() </p>
	 * 
	 * <p> Helper to validate username constraints:
	 * 4-32 characters, alphanumeric only, must start with a letter.</p>
	 */
	private static String validateUsername(String username) {
		if (username == null || username.length() < 4 || username.length() > 32) {
			return "Username must be within 4-32 characters and start with a letter.";
		}

		// Must start with an alphabetic letter
		char firstChar = username.charAt(0);
		if (!Character.isLetter(firstChar)) {
			return "Username must be within 4-32 characters and start with a letter.";
		}

		// Must be strictly alphanumeric (no spaces, no symbols)
		for (int i = 0; i < username.length(); i++) {
			char c = username.charAt(i);
			if (!Character.isLetterOrDigit(c)) {
				return "Username must be within 4-32 characters and start with a letter.";
			}
		}

		return ""; // Empty string indicates valid
	}

	/**********
	 * <p> Method: validatePassword() </p>
	 * 
	 * <p> Helper to validate password constraints:
	 * 8-32 characters, uppercase, lowercase, numeric digit, and special character.</p>
	 */
	private static String validatePassword(String password) {
		if (password == null || password.length() < 8 || password.length() > 32) {
			return "Passwords must be within 8-32 characters, and include uppercase, lowercase, digit, and a special character.";
		}

		boolean hasUpper = false;
		boolean hasLower = false;
		boolean hasDigit = false;
		boolean hasSpecial = false;
		String specialChars = "~`!@#$%^&*()_-+{}[]|:,.?/";

		for (int i = 0; i < password.length(); i++) {
			char c = password.charAt(i);
			if (Character.isUpperCase(c)) {
				hasUpper = true;
			} else if (Character.isLowerCase(c)) {
				hasLower = true;
			} else if (Character.isDigit(c)) {
				hasDigit = true;
			} else if (specialChars.indexOf(c) != -1) {
				hasSpecial = true;
			}
		}

		if (!hasUpper || !hasLower || !hasDigit || !hasSpecial) {
			return "Passwords must be within 8-32 characters, and include uppercase, lowercase, digit, and a special character.";
		}

		return ""; // Empty string indicates valid
	}
	
	protected static void performQuit() {
		System.out.println("Perform Quit");
		System.exit(0);
	}	
}