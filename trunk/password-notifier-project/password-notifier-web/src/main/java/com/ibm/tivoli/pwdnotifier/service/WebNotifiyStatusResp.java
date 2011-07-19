package com.ibm.tivoli.pwdnotifier.service;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "WebNotifiyStatusResp")
public class WebNotifiyStatusResp {
  private String code = null;
  private String message = null;
  private boolean needToNotify = false;

  public WebNotifiyStatusResp() {
    super();
  }

  public WebNotifiyStatusResp(String code, String message, boolean needToNotify) {
    super();
    this.code = code;
    this.message = message;
    this.needToNotify = needToNotify;
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
   * @return the needToNotify
   */
  public boolean isNeedToNotify() {
    return needToNotify;
  }

  /**
   * @param needToNotify
   *          the needToNotify to set
   */
  public void setNeedToNotify(boolean needToNotify) {
    this.needToNotify = needToNotify;
  }

}
