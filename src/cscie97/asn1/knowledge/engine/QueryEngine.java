/**
 * 
 */
package cscie97.asn1.knowledge.engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

/**
 * Reads input query file in order to calculate all possible results in every query set.
 * Takes KnowledgeGraph instance to pass on every query from input query File to 
 * retrieve all Triple sets resulting from it. 
 * 
 * @author Carlos Daniel Carreon Guzman
 */
public class QueryEngine {
    
    private static KnowledgeGraph kg = KnowledgeGraph.getInstance();

    /**
     * Executes a query input on KnowledgeGraph. Takes query input
     * as String and prints out all possible results.
     * 
     * @param strQuery String query input in a form of Triplet
     * @throws QueryEngineException 
     */
    public void executeQuery(String strQuery) throws QueryEngineException {
	
	int lineLenght = strQuery.length();
	String subject = "";
	String predicate = "";
	String object = "";
	Triple tripleQuery = null;
	// verifies if line read is not blank and last char is a dot
	if (strQuery.length() > 0 && strQuery.indexOf(".") == (lineLenght - 1)) {
	    
	    print("input query:");
	    print(strQuery);
	    print("");
	    
	    // removes last dot
	    strQuery = strQuery.substring(0, lineLenght - 1);
	    // splits line read in order to acquire subject + predicate + object query
	    String strTriple[] = strQuery.split(" ");
	    // ensure array has 3 split parts
	    if (strTriple.length == 3) {
		
		subject = strTriple[0].trim();
		predicate = strTriple[1].trim();
		object = strTriple[2].trim();
		// recreate triplet in order to query knowledge graph
		tripleQuery = new Triple();
		tripleQuery.setSubject(kg.getNode(subject));
		tripleQuery.setPredicate(kg.getPredicate(predicate));
		tripleQuery.setObject(kg.getNode(object));
		tripleQuery.setIdentifier(strQuery);
		// obtain query result
		Set<Triple> queryResult = kg.executeQuery(tripleQuery);
		print("output query:");
		// output results, else print null
		if (queryResult != null) {
		    Iterator<Triple> itr = queryResult.iterator();
		    while(itr.hasNext()) {
			Triple obj = (Triple) itr.next();
			print(obj.getIdentifier() + ".");
		    }
		}
		else
		    print("<null>");
		
		print("");
		
	    } else
		throw new QueryEngineException("Query doesn't have the correct number of arguments (? ? ?). Query: " + strQuery);
	} else
	    throw new QueryEngineException("Query Triple is malformed. Verify it ends with a period. Query: " + strQuery);
    }
    
    /**
     * Reads and queries all inputs from a file.
     * Each query input is processed by executeQuery method. 
     * 
     * @param fileName input File name
     */
    public void executeQueryFile(String fileName) {
	
	try (BufferedReader buffReader = new BufferedReader(new FileReader(fileName))) {

	    String strLine;
	    while ((strLine = buffReader.readLine()) != null) {
		try {
		    executeQuery(strLine);
		} catch (QueryEngineException e) {
		    e.getException();
		}
	    }
	} catch (IOException e){
	    System.out.println("There was an error trying to read query file. Verify file and path are correct.");
	}
    }
    
    private void print(String statement) {
	System.out.println(statement);
    }
}
