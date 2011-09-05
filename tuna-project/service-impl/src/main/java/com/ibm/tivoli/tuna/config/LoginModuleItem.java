/**
 * 
 */
package com.ibm.tivoli.tuna.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represent a Login module item in login context config.
 * 
 * @author zhaodonglu
 * 
 */
public class LoginModuleItem {

  private String loginModuleClass = null;
  private String moduleFlag = null;
  private List<ModuleOption> moduleOptions = new ArrayList<ModuleOption>();

  /**
   * 
   */
  public LoginModuleItem() {
    super();
  }

  public LoginModuleItem(String loginModuleClass, String callbackHandlerClass, String moduleFlag) {
    super();
    this.loginModuleClass = loginModuleClass;
    this.moduleFlag = moduleFlag;
  }

  public LoginModuleItem(String loginModuleClass, String callbackHandlerClass, String moduleFlag, List<ModuleOption> moduleOptions) {
    super();
    this.loginModuleClass = loginModuleClass;
    this.moduleFlag = moduleFlag;
    this.moduleOptions = moduleOptions;
  }

  public LoginModuleItem(String loginModuleClass, String callbackHandlerClass, String moduleFlag, ModuleOption[] moduleOptions) {
    super();
    this.loginModuleClass = loginModuleClass;
    this.moduleFlag = moduleFlag;
    this.moduleOptions.addAll(Arrays.asList(moduleOptions));
  }

  /**
   * @return the loginModuleClass
   */
  public String getLoginModuleClass() {
    return loginModuleClass;
  }

  /**
   * @param loginModuleClass
   *          the loginModuleClass to set
   */
  public void setLoginModuleClass(String loginModuleClass) {
    this.loginModuleClass = loginModuleClass;
  }

  /**
   * @return the moduleFlag
   */
  public String getModuleFlag() {
    return moduleFlag;
  }

  /**
   * @param moduleFlag
   *          the moduleFlag to set
   */
  public void setModuleFlag(String moduleFlag) {
    this.moduleFlag = moduleFlag;
  }

  /**
   * @return the moduleOptions
   */
  public List<ModuleOption> getModuleOptions() {
    return moduleOptions;
  }

  /**
   * @param moduleOptions
   *          the moduleOptions to set
   */
  public void setModuleOptions(List<ModuleOption> moduleOptions) {
    this.moduleOptions = moduleOptions;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    final int maxLen = 100;
    return String.format("LoginModuleItem [loginModuleClass=%s, moduleFlag=%s, moduleOptions=%s]", loginModuleClass, moduleFlag,
        moduleOptions != null ? moduleOptions.subList(0, Math.min(moduleOptions.size(), maxLen)) : null);
  }


}
