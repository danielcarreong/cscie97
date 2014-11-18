/**
 * 
 */
package cscie97.asn4.squaredesk.authentication;

/**
 * Credential class gives User a set of Username and Password in order to be
 * Logged in into the application.
 * 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class Credential {

   private String credentialID;
   private String username;
   private String password;
   
    /**
     * @return the credentialID
     */
    public String getCredentialID() {
        return credentialID;
    }
    /**
     * @param credentialID the credentialID to set
     */
    public void setCredentialID(String credentialID) {
        this.credentialID = credentialID;
    }
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
