/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/FileSyncItemWriter.java,v 1.5 2008/11/20 09:17:03 zhao Exp $
 * $Revision: 1.5 $
 * $Date: 2008/11/20 09:17:03 $
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2008/11/20 09:17:03 $
 */
public abstract class FileSyncItemWriter implements SyncItemWriter {

  private static Log log = LogFactory.getLog(FileSyncItemWriter.class);
  
  private File file = null;
  private BufferedWriter writer = null;

  private int totalRecords = 0;
  private File bodyFile;
  private Date fromDate = new Date();
  private Date toDate = new Date();
  /**
   * 
   */
  private FileSyncItemWriter(File file) {
    super();
    this.file = file;
  }

  /**
   * @param file
   * @param fromDate
   * @param toDate
   */
  public FileSyncItemWriter(File file, Date fromDate, Date toDate) {
    this(file);
    this.fromDate = fromDate;
    this.toDate = toDate;
  }

  /**
   * @return the fromDate
   */
  public Date getFromDate() {
    return fromDate;
  }

  /**
   * @param fromDate the fromDate to set
   */
  public void setFromDate(Date fromDate) {
    this.fromDate = fromDate;
  }

  /**
   * @return the toDate
   */
  public Date getToDate() {
    return toDate;
  }

  /**
   * @param toDate the toDate to set
   */
  public void setToDate(Date toDate) {
    this.toDate = toDate;
  }

  /* (non-Javadoc)
   * @see com.npower.unicom.sync.SyncItemWriter#close()
   */
  public void close() throws IOException {
    this.writer.flush();
    this.writer.close();
    
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    Writer out = new FileWriter(this.file);
    out.write("0001");
    out.write('\t');
    out.write("00000");
    out.write('\t');
    out.write(format.format(new Date()));
    out.write('\t');
    out.write("ZB");
    out.write('\t');
    
    String from = (fromDate != null)?format.format(fromDate):"19700101000000";
    String to = (toDate != null)?format.format(toDate):"19700101000000";
    
    out.write(from);
    out.write('\t');
    out.write(to);
    out.write('\t');
    out.write(Integer.toString(this.totalRecords));
    out.write('\t');
    out.write('\n');
    // 输出文件体
    FileReader in = new FileReader(this.bodyFile);
    char[] buf = new char[512];
    int len = in.read(buf);
    while (len > 0) {
          out.write(buf, 0, len);
          out.flush();
          len = in.read(buf);
    }
    in.close();
    out.close();
  }

  /* (non-Javadoc)
   * @see com.npower.unicom.sync.SyncItemWriter#open()
   */
  public void open() throws IOException {
    if (this.writer == null) {
       this.bodyFile = File.createTempFile("otas_output", "body");
       this.writer = new BufferedWriter(new FileWriter(this.bodyFile));
    } else {
      log.warn("[ " + this.getClass().getSimpleName() + "] was opened!");
    }
  }

  /* (non-Javadoc)
   * @see com.npower.unicom.sync.SyncItemWriter#write(com.npower.unicom.sync.SyncItem)
   */
  public void write(SyncItem item) throws IOException {
    if (item != null) {
       this.writer.write(this.convertToString(item));
       this.writer.flush();
       this.totalRecords++;
    }
  }

  /**
   * 将SyncItem转换为文件行数据.各属性间使用‘\t’分隔，在生成记录时若相应的属性值为空，直接用分隔符隔开，分隔符之间无其他符号
   * @param item
   * @return
   */
  protected abstract String convertToString(SyncItem item);

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
