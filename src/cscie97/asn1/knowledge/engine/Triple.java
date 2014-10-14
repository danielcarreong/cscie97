/**
 * 
 */
package cscie97.asn1.knowledge.engine;

/**
 * Triple class represents an input from input File read by Importer.
 * Each triple consists of: Node + Predicate + Node (Subject + Predicate + Object, respectively) 
 * 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class Triple {

    private String identifier;
    private Node subject;
    private Predicate predicate;
    private Node object;

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
    
    /**
     * @return the subject
     */
    public Node getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(Node subject) {
        this.subject = subject;
    }

    /**
     * @return the predicate
     */
    public Predicate getPredicate() {
        return predicate;
    }

    /**
     * @param predicate the predicate to set
     */
    public void setPredicate(Predicate predicate) {
        this.predicate = predicate;
    }

    /**
     * @return the object
     */
    public Node getObject() {
        return object;
    }

    /**
     * @param object the object to set
     */
    public void setObject(Node object) {
        this.object = object;
    }
    
}
