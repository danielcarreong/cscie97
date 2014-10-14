/**
 * 
 */
package cscie97.asn1.knowledge.engine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * KnowledgeGraph is a singleton class that initialize and sets/gets all maps
 * content by receiving triplets in String form.
 * Also creates the queryMapSet which receives queries and returns all
 * permutations stored in the Map. 
 * 
 * @author Carlos Daniel Carreon Guzman
 */
public class KnowledgeGraph {

    private static Map<String, Node> nodeMap;
    private static Map<String, Predicate> predicateMap;
    private static Map<String, Triple> tripleMap;
    private static Map<String, Set<Triple>> queryMapSet;
    private static KnowledgeGraph singleton = new KnowledgeGraph();
    
    /**
     * private constructor that prevents class to be instantiated.
     * Also initialize all Maps.
     */
    private KnowledgeGraph() {
	nodeMap = new HashMap<String, Node>();
	predicateMap = new HashMap<String, Predicate>();
	tripleMap = new HashMap<String, Triple>();
	queryMapSet = new HashMap<String, Set<Triple>>();
    }
    
    /**
     * Looks up for the Triple defined as parameter, returns a Set of
     * all Triplets found, or null if there's no match.
     * 
     * @param query a Triple type parameter which executeQuery will look for
     * @return Set<Triple> the set of Triplets found in queryMapSet, or null
     */
    public Set<Triple> executeQuery(Triple query) {
	return queryMapSet.get(query.getIdentifier());
    }
    
    /**
     * Retrieves a single instance of the KnowledgeGraph class
     * 
     * @return KnowledgeGraph single instance of class
     */
    public static KnowledgeGraph getInstance() {
	return singleton;
    }
    
    /**
     * Returns a Node object type if exists, if not, it sets
     * it's contents into nodeMap as an entry. A Node can function as
     * subject or object, both as treated as equal.
     *  
     * @param identifier parameter that defines a Node uniquely in the nodeMap.
     * @return Node object that matches identifies uniquely by identifier parameter.
     */
    public Node getNode(String identifier) {
	
	Node node = null;
	
	if (!nodeMap.isEmpty()) {
	    // loop trough nodeMap in order to find given identifier
	    for (Map.Entry<String, Node> entry : nodeMap.entrySet()) {
		
		node = entry.getValue();
		if (node.getIdentifier().equalsIgnoreCase(identifier)) {
		    // if map entry is found, return it
		    return node;
		}
	    }
	}
	// if identifier is not found, add it into the node map as new entry
	node = new Node();
	node.setIdentifier(identifier);
	nodeMap.put(String.valueOf(nodeMap.size()), node);
	
	return node;
    }
    
    /**
     * Returns a Predicate object type if exists, if not, set a new 
     * predicate into predicateMap.
     * 
     * @param identifier parameter that defines a Predicate uniquely
     * @return Predicate object found or newly created that matches identifier parameter
     */
    public Predicate getPredicate(String identifier) {
	
	Predicate predicate = null;
	
	if (!predicateMap.isEmpty()) {
	    // loop trough predicateMap in order to find given identifier
	    for (Map.Entry<String, Predicate> entry : predicateMap.entrySet()) {
		
		predicate = entry.getValue();
		if (predicate.getIdentifier().equalsIgnoreCase(identifier)) {
		    // if map entry is found, return it
		    return predicate;
		}
	    }
	}
	// if identifier is not found, add it into the predicate map as new entry
	predicate = new Predicate();
	predicate.setIdentifier(identifier);
	predicateMap.put(String.valueOf(predicateMap.size()), predicate);
	
	return predicate;
    }
    
