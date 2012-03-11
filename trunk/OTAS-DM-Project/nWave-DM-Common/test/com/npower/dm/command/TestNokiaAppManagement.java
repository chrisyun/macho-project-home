/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/command/TestNokiaAppManagement.java,v 1.5 2007/12/12 15:15:05 zhao Exp $
  * $Revision: 1.5 $
  * $Date: 2007/12/12 15:15:05 $
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
package com.npower.dm.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import junit.framework.TestCase;
import sync4j.framework.tools.Base64;

import com.npower.dm.AllTests;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2007/12/12 15:15:05 $
 */
public class TestNokiaAppManagement extends TestCase {

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
   * @return
   */
  private File getInstallImage() {
    File installImage = new File(AllTests.BASE_DIR, "metadata/apps/test/nokia/msn_mobile_3.5_for_nokia_6120_544.sisx"); 
    //File installImage = new File(AllTests.BASE_DIR, "metadata/apps/test/nokia/unrealdevman_trial.sis"); 
    //File installImage = new File(AllTests.BASE_DIR, "metadata/apps/test/nokia/callassistant_trial.sis"); 
    //File installImage = new File(AllTests.BASE_DIR, "metadata/apps/test/nokia/194633738.sisx");
    return installImage;
  }

  public void testGenerateDMScript4DeliveryMode() throws Exception {
    File installImage = getInstallImage(); 
    InputStream in = new FileInputStream(installImage);
    byte[] contents = new byte[(int)installImage.length()];
    in.read(contents, 0, contents.length);
    byte[] decodedBytes = Base64.encode(contents);
    in.close();
    
    StringBuffer result = new StringBuffer();
    
    String appNodeName = "BareApp";
    result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    result.append("<Script>\n");
    result.append("  <Sequence>\n");
    result.append("    <Add>\n");
    result.append("      <InteriorNode>\n");
    result.append("        <Target>./SCM/Inventory/Delivered/" + appNodeName + "</Target>\n");
    result.append("      </InteriorNode>\n");
    result.append("    </Add>\n");
    result.append("    <Add>\n");
    result.append("      <LeafNode>\n");
    result.append("        <Target>./SCM/Inventory/Delivered/" + appNodeName + "/Name</Target>\n");
    result.append("        <Data format=\"chr\">Bare</Data>\n");
    result.append("      </LeafNode>\n");
    result.append("    </Add>\n");
    result.append("    <Add>\n");
    result.append("      <LeafNode>\n");
    result.append("        <Target>./SCM/Inventory/Delivered/" + appNodeName + "/Version</Target>\n");
    result.append("        <Data format=\"chr\">1.0</Data>\n");
    result.append("      </LeafNode>\n");
    result.append("    </Add>\n");
    result.append("  </Sequence>\n");
    result.append("  \n");
    result.append("  <Replace>\n");
    result.append("    <LeafNode>\n");
    result.append("      <Target>./SCM/Inventory/Delivered/" + appNodeName + "/Data</Target>\n");
    result.append("      <Data format=\"b64\" type=\"x-epoc/x-sisx-app\">" + new String(decodedBytes, "iso8859-1") + "</Data>\n");
    result.append("    </LeafNode>\n");
    result.append("  </Replace>\n");
    result.append("  \n");
    result.append("  <Replace>\n");
    result.append("    <LeafNode>\n");
    result.append("      <Target>./SCM/Inventory/Delivered/" + appNodeName + "/InstallOpts</Target>\n");
    result.append("      <Data format=\"chr\" type=\"text/xml\"><![CDATA[ <InstOpts>\n");
    result.append("<StdOpt name=\"drive\" value=\"!\"/>\n");
    result.append("<StdOpt name=\"lang\" value=\"*\" />\n");
    result.append("<StdOpt name=\"upgrade\" value=\"yes\"/>\n");
    result.append("<StdOpt name=\"kill\" value=\"yes\"/>\n");
    result.append("<StdSymOpt name=\"pkginfo\" value=\"yes\"/>\n");
    result.append("<StdSymOpt name=\"optionals\" value=\"yes\"/>\n");
    result.append("<StdSymOpt name=\"ocsp\" value=\"yes\"/>\n");
    result.append("<StdSymOpt name=\"capabilities\" value=\"yes\"/>\n");
    result.append("<StdSymOpt name=\"untrusted\" value=\"yes\"/>\n");
    result.append("<StdSymOpt name=\"ignoreocspwarn\" value=\"yes\"/>\n");
    result.append("<StdSymOpt name=\"ignorewarn\" value=\"yes\"/>\n");
    result.append("<StdSymOpt name=\"fileoverwrite\" value=\"yes\"/>\n");
    result.append("</InstOpts> ]]></Data>\n");
    result.append("    </LeafNode>\n");
    result.append("  </Replace>\n");
    result.append("  \n");
    result.append("  <Exec>\n");
    result.append("    <Item>\n");
    result.append("      <Target>./SCM/Inventory/Delivered/" + appNodeName + "/Operations/InstallAndActivate</Target>\n");
    result.append("    </Item>\n");
    result.append("  </Exec>\n");
    result.append("  \n");
    result.append("</Script>\n");
    
    System.out.println(result.toString());
  }

