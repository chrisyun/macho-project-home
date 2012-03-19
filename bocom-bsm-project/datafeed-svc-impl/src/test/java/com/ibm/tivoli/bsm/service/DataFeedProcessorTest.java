/**
 * 
 */
package com.ibm.tivoli.bsm.service;

import java.io.InputStream;
import java.net.URLEncoder;

import junit.framework.TestCase;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author ZhaoDongLu
 * 
 */
public class DataFeedProcessorTest extends TestCase {

  private BeanFactory factory;

  /*
   * (non-Javadoc)
   * 
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] { "/com/ibm/tivoli/bsm/service/spring/mainBean.xml" });
    factory = (BeanFactory) appContext;
  }

  /*
   * (non-Javadoc)
   * 
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testParseAppRawDataSuccess() throws Exception {
    DataFeedProcessor processor = (DataFeedProcessor)factory.getBean("dataFeedProcessor", DataFeedProcessor.class);

    InputStream in = this.getClass().getResourceAsStream("/data/datafeed.1.xml");
    processor.process(in);
  }

  public void testParseFailure() throws Exception {
    DataFeedProcessor processor = new DataFeedProcessorImpl();

    InputStream in = this.getClass().getResourceAsStream("/data/datafeed.fail.xml");
    try {
      processor.process(in);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void testURLEncoder() throws Exception {
    String s = URLEncoder.encode("应用服务器CPU使用率", "UTF-8");
    assertEquals("%E5%BA%94%E7%94%A8%E6%9C%8D%E5%8A%A1%E5%99%A8CPU%E4%BD%BF%E7%94%A8%E7%8E%87", s);
  }
  
  public void testParseTopNDataSuccess() throws Exception {
    DataFeedProcessor processor = (DataFeedProcessor)factory.getBean("dataFeedProcessor", DataFeedProcessor.class);

    InputStream in = this.getClass().getResourceAsStream("/data/datafeed.topN.xml");
    processor.process(in);
  }

}
