package testing;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import entityClasses.LessonLearned;
import entityClasses.LessonLearnedList;
import entityClasses.User;
import guiContributor.ContributorValidator;

/*******
 * <p> Title: ContributorManualTestbed Class. </p>
 * 
 * <p> Description: Interactive console-based manual testbed modeled after the Foundations
 * PasswordEvaluationTestbed. Allows a developer or grader to manually exercise Contributor
 * CRUD operations, test custom positive and negative boundary inputs, switch active user roles
 * to verify ownership authorization guards, exercise TP3 forward-compatible hooks, or invoke
 * the full 28-test semi-automated suite ({@link ContributorTestingAutomation}). </p>
 * 
 * <p> Manual Test Procedures Documented in Javadocs: </p>
 * <ul>
 *   <li>{@link #executeManualCreateTest(LessonLearnedList, User, String, String, String)}
 *       — Manual verification of TP2-REQ-01, TP2-REQ-02, TP2-REQ-03, and TP2-REQ-04</li>
 *   <li>{@link #executeManualReadAndSearchTest(LessonLearnedList, String, String)}
 *       — Manual verification of TP2-REQ-05, TP2-REQ-06, and TP2-REQ-07</li>
 *   <li>{@link #executeManualUpdateTest(LessonLearnedList, User, int, String, String, String)}
 *       — Manual verification of TP2-REQ-08 and TP2-REQ-09</li>
 *   <li>{@link #executeManualDeleteTest(LessonLearnedList, User, int)}
 *       — Manual verification of TP2-REQ-10 and TP2-REQ-11</li>
 *   <li>{@link #executeManualTP3GovernanceTest(LessonLearnedList, int, boolean, String, boolean)}
 *       — Manual verification of TP2-REQ-12</li>
 * </ul>
 * 
 * <p> Copyright: Lynn Robert Carter © 2026 </p>
 * 
 * @author Sanidhya Tiwari
 * @version 2.00 2026-10-23 Interactive manual testbed for TP2 Tasks 6 and 7.
 */
public class ContributorManualTestbed {

    /** Auto-incrementing primary key counter for manual console session records. */
    private static int nextManualId = 301;

    /**
     * Default constructor.
     */
    public ContributorManualTestbed() {
    }

