package com.ibm.tivoli.tuna.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuthenticationResult {
  
  private Status status = null;
  
  private UserSubject issuer = null;
  
  private List<UserSubject> subjects = new ArrayList<UserSubject>();
  
  private AttributeStatement attributeStatement = null;

  private Date timestamp = new Date();
  
  private String nonce = null;
  
  public AuthenticationResult() {
    super();
  }

  public AuthenticationResult(Status status, UserSubject issuer, UserSubject subject, AttributeStatement attributeStatement, Date timestamp) {
    super();
    this.status = status;
    this.issuer = issuer;
    this.subjects.add(subject);
    this.attributeStatement = attributeStatement;
    this.timestamp = timestamp;
  }


  public AuthenticationResult(Status status, UserSubject issuer, UserSubject subject, AttributeStatement attributeStatement) {
    super();
    this.status = status;
    this.issuer = issuer;
    this.subjects.add(subject);
    this.attributeStatement = attributeStatement;
  }
  
  public AuthenticationResult(Status status, UserSubject issuer, List<UserSubject> subjects, AttributeStatement attributeStatement) {
    super();
    this.status = status;
    this.issuer = issuer;
    this.subjects = subjects;
    this.attributeStatement = attributeStatement;
  }

  public AuthenticationResult(Status status, UserSubject issuer) {
    super();
    this.status = status;
    this.issuer = issuer;
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
   * @return the subjects
   */
  public List<UserSubject> getSubjects() {
    return subjects;
  }

  /**
   * @param subjects the subjects to set
   */
  public void setSubjects(List<UserSubject> subjects) {
    this.subjects = subjects;
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

  /**
   * @return the nonce
   */
  public String getNonce() {
    return nonce;
  }

  /**
   * @param nonce the nonce to set
   */
  public void setNonce(String nonce) {
    this.nonce = nonce;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    final int maxLen = 100;
    return String.format("AuthenticationResult [status=%s, issuer=%s, subjects=%s, attributeStatement=%s, timestamp=%s, nonce=%s]", status, issuer,
        subjects != null ? subjects.subList(0, Math.min(subjects.size(), maxLen)) : null, attributeStatement, timestamp, nonce);
  }

}
