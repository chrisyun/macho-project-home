package com.npower.dm.setup.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileConfigBean;
import com.npower.dm.management.ProfileTemplateBean;
import com.npower.setup.core.SetupException;

public class ProfileConfigTask extends DMTask {
  private List<String> filenames;

  @Override
  protected void process() throws SetupException {
    List<String> filenames = this.getFilenames();
    ManagementBeanFactory factory;
    try {
      factory = this.getManagementBeanFactory();
    } catch (DMException e1) {
      throw new SetupException("get ManagementBeanFactory is error!", e1);
    }
    ProfileConfigBean profileConfigBean = factory.createProfileConfigBean();
    CarrierBean carrierBean = factory.createCarrierBean();
    ProfileTemplateBean profileTempBean = factory.createProfileTemplateBean();          

    for (String filename : filenames) {
      File file = new File(filename);
      if (!file.isAbsolute()) {
        file = new File(this.getSetup().getWorkDir(), filename);
      }
      List<ProfileConfigItem> items = this.loadProfileConfigItems(file);
      for (ProfileConfigItem item : items) {
        try {
          Carrier carrier = carrierBean.getCarrierByExternalID(item.getCarrier());
          ProfileTemplate template = profileTempBean.getTemplateByName(item.getTemplate());
          String profileType = template.getProfileCategory().getName(); 
          ProfileConfig profileConfig = profileConfigBean.getProfileConfigByName(carrier.getExternalID(), template.getProfileCategory().getName(), item.getProfileName());
          if (profileConfig == null && StringUtils.isNotEmpty(item.getExternalID())) {
             profileConfig = profileConfigBean.getProfileConfigByExternalID(item.getExternalID());
          }
          if (profileConfig != null) {
             this.getSetup().getConsole().println("\t\t[" + profileConfig.getName() + "] exists, don't install!");                  
             continue;
          }

          factory.beginTransaction();
          profileConfig = profileConfigBean.newProfileConfigInstance(item.getProfileName(), carrier, template, profileType);
          if (StringUtils.isNotEmpty(item.getExternalID())) {
            profileConfig.setExternalID(item.getExternalID());
          }
          profileConfig.setDescription(item.getDescription());
          String napProfileName = item.getNapProfile();
          if (StringUtils.isNotEmpty(napProfileName)) {
             ProfileConfig napProfileConfig = profileConfigBean.getProfileConfigByName(carrier.getExternalID(), ProfileCategory.NAP_CATEGORY_NAME, napProfileName);
             if (napProfileConfig == null) {
                throw new DMException("Could not found linked NAP profile by name: " + napProfileName);
             }
             profileConfig.setNAPProfile(napProfileConfig);
          }
          String proxyProfileName = item.getProxyProfile();
          if (StringUtils.isNotEmpty(proxyProfileName)) {
             ProfileConfig proxyProfileConfig = profileConfigBean.getProfileConfigByName(carrier.getExternalID(), ProfileCategory.PROXY_CATEGORY_NAME, proxyProfileName);
             if (proxyProfileConfig == null) {
                throw new DMException("Could not found linked Proxy profile by name: " + proxyProfileName);
             }
             profileConfig.setProxyProfile(proxyProfileConfig);
          }
          profileConfig.setIsUserProfile(item.isUserProfile());

          profileConfigBean.update(profileConfig);
          
          for (AttributeItem attribute : item.getAttributes()) {
            try {
              if (StringUtils.isEmpty(attribute.getFileName())){
                profileConfigBean.setAttributeValue(profileConfig, attribute.getName(), attribute.getValue());
              } else {
                File attributefile =  new File(attribute.getFileName());
                if(!attributefile.isAbsolute()){
                  attributefile = new File(this.getSetup().getWorkDir(), attribute.getFileName());
                }
                InputStream inputfile = new FileInputStream(attributefile);
                profileConfigBean.setAttributeValue(profileConfig, attribute.getName(), inputfile);
              }
              
            } catch (DMException e) {
              throw new SetupException("Error in Task profiles,in attributes not found name is " + attribute.getName() + "", e);
            } catch (FileNotFoundException e) {
              throw new SetupException("Error in Task profiles,in attributes not found file is " + attribute.getFileName() + "", e);
            }
          }
          factory.commit();
          
          this.getSetup().getConsole().println("\t\t[" + profileConfig.getName() + "] imported!");                  
          
        } catch (DMException e) {
          if (factory != null) {
            factory.rollback();
          }
          throw new SetupException("Error in Task profiles.", e);
        } catch (Exception e) {
          if (factory != null) {
            factory.rollback();
          }
          throw new SetupException("Error in Task profiles.", e);
        } finally {
        }
      }
    }
  }

  public List<ProfileConfigItem> loadProfileConfigItems(File file) throws SetupException {
    List<ProfileConfigItem> result = new ArrayList<ProfileConfigItem>();
    try {
      Digester digester = this.createProfileConfigDigester();
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

  private Digester createProfileConfigDigester() {
    // Initialize the digester
    Digester digester = new Digester();
    digester.setValidating(false);

    // Setup
    digester.addObjectCreate("*/Profile", ProfileConfigItem.class);
    digester.addBeanPropertySetter("*/Profile/ID", "externalID");
    digester.addBeanPropertySetter("*/Profile/ProfileName", "profileName");
    digester.addBeanPropertySetter("*/Profile/Template", "template");
    digester.addBeanPropertySetter("*/Profile/Carrier", "carrier");
    digester.addBeanPropertySetter("*/Profile/NAPProfile", "napProfile");
    digester.addBeanPropertySetter("*/Profile/ProxyProfile", "proxyProfile");
    digester.addBeanPropertySetter("*/Profile/Description", "description");
    digester.addBeanPropertySetter("*/Profile/IsUserProfile", "isUserProfile");
    
    digester.addObjectCreate("*/Attributes/Attribute", AttributeItem.class);
    digester.addBeanPropertySetter("*/Attributes/Attribute/Name", "name");
    digester.addBeanPropertySetter("*/Attributes/Attribute/Value", "value");

    //digester.addCallMethod("*/Attributes/Attribute/Value", "setFileName", 1);
    digester.addSetProperties("*/Attributes/Attribute/Value", "filename", "fileName");    
    
    digester.addSetNext("*/Attributes/Attribute", "addAttribute");
    
    digester.addSetNext("*/Profile", "add");

    return (digester);
  }
}
