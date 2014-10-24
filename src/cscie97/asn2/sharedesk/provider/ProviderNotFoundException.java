/**
 * 
 */
package cscie97.asn2.sharedesk.provider;

/**
 * ProviderException indicating a Provider record was not found
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class ProviderNotFoundException extends ProviderException {

    private static final long serialVersionUID = 1L;
    
    @Override
    public String toString() {
	return "ProviderNotFoundException {" + 
			"errorDescription: '" + getDescription() + "'}";
    }
}
