/**
 * 
 */
package cscie97.asn2.sharedesk.provider;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class Rate {

    private RateType period;
    private String cost;
    
    /**
     * @return the period
     */
    public RateType getPeriod() {
        return period;
    }
    /**
     * @param period the period to set
     */
    public void setPeriod(RateType period) {
        this.period = period;
    }
    /**
     * @return the cost
     */
    public String getCost() {
        return cost;
    }
    /**
     * @param cost the cost to set
     */
    public void setCost(String cost) {
        this.cost = cost;
    }
    
}
