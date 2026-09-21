package guiAdminHome;

import java.util.Optional;
import java.util.UUID;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import database.Database;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import entityClasses.User;
import java.util.List;

/*******
 * <p> Title: GUIAdminHomePage Class. </p>
 * 
 * <p> Description: The Java/FX-based Admin Home Page.  This class provides the controller actions
 * basic on the user's use of the JavaFX GUI widgets defined by the View class.
 * 
 * This page contains a number of buttons that have not yet been implemented.  WHen those buttons
 * are pressed, an alert pops up to tell the user that the function associated with the button has
 * not been implemented. Also, be aware that What has been implemented may not work the way the
 * final product requires and there maybe defects in this code.
 * 
 * The class has been written assuming that the View or the Model are the only class methods that
 * can invoke these methods.  This is why each has been declared at "protected".  Do not change any
 * of these methods to public.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00		2025-08-17 Initial version
 * @version 1.01		2025-09-16 Update Javadoc documentation *  
 */


public class ControllerAdminHome {

	public ControllerAdminHome() {
	}

	private static Database theDatabase = applicationMain.FoundationsMain.database;

	protected static void deleteUser() {
	    String targetUser = "";
	    if (ViewAdminHome.combobox_SelectUser.getValue() != null) {
	        targetUser = ViewAdminHome.combobox_SelectUser.getValue();
	    }

	    if (targetUser.isEmpty() || targetUser.equals("<Select a User>")) {
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.setTitle("Selection Error");
	        alert.setHeaderText(null);
	        alert.setContentText("Please select a valid user to delete.");
	        alert.showAndWait();
	        return;
	    }

	    if (targetUser.equalsIgnoreCase(ViewAdminHome.theUser.getUserName())) {
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Action Denied");
	        alert.setHeaderText(null);
	        alert.setContentText("An admin is not allowed to remove that admin's access.");
	        alert.showAndWait();
	        return;
	    }

	    Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
	    confirmAlert.setTitle("Confirm Deletion");
	    confirmAlert.setHeaderText(null);
	    confirmAlert.setContentText("Are you sure?");
	    ButtonType buttonYes = new ButtonType("Yes");
	    ButtonType buttonNo = new ButtonType("No");
	    confirmAlert.getButtonTypes().setAll(buttonYes, buttonNo);

	    Optional<ButtonType> result = confirmAlert.showAndWait();
	    if (result.isPresent() && result.get() == buttonYes) {
	        if (theDatabase.deleteUser(targetUser)) {
	            Alert infoAlert = new Alert(AlertType.INFORMATION);
	            infoAlert.setTitle("Success");
	            infoAlert.setHeaderText(null);
	            infoAlert.setContentText("User account '" + targetUser + "' has been successfully deleted.");
	            infoAlert.showAndWait();

	            ViewAdminHome.refreshUserList();
	            ViewAdminHome.label_NumberOfUsers.setText("Number of users: " + theDatabase.getNumberOfUsers());
	        } else {
	            Alert errorAlert = new Alert(AlertType.ERROR);
	            errorAlert.setTitle("Database Error");
	            errorAlert.setHeaderText(null);
	            errorAlert.setContentText("Failed to delete user account from the database.");
	            errorAlert.showAndWait();
	        }
	    }
	}

