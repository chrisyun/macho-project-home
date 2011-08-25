/**
 * 
 */
package com.ibm.tivoli.tuna.service;

import java.security.Principal;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.tuna.jaas.SimpleCallbackHandler;

/**
 * @author zhaodonglu
 *
 */
public class AuthenticationServiceImpl implements AuthenticationService {
  
  private static Log log = LogFactory.getLog(AuthenticationServiceImpl.class);

  /**
   * 
   */
  public AuthenticationServiceImpl() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.tuna.service.AuthenticationService#authentication(com.ibm.tivoli.tuna.service.Requester, com.ibm.tivoli.tuna.service.Context, com.ibm.tivoli.tuna.service.Credentials)
   */
  public AuthenticationResult authentication(Requester requester, Context context, Credentials credentials) {
    UserSubject issuer = new UserSubject("url", "http://idp.tivoli.ibm.com");

    System.setProperty("java.security.auth.login.config", "c:/users/ibm_admin/workspace/tuna-project/service-impl/src/test/resources/sample_jaas.config");


    LoginContext lc = null;
    try {
      // LoginContext Name
      String nameOfLoginContext = context.getParameter("LoginModuleName").getValues().get(0);
      lc = new LoginContext(nameOfLoginContext, new SimpleCallbackHandler(requester, context, credentials));
    } catch (LoginException e) {
      log.error("Cannot create LoginContext. " + e.getMessage());
      AuthenticationResult result = new AuthenticationResult(new Status("failure", e.getMessage()), issuer);
      return result;
    } catch (SecurityException e) {
      log.error("Cannot create LoginContext. " + e.getMessage());
      AuthenticationResult result = new AuthenticationResult(new Status("failure", e.getMessage()), issuer);
      return result;
    } catch (Throwable e) {
      log.error("Cannot create LoginContext. " + e.getMessage());
      AuthenticationResult result = new AuthenticationResult(new Status("failure", e.getMessage()), issuer);
      return result;
    }

    try {

      // attempt authentication
      lc.login();

      // if we return with no exception,
      // authentication succeeded
      AuthenticationResult result = new AuthenticationResult(new Status("success", "success"), issuer);
      Subject lcSubject = lc.getSubject();
      for (Principal principal: lcSubject.getPrincipals()) {
          String pname = principal.getName();
          UserSubject userSubject = new UserSubject(principal.getClass().getCanonicalName(), pname);
          result.getSubjects().add(userSubject);
      }
      AttributeStatement attributeStatment = new AttributeStatement();
      attributeStatment.getAttributes().add(new Attribute("uid", "string", "testUser"));
      attributeStatment.getAttributes().add(new Attribute("email", "string", new String[] { "test@sinopec.com.cn", "testuser@gmail.com" }));
      attributeStatment.getAttributes().add(new Attribute("cn", "string", "ÕÅÈý"));
      result.setAttributeStatement(attributeStatment);
      return result;
    } catch (LoginException le) {
      log.warn("Authentication failure!", le);
      AuthenticationResult result = new AuthenticationResult(new Status("failure", le.getMessage()), issuer);
      return result;
    }
  }

}
