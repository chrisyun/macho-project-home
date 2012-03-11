/**
 * $Header:
 * /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/OTAClientProvJobBeanImpl.java,v
 * 1.9 2008/02/03 10:17:40 zhao Exp $ $Revision: 1.26 $ $Date: 2008/02/03
 * 10:17:40 $
 * 
 * ===============================================================================================
 * License, Version 1.1
 * 
 * Copyright (c) 1994-2007 NPower Network Software Ltd. All rights reserved.
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
package com.npower.dm.hibernate.management;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import com.npower.cp.OTAException;
import com.npower.cp.OTAInventory;
import com.npower.cp.ProvisioningDoc;
import com.npower.cp.convertor.ProfileConvertor;
import com.npower.cp.convertor.ProfileConvertorFactory;
import com.npower.cp.convertor.ValueFetcher;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.ClientProvAuditLog;
import com.npower.dm.core.ClientProvJobTargetDevice;
import com.npower.dm.core.ClientProvTemplate;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.Subscriber;
import com.npower.dm.hibernate.entity.ClientProvJobTargetDeviceEntity;
import com.npower.dm.hibernate.entity.ProvisionRequest;
import com.npower.dm.management.ClientProvAuditLogBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.OTAClientProvJobBean;
import com.npower.sms.SmsException;
import com.npower.sms.client.SmsSender;
import com.npower.wap.nokia.NokiaOTASettings;
import com.npower.wap.nokia.NokiaOtaBookmarkSettings;
import com.npower.wap.nokia.NokiaOtaBrowserDoc;
import com.npower.wap.nokia.SyncMLDSDoc;
import com.npower.wap.omacp.OMACPSecurityMethod;
import com.npower.wap.omacp.OMAClientProvDoc;
import com.npower.wap.omacp.OMAClientProvSettings;
import com.npower.wap.push.SmsWapPushMessage;
import com.npower.xml.TextXmlWriter;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.26 $ $Date: 2008/12/10 02:28:54 $
 */
public class OTAClientProvJobBeanImpl extends AbstractBean implements OTAClientProvJobBean {

  private static Log   log          = LogFactory.getLog(OTAClientProvJobBeanImpl.class);

  private OTAInventory otaInventory = null;

  private SmsSender    smsSender;
  
  public class SmsWapMessageDecorator implements Serializable {
    private SmsWapPushMessage message = null;
    private String messageEncoder = null;
    private String messageOriginalContent = null;
    
    
    /**
     * 
     */
    public SmsWapMessageDecorator() {
      super();
    }

    /**
     * @param message
     * @param messageEncoder
     * @param messageOriginalContent
     */
    public SmsWapMessageDecorator(SmsWapPushMessage message, String messageEncoder, String messageOriginalContent) {
      super();
      this.message = message;
      this.messageEncoder = messageEncoder;
      this.messageOriginalContent = messageOriginalContent;
    }

    /**
     * @return Returns the message.
     */
    public SmsWapPushMessage getMessage() {
      return message;
    }
    /**
     * @param message The message to set.
     */
    public void setMessage(SmsWapPushMessage message) {
      this.message = message;
    }
    /**
     * @return Returns the messageEncoder.
     */
    public String getMessageEncoder() {
      return messageEncoder;
    }
    /**
     * @param messageEncoder The messageEncoder to set.
     */
    public void setMessageEncoder(String messageEncoder) {
      this.messageEncoder = messageEncoder;
    }
    /**
     * @return Returns the messageOriginalContent.
     */
    public String getMessageOriginalContent() {
      return messageOriginalContent;
    }
    /**
     * @param messageOriginalContent The messageOriginalContent to set.
     */
    public void setMessageOriginalContent(String messageOriginalContent) {
      this.messageOriginalContent = messageOriginalContent;
    }
  }

  /**
   * Constructor
   */
  protected OTAClientProvJobBeanImpl() {
    super();
  }

  /**
   * @param factory
   * @param hsession
   */
  public OTAClientProvJobBeanImpl(ManagementBeanFactory factory, Session hsession, OTAInventory otaInventory,
      SmsSender sender) {
    super(factory, hsession);
    this.setOtaInventory(otaInventory);
    this.setSmsSender(sender);
  }

