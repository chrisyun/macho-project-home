/**
 * 
 */
package com.ibm.tivoli.tuna.service;

import junit.framework.TestCase;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhaodonglu
 *
 */
public class AuthenticationServiceTest extends TestCase {

  private BeanFactory beanFactory;

  /**
   * @param name
   */
  public AuthenticationServiceTest(String name) {
    super(name);
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] {"com/ibm/tivoli/tuna/spring/applicationContext.xml"});
    beanFactory = (BeanFactory) appContext;
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testSample() throws Exception {
    AuthenticationService service = (AuthenticationService)this.beanFactory.getBean("authenticationServiceBean");

    Context context = new Context();
    context.getParameters().add(new Parameter("LoginModuleName", "SAMPLE"));
    Credentials credentials = new Credentials();
    credentials.getCredentials().add(new Credential("username", "testUser"));
    credentials.getCredentials().add(new Credential("password", "testPassword"));
    Requester requester = new Requester();
    AuthenticationResult result = service.authentication(requester, context, credentials);
    assertEquals("success", result.getStatus().getCode());

  }

}
