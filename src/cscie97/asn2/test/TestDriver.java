/**
 * 
 */
package cscie97.asn2.test;

import java.util.Iterator;

import cscie97.asn2.sharedesk.provider.CommonAccess;
import cscie97.asn2.sharedesk.provider.Feature;
import cscie97.asn2.sharedesk.provider.OfficeSpace;
import cscie97.asn2.sharedesk.provider.OfficeSpaceServiceImpl;
import cscie97.asn2.sharedesk.provider.Provider;
import cscie97.asn2.sharedesk.provider.ProviderServiceImpl;

/**
 * Class used as caller to demonstrate Provider and OfficeSpace services in ShareDesk application
 * @author Carlos Daniel Carreon Guzman
 *
 */
public class TestDriver {

    /**
     * @param args
     */
    public static void main(String args[]) {
	
	final String authToken = "admin";
	
	ProviderServiceImpl sds = ProviderServiceImpl.getInstance();
	OfficeSpaceServiceImpl oss = OfficeSpaceServiceImpl.getInstance();
	
	try {
	    sds.createProvider(authToken, "Provider_1.yaml");
	    sds.createProvider(authToken, "Provider_2.yaml");
	    
	    Provider toDelete = new Provider();
	    Provider toUpdate = new Provider();
	    
	    Iterator<Provider> itr = sds.getProviderList(authToken).iterator();
	    System.out.println("---Providers List---\n");
	    while(itr.hasNext()) {
		Provider provider = (Provider) itr.next();
		if (provider.getName().equalsIgnoreCase("Carlos Carreon"))
		    toUpdate = provider;
		else
		    toDelete = provider;
		System.out.println("Provider name: " + provider.getName());
		System.out.println("Provider contact: " + provider.getContact());
		System.out.println("Provider picture:");
		System.out.println("- Picture name: " + provider.getPicture().getName());
		System.out.println("- Picture description: " + provider.getPicture().getDescription());
		System.out.println("- Picture description: " + provider.getPicture().getURI());
		System.out.println(provider.getName() + "'s OfficeSpace: " + oss.getOffice(authToken, provider.getOfficeSpaceIdentifier()).getName());
		System.out.println("");
	    }
	    System.out.println("---End Providers List---\n");
	    
	    Iterator<OfficeSpace> itr2 = oss.getOfficeList(authToken).iterator();
	    System.out.println("---OfficeSpace List---\n");
	    while(itr2.hasNext()) {
		OfficeSpace office = (OfficeSpace) itr2.next();
		System.out.println("OfficeSpace name: " + office.getName());
		System.out.println("OfficeSpace ID: " + office.getIdentifier());
		System.out.println("OfficeSpace capacity: ");
		System.out.println("- Number of People: " + office.getCapacity().getNumberOfPeople());
		System.out.println("- Square Feet: " + office.getCapacity().getSquareFeet());
		System.out.println("- Work Spaces: " + office.getCapacity().getWorkSpaces());
		System.out.println("OfficeSpace common access:");
		Iterator<CommonAccess> commonAccessItr = office.getCommonAccess().iterator();
		while(commonAccessItr.hasNext()) {
		    CommonAccess ca = commonAccessItr.next();
		    System.out.println("- " + ca.getName());
		}
		System.out.println("OfficeSpace features:");
		Iterator<Feature> featureItr = office.getFeature().iterator();
		while(featureItr.hasNext()) {
		    Feature feature = featureItr.next();
		    System.out.println("- " + feature.getName());
		}
		System.out.println("");
	    }
	    System.out.println("---End OfficeSpace List---\n");
	    
	    oss.deleteOffice(authToken, oss.getOffice(authToken, toDelete.getOfficeSpaceIdentifier()));
	    
	    Iterator<Provider> itr3 = sds.getProviderList(authToken).iterator();
	    System.out.println("---Providers List---\n");
	    while(itr3.hasNext()) {
		Provider provider = (Provider) itr3.next();
		System.out.println("Provider name: " + provider.getName());
		System.out.println("Provider contact: " + provider.getContact());
		System.out.println("Provider picture:");
		System.out.println("- Picture name: " + provider.getPicture().getName());
		System.out.println("- Picture description: " + provider.getPicture().getDescription());
		System.out.println("- Picture description: " + provider.getPicture().getURI());
		if (provider.getOfficeSpaceIdentifier() == null)
		    System.out.println(provider.getName() + "'s OfficeSpace: " + provider.getOfficeSpaceIdentifier());
		else
		    System.out.println(provider.getName() + "'s OfficeSpace: " + oss.getOffice(authToken, provider.getOfficeSpaceIdentifier()).getName());
		System.out.println("");
	    }
	    System.out.println("---End Providers List---\n");
	    
	    Iterator<OfficeSpace> itr5 = oss.getOfficeList(authToken).iterator();
	    System.out.println("---OfficeSpace List---\n");
	    while(itr5.hasNext()) {
		OfficeSpace office = (OfficeSpace) itr5.next();
		System.out.println("OfficeSpace name: " + office.getName());
		System.out.println("OfficeSpace ID: " + office.getIdentifier());
		System.out.println("OfficeSpace capacity: ");
		System.out.println("- Number of People: " + office.getCapacity().getNumberOfPeople());
		System.out.println("- Square Feet: " + office.getCapacity().getSquareFeet());
		System.out.println("- Work Spaces: " + office.getCapacity().getWorkSpaces());
		System.out.println("OfficeSpace common access:");
		Iterator<CommonAccess> commonAccessItr = office.getCommonAccess().iterator();
		while(commonAccessItr.hasNext()) {
		    CommonAccess ca = commonAccessItr.next();
		    System.out.println("- " + ca.getName());
		}
		System.out.println("OfficeSpace features:");
		Iterator<Feature> featureItr = office.getFeature().iterator();
		while(featureItr.hasNext()) {
		    Feature feature = featureItr.next();
		    System.out.println("- " + feature.getName());
		}
		System.out.println("");
	    }
	    System.out.println("---End OfficeSpace List---\n");
	    
	    System.out.println("---Update Process---\n");
	    sds.updateProvider(authToken, toUpdate,"Provider_2Update.yaml");
	    System.out.println("---End Update Process---\n");
	    
	    Iterator<Provider> itr4 = sds.getProviderList(authToken).iterator();
	    System.out.println("---Providers List---\n");
	    while(itr4.hasNext()) {
		Provider provider = (Provider) itr4.next();
		System.out.println("Provider name: " + provider.getName());
		System.out.println("Provider contact: " + provider.getContact());
		System.out.println("Provider picture:");
		System.out.println("- Picture name: " + provider.getPicture().getName());
		System.out.println("- Picture description: " + provider.getPicture().getDescription());
		System.out.println("- Picture description: " + provider.getPicture().getURI());
		if (provider.getOfficeSpaceIdentifier() == null)
		    System.out.println(provider.getName() + "'s OfficeSpace: " + provider.getOfficeSpaceIdentifier());
		else
		    System.out.println(provider.getName() + "'s OfficeSpace: " + oss.getOffice(authToken, provider.getOfficeSpaceIdentifier()).getName());
		System.out.println("");
	    }
	    System.out.println("---End Providers List---\n");
	    
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
