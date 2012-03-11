package com.npower.dm.core;

import java.util.Date;

/**
* @author Liu AiHui
* @version $Revision: 1.5 $ $Date: 2008/08/01 06:45:39 $
*/
public interface ClientProvAuditLog {

  /**
   * SMS STATUS: queued
   *     表示短信息没有成功提交到SMS Gateway的队列, 等待再次提交到SMS Gateway队列
   */
  public final static String STATUS_WAIT = "wait_to_enqueue";
  
  /**
   * SMS STATUS: queued
   *     表示短信息已经成功提交到SMS Gateway的队列
   */
  public final static String STATUS_QUEUED = "queued";
  
  /**
   * SMS STATUS: sent
   *     表示短信息已经由SMS Gateway成功送出
   */
  public final static String STATUS_SENT = "sent";

  /**
   * SMS STATUS: failure
   *     表示短信息没有被SMS Gateway成功送出, 即SMS Gateway发送失败
   */
  public final static String STATUS_FAILURE = "failure";

  /**
   * SMS STATUS: received
   *     表示短信息已经成功被终端设备接收
   */
  public final static String STATUS_RECEIVED = "received";

  /**
   * SMS STATUS: success
   *     预留, 表示任务已经成功完成, 即：配置已经被成功安装
   */
  public final static String STATUS_SUCCESS = "success";

  /**
   * SMS STATUS: cancelled
   *     被取消, 表示被任务队列调度器丢弃
   */
  public final static String STATUS_CANCELLED = "cancelled";

  /**
   * SMS STATUS: unkown
   *     状态未知
   */
  public final static String STATUS_UNKNOW = "unkown";

  /** 
   * @return Client Provider Audit Log of ID 
   */
  public abstract Long getCpAuditLogId() ;
  
  /**
   * @param Client Provider Audit Log of ID 
   */
  public abstract void setCpAuditLogId(Long cpAuditLogId) ;

  /** 
   * @return Client Provider Job ID 
   */
  public abstract long getJobId() ;
  
  /**
   * @param Client Provider Job ID 
   */
  public abstract void setJobId(long cpAuditLogId) ;

  /**
   * @return Send SMS Time
   */
  public abstract Date getTimeStamp() ;
  
  public abstract void setTimeStamp(Date timeStamp) ;

  /**
   * @return PhoneNumber
   */
  public abstract String getDevicePhoneNumber() ;
  
  public abstract void setDevicePhoneNumber(String devicePhoneNumber) ;

  /**
   * @return Manufacturer
   */
  public abstract String getManufacturerExtID() ;
  
  public abstract void setManufacturerExtID(String manufacturerExtID) ;

  /**
   * @return Model
   */
  public abstract String getModelExtID() ;
  
  public abstract void setModelExtID(String modelExtID) ;

  /**
   * @return Carrier
   */
  public abstract String getCarrierExtID() ;
  
  public abstract void setCarrierExtID(String carrierExtID) ;

  /**
   * Status:Send SMS Status
   *        1.Success
   *        2.Failure
   * @return Status
   */
  public abstract String getStatus() ;
  
  public abstract void setStatus(String status) ;

  /**
   * @return Profile Category
   */
  public abstract String getProfileCategoryName() ;
  
  public abstract void setProfileCategoryName(String profileCategoryName) ;

  /**
   * @return Profile Name
   */
  public abstract String getProfileName();
  
  public abstract void setProfileName(String profileName) ;

  /**
   * @return Profile Content (报文内容)
   */
  public abstract String getProfileContent() ;
  
  public abstract void setProfileContent(String profileContent) ;

  /**
   * 发送短消息的SMS Gateway队列项的ID
   * @return Returns the messageID.
   */
  public abstract String getMessageID();

  /**
   * @param messageID The messageID to set.
   */
  public abstract void setMessageID(String messageID);

  /**
   * 发送短消息的SMS Encoder
   * @return Returns the messageID.
   */
  public abstract String getMessageEncoder();

  /**
   * @param Encoder The Encoder to set.
   */
  public abstract void setMessageEncoder(String encoder);

  /**
   * @return Memo
   */
  public abstract String getMemo() ;
  
  public abstract void setMemo(String memo) ;

}
