/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/msm/JADCreatorTest.java,v 1.2 2009/02/26 09:14:20 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2009/02/26 09:14:20 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2009 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.msm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.jar.Manifest;

import junit.framework.TestCase;

import com.npower.dm.AllTests;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2009/02/26 09:14:20 $
 */
public class JADCreatorTest extends TestCase {

  /**
   * @param name
   */
  public JADCreatorTest(String name) {
    super(name);
  }

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
  
  public void testGetJADManufest() throws Exception {
    JADCreator creator = new JADCreator();
    
    File jarFile = new File(AllTests.BASE_DIR, "metadata/apps/test/jars/UCWEB6.3-63-999-70-09011709.jar");
    FileInputStream jar = new FileInputStream(jarFile);
    Manifest manifest = creator.getJADManufest(jarFile, "http://aaa.com");
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    manifest.write(out);
    String result = out.toString();
    assertEquals("Manifest-Version: 1.0\n" +
        "MicroEdition-Configuration: CLDC-1.0\n" +
        "MIDlet-Name: UCWEB6.3\n" +
        "MIDlet-Permissions-Opt: javax.microedition.io.Connector.http,javax.mic\n" +
        " roedition.io.Connector.https,javax.microedition.io.Connector.file.rea\n" +
        " d,javax.microedition.io.Connector.file.write,javax.microedition.io.Co\n" +
        " nnector.socket,javax.microedition.io.PushRegistry,javax.microedition.\n" +
        " io.sms,javax.microedition.io.Connector.sms,javax.wireless.messaging.s\n" +
        " ms.send,javax.wireless.messaging.sms.receive\n" +
        "MIDlet-Vendor: UCWEB\n" +
        "MIDlet-1: UCWEB6.3,/l.png,WebClient\n" +
        "MIDlet-Jar-Size: 255997\n" +
        "MIDlet-Jar-URL: http://aaa.com\n" +
        "MIDlet-Version: 6.3.2\n" +
        "MicroEdition-Profile: MIDP-2.0\n".trim(), result.trim());
  }
  
  public void testGetSoftwareInformation() throws Exception {
    JADCreator creator = new JADCreator();
    
    File jarFile = new File(AllTests.BASE_DIR, "metadata/apps/test/jars/UCWEB6.3-63-999-70-09011709.jar");
    JADCreator.SoftwareInformation info = creator.getSoftwareInformation(jarFile);
    assertEquals("UCWEB", info.getVendor());
    assertEquals("UCWEB6.3", info.getName());
    assertEquals("6.3.2", info.getVersion());
    assertTrue(info.isMidp2());
  }

}
