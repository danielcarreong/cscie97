/**
 * 
 */
package cscie97.asn2.sharedesk.provider;

/**
 * User subclass providing Renter information
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class Renter extends User implements Comparable<Object> {

    @Override
    public boolean equals(Object obj) {
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	
	Renter comparator = (Renter) obj;
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
	    Renter compare = (Renter) obj;
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
