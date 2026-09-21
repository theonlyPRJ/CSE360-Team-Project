package entityClasses;

/*******
 * <p> Title: User Class </p>
 * 
 * <p> Description: This User class represents a user entity in the system. It contains the user's
 * details such as userName, password, roles being played, and One-Time Password (OTP) state. </p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 */ 

public class User {
    
    /*
     * These are the private attributes for this entity object
     */
    private String userName;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String preferredFirstName;
    private String emailAddress;
    private boolean adminRole;
    private boolean role1;
    private boolean role2;
    private String oneTimePassword = null;
    private boolean isOtpActive = false;
    
    /*****
     * <p> Method: User() </p>
     * 
     * <p> Description: This default constructor is not used in this system. </p>
     */
    public User() {
        
    }

    /*****
     * <p> Method: User(String userName, String password, String fn, String mn, String ln, String pfn,
     *      String ea, boolean r1, boolean r2, boolean r3) </p>
     * 
     * <p> Description: This constructor is used to establish user entity objects. </p>
     * 
     * @param userName specifies the account userName for this user
     * @param password specifies the account password for this user
     * @param fn specifies the first name
     * @param mn specifies the middle name
     * @param ln specifies the last name
     * @param pfn specifies the preferred first name
     * @param ea specifies the email address
     * @param r1 specifies the Admin attribute (TRUE or FALSE) for this user
     * @param r2 specifies the Role1 / Contributor attribute (TRUE or FALSE) for this user
     * @param r3 specifies the Role2 / Viewer attribute (TRUE or FALSE) for this user
     */
    public User(String userName, String password, String fn, String mn, String ln, String pfn, 
            String ea, boolean r1, boolean r2, boolean r3) {
        this.userName = userName;
        this.password = password;
        this.firstName = fn;
        this.middleName = mn;
        this.lastName = ln;
        this.preferredFirstName = pfn;
        this.emailAddress = ea;
        this.adminRole = r1;
        this.role1 = r2;
        this.role2 = r3;
    }

    /*****
     * <p> Method: void setAdminRole(boolean role) </p>
     * 
     * <p> Description: This setter defines the Admin role attribute. </p>
     * 
     * @param role is a boolean that specifies if this user in playing the Admin role.
     */
    public void setAdminRole(boolean role) {
        this.adminRole = role;
    }

    /*****
     * <p> Method: void setRole1User(boolean role) </p>
     * 
     * <p> Description: This setter defines the role1 attribute. </p>
     * 
     * @param role is a boolean that specifies if this user in playing role1.
     */
    public void setRole1User(boolean role) {
        this.role1 = role;
    }

    // Setter for Contributor role alias
    public void setContributorRole(boolean role) {
        this.role1 = role;
    }

    /*****
     * <p> Method: void setRole2User(boolean role) </p>
     * 
     * <p> Description: This setter defines the role2 attribute. </p>
     * 
     * @param role is a boolean that specifies if this user in playing role2.
     */
    public void setRole2User(boolean role) {
        this.role2 = role;
    }

    // Setter for Viewer role alias
    public void setViewerRole(boolean role) {
        this.role2 = role;
    }

    // One-Time Password (OTP) Setters and Controls
    public void setOneTimePassword(String otp) { 
        this.oneTimePassword = otp; 
        this.isOtpActive = true; 
    }

    public void clearOtp() { 
        this.oneTimePassword = null; 
        this.isOtpActive = false; 
    }

    /*****
     * Standard Field Getters and Setters
     *****/
    public String getUserName() { return userName; }
    public String getPassword() { return password; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getLastName() { return lastName; }
    public String getPreferredFirstName() { return preferredFirstName; }
    public String getEmailAddress() { return emailAddress; }
    public String getOneTimePassword() { return oneTimePassword; }
    public boolean isOtpActive() { return isOtpActive; }

    public void setUserName(String s) { userName = s; }
    public void setPassword(String s) { password = s; }
    public void setFirstName(String s) { firstName = s; }
    public void setMiddleName(String s) { middleName = s; }
    public void setLastName(String s) { lastName = s; }
    public void setPreferredFirstName(String s) { preferredFirstName = s; }
    public void setEmailAddress(String s) { emailAddress = s; }

    /*****
     * Role Getters & Helpers
     *****/
    public boolean getAdminRole() { return adminRole; }
    public boolean getNewRole1() { return role1; }
    public boolean getContributorRole() { return role1; }
    public boolean getNewRole2() { return role2; }
    public boolean getViewerRole() { return role2; }

    public int getNumRoles() {
        int numRoles = 0;
        if (adminRole) numRoles++;
        if (role1) numRoles++;
        if (role2) numRoles++;
        return numRoles;
    }
}