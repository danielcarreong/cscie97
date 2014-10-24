/**
 * 
 */
package cscie97.asn2.sharedesk.provider;

/**
 * Class defining OfficeSpace Location information, contains an Address association
 * in order to specify OfficeSpace address.
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class Location {
    
    private String name;
    private double lat;
    private double lon;
    private Address address;
    
    /**
     * @return the lat
     */
    public double getLat() {
        return lat;
    }
    /**
     * @param lat the lat to set
     */
    public void setLat(double lat) {
        this.lat = lat;
    }
    /**
     * @return the lon
     */
    public double getLon() {
        return lon;
    }
    /**
     * @param lon the lon to set
     */
    public void setLon(double lon) {
        this.lon = lon;
    }
    /**
     * @return the name
     */
    public String getName() {
	return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
	this.name = name;
    }
    /**
     * @return the adress
     */
    public Address getAddress() {
	return address;
    }
    /**
     * @param adress the adress to set
     */
    public void setAddress(Address adress) {
	this.address = adress;
    }

}
