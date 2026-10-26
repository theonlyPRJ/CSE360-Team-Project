package testing;

import java.util.List;
import java.util.Optional;
import entityClasses.LessonLearned;
import entityClasses.LessonLearnedList;
import entityClasses.User;
import guiContributor.ContributorValidator;

/*******
 * <p> Title: ContributorTestingAutomation Class. </p>
 * 
 * <p> Description: Semi-automated testing testbed modeled after the Foundations
 * PasswordEvaluationTestingAutomation architecture. Executes 28 positive and negative
 * test cases across all 12 Phase Two (TP2) Contributor requirements (TP2-REQ-01 through
 * TP2-REQ-12), covering LessonLearned entity construction, LessonLearnedList CRUD
 * operations, ContributorValidator input boundary checks, ownership authorization guards,
 * and TP3 forward-compatible moderation, endorsement, and verification hooks. </p>
 * 
 * <p> Requirement Coverage Summary: </p>
 * <ul>
 *   <li><b>TP2-REQ-01 (US-HW2-01 Create Entity &amp; Master Insertion):</b>
 *       {@link #performTestCase01_CreateValidLesson()},
 *       {@link #performTestCase02_CreateNullLesson()},
 *       {@link #performTestCase03_CreateDuplicateIdLesson()}</li>
 *   <li><b>TP2-REQ-02 (US-HW2-01 Title Input Boundary Validation):</b>
 *       {@link #performTestCase04_ValidateValidTitleBoundaries()},
 *       {@link #performTestCase05_ValidateBlankAndShortTitle()},
 *       {@link #performTestCase06_ValidateOversizedTitle()}</li>
 *   <li><b>TP2-REQ-03 (US-HW2-01 Description Input Boundary Validation):</b>
 *       {@link #performTestCase07_ValidateValidDescriptionBoundaries()},
 *       {@link #performTestCase08_ValidateBlankAndShortDescription()},
 *       {@link #performTestCase09_ValidateOversizedDescription()}</li>
 *   <li><b>TP2-REQ-04 (US-HW2-01 Category &amp; Author Input Validation):</b>
 *       {@link #performTestCase10_ValidateValidCategoryAndAuthor()},
 *       {@link #performTestCase11_ValidateInvalidCategoryAndAuthor()}</li>
 *   <li><b>TP2-REQ-05 (US-HW2-02 Read All Lessons &amp; Primary Key Lookup):</b>
 *       {@link #performTestCase12_ReadAllAndFindExistingId()},
 *       {@link #performTestCase13_ReadNonExistentIdAndUnmodifiableView()}</li>
 *   <li><b>TP2-REQ-06 (US-HW2-02 Keyword Search Across Arbitrary Subset Sizes):</b>
 *       {@link #performTestCase14_SearchKeywordMultiAndSingleSubset()},
 *       {@link #performTestCase15_SearchKeywordEmptySubset()},
 *       {@link #performTestCase16_SearchKeywordBlankResetAndOversizedQuery()}</li>
 *   <li><b>TP2-REQ-07 (US-HW2-02 Category Filter Operations):</b>
 *       {@link #performTestCase17_FilterCategoryMatchingAndAllReset()},
 *       {@link #performTestCase18_FilterCategoryNoMatchEmptySubset()}</li>
 *   <li><b>TP2-REQ-08 (US-HW2-03 Update Existing Lesson &amp; Subset Synchronization):</b>
 *       {@link #performTestCase19_UpdateValidLessonAndSyncSubset()},
 *       {@link #performTestCase20_UpdateInvalidFieldsAndNonExistentId()}</li>
 *   <li><b>TP2-REQ-09 (US-HW2-03 Update Ownership &amp; Role Authorization):</b>
 *       {@link #performTestCase21_AuthorizeUpdateByOriginalAuthorAndAdmin()},
 *       {@link #performTestCase22_RejectUpdateByUnauthorizedContributor()}</li>
 *   <li><b>TP2-REQ-10 (US-HW2-04 Delete Existing Lesson &amp; Subset Synchronization):</b>
 *       {@link #performTestCase23_DeleteValidLessonFromMasterAndSubset()},
 *       {@link #performTestCase24_DeleteNonExistentAndNegativeId()}</li>
 *   <li><b>TP2-REQ-11 (US-HW2-04 Delete Ownership &amp; Role Authorization):</b>
 *       {@link #performTestCase25_AuthorizeDeleteByOriginalAuthorAndAdmin()},
 *       {@link #performTestCase26_RejectDeleteByUnauthorizedContributor()}</li>
 *   <li><b>TP2-REQ-12 (TP3 Epics Forward-Compatible Governance Hooks):</b>
 *       {@link #performTestCase27_VerifyTP3ModerationEndorsementAndVerification()},
 *       {@link #performTestCase28_RejectTP3HooksOnNonExistentId()}</li>
 * </ul>
 * 
 * <p> Copyright: Lynn Robert Carter © 2026 </p>
 * 
 * @author Sanidhya Tiwari
 * @version 2.00 2026-10-23 TP2 Tasks 6 and 7 Testing Framework and Javadocs.
 */
public class ContributorTestingAutomation {

    /** Counter tracking the number of passed test cases during execution. */
    public static int numPassed = 0;

    /** Counter tracking the number of failed test cases during execution. */
    public static int numFailed = 0;

    /**
     * Default constructor.
     */
    public ContributorTestingAutomation() {
    }

