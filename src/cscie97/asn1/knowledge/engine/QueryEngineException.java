/**
 * 
 */
package cscie97.asn1.knowledge.engine;

/**
 * Exception custom class that catches common errors while executing
 * QueryEngine processes.
 * 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class QueryEngineException extends Throwable {
    
    private static final long serialVersionUID = 1L;
    private String exception;

    /**
     * @param exception
     */
    public QueryEngineException(String exception) {
	this.exception = exception;
    }

    /**
     * @return the exception
     */
    public String getException() {
	return exception;
    }

    /**
     * @param exception the exception to set
     */
    public void setException(String exception) {
	this.exception = exception;
    }
}
