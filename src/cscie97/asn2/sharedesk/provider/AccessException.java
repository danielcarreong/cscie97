/**
 * 
 */
package cscie97.asn2.sharedesk.provider;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class AccessException extends Exception {

    private static final long serialVersionUID = 1L;
    private String description;

    /**
     * @return the description
     */
    public String getDescription() {
	return description;
    }

    /**
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
