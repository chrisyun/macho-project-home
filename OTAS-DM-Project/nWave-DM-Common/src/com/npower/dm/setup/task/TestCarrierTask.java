package com.npower.dm.setup.task;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import junit.framework.TestCase;

import org.hibernate.Session;

import com.npower.cp.OTAInventory;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.hibernate.entity.CarrierEntity;
import com.npower.dm.hibernate.entity.CountryEntity;
import com.npower.dm.hibernate.entity.ProfileConfigEntity;
import com.npower.dm.management.AutomaticProvisionJobBean;
import com.npower.dm.management.ClientProvAuditLogBean;
import com.npower.dm.management.ClientProvTemplateBean;
import com.npower.dm.management.DDFTreeBean;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.DeviceLogBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ModelClassificationBean;
import com.npower.dm.management.OTAClientProvJobBean;
import com.npower.dm.management.ProfileAssignmentBean;
import com.npower.dm.management.ProfileMappingBean;
import com.npower.dm.management.ProfileTemplateBean;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.management.SearchBean;
import com.npower.dm.management.ServiceProviderBean;
import com.npower.dm.management.SoftwareBean;
import com.npower.dm.management.SoftwareEvaluateBean;
import com.npower.dm.management.SoftwareTopListBean;
import com.npower.dm.management.SoftwareTrackingBean;
import com.npower.dm.management.SubscriberBean;
import com.npower.dm.management.UpdateImageBean;
import com.npower.setup.console.ConsoleImpl;
import com.npower.setup.core.Console;
import com.npower.setup.core.Setup;
import com.npower.setup.core.SetupImpl;
import com.npower.sms.client.SmsSender;
import com.npower.wap.omacp.OMACPSecurityMethod;
/**
 * @author Administrator
 * 
 */
public class TestCarrierTask extends TestCase {
  private static String carrierDataFile = "D:/Zhao/MyWorkspace/OTAS-Setup-Common/metadata/test/dm/carriers/carrier.default.xml";

  private static String carrierDataFileErr = "D:/Zhao/MyWorkspace/OTAS-Setup-Common/metadata/test/dm/carriers/carrierErr.default.xml";
  
  private String carrier_name_1                   = "Default Carrier";

  private String carrier_externalID_1             = "DefaultCarrier";

  private String carrier_country_1                = "CN";

  private String carrier_bootstrapNAOprofile_1    = "";

  private String carrier_authtype_1               = "syncml:auth-md5";

  private String carrier_notificationtype_1       = "wapPush";

  private String carrier_notificationtimeout_1    = "7200";

  private String carrier_bootstraptimeout_1       = "0";

  private String carrier_notificationMaxRetries_1 = "3";

  private String carrier_phoneNumberpolicy_1      = ".*";
  
  private String carrier_bootstrapMaxRetries_1       = CarrierItem.DEFAULT_BOOTSTRAP_MAX_RETRIES;

  private String carrier_defaultBootstrapPinType_1 = OMACPSecurityMethod.USERPIN.toString();

  private String carrier_defaultBootstrapUserPin_1      = Carrier.DEFAULT_BOOTSTRAP_USER_PIN;
  

  private String carrier_name_2                   = "中国移动";

  private String carrier_externalID_2             = "ChinaMobile";

  private String carrier_country_2                = "CN";

  private String carrier_bootstrapNAOprofile_2    = "";

  private String carrier_authtype_2               = "syncml:auth-basic";

  private String carrier_notificationtype_2       = "wapPush";

  private String carrier_notificationtimeout_2    = "3600";

  private String carrier_bootstraptimeout_2       = "3600";

  private String carrier_notificationMaxRetries_2 = "3";

  private String carrier_phoneNumberpolicy_2      = "";

  private String carrier_bootstrapMaxRetries_2       = CarrierItem.DEFAULT_BOOTSTRAP_MAX_RETRIES;

  private String carrier_defaultBootstrapPinType_2 = "NETWPIN";