	protected static void performInvitation() {
	    String emailAddress = ViewAdminHome.text_InvitationEmailAddress.getText();
	    if (invalidEmailAddress(emailAddress)) return;

	    if (theDatabase.emailaddressHasBeenUsed(emailAddress)) {
	        ViewAdminHome.alertEmailError.setContentText(
	            "An invitation has already been issued to that email address.");
	        ViewAdminHome.alertEmailError.showAndWait();
	        return;
	    }

	    String theSelectedRole = (String) ViewAdminHome.combobox_SelectRole.getValue();
	    String dbRoleKey = theSelectedRole;
	    if ("Contributor".equals(theSelectedRole)) dbRoleKey = "Role1";
	    else if ("Viewer".equals(theSelectedRole)) dbRoleKey = "Role2";

	    String invitationCode = theDatabase.generateInvitationCode(emailAddress, dbRoleKey);
	    String msg = "Code: " + invitationCode + " for role " + theSelectedRole +
	            " was sent to: " + emailAddress;
	    System.out.println(msg);
	    ViewAdminHome.alertEmailSent.setContentText(msg);
	    ViewAdminHome.alertEmailSent.showAndWait();

	    ViewAdminHome.text_InvitationEmailAddress.setText("");
	    ViewAdminHome.label_NumberOfInvitations.setText("Number of outstanding invitations: " +
	            theDatabase.getNumberOfInvitations());
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: manageInvitations () Method. </p>
	 * 
	 * <p> Description: Protected method that is currently a stub informing the user that
	 * this function has not yet been implemented. </p>
	 */
	protected static void manageInvitations () {
		System.out.println("\n*** WARNING ***: Manage Invitations Not Yet Implemented");
		ViewAdminHome.alertNotImplemented.setTitle("*** WARNING ***");
		ViewAdminHome.alertNotImplemented.setHeaderText("Manage Invitations Issue");
		ViewAdminHome.alertNotImplemented.setContentText("Manage Invitations Not Yet Implemented");
		ViewAdminHome.alertNotImplemented.showAndWait();
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: setOnetimePassword () Method. </p>
	 * 
	 * <p> Description: Generates a temporary One-Time Password for a selected target username. </p>
	 */
	protected static void setOnetimePassword () {
		// Prompt the admin to enter the target username
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Set One-Time Password");
		dialog.setHeaderText("Generate an OTP for a user account");
		dialog.setContentText("Enter Target Username:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent() && !result.get().trim().isEmpty()) {
			String targetUsername = result.get().trim();

			// Check if target user exists in database
			if (!theDatabase.doesUserExist(targetUsername)) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("User Not Found");
				alert.setContentText("The username '" + targetUsername + "' does not exist.");
				alert.showAndWait();
				return;
			}

			// Generate a random 8-character OTP
			String generatedOTP = UUID.randomUUID().toString().substring(0, 8);

			// Store OTP and set active status in H2 DB
			if (theDatabase.setOneTimePassword(targetUsername, generatedOTP)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("OTP Generated");
				alert.setHeaderText("One-Time Password Successfully Set");
				alert.setContentText("One-Time Password for " + targetUsername + ": " + generatedOTP);
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Database Error");
				alert.setContentText("Failed to set One-Time Password.");
				alert.showAndWait();
			}
		}
	}
	
	// Helper class for TableView binding
	public static class UserTableEntry {
		private final String username;
		private final String fullName;
		private final String email;
		private final String roles;
		
		public UserTableEntry(String username, String fullName, String email, String roles) {
			this.username = username;
			this.fullName = fullName;
			this.email= email;
			this.roles = roles;
		}
		
		public String getUsername() {return username;}
		public String getFullName() {return fullName;}
		public String getEmail() {return email;}
		public String getRoles() {return roles;}
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: listUsers () Method. </p>
	 * 
	 * <p> Description: list user function 9-19-2026. </p>
	 */
	protected static void listUsers() {
		// 1. Fetch user accounts from database
	    List<User> userList = theDatabase.getAllUsers();
	    ObservableList<User> data = FXCollections.observableArrayList(userList);

	    // 2. Build JavaFX TableView
	    TableView<UserTableEntry> table = new TableView<>();

	    TableColumn<UserTableEntry, String> colUser = new TableColumn<>("Username");
	    colUser.setCellValueFactory(new PropertyValueFactory<>("username"));

	    TableColumn<UserTableEntry, String> colName = new TableColumn<>("Name");
	    colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));

	    TableColumn<UserTableEntry, String> colEmail = new TableColumn<>("Email");
	    colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

	    TableColumn<UserTableEntry, String> colRoles = new TableColumn<>("Assigned Roles");
	    colRoles.setCellValueFactory(new PropertyValueFactory<>("roles"));

	    table.getColumns().addAll(colUser, colName, colEmail, colRoles);

	    // Populate rows
	    ObservableList<UserTableEntry> tableEntries = FXCollections.observableArrayList();
	    for (User u : userList) {
	        String fullName = u.getFirstName() + " " + u.getLastName();
	        
	        // Build roles string
	        StringBuilder roles = new StringBuilder();
	        if (u.getAdminRole()) roles.append("Admin ");
	        if (u.getNewRole1()) roles.append("Role1 ");
	        if (u.getNewRole2()) roles.append("Role2 ");

	        tableEntries.add(new UserTableEntry(u.getUserName(), fullName.trim(), u.getEmailAddress(), roles.toString().trim()));
	    }
	    table.setItems(tableEntries);

	    // 3. Display in a Modal Window
	    Stage dialog = new Stage();
	    dialog.initModality(Modality.APPLICATION_MODAL);
	    dialog.setTitle("All System User Accounts");

	    VBox layout = new VBox(10);
	    layout.getChildren().add(table);

	    Scene scene = new Scene(layout, 600, 400);
	    dialog.setScene(scene);
	    dialog.showAndWait();
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: addRemoveRoles () Method. </p>
	 * 
	 * <p> Description: Protected method that allows an admin to add and remove roles for any of
	 * the users currently in the system.  This is done by invoking the AddRemoveRoles Page. There
	 * is no need to specify the home page for the return as this can only be initiated by and
	 * Admin.</p>
	 */
	protected static void addRemoveRoles() {
		guiAddRemoveRoles.ViewAddRemoveRoles.displayAddRemoveRoles(ViewAdminHome.theStage, 
				ViewAdminHome.theUser);
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: invalidEmailAddress () Method. </p>
	 * 
	 * <p> Description: Protected method that is intended to check an email address before it is
	 * used to reduce errors.  The code currently only checks to see that the email address is not
	 * empty.  In the future, a syntactic check must be performed and maybe there is a way to check
	 * if a properly email address is active.</p>
	 * 
	 * @param emailAddress	This String holds what is expected to be an email address
	 */
	protected static boolean invalidEmailAddress(String emailAddress) {
		// Verify email is non-empty and enforces the universal 320-character maximum ceiling
		if (emailAddress == null || emailAddress.length() == 0 || emailAddress.length() > 320) {
			ViewAdminHome.alertEmailError.setContentText(
					"Correct the email address (1-320 characters) and try again.");
			ViewAdminHome.alertEmailError.showAndWait();
			return true;

		}
		return false;
	}

	protected static void performLogout() {
		guiUserLogin.ViewUserLogin.displayUserLogin(ViewAdminHome.theStage);
	}

	protected static void performQuit() {
		System.exit(0);
	}
}