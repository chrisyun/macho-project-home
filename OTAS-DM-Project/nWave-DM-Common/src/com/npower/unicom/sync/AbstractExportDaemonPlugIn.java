/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/AbstractExportDaemonPlugIn.java,v 1.7 2008/11/20 09:17:03 zhao Exp $
 * $Revision: 1.7 $
 * $Date: 2008/11/20 09:17:03 $
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
package com.npower.unicom.sync;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.npower.common.plugins.AbstractDisablePlugIn;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2008/11/20 09:17:03 $
 */
public abstract class AbstractExportDaemonPlugIn extends AbstractDisablePlugIn implements PlugIn, Runnable {

  private static final String FILENAME_LAST_SYNC_TIME_STAMP = "_last_sync_time_stamp";

  private static Log log = LogFactory.getLog(AbstractExportDaemonPlugIn.class);
  
  private String directory = "./incoming/sync/simple";
  private String timePattern = null;
  
  private Thread thread;

  /**
   * 
   */
  public AbstractExportDaemonPlugIn() {
    super();
  }

  /**
   * @return the directory
   */
  public String getDirectory() {
    return directory;
  }

  /**
   * @param directory the directory to set
   */
  public void setDirectory(String directory) {
    this.directory = directory;
  }

  /**
   * @return the timePattern
   */
  public String getTimePattern() {
    return timePattern;
  }

  /**
   * @param timePattern the timePattern to set
   */
  public void setTimePattern(String timePattern) {
    this.timePattern = timePattern;
  }
  /**
   * 检测指定时间是否满足运行条件.
   * 时间调度模版: "2004-09-06 15:4*:*"
   * @param date
   * @param patternStr
   * @return
   */
  public static boolean onTime(Date date, String patternStr) {
    if (patternStr == null) {
       return false;
    }
    String[] patterns = StringUtils.split(patternStr, ",");
    if (patterns == null || patterns.length == 0) {
       return false;
    }
    
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    String dateString = formatter.format(date);
    for (int i = 0; patterns != null && i < patterns.length; i++) {
      String tmp = patterns[i].replaceAll("\\*", ".*").trim();
      Pattern pattern = Pattern.compile(tmp);
      Matcher matcher = pattern.matcher(dateString);
      if (matcher.matches()) {
        return true;
      }
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.apache.struts.action.PlugIn#destroy()
   */
  public void destroy() {
    try {
        if (this.thread != null) {
           this.thread.interrupt();
        }
    } catch (Exception ex) {
      log.info("failure to destroy SGSN File Monitor Daemon", ex);
    } finally {
    }
  }

  public void init(ActionServlet arg0, ModuleConfig arg1) {
    log.info("Starting Daemon[" + this.getClass().getCanonicalName() + "] ...");
    try {
      thread = new Thread(this);
      thread.start();

      log.info("Daemon[" + this.getClass().getCanonicalName() + "] has been started.");
    } catch (Exception e) {
      log.error("failure to initialize " + this.getClass().getCanonicalName(), e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see java.lang.Runnable#run()
   */
  private boolean isRunning = false;
  public synchronized void run() {
    if (this.isRunning) {
       return;
    }
    this.isRunning = true;
    // 防止在1秒内多次运行
    long lastRunningTime = 0;
    try {
      while (true) {
        try {
            Date now = new Date();
            if (onTime(now, this.getTimePattern()) && lastRunningTime/1000 != now.getTime()/1000) {
               File requestDir = getRequestDir();
               String requestFilename = getRequestFilename(now);
               File requestFile = new File(requestDir, requestFilename);
               Date lastSyncTime = this.loadLastSyncTimeStamp();
               SyncProcessor processor = this.getProcessor(requestFile, lastSyncTime, now);
               processor.sync();
               
               // 输出LastSyncTimeStamp
               this.updateLastSyncTimeStamp(now);
               
               lastRunningTime = now.getTime();
            }
        } catch(Exception ex) {
          log.error("Error to export data: " + ex.getMessage(), ex);
        } finally {
          try {
            Thread.sleep(50);
          } catch (InterruptedException e) {
            log.warn("Daemon[" + this.getClass().getCanonicalName() + "] has been interrupted.");
            if (log.isDebugEnabled()) {
              log.debug("Daemon[" + this.getClass().getCanonicalName() + "] has been interrupted.", e);
            }
            break;
          }
        }
      }
    } catch (Exception e) {
      log.error("Error in Daemon[" + this.getClass().getCanonicalName() + "]" + this.getDirectory(), e);
    } finally {
      this.isRunning = false;
    }
  }

  /**
   * @param now
   * @return
   */
  private String getRequestFilename(Date now) {
    String stateCode = "001";
     String fileSN = "00001";
     SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
     String requestFilename = format.format(now) + stateCode + fileSN + ".req";
    return requestFilename;
  }

  /**
   * @param dir
   * @return
   */
  private File getRequestDir() {
    File dir = new File(this.getDirectory());
    if (!dir.isAbsolute()) {
       dir = new File(System.getProperty("otas.dm.home"), this.getDirectory());
    }
    File requestDir = new File(dir, "request");
    if (!requestDir.exists()) {
       log.info("create directory: " + requestDir.getAbsolutePath());
       requestDir.mkdirs();
    }
    return requestDir;
  }
  
  /**
   * 更新同步时戳
   * @param date
   */
  private void updateLastSyncTimeStamp(Date date) {
    File requestDir = this.getRequestDir();
    File file = new File(requestDir, AbstractExportDaemonPlugIn.FILENAME_LAST_SYNC_TIME_STAMP);
    try {
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
      out.writeObject(date);
      out.close();
    } catch (FileNotFoundException e) {
      log.error("failure to update last sync time stamp: " + e.getMessage(), e);
    } catch (IOException e) {
      log.error("failure to update last sync time stamp: " + e.getMessage(), e);
    }
  }

  /**
   * 载入同步时戳
   * @param date
   */
  private Date loadLastSyncTimeStamp() {
    File requestDir = this.getRequestDir();
    File file = new File(requestDir, AbstractExportDaemonPlugIn.FILENAME_LAST_SYNC_TIME_STAMP);
    try {
      if (file.exists()) {
         ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
         Date time = (Date)in.readObject();
         in.close();
         return time;
      }
    } catch (ClassNotFoundException e) {
      log.error("failure to update last sync time stamp: " + e.getMessage(), e);
    } catch (IOException e) {
      log.error("failure to update last sync time stamp: " + e.getMessage(), e);
    }
    return null;
  }
  /**
   * 导出数据处理
   * @param requestFile
   * @return
   */
  private SyncProcessor getProcessor(File requestFile, Date fromTime, Date toTime) {
    DBSyncItemReader<?> reader = getSyncItemReader();
    reader.setFromTime(fromTime);
    reader.setToTime(toTime);
    
    SyncItemWriter writer = getSyncItemWriter(requestFile, fromTime, toTime);
    
    SyncProcessor processor = new SyncProcessor(reader, writer, null);
    return processor;
  }

  /**
   * 返回当前任务对应的SyncItemWriter.
   * @param requestFile
   * @return
   */
  protected abstract SyncItemWriter getSyncItemWriter(File requestFile, Date fromTime, Date toTime);

  /**
   * 返回当前任务对应的SyncItemReader
   * @return
   */
  protected abstract DBSyncItemReader<?> getSyncItemReader();
  
}
