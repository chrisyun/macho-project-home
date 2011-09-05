/**
 * 
 */
package com.ibm.tivoli.tuna.module.ltpa;

import javax.security.auth.callback.Callback;

/**
 * @author zhaodonglu
 *
 */
public class LtpaTokenCallback implements Callback {

  private String token = null;
  
  /**
   * 
   */
  public LtpaTokenCallback() {
    super();
  }

  /**
   * @return the token
   */
  public String getToken() {
    return token;
  }

  /**
   * @param token the token to set
   */
  public void setToken(String token) {
    this.token = token;
  }

}
