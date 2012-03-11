/**
 * $Header: /home/master/nWave-DM-Common/codetemplates.xml,v 1.4 2006/05/12
 * 02:55:43 zhao Exp $ $Revision: 1.5 $ $Date: 2008/06/16 10:11:14 $
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

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.npower.cp.OTAInventory;
import com.npower.cp.convertor.PropertiesValueFetcher;
import com.npower.cp.convertor.ValueFetcher;
import com.npower.cp.db.OTAInventoryImpl;
import com.npower.dm.AllTests;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.ClientProvAuditLog;
import com.npower.dm.core.ClientProvJobTargetDevice;
import com.npower.dm.core.ClientProvTemplate;
import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.Subscriber;
import com.npower.sms.client.NoopSmsSender;
import com.npower.sms.client.SmsSender;
import com.npower.wap.omacp.OMACPSecurityMethod;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2008/06/16 10:11:14 $
 */
public class TestOTAClientProvJobBean extends TestCase {

  private static final String BASE_DIR                               = AllTests.BASE_DIR;

  /**
   * Profile Template XML File for Testing
   */
  private static final String FILENAME_PROFILE_METADATA              = BASE_DIR
                                                                         + "/metadata/setup/profiles/profile.template.NAP.install.xml";

  /**
   * CarrierEntity External ID
   */
  private static String              Carrier_External_ID                    = "Test.Carrier";

  private static final String MANUFACTURER_External_ID_1             = "Test.Manu.1." + System.currentTimeMillis();

  private static final String MODEL_External_ID_1                    = "Test.Model.1." + System.currentTimeMillis();

  /**
   * 
   */
  private static final String CLIENT_PASSWORD                        = "otasdm";

  /**
   * 
   */
  private static final String CLIENT_USERNAME                        = "otasdm";

  /**
   * ProfileTemplate name for testcase
   */
  private static final String PROFILE_TEMPLATE_NAME_4_NAP            = "NAP Default Template";

  /**
   * 
   */
  private static final String ATTRIBUTE_DEFAULT_VALUE                = "default value " + System.currentTimeMillis();

  private static final String DEVICE_EXT_ID = "IMEI:" + System.currentTimeMillis();

  private static String DEVICE_PHONE_NUMBER = "138" + System.currentTimeMillis();

  private static final String PROFILE_NAP_NAME = "NAP." + System.currentTimeMillis();;

  /**
   * @param name
   */
  public TestOTAClientProvJobBean(String name) {
    super(name);
  }

  /*
   * (non-Javadoc)
   * 
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    ManagementBeanFactory factory = null;

    try {
      factory = AllTests.getManagementBeanFactory();
      ModelBean modelBean = factory.createModelBean();
      factory.beginTransaction();
      // Setup model
      this.setupModel(modelBean);
      factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }

    try {
      factory = AllTests.getManagementBeanFactory();
      factory.beginTransaction();
      // Setup CP Template
      this.setupCPTemplate();
      factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }

    try {
      factory = AllTests.getManagementBeanFactory();
      factory.beginTransaction();
      this.setUpCarrier(Carrier_External_ID);
      factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }

    try {
      factory = AllTests.getManagementBeanFactory();
      factory.beginTransaction();
      this.setupProfileTemplates(FILENAME_PROFILE_METADATA);
      factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }

    try {
      factory = AllTests.getManagementBeanFactory();
      factory.beginTransaction();
      this.setUpProfileConfigs(Carrier_External_ID);
      factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }

    try {
      factory = AllTests.getManagementBeanFactory();
      factory.beginTransaction();
      this.setupDevice(Carrier_External_ID, MANUFACTURER_External_ID_1, MODEL_External_ID_1, DEVICE_EXT_ID);
      factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  private void setupModel(ModelBean modelBean) throws Exception {
    try {
      String manufacturer_ext_id = MANUFACTURER_External_ID_1;
      String model_ext_id = MODEL_External_ID_1;
      createModel(modelBean, manufacturer_ext_id, model_ext_id);

    } catch (DMException e) {
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * @param modelBean
   * @param manufacturer_ext_id
   * @param model_ext_id
   * @throws DMException
   */
  private void createModel(ModelBean modelBean, String manufacturer_ext_id, String model_ext_id) throws DMException {
    Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturer_ext_id);
    if (manufacturer != null) {
       modelBean.delete(manufacturer);
    }
    manufacturer = modelBean.newManufacturerInstance(manufacturer_ext_id, manufacturer_ext_id, manufacturer_ext_id);
    modelBean.update(manufacturer);