    /**
     * Sets a new Triple into tripleMap or retrieves it if found.
     * Takes subject, predicate and object parameters to generate a Triple, then
     * looks it up in tripleMap in order to return it or create it.
     * 
     * @param subject Node object set as part of Triple.
     * @param predicate Predicate object.
     * @param object Node set as part of Triple. 
     * @return Triple object found or newly created.
     */
    public Triple getTriple(Node subject, Predicate predicate, Node object) {
	
	Triple triple = null;
	String identifier = subject.getIdentifier() + " " + predicate.getIdentifier() + " " + object.getIdentifier();
	
	if (!tripleMap.isEmpty()) {
	    // loop through tripleMap to find triplet identifier formed by subject + predicate + object
	    for (Map.Entry<String, Triple> entry : tripleMap.entrySet()) {
		
		triple = entry.getValue();
		if (entry.getValue().getIdentifier().equalsIgnoreCase(identifier)) {
		    return triple;
		}
	    }
	}
	// if identifier is not found, add it into the triple map as new entry
	triple = new Triple();
	triple.setIdentifier(identifier);
	triple.setSubject(subject);
	triple.setPredicate(predicate);
	triple.setObject(object);
	tripleMap.put(String.valueOf(tripleMap.size()), triple);
	
	return triple;
    }
    
    /**
     * Permutes all possible variations in a Triple.
     * Once a permutation is set, it's inserted as a new element in queryMapSet list 
     * along with it'scorresponding Triple in order to create a relationship. This
     * permits a fast retrieval of Triplets when a query is set.
     * 
     * @param triple object that is used as base to generate all permutations
     */
    public void addTriplePermutation(Triple triple) {
	
	String strPattern = null;
	Set<Triple> tripleSet = null;
	
	String subject = triple.getSubject().getIdentifier();
	String predicate = triple.getPredicate().getIdentifier();
	String object = triple.getObject().getIdentifier();
	String identifier = "";
	// each triplet have 8 permutations, through a loop all permutation are generated and stored
	for (int i = 0; i < 8; i++) {
	    
	    Triple tripleQuery = new Triple();
	    tripleQuery = triple;
	    // get binary pattern giving i counter variable as parameter, retrieving next permutation
	    strPattern = getBinary(i, "");
	    
	    // if 1st, 2nd or 3rd position of pattern (subject, predicate or object) is a number 1 digit, replace word with question mark (?)
	    if (strPattern.charAt(0) == '1')
		tripleQuery.getSubject().setIdentifier("?");
	    else
		tripleQuery.getSubject().setIdentifier(subject);

	    if (strPattern.charAt(1) == '1')
		tripleQuery.getPredicate().setIdentifier("?");
	    else
		tripleQuery.getPredicate().setIdentifier(predicate);

	    if (strPattern.charAt(2) == '1')
		tripleQuery.getObject().setIdentifier("?");
	    else
		tripleQuery.getObject().setIdentifier(object);

	    identifier = tripleQuery.getSubject().getIdentifier() + " " + tripleQuery.getPredicate().getIdentifier() + " " + tripleQuery.getObject().getIdentifier();
	    // verifies if queryMapSet contains current permutation, if not, creates Set; if does, add an entry to existing Set 
	    if (!queryMapSet.containsKey(identifier))
		tripleSet = new HashSet<Triple>();
	    else
		tripleSet = queryMapSet.get(identifier);
	    
	    tripleSet.add(triple);
	    queryMapSet.put(identifier, tripleSet);
	}
    }

    /**
     * Retrieves the binary representation of an integer number in String format.
     * Binary number is represented as 3 digit long. 
     * 
     * @param number to convert to binary
     * @param trail remainder of operation of converting number to binary
     * @return String representing a binary number
     */
    private String getBinary(int number, String trail) {
	int quotient = number / 2;
	int remainder = number % 2;
	
	if (quotient < 2) {
	    if (trail.equals(""))
		return "0" + String.valueOf(quotient) + String.valueOf(remainder);
	    else
		return String.valueOf(quotient) + String.valueOf(remainder) + trail;
	} else
	    return getBinary(quotient, String.valueOf(remainder));
    }
}
