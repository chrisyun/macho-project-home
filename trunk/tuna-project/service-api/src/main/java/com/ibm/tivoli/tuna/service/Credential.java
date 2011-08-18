package com.ibm.tivoli.tuna.service;

public class Credential {
  /**
   * 
   * username, password, x509cert, rsa_securid, ...
   */
  private String type = null;
  /**
   * byte[], string, date, bool, int, float
   */
  private String format = null;
  /**
   * base64, clear, x509perm, x509der
   */
  private String encode = null;
  
  private String valueAsString = null;

  public Credential() {
    super();
  }

  /**
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * @return the format
   */
  public String getFormat() {
    return format;
  }

  /**
   * @param format the format to set
   */
  public void setFormat(String dataType) {
    this.format = dataType;
  }

  /**
   * @return the encode
   */
  public String getEncode() {
    return encode;
  }

  /**
   * @param encode the encode to set
   */
  public void setEncode(String encode) {
    this.encode = encode;
  }

  /**
   * @return the valueAsString
   */
  public String getValueAsString() {
    return valueAsString;
  }

  /**
   * @param valueAsString the valueAsString to set
   */
  public void setValueAsString(String valueAsString) {
    this.valueAsString = valueAsString;
  }

  
}
