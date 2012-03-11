package com.npower.dm.management;

import java.util.Date;
import java.util.List;

import com.npower.dm.core.ClientProvAuditLog;
import com.npower.dm.core.DMException;

public interface ClientProvAuditLogBean {
  
  
  /**
   * Create a instance of ClientProvAuditLog
   * @return
   * @throws DMException
   */
  public abstract ClientProvAuditLog newClientProvAuditLogInstance() throws DMException;
  
  /**
   * @param cpAuditLogId
   * @param timeStamp
   * @param devicePhoneNumber
   * @param status
   * @return
   * @throws DMException
   */
  public ClientProvAuditLog newClientProvAuditLogInstance(Date timeStamp, String devicePhoneNumber, String status) throws DMException;
  /**
   * Find a ClientProvAuditLogEntity by ClientProvAuditLogEntity ID
   * 
   * @param id
   * @return ClientProvAuditLogEntity
   * @throws DMException
   */
  public abstract ClientProvAuditLog getClientProvAuditLogByID(long id) throws DMException;


  /**
   * Add or update a ClientProvAuditLogEntity into DM inventory.
   * 
   * @param clientProvAuditLog
   * @throws DMException
   */
  public abstract void update(ClientProvAuditLog clientProvAuditLog) throws DMException;

  /**
   * Retrieve all of ClientProvAuditLogs.
   * 
   * @return List Array of ClientProvAuditLogEntity
   * @throws DMException
   */
  public abstract List<ClientProvAuditLog> getAllClientProvAuditLogs() throws DMException;

  /**
   * Retrieve all of ClientProvAuditLogs. 
   * For example: from ClientProvAuditLogEntity where ...
   * 
   * 
   * @return List Array of ClientProvAuditLogEntity
   * @throws DMException
   */
  public abstract List<ClientProvAuditLog> findClientProvAuditLogs(String whereClause) throws DMException;

  /**
   * Delete a ClientProvAuditLog. it will remove all of ClientProvAuditLog with carrier, except audit
   * log.
   * 
   * @param clientProvAuditLog
   * @throws DMException
   */
  public abstract void delete(ClientProvAuditLog clientProvAuditLog) throws DMException;

  /**
   * 取得 ClientProvAuditLog 的下一项 
   * @param CPAuditLogID
   * @return
   * @throws DMException
   */
  public abstract Long getNextID(Long CPAuditLogID)throws DMException;
  
  /**
   * 取得 ClientProvAuditLog 的前一项 
   * @param CPAuditLogID
   * @return
   * @throws DMException
   */
  public abstract Long getPrevID(Long CPAuditLogID)throws DMException;
  
  /**
   * 根据电话号码和状态取得最新 ClientProvAuditLog 的最新时间 
   * @param phoneNumber
   * @param status
   *        发送状态信息. 可以为null, 表示提取任何状态下的最新信息;
   * @return
   * @throws DMException
   */
  public abstract long getLatestSendTime(String phoneNumber, String status)throws DMException;
  
  /**
   * 根据电话号码和状态取得最新 ClientProvAuditLog 的最新时间.
   * select from state1 or state2 or state3
   * @param phoneNumber
   * @param states
   * @return
   * @throws DMException
   */
  public abstract long getLatestSendTime(String phoneNumber, String[] states)throws DMException;
}