  private String carrier_defaultBootstrapUserPin_2      = Carrier.DEFAULT_BOOTSTRAP_USER_PIN;


  private String carrier_name_3                   = "中国联通";

  private String carrier_externalID_3             = "ChinaUnicom";

  private String carrier_country_3                = "CN";

  private String carrier_bootstrapNAOprofile_3    = "";

  private String carrier_authtype_3               = "syncml:auth-basic";

  private String carrier_notificationtype_3       = "wapPush";

  private String carrier_notificationtimeout_3    = "3600";

  private String carrier_bootstraptimeout_3       = "3600";

  private String carrier_notificationMaxRetries_3 = "3";

  private String carrier_phoneNumberpolicy_3      = "";
  
  private String carrier_bootstrapMaxRetries_3       = "2";

  private String carrier_defaultBootstrapPinType_3 = "USERNETWPIN";

  private String carrier_defaultBootstrapUserPin_3      = "123456";
  
  
  private String carrier_name_4                   = "中国联通1";

  private String carrier_externalID_4             = "ChinaUnicom";

  private String carrier_country_4                = "CN";

  private String carrier_bootstrapNAOprofile_4    = "";

  private String carrier_authtype_4               = "syncml:auth-basic";

  private String carrier_notificationtype_4       = "wapPush";

  private String carrier_notificationtimeout_4    = "3600";

  private String carrier_bootstraptimeout_4       = "3600";

  private String carrier_notificationMaxRetries_4 = "3";

  private String carrier_phoneNumberpolicy_4      = "";
  
  private String carrier_bootstrapMaxRetries_4       = "2";

  private String carrier_defaultBootstrapPinType_4 = "USERPIN";

  private String carrier_defaultBootstrapUserPin_4      = "123456";
  
  
  private String carrier_name_5                   = "中国联通2";

  private String carrier_externalID_5             = "ChinaUnicom";

  private String carrier_country_5                = "CN";

  private String carrier_bootstrapNAOprofile_5    = "";

  private String carrier_authtype_5               = "syncml:auth-basic";

  private String carrier_notificationtype_5       = "wapPush";

  private String carrier_notificationtimeout_5    = "3600";

  private String carrier_bootstraptimeout_5       = "3600";

  private String carrier_notificationMaxRetries_5 = "3";

  private String carrier_phoneNumberpolicy_5      = "";
  
  private String carrier_bootstrapMaxRetries_5       = "2";

  private String carrier_defaultBootstrapPinType_5 = "USERPINMAC";

  private String carrier_defaultBootstrapUserPin_5      = "123456";
  

