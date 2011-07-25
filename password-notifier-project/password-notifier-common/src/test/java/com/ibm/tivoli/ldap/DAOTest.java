/**
 * 
 */
package com.ibm.tivoli.ldap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapRdn;
import org.springframework.util.StringUtils;

import com.ibm.tivoli.ldap.dao.DAOFactory;
import com.ibm.tivoli.ldap.dao.InetOrgPersonDAO;
import com.ibm.tivoli.ldap.dao.TamSecUserDAO;
import com.ibm.tivoli.ldap.entity.InetOrgPerson;
import com.ibm.tivoli.ldap.entity.TamSecUser;

/**
 * @author ZhaoDongLu
 * 
 */
public class DAOTest extends TestCase {

  private DAOFactory factory;

  /*
   * (non-Javadoc)
   * 
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    // Get Spring Bean Factory
    ApplicationContext context = new ClassPathXmlApplicationContext("com/ibm/tivoli/ldap/spring/applicationContext.xml");
    // Get InetOrgPersonDAOfactory from spring container
    factory = (DAOFactory) context.getBean("daoFactory");

  }

  /*
   * (non-Javadoc)
   * 
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testFindInetPerson() throws Exception {
    AdminContext adminContext = new AdminContext("admin", "password".toCharArray());
    InetOrgPersonDAO InetOrgPersonDAO = factory.getInetOrgPersonDAO(adminContext);

    List<InetOrgPerson> persons = InetOrgPersonDAO.findByFilter("ou=employees, ou=whitepages", "(uid=bgoldmann)");
    assertTrue(persons.size() == 1);
    assertEquals("bgoldmann", persons.get(0).getCn());
    assertEquals("BGoldmann", persons.get(0).getUid());
    assertEquals("BGoldmann@iamdemo.tivoli.com", persons.get(0).getEmail());
  }
  
  public void testTamSecUser() throws Exception {
    AdminContext adminContext = new AdminContext("admin", "password".toCharArray());
    TamSecUserDAO dao = factory.getTamSecUserDAO(adminContext);

    List<TamSecUser> users = dao.findByFilter("", "(principalName=bgoldmann)");
    assertTrue(users.size() == 1);
    assertEquals("Tue Sep 14 07:44:10 CST 2010", users.get(0).getSecPwdLastChanaged().toString());
    assertEquals(0, users.get(0).getPasswordMaxAge());
  }
  
  public void testName() throws Exception {
    DistinguishedName dn1 = new DistinguishedName("dc=ibm,dc=com");
    dn1.add("ou=users");
    dn1.add("cn=abc");
    
    DistinguishedName dn2 = new DistinguishedName("dc=ibm,dc=com");
    dn2.add("ou=users");
    
    DistinguishedName dn3 = new DistinguishedName("dc=ibm,dc=com");
    dn3.add("ou=users");
    dn3.add("cn=abc");
    
    assertEquals(dn1.hashCode(), dn3.hashCode());

    assertEquals("", dn1.getPrefix(dn1.size() - 1));
    DistinguishedName reverseDN = reverse(dn2);
    assertEquals("dc=com,dc=ibm,ou=users", reverseDN.toString());
  }

  /**
   * @param dn2
   * @return
   */
  private DistinguishedName reverse(DistinguishedName dn) {
    List<LdapRdn> names = dn.getNames();
    DistinguishedName reverseDN = new DistinguishedName();
    for (LdapRdn rdn: names) {
        reverseDN.add(0, rdn);
    }
    return reverseDN;
  }
  
  public void testLDAPDateStringConvert() throws Exception {
    String s = "20100913234410.0Z";
    s = StringUtils.replace(s, ".0Z", "+0000");
    DateFormat f = new SimpleDateFormat("yyyyMMddHHmmssZZZ");
    Date d = f.parse(s);
    assertEquals("20100914074410+0800", (new SimpleDateFormat("yyyyMMddHHmmssZZZ")).format(d));
  }
  
  public void testDate() throws Exception {
    Date now = new Date();
  }

}
