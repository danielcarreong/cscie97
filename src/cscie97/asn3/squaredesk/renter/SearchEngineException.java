/**
 * 
 */
package cscie97.asn3.squaredesk.renter;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class SearchEngineException extends Exception {

    private static final long serialVersionUID = 1L;
    private String description;

    /**
     * @return the description
     */
    public String getDescription() {
	return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
	this.description = description;
    }
}
