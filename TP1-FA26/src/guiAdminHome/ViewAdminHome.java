package guiAdminHome;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import database.Database;
import entityClasses.User;
import guiUserUpdate.ViewUserUpdate;

public class ViewAdminHome {

	private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
	private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;

	// User info header
	protected static Label label_PageTitle = new Label();
	protected static Label label_UserDetails = new Label();
	protected static Button button_UpdateThisUser = new Button("Account Update");

	private static Line line_Separator1 = new Line(20, 95, width - 20, 95);

	// Status
	protected static Label label_NumberOfUsers = new Label("Number of Users: x");

	private static Line line_Separator2 = new Line(20, 165, width - 20, 165);

	// User Deletion Section
	protected static Button button_DeleteUser = new Button("Delete a User");
	protected static ComboBox<String> combobox_SelectUser = new ComboBox<String>();

	private static Line line_Separator3 = new Line(20, 525, width - 20, 525);

	// Exit buttons
	protected static Button button_Logout = new Button("Logout");
	protected static Button button_Quit = new Button("Quit");

	private static ViewAdminHome theView;
	private static Database theDatabase = applicationMain.FoundationsMain.database;

	protected static Stage theStage;
	private static Pane theRootPane;
	protected static User theUser;

	private static Scene theAdminHomeScene;
	private static final int theRole = 1;

	public static void displayAdminHome(Stage ps, User user) {
		theStage = ps;
		theUser = user;

		if (theView == null) theView = new ViewAdminHome();

		theDatabase.getUserAccountDetails(user.getUserName());
		applicationMain.FoundationsMain.activeHomePage = theRole;

		// Refresh user dropdown and count
		refreshUserList();
		label_NumberOfUsers.setText("Number of users: " + theDatabase.getNumberOfUsers());

		theStage.setTitle("CSE 360 Foundation Code: Admin Home Page");
		theStage.setScene(theAdminHomeScene);
		theStage.show();
	}

	public static void refreshUserList() {
		List<String> users = theDatabase.getUserList();
		if (users != null) {
			combobox_SelectUser.setItems(FXCollections.observableArrayList(users));
			combobox_SelectUser.getSelectionModel().select(0);
		}
	}

	private ViewAdminHome() {
		theRootPane = new Pane();
		theAdminHomeScene = new Scene(theRootPane, width, height);

		// Area 1: Header
		label_PageTitle.setText("Admin Home Page");
		setupLabelUI(label_PageTitle, "Arial", 28, width, Pos.CENTER, 0, 5);

		label_UserDetails.setText("User: " + theUser.getUserName());
		setupLabelUI(label_UserDetails, "Arial", 20, width, Pos.BASELINE_LEFT, 20, 55);

		setupButtonUI(button_UpdateThisUser, "Dialog", 18, 170, Pos.CENTER, 610, 45);
		button_UpdateThisUser.setOnAction((_) -> {
			ViewUserUpdate.displayUserUpdate(theStage, theUser);
		});

		// Area 2: Status
		setupLabelUI(label_NumberOfUsers, "Arial", 20, 200, Pos.BASELINE_LEFT, 20, 115);
		label_NumberOfUsers.setText("Number of users: " + theDatabase.getNumberOfUsers());

		// Area 3: Delete Action
		setupButtonUI(button_DeleteUser, "Dialog", 16, 250, Pos.CENTER, 20, 200);
		button_DeleteUser.setOnAction((_) -> {
			ControllerAdminHome.deleteUser();
		});

		setupComboBoxUI(combobox_SelectUser, "Dialog", 16, 220, 290, 200);

		// Area 4: Logout / Quit
		setupButtonUI(button_Logout, "Dialog", 18, 250, Pos.CENTER, 20, 540);
		button_Logout.setOnAction((_) -> {
			ControllerAdminHome.performLogout();
		});

		setupButtonUI(button_Quit, "Dialog", 18, 250, Pos.CENTER, 300, 540);
		button_Quit.setOnAction((_) -> {
			ControllerAdminHome.performQuit();
		});

		theRootPane.getChildren().addAll(
			label_PageTitle,
			label_UserDetails,
			button_UpdateThisUser,
			line_Separator1,
			label_NumberOfUsers,
			line_Separator2,
			button_DeleteUser,
			combobox_SelectUser,
			line_Separator3,
			button_Logout,
			button_Quit
		);
	}

	private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y) {
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);
	}

	private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y) {
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);
	}

	private void setupComboBoxUI(ComboBox<String> c, String ff, double f, double w, double x, double y) {
		c.setStyle("-fx-font: " + f + " " + ff + ";");
		c.setMinWidth(w);
		c.setLayoutX(x);
		c.setLayoutY(y);
	}
}