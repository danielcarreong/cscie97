/**
 * 
 */
package cscie97.asn3.squaredesk.renter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cscie97.asn2.sharedesk.provider.AccessException;
import cscie97.asn2.sharedesk.provider.ImportException;
import cscie97.asn3.squaredesk.renter.RenterImporter;
import cscie97.asn2.sharedesk.provider.Renter;

/**
 * RenterService implementation class. Defines business logic for Renter CRUD
 * services. Only one instance of the class can exist to preserve information.
 * 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class RenterServiceImpl implements RenterService {

    private static RenterServiceImpl singleton = new RenterServiceImpl();
    private Map<UUID, Renter> renterMap;
    private static final String AUTHTOKEN = "admin";

    private RenterServiceImpl() {
	renterMap = new HashMap<UUID, Renter>();
    };

    /**
     * @return RenterServiceImpl single instance
     */
    public static RenterServiceImpl getInstance() {
	return singleton;
    }

    private void importRenterFile(String fileName) throws ImportException,
	    AccessException, RenterException {
	RenterImporter importer = new RenterImporter();
	importer.importYamlFile(fileName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cscie97.asn3.squaredesk.renter.RenterService#createRenter(java.lang.String
     * , cscie97.asn2.sharedesk.provider.Renter)
     */
    @Override
    public Renter createRenter(String authToken, Renter renter)
	    throws RenterAlreadyExistException, AccessException {
	if (authorization(authToken)) {
	    if (renterMap.containsValue(renter)) {
		RenterAlreadyExistException ex = new RenterAlreadyExistException();
		ex.setDescription("Renter name: "
			+ renter.getName()
			+ " already exists in our records. Please define a different one.\n");
		throw ex;
	    } else {
		renterMap.put(renter.getIdentifier(), renter);
		System.out.println("Renter: '" + renter.getName()
			+ "' succesfully created.");
		return renter;
	    }
	}
	return null;
    }

    /**
     * @param authToken
     * @param renterInputFile
     * @return Remter
     * @throws RenterAlreadyExistException
     * @throws AccessException
     */
    public Renter createRenter(String authToken, String renterInputFile) throws RenterAlreadyExistException, AccessException {

	if (authorization(authToken)) {
	    try {
		importRenterFile(renterInputFile);
	    } catch (ImportException | RenterException e) {
		e.printStackTrace();
	    }
	}
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cscie97.asn3.squaredesk.renter.RenterService#updateRenter(java.lang.String
     * , cscie97.asn2.sharedesk.provider.Renter)
     */
    @Override
    public Renter updateRenter(String authToken, Renter renter) throws AccessException {

	if (authorization(authToken)) {
	    if (renterMap.containsValue(renter)) {
		return renterMap.put(renter.getIdentifier(), renter);
	    } else
		return null;
	}
	return null;
    }

    /**
     * @param authToken
     * @param oldRenter
     * @param newRenterInputFileName
     * @throws AccessException
     * @throws ImportException
     * @throws RenterException
     */
    public void updateRenter(String authToken, Renter oldRenter, String newRenterInputFileName) throws AccessException, ImportException, RenterException {

	if (authorization(authToken)) {
	    if (renterMap.containsValue(oldRenter)) {
		deleteRenter(authToken, oldRenter.getIdentifier());
		importRenterFile(newRenterInputFileName);
	    }
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cscie97.asn3.squaredesk.renter.RenterService#deleteRenter(java.lang.String
     * , java.util.UUID)
     */
    @Override
    public void deleteRenter(String authToken, UUID renterID) throws RenterNotFoundException, AccessException {

	if (authorization(authToken)) {
	    if (!renterMap.containsKey(renterID)) {
		RenterNotFoundException ex = new RenterNotFoundException();
		ex.setDescription("Renter ID: '" + renterID + "' was not found in our records.\n");
		throw ex;
	    } else {
		System.out.println("Attempting deletion of Renter ID: '" + renterID + "'");
		renterMap.remove(renterID);
		System.out.println("Deletion of Renter ID: '" + renterID + "' completed.\n");
	    }
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cscie97.asn3.squaredesk.renter.RenterService#getRenter(java.lang.String,
     * java.util.UUID)
     */
    @Override
    public Renter getRenter(String authToken, UUID renterID) throws RenterNotFoundException, AccessException {
	if (authorization(authToken)) {
	    if (!renterMap.containsKey(renterID)) {
		RenterNotFoundException ex = new RenterNotFoundException();
		ex.setDescription("Renter ID: " + renterID
			+ " does not exists in our records.\n");
		throw ex;
	    } else {
		return renterMap.get(renterID);
	    }
	}
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cscie97.asn3.squaredesk.renter.RenterService#getRenterList(java.lang.
     * String)
     */
    @Override
    public List<Renter> getRenterList(String authToken) throws AccessException {
	if (authorization(authToken)) {
	    if (renterMap.size() > 0)
		return new ArrayList<Renter>(renterMap.values());
	}
	return null;
    }

    /**
     * 
     * @param authToken
     * @return
     * @throws AccessException
     */
    private boolean authorization(String authToken) throws AccessException {

	AccessException accessEx = new AccessException();
	if (authToken == null || authToken.length() == 0) {
	    accessEx.setDescription("An Authorization Token must be specified.\n");
	    throw accessEx;
	} else if (!authToken.equalsIgnoreCase(AUTHTOKEN)) {
	    accessEx.setDescription("Authorization Token is invalid.\n");
	    throw accessEx;
	} else
	    return true;
    }
}
