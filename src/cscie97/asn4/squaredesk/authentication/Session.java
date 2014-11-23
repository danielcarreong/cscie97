/**
 * 
 */
package cscie97.asn4.squaredesk.authentication;

import java.util.Date;
import java.util.UUID;

/**
 * Session class will allow the application to give a control time of use during
 * the activity of a User.
 * 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class Session {

    private UUID authToken;
    private Date timeStamp;
    private boolean valid;
    
    /**
     * @return the authToken
     */
    public UUID getAuthToken() {
        return authToken;
    }
    /**
     * @param authToken the authToken to set
     */
    public void setAuthToken(UUID authToken) {
        this.authToken = authToken;
    }
    /**
     * @return the valid
     */
    public boolean isValid() {
        return valid;
    }
    /**
     * @param valid the valid to set
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }
    /**
     * @return the timeStamp
     */
    public Date getTimeStamp() {
        return timeStamp;
    }
    /**
     * @param timeStamp the timeStamp to set
     */
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
