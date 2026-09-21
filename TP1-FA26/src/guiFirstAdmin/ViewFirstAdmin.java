package guiFirstAdmin;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;

/*******
 * <p> Title: ViewFirstAdmin Class</p>
 * 
 * <p> Description: The FirstAdmin Page View. This class is used to require the very first user of
 * the system to specify an Admin Username and Password when there is no database for the
 * application. This avoids the common weakness practice of hard coding credentials into the 
 * application.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * @version 1.00 2025-08-15 Initial version
 */
public class ViewFirstAdmin {

	/*-********************************************************************************************
	Attributes
	 */

	// Application values required by user interface
	private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
	private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;

	// GUI Widgets
	private static Label label_ApplicationTitle = new Label("Foundation Application Startup Page");
	private static Label label_TitleLine1 = 
			new Label("You are the first user. You must be an administrator.");
	
	private static Label label_TitleLine2 = 
			new Label("Enter the Admin's Username, the Password twice, and then click on " + 
					"Setup Admin Account.");
	
	protected static Label label_PasswordsDoNotMatch = new Label();
	protected static TextField text_AdminUsername = new TextField();
	protected static PasswordField text_AdminPassword1 = new PasswordField();
	protected static PasswordField text_AdminPassword2 = new PasswordField();
	private static Button button_AdminSetup = new Button("Setup Admin Account");

	// Alert used if error dialogs are needed
	protected static Alert alertUsernamePasswordError = new Alert(AlertType.INFORMATION);

	// Quit button to abort setup
	private static Button button_Quit = new Button("Quit");

	// Stage and Scene setup
	protected static Stage theStage;	
	private static Pane theRootPane;
	private static Scene theFirstAdminScene = null;
	private static final int theRole = 1; // Admin: 1; Role1: 2; Role2: 3

	/*-********************************************************************************************
	Constructor and Display
	 */

	/**********
	 * <p> Method: public static void displayFirstAdmin(Stage ps) </p>
	 * 
	 * <p> Description: Displays the first-admin view onto the stage.</p>
	 * 
	 * @param ps specifies the JavaFX Stage to be used for this GUI
	 */
	public static void displayFirstAdmin(Stage ps) {
		theStage = ps;
		new ViewFirstAdmin();
		applicationMain.FoundationsMain.activeHomePage = theRole;

		theStage.setTitle("CSE 360 Foundation Code: First User Account Setup");	
		theStage.setScene(theFirstAdminScene);
		theStage.show();
	}

	/**********
	 * <p> Method: private ViewFirstAdmin() </p>
	 * 
	 * <p> Description: Initializes and positions all GUI components.</p>
	 */
	private ViewFirstAdmin() {
		theRootPane = new Pane();
		theFirstAdminScene = new Scene(theRootPane, width, height);

		setupLabelUI(label_ApplicationTitle, "Arial", 32, width, Pos.CENTER, 0, 10);
		setupLabelUI(label_TitleLine1, "Arial", 24, width, Pos.CENTER, 0, 70);
		setupLabelUI(label_TitleLine2, "Arial", 18, width, Pos.CENTER, 0, 130);

		// Admin Username Text Field
		setupTextUI(text_AdminUsername, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 160, true);
		text_AdminUsername.setPromptText("Enter Admin Username");
		text_AdminUsername.textProperty().addListener((observable, oldValue, newValue) -> {
			ControllerFirstAdmin.setAdminUsername();
		});

		// Admin Password 1 Field
		setupTextUI(text_AdminPassword1, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 210, true);
		text_AdminPassword1.setPromptText("Enter Admin Password");
		text_AdminPassword1.textProperty().addListener((observable, oldValue, newValue) -> {
			ControllerFirstAdmin.setAdminPassword1();
		});

		// Admin Password 2 Field
		setupTextUI(text_AdminPassword2, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 260, true);
		text_AdminPassword2.setPromptText("Enter Admin Password Again");
		text_AdminPassword2.textProperty().addListener((observable, oldValue, newValue) -> {
			ControllerFirstAdmin.setAdminPassword2();
		});

		// Setup Admin Account Button
		setupButtonUI(button_AdminSetup, "Dialog", 18, 220, Pos.CENTER, 450, 210);
		button_AdminSetup.setOnAction((event) -> {
			ControllerFirstAdmin.doSetupAdmin(theStage, 1);
		});

		// Label to display validation error messages in red with wrap
		setupLabelUI(label_PasswordsDoNotMatch, "Arial", 16, width - 40, Pos.CENTER, 20, 310);
		label_PasswordsDoNotMatch.setTextFill(Color.RED);
		label_PasswordsDoNotMatch.setWrapText(true);

		// Quit Button
		setupButtonUI(button_Quit, "Dialog", 18, 250, Pos.CENTER, 300, 520);
		button_Quit.setOnAction((event) -> {
			ControllerFirstAdmin.performQuit();
		});

		// Add all nodes to the pane
		theRootPane.getChildren().addAll(
				label_ApplicationTitle, 
				label_TitleLine1,
				label_TitleLine2, 
				text_AdminUsername, 
				text_AdminPassword1, 
				text_AdminPassword2, 
				button_AdminSetup, 
				label_PasswordsDoNotMatch,
				button_Quit
		);
	}

	/*-********************************************************************************************
	Helper Methods
	 */

	private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y) {
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setMaxWidth(w);
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

	private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e) {
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);		
		t.setEditable(e);
	}
}