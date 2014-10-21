/**
 * 
 */
package cscie97.asn3.squaredesk.renter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public interface SchedulingService {

    /**
     * @param booking
     * @return Booking
     */
    public Booking createBooking(Booking booking);
    /**
     * @param officeSpaceID
     * @param startDate
     * @param endDate
     * @return boolean
     */
    public boolean checkAvailability(UUID officeSpaceID, Date startDate, Date endDate);
    /**
     * @param bookingID
     */
    public void deleteBooking(UUID bookingID);
    /**
     * @param officeSpaceID
     * @return List<Booking>
     */
    public List<Booking> getBookingList(UUID officeSpaceID);
    /**
     * @return List<Booking>
     */
    public List<Booking> getBookingList();
}
