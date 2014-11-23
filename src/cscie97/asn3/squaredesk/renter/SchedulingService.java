/**
 * 
 */
package cscie97.asn3.squaredesk.renter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import cscie97.asn2.sharedesk.provider.AccessException;

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
     * @param authToken 
     * 
     * @param booking
     * @return Booking
     * @throws BookingException
     * @throws AccessException 
     */
    public Booking createBooking(String authToken, Booking booking) throws BookingException, AccessException;

    /**
     * Checks whether an OfficeSpace is available within dates range specified
     * @param authToken 
     * 
     * @param officeSpaceID
     * @param startDate
     * @param endDate
     * @return boolean
     * @throws BookingException
     * @throws AccessException 
     */
    public boolean checkAvailability(String authToken, UUID officeSpaceID, Date startDate, Date endDate) throws BookingException, AccessException;

    /**
     * Removes a Booking instance and information through an specified ID
     * @param authToken 
     * 
     * @param bookingID
     * @throws BookingException
     * @throws AccessException 
     */
    public void deleteBooking(String authToken, UUID bookingID) throws BookingException, AccessException;

    /**
     * Retrieves the list of Bookings made to an specific OfficeSpace
     * @param authToken 
     * 
     * @param officeSpaceID
     * @return List<Booking>
     * @throws BookingException
     * @throws AccessException 
     */
    public List<Booking> getBookingList(String authToken, UUID officeSpaceID) throws BookingException, AccessException;

    /**
     * Retrieves a list of all Bookings made up to the point of making this
     * request.
     * @param authToken 
     * 
     * @return List<Booking>
     * @throws AccessException 
     */
    public List<Booking> getBookingList(String authToken) throws AccessException;
}
