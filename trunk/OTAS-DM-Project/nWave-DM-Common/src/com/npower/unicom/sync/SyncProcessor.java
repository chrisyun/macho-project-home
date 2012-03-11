/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/SyncProcessor.java,v 1.5 2008/11/20 09:17:03 zhao Exp $
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

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.core.DMException;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2008/11/20 09:17:03 $
 */
public class SyncProcessor {
  
  private static Log log = LogFactory.getLog(SyncProcessor.class);
  
  private SyncItemReader reader; 
  private SyncItemWriter writer;
  private SyncResultWriter result;

  /**
   * 
   */
  public SyncProcessor() {
    super();
  }

  /**
   * @param reader
   * @param writer
   * @param result
   */
  public SyncProcessor(SyncItemReader reader, SyncItemWriter writer, SyncResultWriter result) {
    super();
    this.reader = reader;
    this.writer = writer;
    this.result = result;
  }

  /**
   * @return the reader
   */
  public SyncItemReader getReader() {
    return reader;
  }

  /**
   * @param reader the reader to set
   */
  public void setReader(SyncItemReader reader) {
    this.reader = reader;
  }

  /**
   * @return the writer
   */
  public SyncItemWriter getWriter() {
    return writer;
  }

  /**
   * @param writer the writer to set
   */
  public void setWriter(SyncItemWriter writer) {
    this.writer = writer;
  }

  /**
   * @return the result
   */
  public SyncResultWriter getResult() {
    return result;
  }

  /**
   * @param result the result to set
   */
  public void setResult(SyncResultWriter result) {
    this.result = result;
  }

  /**
   * 启动同步数据流程, 同步情况保存在Result中.
   * @param reader
   * @param writer
   * @param result
   * @throws DMException
   */
  public void sync() throws DMException, IOException {
    log.info("Starting sync data [" + this.reader.getClass().getName() + "] -> [" + this.writer.getClass().getName() + "]");
    this.reader.open();
    this.writer.open();
    if (this.result != null) {
       this.result.open();
    }
    
    SyncItem item = null;
    String errorCode = "0";
    int total = 0;
    do {
      try {
          item = this.reader.read();
          if (item != null) {
             this.writer.write(item);
             total++;
          }
      } catch (Exception e) {
        errorCode = "1";
        log.error(e.getMessage(), e);
      } finally {
        if (this.result != null) {
           // 通知当前记录的处理情况
           this.result.write(item, errorCode);
        }
      }
    } while (item != null);
    
    log.info("Finished sync data: items [" + total + "] [" + this.reader.getClass().getName() + "] -> [" + this.writer.getClass().getName() + "]");
    
    this.reader.close();
    this.writer.close();
    if (this.result != null) {
       this.result.close();
    }
  }
  
}
