package guiContributor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import database.Database;
import entityClasses.LessonLearned;
import entityClasses.User;
import guiTools.LessonValidation;
import java.sql.SQLException;
import java.util.List;

/*******
 * <p> Title: ViewContributorHome Class. </p>
 * 
 * <p> Description: JavaFX Contributor Home Page implementing full CRUD controls
 * (Create, Read/Search, Update, Delete) and input validation for Lessons Learned.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter, Pranjal Singh
 * 
 * @version 2.00        2026-10-06 Complete CRUD implementation for CSE 360 HW2
 *  
 */
public class ViewContributorHome {
    
    /*-*******************************************************************************************
    Attributes
     *********************************************************************************************/
    
    private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
    private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;

    // GUI Area 1
    protected static Label label_PageTitle = new Label();
    protected static Label label_UserDetails = new Label();
    protected static Button button_UpdateThisUser = new Button("Account Update");
    protected static Line line_Separator1 = new Line(20, 90, width - 20, 90);

    // GUI Area 2: Lesson Learned CRUD Controls
    protected static Label label_Title = new Label("Title (4-64 chars):");
    protected static TextField text_Title = new TextField();

    protected static Label label_Desc = new Label("Description (10-1000 chars):");
    protected static TextArea text_Description = new TextArea();

    protected static Label label_Author = new Label("Author:");
    protected static TextField text_Author = new TextField();

    protected static Label label_Category = new Label("Category:");
    protected static TextField text_Category = new TextField();

    protected static Label label_ID = new Label("Lesson ID (Update/Delete):");
    protected static TextField text_LessonID = new TextField();

    protected static Label label_Search = new Label("Search Keyword:");
    protected static TextField text_Search = new TextField();

    protected static Button button_CreateLesson = new Button("Create Lesson");
    protected static Button button_UpdateLesson = new Button("Update Lesson");
    protected static Button button_DeleteLesson = new Button("Delete Lesson");
    protected static Button button_SearchLessons = new Button("Search");
    protected static Button button_ViewAllLessons = new Button("View All");

    protected static Label label_Status = new Label("Ready");

    // Table View for Displaying Stored Lessons Learned
    protected static TableView<LessonLearned> table_Lessons = new TableView<>();
    private static ObservableList<LessonLearned> lessonData = FXCollections.observableArrayList();

    // GUI Area 3
    protected static Line line_Separator4 = new Line(20, 595, width - 20, 595);
    protected static Button button_Logout = new Button("Logout");
    protected static Button button_Quit = new Button("Quit");

    // Singleton and Framework References
    private static ViewContributorHome theView;
    private static Database theDatabase = applicationMain.FoundationsMain.database;

    protected static Stage theStage;            
    protected static Pane theRootPane;            
    protected static User theUser;                
    
    private static Scene theViewContributorHomeScene;    
    protected static final int theRole = 2;
    
    // GUI Controls for Experience Information (Time & Effort Data)
    protected static Label label_ExperienceDetails = new Label("Experience Details:");
    protected static TextArea text_ExperienceDetails = new TextArea();

    protected static Label label_TimeSpent = new Label("Time Spent (mins):");
    protected static TextField text_TimeSpent = new TextField();

    protected static Label label_TeamEffort = new Label("Team Effort:");
    protected static TextField text_TeamEffort = new TextField();

    protected static Button button_SaveExperience = new Button("Add Experience Info");

    /*-*******************************************************************************************
    Display and Setup Methods
     *********************************************************************************************/

    public static void displayContributorHome(Stage ps, User user) {
        theStage = ps;
        theUser = user;
        
        if (theView == null) {
            theView = new ViewContributorHome();
        }
        
        try {
            theDatabase.getUserAccountDetails(user.getUserName());
        } catch (Exception e) {
            System.err.println("User details lookup warning: " + e.getMessage());
        }

        applicationMain.FoundationsMain.activeHomePage = theRole;
        
        label_UserDetails.setText("User: " + theUser.getUserName());
        text_Author.setText(theUser.getUserName());
        
        loadAllLessonsIntoTable();

        theStage.setTitle("CSE 360 Foundations: Contributor Home Page");
        theStage.setScene(theViewContributorHomeScene);
        theStage.show();
    }
    