  /*
   * (non-Javadoc)
   * 
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /*
   * (non-Javadoc)
   * 
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testLoadCarrierItems() throws Exception {
    CarrierTask task = new CarrierTask();
    List<CarrierItem> carriers = task.loadCarrierItems(new File(carrierDataFile));
    assertNotNull(carriers);
    assertTrue(carriers.size() == 5);

    CarrierItem c = (CarrierItem) carriers.get(0);
    assertEquals(c.getName(), carrier_name_1);
    assertEquals(c.getExternalID(), carrier_externalID_1);
    assertEquals(c.getCountry(), carrier_country_1);
    //assertEquals(c.getBootstrapNAPProfile(), carrier_bootstrapNAOprofile_1);
    assertEquals(c.getPhoneNumberPolicy(), carrier_phoneNumberpolicy_1);
    assertEquals(c.getAuthType(), carrier_authtype_1);
    assertEquals(c.getSmsCProperties(), carrier_bootstrapNAOprofile_1);// SMSCProperties??
    assertEquals(c.getNotificationType(), carrier_notificationtype_1);
    assertEquals(c.getNotificationTimeout(), carrier_notificationtimeout_1);
    assertEquals(c.getBootstrapTimeout(), carrier_bootstraptimeout_1);
    assertEquals(c.getNotificationMaxRetries(), carrier_notificationMaxRetries_1);    
    assertEquals(c.getDefaultBootstrapPinType(), "");
    assertEquals(c.getDefaultBootstrapUserPin(), "");
    assertEquals(c.getBootstrapMaxRetries(), "");

    c = (CarrierItem) carriers.get(1);
    assertEquals(c.getName(), carrier_name_2);
    assertEquals(c.getExternalID(), carrier_externalID_2);
    assertEquals(c.getCountry(), carrier_country_2);
    //assertEquals(c.getBootstrapNAPProfile(), carrier_bootstrapNAOprofile_2);
    assertEquals(c.getPhoneNumberPolicy(), carrier_phoneNumberpolicy_2);
    assertEquals(c.getAuthType(), carrier_authtype_2);
    assertEquals(c.getSmsCProperties(), carrier_bootstrapNAOprofile_2);// SMSCProperties??
    assertEquals(c.getNotificationType(), carrier_notificationtype_2);
    assertEquals(c.getNotificationTimeout(), carrier_notificationtimeout_2);
    assertEquals(c.getBootstrapTimeout(), carrier_bootstraptimeout_2);
    assertEquals(c.getNotificationMaxRetries(), carrier_notificationMaxRetries_2);

    assertEquals(c.getDefaultBootstrapPinType(), carrier_defaultBootstrapPinType_2);
    assertEquals(c.getDefaultBootstrapUserPin(), carrier_defaultBootstrapUserPin_2);
    assertEquals(c.getBootstrapMaxRetries(), carrier_bootstrapMaxRetries_2);

    c = (CarrierItem) carriers.get(2);
    assertEquals(c.getName(), carrier_name_3);
    assertEquals(c.getExternalID(), carrier_externalID_3);
    assertEquals(c.getCountry(), carrier_country_3);
    //assertEquals(c.getBootstrapNAPProfile(), carrier_bootstrapNAOprofile_3);
    assertEquals(c.getPhoneNumberPolicy(), carrier_phoneNumberpolicy_3);
    assertEquals(c.getAuthType(), carrier_authtype_3);
    assertEquals(c.getSmsCProperties(), carrier_bootstrapNAOprofile_3);// SMSCProperties??
    assertEquals(c.getNotificationType(), carrier_notificationtype_3);
    assertEquals(c.getNotificationTimeout(), carrier_notificationtimeout_3);
    assertEquals(c.getBootstrapTimeout(), carrier_bootstraptimeout_3);
    assertEquals(c.getNotificationMaxRetries(), carrier_notificationMaxRetries_3);
    assertEquals(c.getDefaultBootstrapPinType(), carrier_defaultBootstrapPinType_3);
    assertEquals(c.getDefaultBootstrapUserPin(), carrier_defaultBootstrapUserPin_3);
    assertEquals(c.getBootstrapMaxRetries(), carrier_bootstrapMaxRetries_3);

    c = (CarrierItem) carriers.get(3);
    assertEquals(c.getName(), carrier_name_4);
    assertEquals(c.getExternalID(), carrier_externalID_4);
    assertEquals(c.getCountry(), carrier_country_4);
    //assertEquals(c.getBootstrapNAPProfile(), carrier_bootstrapNAOprofile_4);
    assertEquals(c.getPhoneNumberPolicy(), carrier_phoneNumberpolicy_4);
    assertEquals(c.getAuthType(), carrier_authtype_4);
    assertEquals(c.getSmsCProperties(), carrier_bootstrapNAOprofile_4);// SMSCProperties??
    assertEquals(c.getNotificationType(), carrier_notificationtype_4);
    assertEquals(c.getNotificationTimeout(), carrier_notificationtimeout_4);
    assertEquals(c.getBootstrapTimeout(), carrier_bootstraptimeout_4);
    assertEquals(c.getNotificationMaxRetries(), carrier_notificationMaxRetries_4);
    assertEquals(c.getDefaultBootstrapPinType(), carrier_defaultBootstrapPinType_4);
    assertEquals(c.getDefaultBootstrapUserPin(), carrier_defaultBootstrapUserPin_4);
    assertEquals(c.getBootstrapMaxRetries(), carrier_bootstrapMaxRetries_4);
    
    c = (CarrierItem) carriers.get(4);
    assertEquals(c.getName(), carrier_name_5);
    assertEquals(c.getExternalID(), carrier_externalID_5);
    assertEquals(c.getCountry(), carrier_country_5);
    //assertEquals(c.getBootstrapNAPProfile(), carrier_bootstrapNAOprofile_5);
    assertEquals(c.getPhoneNumberPolicy(), carrier_phoneNumberpolicy_5);
    assertEquals(c.getAuthType(), carrier_authtype_5);
    assertEquals(c.getSmsCProperties(), carrier_bootstrapNAOprofile_5);// SMSCProperties??
    assertEquals(c.getNotificationType(), carrier_notificationtype_5);
    assertEquals(c.getNotificationTimeout(), carrier_notificationtimeout_5);
    assertEquals(c.getBootstrapTimeout(), carrier_bootstraptimeout_5);
    assertEquals(c.getNotificationMaxRetries(), carrier_notificationMaxRetries_5);
    assertEquals(c.getDefaultBootstrapPinType(), carrier_defaultBootstrapPinType_5);
    assertEquals(c.getDefaultBootstrapUserPin(), carrier_defaultBootstrapUserPin_5);
    assertEquals(c.getBootstrapMaxRetries(), carrier_bootstrapMaxRetries_5);
    
  }
  public void testCopy() throws Exception {
    CarrierTask task = new CarrierTask();
    List<CarrierItem> carriers = task.loadCarrierItems(new File(carrierDataFile));
    assertNotNull(carriers);
    assertTrue(carriers.size() == 5);
    CarrierBeanImpl carrierBean = new CarrierBeanImpl();
    List<Carrier> resultlist = new ArrayList<Carrier>();
    ManagementBeanFactory factory = new ManagementBeanFactoryDemo();
    for(CarrierItem item:carriers){
      Carrier carrier = carrierBean.newCarrierInstance();
      task.copy(factory , item , carrier);
      resultlist.add(carrier);
    }
    assertNotNull(resultlist);
    assertTrue(resultlist.size() == 5);

    Carrier c = (Carrier) resultlist.get(0);
    assertEquals(c.getName(), carrier_name_1);
    assertEquals(c.getExternalID(), carrier_externalID_1);
    //assertEquals(c.getCountry().getISOCode(), carrier_country_1);
    //assertEquals(c.getBootstrapProfileConfig(), carrier_bootstrapNAOprofile_1);
    assertEquals(c.getPhoneNumberPolicy(), carrier_phoneNumberpolicy_1);
    assertEquals(c.getServerAuthType(), carrier_authtype_1);
    assertEquals(c.getNotificationType(), carrier_notificationtype_1);
    assertEquals(String.valueOf(c.getNotificationStateTimeout()), carrier_notificationtimeout_1);
    assertEquals(String.valueOf(c.getBootstrapTimeout()), carrier_bootstraptimeout_1);
    assertEquals(String.valueOf(c.getNotificationMaxNumRetries()), carrier_notificationMaxRetries_1);
    assertEquals(c.getDefaultBootstrapPinType(), carrier_defaultBootstrapPinType_1);
    assertEquals(c.getDefaultBootstrapUserPin(), carrier_defaultBootstrapUserPin_1);
    assertEquals(String.valueOf(c.getBootstrapMaxRetries()), carrier_bootstrapMaxRetries_1);

    c = (Carrier) resultlist.get(1);
    assertEquals(c.getName(), carrier_name_2);
    assertEquals(c.getExternalID(), carrier_externalID_2);
    //assertEquals(c.getCountry().getISOCode(), carrier_country_2);
    //assertEquals(c.getBootstrapProfileConfig(), carrier_bootstrapNAOprofile_2);
    assertEquals(c.getPhoneNumberPolicy(), carrier_phoneNumberpolicy_2);
    assertEquals(c.getServerAuthType(), carrier_authtype_2);
    assertEquals(c.getNotificationType(), carrier_notificationtype_2);
    assertEquals(String.valueOf(c.getNotificationStateTimeout()), carrier_notificationtimeout_2);
    assertEquals(String.valueOf(c.getBootstrapTimeout()), carrier_bootstraptimeout_2);
    assertEquals(String.valueOf(c.getNotificationMaxNumRetries()), carrier_notificationMaxRetries_2);
    assertEquals(c.getDefaultBootstrapPinType(), ""+OMACPSecurityMethod.valueByString(carrier_defaultBootstrapPinType_2).getValue());
    assertEquals(c.getDefaultBootstrapUserPin(), carrier_defaultBootstrapUserPin_2);
    assertEquals(String.valueOf(c.getBootstrapMaxRetries()), carrier_bootstrapMaxRetries_2);

    c = (Carrier) resultlist.get(2);
    assertEquals(c.getName(), carrier_name_3);
    assertEquals(c.getExternalID(), carrier_externalID_3);
    //assertEquals(c.getCountry().getISOCode(), carrier_country_3);
    //assertEquals(c.getBootstrapProfileConfig(), carrier_bootstrapNAOprofile_3);
    assertEquals(c.getPhoneNumberPolicy(), carrier_phoneNumberpolicy_3);
    assertEquals(c.getServerAuthType(), carrier_authtype_3);
    assertEquals(c.getNotificationType(), carrier_notificationtype_3);
    assertEquals(String.valueOf(c.getNotificationStateTimeout()), carrier_notificationtimeout_3);
    assertEquals(String.valueOf(c.getBootstrapTimeout()), carrier_bootstraptimeout_3);
    assertEquals(String.valueOf(c.getNotificationMaxNumRetries()), carrier_notificationMaxRetries_3);
    assertEquals(c.getDefaultBootstrapPinType(), ""+OMACPSecurityMethod.valueByString(carrier_defaultBootstrapPinType_3).getValue());
    assertEquals(c.getDefaultBootstrapUserPin(), carrier_defaultBootstrapUserPin_3);
    assertEquals(String.valueOf(c.getBootstrapMaxRetries()), carrier_bootstrapMaxRetries_3);        
   
    //test default boot userPin error
    carriers = task.loadCarrierItems(new File(carrierDataFileErr));
    assertNotNull(carriers);
    assertTrue(carriers.size() == 1);
    Carrier carrier = carrierBean.newCarrierInstance();
    try {
      task.copy(factory , carriers.get(0) , carrier);
      assertTrue(false);
    } catch (Exception e) {
      e.printStackTrace();
      assertTrue(true);
    }
  }
  public void testprocessCarriers() throws Exception {
    Setup setup = new SetupImpl();
    Console console = new ConsoleImpl();
    setup.setConsole(console);
    
    CarrierTask task = new CarrierTask();
    task.setSetup(setup);
    
    List<String> filenames = new ArrayList<String>();
    filenames.add(carrierDataFile);
    task.setFilenames(filenames);
    ManagementBeanFactoryDemo managementBeanFactory = new ManagementBeanFactoryDemo();
    task.setManagementBeanFactory(managementBeanFactory);  
    task.process();    
    CarrierBeanImpl carrierBean = managementBeanFactory.getCarrierBean();
    List<Carrier> carriers = carrierBean.getUpdateCarriers();
    
    assertNotNull(carriers);
    assertTrue(carriers.size() == 5);
    
    Carrier c = (Carrier) carriers.get(0);
    assertEquals(c.getName(), carrier_name_1);
    assertEquals(c.getExternalID(), carrier_externalID_1);
    //assertEquals(c.getCountry().getISOCode(), carrier_country_1);
    //assertEquals(c.getBootstrapProfileConfig(), carrier_bootstrapNAOprofile_1);
    assertEquals(c.getPhoneNumberPolicy(), carrier_phoneNumberpolicy_1);
    assertEquals(c.getServerAuthType(), carrier_authtype_1);
    assertEquals(c.getNotificationType(), carrier_notificationtype_1);
    assertEquals(String.valueOf(c.getNotificationStateTimeout()), carrier_notificationtimeout_1);
    assertEquals(String.valueOf(c.getBootstrapTimeout()), carrier_bootstraptimeout_1);
    assertEquals(String.valueOf(c.getNotificationMaxNumRetries()), carrier_notificationMaxRetries_1);
    assertEquals(c.getDefaultBootstrapPinType(), carrier_defaultBootstrapPinType_1);
    assertEquals(c.getDefaultBootstrapUserPin(), carrier_defaultBootstrapUserPin_1);
    assertEquals(String.valueOf(c.getBootstrapMaxRetries()), carrier_bootstrapMaxRetries_1);

    c = (Carrier) carriers.get(1);
    assertEquals(c.getName(), carrier_name_2);
    assertEquals(c.getExternalID(), carrier_externalID_2);
    //assertEquals(c.getCountry().getISOCode(), carrier_country_2);
    //assertEquals(c.getBootstrapProfileConfig(), carrier_bootstrapNAOprofile_2);
    assertEquals(c.getPhoneNumberPolicy(), carrier_phoneNumberpolicy_2);
    assertEquals(c.getServerAuthType(), carrier_authtype_2);
    assertEquals(c.getNotificationType(), carrier_notificationtype_2);
    assertEquals(String.valueOf(c.getNotificationStateTimeout()), carrier_notificationtimeout_2);
    assertEquals(String.valueOf(c.getBootstrapTimeout()), carrier_bootstraptimeout_2);
    assertEquals(String.valueOf(c.getNotificationMaxNumRetries()), carrier_notificationMaxRetries_2);
    assertEquals(c.getDefaultBootstrapPinType(), ""+OMACPSecurityMethod.valueByString(carrier_defaultBootstrapPinType_2).getValue());
    assertEquals(c.getDefaultBootstrapUserPin(), carrier_defaultBootstrapUserPin_2);
    assertEquals(String.valueOf(c.getBootstrapMaxRetries()), carrier_bootstrapMaxRetries_2);

    c = (Carrier) carriers.get(2);
    assertEquals(c.getName(), carrier_name_3);
    assertEquals(c.getExternalID(), carrier_externalID_3);
    //assertEquals(c.getCountry().getISOCode(), carrier_country_3);
    //assertEquals(c.getBootstrapProfileConfig(), carrier_bootstrapNAOprofile_3);
    assertEquals(c.getPhoneNumberPolicy(), carrier_phoneNumberpolicy_3);
    assertEquals(c.getServerAuthType(), carrier_authtype_3);
    assertEquals(c.getNotificationType(), carrier_notificationtype_3);
    assertEquals(String.valueOf(c.getNotificationStateTimeout()), carrier_notificationtimeout_3);
    assertEquals(String.valueOf(c.getBootstrapTimeout()), carrier_bootstraptimeout_3);
    assertEquals(String.valueOf(c.getNotificationMaxNumRetries()), carrier_notificationMaxRetries_3);
    assertEquals(c.getDefaultBootstrapPinType(), ""+OMACPSecurityMethod.valueByString(carrier_defaultBootstrapPinType_3).getValue());
    assertEquals(c.getDefaultBootstrapUserPin(), carrier_defaultBootstrapUserPin_3);
    assertEquals(String.valueOf(c.getBootstrapMaxRetries()), carrier_bootstrapMaxRetries_3);

    //test default boot userPin error
    List<String> filenames2 = new ArrayList<String>();
    filenames2.add(carrierDataFileErr);
    task.setFilenames(filenames2);
    try {
      task.process();
      assertTrue(false);
    } catch (Exception e) {
      e.printStackTrace();
      assertTrue(true);
    }
  }
}

class ManagementBeanFactoryDemo extends ManagementBeanFactory {  
  private CarrierBeanImpl carrierBean = new CarrierBeanImpl();

  @Override
  public void beginTransaction() throws DMException {
    // TODO Auto-generated method stub

  }
  public OTAClientProvJobBean createOTAClientProvJobBean(OTAInventory otaInventory, SmsSender sender) {
    return null;
  }
  @Override
  public void commit() {
    // TODO Auto-generated method stub

  }
  
  @Override
  public AutomaticProvisionJobBean createAutoProvisionJobBean() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ClientProvTemplateBean createClientProvTemplateBean() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public DDFTreeBean createDDFTreeBean() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public DeviceBean createDeviceBean() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public DeviceLogBean createDeviceLogBean() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ModelBean createModelBean() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ProfileAssignmentBean createProfileAssignmentBean() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ProfileMappingBean createProfileMappingBean() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ProfileTemplateBean createProfileTemplateBean() {
    ProfileTemplateBeanImp2 profileTemplateBean = new ProfileTemplateBeanImp2();
    return profileTemplateBean;
  }

  @Override
  public ProvisionJobBean createProvisionJobBean() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public SearchBean createSearchBean() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public SubscriberBean createSubcriberBean() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public UpdateImageBean createUpdateImageBean() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Subject getSubject() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isOpen() throws DMException {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  protected void releaseResource() {
    // TODO Auto-generated method stub

  }

  @Override
  public void rollback() {
    // TODO Auto-generated method stub

  }

  @Override
  public void setSubject(Subject subject) {
    // TODO Auto-generated method stub

  }

  @Override
  public com.npower.dm.management.CarrierBean createCarrierBean() {
    return carrierBean;     
  }

  @Override
  public com.npower.dm.management.CountryBean createCountryBean() {
    CountryBeanImpl countryBean = new CountryBeanImpl();
    return countryBean;
  }

  @Override
  public com.npower.dm.management.ProfileConfigBean createProfileConfigBean() {
    ProfileConfigBeanImpl profileConfigBean = new ProfileConfigBeanImpl();
    return profileConfigBean;
  }
  public CarrierBeanImpl getCarrierBean() {
    return carrierBean;
  }
  public void setCarrierBean(CarrierBeanImpl carrierBean) {
    this.carrierBean = carrierBean;
  }
  /* (non-Javadoc)
   * @see com.npower.dm.management.ManagementBeanFactory#createClientProvAuditLogBean()
   */
  @Override
  public ClientProvAuditLogBean createClientProvAuditLogBean() {
    // TODO Auto-generated method stub
    return null;
  }
  @Override
  public SoftwareBean createSoftwareBean() {
    // TODO Auto-generated method stub
    return null;
  }
  @Override
  public ServiceProviderBean createServiceProviderBean() {
    // TODO Auto-generated method stub
    return null;
  }
  @Override
  public SoftwareTrackingBean createSoftwareTrackingBean() {
    // TODO Auto-generated method stub
    return null;
  }
  @Override
  public SoftwareTopListBean createSoftwareTopListBean() {
    // TODO Auto-generated method stub
    return null;
  }
  @Override
  public Object createBean(Class<?> clazz) throws InstantiationException, IllegalAccessException {
    // TODO Auto-generated method stub
    return null;
  }
  @Override
  public ModelClassificationBean createModelClassificationBean() {
    // TODO Auto-generated method stub
    return null;
  }
  @Override
  public SoftwareEvaluateBean createSoftwareEvaluateBean() {
    // TODO Auto-generated method stub
    return null;
  }
}

