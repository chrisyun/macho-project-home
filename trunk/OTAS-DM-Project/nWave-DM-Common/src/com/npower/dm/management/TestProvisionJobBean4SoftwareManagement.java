/**
 * $Header:
 * /home/master/nWave-DM-Common/src/com/npower/dm/management/TestProvisionJobBeanForProfileAssignment.java,v
 * 1.12 2008/01/11 04:22:10 zhao Exp $ $Revision: 1.3 $ $Date: 2008/01/11
 * 04:22:10 $
 * 
 * ===============================================================================================
 * License, Version 1.1
 * 
 * Copyright (c) 1994-2006 NPower Network Software Ltd. All rights reserved.
 * 
 * This SOURCE CODE FILE, which has been provided by NPower as part of a NPower
 * product for use ONLY by licensed users of the product, includes CONFIDENTIAL
 * and PROPRIETARY information of NPower.
 * 
 * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS OF THE LICENSE
 * STATEMENT AND LIMITED WARRANTY FURNISHED WITH THE PRODUCT.
 * 
 * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED COMPANIES AND
 * ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS OR LIABILITIES ARISING
 * OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION OF YOUR PROGRAMS, INCLUDING ANY
 * CLAIMS OR LIABILITIES ARISING OUT OF OR RESULTING FROM THE USE, MODIFICATION,
 * OR DISTRIBUTION OF PROGRAMS OR FILES CREATED FROM, BASED ON, AND/OR DERIVED
 * FROM THIS SOURCE CODE FILE.
 * ===============================================================================================
 */
package com.npower.dm.management;

import java.util.List;

import junit.framework.TestCase;

import com.npower.dm.AllTests;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.ProvisionJobStatus;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.core.SoftwareLicenseType;
import com.npower.dm.core.SoftwareVendor;
import com.npower.dm.core.Subscriber;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/06/16 10:11:14 $
 */
public class TestProvisionJobBean4SoftwareManagement extends TestCase {

  private static final String SOFTWARE_VENDOR_EXT_ID = "Vendor#1";

  /**
   * CarrierEntity External ID
   */
  private String              Carrier_External_ID      = "Test.Carrier";

  private static final String MANUFACTURER_External_ID = "TEST.MANUFACTUER";

  private static final String MODEL_External_ID        = "TEST.MODEL";

  /**
   * DeviceEntity IMEI, ExternalID
   */
  private String              imei                     = "1234567890123456";
  
  private long softwareID = 0;

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();

    this.tearDown();
    
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
      
      setUpCarriers(factory);

      setupModel(factory);

      setupDevice(factory);
      
