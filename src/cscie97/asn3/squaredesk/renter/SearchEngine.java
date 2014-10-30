/**
 * 
 */
package cscie97.asn3.squaredesk.renter;

import java.util.Date;

import cscie97.asn2.sharedesk.provider.OfficeSpace;

/**
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
    public OfficeSpace searchByRate(OfficeSpace officeSpace)
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
