/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/Element4Provision.java,v 1.6 2006/04/27 08:08:55 zhao Exp $
 * $Revision: 1.6 $
 * $Date: 2006/04/27 08:08:55 $
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

import com.npower.dm.core.Carrier;
import com.npower.dm.core.DeviceGroup;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2006/04/27 08:08:55 $
 */
public class Element4Provision extends AbstractElement4Provision implements java.io.Serializable {

  // Constructors

  /** default constructor */
  public Element4Provision() {
    super();
  }

  /** full constructor */
  public Element4Provision(DeviceGroup deviceGroup, ProvisionRequest provReq) {
    super(deviceGroup, provReq);
  }

  /** full constructor */
  public Element4Provision(Carrier carrier, DeviceGroup deviceGroup, ProvisionRequest provReq) {
    super(carrier, deviceGroup, provReq);
  }

}
