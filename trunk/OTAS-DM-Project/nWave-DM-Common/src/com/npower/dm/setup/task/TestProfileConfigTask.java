package com.npower.dm.setup.task;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import junit.framework.TestCase;

import com.npower.cp.OTAInventory;
import com.npower.dm.core.AttributeTypeValue;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileAttributeType;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.hibernate.entity.CarrierEntity;
import com.npower.dm.hibernate.entity.ProfileCategoryEntity;
import com.npower.dm.hibernate.entity.ProfileConfigEntity;
import com.npower.dm.hibernate.entity.ProfileTemplateEntity;
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
import com.npower.setup.core.SetupException;
import com.npower.setup.core.SetupImpl;
import com.npower.sms.client.SmsSender;

public class TestProfileConfigTask extends TestCase {

  private static final String carrier_1 = "ChinaMobile";
  private static final String napProfile_1 = "";
  private static final String profileName_1 = "CMNet";
  private static final String proxyProfile_1 = "";
  private static final String template_1 = "NAP Default Template";
  private static final String filename = "D:/Zhao/MyWorkspace/OTAS-Setup-Common/metadata/test/dm/profiles/profile.config.default.xml";
  private static final String description_1 = "CMNetdesc";
  
  private static final String carrier_2 = "ChinaMobile";
  private static final String napProfile_2 = "CMWap";
  private static final String profileName_2 = "CMWAP";
  private static final String proxyProfile_2 = "";
  private static final String template_2 = "Proxy Default Template";
  private static final String description_2 = "CMWAPdesc";

  private static final String carrier_3 = "ChinaMobile";
  private static final String napProfile_3 = "";
  private static final String profileName_3 = "OTAS PIM";
  private static final String proxyProfile_3 = "CMWAP";
  private static final String template_3 = "SyncDS Default Template";
  private static final String description_3 = "OTAS PIM desc";
  
  private static final String carrier_4 = "ChinaMobile";
  private static final String napProfile_4 = "";
  private static final String profileName_4 = "CMMMS";
  private static final String proxyProfile_4 = "CMWAP";
  private static final String template_4 = "MMS Default Template";
  private static final String description_4 = "CMMMSdesc";
  
  private static final String carrier_5 = "ChinaMobile";
  private static final String napProfile_5 = "CMNet";
  private static final String profileName_5 = "GMail";
  private static final String proxyProfile_5 = "";
  private static final String template_5 = "Email Default Template";
  private static final String filepath = "D:/Zhao/MyWorkspace/OTAS-Setup-Common/metadata/test/dm/profiles/profile.config.default.xml";
  private static final String description_5 = "GMaildesc";

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testloadProfileConfigItems() throws SetupException{
    ProfileConfigTask task = new ProfileConfigTask();
    List<ProfileConfigItem> profileConfigs = task.loadProfileConfigItems(new File(filepath));
    assertNotNull(profileConfigs);
    assertTrue(profileConfigs.size() == 5);    
    
    ProfileConfigItem profile = (ProfileConfigItem) profileConfigs.get(0);
    assertEquals(profile.getCarrier(), carrier_1);
    assertEquals(profile.getNapProfile(),napProfile_1);
    assertEquals(profile.getProfileName(),profileName_1);
    assertEquals(profile.getProxyProfile(),proxyProfile_1);
    assertEquals(profile.getTemplate(),template_1);
    assertEquals(profile.getDescription(),description_1);
    assertNotNull(profile.getAttributes());
    assertTrue(profile.getAttributes().size() > 0); 
    AttributeItem attribute = (AttributeItem)profile.getAttributes().get(2);
    assertEquals(attribute.getFileName(),filename);
    attribute = (AttributeItem)profile.getAttributes().get(3);
    assertEquals(attribute.getFileName(),filename);
    
    profile = (ProfileConfigItem) profileConfigs.get(1);
    assertEquals(profile.getCarrier(), carrier_2);
    assertEquals(profile.getNapProfile(),napProfile_2);
    assertEquals(profile.getProfileName(),profileName_2);
    assertEquals(profile.getProxyProfile(),proxyProfile_2);
    assertEquals(profile.getTemplate(),template_2);
    assertEquals(profile.getDescription(),description_2);
    assertNotNull(profile.getAttributes());
    assertTrue(profile.getAttributes().size() > 0);
    
    profile = (ProfileConfigItem) profileConfigs.get(2);
    assertEquals(profile.getCarrier(), carrier_3);
    assertEquals(profile.getNapProfile(),napProfile_3);
    assertEquals(profile.getProfileName(),profileName_3);
    assertEquals(profile.getProxyProfile(),proxyProfile_3);
    assertEquals(profile.getTemplate(),template_3);
    assertEquals(profile.getDescription(),description_3);
    assertNotNull(profile.getAttributes());
    assertTrue(profile.getAttributes().size() > 0);    

    profile = (ProfileConfigItem) profileConfigs.get(3);
    assertEquals(profile.getCarrier(), carrier_4);
    assertEquals(profile.getNapProfile(),napProfile_4);
    assertEquals(profile.getProfileName(),profileName_4);
    assertEquals(profile.getProxyProfile(),proxyProfile_4);
    assertEquals(profile.getTemplate(),template_4);
    assertEquals(profile.getDescription(),description_4);
    assertNotNull(profile.getAttributes());
    assertTrue(profile.getAttributes().size() > 0);    
    
    profile = (ProfileConfigItem) profileConfigs.get(4);
    assertEquals(profile.getCarrier(), carrier_5);
    assertEquals(profile.getNapProfile(),napProfile_5);
    assertEquals(profile.getProfileName(),profileName_5);
    assertEquals(profile.getProxyProfile(),proxyProfile_5);
    assertEquals(profile.getTemplate(),template_5);
    assertEquals(profile.getDescription(),description_5);
    assertNotNull(profile.getAttributes());
    assertTrue(profile.getAttributes().size() > 0);    
  }
  
