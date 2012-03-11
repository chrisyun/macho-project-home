/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/util/TestUtil.java,v 1.5 2007/08/14 07:24:59 zhao Exp $
  * $Revision: 1.5 $
  * $Date: 2007/08/14 07:24:59 $
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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import junit.framework.TestCase;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;

import sync4j.framework.core.AbstractCommand;
import sync4j.framework.core.Item;
import sync4j.framework.core.Meta;
import sync4j.framework.core.Results;
import sync4j.framework.core.SyncML;
import sync4j.framework.core.Util;
import sync4j.framework.tools.WBXMLTools;

import com.npower.dm.AllTests;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2007/08/14 07:24:59 $
 */
public class TestUtil extends TestCase {

  /**
   * 
   */
  public TestUtil(String name) {
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
  
  /**
   * SonyEricsson M600 发送binary数据时, 在Item中缺少Meta信息, 测试修正后能否提前将binary数据转换为b64格式
   * @throws Exception
   */
  public void testCase1() throws Exception {
    String dumpFilename = AllTests.BASE_DIR + "/test/com/npower/dm/util/SE_M600_request_msg.bin";
    //String dumpFilename = "D:/Temp/m600dump/W0JAMTMwZTcxNC0xMTc0ODc4NTAyODAz_1174878502853_req.msg";
    File dumpFile = new File(dumpFilename);
    byte[] msg = new byte[(int)dumpFile.length()];
    InputStream in = new FileInputStream(dumpFilename);
    in.read(msg);
    
    String inMessage = WBXMLTools.wbxmlToXml(msg);
    System.out.println(inMessage);
    
    SyncMLCanonizer4Test syncMLCanonizer = new SyncMLCanonizer4Test();
    inMessage = syncMLCanonizer.canonizeInputMessage(inMessage);


    IBindingFactory f = BindingDirectory.getFactory(SyncML.class);
    IUnmarshallingContext c = f.createUnmarshallingContext();

    Object syncML = c.unmarshalDocument( new ByteArrayInputStream(inMessage.getBytes("UTF-8")), "UTF-8");

    SyncML inputMessage = (SyncML)syncML;
    
    AbstractCommand resultCommand = (AbstractCommand)inputMessage.getSyncBody().getCommands().get(2);
    Meta meta = resultCommand.getMeta();
    assertEquals("b64", meta.getMetInf().getFormat());
    assertEquals("text/plain", meta.getMetInf().getType());
    
    assertTrue("Results commnad", resultCommand instanceof Results);
    Results command = (Results)resultCommand;
    assertEquals(1, command.getItems().size());
    assertNotNull(command.getItems().get(0));
    assertNotNull(command.getItems().get(0) instanceof Item);
    Item item = (Item)command.getItems().get(0);
    assertNotNull(item.getMeta());
    assertNotNull(item.getMeta().getMetInf());
    assertEquals("b64", item.getMeta().getMetInf().getFormat());
    assertEquals("text/plain", item.getMeta().getMetInf().getType());
    
    String result = Util.toXML(inputMessage);
    System.out.println(result);
    
  }
  
  public void testCase2() throws Exception {
    String inMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
    "<SyncML>" +
    "<SyncHdr>" +
    "<VerDTD>1.1</VerDTD>" +
    "<VerProto>DM/1.1</VerProto>" +
    "<SessionID>1065</SessionID>" +
    "<MsgID>3</MsgID>" +
    "<Target>" +
    "<LocURI>http://192.168.1.1:8080/otasdm/dm?sid=W0JAMTMwZTcxNC0xMTc0ODc4NTAyODAz</LocURI>" +
    "</Target>" +
    "<Source>" +
    "<LocURI>IMEI:350149101482769</LocURI>" +
    "</Source>" +
    "<Meta>" +
    "<MaxMsgSize>10000</MaxMsgSize>" +
    "</Meta>" +
    "</SyncHdr>" +
    "<SyncBody>" +
    "<Status>" +
    "<CmdID>1</CmdID>" +
    "<MsgRef>2</MsgRef>" +
    "<CmdRef>0</CmdRef>" +
    "<Cmd>SyncHdr</Cmd>" +
    "<TargetRef>IMEI:350149101482769</TargetRef>" +
    "<SourceRef>http://192.168.1.1:8080/otasdm/dm?sid=W0JAMTMwZTcxNC0xMTc0ODc4NTAyODAz</SourceRef>" +
    "<Chal>" +
    "<Meta>" +
    "<Format>b64</Format>" +
    "<Type>syncml:auth-MAC</Type>" +
    "<NextNonce>TTI2OVBBR2xsMFowMTRlMUZWVXNHQT09</NextNonce>" +
    "</Meta>" +
    "</Chal>" +
    "<Data>200</Data>" +
    "</Status>" +
    "<Status>" +
    "<CmdID>2</CmdID>" +
    "<MsgRef>2</MsgRef>" +
    "<CmdRef>2</CmdRef>" +
    "<Cmd>Get</Cmd>" +
    "<TargetRef>./UIQ/BBE/System/Icon/DefaultFolder</TargetRef>" +
    "<Data>200</Data>" +
    "</Status>" +
    "<Results>" +
    "<CmdID>3</CmdID>" +
    "<MsgRef>2</MsgRef>" +
    "<CmdRef>2</CmdRef>" +
    "<Meta>" +
    "<Format>b64</Format>" +
    "<Type>text/plain</Type>" +
    "<Size>10353</Size>" +
    "</Meta>" +
    "<Item>" +
    "<Source>" +
    "<LocURI>./UIQ/BBE/System/Icon/DefaultFolder</LocURI>" +
    "</Source>" +
    "<Data>NwAAEEIAABAAAAAAOW</Data>" +
    "<MoreData></MoreData>" +
    "</Item>" +
    "</Results>" +
    "</SyncBody>" +
    "</SyncML>";
    
    SyncMLCanonizer4Test syncMLCanonizer = new SyncMLCanonizer4Test();
    inMessage = syncMLCanonizer.canonizeInputMessage(inMessage);


    IBindingFactory f = BindingDirectory.getFactory(SyncML.class);
    IUnmarshallingContext c = f.createUnmarshallingContext();

    Object syncML = c.unmarshalDocument( new ByteArrayInputStream(inMessage.getBytes("UTF-8")), "UTF-8");

    SyncML inputMessage = (SyncML)syncML;

    AbstractCommand resultCommand = (AbstractCommand)inputMessage.getSyncBody().getCommands().get(2);
    Meta meta = resultCommand.getMeta();
    assertEquals("b64", meta.getMetInf().getFormat());
    assertEquals("text/plain", meta.getMetInf().getType());
    
    assertTrue("Results commnad", resultCommand instanceof Results);
    Results command = (Results)resultCommand;
    assertEquals(1, command.getItems().size());
    assertNotNull(command.getItems().get(0));
    assertNotNull(command.getItems().get(0) instanceof Item);
    Item item = (Item)command.getItems().get(0);
    assertNotNull(item.getMeta());
    assertNotNull(item.getMeta().getMetInf());
    assertEquals("b64", item.getMeta().getMetInf().getFormat());
    assertEquals("text/plain", item.getMeta().getMetInf().getType());
    
    String result = Util.toXML(inputMessage);
    System.out.println(result);
  }

  public void testCase3() throws Exception {
    String inMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
    "<SyncML>" +
    "<SyncHdr>" +
    "<VerDTD>1.1</VerDTD>" +
    "<VerProto>DM/1.1</VerProto>" +
    "<SessionID>1065</SessionID>" +
    "<MsgID>3</MsgID>" +
    "<Target>" +
    "<LocURI>http://192.168.1.1:8080/otasdm/dm?sid=W0JAMTMwZTcxNC0xMTc0ODc4NTAyODAz</LocURI>" +
    "</Target>" +
    "<Source>" +
    "<LocURI>IMEI:350149101482769</LocURI>" +
    "</Source>" +
    "<Meta>" +
    "<MaxMsgSize>10000</MaxMsgSize>" +
    "</Meta>" +
    "</SyncHdr>" +
    "<SyncBody>" +
    "<Status>" +
    "<CmdID>1</CmdID>" +
    "<MsgRef>2</MsgRef>" +
    "<CmdRef>0</CmdRef>" +
    "<Cmd>SyncHdr</Cmd>" +
    "<TargetRef>IMEI:350149101482769</TargetRef>" +
    "<SourceRef>http://192.168.1.1:8080/otasdm/dm?sid=W0JAMTMwZTcxNC0xMTc0ODc4NTAyODAz</SourceRef>" +
    "<Chal>" +
    "<Meta>" +
    "<Format>b64</Format>" +
    "<Type>syncml:auth-MAC</Type>" +
    "<NextNonce>TTI2OVBBR2xsMFowMTRlMUZWVXNHQT09</NextNonce>" +
    "</Meta>" +
    "</Chal>" +
    "<Data>200</Data>" +
    "</Status>" +
    "<Status>" +
    "<CmdID>2</CmdID>" +
    "<MsgRef>2</MsgRef>" +
    "<CmdRef>2</CmdRef>" +
    "<Cmd>Get</Cmd>" +
    "<TargetRef>./UIQ/BBE/System/Icon/DefaultFolder</TargetRef>" +
    "<Data>200</Data>" +
    "</Status>" +
    "<Results>" +
    "<CmdID>3</CmdID>" +
    "<MsgRef>2</MsgRef>" +
    "<CmdRef>2</CmdRef>" +

    "<Item>" +
    "<Meta>" +
    "<Format>b64</Format>" +
    "<Type>text/plain</Type>" +
    "<Size>10353</Size>" +
    "</Meta>" +
    "<Source>" +
    "<LocURI>./UIQ/BBE/System/Icon/DefaultFolder</LocURI>" +
    "</Source>" +
    "<Data>NwAAEEIAABAAAAAAOW</Data>" +
    "<MoreData></MoreData>" +
    "</Item>" +
    "</Results>" +
    "</SyncBody>" +
    "</SyncML>";
    
    SyncMLCanonizer4Test syncMLCanonizer = new SyncMLCanonizer4Test();
    inMessage = syncMLCanonizer.canonizeInputMessage(inMessage);


    IBindingFactory f = BindingDirectory.getFactory(SyncML.class);
    IUnmarshallingContext c = f.createUnmarshallingContext();

    Object syncML = c.unmarshalDocument( new ByteArrayInputStream(inMessage.getBytes("UTF-8")), "UTF-8");

    SyncML inputMessage = (SyncML)syncML;

    AbstractCommand resultCommand = (AbstractCommand)inputMessage.getSyncBody().getCommands().get(2);
    Meta meta = resultCommand.getMeta();
    assertNull(meta);
    
    String result = Util.toXML(inputMessage);
    System.out.println(result);
  }

  /**
   * 测试中文信息的处理
   * @throws Exception
   */
  public void testChinese() throws Exception {
    String dumpFilename = AllTests.BASE_DIR + "/test/com/npower/dm/util/chinese_msg.bin";
    //String dumpFilename = "D:/Temp/m600dump/W0JAMTMwZTcxNC0xMTc0ODc4NTAyODAz_1174878502853_req.msg";
    File dumpFile = new File(dumpFilename);
    byte[] msg = new byte[(int)dumpFile.length()];
    InputStream in = new FileInputStream(dumpFilename);
    in.read(msg);
    
    String inMessage = WBXMLTools.wbxmlToXml(msg);
    System.out.println(inMessage);
    
    SyncMLCanonizer4Test syncMLCanonizer = new SyncMLCanonizer4Test();
    inMessage = syncMLCanonizer.canonizeInputMessage(inMessage);


    IBindingFactory f = BindingDirectory.getFactory(SyncML.class);
    IUnmarshallingContext c = f.createUnmarshallingContext();

    Object syncML = c.unmarshalDocument( new ByteArrayInputStream(inMessage.getBytes("UTF-8")), "UTF-8");

    SyncML inputMessage = (SyncML)syncML;
    
    assertEquals(3, inputMessage.getSyncBody().getCommands().size());
    AbstractCommand resultCommand = (AbstractCommand)inputMessage.getSyncBody().getCommands().get(2);
    assertTrue(resultCommand instanceof Results);
    Results result = (Results)resultCommand;
    assertNotNull(result.getItems());
    assertEquals(1, result.getItems().size());
    Item item = (Item)result.getItems().get(0);
    String data = item.getData().getData();
    assertEquals("诺基亚 PC 套件 ", data);
    
    byte[] bs = "诺基亚 PC 套件 ".getBytes("UTF-8");
    assertNotNull(bs);
    System.out.println("诺基亚 PC 套件 ".getBytes("UTF-8"));
    
  }
  
  public void testAppendParameter() throws Exception {
    {
      String url = "http://www.aaa.com/login?a=1";
      String paramName = "msisdn";
      String paramValue = "+8613801356729";
      assertEquals("http://www.aaa.com/login?a=1&msisdn=%2B8613801356729", 
                   DMUtil.appendParameter(url, paramName, paramValue));
    }
    {
      String url = "http://www.aaa.com/login?a=1&msisdn=%2B8613801356729";
      String paramName = "msisdn";
      String paramValue = "+8613801356729";
      assertEquals("http://www.aaa.com/login?a=1&msisdn=%2B8613801356729&msisdn=%2B8613801356729", 
                   DMUtil.appendParameter(url, paramName, paramValue));
    }
  }
}
