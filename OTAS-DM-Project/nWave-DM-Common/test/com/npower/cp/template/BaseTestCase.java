/**
 * $Header: /home/master/nWave-DM-Common/test/com/npower/cp/template/BaseTestCase.java,v 1.1 2007/09/11 09:36:10 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2007/09/11 09:36:10 $
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
package com.npower.cp.template;

import java.util.Properties;

import com.npower.cp.OTAException;
import com.npower.cp.OTAInventory;
import com.npower.cp.xmlinventory.OTAInventoryImpl;
import com.npower.dm.AllTests;

import junit.framework.TestCase;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/09/11 09:36:10 $
 */
public abstract class BaseTestCase extends TestCase {

  /**
   * 
   */
  public BaseTestCase() {
    super();
  }

  /**
   * @param name
   */
  public BaseTestCase(String name) {
    super(name);
  }

  /**
   * @return
   * @throws OTAException
   */
  protected OTAInventory getOTAInvetory() throws OTAException {
    Properties props = new Properties();
    props.setProperty(OTAInventory.PROPERTY_FACTORY, OTAInventoryImpl.class.getName());
    props.setProperty(OTAInventoryImpl.CP_TEMPLATE_RESOURCE_DIR, AllTests.BASE_DIR + "/metadata/cp/inventory");
    props.setProperty(OTAInventoryImpl.CP_TEMPLATE_RESOURCE_FILENAME, "main.xml");
    
    OTAInventory inventory = OTAInventory.newInstance(props);
    return inventory;
  }

}