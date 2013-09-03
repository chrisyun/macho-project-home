/**
 * 
 */
package com.ibm.tivoli.w7.util;


/**
 * @author zhaodonglu
 *
 */
public class W7Who {
  private String logonname = null;
  private String realname = null;

  /**
   * 
   */
  public W7Who() {
    super();
  }
  /**
   * @param logonname
   * @param realname
   */
  public W7Who(String logonname, String realname) {
    super();
    this.logonname = logonname;
    this.realname = realname;
  }
  /**
   * @return the logonname
   */
  public String getLogonname() {
    return logonname;
  }
  /**
   * @param logonname the logonname to set
   */
  public void setLogonname(String logonname) {
    this.logonname = logonname;
  }
  /**
   * @return the realname
   */
  public String getRealname() {
    return realname;
  }
  /**
   * @param realname the realname to set
   */
  public void setRealname(String realname) {
    this.realname = realname;
  }
  
}
