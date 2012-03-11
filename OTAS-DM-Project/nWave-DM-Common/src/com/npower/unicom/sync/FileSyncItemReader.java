/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/FileSyncItemReader.java,v 1.2 2008/11/18 07:00:59 zhao Exp $
 * $Revision: 1.2 $
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

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/11/18 07:00:59 $
 */
public abstract class FileSyncItemReader implements SyncItemReader {

  private File file= null;
  private BufferedReader reader = null;
  private RequestHeader header = null;

  /**
   * 
   */
  public FileSyncItemReader(File file) {
    super();
    this.file = file;
  }

  /**
   * @return the header
   */
  public RequestHeader getHeader() {
    return header;
  }

  /**
   * @param header the header to set
   */
  public void setHeader(RequestHeader header) {
    this.header = header;
  }

  /* (non-Javadoc)
   * @see com.npower.unicom.sync.SyncItemReader#close()
   */
  public void close() throws IOException {
    this.reader.close();
  }

  /* (non-Javadoc)
   * @see com.npower.unicom.sync.SyncItemReader#open()
   */
  public void open() throws IOException {
    // 提取文件头信息
    this.header = RequestHeader.parseHeaderInfo(this.file);
    // 打开文件, 为读取做准备
    this.reader = new BufferedReader(new FileReader(this.file));
    // 跳过第一行文件头信息
    this.reader.readLine();
  }

  /* (non-Javadoc)
   * @see com.npower.unicom.sync.SyncItemReader#read()
   */
  public SyncItem read() throws IOException, ParseException {
    String line = this.reader.readLine();
    if (line != null) {
       SyncItem item = this.parse(line);   
       return item;
    } else {
      return null;
    }
  }

  /**
   * 实现对数据行的解析处理
   * @param line
   * @return
   */
  protected abstract SyncItem parse(String line) throws ParseException;

}
