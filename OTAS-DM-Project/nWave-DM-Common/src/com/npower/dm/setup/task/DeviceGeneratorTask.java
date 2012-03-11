/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/DeviceGeneratorTask.java,v 1.1 2008/09/05 03:24:40 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/09/05 03:24:40 $
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
package com.npower.dm.setup.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.setup.core.SetupException;

/**
 * <pre>
    <!-- Step#1: Create device -->
    <Task class="com.npower.dm.setup.task.DeviceGeneratorTask" disable="false">
      <Name>Create Sample Devices</Name>
      <Description></Description>
      <Properties>
        <Property key="total" value="500" />
        <Property key="thread" value="30" />
        <Property key="carrier" value="ChinaMobile" />
      </Properties>
    </Task>

#!/bin/sh
OTAS_SETUP_HOME=$OTAS_DM_HOME/install; export OTAS_SETUP_HOME
while [ 1 ]
do
cd $OTAS_SETUP_HOME/bin
./otassetup.sh -f $OTAS_DM_HOME/install/dmsetup4benchmark.xml >> /home/otasdm/logs/benchmark.log
done

 * </pre>
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/09/05 03:24:40 $
 */
public class DeviceGeneratorTask extends DMTask {
  
  private static final long BEGIN_IMEI = 123456781234560L;
  
  private static final long BEGIN_IMSI = 453456781234560L;
    
  private String carrierExternalID = null;

  private long beginPhoneNumber;
  
  private long beginIMEI = 0;
  
  private long beginIMSI = 0;
  
  public class TaskThread implements Runnable {
    
    private List<Model> models = new ArrayList<Model>();
    
    private DeviceGeneratorTask executor = null;

    public TaskThread() {
      super();
    }

    public TaskThread(DeviceGeneratorTask executor) {
      super();
      this.executor = executor;
    }

    public DeviceGeneratorTask getExecutor() {
      return executor;
    }

    public void setExecutor(DeviceGeneratorTask executor) {
      this.executor = executor;
    }

    /**
     * @throws SetupException
     */
    private void loadModelCache(ManagementBeanFactory factory) throws SetupException {
      try {
          ModelBean bean = factory.createModelBean();
          for (Model model: bean.getAllModel()) {
              models.add(model);
          }
      } catch (DMException e) {
        this.getExecutor().getSetup().getConsole().error(this.getExecutor(), e);
      } finally {
      }
    }

    public void run() {
      int total = this.getExecutor().getTotalDevice();
      ManagementBeanFactory factory = null;
      try {
          this.getExecutor().getSetup().getConsole().println("Creating total device: " + total);
          
          factory = this.getExecutor().getManagementBeanFactory();
          
          this.getExecutor().getSetup().getConsole().println("Loading Models ...");
          loadModelCache(factory);
          this.getExecutor().getSetup().getConsole().println("Total models loaded: " + this.models.size());
          
          DeviceBean deviceBean = factory.createDeviceBean();
          CarrierBean carrierBean = factory.createCarrierBean();
          Carrier carrier = carrierBean.getCarrierByExternalID(this.getExecutor().getCarrierExternalID());
          for (int i = 0; i < total; i++) {
              Model model = models.get((int)(System.currentTimeMillis() % models.size()));
              String phoneNumber = this.getExecutor().getNextPhoneNumber();
              String imei = this.getExecutor().getNextIMEI();
              String imsi = this.getExecutor().getNextIMSI();
              try {
                  factory.beginTransaction();
                  deviceBean.enroll(imei, phoneNumber, imsi, model, carrier, null);
                  factory.commit();
              } catch (Exception e) {
                e.printStackTrace();
              }
          }
          this.getExecutor().getSetup().getConsole().println("Created total device: " + total);
      } catch (Exception e) {
        try {
          this.getExecutor().getSetup().getConsole().error(this.getExecutor(), e);
        } catch (SetupException e1) {
          e1.printStackTrace();
        }
      } finally {
        if (factory != null) {
           factory.release();
        }
      }
    }
    
  }
  
  /**
   * 
   */
  public DeviceGeneratorTask() {
    super();
  }

