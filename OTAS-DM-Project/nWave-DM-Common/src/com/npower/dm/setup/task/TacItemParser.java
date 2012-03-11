/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/TacItemParser.java,v 1.1 2008/10/31 10:03:04 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/10/31 10:03:04 $
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
package com.npower.dm.setup.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/10/31 10:03:04 $
 */
public class TacItemParser {
  
  private File file = null;
  
  /**
   * 
   */
  public TacItemParser() {
    super();
  }


  /**
   * @param file
   */
  public TacItemParser(File file) {
    super();
    this.file = file;
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
  
  private String trim(String s) {
    return StringUtils.trim(StringUtils.strip(s, "\"';"));
  }
  
  private boolean boolValue(String s) {
    boolean result = false;
    if (StringUtils.isEmpty(s)) {
       return result;
    }
    if (s.equalsIgnoreCase("1")) {
       return true;
    } else {
      return false;
    }
  }
  
  /**
   * @return
   * @throws Exception
   */
  public TacItem getNext() throws Exception {
    String line = this.getNextLine();
    
    if (StringUtils.isEmpty(line)) {
       return null;
    }
    String[] cols = StringUtils.splitPreserveAllTokens(line, ";");
    if (cols.length != 20) {
       throw new Exception("More columns in line#" + this.lineNumber + ":" + line);
    }
    TacItem item = new TacItem(this.lineNumber);
    item.setTac(this.trim(cols[0]));
    item.setHandsetBrand(this.trim(cols[1]));
    item.setHandsetModel(this.trim(cols[2]));
    item.setReportingBody(this.trim(cols[3]));
    item.setApprovedIn(this.trim(cols[4]));
    item.setGsm450(this.boolValue(this.trim(cols[5])));
    item.setGsm850(this.boolValue(this.trim(cols[6])));
    item.setGsm900(this.boolValue(this.trim(cols[7])));
    item.setGsm1800(this.boolValue(this.trim(cols[8])));
    item.setGsm1900(this.boolValue(this.trim(cols[9])));
    item.setGsmr(this.boolValue(this.trim(cols[10])));
    item.setGsm3(this.boolValue(this.trim(cols[11])));
    item.setFoma(this.boolValue(this.trim(cols[12])));
    item.setIden800(this.boolValue(this.trim(cols[13])));
    item.setCdma800(this.boolValue(this.trim(cols[14])));
    item.setCdma1800(this.boolValue(this.trim(cols[15])));
    item.setCdma1900(this.boolValue(this.trim(cols[16])));
    item.setTdma(this.boolValue(this.trim(cols[17])));
    item.setSatellite(this.boolValue(this.trim(cols[18])));
    return item;
  }

  private BufferedReader reader = null;
  /**
   * @throws FileNotFoundException
   */
  public void open() throws FileNotFoundException  {
    if (this.reader == null) {
       this.reader = new BufferedReader(new FileReader(this.getFile()));
    }
  }
  
  /**
   * @throws IOException
   */
  public void close() throws IOException {
    if (this.reader != null) {
       this.reader.close();
    }
  }
  
  private int lineNumber = 0;
  /**
   * @return
   * @throws IOException
   */
  private String getNextLine() throws IOException {
    this.lineNumber++;
    String line = this.reader.readLine();
    if (this.lineNumber == 1) {
       line = this.reader.readLine();
    }
    return line;
  }
}
