/**
 * 
 */
package cscie97.asn2.sharedesk.provider;

/**
 * Class defining an exception when a user tries to call a secure method
 * @author Carlos Daniel Carreon Guzman
 */
public class AccessException extends Exception {

    private static final long serialVersionUID = 1L;
    private String description;

    /**
     * Retrieves exception description
     * @return the description
     */
    public String getDescription() {
	return description;
    }

    /**
     * Sets exception description
     * @param description the description to set
     */
    public void setDescription(String description) {
	this.description = description;
    }
    
    @Override
    public String toString() {
	return "AcessException {" + 
			"errorDescription: '" + description + "'}";
    }
}
