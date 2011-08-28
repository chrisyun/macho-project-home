/**
 * 
 */
package com.ibm.tivoli.bsm.service;

import java.io.StringReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author ZhaoDongLu
 * 
 */
public class SimpleDataFeedServiceImpl implements DataFeedService {
  private static Log log = LogFactory.getLog(SimpleDataFeedServiceImpl.class);

  private DataFeedProcessor dataFeedProcessor = null;

  /**
   * Tracking number of active threads
   */
  private int totalActiveThread = 0;

  /**
   * Default constructor
   */
  public SimpleDataFeedServiceImpl() {
    super();
  }

  public DataFeedProcessor getDataFeedProcessor() {
    return dataFeedProcessor;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.tbsm.hc.datafeed.DataFeedService#process(java.lang.String)
   */
  public DataFeedResponse process(String xml) {
    DataFeedResponse response = new DataFeedResponse();
    try {
      this.totalActiveThread++;
      log.info("receiving a datafeed request, current active thread: " + this.totalActiveThread);
      this.dataFeedProcessor.process(new StringReader(xml));
      response.setStatus(DataFeedResponse.STATUS_SUCCESS);
    } catch (DataFeedException e) {
      log.error("fail to process datafeed request, cause: " + e.getMessage(), e);
      response.setStatus("" + e.getReturnCode());
      response.setMessage(e.getMessage());
    } catch (Throwable e) {
      log.error("fail to process datafeed request, cause: " + e.getMessage(), e);
      response.setStatus(DataFeedResponse.STATUS_FAIL);

      Throwable ex = e;
      StringBuffer buf = new StringBuffer();
      int deep = 0;
      while (ex != null && deep < 10) {
        buf.append(ex.getMessage());
        buf.append(';');
        deep++;
        ex = ex.getCause();
      }
      response.setMessage(buf.toString());
    } finally {
      this.totalActiveThread--;
    }
    return response;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tbsm.hc.datafeed.DataFeedService#setDataFeedProcessor(com.ibm.tbsm
   * .hc.datafeed.DataFeedProcessor)
   */
  public void setDataFeedProcessor(DataFeedProcessor processor) {
    this.dataFeedProcessor = processor;
  }

  public DataFeedResponse submitXMLData(String xml) {
    return this.process(xml);
  }

}
