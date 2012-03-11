/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/notifcation/NotificationService.java,v 1.7 2007/04/03 01:58:26 zhao Exp $
 * $Revision: 1.7 $
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
 * @version $Revision: 1.7 $ $Date: 2007/04/03 01:58:26 $
 */
public interface NotificationService {

  /**
   * Return ManagementBeanFactory.
   * @return the beanFactory
   */
  public abstract ManagementBeanFactory getBeanFactory();

  /**
   * Set ManagementBeanFactory
   * @param beanFactory the beanFactory to set
   */
  public abstract void setBeanFactory(ManagementBeanFactory beanFactory);

  /**
   * Return SmsSender
   * @return the smsSender
   */
  public abstract SmsSender getSmsSender();

  /**
   * Set SmsSender
   * @param smsSender the smsSender to set
   */
  public abstract void setSmsSender(SmsSender smsSender);

  /**
   * Notificate a device by device's externalID.
   * @param deviceExternalID
   *        Device external ID.
   * @param dmServerID
   *        DM Server ID
   * @param sessionID
   *        DM Session ID
   * @param uiMode
   *        UI mode
   * @param initiator
   *        Initiator
   * @param scheduleTime
   *        Time of send the notification
   * @param maxRetry
   *        Number of max retries for this message.
   *        If < 0, will overwrite by default max retries.
   * @param maxDuration
   *        Max duration time for next retry in miliseconds.
   *        If < 0, will overwrite by default duration.
   * @throws DMException
   * @throws SmsException
   *         Failured in send SMS
   */
  public abstract void notificate(String deviceExternalID, 
                                  String dmServerID, 
                                  int sessionID, 
                                  UIMode uiMode, 
                                  Initiator initiator, 
                                  long scheduleTime, 
                                  int maxRetry, 
                                  long maxDuration) throws DMException, SmsException;

  /**
   * Notificate a device by device's externalID.
   * @param deviceExternalID
   *        Device external ID.
   * @param dmServerID
   *        DM Server ID
   * @param sessionID
   *        DM Session ID
   * @param uiMode
   *        UI mode
   * @param initiator
   *        Initiator
   * @throws DMException
   * @throws SmsException
   *         Failured in send SMS
   */
  public abstract void notificate(String deviceExternalID, 
                                  String dmServerID, 
                                  int sessionID, 
                                  UIMode uiMode, 
                                  Initiator initiator) throws DMException, SmsException;

  /**
   * Notification a device by device's phone number.
   * @param msisdn
   *        device's msisdn      
   * @param dmServerID
   *        DM server ID
   * @param serverPassword
   *        DM Server password
   * @param sessionID
   *        DM Session ID
   * @param uiMode
   *        UI mode
   * @param initiator
   *        Initiator
   * @param scheduleTime
   *        Time of send the notification
   * @param maxRetry
   *        Number of max retries for this message.
   *        If < 0, will overwrite by default max retries.
   * @param maxDuration
   *        Max duration time for next retry in miliseconds.
   *        If < 0, will overwrite by default duration.
   * @throws DMException
   * @throws SmsException
   *         Failured in send SMS
   */
  public abstract void notificate(String msisdn, 
                                  String dmServerID, 
                                  String serverPassword, 
                                  int sessionID, 
                                  UIMode uiMode, 
                                  Initiator initiator, 
                                  long scheduleTime, 
                                  int maxRetry, 
                                  long maxDuration) throws DMException, SmsException;

  /**
   * Notification a device by device's phone number.
   * @param msisdn
   *        device's msisdn      
   * @param dmServerID
   *        DM server ID
   * @param serverPassword
   *        DM Server password
   * @param sessionID
   *        DM Session ID
   * @param uiMode
   *        UI mode
   * @param initiator
   *        Initiator
   * @throws DMException
   * @throws SmsException
   *         Failured in send SMS
   */
  public abstract void notificate(String msisdn, 
                                  String dmServerID, 
                                  String serverPassword, 
                                  int sessionID, 
                                  UIMode uiMode, 
                                  Initiator initiator) throws DMException, SmsException;
}