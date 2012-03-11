package com.npower.dm.hibernate.entity;

import com.npower.dm.tracking.AccessLog;
import com.npower.dm.tracking.AccessLogHeader;

/**
 * AccessLogHeaderEntity entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class AccessLogHeaderEntity extends AbstractAccessLogHeader implements java.io.Serializable, AccessLogHeader {

  // Constructors

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** default constructor */
  public AccessLogHeaderEntity() {
  }

  /** full constructor */
  public AccessLogHeaderEntity(AccessLog dmAccessLog, String name, String value) {
    super(dmAccessLog, name, value);
  }

}
