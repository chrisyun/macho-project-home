/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/xml/LocalEntityResolver.java,v 1.2 2008/12/15 03:46:44 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/12/15 03:46:44 $
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
package com.npower.dm.xml;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/12/15 03:46:44 $
 */
public class LocalEntityResolver implements EntityResolver {

  private static Log log = LogFactory.getLog(LocalEntityResolver.class);
  /**
   * 
   */
  public LocalEntityResolver() {
    super();
  }

  /* (non-Javadoc)
   * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
   */
  public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
    if (StringUtils.isNotEmpty(systemId)) {
       if (systemId.toLowerCase().startsWith("http://www.openmobilealliance.org/tech/DTD".toLowerCase())) {
         String filename = systemId.substring(systemId.lastIndexOf('/') + 1, systemId.length());
         File dtdDir = new File(System.getProperty("otas.dm.home"), "dtd/ddf");
         File dtdFile = new File(dtdDir, filename);
         if (dtdFile.exists()) {
            log.info("Using local dtd file: " + dtdFile.getAbsolutePath());
            return new InputSource(dtdFile.getAbsolutePath());
         }
       }
    }
    //log.info("Local DTD not found, using original: " + systemId);
    //return new InputSource(systemId);
    
    log.info("Local DTD not found, ignore: " + systemId);
    return new InputSource(new StringReader(""));
  }

}
