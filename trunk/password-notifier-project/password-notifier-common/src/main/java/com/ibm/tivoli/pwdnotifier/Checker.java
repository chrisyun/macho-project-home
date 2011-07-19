/**
 * 
 */
package com.ibm.tivoli.pwdnotifier;

import java.util.List;

/**
 * @author zhaodonglu
 * 
 */
public interface Checker {
  
  /**
   * Get user password status
   * @param userid
   * @return
   * @throws Exception
   */
  public UserPasswordStatus getPasswordStatus(String userid) throws Exception;
  
  /**
   * Return status for detection whether send password expired information
   * 
   * @param userid
   * @param daysOfNotification
   * @return  true - Reach time window
   *          false - Not reach time window
   * @throws Exception
   */
  public boolean checkWebNotifiyStatus(String userid) throws Exception;
  
  public List<UserPasswordStatus> searchByFilter(String baseDN, String filter) throws Exception;
  
}
