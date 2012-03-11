package com.npower.dm.hibernate.entity;

import com.npower.dm.tracking.AccessLog;
import com.npower.dm.tracking.AccessLogHeader;

/**
 * AbstractAccessLogHeader entity provides the base persistence definition of
 * the AccessLogHeaderEntity entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractAccessLogHeader implements java.io.Serializable, AccessLogHeader {

  // Fields

  private long        id;

  private AccessLog accessLog;

  private String      name;

  private String      value;

  // Constructors

  /** default constructor */
  public AbstractAccessLogHeader() {
  }

  /** full constructor */
  public AbstractAccessLogHeader(AccessLog dmAccessLog, String name, String value) {
    this.accessLog = dmAccessLog;
    this.name = name;
    this.value = value;
  }

  // Property accessors

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public AccessLog getAccessLog() {
    return this.accessLog;
  }

  public void setAccessLog(AccessLog dmAccessLog) {
    this.accessLog = dmAccessLog;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}