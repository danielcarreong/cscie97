/**
 * 
 */
package cscie97.asn4.squaredesk.authentication;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class EntitlementFactory {

    /**
     * @param entitlementType
     * @return Entitlement
     */
    public Entitlement getEntitlement (String entitlementType) {
	if (entitlementType == null) {
	    return null;
	}
	if (entitlementType.equalsIgnoreCase("ROLE")) {
	    return new Role();
	} else if (entitlementType.equalsIgnoreCase("PERMISSION")) {
	    return new Permission();
	}
	return null;
    }
}
