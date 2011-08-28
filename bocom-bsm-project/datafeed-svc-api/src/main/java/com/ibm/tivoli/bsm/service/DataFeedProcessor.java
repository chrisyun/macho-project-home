/**
 * 
 */
package com.ibm.tivoli.bsm.service;

import java.io.InputStream;
import java.io.Reader;

/**
 * @author ZhaoDongLu
 *
 */
public interface DataFeedProcessor {
 
  /**
   * Preocess a data feed request.
   * @return
   */
  public abstract void process(Reader xmlIn) throws ServiceException;
  
  /**
   * Preocess a data feed request.
   * @return
   */
  public abstract void process(InputStream xmlIn) throws ServiceException;
  
}
