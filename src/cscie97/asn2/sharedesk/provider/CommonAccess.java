/**
 * 
 */
package cscie97.asn2.sharedesk.provider;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class CommonAccess implements Comparable<Object> {

    private String name;
    
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
    
    @Override
    public boolean equals(Object obj) {
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	
	CommonAccess comparator = (CommonAccess) obj;
	if (!this.name.equalsIgnoreCase(comparator.getName()))
	    return false;
	
	return true;
    }
    
    public int compareTo(Object obj) {
	if (obj instanceof CommonAccess) {
	    CommonAccess compare = (CommonAccess) obj;
	    int result = this.name.compareTo(compare.getName());
	    return result;
	}
	return 0;
    }
    
    @Override
    public int hashCode() {
	return name.hashCode();
    }
}
