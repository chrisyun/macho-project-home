/**
 * 
 */
package com.ibm.tivoli.bsm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhaodonglu
 * 
 */
public class TopNData {
  private Date timestamp = null;
  private String ip = null;

  private String name = null;
  private List<TopNRecord> records = new ArrayList<TopNRecord>();

  /**
   * 
   */
  public TopNData() {
    super();
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the records
   */
  public List<TopNRecord> getRecords() {
    return records;
  }

  /**
   * @param records
   *          the records to set
   */
  public void setRecords(List<TopNRecord> records) {
    this.records = records;
  }
  
  public void addRecord(TopNRecord record) {
    this.records.add(record);
  }

  /**
   * @return the timestamp
   */
  public Date getTimestamp() {
    return timestamp;
  }

  /**
   * @param timestamp the timestamp to set
   */
  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  /**
   * @return the ip
   */
  public String getIp() {
    return ip;
  }

  /**
   * @param ip the ip to set
   */
  public void setIp(String ip) {
    this.ip = ip;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    final int maxLen = 100;
    return String.format("TopNData [timestamp=%s, ip=%s, name=%s, records=%s]", timestamp, ip, name,
        records != null ? records.subList(0, Math.min(records.size(), maxLen)) : null);
  }

}
