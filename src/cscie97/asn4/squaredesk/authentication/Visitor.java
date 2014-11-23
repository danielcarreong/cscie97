/**
 * 
 */
package cscie97.asn4.squaredesk.authentication;

/**
 * Visitor class implements the Visitor patterns in order to traverse through all
 * classes implementing its services to be able to perform additional tasks.
 * 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public interface Visitor {

    /**
     * @param user
     */
    public void visit(User user);
    /**
     * @param service
     */
    public void visit(Service service);
    /**
     * @param entitlement 
     */
    public void visit(Entitlement entitlement);
    /**
     * @param credential
     */
    public void visit(Credential credential);
}
