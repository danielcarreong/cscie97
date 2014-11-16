/**
 * 
 */
package cscie97.asn3.test;

import java.text.SimpleDateFormat;
import java.util.Iterator;

import cscie97.asn2.sharedesk.provider.OfficeSpace;
import cscie97.asn2.sharedesk.provider.OfficeSpaceServiceImpl;
import cscie97.asn2.sharedesk.provider.ProviderServiceImpl;
import cscie97.asn2.sharedesk.provider.Renter;
import cscie97.asn3.squaredesk.renter.Booking;
import cscie97.asn3.squaredesk.renter.RenterServiceImpl;
import cscie97.asn3.squaredesk.renter.SchedulingServiceImpl;

/**
 * Class used as caller to demonstrate Renter services in SquareDesk application
 * 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class TestDriver {

    /**
     * @param args
     */
    public static void main(String args[]) {

	final String authToken = "admin";

	ProviderServiceImpl psi = ProviderServiceImpl.getInstance();
	OfficeSpaceServiceImpl oss = OfficeSpaceServiceImpl.getInstance();
	RenterServiceImpl rsi = RenterServiceImpl.getInstance();
	SchedulingServiceImpl ssi = SchedulingServiceImpl.getInstance();

	try {
	    // first, create Providers and corresponding OfficeSpaces
	    psi.createProvider(authToken, "Provider1.yaml");
	    psi.createProvider(authToken, "Provider2.yaml");
	    // then, create Renter records
	    rsi.createRenter(authToken, "Renter1.yaml");
	    rsi.createRenter(authToken, "Renter2.yaml");
	    rsi.createRenter(authToken, "Renter3.yaml");

	    Renter renterToUpdate = new Renter();
	    Renter renterToDelete = new Renter();
	    // display full Renter list
	    System.out.println("---Renter List---\n");
	    for (Iterator<Renter> itr = rsi.getRenterList(authToken).iterator(); itr.hasNext();) {
		Renter renter = (Renter) itr.next();
		if (renter.getName().equalsIgnoreCase("Anna"))
		    renterToUpdate = renter;
		else
		    renterToDelete = renter;
		System.out.println("Renter ID: " + renter.getIdentifier());
		System.out.println("Renter name: " + renter.getName());
		System.out.println("Renter gender: " + renter.getGender());
		System.out.println("Renter contact: " + renter.getContact());
		System.out.println("Renter picture:");
		System.out.println("- Picture URI: "+ renter.getPicture().getURI());
		System.out.println("");
	    }
	    System.out.println("---End Renter List---\n");
	    
	    // update a Renter record
	    System.out.println("---Update Process---\n");
	    rsi.updateRenter(authToken, renterToUpdate, "Renter2_Update.yaml");
	    System.out.println("---End Update Process---\n");
	    
	    rsi.deleteRenter(authToken, renterToDelete.getIdentifier());
	    
	    System.out.println("---Renter List Updated---\n");
	    for (Iterator<Renter> itr = rsi.getRenterList(authToken).iterator(); itr.hasNext();) {
		Renter renter = (Renter) itr.next();
		if (renter.getName().equalsIgnoreCase("Anna"))
		    renterToUpdate = renter;
		else
		    renterToDelete = renter;
		System.out.println("Renter ID: " + renter.getIdentifier());
		System.out.println("Renter name: " + renter.getName());
		System.out.println("Renter gender: " + renter.getGender());
		System.out.println("Renter contact: " + renter.getContact());
		System.out.println("Renter picture:");
		System.out.println("- Picture URI: "+ renter.getPicture().getURI());
		System.out.println("");
	    }
	    System.out.println("---End Renter List Updated---\n");

	    OfficeSpace space = new OfficeSpace();
	    space = oss.getOfficeList(authToken).get(0);

	    String sdate = "01/01/2014";
	    String edate = "01/10/2014";
	    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
	    
	    Booking booking = new Booking();
	    booking.setOfficeSpaceID(space.getIdentifier());
	    booking.setRenterID(renterToUpdate.getIdentifier());
	    booking.setStartDate(format.parse(sdate));
	    booking.setEndDate(format.parse(edate));
	    ssi.createBooking(booking);
	    
	    // Forces booking with same date
	    System.out.println("---Forcing a booking to be made with same date---");
	    Booking booking2 = new Booking();
	    booking2.setOfficeSpaceID(space.getIdentifier());
	    booking2.setRenterID(renterToUpdate.getIdentifier());
	    booking2.setStartDate(format.parse(sdate));
	    booking2.setEndDate(format.parse(edate));
	    ssi.createBooking(booking2);

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
