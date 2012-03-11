/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/SyncResultWriter.java,v 1.3 2008/11/18 08:16:47 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2008/11/18 08:16:47 $
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
 * @version $Revision: 1.3 $ $Date: 2008/11/18 08:16:47 $
 */
public class SyncResultWriter {
  private static Log log = LogFactory.getLog(SyncResultWriter.class);
  
  private static final String SUCCESS_FLAG = "0";
  private static final String SUB_DIR_FOR_RIGHT = "right";
  private static final String SUB_DIR_FOR_BAD = "bad";
  private static final String SUB_DIR_FOR_SEMI_WRONG = "semiwrong";
  
  private int totalRecords = 0;
  private int errorRecords = 0;
  private int successRecords = 0;
  
  /**
   * Request File Object.
   */
  private File requestFile;

  private File bodyFile;

  private Writer bodyWriter;
  private File responseDir;

  /**
   * 构造回执文件生成器
   * @param requestFile
   *        请求文件 
   * @param responseDir
   *        回执文件存放的基础目录, 在此目录下虚包含right, bad和semiwrong等子目录
   */
  public SyncResultWriter(File requestFile, File responseDir) {
    super();
    this.requestFile = requestFile;
    this.responseDir = responseDir;
  }
  
  /**
   * 打开Writer, 准备写入数据
   * @throws IOException
   */
  public void open() throws IOException {
    bodyFile = File.createTempFile("otas_sync_error", "body");
    bodyWriter = new FileWriter(bodyFile);
    if (log.isDebugEnabled()) {
       log.debug("open reponse body file: " + this.bodyFile.getAbsolutePath());
    }
  }
  
  /**
   * 关闭Writer
   * @throws IOException
   */
  public void close() throws IOException {
    this.bodyWriter.close();

    File outputFile = null;

    // 计算输出文件路径及文件名
    String requestFilename = this.requestFile.getName();
    String reponseFilename = requestFilename.substring(0, requestFilename.length() - ".req".length()) + ".res";
    if (this.totalRecords == this.successRecords) {
       // 完全正确
       File outputDir = new File(this.responseDir, SUB_DIR_FOR_RIGHT);
       if (!outputDir.exists()) {
          log.info("create directory: " + outputDir.getAbsolutePath());
          outputDir.mkdirs();
       }
       outputFile = new File(outputDir, reponseFilename);
    } else if (this.totalRecords == this.errorRecords) {
      // 完全错误
      File outputDir = new File(this.responseDir, SUB_DIR_FOR_BAD);
      if (!outputDir.exists()) {
         log.info("create directory: " + outputDir.getAbsolutePath());
         outputDir.mkdirs();
      }
      outputFile = new File(outputDir, reponseFilename);
    } else {
      // 部分错误
      File outputDir = new File(this.responseDir, SUB_DIR_FOR_SEMI_WRONG);
      outputFile = new File(outputDir, reponseFilename);
      if (!outputDir.exists()) {
         log.info("create directory: " + outputDir.getAbsolutePath());
         outputDir.mkdirs();
      }
    }
    
    log.info("output reponse file: " + outputFile.getAbsolutePath());
    
    // 输出文件头信息
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    RequestHeader header = RequestHeader.parseHeaderInfo(this.requestFile);
    Writer writer = new FileWriter(outputFile);
    writer.write(header.getSerialNumber());
    writer.write('\t');
    writer.write(header.getVersion());
    writer.write('\t');
    writer.write(format.format(new Date()));
    writer.write('\t');
    writer.write("河南");
    writer.write('\t');
    writer.write(this.totalRecords);
    writer.write('\t');
    writer.write(this.successRecords);    
    writer.write('\t');
    writer.write('\n');
    
    if (this.totalRecords != this.successRecords && this.totalRecords != this.errorRecords) {
       // 部分错误
       FileReader in = new FileReader(this.bodyFile);
       char[] buf = new char[512];
       int len = in.read(buf);
       while (len > 0) {
             writer.write(buf, 0, len);
             len = in.read(buf);
       }
       in.close();
    }
    
    writer.close();
  }
  
  /**
   * 输出一个Item
   * @param item
   * @throws IOException
   */
  public void write(SyncItem item, String errorCode) throws IOException {
    if (item == null) {
       return;
    }
    this.totalRecords++;
    if (SUCCESS_FLAG.equals(errorCode)) {
       this.successRecords++;
    } else {
      this.errorRecords++;
      // 输出错误信息
      this.bodyWriter.write(item.getId());
      this.bodyWriter.write('\t');
      this.bodyWriter.write(errorCode);
      this.bodyWriter.write('\n');
    }
  }

}
