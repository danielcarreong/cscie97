/**
 * 
 */
package cscie97.asn2.sharedesk.provider;

import java.util.Date;

/**
 * Class containing User and OfficeSpace rating information 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class Rating {

    private int stars;
    private String comment;
    private Date date;
    private String authorsId;
    
    /**
     * @return the stars
     */
    public int getStars() {
        return stars;
    }
    /**
     * @param stars the stars to set
     */
    public void setStars(int stars) {
        this.stars = stars;
    }
    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }
    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }
    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }
    /**
     * @return the authorsId
     */
    public String getAuthorsId() {
        return authorsId;
    }
    /**
     * @param authorsId the authorsId to set
     */
    public void setAuthorsId(String authorsId) {
        this.authorsId = authorsId;
    }
    
    
}
