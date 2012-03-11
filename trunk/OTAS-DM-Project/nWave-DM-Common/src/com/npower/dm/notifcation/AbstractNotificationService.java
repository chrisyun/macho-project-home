/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/notifcation/AbstractNotificationService.java,v 1.3 2007/04/03 01:58:26 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2007/04/03 01:58:26 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.notifcation;

import com.npower.dm.core.DMException;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.sms.SmsException;
import com.npower.sms.client.SmsSender;
import com.npower.wap.omacp.notification.Initiator;
import com.npower.wap.omacp.notification.UIMode;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2007/04/03 01:58:26 $
 */
public abstract class AbstractNotificationService implements NotificationService {

  /**
   * Property beanFactory
   */
  private ManagementBeanFactory beanFactory = null;
  
  /**
   * Property SmsSender
   */
  private SmsSender smsSender = null;
  
  /**
   * Default constructor
   */
  protected AbstractNotificationService() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.notifcation.NotificationService#getBeanFactory()
   */
  public ManagementBeanFactory getBeanFactory() {
    return this.beanFactory;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.notifcation.NotificationService#getSmsSender()
   */
  public SmsSender getSmsSender() {
    return this.smsSender;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.notifcation.NotificationService#setBeanFactory(com.npower.dm.management.ManagementBeanFactory)
   */
  public void setBeanFactory(ManagementBeanFactory beanFactory) {
    this.beanFactory = beanFactory;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.notifcation.NotificationService#setSmsSender(com.npower.sms.client.SmsSender)
   */
  public void setSmsSender(SmsSender smsSender) {
    this.smsSender = smsSender;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.notifcation.NotificationService#notificate(java.lang.String, java.lang.String, int, com.npower.wap.omacp.notification.UIMode, com.npower.wap.omacp.notification.Initiator)
   */
  public abstract void notificate(String deviceExternalID, 
                                  String dmServerID, 
                                  int sessionID, 
                                  UIMode uiMode, 
                                  Initiator initiator)
      throws DMException, SmsException;

  /* (non-Javadoc)
   * @see com.npower.dm.notifcation.NotificationService#notificate(java.lang.String, java.lang.String, java.lang.String, int, com.npower.wap.omacp.notification.UIMode, com.npower.wap.omacp.notification.Initiator)
   */
  public abstract void notificate(String msisdn, 
                                  String dmServerID, 
                                  String serverPassword, 
                                  int sessionID, 
                                  UIMode uiMode, 
                                  Initiator initiator) 
      throws DMException, SmsException;
}
