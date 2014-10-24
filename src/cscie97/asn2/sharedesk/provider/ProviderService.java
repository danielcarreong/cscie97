/**
 * 
 */
package cscie97.asn2.sharedesk.provider;

import java.util.List;
import java.util.UUID;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public interface ProviderService {

    /**
     * @param authToken
     * @param provider
     * @return Provider
     * @throws ProviderAlreadyExistException
     * @throws AccessException
     */
    public Provider createProvider(String authToken, Provider provider) throws ProviderAlreadyExistException, AccessException;
    /**
     * @param authToken
     * @param provider
     * @return Provider
     * @throws AccessException
     */
    public Provider updateProvider(String authToken, Provider provider) throws AccessException;
    /**
     * @param authToken
     * @param providerID 
     * @throws ProviderNotFoundException
     * @throws AccessException
     */
    public void deleteProvider(String authToken, UUID providerID) throws ProviderNotFoundException, AccessException;
    /**
     * @param authToken
     * @param identifier
     * @return Provider
     * @throws ProviderNotFoundException
     * @throws AccessException
     */
    public Provider getProvider(String authToken, UUID identifier) throws ProviderNotFoundException, AccessException;
    /**
     * @param authToken
     * @return List
     * @throws AccessException
     */
    public List<Provider> getProviderList(String authToken) throws AccessException;
}
