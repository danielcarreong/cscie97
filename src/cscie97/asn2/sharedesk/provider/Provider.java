/**
 * 
 */
package cscie97.asn2.sharedesk.provider;

import java.util.UUID;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class Provider extends User implements Comparable<Object> {
    
    private UUID officeSpaceIdentifier;
    
    /**
     * @return the officeSpace
     */
    public UUID getOfficeSpaceIdentifier() {
	return officeSpaceIdentifier;
    }
    /**
     * @param officeSpaceIdentifier 
     */
    public void setOfficeSpaceIdentifier(UUID officeSpaceIdentifier) {
	this.officeSpaceIdentifier = officeSpaceIdentifier;
    }
    
    @Override
    public boolean equals(Object obj) {
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	
	Provider comparator = (Provider) obj;
	if (!super.getName().equalsIgnoreCase(comparator.getName()))
	    return false;
	
	return true;
    }
    
    /**
     * @param obj
     * @return comparator result
     */
    public int compareTo(Object obj) {
	if (obj instanceof Provider) {
	    Provider compare = (Provider) obj;
	    int result = super.getName().compareTo(compare.getName());
	    return result;
	}
	return 0;
    }
    
    @Override
    public int hashCode() {
	return super.hashCode();
    }
}
