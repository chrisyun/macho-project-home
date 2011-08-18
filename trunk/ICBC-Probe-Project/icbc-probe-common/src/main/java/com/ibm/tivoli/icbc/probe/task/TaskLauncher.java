/**
 * 
 */
package com.ibm.tivoli.icbc.probe.task;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.ibm.tivoli.icbc.probe.ProbeException;
import com.ibm.tivoli.icbc.probe.dns.NameServerLookup;
import com.ibm.tivoli.icbc.probe.http.JxBrowserExecutorImpl;
import com.ibm.tivoli.icbc.result.ResultFormater;
import com.ibm.tivoli.icbc.result.ResultFormaterV2;

/**
 * @author Zhao Dong Lu
 * 
 */
public class TaskLauncher implements ApplicationContextAware {

  private static Log log = LogFactory.getLog(TaskLauncher.class);

  private TaskConfigFactory taskConfigFactory = null;

  private ApplicationContext applicationContext;

  private TaskConfig taskConfig = null;

  private TaskContext taskContext = null;

  private DataDispatcher dataDispatcher;

  private ResultFormater resultFormater = new ResultFormaterV2();

  /**
   * 
   */
  public TaskLauncher() {
    super();
  }

  public TaskConfigFactory getTaskConfigFactory() {
    return taskConfigFactory;
  }

  public void setTaskConfigFactory(TaskConfigFactory taskConfigFactory) {
    this.taskConfigFactory = taskConfigFactory;
  }

  public TaskContext getTaskContext() {
    return taskContext;
  }

  public void setTaskContext(TaskContext taskContext) {
    this.taskContext = taskContext;
  }

  public DataDispatcher getDataDispatcher() {
    return dataDispatcher;
  }

  public void setDataDispatcher(DataDispatcher dataDispatcher) {
    this.dataDispatcher = dataDispatcher;
  }

  public ResultFormater getResultFormater() {
    return resultFormater;
  }

  public void setResultFormater(ResultFormater resultFormater) {
    this.resultFormater = resultFormater;
  }

  /**
   * Start Task launcher loop
   * 
   * @param context
   * @throws ProbeException
   */
  private long lastExecutionTime = 0;

  /**
   * 清理IE抓屏图片文件时的过期时间，早于这个小时数的文件将被自动删除
   */
  private int imageFileExpiredInHour = 1;

  /**
   * 控制是否在每次运行任务前，清除无效的JExplorer进程 
   */
  private boolean killJExplorerProcess = false;

  /**
   * @return the imageFileExpiredInHour
   */
  public int getImageFileExpiredInHour() {
    return imageFileExpiredInHour;
  }

  /**
   * @param imageFileExpiredInHour
   *          the imageFileExpiredInHour to set
   */
  public void setImageFileExpiredInHour(int imageFileExpiredInHour) {
    this.imageFileExpiredInHour = imageFileExpiredInHour;
  }

  /**
   * @return the killJExplorerProcess
   */
  public boolean isKillJExplorerProcess() {
    return killJExplorerProcess;
  }

  /**
   * @param killJExplorerProcess the killJExplorerProcess to set
   */
  public void setKillJExplorerProcess(boolean killJExplorerProcess) {
    this.killJExplorerProcess = killJExplorerProcess;
  }

  public void run() throws ProbeException {
    long beginTime = System.currentTimeMillis();
    try {
      long timeToSleeping = 5 * 1000;
      if (this.taskConfig == null) {
        // First time, load task config
        fireLoadTaskConfigCycle(this.taskContext);
      }

      if (this.taskConfig != null) {
        timeToSleeping = taskConfig.getInterval() * 1000;
        if (beginTime - lastExecutionTime >= timeToSleeping) {
          if (log.isDebugEnabled()) {
            log.debug("Starting all of task execution cycle.");
          }
          lastExecutionTime = beginTime;
          fireTaskExecutionCycle(this.taskContext, timeToSleeping);
          fireSubmitResultCycle(this.taskContext);
          fireLoadTaskConfigCycle(this.taskContext);
          if (log.isDebugEnabled()) {
            log.debug("End task execution cycle, elapse time: " + (System.currentTimeMillis() - beginTime) + "ms");
          }
        }
      } else {
        log.warn("Missing Task configuration!");
      }
    } catch (Exception e) {
      log.error("Fail to run Task group, cause: " + e);
    } finally {
    }
  }

