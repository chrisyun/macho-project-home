/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/SoftwareTrackingBeanImpl.java,v 1.6 2008/08/18 05:51:35 zhao Exp $
 * $Revision: 1.6 $
 * $Date: 2008/08/18 05:51:35 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
 *
 * This SOURCE CODE FILE, which has been provided by NPower as part
 * of a NPower product for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information of NPower.
 *
 * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
 * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
 * THE PRODUCT.
 *
 * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
 * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
 * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
 * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
 * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
 * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
 * CODE FILE.
 * ===============================================================================================
 */
package com.npower.dm.hibernate.management;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwarePackage;
import com.npower.dm.hibernate.entity.SoftwareTrackingLog;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareBean;
import com.npower.dm.management.SoftwareTrackingBean;
import com.npower.dm.management.SoftwareTrackingEvent;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2008/08/18 05:51:35 $
 */
public class SoftwareTrackingBeanImpl extends AbstractBean implements SoftwareTrackingBean {

  private static Log log = LogFactory.getLog(SoftwareTrackingBeanImpl.class);
  
  /**
   * 
   */
  public SoftwareTrackingBeanImpl() {
    super();
  }

  /**
   * @param factory
   * @param hsession
   */
  public SoftwareTrackingBeanImpl(ManagementBeanFactory factory, Session hsession) {
    super(factory, hsession);
  }

  // Private methods --------------------------------------------------------------------------
  // Implements interface ---------------------------------------------------------------------
  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareTrackingBean#track(com.npower.dm.core.SoftwarePackage, com.npower.dm.management.SoftwareTrackingType)
   */
  public void track(SoftwarePackage pkg, SoftwareTrackingEvent event) {
    Session session = this.getHibernateSession();
    SoftwareTrackingLog record = new SoftwareTrackingLog();
    record.setClientIp(event.getClientIP());
    record.setPhoneNumber(event.getPhoneNumber());
    record.setSoftwarePackage(pkg);
    record.setSoftware(pkg.getSoftware());
    record.setTrackingType(event.getType().toString());
    Date timeStamp = event.getTimeStamp();
    if (timeStamp == null) {
       timeStamp = new Date();
    }
    record.setTimeStamp(timeStamp);
    session.saveOrUpdate(record);
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareTrackingBean#trackPackage(long, com.npower.dm.management.SoftwareTrackingEvent)
   */
  public void trackPackage(long pkgID, SoftwareTrackingEvent event) {
    SoftwareBean bean = this.getManagementBeanFactory().createSoftwareBean();
    try {
        SoftwarePackage pkg = bean.getPackageByID(pkgID);
        if (pkg == null) {
           // Not found
           return;
        }
        this.track(pkg, event);
    } catch (DMException ex) {
      log.error("Could not update tracking status for pkg: " + pkgID + ", event: " + event.toString(), ex);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareTrackingBean#track(com.npower.dm.core.Software, com.npower.dm.management.SoftwareTrackingEvent)
   */
  public void track(Software software, SoftwareTrackingEvent event) {
    Session session = this.getHibernateSession();
    SoftwareTrackingLog record = new SoftwareTrackingLog();
    record.setClientIp(event.getClientIP());
    record.setPhoneNumber(event.getPhoneNumber());
    record.setSoftware(software);
    record.setTrackingType(event.getType().toString());
    Date timeStamp = event.getTimeStamp();
    if (timeStamp == null) {
       timeStamp = new Date();
    }
    record.setTimeStamp(timeStamp);
    session.saveOrUpdate(record);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareTrackingBean#trackSoftware(long, com.npower.dm.management.SoftwareTrackingEvent)
   */
  public void trackSoftware(long softwareID, SoftwareTrackingEvent event) {
    SoftwareBean bean = this.getManagementBeanFactory().createSoftwareBean();
    try {
        Software software = bean.getSoftwareByID(softwareID);
        if (software == null) {
           // Not found
           return;
        }
        this.track(software, event);
    } catch (DMException ex) {
      log.error("Could not update tracking status for software: " + softwareID + ", event: " + event.toString(), ex);
    }
  }
}
