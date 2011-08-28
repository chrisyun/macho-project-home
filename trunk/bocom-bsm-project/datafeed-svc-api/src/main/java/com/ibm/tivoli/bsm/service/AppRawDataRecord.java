/**
 * 
 */
package com.ibm.tivoli.bsm.service;

import java.text.ParseException;
import java.util.Date;

/**
 * @author ZhaoDongLu
 * 
 */
public class AppRawDataRecord {

  private String metricId = null;
  private Date timestamp = null;
  private String ip = null;
  private String type = null;
  private double value = 0.0;
  
  //added by Xue Dayu
  private String indexType = null;
  private String monType = null;

  /**
   * 选填项
   */
  private String node = null;
  /**
   * 选填项
   */
  private String bizName = null;
  
  private String branchName = null;
  private String appName = null;

  /**
   * 
   */
  public AppRawDataRecord() {
    super();
  }

  public AppRawDataRecord(Date timestamp, String ip, String metricId, double value) {
    super();
    this.timestamp = timestamp;
    this.ip = ip;
    this.metricId = metricId;
    this.value = value;
  }

  public String getMetricId() {
    return metricId;
  }

  public void setMetricId(String metricId) {
    this.metricId = metricId;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) throws ParseException {
    this.timestamp = timestamp;
  }

  public String getBizName() {
    return bizName;
  }

  public void setBizName(String bizName) {
    this.bizName = bizName;
  }

  public String getBranchName() {
    return branchName;
  }

  public void setBranchName(String branchName) {
    this.branchName = branchName;
  }

  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }
  
  public String getNode() {
    return node;
  }

  public void setNode(String node) {
    this.node = node;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
  
  public String getMonType() {
	return monType;
  }

  public void setMonType(String monType) {
	this.monType = monType;
  }
  
  public String getIndexType() {
	  return indexType;
  }
  
  public void setIndexType(String indexType) {
	  this.indexType = indexType;
  }
  

  public String toString() {
    //TODO YanYaWei Implements this method, "[metricId=xxx, appName=xxx, bizName=, ...]"
    StringBuffer buf = new StringBuffer();
    buf.append('[');
    buf.append("metricDesc=");
    buf.append((this.getMetricId() == null)?"":this.getMetricId());
    return buf.toString();
  }

}