  /**
   * @return the otaInventory
   */
  public OTAInventory getOtaInventory() {
    return otaInventory;
  }

  /**
   * @param otaInventory
   *            the otaInventory to set
   */
  public void setOtaInventory(OTAInventory otaInventory) {
    this.otaInventory = otaInventory;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.bootstrap.BootstrapService#getSmsSender()
   */
  public SmsSender getSmsSender() {
    return smsSender;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.bootstrap.BootstrapService#setSmsSender(com.npower.sms.client.SmsSender)
   */
  public void setSmsSender(SmsSender smsSender) {
    this.smsSender = smsSender;
  }

  // Private methods
  // -------------------------------------------------------------------------------------------------

  /**
   * Return a ProfileConvertor.
   * 
   * @param profile
   * @return
   * @throws DMException
   */
  private ProfileConvertor getProfileConverter(ProfileConfig profile,
      ValueFetcher<ProfileCategory, String, String> valueFetcher) throws DMException {
    ProfileConvertorFactory factory = ProfileConvertorFactory.newInstance();
    ProfileConvertor convertor = factory.getProfileConvertor(profile, valueFetcher);
    return convertor;
  }

  /**
   * Find OTA CP Template
   * 
   * @param manufacturerName
   * @param modelName
   * @return
   * @throws DSException
   */
  private ClientProvTemplate findClientProvTemplate(Model model, ProfileConfig profile) throws DMException {
    try {
        OTAInventory inventory = this.getOtaInventory();
        return inventory.findTemplate(model.getManufacturer().getExternalId(), 
                                      model.getManufacturerModelId(), 
                                      profile.getProfileTemplate().getProfileCategory().getName());
    } catch (OTAException e) {
      throw new DMException("Failure to find OTA Template.", e);
    }
  }

  /**
   * Record a OTA CP audit log
   * 
   * @param msisdn
   *            MSISDN of device
   * @param model
   *            Model of device
   * @param carrier
   *            Carrier of device
   * @param status
   *            Status of job
   * @param messageID
   *            SMS id
   * @param profile
   *            Profile for provision
   * @param provDoc
   * @throws DMException
   */
  private void tracking(long jobID, String msisdn, Model model, Carrier carrier, String status, String messageID,
      ProfileConfig profile, SmsWapMessageDecorator msg) throws DMException {
    try {
    ClientProvAuditLogBean cpAuditLogbean = this.getManagementBeanFactory().createClientProvAuditLogBean();
    ClientProvAuditLog auditLog = cpAuditLogbean.newClientProvAuditLogInstance(new Date(), msisdn, status);
    auditLog.setCarrierExtID(carrier.getExternalID());
    auditLog.setManufacturerExtID(model.getManufacturer().getExternalId());
    auditLog.setModelExtID(model.getManufacturerModelId());
    auditLog.setProfileCategoryName(profile.getProfileTemplate().getProfileCategory().getName());
    auditLog.setProfileName(profile.getName());
    auditLog.setMessageID(messageID);
    auditLog.setJobId(jobID);

    auditLog.setMessageEncoder(msg.getMessageEncoder());
    auditLog.setProfileContent(msg.getMessageOriginalContent());
    auditLog.setMemo(null);

    cpAuditLogbean.update(auditLog);
    } catch (Exception ex) {
      log.error("Failure to dump cp message into database: " + ex.getMessage(), ex);
    }
  }

  /**
   * 根据型号及需要派发的Profile, 构建一个SmsWapMessage.
   * @param model
   * @param profile
   * @param valueFetcher
   * @param securityMethod
   * @param pin
   * @return
   * @throws DMException
   */
  private SmsWapMessageDecorator buildWapPushMessageDecorator(Model model, ProfileConfig profile,
      ValueFetcher<ProfileCategory, String, String> valueFetcher, OMACPSecurityMethod securityMethod, String pin)
      throws NotFoundClientProvTemplateException, DMException {
    try {
        // 如果是书签类型, 直接生成SmsWapMessage
        if (ProfileCategory.BOOKMARK_CATEGORY_NAME.equals(profile.getProfileTemplate().getProfileCategory().getName())) {
           return buildWapPushMessage4Nokia_Bookmark(profile, valueFetcher);
        }
      
        // 非书签类型, 寻找匹配的Template
        ClientProvTemplate cpTemplate = this.findClientProvTemplate(model, profile);
        if (null == cpTemplate) {
           throw new NotFoundClientProvTemplateException("Could not found ClientProvTemplate for profile: " + profile.getExternalID() 
                                  + ", category: " + profile.getProfileTemplate().getProfileCategory()
                                  + ", model: " + model.getManufacturer().getName() + " " 
                                  + model.getManufacturerModelId());
        }
        
        // 根据不同的编码方式, 生成不同类型的SmsWapMessage
        String encoder = cpTemplate.getEncoder();
        if (ClientProvTemplate.OMA_CP_1_1_ENCODER.equalsIgnoreCase(encoder)) {
          // OMA CP Encoder
          return buildWapPushMessage4OMA_CP(model, profile, valueFetcher, securityMethod, pin);
        } else if (ClientProvTemplate.NOKIA_OTA_7_0_ENCODER.equalsIgnoreCase(encoder)) {
          return buildWapPushMessage4Nokia_OTA(model, profile, valueFetcher);
        } else {
          throw new DMException("Unknown OTA Provision Doc Encoder: " + encoder);
        }
    } catch (Exception e) {
      throw new DMException("Failure to build OTA WapPushMessage.", e);
    }
  }

  /**
   * 构建Bookmark消息
   * @param profile
   * @param valueFetcher
   * @return
   * @throws DMException
   * @throws IOException
   * @throws Exception
   */
  private SmsWapMessageDecorator buildWapPushMessage4Nokia_Bookmark(ProfileConfig profile,
      ValueFetcher<ProfileCategory, String, String> valueFetcher) throws DMException, IOException, Exception {
     SmsWapMessageDecorator result = null;
     ProfileConvertor converter = this.getProfileConverter(profile, valueFetcher);
     NokiaOtaBookmarkSettings setting = (NokiaOtaBookmarkSettings)converter.convertToNokiaOTA(profile);
     // Convert to XML
     StringWriter writer = new StringWriter();
     setting.writeXmlTo(new TextXmlWriter(writer));
     String content = writer.getBuffer().toString();
     
     result = new SmsWapMessageDecorator(setting.getSmsWapPushMessage(), ClientProvTemplate.NOKIA_OTA_7_0_ENCODER, content);
     return result;
  }

  /**
   * 基于Nokia OTA模式构建一个WapPushMessage.
   * @param model
   * @param profile
   * @param valueFetcher
   * @return
   * @throws DMException
   * @throws Exception
   * @throws OTAException
   */
  private SmsWapMessageDecorator buildWapPushMessage4Nokia_OTA(Model model, ProfileConfig profile,
      ValueFetcher<ProfileCategory, String, String> valueFetcher) throws DMException, Exception, OTAException {
    // Nokia OTA Mode
    ProvisioningDoc pDoc = this.buildNokiaOTAMessage(model, profile, valueFetcher);
    String category = profile.getProfileTemplate().getProfileCategory().getName();
    SmsWapMessageDecorator result = null;
    if (ProfileCategory.NAP_CATEGORY_NAME.equalsIgnoreCase(category)
        || ProfileCategory.PROXY_CATEGORY_NAME.equalsIgnoreCase(category)) {
      SmsWapPushMessage msg = new SmsWapPushMessage(new NokiaOtaBrowserDoc(pDoc.getReader()));
      result = new SmsWapMessageDecorator(msg, ClientProvTemplate.NOKIA_OTA_7_0_ENCODER, pDoc.getContent());
    } else if (ProfileCategory.MMS_CATEGORY_NAME.equalsIgnoreCase(category)) {
      SmsWapPushMessage msg = new SmsWapPushMessage(new SyncMLDSDoc(pDoc.getReader()));
      result = new SmsWapMessageDecorator(msg, ClientProvTemplate.NOKIA_OTA_7_0_ENCODER, pDoc.getContent());
    } else {
      throw new DMException("Unsupported Nokia OTA Type: " + category);
    }
    return result;
  }

  /**
   * 基于OMA CP模式构建一个WapPushMessage.
   * @param model
   * @param profile
   * @param valueFetcher
   * @param securityMethod
   * @param pin
   * @return
   * @throws DMException
   * @throws Exception
   */
  private SmsWapMessageDecorator buildWapPushMessage4OMA_CP(Model model, ProfileConfig profile,
      ValueFetcher<ProfileCategory, String, String> valueFetcher, OMACPSecurityMethod securityMethod, String pin)
      throws DMException, Exception {
    // 基于OMA CP模式构建一个WapPushMessage
    OMAClientProvDoc doc = this.buildProvisionDoc(model, profile, valueFetcher);
    if (StringUtils.isNotEmpty(pin)) {
      if (securityMethod == null) {
        securityMethod = OMACPSecurityMethod.USERPIN;
      }
      doc.setSecurityMethod(securityMethod);
      doc.setPIN(pin);
    }
    SmsWapPushMessage msg = doc.getSmsWapPushMessage();
    SmsWapMessageDecorator result = new SmsWapMessageDecorator(msg, ClientProvTemplate.OMA_CP_1_1_ENCODER, doc.getContent());
    return result;
  }

  /**
   * 构建NOKIA OTA模式的ProvisionDoc
   * @param model
   * @param profile
   * @param valueFetcher
   * @return
   * @throws DMException
   */
  private ProvisioningDoc buildNokiaOTAMessage(Model model, ProfileConfig profile,
      ValueFetcher<ProfileCategory, String, String> valueFetcher) throws DMException {
    try {
        ProfileConvertor converter = this.getProfileConverter(profile, valueFetcher);
        NokiaOTASettings settings = converter.convertToNokiaOTA(profile);
        ClientProvTemplate cpTemplate = this.findClientProvTemplate(model, profile);
        ProvisioningDoc pDoc = cpTemplate.merge(settings);
        log.debug("Build CP Settings Content Encoder: " + cpTemplate.getEncoder());
        log.debug("Build CP Settings Content: " + pDoc.getContent());
        if (ClientProvTemplate.NOKIA_OTA_7_0_ENCODER.equalsIgnoreCase(cpTemplate.getEncoder())) {
          return pDoc;
        } else {
          throw new DMException("Unsupported Nokia OTA Provision Doc Encoder: " + cpTemplate.getEncoder());
        }
    } catch (OTAException e) {
      throw new DMException("Failure to build Nokia OTA document.", e);
    } catch (Exception e) {
      throw new DMException("Failure to build Nokia OTA document.", e);
    }
  }

  // Public methods
  // --------------------------------------------------------------------------------------------------
  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.OTAClientProvJobBean#buildProvisionDoc(com.npower.dm.core.Model,
   *      com.npower.dm.core.ProfileConfig)
   */
  public OMAClientProvDoc buildProvisionDoc(Model model, ProfileConfig profile,
      ValueFetcher<ProfileCategory, String, String> valueFetcher) throws DMException {
    try {
      ClientProvTemplate cpTemplate = this.findClientProvTemplate(model, profile);
      if (cpTemplate == null) {
         throw new DMException("Un-supported OTA Provision, category: " + profile.getProfileTemplate().getProfileCategory().getName() + " for Model: " + model.getManufacturer().getExternalId() + " " + model.getManufacturerModelId()); 
      }
      if (log.isDebugEnabled()) {
         log.debug("Build CP Settings Content Encoder: " + cpTemplate.getEncoder());
      }

      ProfileConvertor converter = this.getProfileConverter(profile, valueFetcher);
      ProvisioningDoc pDoc = null;
      if (converter != null) {
         OMAClientProvSettings settings = converter.convertToOMAClientProv(profile);
         pDoc = cpTemplate.merge(settings);
         log.debug("Build CP Settings Content: " + pDoc.getContent());
      } else {
        pDoc = cpTemplate.merge(profile);
      }
      
      if (log.isDebugEnabled()) {
         log.debug("Build CP Settings Content: " + pDoc.getContent());
      }
      if (ClientProvTemplate.OMA_CP_1_1_ENCODER.equalsIgnoreCase(cpTemplate.getEncoder())) {
        OMAClientProvDoc doc = new OMAClientProvDoc(pDoc.getContent());
        return doc;
      } else {
        throw new DMException("Unsupported OTA Provision Doc Encoder: " + cpTemplate.getEncoder());
      }
    } catch (OTAException e) {
      throw new DMException("Failure to build OTA OMAClientProvDoc.", e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.OTAClientProvJobBean#buildWapPushMessage(com.npower.dm.core.Model,
   *      com.npower.dm.core.ProfileConfig,
   *      com.npower.wap.omacp.OMACPSecurityMethod, java.lang.String)
   */
  public SmsWapPushMessage buildWapPushMessage(Model model, ProfileConfig profile,
      ValueFetcher<ProfileCategory, String, String> valueFetcher, OMACPSecurityMethod securityMethod, String pin)
      throws DMException, NotFoundClientProvTemplateException {
    SmsWapMessageDecorator result = this.buildWapPushMessageDecorator(model, profile, valueFetcher, securityMethod, pin);
    return result.getMessage();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.OTAClientProvJobBean#provision(java.lang.String,
   *      com.npower.dm.core.Model, com.npower.dm.core.Carrier,
   *      com.npower.dm.core.ProfileConfig,
   *      com.npower.cp.convertor.ValueFetcher,
   *      com.npower.wap.omacp.OMACPSecurityMethod, java.lang.String, long, int,
   *      long)
   */
  public ProvisionJob provision(String msisdn, Model model, Carrier carrier, ProfileConfig profile,
      ValueFetcher<ProfileCategory, String, String> valueFetcher, OMACPSecurityMethod securityMethod, String pin,
      long scheduleTime, int maxRetry, long maxDuration) throws DMException {

    String status = ClientProvAuditLog.STATUS_FAILURE;
    String messageID = null;
    SmsWapMessageDecorator message = null;
    ProvisionRequest job = null;

    ManagementBeanFactory factory = null;
    try {
  
        // Update job
        job = new ProvisionRequest();
        job.setJobMode(ProvisionJob.JOB_MODE_CP);
        job.setJobType(ProvisionJob.JOB_TYPE_ASSIGN_PROFILE);
        job.setTargetType(ProvisionJob.JOB_ELEMENT_TYPE_SINGLE);
        job.setMaxRetries(maxRetry);
        job.setMaxDuration(maxDuration);
        job.setScheduledTime(new Date(scheduleTime));
        job.setState(ProvisionJob.JOB_STATE_APPLIED);

        ClientProvJobTargetDeviceEntity target = new ClientProvJobTargetDeviceEntity();
        target.setCarrierExternalId(carrier.getExternalID());

        // Send message into SMS Gateway
        SmsWapPushMessage wapMessage = null;
        try {
            // Send OTA Message.
            log.info("OTA Provisioning, device msisdn: " + msisdn);
            message = this.buildWapPushMessageDecorator(model, profile, valueFetcher, securityMethod, pin);
            wapMessage = message.getMessage();
            messageID = this.getSmsSender().send(wapMessage, msisdn, msisdn, scheduleTime, maxRetry, maxDuration);
            status = ClientProvAuditLog.STATUS_QUEUED;
    
            target.setMessageId(messageID);
            target.setMessageContent(message.getMessageOriginalContent());
            target.setSmsMessage(wapMessage);
            target.setMessageType(message.getMessageEncoder());

        } catch (SmsException e) {
          status = ClientProvAuditLog.STATUS_WAIT;
          log.error("Error in Send SMS for OTA Provision.", e);
        } catch (IOException e) {
          status = ClientProvAuditLog.STATUS_WAIT;
          log.error("Error in Send SMS for OTAS Provision.", e);
        } catch (NotFoundClientProvTemplateException e) {
          status = ClientProvAuditLog.STATUS_CANCELLED;
          log.error("Error in Send SMS for OTAS Provision.", e);
        }
  
        factory = this.getManagementBeanFactory();
        factory.beginTransaction();
        // job.setType("OTA");
        this.update(job);

        target.setJob(job);
        target.setManufacturerExternalId(model.getManufacturer().getExternalId());
        target.setModelExternalId(model.getManufacturerModelId());
        target.setPhoneNumber(msisdn);
        target.setProfileExternalId(profile.getExternalID());
        target.setSecurityMethod(securityMethod.toString());
        target.setSecurityPin(pin);
        target.setStatus(status);
        target.setNumberOfEnqueueRetries(1);
        target.setLastEnqueueRetriesTime(new Date());
        job.getOtaTargetDevices().add(target);
        this.update(target);
  
        factory.commit();
        return job;
  
    } catch (Exception ex) {
      if (factory != null) {
        factory.rollback();
      }
      throw new DMException("Failured in creating OTA Provision Job.", ex);
    } finally {
      // Audit Log
      if (message != null) {
         tracking(job.getID(), msisdn, model, carrier, status, messageID, profile, message);
      }
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.OTAClientProvJobBean#provision(com.npower.dm.core.Device,
   *      com.npower.dm.core.ProfileConfig,
   *      com.npower.cp.convertor.ValueFetcher,
   *      com.npower.wap.omacp.OMACPSecurityMethod, java.lang.String, long, int,
   *      long)
   */
  public ProvisionJob provision(Device device, ProfileConfig profile,
      ValueFetcher<ProfileCategory, String, String> valueFetcher, OMACPSecurityMethod securityMethod, String pin,
      long scheduleTime, int maxRetry, long maxDuration) throws DMException {

    Subscriber subscriber = device.getSubscriber();
    String msisdn = subscriber.getPhoneNumber();
    Carrier carrier = subscriber.getCarrier();
    Model model = device.getModel();
    String status = ClientProvAuditLog.STATUS_FAILURE;
    String messageID = null;
    SmsWapMessageDecorator message = null;
    ProvisionRequest job = null;

    ManagementBeanFactory factory = null;
    try {
  
        // Create a job
        job = new ProvisionRequest();
        job.setJobMode(ProvisionJob.JOB_MODE_CP);
        job.setJobType(ProvisionJob.JOB_TYPE_ASSIGN_PROFILE);
        job.setTargetType(ProvisionJob.JOB_ELEMENT_TYPE_SINGLE);
        job.setMaxRetries(maxRetry);
        job.setMaxDuration(maxDuration);
        job.setScheduledTime(new Date(scheduleTime));
        job.setState(ProvisionJob.JOB_STATE_APPLIED);

        ClientProvJobTargetDeviceEntity target = new ClientProvJobTargetDeviceEntity();
        target.setCarrierExternalId(carrier.getExternalID());

        // Send message into SMS Gateway
        SmsWapPushMessage wapMessage = null;
        try {
            // Send OTA Message.
            log.info("OTA Provisioning, device msisdn: " + msisdn);
            message = this.buildWapPushMessageDecorator(model, profile, valueFetcher, securityMethod, pin);
            wapMessage = message.getMessage();
            messageID = this.getSmsSender().send(wapMessage, msisdn, msisdn, scheduleTime, maxRetry, maxDuration);
            status = ClientProvAuditLog.STATUS_QUEUED;
            
            target.setMessageId(messageID);
            target.setMessageContent(message.getMessageOriginalContent());
            target.setSmsMessage(wapMessage);
            target.setMessageType(message.getMessageEncoder());
        } catch (SmsException e) {
          status = ClientProvAuditLog.STATUS_WAIT;
          log.error("Error in Send SMS for OTA Provision.", e);
        } catch (IOException e) {
          status = ClientProvAuditLog.STATUS_WAIT;
          log.error("Error in Send SMS for OTAS Provision.", e);
        } catch (NotFoundClientProvTemplateException e) {
          status = ClientProvAuditLog.STATUS_CANCELLED;
          log.error("Error in Send SMS for OTAS Provision.", e);
        }
  
        factory = this.getManagementBeanFactory();
        factory.beginTransaction();
        // job.setType("OTA");
        this.update(job);

        target.setJob(job);
        target.setDeviceId("" + device.getID());
        target.setManufacturerExternalId(model.getManufacturer().getExternalId());
        target.setModelExternalId(model.getManufacturerModelId());
        target.setPhoneNumber(msisdn);
        target.setProfileExternalId(profile.getExternalID());
        target.setSecurityMethod(securityMethod.toString());
        target.setSecurityPin(pin);
        target.setStatus(status);
        target.setNumberOfEnqueueRetries(1);
        target.setLastEnqueueRetriesTime(new Date());
        
        job.getOtaTargetDevices().add(target);
        this.update(target);
  
        factory.commit();
        return job;
  
    } catch (Exception ex) {
      if (factory != null) {
        factory.rollback();
      }
      throw new DMException("Failured in creating OTA Provision Job.", ex);
    } finally {
      // Audit Log
      if (message != null) {
         tracking(job.getID(), msisdn, model, carrier, status, messageID, profile, message);
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.OTAClientProvJobBean#delete(long)
   */
  public void delete(long jobID) throws DMException {
    try {
      Session session = this.getHibernateSession();
      ProvisionJob job = this.getJobById(jobID);
      if (job != null) {
        session.delete(job);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    }
  }

  public void disable(long jobID) throws DMException {
    String newState = ProvisionJob.JOB_STATE_DISABLE;
    try {
      ProvisionJob job = this.getJobById(jobID);
      job.setLastUpdatedTime(new Date());
      job.setState(newState);
      Session session = this.getHibernateSession();
      session.saveOrUpdate(job);
    } catch (HibernateException e) {
      throw new DMException(e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.OTAClientProvJobBean#enable(long)
   */
  public void enable(long jobID) throws DMException {
    String newState = ProvisionJob.JOB_STATE_APPLIED;
    try {
      ProvisionJob job = this.getJobById(jobID);
      job.setState(newState);
      job.setLastUpdatedTime(new Date());
      Session session = this.getHibernateSession();
      session.saveOrUpdate(job);
    } catch (HibernateException e) {
      throw new DMException(e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.OTAClientProvJobBean#getJobById(long)
   */
  public ProvisionJob getJobById(long jobID) throws DMException {
    try {
      Session session = this.getHibernateSession();
      ProvisionJob job = (ProvisionJob) session.get(ProvisionRequest.class, new Long(jobID));
      return job;
    } catch (HibernateException e) {
      throw new DMException("Invalidate jobID: " + jobID, e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.OTAClientProvJobBean#update(com.npower.dm.core.ClientProvJob)
   */
  public void update(ProvisionJob job) throws DMException {
    try {
      Session session = this.getHibernateSession();
      job.setLastUpdatedTime(new Date());
      session.saveOrUpdate(job);
    } catch (HibernateException e) {
      throw new DMException(e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.OTAClientProvJobBean#update(com.npower.dm.core.ClientProvJobTargetDevice)
   */
  public void update(ClientProvJobTargetDevice targetDevice) throws DMException {
    try {
      Session session = this.getHibernateSession();
      targetDevice.setLastUpdatedTime(new Date());
      session.saveOrUpdate(targetDevice);
      
      // 检查是否所有的任务都完成, 设置完成标志
      if (targetDevice.isFinished()) {
        ProvisionJob job = targetDevice.getJob();
        for (ClientProvJobTargetDevice target: job.getOtaTargetDevices()) {
            if (!target.isFinished()) {
               return;
            }
        }
        job.setState(ProvisionJob.JOB_STATE_FINISHED);
        this.update(job);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.OTAClientProvJobBean#findTargetDeviceByMessageID(java.lang.String)
   */
  public List<ClientProvJobTargetDevice> findTargetDeviceByMessageID(String messageID) throws DMException {
    Session session = this.getHibernateSession();
    try {
        Criteria criteria = session.createCriteria(ClientProvJobTargetDeviceEntity.class);
        criteria.add(Expression.eq("messageId", messageID));
        List<ClientProvJobTargetDevice> list = criteria.list();
        return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  public ClientProvJobTargetDevice getProvisionJobStatus(ProvisionJob job, Device device) throws DMException {
    Session session = this.getHibernateSession();
    try {
        Criteria criteria = session.createCriteria(ClientProvJobTargetDeviceEntity.class);
        criteria.add(Expression.eq("job", job));
        criteria.add(Expression.eq("deviceId", Long.toString(device.getID())));
        List<ClientProvJobTargetDevice> list = criteria.list();
        if (list != null && list.size() == 1) {
          return (ClientProvJobTargetDevice)list.get(0);
        } else {
         if (list == null || list.size() == 0) {
            // This device is not the target of this job.
            throw new DMException("error in data constrain, none status boundled with the device.");
         } else {
           throw new DMException("error in data constrain, multiple cp job status boundled with the device.");
         }
       }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

}
