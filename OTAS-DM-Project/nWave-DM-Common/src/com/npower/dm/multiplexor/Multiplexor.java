/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/multiplexor/Multiplexor.java,v 1.5 2008/12/25 04:31:19 zhao Exp $
 * $Revision: 1.5 $
 * $Date: 2008/12/25 04:31:19 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
 *
 * This SOURCE CODE FILE, which has been provided by NPower as part
 * of a NPower product for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information of NPower.
 *
 * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
 * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
 * THE PRODUCT.
 *
 * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
 * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
 * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
 * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
 * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
 * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
 * CODE FILE.
 * ===============================================================================================
 */

package com.npower.dm.multiplexor;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.servlet.ServletException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.xml.sax.SAXException;

import com.npower.common.plugins.AbstractDisablePlugIn;
import com.npower.jms.JMSManager;
import com.npower.jndi.JndiContextFactory;
import com.npower.sms.SmsException;
import com.npower.sms.transport.JmsSmsReceiverImpl;
import com.npower.sms.transport.SmsReceiver;
import com.npower.util.HelperUtil;


/**
 * @author Zhao wanxiang
 * @version $Revision: 1.5 $ $Date: 2008/12/25 04:31:19 $
 */

public class Multiplexor extends AbstractDisablePlugIn implements PlugIn,  MessageListener {

  private static Log log = LogFactory.getLog(Multiplexor.class);

  private String incomingQueueName;
  private String mappingFile;
  
  private MappingTable mappingTable;

  private QueueConnection connection;
  
  private QueueSession session;
  
  /**
   * 
   */
  public Multiplexor() {
    super();
  }

  public Multiplexor(MappingTable mappingTable) {
    super();
    this.mappingTable = mappingTable;
  }
  
  /**
   * @return the incomingQueueName
   */
  public String getIncomingQueueName() {
    return incomingQueueName;
  }

  /**
   * @param incomingQueueName the incomingQueueName to set
   */
  public void setIncomingQueueName(String incomingQueueName) {
    this.incomingQueueName = incomingQueueName;
  }

  /**
   * @return the mappingFile
   */
  public String getMappingFile() {
    return mappingFile;
  }

  /**
   * @throws SmsException
   */
  private File getFile(String filename) throws SmsException {
    String home = System.getProperty("otas.dm.home");
    if (home == null || home.trim().length() == 0) {
      log.error("Please setup the property: otas.dm.home!");
      throw new SmsException("Missing property: otas.dm.home");
    }
    File file = new File(filename);
    if(!file.isAbsolute()){
      File file4Importer = new File(new File(home),filename);
      return file4Importer;
    }else {
      return file;
    }
  }
  
  /**
   * @param mappingFile the mappingFile to set
   */
  public void setMappingFile(String mappingFile) {
    this.mappingFile = mappingFile;
  }

  private MappingTable loadMappingTable(String filename){
    MappingTableFactory factory = new MappingTableFactory();
    try {
      return  factory.createMappingTable(this.getFile(filename));
    } catch (IOException e) {
      log.error("load MappingTable is unsuccessful ",e);
    } catch (SAXException e) {
      log.error("load MappingTable is unsuccessful ",e);
    } catch (SmsException e) {
      log.error("load MappingTable is unsuccessful ",e);
    }
    return null;
  }
  
  /**
   * 根据目标电话号码查找目标队列.
   * @param from
   *        短消息发送方电话号码
   * @param to
   *        短消息目标电话号码
   * @param msgBytes
   *        短消息内容
   * @param msgText
   *        短消息内容
   * @throws Exception
   */
  private void dispatch(String from, String to, byte[] msgBytes, String msgText) throws TargetQueueNotFoundException, Exception {
    String targetQueueName = this.mappingTable.getTarget(to);
    if (StringUtils.isNotEmpty(targetQueueName)) {
       SmsReceiver receiver = new JmsSmsReceiverImpl(targetQueueName);
       receiver.process(from, to, msgBytes, msgText);
    } else {
      // Target queue not found, notify caller
      throw new TargetQueueNotFoundException(from, to, msgBytes, msgText);
    }
  }

  /* (non-Javadoc)
   * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
   */
  public void onMessage(Message jmsMsg) {
    try {
      String from = jmsMsg.getStringProperty(JmsSmsReceiverImpl.PROPERTY_DEVICE_MSISDN_NAME);
      String to = jmsMsg.getStringProperty(JmsSmsReceiverImpl.PROPERTY_DEVICE_SERVICE_CODE);
      String msgText = jmsMsg.getStringProperty(JmsSmsReceiverImpl.PROPERTY_TEXT_MESSAGE);
      byte[] msgBytes = HelperUtil.hexStringToBytes(jmsMsg.getStringProperty(JmsSmsReceiverImpl.PROPERTY_RAW_MESSAGE));
      if (log.isDebugEnabled()) {
         log.debug("Incoming msg, [from: " + from + ", to: " + to + ", text=" + msgText + "]");
      }
      this.dispatch(from, to, msgBytes, msgText);
    } catch (JMSException e) {
      log.error("Failure to dispatch a incoming message.", e);
    } catch (TargetQueueNotFoundException e) {
      log.error(e.getMessage());
      log.info("Could not found target queue, message has been discarded!");
    } catch (Exception e) {
      log.error("Failure to dispatch a incoming message.", e);
    }
  }

  /* (non-Javadoc)
   * @see org.apache.struts.action.PlugIn#init(org.apache.struts.action.ActionServlet, org.apache.struts.config.ModuleConfig)
   */
  public void init(ActionServlet arg0, ModuleConfig arg1) throws ServletException {
    log.info("Starting SMS Multiplexor Daemon ...");
    connection = null;
    session = null;
    try {
        // Load Mapping Table
        this.mappingTable = this.loadMappingTable(this.getMappingFile());
        
        // Initialize and connect to JMS Queue
        JndiContextFactory jndiFactory = JndiContextFactory.newInstance(new Properties());
        Context jndiCtx = jndiFactory.getJndiContext();
        JMSManager jmsManager = JMSManager.newInstance(jndiCtx);
        
        QueueConnectionFactory connectionFactory = jmsManager.getQueueConnectionFactory();
        connection = connectionFactory.createQueueConnection();
        session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        
        // Create or get outgoing queue
        Queue queue = jmsManager.getQueue(this.getIncomingQueueName(), null);
        QueueReceiver receiver = session.createReceiver(queue);
        receiver.setMessageListener(this);
        
        // Start JMS Listener
        connection.start();
        
        log.info("SMS Multiplexor Daemon has been started.");
    } catch (Exception e) {
      log.error("failure to initialize " + this.getClass().getCanonicalName(), e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see org.apache.struts.action.PlugIn#destroy()
   */
  public void destroy() {
    log.info("Stopping SMS Multiplexor Daemon ...");
    try {
      if (session != null) {
         session.close();
      } 
      if (connection != null) {
         connection.close();
      }
      log.info("Multiplexor Daemon has been stopped.");
    } catch (JMSException e) {
      log.error("failure to stop " + this.getClass().getCanonicalName(), e);
    }
  }

}
