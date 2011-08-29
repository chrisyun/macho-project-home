/**
 * 
 */
package com.ibm.tivoli.tuna.service;

import javax.security.auth.login.LoginContext;

/**
 * @author zhaodonglu
 *
 */
public interface AuthenticationResultHandler {

  void fullfill(LoginContext lc, AuthenticationResult result);

}
