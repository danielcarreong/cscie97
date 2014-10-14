/**
 * 
 */
package cscie97.asn2.sharedesk.provider;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class ImportException extends Exception {

    private static final long serialVersionUID = 1L;
    private String fileName;
    private String description;
    
    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }
    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
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
    @Override
    public String toString() {
        return "ImportException{" +
                "filename='" + fileName + '\'' +
                ", errorDescription='" + description + '\'' +
                '}';
    }
}
