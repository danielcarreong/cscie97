/**
 * 
 */
package cscie97.asn3.squaredesk.renter;

import java.util.Iterator;
import java.util.UUID;

import cscie97.asn1.knowledge.engine.QueryEngineException;
import cscie97.asn1.knowledge.engine.QueryEngine;
import cscie97.asn2.sharedesk.provider.AccessException;
import cscie97.asn2.sharedesk.provider.CommonAccess;
import cscie97.asn2.sharedesk.provider.FacilityType;
import cscie97.asn2.sharedesk.provider.Feature;
import cscie97.asn2.sharedesk.provider.OfficeSpace;
import cscie97.asn2.sharedesk.provider.OfficeSpaceNotFoundException;
import cscie97.asn2.sharedesk.provider.OfficeSpaceServiceImpl;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class SearchEngineImpl implements SearchEngine {

    private static SearchEngineImpl singleton = new SearchEngineImpl();
    private static final String AUTHTOKEN = "admin";
    QueryEngine queryEngine = new QueryEngine();

    private String FEATURE = " has_feature ";
    private String COMMONACCESS = " has_common_access ";
    private String LAT_LONG = " has_lat_log ";
    private String FACILITY = " has_facility_type_category ";
    private String RATING = " has_average_rating ";

    private SearchEngineImpl() {
    };

    /**
     * @return SearchEngineImpl single instance
     */
    public static SearchEngineImpl getInstance() {
	return singleton;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cscie97.asn3.squaredesk.renter.SearchEngine#searchByFeature(java.lang
     * .String)
     */
    @Override
    public OfficeSpace searchByFeature(OfficeSpace officeSpace)
	    throws SearchEngineException {
	for (Iterator<Feature> featureItr = officeSpace.getFeature().iterator(); featureItr
		.hasNext();) {
	    String query = "?" + FEATURE + (featureItr.next()).getName();
	    UUID officeIDFound = executeSearch(query);
	    if (officeIDFound != null) {
		try {
		    return OfficeSpaceServiceImpl.getInstance().getOffice(
			    AUTHTOKEN, officeIDFound);
		} catch (OfficeSpaceNotFoundException | AccessException e) {
		    e.printStackTrace();
		}
	    }
	}
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cscie97.asn3.squaredesk.renter.SearchEngine#searchByLocation(double,
     * double)
     */
    @Override
    public OfficeSpace searchByLocation(OfficeSpace officeSpace)
	    throws SearchEngineException {
	String lat = String.valueOf(Math.round(officeSpace.getLocation()
		.getLat()));
	String lon = String.valueOf(Math.round(officeSpace.getLocation()
		.getLon()));
	String query = "?" + LAT_LONG + lat + "_" + lon;
	UUID officeIDFound = executeSearch(query);
	if (officeIDFound != null) {
	    try {
		return OfficeSpaceServiceImpl.getInstance().getOffice(
			AUTHTOKEN, officeIDFound);
	    } catch (OfficeSpaceNotFoundException | AccessException e) {
		e.printStackTrace();
	    }
	}
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cscie97.asn3.squaredesk.renter.SearchEngine#searchByFacilityCategory(
     * java.lang.String)
     */
    @Override
    public OfficeSpace searchByFacilityCategory(OfficeSpace officeSpace)
	    throws SearchEngineException {
	if (officeSpace.getFacility() != null) {
	    String facility = new String();
	    if (officeSpace.getFacility().getType().equals(FacilityType.HOME))
		facility = "HOME";
	    else
		facility = "GARAGE";
	    String category = officeSpace.getFacility().getCategory();
	    String query;
	    if (facility != null)
		if (category != null)
		    query = "?" + FACILITY + facility + "_" + category;
		else
		    query = "?" + FACILITY + facility;
	    else
		return null;
	    System.out.println(query);
	    UUID officeIDFound = executeSearch(query);
	    if (officeIDFound != null) {
		try {
		    return OfficeSpaceServiceImpl.getInstance().getOffice(
			    AUTHTOKEN, officeIDFound);
		} catch (OfficeSpaceNotFoundException | AccessException e) {
		    e.printStackTrace();
		}
	    }
	}
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cscie97.asn3.squaredesk.renter.SearchEngine#searchByRate(int)
     */
    @Override
    public OfficeSpace searchByRate(OfficeSpace officeSpace)
	    throws SearchEngineException {
	// TODO Auto-generated method stub
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cscie97.asn3.squaredesk.renter.SearchEngine#searchByDate(java.util.Date,
     * java.util.Date)
     */
    @Override
    public OfficeSpace searchByDate(OfficeSpace officeSpace)
	    throws SearchEngineException {
	// TODO Auto-generated method stub
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cscie97.asn3.squaredesk.renter.SearchEngine#searchByCommonAccess(cscie97
     * .asn2.sharedesk.provider.OfficeSpace)
     */
    @Override
    public OfficeSpace searchByCommonAccess(OfficeSpace officeSpace)
	    throws SearchEngineException {
	for (Iterator<CommonAccess> commonAccessItr = officeSpace
		.getCommonAccess().iterator(); commonAccessItr.hasNext();) {
	    String query = "?" + COMMONACCESS
		    + (commonAccessItr.next()).getName();
	    UUID officeIDFound = executeSearch(query);
	    if (officeIDFound != null) {
		try {
		    return OfficeSpaceServiceImpl.getInstance().getOffice(
			    AUTHTOKEN, officeIDFound);
		} catch (OfficeSpaceNotFoundException | AccessException e) {
		    e.printStackTrace();
		}
	    }
	}
	return null;
    }

    private UUID executeSearch(String query) {
	try {
	    String officeIDFound = queryEngine.executeQuery((query + "."));
	    if (officeIDFound != null && officeIDFound.length() > 0) {
		return UUID.fromString(officeIDFound);
	    }
	} catch (QueryEngineException e) {
	    e.printStackTrace();
	}
	return null;
    }
}
