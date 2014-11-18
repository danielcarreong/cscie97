/**
 * 
 */
package cscie97.asn4.test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import cscie97.asn2.sharedesk.provider.OfficeSpace;
import cscie97.asn2.sharedesk.provider.OfficeSpaceServiceImpl;
import cscie97.asn2.sharedesk.provider.ProviderServiceImpl;
import cscie97.asn2.sharedesk.provider.Renter;
import cscie97.asn3.squaredesk.renter.Booking;
import cscie97.asn3.squaredesk.renter.RenterServiceImpl;
import cscie97.asn3.squaredesk.renter.SchedulingServiceImpl;
import cscie97.asn4.squaredesk.authentication.AuthenticationException;
import cscie97.asn4.squaredesk.authentication.AuthenticationServiceImpl;
import cscie97.asn4.squaredesk.authentication.Entitlement;
import cscie97.asn4.squaredesk.authentication.Permission;
import cscie97.asn4.squaredesk.authentication.Role;

/**
 * Class used as caller to demonstrate Authentication services.
 * 
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class TestDriver {

    /**
     * @param args
     */
    public static void main(String args[]) {
	Reader in;
	try {
	    in = new FileReader("authentication2.csv");
	    CSVParser parser = CSVFormat.EXCEL.parse(in);
	    List list = parser.getRecords();
	    for (Iterator itr = list.iterator(); itr.hasNext();) {
		CSVRecord record = (CSVRecord) itr.next();
		if (!record.get(0).equalsIgnoreCase("") && !record.get(0).contains("#")) {
		    System.out.println(record.get(0) + " " + record.get(1));
		}
	    }
	    /*
	    Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
	    for (CSVRecord record : records) {
		System.out.println(record.get(""));
	    }
	    */
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
