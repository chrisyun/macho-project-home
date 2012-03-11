/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/ModelClassificationTask4Midp.java,v 1.1 2008/09/17 03:18:11 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/09/17 03:18:11 $
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
package com.npower.dm.setup.task;

import com.npower.dm.core.selector.j2me.MidpV1Selector;
import com.npower.dm.core.selector.j2me.MidpV2Selector;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/09/17 03:18:11 $
 */
public class ModelClassificationTask4Midp extends BaseModelClassificationTask {

  /**
   * Default constructor
   */
  public ModelClassificationTask4Midp() {
    super();
  }
  
  @Override
  protected void createAll(ManagementBeanFactory factory) throws Exception {
    this.updateModelClassification(factory, new MidpV1Selector(), "Java_MIDP_1.0_Platform", "Java MIDP 1.0 Platform", "Java MIDP 1.0 Platform");
    this.updateModelClassification(factory, new MidpV2Selector(), "Java_MIDP_2.0_Platform", "Java MIDP 2.0 Platform", "Java MIDP 2.0 Platform");
  }

}
