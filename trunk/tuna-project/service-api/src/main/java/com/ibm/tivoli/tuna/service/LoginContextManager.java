/**
 * 
 */
package com.ibm.tivoli.tuna.service;

import javax.security.auth.login.LoginContext;

/**
 * LoginContext Configuration Manager
 * @author zhaodonglu
 *
 */
public interface LoginContextManager {
  
  /**
   * Return a JAAS LoginContext
   * @param loginModuleName
   * @param requester
   * @param context
   * @param credentials
   * @return
   */
  public LoginContext getLoginContext(Requester requester, Context context, Credentials credentials) throws TunaException;

  /**
   * @param result
   * @return
   */
  public AuthenticationResultHandler getAuthenticationHandler(AuthenticationResult result) throws TunaException;

}
