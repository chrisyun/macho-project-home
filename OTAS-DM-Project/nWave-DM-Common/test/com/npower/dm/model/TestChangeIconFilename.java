/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/model/TestChangeIconFilename.java,v 1.1 2007/11/15 04:08:24 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/11/15 04:08:24 $
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
package com.npower.dm.model;

import java.io.File;

import junit.framework.TestCase;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/11/15 04:08:24 $
 */
public class TestChangeIconFilename extends TestCase {

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
   * @param baseDir
   */
  private void findFiles(File baseDir) {
    File[] files = baseDir.listFiles();
    for (int i = 0; files != null && i < files.length; i++) {
        if (files[i].isFile()) {
           String filename = files[i].getName();
           if (filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".gif")) {
              if (!filename.toLowerCase().equals(filename)) {
                 System.err.println(files[i].getAbsolutePath());
                 //files[i].renameTo(new File(files[i].getAbsolutePath().toLowerCase()));
              }
           }
        } else {
          this.findFiles(files[i]);
        }
    }
  }

  public void testToLowerCase() throws Exception {
    File baseDir = new File("D:/zhao/myworkspace/nWave-DM-Common/metadata/setup/models");
    
    findFiles(baseDir);
  }

}
