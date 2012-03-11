/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/export/XMLWriter4SoftwareCategory.java,v 1.3 2008/12/16 04:19:31 chenlei Exp $
 * $Revision: 1.3 $
 * $Date: 2008/12/16 04:19:31 $
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

import com.npower.dm.core.SoftwareCategory;


/**
 * @author Zhao wanxiang
 * @version $Revision: 1.3 $ $Date: 2008/12/16 04:19:31 $
 */

public class XMLWriter4SoftwareCategory extends BaseXMLWriter {

  /**
   * 
   */
  public XMLWriter4SoftwareCategory() {
    super();
  }

  public XMLWriter4SoftwareCategory(Writer writer) {
    super(writer);
  }

  public void writeBody(SoftwareCategory softwareCategory) throws IOException {

    if (writer != null && softwareCategory != null) {
      writeBlank(2);
      writer.write("<SoftwareCategory>"); 
      writer.write("\n");
      writeBlank(3);
      writer.write("<Name>");
      String name = softwareCategory.getName();
      write(name);
      writer.write("</Name>");
      writer.write("\n");
      writeBlank(3);
      writer.write("<Description>");
      String description = softwareCategory.getDescription();       
      write(description);      
      writer.write("</Description>");
      writer.write("\n");

      if (softwareCategory.getChildren() != null) {
        for(SoftwareCategory children : softwareCategory.getChildren()) {
          writeBody(children);          
        }
      }
      writeBlank(2);
      writer.write("</SoftwareCategory>"); 
      writer.write("\n");
    }
  }
  
  public void writeHeader() throws IOException{
    if (writer != null) {
      
      writer.write("\n");  
      writeBlank(1);
      writer.write("<SoftwareCategorys>"); 
      writer.write("\n");      
    }
  }
  
  public void writeEnd() throws IOException{
    if(writer != null) {
      writeBlank(1);
      writer.write("</SoftwareCategorys>");
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