    /*****
     * <p> Method: main(String[] args) </p>
     * 
     * <p> Description: Interactive menu loop allowing manual testing of all Contributor
     * User Stories and requirements, or running the full automated test suite when launched
     * with {@code --auto} or menu option {@code 6}. </p>
     * 
     * @param args optional command-line flag ({@code --auto} runs automated suite and exits)
     */
    public static void main(String[] args) {
        LessonLearnedList sessionList = new LessonLearnedList();
        User activeUser = new User("Sanidhya", "ValidPass1!", "Sanidhya", "", "Tiwari",
                "Sanidhya", "stiwar60@asu.edu", false, true, false);

        // Pre-populate two baseline lessons so Read/Update/Delete can be tested immediately
        sessionList.addLesson(new LessonLearned(
                300,
                "Baseline H2 Connection Guide",
                "Ensure H2 JDBC driver is on the module path and closeConnection() is called on exit.",
                "Sanidhya",
                "Database",
                System.currentTimeMillis()
        ));

        if (args != null && args.length > 0 && "--auto".equalsIgnoreCase(args[0])) {
            ContributorTestingAutomation.main(new String[0]);
            return;
        }

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("============================================================================");
        System.out.println("         CSE 360 TP2 Contributor Interactive Manual Testbed                 ");
        System.out.println("============================================================================");

        while (running) {
            System.out.println("\nActive User: " + activeUser.getUserName()
                    + " (Admin=" + activeUser.getAdminRole() + ", Contributor=" + activeUser.getNewRole1() + ")");
            System.out.println("1. Manual Create Lesson Test (TP2-REQ-01 to TP2-REQ-04)");
            System.out.println("2. Manual Read / Search / Filter Test (TP2-REQ-05 to TP2-REQ-07)");
            System.out.println("3. Manual Update Lesson Test (TP2-REQ-08 & TP2-REQ-09)");
            System.out.println("4. Manual Delete Lesson Test (TP2-REQ-10 & TP2-REQ-11)");
            System.out.println("5. Manual TP3 Governance Hooks Test (TP2-REQ-12)");
            System.out.println("6. Run All 28 Semi-Automated Tests (ContributorTestingAutomation)");
            System.out.println("0. Quit Manual Testbed");
            System.out.print("Select an option (0-6): ");

            if (!scanner.hasNextLine()) {
                break;
            }
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Enter Title (4-64 chars): ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Category (GUI, Architecture, Database, Testing, Version Control, General): ");
                    String cat = scanner.nextLine();
                    System.out.print("Enter Description (10-1000 chars): ");
                    String desc = scanner.nextLine();
                    executeManualCreateTest(sessionList, activeUser, title, desc, cat);
                    break;
                case "2":
                    System.out.print("Enter Search Keyword (or blank for all): ");
                    String kw = scanner.nextLine();
                    System.out.print("Enter Category Filter (or 'All'): ");
                    String filterCat = scanner.nextLine();
                    executeManualReadAndSearchTest(sessionList, kw, filterCat);
                    break;
                case "3":
                    System.out.print("Enter Lesson ID to Update: ");
                    int upId = parseSafeInt(scanner.nextLine());
                    System.out.print("Enter New Title (4-64 chars): ");
                    String newTitle = scanner.nextLine();
                    System.out.print("Enter New Category: ");
                    String newCat = scanner.nextLine();
                    System.out.print("Enter New Description (10-1000 chars): ");
                    String newDesc = scanner.nextLine();
                    executeManualUpdateTest(sessionList, activeUser, upId, newTitle, newDesc, newCat);
                    break;
                case "4":
                    System.out.print("Enter Lesson ID to Delete: ");
                    int delId = parseSafeInt(scanner.nextLine());
                    executeManualDeleteTest(sessionList, activeUser, delId);
                    break;
                case "5":
                    System.out.print("Enter Lesson ID for TP3 Governance Hooks: ");
                    int tp3Id = parseSafeInt(scanner.nextLine());
                    System.out.print("Flag for moderation? (true/false): ");
                    boolean flag = Boolean.parseBoolean(scanner.nextLine().trim());
                    System.out.print("Moderation note: ");
                    String note = scanner.nextLine();
                    System.out.print("Mark staff verified? (true/false): ");
                    boolean verified = Boolean.parseBoolean(scanner.nextLine().trim());
                    executeManualTP3GovernanceTest(sessionList, tp3Id, flag, note, verified);
                    break;
                case "6":
                    ContributorTestingAutomation.main(new String[0]);
                    break;
                case "0":
                    running = false;
                    System.out.println("Exiting Contributor Manual Testbed.");
                    break;
                default:
                    System.out.println("Invalid option. Please enter a number from 0 to 6.");
                    break;
            }
        }
        scanner.close();
    }

    /*****
     * <p> Method: executeManualCreateTest(LessonLearnedList list, User activeUser,
     *                                     String title, String description, String category) </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-01, TP2-REQ-02, TP2-REQ-03, and TP2-REQ-04
     * (Manual Create and Input Validation evaluation). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Validates the user-entered
     * {@code title}, {@code description}, {@code author}, and {@code category} via
     * {@link ContributorValidator#validateLessonFields(String, String, String, String)} before
     * instantiating a {@link LessonLearned} and adding it to {@link LessonLearnedList#addLesson(LessonLearned)}. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> For a positive manual input,
     * the console prints {@code [MANUAL CREATE PASS]} along with the assigned ID and updated collection
     * count. For a negative manual input (such as a blank title or short description), the test passes
     * if the console prints {@code [MANUAL CREATE REJECTED]} with the specific field validation error
     * and leaves the collection count unchanged. </p>
     * 
     * @param list target LessonLearnedList collection
     * @param activeUser currently logged-in User authoring the lesson
     * @param title entered title string
     * @param description entered description string
     * @param category entered category string
     * @return true if the lesson was valid and created, false if rejected by validation
     */
    public static boolean executeManualCreateTest(LessonLearnedList list, User activeUser,
                                                  String title, String description, String category) {
        String author = (activeUser != null) ? activeUser.getUserName() : "";
        String error = ContributorValidator.validateLessonFields(title, description, author, category);
        if (!error.isEmpty()) {
            System.out.println("[MANUAL CREATE REJECTED] " + error);
            return false;
        }
        int assignedId = nextManualId++;
        LessonLearned lesson = new LessonLearned(assignedId, title.trim(), description.trim(),
                author.trim(), category.trim(), System.currentTimeMillis());
        boolean added = list.addLesson(lesson);
        if (added) {
            System.out.println("[MANUAL CREATE PASS] Created Lesson #" + assignedId
                    + " | Total Stored: " + list.getTotalCount());
            return true;
        }
        System.out.println("[MANUAL CREATE REJECTED] Could not insert lesson into collection.");
        return false;
    }

    /*****
     * <p> Method: executeManualReadAndSearchTest(LessonLearnedList list, String keyword, String category) </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-05, TP2-REQ-06, and TP2-REQ-07 (Manual Read All,
     * Keyword Search, and Category Filter evaluation). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Validates the search
     * query length via {@link ContributorValidator#validateSearchKeyword(String)}, executes
     * {@link LessonLearnedList#searchByKeyword(String)} or
     * {@link LessonLearnedList#filterByCategory(String)}, and prints all matching records in
     * {@code currentSubset}. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [MANUAL READ/SEARCH RESULT]} with the subset size and lists each matching record,
     * or prints {@code [MANUAL SEARCH REJECTED]} if the keyword exceeds 64 characters. </p>
     * 
     * @param list target LessonLearnedList collection
     * @param keyword search keyword (or blank)
     * @param category category filter (or "All")
     * @return number of matching records in the resulting subset, or -1 if validation failed
     */
    public static int executeManualReadAndSearchTest(LessonLearnedList list, String keyword, String category) {
        String kwError = ContributorValidator.validateSearchKeyword(keyword);
        if (!kwError.isEmpty()) {
            System.out.println("[MANUAL SEARCH REJECTED] " + kwError);
            return -1;
        }
        List<LessonLearned> results;
        if (category != null && !category.trim().isEmpty() && !"All".equalsIgnoreCase(category.trim())) {
            results = list.filterByCategory(category);
        } else {
            results = list.searchByKeyword(keyword);
        }
        System.out.println("[MANUAL READ/SEARCH RESULT] Subset Count: " + results.size()
                + " | Empty=" + list.isSubsetEmpty());
        for (LessonLearned l : results) {
            System.out.println("   -> #" + l.getId() + " [" + l.getCategory() + "] "
                    + l.getTitle() + " (by " + l.getAuthor() + ")");
        }
        return results.size();
    }

    /*****
     * <p> Method: executeManualUpdateTest(LessonLearnedList list, User activeUser, int id,
     *                                     String newTitle, String newDescription, String newCategory) </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-08 and TP2-REQ-09 (Manual Update and Ownership
     * Authorization evaluation). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Locates the target
     * record via {@link LessonLearnedList#findById(int)}, checks ownership permission via
     * {@link ContributorValidator#verifyModifyPermission(LessonLearned, User)}, validates the
     * updated fields via {@link ContributorValidator#validateLessonFields(String, String, String, String)},
     * and applies the update via {@link LessonLearnedList#updateLesson(int, String, String, String)}. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> Outputs
     * {@code [MANUAL UPDATE PASS]} when an authorized user supplies valid fields for an existing ID,
     * or {@code [MANUAL UPDATE REJECTED]} with the specific not-found, authorization, or field
     * boundary error message. </p>
     * 
     * @param list target LessonLearnedList collection
     * @param activeUser currently logged-in User attempting the update
     * @param id primary key ID of the lesson to update
     * @param newTitle updated title string
     * @param newDescription updated description string
     * @param newCategory updated category string
     * @return true if the update succeeded, false if rejected
     */
    public static boolean executeManualUpdateTest(LessonLearnedList list, User activeUser, int id,
                                                  String newTitle, String newDescription, String newCategory) {
        Optional<LessonLearned> targetOpt = list.findById(id);
        if (targetOpt.isEmpty()) {
            System.out.println("[MANUAL UPDATE REJECTED] Error: Lesson ID #" + id + " not found.");
            return false;
        }
        String authError = ContributorValidator.verifyModifyPermission(targetOpt.get(), activeUser);
        if (!authError.isEmpty()) {
            System.out.println("[MANUAL UPDATE REJECTED] " + authError);
            return false;
        }
        String fieldError = ContributorValidator.validateLessonFields(
                newTitle, newDescription, targetOpt.get().getAuthor(), newCategory);
        if (!fieldError.isEmpty()) {
            System.out.println("[MANUAL UPDATE REJECTED] " + fieldError);
            return false;
        }
        boolean updated = list.updateLesson(id, newTitle, newDescription, newCategory);
        System.out.println("[MANUAL UPDATE PASS] Updated Lesson #" + id + " -> " + updated);
        return updated;
    }

    /*****
     * <p> Method: executeManualDeleteTest(LessonLearnedList list, User activeUser, int id) </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-10 and TP2-REQ-11 (Manual Delete and Ownership
     * Authorization evaluation). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Checks whether ID
     * exists via {@link LessonLearnedList#findById(int)}, verifies ownership or Admin privileges
     * via {@link ContributorValidator#verifyModifyPermission(LessonLearned, User)}, and removes
     * the record via {@link LessonLearnedList#deleteLesson(int)}. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> Prints
     * {@code [MANUAL DELETE PASS]} when an authorized user deletes an existing lesson, or
     * {@code [MANUAL DELETE REJECTED]} when the ID is invalid/missing or the user lacks permission. </p>
     * 
     * @param list target LessonLearnedList collection
     * @param activeUser currently logged-in User attempting deletion
     * @param id primary key ID of the lesson to delete
     * @return true if deleted, false if rejected
     */
    public static boolean executeManualDeleteTest(LessonLearnedList list, User activeUser, int id) {
        Optional<LessonLearned> targetOpt = list.findById(id);
        if (targetOpt.isEmpty()) {
            System.out.println("[MANUAL DELETE REJECTED] Error: Lesson ID #" + id + " not found.");
            return false;
        }
        String authError = ContributorValidator.verifyModifyPermission(targetOpt.get(), activeUser);
        if (!authError.isEmpty()) {
            System.out.println("[MANUAL DELETE REJECTED] " + authError);
            return false;
        }
        boolean deleted = list.deleteLesson(id);
        System.out.println("[MANUAL DELETE PASS] Deleted Lesson #" + id
                + " | Remaining Total: " + list.getTotalCount());
        return deleted;
    }

    /*****
     * <p> Method: executeManualTP3GovernanceTest(LessonLearnedList list, int id,
     *                                            boolean flagged, String note, boolean verified) </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-12 (Manual evaluation of TP3 forward-compatible
     * moderation flag, peer endorsement counter, and staff verification hooks). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Invokes
     * {@link LessonLearnedList#setModerationFlag(int, boolean, String)},
     * {@link LessonLearnedList#incrementEndorsement(int)}, and
     * {@link LessonLearnedList#setTechnicalVerification(int, boolean)} on the specified lesson ID. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> Prints
     * {@code [MANUAL TP3 HOOKS PASS]} with the updated flag, moderation note, endorsement count,
     * and verification status when the ID exists, or {@code [MANUAL TP3 HOOKS REJECTED]} when the
     * ID does not exist. </p>
     * 
     * @param list target LessonLearnedList collection
     * @param id primary key ID of the lesson
     * @param flagged moderation flag state to set
     * @param note administrative moderation explanation note
     * @param verified staff verification state to set
     * @return true if all hooks succeeded, false if lesson ID was not found
     */
    public static boolean executeManualTP3GovernanceTest(LessonLearnedList list, int id,
                                                         boolean flagged, String note, boolean verified) {
        boolean fOk = list.setModerationFlag(id, flagged, note);
        boolean eOk = list.incrementEndorsement(id);
        boolean vOk = list.setTechnicalVerification(id, verified);
        if (!fOk || !eOk || !vOk) {
            System.out.println("[MANUAL TP3 HOOKS REJECTED] Error: Lesson ID #" + id + " not found.");
            return false;
        }
        LessonLearned target = list.findById(id).get();
        System.out.println("[MANUAL TP3 HOOKS PASS] Lesson #" + id
                + " | Flagged=" + target.isFlagged()
                + " | Note='" + target.getModerationNote() + "'"
                + " | Endorsements=" + target.getEndorsementCount()
                + " | Verified=" + target.isVerified());
        return true;
    }

    /*****
     * <p> Method: parseSafeInt(String input) </p>
     * 
     * <p> Description: Parses a console string into an integer ID, returning -1 if non-numeric. </p>
     * 
     * @param input raw string read from console input
     * @return parsed integer or -1 if invalid
     */
    private static int parseSafeInt(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch ( Exception ex) {
            return -1;
        }
    }
}
