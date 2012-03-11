/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/xml/NoopEntityResolver.java,v 1.1 2008/12/15 03:24:14 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/12/15 03:24:14 $
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

import java.io.IOException;
import java.io.StringBufferInputStream;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * SAX Entity Resolver for nothing to do.
 * 如果不需要XML Validator 下载DTD, 则可以使用此EntityRecolver.
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/12/15 03:24:14 $
 */
public class NoopEntityResolver implements EntityResolver {

  /**
   * 
   */
  public NoopEntityResolver() {
    super();
  }

  /* (non-Javadoc)
   * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
   */
  public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
    return new InputSource(new StringBufferInputStream(""));
  }

}
