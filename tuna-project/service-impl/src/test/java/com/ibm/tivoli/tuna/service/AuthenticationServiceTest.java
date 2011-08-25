/**
 * 
 */
package com.ibm.tivoli.tuna.service;

import junit.framework.TestCase;

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
    AuthenticationService service = new AuthenticationServiceImpl();
    Context context = new Context();
    Credentials credentials = new Credentials();
    credentials.getCredentials().add(new Credential("username", "testUser"));
    credentials.getCredentials().add(new Credential("password", "testPassword"));
    Requester requester = new Requester();
    AuthenticationResult result = service.authentication(requester, context, credentials);
    assertEquals("success", result.getStatus().getCode());
  }

}
