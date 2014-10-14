/**
 * 
 */
package cscie97.asn1.knowledge.engine;

/**
 * Predicate class represents a Predicate within a Triplet.
 * It's uniquely represented by an identifier.  
 * 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class Predicate {

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
