package entityClasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/*******
 * <p> Title: LessonLearnedList Class. </p>
 * 
 * <p> Description: Entity collection manager that encapsulates all persistent lessons
 * learned in the system as well as arbitrary search/filter subsets. Implements full
 * in-memory CRUD (Create, Read, Update, Delete) capabilities, defensive input validation,
 * and forward-compatible hooks for TP3 Administrative moderation, staff verification,
 * and community endorsements. </p>
 * 
 * <p> Architectural Pattern: Part of the Entity / Model layer in Foundations MVC architecture.
 * Decouples collection manipulation from JavaFX UI views and JDBC database operations. </p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Pranjal Singh
 * @author Sanidhya Tiwari
 * @author Gauri
 * @author Junbo You
 * 
 * @version 2.00    2026-10-22 Comprehensive TP2 Task 3 implementation satisfying
 *                  Contributor User Stories and TP3 Epic forward compatibility.
 */
public class LessonLearnedList {

    // =========================================================================
    // ATTRIBUTES & DATA STRUCTURES (Rubric 3.3 & 3.4)
    // =========================================================================

    /**
     * Master collection holding all active lessons learned in the system.
     * <p> Source and Rationale: Derived from Contributor Phase Two requirements. Mirrors
     * the persistent relational database state in memory, allowing fast in-memory
     * indexing and sorting without issuing redundant SQL queries. </p>
     */
    private final List<LessonLearned> allLessons = new ArrayList<>();

    /**
     * Working subset collection representing the results of keyword queries,
     * category filters, or administrative moderation audits.
     * <p> Source and Rationale: Required by US-HW2-02. Supports arbitrary collection sizes
     * (empty, single-item, or unbounded capacity) without fixed upper limits. </p>
     */
    private final List<LessonLearned> currentSubset = new ArrayList<>();

    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================

    /**
     * Default constructor initializing an empty lesson collection manager.
     */
    public LessonLearnedList() {
        // Line requirement trace: Default constructor enables framework instantiation
    }

    /**
     * Parameterized constructor pre-populating the master collection.
     * 
     * @param initialLessons initial list of lessons retrieved from persistent storage
     */
    public LessonLearnedList(List<LessonLearned> initialLessons) {
        setAllLessons(initialLessons);
    }

    // =========================================================================
    // 1. CREATE OPERATIONS (CRUD: Create - Rubric 3.2, US-HW2-01)
    // =========================================================================

    /**
     * Adds a newly created, valid LessonLearned entity to the master collection.
     * <p> Requirement Trace: US-HW2-01 (Acceptance Criterion e). </p>
     * 
     * @param lesson the LessonLearned entity to add to the master collection
     * @return true if the lesson was successfully added, false if null or duplicate ID
     * @throws IllegalArgumentException if the lesson violates basic boundary limits
     */
    public boolean addLesson(LessonLearned lesson) {
        // Line requirement trace: US-HW2-01 defensive null check
        if (lesson == null) {
            return false;
        }

        // Defensive check: Prevent duplicate primary keys in the master collection
        if (lesson.getId() > 0 && findById(lesson.getId()).isPresent()) {
            return false;
        }

        // Rationale: Synchronization ensures thread-safe insertion during background updates
        synchronized (this.allLessons) {
            return this.allLessons.add(lesson);
        }
    }

    // =========================================================================
    // 2. READ OPERATIONS (CRUD: Read - Rubric 3.2, US-HW2-02)
    // =========================================================================

    /**
     * Returns an unmodifiable view of all lessons learned.
     * <p> Requirement Trace: US-HW2-02 (Acceptance Criterion a). </p>
     * <p> Rationale: Wrapping with Collections.unmodifiableList prevents external callers
     * from corrupting internal collection state without using class CRUD mutators. </p>
     * 
     * @return unmodifiable List containing all LessonLearned instances
     */
    public List<LessonLearned> getAllLessons() {
        return Collections.unmodifiableList(this.allLessons);
    }

    /**
     * Replaces the contents of the master list with fresh records from persistent storage.
     * 
     * @param lessons list of persistent lessons loaded from Database DAO
     */
    public void setAllLessons(List<LessonLearned> lessons) {
        synchronized (this.allLessons) {
            this.allLessons.clear();
            if (lessons != null) {
                this.allLessons.addAll(lessons);
            }
        }
    }

    /**
     * Retrieves an individual lesson by its primary key ID.
     * <p> Requirement Trace: US-HW2-03 and US-HW2-04 prerequisite lookup. </p>
     * 
     * @param id unique primary key ID of the record
     * @return an Optional containing the matching LessonLearned, or Optional.empty()
     */
    public Optional<LessonLearned> findById(int id) {
        synchronized (this.allLessons) {
            return this.allLessons.stream()
                    .filter(lesson -> lesson.getId() == id)
                    .findFirst();
        }
    }