    /*****
     * <p> Method: main(String[] args) </p>
     * 
     * <p> Description: Main driver method that initializes the test counters, executes all
     * 28 positive and negative test cases sequentially, prints detailed diagnostic trace output
     * for each test case, and summarizes total passed and failed counts. </p>
     * 
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        numPassed = 0;
        numFailed = 0;

        System.out.println("____________________________________________________________________________");
        System.out.println("\nContributor Testing Automation Testbed (TP2 Tasks 6 & 7)");
        System.out.println("____________________________________________________________________________\n");

        // TP2-REQ-01: Create Valid Lesson Learned & Reject Null/Duplicate IDs
        performTestCase01_CreateValidLesson();
        performTestCase02_CreateNullLesson();
        performTestCase03_CreateDuplicateIdLesson();

        // TP2-REQ-02: Title Input Boundary Validation (4-64 chars)
        performTestCase04_ValidateValidTitleBoundaries();
        performTestCase05_ValidateBlankAndShortTitle();
        performTestCase06_ValidateOversizedTitle();

        // TP2-REQ-03: Description Input Boundary Validation (10-1000 chars)
        performTestCase07_ValidateValidDescriptionBoundaries();
        performTestCase08_ValidateBlankAndShortDescription();
        performTestCase09_ValidateOversizedDescription();

        // TP2-REQ-04: Category and Author Input Validation
        performTestCase10_ValidateValidCategoryAndAuthor();
        performTestCase11_ValidateInvalidCategoryAndAuthor();

        // TP2-REQ-05: Read All Stored Lessons & Lookup by Primary Key ID
        performTestCase12_ReadAllAndFindExistingId();
        performTestCase13_ReadNonExistentIdAndUnmodifiableView();

        // TP2-REQ-06: Keyword Search Across Arbitrary Subset Sizes
        performTestCase14_SearchKeywordMultiAndSingleSubset();
        performTestCase15_SearchKeywordEmptySubset();
        performTestCase16_SearchKeywordBlankResetAndOversizedQuery();

        // TP2-REQ-07: Category Filter Operations
        performTestCase17_FilterCategoryMatchingAndAllReset();
        performTestCase18_FilterCategoryNoMatchEmptySubset();

        // TP2-REQ-08: Update Existing Lesson & Subset Synchronization
        performTestCase19_UpdateValidLessonAndSyncSubset();
        performTestCase20_UpdateInvalidFieldsAndNonExistentId();

        // TP2-REQ-09: Update Ownership & Role Authorization
        performTestCase21_AuthorizeUpdateByOriginalAuthorAndAdmin();
        performTestCase22_RejectUpdateByUnauthorizedContributor();

        // TP2-REQ-10: Delete Existing Lesson & Subset Synchronization
        performTestCase23_DeleteValidLessonFromMasterAndSubset();
        performTestCase24_DeleteNonExistentAndNegativeId();

        // TP2-REQ-11: Delete Ownership & Role Authorization
        performTestCase25_AuthorizeDeleteByOriginalAuthorAndAdmin();
        performTestCase26_RejectDeleteByUnauthorizedContributor();

        // TP2-REQ-12: TP3 Epics Forward-Compatible Governance Hooks
        performTestCase27_VerifyTP3ModerationEndorsementAndVerification();
        performTestCase28_RejectTP3HooksOnNonExistentId();

        System.out.println("____________________________________________________________________________");
        System.out.println("Number of tests passed: " + numPassed);
        System.out.println("Number of tests failed: " + numFailed);
        System.out.println("____________________________________________________________________________");
    }

    // =========================================================================
    // TP2-REQ-01: CREATE LESSON LEARNED & MASTER COLLECTION INSERTION
    // =========================================================================

    /*****
     * <p> Method: performTestCase01_CreateValidLesson() [POSITIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-01 (US-HW2-01 Acceptance Criterion e: Create and insert
     * a valid LessonLearned entity into the master collection). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Instantiates a
     * {@link LessonLearned} object with valid ID (101), title ("H2 Database Lock Fix"),
     * description ("Close active H2 connections in stop() to prevent lock file errors."),
     * author ("Sanidhya"), and category ("Database"), then invokes
     * {@link LessonLearnedList#addLesson(LessonLearned)} to store the entity in the synchronized
     * {@code allLessons} list. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-01 | TP2-REQ-01 | POSITIVE]} followed by the return value of {@code addLesson}
     * and the updated master count. The test passes if {@code addLesson} returns {@code true},
     * {@code getTotalCount()} equals {@code 1}, and the console outputs
     * {@code ***Success*** TC-01 Passed}; otherwise it outputs {@code ***Failure*** TC-01 Failed}. </p>
     */
    public static void performTestCase01_CreateValidLesson() {
        LessonLearnedList list = new LessonLearnedList();
        // Construct a valid LessonLearned instance satisfying all field length boundaries
        LessonLearned lesson = new LessonLearned(
                101,
                "H2 Database Lock Fix",
                "Close active H2 connections in stop() to prevent lock file errors.",
                "Sanidhya",
                "Database",
                1729700000000L
        );

        boolean added = list.addLesson(lesson);
        boolean passed = added && list.getTotalCount() == 1
                && "H2 Database Lock Fix".equals(list.getAllLessons().get(0).getTitle());

        recordResult(
                "TC-01",
                "TP2-REQ-01",
                "POSITIVE",
                "Create and add a valid LessonLearned entity to LessonLearnedList",
                "addLesson() == true, getTotalCount() == 1",
                "addLesson() == " + added + ", getTotalCount() == " + list.getTotalCount(),
                passed
        );
    }

    /*****
     * <p> Method: performTestCase02_CreateNullLesson() [NEGATIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-01 (US-HW2-01 Defensive null entity rejection during
     * Create operation). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Calls
     * {@link LessonLearnedList#addLesson(LessonLearned)} with a {@code null} reference. Inside
     * {@code addLesson}, the guard condition {@code if (lesson == null) return false;} intercepts
     * the null input before acquiring the collection lock, preventing null elements from corrupting
     * {@code allLessons}. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-02 | TP2-REQ-01 | NEGATIVE]}. The test passes if {@code addLesson(null)} returns
     * {@code false}, {@code getTotalCount()} remains {@code 0}, and the console outputs
     * {@code ***Success*** TC-02 Passed}. </p>
     */
    public static void performTestCase02_CreateNullLesson() {
        LessonLearnedList list = new LessonLearnedList();
        // Pass null to verify defensive guard blocks null insertion
        boolean added = list.addLesson(null);
        boolean passed = !added && list.getTotalCount() == 0;

        recordResult(
                "TC-02",
                "TP2-REQ-01",
                "NEGATIVE",
                "Reject null LessonLearned reference in addLesson(null)",
                "addLesson(null) == false, getTotalCount() == 0",
                "addLesson(null) == " + added + ", getTotalCount() == " + list.getTotalCount(),
                passed
        );
    }

    /*****
     * <p> Method: performTestCase03_CreateDuplicateIdLesson() [NEGATIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-01 (US-HW2-01 Primary key uniqueness enforcement
     * during Create operation). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Inserts an initial
     * {@link LessonLearned} with ID {@code 101}, then attempts to insert a second
     * {@link LessonLearned} sharing the same primary key ID {@code 101}. The guard
     * {@code if (lesson.getId() > 0 && findById(lesson.getId()).isPresent()) return false;}
     * detects the collision and rejects the duplicate entry. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-03 | TP2-REQ-01 | NEGATIVE]}. The test passes if the first insertion returns
     * {@code true}, the duplicate insertion returns {@code false}, {@code getTotalCount()} stays
     * {@code 1}, and the console displays {@code ***Success*** TC-03 Passed}. </p>
     */
    public static void performTestCase03_CreateDuplicateIdLesson() {
        LessonLearnedList list = new LessonLearnedList();
        LessonLearned first = new LessonLearned(101, "Initial Lesson Title", "Initial valid description text for lesson 101.", "Sanidhya", "Database", 1729700000000L);
        LessonLearned duplicate = new LessonLearned(101, "Duplicate ID Lesson", "Attempting to insert a second lesson with ID 101.", "Gauri", "GUI", 1729700005000L);

        boolean firstAdded = list.addLesson(first);
        // Attempt to insert a second record with the same primary key ID 101
        boolean secondAdded = list.addLesson(duplicate);
        boolean passed = firstAdded && !secondAdded && list.getTotalCount() == 1;

        recordResult(
                "TC-03",
                "TP2-REQ-01",
                "NEGATIVE",
                "Reject duplicate primary key ID #101 in addLesson()",
                "firstAdded == true, secondAdded == false, getTotalCount() == 1",
                "firstAdded == " + firstAdded + ", secondAdded == " + secondAdded + ", getTotalCount() == " + list.getTotalCount(),
                passed
        );
    }

    // =========================================================================
    // TP2-REQ-02: TITLE INPUT BOUNDARY VALIDATION (4-64 CHARS)
    // =========================================================================

    /*****
     * <p> Method: performTestCase04_ValidateValidTitleBoundaries() [POSITIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-02 (US-HW2-01 Title length boundary validation for
     * minimum 4 characters, typical length, and maximum 64 characters). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Invokes
     * {@link ContributorValidator#validateTitle(String)} with an exact 4-character title
     * ({@code "Java"}), a standard descriptive title, and an exact 64-character title.
     * {@code validateTitle} trims whitespace, checks {@code length >= 4 && length <= 64},
     * and returns an empty string {@code ""} when valid. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-04 | TP2-REQ-02 | POSITIVE]}. The test passes if all three boundary strings
     * return {@code ""} (empty error string) and the console outputs
     * {@code ***Success*** TC-04 Passed}. </p>
     */
    public static void performTestCase04_ValidateValidTitleBoundaries() {
        String minTitle = "Java"; // Exactly 4 characters (lower boundary)
        String midTitle = "Resolving JavaFX Module Path Configuration Errors";
        String maxTitle = "T".repeat(64); // Exactly 64 characters (upper boundary)

        String errMin = ContributorValidator.validateTitle(minTitle);
        String errMid = ContributorValidator.validateTitle(midTitle);
        String errMax = ContributorValidator.validateTitle(maxTitle);

        boolean passed = errMin.isEmpty() && errMid.isEmpty() && errMax.isEmpty();

        recordResult(
                "TC-04",
                "TP2-REQ-02",
                "POSITIVE",
                "Accept valid titles at 4-char lower bound, standard length, and 64-char upper bound",
                "errMin == '', errMid == '', errMax == ''",
                "errMin='" + errMin + "', errMid='" + errMid + "', errMax='" + errMax + "'",
                passed
        );
    }