      this.softwareID = this.setupSoftware();

    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        factory.beginTransaction();
        SoftwareBean bean = factory.createSoftwareBean();
        for (SoftwareVendor vendor: bean.getAllOfVendors()) {
            bean.delete(vendor);
        }
        factory.commit();
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
  }

  public void setUpCarriers(ManagementBeanFactory factory) throws Exception {
    try {
      Country country = factory.createCountryBean().getCountryByISOCode("CN");
      assertNotNull(country);
      assertEquals(country.getCountryCode(), "86");
      CarrierBean carrierBean = factory.createCarrierBean();
      Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID);
      if (carrier == null) {
        carrier = carrierBean.newCarrierInstance(country, Carrier_External_ID, Carrier_External_ID);
        factory.beginTransaction();
        carrierBean.update(carrier);
        factory.commit();
      }
    } catch (Exception e) {
      factory.rollback();
      throw e;
    }
  }

  private void setupModel(ManagementBeanFactory factory) throws Exception {
    try {
      ModelBean modelBean = factory.createModelBean();

      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);

      factory.beginTransaction();
      if (manufacturer == null) {
        manufacturer = modelBean.newManufacturerInstance(MANUFACTURER_External_ID, MANUFACTURER_External_ID,
            MANUFACTURER_External_ID);
        modelBean.update(manufacturer);
      }

      Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);
      if (model == null) {
        model = modelBean.newModelInstance(manufacturer, MODEL_External_ID, MODEL_External_ID, true, true, true, true,
            true);
        modelBean.update(model);
      }
      factory.commit();
    } catch (DMException e) {
      factory.rollback();
      throw e;
    }
  }

  public void setupDevice(ManagementBeanFactory factory) throws Exception {
    try {
      CarrierBean carrierBean = factory.createCarrierBean();
      ModelBean modelBean = factory.createModelBean();
      SubscriberBean subscriberBean = factory.createSubcriberBean();
      DeviceBean deviceBean = factory.createDeviceBean();

      Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID);
      assertNotNull(carrier);

      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
      assertNotNull(manufacturer);
      Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);
      assertNotNull(model);

      String externalID = "Zhao DongLu";
      String phoneNumber = "13801356729";
      String password = "123456!@#";

      List<Subscriber> list = subscriberBean.findSubscriber("from SubscriberEntity where externalId='" + externalID
          + "'");
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

      Device device = deviceBean.getDeviceByExternalID(imei);
      if (device != null) {
        factory.beginTransaction();
        deviceBean.delete(device);
        factory.commit();
      }

      // Create a DeviceEntity
      device = deviceBean.newDeviceInstance(subscriber, model, imei);

      factory.beginTransaction();
      deviceBean.update(device);
      factory.commit();

      // Test found
      device = deviceBean.getDeviceByID("" + device.getID());
      assertNotNull(device);

      assertEquals(imei, device.getExternalId());

    } catch (DMException e) {
      factory.rollback();
      throw e;
    }
  }

  private long setupSoftware() throws Exception {

    SoftwareVendor vendor = BaseTestSoftwareBean.setupSoftwareVendor(SOFTWARE_VENDOR_EXT_ID, "Mobile software vendor#1");

    SoftwareCategory category = BaseTestSoftwareBean.setupSoftwareCategory(null, "Category#1", "Mobile software category#1");

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();

    factory.beginTransaction();
    Software s1 = bean.newSoftwareInstance(vendor, category, "MSN", "Windows Mobile Messager", "1.0",
                                           SoftwareLicenseType.FREE);
    s1.setDescription("Microsoft Winodws Mobile Messager");
    bean.update(s1);
    factory.commit();

    // Testing & checking
    assertTrue(s1.getId() > 0);
    Software s = bean.getSoftwareByID(s1.getId());
    assertNotNull(s);
    assertEquals("MSN", s.getExternalId());
    assertEquals("Windows Mobile Messager", s.getName());
    assertEquals("1.0", s.getVersion());
    assertEquals(SoftwareLicenseType.FREE.toString(), s.getLicenseType());
    assertEquals("Microsoft Winodws Mobile Messager", s.getDescription());
    assertEquals(vendor.getId(), s.getVendor().getId());
    assertEquals(category.getId(), s.getCategory().getId());
    
    return s1.getId();
  }

  public void testInstall() throws Exception {
    assertTrue(true);

    ManagementBeanFactory factory = null;
    try {
      factory = AllTests.getManagementBeanFactory();
      ProvisionJobBean jobBean = factory.createProvisionJobBean();

      CarrierBean carrierBean = factory.createCarrierBean();
      Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID);
      assertNotNull(carrier);

      SoftwareBean softwareBean = factory.createSoftwareBean();
      Software software = softwareBean.getSoftwareByID(this.softwareID);

      // Testcase: add a Job
      DeviceBean deviceBean = factory.createDeviceBean();
      Device device = deviceBean.getDeviceByExternalID(imei);

      factory.beginTransaction();
      ProvisionJob job1 = jobBean.newJob4SoftwareInstall(device, software);
      long jobID1 = job1.getID();
      factory.commit();

      // Testcase: load a Job
      job1 = jobBean.loadJobByID(jobID1);
      assertNotNull(job1);
      assertEquals(ProvisionJob.JOB_TYPE_SOFTWARE_INSTALL, job1.getJobType());
      assertEquals(ProvisionJob.JOB_STATE_APPLIED, job1.getState());
      assertEquals(ProvisionJob.JOB_ELEMENT_TYPE_MULTIPLE, job1.getTargetType());
      assertEquals(this.softwareID, job1.getTargetSoftware().getId());
      
      assertEquals(1, job1.getAllOfProvisionJobStatus().size());
      job1.getAllOfProvisionJobStatus().get(0);
      assertEquals(this.softwareID, job1.getAllOfProvisionJobStatus().get(0).getTargetSoftware().getId());
      assertEquals(device.getExternalId(), job1.getAllOfProvisionJobStatus().get(0).getDevice().getExternalId());
      assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_READY, job1.getAllOfProvisionJobStatus().get(0).getState());

      // Testcase: disable a job
      factory.beginTransaction();
      jobBean.disable(jobID1);
      factory.commit();
      assertEquals(ProvisionJob.JOB_STATE_DISABLE, job1.getState());
      job1 = jobBean.loadJobByID(jobID1);
      assertNotNull(job1);
      assertEquals(ProvisionJob.JOB_STATE_DISABLE, job1.getState());
      
      assertEquals(1, job1.getAllOfProvisionJobStatus().size());
      job1.getAllOfProvisionJobStatus().get(0);
      assertEquals(this.softwareID, job1.getAllOfProvisionJobStatus().get(0).getTargetSoftware().getId());
      assertEquals(device.getExternalId(), job1.getAllOfProvisionJobStatus().get(0).getDevice().getExternalId());
      assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_CANCELLED, job1.getAllOfProvisionJobStatus().get(0).getState());

      // Testcase: cancel a job
      factory.beginTransaction();
      jobBean.cancel(jobID1);
      factory.commit();
      assertEquals(ProvisionJob.JOB_STATE_CANCELLED, job1.getState());
      job1 = jobBean.loadJobByID(jobID1);
      assertNotNull(job1);
      assertEquals(ProvisionJob.JOB_STATE_CANCELLED, job1.getState());

      assertEquals(1, job1.getAllOfProvisionJobStatus().size());
      job1.getAllOfProvisionJobStatus().get(0);
      assertEquals(this.softwareID, job1.getAllOfProvisionJobStatus().get(0).getTargetSoftware().getId());
      assertEquals(device.getExternalId(), job1.getAllOfProvisionJobStatus().get(0).getDevice().getExternalId());
      assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_CANCELLED, job1.getAllOfProvisionJobStatus().get(0).getState());

      // Testcase: enable a job
      factory.beginTransaction();
      jobBean.enable(jobID1);
      factory.commit();
      assertEquals(ProvisionJob.JOB_STATE_APPLIED, job1.getState());
      job1 = jobBean.loadJobByID(jobID1);
      assertNotNull(job1);
      assertEquals(ProvisionJob.JOB_STATE_APPLIED, job1.getState());

      assertEquals(1, job1.getAllOfProvisionJobStatus().size());
      job1.getAllOfProvisionJobStatus().get(0);
      assertEquals(this.softwareID, job1.getAllOfProvisionJobStatus().get(0).getTargetSoftware().getId());
      assertEquals(device.getExternalId(), job1.getAllOfProvisionJobStatus().get(0).getDevice().getExternalId());
      assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_READY, job1.getAllOfProvisionJobStatus().get(0).getState());

      // Testcase: delete a Job
      factory.beginTransaction();
      jobBean.delete(jobID1);
      factory.commit();

    } catch (Exception e) {
      // factory.rollback();
      throw e;
    } finally {
      if (factory != null) {
        factory.release();
      }
    }
  }

  public void testUnInstall() throws Exception {
    assertTrue(true);

    ManagementBeanFactory factory = null;
    try {
      factory = AllTests.getManagementBeanFactory();
      ProvisionJobBean jobBean = factory.createProvisionJobBean();

      CarrierBean carrierBean = factory.createCarrierBean();
      Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID);
      assertNotNull(carrier);

      SoftwareBean softwareBean = factory.createSoftwareBean();
      Software software = softwareBean.getSoftwareByID(this.softwareID);

      // Testcase: add a Job
      DeviceBean deviceBean = factory.createDeviceBean();
      Device device = deviceBean.getDeviceByExternalID(imei);

      factory.beginTransaction();
      ProvisionJob job1 = jobBean.newJob4SoftwareUnInstall(device, software);
      long jobID1 = job1.getID();
      factory.commit();

      // Testcase: load a Job
      job1 = jobBean.loadJobByID(jobID1);
      assertNotNull(job1);
      assertEquals(ProvisionJob.JOB_TYPE_SOFTWARE_UN_INSTALL, job1.getJobType());
      assertEquals(ProvisionJob.JOB_STATE_APPLIED, job1.getState());
      assertEquals(ProvisionJob.JOB_ELEMENT_TYPE_MULTIPLE, job1.getTargetType());
      assertEquals(this.softwareID, job1.getTargetSoftware().getId());
      
      assertEquals(1, job1.getAllOfProvisionJobStatus().size());
      job1.getAllOfProvisionJobStatus().get(0);
      assertEquals(this.softwareID, job1.getAllOfProvisionJobStatus().get(0).getTargetSoftware().getId());
      assertEquals(device.getExternalId(), job1.getAllOfProvisionJobStatus().get(0).getDevice().getExternalId());
      assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_READY, job1.getAllOfProvisionJobStatus().get(0).getState());

      // Testcase: disable a job
      factory.beginTransaction();
      jobBean.disable(jobID1);
      factory.commit();
      assertEquals(ProvisionJob.JOB_STATE_DISABLE, job1.getState());
      job1 = jobBean.loadJobByID(jobID1);
      assertNotNull(job1);
      assertEquals(ProvisionJob.JOB_STATE_DISABLE, job1.getState());
      
      assertEquals(1, job1.getAllOfProvisionJobStatus().size());
      job1.getAllOfProvisionJobStatus().get(0);
      assertEquals(this.softwareID, job1.getAllOfProvisionJobStatus().get(0).getTargetSoftware().getId());
      assertEquals(device.getExternalId(), job1.getAllOfProvisionJobStatus().get(0).getDevice().getExternalId());
      assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_CANCELLED, job1.getAllOfProvisionJobStatus().get(0).getState());

      // Testcase: cancel a job
      factory.beginTransaction();
      jobBean.cancel(jobID1);
      factory.commit();
      assertEquals(ProvisionJob.JOB_STATE_CANCELLED, job1.getState());
      job1 = jobBean.loadJobByID(jobID1);
      assertNotNull(job1);
      assertEquals(ProvisionJob.JOB_STATE_CANCELLED, job1.getState());

      assertEquals(1, job1.getAllOfProvisionJobStatus().size());
      job1.getAllOfProvisionJobStatus().get(0);
      assertEquals(this.softwareID, job1.getAllOfProvisionJobStatus().get(0).getTargetSoftware().getId());
      assertEquals(device.getExternalId(), job1.getAllOfProvisionJobStatus().get(0).getDevice().getExternalId());
      assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_CANCELLED, job1.getAllOfProvisionJobStatus().get(0).getState());

      // Testcase: enable a job
      factory.beginTransaction();
      jobBean.enable(jobID1);
      factory.commit();
      assertEquals(ProvisionJob.JOB_STATE_APPLIED, job1.getState());
      job1 = jobBean.loadJobByID(jobID1);
      assertNotNull(job1);
      assertEquals(ProvisionJob.JOB_STATE_APPLIED, job1.getState());

      assertEquals(1, job1.getAllOfProvisionJobStatus().size());
      job1.getAllOfProvisionJobStatus().get(0);
      assertEquals(this.softwareID, job1.getAllOfProvisionJobStatus().get(0).getTargetSoftware().getId());
      assertEquals(device.getExternalId(), job1.getAllOfProvisionJobStatus().get(0).getDevice().getExternalId());
      assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_READY, job1.getAllOfProvisionJobStatus().get(0).getState());

      // Testcase: delete a Job
      factory.beginTransaction();
      jobBean.delete(jobID1);
      factory.commit();

    } catch (Exception e) {
      // factory.rollback();
      throw e;
    } finally {
      if (factory != null) {
        factory.release();
      }
    }
  }

  public void testActive() throws Exception {
    assertTrue(true);

    ManagementBeanFactory factory = null;
    try {
      factory = AllTests.getManagementBeanFactory();
      ProvisionJobBean jobBean = factory.createProvisionJobBean();

      CarrierBean carrierBean = factory.createCarrierBean();
      Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID);
      assertNotNull(carrier);

      SoftwareBean softwareBean = factory.createSoftwareBean();
      Software software = softwareBean.getSoftwareByID(this.softwareID);

      // Testcase: add a Job
      DeviceBean deviceBean = factory.createDeviceBean();
      Device device = deviceBean.getDeviceByExternalID(imei);

      factory.beginTransaction();
      ProvisionJob job1 = jobBean.newJob4SoftwareActivation(device, software);
      long jobID1 = job1.getID();
      factory.commit();

      // Testcase: load a Job
      job1 = jobBean.loadJobByID(jobID1);
      assertNotNull(job1);
      assertEquals(ProvisionJob.JOB_TYPE_SOFTWARE_ACTIVE, job1.getJobType());
      assertEquals(ProvisionJob.JOB_STATE_APPLIED, job1.getState());
      assertEquals(ProvisionJob.JOB_ELEMENT_TYPE_MULTIPLE, job1.getTargetType());
      assertEquals(this.softwareID, job1.getTargetSoftware().getId());
      
      assertEquals(1, job1.getAllOfProvisionJobStatus().size());
      job1.getAllOfProvisionJobStatus().get(0);
      assertEquals(this.softwareID, job1.getAllOfProvisionJobStatus().get(0).getTargetSoftware().getId());
      assertEquals(device.getExternalId(), job1.getAllOfProvisionJobStatus().get(0).getDevice().getExternalId());
      assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_READY, job1.getAllOfProvisionJobStatus().get(0).getState());

      // Testcase: disable a job
      factory.beginTransaction();
      jobBean.disable(jobID1);
      factory.commit();
      assertEquals(ProvisionJob.JOB_STATE_DISABLE, job1.getState());
      job1 = jobBean.loadJobByID(jobID1);
      assertNotNull(job1);
      assertEquals(ProvisionJob.JOB_STATE_DISABLE, job1.getState());
      
      assertEquals(1, job1.getAllOfProvisionJobStatus().size());
      job1.getAllOfProvisionJobStatus().get(0);
      assertEquals(this.softwareID, job1.getAllOfProvisionJobStatus().get(0).getTargetSoftware().getId());
      assertEquals(device.getExternalId(), job1.getAllOfProvisionJobStatus().get(0).getDevice().getExternalId());
      assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_CANCELLED, job1.getAllOfProvisionJobStatus().get(0).getState());

      // Testcase: cancel a job
      factory.beginTransaction();
      jobBean.cancel(jobID1);
      factory.commit();
      assertEquals(ProvisionJob.JOB_STATE_CANCELLED, job1.getState());
      job1 = jobBean.loadJobByID(jobID1);
      assertNotNull(job1);
      assertEquals(ProvisionJob.JOB_STATE_CANCELLED, job1.getState());

      assertEquals(1, job1.getAllOfProvisionJobStatus().size());
      job1.getAllOfProvisionJobStatus().get(0);
      assertEquals(this.softwareID, job1.getAllOfProvisionJobStatus().get(0).getTargetSoftware().getId());
      assertEquals(device.getExternalId(), job1.getAllOfProvisionJobStatus().get(0).getDevice().getExternalId());
      assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_CANCELLED, job1.getAllOfProvisionJobStatus().get(0).getState());

      // Testcase: enable a job
      factory.beginTransaction();
      jobBean.enable(jobID1);
      factory.commit();
      assertEquals(ProvisionJob.JOB_STATE_APPLIED, job1.getState());
      job1 = jobBean.loadJobByID(jobID1);
      assertNotNull(job1);
      assertEquals(ProvisionJob.JOB_STATE_APPLIED, job1.getState());

      assertEquals(1, job1.getAllOfProvisionJobStatus().size());
      job1.getAllOfProvisionJobStatus().get(0);
      assertEquals(this.softwareID, job1.getAllOfProvisionJobStatus().get(0).getTargetSoftware().getId());
      assertEquals(device.getExternalId(), job1.getAllOfProvisionJobStatus().get(0).getDevice().getExternalId());
      assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_READY, job1.getAllOfProvisionJobStatus().get(0).getState());

      // Testcase: delete a Job
      factory.beginTransaction();
      jobBean.delete(jobID1);
      factory.commit();

    } catch (Exception e) {
      // factory.rollback();
      throw e;
    } finally {
      if (factory != null) {
        factory.release();
      }
    }
  }

  public void testDeActive() throws Exception {
    assertTrue(true);

    ManagementBeanFactory factory = null;
    try {
      factory = AllTests.getManagementBeanFactory();
      ProvisionJobBean jobBean = factory.createProvisionJobBean();

      CarrierBean carrierBean = factory.createCarrierBean();
      Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID);
      assertNotNull(carrier);

      SoftwareBean softwareBean = factory.createSoftwareBean();
      Software software = softwareBean.getSoftwareByID(this.softwareID);

      // Testcase: add a Job
      DeviceBean deviceBean = factory.createDeviceBean();
      Device device = deviceBean.getDeviceByExternalID(imei);

      factory.beginTransaction();
      ProvisionJob job1 = jobBean.newJob4SoftwareDeactivation(device, software);
      long jobID1 = job1.getID();
      factory.commit();

      // Testcase: load a Job
      job1 = jobBean.loadJobByID(jobID1);
      assertNotNull(job1);
      assertEquals(ProvisionJob.JOB_TYPE_SOFTWARE_DEACTIVE, job1.getJobType());
      assertEquals(ProvisionJob.JOB_STATE_APPLIED, job1.getState());
      assertEquals(ProvisionJob.JOB_ELEMENT_TYPE_MULTIPLE, job1.getTargetType());
      assertEquals(this.softwareID, job1.getTargetSoftware().getId());
      
      assertEquals(1, job1.getAllOfProvisionJobStatus().size());
      job1.getAllOfProvisionJobStatus().get(0);
      assertEquals(this.softwareID, job1.getAllOfProvisionJobStatus().get(0).getTargetSoftware().getId());
      assertEquals(device.getExternalId(), job1.getAllOfProvisionJobStatus().get(0).getDevice().getExternalId());
      assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_READY, job1.getAllOfProvisionJobStatus().get(0).getState());

      // Testcase: disable a job
      factory.beginTransaction();
      jobBean.disable(jobID1);
      factory.commit();
      assertEquals(ProvisionJob.JOB_STATE_DISABLE, job1.getState());
      job1 = jobBean.loadJobByID(jobID1);
      assertNotNull(job1);
      assertEquals(ProvisionJob.JOB_STATE_DISABLE, job1.getState());
      
      assertEquals(1, job1.getAllOfProvisionJobStatus().size());
      job1.getAllOfProvisionJobStatus().get(0);
      assertEquals(this.softwareID, job1.getAllOfProvisionJobStatus().get(0).getTargetSoftware().getId());
      assertEquals(device.getExternalId(), job1.getAllOfProvisionJobStatus().get(0).getDevice().getExternalId());
      assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_CANCELLED, job1.getAllOfProvisionJobStatus().get(0).getState());

      // Testcase: cancel a job
      factory.beginTransaction();
      jobBean.cancel(jobID1);
      factory.commit();
      assertEquals(ProvisionJob.JOB_STATE_CANCELLED, job1.getState());
      job1 = jobBean.loadJobByID(jobID1);
      assertNotNull(job1);
      assertEquals(ProvisionJob.JOB_STATE_CANCELLED, job1.getState());

      assertEquals(1, job1.getAllOfProvisionJobStatus().size());
      job1.getAllOfProvisionJobStatus().get(0);
      assertEquals(this.softwareID, job1.getAllOfProvisionJobStatus().get(0).getTargetSoftware().getId());
      assertEquals(device.getExternalId(), job1.getAllOfProvisionJobStatus().get(0).getDevice().getExternalId());
      assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_CANCELLED, job1.getAllOfProvisionJobStatus().get(0).getState());

      // Testcase: enable a job
      factory.beginTransaction();
      jobBean.enable(jobID1);
      factory.commit();
      assertEquals(ProvisionJob.JOB_STATE_APPLIED, job1.getState());
      job1 = jobBean.loadJobByID(jobID1);
      assertNotNull(job1);
      assertEquals(ProvisionJob.JOB_STATE_APPLIED, job1.getState());

      assertEquals(1, job1.getAllOfProvisionJobStatus().size());
      job1.getAllOfProvisionJobStatus().get(0);
      assertEquals(this.softwareID, job1.getAllOfProvisionJobStatus().get(0).getTargetSoftware().getId());
      assertEquals(device.getExternalId(), job1.getAllOfProvisionJobStatus().get(0).getDevice().getExternalId());
      assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_READY, job1.getAllOfProvisionJobStatus().get(0).getState());

      // Testcase: delete a Job
      factory.beginTransaction();
      jobBean.delete(jobID1);
      factory.commit();

    } catch (Exception e) {
      // factory.rollback();
      throw e;
    } finally {
      if (factory != null) {
        factory.release();
      }
    }
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(TestProvisionJobBean4SoftwareManagement.class);
  }

}
