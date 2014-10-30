/**
 * 
 */
package cscie97.asn3.squaredesk.renter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Class defining Scheduling services, which contain creation and deletion of
 * Booking instances. Also checks the availability of an OfficeSpace, retrieves
 * a Booking instance if exists and a List of Booking instances.
 * 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public interface SchedulingService {

    /**
     * Creates a new Booking instance
     * 
     * @param booking
     * @return Booking
     * @throws BookingException
     */
    public Booking createBooking(Booking booking) throws BookingException;

    /**
     * Checks whether an OfficeSpace is available within dates range specified
     * 
     * @param officeSpaceID
     * @param startDate
     * @param endDate
     * @return boolean
     * @throws BookingException
     */
    public boolean checkAvailability(UUID officeSpaceID, Date startDate,
	    Date endDate) throws BookingException;

    /**
     * Removes a Booking instance and information through an specified ID
     * 
     * @param bookingID
     * @throws BookingException
     */
    public void deleteBooking(UUID bookingID) throws BookingException;

    /**
     * Retrieves the list of Bookings made to an specific OfficeSpace
     * 
     * @param officeSpaceID
     * @return List<Booking>
     * @throws BookingException
     */
    public List<Booking> getBookingList(UUID officeSpaceID)
	    throws BookingException;

    /**
     * Retrieves a list of all Bookings made up to the point of making this
     * request.
     * 
     * @return List<Booking>
     */
    public List<Booking> getBookingList();
}
