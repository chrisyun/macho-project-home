package com.ibm.tivoli.ldap;

import java.util.List;

import javax.naming.directory.SearchControls;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.odm.core.OdmManager;

public class OdmTest extends TestCase {

	public void testCase() throws Exception {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("com/ibm/tivoli/ldap/test.config.xml");		
		OdmManager odmManager = (OdmManager)context.getBean("odmManager", OdmManager.class);
		
//		create(odmManager);
    read(odmManager);
		list(odmManager);
	}
	
	private static void create(OdmManager odmManager){

		ApplicationEntity addressEntity = new ApplicationEntity();
		
		DistinguishedName distinguishedName = new DistinguishedName("ou=services,ou=configuration,ou=system");
		distinguishedName.add("cn", "Address");		
		addressEntity.setDistinguisedName(distinguishedName);
		addressEntity.setCn("Address");
		addressEntity.setDescription("Contains information about the Address entity");
		addressEntity.setPresentationAddress("Address");
		
		addressEntity.getObjectClassNames().add("top");
		addressEntity.getObjectClassNames().add("applicationEntity");
		
		odmManager.create(addressEntity);				
	}
	
	private static void read(OdmManager odmManager){
		
		String baseDn = "ou=employees,ou=whitepages,dc=jke,dc=com";
		DistinguishedName distinguisedName = new DistinguishedName(baseDn);
		distinguisedName.add("cn", "bgoldmann");

		ApplicationEntity applicationEntity = odmManager.read(ApplicationEntity.class, distinguisedName);
		System.out.println(applicationEntity);
	}

	private static void list(OdmManager odmManager){
		
		String baseDn = "ou=services,ou=configuration,ou=system";
		DistinguishedName distinguisedName = new DistinguishedName(baseDn);

		SearchControls searchControls = new SearchControls();
		
		List<ApplicationEntity> applicationEntityList = odmManager.findAll(
			ApplicationEntity.class, distinguisedName, searchControls);
		for (ApplicationEntity applicationEntity : applicationEntityList){
			System.out.println(applicationEntity);
		}
	}

}

