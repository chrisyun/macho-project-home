package com.ibm.tivoli.tuna.service;

import java.util.Date;

public class AuthenticationResult {
  
  private Status status = null;
  
  private UserSubject issuer = null;
  
  private UserSubject subject = null;
  
  private AttributeStatement attributeStatement = null;

  private Date timestamp = new Date();
  
  public AuthenticationResult() {
    super();
  }

  /**
   * @return the status
   */
  public Status getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(Status status) {
    this.status = status;
  }

  /**
   * @return the issuer
   */
  public UserSubject getIssuer() {
    return issuer;
  }

  /**
   * @param issuer the issuer to set
   */
  public void setIssuer(UserSubject issuer) {
    this.issuer = issuer;
  }

  /**
   * @return the subject
   */
  public UserSubject getSubject() {
    return subject;
  }

  /**
   * @param subject the subject to set
   */
  public void setSubject(UserSubject subject) {
    this.subject = subject;
  }

  /**
   * @return the attributeStatement
   */
  public AttributeStatement getAttributeStatement() {
    return attributeStatement;
  }

  /**
   * @param attributeStatement the attributeStatement to set
   */
  public void setAttributeStatement(AttributeStatement attributeStatement) {
    this.attributeStatement = attributeStatement;
  }

  /**
   * @return the timestamp
   */
  public Date getTimestamp() {
    return timestamp;
  }

  /**
   * @param timestamp the timestamp to set
   */
  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

}
