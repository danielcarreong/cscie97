/**
 * 
 */
package cscie97.asn1.knowledge.engine;

/**
 * Node class represents a Subject or an Object within
 * a Triplet. Each identifier is unique.
 * 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class Node {

    private String identifier;

    /**
     * @return the identifier
     */
    public String getIdentifier() {
	return identifier;
    }

    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier) {
	this.identifier = identifier;
    }
    
}
