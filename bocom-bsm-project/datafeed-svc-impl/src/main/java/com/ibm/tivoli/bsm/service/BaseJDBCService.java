package com.ibm.tivoli.bsm.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.ibm.tivoli.bsm.util.MyPropertyPlaceholderConfigurer;

public abstract class BaseJDBCService {

  private static Log log = LogFactory.getLog(BaseJDBCService.class);
  private Properties properties = new Properties();

  protected BaseJDBCService() {
    super();
  }

  public abstract String getDataSourceName();

  public abstract String getJdbcUrl();

  public abstract String getJdbcUser();

  public abstract String getJdbcPassword();

  public abstract String getJdbcDriverClass();
  
  public abstract String getCharset();
  
  /**
   * Receive PropertyPlaceholderConfigurer from Spring container.
   * @param configurer
   * @throws IOException
   */
  public void setPropertyPlaceholderConfigurer(PropertyPlaceholderConfigurer configurer) throws IOException {
    if (configurer != null && configurer instanceof MyPropertyPlaceholderConfigurer) {
       this.setProperties(((MyPropertyPlaceholderConfigurer)configurer).getProperties());
    }
  }
  
  public Properties getProperties() {
    return this.properties ;
  }

  public void setProperties(Properties properties) {
    this.properties = properties;
  }

  protected Connection getConnection() throws ServiceException {
    if (this.getDataSourceName() != null && this.getDataSourceName().trim().length() > 0) {
      return this.getDataSourceConnection();
    } else {
      return this.getJDBCConnection();
    }
  }

  protected Connection getDataSourceConnection() throws ServiceException {
    // String jndiName = "java:/comp/env/jdbc/" + dataSourceName;
    // String jndiName = "jdbc/" + dataSourceName;
    String jndiName = this.getDataSourceName();
    try {
      Context ctx = new InitialContext();
      DataSource ds = (DataSource) ctx.lookup(jndiName);
      if (ds != null) {
        if (log.isDebugEnabled()) {
          log.debug("Found Data Source:[" + jndiName + "]");
        }
        return ds.getConnection();
      }
      throw new ServiceException("could not found data source: [" + jndiName + "]");
    } catch (Exception e) {
      throw new ServiceException("Fail to get database connection with JNDI: [" + jndiName + "]", e);
    }
  }

  protected Connection getJDBCConnection() throws ServiceException {
    try {
      Object driver = Class.forName(this.getJdbcDriverClass()).newInstance();
      if (driver instanceof Driver) {
        DriverManager.registerDriver((Driver)driver);
      }
      Properties props = this.getProperties();
      
      // Set default charset for Sybase database
      if (this.getCharset() == null) {
         props.setProperty("charset", "utf8");
      }

      // props.put("user", "root");
      // props.put("password", "passw0rd1234");
      // Connection con =
      // DriverManager.getConnection("jdbc:sybase:Tds:83.25.46.112:4100/OSCMHQAP",
      // props);
      props.put("user", this.getJdbcUser());
      props.put("password", this.getJdbcPassword());
      
      if (log.isDebugEnabled()) {
         log.debug("using connection properties: " + props.toString());
      }
      Connection con = DriverManager.getConnection(this.getJdbcUrl(), props);
      return con;
    } catch (InstantiationException e) {
      throw new ServiceException("fail to make connection, cause: " + e.getMessage(), e);
    } catch (IllegalAccessException e) {
      throw new ServiceException("fail to make connection, cause: " + e.getMessage(), e);
    } catch (ClassNotFoundException e) {
      throw new ServiceException("fail to make connection, cause: " + e.getMessage(), e);
    } catch (SQLException e) {
      throw new ServiceException("fail to make connection, cause: " + e.getMessage(), e);
    }
  }

}