    /*****
     * <p> Method: performTestCase05_ValidateBlankAndShortTitle() [NEGATIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-02 (US-HW2-01 Rejection of null, blank, and
     * under-length titles &lt; 4 characters). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Passes {@code "   "}
     * (whitespace-only) and {@code "Bug"} (3 characters) to
     * {@link ContributorValidator#validateTitle(String)}. The validator trims the input, detects
     * {@code isEmpty()} and {@code length < 4}, and returns context-relevant error messages. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-05 | TP2-REQ-02 | NEGATIVE]}. The test passes if both inputs return non-empty
     * diagnostic error strings containing {@code "cannot be blank"} and {@code "too short"},
     * and the console prints {@code ***Success*** TC-05 Passed}. </p>
     */
    public static void performTestCase05_ValidateBlankAndShortTitle() {
        String errBlank = ContributorValidator.validateTitle("   ");
        String errShort = ContributorValidator.validateTitle("Bug"); // 3 chars (< 4 min)

        boolean passed = errBlank.contains("cannot be blank") && errShort.contains("too short");

        recordResult(
                "TC-05",
                "TP2-REQ-02",
                "NEGATIVE",
                "Reject blank title ('   ') and 3-char title ('Bug')",
                "Non-empty error messages for blank and < 4 char title",
                "Blank->'" + errBlank + "' | Short->'" + errShort + "'",
                passed
        );
    }

    /*****
     * <p> Method: performTestCase06_ValidateOversizedTitle() [NEGATIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-02 (US-HW2-01 Rejection of titles exceeding the
     * 64-character upper boundary). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Constructs a 65-character
     * title string and passes it to {@link ContributorValidator#validateTitle(String)}. The condition
     * {@code if (length > 64)} triggers and returns an error message specifying that the title
     * must be between 4 and 64 characters. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-06 | TP2-REQ-02 | NEGATIVE]}. The test passes if the returned error string
     * contains {@code "too long"} and {@code "between 4 and 64"}, and the console displays
     * {@code ***Success*** TC-06 Passed}. </p>
     */
    public static void performTestCase06_ValidateOversizedTitle() {
        String oversizedTitle = "X".repeat(65); // 65 chars (> 64 max)
        String errOver = ContributorValidator.validateTitle(oversizedTitle);

        boolean passed = errOver.contains("too long") && errOver.contains("between 4 and 64");

        recordResult(
                "TC-06",
                "TP2-REQ-02",
                "NEGATIVE",
                "Reject 65-character title exceeding the 64-character upper limit",
                "Error message stating title is too long (65 chars)",
                "errOver='" + errOver + "'",
                passed
        );
    }

    // =========================================================================
    // TP2-REQ-03: DESCRIPTION INPUT BOUNDARY VALIDATION (10-1000 CHARS)
    // =========================================================================

    /*****
     * <p> Method: performTestCase07_ValidateValidDescriptionBoundaries() [POSITIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-03 (US-HW2-01 Description length boundary validation
     * for minimum 10 characters and maximum 1000 characters). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Invokes
     * {@link ContributorValidator#validateDescription(String)} with an exact 10-character string
     * ({@code "TenCharsOK"}) and an exact 1000-character string. Both satisfy
     * {@code length >= 10 && length <= 1000} and return {@code ""}. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-07 | TP2-REQ-03 | POSITIVE]}. The test passes if both boundary checks return
     * empty strings {@code ""} and the console outputs {@code ***Success*** TC-07 Passed}. </p>
     */
    public static void performTestCase07_ValidateValidDescriptionBoundaries() {
        String minDesc = "TenCharsOK"; // Exactly 10 characters
        String maxDesc = "D".repeat(1000); // Exactly 1000 characters

        String errMin = ContributorValidator.validateDescription(minDesc);
        String errMax = ContributorValidator.validateDescription(maxDesc);

        boolean passed = errMin.isEmpty() && errMax.isEmpty();

        recordResult(
                "TC-07",
                "TP2-REQ-03",
                "POSITIVE",
                "Accept valid descriptions at 10-char lower bound and 1000-char upper bound",
                "errMin == '', errMax == ''",
                "errMin='" + errMin + "', errMax='" + errMax + "'",
                passed
        );
    }

    /*****
     * <p> Method: performTestCase08_ValidateBlankAndShortDescription() [NEGATIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-03 (US-HW2-01 Rejection of empty and under-length
     * descriptions &lt; 10 characters). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Calls
     * {@link ContributorValidator#validateDescription(String)} with an empty string {@code ""}
     * and a 9-character string ({@code "ShortDesc"}). The validator catches both violations and
     * returns context-specific error messages. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-08 | TP2-REQ-03 | NEGATIVE]}. The test passes if {@code errBlank} contains
     * {@code "cannot be empty"} and {@code errShort} contains {@code "too short"}, producing
     * {@code ***Success*** TC-08 Passed}. </p>
     */
    public static void performTestCase08_ValidateBlankAndShortDescription() {
        String errBlank = ContributorValidator.validateDescription("");
        String errShort = ContributorValidator.validateDescription("ShortDesc"); // 9 chars (< 10 min)

        boolean passed = errBlank.contains("cannot be empty") && errShort.contains("too short");

        recordResult(
                "TC-08",
                "TP2-REQ-03",
                "NEGATIVE",
                "Reject empty description ('') and 9-char description ('ShortDesc')",
                "Error messages for empty and < 10 char description",
                "Blank->'" + errBlank + "' | Short->'" + errShort + "'",
                passed
        );
    }

    /*****
     * <p> Method: performTestCase09_ValidateOversizedDescription() [NEGATIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-03 (US-HW2-01 Rejection of descriptions exceeding
     * 1000 characters). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Passes a 1001-character
     * string to {@link ContributorValidator#validateDescription(String)}, triggering the
     * {@code length > 1000} boundary check. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-09 | TP2-REQ-03 | NEGATIVE]}. The test passes if the returned error string
     * contains {@code "too long"} and {@code "between 10 and 1000"}, and outputs
     * {@code ***Success*** TC-09 Passed}. </p>
     */
    public static void performTestCase09_ValidateOversizedDescription() {
        String oversizedDesc = "D".repeat(1001); // 1001 chars (> 1000 max)
        String errOver = ContributorValidator.validateDescription(oversizedDesc);

        boolean passed = errOver.contains("too long") && errOver.contains("between 10 and 1000");

        recordResult(
                "TC-09",
                "TP2-REQ-03",
                "NEGATIVE",
                "Reject 1001-character description exceeding the 1000-character limit",
                "Error message stating description is too long (1001 chars)",
                "errOver='" + errOver + "'",
                passed
        );
    }

    // =========================================================================
    // TP2-REQ-04: CATEGORY AND AUTHOR INPUT VALIDATION
    // =========================================================================

