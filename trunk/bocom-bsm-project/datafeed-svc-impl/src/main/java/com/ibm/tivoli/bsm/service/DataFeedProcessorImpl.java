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

  private String beanNameOfAppRawDataDAO = "appRawDataDAO";
  private String beanNameOfTopNDataDAO = "topNDataDAO";

  /**
   * 
   */
  public DataFeedProcessorImpl() {
    super();
  }

  public String getBeanNameOfAppRawDataDAO() {
    return beanNameOfAppRawDataDAO;
  }

  public void setBeanNameOfAppRawDataDAO(String beanNameOfRecordDAO) {
    this.beanNameOfAppRawDataDAO = beanNameOfRecordDAO;
  }

  /**
   * @return the beanNameOfTopNDataDAO
   */
  public String getBeanNameOfTopNDataDAO() {
    return beanNameOfTopNDataDAO;
  }

  /**
   * @param beanNameOfTopNDataDAO the beanNameOfTopNDataDAO to set
   */
  public void setBeanNameOfTopNDataDAO(String beanNameOfTopNDataDAO) {
    this.beanNameOfTopNDataDAO = beanNameOfTopNDataDAO;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.tbsm.hc.datafeed.DataFeedProcessor#process(java.io.Reader)
   */
  public void process(Reader xmlIn) throws ServiceException {
    Digester digester = this.getDigester();
    List<DataGroup> groups = new ArrayList<DataGroup>();
    digester.push(groups);
    CountLineReader reader = new CountLineReader(xmlIn);
    JDBCAppRawDataRecordDAOImpl appRawDataDAO = (JDBCAppRawDataRecordDAOImpl) this.beanFactory.getBean(this.beanNameOfAppRawDataDAO);
    JDBCTopNDataDAOImpl topNDAO = (JDBCTopNDataDAOImpl) this.beanFactory.getBean(this.beanNameOfTopNDataDAO);
    try {
      digester.parse(reader);

      appRawDataDAO.initialize();
      appRawDataDAO.beginTransaction();
      
      topNDAO.initialize();
      topNDAO.beginTransaction();
      for (DataGroup group : groups) {
        // save app raw data
        for (AppRawDataRecord r : group.getAppRawDataRecords()) {
          r.getTimestamp().setSeconds(0);
          // ´æÈëAPP_RAW_DATA±í
          // String finalMetricID =
          // r.getAppName()+"-"+r.getIndexType()+"-"+r.getMetricId();
          String finalMetricID = r.getMetricId();
          r.setMetricId(finalMetricID);
          appRawDataDAO.save(r);
        }
        // save top N data
        for (TopNData r : group.getTopNDatas()) {
          r.getTimestamp().setSeconds(0);
          topNDAO.save(r);
        }
      }
      appRawDataDAO.commit();
      topNDAO.commit();
    } catch (DataFeedException e) {
      appRawDataDAO.rollback();
      topNDAO.rollback();
      throw e;
    } catch (IOException e) {
      appRawDataDAO.rollback();
      topNDAO.rollback();
      throw new ServiceException("Fail to read feeddata from xml at line#" + reader.getLineCount(), e);
    } catch (SAXException e) {
      appRawDataDAO.rollback();
      topNDAO.rollback();
      if (e.getMessage().indexOf("Unparseable date") > 0) {
        throw new DataFeedException(10, e.getMessage(), e);
      } else {
        throw new ServiceException("Fail to read feeddata from xml at line#" + reader.getLineCount(), e);
      }
    } catch (Exception e) {
      appRawDataDAO.rollback();
      topNDAO.rollback();
      throw new ServiceException("Fail to read feeddata from xml at line#" + reader.getLineCount(), e);
    } finally {
      appRawDataDAO.close();
      topNDAO.close();
    }

  }

  public void process(InputStream xmlIn) throws ServiceException {
    try {
      this.process(new InputStreamReader(xmlIn, "UTF-8"));
    } catch (UnsupportedEncodingException e) {
      throw new ServiceException("Fail to read feed data from xml", e);
    }
  }

  public static class DataGroup {
    private Date timestamp = null;
    private String appName = null;
    private String flag = null;
    private String serverIP = null;
    private String indexType = null;
    private String type = null;
    private String monType = null;
    private List<AppRawDataRecord> appRawDataRecords = new ArrayList<AppRawDataRecord>();
    private List<TopNData> topNDatas = new ArrayList<TopNData>();

    public DataGroup() {
      super();
    }

    public DataGroup(Date timestamp, String appName, String flag, String serverIP, String indexType) {
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

    public List<AppRawDataRecord> getAppRawDataRecords() {
      return appRawDataRecords;
    }

    public void setAppRawDataRecords(List<AppRawDataRecord> records) {
      this.appRawDataRecords = records;
    }

    public void addAppRawDataRecord(AppRawDataRecord r) throws ParseException {
      r.setTimestamp(this.timestamp);
      r.setAppName(this.appName);
      r.setIp(this.serverIP);
      r.setType(this.type);
      r.setMonType(this.monType);
      r.setIndexType(this.indexType);

      this.appRawDataRecords.add(r);
    }

    /**
     * @return the topNDatas
     */
    public List<TopNData> getTopNDatas() {
      return topNDatas;
    }

    /**
     * @param topNDatas the topNDatas to set
     */
    public void setTopNDatas(List<TopNData> topNDatas) {
      this.topNDatas = topNDatas;
    }
    
    public void addTopNDatas(TopNData r) throws ParseException {
      r.setTimestamp(this.timestamp);
      r.setIp(this.serverIP);

      this.topNDatas.add(r);
    }

  }

  private Digester getDigester() {
    ConvertUtils.register(new MyDateConverter(), Date.class);

    Digester digester = new Digester();
    digester.setValidating(false);

    digester.addObjectCreate("*/APPROOT", DataGroup.class);
    digester.addSetNext("*/APPROOT/", "add");

    digester.addBeanPropertySetter("*/APPROOT/PUBLIC/DATE", "timestamp");
    digester.addBeanPropertySetter("*/APPROOT/PUBLIC/APPSNAME", "appName");
    digester.addBeanPropertySetter("*/APPROOT/PUBLIC/SERVERIP", "serverIP");
    digester.addBeanPropertySetter("*/APPROOT/PUBLIC/INDEXTYPE", "indexType");
    digester.addBeanPropertySetter("*/APPROOT/PUBLIC/FLAG", "flag");
    digester.addBeanPropertySetter("*/APPROOT/PUBLIC/TYPE", "type");
    digester.addBeanPropertySetter("*/APPROOT/PUBLIC/MONTYPE", "monType");

    // AppRawData
    digester.addObjectCreate("*/APPROOT/PRIVATE/ROWSET/ROW", AppRawDataRecord.class);
    digester.addBeanPropertySetter("*/APPROOT/PRIVATE/ROWSET/ROW/NAME", "metricId");
    digester.addBeanPropertySetter("*/APPROOT/PRIVATE/ROWSET/ROW/INFO", "value");
    digester.addSetNext("*/APPROOT/PRIVATE/ROWSET/ROW", "addAppRawDataRecord");

    // TopN
    digester.addObjectCreate("*/APPROOT/PRIVATE/TopNSET/TopN", TopNData.class);
    digester.addBeanPropertySetter("*/APPROOT/PRIVATE/TopNSET/TopN/NAME", "name");
   
    digester.addObjectCreate("*/APPROOT/PRIVATE/TopNSET/TopN/INFO/ROW", TopNRecord.class);
    digester.addBeanPropertySetter("*/APPROOT/PRIVATE/TopNSET/TopN/INFO/ROW/SEQ", "rankSeq");
    digester.addBeanPropertySetter("*/APPROOT/PRIVATE/TopNSET/TopN/INFO/ROW/NAME", "rankName");
    digester.addBeanPropertySetter("*/APPROOT/PRIVATE/TopNSET/TopN/INFO/ROW/CODE", "rankCode");
    digester.addBeanPropertySetter("*/APPROOT/PRIVATE/TopNSET/TopN/INFO/ROW/VALUE", "rankValue");
    digester.addSetNext("*/APPROOT/PRIVATE/TopNSET/TopN/INFO/ROW", "addRecord");
    digester.addSetNext("*/APPROOT/PRIVATE/TopNSET/TopN", "addTopNDatas");
    return digester;
  }

  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
  }

}
