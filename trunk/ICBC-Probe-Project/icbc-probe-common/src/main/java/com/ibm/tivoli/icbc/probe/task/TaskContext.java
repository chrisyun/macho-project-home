/**
 * 
 */
package com.ibm.tivoli.icbc.probe.task;

import java.util.HashMap;
import java.util.Map;

import com.ibm.tivoli.icbc.result.Result;

/**
 * @author Zhao Dong Lu
 *
 */
public class TaskContext {
  
  private Map<String, Object> attributes = new HashMap<String, Object>();
  private Map<String, Result> results = new HashMap<String, Result>();
  /**
   * 
   */
  public TaskContext() {
    super();
  }

  public Object getAttribute(String name) {
    return attributes.get(name);
  }
  
  public void setAttribute(String name, String value) {
    this.attributes.put(name, value);
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes.putAll(attributes);
  }

  /**
   * @param id - ID of Task
   * @param result
   */
  public void putTaskResult(String id, Result result) {
    this.results.put(id, result);
  }

  public Map<String, Result> getTaskResults() {
    return this.results;
  }
  
  public void clearTaskResults() {
    this.results.clear();
  }

  public Map<String, Object> getAttributes() {
     return attributes;
  }
}