    /*****
     * <p> Method: performTestCase10_ValidateValidCategoryAndAuthor() [POSITIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-04 (US-HW2-01 Acceptance of approved technical
     * categories and valid alphanumeric contributor usernames). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Calls
     * {@link ContributorValidator#validateCategory(String)} with {@code "Architecture"} and
     * {@link ContributorValidator#validateAuthor(String)} with {@code "Sanidhya26"}, and verifies
     * composite validation via {@link ContributorValidator#validateLessonFields(String, String, String, String)}. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-10 | TP2-REQ-04 | POSITIVE]}. The test passes if all three calls return {@code ""}
     * and the console prints {@code ***Success*** TC-10 Passed}. </p>
     */
    public static void performTestCase10_ValidateValidCategoryAndAuthor() {
        String errCat = ContributorValidator.validateCategory("Architecture");
        String errAuth = ContributorValidator.validateAuthor("Sanidhya26");
        String errAll = ContributorValidator.validateLessonFields(
                "Singleton Pattern in JavaFX",
                "Ensure View classes instantiate once via display Home entry points.",
                "Sanidhya26",
                "Architecture"
        );

        boolean passed = errCat.isEmpty() && errAuth.isEmpty() && errAll.isEmpty();

        recordResult(
                "TC-10",
                "TP2-REQ-04",
                "POSITIVE",
                "Accept approved category ('Architecture') and valid author ('Sanidhya26')",
                "errCat == '', errAuth == '', errAll == ''",
                "errCat='" + errCat + "', errAuth='" + errAuth + "', errAll='" + errAll + "'",
                passed
        );
    }

    /*****
     * <p> Method: performTestCase11_ValidateInvalidCategoryAndAuthor() [NEGATIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-04 (US-HW2-01 Rejection of unselected/unapproved
     * categories and malformed contributor usernames). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Tests
     * {@link ContributorValidator#validateCategory(String)} with the default placeholder
     * {@code "Select Category"} and an unapproved category {@code "Astrology"}, and tests
     * {@link ContributorValidator#validateAuthor(String)} with a numeric-leading username
     * {@code "9User"} and blank string {@code ""}. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-11 | TP2-REQ-04 | NEGATIVE]}. The test passes if all four invalid inputs produce
     * non-empty diagnostic error messages and the console outputs {@code ***Success*** TC-11 Passed}. </p>
     */
    public static void performTestCase11_ValidateInvalidCategoryAndAuthor() {
        String errPlaceholder = ContributorValidator.validateCategory("Select Category");
        String errUnknownCat = ContributorValidator.validateCategory("Astrology");
        String errDigitAuthor = ContributorValidator.validateAuthor("9User");
        String errBlankAuthor = ContributorValidator.validateAuthor("");

        boolean passed = errPlaceholder.contains("Category is required")
                && errUnknownCat.contains("Invalid category")
                && errDigitAuthor.contains("start with an alphabetic")
                && errBlankAuthor.contains("cannot be blank");

        recordResult(
                "TC-11",
                "TP2-REQ-04",
                "NEGATIVE",
                "Reject placeholder/unknown categories and blank/digit-leading authors",
                "Specific error messages for each invalid category and author input",
                "Placeholder->'" + errPlaceholder + "' | DigitAuthor->'" + errDigitAuthor + "'",
                passed
        );
    }

    // =========================================================================
    // TP2-REQ-05: READ ALL STORED LESSONS & PRIMARY KEY ID LOOKUP
    // =========================================================================

    /*****
     * <p> Method: performTestCase12_ReadAllAndFindExistingId() [POSITIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-05 (US-HW2-02 Acceptance Criterion a: Retrieve
     * complete master collection and locate specific lesson by primary key ID). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Populates a
     * {@link LessonLearnedList} with 3 realistic course lessons, calls
     * {@link LessonLearnedList#getAllLessons()} to read the full collection, and invokes
     * {@link LessonLearnedList#findById(int)} for ID {@code 202}. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-12 | TP2-REQ-05 | POSITIVE]}. The test passes if {@code getAllLessons().size()}
     * equals {@code 3}, {@code findById(202).isPresent()} is {@code true} with matching title,
     * and the console outputs {@code ***Success*** TC-12 Passed}. </p>
     */
    public static void performTestCase12_ReadAllAndFindExistingId() {
        LessonLearnedList list = buildSampleLessonList();
        List<LessonLearned> all = list.getAllLessons();
        Optional<LessonLearned> found = list.findById(202);

        boolean passed = all.size() == 3 && found.isPresent()
                && "JavaFX VM Arguments Setup".equals(found.get().getTitle());

        recordResult(
                "TC-12",
                "TP2-REQ-05",
                "POSITIVE",
                "Retrieve all 3 lessons via getAllLessons() and locate ID #202 via findById()",
                "all.size() == 3, findById(202).isPresent() == true",
                "all.size() == " + all.size() + ", findById(202) == " + (found.isPresent() ? found.get().getTitle() : "EMPTY"),
                passed
        );
    }

    /*****
     * <p> Method: performTestCase13_ReadNonExistentIdAndUnmodifiableView() [NEGATIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-05 (US-HW2-02 Safe handling of non-existent primary
     * key lookups and protection of internal collection state against unvalidated external mutation). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Calls
     * {@link LessonLearnedList#findById(int)} with non-existent ID {@code 9999} (returning
     * {@code Optional.empty()}), and attempts to call {@code clear()} directly on the list view
     * returned by {@link LessonLearnedList#getAllLessons()}, which throws
     * {@link UnsupportedOperationException} due to {@code Collections.unmodifiableList}. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-13 | TP2-REQ-05 | NEGATIVE]}. The test passes if {@code findById(9999).isEmpty()}
     * is {@code true}, external mutation is blocked by {@code UnsupportedOperationException}, and
     * the console outputs {@code ***Success*** TC-13 Passed}. </p>
     */
    public static void performTestCase13_ReadNonExistentIdAndUnmodifiableView() {
        LessonLearnedList list = buildSampleLessonList();
        Optional<LessonLearned> missing = list.findById(9999);

        boolean unmodifiableBlocked = false;
        try {
            // Attempt unauthorized direct mutation bypassing LessonLearnedList CRUD methods
            list.getAllLessons().clear();
        } catch (UnsupportedOperationException ex) {
            unmodifiableBlocked = true;
        }

        boolean passed = missing.isEmpty() && unmodifiableBlocked && list.getTotalCount() == 3;

        recordResult(
                "TC-13",
                "TP2-REQ-05",
                "NEGATIVE",
                "Return Optional.empty() for non-existent ID #9999 and block direct getAllLessons().clear()",
                "findById(9999).isEmpty() == true, unmodifiableBlocked == true",
                "findById(9999).isEmpty() == " + missing.isEmpty() + ", unmodifiableBlocked == " + unmodifiableBlocked,
                passed
        );
    }

    // =========================================================================
    // TP2-REQ-06: KEYWORD SEARCH ACROSS ARBITRARY SUBSET SIZES
    // =========================================================================

