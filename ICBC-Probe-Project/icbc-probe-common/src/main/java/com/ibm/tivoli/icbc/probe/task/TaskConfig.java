/**
 * 
 */
package com.ibm.tivoli.icbc.probe.task;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhao Dong Lu
 *
 */
public class TaskConfig {

  /**
   * Interval in seconds between next launcher to run., default is 5 minutes.
   */
  private long interval = 5 * 60;
  
  private List<Task> tasks = new ArrayList<Task>();
  
  /**
   * In seconds
   */
  private int retryInterval = 1;
  
  private int numberOfRetry = 3;
  
  /**
   * 
   */
  public TaskConfig() {
    super();
  }

  public long getInterval() {
    return interval;
  }

  public void setInterval(long interval) {
    this.interval = interval;
  }

  public List<Task> getTasks() {
    return tasks;
  }

  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
  }
  
  public void add(Task task) {
    task.setIndex(this.tasks.size());
    this.tasks.add(task);
  }

  public int getRetryInterval() {
    return retryInterval;
  }

  public void setRetryInterval(int retryInterval) {
    this.retryInterval = retryInterval;
  }

  public int getNumberOfRetry() {
    return numberOfRetry;
  }

  public void setNumberOfRetry(int numberOfRetry) {
    this.numberOfRetry = numberOfRetry;
  }
  
}
