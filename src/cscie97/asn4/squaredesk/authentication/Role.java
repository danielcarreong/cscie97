/**
 * 
 */
package cscie97.asn4.squaredesk.authentication;

import java.util.Map;

/**
 * Role class to sets gets information, extends Entitlement class attributes. 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class Role extends Entitlement {

    private Map<String, Entitlement> entitlementMap;

    /**
     * @return the entitlementMap
     */
    public Map<String, Entitlement> getEntitlementMap() {
        return entitlementMap;
    }

    /**
     * @param entitlementMap the entitlementMap to set
     */
    public void setEntitlementMap(Map<String, Entitlement> entitlementMap) {
        this.entitlementMap = entitlementMap;
    }

    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.Visitable#acceptVisitor(cscie97.asn4.squaredesk.authentication.Visitor)
     */
    @Override
    public void acceptVisitor(Visitor visitor) {
	visitor.visit(this);
    }
}