    /*****
     * <p> Method: performTestCase14_SearchKeywordMultiAndSingleSubset() [POSITIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-06 (US-HW2-02 Acceptance Criteria b &amp; d:
     * Case-insensitive keyword search producing multi-item and single-item subsets). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Calls
     * {@link LessonLearnedList#searchByKeyword(String)} with {@code "javafx"} (matching 2 records
     * across title and description case-insensitively) and then with {@code "branch"} (matching
     * exactly 1 record), verifying {@link LessonLearnedList#getSubsetSize()} and
     * {@link LessonLearnedList#isSubsetEmpty()}. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-14 | TP2-REQ-06 | POSITIVE]}. The test passes if the first search yields subset
     * size {@code 2}, the second search yields subset size {@code 1}, and the console outputs
     * {@code ***Success*** TC-14 Passed}. </p>
     */
    public static void performTestCase14_SearchKeywordMultiAndSingleSubset() {
        LessonLearnedList list = buildSampleLessonList();

        // Add a fourth record mentioning JavaFX in description to test multi-match search
        list.addLesson(new LessonLearned(
                204,
                "MVC Singleton Scene Graph",
                "Reuse the JavaFX Scene singleton across role dispatch transitions.",
                "Junbo",
                "Architecture",
                1729700030000L
        ));

        List<LessonLearned> multiMatch = list.searchByKeyword("javafx");
        int multiReturnSize = multiMatch.size();
        int multiCount = list.getSubsetSize();

        List<LessonLearned> singleMatch = list.searchByKeyword("branch");
        int singleReturnSize = singleMatch.size();
        int singleCount = list.getSubsetSize();

        boolean passed = multiReturnSize == 2 && multiCount == 2
                && singleReturnSize == 1 && singleCount == 1 && !list.isSubsetEmpty();

        recordResult(
                "TC-14",
                "TP2-REQ-06",
                "POSITIVE",
                "Search keyword 'javafx' (multi-item subset=2) and 'branch' (single-item subset=1)",
                "multiCount == 2, singleCount == 1, isSubsetEmpty() == false",
                "multiCount == " + multiCount + ", singleCount == " + singleCount + ", isSubsetEmpty() == " + list.isSubsetEmpty(),
                passed
        );
    }

    /*****
     * <p> Method: performTestCase15_SearchKeywordEmptySubset() [NEGATIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-06 (US-HW2-02 Acceptance Criterion e: Graceful
     * handling of 0-match empty search subsets without null pointer exceptions). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Invokes
     * {@link LessonLearnedList#searchByKeyword(String)} with unmatched query
     * {@code "nonexistent_quantum_kernel"}. {@code searchByKeyword} populates {@code currentSubset}
     * with an empty list rather than returning {@code null}, and {@code isSubsetEmpty()} returns
     * {@code true}. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-15 | TP2-REQ-06 | NEGATIVE]}. The test passes if {@code getSubsetSize() == 0}
     * and {@code isSubsetEmpty() == true}, and outputs {@code ***Success*** TC-15 Passed}. </p>
     */
    public static void performTestCase15_SearchKeywordEmptySubset() {
        LessonLearnedList list = buildSampleLessonList();
        List<LessonLearned> noMatches = list.searchByKeyword("nonexistent_quantum_kernel");

        boolean passed = noMatches != null && noMatches.isEmpty()
                && list.getSubsetSize() == 0 && list.isSubsetEmpty();

        recordResult(
                "TC-15",
                "TP2-REQ-06",
                "NEGATIVE",
                "Search unmatched keyword 'nonexistent_quantum_kernel' returning 0-element empty subset",
                "getSubsetSize() == 0, isSubsetEmpty() == true",
                "getSubsetSize() == " + list.getSubsetSize() + ", isSubsetEmpty() == " + list.isSubsetEmpty(),
                passed
        );
    }

    /*****
     * <p> Method: performTestCase16_SearchKeywordBlankResetAndOversizedQuery() [NEGATIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-06 (US-HW2-02 Blank keyword reset behavior and
     * pre-query rejection of oversized search keywords &gt; 64 characters). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Calls
     * {@link ContributorValidator#validateSearchKeyword(String)} with a 65-character query string
     * to verify pre-query rejection, and calls {@link LessonLearnedList#searchByKeyword(String)}
     * with {@code "   "} to verify that blank search queries reset {@code currentSubset} to the
     * full master collection. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-16 | TP2-REQ-06 | NEGATIVE]}. The test passes if the 65-char query produces an
     * error message containing {@code "maximum length of 64"} and the blank search resets the
     * subset count to {@code 3}, outputting {@code ***Success*** TC-16 Passed}. </p>
     */
    public static void performTestCase16_SearchKeywordBlankResetAndOversizedQuery() {
        LessonLearnedList list = buildSampleLessonList();
        String errOverQuery = ContributorValidator.validateSearchKeyword("Q".repeat(65));
        List<LessonLearned> resetSubset = list.searchByKeyword("   ");

        boolean passed = errOverQuery.contains("maximum length of 64") && resetSubset.size() == 3;

        recordResult(
                "TC-16",
                "TP2-REQ-06",
                "NEGATIVE",
                "Reject 65-char search query via validateSearchKeyword() and reset subset on blank keyword",
                "errOverQuery contains 'maximum length of 64', resetSubset.size() == 3",
                "errOverQuery='" + errOverQuery + "', resetSubset.size() == " + resetSubset.size(),
                passed
        );
    }

    // =========================================================================
    // TP2-REQ-07: CATEGORY FILTER OPERATIONS
    // =========================================================================

    /*****
     * <p> Method: performTestCase17_FilterCategoryMatchingAndAllReset() [POSITIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-07 (US-HW2-02 Acceptance Criterion c: Filter
     * master collection by exact category match and reset with "All"). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Calls
     * {@link LessonLearnedList#filterByCategory(String)} with {@code "GUI"} to isolate GUI
     * lessons in {@code currentSubset}, and then with {@code "All"} to restore all 3 records. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-17 | TP2-REQ-07 | POSITIVE]}. The test passes if filtering by {@code "GUI"}
     * yields {@code 1} record and filtering by {@code "All"} yields {@code 3} records, printing
     * {@code ***Success*** TC-17 Passed}. </p>
     */
    public static void performTestCase17_FilterCategoryMatchingAndAllReset() {
        LessonLearnedList list = buildSampleLessonList();
        List<LessonLearned> guiOnly = list.filterByCategory("GUI");
        int guiCount = guiOnly.size();
        String guiTitle = guiCount > 0 ? guiOnly.get(0).getTitle() : "";

        List<LessonLearned> allRestored = list.filterByCategory("All");
        int allCount = allRestored.size();

        boolean passed = guiCount == 1 && "JavaFX VM Arguments Setup".equals(guiTitle)
                && allCount == 3;

        recordResult(
                "TC-17",
                "TP2-REQ-07",
                "POSITIVE",
                "Filter lessons by category 'GUI' (1 match) and reset filter with 'All' (3 matches)",
                "guiCount == 1, allCount == 3",
                "guiCount == " + guiCount + ", allCount == " + allCount,
                passed
        );
    }

    /*****
     * <p> Method: performTestCase18_FilterCategoryNoMatchEmptySubset() [NEGATIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-07 (US-HW2-02 Category filter with zero matching
     * records returning an empty subset). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Invokes
     * {@link LessonLearnedList#filterByCategory(String)} for category {@code "Testing"} when no
     * lessons in the sample list belong to {@code "Testing"}. {@code currentSubset} is updated
     * to an empty list and {@code isSubsetEmpty()} evaluates to {@code true}. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-18 | TP2-REQ-07 | NEGATIVE]}. The test passes if {@code getSubsetSize() == 0}
     * and {@code isSubsetEmpty() == true}, outputting {@code ***Success*** TC-18 Passed}. </p>
     */
    public static void performTestCase18_FilterCategoryNoMatchEmptySubset() {
        LessonLearnedList list = buildSampleLessonList();
        List<LessonLearned> emptyCat = list.filterByCategory("Testing");

        boolean passed = emptyCat.isEmpty() && list.getSubsetSize() == 0 && list.isSubsetEmpty();

        recordResult(
                "TC-18",
                "TP2-REQ-07",
                "NEGATIVE",
                "Filter by unpopulated category 'Testing' yielding an empty subset",
                "emptyCat.isEmpty() == true, getSubsetSize() == 0",
                "emptyCat.isEmpty() == " + emptyCat.isEmpty() + ", getSubsetSize() == " + list.getSubsetSize(),
                passed
        );
    }

