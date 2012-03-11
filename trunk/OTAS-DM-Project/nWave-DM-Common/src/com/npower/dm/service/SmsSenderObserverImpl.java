/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/service/SmsSenderObserverImpl.java,v 1.6 2008/08/01 10:55:06 zhao Exp $
  * $Revision: 1.6 $
  * $Date: 2008/08/01 10:55:06 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.service;

import java.util.List;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.core.ClientProvAuditLog;
import com.npower.dm.core.ClientProvJobTargetDevice;
import com.npower.dm.management.ClientProvAuditLogBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.OTAClientProvJobBean;
import com.npower.dm.server.engine.EngineConfig;
import com.npower.sms.client.SmsSenderObserver;
import com.npower.sms.client.SmsSenderObserverEvent;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2008/08/01 10:55:06 $
 */
public class SmsSenderObserverImpl implements SmsSenderObserver, MessageListener {
  
  private static Log log = LogFactory.getLog(SmsSenderObserverImpl.class);

  /**
   * 
   */
  public SmsSenderObserverImpl() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.sms.client.SmsSenderObserver#setEvent(com.npower.sms.client.SmsSenderObserverEvent)
   */
  public void setEvent(SmsSenderObserverEvent event) {
    log.info("SMS Status Observer receved event: " + event.toString() );
    
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        OTAClientProvJobBean jobBean = factory.createOTAClientProvJobBean(null, null);
        ClientProvAuditLogBean auditBean = factory.createClientProvAuditLogBean();
        
        factory.beginTransaction();
        List<ClientProvJobTargetDevice> devices = jobBean.findTargetDeviceByMessageID(Long.toString(event.getTarget().getID()));
        for (ClientProvJobTargetDevice device: devices) {
            String status = translateEvent(event);
            device.setStatus(status);
            jobBean.update(device);
        }
        factory.commit();
        
        long messageID = event.getTarget().getID();
        String whereClause = "from ClientProvAuditLogEntity where messageID='" + messageID + "'";
        List<ClientProvAuditLog> result = auditBean.findClientProvAuditLogs(whereClause);
        for (ClientProvAuditLog record: result) {
            String status = translateEvent(event);
            record.setStatus(status);
            // Not required transaction control
            auditBean.update(record);
        }
    } catch (Exception ex) {
      log.error("failure to process sms send event.", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /**
   * Translate event status to Job status
   * @param event
   * @return
   */
  private String translateEvent(SmsSenderObserverEvent event) {
    String status = ClientProvAuditLog.STATUS_UNKNOW;
    if (SmsSenderObserverEvent.SENT.equalsIgnoreCase(event.getType())) {
       status = ClientProvAuditLog.STATUS_SENT;
    } else if (SmsSenderObserverEvent.DISCARDED.equalsIgnoreCase(event.getType())) {
      status = ClientProvAuditLog.STATUS_FAILURE;
    } else if (SmsSenderObserverEvent.RECEIVED.equalsIgnoreCase(event.getType())) {
      status = ClientProvAuditLog.STATUS_RECEIVED;
    } else if (SmsSenderObserverEvent.CANCELED.equalsIgnoreCase(event.getType())) {
      status = ClientProvAuditLog.STATUS_FAILURE;
    } else {
      status = ClientProvAuditLog.STATUS_UNKNOW;
    }
    return status;
  }

  /* (non-Javadoc)
   * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
   */
  public void onMessage(Message message) {
    try {
      SmsSenderObserverEvent event = null;
      if (message != null) {
        if (message instanceof ObjectMessage) {
          ObjectMessage m = (ObjectMessage) message;
          event = (SmsSenderObserverEvent)m.getObject();
        }
      }
      if (event == null) {
         log.error("unkonw sms observer event in queue: " + message);
         return;
      }
      if (log.isDebugEnabled()) {
         log.debug("processing sms observer event, type: " + event.getType() + ", id: " + event.getTarget().getID());
      }
      
      this.setEvent(event);
      
      if (log.isDebugEnabled()) {
         log.debug("end processing sms observer event, type: " + event.getType() + ", id: " + event.getTarget().getID());
      }
  } catch (Exception e) {
    log.error("failure to process sms message", e);
  } finally {
  }
  }

}
