/**
 * 
 */
package cscie97.asn2.sharedesk.provider;

import java.util.List;
import java.util.UUID;

/**
 * Super class providing general attributes for ShareDesk users
 * @author Carlos Daniel Carreon Guzman
 *
 */
public abstract class User {

    private UUID identifier;
    private String name;
    private String contact;
    private Image picture;
    private List<Rating> ratings;
    private Account account;

    /**
     * @return the identifier
     */
    public UUID getIdentifier() {
        return identifier;
    }
    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(UUID identifier) {
        this.identifier = identifier;
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
     * @return the contact
     */
    public String getContact() {
        return contact;
    }
    /**
     * @param contact the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }
    /**
     * @return the picture
     */
    public Image getPicture() {
        return picture;
    }
    /**
     * @param picture the picture to set
     */
    public void setPicture(Image picture) {
        this.picture = picture;
    }
    /**
     * @return the ratings
     */
    public List<Rating> getRatings() {
        return ratings;
    }

    /**
     * @param ratings the ratings to set
     */
    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
    /**
     * @return the account
     */
    public Account getAccount() {
	return account;
    }
    /**
     * @param account the account to set
     */
    public void setAccount(Account account) {
	this.account = account;
    }
    
    public int hashCode() {
	return name.hashCode();
    }
}
