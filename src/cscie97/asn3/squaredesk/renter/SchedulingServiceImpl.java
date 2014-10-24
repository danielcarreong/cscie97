/**
 * 
 */
package cscie97.asn3.squaredesk.renter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cscie97.asn2.sharedesk.provider.Provider;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class SchedulingServiceImpl implements SchedulingService {

    private Map<UUID, Booking> bookingMap;
    private static SchedulingServiceImpl singleton = new SchedulingServiceImpl();
    
    private SchedulingServiceImpl() {
	bookingMap = new HashMap<UUID, Booking>();
    };
    
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
    public Booking createBooking(Booking booking) throws BookingException {
	if (booking == null) {
	    BookingNotFoundException ex = new BookingNotFoundException();
	    ex.setDescription("Please define Booking information to proceed with your request.");
	    throw ex;
	} else if (bookingMap.containsValue(booking)) {
	    BookingAlreadyExistsException ex = new BookingAlreadyExistsException();
	    ex.setDescription("Booking already exists for Renter ID: " + booking.getRenterID() + " and OfficeSpace ID: " + booking.getOfficeSpaceID());
	    throw ex;
	} else {
	    bookingMap.put(UUID.randomUUID(), booking);
	    System.out.println("Booking: '" + booking.getBooking() + "' succesfully created.");
	}
	return null;
    }

    /* (non-Javadoc)
     * @see cscie97.asn3.squaredesk.renter.SchedulingService#checkAvailability(java.util.UUID, java.util.Date, java.util.Date)
     */
    @Override
    public boolean checkAvailability(UUID officeSpaceID, Date startDate, Date endDate) throws BookingException {
	// TODO Auto-generated method stub
	return false;
    }

    /* (non-Javadoc)
     * @see cscie97.asn3.squaredesk.renter.SchedulingService#deleteBooking(java.util.UUID)
     */
    @Override
    public void deleteBooking(UUID bookingID) throws BookingException {
	System.out.println("Attempting deletion of Boooking: '" + bookingID + "'");
	if (bookingID == null) {
	    BookingNotFoundException ex = new BookingNotFoundException();
	    ex.setDescription("Please define a Booking ID to proceed with your request.");
	    throw ex;
	} else if (!bookingMap.containsKey(bookingID)) {
	    BookingNotFoundException ex = new BookingNotFoundException();
	    ex.setDescription("Booking ID: '" + bookingID + "' does not exists in our records.");
	    throw ex;
	} else if (bookingMap.containsKey(bookingID)) {
	    bookingMap.remove(bookingMap.get(bookingID));
	    System.out.println("Deletion of Booking ID: '" + bookingID + "' completed.\n");
	}
    }

    /* (non-Javadoc)
     * @see cscie97.asn3.squaredesk.renter.SchedulingService#getBookingList(java.util.UUID)
     */
    @Override
    public List<Booking> getBookingList(UUID officeSpaceID) throws BookingException {
	List<Booking> bookingList = null;
	if (officeSpaceID == null) {
	    BookingNotFoundException ex = new BookingNotFoundException();
	    ex.setDescription("Please define an OfficeSpace ID to proceed with your request.");
	    throw ex;
	} else {
	    bookingList = new ArrayList<Booking>();
	    Iterator<Booking> bookingItr = getBookingList().iterator();
	    while(bookingItr.hasNext()) {
		Booking booking = (Booking) bookingItr.next();
		if (booking.getOfficeSpaceID().equals(officeSpaceID))
		    bookingList.add(booking);
	    }
	}
	return bookingList;
    }

    /* (non-Javadoc)
     * @see cscie97.asn3.squaredesk.renter.SchedulingService#getBookingList()
     */
    @Override
    public List<Booking> getBookingList() {
	if (bookingMap.size() > 0)
	    return new ArrayList<Booking> (bookingMap.values());
	return null;
    }
}
