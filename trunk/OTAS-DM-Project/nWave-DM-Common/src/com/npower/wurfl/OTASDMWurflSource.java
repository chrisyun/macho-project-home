/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/wurfl/OTASDMWurflSource.java,v 1.1 2007/11/13 07:17:52 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/11/13 07:17:52 $
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
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/11/13 07:17:52 $
 */
public class OTASDMWurflSource implements WurflSource {

  private File wurflFile;

  /**
   * 
   */
  public OTASDMWurflSource() throws Exception {
    super();
    String dmHome = System.getProperty("otas.dm.home");
    if (dmHome == null) {
       throw new WurflException("Could not found 'otas.dm.home' from system properties.");
    }
    File dmHomeDir = new File(dmHome);
    File configDir = new File(dmHomeDir, "config");
    wurflFile = new File(configDir, "wurfl/wurfl.xml");
    if (!wurflFile.exists()) {
       throw new WurflException("Could not found wurfl file at " + wurflFile.getAbsolutePath());
    }
  }

  /* (non-Javadoc)
   * @see com.npower.wurfl.WurflSource#getWurflInputStream()
   */
  public InputStream getWurflInputStream() throws IOException {
    FileWurflSource source = new FileWurflSource(wurflFile);
    return source.getWurflInputStream();
  }

  /* (non-Javadoc)
   * @see com.npower.wurfl.WurflSource#getWurflPatchInputStream()
   */
  public InputStream getWurflPatchInputStream() throws IOException {
    FileWurflSource source = new FileWurflSource(wurflFile);
    return source.getWurflPatchInputStream();
  }

}
