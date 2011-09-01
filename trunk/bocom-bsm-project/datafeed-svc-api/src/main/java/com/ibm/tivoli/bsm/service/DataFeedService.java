/**
 * 
 */
package com.ibm.tivoli.bsm.service;

import javax.jws.WebService;

/**
 * @author ZhaoDongLu
 *
 */
@WebService
public interface DataFeedService {
   
  /**
   * Receive and process data feed request
   * @param xml
   * @return
   */
  public abstract DataFeedResponse process(String xml);
  
  /**
   * 为应用预留的接口，实现方式待定
   * @param xml
   * @return
   */
  //public abstract DataFeedResponse submitXMLData(String xml);
}