class CarrierBeanImpl implements com.npower.dm.management.CarrierBean {
  private List<Carrier> updateCarriers = new ArrayList<Carrier>();
  public Carrier newCarrierInstance() {
    Carrier carrier = new CarrierEntity();
    return carrier;
  }

  public void update(Carrier carrier) {
      updateCarriers.add(carrier);
  }

  public void delete(Carrier carrier) throws DMException {
    // TODO Auto-generated method stub

  }

  public Carrier findCarrierByPhoneNumberPolicy(String phoneNumber) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public List<Carrier> findCarriers(String whereClause) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public List<Carrier> getAllCarriers() throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public Carrier getCarrierByExternalID(String id) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public Carrier getCarrierByID(String id) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public Carrier newCarrierInstance(Country country, String carrierExternalId, String name) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public ManagementBeanFactory getManagementBeanFactory() {
    // TODO Auto-generated method stub
    return null;
  }

  public List<Carrier> getUpdateCarriers() {
    return updateCarriers;
  }

  public void setUpdateCarriers(List<Carrier> updateCarriers) {
    this.updateCarriers = updateCarriers;
  }
}

class CountryBeanImpl implements com.npower.dm.management.CountryBean {
  public Country getCountryByISOCode() {
    Country country = new CountryEntity();
    return country;
  }