  private void fireTearDown(TaskContext taskContext2) {
    if (this.killJExplorerProcess ) {
      try {
        // 清除进程tskill JExplorer32
        Runtime rt = Runtime.getRuntime();
        log.info("kill JExplorer32");
        Process proc = rt.exec("tskill JExplorer32");
        proc.wait(10 * 1000);
        int exitValue = proc.exitValue();
        log.info("[tskill JExplorer32] exit: " + exitValue);
      } catch (Exception e) {
        log.error("failure to [tskill JExplorer32]", e);
      }
    }

    try {
      // 清除浏览器抓屏图片
      File tmpDir = new File(System.getProperty("java.io.tmpdir"));
      File[] imgFiles = tmpDir.listFiles(new FilenameFilter() {

        @Override
        public boolean accept(File dir, String name) {
          return name.startsWith("java.io.tmpdir") && name.endsWith(".png");
        }
      });
      if (imgFiles != null) {
        for (File file : imgFiles) {
          if (file.exists() && file.isFile() && System.currentTimeMillis() - file.lastModified() > imageFileExpiredInHour * 3600 * 1000) {
            file.delete();
          }
        }
      }
    } catch (Exception e) {
      log.error("failure to [clear snapshot image files]", e);
    }
  }

  private void fireSubmitResultCycle(TaskContext context) throws ProbeException {

    Writer writer = new StringWriter();
    resultFormater.format(new Date(), context.getTaskResults().values(), writer);
    if (log.isDebugEnabled()) {
      log.debug("Submit results: " + writer.toString());
    }
    try {
      File file = createTempFile();
      log.debug("Output result XML to file: [" + file.getCanonicalPath() + "]");
      FileUtils.writeStringToFile(file, writer.toString());
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }

    // Call Dispatcher and submit data
    DataDispatcher dispatcher = this.dataDispatcher;
    dispatcher.submit(new Date(), context, this.resultFormater, this.taskConfig);

  }

  private void fireLoadTaskConfigCycle(TaskContext context) {
    try {
      URL configUrl = new URL((String) context.getAttribute("TASK_CONFIG_URL"));
      TaskConfig config = this.taskConfigFactory.loadTaskConfig(new InputStreamReader(configUrl.openStream(), "UTF-8"));
      if (config != null) {
        if (log.isDebugEnabled()) {
          log.info("Task config loaded from: [" + configUrl.toString() + "], total tasks: " + config.getTasks().size());
        }
        this.taskConfig = config;
      } else {
        log.info("Task config is empty, config file: [" + configUrl.toString() + "]");
      }
    } catch (Exception e) {
      log.error("Fail to run Task group, cause: " + e);
    }
  }

  private File createTempFile() throws IOException {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd.HHmmss");
    String timeStr = df.format(new Date());;
    return new File(System.getProperty("java.io.tmpdir"), JxBrowserExecutorImpl.PROBE_TEMP_FILE_PREFIX + timeStr + "_result.xml");
  }
  
  /**
   * Start one task execution cycle
   * 
   * @param context
   * @param config
   * @return
   */
  private void fireTaskExecutionCycle(TaskContext context, long timeToSleeping) {
    try {
      // Clear something
      fireTearDown(this.taskContext);

      // Init TaskContext
      NameServerLookup nameServerLookup = (NameServerLookup) this.taskContext.getAttribute("NAMESERVER_IP_ADDRESS_CACHE");
      if (nameServerLookup != null) {
        nameServerLookup.clear();
      }

      // Clear all of result
      this.taskContext.clearTaskResults();

      List<Thread> taskThreads = new ArrayList<Thread>();
      for (Task task : taskConfig.getTasks()) {
        Runnable runnable = this.getRunnable(context, task);
        if (task.getRunMode() == TaskRunMode.CONCURRENT) {
          if (log.isDebugEnabled()) {
            log.debug("Fire task thread in CONCURRENT mode for task: " + task.getBtype());
          }
          Thread thread = new Thread(runnable, "TaskThread-" + task.getBtype());
          // Fire other thread in concurrent mode
          thread.start();
          taskThreads.add(thread);
        } else {
          if (log.isDebugEnabled()) {
            log.debug("Fire task thread in SERIAL mode for task: " + task.getName());
          }
          runnable.run();
        }
      }

      // Waiting all of thread to die
      for (Thread thread : taskThreads) {
        thread.join(timeToSleeping);
      }
    } catch (Exception e) {
      log.error("Fail to run Task group, cause: " + e, e);
    }
  }

  private Runnable getRunnable(TaskContext taskContext, Task task) {
    ProbeRunnable runnable = new ProbeRunnable(applicationContext, taskContext, task);
    return runnable;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

}
