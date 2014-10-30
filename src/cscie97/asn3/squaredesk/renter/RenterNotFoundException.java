/**
 * 
 */
package cscie97.asn3.squaredesk.renter;

/**
 * RenterException indicating a Renter record was not found
 * 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class RenterNotFoundException extends RenterException {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
	return "RenterNotFoundException {" + "errorDescription: '"
		+ getDescription() + "'}";
    }
}