  public void delete(Country country) throws DMException {
    // TODO Auto-generated method stub

  }

  public List<Country> getAllCountries() throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public Country getCountryByCountryCode(String countryCode) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public Country getCountryByID(long id) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public Country getCountryByISOCode(String isoCode) throws DMException {
    Country country = new CountryEntity();
    country.setCountryCode("CN");
    country.setName("ChinaMobibe");
    return country;
  }

  public Session getHibernateSession() {
    // TODO Auto-generated method stub
    return null;
  }

  public Country newCountryInstance() throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public Country newCountryInstance(String isoCode, String countryCode, String countryName, String trunkCode,
      boolean displayCountryCode, boolean displayTrunkCode, boolean displayPrefix) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public void update(Country country) throws DMException {
    // TODO Auto-generated method stub

  }

  public ManagementBeanFactory getManagementBeanFactory() {
    // TODO Auto-generated method stub
    return null;
  }
}

class ProfileConfigBeanImpl implements com.npower.dm.management.ProfileConfigBean {
  public ProfileConfig getProfileConfigByName() {
    ProfileConfig profileConfig = new ProfileConfigEntity();
    profileConfig.setName("profileconfigName");  
    return profileConfig;
  }

  public void deleteProfileConfig(ProfileConfig config) throws DMException {
    // TODO Auto-generated method stub

  }

