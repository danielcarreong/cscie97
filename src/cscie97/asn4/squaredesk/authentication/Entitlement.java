/**
 * 
 */
package cscie97.asn4.squaredesk.authentication;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public abstract class Entitlement {

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
    
}
