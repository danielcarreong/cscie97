/**
 * 
 */
package cscie97.asn4.test;

import java.io.ObjectInputStream.GetField;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import cscie97.asn2.sharedesk.provider.AccessException;
import cscie97.asn2.sharedesk.provider.Feature;
import cscie97.asn2.sharedesk.provider.OfficeSpace;
import cscie97.asn2.sharedesk.provider.OfficeSpaceServiceImpl;
import cscie97.asn2.sharedesk.provider.Provider;
import cscie97.asn2.sharedesk.provider.ProviderAlreadyExistException;
import cscie97.asn2.sharedesk.provider.ProviderServiceImpl;
import cscie97.asn2.sharedesk.provider.Renter;
import cscie97.asn3.squaredesk.renter.RenterAlreadyExistException;
import cscie97.asn3.squaredesk.renter.RenterNotFoundException;
import cscie97.asn3.squaredesk.renter.RenterServiceImpl;
import cscie97.asn3.squaredesk.renter.SearchEngineException;
import cscie97.asn3.squaredesk.renter.SearchEngineImpl;
import cscie97.asn4.squaredesk.authentication.AuthenticationException;
import cscie97.asn4.squaredesk.authentication.AuthenticationServiceImpl;
import cscie97.asn4.squaredesk.authentication.CSVImporter;
import cscie97.asn4.squaredesk.authentication.Inventory;
import cscie97.asn4.squaredesk.authentication.Permission;
import cscie97.asn4.squaredesk.authentication.Service;
import cscie97.asn4.squaredesk.authentication.User;
import cscie97.asn4.squaredesk.authentication.Visitable;

/**
 * Class used as caller to demonstrate Authentication services.
 * 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class TestDriver {

    private static String AUTHTOKEN = "ADMIN";
    
    /**
     * @param args
     */
    public static void main(String args[]) {
	if (args[0].length() > 0) {
	    // import CSV data into Authentication Service API
	    System.out.println("---Importing Authentication Services---");
	    CSVImporter importer = new CSVImporter();
	    //importer.importCSV("authentication2.csv");
	    importer.importCSV(args[0]);
	    System.out.println("Filename Imported: " + args[0]);
	    System.out.println("---Finish Importing---\n");

	    AuthenticationServiceImpl asi = AuthenticationServiceImpl.getInstance();

	    System.out.println("---Generating Inventory output File---");
	    String fileName = "AuthInventory.yaml";
	    Inventory inventory = new Inventory();
	    inventory.generateOutputFile(AUTHTOKEN, fileName);
	    System.out.println("File created: " + fileName);
	    System.out.println("---Inventory output File Finished---\n");
	    
	    System.out.println("---Testing Renter Services---\n");
	    RenterServiceImpl rsi = RenterServiceImpl.getInstance();
	    try {
		System.out.println("Logging in: ");
		System.out.println("username: lucy / password: 4567");
		User user = asi.logIn("lucy", "4567");
		String authToken = user.getSession().getAuthToken().toString();
		try {
		    System.out.println("\n---Attempting creation of Renter---");
		    rsi.createRenter(authToken, "Renter1.yaml");
		    
		    System.out.println("---Retieving Renter List---");
		    List<Renter> renterList = rsi.getRenterList(authToken);
		    
		    System.out.println("\n---Adding Permission of: get_renter_list to User---");
		    Permission permission = new Permission();
		    permission.setEntitlementID("get_renter_list");
		    permission.setName("Get Renter List");
		    permission.setDescription("Retrieves Renters List.");
		    
		    Service service = asi.getService("admin", "renter_service");
		    asi.definePermission(authToken, service, permission);
		    asi.addEntitlement(authToken, user.getRole(), permission);
		    System.out.println("---Permission: " + permission.getEntitlementID() + " to User granted---\n");
		    
		    System.out.println("---Retieving Renter List---");
		    renterList = rsi.getRenterList(authToken);
		    Renter renter = null;
		    
		    if (renterList.size() > 0)
			renter = renterList.get(0);
		    
		    System.out.println("---Attempting deletion of Renter---");
		    rsi.deleteRenter(authToken, renter.getIdentifier());
		    
		    System.out.println("\n---Adding Permission of: delete_renter to User---");
		    permission = new Permission();
		    permission.setEntitlementID("delete_renter");
		    permission.setName("Deletes a Renter");
		    permission.setDescription("Deletes a Renter record from application.");
		    
		    service = asi.getService("admin", "renter_service");
		    asi.definePermission(authToken, service, permission);
		    asi.addEntitlement(authToken, user.getRole(), permission);
		    System.out.println("---Permission: " + permission.getEntitlementID() + " to User granted---\n");
		    
		    System.out.println("---Re-attempting deletion of Renter---");
		    rsi.deleteRenter(authToken, renter.getIdentifier());
		    
		    System.out.println("---Attempting creation of Renter---");
		    rsi.createRenter(authToken, "Renter2.yaml");
		    
		    System.out.println("---Logging Out---");
		    asi.logOut("lucy");
		    System.out.println("---Calling a Service after Logging Out---");
		    rsi.getRenterList(authToken);
		    
		    System.out.println("\n---Generating Inventory output File---");
		    fileName = "AuthInventory_Updated.yaml";
		    inventory.generateOutputFile(AUTHTOKEN, fileName);
		    System.out.println("File created: " + fileName);
		    System.out.println("---Inventory output File Finished---\n");
		    
		    System.out.println("---LogIn with Provider User---");
		    user = asi.logIn("joe", "1234");
		    authToken = user.getSession().getAuthToken().toString();
		    
		    System.out.println("\n---Calling Provider Services---");
		    ProviderServiceImpl psi = ProviderServiceImpl.getInstance();
		    psi.createProvider(authToken, "Provider2.yaml");
		    
		    System.out.println("---Attempting Office Space Search---");
		    SearchEngineImpl sei = SearchEngineImpl.getInstance();
		    OfficeSpace officeSpace = new OfficeSpace();
		    Feature feature = new Feature();
		    feature.setName("WIFI");
		    Set<Feature> featureSet = new HashSet<Feature>();
		    featureSet.add(feature);
		    officeSpace.setFeature(featureSet);
		    sei.searchByFeature(authToken, officeSpace);
		    
		    System.out.println("\n---Adding Permission of: delete_renter to User---");
		    permission = new Permission();
		    permission.setEntitlementID("search_office_space_feature");
		    permission.setName("Searches an Office Space by a Feature");
		    permission.setDescription("Search Office Spaces by specifying a Feature.");
		    
		    service = asi.getService("admin", "provider_api_service");
		    asi.definePermission(authToken, service, permission);
		    asi.addEntitlement(authToken, user.getRole(), permission);
		    System.out.println("---Permission: " + permission.getEntitlementID() + " to User granted---\n");
		    
		    System.out.println("---Re-attempting Office Space Search---");
		    officeSpace = sei.searchByFeature(authToken, officeSpace);
		    
		    asi.logOut("joe");
		    
		} catch (RenterAlreadyExistException | AccessException | RenterNotFoundException | ProviderAlreadyExistException | SearchEngineException e) {
		    e.printStackTrace();
		}
	    } catch (AuthenticationException e) {
		e.printStackTrace();
	    }
	} else {
	    System.out.println("You have to specify an inputFileName.");
	} 
    }
}
