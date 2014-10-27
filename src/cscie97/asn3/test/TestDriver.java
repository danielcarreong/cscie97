/**
 * 
 */
package cscie97.asn3.test;

import java.text.SimpleDateFormat;
import java.util.Iterator;

import cscie97.asn1.knowledge.engine.KnowledgeGraph;
import cscie97.asn1.knowledge.engine.QueryEngine;
import cscie97.asn1.knowledge.engine.QueryEngineException;
import cscie97.asn2.sharedesk.provider.CommonAccess;
import cscie97.asn2.sharedesk.provider.Feature;
import cscie97.asn2.sharedesk.provider.OfficeSpace;
import cscie97.asn2.sharedesk.provider.OfficeSpaceServiceImpl;
import cscie97.asn2.sharedesk.provider.Provider;
import cscie97.asn2.sharedesk.provider.ProviderServiceImpl;
import cscie97.asn2.sharedesk.provider.Renter;
import cscie97.asn3.squaredesk.renter.Booking;
import cscie97.asn3.squaredesk.renter.RenterServiceImpl;
import cscie97.asn3.squaredesk.renter.SchedulingServiceImpl;

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
	
	ProviderServiceImpl psi = ProviderServiceImpl.getInstance();
	OfficeSpaceServiceImpl oss = OfficeSpaceServiceImpl.getInstance();
	RenterServiceImpl rsi = RenterServiceImpl.getInstance();
	SchedulingServiceImpl ssi = SchedulingServiceImpl.getInstance();
	KnowledgeGraph kg = KnowledgeGraph.getInstance();
	QueryEngine queryEngine = new QueryEngine();
	
	try {
	    
	    psi.createProvider(authToken, "Provider1.yaml");
	    psi.createProvider(authToken, "Provider2.yaml");
	    // Populating KnowledgeGraph with OfficeSpace information
	    System.out.println("---Populating Knowledge Graph---\n");
	    for (Iterator<OfficeSpace> itr = oss.getOfficeList(authToken).iterator(); itr.hasNext();) {
		OfficeSpace office = (OfficeSpace) itr.next();
		// adding OfficeSpace latitude and longitude 
		String lat = String.valueOf(office.getLocation().getLat());
		String lon = String.valueOf(office.getLocation().getLon());
		kg.addTriplePermutation(
			kg.getTriple(
				kg.getNode(office.getIdentifier().toString()), 
				kg.getPredicate("has_lat_long"),
				kg.getNode(lat + "_" + lon)));
		// adding OfficeSpace CommonAccess
		for (Iterator<CommonAccess> commonAccessItr = office.getCommonAccess().iterator(); commonAccessItr.hasNext();) {
		    CommonAccess ca = commonAccessItr.next();
		    kg.addTriplePermutation(
			    kg.getTriple(
				    kg.getNode(office.getIdentifier().toString()), 
				    kg.getPredicate("has_common_access"), 
				    kg.getNode(ca.getName())));
		}
		// adding OfficeSpace Features
		for (Iterator<Feature> featureItr = office.getFeature().iterator(); featureItr.hasNext();) {
		    Feature feature = featureItr.next();
		    kg.addTriplePermutation(
			    kg.getTriple(
				    kg.getNode(office.getIdentifier().toString()), 
				    kg.getPredicate("has_feature"), 
				    kg.getNode(feature.getName())));
		}
	    }
	    try {
		queryEngine.executeQuery("? has_common_access ?.");
	    } catch (QueryEngineException e) {
		e.printStackTrace();
	    }
	    System.out.println("---End Populating Knowledge Graph---\n");
	    
	    Provider providerToDelete = new Provider();
	    Provider providerToUpdate = new Provider();
	    
	    System.out.println("---Providers List---\n");
	    for (Iterator<Provider> itr = psi.getProviderList(authToken).iterator(); itr.hasNext();) {
		Provider provider = (Provider) itr.next();
		if (provider.getName().equalsIgnoreCase("Carlos Carreon"))
		    providerToUpdate = provider;
		else
		    providerToDelete = provider;
		System.out.println("Provider name: " + provider.getName());
		System.out.println("Provider contact: " + provider.getContact());
		System.out.println("Provider picture:");
		System.out.println("- Picture name: " + provider.getPicture().getName());
		System.out.println("- Picture description: " + provider.getPicture().getDescription());
		System.out.println("- Picture description: " + provider.getPicture().getURI());
		System.out.println(provider.getName() + "'s OfficeSpace: " + oss.getOffice(authToken, provider.getOfficeSpaceIdentifier()).getName());
		System.out.println("");
	    }
	    System.out.println("---End Providers List---\n");
	    
	    System.out.println("---OfficeSpace List---\n");
	    for (Iterator<OfficeSpace> itr = oss.getOfficeList(authToken).iterator(); itr.hasNext();) {
		OfficeSpace office = (OfficeSpace) itr.next();
		System.out.println("OfficeSpace name: " + office.getName());
		System.out.println("OfficeSpace ID: " + office.getIdentifier());
		System.out.println("OfficeSpace capacity: ");
		System.out.println("- Number of People: " + office.getCapacity().getNumberOfPeople());
		System.out.println("- Square Feet: " + office.getCapacity().getSquareFeet());
		System.out.println("- Work Spaces: " + office.getCapacity().getWorkSpaces());
		System.out.println("OfficeSpace common access:");
		Iterator<CommonAccess> commonAccessItr = office.getCommonAccess().iterator();
		while(commonAccessItr.hasNext()) {
		    CommonAccess ca = commonAccessItr.next();
		    System.out.println("- " + ca.getName());
		}
		System.out.println("OfficeSpace features:");
		Iterator<Feature> featureItr = office.getFeature().iterator();
		while(featureItr.hasNext()) {
		    Feature feature = featureItr.next();
		    System.out.println("- " + feature.getName());
		}
		System.out.println("");
	    }
	    System.out.println("---End OfficeSpace List---\n");
	    
	    //oss.deleteOffice(authToken, oss.getOffice(authToken, toDelete.getOfficeSpaceIdentifier()));
	    
	    System.out.println("---Providers List---\n");
	    for (Iterator<Provider> itr = psi.getProviderList(authToken).iterator(); itr.hasNext();) {
		Provider provider = (Provider) itr.next();
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
	    
	    System.out.println("---OfficeSpace List---\n");
	    for (Iterator<OfficeSpace> itr = oss.getOfficeList(authToken).iterator();itr.hasNext();) {
		OfficeSpace office = (OfficeSpace) itr.next();
		System.out.println("OfficeSpace name: " + office.getName());
		System.out.println("OfficeSpace ID: " + office.getIdentifier());
		System.out.println("OfficeSpace capacity: ");
		System.out.println("- Number of People: " + office.getCapacity().getNumberOfPeople());
		System.out.println("- Square Feet: " + office.getCapacity().getSquareFeet());
		System.out.println("- Work Spaces: " + office.getCapacity().getWorkSpaces());
		System.out.println("OfficeSpace common access:");
		Iterator<CommonAccess> commonAccessItr = office.getCommonAccess().iterator();
		while(commonAccessItr.hasNext()) {
		    CommonAccess ca = commonAccessItr.next();
		    System.out.println("- " + ca.getName());
		}
		System.out.println("OfficeSpace features:");
		Iterator<Feature> featureItr = office.getFeature().iterator();
		while(featureItr.hasNext()) {
		    Feature feature = featureItr.next();
		    System.out.println("- " + feature.getName());
		}
		System.out.println("");
	    }
	    System.out.println("---End OfficeSpace List---\n");
	    
	    System.out.println("---Update Process---\n");
	    psi.updateProvider(authToken, providerToUpdate,"Provider2_Update.yaml");
	    System.out.println("---End Update Process---\n");
	    
	    System.out.println("---Providers List---\n");
	    for (Iterator<Provider> itr = psi.getProviderList(authToken).iterator(); itr.hasNext();) {
		Provider provider = (Provider) itr.next();
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
	    
	    //cscie97.asn2.test.TestDriver td = new cscie97.asn2.test.TestDriver();
	    //cscie97.asn2.test.TestDriver.main(null);
	    
	    rsi.createRenter(authToken, "Renter1.yaml");
	    rsi.createRenter(authToken, "Renter2.yaml");
	    
	    Renter renterToUpdate = new Renter();
	    Renter renterToDelete = new Renter();
	    
	    System.out.println("---Renter List---\n");
	    for (Iterator<Renter> itr = rsi.getRenterList(authToken).iterator(); itr.hasNext();) {
		Renter renter = (Renter) itr.next();
		if (renter.getName().equalsIgnoreCase("Carlos Carreon"))
		    renterToUpdate = renter;
		else
		    renterToDelete = renter;
		System.out.println("Renter name: " + renter.getName());
		System.out.println("Renter gender: " + renter.getGender());
		System.out.println("Renter contact: " + renter.getContact());
		System.out.println("Renter picture:");
		System.out.println("- Picture URI: " + renter.getPicture().getURI());
		System.out.println("");
	    }
	    System.out.println("---End Renter List---\n");
	    
	    OfficeSpace space = new OfficeSpace();
	    System.out.println("---OfficeSpace List---\n");
	    for (Iterator<OfficeSpace> itr = oss.getOfficeList(authToken).iterator(); itr.hasNext();) {
		OfficeSpace office = (OfficeSpace) itr.next();
		space = office;
		System.out.println("OfficeSpace name: " + office.getName());
		System.out.println("OfficeSpace ID: " + office.getIdentifier());
		System.out.println("OfficeSpace capacity: ");
		System.out.println("- Number of People: " + office.getCapacity().getNumberOfPeople());
		System.out.println("- Square Feet: " + office.getCapacity().getSquareFeet());
		System.out.println("- Work Spaces: " + office.getCapacity().getWorkSpaces());
		System.out.println("OfficeSpace common access:");
		Iterator<CommonAccess> commonAccessItr = office.getCommonAccess().iterator();
		while(commonAccessItr.hasNext()) {
		    CommonAccess ca = commonAccessItr.next();
		    System.out.println("- " + ca.getName());
		}
		System.out.println("OfficeSpace features:");
		Iterator<Feature> featureItr = office.getFeature().iterator();
		while(featureItr.hasNext()) {
		    Feature feature = featureItr.next();
		    System.out.println("- " + feature.getName());
		}
		System.out.println("");
	    }
	    System.out.println("---End OfficeSpace List---\n");
	    
	    System.out.println();
	    String sdate = "01/01/2014";
	    String edate = "01/10/2014";
	    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
	    boolean result = ssi.checkAvailability(space.getIdentifier(), 
		    format.parse(sdate), format.parse(edate));
	    System.out.println(result);
	    
	    System.out.println(format.format(format.parseObject(sdate)));
	    System.out.println(format.format(format.parseObject(edate)));
	    
	    Booking booking = new Booking();
	    booking.setOfficeSpaceID(space.getIdentifier());
	    booking.setRenterID(renterToUpdate.getIdentifier());
	    booking.setStartDate(format.parse(sdate));
	    booking.setEndDate(format.parse(edate));
	    ssi.createBooking(booking);
	    
	    Booking booking2 = new Booking();
	    booking2.setOfficeSpaceID(space.getIdentifier());
	    booking2.setRenterID(renterToUpdate.getIdentifier());
	    booking2.setStartDate(format.parse(sdate));
	    booking2.setEndDate(format.parse(edate));
	    ssi.createBooking(booking2);
	    
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
