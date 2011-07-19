/**
 * 
 */
package com.ibm.tivoli.pwdnotifier.service;

import javax.xml.bind.annotation.XmlRootElement;

import com.ibm.tivoli.pwdnotifier.UserPasswordStatus;

/**
 * @author zhaodonglu
 * 
 */
@XmlRootElement(name = "GetPwdStatusResp")
public class GetPwdStatusResp {
  private String code = null;
  private String message = null;
  private UserPasswordStatus userPwdStatus = null;

  /**
   * 
   */
  public GetPwdStatusResp() {
    super();
  }

  public GetPwdStatusResp(String code, String message, UserPasswordStatus userPwdStatus) {
    super();
    this.code = code;
    this.message = message;
    this.userPwdStatus = userPwdStatus;
  }

  /**
   * @return the code
   */
  public String getCode() {
    return code;
  }

  /**
   * @param code
   *          the code to set
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @param message
   *          the message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * @return the userPwdStatus
   */
  public UserPasswordStatus getUserPwdStatus() {
    return userPwdStatus;
  }

  /**
   * @param userPwdStatus
   *          the userPwdStatus to set
   */
  public void setUserPwdStatus(UserPasswordStatus userPwdStatus) {
    this.userPwdStatus = userPwdStatus;
  }

}
