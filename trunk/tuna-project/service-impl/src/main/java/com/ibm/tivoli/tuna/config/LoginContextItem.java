/**
 * 
 */
package com.ibm.tivoli.tuna.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Config item which represent a login context definition.
 * 
 * @author zhaodonglu
 * 
 */
public class LoginContextItem {

  /**
   * The name of this login context, which will be used in JAAS LoginContext.
   */
  private String name = null;
  private String callbackHandlerClass = null;
  private String resultHandlerClass = null;
  private List<LoginModuleItem> loginModules = new ArrayList<LoginModuleItem>();

  /**
   * 
   */
  public LoginContextItem() {
    super();
  }

  public LoginContextItem(String name) {
    super();
    this.name = name;
  }

  public LoginContextItem(String name, LoginModuleItem loginModuleItem) {
    super();
    this.name = name;
    this.loginModules.add(loginModuleItem);

  }

  public LoginContextItem(String name, List<LoginModuleItem> loginModules) {
    super();
    this.name = name;
    this.loginModules = loginModules;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the callbackHandlerClass
   */
  public String getCallbackHandlerClass() {
    return callbackHandlerClass;
  }

  /**
   * @param callbackHandlerClass
   *          the callbackHandlerClass to set
   */
  public void setCallbackHandlerClass(String callbackHandlerClass) {
    this.callbackHandlerClass = callbackHandlerClass;
  }

  /**
   * @return the resultHandlerClass
   */
  public String getResultHandlerClass() {
    return resultHandlerClass;
  }

  /**
   * @param resultHandlerClass
   *          the resultHandlerClass to set
   */
  public void setResultHandlerClass(String resultHandlerClass) {
    this.resultHandlerClass = resultHandlerClass;
  }

  /**
   * @return the loginModules
   */
  public List<LoginModuleItem> getLoginModules() {
    return loginModules;
  }

  /**
   * @param loginModules
   *          the loginModules to set
   */
  public void setLoginModules(List<LoginModuleItem> loginModules) {
    this.loginModules = loginModules;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    final int maxLen = 100;
    return String.format("LoginContextItem [name=%s, callbackHandlerClass=%s, resultHandlerClass=%s, loginModules=%s]", name, callbackHandlerClass,
        resultHandlerClass, loginModules != null ? loginModules.subList(0, Math.min(loginModules.size(), maxLen)) : null);
  }

}
