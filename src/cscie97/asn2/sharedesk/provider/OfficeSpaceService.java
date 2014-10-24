/**
 * 
 */
package cscie97.asn2.sharedesk.provider;

import java.util.List;
import java.util.UUID;

/**
 * OfficeSpace Services interface 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public interface OfficeSpaceService {

    /**
     * @param authToken
     * @param office
     * @return OfficeSpace
     * @throws OfficeSpaceAlreadyExistException
     * @throws AccessException
     */
    public OfficeSpace createOffice(String authToken, OfficeSpace office) throws OfficeSpaceAlreadyExistException, AccessException;
    /**
     * @param authToken
     * @param office
     * @return OfficeSpace
     * @throws AccessException
     */
    public OfficeSpace updateOffice(String authToken, OfficeSpace office) throws AccessException;
    /**
     * @param authToken
     * @param office
     * @throws OfficeSpaceNotFoundException
     * @throws AccessException
     */
    public void deleteOffice(String authToken, OfficeSpace office) throws OfficeSpaceNotFoundException, AccessException;
    /**
     * @param authToken
     * @param identifier
     * @return OfficeSpace
     * @throws OfficeSpaceNotFoundException
     * @throws AccessException
     */
    public OfficeSpace getOffice(String authToken, UUID identifier) throws OfficeSpaceNotFoundException, AccessException;
    /**
     * @param authToken
     * @return List
     * @throws AccessException
     */
    public List<OfficeSpace> getOfficeList(String authToken) throws AccessException;
}
