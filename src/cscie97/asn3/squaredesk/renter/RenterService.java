/**
 * 
 */
package cscie97.asn3.squaredesk.renter;

import java.util.List;
import java.util.UUID;

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
     */
    public Renter createRenter(String authToken, Renter renter);
    /**
     * @param authToken
     * @param renter
     * @return Renter
     */
    public Renter updateRenter(String authToken, Renter renter);
    /**
     * @param authToken
     * @param renterID
     */
    public void deleteRenter(String authToken, UUID renterID);
    /**
     * @param authToken
     * @param renterID
     * @return Renter
     */
    public Renter getRenter(String authToken, UUID renterID);
    /**
     * @param authToken
     * @return List<Renter>
     */
    public List<Renter> getRenterList(String authToken);
}
