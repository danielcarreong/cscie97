/**
 * 
 */
package cscie97.asn2.sharedesk.provider;

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
import java.util.UUID;

import org.yaml.snakeyaml.Yaml;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class Importer {
    
    private static final String AUTHTOKEN = "admin";
    private static final String PROFILE = "provider";
    private static final String NAME = "name";
    private static final String CONTACT = "contact";
    private static final String PICTURE = "picture";
    private static final String ACCOUNT = "account";
    private static final String PAYPAL = "payPalAccountNumber";
    private static final String OFFICESPACES = "officeSpaces";
    private static final String OFFICESPACE = "officeSpace";
    private static final String CAPACITY = "capacity";
    private static final String NUMBEROFPEOPLE = "numberOfPeople";
    private static final String SQUAREFEET = "squarefeet";
    private static final String WORKSPACES = "workSpaces";
    private static final String COMMONACCESS = "commonAccess";
    private static final String FACILITY = "facility";
    private static final String CATEGORY = "category";
    private static final String TYPE = "type";
    private static final String FEATURES = "features";
    private static final String IMAGES = "images";
    private static final String DESCRIPTION = "description";
    private static final String URI = "URI";
    private static final String LOCATION = "location";
    private static final String LAT = "lat";
    private static final String LONG = "long";
    private static final String ADDRESS = "address";
    private static final String COUNTRYCODE = "countryCode";
    private static final String STATE = "state";
    private static final String STREET1 = "street1";
    private static final String STREET2 = "street2";
    private static final String ZIPCODE = "zipcode";
    private static final String RATES = "rates";
    private static final String COST = "cost";
    private static final String PERIOD = "period";
    private static final String RATINGS = "ratings";
    private static final String AUTHORSID = "authorsId";
    private static final String COMMENT = "comment";
    private static final String DATE = "date";
    private static final String STARS = "stars";
    
    /**
     * @param fileName
     * @throws ImportException 
     * @throws AccessException 
     * @throws ProviderException 
     * @throws OfficeSpaceException 
     * @throws ProviderAlreadyExistException 
     */
    public void importYamlFile(String fileName) throws ImportException, AccessException, ProviderException, OfficeSpaceException {
	
	ProviderServiceImpl sds = ProviderServiceImpl.getInstance();
	OfficeSpaceServiceImpl oss = OfficeSpaceServiceImpl.getInstance();
	
	ImportException ie = new ImportException();
	System.out.println("Importing Provider document ...");
	try(InputStream input = new FileInputStream(new File(fileName))) {
	    
	    Yaml yaml = new Yaml();
	    Object yamlObj = yaml.load(input);
	    Provider provider = null;
	    OfficeSpace officeSpace = null;
	    //Set<Feature> featureSet = oss.getFeatureSet();
	    //Set<CommonAccess> commonAccessSet = oss.getCommonAccessSet();
	    
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
			ie.setDescription("User role is different from Provider.");
			throw ie;
		    } else {
			UUID identifier = null;
			// Provider
			provider = new Provider();
			identifier = UUID.randomUUID();
			provider.setIdentifier(identifier);
			
			if (validInput(data.get(NAME)))
			    provider.setName(data.get(NAME).toString());
			
			if (validInput(data.get(CONTACT)))
			    provider.setContact(data.get(CONTACT).toString());
			
			if (validInput(data.get(PICTURE))) {
			    Image picture = new Image();
			    picture.setURI(data.get(PICTURE).toString());
			    provider.setPicture(picture);
			}
			// Account
			LinkedHashMap<Object, Object> accountMap = (LinkedHashMap<Object, Object>) data.get(ACCOUNT);
			if (validInput(accountMap)) {
			    Account account = new Account();
			    account.setPayPal(accountMap.get(PAYPAL).toString());
			    provider.setAccount(account);
			}
			// OfficeSpace List
			ArrayList<Object> officeArray = (ArrayList<Object>) data.get(OFFICESPACES);
			if (validInput(officeArray)) {
			    Iterator<Object> officeSpaceItr = officeArray.iterator();
			    
			    officeSpace = new OfficeSpace();
			    identifier = UUID.randomUUID();
			    officeSpace.setIdentifier(identifier);
				
			    while(officeSpaceItr.hasNext()) {
				// OfficeSpace
				LinkedHashMap<Object, Object> officeMap = (LinkedHashMap<Object, Object>) officeSpaceItr.next();
				
				if (validInput(officeMap)) {
				    LinkedHashMap<Object, Object> officeSpaceMap = (LinkedHashMap<Object, Object>) officeMap.get(OFFICESPACE);
				    
				    if (validInput(officeSpaceMap.get(NAME)))
					officeSpace.setName(officeSpaceMap.get(NAME).toString());
				    
				    // Capacity
				    LinkedHashMap<Object, Object> capacityMap = (LinkedHashMap<Object, Object>) officeSpaceMap.get(CAPACITY);
				    if (validInput(capacityMap)) {
					Capacity capacity = new Capacity();
					// check parse
					if (validInput(capacityMap.get(NUMBEROFPEOPLE)))
					    capacity.setNumberOfPeople(Integer.valueOf(capacityMap.get(NUMBEROFPEOPLE).toString()));
					
					if (validInput(capacityMap.get(SQUAREFEET)))
					    capacity.setSquareFeet(Float.valueOf(capacityMap.get(SQUAREFEET).toString()));
					
					if (validInput(capacityMap.get(WORKSPACES)))
					    capacity.setWorkSpaces(Integer.valueOf(capacityMap.get(WORKSPACES).toString()));
					
					officeSpace.setCapacity(capacity);
				    }
				    // CommonAccess attributes
				    ArrayList<String> commonAccessArray = (ArrayList<String>) officeSpaceMap.get(COMMONACCESS);
				    if (validInput(commonAccessArray)) {
					Iterator<String> commonAccessItr = commonAccessArray.iterator();
					Set<CommonAccess> commonAccessSet = new HashSet<CommonAccess>();
					while(commonAccessItr.hasNext()) {
					    String strCommonAccess = commonAccessItr.next();
					    if (validInput(strCommonAccess)) {
						CommonAccess commonAccess = new CommonAccess();
						commonAccess.setName(strCommonAccess);
						commonAccessSet.add(commonAccess);
					    }
					}
					officeSpace.setCommonAccess(commonAccessSet);
				    }
				    // Facility
				    LinkedHashMap<String, String> facilityMap = (LinkedHashMap<String, String>) officeSpaceMap.get(FACILITY);
				    if (validInput(facilityMap)) {
					Facility facility = new Facility();
					
					if (validInput(facilityMap.get(CATEGORY)))
					    facility.setCategory(facilityMap.get(CATEGORY));
					
					if (validInput(facilityMap.get(TYPE))) {
					    String strType = facilityMap.get(TYPE);
					    if (strType.equalsIgnoreCase("home"))
						facility.setType(FacilityType.HOME);
					    else if (strType.equalsIgnoreCase("garage"))
						facility.setType(FacilityType.GARAGE);
					}
					officeSpace.setFacility(facility);
				    }
				    // Features
				    ArrayList<String> featuresArray = (ArrayList<String>) officeSpaceMap.get(FEATURES);
				    if (validInput(featuresArray)) {
					Iterator<String> featuresItr = featuresArray.iterator();
					Set<Feature> featureSet = new HashSet<Feature>();
					while(featuresItr.hasNext()) {
					    String strFeature = featuresItr.next();
					    
					    if (validInput(strFeature)) {
						Feature feature = new Feature();
						feature.setName(strFeature);
						featureSet.add(feature);
					    }
					}
					officeSpace.setFeature(featureSet);
				    }
				    // Image List
				    ArrayList<LinkedHashMap<String, String>> imagesArray = (ArrayList<LinkedHashMap<String, String>>) officeSpaceMap.get(IMAGES);
				    if (validInput(imagesArray)) {
					Iterator<LinkedHashMap<String, String>> imagesItr = imagesArray.iterator();
					List<Image> imageList = new ArrayList<Image>();
					while(imagesItr.hasNext()) {
					    // Image
					    LinkedHashMap<String, String> imageMap = imagesItr.next();
					    if (validInput(imageMap)) {
						Image image = new Image();
						
						if (validInput(imageMap.get(DESCRIPTION)))
						    image.setDescription(imageMap.get(DESCRIPTION));
						
						if (validInput(imageMap.get(NAME)))
						    image.setName(imageMap.get(NAME));
						
						if (validInput(imageMap.get(URI)))
						    image.setURI(imageMap.get(URI));
						
						imageList.add(image);
					    }
					}
					officeSpace.setImageList(imageList);
				    }
				    // Location
				    LinkedHashMap<String, Object> locationMap = (LinkedHashMap<String, Object>) officeSpaceMap.get(LOCATION);
				    if (locationMap != null && locationMap.size() > 0) {
					
					Location location = new Location();
					
					if (validInput(locationMap.get(NAME)))
					    location.setName(locationMap.get(NAME).toString());
					
					if (validInput(locationMap.get(LAT)))
					    location.setLat(Double.valueOf(locationMap.get(LAT).toString()));
					
					if (validInput(locationMap.get(LONG)))
					    location.setLon(Double.valueOf(locationMap.get(LONG).toString()));
					
					// Address
					LinkedHashMap<String, String> addressMap = (LinkedHashMap<String, String>) locationMap.get(ADDRESS);
					if (addressMap != null && addressMap.size() > 0) {
					    Address address = new Address();
					    
					    if (validInput(addressMap.get(COUNTRYCODE)))
						address.setState(addressMap.get(COUNTRYCODE));
					    
					    if (validInput(addressMap.get(STATE)))
						address.setState(addressMap.get(STATE));
					    
					    if (validInput(addressMap.get(STREET1)))
						address.setStreet1(addressMap.get(STREET1));
					    
					    if (validInput(addressMap.get(STREET2)))
						address.setStreet2(addressMap.get(STREET2).toString());
					    
					    if (validInput(addressMap.get(ZIPCODE)))
						address.setZipCode(String.valueOf(addressMap.get(ZIPCODE)));
					    
					    location.setAddress(address);
					}
					officeSpace.setLocation(location);
				    }
				    // Rates List
				    ArrayList<LinkedHashMap<String, String>> ratesArray = (ArrayList<LinkedHashMap<String, String>>) officeSpaceMap.get(RATES);
				    if (validInput(ratesArray)) {
					Iterator<LinkedHashMap<String, String>> ratesItr = ratesArray.iterator();
					List<Rate> rateList = new ArrayList<Rate>();
					while(ratesItr.hasNext()) {
					    
					    LinkedHashMap<String, String> rateMap = (LinkedHashMap<String, String>) ratesItr.next();
					    
					    if (validInput(rateMap)) {
						Rate rate = new Rate();
						
						if (validInput(rateMap.get(COST)))
						    rate.setCost(rateMap.get(COST));
						
						if (validInput(rateMap.get(PERIOD))) {
						    String strPeriod = rateMap.get(PERIOD);
						    if (strPeriod.equalsIgnoreCase("hour"))
							rate.setPeriod(RateType.HOUR);
						    else if (strPeriod.equalsIgnoreCase("day"))
							rate.setPeriod(RateType.DAY);
						    else if (strPeriod.equalsIgnoreCase("week"))
							rate.setPeriod(RateType.WEEK);
						    else if (strPeriod.equalsIgnoreCase("month"))
							rate.setPeriod(RateType.MONTH);
						    else if (strPeriod.equalsIgnoreCase("year"))
							rate.setPeriod(RateType.YEAR);
						}
						rateList.add(rate);
					    }
					}
					officeSpace.setRateList(rateList);
				    }
				    // Ratings List
				    ArrayList<LinkedHashMap<String, Object>> ratingsArray = (ArrayList<LinkedHashMap<String, Object>>) officeSpaceMap.get(RATINGS);
				    if (validInput(ratingsArray)) {
					Iterator<LinkedHashMap<String, Object>> ratingsItr = ratingsArray.iterator();
					List<Rating> ratingList = new ArrayList<Rating>();
					while(ratingsItr.hasNext()) {
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
					officeSpace.setRatingList(ratingList);
				    }
				}
			    }
			    provider.setOfficeSpaceIdentifier(officeSpace.getIdentifier());
			    sds.createProvider(AUTHTOKEN, provider);
			    oss.createOffice(AUTHTOKEN, officeSpace);
			    
			    System.out.println("Document imported succesfully.\n");
			}
		    }
		}
	    }
	} catch (ImportException e) {
	    throw e;
	} catch (AccessException e) {
	    throw e;
	} catch (ProviderException e) {
	    throw e;
	} catch (OfficeSpaceException e) {
	    throw e;
	} catch (IOException e) {
            e.printStackTrace();
            ie.setDescription(e.getMessage());
            throw ie;
        }
    }
    
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
