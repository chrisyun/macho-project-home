package com.npower.dm.msm;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import sync4j.framework.engine.dm.ManagementProcessor;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceTreeNode;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.Software;
import com.npower.dm.core.Subscriber;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.processor.BaseProcessor;
import com.npower.dm.util.MessageResources;
import com.npower.dm.util.MessageResourcesFactory;
import com.npower.sms.SmsBuilder;
import com.npower.sms.SmsException;
import com.npower.sms.SmsMessage;
import com.npower.sms.client.SmsSender;
import com.npower.sms.client.SmsSenderFactory;

public abstract class BaseS60V3Processor extends BaseProcessor implements ManagementProcessor, Serializable {

  private static final String BASE_SCOMO_NODE = "./SCM";
  private static final String BASE_DOWNLOAD_NODE = BASE_SCOMO_NODE + "/Download";
  private static final String BASE_DEPLOYED_NODE = BASE_SCOMO_NODE + "/Inventory/Deployed";
  private static final String BASE_DELIVERED_NODE = BASE_SCOMO_NODE + "/Inventory/Delivered";
  private static final String STATE_NODE_NAME = "StateValue";

  /**
   * Sms gateway config filename
   */
  private static final String SMS_GATEWAY_PROPERTIES = "otasdm/otasdm.properties";
  /**
   * Download server config filename
   */
  private static final String DOWNLOAD_SERVER_PROPERTIES = "otasdm/otasdm.properties";


  public BaseS60V3Processor() {
    super();
  }
  
  /**
   * Return resources.
   * @return
   */
  public static MessageResources getResources() {
    MessageResourcesFactory factory = MessageResourcesFactory.createFactory();
    MessageResources resources = factory.createResources("com/npower/dm/msm/processor");
    return resources;
  }
  
  /**
   * 返回设备的Locale
   * @param device
   * @return
   */
  public static Locale getLocale(Device device) {
    Locale locale = Locale.CHINESE;
    return locale;
  }
  
  /**
   * 发送文本短信息
   * @param device
   * @param text
   * @throws Exception
   * @throws SmsException
   * @throws IOException
   */
  public static void sendTextMessage(Device device, String text) throws Exception, SmsException, IOException {
    Subscriber subscriber = device.getSubscriber();
    Carrier carrier = subscriber.getCarrier();
    SmsSender sender = getSmsSender(carrier);
    SmsMessage msg = SmsBuilder.createUnicodeTextSms(text);
    sender.send(msg, subscriber.getPhoneNumber(), subscriber.getPhoneNumber());
  }

  /**
   * Return BASE_SCOMO_NODE
   * @return
   */
  public String getBaseScomoNode() {
    return BASE_SCOMO_NODE;
  }
  
  /**
   * Return BASE_DEPLOYED_NODE
   * @return
   */
  public String getBaseScomoDeployedNode() {
    return BASE_DEPLOYED_NODE;
  }

  /**
   * Return BASE_DOWNLOAD_NODE
   * @return
   */
  public String getBaseScomoDownloadNode() {
    return BASE_DOWNLOAD_NODE;
  }

  /**
   * Return BASE_DELIVERED_NODE
   * @return
   */
  public String getBaseScomoDeliveredNode() {
    return BASE_DELIVERED_NODE;
  }

  /**
   * @param filename
   * @return
   * @throws DMException
   */
  private static Properties loadPropertiesByFileNameInConfigDir(String filename) throws DMException {
    try {
        Properties props = loadProperties(filename);
        return props;
    } catch (FileNotFoundException e) {
      throw new DMException("Failure in loading config file: " + filename);
    } catch (IOException e) {
      throw new DMException("Failure in loading config file: " + filename);
    }
  }

