/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/ClientProvJobTargetDeviceEntity.java,v 1.6 2008/08/01 06:45:39 zhao Exp $
  * $Revision: 1.6 $
  * $Date: 2008/08/01 06:45:39 $
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
package com.npower.dm.hibernate.entity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Hibernate;

import com.npower.dm.core.ClientProvAuditLog;
import com.npower.sms.SmsMessage;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2008/08/01 06:45:39 $
 */
public class ClientProvJobTargetDeviceEntity extends AbstractClientProvJobTargetDevice implements java.io.Serializable {

  /**
   * States for finished
   */
  private static Set<String> SET_OF_FINISHED_STATES = new HashSet<String>();
  static {
    //SET_OF_FINISHED_STATES.add(ClientProvAuditLog.STATUS_QUEUED);
    SET_OF_FINISHED_STATES.add(ClientProvAuditLog.STATUS_CANCELLED);
    SET_OF_FINISHED_STATES.add(ClientProvAuditLog.STATUS_SENT);
    SET_OF_FINISHED_STATES.add(ClientProvAuditLog.STATUS_RECEIVED);
    SET_OF_FINISHED_STATES.add(ClientProvAuditLog.STATUS_SUCCESS);
    SET_OF_FINISHED_STATES.add(ClientProvAuditLog.STATUS_UNKNOW);
  }
  
  // Constructors

  /**
   * 
   */
  private static final long serialVersionUID = 6884882540317960445L;

  /** default constructor */
  public ClientProvJobTargetDeviceEntity() {
    super();
  }

  /** minimal constructor */
  public ClientProvJobTargetDeviceEntity(String status, Date createdTime) {
    super(status, createdTime);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ClientProvJobTargetDevice#isFinished()
   */
  public boolean isFinished() {
    return SET_OF_FINISHED_STATES.contains(this.getStatus());
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ClientProvJobTargetDevice#getSmsMessage()
   */
  public SmsMessage getSmsMessage() throws Exception {
    Blob blob = this.getMessageRaw();
    ObjectInputStream in = new ObjectInputStream(blob.getBinaryStream());
    SmsMessage result = (SmsMessage)in.readObject();
    in.close();
    return result;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ClientProvJobTargetDevice#setSmsMessage(com.npower.sms.SmsMessage)
   */
  public void setSmsMessage(SmsMessage message) throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    ObjectOutputStream out = new ObjectOutputStream(bytes);
    out.writeObject(message);
    out.flush();
    this.setMessageRaw(Hibernate.createBlob(bytes.toByteArray()));
  }

}
