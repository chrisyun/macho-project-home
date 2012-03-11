/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/bootstrap/BootstrapService.java,v 1.12 2008/07/29 07:17:40 zhao Exp $
 * $Revision: 1.12 $
 * $Date: 2008/07/29 07:17:40 $
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
package com.npower.dm.bootstrap;

import com.npower.cp.OTAInventory;
import com.npower.dm.core.DMException;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.sms.SmsException;
import com.npower.sms.client.SmsSender;
import com.npower.wap.omacp.OMACPSecurityMethod;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.12 $ $Date: 2008/07/29 07:17:40 $
 */
public interface BootstrapService {

  /**
   * Bootstrap URL parameter name for target device MSISDN
   */
  //public static final String BOOTSTRAP_MSISDN_PARAMETER_NAME = "bootstrap_msisdn";
  
  /**
   * Bootstrap URL parameter name for target device ID
   */
  //public static final String BOOTSTRAP_DEVICE_ID_PARAMETER_NAME = "bootstrap_device_id";

  /**
   * Bootstrap URL parameter name for target device ext ID
   */
  //public static final String BOOTSTRAP_DEVICE_EXT_ID_PARAMETER_NAME = "bootstrap_device_ext_id";

  /**
   * Bootstrap URL parameter name for target device ext ID
   */
  public static final String BOOTSTRAP_PARAMETRERS_NAME = "bsp";

  /**
   * @return the beanFactory
   */
  public abstract ManagementBeanFactory getBeanFactory();

  /**
   * @param beanFactory the beanFactory to set
   */
  public abstract void setBeanFactory(ManagementBeanFactory beanFactory);

  /**
   * @return the otaInventory
   */
  public abstract OTAInventory getOtaInventory();

  /**
   * @param otaInventory the otaInventory to set
   */
  public abstract void setOtaInventory(OTAInventory otaInventory);

  /**
   * @return the smsSender
   */
  public abstract SmsSender getSmsSender();

  /**
   * @param smsSender the smsSender to set
   */
  public abstract void setSmsSender(SmsSender smsSender);

  /**
   * Bootstrap a device.
   * @param deviceExternalID   device external id in DM respository.
   * @param pinType            Type of Pin
   * @param pin                pin for bootstrap USERPIN.
   * @return
   *         Job ID
   * @throws DMException
   * @throws SmsException
   */
  public abstract long bootstrap(String deviceExternalID, OMACPSecurityMethod pinType, String pin) 
      throws DMException, SmsException;
  
  /**
   * Bootstrap a device.
   * @param deviceExternalID   device external id in DM respository.
   * @param pinType            Type of Pin
   * @param pin                pin for bootstrap USERPIN.
   * @param scheduleTime       Time to send the bootstrap.
   * @param maxRetry
   *        Number of max retries for this message.
   *        If < 0, will overwrite by default max retries.
   * @param maxDuration
   *        Max duration time for next retry in miliseconds.
   *        If < 0, will overwrite by default duration.
   * @return
   *         Job ID
   * @throws DMException
   */
  public abstract long bootstrap(String deviceExternalID, 
                                 OMACPSecurityMethod pinType, String pin, 
                                 long scheduleTime, 
                                 int maxRetry, 
                                 long maxDuration) throws DMException, SmsException;
  
  /**
   * Bootstrap a device.
   * @param msisdn
   *        msisdn of device
   * @param serverPassword
   *        Password of DM Server
   * @param serverNonce
   *        Nonce of DM Server
   * @param clientUsername
   *        sername of client
   * @param clientPassword
   *        Password of client
   * @param clientNonce
   *        Nonce of client
   * @param pinType
   *        Type of Pin
   * @param pin
   *        Pin of bootstrap
   * @return
   *         Job ID
   * @throws DMException
   */
  public abstract long bootstrap(String msisdn, 
                                 String serverPassword, 
                                 String serverNonce, 
                                 String clientUsername, 
                                 String clientPassword, 
                                 String clientNonce, 
                                 OMACPSecurityMethod pinType, 
                                 String pin) throws DMException, SmsException;

  /**
   * Bootstrap a device.
   * @param msisdn
   *        msisdn of device
   * @param serverPassword
   *        Password of DM Server
   * @param serverNonce
   *        Nonce of DM Server
   * @param clientUsername
   *        sername of client
   * @param clientPassword
   *        Password of client
   * @param clientNonce
   *        Nonce of client
   * @param pinType
   *        Type of Pin
   * @param pin
   *        Pin of bootstrap
   * @param scheduleTime
   *        Time to send the bootstrap.
   * @param maxRetry
   *        Number of max retries for this message.
   *        If < 0, will overwrite by default max retries.
   * @param maxDuration
   *        Max duration time for next retry in miliseconds.
   *        If < 0, will overwrite by default duration.
   * @return
   *         Job ID
   * @throws DMException
   */
  public abstract long bootstrap(String msisdn, 
                                 String serverPassword, 
                                 String serverNonce, 
                                 String clientUsername, 
                                 String clientPassword, 
                                 String clientNonce, 
                                 OMACPSecurityMethod pinType, 
                                 String pin, 
                                 long scheduleTime, 
                                 int maxRetry, 
                                 long maxDuration) throws DMException, SmsException;

}