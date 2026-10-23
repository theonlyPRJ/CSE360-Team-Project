package entityClasses;

/*******
 * <p> Title: LessonLearned Entity Class. </p>
 * 
 * <p> Description: Encapsulates all data attributes for an individual Lesson Learned.
 * Satisfies Contributor Phase Two User Stories (US-HW2-01 through US-HW2-04) and 
 * provides forward compatibility for TP3 Administrator moderation, peer review 
 * endorsements, and staff verification epics. </p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Pranjal Singh
 * @author Sanidhya Tiwari
 * @author Gauri
 * @author Junbo You
 * 
 * @version 2.00    2026-10-22 Clean TP2 Phase Two Entity with TP3 forward attributes.
 */
public class LessonLearned {

    // =========================================================================
    // ATTRIBUTES (Phase Two Contributor Core + TP3 Epics Forward Compatibility)
    // =========================================================================

    /** Primary key unique identifier in persistent relational database. */
    private int id;

    /** Concise summary title of the lesson learned (4-64 characters). */
    private String title;

    /** Comprehensive descriptive body of the lesson (10-1000 characters). */
    private String description;

    /** Username of the contributing team member author. */
    private String author;

    /** Functional technical category/domain (e.g., GUI, Architecture, Database). */
    private String category;

    /** Creation timestamp in epoch milliseconds. */
    private long createdAt;

    // --- Forward-Compatible Attributes for TP3 Epics (Rubric 3.3) ---

    /** Flag indicating administrative review/moderation requirement. */
    private boolean isFlagged;

    /** Administrative explanation note justifying moderation or flag. */
    private String moderationNote = "";

    /** Aggregate count of community peer endorsements. */
    private int endorsementCount = 0;

    /** Status indicating senior staff/curator verification. */
    private boolean isVerified;

    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================

    /**
     * Default constructor initializing default attribute states.
     */
    public LessonLearned() {
        this.id = -1;
        this.title = "";
        this.description = "";
        this.author = "";
        this.category = "";
        this.createdAt = System.currentTimeMillis();
        this.isFlagged = false;
        this.moderationNote = "";
        this.endorsementCount = 0;
        this.isVerified = false;
    }

    /**
     * Parameterized constructor for new lesson creation before persistent database insertion.
     * 
     * @param title concise title (4 to 64 chars)
     * @param description comprehensive descriptive body (10 to 1000 chars)
     * @param author contributor username
     * @param category functional category identifier
     */
    public LessonLearned(String title, String description, String author, String category) {
        this(-1, title, description, author, category, System.currentTimeMillis());
    }

    /**
     * Full parameterized constructor for reconstructing persistent records from H2 database queries.
     * 
     * @param id primary key identifier
     * @param title concise title
     * @param description descriptive body
     * @param author contributor username
     * @param category functional category identifier
     * @param createdAt creation timestamp in milliseconds
     */
    public LessonLearned(int id, String title, String description, String author, String category, long createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.category = category;
        this.createdAt = createdAt;
        this.isFlagged = false;
        this.moderationNote = "";
        this.endorsementCount = 0;
        this.isVerified = false;
    }

    // =========================================================================
    // GETTERS AND SETTERS
    // =========================================================================

    /**
     * Returns the primary key unique identifier.
     * 
     * @return integer record id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the primary key unique identifier.
     * 
     * @param id integer record id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the concise title string.
     * 
     * @return title string
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the concise title string.
     * 
     * @param title title string (4 to 64 chars)
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the descriptive body text.
     * 
     * @return description body text
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the descriptive body text.
     * 
     * @param description description string (10 to 1000 chars)
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the author username.
     * 
     * @return author username string
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author username.
     * 
     * @param author author username string
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Returns the functional category identifier.
     * 
     * @return category string
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the functional category identifier.
     * 
     * @param category category identifier string
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Returns the creation timestamp in milliseconds.
     * 
     * @return epoch timestamp in milliseconds
     */
    public long getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp in milliseconds.
     * 
     * @param createdAt epoch timestamp in milliseconds
     */
    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    // --- TP3 Forward-Compatible Getters and Setters (Rubric 3.3) ---

    /**
     * Evaluates whether this record has been flagged for administrative review.
     * 
     * @return true if flagged, false otherwise
     */
    public boolean isFlagged() {
        return isFlagged;
    }
    
    /**
     * Evaluates whether this lesson has been officially verified by staff/curators.
     * 
     * @return true if verified, false otherwise
     */
    public boolean isVerified() {
        return isVerified;
    }
    
    /**
     * Returns the administrative moderation explanation note.
     * 
     * @return moderation note string
     */
    public String getModerationNote() {
        return moderationNote;
    }

    /**
     * Returns the aggregate count of community peer endorsements.
     * 
     * @return peer endorsement counter value
     */
    public int getEndorsementCount() {
        return endorsementCount;
    }
    
    /**
     * Sets the administrative review flag on this record.
     * 
     * @param isFlagged boolean flag state
     */
    public void setFlagged(boolean isFlagged) {
        this.isFlagged = isFlagged;
    }

    /**
     * Sets the administrative moderation explanation note.
     * 
     * @param moderationNote moderation explanation string
     */
    public void setModerationNote(String moderationNote) {
        this.moderationNote = moderationNote;
    }

    /**
     * Sets the aggregate count of community peer endorsements.
     * 
     * @param endorsementCount integer counter value
     */
    public void setEndorsementCount(int endorsementCount) {
        this.endorsementCount = endorsementCount;
    }

    /**
     * Sets the staff technical verification flag.
     * 
     * @param isVerified boolean verification state
     */
    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }
}