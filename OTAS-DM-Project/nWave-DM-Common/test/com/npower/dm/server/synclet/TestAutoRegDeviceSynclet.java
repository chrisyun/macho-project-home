/**
 * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/server/synclet/TestAutoRegDeviceSynclet.java,v 1.4 2008/06/16 10:11:15 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2008/06/16 10:11:15 $
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
package com.npower.dm.server.synclet;

import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;

import sync4j.framework.core.SyncML;
import sync4j.framework.engine.pipeline.MessageProcessingContext;

import com.npower.dm.AllTests;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.Device;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.Subscriber;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.SubscriberBean;

/**
 * @author Zhao DongLu
 * 
 */
public class TestAutoRegDeviceSynclet extends TestCase {

  /**
   * CarrierEntity External ID
   */
  private String Carrier_External_ID_1 = "Test.Carrier.1";

  private String Carrier_External_ID_2 = "Test.Carrier.2";

  private static final String MANUFACTURER_External_ID_1 = "TEST.MANUFACTUER.1";

  private static final String MANUFACTURER_External_ID_2 = "TEST.MANUFACTUER.2";

  private static final String MODEL_External_ID_1 = "TEST.MODEL.1";

  private static final String MODEL_External_ID_2 = "TEST.MODEL.2";

  // device#2
  private static final String subscriberExternalID_1 = "13801351111";
  private static final String subscriberPhoneNumber_1 = "13801351111";
  private static final String subscriberPassword_1 = "12345678";
  private static final String deviceExternalID_1 = "IMEI:12345678904321";

  // device#2
  private static final String subscriberExternalID_2 = "13801352222";
  private static final String subscriberPhoneNumber_2 = "13801352222";
  private static final String subscriberPassword_2 = "87654321";
  private static final String deviceExternalID_2 = "IMEI:09876543214321";

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    
    this.setUpCarrier(Carrier_External_ID_1);
    this.setUpCarrier(Carrier_External_ID_2);
    // Add model with TAC
    this.setupModel(MANUFACTURER_External_ID_1, MODEL_External_ID_1, deviceExternalID_1);
    this.setupModel(MANUFACTURER_External_ID_2, MODEL_External_ID_2, deviceExternalID_2);
    
