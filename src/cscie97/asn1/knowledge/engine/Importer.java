/**
 * 
 */
package cscie97.asn1.knowledge.engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads and processes all Triplet inputs from a file.
 * Each Triple is stored in KnowledgeGraph instance in order to keep a database of
 * each sentence. All permutations are generated and stored from each input also.
 * 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class Importer {

    /** 
     * Reads input file and process each line to create Triplets and store them
     * in KnowledgeGraph.
     * 
     * @param fileName as String input file name
     */
    public void importTripleFile(String fileName) {
	// get instance of KnowledgeGraph class in order to use fill every map with file's triplets
	KnowledgeGraph kg = KnowledgeGraph.getInstance();

	try (BufferedReader buffReader = new BufferedReader(new FileReader(fileName))) {
	    
	    String strLine;
	    while ((strLine = buffReader.readLine()) != null) {
		
		int lineLenght = strLine.length();
		// verifies if line read is not blank and last char is a dot
		
		if (strLine.length() > 0) {
		    if (strLine.indexOf(".") == (lineLenght - 1)) {
			// removes last dot
			strLine = strLine.substring(0, lineLenght - 1);
			// splits line read in order to acquire subject + predicate + object
			String strTriple[] = strLine.split(" ");
			// ensure array has 3 split parts
			if (strTriple.length == 3) {
			    // get triple (if exists, if not, get recently new created). Subject, Predicate and Node are also returned/created
			    // then add new triple into query map to generate all possible permutations
			    kg.addTriplePermutation(
				    kg.getTriple(
					    kg.getNode(strTriple[0].trim()), 
					    kg.getPredicate(strTriple[1].trim()), 
					    kg.getNode(strTriple[2].trim())));
			} else
			    System.out.println("Triple doesn't have the correct number of arguments (? ? ?). Triple: " + strLine);
		    } else
			System.out.println("Triple is malformed. Verify it ends with a period. Triple: " + strLine);
		}
	    }
	} catch (IOException e){ 
	    System.out.println("There was an error trying to read input file. Verify file and path are correct.");
	}
    }
}
