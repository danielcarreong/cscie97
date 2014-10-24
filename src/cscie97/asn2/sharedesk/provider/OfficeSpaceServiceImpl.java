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

/**
 * Class implementation of OfficeSpaceService interface provided as a singleton pattern in order to keep
 * persistence of OfficeSpace records in a single instance
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class OfficeSpaceServiceImpl implements OfficeSpaceService {

    private static OfficeSpaceServiceImpl singleton = new OfficeSpaceServiceImpl();
    private Map<UUID, OfficeSpace> officeMap;
    private Set<CommonAccess> commonAccessSet;
    private Set<Feature> featureSet;
    private static final String AUTHTOKEN = "admin";
    
    private OfficeSpaceServiceImpl() {
	officeMap = new HashMap<UUID, OfficeSpace>();
	commonAccessSet = new HashSet<CommonAccess>();
	featureSet = new HashSet<Feature>();
    }
    
    /**
     * @return ShareDeskService unique instance
     */
    public static OfficeSpaceServiceImpl getInstance() {
	return singleton;
    }
    /**
     * @return the commonAccessSet
     */
    public Set<CommonAccess> getCommonAccessSet() {
	return commonAccessSet;
    }

    /**
     * @param commonAccessSet the commonAccessSet to set
     */
    public void setCommonAccessSet(Set<CommonAccess> commonAccessSet) {
	this.commonAccessSet = commonAccessSet;
    }

    /**
     * @return the featureSet
     */
    public Set<Feature> getFeatureSet() {
	return featureSet;
    }

    /**
     * @param featureSet the featureSet to set
     */
    public void setFeatureSet(Set<Feature> featureSet) {
	this.featureSet = featureSet;
    }
    /**
     * @param authToken
     * @param office
     * @return OfficeSpace
     * @throws OfficeSpaceAlreadyExistException 
     * @throws AccessException 
     */
    public OfficeSpace createOffice(String authToken, OfficeSpace office) throws OfficeSpaceAlreadyExistException, AccessException {
	
	if (authorization(authToken)) {
	    if (officeMap.containsValue(office)) {
		OfficeSpaceAlreadyExistException ex = new OfficeSpaceAlreadyExistException();
		ex.setDescription("Office Space name: " + office.getName() + " already exists in our records. Please define a different one.\n");
		throw ex;
	    }
	    else {
		officeMap.put(office.getIdentifier(), office);
		System.out.println("Office Space: '" + office.getName() + "' succesfully created.");
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

	if (authorization(authToken)) {
	    if (officeMap.containsValue(office)) {
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
	
	if (authorization(authToken)) {
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
	
	if (authorization(authToken)) {
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
	
	if (authorization(authToken)) {
	    if (officeMap.size() > 0)
		return new ArrayList<OfficeSpace> (officeMap.values());
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
