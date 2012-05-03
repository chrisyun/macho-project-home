/**
 * 
 */
package com.sinopec.siam.im.security;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author zhaodonglu
 * 
 */
public class AbstractAuthenticationManagerTest extends TestCase {

  private ApplicationContext context = null;

  /*
   * (non-Javadoc)
   * 
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    //context = new ClassPathXmlApplicationContext("/com/sinopec/siam/im/security/applicationContext-security.xml");
  }

  /*
   * (non-Javadoc)
   * 
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testCase1() throws Exception {
    SimpleAdminUserDetailsService userDetailsService = new SimpleAdminUserDetailsService();
    SimpleAuthenticationProvider am = new SimpleAuthenticationProvider(userDetailsService);
    
    Authentication request = new UsernamePasswordAuthenticationToken("user", "user");
    Authentication result = am.authenticate(request);
    SecurityContextHolder.getContext().setAuthentication(result);
    
  }

}
