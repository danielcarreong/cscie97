/**
 * 
 */
package cscie97.asn4.squaredesk.authentication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class AuthenticationServiceImpl implements AuthenticationService {
    
    private Map<String, User> userMap;
    private Map<String, Service> serviceMap;
    private Map<String, Role> roleMap;
    private static AuthenticationServiceImpl singleton = new AuthenticationServiceImpl();
    
    private AuthenticationServiceImpl() {
	userMap = new HashMap<String, User>();
	serviceMap = new HashMap<String, Service>();
	roleMap = new HashMap<String, Role>();
    }
    
    /**
     * @return AuthenticationServiceImpl unique single instance
     */
    public static AuthenticationServiceImpl getInstance() {
	return singleton;
    }
    
    /**
     * @return the userMap
     */
    public Map<String, User> getUserMap() {
        return userMap;
    }

    /**
     * @param userMap the userMap to set
     */
    public void setUserMap(Map<String, User> userMap) {
        this.userMap = userMap;
    }

    /**
     * @return the serviceMap
     */
    public Map<String, Service> getServiceMap() {
        return serviceMap;
    }

    /**
     * @param serviceMap the serviceMap to set
     */
    public void setServiceMap(Map<String, Service> serviceMap) {
        this.serviceMap = serviceMap;
    }

    /**
     * @return the roleMap
     */
    public Map<String, Role> getRoleMap() {
        return roleMap;
    }

    /**
     * @param roleMap the roleMap to set
     */
    public void setRoleMap(Map<String, Role> roleMap) {
        this.roleMap = roleMap;
    }

    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#defineService(cscie97.asn4.squaredesk.authentication.Service)
     */
    @Override
    public Service defineService(Service service) throws AuthenticationException {
	if (service == null) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("");
	    throw ex;
	} else {
	    serviceMap.put(service.getServiceID(), service);
	}
	return null;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#revokeService(java.lang.String)
     */
    @Override
    public void revokeService(String serviceID) throws AuthenticationException {
	if (serviceID == null || serviceID.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("");
	    throw ex;
	} else {
	    serviceMap.remove(serviceID);
	}
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#getService(java.lang.String)
     */
    @Override
    public Service getService(String serviceID) throws AuthenticationException {
	if (serviceID == null || serviceID.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("");
	    throw ex;
	} else {
	    if (!serviceMap.containsKey(serviceID)) {
		AuthenticationException ex = new AuthenticationException();
		ex.setDescription("");
		throw ex;
	    } else {
		serviceMap.get(serviceID);
	    }
	}
	return null;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#getServiceList()
     */
    @Override
    public List<Service> getServiceList() throws AuthenticationException {
	if (serviceMap.size() > 0)
	    return (List<Service>) serviceMap.values();
	return null;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#definePermission(cscie97.asn4.squaredesk.authentication.Service, cscie97.asn4.squaredesk.authentication.Permission)
     */
    @Override
    public Permission definePermission(Service service, Permission permission) throws AuthenticationException {
	if (service == null || permission == null) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("");
	    throw ex;
	} else {
	    if (getService(service.getServiceID()) != null) {
		Map<String, Permission> permissionMap = new HashMap<String, Permission>();
		permissionMap.put(permission.getEntitlementID(), permission);
		service.setPermissionMap(permissionMap);
		serviceMap.put(service.getServiceID(), service);
	    }
	}
	return null;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#revokePermission(java.lang.String, java.lang.String)
     */
    @Override
    public void revokePermission(String serviceID, String permissionID) throws AuthenticationException {
	if (serviceID == null || serviceID.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("");
	    throw ex;
	} else {
	    Service service = getService(serviceID);
	    if (service != null) {
		Map<String, Permission> permissionMap = service.getPermissionMap();
		if (permissionMap.containsKey(permissionID))
		    permissionMap.remove(permissionID);
		service.setPermissionMap(permissionMap);
		serviceMap.put(serviceID, service);
	    }
	}
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#getPermission(java.lang.String)
     */
    @Override
    public Permission getPermission(String permissionID) throws AuthenticationException {
	if (permissionID == null || permissionID.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("");
	    throw ex;
	} else {
	    if (serviceMap.size() == 0) {
		AuthenticationException ex = new AuthenticationException();
		ex.setDescription("");
		throw ex;
	    } else {
		for (Map.Entry<String, Service> entry : serviceMap.entrySet()) {
		    Service service = entry.getValue();
		    if (service.getPermissionMap().containsKey(permissionID))
			return service.getPermissionMap().get(permissionID);
		}
	    }
	}
	return null;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#getPermissionList()
     */
    @Override
    public List<Permission> getPermissionList() throws AuthenticationException {
	List<Permission> permissionList = new ArrayList<Permission>();
	if (serviceMap.size() > 0) {
	    for (Map.Entry<String, Service> serviceEntry : serviceMap.entrySet()) {
		Service service = serviceEntry.getValue();
		for (Map.Entry<String, Permission> permissionEntry : service.getPermissionMap().entrySet()) {
		    permissionList.add(permissionEntry.getValue());
		}
	    }
	    return permissionList;
	}
	return null;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#defineRole(cscie97.asn4.squaredesk.authentication.Role)
     */
    @Override
    public Role defineRole(Role role) throws AuthenticationException {
	if (role == null) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("");
	    throw ex;
	} else {
	    roleMap.put(role.getEntitlementID(), role);
	}
	return null;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#revokeRole(java.lang.String)
     */
    @Override
    public void revokeRole(String roleID) throws AuthenticationException {
	if (roleID == null || roleID.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("");
	    throw ex;
	} else {
	    roleMap.remove(roleID);
	}
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#getRole(java.lang.String)
     */
    @Override
    public Role getRole(String roleID) throws AuthenticationException {
	if (roleID == null || roleID.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("");
	    throw ex;
	} else {
	    if (!roleMap.containsKey(roleID)) {
		AuthenticationException ex = new AuthenticationException();
		ex.setDescription("");
		throw ex;
	    } else {
		roleMap.get(roleID);
	    }
	}
	return null;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#getRoleList()
     */
    @Override
    public List<Role> getRoleList() throws AuthenticationException {
	if (roleMap.size() > 0)
	    return (List<Role>) roleMap.values();
	return null;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#addEntitlement(cscie97.asn4.squaredesk.authentication.Role, cscie97.asn4.squaredesk.authentication.Entitlement)
     */
    @Override
    public Entitlement addEntitlement(Role role, Entitlement entitlement) throws AuthenticationException {
	if (role == null || entitlement == null) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("");
	    throw ex;
	} else {	    
	    if (entitlement instanceof Permission) {
		Map<String, Entitlement> entitlementMap = role.getEntitlementMap();
		if (entitlementMap.size() == 0)
		    entitlementMap = new HashMap<String, Entitlement>();
		entitlementMap.put(entitlement.getEntitlementID(), entitlement);
		role.setEntitlementMap(entitlementMap);
		roleMap.put(role.getEntitlementID(), role);
	    } else if (entitlement instanceof Role) {
		Role addingRole = getRole(entitlement.getEntitlementID());
		role.setEntitlementMap(addingRole.getEntitlementMap());
		roleMap.put(role.getEntitlementID(), role);
	    }
	}
	return null;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#addEntitlement(cscie97.asn4.squaredesk.authentication.User, cscie97.asn4.squaredesk.authentication.Entitlement)
     */
    @Override
    public Entitlement addEntitlement(User user, Entitlement entitlement) throws AuthenticationException {
	// TODO Auto-generated method stub
	return null;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#deleteEntitlement(java.lang.String)
     */
    @Override
    public void deleteEntitlement(String roleID, String entitlementID) throws AuthenticationException {
	if (roleID == null || roleID.length() == 0 || entitlementID == null || entitlementID.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("");
	    throw ex;
	} else {
	    Role role = getRole(roleID);
	    if (role != null) {
		Entitlement entitlement = getEntitlement(entitlementID);
		if (entitlement != null) {
		    Map<String, Entitlement> entitlementMap = role.getEntitlementMap();
		    entitlementMap.remove(entitlement);
		    role.setEntitlementMap(entitlementMap);
		    roleMap.put(role.getEntitlementID(), role);
		}
	    }
	}
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#getEntitlement(java.lang.String)
     */
    @Override
    public Entitlement getEntitlement(String entitlementID) throws AuthenticationException {
	if (entitlementID == null || entitlementID.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("");
	    throw ex;
	} else {
	    if (roleMap.size() > 0) {
		for (Map.Entry<String, Role> roleEntry : roleMap.entrySet()) {
		    Role role = roleEntry.getValue();
		    if (role.getEntitlementMap().containsKey(entitlementID))
			return role.getEntitlementMap().get(entitlementID);
		}
	    }
	}
	return null;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#getEntitlementList()
     */
    @Override
    public List<Entitlement> getEntitlementList() throws AuthenticationException {
	List<Entitlement> entitlementList = new ArrayList<Entitlement>();
	if (roleMap.size() > 0) {
	    for (Map.Entry<String, Role> roleEntry : roleMap.entrySet()) {
		entitlementList.addAll(roleEntry.getValue().getEntitlementMap().values());
	    }
	    return entitlementList;
	} 
	return null;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#createUser(cscie97.asn4.squaredesk.authentication.User)
     */
    @Override
    public User createUser(User user) throws AuthenticationException {
	if (user == null) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("");
	    throw ex;
	} else {
	    userMap.put(user.getUserID(), user);
	}
	return null;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#deleteUser(java.lang.String)
     */
    @Override
    public void deleteUser(String userID) throws AuthenticationException {
	if (userID == null || userID.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("");
	    throw ex;
	} else {
	    userMap.remove(userID);
	}
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#getUser(java.lang.String)
     */
    @Override
    public User getUser(String userID) throws AuthenticationException {
	if (userID == null || userID.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("");
	    throw ex;
	} else {
	    if (!userMap.containsKey(userID)) {
		AuthenticationException ex = new AuthenticationException();
		ex.setDescription("");
		throw ex;
	    } else {
		userMap.get(userID);
	    }
	}
	return null;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#getUserList()
     */
    @Override
    public List<User> getUserList() throws AuthenticationException {
	if (userMap.size() > 0)
	    return (List<User>) userMap.values();
	return null;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#addCredential(cscie97.asn4.squaredesk.authentication.User, cscie97.asn4.squaredesk.authentication.Credential)
     */
    @Override
    public Credential addCredential(User user, Credential credential) throws AuthenticationException {
	if (user == null || credential == null) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("");
	    throw ex;
	} else {
	    if (getUser(user.getUserID()) != null) {
		// User credentials must be null before adding new ones, else, throws an exception
		if (user.getCredential() != null) {
		    AuthenticationException ex = new AuthenticationException();
		    ex.setDescription("");
		    throw ex;
		} else {
		    user.setCredential(credential);
		    userMap.put(user.getUserID(), user);
		}
	    }
	}
	return null;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#deleteCredential(java.lang.String)
     */
    @Override
    public void deleteCredential(String userID) throws AuthenticationException {
	if (userID == null || userID.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("");
	    throw ex;
	} else {
	    User user = getUser(userID);
	    if (user != null) {
		user.setCredential(null);
		userMap.put(userID, user);
	    }
	}
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#getCredential(java.lang.String)
     */
    @Override
    public Credential getCredential(String userID) throws AuthenticationException {
	if (userID == null || userID.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("");
	    throw ex;
	} else {
	    if (userMap.size() == 0) {
		AuthenticationException ex = new AuthenticationException();
		ex.setDescription("");
		throw ex;
	    } else {
		User user = getUser(userID);
		if (user != null)
		    return user.getCredential();
	    }
	}
	return null;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#getCredentialList()
     */
    @Override
    public List<Credential> getCredentialList() throws AuthenticationException {
	List<Credential> credentialList = new ArrayList<Credential>();
	if (userMap.size() > 0) {
	    for (Map.Entry<String, User> userEntry : userMap.entrySet()) {
		Credential credential = userEntry.getValue().getCredential();
		credentialList.add(credential);
	    }
	    return credentialList;
	}
	return null;
    }

}
