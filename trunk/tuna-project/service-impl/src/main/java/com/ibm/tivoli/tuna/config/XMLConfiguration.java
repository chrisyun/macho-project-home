/**
 * 
 */
package com.ibm.tivoli.tuna.config;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;
import javax.security.auth.login.Configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author zhaodonglu
 *
 */
public class XMLConfiguration extends Configuration implements LoginContextItemFinder {
  private static Log log = LogFactory.getLog(XMLConfiguration.class);
  
  private List<LoginContextItem> items = new ArrayList<LoginContextItem>(); 
  
  /**
   * 
   */
  public XMLConfiguration() {
    super();
  }
  
  public XMLConfiguration(List<LoginContextItem> items) {
    super();
    this.items = items;
  }

  public XMLConfiguration(InputStream in) throws IOException {
    super();
    log.info(String.format("Load JAAS Configuration from: InputStream[%s]", in));
    this.setItems(load(in));
    log.debug(String.format("Load finished, Current Login Modules: [%s]", this.getItems()));
  }
  
  public XMLConfiguration(File configFile) throws IOException {
    log.info(String.format("Load JAAS Configuration from: File[%s]", configFile.getCanonicalFile()));
    FileInputStream in = new FileInputStream(configFile);
    this.setItems(load(in));
    log.debug(String.format("Load finished, Current Login Modules: [%s]", this.getItems()));
  }

  public XMLConfiguration(String content) throws IOException {
    log.info(String.format("Load JAAS Configuration from: Content[%s]", content));
    InputStream in = new ByteArrayInputStream(content.getBytes());
    this.setItems(load(in));
    log.debug(String.format("Load finished, Current Login Modules: [%s]", this.getItems()));
  }

  /**
   * @param in
   * @return
   * @throws IOException
   */
  private static List<LoginContextItem> load(InputStream in) throws IOException {
    XStream xstream = new XStream(new DomDriver("UTF-8"));
    xstream.alias("loginContext", LoginContextItem.class);
    xstream.alias("loginModule", LoginModuleItem.class);
    xstream.alias("moduleOption", ModuleOption.class);
    
    List<LoginContextItem> rs = (List<LoginContextItem>)xstream.fromXML(in);
    return rs;
  }

  /**
   * @return the items
   */
  public synchronized List<LoginContextItem> getItems() {
    return items;
  }

  /**
   * @param items the items to set
   */
  public synchronized void setItems(List<LoginContextItem> items) {
    this.items = items;
  }
  
  @Override
  public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
    log.debug(String.format("Get JAAS config with name[%s]", name));
    List<AppConfigurationEntry> result = new ArrayList<AppConfigurationEntry>();
    for (LoginContextItem item: this.getItems()) {
        if (item.getName().equals(name)) {
           for (LoginModuleItem i: item.getLoginModules()) {
               LoginModuleControlFlag controlFlag = null;
               if ("required".equalsIgnoreCase(i.getModuleFlag())) {
                 controlFlag = LoginModuleControlFlag.REQUIRED;
               } else if ("requisite".equalsIgnoreCase(i.getModuleFlag())) {
                 controlFlag = LoginModuleControlFlag.REQUISITE;
               } else if ("sufficient".equalsIgnoreCase(i.getModuleFlag())) {
                 controlFlag = LoginModuleControlFlag.SUFFICIENT;
               } else if ("optional".equalsIgnoreCase(i.getModuleFlag())) {
                 controlFlag = LoginModuleControlFlag.OPTIONAL;
               }
               Map<String, String> options = new HashMap<String, String>();
               for (ModuleOption option: i.getModuleOptions()) {
                   options.put(option.getName(), option.getValue());
               }
              AppConfigurationEntry entry = new AppConfigurationEntry(i.getLoginModuleClass(), controlFlag, options);
              log.debug(String.format("Found JAAS module for name[%s], module[%s]", name, i.toString()));
              result.add(entry);
           }
           break;
        }
    }
    return result.toArray(new AppConfigurationEntry[0]);
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.tuna.config.LoginContextItemFinder#findLoginContextItemByName(java.lang.String)
   */
  public LoginContextItem findLoginContextItemByName(String name) {
    for (LoginContextItem item: this.getItems()) {
      if (item.getName().equals(name)) {
         return item;
      }
    }
    return null;
  }

  @Override
  public void refresh() {
    // TODO Auto-generated method stub
    
  }

}
