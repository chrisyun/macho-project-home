/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/notifcation/NotificationServiceImpl.java,v 1.7 2008/06/30 10:45:46 zhao Exp $
  * $Revision: 1.7 $
  * $Date: 2008/06/30 10:45:46 $
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

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.bootstrap.AbstractBootstrapService;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.management.DeviceBean;
import com.npower.sms.SmsException;
import com.npower.wap.omacp.notification.DMNotificationMessage;
import com.npower.wap.omacp.notification.Initiator;
import com.npower.wap.omacp.notification.UIMode;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2008/06/30 10:45:46 $
 */
/**
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2008/06/30 10:45:46 $
 */
/**
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2008/06/30 10:45:46 $
 */
public class NotificationServiceImpl extends AbstractNotificationService implements NotificationService {

  private static Log log = LogFactory.getLog(NotificationServiceImpl.class);
  
  /**
   * Default constructor.
   */
  public NotificationServiceImpl() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.notifcation.NotificationService#notificate(java.lang.String, java.lang.String, int, com.npower.wap.omacp.notification.UIMode, com.npower.wap.omacp.notification.Initiator, long, int, long)
   */
  public void notificate(String deviceExternalID, String dmServerID, int sessionID, UIMode uiMode, Initiator initiator, long scheduleTime, int maxRetry, long maxDuration)
      throws DMException, SmsException {
    DeviceBean deviceBean = this.getBeanFactory().createDeviceBean();
    Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
    if (device == null) {
       throw new DMException("Notification device not not found, externalID: " + deviceExternalID);
    }
    String msisdn = AbstractBootstrapService.caculatePhoneNumber(device);
    // Send Notification Message.
    log.info("Notification, device msisdn: " + msisdn + ", device externalID:" + deviceExternalID);
    notificate(msisdn, dmServerID, device.getOMAServerPassword(), sessionID, uiMode, initiator, scheduleTime, maxRetry, maxDuration);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.notifcation.AbstractNotificationService#notificate(java.lang.String, java.lang.String, int, com.npower.wap.omacp.notification.UIMode, com.npower.wap.omacp.notification.Initiator)
   */
  public void notificate(String deviceExternalID, String dmServerID, int sessionID, UIMode uiMode, Initiator initiator)
      throws DMException, SmsException {
    long scheduleTime = System.currentTimeMillis();
    this.notificate(deviceExternalID, dmServerID, sessionID, uiMode, initiator, scheduleTime, -1, -1);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.notifcation.NotificationService#notificate(java.lang.String, java.lang.String, java.lang.String, int, com.npower.wap.omacp.notification.UIMode, com.npower.wap.omacp.notification.Initiator, long, int, long)
   */
  public void notificate(String msisdn, String dmServerID, String serverPassword, int sessionID, UIMode uiMode, Initiator initiator, long scheduleTime, int maxRetry, long maxDuration) 
      throws DMException, SmsException {
    DMNotificationMessage msg = new DMNotificationMessage();
    msg.setInitiator(initiator);
    msg.setUiMode(uiMode);
    msg.setServerId(dmServerID);
    msg.setServerPassword(serverPassword);
    msg.setServerNonce(null);
    msg.setSessionId(sessionID);
    msg.setDmProtocolVersion(1.1f);
    
    try {
        // Send Notification Message.
        log.info("Notification, device msisdn: " + msisdn);
        this.getSmsSender().send(msg, msisdn, msisdn, scheduleTime, maxRetry, maxDuration);
    } catch (SmsException e) {
      throw e;
    } catch (IOException e) {
      throw new DMException("Error in Send SMS for Notification.", e);
    } catch (Exception e) {
      throw new DMException("Error in Send SMS for Notification.", e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.notifcation.AbstractNotificationService#notificate(java.lang.String, java.lang.String, java.lang.String, int, com.npower.wap.omacp.notification.UIMode, com.npower.wap.omacp.notification.Initiator)
   */
  public void notificate(String msisdn, String dmServerID, String serverPassword, int sessionID, UIMode uiMode, Initiator initiator) 
      throws DMException, SmsException {
    long scheduleTime = System.currentTimeMillis();
    this.notificate(msisdn, dmServerID, serverPassword, sessionID, uiMode, initiator, scheduleTime, -1, -1);
  }
}
