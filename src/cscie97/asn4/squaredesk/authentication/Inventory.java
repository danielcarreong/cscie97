/**
 * 
 */
package cscie97.asn4.squaredesk.authentication;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class Inventory implements Visitor {

    private AuthenticationServiceImpl asi = AuthenticationServiceImpl.getInstance();
    private PrintWriter writer;
    private String L1 = "  ";
    private String L2 = "    ";
    
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.Visitor#visit(cscie97.asn4.squaredesk.authentication.User)
     */
    @Override
    public void visit(User user) {

	writer.println("User:");
	writer.println(L1 + "ID: " + user.getUserID());
	writer.println(L1 + "Name: " + user.getName());
	
	if (user.getRole() != null) {
	    writer.println(L1 + "Role:");
	    writer.println(L2 + "ID: " + user.getRole().getEntitlementID());
	    writer.println(L2 + "Name: " + user.getRole().getName());
	    writer.println(L2 + "Description: " + user.getRole().getDescription());
	}
	
	if (user.getCredentialMap() != null) {
	    for (Credential credential : user.getCredentialMap().values())
		visit(credential);
	}
	
	if (user.getSession() != null) {
	    writer.println(L1 + "Session:");
	    writer.println(L2 + "AuthToken: " + user.getSession().getAuthToken());
	    writer.println(L2 + "TimeStamp: " + user.getSession().getTimeStamp());
	    writer.println(L2 + "Valid: " + user.getSession().isValid());
	}
    }

    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.Visitor#visit(cscie97.asn4.squaredesk.authentication.Service)
     */
    @Override
    public void visit(Service service) {
	writer.println("Service:");
	writer.println(L1 + "ServiceID: " + service.getServiceID());
	writer.println(L1 + "Name: " + service.getName());
	writer.println(L1 + "Description: " + service.getDescription());
	
	for (Permission permission : service.getPermissionMap().values())
	    visit(permission);
    }

    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.Visitor#visit(cscie97.asn4.squaredesk.authentication.Entitlement)
     */
    @Override
    public void visit(Entitlement entitlement) {
	if (entitlement instanceof Role) {
	    Role role = (Role) entitlement;
	    writer.println("Role:");
	    writer.println(L1 + "EntitlementID: " + role.getEntitlementID());
	    writer.println(L1 + "Name: " + role.getName());
	    writer.println(L1 + "Description: " + role.getDescription());

	    if (role.getEntitlementMap().size() > 0) {
		for (Entitlement entitlementEntry : role.getEntitlementMap().values())
		    if (entitlementEntry instanceof Role)
			visit((Role) entitlementEntry);
		    else if (entitlementEntry instanceof Permission)
			visit((Permission) entitlementEntry);
	    }
	} else if (entitlement instanceof Permission) {
	    Permission permission = (Permission) entitlement;
	    writer.println(L1 + "Permission:");
	    writer.println(L2 + "EntitlementID: " + permission.getEntitlementID());
	    writer.println(L2 + "Name: " + permission.getName());
	    writer.println(L2 + "Description: " + permission.getDescription());
	}
    }

    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.Visitor#visit(cscie97.asn4.squaredesk.authentication.Credential)
     */
    @Override
    public void visit(Credential credential) {
	writer.println(L1 + "Credential:");
	writer.println(L2 + "Username: " + credential.getUsername());
	writer.println(L2 + "Password: " + credential.getPassword());
    }
    
    /**
     * Generates an output file with the Authentication Inventory information
     * @param authToken 
     * @param fileName 
     */
    public void generateOutputFile(String authToken, String fileName) {
	try {
	    writer = new PrintWriter(fileName, "UTF-8");
	    
	    for (Visitable visitable : asi.getServiceList(authToken))
		visitable.acceptVisitor(this);

	    for (Visitable visitable : asi.getRoleList(authToken))
		visitable.acceptVisitor(this);

	    for (Visitable visitable : asi.getUserList(authToken))
		visitable.acceptVisitor(this);
	    
	    writer.close();
	    
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	}
    }
}
