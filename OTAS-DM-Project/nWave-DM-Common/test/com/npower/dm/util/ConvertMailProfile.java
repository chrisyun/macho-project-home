/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/util/ConvertMailProfile.java,v 1.1 2007/09/11 11:03:47 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/09/11 11:03:47 $
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
package com.npower.dm.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/09/11 11:03:47 $
 */
public class ConvertMailProfile {

  /**
   * 
   */
  public ConvertMailProfile() {
  }

  /**
   * @param writer
   * @param values
   */
  private static void writeXML(FileWriter writer, Map<String, String> values) throws IOException {
    String result = "  <!-- Email Profile: " + values.get("name") + " Email Profile-->\n" +
                    "  <Profile>\n" +
                    "    <ProfileName>" + values.get("name") + "</ProfileName>\n" +
                    "    <Template>Email Default Template</Template>\n" +
                    "    <Carrier>ChinaMobile</Carrier>\n" +
                    "    <NAPProfile>NAP CMNET</NAPProfile>\n" +
                    "    <ProxyProfile></ProxyProfile>\n" +
                    "    <Description>" + values.get("name") + " Email Profile</Description>\n" +
                    "    <Attributes>\n" +
                    "      <Attribute>\n" +
                    "         <Name>Display Name</Name>\n" +
                    "         <Value>" + values.get("name") + "</Value>\n" +
                    "      </Attribute>\n" +
                    "      <Attribute>\n" +
                    "         <Name>Username</Name>\n" +
                    "         <Value></Value>\n" +
                    "      </Attribute>    \n" +
                    "      <Attribute>\n" +
                    "         <Name>Email Address</Name>\n" +
                    "         <Value>@" + values.get("name").toLowerCase() + "</Value>\n" +
                    "      </Attribute>    \n" +
                    "      <Attribute>\n" +
                    "         <Name>Reply To Address</Name>\n" +
                    "         <Value></Value>\n" +
                    "      </Attribute>\n" +
                    "      <Attribute>\n" +
                    "         <Name>Receiving Server Address</Name>\n" +
                    "         <Value>" + values.get("pop.host").toLowerCase() + "</Value>\n" +
                    "      </Attribute>\n" +
                    "      <Attribute>\n" +
                    "         <Name>Receiving Server Port</Name>\n" +
                    "         <Value>110</Value>\n" +
                    "      </Attribute>\n" +
                    "      <Attribute>\n" +
                    "         <Name>Use SSL Receiving Service</Name>\n" +
                    "         <Value>false</Value>\n" +
                    "      </Attribute>\n" +
                    "      <Attribute>\n" +
                    "         <Name>Sending Server Address</Name>\n" +
                    "         <Value>" + values.get("smtp.host").toLowerCase() + "</Value>\n" +
                    "      </Attribute>\n" +
                    "      <Attribute>\n" +
                    "         <Name>Sending Server Port</Name>\n" +
                    "         <Value>25</Value>\n" +
                    "      </Attribute>\n" +
                    "      <Attribute>\n" +
                    "         <Name>Use SSL Sending Service</Name>\n" +
                    "         <Value>false</Value>\n" +
                    "      </Attribute>\n" +
                    "      <Attribute>\n" +
                    "         <Name>Use SMTP authentication</Name>\n" +
                    "         <Value>true</Value>\n" +
                    "      </Attribute>\n" +
                    "      <Attribute>\n" +
                    "         <Name>Mailbox Protocol</Name>\n" +
                    "         <Value>POP</Value>\n" +
                    "      </Attribute>\n" +
                    "    </Attributes>\n" +
                    "  </Profile>\n\n";
    writer.write(result);
  }

  /**
   * @param args
   */
  public static void main(String[] args) throws Exception {
    File outputFile = new File("c:/temp/mail.xml");
    FileWriter writer = new FileWriter(outputFile);
    
    File csvFile = new File("c:/temp/mail.csv");
    BufferedReader reader = new BufferedReader(new FileReader(csvFile));
    String line = reader.readLine();
    while (line != null) {
          line = reader.readLine();
          if (StringUtils.isEmpty(line)) {
             continue;
          }
 
          String[] cols = StringUtils.split(line, ',');
          Map<String, String> values = new HashMap<String, String>();
          values.put("name", cols[0]);
          values.put("smtp.host", cols[1]);
          values.put("pop.host", cols[2]);
          
          writeXML(writer, values);
    }
    
    writer.close();
    reader.close();
  }

}
