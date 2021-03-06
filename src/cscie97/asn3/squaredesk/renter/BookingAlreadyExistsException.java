/**
 * 
 */
package cscie97.asn3.squaredesk.renter;

/**
 * Boooking exception thrown when a boooking record already exists in the application.
 * @author Carlos Daniel Carreon Guzman
 */
public class BookingAlreadyExistsException extends BookingException {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
	return "BookingAlreadyExistsException {" + "errorDescription: '"
		+ getDescription() + "'}";
    }
}
