/**
 * 
 */
package cscie97.asn3.squaredesk.renter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import cscie97.asn2.sharedesk.provider.Renter;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class RenterServiceImpl implements RenterService {
    
    private Map<String, Renter> renterMap;
    private static RenterServiceImpl singleton = new RenterServiceImpl();
    
    private RenterServiceImpl() {};
    
    /**
     * @return RenterServiceImpl single instance
     */
    public static RenterServiceImpl getInstance() {
	return singleton;
    }

    /* (non-Javadoc)
     * @see cscie97.asn3.squaredesk.renter.RenterService#createRenter(java.lang.String, cscie97.asn2.sharedesk.provider.Renter)
     */
    @Override
    public Renter createRenter(String authToken, Renter renter) {
	// TODO Auto-generated method stub
	return null;
    }

    /* (non-Javadoc)
     * @see cscie97.asn3.squaredesk.renter.RenterService#updateRenter(java.lang.String, cscie97.asn2.sharedesk.provider.Renter)
     */
    @Override
    public Renter updateRenter(String authToken, Renter renter) {
	// TODO Auto-generated method stub
	return null;
    }

    /* (non-Javadoc)
     * @see cscie97.asn3.squaredesk.renter.RenterService#deleteRenter(java.lang.String, java.util.UUID)
     */
    @Override
    public void deleteRenter(String authToken, UUID renterID) {
	// TODO Auto-generated method stub
	
    }

    /* (non-Javadoc)
     * @see cscie97.asn3.squaredesk.renter.RenterService#getRenter(java.lang.String, java.util.UUID)
     */
    @Override
    public Renter getRenter(String authToken, UUID renterID) {
	// TODO Auto-generated method stub
	return null;
    }

    /* (non-Javadoc)
     * @see cscie97.asn3.squaredesk.renter.RenterService#getRenterList(java.lang.String)
     */
    @Override
    public List<Renter> getRenterList(String authToken) {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * @return the renterMap
     */
    public Map<String, Renter> getRenterMap() {
	return renterMap;
    }

    /**
     * @param renterMap the renterMap to set
     */
    public void setRenterMap(Map<String, Renter> renterMap) {
	this.renterMap = renterMap;
    }

}
