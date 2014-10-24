/**
 * 
 */
package cscie97.asn3.squaredesk.renter;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cscie97.asn2.sharedesk.provider.Renter;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class SchedulingServiceImpl implements SchedulingService {

    private Map<String, Renter> bookingMap;
    private static SchedulingServiceImpl singleton = new SchedulingServiceImpl();
    
    private SchedulingServiceImpl() {};
    
    /**
     * @return SchedulingServiceImpl single instance
     */
    public static SchedulingServiceImpl getInstance() {
	return singleton;
    }
    
    /* (non-Javadoc)
     * @see cscie97.asn3.squaredesk.renter.SchedulingService#createBooking(cscie97.asn3.squaredesk.renter.Booking)
     */
    @Override
    public Booking createBooking(Booking booking) {
	// TODO Auto-generated method stub
	return null;
    }

    /* (non-Javadoc)
     * @see cscie97.asn3.squaredesk.renter.SchedulingService#checkAvailability(java.util.UUID, java.util.Date, java.util.Date)
     */
    @Override
    public boolean checkAvailability(UUID officeSpaceID, Date startDate,
	    Date endDate) {
	// TODO Auto-generated method stub
	return false;
    }

    /* (non-Javadoc)
     * @see cscie97.asn3.squaredesk.renter.SchedulingService#deleteBooking(java.util.UUID)
     */
    @Override
    public void deleteBooking(UUID bookingID) {
	// TODO Auto-generated method stub
	
    }

    /* (non-Javadoc)
     * @see cscie97.asn3.squaredesk.renter.SchedulingService#getBookingList(java.util.UUID)
     */
    @Override
    public List<Booking> getBookingList(UUID officeSpaceID) {
	// TODO Auto-generated method stub
	return null;
    }

    /* (non-Javadoc)
     * @see cscie97.asn3.squaredesk.renter.SchedulingService#getBookingList()
     */
    @Override
    public List<Booking> getBookingList() {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * @return the bookingMap
     */
    public Map<String, Renter> getBookingMap() {
	return bookingMap;
    }

    /**
     * @param bookingMap the bookingMap to set
     */
    public void setBookingMap(Map<String, Renter> bookingMap) {
	this.bookingMap = bookingMap;
    }

}
