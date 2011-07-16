/**
 * 
 */
package com.ibm.tivoli.icbc.ws;

/**
 * @author Zhao Dong Lu
 *
 */
public class Response {
  private int code = 0;
  private String message = null;
  private String taskConfiguration = null;

  /**
   * 
   */
  public Response() {
    super();
  }

  public Response(int code, String message, String taskConfiguration) {
    super();
    this.code = code;
    this.message = message;
    this.taskConfiguration = taskConfiguration;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getTaskConfiguration() {
    return taskConfiguration;
  }

  public void setTaskConfiguration(String taskConfiguration) {
    this.taskConfiguration = taskConfiguration;
  }

}
