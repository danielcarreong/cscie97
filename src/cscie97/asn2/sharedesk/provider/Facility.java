/**
 * 
 */
package cscie97.asn2.sharedesk.provider;

/**
 * Class holding OfficeSpace Facility information.
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class Facility {

    private String category;
    private FacilityType type;

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the type
     */
    public FacilityType getType() {
	return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(FacilityType type) {
	this.type = type;
    }
    
}
