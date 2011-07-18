/**
 * 
 */
package com.ibm.tivoli.pwdnotifier;

/**
 * @author zhaodonglu
 * 
 */
public interface Checker {
  
  /**
   * Check user password status
   * @param userid
   * @return
   * @throws Exception
   */
  public UserPasswordStatus check(String userid) throws Exception;
}