    this.setupDevices();
  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();

  }

  public void setUpCarrier(String carrierID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    CarrierBean carrierBean = factory.createCarrierBean();
    try {
        Country country = factory.createCountryBean().getCountryByISOCode("CN");
        assertNotNull(country);
        assertEquals(country.getCountryCode(), "86");
        Carrier carrier = carrierBean.getCarrierByExternalID(carrierID);
        if (carrier != null) {
           factory.beginTransaction();
           carrierBean.delete(carrier);
           factory.commit();
        }
        factory.beginTransaction();
        carrier = carrierBean.newCarrierInstance(country, carrierID, carrierID);
        carrierBean.update(carrier);
        factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  private void setupModel(String manufacturerExternalID, String modelExternalID, String tacInfo) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    ModelBean modelBean = factory.createModelBean();

    try {
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        if (manufacturer != null) {
           factory.beginTransaction();
           modelBean.delete(manufacturer);
           factory.commit();
        }
        manufacturer = modelBean.newManufacturerInstance(manufacturerExternalID, manufacturerExternalID, manufacturerExternalID);
        factory.beginTransaction();
        modelBean.update(manufacturer);
        factory.commit();
  
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelExternalID);
        if (model != null) {
           factory.beginTransaction();
           modelBean.delete(model);
           factory.commit();
        }
        factory.beginTransaction();
        model = modelBean.newModelInstance(manufacturer, modelExternalID, modelExternalID, true, true, true, true, true);
        modelBean.update(model);
        if (StringUtils.isNotEmpty(tacInfo)) {
           modelBean.addTACInfo(model, tacInfo);
        }
        factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }
  
  protected void setupDevices() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        DeviceBean bean = factory.createDeviceBean();
        SubscriberBean subscriberBean = factory.createSubcriberBean();
        CarrierBean carrierBean = factory.createCarrierBean();
        ModelBean modelBean = factory.createModelBean();
        
        Carrier carrier_1 = carrierBean.getCarrierByExternalID(Carrier_External_ID_1);
        Carrier carrier_2 = carrierBean.getCarrierByExternalID(Carrier_External_ID_2);
        Manufacturer manufacturer_1 = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID_1);
        Manufacturer manufacturer_2 = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID_2);
        Model model_1 = modelBean.getModelByManufacturerModelID(manufacturer_1, MODEL_External_ID_1);
        Model model_2= modelBean.getModelByManufacturerModelID(manufacturer_2, MODEL_External_ID_2);
    
        factory.beginTransaction();
        // Setup device#1
        Subscriber subscriber_1 = subscriberBean.newSubscriberInstance(carrier_1, subscriberExternalID_1, subscriberPhoneNumber_1, subscriberPassword_1);
        subscriberBean.update(subscriber_1);
        Device device_1 = bean.newDeviceInstance(subscriber_1, model_1, deviceExternalID_1);
        bean.update(device_1);
    
        // Setup device#1
        Subscriber subscriber_2 = subscriberBean.newSubscriberInstance(carrier_2, subscriberExternalID_2, subscriberPhoneNumber_2, subscriberPassword_2);
        subscriberBean.update(subscriber_2);
        Device device_2 = bean.newDeviceInstance(subscriber_2, model_2, deviceExternalID_2);
        bean.update(device_2);
        
        factory.commit();
    } catch (Exception ex) {
      factory.rollback();
      throw ex;
    } finally {
      factory.release();
    }
  }

  /**
   * 
   */
  private SyncML setupSyncML(String deviceExternalID, String manufacturerExternalID, String modelExternalID) throws Exception {
    
    String content = "<SyncML>" + 
                      "<SyncHdr>" + 
                      "<VerDTD>1.1</VerDTD>" + 
                      "<VerProto>DM/1.1</VerProto>" + 
                      "<SessionID>1</SessionID>" + 
                      "<MsgID>1</MsgID>" + 
                      "<Target><LocURI>http://219.142.217.167:8080/otasdm/dm</LocURI></Target>" + 
                      "<Source><LocURI>" + deviceExternalID + "</LocURI></Source>" + 
                      "<Cred><Meta><Type>syncml:auth-basic</Type></Meta><Data>b3Rhc2RtOm90YXNkbQ==</Data></Cred>" + 
                      "<Meta><MaxMsgSize>10000</MaxMsgSize><MaxObjSize>786432</MaxObjSize></Meta>" + 
                      "</SyncHdr>" + 
                      "<SyncBody>" + 
                      "<Alert><CmdID>1</CmdID><Data>1201</Data></Alert>" + 
                      "<Replace>" + 
                      "<CmdID>2</CmdID>" + 
                      "<Item>" + 
                      "<Source><LocURI>./DevInfo/Man</LocURI></Source><Meta><Format>chr</Format><Type>text/plain</Type></Meta>" + 
                      "<Data>" + manufacturerExternalID + "</Data>" + 
                      "</Item>" + 
                      "<Item><Source><LocURI>./DevInfo/Mod</LocURI></Source><Meta><Format>chr</Format><Type>text/plain</Type></Meta>" + 
                      "<Data>" + modelExternalID + "</Data></Item>" + 
                      "<Item><Source><LocURI>./DevInfo/DevId</LocURI></Source>" + 
                      "<Meta><Format>chr</Format><Type>text/plain</Type></Meta><Data>IMEI:351886011493928</Data></Item><Item><Source>" + 
                      "<LocURI>./DevInfo/Lang</LocURI></Source><Meta><Format>chr</Format><Type>text/plain</Type></Meta><Data>ch</Data></Item>" + 
                      "<Item><Source><LocURI>./DevInfo/DmV</LocURI></Source><Meta><Format>chr</Format><Type>text/plain</Type></Meta>" + 
                      "<Data>1.0</Data></Item><Item><Source><LocURI>./DevInfo/Ext/ModDDF</LocURI></Source><Meta><Format>chr</Format><Type>text/plain</Type></Meta><Data>5238</Data></Item>" + 
                      "<Item><Source><LocURI>./DevInfo/Ext/ModDevDet</LocURI></Source><Meta><Format>chr</Format><Type>text/plain</Type>" + 
                      "</Meta><Data>25350</Data></Item></Replace>" + 
                      "<Final></Final>" + 
                      "</SyncBody>" + 
                      "</SyncML>";
    
    IBindingFactory f = BindingDirectory.getFactory(SyncML.class);
    IUnmarshallingContext c = f.createUnmarshallingContext();

     SyncML syncML = (SyncML)c.unmarshalDocument(new ByteArrayInputStream(content.getBytes("UTF-8")), "UTF-8");
     return syncML;
  }
  
  public HttpServletRequest setupHttpRequest(String headerName, String headerValue) {
    SimpleHttpRequest request = new SimpleHttpRequest();
    request.setHeader(headerName, headerValue);
    return request;
  }
  
  /**
   *  测试Synclet的缺省状态
   * @throws Exception
   */
  public void testStatus() throws Exception {
    AutoRegDeviceSynclet synclet = new AutoRegDeviceSynclet();
    // Default status
    assertTrue(synclet.isEnable());
    assertFalse(synclet.isAutoBoundle());
  }
  
  /**
   *  测试Synclet的状态为Disable.
   *  Disable时不做任何处理
   * @throws Exception
   */
  public void testDisable() throws Exception {
    AutoRegDeviceSynclet synclet = new AutoRegDeviceSynclet();
    synclet.setEnable(false);

    assertFalse(synclet.isEnable());
    assertFalse(synclet.isAutoBoundle());

    synclet.preProcessMessage(null, null, null);
  }
  
  /**
   *  测试是否能够自动添加未知设备
   *  预期结果：应该能够自动添加未知设备
   * @throws Exception
   */
  public void testAutoAddUnkownDevice() throws Exception {
    String headerName = "user-agent";
    AutoRegDeviceSynclet synclet = new AutoRegDeviceSynclet();
    // Default status
    assertTrue(synclet.isEnable());
    assertFalse(synclet.isAutoBoundle());
    SimpleCarrierDetector carrierDetector = new SimpleCarrierDetector();
    carrierDetector.setCarrierExternalID(Carrier_External_ID_1);
    synclet.setCarrierDetector(carrierDetector);
    String clientUsername = "otasdm";
    synclet.setClientUsername(clientUsername);
    String clientPassword = "otasdm";
    synclet.setClientPassword(clientPassword);
    String serverPassword = "otasdm";
    synclet.setServerPassword(serverPassword);
    HttpHeaderPhoneNumberDetector parser = new HttpHeaderPhoneNumberDetector();
    parser.setHeaderName(headerName);
    synclet.setPhoneNumberDetector(parser);
    
    String deviceExternalID = deviceExternalID_1 + System.currentTimeMillis();
    String devicePhoneNumber = subscriberPhoneNumber_1 + System.currentTimeMillis();
    String manufacturerExternalID = MANUFACTURER_External_ID_1;
    String modelExternalID = MODEL_External_ID_1;
    
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
        
        assertNull(device);
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }

    MessageProcessingContext processingContext = new MessageProcessingContext();
    SyncML message = this.setupSyncML(deviceExternalID, manufacturerExternalID, modelExternalID);
    HttpServletRequest httpRequest = this.setupHttpRequest(headerName, devicePhoneNumber);
    synclet.preProcessMessage(processingContext, message, httpRequest);
    
    factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
        
        assertNotNull(device);
        assertEquals(deviceExternalID, device.getExternalId());
        assertEquals(devicePhoneNumber, device.getSubscriberPhoneNumber());
        assertEquals(devicePhoneNumber, device.getSubscriber().getPhoneNumber());
        assertEquals(MANUFACTURER_External_ID_1, device.getModel().getManufacturer().getExternalId());
        assertEquals(MODEL_External_ID_1, device.getModel().getManufacturerModelId());
        assertEquals(clientUsername, device.getOMAClientPassword());
        assertEquals(clientPassword, device.getOMAClientPassword());
        assertEquals(serverPassword, device.getOMAServerPassword());
        assertEquals(clientPassword, device.getSubscriber().getPassword());
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }
  
  /**
   *  测试:设备已经存在
   *  预期结果：设备已经存在，设备为发生变化
   * @throws Exception
   */
  public void testExists() throws Exception {
    String headerName = "user-agent";
    AutoRegDeviceSynclet synclet = new AutoRegDeviceSynclet();
    // Default status
    assertTrue(synclet.isEnable());
    assertFalse(synclet.isAutoBoundle());
    
    SimpleCarrierDetector carrierDetector = new SimpleCarrierDetector();
    carrierDetector.setCarrierExternalID(Carrier_External_ID_1);
    synclet.setCarrierDetector(carrierDetector);
    synclet.setClientUsername("otasdm");
    synclet.setClientPassword("otasdm");
    synclet.setServerPassword("otasdm");
    HttpHeaderPhoneNumberDetector parser = new HttpHeaderPhoneNumberDetector();
    parser.setHeaderName(headerName);
    synclet.setPhoneNumberDetector(parser);
    
    String deviceExternalID = deviceExternalID_1;
    String devicePhoneNumber = subscriberPhoneNumber_1;
    String manufacturerExternalID = MANUFACTURER_External_ID_1;
    String modelExternalID = MODEL_External_ID_1;
    
    ManagementBeanFactory factory = null;
    Device oldDevice = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        DeviceBean deviceBean = factory.createDeviceBean();
        oldDevice = deviceBean.getDeviceByExternalID(deviceExternalID);
        
        assertNotNull(oldDevice);
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }

    MessageProcessingContext processingContext = new MessageProcessingContext();
    SyncML message = this.setupSyncML(deviceExternalID, manufacturerExternalID, modelExternalID);
    HttpServletRequest httpRequest = this.setupHttpRequest(headerName, devicePhoneNumber);
    synclet.preProcessMessage(processingContext, message, httpRequest);
    
    factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
        
        assertNotNull(device);
        assertEquals(oldDevice.getID(), device.getID());
        assertEquals(deviceExternalID, device.getExternalId());
        assertEquals(devicePhoneNumber, device.getSubscriberPhoneNumber());
        assertEquals(devicePhoneNumber, device.getSubscriber().getPhoneNumber());
        assertEquals(MANUFACTURER_External_ID_1, device.getModel().getManufacturer().getExternalId());
        assertEquals(MODEL_External_ID_1, device.getModel().getManufacturerModelId());
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }
  
  /**
   *  测试:设备已经存在, 并检查设备电话号码的一致性, 设备电话号码发生变化
   *  预期结果：设备电话号码发生变化
   * @throws Exception
   */
  public void testEnableBoudle() throws Exception {
    String headerName = "user-agent";
    AutoRegDeviceSynclet synclet = new AutoRegDeviceSynclet();
    synclet.setAutoBoundle(true);
    
    assertTrue(synclet.isEnable());
    assertTrue(synclet.isAutoBoundle());
    
    SimpleCarrierDetector carrierDetector = new SimpleCarrierDetector();
    carrierDetector.setCarrierExternalID(Carrier_External_ID_1);
    synclet.setCarrierDetector(carrierDetector);
    synclet.setClientUsername("otasdm");
    synclet.setClientPassword("otasdm");
    synclet.setServerPassword("otasdm");
    HttpHeaderPhoneNumberDetector parser = new HttpHeaderPhoneNumberDetector();
    parser.setHeaderName(headerName);
    synclet.setPhoneNumberDetector(parser);
    
    String deviceExternalID = deviceExternalID_1;
    String devicePhoneNumber = subscriberPhoneNumber_1 + System.currentTimeMillis();
    String manufacturerExternalID = MANUFACTURER_External_ID_1;
    String modelExternalID = MODEL_External_ID_1;
    
    ManagementBeanFactory factory = null;
    Device oldDevice = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        DeviceBean deviceBean = factory.createDeviceBean();
        oldDevice = deviceBean.getDeviceByExternalID(deviceExternalID);
        
        assertNotNull(oldDevice);
        assertEquals(subscriberPhoneNumber_1, oldDevice.getSubscriberPhoneNumber());
        assertEquals(subscriberPhoneNumber_1, oldDevice.getSubscriber().getPhoneNumber());
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }

    MessageProcessingContext processingContext = new MessageProcessingContext();
    SyncML message = this.setupSyncML(deviceExternalID, manufacturerExternalID, modelExternalID);
    HttpServletRequest httpRequest = this.setupHttpRequest(headerName, devicePhoneNumber);
    synclet.preProcessMessage(processingContext, message, httpRequest);
    
    factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
        
        assertNotNull(device);
        assertEquals(oldDevice.getID(), device.getID());
        assertEquals(deviceExternalID, device.getExternalId());
        assertEquals(devicePhoneNumber, device.getSubscriberPhoneNumber());
        assertEquals(devicePhoneNumber, device.getSubscriber().getPhoneNumber());
        assertEquals(MANUFACTURER_External_ID_1, device.getModel().getManufacturer().getExternalId());
        assertEquals(MODEL_External_ID_1, device.getModel().getManufacturerModelId());
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }
  

  /**
   *  测试:已经存在,电话号码发生变化, 但禁止电话号码一致性检查
   *  预期结果：设备得电话号码未发生变化
   * @throws Exception
   */
  public void testDisableBoudle() throws Exception {
    String headerName = "user-agent";
    AutoRegDeviceSynclet synclet = new AutoRegDeviceSynclet();
    synclet.setAutoBoundle(false);
    
    assertTrue(synclet.isEnable());
    assertFalse(synclet.isAutoBoundle());
    
    SimpleCarrierDetector carrierDetector = new SimpleCarrierDetector();
    carrierDetector.setCarrierExternalID(Carrier_External_ID_1);
    synclet.setCarrierDetector(carrierDetector);
    synclet.setClientUsername("otasdm");
    synclet.setClientPassword("otasdm");
    synclet.setServerPassword("otasdm");
    HttpHeaderPhoneNumberDetector parser = new HttpHeaderPhoneNumberDetector();
    parser.setHeaderName(headerName);
    synclet.setPhoneNumberDetector(parser);
    
    String deviceExternalID = deviceExternalID_1;
    String devicePhoneNumber = subscriberPhoneNumber_1 + System.currentTimeMillis();
    String manufacturerExternalID = MANUFACTURER_External_ID_1;
    String modelExternalID = MODEL_External_ID_1;
    
    ManagementBeanFactory factory = null;
    Device oldDevice = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        DeviceBean deviceBean = factory.createDeviceBean();
        oldDevice = deviceBean.getDeviceByExternalID(deviceExternalID);
        
        assertNotNull(oldDevice);
        assertEquals(subscriberPhoneNumber_1, oldDevice.getSubscriberPhoneNumber());
        assertEquals(subscriberPhoneNumber_1, oldDevice.getSubscriber().getPhoneNumber());
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }

    MessageProcessingContext processingContext = new MessageProcessingContext();
    SyncML message = this.setupSyncML(deviceExternalID, manufacturerExternalID, modelExternalID);
    HttpServletRequest httpRequest = this.setupHttpRequest(headerName, devicePhoneNumber);
    synclet.preProcessMessage(processingContext, message, httpRequest);
    
    factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
        
        assertNotNull(device);
        assertEquals(oldDevice.getID(), device.getID());
        assertEquals(deviceExternalID, device.getExternalId());
        assertEquals(subscriberPhoneNumber_1, device.getSubscriberPhoneNumber());
        assertEquals(subscriberPhoneNumber_1, device.getSubscriber().getPhoneNumber());
        assertEquals(MANUFACTURER_External_ID_1, device.getModel().getManufacturer().getExternalId());
        assertEquals(MODEL_External_ID_1, device.getModel().getManufacturerModelId());
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }
  

  public static void main(String[] args) {
    junit.textui.TestRunner.run(TestAutoRegDeviceSynclet.class);
  }

}
