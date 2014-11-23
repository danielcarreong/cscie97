/**
 * 
 */
package cscie97.asn4.squaredesk.authentication;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class Permission extends Entitlement {

    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.Visitable#acceptVisitor(cscie97.asn4.squaredesk.authentication.Visitor)
     */
    @Override
    public void acceptVisitor(Visitor visitor) {
	visitor.visit(this);
    }

}
