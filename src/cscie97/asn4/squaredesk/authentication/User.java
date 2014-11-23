/**
 * 
 */
package cscie97.asn4.squaredesk.authentication;

import java.util.Map;

/**
 * User class for Authentication services. User information will be given Permissions,
 * Roles, and be allowed to execute Services. Also, will have associated Credentials
 * and a Session for Login and Logout services.
 * 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class User implements Visitable {

    private String userID;
    private String name;
    private Session session;
    private Map<String, Credential> credentialMap;
    private Role role;
    
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
    /**
     * @return the role
     */
    public Role getRole() {
	return role;
    }
    /**
     * @param role the role to set
     */
    public void setRole(Role role) {
	this.role = role;
    }
    /**
     * @return the credentialMap
     */
    public Map<String, Credential> getCredentialMap() {
	return credentialMap;
    }
    /**
     * @param credentialMap the credentialMap to set
     */
    public void setCredentialMap(Map<String, Credential> credentialMap) {
	this.credentialMap = credentialMap;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.Visitable#acceptVisitor(cscie97.asn4.squaredesk.authentication.Visitor)
     */
    @Override
    public void acceptVisitor(Visitor visitor) {
	visitor.visit(this);
    }
}