    Model model = modelBean.getModelByManufacturerModelID(manufacturer, model_ext_id);
    if (model == null) {
      model = modelBean.newModelInstance(manufacturer, model_ext_id, model_ext_id, true, true, true, true, true);

      modelBean.update(model);
    }
  }

  public void setupCPTemplate() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ModelBean modelBean = factory.createModelBean();
    ProfileTemplateBean profileTemplateBean = factory.createProfileTemplateBean();

    ClientProvTemplateBean bean = factory.createClientProvTemplateBean();
    try {
      ClientProvTemplate template = bean.newClientProvTemplateInstance();

      String content = "<?xml version='1.0' encoding='UTF-8'?>\n" + "<!-- \n"
          + "  OMA CP ClientProv 1.1 Compatiable Profile\n" + " -->\n" + "<wap-provisioningdoc version=\"1.1\">\n"
          + "\n" + "## PXLOGICAL\n" + "#foreach ( $pxLogicalElement in $profile.PXLogicalElements ) \n"
          + "  <characteristic type=\"PXLOGICAL\">\n" + "  #foreach ( $parameter in $pxLogicalElement.parameters)\n"
          + "    #foreach ( $value in $parameter.values)\n"
          + "      <parm name=\"${parameter.name}\" value=\"$!{value}\"/>\n" + "    #end\n" + "  #end\n"
          + "  ## PXAUTHINFO\n" + "  #foreach ( $pxAuthInfo  in $pxLogicalElement.pxAuthInfos)\n"
          + "    <characteristic type=\"PXAUTHINFO\">\n" + "    #foreach ( $parameter in $pxAuthInfo.parameters)\n"
          + "      #foreach ( $value in $parameter.values)\n"
          + "        <parm name=\"${parameter.name}\" value=\"$!{value}\"/>\n" + "      #end\n" + "    #end\n"
          + "    </characteristic>\n" + "  #end\n" + "  ## PORT\n"
          + "  #foreach ( $port  in $pxLogicalElement.ports)\n" + "    <characteristic type=\"PORT\">\n"
          + "    #foreach ( $parameter in $port.parameters)\n" + "      #foreach ( $value in $parameter.values)\n"
          + "        <parm name=\"${parameter.name}\" value=\"$!{value}\"/>\n" + "      #end\n" + "    #end\n"
          + "    </characteristic>\n" + "  #end\n" + "  ## PXPHYSICAL\n"
          + "  #foreach ( $pxPhysical  in $pxLogicalElement.pxPhysicals)\n"
          + "    <characteristic type=\"PXPHYSICAL\">\n" + "    #foreach ( $parameter in $pxPhysical.parameters)\n"
          + "      #foreach ( $value in $parameter.values)\n"
          + "        <parm name=\"${parameter.name}\" value=\"$!{value}\"/>\n" + "      #end\n" + "    #end\n"
          + "    #foreach ( $port  in $pxPhysical.ports)\n" + "      <characteristic type=\"PORT\">\n"
          + "      #foreach ( $parameter in $port.parameters)\n" + "        #foreach ( $value in $parameter.values)\n"
          + "          <parm name=\"${parameter.name}\" value=\"$!{value}\"/>\n" + "        #end\n" + "      #end\n"
          + "      </characteristic>\n" + "    #end\n" + "    </characteristic>\n" + "  #end\n" + "  \n"
          + "  </characteristic>\n" + "#end\n" + "\n" + "\n" + "## NAPDEF\n"
          + "#foreach ( $napDefElement in $profile.napDefElements ) \n" + "  <characteristic type=\"NAPDEF\">\n"
          + "  #foreach ( $parameter in $napDefElement.parameters)\n" + "    #foreach ( $value in $parameter.values)\n"
          + "      <parm name=\"${parameter.name}\" value=\"$!{value}\"/>\n" + "    #end\n" + "  #end\n"
          + "  ## NAPAUTHINFO\n" + "  #foreach ( $napAuthInfo in $napDefElement.napAuthInfos)\n"
          + "    <characteristic type=\"NAPAUTHINFO\">\n" + "    #foreach ( $parameter in $napAuthInfo.parameters)\n"
          + "      #foreach ( $value in $parameter.values)\n"
          + "        <parm name=\"${parameter.name}\" value=\"$!{value}\"/>\n" + "      #end\n" + "    #end\n"
          + "    </characteristic>\n" + "  #end\n" + "  ## VALIDITY\n"
          + "  #foreach ( $validity in $napDefElement.validities)\n" + "    <characteristic type=\"VALIDITY\">\n"
          + "    #foreach ( $parameter in $validity.parameters)\n" + "      #foreach ( $value in $parameter.values)\n"
          + "        <parm name=\"${parameter.name}\" value=\"$!{value}\"/>\n" + "      #end\n" + "    #end\n"
          + "    </characteristic>\n" + "  #end\n" + "  \n" + "  </characteristic>\n" + "#end\n" + "\n" + "\n"
          + "## BOOTSTRAP\n" + "#foreach ( $bootstrapElement in $profile.bootstrapElements ) \n"
          + "  <characteristic type=\"BOOTSTRAP\">\n" + "  #foreach ( $parameter in $bootstrapElement.parameters)\n"
          + "    #foreach ( $value in $parameter.values)\n"
          + "      <parm name=\"${parameter.name}\" value=\"$!{value}\"/>\n" + "    #end\n" + "  #end\n"
          + "  </characteristic>\n" + "#end\n" + "\n" + "\n" + "## CLIENTIDENTITY\n"
          + "#if ( $profile.clientIndetityElement ) \n" + "  <characteristic type=\"CLIENTIDENTITY\">\n"
          + "  #foreach ( $parameter in $profile.clientIndetityElement.parameters)\n"
          + "    #foreach ( $value in $parameter.values)\n"
          + "      <parm name=\"${parameter.name}\" value=\"$!{value}\"/>\n" + "    #end\n" + "  #end\n"
          + "  </characteristic>\n" + "#end\n" + "\n" + "\n" + "## VENDORCONFIG\n"
          + "#foreach ( $vendorConfigElement in $profile.vendorConfigElements ) \n"
          + "  <characteristic type=\"VENDORCONFIG\">\n"
          + "  #foreach ( $parameter in $vendorConfigElement.parameters)\n"
          + "    #foreach ( $value in $parameter.values)\n"
          + "      <parm name=\"${parameter.name}\" value=\"$!{value}\"/>\n" + "    #end\n" + "  #end\n"
          + "  </characteristic>\n" + "#end\n" + "\n" + "\n" + "## Application\n"
          + "#foreach ( $Application in $profile.ApplicationElements ) \n"
          + "  <characteristic type=\"APPLICATION\">\n" + "    #foreach ( $parameter in $Application.parameters)\n"
          + "      #foreach ( $value in $parameter.values)\n"
          + "      <parm name=\"${parameter.name}\" value=\"$!{value}\"/>\n" + "      #end\n" + "    #end\n" + "    \n"
          + "    ## AppAddress Elements\n" + "    #foreach( $appAddr in $Application.appAddrElements)\n"
          + "      <characteristic type=\"APPADDR\">\n" + "      #foreach ( $parameter in $appAddr.parameters)\n"
          + "        #foreach ( $value in $parameter.values)\n"
          + "        <parm name=\"${parameter.name}\" value=\"$!{value}\"/>\n" + "        #end\n" + "      #end\n"
          + "      #foreach( $port in $appAddr.ports)\n" + "        <characteristic type=\"PORT\">\n"
          + "        #foreach ( $parameter in $port.parameters)\n"
          + "          #foreach ( $value in $parameter.values)\n"
          + "          <parm name=\"${parameter.name}\" value=\"$!{value}\"/>\n" + "          #end\n"
          + "        #end\n" + "        </characteristic>\n" + "      #end\n" + "      </characteristic>\n"
          + "    #end\n" + "    \n" + "    ## AppAuth Elements\n"
          + "    #foreach( $appAuth in $Application.appAuthElements)\n" + "    #if ( ${Application.AppID} == 'w5' )\n"
          + "      ## SyncDS Application\n"
          + "      #if ( ! $appAuth.AppAuthLevel || $appAuth.AppAuthLevel.toString() == \"APPSRV\" )\n"
          + "      <characteristic type=\"APPAUTH\">\n"
          + "        <parm name=\"AAUTHNAME\" value=\"$!{appAuth.AppAuthName}\"/>\n"
          + "        <parm name=\"AAUTHSECRET\" value=\"$!{appAuth.AppAuthSecret}\"/>\n" + "      </characteristic>\n"
          + "      #end\n" + "    #else\n" + "      <characteristic type=\"APPAUTH\">\n"
          + "      #foreach ( $parameter in $appAuth.parameters)\n"
          + "        #foreach ( $value in $parameter.values)\n"
          + "        <parm name=\"${parameter.name}\" value=\"$!{value}\"/>\n" + "        #end\n" + "      #end\n"
          + "      </characteristic>\n" + "    #end\n" + "    #end\n" + "    \n" + "    ## Resources Elements\n"
          + "    #foreach( $resource in $Application.resourceElements)\n"
          + "      <characteristic type=\"RESOURCE\">\n" + "      #foreach ( $parameter in $resource.parameters)\n"
          + "        #foreach ( $value in $parameter.values)\n"
          + "        <parm name=\"${parameter.name}\" value=\"$!{value}\"/>\n" + "        #end\n" + "      #end\n"
          + "    </characteristic>\n" + "    #end\n" + "  </characteristic>\n" + "#end\n" + "\n" + "## ACCESS\n"
          + "#foreach ( $accessElement in $profile.accessElements ) \n" + "  <characteristic type=\"ACCESS\">\n"
          + "  #foreach ( $parameter in $accessElement.parameters)\n" + "    #foreach ( $value in $parameter.values)\n"
          + "      <parm name=\"${parameter.name}\" value=\"$!{value}\"/>\n" + "    #end\n" + "  #end\n"
          + "  </characteristic>\n" + "#end\n" + "\n" + "\n" + "</wap-provisioningdoc>\n";

      String description = "description";
      String name = "name";
      String manufacturerExtID = MANUFACTURER_External_ID_1;
      String modelExtID = MODEL_External_ID_1;
      String categoryName = ProfileCategory.NAP_CATEGORY_NAME;

      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExtID);
      assertNotNull(manufacturer);
      Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelExtID);
      assertNotNull(model);
      ProfileCategory category = profileTemplateBean.getProfileCategoryByName(categoryName);
      assertNotNull(category);

      template.setExternalID(name);
      template.setContent(content);
      template.setEncoder("omacp");
      template.setName(name);
      template.setDescription(description);
      template.setProfileCategory(category);

      // Add
      factory.beginTransaction();
      bean.update(template);
      factory.commit();

      ClientProvTemplate other = bean.getTemplate(template.getID().toString());
      assertNotNull(other);

      // Attach
      factory.beginTransaction();
      bean.attach(model, other);
      factory.commit();

      assertFalse(model.getClientProvTemplates().isEmpty());
      assertTrue(model.getClientProvTemplates().size() == 1);
      assertTrue(model.getClientProvTemplates().contains(other));

      // Test findTemplate
      ClientProvTemplate another = bean.findTemplate(model, category);
      assertNotNull(another);

      assertEquals(template.getContent(), another.getContent());
      assertEquals(template.getDescription(), another.getDescription());
      assertEquals(template.getName(), another.getName());
      assertEquals(template.getProfileCategory().getID(), another.getProfileCategory().getID());
      assertEquals(categoryName, another.getProfileCategory().getName());

      // Test findTemplates
      List<ClientProvTemplate> templates = bean.findTemplates(model);
      assertNotNull(templates);
      assertTrue(templates.size() > 0);

    } catch (Exception ex) {
      if (factory != null) {
        factory.rollback();
      }
      throw ex;
    } finally {
      if (factory != null) {
        factory.release();
      }
    }
  }

  private void setupProfileTemplates(String templateFilename) throws Exception {
    InputStream in = null;
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
      in = new FileInputStream(templateFilename);

      String clause4AllTestTemplates = "from ProfileTemplateEntity where name like 'NAP Default Template'";

      factory.beginTransaction();
      ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
      List<ProfileTemplate> templates = templateBean.findTemplates(clause4AllTestTemplates);
      for (int i = 0; i < templates.size(); i++) {
        ProfileTemplate template = (ProfileTemplate) templates.get(i);
        templateBean.delete(template);
      }
      factory.commit();

      templates = templateBean.findTemplates(clause4AllTestTemplates);
      assertEquals(templates.size(), 0);

      int total = templateBean.importProfileTemplates(in);
      in.close();
      assertTrue(total > 0);

      templates = templateBean.findTemplates(clause4AllTestTemplates);
      assertTrue(templates.size() > 0);

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

  /**
   * Create a profile config.
   * 
   * @param carrierExternalID
   * @param templateName
   * @param profileName
   * @param profileType
   * 
   * @return Return a ProfileConfig.
   * @throws Exception
   */
  private ProfileConfig createProfileConfig(ManagementBeanFactory factory, String carrierExternalID,
      String templateName, String profileName, String profileType) throws Exception {
    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    CarrierBean carrierBean = factory.createCarrierBean();
    ProfileConfigBean profileBean = factory.createProfileConfigBean();

    ProfileTemplate template = templateBean.getTemplateByName(templateName);
    assertNotNull(template);
    
    Carrier carrier = carrierBean.getCarrierByExternalID(carrierExternalID);
    assertNotNull(carrier);

    ProfileConfig configProfile = profileBean.getProfileConfigByName(carrierExternalID, profileType, profileName);
    if (configProfile != null) {
      profileBean.deleteProfileConfig(configProfile);
    }

    configProfile = profileBean.newProfileConfigInstance(profileName, carrier, template, ProfileConfig.PROFILE_TYPE_NAP);
    configProfile.setExternalID(profileName);
    // Add
    profileBean.update(configProfile);

    assertTrue(configProfile.getID() > 0);
    return configProfile;
  }

  private void setUpProfileConfigs(String carrierExternalID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ProfileConfigBean profileBean = factory.createProfileConfigBean();
    try {
      factory.beginTransaction();
      {
        String templateName = PROFILE_TEMPLATE_NAME_4_NAP;
        String typeOfProfile = "NAP";
        ProfileConfig profile = this.createProfileConfig(factory, carrierExternalID, templateName, PROFILE_NAP_NAME,
            typeOfProfile);
        profileBean.setAttributeValue(profile, "NAME", ATTRIBUTE_DEFAULT_VALUE);
        profileBean.setAttributeValue(profile, "BEARER", "GSM-GPRS");
        profileBean.setAttributeValue(profile, "NAP-ADDRESS", "cmnet");
        profileBean.update(profile);
      }
      factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }

  }

  public void setupDevice(String carrierExternalID, String manufacturerExternalID, String modelExternalID,
      String deviceExternalID) throws Exception {
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

      String password = "123456!@#";

      List<Subscriber> list = subscriberBean.findSubscriber("from SubscriberEntity where externalId='" + DEVICE_EXT_ID
          + "'");
      Subscriber subscriber = null;
      if (list.size() == 0) {
        subscriber = subscriberBean.newSubscriberInstance(carrier, DEVICE_EXT_ID, DEVICE_PHONE_NUMBER, password);

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
        device.setOMAServerPassword("otasdm");
        factory.beginTransaction();
        deviceBean.update(device);
        factory.commit();
      }

      // Test found
      device = deviceBean.getDeviceByID("" + device.getID());
      assertNotNull(device);

      assertEquals(deviceExternalID, device.getExternalId());

    } catch (DMException e) {
      throw e;
    } finally {
      factory.release();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
      CarrierBean carrierBean = factory.createCarrierBean();
      Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID);
      factory.beginTransaction();
      carrierBean.delete(carrier);
      factory.commit();
      
      ModelBean modelBean = factory.createModelBean();
      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID_1);
      factory.beginTransaction();
      modelBean.delete(manufacturer);
      factory.commit();
      
    } catch (Exception ex) {
      throw ex;
    }
  }

  public void testProvisionCase1() throws Exception {
    ManagementBeanFactory factory = null;
    SmsSender sender = new NoopSmsSender();
    OTAInventory otaInventory = new OTAInventoryImpl();
    
    long jobID = 0;
    try {
        factory = AllTests.getManagementBeanFactory();

        CarrierBean carrierBean = factory.createCarrierBean();
        Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID);
        assertNotNull(carrier);
        
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(DEVICE_EXT_ID);
        assertNotNull(device);
        Model model = device.getModel();
        
        //ModelBean modelBean = factory.createModelBean();
        //Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID_1);
        //Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID_1);
        //assertNotNull(model);
        
        ProfileConfigBean profileBean = factory.createProfileConfigBean();
        ProfileConfig profile = profileBean.getProfileConfigByExternalID(PROFILE_NAP_NAME);
        assertNotNull(profile);
        
        OTAClientProvJobBean bean = factory.createOTAClientProvJobBean(otaInventory, sender);
        String msisdn = device.getSubscriberPhoneNumber();

        ValueFetcher<ProfileCategory, String, String> valueFetcher = new PropertiesValueFetcher();
        OMACPSecurityMethod securityMethod = OMACPSecurityMethod.USERPIN;
        String pin = "1234";
        int maxRetry = 3;
        long maxDuration = 600;
        long scheduleTime = System.currentTimeMillis();
        
        // Create an OTA Job
        ProvisionJob job = bean.provision(msisdn, model, carrier, profile, valueFetcher, securityMethod, pin, scheduleTime, maxRetry, maxDuration);
        assertTrue(job.getID() > 0);
        assertEquals(ProvisionJob.JOB_STATE_APPLIED, job.getState());
        assertEquals(maxDuration, job.getMaxDuration());
        assertEquals(maxRetry, job.getMaxRetries());
        assertEquals(new Date(scheduleTime), job.getScheduledTime());
        assertEquals(1, job.getOtaTargetDevices().size());
        assertEquals(ProvisionJob.JOB_TYPE_ASSIGN_PROFILE, job.getJobType());
        
        assertNotNull(job.getOtaTargetDevices().iterator().next());
        assertEquals(msisdn, job.getOtaTargetDevices().iterator().next().getPhoneNumber());
        assertEquals(model.getManufacturer().getExternalId(), job.getOtaTargetDevices().iterator().next().getManufacturerExternalId());
        assertEquals(model.getManufacturerModelId(), job.getOtaTargetDevices().iterator().next().getModelExternalId());
        assertEquals(job.getID(), job.getOtaTargetDevices().iterator().next().getJob().getID());
        assertEquals(null, job.getOtaTargetDevices().iterator().next().getDeviceId());
        assertEquals(carrier.getExternalID(), job.getOtaTargetDevices().iterator().next().getCarrierExternalId());
        assertEquals(ClientProvAuditLog.STATUS_QUEUED, job.getOtaTargetDevices().iterator().next().getStatus());
        assertNull(job.getOtaTargetDevices().iterator().next().getFinishedTime());
        assertNotNull(job.getOtaTargetDevices().iterator().next().getMessageId());
        assertEquals(ClientProvTemplate.OMA_CP_1_1_ENCODER, job.getOtaTargetDevices().iterator().next().getMessageType());
        assertNotNull(job.getOtaTargetDevices().iterator().next().getMessageContent());
        assertEquals(securityMethod.toString(), job.getOtaTargetDevices().iterator().next().getSecurityMethod());
        assertEquals(pin, job.getOtaTargetDevices().iterator().next().getSecurityPin());
        assertEquals(profile.getExternalID(), job.getOtaTargetDevices().iterator().next().getProfileExternalId());
        
        // Load an OTA Job
        ProvisionJob job1 = bean.getJobById(job.getID());
        assertNotNull(job1);
        assertEquals(ProvisionJob.JOB_STATE_APPLIED, job.getState());
        assertEquals(maxDuration, job.getMaxDuration());
        assertEquals(maxRetry, job.getMaxRetries());
        assertEquals(new Date(scheduleTime), job.getScheduledTime());
        assertEquals(1, job.getOtaTargetDevices().size());
        assertEquals(ProvisionJob.JOB_TYPE_ASSIGN_PROFILE, job.getJobType());
        
        assertNotNull(job.getOtaTargetDevices().iterator().next());
        assertEquals(msisdn, job.getOtaTargetDevices().iterator().next().getPhoneNumber());
        assertEquals(model.getManufacturer().getExternalId(), job.getOtaTargetDevices().iterator().next().getManufacturerExternalId());
        assertEquals(model.getManufacturerModelId(), job.getOtaTargetDevices().iterator().next().getModelExternalId());
        assertEquals(job.getID(), job.getOtaTargetDevices().iterator().next().getJob().getID());
        assertEquals(null, job.getOtaTargetDevices().iterator().next().getDeviceId());
        assertEquals(carrier.getExternalID(), job.getOtaTargetDevices().iterator().next().getCarrierExternalId());
        assertEquals(ClientProvAuditLog.STATUS_QUEUED, job.getOtaTargetDevices().iterator().next().getStatus());
        assertNull(job.getOtaTargetDevices().iterator().next().getFinishedTime());
        assertNotNull(job.getOtaTargetDevices().iterator().next().getMessageId());
        assertEquals(ClientProvTemplate.OMA_CP_1_1_ENCODER, job.getOtaTargetDevices().iterator().next().getMessageType());
        assertNotNull(job.getOtaTargetDevices().iterator().next().getMessageContent());
        assertEquals(securityMethod.toString(), job.getOtaTargetDevices().iterator().next().getSecurityMethod());
        assertEquals(pin, job.getOtaTargetDevices().iterator().next().getSecurityPin());
        assertEquals(profile.getExternalID(), job.getOtaTargetDevices().iterator().next().getProfileExternalId());
        
        // Test find by messageID
        List<ClientProvJobTargetDevice> list = bean.findTargetDeviceByMessageID(job.getOtaTargetDevices().iterator().next().getMessageId());
        assertEquals(1, list.size());
        assertEquals(job.getOtaTargetDevices().iterator().next().getId(),
                     list.get(0).getId());

        jobID = job.getID();
        
    } catch (Exception ex) {
      throw ex;
    }
    
    // Disable a job
    try {
      OTAClientProvJobBean bean = factory.createOTAClientProvJobBean(otaInventory, sender);
      {
        ProvisionJob job = bean.getJobById(jobID);
        assertNotNull(job);
        assertEquals(ProvisionJob.JOB_STATE_APPLIED, job.getState());
        factory.beginTransaction();
        bean.disable(jobID);
        factory.commit();
      }
      {
        ProvisionJob job = bean.getJobById(jobID);
        assertEquals(ProvisionJob.JOB_STATE_DISABLE, job.getState());
      }
    } catch (Exception ex) {
      throw ex;
    }
    
    // Enable a job
    try {
      OTAClientProvJobBean bean = factory.createOTAClientProvJobBean(otaInventory, sender);
      {
        ProvisionJob job = bean.getJobById(jobID);
        assertNotNull(job);
        assertEquals(ProvisionJob.JOB_STATE_DISABLE, job.getState());
        factory.beginTransaction();
        bean.enable(jobID);
        factory.commit();
      }
      {
        ProvisionJob job = bean.getJobById(jobID);
        assertEquals(ProvisionJob.JOB_STATE_APPLIED, job.getState());
      }
    } catch (Exception ex) {
      throw ex;
    }

    // Delete a job
    try {
      OTAClientProvJobBean bean = factory.createOTAClientProvJobBean(otaInventory, sender);
      {
        ProvisionJob job = bean.getJobById(jobID);
        assertNotNull(job);
        assertEquals(ProvisionJob.JOB_STATE_APPLIED, job.getState());
        factory.beginTransaction();
        bean.delete(jobID);
        factory.commit();
      }
      {
        ProvisionJob job = bean.getJobById(jobID);
        assertNull(job);
      }
    } catch (Exception ex) {
      throw ex;
    }
  }

  public void testProvisionCase2() throws Exception {
    ManagementBeanFactory factory = null;
    SmsSender sender = new NoopSmsSender();
    OTAInventory otaInventory = new OTAInventoryImpl();
    
    long jobID = 0;
    try {
        factory = AllTests.getManagementBeanFactory();

        CarrierBean carrierBean = factory.createCarrierBean();
        Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID);
        assertNotNull(carrier);
        
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(DEVICE_EXT_ID);
        assertNotNull(device);
        Model model = device.getModel();
        
        //ModelBean modelBean = factory.createModelBean();
        //Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID_1);
        //Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID_1);
        //assertNotNull(model);
        
        ProfileConfigBean profileBean = factory.createProfileConfigBean();
        ProfileConfig profile = profileBean.getProfileConfigByExternalID(PROFILE_NAP_NAME);
        assertNotNull(profile);
        
        OTAClientProvJobBean bean = factory.createOTAClientProvJobBean(otaInventory, sender);
        String msisdn = device.getSubscriberPhoneNumber();

        ValueFetcher<ProfileCategory, String, String> valueFetcher = new PropertiesValueFetcher();
        OMACPSecurityMethod securityMethod = OMACPSecurityMethod.USERPIN;
        String pin = "1234";
        int maxRetry = 3;
        long maxDuration = 600;
        long scheduleTime = System.currentTimeMillis();
        
        // Create an OTA Job
        ProvisionJob job = bean.provision(device, profile, valueFetcher, securityMethod, pin, scheduleTime, maxRetry, maxDuration);
        assertTrue(job.getID() > 0);
        assertEquals(ProvisionJob.JOB_STATE_APPLIED, job.getState());
        assertEquals(maxDuration, job.getMaxDuration());
        assertEquals(maxRetry, job.getMaxRetries());
        assertEquals(new Date(scheduleTime), job.getScheduledTime());
        assertEquals(1, job.getOtaTargetDevices().size());
        assertEquals(ProvisionJob.JOB_TYPE_ASSIGN_PROFILE, job.getJobType());
        
        assertNotNull(job.getOtaTargetDevices().iterator().next());
        assertEquals(msisdn, job.getOtaTargetDevices().iterator().next().getPhoneNumber());
        assertEquals(model.getManufacturer().getExternalId(), job.getOtaTargetDevices().iterator().next().getManufacturerExternalId());
        assertEquals(model.getManufacturerModelId(), job.getOtaTargetDevices().iterator().next().getModelExternalId());
        assertEquals(job.getID(), job.getOtaTargetDevices().iterator().next().getJob().getID());
        assertEquals(device.getID() + "", job.getOtaTargetDevices().iterator().next().getDeviceId());
        assertEquals(carrier.getExternalID(), job.getOtaTargetDevices().iterator().next().getCarrierExternalId());
        assertEquals(ClientProvAuditLog.STATUS_QUEUED, job.getOtaTargetDevices().iterator().next().getStatus());
        assertNull(job.getOtaTargetDevices().iterator().next().getFinishedTime());
        assertNotNull(job.getOtaTargetDevices().iterator().next().getMessageId());
        assertEquals(ClientProvTemplate.OMA_CP_1_1_ENCODER, job.getOtaTargetDevices().iterator().next().getMessageType());
        assertNotNull(job.getOtaTargetDevices().iterator().next().getMessageContent());
        assertEquals(securityMethod.toString(), job.getOtaTargetDevices().iterator().next().getSecurityMethod());
        assertEquals(pin, job.getOtaTargetDevices().iterator().next().getSecurityPin());
        assertEquals(profile.getExternalID(), job.getOtaTargetDevices().iterator().next().getProfileExternalId());
        
        // Load an OTA Job
        ProvisionJob job1 = bean.getJobById(job.getID());
        assertNotNull(job1);
        assertEquals(ProvisionJob.JOB_STATE_APPLIED, job.getState());
        assertEquals(maxDuration, job.getMaxDuration());
        assertEquals(maxRetry, job.getMaxRetries());
        assertEquals(new Date(scheduleTime), job.getScheduledTime());
        assertEquals(1, job.getOtaTargetDevices().size());
        assertEquals(ProvisionJob.JOB_TYPE_ASSIGN_PROFILE, job.getJobType());
        
        assertNotNull(job.getOtaTargetDevices().iterator().next());
        assertEquals(msisdn, job.getOtaTargetDevices().iterator().next().getPhoneNumber());
        assertEquals(model.getManufacturer().getExternalId(), job.getOtaTargetDevices().iterator().next().getManufacturerExternalId());
        assertEquals(model.getManufacturerModelId(), job.getOtaTargetDevices().iterator().next().getModelExternalId());
        assertEquals(job.getID(), job.getOtaTargetDevices().iterator().next().getJob().getID());
        assertEquals(device.getID() + "", job.getOtaTargetDevices().iterator().next().getDeviceId());
        assertEquals(carrier.getExternalID(), job.getOtaTargetDevices().iterator().next().getCarrierExternalId());
        assertEquals(ClientProvAuditLog.STATUS_QUEUED, job.getOtaTargetDevices().iterator().next().getStatus());
        assertNull(job.getOtaTargetDevices().iterator().next().getFinishedTime());
        assertNotNull(job.getOtaTargetDevices().iterator().next().getMessageId());
        assertEquals(ClientProvTemplate.OMA_CP_1_1_ENCODER, job.getOtaTargetDevices().iterator().next().getMessageType());
        assertNotNull(job.getOtaTargetDevices().iterator().next().getMessageContent());
        assertEquals(securityMethod.toString(), job.getOtaTargetDevices().iterator().next().getSecurityMethod());
        assertEquals(pin, job.getOtaTargetDevices().iterator().next().getSecurityPin());
        assertEquals(profile.getExternalID(), job.getOtaTargetDevices().iterator().next().getProfileExternalId());
        
        // Test find by messageID
        List<ClientProvJobTargetDevice> list = bean.findTargetDeviceByMessageID(job.getOtaTargetDevices().iterator().next().getMessageId());
        assertEquals(1, list.size());
        assertEquals(job.getOtaTargetDevices().iterator().next().getId(),
                     list.get(0).getId());
        
        jobID = job.getID();
        
    } catch (Exception ex) {
      throw ex;
    }
    
    // Disable a job
    try {
      OTAClientProvJobBean bean = factory.createOTAClientProvJobBean(otaInventory, sender);
      {
        ProvisionJob job = bean.getJobById(jobID);
        assertNotNull(job);
        assertEquals(ProvisionJob.JOB_STATE_APPLIED, job.getState());
        factory.beginTransaction();
        bean.disable(jobID);
        factory.commit();
      }
      {
        ProvisionJob job = bean.getJobById(jobID);
        assertEquals(ProvisionJob.JOB_STATE_DISABLE, job.getState());
      }
    } catch (Exception ex) {
      throw ex;
    }
    
    // Enable a job
    try {
      OTAClientProvJobBean bean = factory.createOTAClientProvJobBean(otaInventory, sender);
      {
        ProvisionJob job = bean.getJobById(jobID);
        assertNotNull(job);
        assertEquals(ProvisionJob.JOB_STATE_DISABLE, job.getState());
        factory.beginTransaction();
        bean.enable(jobID);
        factory.commit();
      }
      {
        ProvisionJob job = bean.getJobById(jobID);
        assertEquals(ProvisionJob.JOB_STATE_APPLIED, job.getState());
      }
    } catch (Exception ex) {
      throw ex;
    }

    // Delete a job
    try {
      OTAClientProvJobBean bean = factory.createOTAClientProvJobBean(otaInventory, sender);
      {
        ProvisionJob job = bean.getJobById(jobID);
        assertNotNull(job);
        assertEquals(ProvisionJob.JOB_STATE_APPLIED, job.getState());
        factory.beginTransaction();
        bean.delete(jobID);
        factory.commit();
      }
      {
        ProvisionJob job = bean.getJobById(jobID);
        assertNull(job);
      }
    } catch (Exception ex) {
      throw ex;
    }
  }

}
