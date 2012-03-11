/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/AbstractImportDaemonPlugIn.java,v 1.7 2008/11/20 10:51:33 zhao Exp $
 * $Revision: 1.7 $
 * $Date: 2008/11/20 10:51:33 $
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
import java.io.FilenameFilter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.npower.common.plugins.AbstractDisablePlugIn;
import com.npower.unicom.sync.SyncItemReader;
import com.npower.unicom.sync.SyncItemWriter;
import com.npower.unicom.sync.SyncProcessor;
import com.npower.unicom.sync.SyncResultWriter;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2008/11/20 10:51:33 $
 */
public abstract class AbstractImportDaemonPlugIn extends AbstractDisablePlugIn implements PlugIn, Runnable {

  private static Log log = LogFactory.getLog(AbstractImportDaemonPlugIn.class);
  
  private String directory = "./incoming/sync/simple";
  private int intervalInSeconds = 2;

  private Thread thread;
  private String successFlags = null;

  /**
   * 
   */
  public AbstractImportDaemonPlugIn() {
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
   * @return the intervalInSeconds
   */
  public int getIntervalInSeconds() {
    return intervalInSeconds;
  }

  /**
   * @param intervalInSeconds the intervalInSeconds to set
   */
  public void setIntervalInSeconds(int intervalInSeconds) {
    this.intervalInSeconds = intervalInSeconds;
  }

  
  public String getSuccessFlags() {
    return successFlags;
  }

  public void setSuccessFlags(String successFlags) {
    this.successFlags = successFlags;
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
      log.info("failure to destroy Daemon[" + this.getClass().getCanonicalName() + "]", ex);
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
    try {
      File dir = new File(this.getDirectory());
      if (!dir.isAbsolute()) {
         dir = new File(System.getProperty("otas.dm.home"), this.getDirectory() + "/request");
      }
      while (true) {
        try {
            File[] files = dir.listFiles(new FilenameFilter(){

              public boolean accept(File dir, String name) {
                if (StringUtils.isNotEmpty(name)) {
                   if (name.toLowerCase().endsWith(".req")) {
                      return true;
                   }
                }
                return false;
              }});
            if (files != null && files.length > 0) {
               // 完成后将处理文件移走
               for (File file: files) {
                   // 检查文件是否已经处理过
                   File finishedDir = new File(file.getParentFile().getParentFile().getAbsolutePath(), "finished");
                   if (!finishedDir.exists()) {
                      finishedDir.mkdirs();
                   }

                   File finishedFile = new File(finishedDir, file.getName());
                   if (!finishedFile.exists()) {
                     log.info("start processing new file: " + file.getAbsolutePath());
                     SyncProcessor processor = this.getProcessor(file);
                     processor.sync();
                     log.info("end of processing new file: " + file.getAbsolutePath());
                   } else {
                     log.info("discard finished file: " + file.getAbsolutePath());
                     finishedFile = new File(finishedDir, file.getName() + "." + System.currentTimeMillis());
                   }
                   
                   // 总是移动完成或撤销的文件
                   boolean renameSuccess = file.renameTo(finishedFile);
                   log.info("rename file:" + renameSuccess + " to " + finishedFile.getAbsolutePath());
               }
            }
        } catch(Exception ex) {
          log.error("Error to monitor directory: " + this.getDirectory(), ex);
        } finally {
          try {
            Thread.sleep(this.getIntervalInSeconds() * 1000);
          } catch (InterruptedException e) {
            log.warn("File monitor thread has been interrupted: " + this.getDirectory());
            if (log.isDebugEnabled()) {
              log.debug("File monitor thread has been interrupted: " + this.getDirectory(), e);
            }
            break;
          }
        }
      }
    } catch (Exception e) {
      log.error("Error to monitor directory: " + this.getDirectory(), e);
    } finally {
      this.isRunning = false;
    }
  }
  
  /**
   * 返回用于当前处理任务的SyncProcessor
   * @param requestFile
   * @return
   */
  private SyncProcessor getProcessor(File requestFile) {
    SyncItemReader reader = getSyncItemReader(requestFile);
    SyncItemWriter writer = getSyncItemWriter(requestFile);
    File responseDir = new File(this.directory, "reponse");
    if (!responseDir.isAbsolute()) {
       responseDir = new File(System.getProperty("otas.dm.home"), this.directory + "/reponse");
    }
    SyncResultWriter result = new SyncResultWriter(requestFile, responseDir);
    
    SyncProcessor processor = new SyncProcessor(reader, writer, result);
    return processor;
  }

  /**
   * 返回当前任务对应的SyncItemWriter.
   * @return
   */
  protected abstract SyncItemWriter getSyncItemWriter(File requestFile);

  /**
   * 返回当前任务对应的SyncItemReader
   * @param requestFile
   * @return
   */
  protected abstract SyncItemReader getSyncItemReader(File requestFile);
  
}
