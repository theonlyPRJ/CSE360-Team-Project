package guiAdminHome;

import database.Database;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class ControllerAdminHome {

	public ControllerAdminHome() {
	}

	private static Database theDatabase = applicationMain.FoundationsMain.database;

	protected static void deleteUser() {
		String targetUser = "";
		if (ViewAdminHome.combobox_SelectUser != null && ViewAdminHome.combobox_SelectUser.getValue() != null) {
			targetUser = (String) ViewAdminHome.combobox_SelectUser.getValue();
		}

		if (targetUser.isEmpty() || targetUser.equals("<Select a User>")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Selection Error");
			alert.setHeaderText(null);
			alert.setContentText("Please select a valid user to delete.");
			alert.showAndWait();
			return;
		}

		// Admin cannot delete their own account
		if (targetUser.equalsIgnoreCase(ViewAdminHome.theUser.getUserName())) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Action Denied");
			alert.setHeaderText(null);
			alert.setContentText("An admin is not allowed to remove that admin's access.");
			alert.showAndWait();
			return;
		}

		// Exact required prompt: "Are you sure?"
		Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
		confirmAlert.setTitle("Confirm Deletion");
		confirmAlert.setHeaderText(null);
		confirmAlert.setContentText("Are you sure?");

		ButtonType buttonYes = new ButtonType("Yes");
		ButtonType buttonNo = new ButtonType("No");
		confirmAlert.getButtonTypes().setAll(buttonYes, buttonNo);

		Optional<ButtonType> result = confirmAlert.showAndWait();

		// Only execute on explicit Yes
		if (result.isPresent() && result.get() == buttonYes) {
			boolean success = theDatabase.deleteUser(targetUser);
			if (success) {
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

	protected static void performLogout() {
		guiUserLogin.ViewUserLogin.displayUserLogin(ViewAdminHome.theStage);
	}

	protected static void performQuit() {
		System.exit(0);
	}
}