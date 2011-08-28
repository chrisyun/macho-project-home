/**
 * 
 */
package com.ibm.tivoli.bsm.service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhaoDongLu
 *
 */
public class SimpleRecordDAOImpl implements RecordDAO {
  
  private List<AppRawDataRecord> result = new ArrayList<AppRawDataRecord>();

  /**
   * 
   */
  public SimpleRecordDAOImpl() {
    super();
  }

  public void save(AppRawDataRecord record) throws ServiceException {
    result.add(record);
  }

  public List<AppRawDataRecord> getResult() {
    return result;
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
