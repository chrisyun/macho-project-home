/**
 * 
 */
package com.ibm.tivoli.tuna.config;


/**
 * @author zhaodonglu
 *
 */
public interface LoginContextItemFinder {
  /**
   * @return
   */
  public LoginContextItem findLoginContextItemByName(String name);
}