  public void testprocess() throws SetupException{
    Setup setup = new SetupImpl();
    Console console = new ConsoleImpl();
    setup.setConsole(console);
    
    ProfileConfigTask task = new ProfileConfigTask();
    task.setSetup(setup);
    
    List<String> filenames = new ArrayList<String>();
    filenames.add(filepath);
    task.setFilenames(filenames);
    ManagementBeanFactoryDemo2 managementBeanFactory = new ManagementBeanFactoryDemo2();
    task.setManagementBeanFactory(managementBeanFactory);  
    task.process();
    ProfileConfigBeanImpl2 profileConfigBean = managementBeanFactory.getProfileConfigBean();
    List<ProfileConfig> profiles = profileConfigBean.getProfileConfigs();
    assertNotNull(profiles);
    assertTrue(profiles.size() == 5);    
  }
}




class ManagementBeanFactoryDemo2 extends ManagementBeanFactory {  
  private ProfileConfigBeanImpl2 profileConfigBean = new ProfileConfigBeanImpl2();

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
    CarrierBeanImpl2 carrierBean = new CarrierBeanImpl2();
    return carrierBean;     
  }

  @Override
  public com.npower.dm.management.CountryBean createCountryBean() {
    CountryBeanImpl countryBean = new CountryBeanImpl();
    return countryBean;
  }

  @Override
  public com.npower.dm.management.ProfileConfigBean createProfileConfigBean() {    
    return profileConfigBean;
  }
  public ProfileConfigBeanImpl2 getProfileConfigBean() {
    return profileConfigBean;
  }
  public void setProfileConfigBean(ProfileConfigBeanImpl2 profileConfigBean) {
    this.profileConfigBean = profileConfigBean;
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

class CarrierBeanImpl2 implements com.npower.dm.management.CarrierBean {
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

class ProfileConfigBeanImpl2 implements com.npower.dm.management.ProfileConfigBean {
  private List<ProfileConfig> profileConfigs = new ArrayList<ProfileConfig>();
  public ProfileConfig getProfileConfigByName() {
    ProfileConfig profileConfig = new ProfileConfigEntity();
    profileConfig.setName("profileconfigName");  
    return profileConfig;
  }

  public void getUpdateProfileConfig(ProfileConfig profileConfig) {    
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

  public ProfileConfig getProfileConfigByExternalID(String id) throws DMException {
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
    profileConfigs.add(profile);
  }

  public ManagementBeanFactory getManagementBeanFactory() {
    // TODO Auto-generated method stub
    return null;
  }

  public List<ProfileConfig> getProfileConfigs() {
    return profileConfigs;
  }

  public void setProfileConfigs(List<ProfileConfig> profileConfigs) {
    this.profileConfigs = profileConfigs;
  }

  public ProfileConfig newProfileConfigInstance(String externalID, String name, Carrier carrier,
      ProfileTemplate template, String profileType) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }
}
class ProfileTemplateBeanImp2 implements com.npower.dm.management.ProfileTemplateBean{

  public void delete(AttributeTypeValue attributeTypeValue) throws DMException {
    // TODO Auto-generated method stub
    
  }

  public void delete(ProfileAttribute attr) throws DMException {
    // TODO Auto-generated method stub
    
  }

  public void delete(ProfileTemplate template) throws DMException {
    // TODO Auto-generated method stub
    
  }

  public void deleteAttributeType(ProfileAttributeType type) throws DMException {
    // TODO Auto-generated method stub
    
  }

  public void deleteCategory(ProfileCategory profileCategory) throws DMException {
    // TODO Auto-generated method stub
    
  }

  public List<ProfileAttributeType> findProfileAttributeTypes(String whereClause) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public List<ProfileAttribute> findProfileAttributes(String clause) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public List<ProfileCategory> findProfileCategories(String whereClause) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Retrieve all of ProfileCategories
   * 
   * @return List Array of CarrierEntity
   * @throws DMException
   */
  public List<ProfileCategory> getAllOfProfileCategories() throws DMException {
    return null;
  }

  public List<ProfileTemplate> findTemplates(String clause) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public AttributeTypeValue getAttributeTypeValueByID(String id) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public ProfileAttribute getProfileAttributeByID(String id) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public ProfileAttribute getProfileAttributeByName(String templateName, String name) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public ProfileAttributeType getProfileAttributeTypeByID(String id) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public ProfileAttributeType getProfileAttributeTypeByName(String name) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public ProfileCategory getProfileCategoryByID(String id) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public ProfileCategory getProfileCategoryByName(String name) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public ProfileTemplate getTemplateByID(String id) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public ProfileTemplate getTemplateByName(String name) throws DMException {
    ProfileTemplate profileTemplate = new ProfileTemplateEntity();
    ProfileCategory nwDmProfileCategory = new ProfileCategoryEntity();
    nwDmProfileCategory.setName("test");
    profileTemplate.setProfileCategory(nwDmProfileCategory );
    return profileTemplate;
  }

  public int importCategory(InputStream in) throws DMException {
    // TODO Auto-generated method stub
    return 0;
  }

  public int importProfileAttributeType(InputStream in) throws DMException {
    // TODO Auto-generated method stub
    return 0;
  }

  public int importProfileTemplates(InputStream in) throws DMException {
    // TODO Auto-generated method stub
    return 0;
  }

  public ProfileAttribute newAttributeInstance() throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public ProfileAttributeType newAttributeTypeInstance() throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public AttributeTypeValue newAttributeTypeValueInstance(ProfileAttributeType type) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public ProfileCategory newProfileCategoryInstance() throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public ProfileCategory newProfileCategoryInstance(String name, String description) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public ProfileTemplate newTemplateInstance() throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public List<ProfileCategory> parseCategory(InputStream in) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public List<ProfileAttributeType> parseProfileAttributeType(InputStream in) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public List<ProfileTemplate> parseProfileTemplate(InputStream in) throws DMException {
    // TODO Auto-generated method stub
    return null;
  }

  public void update(AttributeTypeValue attributeTypeValue) throws DMException {
    // TODO Auto-generated method stub
    
  }

  public void update(ProfileAttribute attribute) throws DMException {
    // TODO Auto-generated method stub
    
  }

  public void update(ProfileTemplate template) throws DMException {
    // TODO Auto-generated method stub
    
  }

  public void updateAttributeType(ProfileAttributeType attributeType) throws DMException {
    // TODO Auto-generated method stub
    
  }

  public void updateCategory(ProfileCategory category) throws DMException {
    // TODO Auto-generated method stub
    
  }

  public ManagementBeanFactory getManagementBeanFactory() {
    // TODO Auto-generated method stub
    return null;
  }
  
}