  public List<ProfileConfig> findProfileConfigs(String whereClause) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public ProfileConfig getProfileConfigByID(String id) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public ProfileConfig getProfileConfigByName(String carrierExtID, String categoryName, String name) throws DMException {
    ProfileConfig profileConfig = null;
    return profileConfig;
  }

  public ProfileConfig newProfileConfigInstance(String name, Carrier carrier, ProfileTemplate template,
      String profileType) throws DMException {
    ProfileConfig profileConfig = new ProfileConfigEntity();
    return profileConfig;
  }

  public void setAttributeValue(ProfileConfig config, String name, InputStream value) throws DMException, IOException {
    // TODO Auto-generated method stub

  }

  public void setAttributeValue(ProfileConfig config, String name, String value) throws DMException {
    // TODO Auto-generated method stub

  }

  public void setAttributeValue(ProfileConfig config, String name, String[] value) throws DMException {
    // TODO Auto-generated method stub

  }

  public void setAttributeValue(ProfileConfig config, String name, InputStream[] value) throws DMException, IOException {
    // TODO Auto-generated method stub

  }

  public void update(ProfileConfig profile) throws DMException {
    // TODO Auto-generated method stub

  }

  public ManagementBeanFactory getManagementBeanFactory() {
    // TODO Auto-generated method stub
    return null;
  }

  public ProfileConfig getProfileConfigByExternalID(String externalID) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public ProfileConfig newProfileConfigInstance(String externalID, String name, Carrier carrier,
      ProfileTemplate template, String profileType) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }
}