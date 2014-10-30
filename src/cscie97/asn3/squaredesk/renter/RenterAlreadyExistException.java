/**
 * 
 */
package cscie97.asn3.squaredesk.renter;

/**
 * RenterException subclass indicating the previous existence of a Renter record
 * with similar information
 * 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class RenterAlreadyExistException extends RenterException {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
	return "RenterAlreadyExistException {" + "errorDescription: '"
		+ getDescription() + "'}";
    }

}
