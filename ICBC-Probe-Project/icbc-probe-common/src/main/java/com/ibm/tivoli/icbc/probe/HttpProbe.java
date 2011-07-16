/**
 * 
 */
package com.ibm.tivoli.icbc.probe;

import com.ibm.tivoli.icbc.result.http.HttpResult;

/**
 * @author Zhao Dong Lu
 *
 */
public interface HttpProbe extends Probe<HttpResult> {
  
  public HttpResult run() throws ProbeException;

}
