/**
 * 
 */
package cscie97.asn4.squaredesk.authentication;

import java.util.List;

/**
 * Authentication Service interface defining services for adding and revoking Permission, Users, Services and Roles. 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public interface AuthenticationService {

    /**
     * Defines a new Service record specifying the Session instance containing proper information.
     * @param authToken 
     * @param service
     * @return Service
     * @throws AuthenticationException 
     */
    public Service defineService(String authToken, Service service) throws AuthenticationException;
    /**
     * Revokes an existing Service record.
     * @param authToken 
     * @param serviceID
     * @throws AuthenticationException 
     */
    public void revokeService(String authToken, String serviceID) throws AuthenticationException;
    /**
     * Gets a specific Service record by providing the service ID. 
     * @param authToken 
     * @param serviceID
     * @return Service
     * @throws AuthenticationException 
     */
    public Service getService(String authToken, String serviceID) throws AuthenticationException;
    /**
     * Returns a list of all current Services records. 
     * @param authToken 
     * @return Service List
     */
    public List<Service> getServiceList(String authToken);
    /**
     * Defines a new Permission record by defining the Service which is related to and proper Permission information. 
     * @param authToken 
     * @param service
     * @param permission
     * @return Entitlement
     * @throws AuthenticationException 
     */
    public Entitlement definePermission(String authToken, Service service, Entitlement permission) throws AuthenticationException;
    /**
     * Revokes a Permission from a Service by providing their ID.
     * @param authToken 
     * @param serviceID
     * @param permissionID
     * @throws AuthenticationException 
     */
    public void revokePermission(String authToken, String serviceID, String permissionID) throws AuthenticationException;
    /**
     * Retrieves an existing Permission record.
     * @param authToken 
     * @param permissionID
     * @return Entitlement
     * @throws AuthenticationException 
     */
    public Entitlement getPermission(String authToken, String permissionID) throws AuthenticationException;
    /**
     * Retrieves a list of all current existent Permissions.
     * @param authToken 
     * @return Entitlement List of Permissions
     */
    public List<Entitlement> getPermissionList(String authToken);
    /**
     * Defines a new Role record.
     * @param authToken 
     * @param role
     * @return Entitlement
     * @throws AuthenticationException 
     */
    public Entitlement defineRole(String authToken, Entitlement role) throws AuthenticationException;
    /**
     * Revokes a Role record.
     * @param authToken 
     * @param roleID
     * @throws AuthenticationException 
     */
    public void revokeRole(String authToken, String roleID) throws AuthenticationException;
    /**
     * Get an existing Role record.
     * @param authToken 
     * @param roleID
     * @return Entitlement
     * @throws AuthenticationException 
     */
    public Entitlement getRole(String authToken, String roleID) throws AuthenticationException;
    /**
     * Retrieves a list of all Roles.
     * @param authToken 
     * @return Entitlement List of Roles
     */
    public List<Entitlement> getRoleList(String authToken);
    /**
     * 
     * @param authToken 
     * @param role
     * @param entitlement
     * @return Entitlement
     * @throws AuthenticationException 
     */
    public Entitlement addEntitlement(String authToken, Role role, Entitlement entitlement) throws AuthenticationException;
    /**
     * @param authToken 
     * @param userID 
     * @param entitlement
     * @return Entitlement
     * @throws AuthenticationException 
     */
    public Entitlement addEntitlement(String authToken, String userID, Entitlement entitlement) throws AuthenticationException;
    /**
     * @param authToken 
     * @param roleID 
     * @param entitlementID
     * @throws AuthenticationException 
     */
    public void deleteEntitlement(String authToken, String roleID, String entitlementID) throws AuthenticationException;
    /**
     * @param authToken 
     * @param entitlementID
     * @return Entitlement
     * @throws AuthenticationException 
     */
    public Entitlement getEntitlement(String authToken, String entitlementID) throws AuthenticationException;
    /**
     * @param authToken 
     * @return Entitlement List
     */
    public List<Entitlement> getEntitlementList(String authToken);
    
    /**
     * @param authToken 
     * @param user
     * @return User
     * @throws AuthenticationException 
     */
    public User createUser(String authToken, User user) throws AuthenticationException;
    /**
     * @param authToken 
     * @param userID
     * @throws AuthenticationException 
     */
    public void deleteUser(String authToken, String userID) throws AuthenticationException;
    /**
     * @param authToken 
     * @param userID
     * @return User
     * @throws AuthenticationException 
     */
    public User getUser(String authToken, String userID) throws AuthenticationException;
    /**
     * @param authToken 
     * @return User List
     */
    public List<User> getUserList(String authToken);
    
    /**
     * @param authToken 
     * @param userID 
     * @param credential
     * @return Credential
     * @throws AuthenticationException 
     */
    public Credential addCredential(String authToken, String userID, Credential credential) throws AuthenticationException;
    /**
     * @param authToken 
     * @param userID 
     * @param username 
     * @throws AuthenticationException 
     */
    public void deleteCredential(String authToken, String userID, String username) throws AuthenticationException;
    /**
     * @param authToken 
     * @param userID 
     * @param username 
     * @return Credential
     * @throws AuthenticationException 
     */
    public Credential getCredential(String authToken, String userID, String username) throws AuthenticationException;
    /**
     * @param authToken 
     * @return Credential List
     */
    public List<Credential> getCredentialList(String authToken);
    /**
     * @param username 
     * @param password 
     * @return boolean
     * @throws AuthenticationException 
     */
    public User logIn(String username, String password) throws AuthenticationException;
    /**
     * @param username 
     * @return boolean
     * @throws AuthenticationException 
     */
    public User logOut(String username) throws AuthenticationException;
}
