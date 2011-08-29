/**
 * 
 */
package com.ibm.tivoli.tuna.service;

import junit.framework.TestCase;

import com.ibm.tivoli.tuna.config.FileLoginContextConfigurationManager;

/**
 * @author zhaodonglu
 *
 */
public class AuthenticationServiceTest extends TestCase {

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
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testAuthentication() throws Exception {
    AuthenticationServiceImpl service = new AuthenticationServiceImpl();
    
    LoginContextManager loginContextManager = new FileLoginContextConfigurationManager("c:/users/ibm_admin/workspace/tuna-project/service-impl/src/test/resources/sample_jaas.config");
    service.setLoginContextConfigManager(loginContextManager );
    
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
