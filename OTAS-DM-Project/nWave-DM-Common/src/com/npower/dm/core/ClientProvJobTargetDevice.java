package com.npower.dm.core;

import java.io.IOException;
import java.sql.Blob;
import java.util.Date;

import com.npower.sms.SmsMessage;

public interface ClientProvJobTargetDevice {

  /**
   * @return
   */
  public abstract long getId();

  /**
   * @param id
   */
  public abstract void setId(long id);

  /**
   * @return
   */
  public abstract ProvisionJob getJob();

  /**
   * @param nwCpJob
   */
  public abstract void setJob(ProvisionJob nwCpJob);

  /**
   * @return
   */
  public abstract String getDeviceId();

  /**
   * @param deviceId
   */
  public abstract void setDeviceId(String deviceId);

  /**
   * @return
   */
  public abstract String getPhoneNumber();

  /**
   * @param phoneNumber
   */
  public abstract void setPhoneNumber(String phoneNumber);

  /**
   * @return
   */
  public abstract String getManufacturerExternalId();

  /**
   * @param manufacturerExternalId
   */
  public abstract void setManufacturerExternalId(String manufacturerExternalId);

  /**
   * @return
   */
  public abstract String getModelExternalId();

  /**
   * @param modelExternalId
   */
  public abstract void setModelExternalId(String modelExternalId);

  /**
   * @return
   */
  public abstract String getCarrierExternalId();

  /**
   * @param carrierExternalId
   */
  public abstract void setCarrierExternalId(String carrierExternalId);

  /**
   * @return
   */
  public abstract String getStatus();

  /**
   * @param status
   */
  public abstract void setStatus(String status);

  /**
   * @return
   */
  public abstract String getMessageId();

  /**
   * @param messageId
   */
  public abstract void setMessageId(String messageId);

  /**
   * @return
   */
  public abstract String getMessageType();

  /**
   * @param messageType
   */
  public abstract void setMessageType(String messageType);

  /**
   * @return
   */
  public abstract String getMessageContent();

  /**
   * @param messageContent
   */
  public abstract void setMessageContent(String messageContent);
  
  
  /**
   * @return
   * @throws IOException 
   * @throws Exception 
   */
  public abstract SmsMessage getSmsMessage() throws Exception;
  
  /**
   * @param message
   * @throws IOException 
   */
  public abstract void setSmsMessage(SmsMessage message) throws IOException;

  /**
   * @return
   */
  public abstract String getSecurityMethod();

  /**
   * @param securityMethod
   */
  public abstract void setSecurityMethod(String securityMethod);

  /**
   * @return
   */
  public abstract String getSecurityPin();

  /**
   * @param securityPin
   */
  public abstract void setSecurityPin(String securityPin);

  /**
   * @return
   */
  public abstract String getProfileExternalId();

  /**
   * @param profileExternalId
   */
  public abstract void setProfileExternalId(String profileExternalId);

  /**
   * @return
   */
  public abstract Blob getParameters();

  /**
   * @param parameters
   */
  public abstract void setParameters(Blob parameters);

  /**
   * @return
   */
  public abstract Date getFinishedTime();

  /**
   * @param finishedTime
   */
  public abstract void setFinishedTime(Date finishedTime);

  /**
   * @return
   */
  public abstract Date getCreatedTime();

  /**
   * @param createdTime
   */
  public abstract void setCreatedTime(Date createdTime);

  /**
   * @return
   */
  public abstract String getCreatedBy();

  /**
   * @param createdBy
   */
  public abstract void setCreatedBy(String createdBy);

  /**
   * @return
   */
  public abstract Date getLastUpdatedTime();

  /**
   * @param lastUpdatedTime
   */
  public abstract void setLastUpdatedTime(Date lastUpdatedTime);

  /**
   * @return
   */
  public abstract String getLastUpdatedBy();

  /**
   * @param lastUpdatedBy
   */
  public abstract void setLastUpdatedBy(String lastUpdatedBy);

  /**
   * Return true, if all of device finished.
   * @return
   */
  public abstract boolean isFinished();

  /**
   * @return the numberOfEnqueueRetries
   */
  public abstract long getNumberOfEnqueueRetries();

  /**
   * @param numberOfEnqueueRetries the numberOfEnqueueRetries to set
   */
  public abstract void setNumberOfEnqueueRetries(long numberOfEnqueueRetries);
  /**
   * @return the lastEnqueueRetriesTime
   */
  public abstract Date getLastEnqueueRetriesTime();

  /**
   * @param lastEnqueueRetriesTime the lastEnqueueRetriesTime to set
   */
  public abstract void setLastEnqueueRetriesTime(Date lastEnqueueRetriesTime);

  //public abstract Set getProfiles();

  //public abstract void setProfiles(Set profiles);

}