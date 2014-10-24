/**
 * 
 */
package cscie97.asn3.test;

import java.util.Iterator;

import cscie97.asn2.sharedesk.provider.CommonAccess;
import cscie97.asn2.sharedesk.provider.Feature;
import cscie97.asn2.sharedesk.provider.OfficeSpace;
import cscie97.asn2.sharedesk.provider.OfficeSpaceServiceImpl;
import cscie97.asn2.sharedesk.provider.Provider;
import cscie97.asn2.sharedesk.provider.ProviderServiceImpl;
import cscie97.asn2.sharedesk.provider.Renter;
import cscie97.asn3.squaredesk.renter.RenterServiceImpl;

/**
 * Class used as caller to demonstrate Renter services in SquareDesk application
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class TestDriver {

    /**
     * @param args
     */
    public static void main(String args[]) {
	
	final String authToken = "admin";
	
	RenterServiceImpl rsi = RenterServiceImpl.getInstance();
	
	try {
	    rsi.createRenter(authToken, "Provider_1.yaml");
	    rsi.createRenter(authToken, "Provider_2.yaml");
	    
	    Renter toDelete = new Renter();
	    Renter toUpdate = new Renter();
	    
	    Iterator<Renter> itr = rsi.getRenterList(authToken).iterator();
	    System.out.println("---Renter List---\n");
	    while(itr.hasNext()) {
		Renter renter = (Renter) itr.next();
		if (renter.getName().equalsIgnoreCase("Carlos Carreon"))
		    toUpdate = renter;
		else
		    toDelete = renter;
		System.out.println("Provider name: " + renter.getName());
		System.out.println("Provider contact: " + renter.getContact());
		System.out.println("Provider picture:");
		System.out.println("- Picture name: " + renter.getPicture().getName());
		System.out.println("- Picture description: " + renter.getPicture().getDescription());
		System.out.println("- Picture description: " + renter.getPicture().getURI());
		System.out.println("");
	    }
	    System.out.println("---End Providers List---\n");
	    /*
	    System.out.println("---Update Process---\n");
	    rsi.updateProvider(authToken, toUpdate,"Provider_2Update.yaml");
	    System.out.println("---End Update Process---\n");
	    
	    Iterator<Provider> itr4 = rsi.getProviderList(authToken).iterator();
	    System.out.println("---Providers List---\n");
	    while(itr4.hasNext()) {
		Provider provider = (Provider) itr4.next();
		System.out.println("Provider name: " + provider.getName());
		System.out.println("Provider contact: " + provider.getContact());
		System.out.println("Provider picture:");
		System.out.println("- Picture name: " + provider.getPicture().getName());
		System.out.println("- Picture description: " + provider.getPicture().getDescription());
		System.out.println("- Picture description: " + provider.getPicture().getURI());
		if (provider.getOfficeSpaceIdentifier() == null)
		    System.out.println(provider.getName() + "'s OfficeSpace: " + provider.getOfficeSpaceIdentifier());
		else
		    System.out.println(provider.getName() + "'s OfficeSpace: " + oss.getOffice(authToken, provider.getOfficeSpaceIdentifier()).getName());
		System.out.println("");
	    }
	    System.out.println("---End Providers List---\n");
	    */
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
