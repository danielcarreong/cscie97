/**
 * 
 */
package cscie97.asn2.sharedesk.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import cscie97.asn1.knowledge.engine.KnowledgeGraph;
import cscie97.asn4.squaredesk.authentication.AuthenticationException;
import cscie97.asn4.squaredesk.authentication.AuthenticationServiceImpl;

/**
 * Class implementation of OfficeSpaceService interface provided as a singleton pattern in order to keep
 * persistence of OfficeSpace records in a single instance
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class OfficeSpaceServiceImpl implements OfficeSpaceService {

    private static OfficeSpaceServiceImpl singleton = new OfficeSpaceServiceImpl();
    private Map<UUID, OfficeSpace> officeMap;
    //private static final String AUTHTOKEN = "admin";
    private KnowledgeGraph kg = KnowledgeGraph.getInstance();
    private AuthenticationServiceImpl asi = AuthenticationServiceImpl.getInstance();
    
    private String FEATURE = "has_feature";
    private String COMMONACCESS = "has_common_access";
    private String LAT_LONG = "has_lat_log";
    private String FACILITY = "has_facility_type_category";
    private String RATING = "has_average_rating";
    
    private OfficeSpaceServiceImpl() {
	officeMap = new HashMap<UUID, OfficeSpace>();
    }
    /**
     * @return ShareDeskService unique instance
     */
    public static OfficeSpaceServiceImpl getInstance() {
	return singleton;
    }
    /**
     * @param authToken
     * @param office
     * @return OfficeSpace
     * @throws OfficeSpaceAlreadyExistException 
     * @throws AccessException 
     */
    public OfficeSpace createOffice(String authToken, OfficeSpace office) throws OfficeSpaceAlreadyExistException, AccessException {
	
	if (authorization("create_office_space", authToken)) {
	    if (officeMap.containsValue(office)) {
		OfficeSpaceAlreadyExistException ex = new OfficeSpaceAlreadyExistException();
		ex.setDescription("Office Space name: " + office.getName() + " already exists in our records. Please define a different one.\n");
		throw ex;
	    }
	    else {
		officeMap.put(office.getIdentifier(), office);
		System.out.println("Office Space: '" + office.getName() + "' succesfully created.");
		updateKnowledgeGraph(office);
		return office;
	    }
	}
	return null;
    }
    /**
     * @param authToken
     * @param office
     * @return OfficeSpace
     * @throws AccessException 
     */
    public OfficeSpace updateOffice(String authToken, OfficeSpace office) throws AccessException {
	if (authorization("update_office_space", authToken)) {
	    if (officeMap.containsValue(office)) {
		// when updating OfficeSpaces: if OfficeSpace already exists, simply put instance in Map, this will
		// automatically update object as long it is referenced by the same Key (OfficeSpace ID)
		return officeMap.put(office.getIdentifier(), office);
	    } else
		return null;
	}
	return null;
    }
    /**
     * @param authToken
     * @param office
     * @throws OfficeSpaceNotFoundException 
     * @throws AccessException 
     */
    public void deleteOffice(String authToken, OfficeSpace office) throws OfficeSpaceNotFoundException, AccessException {
	
	if (authorization("delete_office_space", authToken)) {
	    if (!officeMap.containsValue(office) || office.getIdentifier().equals("")) {
		OfficeSpaceNotFoundException ex = new OfficeSpaceNotFoundException();
		ex.setDescription("Office Space: " + office.getName() + " was not found in our records.\n");
		throw ex;
	    } else {
		Iterator<Provider> providerItr = ProviderServiceImpl.getInstance().getProviderList(authToken).iterator();
		while(providerItr.hasNext()) {
		    Provider provider = (Provider)providerItr.next();
		    if (provider.getOfficeSpaceIdentifier() != null && provider.getOfficeSpaceIdentifier().equals(office.getIdentifier())) {
			System.out.println("Attempting deletion of Office Space: '" + office.getName() + "'");
			provider.setOfficeSpaceIdentifier(null);
			ProviderServiceImpl.getInstance().updateProvider(authToken, provider);
			officeMap.remove(office.getIdentifier());
			System.out.println("Deletion of Office Space: '" + office.getName() + "' completed.\n");
			break;
		    }
		}
	    }
	}
    }
    /**
     * @param authToken
     * @param identifier 
     * @return OfficeSpace
     * @throws OfficeSpaceNotFoundException 
     * @throws AccessException 
     */
    public OfficeSpace getOffice(String authToken, UUID identifier) throws OfficeSpaceNotFoundException, AccessException {
	
	if (authorization("get_office_space", authToken)) {
	    if (!officeMap.containsKey(identifier)) {
		OfficeSpaceNotFoundException ex = new OfficeSpaceNotFoundException();
		ex.setDescription("OfficeSpace with identifier: " + identifier + " does not exists in our records.\n");
		throw ex;
	    } else {
		return officeMap.get(identifier);
	    }
	}
	return null;
    }
    /**
     * @param authToken
     * @return List
     * @throws AccessException 
     */
    public List<OfficeSpace> getOfficeList(String authToken) throws AccessException {
	
	if (authorization("get_office_space_list", authToken)) {
	    if (officeMap.size() > 0)
		return new ArrayList<OfficeSpace> (officeMap.values());
	}
	return null;
    }
    
    private void updateKnowledgeGraph(OfficeSpace officeSpace) throws AccessException {
	if (officeSpace != null) {
	    String officeID = officeSpace.getIdentifier().toString();
	    // Populating KnowledgeGraph with OfficeSpace information
	    for (Iterator<OfficeSpace> itr = officeMap.values().iterator(); itr.hasNext();) {
		OfficeSpace office = (OfficeSpace) itr.next();
		// adding Facility Type and Category
		if (office.getFacility() != null) {
		    
		    String type = new String();
		    if (office.getFacility().getType().equals(FacilityType.HOME))
			type = "HOME";
		    else
			type = "GARAGE";
		    
		    String category = office.getFacility().getCategory();
		    if (type != null)
			if (category != null)
			    addKnowledgeNode(officeID, FACILITY, type + "_" + category);
			else
			    addKnowledgeNode(officeID, FACILITY, type);
		}
		// adding rating
		for (Iterator<Rating> ratingItrItr = office.getRatingList().iterator(); ratingItrItr.hasNext();) {
		    Rating rating = ratingItrItr.next();
		    addKnowledgeNode(officeID, RATING, String.valueOf(rating.getStars()));
		}
		// adding OfficeSpace latitude and longitude
		String lat = String.valueOf(Math.round(office.getLocation().getLat()));
		String lon = String.valueOf(Math.round(office.getLocation().getLon()));
		addKnowledgeNode(office.getIdentifier().toString(), LAT_LONG, lat + "_" + lon);
		// adding OfficeSpace CommonAccess
		for (Iterator<CommonAccess> commonAccessItr = office .getCommonAccess().iterator(); commonAccessItr.hasNext();) {
		    CommonAccess ca = commonAccessItr.next();
		    addKnowledgeNode(officeID, COMMONACCESS, ca.getName());
		}
		// adding OfficeSpace Features
		for (Iterator<Feature> featureItr = office.getFeature().iterator(); featureItr.hasNext();) {
		    Feature feature = featureItr.next();
		    addKnowledgeNode(officeID,FEATURE, feature.getName());
		}
	    }
	}
    }
    
    private void addKnowledgeNode(String subject, String predicate, String object) {
	kg.addTriplePermutation(kg.getTriple(
		    kg.getNode(subject),
		    kg.getPredicate(predicate),
		    kg.getNode(object)));
    }
    /**
     * 
     * @param authToken
     * @return
     * @throws AccessException
     */
    private boolean authorization(String serviceName, String authToken) throws AccessException {
	try {
	    return asi.checkAccess(serviceName, authToken);
	} catch (AuthenticationException e) {
	    e.printStackTrace();
	}
	return false;
    }
}
