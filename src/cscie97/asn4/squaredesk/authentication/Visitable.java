/**
 * 
 */
package cscie97.asn4.squaredesk.authentication;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public interface Visitable {

    /**
     * @param visitor
     */
    public void acceptVisitor(Visitor visitor);
}
