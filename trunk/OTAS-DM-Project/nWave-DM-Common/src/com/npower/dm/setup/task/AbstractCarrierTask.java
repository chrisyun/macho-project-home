package com.npower.dm.setup.task;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;
import com.npower.dm.management.CountryBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.setup.core.SetupException;
import com.npower.wap.omacp.OMACPSecurityMethod;

public abstract class AbstractCarrierTask extends DMTask {
  private List<String> filenames;

  /**
   * 
   */
  public AbstractCarrierTask() {
    super();
  }

  private Digester createCarrierDigester() {
    // Initialize the digester
    Digester digester = new Digester();
    digester.setValidating(false);

    // Setup
    digester.addObjectCreate("*/Carrier", CarrierItem.class);
    digester.addBeanPropertySetter("*/Carrier/ExternalID", "externalID");
    digester.addBeanPropertySetter("*/Carrier/Name", "name");
    digester.addBeanPropertySetter("*/Carrier/Country", "country");
    digester.addBeanPropertySetter("*/Carrier/PhoneNumberPolicy", "phoneNumberPolicy");
    digester.addBeanPropertySetter("*/Carrier/SMSCProperties", "smsCProperties");
    digester.addBeanPropertySetter("*/Carrier/AuthType", "authType");
    digester.addBeanPropertySetter("*/Carrier/NotificationType", "notificationType");
    digester.addBeanPropertySetter("*/Carrier/NotificationTimeout", "notificationTimeout");
    digester.addBeanPropertySetter("*/Carrier/BootstrapTimeout", "bootstrapTimeout");
    digester.addBeanPropertySetter("*/Carrier/NotificationMaxRetries", "notificationMaxRetries");

    digester.addBeanPropertySetter("*/Carrier/BootstrapNapProfile", "bootstrapNapProfile");
    digester.addBeanPropertySetter("*/Carrier/BootstrapDmProfile", "bootstrapDmProfile");

    digester.addBeanPropertySetter("*/Carrier/BootstrapMaxRetries", "bootstrapMaxRetries");
    digester.addBeanPropertySetter("*/Carrier/DefaultBootstrapPinType", "defaultBootstrapPinType");
    digester.addBeanPropertySetter("*/Carrier/DefaultBootstrapUserPin", "defaultBootstrapUserPin");
    
//    digester.addObjectCreate("*/Carrier/BootstrapNAPProfile", ProfileConfigItem.class);
//    digester.addBeanPropertySetter("*/Carrier/BootstrapNAPProfile/ProfileName", "profileName");
//    digester.addBeanPropertySetter("*/Carrier/BootstrapNAPProfile/Template", "template");
//    digester.addBeanPropertySetter("*/Carrier/BootstrapNAPProfile/NAPProfile", "napProfile");
//    digester.addBeanPropertySetter("*/Carrier/BootstrapNAPProfile/ProxyProfile", "proxyProfile");
//    digester.addBeanPropertySetter("*/Carrier/BootstrapNAPProfile/Description", "description");
//
//    digester.addObjectCreate("*/Carrier/BootstrapNAPProfile/Attributes/Attribute", AttributeItem.class);
//    digester.addBeanPropertySetter("*/Carrier/BootstrapNAPProfile/Attributes/Attribute/Name", "name");
//    digester.addBeanPropertySetter("*/Carrier/BootstrapNAPProfile/Attributes/Attribute/Value", "value");
//
//    digester.addCallMethod("*/Carrier/BootstrapNAPProfile/Attributes/Attribute/Value", "setFileName", 1);
//    digester.addSetProperties("*/Carrier/BootstrapNAPProfile/Attributes/Attribute/Value","filename","fileName");    
//    digester.addSetNext("*/Carrier/BootstrapNAPProfile/Attributes/Attribute", "addAttribute");
//    digester.addSetNext("*/Carrier/BootstrapNAPProfile", "setProfileConfig");

    digester.addSetNext("*/Carrier", "add");

    return (digester);
  }

  @Override
  protected void process() throws SetupException {
    List<String> filenames = this.getFilenames();
    ManagementBeanFactory factory;
    try {
      factory = this.getManagementBeanFactory();
    } catch (DMException e1) {
      throw new SetupException("getManagementBeanFactory() is error!", e1);
    }

    for (String filename : filenames) {
      File file = new File(filename);
      if (!file.isAbsolute()) {
        file = new File(this.getSetup().getWorkDir(), filename);
      }
      List<CarrierItem> items = this.loadCarrierItems(file);
      for (CarrierItem item : items) {
        processCarrierItem(factory, item);
      }
    }
  }

  public void copy(ManagementBeanFactory factory, CarrierItem item, Carrier carrier) throws DMException {
    carrier.setExternalID(item.getExternalID());
    carrier.setName(item.getName());
    carrier.setNotificationMaxNumRetries(Long.valueOf(item.getNotificationMaxRetries()));
    carrier.setNotificationProperties(item.getSmsCProperties());
    carrier.setNotificationStateTimeout(Long.valueOf(item.getNotificationTimeout()));
    carrier.setNotificationType(item.getNotificationType());
    carrier.setPhoneNumberPolicy(item.getPhoneNumberPolicy());
    carrier.setServerAuthType(item.getAuthType());
    carrier.setBootstrapTimeout(Long.valueOf(item.getBootstrapTimeout()));

    if(StringUtils.isEmpty(item.getDefaultBootstrapUserPin())){
      carrier.setDefaultBootstrapUserPin(Carrier.DEFAULT_BOOTSTRAP_USER_PIN);
    }else{
      carrier.setDefaultBootstrapUserPin(item.getDefaultBootstrapUserPin());
    }
    if(StringUtils.isEmpty(item.getBootstrapMaxRetries())){
      carrier.setBootstrapMaxRetries(Integer.parseInt(CarrierItem.DEFAULT_BOOTSTRAP_MAX_RETRIES));
    }else{
      carrier.setBootstrapMaxRetries(Integer.parseInt(item.getBootstrapMaxRetries()));      
    }
    if(StringUtils.isEmpty(item.getDefaultBootstrapPinType())){
      carrier.setDefaultBootstrapPinType(OMACPSecurityMethod.USERPIN.toString());
    }else{
      carrier.setDefaultBootstrapPinType("" + OMACPSecurityMethod.valueByString(item.getDefaultBootstrapPinType()).getValue());
    }

    CountryBean countryBean = factory.createCountryBean();
    Country country = countryBean.getCountryByISOCode(item.getCountry());
    carrier.setCountry(country);
  }

  public List<CarrierItem> loadCarrierItems(File file) throws SetupException {
    List<CarrierItem> result = new ArrayList<CarrierItem>();
    try {
      Digester digester = this.createCarrierDigester();
      digester.push(result);
      digester.parse(new FileInputStream(file));
      return result;
    } catch (Exception e) {
      throw new SetupException(e);
    }
  }

  public List<String> getFilenames() {
    if (this.filenames == null || this.filenames.size() == 0) {
      return this.getPropertyValues("import.files");
    }
    return this.filenames;
  }

  @Override
  protected void rollback() throws SetupException {    

  }

  public void setFilenames(List<String> filenames) {
    this.filenames = filenames;
  }

  /**
   * @param factory
   * @param carrierBean
   * @param item
   * @throws SetupException
   */
  protected abstract void processCarrierItem(ManagementBeanFactory factory, CarrierItem item)
      throws SetupException;

}
