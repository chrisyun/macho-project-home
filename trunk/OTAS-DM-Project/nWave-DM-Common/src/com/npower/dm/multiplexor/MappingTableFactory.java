/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/multiplexor/MappingTableFactory.java,v 1.2 2008/12/25 02:37:42 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/12/25 02:37:42 $
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

package com.npower.dm.multiplexor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import com.npower.sms.SmsException;

/**
 * @author Zhao wanxiang
 * @version $Revision: 1.2 $ $Date: 2008/12/25 02:37:42 $
 */

public class MappingTableFactory {

  /**
   * 
   */
  public MappingTableFactory() {
    super();
  }

  public MappingTable createMappingTable(File file) throws IOException, SAXException, SmsException {
    return this.createMappingTable(new FileInputStream(file));
  }

  public MappingTable createMappingTable(InputStream input) throws IOException, SAXException, SmsException {
    return this.loadNumberMappingImporter(input);
  }

  private Digester createNumberMappingDataDigester(){
    
    Digester digester = new Digester();
    digester.setValidating(false);
    
    digester.addObjectCreate("*/NumberMapping", "class", MappingRuleBasedRegExpress.class);
    digester.addBeanPropertySetter("*/NumberMapping/NumberPattern", "numberPattern");
    digester.addBeanPropertySetter("*/NumberMapping/QueueName", "queueName");
    digester.addSetNext("*/NumberMapping", "addRule");
    return digester;
  }
  
  public MappingTable loadNumberMappingImporter(InputStream input) throws IOException, SAXException, SmsException{
    MappingTable table = new MappingTable();
    Digester digester = this.createNumberMappingDataDigester();
    digester.push(table);   
    digester.parse(input);  
    return table;
  }
  
}
