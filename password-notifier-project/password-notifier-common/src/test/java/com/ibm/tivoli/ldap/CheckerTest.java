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
import com.ibm.tivoli.pwdnotifier.Checker;
import com.ibm.tivoli.pwdnotifier.UserPasswordStatus;

/**
 * @author ZhaoDongLu
 * 
 */
public class CheckerTest extends TestCase {

  private Checker checker;
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
    checker = (Checker) context.getBean("checker");
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

  public void testGetStatus() throws Exception {
    UserPasswordStatus status = checker.getPasswordStatus("bgoldmann");
    assertNotNull(status);
    assertEquals("BGoldmann", status.getUserid());
    assertEquals("bgoldmann", status.getCn());
    assertEquals("BGoldmann@iamdemo.tivoli.com", status.getEmail());
    assertEquals(86400, status.getPasswordMaxAgeInSeconds());
    assertEquals("Tue Sep 14 07:44:10 CST 2010", status.getLastPasswordChangedTime().toString());
  }
  
}