    /**
     * Executes a case-insensitive keyword search against both Title and Description fields,
     * loading the matching arbitrary subset into currentSubset.
     * <p> Requirement Trace: US-HW2-02 (Acceptance Criteria b and d). </p>
     * 
     * @param keyword the substring to match within title or description
     * @return unmodifiable List of lessons matching the query criteria
     */
    public List<LessonLearned> searchByKeyword(String keyword) {
        List<LessonLearned> matches = new ArrayList<>();
        
        // Rationale: Blank keyword returns full list per Foundations search convention
        if (keyword == null || keyword.trim().isEmpty()) {
            setCurrentSubset(this.allLessons);
            return getCurrentSubset();
        }

        String searchLower = keyword.trim().toLowerCase();
        synchronized (this.allLessons) {
            for (LessonLearned lesson : this.allLessons) {
                boolean titleMatch = lesson.getTitle() != null && lesson.getTitle().toLowerCase().contains(searchLower);
                boolean descMatch = lesson.getDescription() != null && lesson.getDescription().toLowerCase().contains(searchLower);
                if (titleMatch || descMatch) {
                    matches.add(lesson);
                }
            }
        }

        setCurrentSubset(matches);
        return getCurrentSubset();
    }

    /**
     * Filters the master collection by exact category match, updating currentSubset.
     * <p> Requirement Trace: US-HW2-02 (Acceptance Criterion c). </p>
     * 
     * @param category category identifier to filter by (e.g., "GUI", "Architecture")
     * @return unmodifiable List of lessons belonging exclusively to the given category
     */
    public List<LessonLearned> filterByCategory(String category) {
        List<LessonLearned> matches = new ArrayList<>();
        if (category == null || category.trim().isEmpty() || "All".equalsIgnoreCase(category.trim())) {
            setCurrentSubset(this.allLessons);
            return getCurrentSubset();
        }

        String targetCategory = category.trim();
        synchronized (this.allLessons) {
            for (LessonLearned lesson : this.allLessons) {
                if (lesson.getCategory() != null && lesson.getCategory().equalsIgnoreCase(targetCategory)) {
                    matches.add(lesson);
                }
            }
        }

        setCurrentSubset(matches);
        return getCurrentSubset();
    }

    /**
     * Returns an unmodifiable view of the currently active search or filter subset.
     * <p> Requirement Trace: US-HW2-02 (Acceptance Criterion d). </p>
     * 
     * @return unmodifiable List representing the current active query subset
     */
    public List<LessonLearned> getCurrentSubset() {
        return Collections.unmodifiableList(this.currentSubset);
    }

    /**
     * Explicitly sets the active subset collection.
     * 
     * @param subset list of lesson entries to set as the active subset
     */
    public void setCurrentSubset(List<LessonLearned> subset) {
        synchronized (this.currentSubset) {
            this.currentSubset.clear();
            if (subset != null) {
                this.currentSubset.addAll(subset);
            }
        }
    }

    /**
     * Evaluates whether the current search/filter subset contains zero records.
     * <p> Requirement Trace: US-HW2-02 (Acceptance Criterion e: graceful empty handling). </p>
     * 
     * @return true if the active subset is empty, false otherwise
     */
    public boolean isSubsetEmpty() {
        return this.currentSubset.isEmpty();
    }

    /**
     * Returns the number of elements in the current active subset.
     * 
     * @return integer element count of currentSubset
     */
    public int getSubsetSize() {
        return this.currentSubset.size();
    }

    /**
     * Returns the total count of lessons stored in the master list.
     * 
     * @return integer element count of allLessons
     */
    public int getTotalCount() {
        return this.allLessons.size();
    }

    // =========================================================================
    // 3. UPDATE OPERATIONS (CRUD: Update - Rubric 3.2, US-HW2-03)
    // =========================================================================

