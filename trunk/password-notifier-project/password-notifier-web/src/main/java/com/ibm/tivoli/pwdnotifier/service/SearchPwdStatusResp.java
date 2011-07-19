package com.ibm.tivoli.pwdnotifier.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.ibm.tivoli.pwdnotifier.UserPasswordStatus;

@XmlRootElement(name = "SearchPwdStatusResp")
public class SearchPwdStatusResp {
  private String code = null;
  private String message = null;
  private List<UserPasswordStatus> userPwdStatus = new ArrayList<UserPasswordStatus>();

  /**
   * 
   */
  public SearchPwdStatusResp() {
    super();
  }

  public SearchPwdStatusResp(String code, String message, List<UserPasswordStatus> userPwdStatus) {
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
  public List<UserPasswordStatus> getUserPwdStatus() {
    return userPwdStatus;
  }

  /**
   * @param userPwdStatus
   *          the userPwdStatus to set
   */
  public void setUserPwdStatus(List<UserPasswordStatus> userPwdStatus) {
    this.userPwdStatus = userPwdStatus;
  }

}
