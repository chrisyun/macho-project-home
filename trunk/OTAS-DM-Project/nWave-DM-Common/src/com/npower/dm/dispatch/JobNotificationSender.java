/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/dispatch/JobNotificationSender.java,v 1.5 2008/08/01 06:45:39 zhao Exp $
  * $Revision: 1.5 $
  * $Date: 2008/08/01 06:45:39 $
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
package com.npower.dm.dispatch;

import java.util.Properties;

import com.npower.dm.bootstrap.BootstrapService;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.notifcation.NotificationService;
import com.npower.sms.client.SmsSender;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2008/08/01 06:45:39 $
 */
public interface JobNotificationSender {

  /**
   * @return Returns the defaultSmsGatewayProperties.
   */
  public Properties getDefaultSmsGatewayProperties();

  /**
   * @param defaultSmsGatewayProperties The defaultSmsGatewayProperties to set.
   */
  public void setDefaultSmsGatewayProperties(Properties defaultSmsGatewayProperties);

  /**
   * @return Returns the notificationService.
   */
  public NotificationService getNotificationService();

  /**
   * @param notificationService The notificationService to set.
   */
  public void setNotificationService(NotificationService notificationService);

  /**
   * @return Returns the bootstrapService.
   */
  public BootstrapService getBootstrapService();

  /**
   * @param bootstrapService The bootstrapService to set.
   */
  public void setBootstrapService(BootstrapService bootstrapService);

  /**
   * Return a SmsSender for send SMS message.
   * @param carrier
   *        Carrier which included SMSC Properties.
   * @return
   * @throws Exception
   */
  public SmsSender getSmsSender(Carrier carrier) throws Exception;
  
  /**
   * Dispatch notification at schedule time
   * @param jobID
   * @param deviceJobStatusID
   * @throws DMException
   */
  public void notify(long jobID, long deviceJobStatusID, long deviceID, long scheduleTime) throws DMException;

  /**
   * Dispatch bootstrap message
   * @param jobID
   * @param deviceJobStatusID
   * @param deviceID
   * @param scheduleTime
   * @param maxRetry
   * @param maxDuration
   *        In milliseconds
   * @throws DMException
   */
  public void bootstrap(long jobID, long deviceJobStatusID, long deviceID, long scheduleTime, long maxRetry, long maxDuration) throws DMException;

}
