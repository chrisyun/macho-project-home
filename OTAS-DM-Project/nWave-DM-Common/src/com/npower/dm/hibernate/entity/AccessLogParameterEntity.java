package com.npower.dm.hibernate.entity;

import com.npower.dm.tracking.AccessLog;
import com.npower.dm.tracking.AccessLogParameter;

/**
 * AccessLogParameterEntity entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class AccessLogParameterEntity extends AbstractAccessLogParameter implements java.io.Serializable, AccessLogParameter {

  // Constructors

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** default constructor */
  public AccessLogParameterEntity() {
  }

  /** full constructor */
  public AccessLogParameterEntity(AccessLog dmAccessLog, String name, String value) {
    super(dmAccessLog, name, value);
  }

}
