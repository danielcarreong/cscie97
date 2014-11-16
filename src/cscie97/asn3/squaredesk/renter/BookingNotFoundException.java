/**
 * 
 */
package cscie97.asn3.squaredesk.renter;

/**
 * Boooking exception thrown when a Booking record is not found.
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class BookingNotFoundException extends BookingException {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
	return "BookingNotFoundException {" + "errorDescription: '"
		+ getDescription() + "'}";
    }
}
