/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestUpdateImageBean.java,v 1.11 2008/06/16 10:11:15 zhao Exp $
 * $Revision: 1.11 $
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
package com.npower.dm.management;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import junit.framework.TestCase;

import com.npower.dm.AllTests;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Image;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.Subscriber;
import com.npower.dm.core.Update;

/**
 * @author Zhao DongLu
 * 
 */
public class TestUpdateImageBean extends TestCase {

  //private static Log log = LogFactory.getLog(TestProfileConfigBean.class);

  private static final String BASE_DIR = AllTests.BASE_DIR;

  private static final String BINARY_FILE_1_TO_2 = "/metadata/fota/test/test1_image_1_to_2.whr";

  private static final String BINARY_FILE_2_TO_1 = "/metadata/fota/test/test1_image_2_to_1.whr";

  /**
   * CarrierEntity External ID
   */
  private String Carrier_External_ID = "Test.Carrier";

  private static final String MANUFACTURER_External_ID = "TEST.MANUFACTUER";

  private static final String MODEL_External_ID = "TEST.MODEL";

  /**
   * DeviceEntity IMEI, ExternalID
   */
  private String imei = "1234567890123456";

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        setupModel(factory);
        setupDevice(factory);
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
  }

  private void setupModel(ManagementBeanFactory factory) throws Exception {
    try {
        ModelBean modelBean = factory.createModelBean();

        factory.beginTransaction();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
        if (manufacturer == null) {
           manufacturer = modelBean.newManufacturerInstance(MANUFACTURER_External_ID, MANUFACTURER_External_ID, MANUFACTURER_External_ID);
           modelBean.update(manufacturer);
        }
  
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);
        if (model == null) {
           model = modelBean.newModelInstance(manufacturer, MODEL_External_ID, MODEL_External_ID, true, true, true,  true, true);
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
        SubscriberBean subscriberBean = factory.createSubcriberBean();
        ModelBean modelBean = factory.createModelBean();
        DeviceBean deviceBean = factory.createDeviceBean();

        Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID);
        assertNotNull(carrier);
  
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
        assertNotNull(manufacturer);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);
        assertNotNull(model);
  
        String externalID = "Zhao DongLu";
        String phoneNumber = "1380142342321";
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

  public void testBasicMethods() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
      ModelBean modelBean = factory.createModelBean();
      UpdateImageBean bean = factory.createUpdateImageBean();

      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
      assertNotNull(manufacturer);
      Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);
      assertNotNull(model);

      String version_1_0 = "test.1.version 1.0.0";
      String version_2_0 = "test.1.version 2.0.0";

      List<Update> list = bean.findUpdatesByFromImageVersionID(model, version_1_0);
      if (list.size() > 0) {
        factory.beginTransaction();
        for (int i = 0; i < list.size(); i++) {
          bean.delete((Update) list.get(i));
        }
        factory.commit();
      }

      Image fromImage = bean.newImageInstance(model, version_1_0, true, "Version 1.0");
      Image toImage = bean.newImageInstance(model, version_2_0, true, "Version 2.0");

      // Test case #1
      Update update = bean.newUpdateInstance(fromImage, toImage);
      factory.beginTransaction();
      bean.update(update);
      factory.commit();

      long updateID = update.getID();
      assertTrue(updateID > 0);

      // Test case #2
      update = bean.getUpdateByID("" + updateID);
      assertNotNull(update);
      assertEquals(update.getStatus(), bean.getImageUpdateStatus(Image.STATUS_CREATED));
      assertEquals(update.getFromImage().getVersionId(), version_1_0);
      assertEquals(update.getToImage().getVersionId(), version_2_0);
      assertEquals(update.getFromImage().getStatus(), bean.getImageUpdateStatus(Image.STATUS_CREATED));
      assertEquals(update.getToImage().getStatus(), bean.getImageUpdateStatus(Image.STATUS_CREATED));

      // Test case #3
      update = (Update) bean.findUpdatesByFromImageVersionID(model, version_1_0).get(0);
      assertNotNull(update);
      assertEquals(update.getStatus(), bean.getImageUpdateStatus(Image.STATUS_CREATED));
      assertEquals(update.getFromImage().getVersionId(), version_1_0);
      assertEquals(update.getToImage().getVersionId(), version_2_0);
      assertEquals(update.getFromImage().getStatus(), bean.getImageUpdateStatus(Image.STATUS_CREATED));
      assertEquals(update.getToImage().getStatus(), bean.getImageUpdateStatus(Image.STATUS_CREATED));

      // Test case #4
      update = (Update) bean.findUpdatesByToImageVersionID(model, version_2_0).get(0);
      assertNotNull(update);
      assertEquals(update.getStatus(), bean.getImageUpdateStatus(Image.STATUS_CREATED));
      assertEquals(update.getFromImage().getVersionId(), version_1_0);
      assertEquals(update.getToImage().getVersionId(), version_2_0);
      assertEquals(update.getFromImage().getStatus(), bean.getImageUpdateStatus(Image.STATUS_CREATED));
      assertEquals(update.getToImage().getStatus(), bean.getImageUpdateStatus(Image.STATUS_CREATED));

      // Test Case #5
      Image image = bean.getImageByID("" + fromImage.getID());
      assertNotNull(image);
      assertEquals(image.getID(), fromImage.getID());
      image = bean.getImageByID("" + toImage.getID());
      assertNotNull(image);
      assertEquals(image.getID(), toImage.getID());

      // Test Case #6
      factory.beginTransaction();
      bean.delete(update);
      factory.commit();
      list = bean.findUpdatesByToImageVersionID(model, version_2_0);
      assertEquals(0, list.size());
      fromImage = bean.getImageByVersionID(model, version_1_0);
      assertNull(fromImage);
      toImage = bean.getImageByVersionID(model, version_2_0);
      assertNull(toImage);

    } catch (DMException e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }

  }

  public void testUpgradeMethod() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
      ModelBean modelBean = factory.createModelBean();
      UpdateImageBean bean = factory.createUpdateImageBean();

      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
      assertNotNull(manufacturer);
      Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);
      assertNotNull(model);

      String version_1_0 = "test.2.version 1.0.0";
      String version_2_0 = "test.2.version 2.0.0";
      String version_3_0 = "test.2.version 3.0.0";
      String version_4_0 = "test.2.version 4.0.0";
      String version_5_0 = "test.2.version 5.0.0";
      String version_6_0 = "test.2.version 6.0.0";

      List<Update> list = bean.findUpdatesByFromImageVersionID(model, version_1_0);
      if (list.size() > 0) {
        factory.beginTransaction();
        for (int i = 0; i < list.size(); i++) {
          bean.delete((Update) list.get(i));
        }
        factory.commit();
      }
      list = bean.findUpdatesByFromImageVersionID(model, version_2_0);
      if (list.size() > 0) {
        factory.beginTransaction();
        for (int i = 0; i < list.size(); i++) {
          bean.delete((Update) list.get(i));
        }
        factory.commit();
      }
      list = bean.findUpdatesByFromImageVersionID(model, version_3_0);
      if (list.size() > 0) {
        factory.beginTransaction();
        for (int i = 0; i < list.size(); i++) {
          bean.delete((Update) list.get(i));
        }
        factory.commit();
      }
      list = bean.findUpdatesByFromImageVersionID(model, version_4_0);
      if (list.size() > 0) {
        factory.beginTransaction();
        for (int i = 0; i < list.size(); i++) {
          bean.delete((Update) list.get(i));
        }
        factory.commit();
      }

      Image image1 = bean.newImageInstance(model, version_1_0, true, "Version 1.0");
      Image image2 = bean.newImageInstance(model, version_2_0, true, "Version 2.0");
      Image image3 = bean.newImageInstance(model, version_3_0, true, "Version 3.0");
      Image image4 = bean.newImageInstance(model, version_4_0, true, "Version 4.0");
      Image image5 = bean.newImageInstance(model, version_5_0, true, "Version 5.0");
      Image image6 = bean.newImageInstance(model, version_6_0, true, "Version 6.0");

      // Test case #1
      Update update_2_to_5 = bean.newUpdateInstance(image2, image5);
      Update update_2_to_6 = bean.newUpdateInstance(image2, image6);

      Update update_1_to_2 = bean.newUpdateInstance(image1, image2);
      Update update_1_to_3 = bean.newUpdateInstance(image1, image3);
      Update update_1_to_4 = bean.newUpdateInstance(image1, image4);
      Update update_2_to_1 = bean.newUpdateInstance(image2, image1);
      Update update_2_to_3 = bean.newUpdateInstance(image2, image3);
      Update update_2_to_4 = bean.newUpdateInstance(image2, image4);
      Update update_3_to_1 = bean.newUpdateInstance(image3, image1);
      Update update_3_to_2 = bean.newUpdateInstance(image3, image2);
      Update update_3_to_4 = bean.newUpdateInstance(image3, image4);
      Update update_4_to_1 = bean.newUpdateInstance(image4, image1);
      Update update_4_to_2 = bean.newUpdateInstance(image4, image2);
      Update update_4_to_3 = bean.newUpdateInstance(image4, image3);
      update_2_to_5.setStatus(bean.getImageUpdateStatus(Image.STATUS_RELEASED));
      update_2_to_6.setStatus(bean.getImageUpdateStatus(Image.STATUS_RELEASED));
      update_1_to_2.setStatus(bean.getImageUpdateStatus(Image.STATUS_RELEASED));
      update_1_to_3.setStatus(bean.getImageUpdateStatus(Image.STATUS_RELEASED));
      update_1_to_4.setStatus(bean.getImageUpdateStatus(Image.STATUS_RELEASED));
      update_2_to_1.setStatus(bean.getImageUpdateStatus(Image.STATUS_RELEASED));
      update_2_to_3.setStatus(bean.getImageUpdateStatus(Image.STATUS_RELEASED));
      update_2_to_4.setStatus(bean.getImageUpdateStatus(Image.STATUS_RELEASED));
      update_3_to_1.setStatus(bean.getImageUpdateStatus(Image.STATUS_RELEASED));
      update_3_to_2.setStatus(bean.getImageUpdateStatus(Image.STATUS_RELEASED));
      update_3_to_4.setStatus(bean.getImageUpdateStatus(Image.STATUS_RELEASED));
      update_4_to_1.setStatus(bean.getImageUpdateStatus(Image.STATUS_RELEASED));
      update_4_to_2.setStatus(bean.getImageUpdateStatus(Image.STATUS_RELEASED));
      update_4_to_3.setStatus(bean.getImageUpdateStatus(Image.STATUS_RELEASED));
      factory.beginTransaction();
      bean.update(update_2_to_5);
      bean.update(update_2_to_6);
      bean.update(update_1_to_2);
      bean.update(update_1_to_3);
      bean.update(update_1_to_4);
      bean.update(update_2_to_1);
      bean.update(update_2_to_3);
      bean.update(update_2_to_4);
      bean.update(update_3_to_1);
      bean.update(update_3_to_2);
      bean.update(update_3_to_4);
      bean.update(update_4_to_1);
      bean.update(update_4_to_2);
      bean.update(update_4_to_3);
      factory.commit();

      long updateID_1_to_2 = update_1_to_2.getID();
      long updateID_1_to_3 = update_1_to_3.getID();
      long updateID_1_to_4 = update_1_to_4.getID();
      long updateID_2_to_1 = update_2_to_1.getID();
      long updateID_2_to_3 = update_2_to_3.getID();
      long updateID_2_to_4 = update_2_to_4.getID();
      long updateID_2_to_5 = update_2_to_5.getID();
      long updateID_2_to_6 = update_2_to_6.getID();
      long updateID_3_to_1 = update_3_to_1.getID();
      long updateID_3_to_2 = update_3_to_2.getID();
      long updateID_3_to_4 = update_3_to_4.getID();
      long updateID_4_to_1 = update_4_to_1.getID();
      long updateID_4_to_2 = update_4_to_2.getID();
      long updateID_4_to_3 = update_4_to_3.getID();
      assertTrue(updateID_1_to_2 > 0);
      assertTrue(updateID_1_to_3 > 0);
      assertTrue(updateID_1_to_4 > 0);
      assertTrue(updateID_2_to_1 > 0);
      assertTrue(updateID_2_to_3 > 0);
      assertTrue(updateID_2_to_4 > 0);
      assertTrue(updateID_2_to_5 > 0);
      assertTrue(updateID_2_to_6 > 0);
      assertTrue(updateID_3_to_1 > 0);
      assertTrue(updateID_3_to_2 > 0);
      assertTrue(updateID_3_to_4 > 0);
      assertTrue(updateID_4_to_1 > 0);
      assertTrue(updateID_4_to_2 > 0);
      assertTrue(updateID_4_to_3 > 0);

      // Test case # 1
      List<Update> list_1_upgrade = bean.findUpdates4Upgrade(model, version_1_0);
      assertEquals(3, list_1_upgrade.size());
      Update u_1_to_2 = (Update) list_1_upgrade.get(0);
      assertEquals(version_1_0, u_1_to_2.getFromImage().getVersionId());
      assertEquals(version_2_0, u_1_to_2.getToImage().getVersionId());

      Update u_1_to_3 = (Update) list_1_upgrade.get(1);
      assertEquals(version_1_0, u_1_to_3.getFromImage().getVersionId());
      assertEquals(version_3_0, u_1_to_3.getToImage().getVersionId());

      Update u_1_to_4 = (Update) list_1_upgrade.get(2);
      assertEquals(version_1_0, u_1_to_4.getFromImage().getVersionId());
      assertEquals(version_4_0, u_1_to_4.getToImage().getVersionId());

      // Test case # 2
      List<Update> list_4_downgrade = bean.findUpdates4Downgrade(model, version_4_0);
      assertEquals(3, list_4_downgrade.size());

      Update u_4_to_3 = (Update) list_4_downgrade.get(0);
      assertEquals(version_4_0, u_4_to_3.getFromImage().getVersionId());
      assertEquals(version_3_0, u_4_to_3.getToImage().getVersionId());

      Update u_4_to_1 = (Update) list_4_downgrade.get(2);
      assertEquals(version_4_0, u_4_to_1.getFromImage().getVersionId());
      assertEquals(version_1_0, u_4_to_1.getToImage().getVersionId());

      Update u_4_to_2 = (Update) list_4_downgrade.get(1);
      assertEquals(version_4_0, u_4_to_2.getFromImage().getVersionId());
      assertEquals(version_2_0, u_4_to_2.getToImage().getVersionId());

      // Test case # 3 findReleasedUpdates() and sorted by versionID desc
      List<Update> list_released_updates = bean.findReleasedUpdates(model, version_2_0);
      assertEquals(5, list_released_updates.size());
      Update u_2_to_6 = (Update) list_released_updates.get(0);
      assertEquals(version_2_0, u_2_to_6.getFromImage().getVersionId());
      assertEquals(version_6_0, u_2_to_6.getToImage().getVersionId());

      Update u_2_to_5 = (Update) list_released_updates.get(1);
      assertEquals(version_2_0, u_2_to_5.getFromImage().getVersionId());
      assertEquals(version_5_0, u_2_to_5.getToImage().getVersionId());

      Update u_2_to_4 = (Update) list_released_updates.get(2);
      assertEquals(version_2_0, u_2_to_4.getFromImage().getVersionId());
      assertEquals(version_4_0, u_2_to_4.getToImage().getVersionId());

      Update u_2_to_3 = (Update) list_released_updates.get(3);
      assertEquals(version_2_0, u_2_to_3.getFromImage().getVersionId());
      assertEquals(version_3_0, u_2_to_3.getToImage().getVersionId());

      Update u_2_to_1 = (Update) list_released_updates.get(4);
      assertEquals(version_2_0, u_2_to_1.getFromImage().getVersionId());
      assertEquals(version_1_0, u_2_to_1.getToImage().getVersionId());

      // Test case # 4 : findUpdates4Downgrade
      List<Update> list_2_downgrade = bean.findUpdates4Downgrade(model, version_2_0);
      assertEquals(1, list_2_downgrade.size());
      u_2_to_1 = (Update) list_2_downgrade.get(0);
      assertEquals(version_2_0, u_2_to_1.getFromImage().getVersionId());
      assertEquals(version_1_0, u_2_to_1.getToImage().getVersionId());

      // Test case # 5: findUpdates4Upgrade() and sorted by versionID asc
      List<Update> list_2_upgrade = bean.findUpdates4Upgrade(model, version_2_0);
      assertEquals(4, list_2_upgrade.size());

      u_2_to_3 = (Update) list_2_upgrade.get(0);
      assertEquals(version_2_0, u_2_to_3.getFromImage().getVersionId());
      assertEquals(version_3_0, u_2_to_3.getToImage().getVersionId());

      u_2_to_4 = (Update) list_2_upgrade.get(1);
      assertEquals(version_2_0, u_2_to_4.getFromImage().getVersionId());
      assertEquals(version_4_0, u_2_to_4.getToImage().getVersionId());

    } catch (DMException e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  public void testBinary() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
      ModelBean modelBean = factory.createModelBean();
      UpdateImageBean bean = factory.createUpdateImageBean();

      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
      assertNotNull(manufacturer);
      Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);
      assertNotNull(model);

      String version_1_0 = "test.3.version 1.0.0";
      String version_2_0 = "test.3.version 2.0.0";

      List<Update> list = bean.findUpdatesByFromImageVersionID(model, version_1_0);
      if (list.size() > 0) {
        factory.beginTransaction();
        for (int i = 0; i < list.size(); i++) {
          bean.delete((Update) list.get(i));
        }
        factory.commit();
      }

      list = bean.findUpdatesByFromImageVersionID(model, version_2_0);
      if (list.size() > 0) {
        factory.beginTransaction();
        for (int i = 0; i < list.size(); i++) {
          bean.delete((Update) list.get(i));
        }
        factory.commit();
      }

      Image image_1 = bean.newImageInstance(model, version_1_0, true, "Version 1.0");
      Image image_2 = bean.newImageInstance(model, version_2_0, true, "Version 2.0");

      File file_1_to_2 = new File(BASE_DIR, BINARY_FILE_1_TO_2);
      File file_2_to_1 = new File(BASE_DIR, BINARY_FILE_2_TO_1);
      InputStream in_1_to_2 = new FileInputStream(file_1_to_2);
      InputStream in_2_to_1 = new FileInputStream(file_2_to_1);

      // Test case #1
      Update update_1_to_2 = bean.newUpdateInstance(image_1, image_2, in_1_to_2);
      Update update_2_to_1 = bean.newUpdateInstance(image_2, image_1, in_2_to_1);
      update_1_to_2.setStatus(bean.getImageUpdateStatus(Image.STATUS_RELEASED));
      update_2_to_1.setStatus(bean.getImageUpdateStatus(Image.STATUS_RELEASED));
      factory.beginTransaction();
      bean.update(update_1_to_2);
      bean.update(update_2_to_1);
      factory.commit();

      long updateID_1_to_2 = update_1_to_2.getID();
      assertTrue(updateID_1_to_2 > 0);
      long updateID_2_to_1 = update_2_to_1.getID();
      assertTrue(updateID_2_to_1 > 0);

      //
      // Caution: must release the Hibernate Session, otherwise will throw the
      // floowing exception:
      // java.lang.UnsupportedOperationException: Blob may not be manipulated
      // from creating session
      //
      factory.release();
      factory = AllTests.getManagementBeanFactory();
      bean = factory.createUpdateImageBean();

      // Test case # 2
      List<Update> list_1_upgrade = bean.findUpdates4Upgrade(model, version_1_0);
      assertEquals(1, list_1_upgrade.size());
      Update u_1_to_2 = (Update) list_1_upgrade.get(0);
      assertEquals(version_1_0, u_1_to_2.getFromImage().getVersionId());
      assertEquals(version_2_0, u_1_to_2.getToImage().getVersionId());
      InputStream b_1_to_2 = u_1_to_2.getInputStream();
      assertNotNull(b_1_to_2);
      long length_1_2 = file_1_to_2.length();
      long len = 0;
      byte[] buf = new byte[512];
      len = b_1_to_2.read(buf);
      long total = 0;
      while (len > 0) {
            total += len;
            len = b_1_to_2.read(buf);
      }
      assertEquals(length_1_2, total);
      
      List<Update> list_2_downgrade = bean.findUpdates4Downgrade(model, version_2_0);
      assertEquals(1, list_2_downgrade.size());
      Update u_2_to_1 = (Update) list_2_downgrade.get(0);
      assertEquals(version_2_0, u_2_to_1.getFromImage().getVersionId());
      assertEquals(version_1_0, u_2_to_1.getToImage().getVersionId());
      InputStream b_2_to_1 = u_2_to_1.getInputStream();
      assertNotNull(b_2_to_1);
      long length_2_1 = file_2_to_1.length();
      len = 0;
      buf = new byte[512];
      len = b_2_to_1.read(buf);
      total = 0;
      while (len > 0) {
            total += len;
            len = b_2_to_1.read(buf);
      }
      assertEquals(length_2_1, total);

    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }

  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(TestUpdateImageBean.class);
  }

}
