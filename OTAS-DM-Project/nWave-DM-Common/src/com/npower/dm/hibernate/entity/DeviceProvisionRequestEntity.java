/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/DeviceProvisionRequestEntity.java,v 1.4 2008/03/12 05:15:23 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2008/03/12 05:15:23 $
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
package com.npower.dm.hibernate.entity;

import java.util.HashSet;
import java.util.Set;

import com.npower.dm.core.Device;
import com.npower.dm.core.ProvisionJobStatus;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/03/12 05:15:23 $
 */
public class DeviceProvisionRequestEntity extends AbstractDeviceProvisionRequest implements java.io.Serializable {

  /**
   * States for finished
   */
  private static Set<String> SET_OF_FINISHED_STATES = new HashSet<String>();
  static {
    SET_OF_FINISHED_STATES.add(ProvisionJobStatus.DEVICE_JOB_STATE_DONE);
    SET_OF_FINISHED_STATES.add(ProvisionJobStatus.DEVICE_JOB_STATE_CANCELLED);
    SET_OF_FINISHED_STATES.add(ProvisionJobStatus.DEVICE_JOB_STATE_ERROR);
  }
  
  /**
   * States for doing
   */
  private static Set<String> SET_OF_DOING_STATES = new HashSet<String>();
  static {
    SET_OF_DOING_STATES.add(ProvisionJobStatus.DEVICE_JOB_STATE_DOING);
  }
  
  // Constructors

  /** default constructor */
  public DeviceProvisionRequestEntity() {
    super();
  }

  public DeviceProvisionRequestEntity(Element4Provision element, Device device) {
    super(element, device);
  }

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo(ProvisionJobStatus o) {
    if (o == null) {
       return 1;
    }
    return (int)(this.getID() - o.getID());
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJobStatus#isFinished()
   */
  public boolean isFinished() {
    String s = this.getState();
    return SET_OF_FINISHED_STATES.contains(s);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJobStatus#isDoing()
   */
  public boolean isDoing() {
    String s = this.getState();
    return SET_OF_DOING_STATES.contains(s);
  }

}
