/**
 * 
 */
package com.ibm.tivoli.bsm.service;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author ZhaoDongLu
 *
 */
public class ConsoleRecordDAOImpl implements RecordDAO {
  private static Log log = LogFactory.getLog(ConsoleRecordDAOImpl.class);
  /**
   * 
   */
  public ConsoleRecordDAOImpl() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ibm.tbsm.hc.datafeed.RecordDAO#save(com.ibm.tbsm.hc.datafeed.Record)
   */
  public void save(AppRawDataRecord record) throws ServiceException {
    if (record != null) {
       log.info(record.toString());
    } else {
      log.info(record);
    }
  }

  public void beginTransaction() throws ServiceException {
    // TODO Auto-generated method stub
    
  }

  public void close() throws ServiceException {
    // TODO Auto-generated method stub
    
  }

  public void commit() throws ServiceException {
    // TODO Auto-generated method stub
    
  }

  public void initialize() throws ServiceException {
    // TODO Auto-generated method stub
    
  }

  public void rollback() throws ServiceException {
    // TODO Auto-generated method stub
    
  }

  public void save(Object record) throws ServiceException {
    // TODO Auto-generated method stub
    
  }

}
