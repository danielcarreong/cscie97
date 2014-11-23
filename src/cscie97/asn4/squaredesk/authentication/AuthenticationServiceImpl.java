/**
 * 
 */
package cscie97.asn4.squaredesk.authentication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Authentication Service interface implementation, defines business logic for creating Services, Permissions, Users, etc.
 * 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class AuthenticationServiceImpl implements AuthenticationService {
    
    private Map<String, User> userMap;
    private Map<String, Service> serviceMap;
    private Map<String, Role> roleMap;
    private static AuthenticationServiceImpl singleton = new AuthenticationServiceImpl();
    private static String AUTHTOKEN = "ADMIN";
    
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
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#defineService(cscie97.asn4.squaredesk.authentication.Service)
     */
    @Override
    public Service defineService(String authToken, Service service) throws AuthenticationException {
	if (service == null) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("An error ocurred while defining Service. You must provide valid information.");
	    throw ex;
	} else {
	    return serviceMap.put(service.getServiceID(), service);
	}
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#revokeService(java.lang.String)
     */
    @Override
    public void revokeService(String authToken, String serviceID) throws AuthenticationException {
	if (serviceID == null || serviceID.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("An error ocurred while revoking Service. You must provide valid information.");
	    throw ex;
	} else {
	    serviceMap.remove(serviceID);
	}
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#getService(java.lang.String)
     */
    @Override
    public Service getService(String authToken, String serviceID) throws AuthenticationException {
	if (serviceID == null || serviceID.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("An error ocurred while retrieving Service. You must provide valid information.");
	    throw ex;
	} else {
	    if (!serviceMap.containsKey(serviceID)) {
		return null;
	    } else {
		return serviceMap.get(serviceID);
	    }
	}
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#getServiceList()
     */
    @Override
    public List<Service> getServiceList(String authToken) {
	if (serviceMap.size() > 0)
	    return new ArrayList<Service>(serviceMap.values());
	return null;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#definePermission(cscie97.asn4.squaredesk.authentication.Service, cscie97.asn4.squaredesk.authentication.Permission)
     */
    @Override
    public Entitlement definePermission(String authToken, Service service, Entitlement permission) throws AuthenticationException {
	if (service == null || permission == null) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("An error ocurred while defining Permission. You must provide valid information.");
	    throw ex;
	} else {
	    if (getService(authToken, service.getServiceID()) != null) {
		Map<String, Permission> permissionMap = null;
		if (service.getPermissionMap() != null && service.getPermissionMap().size() > 0)
		    permissionMap = service.getPermissionMap();
		else
		    permissionMap = new HashMap<String, Permission>();
		
		permissionMap.put(permission.getEntitlementID(), (Permission) permission);
		service.setPermissionMap(permissionMap);
		serviceMap.put(service.getServiceID(), service);
		return permission;
	    }
	}
	return null;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#revokePermission(java.lang.String, java.lang.String)
     */
    @Override
    public void revokePermission(String authToken, String serviceID, String permissionID) throws AuthenticationException {
	if (serviceID == null || serviceID.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("An error ocurred while revoking Permission. You must provide valid information.");
	    throw ex;
	} else {
	    Service service = getService(authToken, serviceID);
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
    public Permission getPermission(String authToken, String permissionID) throws AuthenticationException {
	if (permissionID == null || permissionID.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("An error ocurred while retrieving Permission. You must provide valid information.");
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
    public List<Entitlement> getPermissionList(String authToken) {
	List<Entitlement> permissionList = new ArrayList<Entitlement>();
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
    public Role defineRole(String authToken, Entitlement entitlement) throws AuthenticationException {
	if (entitlement == null) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("An error ocurred while defining Role. You must provide valid information.");
	    throw ex;
	} else {
	    if (entitlement instanceof Role)
		return roleMap.put(entitlement.getEntitlementID(), (Role) entitlement);
	}
	return null;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#revokeRole(java.lang.String)
     */
    @Override
    public void revokeRole(String authToken, String roleID) throws AuthenticationException {
	if (roleID == null || roleID.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("An error ocurred while revoking Role. You must provide valid information.");
	    throw ex;
	} else {
	    roleMap.remove(roleID);
	}
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#getRole(java.lang.String)
     */
    @Override
    public Role getRole(String authToken, String roleID) throws AuthenticationException {
	//System.out.println("looking Role:" + roleID + ":" + roleMap.containsKey(roleID));
	if (roleID == null || roleID.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("An error ocurred while retrieving Role. You must provide valid information.");
	    throw ex;
	} else {
	    if (!roleMap.containsKey(roleID))
		return null;
	    else
		return roleMap.get(roleID);
	}
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#getRoleList()
     */
    @Override
    public List<Entitlement> getRoleList(String authToken) {
	if (userMap.size() > 0)
	    return new ArrayList<Entitlement>(roleMap.values());
	return null;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#addEntitlement(cscie97.asn4.squaredesk.authentication.Role, cscie97.asn4.squaredesk.authentication.Entitlement)
     */
    @Override
    public Entitlement addEntitlement(String authToken, Role role, Entitlement entitlement) throws AuthenticationException {
	if (role == null || entitlement == null) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("An error ocurred while adding Entitlement. You must provide valid information.");
	    throw ex;
	} else {	    
	    if (entitlement instanceof Permission) {
		Map<String, Entitlement> entitlementMap = role.getEntitlementMap();
		if (entitlementMap == null || entitlementMap.size() == 0)
		    entitlementMap = new HashMap<String, Entitlement>();
		entitlementMap.put(entitlement.getEntitlementID(), entitlement);
		role.setEntitlementMap(entitlementMap);
		roleMap.put(role.getEntitlementID(), role);
	    } else if (entitlement instanceof Role) {
		Role addingRole = getRole(authToken, entitlement.getEntitlementID());
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
    public Entitlement addEntitlement(String authToken, String userID, Entitlement entitlement) throws AuthenticationException {
	if (userID == null || userID.length() == 0 || entitlement == null) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("An error ocurred while adding Entitlement. You must provide valid information.");
	    throw ex;
	} else {
	    User user = getUser(authToken, userID);
	    if (user != null) {
		if (entitlement instanceof Role) {
		    Role role = getRole(authToken, entitlement.getEntitlementID());
		    if (role != null)
			user.setRole((Role) entitlement);
		    	userMap.put(user.getUserID(), user);
		    	return role;
		}
	    }
	}
	return null;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#deleteEntitlement(java.lang.String)
     */
    @Override
    public void deleteEntitlement(String authToken, String roleID, String entitlementID) throws AuthenticationException {
	if (roleID == null || roleID.length() == 0 || entitlementID == null || entitlementID.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("An error ocurred while deleting Entitlement. You must provide valid information.");
	    throw ex;
	} else {
	    Role role = getRole(authToken, roleID);
	    if (role != null) {
		Entitlement entitlement = getEntitlement(authToken, entitlementID);
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
    public Entitlement getEntitlement(String authToken, String entitlementID) throws AuthenticationException {
	if (entitlementID == null || entitlementID.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("An error ocurred while retrieving Entitlement. You must provide valid information.");
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
    public List<Entitlement> getEntitlementList(String authToken) {
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
    public User createUser(String authToken, User user) throws AuthenticationException {
	if (user == null) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("An error ocurred while creating User. You must provide valid information.");
	    throw ex;
	} else {
	    return userMap.put(user.getUserID(), user);
	}
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#deleteUser(java.lang.String)
     */
    @Override
    public void deleteUser(String authToken, String userID) throws AuthenticationException {
	if (userID == null || userID.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("An error ocurred while deleting User. You must provide valid information.");
	    throw ex;
	} else {
	    userMap.remove(userID);
	}
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#getUser(java.lang.String)
     */
    @Override
    public User getUser(String authToken, String userID) throws AuthenticationException {
	if (userID == null || userID.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("An error ocurred while retrieving User. You must provide valid information.");
	    throw ex;
	} else {
	    if (!userMap.containsKey(userID))
		return null;
	    else
		return userMap.get(userID);
	}
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#getUserList()
     */
    @Override
    public List<User> getUserList(String authToken) {
	if (userMap.size() > 0)
	    return new ArrayList<User>(userMap.values());
	return null;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#addCredential(cscie97.asn4.squaredesk.authentication.User, cscie97.asn4.squaredesk.authentication.Credential)
     */
    @Override
    public Credential addCredential(String authToken, String userID, Credential credential) throws AuthenticationException {
	if (userID == null || userID.length() == 0 || credential == null) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("An error ocurred while adding Credential. You must provide valid information.");
	    throw ex;
	} else {
	    User user = getUser(authToken, userID);
	    if (user != null) {
		HashMap<String, Credential> credentialMap = null;
		// if user have a credentialMap defined, look for credential to be added, if found return it, if not, add it to the Map
		if (user.getCredentialMap() != null && user.getCredentialMap().size() > 0) {
		    if (user.getCredentialMap().containsKey(credential.getUsername()))
			return user.getCredentialMap().get(credential.getUsername());
		    else
			credentialMap = (HashMap<String, Credential>) user.getCredentialMap();
		} else
		    credentialMap = new HashMap<String, Credential>();
		
		// encrypts password
		if (credential.getUsername() != null && credential.getPassword() != null)
		    credential.setPassword(getSecurePassword(credential.getPassword(), credential.getUsername()));
		credentialMap.put(credential.getUsername(), credential);
		user.setCredentialMap(credentialMap);
		userMap.put(user.getUserID(), user);
		return user.getCredentialMap().get(credential);
	    }
	}
	return null;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#deleteCredential(java.lang.String)
     */
    @Override
    public void deleteCredential(String authToken, String userID, String username) throws AuthenticationException {
	if (userID == null || userID.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("An error ocurred while deleting Credential. You must provide valid information.");
	    throw ex;
	} else {
	    User user = getUser(authToken, userID);
	    if (user != null) {
		user.getCredentialMap().remove(username);
		userMap.put(userID, user);
	    }
	}
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#getCredential(java.lang.String)
     */
    @Override
    public Credential getCredential(String authToken, String userID, String username) throws AuthenticationException {
	if (userID == null || userID.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("An error ocurred while retrieving Credential. You must provide valid information.");
	    throw ex;
	} else {
	    if (userMap.size() == 0) {
		AuthenticationException ex = new AuthenticationException();
		ex.setDescription("");
		throw ex;
	    } else {
		User user = getUser(authToken, userID);
		if (user != null)
		    return user.getCredentialMap().get(username);
	    }
	}
	return null;
    }
    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#getCredentialList()
     */
    @Override
    public List<Credential> getCredentialList(String authToken) {
	List<Credential> credentialList = new ArrayList<Credential>();
	if (userMap.size() > 0) {
	    for (Map.Entry<String, User> userEntry : userMap.entrySet()) {
		Credential credential = (Credential) userEntry.getValue().getCredentialMap().values();
		credentialList.add(credential);
	    }
	    return credentialList;
	}
	return null;
    }

    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#logIn(cscie97.asn4.squaredesk.authentication.User)
     */
    @Override
    public User logIn(String username, String password) throws AuthenticationException {
	if (username == null || username.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("An error ocurred while logging in. You must provide valid information. Username = " + username + " / password = " + password);
	    throw ex;
	} else {
	    for (User user : userMap.values()) {
		Credential credential = user.getCredentialMap().get(username);
		// if user is found, compare passwords
		if (credential != null) {
		    String encryptUserPass = getSecurePassword(password, username);
		    // if user and password match, create a session
		    if (credential.getPassword().equalsIgnoreCase(encryptUserPass)) {
			Session session = new Session();
			Calendar cal = Calendar.getInstance();
			// makes Session valid, set TimeStamp and authToken
			session.setAuthToken(UUID.randomUUID());
			session.setTimeStamp(cal.getTime());
			session.setValid(true);
			user.setSession(session);
			userMap.put(user.getUserID(), user);
			System.out.println("Logged In succesfully.");
			return user;
		    }
		}
	    }
	}
	return null;
    }

    /* (non-Javadoc)
     * @see cscie97.asn4.squaredesk.authentication.AuthenticationService#logOut(cscie97.asn4.squaredesk.authentication.User)
     */
    @Override
    public User logOut(String username) throws AuthenticationException {
	if (username == null || username.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("An error ocurred while logging in. You must provide valid information. Username = " + username);
	    throw ex;
	} else {
	    for (User user : userMap.values()) {
		Credential credential = user.getCredentialMap().get(username);
		if (credential != null) {
		    Session session = user.getSession();
		    // invalids Session
		    if (session != null && session.isValid()) {
			session.setValid(false);
			user.setSession(session);
			userMap.put(user.getUserID(), user);
			System.out.println("Logged Out succesfully.");
			return user;
		    }
		}
	    }
	}
	return null;
    }
    /**
     * Password encryption method.
     * Reusing code from: 
     * http://howtodoinjava.com/2013/07/22/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
     * As suggested method in one of the posts from discussion forum
     * 
     */
    private String getSecurePassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(salt.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest(passwordToHash.getBytes());
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
    
    /**
     * Verifies if a authToken given by a User is authorized to call specified Service name.
     * @param serviceName
     * @param authToken
     * @return boolean representing validation
     * @throws AuthenticationException
     */
    public boolean checkAccess(String serviceName, String authToken) throws AuthenticationException {
	if (serviceName == null || serviceName.length() == 0 || authToken == null || authToken.length() == 0) {
	    AuthenticationException ex = new AuthenticationException();
	    ex.setDescription("Please provide a correct Service Name and AuthToken.");
	    throw ex;
	} else {
	    if (authToken.equalsIgnoreCase(AUTHTOKEN)) {
		return true;
	    } else {
		User user = null;
		for (User userEntry : userMap.values()) {
		    if (userEntry.getSession() != null) {
			if (userEntry.getSession().getAuthToken().toString().equalsIgnoreCase(authToken))
			    user = userEntry;
		    }
		}
		
		if (user != null) {
		    Session session = user.getSession();
		    if (session.isValid()) {
			if (user.getRole() != null) {
			    Map<String, Entitlement> entitlementMap = user.getRole().getEntitlementMap();
			    if (entitlementMap.containsKey(serviceName))
				return true;
			    else {
				System.out.println("User is not authorized to execute: " + serviceName);
			    }
			} else {
			    System.out.println("No Role is assigned for this User.");
			}
		    } else {
			System.out.println("Session is not valid.");
		    }
		} else {
		    System.out.println("User not found.");
		}
	    }
	}
	return false;
    }

}
