/**
 * 
 */
package com.ibm.tivoli.bsm.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.xml.sax.SAXException;

import com.ibm.tivoli.bsm.util.MyDateConverter;

/**
 * @author ZhaoDongLu
 * 
 */
public class DataFeedProcessorImpl implements DataFeedProcessor, BeanFactoryAware {
  private static Log log = LogFactory.getLog(DataFeedProcessorImpl.class);

  private BeanFactory beanFactory;

  private String beanNameOfRecordDAO = "recordDAO";

  /**
   * 
   */
  public DataFeedProcessorImpl() {
    super();
  }

  public String getBeanNameOfRecordDAO() {
    return beanNameOfRecordDAO;
  }

  public void setBeanNameOfRecordDAO(String beanNameOfRecordDAO) {
    this.beanNameOfRecordDAO = beanNameOfRecordDAO;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.tbsm.hc.datafeed.DataFeedProcessor#process(java.io.Reader)
   */
  public void process(Reader xmlIn) throws ServiceException {
    Digester digester = this.getDigester();
    List<AppRawDataGroup> groups = new ArrayList<AppRawDataGroup>();
    digester.push(groups);
    CountLineReader reader = new CountLineReader(xmlIn);
    JDBCAppRawDataRecordDAOImpl recordDAO = (JDBCAppRawDataRecordDAOImpl) this.beanFactory.getBean(this.beanNameOfRecordDAO);
    try {
      digester.parse(reader);

      recordDAO.initialize();
      recordDAO.beginTransaction();
      for (AppRawDataGroup group : groups) {
        for (AppRawDataRecord r : group.getRecords()) {
          r.getTimestamp().setSeconds(0);
          // ´æÈëAPP_RAW_DATA±í
          // String finalMetricID =
          // r.getAppName()+"-"+r.getIndexType()+"-"+r.getMetricId();
          String finalMetricID = r.getMetricId();
          r.setMetricId(finalMetricID);
          recordDAO.save(r);
        }
      }
      recordDAO.commit();
    } catch (DataFeedException e) {
      recordDAO.rollback();
      throw e;
    } catch (IOException e) {
      recordDAO.rollback();
      throw new ServiceException("Fail to read feeddata from xml at line#" + reader.getLineCount(), e);
    } catch (SAXException e) {
      recordDAO.rollback();
      if (e.getMessage().indexOf("Unparseable date") > 0) {
        throw new DataFeedException(10, e.getMessage(), e);
      } else {
        throw new ServiceException("Fail to read feeddata from xml at line#" + reader.getLineCount(), e);
      }
    } catch (Exception e) {
      recordDAO.rollback();
      throw new ServiceException("Fail to read feeddata from xml at line#" + reader.getLineCount(), e);
    } finally {
      recordDAO.close();
    }

  }

  public void process(InputStream xmlIn) throws ServiceException {
    try {
      this.process(new InputStreamReader(xmlIn, "UTF-8"));
    } catch (UnsupportedEncodingException e) {
      throw new ServiceException("Fail to read feed data from xml", e);
    }
  }

  public static class AppRawDataGroup {
    private Date timestamp = null;
    private String appName = null;
    private String flag = null;
    private String serverIP = null;
    private String indexType = null;
    private String type = null;
    private String monType = null;
    private List<AppRawDataRecord> records = new ArrayList<AppRawDataRecord>();

    public AppRawDataGroup() {
      super();
    }

    public AppRawDataGroup(Date timestamp, String appName, String flag, String serverIP, String indexType) {
      super();
      this.timestamp = timestamp;
      this.appName = appName;
      this.flag = flag;
      this.serverIP = serverIP;
      this.indexType = indexType;
    }

    public Date getTimestamp() {
      return timestamp;
    }

    public void setTimestamp(Date timestamp) {
      this.timestamp = timestamp;
    }

    public String getAppName() {
      return appName;
    }

    public void setAppName(String appName) {
      this.appName = appName;
    }

    public String getFlag() {
      return flag;
    }

    public void setFlag(String flag) {
      this.flag = flag;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String getMonType() {
      return monType;
    }

    public void setMonType(String monType) {
      this.monType = monType;
    }

    public String getServerIP() {
      return serverIP;
    }

    public void setServerIP(String serverIP) {
      this.serverIP = serverIP;
    }

    public String getIndexType() {
      return indexType;
    }

    public void setIndexType(String indexType) {
      this.indexType = indexType;
    }

    public List<AppRawDataRecord> getRecords() {
      return records;
    }

    public void setRecords(List<AppRawDataRecord> records) {
      this.records = records;
    }

    public void addRecord(AppRawDataRecord r) throws ParseException {
      r.setTimestamp(this.timestamp);
      r.setAppName(this.appName);
      r.setIp(this.serverIP);
      r.setType(this.type);
      r.setMonType(this.monType);
      r.setIndexType(this.indexType);

      this.records.add(r);
    }
  }

  private Digester getDigester() {
    ConvertUtils.register(new MyDateConverter(), Date.class);

    Digester digester = new Digester();
    digester.setValidating(false);

    digester.addObjectCreate("*/APPROOT", AppRawDataGroup.class);
    digester.addSetNext("*/APPROOT/", "add");

    digester.addBeanPropertySetter("*/APPROOT/PUBLIC/DATE", "timestamp");
    digester.addBeanPropertySetter("*/APPROOT/PUBLIC/APPSNAME", "appName");
    digester.addBeanPropertySetter("*/APPROOT/PUBLIC/SERVERIP", "serverIP");
    digester.addBeanPropertySetter("*/APPROOT/PUBLIC/INDEXTYPE", "indexType");
    digester.addBeanPropertySetter("*/APPROOT/PUBLIC/FLAG", "flag");
    digester.addBeanPropertySetter("*/APPROOT/PUBLIC/TYPE", "type");
    digester.addBeanPropertySetter("*/APPROOT/PUBLIC/MONTYPE", "monType");

    digester.addObjectCreate("*/APPROOT/PRIVATE/ROWSET/ROW", AppRawDataRecord.class);
    digester.addBeanPropertySetter("*/APPROOT/PRIVATE/ROWSET/ROW/NAME", "metricId");
    digester.addBeanPropertySetter("*/APPROOT/PRIVATE/ROWSET/ROW/INFO", "value");

    digester.addSetNext("*/APPROOT/PRIVATE/ROWSET/ROW", "addRecord");
    return digester;
  }

  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
  }

}
