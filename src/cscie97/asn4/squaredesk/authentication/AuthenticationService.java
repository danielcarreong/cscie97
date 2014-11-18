/**
 * 
 */
package cscie97.asn4.squaredesk.authentication;

import java.util.List;

/**
 * Authentication Service interface that defines CRUD services. 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public interface AuthenticationService {

    /**
     * @param service
     * @return Service
     * @throws AuthenticationException 
     */
    public Service defineService(Service service) throws AuthenticationException;
    /**
     * @param serviceID
     * @throws AuthenticationException 
     */
    public void revokeService(String serviceID) throws AuthenticationException;
    /**
     * @param serviceID
     * @return Service
     * @throws AuthenticationException 
     */
    public Service getService(String serviceID) throws AuthenticationException;
    /**
     * @return Service List
     * @throws AuthenticationException 
     */
    public List<Service> getServiceList() throws AuthenticationException;
    
    /**
     * @param service
     * @param permission
     * @return Permission
     * @throws AuthenticationException 
     */
    public Permission definePermission(Service service, Permission permission) throws AuthenticationException;
    /**
     * @param serviceID
     * @param permissionID
     * @throws AuthenticationException 
     */
    public void revokePermission(String serviceID, String permissionID) throws AuthenticationException;
    /**
     * @param permissionID
     * @return Permission
     * @throws AuthenticationException 
     */
    public Permission getPermission(String permissionID) throws AuthenticationException;
    /**
     * @return Permission List
     * @throws AuthenticationException 
     */
    public List<Permission> getPermissionList() throws AuthenticationException;
    
    /**
     * @param role
     * @return Role
     * @throws AuthenticationException 
     */
    public Role defineRole(Role role) throws AuthenticationException;
    /**
     * @param roleID
     * @throws AuthenticationException 
     */
    public void revokeRole(String roleID) throws AuthenticationException;
    /**
     * @param roleID
     * @return Role
     * @throws AuthenticationException 
     */
    public Role getRole(String roleID) throws AuthenticationException;
    /**
     * @return Role List
     * @throws AuthenticationException 
     */
    public List<Role> getRoleList() throws AuthenticationException;
    
    /**
     * @param role
     * @param entitlement
     * @return Entitlement
     * @throws AuthenticationException 
     */
    public Entitlement addEntitlement(Role role, Entitlement entitlement) throws AuthenticationException;
    /**
     * @param user
     * @param entitlement
     * @return Entitlement
     * @throws AuthenticationException 
     */
    public Entitlement addEntitlement(User user, Entitlement entitlement) throws AuthenticationException;
    /**
     * @param roleID 
     * @param entitlementID
     * @throws AuthenticationException 
     */
    public void deleteEntitlement(String roleID, String entitlementID) throws AuthenticationException;
    /**
     * @param entitlementID
     * @return Entitlement
     * @throws AuthenticationException 
     */
    public Entitlement getEntitlement(String entitlementID) throws AuthenticationException;
    /**
     * @return Entitlement List
     * @throws AuthenticationException 
     */
    public List<Entitlement> getEntitlementList() throws AuthenticationException;
    
    /**
     * @param user
     * @return User
     * @throws AuthenticationException 
     */
    public User createUser(User user) throws AuthenticationException;
    /**
     * @param userID
     * @throws AuthenticationException 
     */
    public void deleteUser(String userID) throws AuthenticationException;
    /**
     * @param userID
     * @return User
     * @throws AuthenticationException 
     */
    public User getUser(String userID) throws AuthenticationException;
    /**
     * @return User List
     * @throws AuthenticationException 
     */
    public List<User> getUserList() throws AuthenticationException;
    
    /**
     * @param user
     * @param credential
     * @return Credential
     * @throws AuthenticationException 
     */
    public Credential addCredential(User user, Credential credential) throws AuthenticationException;
    /**
     * @param userID 
     * @throws AuthenticationException 
     */
    public void deleteCredential(String userID) throws AuthenticationException;
    /**
     * @return Credential
     * @throws AuthenticationException 
     */
    public Credential getCredential(String userID) throws AuthenticationException;
    /**
     * @return Credential List
     * @throws AuthenticationException 
     */
    public List<Credential> getCredentialList() throws AuthenticationException;
}
