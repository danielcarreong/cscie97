/**
 * 
 */
package cscie97.asn3.squaredesk.renter;

import java.util.List;
import java.util.UUID;

import cscie97.asn2.sharedesk.provider.AccessException;
import cscie97.asn2.sharedesk.provider.Renter;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public interface RenterService {

    /**
     * @param authToken
     * @param renter
     * @return Renter
     * @throws RenterAlreadyExistException
     * @throws AccessException
     */
    public Renter createRenter(String authToken, Renter renter)
	    throws RenterAlreadyExistException, AccessException;

    /**
     * @param authToken
     * @param renter
     * @return Renter
     * @throws AccessException
     */
    public Renter updateRenter(String authToken, Renter renter)
	    throws AccessException;

    /**
     * @param authToken
     * @param renterID
     * @throws RenterNotFoundException
     * @throws AccessException
     */
    public void deleteRenter(String authToken, UUID renterID)
	    throws RenterNotFoundException, AccessException;

    /**
     * @param authToken
     * @param renterID
     * @return Renter
     * @throws RenterNotFoundException
     * @throws AccessException
     */
    public Renter getRenter(String authToken, UUID renterID)
	    throws RenterNotFoundException, AccessException;

    /**
     * @param authToken
     * @return List<Renter>
     * @throws AccessException
     */
    public List<Renter> getRenterList(String authToken) throws AccessException;
}
