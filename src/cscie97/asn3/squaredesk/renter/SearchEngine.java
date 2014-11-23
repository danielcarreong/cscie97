/**
 * 
 */
package cscie97.asn3.squaredesk.renter;

import cscie97.asn2.sharedesk.provider.AccessException;
import cscie97.asn2.sharedesk.provider.OfficeSpace;

/**
 * SearchEngine interface defining search services used to retrieve OfficeSpace information.
 * 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public interface SearchEngine {
    /**
     * @param authToken 
     * @param officeSpace
     * @return OfficeSpace
     * @throws SearchEngineException
     * @throws AccessException 
     */
    public OfficeSpace searchByFeature(String authToken, OfficeSpace officeSpace) throws SearchEngineException, AccessException;

    /**
     * @param authToken 
     * @param officeSpace
     * @return OfficeSpace
     * @throws SearchEngineException
     * @throws AccessException 
     */
    public OfficeSpace searchByLocation(String authToken, OfficeSpace officeSpace) throws SearchEngineException, AccessException;

    /**
     * @param authToken 
     * @param officeSpace
     * @return OfficeSpace
     * @throws SearchEngineException
     * @throws AccessException 
     */
    public OfficeSpace searchByFacilityCategory(String authToken, OfficeSpace officeSpace) throws SearchEngineException, AccessException;

    /**
     * @param authToken 
     * @param officeSpace
     * @return OfficeSpace
     * @throws SearchEngineException
     * @throws AccessException 
     */
    public OfficeSpace searchByRating(String authToken, OfficeSpace officeSpace) throws SearchEngineException, AccessException;

    /**
     * @param authToken 
     * @param officeSpace
     * @return OfficeSpace
     * @throws SearchEngineException
     * @throws AccessException 
     */
    public OfficeSpace searchByDate(String authToken, OfficeSpace officeSpace) throws SearchEngineException, AccessException;

    /**
     * @param authToken 
     * @param officeSpace
     * @return OfficeSpace
     * @throws SearchEngineException
     * @throws AccessException 
     */
    public OfficeSpace searchByCommonAccess(String authToken, OfficeSpace officeSpace) throws SearchEngineException, AccessException;
}