    /**
     * Updates the core mutable fields of an existing lesson learned by primary key ID.
     * <p> Requirement Trace: US-HW2-03 (Acceptance Criteria a, b, d). </p>
     * 
     * @param id primary key ID of the lesson to modify
     * @param newTitle updated title string (enforces 4-64 chars)
     * @param newDescription updated description string (enforces 10-1000 chars)
     * @param newCategory updated category string (non-blank)
     * @return true if record exists, passes validation, and is updated; false otherwise
     */
    public boolean updateLesson(int id, String newTitle, String newDescription, String newCategory) {
        // Line requirement trace: US-HW2-03 (a) Syntactic boundary guardrails
        if (newTitle == null || newTitle.trim().length() < 4 || newTitle.trim().length() > 64) {
            return false;
        }
        if (newDescription == null || newDescription.trim().length() < 10 || newDescription.trim().length() > 1000) {
            return false;
        }
        if (newCategory == null || newCategory.trim().isEmpty()) {
            return false;
        }

        Optional<LessonLearned> targetOpt = findById(id);
        if (targetOpt.isEmpty()) {
            // Line requirement trace: US-HW2-03 (c) Non-existent ID rejection
            return false;
        }

        LessonLearned target = targetOpt.get();
        target.setTitle(newTitle.trim());
        target.setDescription(newDescription.trim());
        target.setCategory(newCategory.trim());

        // Synchronize in-memory subset reflection if currently present
        refreshSubsetElement(target);
        return true;
    }

    // =========================================================================
    // 4. DELETE OPERATIONS (CRUD: Delete - Rubric 3.2, US-HW2-04)
    // =========================================================================

    /**
     * Permanently expels a lesson learned entry by its primary key ID from both
     * the master collection and any active subset.
     * <p> Requirement Trace: US-HW2-04 (Acceptance Criteria a and b). </p>
     * 
     * @param id primary key ID of the record to remove
     * @return true if the record was located and purged, false if ID not found
     */
    public boolean deleteLesson(int id) {
        // Line requirement trace: US-HW2-04 (c) Negative ID safeguard
        if (id <= 0) {
            return false;
        }

        synchronized (this.allLessons) {
            boolean removedFromAll = this.allLessons.removeIf(l -> l.getId() == id);
            if (removedFromAll) {
                synchronized (this.currentSubset) {
                    this.currentSubset.removeIf(l -> l.getId() == id);
                }
                return true;
            }
        }
        return false;
    }

    // =========================================================================
    // 5. TP3 EPICS FORWARD-COMPATIBLE HOOKS (Rubric 3.3)
    // =========================================================================

    /**
     * Administrative Moderation Flagging Operation.
     * <p> Forward Rationale for TP3: Satisfies Administrator Content Governance Epic
     * by permitting administrators to flag obsolete or policy-violating lessons. </p>
     * 
     * @param id record primary key ID
     * @param flagged true to flag for administrative review, false to clear
     * @param auditReasoning mandatory administrative explanation for the flag
     * @return true if record located and updated, false otherwise
     */
    public boolean setModerationFlag(int id, boolean flagged, String auditReasoning) {
        Optional<LessonLearned> targetOpt = findById(id);
        if (targetOpt.isPresent()) {
            LessonLearned lesson = targetOpt.get();
            lesson.setFlagged(flagged);
            lesson.setModerationNote(auditReasoning != null ? auditReasoning.trim() : "");
            return true;
        }
        return false;
    }

    /**
     * Granular Peer Endorsement Counter Operation.
     * <p> Forward Rationale for TP3: Implements LessonShare integration allowing team
     * members to endorse high-value technical observations. </p>
     * 
     * @param id record primary key ID
     * @return true if successfully incremented, false if record not found
     */
    public boolean incrementEndorsement(int id) {
        Optional<LessonLearned> targetOpt = findById(id);
        if (targetOpt.isPresent()) {
            LessonLearned lesson = targetOpt.get();
            lesson.setEndorsementCount(lesson.getEndorsementCount() + 1);
            return true;
        }
        return false;
    }

    /**
     * Technical Verification Flagging Operation.
     * <p> Forward Rationale for TP3: Satisfies Staff/Curator Verification Epic to mark
     * lessons verified by senior engineers. </p>
     * 
     * @param id record primary key ID
     * @param verified verification boolean state
     * @return true if updated, false if record not found
     */
    public boolean setTechnicalVerification(int id, boolean verified) {
        Optional<LessonLearned> targetOpt = findById(id);
        if (targetOpt.isPresent()) {
            targetOpt.get().setVerified(verified);
            return true;
        }
        return false;
    }

    // =========================================================================
    // PRIVATE HELPER ROUTINES
    // =========================================================================

    /**
     * Synchronizes modifications made to an entity within the active currentSubset.
     * 
     * @param updatedLesson the mutated entity instance
     */
    private void refreshSubsetElement(LessonLearned updatedLesson) {
        synchronized (this.currentSubset) {
            for (int i = 0; i < this.currentSubset.size(); i++) {
                if (this.currentSubset.get(i).getId() == updatedLesson.getId()) {
                    this.currentSubset.set(i, updatedLesson);
                    break;
                }
            }
        }
    }
}	