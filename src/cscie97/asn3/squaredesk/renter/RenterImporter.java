/**
 * 
 */
package cscie97.asn3.squaredesk.renter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.yaml.snakeyaml.Yaml;

import cscie97.asn2.sharedesk.provider.AccessException;
import cscie97.asn2.sharedesk.provider.Account;
import cscie97.asn2.sharedesk.provider.Address;
import cscie97.asn2.sharedesk.provider.Facility;
import cscie97.asn2.sharedesk.provider.FacilityType;
import cscie97.asn2.sharedesk.provider.Feature;
import cscie97.asn2.sharedesk.provider.Image;
import cscie97.asn2.sharedesk.provider.ImportException;
import cscie97.asn2.sharedesk.provider.Location;
import cscie97.asn2.sharedesk.provider.OfficeSpace;
import cscie97.asn2.sharedesk.provider.Rating;
import cscie97.asn2.sharedesk.provider.Renter;

/**
 * Importer class that takes a YAML formatted document information and calls SquareDesk
 * services in order to store all data gathered into Renter class instances.
 * 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class RenterImporter {

    private static final String AUTHTOKEN = "admin";
    private static final String PROFILE = "renter";
    private static final String NAME = "name";
    private static final String CONTACT = "contact";
    private static final String PICTURE = "picture";
    private static final String ACCOUNT = "account";
    private static final String PAYPAL = "payPalAccountNumber";
    private static final String GENDER = "gender";
    private static final String RATINGS = "ratings";
    private static final String AUTHORSID = "authorsID";
    private static final String COMMENT = "comment";
    private static final String DATE = "date";
    private static final String STARS = "stars";
    private static final String SEARCHCRITERIA = "searchCriteria";
    private static final String CATEGORY = "category";
    private static final String STARTDATE = "startDate";
    private static final String ENDDATE = "endDate";
    private static final String FACILITY = "facility";
    private static final String FEATURE = "feature";
    private static final String LOCATION = "location";
    private static final String ADDRESS = "address";
    private static final String CITY = "city";
    private static final String COUNTRYCODE = "countryCode";
    private static final String LAT = "lat";
    private static final String LONG = "long";
    private static final String STATE = "state";
    private static final String STREET1 = "street1";
    private static final String STREET2 = "street2";
    private static final String ZIPCODE = "zipcode";
    private static final String MINAVGRATING = "minAvgRating";

    /**
     * @param fileName
     * @throws ImportException
     * @throws AccessException
     * @throws RenterException
     * @throws RenterAlreadyExistException
     */
    @SuppressWarnings("unchecked")
    public void importYamlFile(String fileName) throws ImportException,
	    AccessException, RenterException {

	RenterServiceImpl rsi = RenterServiceImpl.getInstance();

	ImportException ie = new ImportException();
	System.out.println("Importing Renter document ...");
	try (InputStream input = new FileInputStream(new File(fileName))) {

	    Yaml yaml = new Yaml();
	    Object yamlObj = yaml.load(input);
	    Renter renter = null;

	    if (!validInput(yamlObj)) {
		ie.setDescription("File is empty.");
		throw ie;
	    } else {
		LinkedHashMap<Object, Object> yamlMap = (LinkedHashMap<Object, Object>) yamlObj;

		if (yamlMap.size() == 0) {
		    ie.setDescription("YAML document is malformed.");
		    throw ie;
		} else {
		    LinkedHashMap<Object, Object> data = (LinkedHashMap<Object, Object>) yamlMap.get(PROFILE);

		    if (!validInput(data)) {
			ie.setDescription("User role is different from Renter.");
			throw ie;
		    } else {
			// Renter
			renter = new Renter();

			if (validInput(data.get(NAME)))
			    renter.setName(data.get(NAME).toString());

			if (validInput(data.get(CONTACT)))
			    renter.setContact(data.get(CONTACT).toString());

			if (validInput(data.get(GENDER)))
			    renter.setGender(data.get(GENDER).toString());

			if (validInput(data.get(PICTURE))) {
			    Image picture = new Image();
			    picture.setURI(data.get(PICTURE).toString());
			    renter.setPicture(picture);
			}
			// Account
			LinkedHashMap<Object, Object> accountMap = (LinkedHashMap<Object, Object>) data.get(ACCOUNT);
			if (validInput(accountMap)) {
			    Account account = new Account();
			    account.setPayPal(accountMap.get(PAYPAL).toString());
			    renter.setAccount(account);
			}
			// Ratings List
			ArrayList<LinkedHashMap<String, Object>> ratingsArray = (ArrayList<LinkedHashMap<String, Object>>) data.get(RATINGS);
			if (validInput(ratingsArray)) {
			    Iterator<LinkedHashMap<String, Object>> ratingsItr = ratingsArray.iterator();
			    List<Rating> ratingList = new ArrayList<Rating>();
			    while (ratingsItr.hasNext()) {
				// Rating
				LinkedHashMap<String, Object> ratingMap = ratingsItr.next();
				if (validInput(ratingMap)) {
				    Rating rating = new Rating();
				    if (validInput(ratingMap.get(AUTHORSID)))
					rating.setAuthorsId(ratingMap.get(AUTHORSID).toString());

				    if (validInput(ratingMap.get(COMMENT)))
					rating.setComment(ratingMap.get(COMMENT).toString());

				    if (validInput(ratingMap.get(DATE)))
					rating.setDate(Date.valueOf(ratingMap.get(DATE).toString()));

				    if (validInput(ratingMap.get(STARS)))
					rating.setStars(Integer.valueOf(ratingMap.get(STARS).toString()));

				    ratingList.add(rating);
				}
			    }
			    renter.setRatings(ratingList);
			}

			OfficeSpace officeToFind = null;

			// Search Criteria
			LinkedHashMap<Object, Object> searchCriteriaMap = (LinkedHashMap<Object, Object>) data.get(SEARCHCRITERIA);
			if (validInput(searchCriteriaMap)) {
			    officeToFind = new OfficeSpace();
			    // Start Date
			    if (validInput(searchCriteriaMap.get(STARTDATE))) {
				//System.out.println(searchCriteriaMap.get(STARTDATE));
			    }
			    // End Date
			    if (validInput(searchCriteriaMap.get(ENDDATE)))
				//System.out.println(searchCriteriaMap.get(ENDDATE));
			    // Facility and Category
			    if (validInput(searchCriteriaMap.get(FACILITY))) {
				Facility facility = new Facility();
				if (searchCriteriaMap.get(FACILITY).toString().equals("HOME"))
				    facility.setType(FacilityType.HOME);
				else
				    facility.setType(FacilityType.HOME);

				if (validInput(searchCriteriaMap.get(CATEGORY))) {
				    facility.setCategory(searchCriteriaMap.get(CATEGORY).toString());
				    officeToFind.setFacility(facility);
				}
				officeToFind.setFacility(facility);
			    } else {
				if (validInput(searchCriteriaMap.get(CATEGORY))) {
				    Facility facility = new Facility();
				    facility.setCategory(searchCriteriaMap.get(CATEGORY).toString());
				    officeToFind.setFacility(facility);
				}
			    }
			    // Minimum Average Rating
			    if (validInput(searchCriteriaMap.get(MINAVGRATING))) {
				Rating rating = new Rating();
				rating.setStars(Integer.valueOf(searchCriteriaMap.get(MINAVGRATING).toString()));
				List<Rating> ratingList = new ArrayList<Rating>();
				ratingList.add(rating);
				officeToFind.setRatingList(ratingList);
			    }
			    // Features
			    ArrayList<String> featureArray = (ArrayList<String>) searchCriteriaMap.get(FEATURE);
			    if (validInput(featureArray)) {
				Iterator<String> featureItr = featureArray.iterator();
				Set<Feature> featureList = new HashSet<Feature>();
				while (featureItr.hasNext()) {
				    String strFeature = featureItr.next();
				    if (validInput(strFeature)) {
					Feature feature = new Feature();
					feature.setName(strFeature);
					featureList.add(feature);
				    }
				}
				officeToFind.setFeature(featureList);
			    }
			    // Location
			    LinkedHashMap<String, Object> locationMap = (LinkedHashMap<String, Object>) searchCriteriaMap.get(LOCATION);
			    if (locationMap != null && locationMap.size() > 0) {
				Location location = new Location();
				// Latitude
				if (validInput(locationMap.get(LAT)))
				    location.setLat(Double.parseDouble(String.valueOf(locationMap.get(LAT))));
				// Longitude
				if (validInput(locationMap.get(LONG)))
				    location.setLon(Double.parseDouble(String.valueOf(locationMap.get(LONG))));
				// Address
				LinkedHashMap<String, String> addressMap = (LinkedHashMap<String, String>) locationMap.get(ADDRESS);
				if (addressMap != null && addressMap.size() > 0) {
				    // City
				    Address address = new Address();
				    if (validInput(addressMap.get(CITY)))
					address.setCity(addressMap.get(CITY));
				    // Country Code
				    if (validInput(addressMap.get(COUNTRYCODE)))
					address.setCountryCode(addressMap.get(COUNTRYCODE));
				    // State
				    if (validInput(addressMap.get(STATE)))
					address.setState(addressMap.get(STATE));
				    // Street 1
				    if (validInput(addressMap.get(STREET1)))
					address.setStreet1(addressMap.get(STREET1));
				    // Street 2
				    if (validInput(addressMap.get(STREET2)))
					address.setStreet2(addressMap.get(STREET2).toString());
				    // ZipCode
				    if (validInput(addressMap.get(ZIPCODE)))
					address.setZipCode(String.valueOf(addressMap.get(ZIPCODE)));
				    location.setAddress(address);
				}
				officeToFind.setLocation(location);
			    }
			}

			rsi.createRenter(AUTHTOKEN, renter);
			System.out.println("Document imported succesfully.\n");
			
			if (officeToFind != null) {
			    SearchEngineImpl se = SearchEngineImpl.getInstance();
			    try {
				System.out.println("---Calling SearchEngine services---\n");
				System.out.println("--Search by Feature--");
				se.searchByFeature(AUTHTOKEN, officeToFind);

				System.out.println("--Search by Facility Category--");
				se.searchByFacilityCategory(AUTHTOKEN, officeToFind);

				System.out.println("--Search by Location--");
				se.searchByLocation(AUTHTOKEN, officeToFind);
				
				System.out.println("--Search by Rating--");
				se.searchByRating(AUTHTOKEN, officeToFind);
				
				System.out.println("---Finishing SearchEngine services---.\n");

			    } catch (SearchEngineException e) {
				e.printStackTrace();
			    }
			}
		    }
		}
	    }
	} catch (ImportException e) {
	    throw e;
	} catch (IOException e) {
	    e.printStackTrace();
	    ie.setDescription(e.getMessage());
	    throw ie;
	} catch (RenterAlreadyExistException e) {
	    throw e;
	} catch (AccessException e) {
	    throw e;
	}
    }

    @SuppressWarnings("unchecked")
    private boolean validInput(Object obj) {

	if (obj instanceof String) {
	    String strObj = obj.toString();
	    if (strObj != null && strObj.length() > 0)
		return true;
	} else if (obj instanceof Map) {
	    Map<Object, Object> mapObj = (Map<Object, Object>) obj;
	    if (mapObj != null && mapObj.size() > 0)
		return true;
	} else if (obj instanceof AbstractList) {
	    ArrayList<Object> listObj = (ArrayList<Object>) obj;
	    if (listObj != null && listObj.size() > 0)
		return true;
	} else if (obj != null)
	    return true;

	return false;
    }
}
