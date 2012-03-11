/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/dispatch/JobNotificationSenderImpl.java,v 1.13 2008/08/01 06:45:39 zhao Exp $
  * $Revision: 1.13 $
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

import java.io.ByteArrayInputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.npower.dm.bootstrap.BootstrapService;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.notifcation.NotificationService;
import com.npower.dm.server.engine.EngineConfig;
import com.npower.sms.client.SmsSender;
import com.npower.sms.client.SmsSenderFactory;
import com.npower.wap.omacp.OMACPSecurityMethod;
import com.npower.wap.omacp.notification.Initiator;
import com.npower.wap.omacp.notification.UIMode;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.13 $ $Date: 2008/08/01 06:45:39 $
 */
public class JobNotificationSenderImpl implements JobNotificationSender {

  //private static Log log = LogFactory.getLog(JobNotificationSenderImpl.class);

  private NotificationService notificationService = null;
  
  private BootstrapService bootstrapService = null;
  
  private Properties defaultSmsGatewayProperties = new Properties();

  /**
   * 
   */
  public JobNotificationSenderImpl() {
    super();
  }

  /**
   * @param notificationService
   * @param bootstrapService
   * @param defaultSmsGatewayProperties
   */
  public JobNotificationSenderImpl(NotificationService notificationService, BootstrapService bootstrapService,
      Properties defaultSmsGatewayProperties) {
    super();
    this.notificationService = notificationService;
    this.bootstrapService = bootstrapService;
    this.defaultSmsGatewayProperties = defaultSmsGatewayProperties;
  }

  // Private methods -----------------------------------------------------------------------------------
  /**
   * Return Pin
   * @param pinType
   * @param device
   * @return
   */
  private String getBootstrapPin(OMACPSecurityMethod pinType, Device device) throws DMException {
    String pin = null;
    if (pinType.equals(OMACPSecurityMethod.USERPIN)) {
       pin = device.getBootstrapUserPin();
       if (StringUtils.isEmpty(pin)) {
          pin = device.getSubscriber().getCarrier().getDefaultBootstrapUserPin();
       }
       if (StringUtils.isEmpty(pin)) {
          pin = "1234";
       }
    } else if (pinType.equals(OMACPSecurityMethod.NETWPIN)) {
      pin = device.getSubscriber().getIMSI();
      if (StringUtils.isEmpty(pin)) {
         throw new DMException("Bootstrap error, missing NETWPIN for device: " + device.getExternalId());
      }
    }
    
    return pin;
  }

  /**
   * 返回设备的Bootstrap PIN Type
   * @param device
   * @return
   */
  private OMACPSecurityMethod getBootstrapPinType(Device device) {
    String pTypeStr = device.getBootstrapPinType();
    // 如果设备 PinType不存在, 查找Subscriber的PIN Type
    if (StringUtils.isEmpty(pTypeStr)) {
       pTypeStr = device.getSubscriber().getBootstrapPinType();
    }
    // 如果Device和Subscriber的PinType都不存在, 使用Carrier的PinType
    if (StringUtils.isEmpty(pTypeStr)) {
       Carrier carrier = device.getSubscriber().getCarrier();
       pTypeStr = carrier.getDefaultBootstrapPinType();
    }
    // 使用缺省
    if (StringUtils.isEmpty(pTypeStr)) {
       // Return default;
       return OMACPSecurityMethod.USERPIN;
    }
    OMACPSecurityMethod result = OMACPSecurityMethod.value(Byte.parseByte(pTypeStr));
    
    return result;
  }

  // Public methods ------------------------------------------------------------------------------------
  