  /**
   * @param filename
   * @return
   * @throws FileNotFoundException
   */
  private static InputStream getPropertiesFile(String filename) throws FileNotFoundException {
    String home = System.getProperty("otas.dm.web", System.getProperty("otas.dm.home"));
    InputStream ins = null;
    if (home != null) {
       File configDir = new File(home, "config");
       if (configDir.exists()) {
          File propFile = new File(configDir, filename);
          if (propFile.exists()) {
             ins = new FileInputStream(propFile);
          }
       }
    }
    if (ins == null) {
       ins = BaseS60V3Processor.class.getClassLoader().getResourceAsStream(filename);
    }
    return ins;
  }
  
  /**
   * @param filename
   * @return
   * @throws FileNotFoundException
   * @throws DMException
   * @throws IOException
   */
  private static Properties loadProperties(String filename) throws FileNotFoundException, DMException, IOException {
    InputStream ins = getPropertiesFile(filename);
    if (ins == null) {
       throw new DMException("Could not found config file: " + filename);
    }
    Properties props = new Properties(System.getProperties());
    props.load(ins);
    return props;
  }

  /**
   * Return properties for SMS Gateway
   * @return
   *        Properties of SMS Gateway
   * @throws DMException
   */
  protected static Properties getSmsGatewayProperties() throws DMException {
    String filename = SMS_GATEWAY_PROPERTIES;
    return loadPropertiesByFileNameInConfigDir(filename);
  }

  /**
   * Return properties for download server
   * @return
   *        Properties of download server
   * @throws DMException
   */
  protected static Properties getDownloadServerProperties() throws DMException {
    String filename = DOWNLOAD_SERVER_PROPERTIES;
    return loadPropertiesByFileNameInConfigDir(filename);
  }

  /**
   * Return a SmsSender for send SMS message.
   * @param carrier
   *        Carrier which included SMSC Properties.
   * @return
   * @throws Exception
   */
  public static SmsSender getSmsSender(Carrier carrier) throws Exception {
    // Load default SMS Gateway properties
    Properties props = getSmsGatewayProperties();
    
    if (carrier != null && StringUtils.isNotEmpty(carrier.getNotificationProperties())) {
       // Load from carrier SMSC properties.
       String content = carrier.getNotificationProperties();
       props.load(new ByteArrayInputStream(content.getBytes("UTF-8")));
    }
    SmsSenderFactory factory = SmsSenderFactory.newInstance(props);
    SmsSender sender = factory.getSmsSender();
    return sender;
  }

  /**
   * 计算软件Download节点的路径.
   * 例如: ./SCM/Download/UCWeb
   * @param software
   * @return
   */
  public String getSoftwareDownloadBaseNodePath(Software software) {
    String softwareExtID = software.getExternalId();
    return getSoftwareDownloadBaseNodePath(softwareExtID);
  }

  /**
   * 计算软件Download节点的路径.
   * 例如: ./SCM/Download/UCWeb
   * @param softwareExtID
   * @return
   */
  private String getSoftwareDownloadBaseNodePath(String softwareExtID) {
    String baseNodePath = BASE_DOWNLOAD_NODE + "/" + softwareExtID;
    return baseNodePath;
  }

  /**
   * 计算软件Deployed节点的路径.
   * 例如: ./SCM/Inventory/Deployed/UCWeb
   * @param software
   * @return
   */
  public String getSoftwareDeployedBaseNodePath(Software software) {
    String softwareExtID = software.getExternalId();
    return getSoftwareDeployedBaseNodePath(softwareExtID);
  }

  /**
   * 计算软件Deployed节点的路径.
   * 例如: ./SCM/Inventory/Deployed/UCWeb
   * @param softwareExtID
   * @return
   */
  public String getSoftwareDeployedBaseNodePath(String softwareExtID) {
    String baseNodePath = BASE_DEPLOYED_NODE + "/" + softwareExtID;
    return baseNodePath;
  }

