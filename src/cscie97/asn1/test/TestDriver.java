/**
 * 
 */
package cscie97.asn1.test;

import cscie97.asn1.knowledge.engine.Importer;
import cscie97.asn1.knowledge.engine.QueryEngine;

/**
 * Class defined to run KnowledgeGraph processing by reading a single
 * input File and a single query File.
 * 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class TestDriver {

    /**
     * Main method
     * 
     * @param args
     */
    public static void main(String[] args) {
	// verifies if 2 arguments are set, corresponding to input and query files
	if (args.length > 1) {
	    // verifies if 1st file name is set, if so, run Importer
	    if (args[0].length() > 0) {
		Importer importer = new Importer();
		//importer.importTripleFile("inputTriples.nt");
		importer.importTripleFile(args[0]);
		// check if 2nd file name is set, if so, run QueryEngine 
		if (args[1].length() > 0) {
		    QueryEngine engine = new QueryEngine();
		    //engine.executeQueryFile("sampleQuery.nt");
		    engine.executeQueryFile(args[1]);
		} else
		    System.out.println("You have to specify a queryFileName.");
		
	    } else
		System.out.println("You have to specify an inputFileName.");
	    
	} else
	    System.out.println("Program arguments should be: inputFileName queryFileName. Please try again.");
    }
}
