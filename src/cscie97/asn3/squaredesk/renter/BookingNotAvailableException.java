/**
 * 
 */
package cscie97.asn3.squaredesk.renter;

/**
 * Booking exception thrown when a Booking record is not available.
 * @author Carlos Daniel Carreon Guzman
 * 
 */
public class BookingNotAvailableException extends BookingException {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
	return "BookingNotAvailableException {" + "errorDescription: '"
		+ getDescription() + "'}";
    }
}
