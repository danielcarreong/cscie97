/**
 * 
 */
package cscie97.asn2.sharedesk.provider;

/**
 * OfficeSpaceException sub class indicating the previous existence of an OfficeSpace record
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
