/**
 * 
 */
package cscie97.asn2.sharedesk.provider;

/**
 * Class representing an OfficeSpace image or a User profile picture.
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class Image {

    private String name;
    private String description;
    private String URI;
    
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return the uRI
     */
    public String getURI() {
        return URI;
    }
    /**
     * @param uRI the uRI to set
     */
    public void setURI(String uRI) {
        URI = uRI;
    }
    
}