  private long count = 0;
  public synchronized String getNextPhoneNumber() {
    long msisdn = this.getBeginPhoneNumber() + count;
    this.count++;
    return Long.toString(msisdn);
  }

  private long count4Imei = 0;
  public synchronized String getNextIMEI() {
    long imei = this.getBeginIMEI() + count4Imei;
    this.count4Imei++;
    return "IMEI:" + Long.toString(imei);
  }

  private long count4Imsi = 0;
  public synchronized String getNextIMSI() {
    long imsi = this.getBeginIMSI() + count4Imsi;
    this.count4Imsi++;
    return Long.toString(imsi);
  }

  private String getCarrierExternalID() {
    if (StringUtils.isEmpty(this.carrierExternalID)) {
       String s = this.getPropertyValue("carrier");
       this.carrierExternalID = s;
    }
    return this.carrierExternalID;
  }
  
  private long getBeginPhoneNumber() {
    if (beginPhoneNumber != 0) {
       return this.beginPhoneNumber;
    }
    long now = System.currentTimeMillis();
    String s = Long.toString(now);
    s = s.substring(2, s.length());
    this.beginPhoneNumber =  Long.parseLong(s);
    return this.beginPhoneNumber;
  }

  private long getBeginIMEI() {
    if (beginIMEI != 0) {
       return this.beginIMEI;
    }
    long now = System.currentTimeMillis();
    this.beginIMEI =  BEGIN_IMEI + now;
    return this.beginIMEI;
  }

  private long getBeginIMSI() {
    if (beginIMSI != 0) {
       return this.beginIMSI;
    }
    long now = System.currentTimeMillis();
    this.beginIMSI =  BEGIN_IMSI + now;
    return this.beginIMSI;
  }

  /*
  /*
  private long getBeginPhoneNumber() {
    if (beginPhoneNumber != 0) {
       return this.beginPhoneNumber;
    }
    ManagementBeanFactory factory = null;
    try {
        factory = this.getManagementBeanFactory();
        DeviceBean bean = factory.createDeviceBean();
        DeviceManagementBeanImpl impl = (DeviceManagementBeanImpl)bean;
        
        Session session = impl.getHibernateSession();
        String maxMsisdn = (String)session.createQuery("select max(s.externalId) from SubscriberEntity s ").uniqueResult();
        
        this.beginPhoneNumber = Long.parseLong(maxMsisdn);
        this.beginPhoneNumber++;
        this.getSetup().getConsole().println("Begin MSISDN: " + this.beginPhoneNumber);
    } catch (Exception e) {
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
    return beginPhoneNumber;
  }
  */
  
  private int getTotalDevice() {
    String s = this.getPropertyValue("total");
    int total = 1000;
    try {
        total = Integer.parseInt(s);
    } catch (Exception ex) {
    }
    return total;
  }

  private int getTotalThread() {
    String s = this.getPropertyValue("thread");
    int total = 10;
    try {
        total = Integer.parseInt(s);
    } catch (Exception ex) {
    }
    return total;
  }

  /* (non-Javadoc)
   * @see com.npower.setup.core.AbstractTask#process()
   */
  @Override
  protected void process() throws SetupException {
    
    int totalThread = this.getTotalThread();
    this.getSetup().getConsole().println("Starting total thread: " + totalThread);
    try {
        ThreadGroup group = new ThreadGroup(this.getClass().getName());
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < totalThread; i++) {
            TaskThread runnable = new TaskThread(this);
            Thread t = new Thread(group, runnable, "Thread#" + i);
            threads.add(t);
            t.start();
            this.getSetup().getConsole().println("Started thread: " + t.getName());
        }
        /*
        try {
          Thread.sleep(1000 * 3600 * 12);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        */
        for (Thread t: threads) {
            t.join();
        }
    } catch (InterruptedException e) {
      throw new SetupException(e.getMessage(), e);
    }
    
    this.getSetup().getConsole().println("All of thread finished.");
  }

  /* (non-Javadoc)
   * @see com.npower.setup.core.AbstractTask#rollback()
   */
  @Override
  protected void rollback() throws SetupException {
  }

}