  /* (non-Javadoc)
   * @see com.npower.dm.dispatch.JobNotificationDispatcher#getDefaultSmsGatewayProperties()
   */
  public Properties getDefaultSmsGatewayProperties() {
    return defaultSmsGatewayProperties;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.dispatch.JobNotificationDispatcher#setDefaultSmsGatewayProperties(java.util.Properties)
   */
  public void setDefaultSmsGatewayProperties(Properties defaultSmsGatewayProperties) {
    this.defaultSmsGatewayProperties = defaultSmsGatewayProperties;
  }

  /**
   * @return Returns the bootstrapService.
   */
  public BootstrapService getBootstrapService() {
    return bootstrapService;
  }

  /**
   * @param bootstrapService The bootstrapService to set.
   */
  public void setBootstrapService(BootstrapService bootstrapService) {
    this.bootstrapService = bootstrapService;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.dispatch.JobNotificationDispatcher#getNotificationService()
   */
  public NotificationService getNotificationService() {
    return notificationService;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.dispatch.JobNotificationDispatcher#setNotificationService(com.npower.dm.notifcation.NotificationService)
   */
  public void setNotificationService(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.dispatch.JobNotificationSender#getSmsSender(com.npower.dm.core.Carrier)
   */
  public SmsSender getSmsSender(Carrier carrier) throws Exception {
    // Load default SMS Gateway properties
    Properties props = defaultSmsGatewayProperties;
    
    if (carrier != null && StringUtils.isNotEmpty(carrier.getNotificationProperties())) {
       // Load from carrier SMSC properties.
       String content = carrier.getNotificationProperties();
       props.load(new ByteArrayInputStream(content.getBytes("UTF-8")));
    }
    SmsSenderFactory factory = SmsSenderFactory.newInstance(props);
    SmsSender sender = factory.getSmsSender();
    return sender;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.dispatch.JobNotificationSender#notify(long, long, long, long)
   */
  public void notify(long jobID, long deviceJobStatusID, long deviceID, long scheduleTime) throws DMException {
    NotificationService service = this.getNotificationService();
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByID("" + deviceID);

        ProvisionJob job = jobBean.loadJobByID(jobID);
        //ProvisionJobStatus devicejobStatus = jobBean.getProvisionJobStatus(job, device);
        String deviceExternalID = device.getExternalId();
        int sessionID = (int)jobID;
        String uiModeValue = job.getUiMode();
        int maxRetry = 1;
        long maxDuration = 0;
        // Get DM ServerID
        ProfileConfig dmProfile = device.getSubscriber().getCarrier().getBootstrapDmProfile();
        String dmServerID = null;
        if (dmProfile != null) {
           dmServerID = dmProfile.getProfileAttributeValue("ServerId").getStringValue();
        }
        if (StringUtils.isEmpty(dmServerID)) {
          dmServerID = "otasdm";
        }
        // Send Notification SMS
        Carrier carrier = device.getSubscriber().getCarrier();
        SmsSender sender = this.getSmsSender(carrier);
        service.setSmsSender(sender);
        
        UIMode uiMode = UIMode.USER_INTERACTION;
        if (StringUtils.isNotEmpty(uiModeValue)) {
          uiMode = UIMode.value(uiModeValue);
        }
        service.notificate(deviceExternalID, dmServerID, sessionID, uiMode, Initiator.SERVER, scheduleTime, maxRetry, maxDuration);
    } catch (Exception ex) {
      throw new DMException("Failured to dispatch notification message.", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.dispatch.JobNotificationSender#notify(long, long, long)
   */
  public void notify(long jobID, long deviceJobStatusID, long deviceID) throws DMException {
    long scheduleTime = System.currentTimeMillis();
    this.notify(jobID, deviceJobStatusID, deviceID, scheduleTime );
  }

  /* (non-Javadoc)
   * @see com.npower.dm.dispatch.JobNotificationSender#bootstrap(long, long, long, long, long, long)
   */
  public void bootstrap(long jobID, long deviceJobStatusID, long deviceID, long scheduleTime, long maxRetry, long maxDuration) throws DMException {
    BootstrapService service = this.getBootstrapService();
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByID("" + deviceID);

        String deviceExternalID = device.getExternalId();
        // Send Notification SMS
        Carrier carrier = device.getSubscriber().getCarrier();

        // Get SmsSender
        SmsSender sender = this.getSmsSender(carrier);
        service.setSmsSender(sender);

        OMACPSecurityMethod pinType = this.getBootstrapPinType(device);
        String pin = this.getBootstrapPin(pinType, device);
        
        long bootstrapJobID = service.bootstrap(deviceExternalID, pinType, pin, scheduleTime, (int)maxRetry, maxDuration);
        // Set Parent job
        if (jobID != 0 && bootstrapJobID != 0) {
           ProvisionJobBean bean = factory.createProvisionJobBean();
           ProvisionJob job = bean.loadJobByID(jobID);
           ProvisionJob bootstrapJob = bean.loadJobByID(bootstrapJobID);
           if (job != null && bootstrapJob != null) {
              factory.beginTransaction();
              bootstrapJob.setParent(job);
              bean.update(bootstrapJob);
              factory.commit();
           }
        }
    } catch (Exception ex) {
      throw new DMException("Failured to dispatch bootstrap message.", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

}
