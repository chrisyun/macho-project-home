/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/msm/tools/PackageCheckerImpl.java,v 1.2 2008/05/08 06:54:26 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/05/08 06:54:26 $
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
package com.npower.dm.msm.tools;


import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;

import com.npower.dm.core.DMException;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/05/08 06:54:26 $
 */
public class PackageCheckerImpl implements PackageChecker {

  /**
   * 
   */
  public PackageCheckerImpl() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.msm.tools.PackageChecker#getPackageMetaInfo(java.lang.String)
   */
  public PackageMetaInfo getPackageMetaInfo(String url) throws DMException {
    HttpClient client = new HttpClient();
    HttpMethod method = new GetMethod(url);
    // Provide custom retry handler is necessary
    method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));

    PackageMetaInfo metaInfo = new PackageMetaInfo();
    metaInfo.setUrl(url);
    try {
      // Execute the method.
      int statusCode = client.executeMethod(method);
      metaInfo.setServerStatus(statusCode);
      if (statusCode != HttpStatus.SC_OK) {
         // System.err.println("Method failed: " + method.getStatusLine());
      }

      // Read the response body.
      byte[] responseBody = method.getResponseBody();
      System.out.println(new String(responseBody));
      
      Header mimeType = method.getResponseHeader("Content-Type");
      if (mimeType != null) {
         metaInfo.setMimeType(mimeType.getValue());
      }
      
      Header contentLength = method.getResponseHeader("Content-Length");
      if (contentLength != null && StringUtils.isNotEmpty(contentLength.getValue())) {
         metaInfo.setSize(Integer.parseInt(contentLength.getValue()));
      }

    } catch (HttpException e) {
      metaInfo.setErrorMessage(e.getMessage());
    } catch (IOException e) {
      metaInfo.setErrorMessage(e.getMessage());
    } finally {
      // Release the connection.
      method.releaseConnection();
    } 
    return metaInfo;
  }

}
