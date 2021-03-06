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

import cscie97.asn2.sharedesk.provider.AccessException;
import cscie97.asn2.sharedesk.provider.OfficeSpace;
import cscie97.asn2.sharedesk.provider.OfficeSpaceNotFoundException;
import cscie97.asn2.sharedesk.provider.OfficeSpaceServiceImpl;
import cscie97.asn4.squaredesk.authentication.AuthenticationException;
import cscie97.asn4.squaredesk.authentication.AuthenticationServiceImpl;

/**
 * SchedulingService implementation. Defines logic for managing booking services.
 * Only one instance of the class can be created.
 * 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class SchedulingServiceImpl implements SchedulingService {

    private static SchedulingServiceImpl singleton = new SchedulingServiceImpl();
    private Map<UUID, Booking> bookingMap;
    private static final String AUTHTOKEN = "admin";

    private SchedulingServiceImpl() {
	bookingMap = new HashMap<UUID, Booking>();
    };

    /**
     * @return SchedulingServiceImpl single instance
     */
    public static SchedulingServiceImpl getInstance() {
	return singleton;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cscie97.asn3.squaredesk.renter.SchedulingService#createBooking(cscie97
     * .asn3.squaredesk.renter.Booking)
     */
    @Override
    public Booking createBooking(String authToken, Booking booking) throws BookingException, AccessException {
	System.out.println("Attempting creation of Boooking.");
	if (authorization("create_booking", authToken)) {
	    if (booking == null) {
		BookingNotFoundException ex = new BookingNotFoundException();
		ex.setDescription("Please define Booking information to proceed with your request.");
		throw ex;
	    } else if (bookingMap.containsValue(booking)) {
		BookingAlreadyExistsException ex = new BookingAlreadyExistsException();
		ex.setDescription("Booking already exists for Renter ID: "
			+ booking.getRenterID() + " and OfficeSpace ID: "
			+ booking.getOfficeSpaceID());
		throw ex;
	    } else {
		try {
		    // first, validate date and workspaces availability in
		    // OfficeSpace
		    if (!checkAvailability(authToken,booking.getOfficeSpaceID(), booking.getStartDate(), booking.getEndDate())) {
			BookingNotAvailableException ex = new BookingNotAvailableException();
			ex.setDescription("Booking could not be scheduled. There is no Office workspaces available or within dates specified. Try again later.");
			throw ex;
		    } else {
			// then, update OfficeSpace capacity in workspaces by
			// subtracting 1
			OfficeSpace officeSpace = OfficeSpaceServiceImpl.getInstance().getOffice(AUTHTOKEN, booking.getOfficeSpaceID());
			int workSpaces = officeSpace.getCapacity().getWorkSpaces();
			officeSpace.getCapacity().setWorkSpaces(workSpaces--);
			// update OfficeSpace
			OfficeSpaceServiceImpl.getInstance().updateOffice(AUTHTOKEN, officeSpace);
			booking.setBookingID(UUID.randomUUID());
			bookingMap.put(booking.getBookingID(), booking);
			System.out.println("Booking: '"
				+ booking.getBookingID()
				+ "' succesfully created.\n");
		    }
		} catch (AccessException | OfficeSpaceNotFoundException e) {
		    e.printStackTrace();
		}
	    }
	}
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cscie97.asn3.squaredesk.renter.SchedulingService#checkAvailability(java
     * .util.UUID, java.util.Date, java.util.Date)
     */
    @Override
    public boolean checkAvailability(String authToken, UUID officeSpaceID, Date startDate, Date endDate) throws BookingException, AccessException {
	if (authorization("check_availability", authToken)) {
	    if ((officeSpaceID == null) || (startDate == null) || (endDate == null)) {
		BookingNotFoundException ex = new BookingNotFoundException();
		ex.setDescription("Please define full Booking information (officeSpaceID, Start Date and End Date) to proceed with your request.");
		throw ex;
	    } else {
		try {
		    OfficeSpace officeSpace = OfficeSpaceServiceImpl.getInstance().getOffice(AUTHTOKEN, officeSpaceID);
		    // validates if OfficeSpace have workspaces available
		    if (officeSpace.getCapacity().getWorkSpaces() <= 0)
			return false;
		    else {
			// validates dates availability
			List<Booking> bookingList = getBookingList(authToken, officeSpaceID);
			if (bookingList == null || bookingList.size() == 0) {
			    return true;
			} else {
			    Iterator<Booking> bookingItr = bookingList.iterator();
			    while (bookingItr.hasNext()) {
				Booking booking = (Booking) bookingItr.next();
				// verifies if startDate or endDate is between any of current OfficeSpace Booking list
				if ((startDate.before(booking.getEndDate()) && startDate.after(booking.getStartDate()))
					|| (endDate.before(booking.getEndDate()) && endDate.after(booking.getStartDate()))
					|| (startDate.equals(booking.getStartDate()))
					|| (startDate.equals(booking.getEndDate()))
					|| (endDate.equals(booking.getStartDate()) || (endDate.equals(booking.getEndDate())))) {
				    return false;
				} else {
				    return true;
				}
			    }
			}
		    }
		} catch (AccessException | OfficeSpaceNotFoundException e) {
		    e.printStackTrace();
		}
	    }
	}
	return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cscie97.asn3.squaredesk.renter.SchedulingService#deleteBooking(java.util
     * .UUID)
     */
    @Override
    public void deleteBooking(String authToken, UUID bookingID) throws BookingException, AccessException {
	if (authorization("check_availability", authToken)) {
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
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cscie97.asn3.squaredesk.renter.SchedulingService#getBookingList(java.
     * util.UUID)
     */
    @Override
    public List<Booking> getBookingList(String authToken, UUID officeSpaceID) throws BookingException, AccessException {
	if (authorization("check_availability", authToken)) {
	    List<Booking> bookingList = null;
	    if (officeSpaceID == null) {
		BookingNotFoundException ex = new BookingNotFoundException();
		ex.setDescription("Please define an OfficeSpace ID to proceed with your request.");
		throw ex;
	    } else {
		if ((getBookingList(authToken) != null) && (getBookingList(authToken).size() > 0)) {
		    bookingList = new ArrayList<Booking>();
		    Iterator<Booking> bookingItr = getBookingList(authToken).iterator();
		    while (bookingItr.hasNext()) {
			Booking booking = (Booking) bookingItr.next();
			if (booking.getOfficeSpaceID().equals(officeSpaceID))
			    bookingList.add(booking);
		    }
		}
	    }
	    return bookingList;
	}
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cscie97.asn3.squaredesk.renter.SchedulingService#getBookingList()
     */
    @Override
    public List<Booking> getBookingList(String authToken) throws AccessException {
	if (authorization("check_availability", authToken)) {
	    if (bookingMap.size() > 0)
		return new ArrayList<Booking>(bookingMap.values());
	}
	return null;
    }
    /**
     * 
     * @param authToken
     * @return
     * @throws AccessException
     */
    private boolean authorization(String serviceName, String authToken) throws AccessException {
	try {
	    AuthenticationServiceImpl asi = AuthenticationServiceImpl.getInstance();
	    return asi.checkAccess(serviceName, authToken);
	} catch (AuthenticationException e) {
	    e.printStackTrace();
	}
	return false;
    }
}
