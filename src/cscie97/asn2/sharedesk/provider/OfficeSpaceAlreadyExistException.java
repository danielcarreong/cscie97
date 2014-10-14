/**
 * 
 */
package cscie97.asn2.sharedesk.provider;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class OfficeSpaceAlreadyExistException extends OfficeSpaceException {

    private static final long serialVersionUID = 1L;
    
    @Override
    public String toString() {
	return "OfficeSpaceAlreadyExistException {" + 
			"errorDescription: '" + getDescription() + "'}";
    }
}
