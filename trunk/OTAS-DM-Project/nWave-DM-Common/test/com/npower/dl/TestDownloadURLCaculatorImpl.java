/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/dl/TestDownloadURLCaculatorImpl.java,v 1.3 2007/05/16 07:17:57 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2007/05/16 07:17:57 $
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
package com.npower.dl;

import junit.framework.TestCase;

import com.npower.dm.core.DMException;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2007/05/16 07:17:57 $
 */
public class TestDownloadURLCaculatorImpl extends TestCase {

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link com.npower.dl.DownloadURLCaculatorImpl#getDownloadDescriptorURL(java.lang.String)}.
   * @throws DMException 
   */
  public void testGetDownloadDescriptorURL() throws DMException {
    {
      DownloadURLCaculatorImpl caculator = new DownloadURLCaculatorImpl();
      caculator.setDownloadServerURL("http://download.otas.com:8080/otasdm/dl/DownloadDescriptor/${update.id}.xml");
      String downloadURL = caculator.getDownloadDescriptorURL("12345678");
      
      assertEquals("http://download.otas.com:8080/otasdm/dl/DownloadDescriptor/12345678.xml", 
                    downloadURL);
    }
    
    try {
        DownloadURLCaculatorImpl caculator = new DownloadURLCaculatorImpl();
        caculator.setDownloadServerURL("http://download.otas.com:8080/otasdm/dl/DownloadDescriptor/${update.id}.xml");
        caculator.getDownloadDescriptorURL(null);
        fail("Update is null, will throw exception");
    } catch (DMException e) {
      assertTrue(true);
      assertEquals("Update ID is null, could not caculate download descriptor.", 
                   e.getMessage());
    }
    
    try {
        DownloadURLCaculatorImpl caculator = new DownloadURLCaculatorImpl();
        caculator.setDownloadServerURL(null);
        caculator.getDownloadDescriptorURL("12345678");
        fail("Download server URL is null, will throw exception");
    } catch (DMException e) {
      assertTrue(true);
      assertEquals("Missing download server URL, Could not caculate Download Descriptor for ID: 12345678", 
                   e.getMessage());
    }
  
    
  }

}
