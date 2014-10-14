/**
 * 
 */
package cscie97.asn2.sharedesk.provider;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class Capacity {

    private int numberOfPeople;
    private float squareFeet;
    private int workSpaces;
    
    /**
     * @return the numberOfPeople
     */
    public int getNumberOfPeople() {
        return numberOfPeople;
    }
    /**
     * @param numberOfPeople the numberOfPeople to set
     */
    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }
    /**
     * @return the squareFeet
     */
    public float getSquareFeet() {
        return squareFeet;
    }
    /**
     * @param squareFeet the squareFeet to set
     */
    public void setSquareFeet(float squareFeet) {
        this.squareFeet = squareFeet;
    }
    /**
     * @return the workSpaces
     */
    public int getWorkSpaces() {
        return workSpaces;
    }
    /**
     * @param workSpaces the workSpaces to set
     */
    public void setWorkSpaces(int workSpaces) {
        this.workSpaces = workSpaces;
    }
    
}
