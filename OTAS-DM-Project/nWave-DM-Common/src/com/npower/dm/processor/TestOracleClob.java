/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/processor/TestOracleClob.java,v 1.4 2008/06/16 10:11:14 zhao Exp $
  * $Revision: 1.4 $
  * $Date: 2008/06/16 10:11:14 $
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
package com.npower.dm.processor;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import junit.framework.TestCase;

import com.npower.dm.AllTests;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceTreeNode;
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
 * @version $Revision: 1.4 $ $Date: 2008/06/16 10:11:14 $
 */
public class TestOracleClob extends TestCase {

  private static final String MANUFACTURER_External_ID = "TEST.MANUFACTUER.CLOB";
  private static final String MODEL_External_ID = "TEST.MODEL.CLOB";

  /**
   * 
   */
  private static final String CLIENT_PASSWORD = "otasdm";
  /**
   * 
   */
  private static final String CLIENT_USERNAME = "otasdm";

  private String serverPassword = "srvpwd";
  /**
   * CarrierEntity External ID
   */
  private String Carrier_External_ID = "Test.Carrier.CLOB";

  /**
   * DeviceEntity IMEI, ExternalID
   */
  private String Device_External_ID = "IMEI:353755000569999";

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    
    try {
        // clear all
        this.tearDownEntities();
        
        // Create carrier, manufacturer, model, device, subscriber
        setupEntities();
    } catch (Exception e) {
      throw e;
    }
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
    //this.tearDownEntities();
  }
  
  /**
   * @throws DMException
   */
  private void setupEntities() throws Exception {
    this.setupManufacturer(MANUFACTURER_External_ID);
    this.setupModel(MANUFACTURER_External_ID, MODEL_External_ID);
    this.setUpCarrier(Carrier_External_ID);
    this.setupDevice(Carrier_External_ID, MANUFACTURER_External_ID, MODEL_External_ID, Device_External_ID);
  }

  private void setupManufacturer(String manufacturerExternalID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        if (manufacturer == null) {
           manufacturer = modelBean.newManufacturerInstance(manufacturerExternalID, manufacturerExternalID, manufacturerExternalID);
           factory.beginTransaction();
           modelBean.update(manufacturer);
           factory.commit();
        }
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
    
  }

  private void setupModel(String manufacturerExternalID, String modelExternalID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        assertNotNull(manufacturer);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelExternalID);
        if (model == null) {
           model = modelBean.newModelInstance(manufacturer, modelExternalID, modelExternalID, true, true, true, true, true);
           factory.beginTransaction();
           modelBean.update(model);
           factory.commit();
        }
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
    
  }

  public void setUpCarrier(String carrierExternalID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        CarrierBean carrierBean = factory.createCarrierBean();
        Country country = factory.createCountryBean().getCountryByISOCode("CN");
        assertNotNull(country);
        assertEquals(country.getCountryCode(), "86");
        Carrier carrier = carrierBean.getCarrierByExternalID(carrierExternalID);
        if (carrier == null) {
           carrier = carrierBean.newCarrierInstance(country, carrierExternalID, carrierExternalID);
           factory.beginTransaction();
           carrierBean.update(carrier);
           factory.commit();
        }
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  public void setupDevice(String carrierExternalID, String manufacturerExternalID, String modelExternalID, String deviceExternalID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        CarrierBean carrierBean = factory.createCarrierBean();
        ModelBean modelBean = factory.createModelBean();
        SubscriberBean subscriberBean = factory.createSubcriberBean();
        
        Carrier carrier = carrierBean.getCarrierByExternalID(carrierExternalID);
        assertNotNull(carrier);
  
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        assertNotNull(manufacturer);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelExternalID);
        assertNotNull(model);
  
        String externalID = "Zhao DongLu";
        String phoneNumber = "13801356729";
        String password = "123456!@#";
  
        List<Subscriber> list = subscriberBean.findSubscriber("from SubscriberEntity where externalId='" + externalID + "'");
        Subscriber subscriber = null;
        if (list.size() == 0) {
           subscriber = subscriberBean.newSubscriberInstance(carrier, externalID, phoneNumber, password);
  
           factory.beginTransaction();
           subscriberBean.update(subscriber);
           factory.commit();
        } else {
          subscriber = (Subscriber) list.get(0);
        }
        assertNotNull(subscriber);
  
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
        if (device == null) {
          // Create a DeviceEntity
          device = deviceBean.newDeviceInstance(subscriber, model, deviceExternalID);
          device.setOMAClientUsername(CLIENT_USERNAME);
          device.setOMAClientPassword(CLIENT_PASSWORD);
          device.setOMAServerPassword(serverPassword);
          factory.beginTransaction();
          deviceBean.update(device);
          factory.commit();
        }
  
        // Test found
        device = deviceBean.getDeviceByID("" + device.getID());
        assertNotNull(device);
  
        assertEquals(deviceExternalID, device.getExternalId());
  
    } catch (DMException e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  private void tearDownEntities() throws Exception {
    ManagementBeanFactory factory = null;
    try {
      
        factory = AllTests.getManagementBeanFactory();
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(Device_External_ID);
        if (device != null) {
          factory.beginTransaction();
          deviceBean.delete(device);
          factory.commit();
        }
        
        CarrierBean carrierBean = factory.createCarrierBean();
        Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID);
        if (carrier != null) {
           factory.beginTransaction();
           carrierBean.delete(carrier);
           factory.commit();
        }
        
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
        if (manufacturer != null) {
           factory.beginTransaction();
           modelBean.delete(manufacturer);
           factory.commit();
        }
    } catch (DMException e) {
      factory.rollback();
      e.printStackTrace();
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
    
  }
  

  public void testUpdateDeviceTreeNode() throws Exception {
    java.util.Map<String, String> results = new TreeMap<String, String>();
    
    results.put("./DevDetail/Bearer", "");
    results.put("./DevDetail/DevTyp", "smartphone");
    results.put("./DevDetail/DevTyp", "smartphone");
    results.put("./DevDetail/Ext", "");
    results.put("./DevDetail/Ext", "");
    results.put("./DevDetail/Ext/Symbian", "");
    results.put("./DevDetail/FwV", "592");
    results.put("./DevDetail/HwV", "1.0");
    results.put("./DevDetail/LrgObj", "true");
    results.put("./DevDetail/OEM", "SymbianUK");
    results.put("./DevDetail/SwV", "1.0");
    results.put("./DevDetail/URI", "");
    results.put("./DevDetail/URI/MaxDepth", "13");
    results.put("./DevDetail/URI/MaxSegLen", "32");
    results.put("./DevDetail/URI/MaxTotLen", "400");
    
    /*
    results.put("./DevDetail/Ext/Symbian/Ddf", 
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE MgmtTree PUBLIC \"-//OMA//DT" + 
        "D SYNCML-DMDDF 1.1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/OMA-Syn" + 
        "cML-DMDDFDTD-V1_1_2-20030508-D.dtd\" [<?oma-syncml-dmddf-ver supported-versions" + 
        "=\"1.1.2\"?>]><MgmtTree><VerDTD>1.1.2</VerDTD><Man>Symbian</Man><Mod>1234567890" + 
        "</Mod><Node><NodeName></NodeName><Path></Path><Node><NodeName>UIQ</NodeName><DF" + 
        "Properties><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><P" + 
        "ermanent/></Scope><Occurrence><One/></Occurrence></DFProperties><Node><NodeName" + 
        ">LBE</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><node/></" + 
        "DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence></DFProperti" + 
        "es><Node><NodeName>Apps</NodeName><DFProperties><AccessType><Get/></AccessType>" + 
        "<DFFormat><node/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occu" + 
        "rrence></DFProperties><Node><NodeName>AppLauncher</NodeName><DFProperties><Acce" + 
        "ssType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Permanent/></Scop" + 
        "e><Occurrence><One/></Occurrence></DFProperties><Node><NodeName>DefaultFolder</" + 
        "NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><int/" + 
        "></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFType><" + 
        "MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>GlobalLock" + 
        "s</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><i" + 
        "nt/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFTyp" + 
        "e><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>Display" + 
        "Type</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat" + 
        "><int/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DF" + 
        "Type><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>Fold" + 
        "ers</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><node/></D" + 
        "FFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence></DFPropertie" + 
        "s><Node><NodeName>Main</NodeName><DFProperties><AccessType><Get/></AccessType><" + 
        "DFFormat><node/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occur" + 
        "rence></DFProperties><Node><NodeName>Locks</NodeName><DFProperties><AccessType>" + 
        "<Get/><Replace/></AccessType><DFFormat><int/></DFFormat><Scope><Permanent/></Sc" + 
        "ope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DF" + 
        "Properties></Node><Node><NodeName>SortOrder</NodeName><DFProperties><AccessType" + 
        "><Get/><Replace/></AccessType><DFFormat><chr/></DFFormat><Scope><Permanent/></S" + 
        "cope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></D" + 
        "FProperties></Node></Node><Node><NodeName>Tools</NodeName><DFProperties><Access" + 
        "Type><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Permanent/></Scope>" + 
        "<Occurrence><One/></Occurrence></DFProperties><Node><NodeName>Locks</NodeName><" + 
        "DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><int/></DFForma" + 
        "t><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/" + 
        "plain</MIME></DFType></DFProperties></Node><Node><NodeName>SortOrder</NodeName>" + 
        "<DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><chr/></DFForm" + 
        "at><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text" + 
        "/plain</MIME></DFType></DFProperties></Node></Node></Node></Node></Node><Node><" + 
        "NodeName>System</NodeName><DFProperties><AccessType><Get/></AccessType><DFForma" + 
        "t><node/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><" + 
        "/DFProperties><Node><NodeName>Themes</NodeName><DFProperties><AccessType><Get/>" + 
        " </AccessType><DFFormat><node/></DFFormat><Scope><Permanent/></Scope><Occurrenc" + 
        "e><One/></Occurrence></DFProperties><Node><NodeName>Locks</NodeName><DFProperti" + 
        "es><AccessType><Get/><Replace/></AccessType><DFFormat><int/></DFFormat><Scope><" + 
        "Permanent/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIM" + 
        "E></DFType></DFProperties></Node></Node><Node><NodeName>DateTime</NodeName><DFP" + 
        "roperties><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Pe" + 
        "rmanent/></Scope><Occurrence><One/></Occurrence></DFProperties><Node><NodeName>" + 
        "NITZ</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat" + 
        "><int/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DF" + 
        "Type><MIME>text/plain</MIME></DFType></DFProperties></Node></Node></Node></Node" + 
        "><Node><NodeName>EDC</NodeName><DFProperties><AccessType><Get/></AccessType><DF" + 
        "Format><node/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurre" + 
        "nce></DFProperties><Node><NodeName/><DFProperties><AccessType><Add/><Delete/><G" + 
        "et/><Replace/></AccessType><DFFormat><node/></DFFormat><Scope><Dynamic/></Scope" + 
        "><Occurrence><ZeroOrMore/></Occurrence></DFProperties><Node><NodeName/><DFPrope" + 
        "rties><AccessType><Add/><Delete/><Get/><Replace/></AccessType><DFFormat><node/>" + 
        "</DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrMore/></Occurrence></DFP" + 
        "roperties><Node><NodeName/><DFProperties><AccessType><Add/><Delete/><Get/><Repl" + 
        "ace/></AccessType><DFFormat><node/></DFFormat><Scope><Dynamic/></Scope><Occurre" + 
        "nce><ZeroOrMore/></Occurrence></DFProperties><Node><NodeName>Type</NodeName><DF" + 
        "Properties><AccessType><Add/><Delete/><Get/><Replace/></AccessType><DFFormat><i" + 
        "nt/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence><DFType>" + 
        "<MIME>text/plain</MIME></DFType></DFProperties></Node><Node> <NodeName>Priority" + 
        "</NodeName><DFProperties><AccessType><Add/><Delete/><Get/><Replace/></AccessTyp" + 
        "e><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occur" + 
        "rence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><Node" + 
        "Name>Recipient</NodeName><DFProperties><AccessType><Add/><Delete/><Get/><Replac" + 
        "e/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence" + 
        "><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></No" + 
        "de><Node><NodeName>CommandIcon</NodeName><DFProperties><AccessType><Add/><Delet" + 
        "e/><Get/><Replace/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></S" + 
        "cope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></D" + 
        "FProperties></Node><Node><NodeName>CommandIconId</NodeName><DFProperties><Acces" + 
        "sType><Add/><Delete/><Get/><Replace/></AccessType><DFFormat><int/></DFFormat><S" + 
        "cope><Dynamic/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain<" + 
        "/MIME></DFType></DFProperties></Node><Node><NodeName>CommandIconMask</NodeName>" + 
        "<DFProperties><AccessType><Add/><Delete/><Get/><Replace/></AccessType><DFFormat" + 
        "><int/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence><DFTy" + 
        "pe><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>Comman" + 
        "dType</NodeName><DFProperties><AccessType><Add/><Delete/><Get/><Replace/></Acce" + 
        "ssType><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></" + 
        "Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node>" + 
        "<NodeName>CommandGroupId</NodeName><DFProperties><AccessType><Add/><Delete/><Ge" + 
        "t/><Replace/></AccessType><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><" + 
        "Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFPrope" + 
        "rties></Node><Node><NodeName>CommandNamedGroupLinkId</NodeName><DFProperties> <" + 
        "AccessType><Add/><Delete/><Get/><Replace/></AccessType><DFFormat><int/></DFForm" + 
        "at><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/p" + 
        "lain</MIME></DFType></DFProperties></Node><Node><NodeName>CommandNamedGroupId</" + 
        "NodeName><DFProperties><AccessType><Add/><Delete/><Get/><Replace/></AccessType>" + 
        "<DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurre" + 
        "nce><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeNa" + 
        "me>CommandStateFlags</NodeName><DFProperties><AccessType><Add/><Delete/><Get/><" + 
        "Replace/></AccessType><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><Occu" + 
        "rrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFPropertie" + 
        "s></Node><Node><NodeName>CommandCpfFlags</NodeName><DFProperties><AccessType><A" + 
        "dd/><Delete/><Get/><Replace/></AccessType><DFFormat><int/></DFFormat><Scope><Dy" + 
        "namic/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></" + 
        "DFType></DFProperties></Node><Node><NodeName>LongLabel</NodeName><DFProperties>" + 
        "<AccessType><Add/><Delete/><Get/><Replace/></AccessType><DFFormat><chr/></DFFor" + 
        "mat><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/" + 
        "plain</MIME></DFType></DFProperties></Node><Node><NodeName>ShortLabel</NodeName" + 
        "><DFProperties><AccessType><Add/><Delete/><Get/><Replace/></AccessType><DFForma" + 
        "t><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence><DFT" + 
        "ype><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>Body<" + 
        "/NodeName><DFProperties><AccessType><Add/><Delete/><Get/><Replace/></AccessType" + 
        "><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurr" + 
        "ence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeN" + 
        "ame>Subject</NodeName><DFProperties><AccessType><Add/><Delete/><Get/><Replace/>" + 
        "</AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><O" + 
        "ne/></Occurrence><DFType><MIME>text/plain</MIME></DFType> </DFProperties></Node" + 
        "></Node></Node></Node></Node><Node><NodeName>BBE</NodeName><DFProperties><Acces" + 
        "sType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Permanent/></Scope" + 
        "><Occurrence><One/></Occurrence></DFProperties><Node><NodeName>Apps</NodeName><" + 
        "DFProperties><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope>" + 
        "<Permanent/></Scope><Occurrence><One/></Occurrence></DFProperties><Node><NodeNa" + 
        "me>Agenda</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><nod" + 
        "e/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence></DFPro" + 
        "perties><Node><NodeName>Icon</NodeName><DFProperties><AccessType><Get/></Access" + 
        "Type><DFFormat><node/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/><" + 
        "/Occurrence></DFProperties><Node><NodeName>Application</NodeName><DFProperties>" + 
        "<AccessType><Get/><Replace/></AccessType><DFFormat><bin/></DFFormat><Scope><Per" + 
        "manent/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME><" + 
        "/DFType></DFProperties></Node></Node></Node><Node><NodeName>AppLauncher</NodeNa" + 
        "me><DFProperties><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Sc" + 
        "ope><Permanent/></Scope><Occurrence><One/></Occurrence></DFProperties><Node><No" + 
        "deName>Icon</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><n" + 
        "ode/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence></DFP" + 
        "roperties><Node><NodeName>Application</NodeName><DFProperties><AccessType><Get/" + 
        "><Replace/></AccessType><DFFormat><bin/></DFFormat><Scope><Permanent/></Scope><" + 
        "Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFPrope" + 
        "rties></Node><Node><NodeName>ToolsFolder</NodeName><DFProperties><AccessType><G" + 
        "et/><Replace/></AccessType><DFFormat><bin/></DFFormat><Scope><Permanent/></Scop" + 
        "e><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFPr" + 
        "operties></Node></Node></Node><Node><NodeName>AppInstaller</NodeName><DFPropert" + 
        "ies><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Permanen" + 
        "t/></Scope><Occurrence><One/></Occurrence></DFProperties><Node><NodeName>Animat" + 
        "ion</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><node/></D" + 
        "FFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence></DFPropertie" + 
        "s><Node><NodeName>SecurityChecking</NodeName><DFProperties><AccessType><Get/><R" + 
        "eplace/></AccessType><DFFormat><bin/></DFFormat><Scope><Permanent/></Scope><Occ" + 
        "urrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperti" + 
        "es></Node></Node> </Node><Node><NodeName>Beaming</NodeName><DFProperties><Acces" + 
        "sType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Permanent/></Scope" + 
        "><Occurrence><One/></Occurrence></DFProperties><Node><NodeName>Animation</NodeN" + 
        "ame><DFProperties><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><S" + 
        "cope><Permanent/></Scope><Occurrence><One/></Occurrence></DFProperties><Node><N" + 
        "odeName>BTSend</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType" + 
        "><DFFormat><bin/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occu" + 
        "rrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><Nod" + 
        "eName>Receive</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType>" + 
        "<DFFormat><bin/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occur" + 
        "rence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><Node" + 
        "Name>BTError</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><" + 
        "DFFormat><bin/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurr" + 
        "ence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeN" + 
        "ame>BTSearch</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><" + 
        "DFFormat><bin/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurr" + 
        "ence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeN" + 
        "ame>IRError</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><D" + 
        "FFormat><bin/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurre" + 
        "nce><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeNa" + 
        "me>IRSearch</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><D" + 
        "FFormat><bin/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurre" + 
        "nce><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeNa" + 
        "me>IRSend</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><DFF" + 
        "ormat><bin/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrenc" + 
        "e><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node></Node></Node><" + 
        "Node><NodeName>Calc</NodeName><DFProperties><AccessType><Get/></AccessType><DFF" + 
        "ormat><node/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurren" + 
        "ce></DFProperties><Node><NodeName>Icon</NodeName><DFProperties><AccessType><Get" + 
        "/></AccessType><DFFormat><node/></DFFormat><Scope><Permanent/></Scope><Occurren" + 
        "ce><One/></Occurrence></DFProperties><Node><NodeName>Application</NodeName><DFP" + 
        "roperties><AccessType><Get/><Replace/></AccessType><DFFormat><bin/></DFFormat><" + 
        "Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/pla" + 
        "in</MIME></DFType></DFProperties></Node></Node></Node><Node><NodeName>Connectio" + 
        "ns</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><node/></DF" + 
        "Format><Scope><Permanent/></Scope><Occurrence><One/></Occurrence></DFProperties" + 
        "><Node><NodeName>Icon</NodeName><DFProperties><AccessType><Get/></AccessType><D" + 
        "FFormat><node/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurr" + 
        "ence></DFProperties><Node><NodeName>Application</NodeName><DFProperties><Access" + 
        "Type><Get/><Replace/></AccessType><DFFormat><bin/></DFFormat><Scope><Permanent/" + 
        "></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType" + 
        "></DFProperties></Node></Node></Node><Node><NodeName>Contacts</NodeName><DFProp" + 
        "erties><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Perma" + 
        "nent/></Scope><Occurrence><One/></Occurrence></DFProperties> <Node><NodeName>Ic" + 
        "on</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><node/></DF" + 
        "Format><Scope><Permanent/></Scope><Occurrence><One/></Occurrence></DFProperties" + 
        "><Node><NodeName>Application</NodeName><DFProperties><AccessType><Get/><Replace" + 
        "/></AccessType><DFFormat><bin/></DFFormat><Scope><Permanent/></Scope><Occurrenc" + 
        "e><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></N" + 
        "ode></Node><Node><NodeName>Animation</NodeName><DFProperties><AccessType><Get/>" + 
        "</AccessType><DFFormat><node/></DFFormat><Scope><Permanent/></Scope><Occurrence" + 
        "><One/></Occurrence></DFProperties><Node><NodeName>Copy</NodeName><DFProperties" + 
        "><AccessType><Get/><Replace/></AccessType><DFFormat><bin/></DFFormat><Scope><Pe" + 
        "rmanent/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME>" + 
        "</DFType></DFProperties></Node><Node><NodeName>Delete</NodeName><DFProperties><" + 
        "AccessType><Get/><Replace/></AccessType><DFFormat><bin/></DFFormat><Scope><Perm" + 
        "anent/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></" + 
        "DFType></DFProperties></Node><Node><NodeName>Move</NodeName><DFProperties><Acce" + 
        "ssType><Get/><Replace/></AccessType><DFFormat><bin/></DFFormat><Scope><Permanen" + 
        "t/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFTy" + 
        "pe></DFProperties></Node></Node></Node><Node><NodeName>ControlPanel</NodeName><" + 
        "DFProperties><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope>" + 
        "<Permanent/></Scope><Occurrence><One/></Occurrence></DFProperties><Node><NodeNa" + 
        "me>Icon</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><node/" + 
        "></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence></DFPrope" + 
        "rties><Node><NodeName>Application</NodeName><DFProperties><AccessType><Get/><Re" + 
        "place/> </AccessType><DFFormat><bin/></DFFormat><Scope><Permanent/></Scope><Occ" + 
        "urrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperti" + 
        "es></Node></Node></Node><Node><NodeName>DeviceManagement</NodeName><DFPropertie" + 
        "s><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Permanent/" + 
        "></Scope><Occurrence><One/></Occurrence></DFProperties><Node><NodeName>Animatio" + 
        "n</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><node/></DFF" + 
        "ormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence></DFProperties>" + 
        "<Node><NodeName>StartSession</NodeName><DFProperties><AccessType><Get/><Replace" + 
        "/></AccessType><DFFormat><bin/></DFFormat><Scope><Permanent/></Scope><Occurrenc" + 
        "e><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></N" + 
        "ode></Node></Node><Node><NodeName>DownloadAgent</NodeName><DFProperties><Access" + 
        "Type><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Permanent/></Scope>" + 
        "<Occurrence><One/></Occurrence></DFProperties><Node><NodeName>Icon</NodeName><D" + 
        "FProperties><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><" + 
        "Permanent/></Scope><Occurrence><One/></Occurrence></DFProperties><Node><NodeNam" + 
        "e>Application</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType>" + 
        "<DFFormat><bin/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occur" + 
        "rence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node></Node></No" + 
        "de><Node><NodeName>FileManager</NodeName><DFProperties><AccessType><Get/></Acce" + 
        "ssType><DFFormat><node/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/" + 
        "></Occurrence></DFProperties><Node><NodeName>Icon</NodeName><DFProperties><Acce" + 
        "ssType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Permanent/></Scop" + 
        "e><Occurrence><One/></Occurrence></DFProperties><Node><NodeName>Application</No" + 
        "deName><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><bin/><" + 
        "/DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFType><MI" + 
        "ME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>CurrentFolde" + 
        "r</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><b" + 
        "in/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFTyp" + 
        "e><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>LevelUp" + 
        "Folder</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><DFForm" + 
        "at><bin/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><" + 
        "DFType><MIME>text/plain</MIME> </DFType></DFProperties></Node></Node></Node><No" + 
        "de><NodeName>Jotter</NodeName><DFProperties><AccessType><Get/></AccessType><DFF" + 
        "ormat><node/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurren" + 
        "ce></DFProperties><Node><NodeName>Icon</NodeName><DFProperties><AccessType><Get" + 
        "/></AccessType><DFFormat><node/></DFFormat><Scope><Permanent/></Scope><Occurren" + 
        "ce><One/></Occurrence></DFProperties><Node><NodeName>Application</NodeName><DFP" + 
        "roperties><AccessType><Get/><Replace/></AccessType><DFFormat><bin/></DFFormat><" + 
        "Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/pla" + 
        "in</MIME></DFType></DFProperties></Node></Node></Node><Node><NodeName>Messaging" + 
        "</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><node/></DFFo" + 
        "rmat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence></DFProperties><" + 
        "Node><NodeName>Icon</NodeName><DFProperties><AccessType><Get/></AccessType><DFF" + 
        "ormat><node/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurren" + 
        "ce></DFProperties><Node><NodeName>Application</NodeName><DFProperties><AccessTy" + 
        "pe><Get/><Replace/></AccessType><DFFormat><bin/></DFFormat><Scope><Permanent/><" + 
        "/Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType><" + 
        "/DFProperties></Node></Node><Node><NodeName>Animation</NodeName><DFProperties><" + 
        "AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Permanent/></" + 
        "Scope><Occurrence><One/></Occurrence></DFProperties><Node><NodeName>Copy</NodeN" + 
        "ame><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><bin/></DF" + 
        "Format><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFType><MIME>" + 
        "text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>Delete</NodeNam" + 
        "e><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><bin/></DFFo" + 
        "rmat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFType><MIME>te" + 
        "xt/plain</MIME></DFType></DFProperties></Node><Node><NodeName>Move</NodeName><D" + 
        "FProperties><AccessType><Get/><Replace/></AccessType><DFFormat><bin/></DFFormat" + 
        "><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/p" + 
        "lain</MIME></DFType></DFProperties></Node></Node></Node><Node><NodeName>Phone</" + 
        "NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><node/></DFForm" + 
        "at><Scope><Permanent/></Scope><Occurrence><One/></Occurrence></DFProperties><No" + 
        "de><NodeName>Animation</NodeName><DFProperties><AccessType><Get/></AccessType><" + 
        "DFFormat><node/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occur" + 
        "rence></DFProperties><Node><NodeName>Hold</NodeName><DFProperties><AccessType><" + 
        "Get/><Replace/></AccessType><DFFormat><bin/></DFFormat><Scope><Permanent/></Sco" + 
        "pe><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFP" + 
        "roperties></Node><Node><NodeName>Retrieve</NodeName><DFProperties><AccessType><" + 
        "Get/><Replace/></AccessType><DFFormat><bin/></DFFormat><Scope><Permanent/></Sco" + 
        "pe><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFP" + 
        "roperties></Node><Node><NodeName>Switch</NodeName><DFProperties><AccessType><Ge" + 
        "t/><Replace/></AccessType><DFFormat><bin/></DFFormat><Scope><Permanent/></Scope" + 
        "><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFPro" + 
        "perties></Node></Node></Node><Node><NodeName>Time</NodeName><DFProperties><Acce" + 
        "ssType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Permanent/></Scop" + 
        "e><Occurrence><One/></Occurrence></DFProperties><Node><NodeName>Icon</NodeName>" + 
        " <DFProperties><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scop" + 
        "e><Permanent/></Scope><Occurrence><One/></Occurrence></DFProperties><Node><Node" + 
        "Name>Application</NodeName><DFProperties><AccessType><Get/><Replace/></AccessTy" + 
        "pe><DFFormat><bin/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Oc" + 
        "currence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node></Node><" + 
        "/Node><Node><NodeName>Todo</NodeName><DFProperties><AccessType><Get/></AccessTy" + 
        "pe><DFFormat><node/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></O" + 
        "ccurrence></DFProperties><Node><NodeName>Icon</NodeName><DFProperties><AccessTy" + 
        "pe><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Permanent/></Scope><O" + 
        "ccurrence><One/></Occurrence></DFProperties><Node><NodeName>Application</NodeNa" + 
        "me><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><bin/></DFF" + 
        "ormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFType><MIME>t" + 
        "ext/plain</MIME></DFType></DFProperties></Node></Node></Node><Node><NodeName>Vo" + 
        "ice</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><node/></D" + 
        "FFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence></DFPropertie" + 
        "s><Node><NodeName>Icon</NodeName><DFProperties><AccessType><Get/></AccessType><" + 
        "DFFormat><node/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occur" + 
        "rence></DFProperties><Node><NodeName>Application</NodeName><DFProperties><Acces" + 
        "sType><Get/><Replace/></AccessType><DFFormat><bin/></DFFormat><Scope><Permanent" + 
        "/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFTyp" + 
        "e></DFProperties></Node></Node></Node><Node><NodeName>Web</NodeName><DFProperti" + 
        "es><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Permanent" + 
        "/></Scope><Occurrence><One/></Occurrence></DFProperties><Node><NodeName>Icon</N" + 
        "odeName><DFProperties><AccessType><Get/></AccessType><DFFormat><node/></DFForma" + 
        "t><Scope><Permanent/></Scope><Occurrence><One/></Occurrence></DFProperties><Nod" + 
        "e><NodeName>Application</NodeName><DFProperties><AccessType><Get/><Replace/></A" + 
        "ccessType><DFFormat><bin/></DFFormat><Scope><Permanent/></Scope><Occurrence><On" + 
        "e/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><" + 
        "/Node></Node></Node><Node><NodeName>System</NodeName><DFProperties><AccessType>" + 
        "<Get/></AccessType><DFFormat><node/></DFFormat><Scope><Permanent/></Scope><Occu" + 
        "rrence><One/></Occurrence></DFProperties><Node><NodeName>Themes</NodeName><DFPr" + 
        "operties><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Per" + 
        "manent/></Scope><Occurrence><One/></Occurrence></DFProperties><Node><NodeName>D" + 
        "efault</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><DFForm" + 
        "at><bin/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><" + 
        "DFType><MIME>text/plain</MIME></DFType></DFProperties></Node></Node><Node><Node" + 
        "Name>AlertDialog</NodeName><DFProperties> <AccessType><Get/></AccessType><DFFor" + 
        "mat><node/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence" + 
        "></DFProperties><Node><NodeName>Animation</NodeName><DFProperties><AccessType><" + 
        "Get/><Replace/></AccessType><DFFormat><bin/></DFFormat><Scope><Permanent/></Sco" + 
        "pe><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFP" + 
        "roperties></Node></Node><Node><NodeName>InfoDialog</NodeName><DFProperties><Acc" + 
        "essType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Permanent/></Sco" + 
        "pe><Occurrence><One/></Occurrence></DFProperties><Node><NodeName>Animation</Nod" + 
        "eName><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><bin/></" + 
        "DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFType><MIM" + 
        "E>text/plain</MIME></DFType></DFProperties></Node></Node><Node><NodeName>QueryD" + 
        "ialog</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><node/><" + 
        "/DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence></DFPropert" + 
        "ies><Node><NodeName>Animation</NodeName><DFProperties><AccessType><Get/><Replac" + 
        "e/></AccessType><DFFormat><bin/></DFFormat><Scope><Permanent/></Scope><Occurren" + 
        "ce><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></" + 
        "Node></Node><Node><NodeName>Icon</NodeName><DFProperties><AccessType><Get/></Ac" + 
        "cessType><DFFormat><node/></DFFormat><Scope><Permanent/></Scope><Occurrence><On" + 
        "e/></Occurrence></DFProperties><Node><NodeName>BrokenImage</NodeName><DFPropert" + 
        "ies><AccessType><Get/><Replace/></AccessType><DFFormat><bin/></DFFormat><Scope>" + 
        "<Permanent/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MI" + 
        "ME></DFType></DFProperties></Node><Node><NodeName>CameraFolder</NodeName><DFPro" + 
        "perties><AccessType><Get/><Replace/></AccessType><DFFormat><bin/></DFFormat> <S" + 
        "cope><Permanent/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plai" + 
        "n</MIME></DFType></DFProperties></Node><Node><NodeName>ExternalMemory</NodeName" + 
        "><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><bin/></DFFor" + 
        "mat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFType><MIME>tex" + 
        "t/plain</MIME></DFType></DFProperties></Node><Node><NodeName>GenericFolder</Nod" + 
        "eName><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><bin/></" + 
        "DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFType><MIM" + 
        "E>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>InternalMemor" + 
        "y</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><b" + 
        "in/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFTyp" + 
        "e><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>Default" + 
        "Folder</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><DFForm" + 
        "at><bin/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><" + 
        "DFType><MIME>text/plain</MIME></DFType></DFProperties></Node></Node></Node></No" + 
        "de></Node><Node><NodeName>Browser</NodeName><DFProperties><AccessType><Get/></A" + 
        "ccessType><DFFormat><node/></DFFormat><Scope><Permanent/></Scope><Occurrence><O" + 
        "ne/></Occurrence><DFType><DDFName>com.symbian/1.0/Browser</DDFName></DFType></D" + 
        "FProperties><Node><NodeName>AppID</NodeName><DFProperties><AccessType><Get/></A" + 
        "ccessType><DFFormat><chr/></DFFormat><Scope><Permanent/></Scope><Occurrence><On" + 
        "e/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><" + 
        "Node><NodeName>ProviderID</NodeName><DFProperties><AccessType><Get/></AccessTyp" + 
        "e><DFFormat><chr/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occ" + 
        "urrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><No" + 
        "deName>Name</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><c" + 
        "hr/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFTyp" + 
        "e><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>PrefCon" + 
        "Ref</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat>" + 
        "<chr/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFT" + 
        "ype><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>Searc" + 
        "hPage</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><DFForma" + 
        "t><chr/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><D" + 
        "FType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>Hom" + 
        "ePage</NodeName><DFProperties><AccessType><Get/><Replace/> </AccessType><DFForm" + 
        "at><chr/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><" + 
        "DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>Re" + 
        "source</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><node/>" + 
        "</DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFType><D" + 
        "DFName></DDFName></DFType></DFProperties><Node><NodeName/><DFProperties><Access" + 
        "Type><Add/><Delete/><Get/><Replace/></AccessType><DFFormat><node/></DFFormat><S" + 
        "cope><Dynamic/></Scope><Occurrence><ZeroOrMore/></Occurrence></DFProperties><No" + 
        "de><NodeName>URI</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></Ac" + 
        "cessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/>" + 
        "</Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Nod" + 
        "e><NodeName>Name</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></Ac" + 
        "cessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/>" + 
        "</Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Nod" + 
        "e><NodeName>AAuthType</NodeName><DFProperties><AccessType><Add/><Get/><Replace/" + 
        "></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><" + 
        "ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties>" + 
        "</Node><Node><NodeName>AAuthName</NodeName><DFProperties><AccessType><Add/><Get" + 
        "/><Replace/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><O" + 
        "ccurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DF" + 
        "Properties></Node><Node><NodeName>AAuthSecret</NodeName><DFProperties><AccessTy" + 
        "pe><Add/><Get/><Replace/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynami" + 
        "c/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME>" + 
        "</DFType> </DFProperties></Node><Node><NodeName>ToBmFolder</NodeName><DFPropert" + 
        "ies><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><chr/></DFFormat><" + 
        "Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text" + 
        "/plain</MIME></DFType></DFProperties></Node><Node><NodeName>LockBm</NodeName><D" + 
        "FProperties><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><bool/></D" + 
        "FFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><" + 
        "MIME>text/plain</MIME></DFType></DFProperties></Node></Node></Node><Node><NodeN" + 
        "ame>BmFolder</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><" + 
        "node/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFT" + 
        "ype><DDFName></DDFName></DFType></DFProperties><Node><NodeName/><DFProperties><" + 
        "AccessType><Add/><Delete/><Get/><Replace/></AccessType><DFFormat><node/></DFFor" + 
        "mat><Scope><Dynamic/></Scope><Occurrence><ZeroOrMore/></Occurrence></DFProperti" + 
        "es><Node><NodeName>Name</NodeName><DFProperties><AccessType><Add/><Get/><Replac" + 
        "e/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence" + 
        "><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></No" + 
        "de><Node><NodeName>FolderPath</NodeName><DFProperties><AccessType><Add/><Get/><" + 
        "Replace/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occu" + 
        "rrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFPropertie" + 
        "s></Node><Node><NodeName>LockFolder</NodeName><DFProperties><AccessType><Add/><" + 
        "Get/><Replace/></AccessType><DFFormat><bool/></DFFormat><Scope><Dynamic/></Scop" + 
        "e><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFType>" + 
        "</DFProperties></Node></Node></Node></Node><Node><NodeName>Proxy</NodeName><DFP" + 
        "roperties><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Pe" + 
        "rmanent/></Scope><Occurrence><One/></Occurrence><DFType><DDFName>com.symbian/1." + 
        "0/Proxy</DDFName></DFType></DFProperties><Node> <NodeName/><DFProperties><Acces" + 
        "sType><Add/><Copy/><Delete/><Get/><Replace/></AccessType><DFFormat><node/></DFF" + 
        "ormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrMore/></Occurrence></DFProper" + 
        "ties><Node><NodeName>ProxyId</NodeName><DFProperties><AccessType><Get/></Access" + 
        "Type><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Oc" + 
        "currence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><N" + 
        "odeName>Name</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><" + 
        "DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurren" + 
        "ce><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeNam" + 
        "e>AddrType</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><DF" + 
        "Format><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occu" + 
        "rrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><Nod" + 
        "eName>Addr</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><DF" + 
        "Format><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence" + 
        "><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>" + 
        "Ports</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><node/><" + 
        "/DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType" + 
        "><DDFName></DDFName></DFType></DFProperties><Node><NodeName/><DFProperties><Acc" + 
        "essType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Dynamic/></Scope" + 
        "><Occurrence><OneOrMore/></Occurrence></DFProperties><Node><NodeName>PortNbr</N" + 
        "odeName><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><int/>" + 
        "</DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence><DFType><MIM" + 
        "E>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>Services </No" + 
        "deName><DFProperties><AccessType><Get/></AccessType><DFFormat><node/></DFFormat" + 
        "><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><DDFName" + 
        "></DDFName></DFType></DFProperties><Node><NodeName/><DFProperties><AccessType><" + 
        "Get/></AccessType><DFFormat><node/></DFFormat><Scope><Dynamic/></Scope><Occurre" + 
        "nce><OneOrMore/></Occurrence></DFProperties><Node><NodeName>ServiceName</NodeNa" + 
        "me><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><chr/></DFF" + 
        "ormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence><DFType><MIME>tex" + 
        "t/plain</MIME></DFType></DFProperties></Node></Node></Node></Node></Node><Node>" + 
        "<NodeName>ConRefs</NodeName><DFProperties><AccessType><Get/></AccessType><DFFor" + 
        "mat><node/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence><" + 
        "DFType><DDFName></DDFName></DFType></DFProperties><Node><NodeName/><DFPropertie" + 
        "s><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Dynamic/><" + 
        "/Scope><Occurrence><OneOrMore/></Occurrence></DFProperties><Node><NodeName>ConR" + 
        "ef</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><" + 
        "chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence><DFType" + 
        "><MIME>text/plain</MIME></DFType></DFProperties></Node></Node></Node><Node><Nod" + 
        "eName>AuthInfo</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat" + 
        "><node/></DFFormat><Scope><Dynamic/></Scope><Occurrence><OneOrMore/></Occurrenc" + 
        "e><DFType><DDFName></DDFName></DFType></DFProperties><Node><NodeName/><DFProper" + 
        "ties><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Dynamic" + 
        "/></Scope><Occurrence><OneOrMore/></Occurrence></DFProperties><Node><NodeName>A" + 
        "uthPW</NodeName><DFProperties><AccessType><Replace/></AccessType><DFFormat><chr" + 
        "/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFT" + 
        "ype><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>AuthI" + 
        "D</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><c" + 
        "hr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><D" + 
        "FType><MIME>text/plain</MIME></DFType></DFProperties></Node></Node></Node><Node" + 
        "><NodeName>Domains</NodeName><DFProperties><AccessType><Get/></AccessType><DFFo" + 
        "rmat><node/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occur" + 
        "rence><DFType><DDFName></DDFName></DFType></DFProperties><Node><NodeName/><DFPr" + 
        "operties><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Dyn" + 
        "amic/></Scope><Occurrence><OneOrMore/></Occurrence></DFProperties><Node><NodeNa" + 
        "me>DomainName</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType>" + 
        "<DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></O" + 
        "ccurrence><DFType><MIME>text/plain</MIME></DFType> </DFProperties></Node></Node" + 
        "></Node><Node><NodeName>ProxyParams</NodeName><DFProperties><AccessType><Get/><" + 
        "/AccessType><DFFormat><node/></DFFormat><Scope><Dynamic/></Scope><Occurrence><Z" + 
        "eroOrOne/></Occurrence><DFType><DDFName></DDFName></DFType></DFProperties><Node" + 
        "><NodeName>WAP</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat" + 
        "><node/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrenc" + 
        "e><DFType><DDFName></DDFName></DFType></DFProperties><Node><NodeName>WSPVersion" + 
        "</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><chr/></DFFor" + 
        "mat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME" + 
        ">text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>Startpg</NodeN" + 
        "ame><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><chr/></DF" + 
        "Format><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><M" + 
        "IME>text/plain</MIME></DFType></DFProperties></Node></Node></Node></Node></Node" + 
        "><Node><NodeName>SyncML</NodeName><DFProperties><AccessType><Get/></AccessType>" + 
        "<DFFormat><node/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occu" + 
        "rrence><DFType></DFType></DFProperties><Node><NodeName>DMAcc</NodeName><DFPrope" + 
        "rties><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Perman" + 
        "ent/></Scope><Occurrence><One/></Occurrence><DFType><DDFName>org.openmobilealli" + 
        "ance/1.0/DMAcc</DDFName></DFType></DFProperties><Node><NodeName/><DFProperties>" + 
        "<AccessType><Add/><Delete/><Get/></AccessType><DFFormat><node/></DFFormat><Scop" + 
        "e><Dynamic/></Scope><Occurrence><ZeroOrMore/></Occurrence></DFProperties><Node>" + 
        "<NodeName>Addr</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></Acce" + 
        "ssType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/> <" + 
        "/Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node" + 
        "><NodeName>AddrType</NodeName><DFProperties><AccessType><Add/><Get/><Replace/><" + 
        "/AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><On" + 
        "e/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><" + 
        "Node><NodeName>PortNbr</NodeName><DFProperties><AccessType><Add/><Get/><Replace" + 
        "/></AccessType><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><Occurrence>" + 
        "<One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Nod" + 
        "e><Node><NodeName>ConRef</NodeName><DFProperties><AccessType><Add/><Get/><Repla" + 
        "ce/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrenc" + 
        "e><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></N" + 
        "ode><Node><NodeName>ServerId</NodeName><DFProperties><AccessType><Add/><Get/></" + 
        "AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One" + 
        "/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><N" + 
        "ode><NodeName>ServerPW</NodeName><DFProperties><AccessType><Add/><Replace/></Ac" + 
        "cessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/>" + 
        "</Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Nod" + 
        "e><NodeName>ServerNonce</NodeName><DFProperties><AccessType><Add/><Replace/></A" + 
        "ccessType><DFFormat><bin/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/" + 
        "></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><No" + 
        "de><NodeName>UserName</NodeName><DFProperties><AccessType><Add/><Get/></AccessT" + 
        "ype><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occ" + 
        "urrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><No" + 
        "deName>ClientPW</NodeName><DFProperties><AccessType><Add/><Replace/></AccessTyp" + 
        "e><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occur" + 
        "rence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><Node" + 
        "Name>ClientNonce</NodeName><DFProperties><AccessType><Add/><Replace/></AccessTy" + 
        "pe><DFFormat><bin/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occu" + 
        "rrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><Nod" + 
        "eName>AuthPref</NodeName><DFProperties><AccessType><Add/><Delete/><Get/><Replac" + 
        "e/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence" + 
        "><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFPropertie" + 
        "s></Node><Node><NodeName>Name</NodeName><DFProperties><AccessType><Add/><Delete" + 
        "/><Get/><Replace/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Sc" + 
        "ope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFTyp" + 
        "e></DFProperties></Node></Node></Node><Node><NodeName>DSAcc</NodeName><DFProper" + 
        "ties><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Permane" + 
        "nt/></Scope><Occurrence><One/></Occurrence><DFType><DDFName>com.symbian/1.0/DSA" + 
        "cc</DDFName></DFType></DFProperties><Node><NodeName/><DFProperties><AccessType>" + 
        "<Add/><Delete/> <Get/><Replace/></AccessType><DFFormat><node/></DFFormat><Scope" + 
        "><Dynamic/></Scope><Occurrence><ZeroOrMore/></Occurrence></DFProperties><Node><" + 
        "NodeName>AppID</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat" + 
        "><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence><DFTy" + 
        "pe><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>Name</" + 
        "NodeName><DFProperties><AccessType><Add/><Get/><Replace/></AccessType><DFFormat" + 
        "><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence><DFTy" + 
        "pe><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>ToConR" + 
        "ef</NodeName><DFProperties><AccessType><Add/><Get/></AccessType><DFFormat><node" + 
        "/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence><DFType><D" + 
        "DFName></DDFName></DFType></DFProperties><Node><NodeName/><DFProperties><Access" + 
        "Type><Add/><Get/><Replace/></AccessType><DFFormat><node/></DFFormat><Scope><Dyn" + 
        "amic/></Scope><Occurrence><ZeroOrMore/></Occurrence></DFProperties><Node><NodeN" + 
        "ame>ConRef</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></AccessTy" + 
        "pe><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occu" + 
        "rrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node></Node></N" + 
        "ode><Node><NodeName>AppAddr</NodeName><DFProperties><AccessType><Add/><Get/></A" + 
        "ccessType><DFFormat><node/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One" + 
        "/></Occurrence><DFType><DDFName></DDFName></DFType></DFProperties><Node><NodeNa" + 
        "me/><DFProperties><AccessType><Add/><Delete/><Get/><Replace/></AccessType><DFFo" + 
        "rmat><node/></DFFormat><Scope><Dynamic/></Scope><Occurrence><OneOrMore/></Occur" + 
        "rence></DFProperties><Node><NodeName>Addr</NodeName><DFProperties><AccessType><" + 
        "Add/><Get/><Replace/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/><" + 
        "/Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType><" + 
        "/DFProperties></Node><Node><NodeName>AddrType</NodeName><DFProperties><AccessTy" + 
        "pe><Add/><Get/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope" + 
        "><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFPro" + 
        "perties></Node><Node><NodeName>PortNbr</NodeName><DFProperties><AccessType><Add" + 
        "/><Delete/><Get/><Replace/></AccessType><DFFormat><int/></DFFormat><Scope><Dyna" + 
        "mic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIM" + 
        "E></DFType></DFProperties></Node></Node></Node><Node><NodeName>AppAuth</NodeNam" + 
        "e><DFProperties><AccessType><Add/><Get/></AccessType><DFFormat><node/></DFForma" + 
        "t><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence><DFType><DDFName></DD" + 
        "FName></DFType></DFProperties><Node> <NodeName/><DFProperties><AccessType><Add/" + 
        "><Get/><Replace/></AccessType><DFFormat><node/></DFFormat><Scope><Dynamic/></Sc" + 
        "ope><Occurrence><ZeroOrMore/></Occurrence></DFProperties><Node><NodeName>AAuthL" + 
        "evel</NodeName><DFProperties><AccessType><Add/><Get/></AccessType><DFFormat><ch" + 
        "r/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence><DFType><" + 
        "MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>AAuthType<" + 
        "/NodeName><DFProperties><AccessType><Add/><Get/><Replace/></AccessType><DFForma" + 
        "t><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence><DFT" + 
        "ype><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>AAuth" + 
        "Name</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></AccessType><DF" + 
        "Format><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence" + 
        "><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>" + 
        "AAuthSecret</NodeName><DFProperties><AccessType><Add/><Replace/></AccessType><D" + 
        "FFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurrenc" + 
        "e><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName" + 
        ">AAuthData</NodeName><DFProperties><AccessType><Add/><Replace/></AccessType><DF" + 
        "Format><bin/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence" + 
        "><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node></Node></Node><N" + 
        "ode><NodeName>Resource</NodeName><DFProperties><AccessType><Add/><Get/></Access" + 
        "Type><DFFormat><node/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></O" + 
        "ccurrence><DFType><DDFName></DDFName></DFType></DFProperties><Node><NodeName/><" + 
        "DFProperties><AccessType><Add/><Delete/><Get/><Replace/></AccessType><DFFormat>" + 
        "<node/></DFFormat><Scope><Dynamic/></Scope><Occurrence><OneOrMore/></Occurrence" + 
        "></DFProperties><Node><NodeName>AAccept</NodeName><DFProperties><AccessType><Ad" + 
        "d/><Get/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occu" + 
        "rrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFPropertie" + 
        "s></Node><Node><NodeName>URI</NodeName><DFProperties><AccessType><Add/><Get/><R" + 
        "eplace/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occur" + 
        "rence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties" + 
        "></Node><Node><NodeName>Name</NodeName><DFProperties><AccessType><Add/><Get/><R" + 
        "eplace/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occur" + 
        "rence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties" + 
        "></Node><Node><NodeName>CliURI</NodeName><DFProperties><AccessType><Add/><Get/>" + 
        "<Replace/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occ" + 
        "urrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperti" + 
        "es></Node></Node></Node></Node></Node></Node><Node><NodeName>NAP</NodeName><DFP" + 
        "roperties><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Pe" + 
        "rmanent/> </Scope><Occurrence><One/></Occurrence><DFType><DDFName>com.symbian/1" + 
        ".0/NAP</DDFName></DFType></DFProperties><Node><NodeName/><DFProperties><AccessT" + 
        "ype><Add/><Copy/><Delete/><Get/><Replace/></AccessType><DFFormat><node/></DFFor" + 
        "mat><Scope><Dynamic/></Scope><Occurrence><ZeroOrMore/></Occurrence></DFProperti" + 
        "es><Node><NodeName>NapId</NodeName><DFProperties><AccessType><Get/></AccessType" + 
        "><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurr" + 
        "ence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeN" + 
        "ame>Name</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><DFFo" + 
        "rmat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence><" + 
        "DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>Au" + 
        "thInfo</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><node/>" + 
        "</DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFTyp" + 
        "e><DDFName></DDFName></DFType></DFProperties><Node><NodeName>AuthType</NodeName" + 
        "><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><chr/></DFFor" + 
        "mat><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/" + 
        "plain</MIME></DFType></DFProperties></Node><Node><NodeName>AuthName</NodeName><" + 
        "DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><chr/></DFForma" + 
        "t><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>t" + 
        "ext/plain</MIME></DFType></DFProperties></Node><Node><NodeName>AuthSecret</Node" + 
        "Name><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><chr/></D" + 
        "FFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><" + 
        "MIME>text/plain</MIME></DFType></DFProperties></Node></Node><Node><NodeName>Add" + 
        "rType</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><chr/></" + 
        "DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType>" + 
        "<MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>Addr</Nod" + 
        "eName><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><chr/></" + 
        "DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType>" + 
        "<MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>Linger</N" + 
        "odeName><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><int/>" + 
        "</DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFTyp" + 
        "e><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>IPv4</N" + 
        "odeName><DFProperties><AccessType><Get/></AccessType><DFFormat><node/></DFForma" + 
        "t><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><DDFNam" + 
        "e></DDFName></DFType></DFProperties><Node><NodeName>LocalAddr</NodeName><DFProp" + 
        "erties><AccessType><Get/><Replace/></AccessType><DFFormat><chr/></DFFormat><Sco" + 
        "pe><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/pl" + 
        "ain</MIME></DFType></DFProperties></Node><Node> <NodeName>NetMask</NodeName><DF" + 
        "Properties><AccessType><Get/><Replace/></AccessType><DFFormat><chr/></DFFormat>" + 
        "<Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>tex" + 
        "t/plain</MIME></DFType></DFProperties></Node><Node><NodeName>DefGW</NodeName><D" + 
        "FProperties><AccessType><Get/><Replace/></AccessType><DFFormat><chr/></DFFormat" + 
        "><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>te" + 
        "xt/plain</MIME></DFType></DFProperties></Node><Node><NodeName>AutoConfig</NodeN" + 
        "ame><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><bool/></D" + 
        "FFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><" + 
        "MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>DNS</NodeN" + 
        "ame><DFProperties><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><S" + 
        "cope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><DDFName></" + 
        "DDFName></DFType></DFProperties><Node><NodeName/><DFProperties><AccessType><Get" + 
        "/></AccessType><DFFormat><node/></DFFormat><Scope><Dynamic/></Scope><Occurrence" + 
        "><ZeroOrMore/></Occurrence></DFProperties><Node><NodeName>DNSAddr</NodeName><DF" + 
        "Properties><AccessType><Get/><Replace/></AccessType><DFFormat><chr/></DFFormat>" + 
        "<Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>tex" + 
        "t/plain</MIME></DFType></DFProperties></Node></Node></Node></Node><Node><NodeNa" + 
        "me>IPv6</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><node/" + 
        "></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFTy" + 
        "pe><DDFName></DDFName></DFType></DFProperties><Node><NodeName>LocalAddr</NodeNa" + 
        "me><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><chr/></DFF" + 
        "ormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MI" + 
        "ME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>AutoConfig</" + 
        "NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><bool" + 
        "/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFT" + 
        "ype><MIME>text/plain</MIME></DFType></DFProperties></Node></Node><Node><NodeNam" + 
        "e>NetworkId</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><c" + 
        "hr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><D" + 
        "FType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>Bea" + 
        "rerType</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><DFFor" + 
        "mat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurre" + 
        "nce><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeNa" + 
        "me>Bearer</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><nod" + 
        "e/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DF" + 
        "Type><DDFName></DDFName></DFType></DFProperties><Node><NodeName>3GPPPS</NodeNam" + 
        "e><DFProperties><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><node/" + 
        "></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFTy" + 
        "pe><DDFName></DDFName></DFType></DFProperties><Node><NodeName>PDPType</NodeName" + 
        "><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><chr/></DFFor" + 
        "mat><Scope><Dynamic/></Scope><Occurrence> <ZeroOrOne/></Occurrence><DFType><MIM" + 
        "E>text/plain</MIME></DFType></DFProperties></Node></Node><Node><NodeName>3GPPCS" + 
        "</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></AccessType><DFForm" + 
        "at><node/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurre" + 
        "nce><DFType><DDFName></DDFName></DFType></DFProperties><Node><NodeName>CallType" + 
        "</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><chr/></DFFor" + 
        "mat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME" + 
        ">text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>LinkSpeed</Nod" + 
        "eName><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><chr/></" + 
        "DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType>" + 
        "<MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>UseCB</No" + 
        "deName><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><bool/>" + 
        "</DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFTyp" + 
        "e><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>CBNbr</" + 
        "NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat><chr/" + 
        "></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFTy" + 
        "pe><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>UsePTx" + 
        "tLog</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><DFFormat" + 
        "><bool/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrenc" + 
        "e><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName" + 
        ">ModemInit</NodeName><DFProperties><AccessType><Get/><Replace/></AccessType><DF" + 
        "Format><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occu" + 
        "rrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node></Node><No" + 
        "de><NodeName>CDMA</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></A" + 
        "ccessType><DFFormat><node/></DFFormat><Scope><Dynamic/></Scope><Occurrence><Zer" + 
        "oOrOne/></Occurrence><DFType><DDFName></DDFName></DFType></DFProperties><Node><" + 
        "NodeName>MaxNumRetry</NodeName><DFProperties><AccessType><Get/><Replace/></Acce" + 
        "ssType><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrO" + 
        "ne/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node>" + 
        "<Node><NodeName>FirstRetryTimeout</NodeName><DFProperties><AccessType><Get/><Re" + 
        "place/></AccessType><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><Occurr" + 
        "ence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFPrope" + 
        "rties></Node><Node><NodeName>ReRegThreshold</NodeName><DFProperties><AccessType" + 
        "><Get/><Replace/></AccessType><DFFormat><int/></DFFormat><Scope><Dynamic/></Sco" + 
        "pe><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFType" + 
        "></DFProperties></Node><Node><NodeName>TBit</NodeName><DFProperties><AccessType" + 
        "><Get/><Replace/></AccessType><DFFormat><bool/></DFFormat><Scope><Dynamic/></Sc" + 
        "ope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFTyp" + 
        "e></DFProperties></Node></Node></Node><Node><NodeName>Ext</NodeName><DFProperti" + 
        "es><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Dynamic/>" + 
        "</Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><DDFName></DDFName></DFTyp" + 
        "e></DFProperties><Node><NodeName>Symbian</NodeName><DFProperties><AccessType><G" + 
        "et/></AccessType> <DFFormat><node/></DFFormat><Scope><Dynamic/></Scope><Occurre" + 
        "nce><ZeroOrOne/></Occurrence><DFType><DDFName></DDFName></DFType></DFProperties" + 
        "><Node><NodeName>BearerId</NodeName><DFProperties><AccessType><Get/><Replace/><" + 
        "/AccessType><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><Occurrence><Ze" + 
        "roOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></" + 
        "Node><Node><NodeName>ServiceId</NodeName><DFProperties><AccessType><Get/><Repla" + 
        "ce/></AccessType><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><Occurrenc" + 
        "e><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperti" + 
        "es></Node></Node></Node></Node></Node><Node><NodeName>Email</NodeName><DFProper" + 
        "ties><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Permane" + 
        "nt/></Scope><Occurrence><One/></Occurrence><DFType><DDFName>com.symbian/1.0/Ema" + 
        "il</DDFName></DFType></DFProperties><Node><NodeName/><DFProperties><AccessType>" + 
        "<Add/><Delete/><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Dynamic/>" + 
        "</Scope><Occurrence><ZeroOrMore/></Occurrence></DFProperties><Node><NodeName>In" + 
        "coming</NodeName><DFProperties><AccessType><Add/><Get/></AccessType><DFFormat><" + 
        "node/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence><DFTyp" + 
        "e><DDFName></DDFName></DFType></DFProperties><Node><NodeName>POP3</NodeName><DF" + 
        "Properties><AccessType><Add/><Get/></AccessType><DFFormat><node/></DFFormat><Sc" + 
        "ope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><DDFName></D" + 
        "DFName></DFType></DFProperties><Node><NodeName>AppID</NodeName><DFProperties><A" + 
        "ccessType><Add/><Get/><Replace/></AccessType><DFFormat><chr/></DFFormat><Scope>" + 
        "<Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain" + 
        "</MIME></DFType></DFProperties></Node><Node> <NodeName>ProviderID</NodeName><DF" + 
        "Properties><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><chr/></DFF" + 
        "ormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MI" + 
        "ME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>Name</NodeNa" + 
        "me><DFProperties><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><chr/" + 
        "></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFTy" + 
        "pe><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>AAccep" + 
        "t</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></AccessType><DFFor" + 
        "mat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurre" + 
        "nce><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeNa" + 
        "me>AProtocol</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></Access" + 
        "Type><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne" + 
        "/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><N" + 
        "ode><NodeName>PrefConRef</NodeName><DFProperties><AccessType><Add/><Get/><Repla" + 
        "ce/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrenc" + 
        "e><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperti" + 
        "es></Node><Node><NodeName>AppAddr</NodeName><DFProperties><AccessType><Add/><Ge" + 
        "t/></AccessType><DFFormat><node/></DFFormat><Scope><Dynamic/></Scope><Occurrenc" + 
        "e><One/></Occurrence><DFType><DDFName></DDFName></DFType></DFProperties><Node><" + 
        "NodeName/><DFProperties><AccessType><Add/><Delete/><Get/></AccessType><DFFormat" + 
        "><node/></DFFormat><Scope><Dynamic/></Scope><Occurrence><OneOrMore/></Occurrenc" + 
        "e></DFProperties><Node><NodeName>Addr</NodeName><DFProperties><AccessType><Add/" + 
        "><Copy/><Get/><Replace/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic" + 
        "/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFTyp" + 
        "e></DFProperties></Node><Node><NodeName>AddrType</NodeName><DFProperties><Acces" + 
        "sType> <Add/><Copy/><Get/><Replace/></AccessType><DFFormat><int/></DFFormat><Sc" + 
        "ope><Dynamic/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</" + 
        "MIME></DFType></DFProperties></Node><Node><NodeName>PortNbr</NodeName><DFProper" + 
        "ties><AccessType><Add/><Copy/><Get/><Replace/></AccessType><DFFormat><int/></DF" + 
        "Format><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence><DFType><MIME>te" + 
        "xt/plain</MIME></DFType></DFProperties></Node><Node><NodeName>Service</NodeName" + 
        "><DFProperties><AccessType><Add/><Copy/><Get/><Replace/></AccessType><DFFormat>" + 
        "<int/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence><DFTyp" + 
        "e><MIME>text/plain</MIME></DFType></DFProperties></Node></Node></Node><Node><No" + 
        "deName>AppAuth</NodeName><DFProperties><AccessType><Add/><Get/></AccessType><DF" + 
        "Format><node/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occ" + 
        "urrence><DFType><DDFName></DDFName></DFType></DFProperties><Node><NodeName/><DF" + 
        "Properties><AccessType><Add/><Delete/><Get/></AccessType><DFFormat><node/></DFF" + 
        "ormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrMore/></Occurrence></DFProper" + 
        "ties><Node><NodeName>AAuthType</NodeName><DFProperties><AccessType><Add/><Copy/" + 
        "><Get/><Replace/></AccessType><DFFormat><int/></DFFormat><Scope><Dynamic/></Sco" + 
        "pe><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFP" + 
        "roperties></Node><Node><NodeName>AAuthName</NodeName><DFProperties><AccessType>" + 
        "<Add/><Copy/><Replace/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/" + 
        "></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType" + 
        "></DFProperties></Node><Node><NodeName>AAuthSecret</NodeName><DFProperties><Acc" + 
        "essType><Add/><Copy/><Replace/></AccessType><DFFormat><chr/></DFFormat><Scope><" + 
        "Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain<" + 
        "/MIME></DFType></DFProperties></Node></Node></Node><Node><NodeName>Ext</NodeNam" + 
        "e><DFProperties><AccessType><Add/><Get/></AccessType><DFFormat><node/></DFForma" + 
        "t><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><DDFNam" + 
        "e></DDFName></DFType></DFProperties><Node><NodeName>Symbian</NodeName><DFProper" + 
        "ties><AccessType><Add/><Get/></AccessType><DFFormat><node/></DFFormat><Scope><D" + 
        "ynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><DDFName></DDFName" + 
        "></DFType></DFProperties><Node><NodeName>AutoSend</NodeName><DFProperties><Acce" + 
        "ssType><Add/><Get/><Replace/></AccessType><DFFormat><bool/></DFFormat><Scope><D" + 
        "ynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</" + 
        "MIME></DFType></DFProperties></Node><Node><NodeName>AckRcpt</NodeName><DFProper" + 
        "ties><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><bool/></DFFormat" + 
        "><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>te" + 
        "xt/plain</MIME></DFType></DFProperties></Node><Node><NodeName>DelDisc</NodeName" + 
        "><DFProperties><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><bool/>" + 
        "</DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFTyp" + 
        "e><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>MxMsgSz" + 
        "</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></AccessType><DFForm" + 
        "at><int/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurren" + 
        "ce><DFType><MIME>text/plain</MIME></DFType></DFProperties> </Node><Node><NodeNa" + 
        "me>SyncL</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></AccessType" + 
        "><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></" + 
        "Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node>" + 
        "<NodeName>PopL</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></Acce" + 
        "ssType><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrO" + 
        "ne/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node>" + 
        "<Node><NodeName>DMode</NodeName><DFProperties><AccessType><Add/><Get/><Replace/" + 
        "></AccessType><DFFormat><bool/></DFFormat><Scope><Dynamic/></Scope><Occurrence>" + 
        "<ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties" + 
        "></Node><Node><NodeName>SOpt</NodeName><DFProperties><AccessType><Add/><Get/><R" + 
        "eplace/></AccessType><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><Occur" + 
        "rence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProp" + 
        "erties></Node></Node></Node></Node><Node><NodeName>IMAP4</NodeName><DFPropertie" + 
        "s><AccessType><Add/><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Dyna" + 
        "mic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><DDFName></DDFName></" + 
        "DFType></DFProperties><Node><NodeName>AppID</NodeName><DFProperties><AccessType" + 
        "><Add/><Get/><Replace/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/" + 
        "></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></" + 
        "DFType></DFProperties></Node><Node><NodeName>ProviderID</NodeName><DFProperties" + 
        "><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><chr/></DFFormat><Sco" + 
        "pe><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/pl" + 
        "ain</MIME></DFType></DFProperties></Node><Node><NodeName>Name</NodeName><DFProp" + 
        "erties><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><chr/></DFForma" + 
        "t><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>t" + 
        "ext/plain</MIME></DFType></DFProperties></Node><Node><NodeName>AAccept</NodeNam" + 
        "e><DFProperties><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><chr/>" + 
        "</DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFTyp" + 
        "e><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>AProtoc" + 
        "ol</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></AccessType><DFFo" + 
        "rmat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurr" + 
        "ence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeN" + 
        "ame>PrefConRef</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></Acce" + 
        "ssType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrO" + 
        "ne/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node>" + 
        "<Node><NodeName>AppAddr</NodeName><DFProperties><AccessType><Add/><Get/></Acces" + 
        "sType><DFFormat><node/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></" + 
        "Occurrence><DFType><DDFName></DDFName></DFType></DFProperties><Node><NodeName/>" + 
        "<DFProperties><AccessType><Add/><Delete/><Get/></AccessType><DFFormat><node/></" + 
        "DFFormat><Scope><Dynamic/></Scope><Occurrence><OneOrMore/></Occurrence></DFProp" + 
        "erties><Node><NodeName>Addr</NodeName><DFProperties><AccessType><Add/><Copy/><G" + 
        "et/><Replace/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope>" + 
        "<Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProp" + 
        "erties></Node><Node><NodeName>AddrType</NodeName><DFProperties><AccessType><Add" + 
        "/><Copy/><Get/><Replace/></AccessType><DFFormat><int/></DFFormat><Scope><Dynami" + 
        "c/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFTy" + 
        "pe></DFProperties></Node><Node><NodeName>PortNbr</NodeName><DFProperties><Acces" + 
        "sType><Add/><Copy/><Get/><Replace/></AccessType><DFFormat><int/></DFFormat><Sco" + 
        "pe><Dynamic/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</M" + 
        "IME></DFType></DFProperties></Node><Node><NodeName>Service</NodeName><DFPropert" + 
        "ies><AccessType><Add/><Copy/><Get/><Replace/></AccessType><DFFormat><int/></DFF" + 
        "ormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence><DFType><MIME>tex" + 
        "t/plain</MIME></DFType></DFProperties></Node></Node></Node><Node><NodeName>AppA" + 
        "uth</NodeName><DFProperties><AccessType><Add/><Get/></AccessType><DFFormat><nod" + 
        "e/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DF" + 
        "Type><DDFName></DDFName></DFType></DFProperties><Node><NodeName/><DFProperties>" + 
        "<AccessType><Add/><Delete/><Get/></AccessType><DFFormat><node/></DFFormat><Scop" + 
        "e><Dynamic/></Scope><Occurrence><ZeroOrMore/></Occurrence></DFProperties><Node>" + 
        "<NodeName>AAuthType</NodeName><DFProperties><AccessType><Add/><Copy/><Get/><Rep" + 
        "lace/></AccessType><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><Occurre" + 
        "nce><One/></Occurrence> <DFType><MIME>text/plain</MIME></DFType></DFProperties>" + 
        "</Node><Node><NodeName>AAuthName</NodeName><DFProperties><AccessType><Add/><Cop" + 
        "y/><Replace/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><" + 
        "Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFPrope" + 
        "rties></Node><Node><NodeName>AAuthSecret</NodeName><DFProperties><AccessType><A" + 
        "dd/><Copy/><Replace/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/><" + 
        "/Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DF" + 
        "Type></DFProperties></Node></Node></Node><Node><NodeName>Ext</NodeName><DFPrope" + 
        "rties><AccessType><Add/><Get/></AccessType><DFFormat><node/></DFFormat><Scope><" + 
        "Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><DDFName></DDFNam" + 
        "e></DFType></DFProperties><Node><NodeName>Symbian</NodeName><DFProperties><Acce" + 
        "ssType><Add/><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Dynamic/></" + 
        "Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><DDFName></DDFName></DFType>" + 
        "</DFProperties><Node><NodeName>AutoSend</NodeName><DFProperties><AccessType><Ad" + 
        "d/><Get/><Replace/></AccessType><DFFormat><bool/></DFFormat><Scope><Dynamic/></" + 
        "Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFT" + 
        "ype></DFProperties></Node><Node><NodeName>AckRcpt</NodeName><DFProperties><Acce" + 
        "ssType><Add/><Get/><Replace/></AccessType><DFFormat><bool/></DFFormat><Scope><D" + 
        "ynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</" + 
        "MIME></DFType></DFProperties></Node><Node><NodeName>DelDisc</NodeName><DFProper" + 
        "ties><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><bool/></DFFormat" + 
        "><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>te" + 
        "xt/plain</MIME></DFType></DFProperties></Node><Node><NodeName>MSeen</NodeName><" + 
        "DFProperties><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><bool/></" + 
        "DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType>" + 
        "<MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>Idle</Nod" + 
        "eName><DFProperties><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><b" + 
        "ool/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><" + 
        "DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>Mx" + 
        "MsgSz</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></AccessType><D" + 
        "FFormat><int/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occ" + 
        "urrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><No" + 
        "deName>MxAttSz</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></Acce" + 
        "ssType><DFFormat><int/></DFFormat><Scope> <Dynamic/></Scope><Occurrence><ZeroOr" + 
        "One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node" + 
        "><Node><NodeName>MxBodySz</NodeName><DFProperties><AccessType><Add/><Get/><Repl" + 
        "ace/></AccessType><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><Occurren" + 
        "ce><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFPropert" + 
        "ies></Node><Node><NodeName>SMbLim</NodeName><DFProperties><AccessType><Add/><Ge" + 
        "t/><Replace/></AccessType><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><" + 
        "Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFType></D" + 
        "FProperties></Node><Node><NodeName>SIbLim</NodeName><DFProperties><AccessType><" + 
        "Add/><Get/><Replace/></AccessType><DFFormat><int/></DFFormat><Scope><Dynamic/><" + 
        "/Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DF" + 
        "Type></DFProperties></Node><Node><NodeName>IdleTO</NodeName><DFProperties><Acce" + 
        "ssType><Add/><Get/><Replace/></AccessType><DFFormat><int/></DFFormat><Scope><Dy" + 
        "namic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</M" + 
        "IME></DFType></DFProperties></Node><Node><NodeName>SRate</NodeName><DFPropertie" + 
        "s><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><int/></DFFormat><Sc" + 
        "ope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/p" + 
        "lain</MIME></DFType></DFProperties></Node><Node><NodeName>FRate</NodeName><DFPr" + 
        "operties><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><int/></DFFor" + 
        "mat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME" + 
        ">text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>DPath</NodeNam" + 
        "e><DFProperties><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><chr/>" + 
        "</DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFTyp" + 
        "e><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>PathChr" + 
        "</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></AccessType><DFForm" + 
        "at><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurren" + 
        "ce><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeNam" + 
        "e>FIStrat</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></AccessTyp" + 
        "e><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/><" + 
        "/Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node" + 
        "><NodeName>SIStrat</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></" + 
        "AccessType><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><Occurrence><Zer" + 
        "oOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></N" + 
        "ode><Node><NodeName>SOpt</NodeName><DFProperties> <AccessType><Add/><Get/><Repl" + 
        "ace/></AccessType><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><Occurren" + 
        "ce><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFPropert" + 
        "ies></Node><Node><NodeName>DMode</NodeName><DFProperties><AccessType><Add/><Get" + 
        "/><Replace/></AccessType><DFFormat><bool/></DFFormat><Scope><Dynamic/></Scope><" + 
        "Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFType></D" + 
        "FProperties></Node></Node></Node></Node></Node><Node><NodeName>Outgoing</NodeNa" + 
        "me><DFProperties><AccessType><Add/><Get/></AccessType><DFFormat><node/></DFForm" + 
        "at><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence><DFType><DDFName></D" + 
        "DFName></DFType></DFProperties><Node><NodeName>SMTP</NodeName><DFProperties><Ac" + 
        "cessType><Add/><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Dynamic/>" + 
        "</Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><DDFName></DDFName></DFTyp" + 
        "e></DFProperties><Node><NodeName>AppID</NodeName><DFProperties><AccessType><Add" + 
        "/><Get/><Replace/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Sc" + 
        "ope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFTyp" + 
        "e></DFProperties></Node><Node><NodeName>ProviderID</NodeName><DFProperties><Acc" + 
        "essType><Add/><Get/><Replace/></AccessType><DFFormat><chr/></DFFormat><Scope><D" + 
        "ynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</" + 
        "MIME></DFType></DFProperties></Node><Node><NodeName>AAccept</NodeName><DFProper" + 
        "ties><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><chr/></DFFormat>" + 
        "<Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>tex" + 
        "t/plain</MIME></DFType></DFProperties></Node><Node><NodeName>AProtocol</NodeNam" + 
        "e><DFProperties><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><chr/>" + 
        "</DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFTyp" + 
        "e><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>PrefCon" + 
        "Ref</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></AccessType><DFF" + 
        "ormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occur" + 
        "rence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><Node" + 
        "Name>AppAddr</NodeName><DFProperties><AccessType><Add/><Get/></AccessType><DFFo" + 
        "rmat><node/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence>" + 
        "<DFType><DDFName></DDFName></DFType></DFProperties><Node><NodeName/><DFProperti" + 
        "es><AccessType><Add/><Delete/><Get/></AccessType><DFFormat><node/></DFFormat><S" + 
        "cope><Dynamic/></Scope><Occurrence><OneOrMore/></Occurrence></DFProperties><Nod" + 
        "e><NodeName>Addr</NodeName><DFProperties><AccessType><Add/><Copy/><Get/><Replac" + 
        "e/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence" + 
        "><One/></Occurrence><DFType><MIME>text/plain</MIME> </DFType></DFProperties></N" + 
        "ode><Node><NodeName>AddrType</NodeName><DFProperties><AccessType><Add/><Copy/><" + 
        "Get/><Replace/></AccessType><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope" + 
        "><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFPro" + 
        "perties></Node><Node><NodeName>PortNbr</NodeName><DFProperties><AccessType><Add" + 
        "/><Copy/><Get/><Replace/></AccessType><DFFormat><int/></DFFormat><Scope><Dynami" + 
        "c/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFTy" + 
        "pe></DFProperties></Node><Node><NodeName>Service</NodeName><DFProperties><Acces" + 
        "sType><Add/><Copy/><Get/><Replace/></AccessType><DFFormat><int/></DFFormat><Sco" + 
        "pe><Dynamic/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</M" + 
        "IME></DFType></DFProperties></Node></Node></Node><Node><NodeName>AppAuth</NodeN" + 
        "ame><DFProperties><AccessType><Add/><Get/></AccessType><DFFormat><node/></DFFor" + 
        "mat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><DDFN" + 
        "ame></DDFName></DFType></DFProperties><Node><NodeName/><DFProperties><AccessTyp" + 
        "e><Add/><Delete/><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Dynamic" + 
        "/></Scope><Occurrence><ZeroOrMore/></Occurrence></DFProperties><Node><NodeName>" + 
        "AAuthType</NodeName><DFProperties><AccessType><Add/><Copy/><Get/><Replace/></Ac" + 
        "cessType><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroO" + 
        "rOne/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Nod" + 
        "e><Node><NodeName>AAuthName</NodeName><DFProperties><AccessType><Add/><Copy/><R" + 
        "eplace/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occur" + 
        "rence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProp" + 
        "erties></Node><Node><NodeName>AAuthSecret</NodeName><DFProperties><AccessType><" + 
        "Add/><Copy/><Replace/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/>" + 
        "</Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></D" + 
        "FType></DFProperties></Node></Node></Node><Node><NodeName>EAddr</NodeName><DFPr" + 
        "operties><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><chr/></DFFor" + 
        "mat><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/" + 
        "plain</MIME></DFType></DFProperties></Node><Node><NodeName>RAddr</NodeName><DFP" + 
        "roperties><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><chr/></DFFo" + 
        "rmat><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text" + 
        "/plain</MIME></DFType></DFProperties></Node><Node><NodeName>Ext</NodeName><DFPr" + 
        "operties><AccessType><Add/><Get/></AccessType><DFFormat><node/></DFFormat><Scop" + 
        "e><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence> <DFType><DDFName></DD" + 
        "FName></DFType></DFProperties><Node><NodeName>Symbian</NodeName><DFProperties><" + 
        "AccessType><Add/><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Dynamic" + 
        "/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><DDFName></DDFName></DFT" + 
        "ype></DFProperties><Node><NodeName>AddVC</NodeName><DFProperties><AccessType><A" + 
        "dd/><Get/><Replace/></AccessType><DFFormat><bool/></DFFormat><Scope><Dynamic/><" + 
        "/Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DF" + 
        "Type></DFProperties></Node><Node><NodeName>AddSig</NodeName><DFProperties><Acce" + 
        "ssType><Add/><Get/><Replace/></AccessType><DFFormat><bool/></DFFormat><Scope><D" + 
        "ynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</" + 
        "MIME></DFType></DFProperties></Node><Node><NodeName>Rcpt</NodeName><DFPropertie" + 
        "s><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><bool/></DFFormat><S" + 
        "cope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/" + 
        "plain</MIME></DFType></DFProperties></Node><Node><NodeName>Alias</NodeName><DFP" + 
        "roperties><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><chr/></DFFo" + 
        "rmat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIM" + 
        "E>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>RcAddr</NodeN" + 
        "ame><DFProperties><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><chr" + 
        "/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFT" + 
        "ype><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>Enc</" + 
        "NodeName><DFProperties><AccessType><Add/><Get/><Replace/></AccessType><DFFormat" + 
        "><int/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence" + 
        "><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>" + 
        "CCSelf</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></AccessType><" + 
        "DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Oc" + 
        "currence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><N" + 
        "odeName>SOpt</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></Access" + 
        "Type><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne" + 
        "/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node></" + 
        "Node></Node></Node></Node></Node></Node><Node><NodeName>SMS</NodeName><DFProper" + 
        "ties><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Permane" + 
        "nt/></Scope><Occurrence><One/></Occurrence><DFType><DDFName></DDFName></DFType>" + 
        "</DFProperties><Node><NodeName>Val</NodeName><DFProperties><AccessType><Get/><R" + 
        "eplace/></AccessType><DFFormat><int/></DFFormat><Scope><Permanent/></Scope><Occ" + 
        "urrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperti" + 
        "es></Node><Node><NodeName>ValFmt</NodeName><DFProperties><AccessType><Get/><Rep" + 
        "lace/></AccessType><DFFormat><int/></DFFormat><Scope><Permanent/></Scope><Occur" + 
        "rence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties" + 
        "></Node><Node><NodeName>Type</NodeName><DFProperties><AccessType><Get/><Replace" + 
        "/></AccessType><DFFormat><int/></DFFormat><Scope><Permanent/></Scope><Occurrenc" + 
        "e><One/></Occurrence><DFType><MIME>text/plain</MIME> </DFType></DFProperties></" + 
        "Node><Node><NodeName>StatRep</NodeName><DFProperties><AccessType><Get/><Replace" + 
        "/></AccessType><DFFormat><chr/></DFFormat><Scope><Permanent/></Scope><Occurrenc" + 
        "e><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></N" + 
        "ode><Node><NodeName>Enc</NodeName><DFProperties><AccessType><Get/><Replace/></A" + 
        "ccessType><DFFormat><int/></DFFormat><Scope><Permanent/></Scope><Occurrence><On" + 
        "e/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><" + 
        "Node><NodeName>Concat</NodeName><DFProperties><AccessType><Get/><Replace/></Acc" + 
        "essType><DFFormat><bool/></DFFormat><Scope><Permanent/></Scope><Occurrence><One" + 
        "/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><N" + 
        "ode><NodeName>IncReply</NodeName><DFProperties><AccessType><Get/><Replace/></Ac" + 
        "cessType><DFFormat><bool/></DFFormat><Scope><Permanent/></Scope><Occurrence><On" + 
        "e/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><" + 
        "Node><NodeName>RejDup</NodeName><DFProperties><AccessType><Get/><Replace/></Acc" + 
        "essType><DFFormat><bool/></DFFormat><Scope><Permanent/></Scope><Occurrence><One" + 
        "/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><N" + 
        "ode><NodeName>DelvRep</NodeName><DFProperties><AccessType><Get/><Replace/></Acc" + 
        "essType><DFFormat><bool/></DFFormat><Scope><Permanent/></Scope><Occurrence><One" + 
        "/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><N" + 
        "ode><NodeName>IncSC</NodeName><DFProperties><AccessType><Get/><Replace/></Acces" + 
        "sType><DFFormat><bool/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/>" + 
        "</Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Nod" + 
        "e><NodeName>DefSC</NodeName><DFProperties><AccessType><Get/><Replace/></AccessT" + 
        "ype><DFFormat><chr/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></O" + 
        "ccurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><" + 
        "NodeName>AppAddr</NodeName><DFProperties><AccessType><Get/></AccessType><DFForm" + 
        "at><node/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence>" + 
        "<DFType><DDFName>com.symbian/1.0/Sms</DDFName></DFType></DFProperties><Node><No" + 
        "deName/><DFProperties><AccessType><Add/><Delete/><Get/><Replace/></AccessType><" + 
        "DFFormat><node/></DFFormat><Scope><Dynamic/></Scope><Occurrence><OneOrMore/></O" + 
        "ccurrence></DFProperties><Node><NodeName>Name</NodeName><DFProperties><AccessTy" + 
        "pe><Add/><Get/><Replace/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynami" + 
        "c/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFTy" + 
        "pe></DFProperties></Node><Node><NodeName>Addr</NodeName><DFProperties> <AccessT" + 
        "ype><Add/><Get/><Replace/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynam" + 
        "ic/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFT" + 
        "ype></DFProperties></Node></Node></Node><Node><NodeName>Ext</NodeName><DFProper" + 
        "ties><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Permane" + 
        "nt/></Scope><Occurrence><One/></Occurrence><DFType><DDFName></DDFName></DFType>" + 
        "</DFProperties><Node><NodeName>Symbian</NodeName><DFProperties><AccessType><Get" + 
        "/></AccessType><DFFormat><node/></DFFormat><Scope><Permanent/></Scope><Occurren" + 
        "ce><One/></Occurrence><DFType><DDFName></DDFName></DFType></DFProperties><Node>" + 
        "<NodeName>Delivery</NodeName><DFProperties><AccessType><Get/><Replace/></Access" + 
        "Type><DFFormat><int/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></" + 
        "Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node>" + 
        "<NodeName>BAction</NodeName><DFProperties><AccessType><Get/><Replace/></AccessT" + 
        "ype><DFFormat><bool/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></" + 
        "Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node>" + 
        "<NodeName>Bearer</NodeName><DFProperties><AccessType><Get/><Replace/></AccessTy" + 
        "pe><DFFormat><int/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Oc" + 
        "currence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><N" + 
        "odeName>SDefault</NodeName><DFProperties><AccessType><Get/><Replace/></AccessTy" + 
        "pe><DFFormat><bool/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></O" + 
        "ccurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><" + 
        "NodeName>DescLen</NodeName><DFProperties><AccessType><Get/><Replace/></AccessTy" + 
        "pe><DFFormat><int/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Oc" + 
        "currence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><N" + 
        "odeName>SpecMsg</NodeName><DFProperties><AccessType><Get/><Replace/></AccessTyp" + 
        "e><DFFormat><int/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occ" + 
        "urrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node></Node></" + 
        "Node></Node><Node><NodeName>MMS</NodeName><DFProperties><AccessType><Get/></Acc" + 
        "essType><DFFormat><node/></DFFormat><Scope><Permanent/></Scope><Occurrence><One" + 
        "/></Occurrence><DFType><DDFName></DDFName></DFType></DFProperties><Node><NodeNa" + 
        "me>Accounts</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><n" + 
        "ode/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFTy" + 
        "pe><DDFName>com.symbian/1.0/Mms</DDFName></DFType></DFProperties><Node><NodeNam" + 
        "e/><DFProperties><AccessType><Add/><Delete/><Get/></AccessType><DFFormat><node/" + 
        "></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrMore/></Occurrence></DF" + 
        "Properties><Node><NodeName>AppId</NodeName><DFProperties> <AccessType><Add/><Ge" + 
        "t/><Replace/></AccessType><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><" + 
        "Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFPrope" + 
        "rties></Node><Node><NodeName>Name</NodeName><DFProperties><AccessType><Add/><Ge" + 
        "t/><Replace/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><" + 
        "Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFPrope" + 
        "rties></Node><Node><NodeName>Addr</NodeName><DFProperties><AccessType><Add/><Ge" + 
        "t/><Replace/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><" + 
        "Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFPrope" + 
        "rties></Node><Node><NodeName>CrMode</NodeName><DFProperties><AccessType><Add/><" + 
        "Get/><Replace/></AccessType><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope" + 
        "><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFType><" + 
        "/DFProperties></Node><Node><NodeName>ToProxy</NodeName><DFProperties><AccessTyp" + 
        "e><Add/><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Dynamic/></Scope" + 
        "><Occurrence><ZeroOrOne/></Occurrence><DFType><DDFName></DDFName></DFType></DFP" + 
        "roperties><Node><NodeName/><DFProperties><AccessType><Add/><Delete/><Get/></Acc" + 
        "essType><DFFormat><node/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroO" + 
        "rMore/></Occurrence></DFProperties><Node><NodeName>Proxy</NodeName><DFPropertie" + 
        "s><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><chr/></DFFormat><Sc" + 
        "ope><Dynamic/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</" + 
        "MIME></DFType></DFProperties></Node></Node></Node><Node><NodeName>ToNapID</Node" + 
        "Name><DFProperties><AccessType><Add/><Get/></AccessType><DFFormat><node/></DFFo" + 
        "rmat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><DDF" + 
        "Name></DDFName></DFType></DFProperties><Node><NodeName/><DFProperties><AccessTy" + 
        "pe><Add/><Delete/><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Dynami" + 
        "c/></Scope><Occurrence><ZeroOrMore/></Occurrence></DFProperties><Node><NodeName" + 
        ">NapID</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></AccessType><" + 
        "DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurren" + 
        "ce><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node> </Node></Node" + 
        "><Node><NodeName>Ext</NodeName><DFProperties><AccessType><Add/><Get/></AccessTy" + 
        "pe><DFFormat><node/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occ" + 
        "urrence><DFType><DDFName></DDFName></DFType></DFProperties><Node><NodeName>Symb" + 
        "ian</NodeName><DFProperties><AccessType><Add/><Get/></AccessType><DFFormat><nod" + 
        "e/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DF" + 
        "Type><DDFName></DDFName></DFType></DFProperties><Node><NodeName>RcptNotify</Nod" + 
        "eName><DFProperties><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><b" + 
        "ool/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><" + 
        "DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>Au" + 
        "toDl</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></AccessType><DF" + 
        "Format><int/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occu" + 
        "rrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><Nod" + 
        "eName>CrModeRO</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></Acce" + 
        "ssType><DFFormat><bool/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOr" + 
        "One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node" + 
        "><Node><NodeName>Val</NodeName><DFProperties><AccessType><Add/><Get/><Replace/>" + 
        "</AccessType><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><Occurrence><Z" + 
        "eroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties><" + 
        "/Node><Node><NodeName>MxDlSz</NodeName><DFProperties><AccessType><Add/><Get/><R" + 
        "eplace/></AccessType><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><Occur" + 
        "rence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProp" + 
        "erties></Node><Node><NodeName>Priority</NodeName><DFProperties><AccessType><Add" + 
        "/><Get/><Replace/></AccessType><DFFormat><bool/></DFFormat><Scope><Dynamic/></S" + 
        "cope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFTy" + 
        "pe></DFProperties></Node><Node><NodeName>HideNum</NodeName><DFProperties><Acces" + 
        "sType><Add/><Get/><Replace/></AccessType><DFFormat><bool/></DFFormat><Scope><Dy" + 
        "namic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</M" + 
        "IME></DFType></DFProperties></Node><Node><NodeName>ReadRcpt</NodeName><DFProper" + 
        "ties><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><bool/></DFFormat" + 
        "><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>te" + 
        "xt/plain</MIME></DFType></DFProperties></Node><Node><NodeName>DelvRpt</NodeName" + 
        "><DFProperties><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><bool/>" + 
        "</DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFTyp" + 
        "e><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>IncMsg<" + 
        "/NodeName><DFProperties><AccessType><Add/><Get/><Replace/></AccessType><DFForma" + 
        "t><bool/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurren" + 
        "ce><DFType> <MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeNa" + 
        "me>FiltAds</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></AccessTy" + 
        "pe><DFFormat><bool/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/" + 
        "></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><No" + 
        "de><NodeName>DelvRcpt</NodeName><DFProperties><AccessType><Add/><Get/><Replace/" + 
        "></AccessType><DFFormat><bool/></DFFormat><Scope><Dynamic/></Scope><Occurrence>" + 
        "<ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties" + 
        "></Node><Node><NodeName>MxRetry</NodeName><DFProperties><AccessType><Add/><Get/" + 
        "><Replace/></AccessType><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><Oc" + 
        "currence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFP" + 
        "roperties></Node><Node><NodeName>RetryIntv</NodeName><DFProperties><AccessType>" + 
        "<Add/><Get/><Replace/></AccessType><DFFormat><int/></DFFormat><Scope><Dynamic/>" + 
        "</Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain</MIME></D" + 
        "FType></DFProperties></Node><Node><NodeName>MxSendSz</NodeName><DFProperties><A" + 
        "ccessType><Add/><Get/><Replace/></AccessType><DFFormat><int/></DFFormat><Scope>" + 
        "<Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><MIME>text/plain" + 
        "</MIME></DFType></DFProperties></Node><Node><NodeName>FullScrPrev</NodeName><DF" + 
        "Properties><AccessType><Add/><Get/><Replace/></AccessType><DFFormat><bool/></DF" + 
        "Format><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurrence><DFType><M" + 
        "IME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>DevContClas" + 
        "s</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></AccessType><DFFor" + 
        "mat><int/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/></Occurre" + 
        "nce><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeNa" + 
        "me>MxImgHt</NodeName><DFProperties><AccessType><Add/><Get/><Replace/></AccessTy" + 
        "pe><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><Occurrence><ZeroOrOne/>" + 
        "</Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Nod" + 
        "e><NodeName>MxImgWd</NodeName><DFProperties><AccessType><Add/><Get/><Replace/><" + 
        "/AccessType><DFFormat><int/></DFFormat><Scope><Dynamic/></Scope><Occurrence><Ze" + 
        "roOrOne/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></" + 
        "Node></Node></Node></Node></Node><Node><NodeName>DefaultAcc</NodeName><DFProper" + 
        "ties><AccessType><Get/><Replace/></AccessType><DFFormat><chr/></DFFormat><Scope" + 
        "><Permanent/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</M" + 
        "IME></DFType></DFProperties></Node></Node><Node><NodeName>DevInfo</NodeName><DF" + 
        "Properties><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><P" + 
        "ermanent/></Scope><Occurrence><One/></Occurrence><DFType><DDFName>org.openmobil" + 
        "ealliance.dm/1.0/DevInfo</DDFName></DFType></DFProperties><Node><NodeName>Ext</" + 
        "NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat> <node/></DFFor" + 
        "mat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFType><DDFName>" + 
        "</DDFName></DFType></DFProperties></Node><Node><NodeName>Bearer</NodeName><DFPr" + 
        "operties><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Per" + 
        "manent/></Scope><Occurrence><One/></Occurrence><DFType><DDFName></DDFName></DFT" + 
        "ype></DFProperties></Node><Node><NodeName>DevId</NodeName><DFProperties><Access" + 
        "Type><Get/></AccessType><DFFormat><chr/></DFFormat><Scope><Permanent/></Scope><" + 
        "Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFPrope" + 
        "rties></Node><Node><NodeName>Man</NodeName><DFProperties><AccessType><Get/></Ac" + 
        "cessType><DFFormat><chr/></DFFormat><Scope><Permanent/></Scope><Occurrence><One" + 
        "/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><N" + 
        "ode><NodeName>Mod</NodeName><DFProperties><AccessType><Get/></AccessType><DFFor" + 
        "mat><chr/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence>" + 
        "<DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>D" + 
        "mV</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><chr/></DFF" + 
        "ormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFType><MIME>t" + 
        "ext/plain</MIME></DFType></DFProperties></Node><Node><NodeName>Lang</NodeName><" + 
        "DFProperties><AccessType><Get/></AccessType><DFFormat><chr/></DFFormat><Scope><" + 
        "Permanent/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIM" + 
        "E></DFType></DFProperties></Node></Node><Node><NodeName>DevDetail</NodeName><DF" + 
        "Properties><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><P" + 
        "ermanent/></Scope><Occurrence><One/></Occurrence><DFType><DDFName>org.openmobil" + 
        "ealliance.dm/1.0/DevDetail</DDFName></DFType></DFProperties><Node><NodeName>Ext" + 
        "</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><node/></DFFo" + 
        "rmat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFType><DDFName" + 
        "></DDFName></DFType></DFProperties><Node><NodeName>Symbian</NodeName><DFPropert" + 
        "ies><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Permanen" + 
        "t/></Scope><Occurrence><One/></Occurrence><DFType><DDFName></DDFName></DFType><" + 
        "/DFProperties><Node><NodeName>Ddf</NodeName><DFProperties><AccessType><Get/></A" + 
        "ccessType><DFFormat><chr/></DFFormat><Scope><Permanent/></Scope><Occurrence><On" + 
        "e/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><" + 
        "/Node></Node><Node><NodeName>Bearer</NodeName><DFProperties><AccessType><Get/><" + 
        "/AccessType><DFFormat><node/></DFFormat><Scope><Permanent/></Scope><Occurrence>" + 
        "<One/></Occurrence><DFType><DDFName></DDFName></DFType></DFProperties></Node><N" + 
        "ode><NodeName>URI</NodeName><DFProperties><AccessType><Get/></AccessType><DFFor" + 
        "mat><node/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence" + 
        "><DFType><DDFName></DDFName></DFType></DFProperties><Node><NodeName>MaxDepth</N" + 
        "odeName><DFProperties><AccessType><Get/></AccessType><DFFormat><chr/></DFFormat" + 
        "><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFType> <MIME>text/" + 
        "plain</MIME></DFType></DFProperties></Node><Node><NodeName>MaxTotLen</NodeName>" + 
        "<DFProperties><AccessType><Get/></AccessType><DFFormat><chr/></DFFormat><Scope>" + 
        "<Permanent/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MI" + 
        "ME></DFType></DFProperties></Node><Node><NodeName>MaxSegLen</NodeName><DFProper" + 
        "ties><AccessType><Get/></AccessType><DFFormat><chr/></DFFormat><Scope><Permanen" + 
        "t/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFTy" + 
        "pe></DFProperties></Node></Node><Node><NodeName>DevTyp</NodeName><DFProperties>" + 
        "<AccessType><Get/></AccessType><DFFormat><chr/></DFFormat><Scope><Permanent/></" + 
        "Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></" + 
        "DFProperties></Node><Node><NodeName>OEM</NodeName><DFProperties><AccessType><Ge" + 
        "t/></AccessType><DFFormat><chr/></DFFormat><Scope><Permanent/></Scope><Occurren" + 
        "ce><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></" + 
        "Node><Node><NodeName>FwV</NodeName><DFProperties><AccessType><Get/></AccessType" + 
        "><DFFormat><chr/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occu" + 
        "rrence><DFType><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><Nod" + 
        "eName>SwV</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><chr" + 
        "/></DFFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFType>" + 
        "<MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>HwV</Node" + 
        "Name><DFProperties><AccessType><Get/></AccessType><DFFormat><chr/></DFFormat><S" + 
        "cope><Permanent/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plai" + 
        "n</MIME></DFType></DFProperties></Node><Node><NodeName>LrgObj</NodeName><DFProp" + 
        "erties><AccessType><Get/></AccessType><DFFormat><bool/></DFFormat><Scope><Perma" + 
        "nent/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></D" + 
        "FType></DFProperties></Node></Node><Node><NodeName>Software</NodeName><DFProper" + 
        "ties><AccessType><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Permane" + 
        "nt/></Scope><Occurrence><One/></Occurrence><DFType><DDFName></DDFName></DFType>" + 
        "</DFProperties><Node><NodeName>Inventory</NodeName><DFProperties><AccessType><G" + 
        "et/></AccessType><DFFormat><node/></DFFormat><Scope><Permanent/></Scope><Occurr" + 
        "ence><One/></Occurrence><DFType><DDFName>com.symbian/1.0/AppMgt</DDFName></DFTy" + 
        "pe></DFProperties><Node><NodeName>Native</NodeName><DFProperties><AccessType><G" + 
        "et/></AccessType><DFFormat><node/></DFFormat><Scope><Permanent/></Scope><Occurr" + 
        "ence><One/></Occurrence><DFType><DDFName></DDFName></DFType></DFProperties><Nod" + 
        "e><NodeName/><DFProperties><AccessType><Get/></AccessType><DFFormat><node/></DF" + 
        "Format><Scope><Dynamic/></Scope><Occurrence><ZeroOrMore/></Occurrence></DFPrope" + 
        "rties><Node><NodeName>AppName</NodeName><DFProperties><AccessType><Get/></Acces" + 
        "sType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></O" + 
        "ccurrence><DFType> <MIME>text/plain</MIME></DFType></DFProperties></Node><Node>" + 
        "<NodeName>Vendor</NodeName><DFProperties><AccessType><Get/></AccessType><DFForm" + 
        "at><chr/></DFFormat><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence><DF" + 
        "Type><MIME>text/plain</MIME></DFType></DFProperties></Node><Node><NodeName>Vers" + 
        "ion</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><chr/></DF" + 
        "Format><Scope><Dynamic/></Scope><Occurrence><One/></Occurrence><DFType><MIME>te" + 
        "xt/plain</MIME></DFType></DFProperties></Node></Node></Node><Node><NodeName>Mid" + 
        "let</NodeName><DFProperties><AccessType><Get/></AccessType><DFFormat><node/></D" + 
        "FFormat><Scope><Permanent/></Scope><Occurrence><One/></Occurrence><DFType><DDFN" + 
        "ame></DDFName></DFType></DFProperties><Node><NodeName/><DFProperties><AccessTyp" + 
        "e><Get/></AccessType><DFFormat><node/></DFFormat><Scope><Dynamic/></Scope><Occu" + 
        "rrence><ZeroOrMore/></Occurrence></DFProperties><Node><NodeName>AppName</NodeNa" + 
        "me><DFProperties><AccessType><Get/></AccessType><DFFormat><chr/></DFFormat><Sco" + 
        "pe><Dynamic/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</M" + 
        "IME></DFType></DFProperties></Node><Node><NodeName>Vendor</NodeName><DFProperti" + 
        "es><AccessType><Get/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/><" + 
        "/Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType><" + 
        "/DFProperties></Node><Node><NodeName>Version</NodeName><DFProperties><AccessTyp" + 
        "e><Get/></AccessType><DFFormat><chr/></DFFormat><Scope><Dynamic/></Scope><Occur" + 
        "rence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFType></DFProperties" + 
        "></Node></Node></Node></Node><Node><NodeName>Install</NodeName><DFProperties><A" + 
        "ccessType><Exec/></AccessType><DFFormat><bin/></DFFormat><Scope><Permanent/></S" + 
        "cope><Occurrence><One/></Occurrence><DFType><MIME>application/octet-stream</MIM" + 
        "E></DFType></DFProperties></Node><Node><NodeName>Uninstall</NodeName><DFPropert" + 
        "ies><AccessType><Exec/></AccessType><DFFormat><chr/></DFFormat><Scope><Permanen" + 
        "t/></Scope><Occurrence><One/></Occurrence><DFType><MIME>text/plain</MIME></DFTy" + 
        "pe></DFProperties></Node></Node></Node></MgmtTree>"
    );    
    */
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        // Dump the results into DeviceTree
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(Device_External_ID);
        assertNotNull(device);
        
        factory.beginTransaction();
        Set<String> keys = results.keySet();
        for (Iterator<String> iterator = keys.iterator(); iterator.hasNext(); ) {
            String nodePath = (String)iterator.next();
            Object nodeValue = results.get(nodePath);
            // Save into DM inventory.
            deviceBean.updateDeviceTreeNode(device.getID() + "", nodePath, nodeValue.toString());
        }
        factory.commit();
        
        int total = 0;
        for (Iterator<String> iterator = keys.iterator(); iterator.hasNext(); ) {
            String nodePath = (String)iterator.next();
            DeviceTreeNode node = deviceBean.findDeviceTreeNode(device.getID(), nodePath);
            assertEquals(nodePath, results.get(nodePath).toString(), node.getStringValue());
            total++;
        }
        assertEquals(total, results.size());
    } catch(Exception ex) {
      factory.rollback();
      throw ex;
    } finally {
      factory.release();
    }
    
    factory = AllTests.getManagementBeanFactory();
    try {
        int total = 0;
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(Device_External_ID);
        assertNotNull(device);
        Set<String> keys = results.keySet();
        for (Iterator<String> iterator = keys.iterator(); iterator.hasNext(); ) {
            String nodePath = (String)iterator.next();
            DeviceTreeNode node = deviceBean.findDeviceTreeNode(device.getID(), nodePath);
            String v = node.getStringValue();
            v = (v == null)?"":v;
            assertEquals(nodePath, results.get(nodePath).toString(), v);
            total++;
        }
        assertEquals(total, results.size());
    } catch(Exception ex) {
      factory.rollback();
      throw ex;
    } finally {
      factory.release();
    }
  }

}
