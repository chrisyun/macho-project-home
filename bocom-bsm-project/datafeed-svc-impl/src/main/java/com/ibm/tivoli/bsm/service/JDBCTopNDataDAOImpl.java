/**
 * 
 */
package com.ibm.tivoli.bsm.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zhaodonglu
 *
 */
public class JDBCTopNDataDAOImpl extends BaseJDBCService implements RecordDAO<TopNData> {

  private static Log log = LogFactory.getLog(JDBCAppRawDataRecordDAOImpl.class);
  
  private String dataSourceName = null;

  private String jdbcUrl = "jdbc:oracle:thin:@127.0.0.1:1521:netcool";
  private String jdbcUser = "icbchc";
  private String jdbcPassword = "icbchc";
  private String jdbcDriverClass = "oracle.jdbc.OracleDriver";
  private String charset = null;
  private boolean autoCommit = false;

  private Connection connnection = null;

  /**
   * 
   */
  private JDBCTopNDataDAOImpl() {
    super();
  }

  /**
   * @return the connnection
   */
  public Connection getConnnection() {
    return connnection;
  }

  /**
   * @param connnection the connnection to set
   */
  public void setConnnection(Connection connnection) {
    this.connnection = connnection;
  }

  /**
   * @param dataSourceName the dataSourceName to set
   */
  public void setDataSourceName(String dataSourceName) {
    this.dataSourceName = dataSourceName;
  }

  /**
   * @param jdbcUrl the jdbcUrl to set
   */
  public void setJdbcUrl(String jdbcUrl) {
    this.jdbcUrl = jdbcUrl;
  }

  /**
   * @param jdbcUser the jdbcUser to set
   */
  public void setJdbcUser(String jdbcUser) {
    this.jdbcUser = jdbcUser;
  }

  /**
   * @param jdbcPassword the jdbcPassword to set
   */
  public void setJdbcPassword(String jdbcPassword) {
    this.jdbcPassword = jdbcPassword;
  }

  /**
   * @param jdbcDriverClass the jdbcDriverClass to set
   */
  public void setJdbcDriverClass(String jdbcDriverClass) {
    this.jdbcDriverClass = jdbcDriverClass;
  }

  /**
   * @param charset the charset to set
   */
  public void setCharset(String charset) {
    this.charset = charset;
  }

  /**
   * @return the autoCommit
   */
  public boolean isAutoCommit() {
    return autoCommit;
  }

  /**
   * @param autoCommit the autoCommit to set
   */
  public void setAutoCommit(boolean autoCommit) {
    this.autoCommit = autoCommit;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.bsm.service.RecordDAO#save(java.lang.Object)
   */
  public void save(TopNData record) throws ServiceException {

    try {
        if (log.isDebugEnabled()) {
           log.info(record.toString());
        }
        this.connnection.setAutoCommit(this.autoCommit);

        // Delete history data
        PreparedStatement st4Delete = this.connnection.prepareStatement("delete from TOP_N_DATA where TOP_N_ID in (select TOP_N_ID from TOP_N where top_n_name=? and upload_server_ip=?)");
        st4Delete.setString(1, record.getName());
        st4Delete.setString(2, record.getIp());
        int code = st4Delete.executeUpdate();
        st4Delete.close();

        PreparedStatement st4DeleteData = this.connnection.prepareStatement("delete from TOP_N where top_n_name=? and upload_server_ip=?");
        st4DeleteData.setString(1, record.getName());
        st4DeleteData.setString(2, record.getIp());
        code = st4DeleteData.executeUpdate();
        st4Delete.close();
        
        // Create TopN data
        long topNId = 0L;
        Statement st4Sequence = this.connnection.createStatement();
        ResultSet rs = st4Sequence.executeQuery("select nextval for SEQ_APP_RAW_DID FROM SYSIBM.SYSDUMMY1");
        rs.next();
        topNId = rs.getLong(1);
        rs.close();
        st4Sequence.close();
        
        PreparedStatement st = this.connnection.prepareStatement("INSERT INTO TOP_N(top_n_id, top_n_name, upload_server_ip, upload_time) values( ?, ?, ?, ?)");
        st.setLong(1, topNId);
        st.setString(2, record.getName());
        st.setString(3, record.getIp());
        st.setTimestamp(4, new Timestamp(record.getTimestamp().getTime()));
        code = st.executeUpdate();
        st.close();
        // Get ID
        st = this.connnection.prepareStatement("INSERT INTO TOP_N_DATA(rank_id, top_n_id, rank_name, rank_seq, rank_code, value) values( NEXTVAL FOR SEQ_APP_RAW_DID, ?, ?, ?, ?, ?)");
        for (TopNRecord d: record.getRecords()) {
            st.setLong(1, topNId)          ;
            st.setString(2, d.getRankName());
            st.setInt(3, d.getRankSeq());
            st.setString(4, d.getRankCode());
            st.setDouble(5, d.getRankValue());
            st.executeUpdate();
        }
        st.close();
        
        log.info("TopNData [" + record.toString() + "] inserted.");
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

  /**
   * @return the dataSourceName
   */
  public String getDataSourceName() {
    return dataSourceName;
  }

  /**
   * @return the jdbcUrl
   */
  public String getJdbcUrl() {
    return jdbcUrl;
  }

  /**
   * @return the jdbcUser
   */
  public String getJdbcUser() {
    return jdbcUser;
  }

  /**
   * @return the jdbcPassword
   */
  public String getJdbcPassword() {
    return jdbcPassword;
  }

  /**
   * @return the jdbcDriverClass
   */
  public String getJdbcDriverClass() {
    return jdbcDriverClass;
  }

  /**
   * @return the charset
   */
  public String getCharset() {
    return charset;
  }
}