    public static void displayRole1Home(Stage ps, User user) {
        displayContributorHome(ps, user);
    }

    private ViewContributorHome() {
        theRootPane = new Pane();
        theViewContributorHomeScene = new Scene(theRootPane, width, height);
        
        // GUI Area 1
        label_PageTitle.setText("Contributor Home Page - Lessons Learned");
        setupLabelUI(label_PageTitle, "Arial", 24, width, Pos.CENTER, 0, 10);

        label_UserDetails.setText("User: " + (theUser != null ? theUser.getUserName() : ""));
        setupLabelUI(label_UserDetails, "Arial", 16, 250, Pos.BASELINE_LEFT, 20, 55);
        
        setupButtonUI(button_UpdateThisUser, "Dialog", 14, 150, Pos.CENTER, width - 180, 50);
        button_UpdateThisUser.setOnAction(event -> ControllerContributorHome.performUpdate());
        
        // ==================== GUI Area 2: CRUD Widgets ====================
        
        // Left Column: Creation / Edit Inputs
        setupLabelUI(label_Title, "Dialog", 12, 200, Pos.BASELINE_LEFT, 25, 100);
        text_Title.setLayoutX(25);
        text_Title.setLayoutY(120);
        text_Title.setMinWidth(280);
        text_Title.setPromptText("Enter title (4-64 characters)");

        setupLabelUI(label_Desc, "Dialog", 12, 200, Pos.BASELINE_LEFT, 25, 150);
        text_Description.setLayoutX(25);
        text_Description.setLayoutY(170);
        text_Description.setPrefWidth(280);
        text_Description.setPrefRowCount(3);
        text_Description.setPromptText("Enter description (10-1000 characters)");

        setupLabelUI(label_Author, "Dialog", 12, 100, Pos.BASELINE_LEFT, 25, 245);
        text_Author.setLayoutX(25);
        text_Author.setLayoutY(265);
        text_Author.setMinWidth(135);

        setupLabelUI(label_Category, "Dialog", 12, 100, Pos.BASELINE_LEFT, 170, 245);
        text_Category.setLayoutX(170);
        text_Category.setLayoutY(265);
        text_Category.setMinWidth(135);
        text_Category.setText("Architecture");

        // Action Buttons: Create, Update, Delete
        setupButtonUI(button_CreateLesson, "Dialog", 12, 130, Pos.CENTER, 25, 305);
        setupButtonUI(button_UpdateLesson, "Dialog", 12, 130, Pos.CENTER, 175, 305);

        setupLabelUI(label_ID, "Dialog", 12, 180, Pos.BASELINE_LEFT, 25, 345);
        text_LessonID.setLayoutX(25);
        text_LessonID.setLayoutY(365);
        text_LessonID.setMaxWidth(75);
        text_LessonID.setPromptText("ID");

        setupButtonUI(button_DeleteLesson, "Dialog", 12, 110, Pos.CENTER, 110, 365);

        // Status Feedback Banner
        setupLabelUI(label_Status, "Dialog", 12, 350, Pos.BASELINE_LEFT, 25, 575);
        label_Status.setStyle("-fx-text-fill: #0044cc; -fx-font-weight: bold;");

        // Right Column: Search Controls & Table View
        setupLabelUI(label_Search, "Dialog", 12, 150, Pos.BASELINE_LEFT, 330, 100);
        text_Search.setLayoutX(330);
        text_Search.setLayoutY(120);
        text_Search.setMinWidth(220);
        text_Search.setPromptText("Search keyword...");

        setupButtonUI(button_SearchLessons, "Dialog", 12, 85, Pos.CENTER, 560, 120);
        setupButtonUI(button_ViewAllLessons, "Dialog", 12, 85, Pos.CENTER, 655, 120);

        // TableView Configuration
        table_Lessons.setLayoutX(330);
        table_Lessons.setLayoutY(155);
        table_Lessons.setPrefWidth(420);
        table_Lessons.setPrefHeight(340);
        
        // Experience Information Widgets
        setupLabelUI(label_TimeSpent, "Dialog", 12, 120, Pos.BASELINE_LEFT, 25, 405);
        text_TimeSpent.setLayoutX(25);
        text_TimeSpent.setLayoutY(430);
        text_TimeSpent.setMaxWidth(130);
        text_TimeSpent.setPromptText("Minutes");

        setupLabelUI(label_TeamEffort, "Dialog", 12, 120, Pos.BASELINE_LEFT, 170, 405);
        text_TeamEffort.setLayoutX(170);
        text_TeamEffort.setLayoutY(430);
        text_TeamEffort.setMinWidth(135);
        text_TeamEffort.setPromptText("e.g. 2 devs, 5 hrs");

        setupLabelUI(label_ExperienceDetails, "Dialog", 12, 200, Pos.BASELINE_LEFT, 25, 460);
        text_ExperienceDetails.setLayoutX(25);
        text_ExperienceDetails.setLayoutY(485);
        text_ExperienceDetails.setPrefWidth(280);
        text_ExperienceDetails.setPrefRowCount(2);
        text_ExperienceDetails.setPromptText("What was done, how it was done...");

        setupButtonUI(button_SaveExperience, "Dialog", 12, 150, Pos.CENTER, 25, 545);

        // Selection & Locking Check Listener
        table_Lessons.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selected) -> {
            if (selected != null) {
                text_LessonID.setText(String.valueOf(selected.getId()));
                text_Title.setText(selected.getTitle());
                text_Description.setText(selected.getDescription());
                text_Author.setText(selected.getAuthor());
                text_Category.setText(selected.getCategory());

                // Populate Experience Info
                text_ExperienceDetails.setText(selected.getExperienceDetails() != null ? selected.getExperienceDetails() : "");
                text_TimeSpent.setText(String.valueOf(selected.getTimeSpentMinutes()));
                text_TeamEffort.setText(selected.getTeamEffort() != null ? selected.getTeamEffort() : "");

                // Enforce Locking Rules (Prevent update/delete if locked by curator)
                boolean isLocked = selected.isLocked();
                button_UpdateLesson.setDisable(isLocked);
                button_DeleteLesson.setDisable(isLocked);

                if (isLocked) {
                    label_Status.setText("This lesson is LOCKED and cannot be modified or deleted.");
                    label_Status.setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
                }
            }
        });

        // Save Experience Info Action Handler 
        button_SaveExperience.setOnAction(e -> {
        	try {
                int id = Integer.parseInt(text_LessonID.getText().trim());
                String expDetails = text_ExperienceDetails.getText() == null ? "" : text_ExperienceDetails.getText().trim();
                String teamEffort = text_TeamEffort.getText() == null ? "" : text_TeamEffort.getText().trim();
                
                String timeText = text_TimeSpent.getText() == null ? "" : text_TimeSpent.getText().trim();
                if (timeText.isEmpty()) {
                    label_Status.setText("Time spent cannot be empty.");
                    label_Status.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    showAlert(Alert.AlertType.ERROR, "Validation Error", "Time spent (minutes) cannot be empty.");
                    return;
                }

                int timeSpent = Integer.parseInt(timeText);
                if (timeSpent < 0) {
                    label_Status.setText("Time spent cannot be negative.");
                    label_Status.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    showAlert(Alert.AlertType.ERROR, "Validation Error", "Time spent in minutes must be a positive integer (cannot be negative).");
                    return;
                }

                boolean updated = theDatabase.updateLessonExperienceAndLocks(id, expDetails, timeSpent, teamEffort);
                if (updated) {
                    label_Status.setText("Experience information added for Lesson ID " + id + "!");
                    label_Status.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Experience information successfully updated.");
                    loadAllLessonsIntoTable();
                } else {
                    label_Status.setText("Failed to update experience info. Record ID not found.");
                    label_Status.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                }
            } catch (NumberFormatException ex) {
                label_Status.setText("Invalid numeric input.");
                label_Status.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please provide a valid numeric Lesson ID and Time Spent (minutes).");
            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Database Error", ex.getMessage());
            }
        });

        TableColumn<LessonLearned, Integer> colId = new TableColumn<>("ID");
