/**
 * 
 */
package cscie97.asn4.squaredesk.authentication;

import java.io.FileReader;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class CSVImporter {

    private EntitlementFactory entitlementFactory = new EntitlementFactory();
    private static String AUTHTOKEN = "ADMIN";

    /**
     * @param fileName
     */
    public void importCSV(String fileName) {
	Reader in;
	try {
	    in = new FileReader(fileName);
	    AuthenticationServiceImpl asi = AuthenticationServiceImpl.getInstance();
	    Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
	    for (CSVRecord record : records) {
		if (!record.get(0).trim().equalsIgnoreCase("")) {
		    if (!record.get(0).contains("#")) {
			// reads first CSV input column for defining and adding attributes and services
			// defining Services
			if (record.get(0).trim().equalsIgnoreCase("define_service")) {
			    Service service = new Service();
			    service.setServiceID(record.get(1).trim());
			    service.setName(record.get(2).trim());
			    service.setDescription(record.get(3).trim());
			    asi.defineService(AUTHTOKEN, service);
			}
			// defining Permissions
			if (record.get(0).trim().equalsIgnoreCase("define_permission")) {
			    Entitlement permission = entitlementFactory.getEntitlement("PERMISSION");
			    Service service = asi.getService(AUTHTOKEN, record.get(1).trim());
			    if (service != null) {
				permission.setEntitlementID(record.get(2).trim());
				permission.setName(record.get(3).trim());
				permission.setDescription(record.get(4).trim());
				asi.definePermission(AUTHTOKEN, service,(Permission) permission);
			    }
			}
			// defining Roles
			if (record.get(0).trim().equalsIgnoreCase("define_role")) {
			    Entitlement role = entitlementFactory.getEntitlement("ROLE");
			    role.setEntitlementID(record.get(1).trim());
			    role.setName(record.get(2).trim());
			    role.setDescription(record.get(3).trim());
			    asi.defineRole(AUTHTOKEN, role);
			}
			// defining Entitlements
			if (record.get(0).trim().equalsIgnoreCase("add_entitlement_to_role") || record.get(0).trim().equalsIgnoreCase("add_role_to_user")) {
			    // retrieve Role assignee
			    Role role = asi.getRole(AUTHTOKEN, record.get(1).trim());
			    if (role != null) {
				// retrieve Entitlement to assign (Role or Permission)
				Role roleToAdd = null;
				Permission permissionToAdd = null;

				roleToAdd = asi.getRole(AUTHTOKEN, record.get(2).trim());
				permissionToAdd = asi.getPermission(AUTHTOKEN, record.get(2).trim());

				if (roleToAdd != null) {
				    asi.addEntitlement(AUTHTOKEN, role, roleToAdd);
				} else if (permissionToAdd != null) {
				    asi.addEntitlement(AUTHTOKEN, role, permissionToAdd);
				}
			    } else {
				// retrieve User asignee
				User user = asi.getUser(AUTHTOKEN, record.get(1).trim());
				if (user != null) {
				    // retrieve Role to assign
				    Role roleToAdd = asi.getRole(AUTHTOKEN, record.get(2).trim());
				    if (roleToAdd != null) {
					asi.addEntitlement(AUTHTOKEN, user.getUserID(), roleToAdd);
				    }
				}
			    }
			}
			// creating Users
			if (record.get(0).trim().equalsIgnoreCase("create_user")) {
			    User user = new User();
			    user.setUserID(record.get(1).trim());
			    user.setName(record.get(2).trim());
			    asi.createUser(AUTHTOKEN, user);
			}
			// adding Credentials
			if (record.get(0).trim().equalsIgnoreCase("add_credential")) {
			    User user = asi.getUser(AUTHTOKEN, record.get(1).trim());
			    if (user != null) {
				Credential credential = new Credential();
				credential.setUsername(record.get(2).trim());
				credential.setPassword(record.get(3).trim());
				asi.addCredential(AUTHTOKEN, user.getUserID(), credential);
			    }
			}
		    }
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
