/**
 * 
 */
package cscie97.asn2.sharedesk.provider;

/**
 * OfficeSpaceException subclass indicating an OfficeSpace record was not found
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class OfficeSpaceNotFoundException extends OfficeSpaceException {

    private static final long serialVersionUID = 1L;
    
    @Override
    public String toString() {
	return "OfficeSpaceNotFoundException {" + 
			"errorDescription: '" + getDescription() + "'}";
    }
}
