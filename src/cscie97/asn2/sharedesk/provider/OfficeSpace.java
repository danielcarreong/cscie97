/**
 * 
 */
package cscie97.asn2.sharedesk.provider;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class OfficeSpace implements Comparable<OfficeSpace> {

    private UUID identifier;
    private String name;
    private Set<Feature> feature;
    private Set<CommonAccess> commonAccess;
    private Location location;
    private Capacity capacity;
    private Facility facility;
    private List<Rate> rateList;
    private List<Rating> ratingList;
    private List<Image> imageList;
    
    /**
     * @return the identifier
     */
    public UUID getIdentifier() {
        return identifier;
    }
    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(UUID identifier) {
        this.identifier = identifier;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the feature
     */
    public Set<Feature> getFeature() {
        return feature;
    }
    /**
     * @param feature the feature to set
     */
    public void setFeature(Set<Feature> feature) {
        this.feature = feature;
    }
    /**
     * @return the commonAccess
     */
    public Set<CommonAccess> getCommonAccess() {
        return commonAccess;
    }
    /**
     * @param commonAccess the commonAccess to set
     */
    public void setCommonAccess(Set<CommonAccess> commonAccess) {
        this.commonAccess = commonAccess;
    }
    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }
    /**
     * @param location the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }
    /**
     * @return the capacity
     */
    public Capacity getCapacity() {
        return capacity;
    }
    /**
     * @param capacity the capacity to set
     */
    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
    }
    /**
     * @return the facility
     */
    public Facility getFacility() {
        return facility;
    }
    /**
     * @param facility the facility to set
     */
    public void setFacility(Facility facility) {
        this.facility = facility;
    }
    /**
     * @return the rate
     */
    public List<Rate> getRateList() {
        return rateList;
    }
    /**
     * @param rateList 
     */
    public void setRateList(List<Rate> rateList) {
        this.rateList = rateList;
    }
    /**
     * @return the rating
     */
    public List<Rating> getRatingList() {
        return ratingList;
    }
    /**
     * @param ratingList 
     */
    public void setRatingList(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }
    /**
     * @return the imageList
     */
    public List<Image> getImageList() {
        return imageList;
    }
    /**
     * @param imageList the imageList to set
     */
    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }
    
    @Override
    public boolean equals(Object obj) {
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	if (!(obj instanceof OfficeSpace))
	    return false;
	
	OfficeSpace comparator = (OfficeSpace) obj;
	if (!this.name.equalsIgnoreCase(comparator.getName()))
	    return false;
	
	return true;
    }
    
    public int compareTo(OfficeSpace obj) {
	int result = this.name.compareTo(obj.getName());
	return result;
    }
    
    @Override
    public int hashCode() {
	return name.hashCode();
    }
}
