/**
 * 
 */
package com.ibm.tivoli.tuna.jaas.ldap.test;

import junit.framework.TestCase;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ibm.tivoli.tuna.service.AuthenticationResult;
import com.ibm.tivoli.tuna.service.AuthenticationService;
import com.ibm.tivoli.tuna.service.Context;
import com.ibm.tivoli.tuna.service.Credential;
import com.ibm.tivoli.tuna.service.Credentials;
import com.ibm.tivoli.tuna.service.Parameter;
import com.ibm.tivoli.tuna.service.Requester;

/**
 * @author zhaodonglu
 *
 */
public class LDAPLoginModuleTest extends TestCase {

  private BeanFactory beanFactory;

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] {"com/ibm/tivoli/tuna/spring/applicationContext.xml", "com/ibm/tivoli/tuna/spring/applicationContext-ldap.xml"});
    beanFactory = (BeanFactory) appContext;
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testLdap() throws Exception {
    AuthenticationService service = (AuthenticationService)this.beanFactory.getBean("authenticationServiceBean");

    Context context = new Context();
    context.getParameters().add(new Parameter("LoginModuleName", "LDAP-SIMPLE"));
    Credentials credentials = new Credentials();
    credentials.getCredentials().add(new Credential("username", "testUser"));
    credentials.getCredentials().add(new Credential("password", "testPassword"));
    Requester requester = new Requester();
    AuthenticationResult result = service.authentication(requester, context, credentials);
    assertEquals("success", result.getStatus().getCode());

  }
}
