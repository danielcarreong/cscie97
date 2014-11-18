/**
 * 
 */
package cscie97.asn4.squaredesk.authentication;

import java.util.Date;

/**
 * Session class will allow the application to give a control time of use during
 * the activity of a User.
 * 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class Session {

    private String sessionID;
    private Date timeStamp;
    private boolean state;
    
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
    /**
     * @return the state
     */
    public boolean isState() {
        return state;
    }
    /**
     * @param state the state to set
     */
    public void setState(boolean state) {
        this.state = state;
    }
    /**
     * @return the sessionID
     */
    public String getSessionID() {
        return sessionID;
    }
    /**
     * @param sessionID the sessionID to set
     */
    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
}
