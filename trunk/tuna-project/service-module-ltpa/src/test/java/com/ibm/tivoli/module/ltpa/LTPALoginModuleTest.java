/**
 * 
 */
package com.ibm.tivoli.module.ltpa;

import junit.framework.TestCase;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ibm.tivoli.tuna.module.ltpa.LTPALoginModule;
import com.ibm.tivoli.tuna.service.AuthenticationResult;
import com.ibm.tivoli.tuna.service.AuthenticationService;
import com.ibm.tivoli.tuna.service.Context;
import com.ibm.tivoli.tuna.service.Credential;
import com.ibm.tivoli.tuna.service.Credentials;
import com.ibm.tivoli.tuna.service.Parameter;
import com.ibm.tivoli.tuna.service.Requester;
import com.ibm.tivoli.tuna.service.Status;

/**
 * @author zhaodonglu
 *
 */
public class LTPALoginModuleTest extends TestCase {

  private BeanFactory beanFactory;

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
  
  public void testAuthentication() throws Exception {
    AuthenticationService service = (AuthenticationService)this.beanFactory.getBean("authenticationServiceBean");

    Context context = new Context();
    context.getParameters().add(new Parameter("LoginModuleName", "LTPA-CRED"));
    Credentials credentials = new Credentials();
    credentials.getCredentials().add(new Credential("token", "vT5Zt1Ptg228WednWr7PFXWlM/a3OiibNiqeHh1hZ50v/PjQMFGs6E8aZxx23n2PbjOYg8zpIbtW2pM0D+ZgpEcPSXwiE4RZiyYG+2otRO4xfUI38TCjhHsWOuUF3t1hnBfDQQalE7CmipOIzsWbmwuYRQxsmn4iweIB11f4ckc11xZqGWf1cTiYfgmOJwFCIG3mibI5I7wzo8+ed5DvNYPvuchhVggOjFUUEGaoqH+52r+K/RWBs6GmH9R53zWkDAAvs7CxNNOnuv6U+0UFJjxBcS88iJb9NuI2KsTCb/W8CFHrNU//Ex5g8/LoOx5zsTEBCPDzVWcEweWaQ4cWCctrLHfI4/EX"));
    Requester requester = new Requester();
    AuthenticationResult result = service.authenticate(requester, context, credentials);
    assertEquals("success", result.getStatus().getCode());
    //assertEquals("ids.hq.unicom.local\\:389", response.getCredential().getRealm());
    assertEquals("uid=000015181,cn=users,dc=hq,dc=unicom", result.getSubjects().get(0).getNameID());
    //assertEquals("Fri Mar 04 18:43:00 CST 2011", response.getCredential().getExpireTime().toString());

  }
}
