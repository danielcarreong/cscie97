/**
 * 
 */
package cscie97.asn3.squaredesk.renter;

import java.util.Date;
import java.util.UUID;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class Booking implements Comparable<Booking> {

    private UUID bookingID;
    private UUID renterID;
    private UUID officeSpaceID;
    private String period;
    private float rate;
    private Date startDate;
    private Date endDate;
    private String paymentStatus;
    
    /**
     * @return the booking
     */
    public UUID getBooking() {
        return bookingID;
    }
    /**
     * @param bookingID the bookingID to set
     */
    public void setBooking(UUID bookingID) {
        this.bookingID = bookingID;
    }
    /**
     * @return the renterID
     */
    public UUID getRenterID() {
        return renterID;
    }
    /**
     * @param renterID the renterID to set
     */
    public void setRenterID(UUID renterID) {
        this.renterID = renterID;
    }
    /**
     * @return the officeSpaceID
     */
    public UUID getOfficeSpaceID() {
        return officeSpaceID;
    }
    /**
     * @param officeSpaceID the officeSpaceID to set
     */
    public void setOfficeSpaceID(UUID officeSpaceID) {
        this.officeSpaceID = officeSpaceID;
    }
    /**
     * @return the period
     */
    public String getPeriod() {
        return period;
    }
    /**
     * @param period the period to set
     */
    public void setPeriod(String period) {
        this.period = period;
    }
    /**
     * @return the rate
     */
    public float getRate() {
        return rate;
    }
    /**
     * @param rate the rate to set
     */
    public void setRate(float rate) {
        this.rate = rate;
    }
    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }
    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }
    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    /**
     * @return the paymentStatus
     */
    public String getPaymentStatus() {
        return paymentStatus;
    }
    /**
     * @param paymentStatus the paymentStatus to set
     */
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Booking o) {
	// TODO Auto-generated method stub
	return 0;
    }
}
