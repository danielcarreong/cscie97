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
    
    private static final String authToken = "admin";
    
    /**
     * @param fileName
     * @throws ImportException 
     * @throws AccessException 
     * @throws ProviderException 
     * @throws OfficeSpaceException 
     * @throws ProviderAlreadyExistException 
     */
    public void importYamlFile(String fileName) throws ImportException, AccessException, ProviderException, OfficeSpaceException {
	
	ShareDeskService sds = ShareDeskService.getInstance();
	OfficeSpaceService oss = OfficeSpaceService.getInstance();
	
	ImportException ie = new ImportException();
	System.out.println("Importing document ...");
	try(InputStream input = new FileInputStream(new File(fileName))) {
	    
	    Yaml yaml = new Yaml();
	    Object yamlObj = yaml.load(input);
	    Provider provider = null;
	    OfficeSpace officeSpace = null;
	    Set<Feature> featureSet = oss.getFeatureSet();
	    Set<CommonAccess> commonAccessSet = oss.getCommonAccessSet();
	    
	    if (!validInput(yamlObj)) {
		ie.setDescription("File is empty.");
		throw ie;
	    } else {
		LinkedHashMap<Object, Object> yamlMap = (LinkedHashMap<Object, Object>) yamlObj;
		
		if (yamlMap.size() == 0) {
		    ie.setDescription("YAML document is malformed.");
		    throw ie;
		} else {
		    LinkedHashMap<Object, Object> data = (LinkedHashMap<Object, Object>) yamlMap.get("provider");
		    
		    if (!validInput(data)) {
			ie.setDescription("User role is different from Provider.");
			throw ie;
		    } else {
			UUID identifier = null;
			// Provider
			provider = new Provider();
			identifier = UUID.randomUUID();
			provider.setIdentifier(identifier);
			
			if (validInput(data.get("name")))
			    provider.setName(data.get("name").toString());
			
			if (validInput(data.get("contact")))
			    provider.setContact(data.get("contact").toString());
			
			if (validInput(data.get("picture"))) {
			    Image picture = new Image();
			    picture.setURI(data.get("picture").toString());
			    provider.setPicture(picture);
			}
			// Account
			LinkedHashMap<Object, Object> accountMap = (LinkedHashMap<Object, Object>) data.get("account");
			if (validInput(accountMap)) {
			    Account account = new Account();
			    account.setPayPal(accountMap.get("payPalAccountNumber").toString());
			    provider.setAccount(account);
			}
			// OfficeSpace List
			ArrayList<Object> officeArray = (ArrayList<Object>) data.get("officeSpaces");
			if (validInput(officeArray)) {
			    Iterator<Object> officeSpaceItr = officeArray.iterator();
			    
			    officeSpace = new OfficeSpace();
			    identifier = UUID.randomUUID();
			    officeSpace.setIdentifier(identifier);
				
			    while(officeSpaceItr.hasNext()) {
				// OfficeSpace
				LinkedHashMap<Object, Object> officeMap = (LinkedHashMap<Object, Object>) officeSpaceItr.next();
				
				if (validInput(officeMap)) {
				    LinkedHashMap<Object, Object> officeSpaceMap = (LinkedHashMap<Object, Object>) officeMap.get("officeSpace");
				    
				    if (validInput(officeSpaceMap.get("name")))
					officeSpace.setName(officeSpaceMap.get("name").toString());
				    
				    // Capacity
				    LinkedHashMap<Object, Object> capacityMap = (LinkedHashMap<Object, Object>) officeSpaceMap.get("capacity");
				    if (validInput(capacityMap)) {
					Capacity capacity = new Capacity();
					// check parse
					if (validInput(capacityMap.get("numberOfPeople")))
					    capacity.setNumberOfPeople(Integer.valueOf(capacityMap.get("numberOfPeople").toString()));
					
					if (validInput(capacityMap.get("squarefeet")))
					    capacity.setSquareFeet(Float.valueOf(capacityMap.get("squarefeet").toString()));
					
					if (validInput(capacityMap.get("workSpaces")))
					    capacity.setWorkSpaces(Integer.valueOf(capacityMap.get("workSpaces").toString()));
					
					officeSpace.setCapacity(capacity);
				    }
				    // CommonAccess attributes
				    ArrayList<String> commonAccessArray = (ArrayList<String>) officeSpaceMap.get("commonAccess");
				    if (validInput(commonAccessArray)) {
					Iterator<String> commonAccessItr = commonAccessArray.iterator();

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
				    LinkedHashMap<String, String> facilityMap = new LinkedHashMap<String, String>();
				    if (validInput(facilityMap)) {
					Facility facility = new Facility();
					
					if (validInput(facilityMap.get("category")))
					    facility.setCategory(facilityMap.get("category"));
					
					if (validInput(facilityMap.get("type"))) {
					    String strType = facilityMap.get("type");
					    if (strType.equalsIgnoreCase("home"))
						facility.setType(FacilityType.HOME);
					    else if (strType.equalsIgnoreCase("garage"))
						facility.setType(FacilityType.GARAGE);
					}
					
					officeSpace.setFacility(facility);
				    }
				    // Features
				    ArrayList<String> featuresArray = (ArrayList<String>) officeSpaceMap.get("features");
				    if (validInput(featuresArray)) {
					Iterator<String> featuresItr = featuresArray.iterator();
					
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
				    ArrayList<LinkedHashMap<String, String>> imagesArray = (ArrayList<LinkedHashMap<String, String>>) officeSpaceMap.get("images");
				    if (validInput(imagesArray)) {
					Iterator<LinkedHashMap<String, String>> imagesItr = imagesArray.iterator();
					List<Image> imageList = new ArrayList<Image>();
					while(imagesItr.hasNext()) {
					    // Image
					    LinkedHashMap<String, String> imageMap = imagesItr.next();
					    if (validInput(imageMap)) {
						Image image = new Image();
						
						if (validInput(imageMap.get("description")))
						    image.setDescription(imageMap.get("description"));
						
						if (validInput(imageMap.get("name")))
						    image.setName(imageMap.get("name"));
						
						if (validInput(imageMap.get("uri")))
						    image.setURI(imageMap.get("uri"));
						
						imageList.add(image);
					    }
					}
					
					officeSpace.setImageList(imageList);
				    }
				    // Location
				    LinkedHashMap<String, Object> locationMap = (LinkedHashMap<String, Object>) officeSpaceMap.get("location");
				    if (locationMap != null && locationMap.size() > 0) {
					
					Location location = new Location();
					
					if (validInput(locationMap.get("name")))
					    location.setName(locationMap.get("name").toString());
					
					if (validInput(locationMap.get("lat")))
					    location.setLat(Double.valueOf(locationMap.get("lat").toString()));
					
					if (validInput(locationMap.get("long")))
					    location.setLon(Double.valueOf(locationMap.get("long").toString()));
					
					// Address
					LinkedHashMap<String, String> addressMap = (LinkedHashMap<String, String>) locationMap.get("address");
					if (addressMap != null && addressMap.size() > 0) {
					    Address address = new Address();
					    
					    if (validInput(addressMap.get("countryCode")))
						address.setState(addressMap.get("countryCode"));
					    
					    if (validInput(addressMap.get("state")))
						address.setState(addressMap.get("state"));
					    
					    if (validInput(addressMap.get("street1")))
						address.setStreet1(addressMap.get("street1"));
					    
					    if (validInput(addressMap.get("street2")))
						address.setStreet2(addressMap.get("street2").toString());
					    
					    if (validInput(addressMap.get("zipcode")))
						address.setZipCode(String.valueOf(addressMap.get("zipcode")));
					    
					    location.setAddress(address);
					}
					
					officeSpace.setLocation(location);
				    }
				    // Rates List
				    ArrayList<LinkedHashMap<String, String>> ratesArray = (ArrayList<LinkedHashMap<String, String>>) officeSpaceMap.get("rates");
				    if (validInput(ratesArray)) {
					Iterator<LinkedHashMap<String, String>> ratesItr = ratesArray.iterator();
					List<Rate> rateList = new ArrayList<Rate>();
					while(ratesItr.hasNext()) {
					    
					    LinkedHashMap<String, String> rateMap = (LinkedHashMap<String, String>) ratesItr.next();
					    
					    if (validInput(rateMap)) {
						Rate rate = new Rate();
						
						if (validInput(rateMap.get("cost")))
						    rate.setCost(rateMap.get("cost"));
						
						if (validInput(rateMap.get("period"))) {
						    String strPeriod = rateMap.get("period");
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
				    ArrayList<LinkedHashMap<String, Object>> ratingsArray = (ArrayList<LinkedHashMap<String, Object>>) officeSpaceMap.get("ratings");
				    if (validInput(ratingsArray)) {
					Iterator<LinkedHashMap<String, Object>> ratingsItr = ratingsArray.iterator();
					List<Rating> ratingList = new ArrayList<Rating>();
					while(ratingsItr.hasNext()) {
					    // Rating
					    LinkedHashMap<String, Object> ratingMap = ratingsItr.next();
					    
					    if (validInput(ratingMap)) {
						Rating rating = new Rating();
						
						if (validInput(ratingMap.get("authorsId")))
						    rating.setAuthorsId(ratingMap.get("authorsId").toString());
						
						if (validInput(ratingMap.get("comment")))
						    rating.setComment(ratingMap.get("comment").toString());
						
						if (validInput(ratingMap.get("date")))
						    rating.setDate(Date.valueOf(ratingMap.get("date").toString()));
						
						if (validInput(ratingMap.get("stars")))
						    rating.setStars(Integer.valueOf(ratingMap.get("stars").toString()));
						
						ratingList.add(rating);
					    }
					}
					
					officeSpace.setRatingList(ratingList);
				    }
				}
			    }
			    provider.setOfficeSpaceIdentifier(officeSpace.getIdentifier());
			    sds.createProvider(authToken, provider);
			    oss.createOffice(authToken, officeSpace);
			    
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
