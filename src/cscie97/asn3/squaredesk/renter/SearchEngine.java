/**
 * 
 */
package cscie97.asn3.squaredesk.renter;

import cscie97.asn2.sharedesk.provider.OfficeSpace;

/**
 * SearchEngine interface defining search services used to retrieve OfficeSpace information.
 * 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public interface SearchEngine {
    /**
     * @param officeSpace
     * @return OfficeSpace
     * @throws SearchEngineException
     */
    public OfficeSpace searchByFeature(OfficeSpace officeSpace)
	    throws SearchEngineException;

    /**
     * @param officeSpace
     * @return OfficeSpace
     * @throws SearchEngineException
     */
    public OfficeSpace searchByLocation(OfficeSpace officeSpace)
	    throws SearchEngineException;

    /**
     * @param officeSpace
     * @return OfficeSpace
     * @throws SearchEngineException
     */
    public OfficeSpace searchByFacilityCategory(OfficeSpace officeSpace)
	    throws SearchEngineException;

    /**
     * @param officeSpace
     * @return OfficeSpace
     * @throws SearchEngineException
     */
    public OfficeSpace searchByRating(OfficeSpace officeSpace)
	    throws SearchEngineException;

    /**
     * @param officeSpace
     * @return OfficeSpace
     * @throws SearchEngineException
     */
    public OfficeSpace searchByDate(OfficeSpace officeSpace)
	    throws SearchEngineException;

    /**
     * @param officeSpace
     * @return OfficeSpace
     * @throws SearchEngineException
     */
    public OfficeSpace searchByCommonAccess(OfficeSpace officeSpace)
	    throws SearchEngineException;
}
