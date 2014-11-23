/**
 * 
 */
package cscie97.asn2.sharedesk.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cscie97.asn4.squaredesk.authentication.AuthenticationException;
import cscie97.asn4.squaredesk.authentication.AuthenticationServiceImpl;


/**
 * Provides implementation for ProviderService interface. Implements singleton pattern for single instantiation
 * in order to keep data persisted.
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class ProviderServiceImpl implements ProviderService {
    
    private static ProviderServiceImpl singleton = new ProviderServiceImpl();
    private Map<UUID, Provider> providerMap;
    //private static final String AUTHTOKEN = "admin";
    
    private ProviderServiceImpl() {
	providerMap = new HashMap<UUID, Provider>();
    }
    /**
     * @return ShareDeskService unique instance
     */
    public static ProviderServiceImpl getInstance() {
	return singleton;
    }
    /**
     * @param fileName
     * @throws ImportException
     * @throws AccessException
     * @throws ProviderException
     * @throws OfficeSpaceException
     */
    private void importProviderFile(String fileName) throws ImportException, AccessException, ProviderException, OfficeSpaceException {
	Importer importer = new Importer();
	importer.importYamlFile(fileName);
    }
    /**
     * @param authToken
     * @param provider 
     * @return Provider
     * @throws ProviderAlreadyExistException 
     * @throws AccessException 
     * @throws AccessException 
     */
    public Provider createProvider(String authToken, Provider provider) throws ProviderAlreadyExistException, AccessException {
	
	if (authorization("create_provider", authToken)) {
	    if (providerMap.containsValue(provider)) {
		ProviderAlreadyExistException ex = new ProviderAlreadyExistException();
		ex.setDescription("Provider name: " + provider.getName() + " already exists in our records. Please define a different one.\n");
		throw ex;
	    }
	    else {
		providerMap.put(provider.getIdentifier(), provider);
		System.out.println("Provider: '" + provider.getName() + "' succesfully created.");
		return provider;
	    }
	}
	return null;
    }
    /**
     * @param authToken
     * @param providerInputFile
     * @return Provider
     * @throws ProviderAlreadyExistException
     * @throws AccessException
     */
    public Provider createProvider(String authToken, String providerInputFile) throws ProviderAlreadyExistException, AccessException {
	
	if (authorization("create_provider", authToken)) {
	    try {
		importProviderFile(providerInputFile);
	    } catch (ImportException | ProviderException | OfficeSpaceException e) {
		e.printStackTrace();
	    }
	}
	return null;
    }
    /**
     * @param authToken 
     * @param provider 
     * @return Provider 
     * @throws AccessException   
     * @throws AccessException   
     */
    public Provider updateProvider(String authToken, Provider provider) throws AccessException {
	
	if (authorization("update_provider", authToken)) {
	    if (providerMap.containsValue(provider)) {
		//deleteProvider(authToken, oldProvider);
		//return createProvider(authToken, newProvider);
		return providerMap.put(provider.getIdentifier(), provider);
	    } else
		return null;
	}
	return null;
    }
    
    /**
     * @param authToken
     * @param oldProvider
     * @param newProviderInputFileName
     * @throws AccessException
     * @throws OfficeSpaceException 
     * @throws ProviderException 
     * @throws ImportException 
     */
    public void updateProvider(String authToken, Provider oldProvider, String newProviderInputFileName) throws AccessException, ImportException, ProviderException, OfficeSpaceException {
	
	if (authorization("update_provider", authToken)) {
	    if (providerMap.containsValue(oldProvider)) {
		deleteProvider(authToken, oldProvider.getIdentifier());
		importProviderFile(newProviderInputFileName);
	    }
	}
    }

    /**
     * @param authToken
     * @param providerID 
     * @throws ProviderNotFoundException 
     * @throws AccessException 
     */
    public void deleteProvider(String authToken, UUID providerID) throws ProviderNotFoundException, AccessException {
	
	if (authorization("delete_provider", authToken)) {
	    if (!providerMap.containsKey(providerID)) {
		ProviderNotFoundException ex = new ProviderNotFoundException();
		ex.setDescription("Provider ID: " + providerID + " was not found in our records.\n");
		throw ex;
	    } else {
		try {
		    OfficeSpace office = OfficeSpaceServiceImpl.getInstance().getOffice(authToken, (getProvider(authToken, providerID)).getOfficeSpaceIdentifier());
		    if (office != null) {
			System.out.println("Attempting deletion of Provider's Office Space: '" + office.getName() + "'");
			OfficeSpaceServiceImpl.getInstance().deleteOffice(authToken, office);
			System.out.println("Attempting deletion of Provider: '" + providerID + "'");
			providerMap.remove(providerID);
			System.out.println("Deletion of Provider ID: '" + providerID + "' completed.\n");
		    } else {
			if (getProvider(authToken, providerID) != null) {
			    System.out.println("Attempting deletion of Provider: '" + providerID + "'");
			    providerMap.remove(providerID);
			    System.out.println("Deletion of Provider ID: '" + providerID + "' completed.\n");
			}
		    }
		} catch (OfficeSpaceNotFoundException e) {
		    e.printStackTrace();
		}
	    }
	}
    }
    /**
     * @param authToken
     * @param identifier 
     * @return Provider
     * @throws ProviderNotFoundException 
     * @throws AccessException 
     */
    public Provider getProvider(String authToken, UUID identifier) throws ProviderNotFoundException, AccessException {
	
	if (authorization("get_provider", authToken)) {
	    if (!providerMap.containsKey(identifier)) {
		ProviderNotFoundException ex = new ProviderNotFoundException();
		ex.setDescription("Identifier: " + identifier + " does not exists in our records.\n");
		throw ex;
	    } else {
		return (Provider) providerMap.get(identifier);
	    }
	}
	return null;
    }
    /**
     * @param authToken
     * @return List
     * @throws AccessException 
     */
    public List<Provider> getProviderList(String authToken) throws AccessException {
	
	if (authorization("get_provider_list", authToken)) {
	    if (providerMap.size() > 0)
		return new ArrayList<Provider> (providerMap.values());
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