    // =========================================================================
    // TP2-REQ-08: UPDATE EXISTING LESSON & SUBSET SYNCHRONIZATION
    // =========================================================================

    /*****
     * <p> Method: performTestCase19_UpdateValidLessonAndSyncSubset() [POSITIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-08 (US-HW2-03 Acceptance Criteria a, b, d: Update
     * mutable fields of an existing lesson and synchronize changes into active subset). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Filters the list so
     * lesson {@code 201} is present in {@code currentSubset}, then calls
     * {@link LessonLearnedList#updateLesson(int, String, String, String)} with valid new title,
     * description, and category. {@code updateLesson} validates the boundaries, mutates the
     * entity, and calls {@code refreshSubsetElement} to keep {@code currentSubset} synchronized. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-19 | TP2-REQ-08 | POSITIVE]}. The test passes if {@code updateLesson} returns
     * {@code true}, the updated title is reflected in both {@code findById(201)} and
     * {@code getCurrentSubset()}, and the console prints {@code ***Success*** TC-19 Passed}. </p>
     */
    public static void performTestCase19_UpdateValidLessonAndSyncSubset() {
        LessonLearnedList list = buildSampleLessonList();
        list.searchByKeyword("H2"); // Places Lesson #201 into currentSubset

        boolean updated = list.updateLesson(
                201,
                "H2 Lock File Resolution (Updated)",
                "Updated resolution: invoke closeConnection() inside FoundationsMain.stop().",
                "Database"
        );

        String masterTitle = list.findById(201).map(LessonLearned::getTitle).orElse("");
        String subsetTitle = list.getCurrentSubset().get(0).getTitle();

        boolean passed = updated
                && "H2 Lock File Resolution (Updated)".equals(masterTitle)
                && "H2 Lock File Resolution (Updated)".equals(subsetTitle);

        recordResult(
                "TC-19",
                "TP2-REQ-08",
                "POSITIVE",
                "Update Lesson #201 with valid fields and verify synchronization in currentSubset",
                "updated == true, masterTitle == subsetTitle == 'H2 Lock File Resolution (Updated)'",
                "updated == " + updated + ", masterTitle='" + masterTitle + "', subsetTitle='" + subsetTitle + "'",
                passed
        );
    }

    /*****
     * <p> Method: performTestCase20_UpdateInvalidFieldsAndNonExistentId() [NEGATIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-08 (US-HW2-03 Acceptance Criteria a &amp; c: Reject
     * updates with invalid field lengths or non-existent primary key IDs without mutating existing data). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Calls
     * {@link LessonLearnedList#updateLesson(int, String, String, String)} on existing ID {@code 201}
     * with a 2-character title ({@code "No"}), with a 5-character description ({@code "Short"}),
     * with an empty category ({@code "  "}), and on non-existent ID {@code 9999} with valid fields.
     * Each invalid call returns {@code false} and leaves Lesson {@code 201} untouched. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-20 | TP2-REQ-08 | NEGATIVE]}. The test passes if all four calls return
     * {@code false}, the original title of Lesson {@code 201} remains unchanged, and the console
     * outputs {@code ***Success*** TC-20 Passed}. </p>
     */
    public static void performTestCase20_UpdateInvalidFieldsAndNonExistentId() {
        LessonLearnedList list = buildSampleLessonList();
        String originalTitle = list.findById(201).get().getTitle();

        boolean badTitle = list.updateLesson(201, "No", "Valid description with sufficient length.", "Database");
        boolean badDesc = list.updateLesson(201, "Valid Title", "Short", "Database");
        boolean badCat = list.updateLesson(201, "Valid Title", "Valid description with sufficient length.", "   ");
        boolean badId = list.updateLesson(9999, "Valid Title", "Valid description with sufficient length.", "Database");

        String afterTitle = list.findById(201).get().getTitle();
        boolean passed = !badTitle && !badDesc && !badCat && !badId && originalTitle.equals(afterTitle);

        recordResult(
                "TC-20",
                "TP2-REQ-08",
                "NEGATIVE",
                "Reject updateLesson() on short title, short description, blank category, and non-existent ID #9999",
                "All 4 invalid update calls return false; original record unchanged",
                "badTitle=" + badTitle + ", badDesc=" + badDesc + ", badCat=" + badCat + ", badId=" + badId,
                passed
        );
    }

    // =========================================================================
    // TP2-REQ-09: UPDATE OWNERSHIP & ROLE AUTHORIZATION
    // =========================================================================

    /*****
     * <p> Method: performTestCase21_AuthorizeUpdateByOriginalAuthorAndAdmin() [POSITIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-09 (US-HW2-03 Verify that the original lesson
     * author and an Administrator are authorized to update a lesson). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Creates Lesson {@code 201}
     * authored by {@code "Sanidhya"}, then invokes
     * {@link ContributorValidator#verifyModifyPermission(LessonLearned, User)} for contributor
     * {@code "Sanidhya"} (matching author) and for admin {@code "AdminPranjal"} ({@code adminRole=true}).
     * Both return {@code ""} indicating permission granted. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-21 | TP2-REQ-09 | POSITIVE]}. The test passes if both permission checks return
     * {@code ""} and the console outputs {@code ***Success*** TC-21 Passed}. </p>
     */
    public static void performTestCase21_AuthorizeUpdateByOriginalAuthorAndAdmin() {
        LessonLearnedList list = buildSampleLessonList();
        LessonLearned lesson201 = list.findById(201).get(); // Authored by "Sanidhya"

        User authorUser = new User("Sanidhya", "ValidPass1!", "Sanidhya", "", "Tiwari", "Sanidhya", "stiwar60@asu.edu", false, true, false);
        User adminUser = new User("AdminPranjal", "ValidPass1!", "Pranjal", "", "Singh", "Pranjal", "psingh@asu.edu", true, false, false);

        String authorPerm = ContributorValidator.verifyModifyPermission(lesson201, authorUser);
        String adminPerm = ContributorValidator.verifyModifyPermission(lesson201, adminUser);

        boolean passed = authorPerm.isEmpty() && adminPerm.isEmpty();

        recordResult(
                "TC-21",
                "TP2-REQ-09",
                "POSITIVE",
                "Permit update when actingUser is original author ('Sanidhya') or Administrator ('AdminPranjal')",
                "authorPerm == '', adminPerm == ''",
                "authorPerm='" + authorPerm + "', adminPerm='" + adminPerm + "'",
                passed
        );
    }

    /*****
     * <p> Method: performTestCase22_RejectUpdateByUnauthorizedContributor() [NEGATIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-09 (US-HW2-03 Block unauthorized contributors from
     * modifying lessons authored by other team members). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Targets Lesson {@code 201}
     * authored by {@code "Sanidhya"} and invokes
     * {@link ContributorValidator#verifyModifyPermission(LessonLearned, User)} with non-admin
     * contributor {@code "PeerUserBob"}. Because {@code actingUser.getAdminRole()} is {@code false}
     * and the usernames do not match, an {@code "Unauthorized:"} error string is returned. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-22 | TP2-REQ-09 | NEGATIVE]}. The test passes if the returned string starts with
     * {@code "Unauthorized:"} and names both users, outputting {@code ***Success*** TC-22 Passed}. </p>
     */
    public static void performTestCase22_RejectUpdateByUnauthorizedContributor() {
        LessonLearnedList list = buildSampleLessonList();
        LessonLearned lesson201 = list.findById(201).get(); // Authored by "Sanidhya"

        User otherContributor = new User("PeerUserBob", "ValidPass1!", "Bob", "", "Smith", "Bob", "bob@asu.edu", false, true, false);
        String permError = ContributorValidator.verifyModifyPermission(lesson201, otherContributor);

        boolean passed = permError.startsWith("Unauthorized:") && permError.contains("PeerUserBob");

        recordResult(
                "TC-22",
                "TP2-REQ-09",
                "NEGATIVE",
                "Block unauthorized update on 'Sanidhya' lesson by non-admin contributor 'PeerUserBob'",
                "Error message starting with 'Unauthorized:'",
                "permError='" + permError + "'",
                passed
        );
    }

