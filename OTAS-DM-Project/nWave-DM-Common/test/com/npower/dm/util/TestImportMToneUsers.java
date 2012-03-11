/**
 * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/util/TestImportMToneUsers.java,v 1.4 2008/06/19 09:37:08 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2008/06/19 09:37:08 $
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
package com.npower.dm.util;

import java.io.File;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;

import com.npower.dm.AllTests;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Device;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ServiceProvider;
import com.npower.dm.core.Subscriber;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ServiceProviderBean;
import com.npower.dm.management.SubscriberBean;
import com.npower.wap.omacp.OMACPSecurityMethod;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/06/19 09:37:08 $
 */
public class TestImportMToneUsers extends TestCase {
  
  /**
   * ServiceProviderEntity External ID
   */
  private String Service_Provider_External_ID = "MTone_10k";

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        this.setUpServiceProviders(factory);
    } catch (RuntimeException e) {
      throw e;
    } finally {
      factory.release();
    }
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void setUpServiceProviders(ManagementBeanFactory factory) throws Exception {
    try {
        ServiceProviderBean bean = factory.createServiceProviderBean();
        
        ServiceProvider sp = bean.getServiceProviderByExternalID(Service_Provider_External_ID);
        if (sp == null) {
          sp = bean.newServiceProviderInstance(Service_Provider_External_ID, Service_Provider_External_ID);
          factory.beginTransaction();
          bean.update(sp);
          factory.commit();
        }
    } catch (Exception e) {
      factory.rollback();
      throw e;
    }
  }

  public void testImport() throws Exception {
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        DeviceBean bean = factory.createDeviceBean();
        SubscriberBean subscriberBean = factory.createSubcriberBean();
        ModelBean modelBean = factory.createModelBean();
        
        CarrierBean carrierBean = factory.createCarrierBean();
        Carrier carrier = carrierBean.getCarrierByExternalID("ChinaMobile");
        
        ServiceProviderBean spBean = factory.createServiceProviderBean();
        ServiceProvider sp = spBean.getServiceProviderByExternalID(Service_Provider_External_ID);

        DeviceDataReader in = new MToneDeviceDataReader(new File("D:/Zhao/MyWorkspace/nWave-DM-Common/test/com/npower/dm/util/mtone_10000_200804_part3.csv"));
        //DeviceDataReader in = new MToneDeviceDataReaderV2(new File("D:/Zhao/MyWorkspace/nWave-DM-Common/test/com/npower/dm/util/mtone_jiangsu_20080605.csv"));
        in.open();
        
        DeviceData item = in.read();
        Device device = null;
        int total = 0;
        long begin = System.currentTimeMillis();
        while (item != null) {
            if (StringUtils.isNotEmpty(item.getImei())) {
               device = bean.getDeviceByExternalID(item.getImei());
               if (device != null) {
                  System.err.println("Device exists, imei: " + item.getImei() + ", msidn: " + item.getPhoneNumber());
                  // Get Next
                  item = in.read();
                  continue;
               }
            }
          
            String phoneNumber = item.getPhoneNumber();
            String manufacturerName = item.getManufacturer();
            String modelName = item.getModel();
            Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerName);
            if (manufacturer == null) {
               System.err.println(item.getLineNumber() + "#:Could not found manufacturer: " + manufacturerName );
               return;
            }
            
            Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelName);
            if (model == null) {
               model = modelBean.getModelByName(manufacturer, modelName);
            }
            if (model == null) {
               System.err.println(item.getLineNumber() + "#:Could not found model: " + manufacturerName + " " + modelName);
               //return;
            }
            
            // Add a device and a subscriber
            factory.beginTransaction();
            device = bean.enroll(phoneNumber, model, carrier, sp);
            if (StringUtils.isNotEmpty(item.getImei())) {
               device.setExternalId(item.getImei());
            }
            bean.update(device);
            Subscriber subscriber = device.getSubscriber();
            if (StringUtils.isNotEmpty(item.getImsi())) {
               subscriber.setIMSI(item.getImsi());
               subscriber.setBootstrapPinType(Byte.toString(OMACPSecurityMethod.NETWPIN.getValue()));
               subscriberBean.update(subscriber);
            }
            factory.commit();
            total++;
            
            if (total % 100 == 0) {
               long now = System.currentTimeMillis();
               double pref = ((double)total) / ((now - begin)/1000.0); 
               System.err.println(total + " records finished, performance: " + pref + "recs/sec");
            }
            
            // Get Next
            item = in.read();
        }
        
        in.close();
        System.out.println("Total records: " + total);
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

}
