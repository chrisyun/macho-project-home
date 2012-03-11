/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/OTAClientProvJobBean.java,v 1.13 2008/12/10 02:28:54 zhao Exp $
  * $Revision: 1.13 $
  * $Date: 2008/12/10 02:28:54 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.management;

import java.util.List;

import com.npower.cp.OTAException;
import com.npower.cp.convertor.ValueFetcher;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.ClientProvJobTargetDevice;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.hibernate.management.NotFoundClientProvTemplateException;
import com.npower.wap.omacp.OMACPSecurityMethod;
import com.npower.wap.omacp.OMAClientProvDoc;
import com.npower.wap.push.SmsWapPushMessage;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.13 $ $Date: 2008/12/10 02:28:54 $
 */
public interface OTAClientProvJobBean extends BaseBean {
  
  /**
   * Build ProvisionDoc
   * @param model
   *        Model of target device
   * @param profile
   *        Profile which will be translated to ProvisionDoc
   * @return
   * @throws DMException
   * @throws OTAException
   */
  public abstract OMAClientProvDoc buildProvisionDoc(Model model, 
                                                     ProfileConfig profile, 
                                                     ValueFetcher<ProfileCategory, String, String> valueFetcher) throws DMException;
  
  /**
   * Build WapPushMessage.
   * @param msisdn
   *        Device MSISDN
   * @param model
   *        Model of device
   * @param profile
   *        Profile to built wap push message
   * @param securityMethod
   *        OTA Security Method
   * @param pin
   *        PIN of OTA security
   * @return
   * @throws DMException
   * @throws NotFoundClientProvTemplateException 
   */
  public abstract SmsWapPushMessage buildWapPushMessage(Model model, 
                                                        ProfileConfig profile, 
                                                        ValueFetcher<ProfileCategory, String, String> valueFetcher, 
                                                        OMACPSecurityMethod securityMethod, 
                                                        String pin) throws DMException, NotFoundClientProvTemplateException;

  /**
   * Create an OTA Job
   * @param msisdn
   *        Device phone number
   * @param model
   *        Model of device
   * @param profile
   * @param valueFetcher
   * @param pinType            Type of Pin
   * @param pin                pin for bootstrap USERPIN.
   * @param scheduleTime       Time to send the OTA message.
   * @param maxRetry
   *        Number of max retries for this message.
   *        If < 0, will overwrite by default max retries.
   * @param maxDuration
   *        Max duration time for next retry in miliseconds.
   *        If < 0, will overwrite by default duration.
   * @return
   * @throws DMException
   */
  public ProvisionJob provision(String msisdn, 
                        Model model, 
                        Carrier carrier, 
                        ProfileConfig profile, 
                        ValueFetcher<ProfileCategory, String, String> valueFetcher, 
                        OMACPSecurityMethod securityMethod, 
                        String pin, 
                        long scheduleTime, int maxRetry, long maxDuration) throws DMException;

  /**
   * Create an OTA Job.
   * @param device
   *        Target device
   * @param profile
   *        OTA Profile
   * @param valueFetcher
   *        ProfileValueFetcher
   * @param securityMethod
   *        OTA Security methods (Pin type)
   * @param pin
   *        OTA Pin
   * @param scheduleTime
   *        Time to send the OTA message
   * @param maxRetry
   *        Number of max retries for this message.
   *        If < 0, will overwrite by default max retries.
   * @param maxDuration
   *        Max duration time for next retry in miliseconds.
   *        If < 0, will overwrite by default duration.
   * @throws DMException
   */
  public ProvisionJob provision(Device device, 
                        ProfileConfig profile, 
                        ValueFetcher<ProfileCategory, String, String> valueFetcher, 
                        OMACPSecurityMethod securityMethod, 
                        String pin,
                        long scheduleTime, 
                        int maxRetry, 
                        long maxDuration) throws DMException;
  
  /**
   * Load an OTA Job by job ID
   * @param jobID
   * @return
   * @throws DMException
   */
  public abstract ProvisionJob getJobById(long jobID) throws DMException;
  
  /**
   * Update an OTA Job
   * @param job
   * @throws DMException
   */
  public abstract void update(ProvisionJob job) throws DMException;
  
  /**
   * Disable an OTA Job
   * @param jobID
   * @throws DMException
   */
  public abstract void disable(long jobID) throws DMException;
  
  /**
   * Enable an OTA Job
   * @param jobID
   * @throws DMException
   */
  public abstract void enable(long jobID) throws DMException;
  
  /**
   * Delete an OTA Job
   * @param jobID
   * @throws DMException
   */
  public abstract void delete(long jobID) throws DMException;
    
  /**
   * @param job
   * @throws DMException
   */
  public abstract void update(ClientProvJobTargetDevice job) throws DMException;
  
  /**
   * Find TargetDevice status records by message id.
   * @param messageID
   *        Message ID
   * @return
   */
  public abstract List<ClientProvJobTargetDevice> findTargetDeviceByMessageID(String messageID) throws DMException;
  
  /**
   * Find TargetDevice status
   * @param job
   * @param device
   * @return
   * @throws DMException
   */
  public abstract ClientProvJobTargetDevice getProvisionJobStatus(ProvisionJob job, Device device) throws DMException;
  
}