//        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setCellValueFactory(cellData -> 
        new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colId.setPrefWidth(45);

        TableColumn<LessonLearned, String> colTitle = new TableColumn<>("Title");
//        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colTitle.setCellValueFactory(cellData -> 
        new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitle()));
        colTitle.setPrefWidth(125);

        TableColumn<LessonLearned, String> colDesc = new TableColumn<>("Description");
//        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDesc.setCellValueFactory(cellData -> 
        new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescription()));
        colDesc.setPrefWidth(150);

        TableColumn<LessonLearned, String> colCat = new TableColumn<>("Category");
//        colCat.setCellValueFactory(new PropertyValueFactory<>("category"));
        colCat.setCellValueFactory(cellData -> 
        new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCategory()));
        colCat.setPrefWidth(95);

        table_Lessons.getColumns().clear();
        table_Lessons.getColumns().addAll(colId, colTitle, colDesc, colCat);
        table_Lessons.setItems(lessonData);

        // Row Selection Auto-Fill
        table_Lessons.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selected) -> {
            if (selected != null) {
                text_LessonID.setText(String.valueOf(selected.getId()));
                text_Title.setText(selected.getTitle());
                text_Description.setText(selected.getDescription());
                text_Author.setText(selected.getAuthor());
                text_Category.setText(selected.getCategory());
            }
        });

        // ==================== Handlers with Checked SQLException Handling ====================

        // 1. CREATE
        button_CreateLesson.setOnAction(e -> {
            String title = text_Title.getText() == null ? "" : text_Title.getText().trim();
            String desc = text_Description.getText() == null ? "" : text_Description.getText().trim();
            String auth = text_Author.getText() == null ? "" : text_Author.getText().trim();
            String cat = text_Category.getText() == null ? "" : text_Category.getText().trim();

            String validationError = LessonValidation.validate(title, desc, auth, cat);
            if (validationError != null) {
                label_Status.setText(validationError);
                label_Status.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                showAlert(Alert.AlertType.ERROR, "Validation Error", validationError);
                return;
            }

            LessonLearned lesson = new LessonLearned(title, desc, auth, cat);
            try {
                int generatedId = theDatabase.createLesson(lesson);
                if (generatedId > 0) {
                    label_Status.setText("Created Lesson successfully! ID: " + generatedId);
                    label_Status.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Lesson Created Successfully (ID: " + generatedId + ")");
                    loadAllLessonsIntoTable();
                } else {
                    label_Status.setText("Database insertion failed.");
                    label_Status.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                }
            } catch (SQLException ex) {
                label_Status.setText("Database error: " + ex.getMessage());
                showAlert(Alert.AlertType.ERROR, "Database Error", ex.getMessage());
            }
        });

        // 2. READ / SEARCH (Matches Arbitrary Subset)
        button_SearchLessons.setOnAction(e -> {
            String keyword = text_Search.getText() == null ? "" : text_Search.getText().trim();
            try {
                List<LessonLearned> matches = theDatabase.searchLessons(keyword, "");
                refreshTableWithList(matches);
                if (matches.isEmpty()) {
                    label_Status.setText("No matching records found.");
                    label_Status.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    showAlert(Alert.AlertType.INFORMATION, "Search Results", "No matching records found.");
                } else {
                    label_Status.setText("Found " + matches.size() + " matching record(s).");
                    label_Status.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                }
            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Search Error", ex.getMessage());
            }
        });

        // READ ALL
        button_ViewAllLessons.setOnAction(e -> {
            text_Search.clear();
            loadAllLessonsIntoTable();
            label_Status.setText("Displaying all " + lessonData.size() + " lessons.");
            label_Status.setStyle("-fx-text-fill: #0044cc; -fx-font-weight: bold;");
        });

        // 3. UPDATE
        button_UpdateLesson.setOnAction(e -> {
            try {
                int id = Integer.parseInt(text_LessonID.getText().trim());
                String title = text_Title.getText() == null ? "" : text_Title.getText().trim();
                String desc = text_Description.getText() == null ? "" : text_Description.getText().trim();
                String cat = text_Category.getText() == null ? "" : text_Category.getText().trim();

                String validationError = LessonValidation.validate(title, desc, text_Author.getText(), cat);
                if (validationError != null) {
                    label_Status.setText(validationError);
                    label_Status.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    showAlert(Alert.AlertType.ERROR, "Validation Error", validationError);
                    return;
                }

                boolean updated = theDatabase.updateLesson(id, title, desc, cat);
                if (updated) {
                    label_Status.setText("Lesson ID " + id + " updated successfully.");
                    label_Status.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Lesson ID " + id + " updated successfully.");
                    loadAllLessonsIntoTable();
                } else {
                    label_Status.setText("Update Failed: Record ID not found.");
                    label_Status.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    showAlert(Alert.AlertType.ERROR, "Update Failed", "Update Failed: Record ID not found.");
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please provide a valid numeric Lesson ID.");
            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Database Error", ex.getMessage());
            }
        });

        // 4. DELETE
        button_DeleteLesson.setOnAction(e -> {
            try {
                int id = Integer.parseInt(text_LessonID.getText().trim());
                boolean deleted = theDatabase.deleteLesson(id);
                if (deleted) {
                    label_Status.setText("Lesson ID " + id + " deleted permanently.");
                    label_Status.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Lesson ID " + id + " deleted permanently.");
                    text_LessonID.clear();
                    loadAllLessonsIntoTable();
                } else {
                    label_Status.setText("Delete Failed: Record ID not found.");
                    label_Status.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    showAlert(Alert.AlertType.ERROR, "Delete Failed", "Delete Failed: Record ID not found.");
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please provide a valid numeric Lesson ID to delete.");
            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Database Error", ex.getMessage());
            }
        });

        // GUI Area 3
        setupButtonUI(button_Logout, "Dialog", 16, 220, Pos.CENTER, 40, 600);
        button_Logout.setOnAction(event -> ControllerContributorHome.performLogout());
        
        setupButtonUI(button_Quit, "Dialog", 16, 220, Pos.CENTER, width - 260, 600);
        button_Quit.setOnAction(event -> ControllerContributorHome.performQuit());

        // Assemble Scene
        theRootPane.getChildren().addAll(
            label_PageTitle, label_UserDetails, button_UpdateThisUser, line_Separator1,
            label_Title, text_Title, label_Desc, text_Description,
            label_Author, text_Author, label_Category, text_Category,
            label_ID, text_LessonID, label_Search, text_Search,
            button_CreateLesson, button_UpdateLesson, button_DeleteLesson,
            button_SearchLessons, button_ViewAllLessons, label_Status, table_Lessons,
            line_Separator4, button_Logout, button_Quit, label_TimeSpent, text_TimeSpent, 
            label_TeamEffort, text_TeamEffort, label_ExperienceDetails, 
            text_ExperienceDetails, button_SaveExperience
        );
    }

    /*-********************************************************************************************
    Helper Methods
     *********************************************************************************************/
    
    /**
     * Loads lessons into table filtered exclusively by current contributor username.
     * <p> Requirement Trace: Satisfies Contributor User Story (contributors can only
     * view their own lessons) </p>
     */
    private static void loadAllLessonsIntoTable() {
    	try {
            if (theUser != null && theUser.getUserName() != null) {
                refreshTableWithList(theDatabase.getLessonsByAuthor(theUser.getUserName()));
            } else {
                refreshTableWithList(theDatabase.getAllLessons());
            }
        } catch (SQLException e) {
            System.err.println("Error fetching lessons from database: " + e.getMessage());
        }
    }

    private static void refreshTableWithList(List<LessonLearned> list) {
        lessonData.clear();
        if (list != null) {
            lessonData.addAll(list);
        }
    }

    private static void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type, content, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private static void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y) {
        l.setFont(Font.font(ff, f));
        l.setMinWidth(w);
        l.setAlignment(p);
        l.setLayoutX(x);
        l.setLayoutY(y);        
    }

    private static void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y) {
        b.setFont(Font.font(ff, f));
        b.setMinWidth(w);
        b.setAlignment(p);
        b.setLayoutX(x);
        b.setLayoutY(y);        
    }
}