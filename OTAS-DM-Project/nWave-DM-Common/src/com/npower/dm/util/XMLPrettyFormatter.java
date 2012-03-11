/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/util/XMLPrettyFormatter.java,v 1.3 2007/01/29 09:15:56 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2007/01/29 09:15:56 $
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
package com.npower.dm.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2007/01/29 09:15:56 $
 */
public class XMLPrettyFormatter {
  
  private static Log log = LogFactory.getLog(XMLPrettyFormatter.class);
  
  private Document document = null;
  
  /**
   * Constructor from a XML String
   * @param xmlText
   * @throws DocumentException
   */
  public XMLPrettyFormatter(String xmlText) throws DocumentException {
    super();
    StringReader reader = new StringReader(xmlText);
    this.document = this.loadDocument(reader);
  }
  
  /**
   * Constructor from a XML file
   * @param xmlFile
   * @throws FileNotFoundException
   * @throws DocumentException
   */
  public XMLPrettyFormatter(File xmlFile) throws FileNotFoundException, DocumentException {
    super();
    this.document = this.loadDocument(new FileReader(xmlFile));
  }
  
  private Document loadDocument(Reader reader) throws DocumentException {
    SAXReader saxReader = new SAXReader(); 
    Document xmlDocument = saxReader.read(reader);
    return xmlDocument;
  }


  /**
   * Format the xml to pretty print format.
   * @return
   * @throws IOException
   */
  public String format() throws IOException {

    StringWriter writer = new StringWriter();
    OutputFormat format = OutputFormat.createPrettyPrint();
    XMLWriter xmlwriter = new XMLWriter(writer, format);
    xmlwriter.write(this.document);
    return writer.toString();

  }

  /**
   * TestCase & Demo.
   * @param args
   */
  public static void main(String[] args) {
    try {
        //String text = "<A><B></B></A>";
        //XMLPrettyFormatter formatter = new XMLPrettyFormatter(text);
        File file = new File("D:/Zhao/MyWorkspace/nWave-DM-Common/metadata/ddf/sony_ericsson/M600_DDF.xml");
        XMLPrettyFormatter formatter = new XMLPrettyFormatter(file);
        String result = formatter.format();
        FileOutputStream out = new FileOutputStream("C:/temp/M600_DDF.xml");
        out.write(result.getBytes());
        out.close();
    } catch (DocumentException e) {
      log.info("XMLPrettyFormatter:" + e);
    } catch (IOException e) {
      log.info("XMLPrettyFormatter:" + e);
    }
  }

}
