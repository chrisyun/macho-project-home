/**
 * 
 */
package com.ibm.tivoli.icbc.probe.task;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.ibm.tivoli.icbc.probe.Probe;
import com.ibm.tivoli.icbc.probe.ProbeException;
import com.ibm.tivoli.icbc.probe.dns.NSAndARecordProbeV2;
import com.ibm.tivoli.icbc.probe.dns.NSProbe;
import com.ibm.tivoli.icbc.probe.dns.NameServerLookup;
import com.ibm.tivoli.icbc.result.Result;

/**
 * @author Zhao Dong Lu
 *
 */
public class ProbeRunnable implements Runnable {
  private static Log log = LogFactory.getLog(ProbeRunnable.class);

  private ApplicationContext applicationContext;
  private TaskContext taskContext;
  private Task task;

  /**
   * 
   */
  public ProbeRunnable() {
    super();
  }

  public ProbeRunnable(ApplicationContext applicationContext, TaskContext taskContext, Task task) {
    this.applicationContext = applicationContext;
    this.taskContext = taskContext;
    this.task = task;
  }

  public ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  public void setApplicationContext(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  public TaskContext getTaskContext() {
    return taskContext;
  }

  public void setTaskContext(TaskContext taskContext) {
    this.taskContext = taskContext;
  }

  public Task getTask() {
    return task;
  }

  public void setTask(Task task) {
    this.task = task;
  }

  @Override
  public void run() {
    try {
      String taskType = task.getType().toUpperCase();
      taskType = taskType.replace('-', '_');
      if (taskType.equalsIgnoreCase("DNS_C")) {
        taskType = "DNS_CNAME";
      } else if (taskType.equalsIgnoreCase("HTTP")) {
        taskType = "HTTP_DNS_MODE";
      } else if (taskType.equalsIgnoreCase("HTTPS")) {
        taskType = "HTTPS_DNS_MODE";
      }
      
      String beanName = "probe4" + taskType;
      Probe<?> probe = (Probe<?>)applicationContext.getBean(beanName);
      if (this.task.getDelay() > 0) {
         log.info("Before execute task[" + this.task.getBtype() + "," + this.task.getName() + ", make a delay " + this.task.getDelay() + "ms ");
         Thread.sleep(this.task.getDelay());
      }
      
      long beginTime = System.currentTimeMillis();
      log.info("Start probe[" + probe.getClass().getSimpleName() + "] for Task[" + this.task.getBtype() + "," + this.task.getName() + "]");
      Map<String, Object> props = new HashMap<String, Object>();
      for (Parameter param: this.task.getParameters()) {
          String paramName = param.getName();
          if (paramName.equals("request")) {
             if (taskType.startsWith("HTTP")) {
               paramName = "targetUrls";
             } else {
               paramName = "targets";
             }
          }
          props.put(paramName, param.getValues());
          
      }
      BeanUtils.populate(probe, props);
      
      if (probe instanceof NSAndARecordProbeV2) {
        NameServerLookup nameServerLookup = (NameServerLookup)this.taskContext.getAttribute("NAMESERVER_IP_ADDRESS_CACHE");
        if (nameServerLookup != null) {
           ((NSAndARecordProbeV2)probe).setNameServerLookup(nameServerLookup);
        }
      }
      if (probe instanceof NSProbe) {
        NameServerLookup nameServerLookup = (NameServerLookup)this.taskContext.getAttribute("NAMESERVER_IP_ADDRESS_CACHE");
        if (nameServerLookup != null) {
           ((NSProbe)probe).setNameServerLookup(nameServerLookup);
        }
      }
      
      
      Result result = (Result)probe.run();
      
      BeanUtils.setProperty(result, "taskId", this.task.getId());
      BeanUtils.setProperty(result, "taskType", this.task.getType());
      BeanUtils.setProperty(result, "taskBtype", this.task.getBtype());
      BeanUtils.setProperty(result, "taskName", this.task.getName());
      BeanUtils.setProperty(result, "index", this.task.getIndex());
      
      this.getTaskContext().putTaskResult(this.task.getId(), (Result)result);
      
      long elapseTime = System.currentTimeMillis() - beginTime;
      log.info("Finished probe[" + probe.getClass().getSimpleName() + "] for Task[" + this.task.getId() + "," + this.task.getName() + "], elapse time: " + elapseTime + "ms");
    } catch (BeansException e) {
      log.error("failure to execute Task[" + this.task.getBtype() + "," + this.task.getName() + "], cause: " + e.getMessage(), e);
    } catch (InterruptedException e) {
      log.error("failure to execute Task[" + this.task.getBtype() + "," + this.task.getName() + "], cause: " + e.getMessage(), e);
    } catch (ProbeException e) {
      log.error("failure to execute Task[" + this.task.getBtype() + "," + this.task.getName() + "], cause: " + e.getMessage(), e);
    } catch (IllegalAccessException e) {
      log.error("failure to execute Task[" + this.task.getBtype() + "," + this.task.getName() + "], cause: " + e.getMessage(), e);
    } catch (InvocationTargetException e) {
      log.error("failure to execute Task[" + this.task.getBtype() + "," + this.task.getName() + "], cause: " + e.getMessage(), e);
    }
  }

}
