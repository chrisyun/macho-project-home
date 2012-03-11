/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/RequestHeader.java,v 1.1 2008/11/18 07:00:59 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/11/18 07:00:59 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
 *
 * This SOURCE CODE FILE, which has been provided by NPower as part
 * of a NPower product for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information of NPower.
 *
 * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
 * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
 * THE PRODUCT.
 *
 * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
 * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
 * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
 * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
 * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
 * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
 * CODE FILE.
 * ===============================================================================================
 */
package com.npower.unicom.sync;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/11/18 07:00:59 $
 */
public class RequestHeader {
  private static Log log = LogFactory.getLog(RequestHeader.class);
  
  private File file = null;
  
  private String serialNumber = "0001";
  private String version = "0000";
  private Date generatedTime = new Date();
  private String generatedBy = null;
  private Date startTime = null;
  private Date endTime = null;
  private int totalRecords = 0;
  private String memo = null;

  /**
   * 
   */
  public RequestHeader() {
    super();
  }
  
  /**
   * 从文件中提取文件头信息
   * @param file
   * @return
   * @throws IOException
   */
  public static RequestHeader parseHeaderInfo(File file) throws IOException {
    // 提取文件头信息
    FileReader in = new FileReader(file);
    BufferedReader reader = new BufferedReader(in);
    RequestHeader header = parseHeaderInfo(reader);
    in.close();
    // 设置文件头信息对应的文件
    header.setFile(file);
    return header;
  }

  /**
   * 从Reader中提取文件头信息
   * @param reader
   * @return
   * @throws IOException
   */
  public static RequestHeader parseHeaderInfo(Reader in) throws IOException {
    BufferedReader reader = new BufferedReader(in);
    String line = reader.readLine();
    RequestHeader header = null;
    
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    if (StringUtils.isNotEmpty(line)) {
       String[] cols = StringUtils.splitPreserveAllTokens(line, '\t');
       header = new RequestHeader();
       header.setSerialNumber(cols[0]);
       header.setVersion(cols[1]);
       try {
           header.setGeneratedTime(format.parse(cols[2]));
       } catch (ParseException e) {
         log.error("error to parse generated time: " + cols[2], e);
       }
       header.setGeneratedBy(cols[3]);
       try {
         header.setStartTime(format.parse(cols[4]));
       } catch (ParseException e) {
         log.error("error to parse start time: " + cols[4], e);
       }
       try {
         header.setEndTime(format.parse(cols[5]));
       } catch (ParseException e) {
         log.error("error to parse end time: " + cols[5], e);
       }
       header.setTotalRecords(Integer.parseInt(cols[6]));
       header.setMemo(cols[7]);
    }
    reader.close();
    return header;
  }

  /**
   * @return the serialNumber
   */
  public String getSerialNumber() {
    return serialNumber;
  }

  /**
   * @param serialNumber the serialNumber to set
   */
  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  /**
   * @return the version
   */
  public String getVersion() {
    return version;
  }

  /**
   * @param version the version to set
   */
  public void setVersion(String version) {
    this.version = version;
  }

  /**
   * @return the generatedTime
   */
  public Date getGeneratedTime() {
    return generatedTime;
  }

  /**
   * @param generatedTime the generatedTime to set
   */
  public void setGeneratedTime(Date generatedTime) {
    this.generatedTime = generatedTime;
  }

  /**
   * @return the generatedBy
   */
  public String getGeneratedBy() {
    return generatedBy;
  }

  /**
   * @param generatedBy the generatedBy to set
   */
  public void setGeneratedBy(String generatedBy) {
    this.generatedBy = generatedBy;
  }

  /**
   * @return the startTime
   */
  public Date getStartTime() {
    return startTime;
  }

  /**
   * @param startTime the startTime to set
   */
  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  /**
   * @return the endTime
   */
  public Date getEndTime() {
    return endTime;
  }

  /**
   * @param endTime the endTime to set
   */
  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  /**
   * @return the totalRecords
   */
  public int getTotalRecords() {
    return totalRecords;
  }

  /**
   * @param totalRecords the totalRecords to set
   */
  public void setTotalRecords(int totalRecords) {
    this.totalRecords = totalRecords;
  }

  /**
   * @return the memo
   */
  public String getMemo() {
    return memo;
  }

  /**
   * @param memo the memo to set
   */
  public void setMemo(String memo) {
    this.memo = memo;
  }

  /**
   * @return the file
   */
  public File getFile() {
    return file;
  }

  /**
   * @param file the file to set
   */
  public void setFile(File file) {
    this.file = file;
  }

}
