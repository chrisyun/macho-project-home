/**
 * 
 */
package com.ibm.tivoli.bsm.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author ZhaoDongLu
 *
 */
public class JDBCAppRawDataRecordDAOImpl extends BaseJDBCService implements RecordDAO<AppRawDataRecord> {

  private static Log log = LogFactory.getLog(JDBCAppRawDataRecordDAOImpl.class);
  
  private String dataSourceName = null;

  private String jdbcUrl = "jdbc:oracle:thin:@127.0.0.1:1521:netcool";
  private String jdbcUser = "icbchc";
  private String jdbcPassword = "icbchc";
  private String jdbcDriverClass = "oracle.jdbc.OracleDriver";
  private String charset = null;

  private Map<String, String> translateMap = new HashMap<String, String>();

  private Connection connnection = null;

  /**
   * 是否清除时间戳中的秒，true时只保留分钟，秒值被置为0
   */
  private boolean trimSecond = true;
  /**
   * 
   */
  public JDBCAppRawDataRecordDAOImpl() {
    super();
  }

  public String getCharset() {
    return charset;
  }

  public void setCharset(String charset) {
    this.charset = charset;
  }

  public String getDataSourceName() {
    return dataSourceName;
  }

  public void setDataSourceName(String dataSourceName) {
    this.dataSourceName = dataSourceName;
  }

  public String getJdbcUrl() {
    return jdbcUrl;
  }

  public void setJdbcUrl(String jdbcUrl) {
    this.jdbcUrl = jdbcUrl;
  }

  public String getJdbcUser() {
    return jdbcUser;
  }

  public void setJdbcUser(String jdbcUser) {
    this.jdbcUser = jdbcUser;
  }

  public String getJdbcPassword() {
    return jdbcPassword;
  }

  public void setJdbcPassword(String jdbcPassword) {
    this.jdbcPassword = jdbcPassword;
  }

  public String getJdbcDriverClass() {
    return jdbcDriverClass;
  }

  public void setJdbcDriverClass(String jdbcDriverClass) {
    this.jdbcDriverClass = jdbcDriverClass;
  }

  public Map<String, String> getTranslateMap() {
    return translateMap;
  }

  public void setTranslateMap(Map<String, String> translateMap) {
    this.translateMap = translateMap;
  }

  public boolean isTrimSecond() {
    return trimSecond;
  }

  public void setTrimSecond(boolean trimSecond) {
    this.trimSecond = trimSecond;
  }

  /* (non-Javadoc)
   * @see com.ibm.tbsm.hc.datafeed.RecordDAO#save(com.ibm.tbsm.hc.datafeed.Record)
   */
  public void save(AppRawDataRecord record) throws ServiceException {
    String sql = null;
    String type = "";
    String monType = "";
/*
    if (record.getType() != null && record.getType().trim().length() > 0) {
      sql = "INSERT INTO app_raw_data(dateslot, timeslot,ip, metric_desc, value, type) values(?, ?, ?, ?, ?, ?)";
    } else {
      sql = "INSERT INTO app_raw_data(dateslot, timeslot,ip, metric_desc, value) values(?, ?, ?, ?, ?)";
    }
*/
    sql = "INSERT INTO app_raw_data(id, dateslot, timeslot,ip, metric_desc, value, type, montype) values( NEXTVAL FOR SEQ_APP_RAW_DID, ?, ?, ?, ?, ?, ?, ?)";
    if (record.getType() != null && record.getType().trim().length() > 0)
    	type = record.getType();
    if (record.getMonType() != null && record.getMonType().trim().length() > 0)
    	monType = record.getMonType();

    try {
        if (log.isDebugEnabled()) {
           log.info(record.toString());
        }
        PreparedStatement st = this.connnection.prepareStatement(sql);
        this.connnection.setAutoCommit(true);
        
        // 转换metricID到中文名称
        String metricID = record.getMetricId();
        String metricName = this.translateMap.get(metricID);
        if (metricName != null && metricName.trim().length() > 0) {
          log.info("translate metricID from [" + metricID + "] to [" + metricName + "]");
          record.setMetricId(metricName);
        }

        Date timestamp = record.getTimestamp();
        if (this.trimSecond) {
          timestamp.setSeconds(0);
        }
        st.setTimestamp(1, new Timestamp(timestamp.getTime()));
        st.setTimestamp(2, new Timestamp(timestamp.getTime()));
        st.setString(3, record.getIp());
        st.setString(4, record.getMetricId());
        st.setDouble(5, record.getValue());
/*        if (record.getType() != null && record.getType().trim().length() > 0) {
           st.setString(6, record.getType());
        }
*/
        st.setString(6, type);
        st.setString(7, monType);
        
        int code = st.executeUpdate();
        log.info("AppRawData [" + record.toString() + "] inserted.");
    } catch (Exception ex) {
      throw new ServiceException("fail to insert data[" + record.toString() + "], cause: " + ex.getMessage(), ex);
    } finally {
    }

  }

  public void beginTransaction() throws ServiceException {
  }

  public void commit() throws ServiceException {
    try {
      this.connnection.commit();
    } catch (SQLException e) {
      throw new ServiceException("fail to commit transaction, cause: " + e.getMessage(), e);
    }
  }

  public void rollback() throws ServiceException {
    try {
      if (this.connnection != null) {
         this.connnection.rollback();
      }
    } catch (SQLException e) {
      throw new ServiceException("fail to commit transaction, cause: " + e.getMessage(), e);
    }
  }

  public void close() throws ServiceException {
    if (this.connnection != null) {
      try {
        this.connnection.close();
     } catch (SQLException e) {
       throw new ServiceException("fail to close database connection, cause: " + e.getMessage(), e);
     }
   }
  }

  public void initialize() throws ServiceException {
    try {
      this.connnection = this.getConnection();
    } catch (Exception e) {
      throw new ServiceException("fail to initialize DAO", e);
    }
    
  }

}
