/**
 * 
 */
package cscie97.asn4.squaredesk.authentication;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public abstract class Entitlement implements Visitable {

    private String entitlementID;
    private String name;
    private String description;
    
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return the entitlementID
     */
    public String getEntitlementID() {
        return entitlementID;
    }
    /**
     * @param entitlementID the entitlementID to set
     */
    public void setEntitlementID(String entitlementID) {
        this.entitlementID = entitlementID;
    }
    @Override
    public int hashCode() {
	return entitlementID.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	if (!(obj instanceof Service))
	    return false;
	
	Entitlement comparator = (Entitlement) obj;
	if (!this.entitlementID.equalsIgnoreCase(comparator.entitlementID))
	    return false;
	
	return true;
    }
}