    // =========================================================================
    // TP2-REQ-10: DELETE EXISTING LESSON & SUBSET SYNCHRONIZATION
    // =========================================================================

    /*****
     * <p> Method: performTestCase23_DeleteValidLessonFromMasterAndSubset() [POSITIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-10 (US-HW2-04 Acceptance Criteria a &amp; b: Expel
     * a lesson by primary key ID from both the master collection and active subset). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Executes a search so
     * Lesson {@code 202} is inside {@code currentSubset}, then calls
     * {@link LessonLearnedList#deleteLesson(int)} with ID {@code 202}. {@code deleteLesson}
     * removes the matching record from both {@code allLessons} and {@code currentSubset}. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-23 | TP2-REQ-10 | POSITIVE]}. The test passes if {@code deleteLesson(202)}
     * returns {@code true}, {@code findById(202).isEmpty()} is {@code true}, {@code getTotalCount()}
     * drops from 3 to 2, and {@code getSubsetSize()} drops to 0, printing
     * {@code ***Success*** TC-23 Passed}. </p>
     */
    public static void performTestCase23_DeleteValidLessonFromMasterAndSubset() {
        LessonLearnedList list = buildSampleLessonList();
        list.searchByKeyword("JavaFX"); // Places Lesson #202 into currentSubset (size 1)

        boolean deleted = list.deleteLesson(202);
        boolean passed = deleted && list.findById(202).isEmpty()
                && list.getTotalCount() == 2 && list.getSubsetSize() == 0;

        recordResult(
                "TC-23",
                "TP2-REQ-10",
                "POSITIVE",
                "Delete Lesson #202 and verify removal from both allLessons and currentSubset",
                "deleted == true, findById(202).isEmpty() == true, totalCount == 2, subsetSize == 0",
                "deleted == " + deleted + ", totalCount == " + list.getTotalCount() + ", subsetSize == " + list.getSubsetSize(),
                passed
        );
    }

    /*****
     * <p> Method: performTestCase24_DeleteNonExistentAndNegativeId() [NEGATIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-10 (US-HW2-04 Acceptance Criterion c: Safe rejection
     * of delete requests targeting non-existent IDs or non-positive IDs &lt;= 0). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Invokes
     * {@link LessonLearnedList#deleteLesson(int)} with ID {@code 9999} (not present), ID {@code 0},
     * and ID {@code -5}. The safeguard {@code if (id <= 0) return false;} and
     * {@code removeIf} check ensure {@code false} is returned without altering the 3 stored records. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-24 | TP2-REQ-10 | NEGATIVE]}. The test passes if all three delete calls return
     * {@code false}, {@code getTotalCount()} remains {@code 3}, and the console outputs
     * {@code ***Success*** TC-24 Passed}. </p>
     */
    public static void performTestCase24_DeleteNonExistentAndNegativeId() {
        LessonLearnedList list = buildSampleLessonList();

        boolean delMissing = list.deleteLesson(9999);
        boolean delZero = list.deleteLesson(0);
        boolean delNegative = list.deleteLesson(-5);

        boolean passed = !delMissing && !delZero && !delNegative && list.getTotalCount() == 3;

        recordResult(
                "TC-24",
                "TP2-REQ-10",
                "NEGATIVE",
                "Reject deleteLesson() for non-existent ID #9999, zero ID #0, and negative ID #-5",
                "delMissing == false, delZero == false, delNegative == false, totalCount == 3",
                "delMissing=" + delMissing + ", delZero=" + delZero + ", delNegative=" + delNegative + ", totalCount=" + list.getTotalCount(),
                passed
        );
    }

    // =========================================================================
    // TP2-REQ-11: DELETE OWNERSHIP & ROLE AUTHORIZATION
    // =========================================================================

    /*****
     * <p> Method: performTestCase25_AuthorizeDeleteByOriginalAuthorAndAdmin() [POSITIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-11 (US-HW2-04 Verify that the original author and
     * an Administrator are authorized to delete a lesson learned). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Evaluates
     * {@link ContributorValidator#verifyModifyPermission(LessonLearned, User)} on Lesson {@code 202}
     * (authored by {@code "Gauri"}) for contributor {@code "Gauri"} and for an Administrator
     * account, then executes {@link LessonLearnedList#deleteLesson(int)}. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-25 | TP2-REQ-11 | POSITIVE]}. The test passes if both authorization checks return
     * {@code ""} and {@code deleteLesson(202)} succeeds, printing {@code ***Success*** TC-25 Passed}. </p>
     */
    public static void performTestCase25_AuthorizeDeleteByOriginalAuthorAndAdmin() {
        LessonLearnedList list = buildSampleLessonList();
        LessonLearned lesson202 = list.findById(202).get(); // Authored by "Gauri"

        User authorGauri = new User("Gauri", "ValidPass1!", "Gauri", "", "Sharma", "Gauri", "gauri@asu.edu", false, true, false);
        User adminUser = new User("AdminUser", "ValidPass1!", "Admin", "", "User", "Admin", "admin@asu.edu", true, false, false);

        String authCheck1 = ContributorValidator.verifyModifyPermission(lesson202, authorGauri);
        String authCheck2 = ContributorValidator.verifyModifyPermission(lesson202, adminUser);
        boolean deleted = authCheck1.isEmpty() && list.deleteLesson(202);

        boolean passed = authCheck1.isEmpty() && authCheck2.isEmpty() && deleted;

        recordResult(
                "TC-25",
                "TP2-REQ-11",
                "POSITIVE",
                "Authorize and execute deletion of Lesson #202 by author ('Gauri') and Administrator",
                "authCheck1 == '', authCheck2 == '', deleted == true",
                "authCheck1='" + authCheck1 + "', authCheck2='" + authCheck2 + "', deleted=" + deleted,
                passed
        );
    }

    /*****
     * <p> Method: performTestCase26_RejectDeleteByUnauthorizedContributor() [NEGATIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-11 (US-HW2-04 Block unauthorized contributors from
     * deleting lessons authored by another team member). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Checks permission via
     * {@link ContributorValidator#verifyModifyPermission(LessonLearned, User)} when contributor
     * {@code "Sanidhya"} (non-admin) attempts to delete Lesson {@code 202} authored by
     * {@code "Gauri"}, and also verifies rejection when {@code actingUser} is {@code null}. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-26 | TP2-REQ-11 | NEGATIVE]}. The test passes if both unauthorized delete checks
     * return non-empty error strings and Lesson {@code 202} remains in {@code list}, printing
     * {@code ***Success*** TC-26 Passed}. </p>
     */
    public static void performTestCase26_RejectDeleteByUnauthorizedContributor() {
        LessonLearnedList list = buildSampleLessonList();
        LessonLearned lesson202 = list.findById(202).get(); // Authored by "Gauri"

        User nonOwner = new User("Sanidhya", "ValidPass1!", "Sanidhya", "", "Tiwari", "Sanidhya", "stiwar60@asu.edu", false, true, false);
        String errPeer = ContributorValidator.verifyModifyPermission(lesson202, nonOwner);
        String errNullUser = ContributorValidator.verifyModifyPermission(lesson202, null);

        boolean passed = errPeer.startsWith("Unauthorized:")
                && errNullUser.contains("No authenticated user")
                && list.findById(202).isPresent();

        recordResult(
                "TC-26",
                "TP2-REQ-11",
                "NEGATIVE",
                "Block deletion of 'Gauri' lesson by non-owner 'Sanidhya' and null session user",
                "errPeer starts with 'Unauthorized:', findById(202).isPresent() == true",
                "errPeer='" + errPeer + "' | errNullUser='" + errNullUser + "'",
                passed
        );
    }

