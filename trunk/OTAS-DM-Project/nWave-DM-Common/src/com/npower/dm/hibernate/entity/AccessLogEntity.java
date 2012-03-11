package com.npower.dm.hibernate.entity;

import com.npower.dm.tracking.AccessLog;

/**
 * AccessLogEntity entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class AccessLogEntity extends AbstractAccessLog implements java.io.Serializable, AccessLog {

  // Constructors

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** default constructor */
  public AccessLogEntity() {
  }

  /** minimal constructor */
  public AccessLogEntity(String url, String clientIp) {
    super(url, clientIp);
  }

  /** full constructor */
  public AccessLogEntity(String url, String query, String clientIp, String userAgent,
      String sessionId) {
    super(url, query, clientIp, userAgent, sessionId);
  }

}
