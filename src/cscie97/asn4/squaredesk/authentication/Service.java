/**
 * 
 */
package cscie97.asn4.squaredesk.authentication;

import java.util.Map;

/**
 * Service class defines all services that will be managed by Authentication application.
 * 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class Service implements Visitable {

    private String serviceID;
    private String name;
    private String description;
    private Map<String, Permission> permissionMap;
    
    /**
     * @return the serviceID
     */
    public String getServiceID() {
        return serviceID;
    }
    /**
     * @param serviceID the serviceID to set
     */
    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
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
     * @return the permissionMap
     */
    public Map<String, Permission> getPermissionMap() {
        return permissionMap;
    }
    /**
     * @param permissionMap the permissionMap to set
     */
    public void setPermissionMap(Map<String, Permission> permissionMap) {
        this.permissionMap = permissionMap;
    }
    @Override
    public int hashCode() {
	return serviceID.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	if (!(obj instanceof Service))
	    return false;
	
	Service comparator = (Service) obj;
	if (!this.serviceID.equalsIgnoreCase(comparator.serviceID))
	    return false;
	
	return true;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.Visitable#acceptVisitor(cscie97.asn4.squaredesk.authentication.Visitor)
     */
    @Override
    public void acceptVisitor(Visitor visitor) {
	visitor.visit(this);
	
    }
}
