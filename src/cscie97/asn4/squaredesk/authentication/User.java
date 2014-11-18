/**
 * 
 */
package cscie97.asn4.squaredesk.authentication;

/**
 * User class for Authentication services. User information will be given Permissions,
 * Roles, and be allowed to execute Services. Also, will have associated Credentials
 * and a Session for Login and Logout services.
 * 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class User {

    private String userID;
    private String name;
    private Session session;
    private Credential credential;
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the session
     */
    public Session getSession() {
        return session;
    }
    /**
     * @param session the session to set
     */
    public void setSession(Session session) {
        this.session = session;
    }
    /**
     * @return the credential
     */
    public Credential getCredential() {
        return credential;
    }
    /**
     * @param credential the credential to set
     */
    public void setCredential(Credential credential) {
        this.credential = credential;
    }
    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }
    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }
}
