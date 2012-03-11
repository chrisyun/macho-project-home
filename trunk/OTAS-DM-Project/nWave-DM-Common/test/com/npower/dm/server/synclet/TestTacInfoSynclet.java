/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/server/synclet/TestTacInfoSynclet.java,v 1.4 2008/06/16 10:11:15 zhao Exp $
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

import junit.framework.TestCase;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;

import sync4j.framework.core.SyncML;
import sync4j.framework.engine.pipeline.MessageProcessingContext;

import com.npower.dm.AllTests;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/06/16 10:11:15 $
 */
public class TestTacInfoSynclet extends TestCase {
  
  String imei = "IMEI:351886011493928";
  String manufacturerExternalID = "NOKIA_TEST";
  String modelExternalID = "6681_TEST";
  
  private SyncML syncML = null;

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    this.setupSyncML();
  }

  /**
   * 
   */
  private void setupSyncML() throws Exception {
    
    String content = "<SyncML>" + 
                      "<SyncHdr>" + 
                      "<VerDTD>1.1</VerDTD>" + 
                      "<VerProto>DM/1.1</VerProto>" + 
                      "<SessionID>1</SessionID>" + 
                      "<MsgID>1</MsgID>" + 
                      "<Target><LocURI>http://219.142.217.167:8080/otasdm/dm</LocURI></Target>" + 
                      "<Source><LocURI>" + imei + "</LocURI></Source>" + 
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

    this.syncML = (SyncML)c.unmarshalDocument(new ByteArrayInputStream(content.getBytes("UTF-8")), "UTF-8");
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testDisable() throws Exception {
    
    // Remove exists model
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        ModelBean modelBean = factory.createModelBean();
        Model model = modelBean.getModelbyTAC(imei);
        if (model != null) {
           factory.beginTransaction();
           modelBean.delete(model);
           factory.commit();
        }
        model = modelBean.getModelbyTAC(imei);
        assertNull(model);
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }

    
    MessageProcessingContext processingContext = new MessageProcessingContext();
    TACInfoSynclet synclet = new TACInfoSynclet();
    synclet.setEnable(false);
    assertFalse(synclet.isEnable());
    synclet.preProcessMessage(processingContext, this.syncML, null);
    
    try {
        factory = AllTests.getManagementBeanFactory();
        ModelBean modelBean = factory.createModelBean();
        Model model = modelBean.getModelbyTAC(imei);
        assertNull(model);
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  public void testMissingModel() throws Exception {
    
    // Remove exists model
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        ModelBean modelBean = factory.createModelBean();
        Model model = modelBean.getModelbyTAC(imei);
        if (model != null) {
           factory.beginTransaction();
           modelBean.delete(model);
           factory.commit();
        }
        model = modelBean.getModelbyTAC(imei);
        assertNull(model);
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }

    
    MessageProcessingContext processingContext = new MessageProcessingContext();
    TACInfoSynclet synclet = new TACInfoSynclet();
    // Default is true
    assertTrue(synclet.isEnable());
    synclet.preProcessMessage(processingContext, this.syncML, null);
    
    try {
        factory = AllTests.getManagementBeanFactory();
        ModelBean modelBean = factory.createModelBean();
        Model model = modelBean.getModelbyTAC(imei);
        assertNotNull(model);
        assertEquals(manufacturerExternalID, model.getManufacturer().getExternalId());
        assertEquals(modelExternalID, model.getManufacturerModelId());
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  public void testMissingManufacturer() throws Exception {
    
    // Remove exists model & manufacturer
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        ModelBean modelBean = factory.createModelBean();
        Model model = modelBean.getModelbyTAC(imei);
        if (model != null) {
           factory.beginTransaction();
           modelBean.delete(model.getManufacturer());
           factory.commit();
        }
        model = modelBean.getModelbyTAC(imei);
        assertNull(model);
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }

    
    MessageProcessingContext processingContext = new MessageProcessingContext();
    TACInfoSynclet synclet = new TACInfoSynclet();
    // Default is true
    assertTrue(synclet.isEnable());
    synclet.preProcessMessage(processingContext, this.syncML, null);
    
    try {
        factory = AllTests.getManagementBeanFactory();
        ModelBean modelBean = factory.createModelBean();
        Model model = modelBean.getModelbyTAC(imei);
        assertNotNull(model);
        assertEquals(manufacturerExternalID, model.getManufacturer().getExternalId());
        assertEquals(modelExternalID, model.getManufacturerModelId());
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
    
  }

  public void testExists() throws Exception {
    
    // Remove exists model & manufacturer
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        ModelBean modelBean = factory.createModelBean();
        Model model = modelBean.getModelbyTAC(imei);
        if (model != null) {
           factory.beginTransaction();
           modelBean.delete(model.getManufacturer());
           factory.commit();
        }
        model = modelBean.getModelbyTAC(imei);
        assertNull(model);
        
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        assertNull(manufacturer);
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }

    factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        ModelBean modelBean = factory.createModelBean();
        factory.beginTransaction();
        Manufacturer manufacturer = modelBean.newManufacturerInstance(manufacturerExternalID, manufacturerExternalID, manufacturerExternalID);
        Model model = modelBean.newModelInstance(manufacturer, modelExternalID, modelExternalID, true, true, true, true, true);
        modelBean.update(manufacturer);
        modelBean.update(model);
        modelBean.addTACInfo(model, imei);
        factory.commit();

        model = modelBean.getModelbyTAC(imei);
        assertNotNull(model);
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }

    
    MessageProcessingContext processingContext = new MessageProcessingContext();
    TACInfoSynclet synclet = new TACInfoSynclet();
    // Default is true
    assertTrue(synclet.isEnable());
    synclet.preProcessMessage(processingContext, this.syncML, null);
    
    try {
        factory = AllTests.getManagementBeanFactory();
        ModelBean modelBean = factory.createModelBean();
        Model model = modelBean.getModelbyTAC(imei);
        assertNotNull(model);
        assertEquals(manufacturerExternalID, model.getManufacturer().getExternalId());
        assertEquals(modelExternalID, model.getManufacturerModelId());
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
    
  }

}
