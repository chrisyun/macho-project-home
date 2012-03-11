/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/wurfl/FileWurflSource.java,v 1.2 2007/11/13 10:30:33 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2007/11/13 10:30:33 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.wurfl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/11/13 10:30:33 $
 */
public class FileWurflSource implements WurflSource {
  private static Log log = LogFactory.getLog(FileWurflSource.class);
  
  private File source = null;

  /**
   * 
   */
  public FileWurflSource(File file) {
    super();
    this.source = file;
  }

  /* (non-Javadoc)
   * @see com.npower.wurfl.WurflSource#getWurflInputStream()
   */
  public InputStream getWurflInputStream() throws IOException {
    return new FileInputStream(this.source);
  }

  /* (non-Javadoc)
   * @see com.npower.wurfl.WurflSource#getWurflPatchInputStream()
   */
  public InputStream getWurflPatchInputStream() throws IOException {
    String fileName = source.getAbsolutePath();
    if (fileName.matches("(.*)wurfl\\.xml$")) {
      int fc = fileName.indexOf("wurfl.xml");
      String dir = fileName.substring(0, fc);
      String patchfile = dir + "wurfl_patch.xml";

      File f = new File(patchfile);
      if (f.exists() && f.canRead()) {
        log.info("potential patchfile: " + patchfile);
        return new FileInputStream(patchfile);
      } else {
        log.info("potential patchfile: " + patchfile + " does not exist, or is not readable");
      }
    }
    return null;
  }

}