  public void testGenerateDMScript4DownloadMode() throws Exception {
    File installImage = getInstallImage(); 
    InputStream in = new FileInputStream(installImage);
    byte[] contents = new byte[(int)installImage.length()];
    in.read(contents, 0, contents.length);
    byte[] decodedBytes = Base64.encode(contents);
    in.close();
    
    StringBuffer result = new StringBuffer();
    
    String appNodeName = "BareApp";
    result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    result.append("<Script>\n");
    result.append("  <Sequence>\n");
    result.append("    <Add>\n");
    result.append("      <InteriorNode>\n");
    result.append("        <Target>./SCM/Download/" + appNodeName + "</Target>\n");
    result.append("      </InteriorNode>\n");
    result.append("    </Add>\n");
    result.append("    <Add>\n");
    result.append("      <LeafNode>\n");
    result.append("        <Target>./SCM/Download/" + appNodeName + "/Name</Target>\n");
    result.append("        <Data format=\"chr\">Bare</Data>\n");
    result.append("      </LeafNode>\n");
    result.append("    </Add>\n");
    result.append("    <Add>\n");
    result.append("      <LeafNode>\n");
    result.append("        <Target>./SCM/Download/" + appNodeName + "/Version</Target>\n");
    result.append("        <Data format=\"chr\">1.0</Data>\n");
    result.append("      </LeafNode>\n");
    result.append("    </Add>\n");
    result.append("  </Sequence>\n");
    result.append("  \n");
    result.append("  <Replace>\n");
    result.append("    <LeafNode>\n");
    result.append("      <Target>./SCM/Download/" + appNodeName + "/URI</Target>\n");
    result.append("      <Data format=\"chr\">http://www.otas.mobi:8080/download/msn_mobile_3.5_for_nokia_6120_544.sisx</Data>\n");
    result.append("    </LeafNode>\n");
    result.append("  </Replace>\n");
    result.append("  \n");
    result.append("  <Replace>\n");
    result.append("    <LeafNode>\n");
    result.append("      <Target>./SCM/Download/" + appNodeName + "/InstallOpts</Target>\n");
    result.append("      <Data format=\"chr\" type=\"text/xml\"><![CDATA[ <InstOpts>\n");
    result.append("<StdOpt name=\"drive\" value=\"!\"/>\n");
    result.append("<StdOpt name=\"lang\" value=\"*\" />\n");
    result.append("<StdOpt name=\"upgrade\" value=\"yes\"/>\n");
    result.append("<StdOpt name=\"kill\" value=\"yes\"/>\n");
    result.append("<StdSymOpt name=\"pkginfo\" value=\"yes\"/>\n");
    result.append("<StdSymOpt name=\"optionals\" value=\"yes\"/>\n");
    result.append("<StdSymOpt name=\"ocsp\" value=\"yes\"/>\n");
    result.append("<StdSymOpt name=\"capabilities\" value=\"yes\"/>\n");
    result.append("<StdSymOpt name=\"untrusted\" value=\"yes\"/>\n");
    result.append("<StdSymOpt name=\"ignoreocspwarn\" value=\"yes\"/>\n");
    result.append("<StdSymOpt name=\"ignorewarn\" value=\"yes\"/>\n");
    result.append("<StdSymOpt name=\"fileoverwrite\" value=\"yes\"/>\n");
    result.append("</InstOpts> ]]></Data>\n");
    result.append("    </LeafNode>\n");
    result.append("  </Replace>\n");
    result.append("  \n");
    result.append("  <Exec>\n");
    result.append("    <Item>\n");
    result.append("      <Target>./SCM/Download/" + appNodeName + "/Operations/DownloadAndInstallAndActivate</Target>\n");
    result.append("    </Item>\n");
    result.append("  </Exec>\n");
    result.append("  \n");
    result.append("</Script>\n");
    
    System.out.println(result.toString());
  }


}
