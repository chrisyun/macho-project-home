/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/export/XMLWriter4SoftwareVendor.java,v 1.2 2008/12/04 09:56:26 zhaowx Exp $
 * $Revision: 1.2 $
 * $Date: 2008/12/04 09:56:26 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
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

package com.npower.dm.export;

import java.io.IOException;
import java.io.Writer;
import com.npower.dm.core.SoftwareVendor;




/**
 * @author Zhao wanxiang
 * @version $Revision: 1.2 $ $Date: 2008/12/04 09:56:26 $
 */

public class XMLWriter4SoftwareVendor extends BaseXMLWriter {


  /**
   * 
   */
  public XMLWriter4SoftwareVendor() {
    super();
  }

  /**
   * 
   */
  public XMLWriter4SoftwareVendor(Writer writer) {
    super(writer);
  }

  public void writeBody(SoftwareVendor softwareVendor) throws IOException {

    if (writer != null && softwareVendor != null) {
      writeBlank(2);
      writer.write("<SoftwareVendor>"); 
      writer.write("\n");
      writeBlank(3);
      writer.write("<Name>");
      String name = softwareVendor.getName();
      write(name);
      writer.write("</Name>");
      writer.write("\n");
      writeBlank(3);
      writer.write("<Description>");
      String description = softwareVendor.getDescription();      
      write(description);      
      writer.write("</Description>");
      writer.write("\n");
      writeBlank(2);
      writer.write("</SoftwareVendor>"); 
      writer.write("\n");
    }
  }
  
  public void writeHeader() throws IOException{
    if (writer != null) {
      writer.write("\n");  
      writeBlank(1);
      writer.write("<SoftwareVendors>"); 
      writer.write("\n");      
    }
  }
  
  public void writeEnd() throws IOException{
    if(writer != null) {
      writeBlank(1);
      writer.write("</SoftwareVendors>");
      writer.write("\n");
      
    }
  }
  
  private void writeBlank(int number) throws IOException {
    if (writer != null) {
      for (int i = 1; i <= number; i++) {
        writer.write("\t");
      }
    }
  }
}