  /**
   * 计算软件Delivered节点的路径.
   * 例如: ./SCM/Inventory/Delivered/UCWeb
   * @param software
   * @return
   */
  public String getSoftwareDeliveredBaseNodePath(Software software) {
    String softwareExtID = software.getExternalId();
    return getSoftwareDeliveredBaseNodePath(softwareExtID);
  }

  /**
   * 计算软件Delivered节点的路径.
   * 例如: ./SCM/Inventory/Delivered/UCWeb
   * @param softwareExtID
   * @return
   */
  public String getSoftwareDeliveredBaseNodePath(String softwareExtID) {
    String baseNodePath = BASE_DELIVERED_NODE + "/" + softwareExtID;
    return baseNodePath;
  }
  
  /**
   * 计算软件Deployed状态节点的路径.
   * 例如: ./SCM/Inventory/Deployed/UCWeb/StateValue
   * @param software
   * @return
   */
  public String getSoftwareDeployedStateNodePath(Software software) {
    String nodePath = this.getSoftwareDeployedBaseNodePath(software) + "/" + STATE_NODE_NAME;
    return nodePath;
  }

  /**
   * 计算软件Deployed状态节点的路径.
   * 例如: ./SCM/Inventory/Deployed/UCWeb/StateValue
   * @param software
   * @return
   */
  public String getSoftwareDeployedStateNodePath(String softwareExtID) {
    String nodePath = this.getSoftwareDeployedBaseNodePath(softwareExtID) + "/" + STATE_NODE_NAME;
    return nodePath;
  }

  /**
   * Return the ProvisionJob which will be processed in this processor.
   * @param factory
   * @return
   * @throws DMException
   */
  protected ProvisionJob getProvisionJob(ManagementBeanFactory factory) throws DMException {
    ProvisionJobBean jobBean = factory.createProvisionJobBean();
    ProvisionJob job = jobBean.loadJobByID(this.sessionContext.getDmstate().mssid);
    return job;
  }
  
  /**
   * 返回目标软件
   * @param factory
   * @return
   * @throws DMException
   */
  protected Software getTargetSoftware(ManagementBeanFactory factory) throws DMException {
    ProvisionJob currentJob = this.getProvisionJob(factory);
    Software software = currentJob.getTargetSoftware();
    return software;
  }

  /**
   * 检查软件是否安装并激活
   * @param factory
   * @param device
   * @param software
   * @return
   */
  protected boolean isInstalledAndActived(ManagementBeanFactory factory, Device device, Software software) throws DMException {
    boolean result = false;
    DeviceBean deviceBean = factory.createDeviceBean();
    DeviceTreeNode node = deviceBean.findDeviceTreeNode(device.getID(), 
                                                        this.getSoftwareDeployedBaseNodePath(software));
    if (node != null) {
       DeviceTreeNode stateNode = deviceBean.findDeviceTreeNode(device.getID(), 
                                                                this.getSoftwareDeployedStateNodePath(software));
       if (stateNode != null) {
          //&& stateNode.getStringValue() != null && stateNode.getStringValue().equalsIgnoreCase("Active")) {
          result = true;
       }
    }
    return result;
  }

  /**
   * 检查软件是否安装
   * @param factory
   * @param device
   * @param software
   * @return
   */
  protected boolean isInstalled(ManagementBeanFactory factory, Device device, Software software) throws DMException {
    boolean result = false;
    DeviceBean deviceBean = factory.createDeviceBean();
    DeviceTreeNode node = deviceBean.findDeviceTreeNode(device.getID(), 
                                                        this.getSoftwareDeployedBaseNodePath(software));
    if (node != null) {
       DeviceTreeNode stateNode = deviceBean.findDeviceTreeNode(device.getID(), 
                                                                this.getSoftwareDeployedStateNodePath(software));
       if (stateNode != null) {
          //&& stateNode.getStringValue() != null && stateNode.getStringValue().equalsIgnoreCase("Active")) {
          result = true;
       }
    }
    return result;
  }

}