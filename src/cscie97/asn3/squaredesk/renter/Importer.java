/**
 * 
 */
package cscie97.asn3.squaredesk.renter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.yaml.snakeyaml.Yaml;

import cscie97.asn2.sharedesk.provider.Account;
import cscie97.asn2.sharedesk.provider.Image;
import cscie97.asn2.sharedesk.provider.Renter;

/**
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class Importer {
    
    private static final String AUTHTOKEN = "admin";
    private static final String PROFILE = "renter";
    private static final String NAME = "name";
    private static final String CONTACT = "contact";
    private static final String PICTURE = "picture";
    private static final String ACCOUNT = "account";
    private static final String PAYPAL = "payPalAccountNumber";
    
    /**
     * @param fileName
     * @throws ImportException 
     * @throws AccessException 
     * @throws ProviderException 
     * @throws OfficeSpaceException 
     * @throws ProviderAlreadyExistException 
     */
    public void importYamlFile(String fileName) throws ImportException {
	
	RenterServiceImpl rsi = RenterServiceImpl.getInstance();
	
	ImportException ie = new ImportException();
	System.out.println("Importing document ...");
	try(InputStream input = new FileInputStream(new File(fileName))) {
	    
	    Yaml yaml = new Yaml();
	    Object yamlObj = yaml.load(input);
	    Renter renter = null;
	    
	    if (!validInput(yamlObj)) {
		ie.setDescription("File is empty.");
		throw ie;
	    } else {
		LinkedHashMap<Object, Object> yamlMap = (LinkedHashMap<Object, Object>) yamlObj;
		
		if (yamlMap.size() == 0) {
		    ie.setDescription("YAML document is malformed.");
		    throw ie;
		} else {
		    LinkedHashMap<Object, Object> data = (LinkedHashMap<Object, Object>) yamlMap.get(PROFILE);
		    
		    if (!validInput(data)) {
			ie.setDescription("User role is different from Renter.");
			throw ie;
		    } else {
			UUID identifier = null;
			// Renter
			renter = new Renter();
			identifier = UUID.randomUUID();
			renter.setIdentifier(identifier);
			
			if (validInput(data.get(NAME)))
			    renter.setName(data.get(NAME).toString());
			
			if (validInput(data.get(CONTACT)))
			    renter.setContact(data.get(CONTACT).toString());
			
			if (validInput(data.get(PICTURE))) {
			    Image picture = new Image();
			    picture.setURI(data.get(PICTURE).toString());
			    renter.setPicture(picture);
			}
			// Account
			LinkedHashMap<Object, Object> accountMap = (LinkedHashMap<Object, Object>) data.get(ACCOUNT);
			if (validInput(accountMap)) {
			    Account account = new Account();
			    account.setPayPal(accountMap.get(PAYPAL).toString());
			    renter.setAccount(account);
			}
			
			rsi.createRenter(AUTHTOKEN, renter);
			System.out.println("Document imported succesfully.\n");
		    }
		}
	    }
	} catch (ImportException e) {
	    throw e;
	} catch (IOException e) {
            e.printStackTrace();
            ie.setDescription(e.getMessage());
            throw ie;
        }
    }
    
    private boolean validInput(Object obj) {
	
	if (obj instanceof String) {
	    String strObj = obj.toString();
	    if (strObj != null && strObj.length() > 0)
		return true;
	} else if (obj instanceof Map) {
	    Map<Object, Object> mapObj = (Map<Object, Object>) obj;
	    if (mapObj != null && mapObj.size() > 0)
		return true;
	} else if (obj instanceof AbstractList) {
	    ArrayList<Object> listObj = (ArrayList<Object>) obj;
	    if (listObj != null && listObj.size() > 0)
		return true;
	} else if (obj != null)
	    return true;
	
	return false;
    }
}
