/**
 * 
 */
package com.ibm.tivoli.tuna.service;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zhaodonglu
 *
 */
public class AuthenticationServiceImpl implements AuthenticationService {
  
  private static Log log = LogFactory.getLog(AuthenticationServiceImpl.class);
  
  private LoginContextManager loginContextManager = null; 

  /**
   * 
   */
  public AuthenticationServiceImpl() {
    super();
  }

  /**
   * @return the loginContextManager
   */
  public LoginContextManager getLoginContextManager() {
    return loginContextManager;
  }

  /**
   * @param loginContextManager the loginContextManager to set
   */
  public void setLoginContextManager(LoginContextManager loginContextManager) {
    this.loginContextManager = loginContextManager;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.tuna.service.AuthenticationService#authentication(com.ibm.tivoli.tuna.service.Requester, com.ibm.tivoli.tuna.service.Context, com.ibm.tivoli.tuna.service.Credentials)
   */
  public AuthenticationResult authentication(Requester requester, Context context, Credentials credentials) {
    UserSubject issuer = new UserSubject("url", "http://idp.tivoli.ibm.com");

    LoginContext lc = null;
    try {
      lc = this.loginContextManager.getLoginContext(requester, context, credentials);
    } catch (TunaException e) {
      log.error("Cannot create LoginContext. " + e.getMessage(), e);
      AuthenticationResult result = new AuthenticationResult(new Status("failure", e.getMessage()), issuer);
      return result;
    } catch (SecurityException e) {
      log.error("Cannot create LoginContext. " + e.getMessage(), e);
      AuthenticationResult result = new AuthenticationResult(new Status("failure", e.getMessage()), issuer);
      return result;
    } catch (Throwable e) {
      log.error("Cannot create LoginContext. " + e.getMessage(), e);
      AuthenticationResult result = new AuthenticationResult(new Status("failure", e.getMessage()), issuer);
      return result;
    }

    try {

      // attempt authentication
      lc.login();

      // if we return with no exception, authentication succeeded
      AuthenticationResult result = new AuthenticationResult(new Status("success", "success"), issuer);
      
      AuthenticationResultHandler resultHandler = this.loginContextManager.getAuthenticationHandler(requester, context, credentials);
      // Retrieve user info and fill into result.
      resultHandler.fullfill(lc, result);
      return result;
    } catch (LoginException le) {
      log.warn("Authentication failure!", le);
      AuthenticationResult result = new AuthenticationResult(new Status("failure", le.getMessage()), issuer);
      return result;
    } catch (TunaException le) {
      log.warn("Authentication failure!", le);
      AuthenticationResult result = new AuthenticationResult(new Status("failure", le.getMessage()), issuer);
      return result;
    }
  }

}