    // =========================================================================
    // TP2-REQ-12: TP3 EPICS FORWARD-COMPATIBLE GOVERNANCE HOOKS (RUBRIC 3.3)
    // =========================================================================

    /*****
     * <p> Method: performTestCase27_VerifyTP3ModerationEndorsementAndVerification() [POSITIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-12 (Rubric 3.3 Forward-compatible hooks for TP3
     * administrative moderation flags, peer endorsement counters, and staff verification). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Invokes
     * {@link LessonLearnedList#setModerationFlag(int, boolean, String)},
     * {@link LessonLearnedList#incrementEndorsement(int)} twice, and
     * {@link LessonLearnedList#setTechnicalVerification(int, boolean)} on Lesson {@code 201},
     * then inspects the mutated getters on {@link LessonLearned}. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-27 | TP2-REQ-12 | POSITIVE]}. The test passes if {@code isFlagged()} is
     * {@code true}, {@code getModerationNote()} equals {@code "Needs H2 version number"},
     * {@code getEndorsementCount()} equals {@code 2}, and {@code isVerified()} is {@code true},
     * outputting {@code ***Success*** TC-27 Passed}. </p>
     */
    public static void performTestCase27_VerifyTP3ModerationEndorsementAndVerification() {
        LessonLearnedList list = buildSampleLessonList();

        boolean flagOk = list.setModerationFlag(201, true, "  Needs H2 version number  ");
        boolean end1 = list.incrementEndorsement(201);
        boolean end2 = list.incrementEndorsement(201);
        boolean verOk = list.setTechnicalVerification(201, true);

        LessonLearned target = list.findById(201).get();
        boolean passed = flagOk && end1 && end2 && verOk
                && target.isFlagged()
                && "Needs H2 version number".equals(target.getModerationNote())
                && target.getEndorsementCount() == 2
                && target.isVerified();

        recordResult(
                "TC-27",
                "TP2-REQ-12",
                "POSITIVE",
                "Execute TP3 forward hooks: setModerationFlag, incrementEndorsement (x2), setTechnicalVerification",
                "isFlagged=true, note='Needs H2 version number', endorsements=2, isVerified=true",
                "isFlagged=" + target.isFlagged() + ", note='" + target.getModerationNote()
                        + "', endorsements=" + target.getEndorsementCount() + ", isVerified=" + target.isVerified(),
                passed
        );
    }

    /*****
     * <p> Method: performTestCase28_RejectTP3HooksOnNonExistentId() [NEGATIVE TEST] </p>
     * 
     * <p> <b>Requirements Tested:</b> TP2-REQ-12 (Rubric 3.3 Safe rejection when TP3 moderation,
     * endorsement, or verification hooks are invoked on a non-existent primary key ID). </p>
     * 
     * <p> <b>How the Code Implements Satisfaction of the Requirements:</b> Calls
     * {@link LessonLearnedList#setModerationFlag(int, boolean, String)},
     * {@link LessonLearnedList#incrementEndorsement(int)}, and
     * {@link LessonLearnedList#setTechnicalVerification(int, boolean)} with non-existent ID
     * {@code 9999}. Each method checks {@code findById(id).isPresent()} and returns {@code false}. </p>
     * 
     * <p> <b>How to Interpret the Output to Know if the Test Passed:</b> The console prints
     * {@code [TC-28 | TP2-REQ-12 | NEGATIVE]}. The test passes if all three calls return
     * {@code false} and the console outputs {@code ***Success*** TC-28 Passed}. </p>
     */
    public static void performTestCase28_RejectTP3HooksOnNonExistentId() {
        LessonLearnedList list = buildSampleLessonList();

        boolean flagMissing = list.setModerationFlag(9999, true, "Audit note");
        boolean endMissing = list.incrementEndorsement(9999);
        boolean verMissing = list.setTechnicalVerification(9999, true);

        boolean passed = !flagMissing && !endMissing && !verMissing;

        recordResult(
                "TC-28",
                "TP2-REQ-12",
                "NEGATIVE",
                "Reject TP3 moderation, endorsement, and verification hooks on non-existent ID #9999",
                "flagMissing == false, endMissing == false, verMissing == false",
                "flagMissing=" + flagMissing + ", endMissing=" + endMissing + ", verMissing=" + verMissing,
                passed
        );
    }

    // =========================================================================
    // HELPER METHODS FOR TEST FIXTURES AND RESULT REPORTING
    // =========================================================================

    /*****
     * <p> Method: buildSampleLessonList() </p>
     * 
     * <p> Description: Constructs a standardized 3-item {@link LessonLearnedList} fixture
     * populated with realistic CSE 360 course lessons for repeatable test execution. </p>
     * 
     * @return populated LessonLearnedList containing lessons #201, #202, and #203
     */
    private static LessonLearnedList buildSampleLessonList() {
        LessonLearnedList list = new LessonLearnedList();
        list.addLesson(new LessonLearned(
                201,
                "H2 Lock File Resolution",
                "Close active H2 database connections before restarting the desktop application.",
                "Sanidhya",
                "Database",
                1729700001000L
        ));
        list.addLesson(new LessonLearned(
                202,
                "JavaFX VM Arguments Setup",
                "Configure module-path and add-modules javafx.controls,javafx.fxml in Eclipse Run Configurations.",
                "Gauri",
                "GUI",
                1729700010000L
        ));
        list.addLesson(new LessonLearned(
                203,
                "Git Feature Branch Workflow",
                "Commit role-specific changes on a dedicated feature branch before merging into dev-phase2.",
                "Pranjal",
                "Version Control",
                1729700020000L
        ));
        return list;
    }

    /*****
     * <p> Method: recordResult(String testId, String reqId, String polarity, String description,
     *                          String expected, String actual, boolean passed) </p>
     * 
     * <p> Description: Formats and prints the outcome of an individual test case to standard
     * output and increments {@link #numPassed} or {@link #numFailed}. </p>
     * 
     * @param testId unique test case identifier (TC-01 through TC-28)
     * @param reqId requirement identifier tested (TP2-REQ-01 through TP2-REQ-12)
     * @param polarity POSITIVE or NEGATIVE test indicator
     * @param description concise statement of the test objective
     * @param expected summary of expected output state
     * @param actual summary of actual observed output state
     * @param passed true if assertions succeeded, false otherwise
     */
    private static void recordResult(String testId, String reqId, String polarity, String description,
                                     String expected, String actual, boolean passed) {
        System.out.println("[" + testId + " | " + reqId + " | " + polarity + "] " + description);
        System.out.println("   Expected: " + expected);
        System.out.println("   Actual:   " + actual);
        if (passed) {
            numPassed++;
            System.out.println("   Result:   ***Success*** " + testId + " Passed\n");
        } else {
            numFailed++;
            System.out.println("   Result:   ***Failure*** " + testId + " Failed\n");
        }
    }
}
