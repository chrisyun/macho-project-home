/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/DeviceLog.java,v 1.4 2006/05/10 10:39:11 zhaoyang Exp $
 * $Revision: 1.4 $
 * $Date: 2006/05/10 10:39:11 $
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
package com.npower.dm.core;

import java.util.Date;

/**
 * <p>
 * Represnet a DeviceLog.
 * </p>
 * 
 * @author Zhao Yang
 * @version $Revision: 1.4 $ $Date: 2006/05/10 10:39:11 $
 */
public interface DeviceLog {

  /**
   * id of device external
   */
  public static final String DEVICE_EXTERNAL_ID = "1234567890";

  /**
   * if of model external
   */
  public static final String MODEL_EXTERNAL_ID = "zy_test_model_name_";

  /**
   * id of manufacturer external
   */
  public static final String MANUFACTURER_EXTERNAL_ID = "zy_test1_manufacturer_name_";

  /**
   * id of carrier external
   */
  public static final String CARRIER_EXTERNAL_ID = "Zhao Yang";

  /**
   * name of carrier
   */
  public static final String CARRIER_NAME = "Yang carrier";

  /**
   * subscriber external id
   */
  public static final String subExternalID = "zhaozhao";

  /**
   * subscriber phone number
   */
  public static final String subPhoneNumber = "13240025585";

  /**
   * subscriber password
   */
  public static final String subPassword = "820213";

  /**
   * Country ISO code
   */
  public static final String Count_Name = "CN";

  /**
   * action name
   */
  public static final String actionName = "CREATED";

  public abstract long getID();

  public abstract void setID(long deviceLogId);

  public abstract DeviceLogAction getLogAction();

  public abstract void setLogAction(DeviceLogAction nwDmDeviceLogAction);

  // public abstract ProvisionRequest getProvReq();

  // public abstract void setProvReq(ProvisionRequest nwDmProvReq);

  public abstract Device getDevice();

  public abstract void setDevice(Device nwDmDevice);

  public abstract Date getCreationDate();

  public abstract void setCreationDate(Date creationDate);

  public abstract String getDeviceExternalId();

  public abstract void setDeviceExternalId(String deviceExternalId);

  public abstract String getSubscriberPhoneNumber();

  public abstract void setSubscriberPhoneNumber(String subscriberPhoneNumber);

  public abstract String getJobType();

  public abstract void setJobType(String jobType);

  public abstract String getMessage();

  public abstract void setMessage(String message);

}