/**
 * 
 */
package com.ibm.tivoli.icbc.probe.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author Zhao Dong Lu
 * 
 */
public class Task {

  private int index = 0;
  private String id = null;
  private String name = null;
  private String type = null;
  private String btype = null;
  private TaskRunMode runMode = TaskRunMode.SERIAL;

  /**
   * In mille-seconds
   */
  private long delay = 0L;

  private List<Parameter> parameters = new ArrayList<Parameter>();

  /**
   * 
   */
  public Task() {
    super();
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public String getId() {
    if (id == null || id.trim().length() == 0) {
       return Integer.toString(this.hashCode());
    }
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getBtype() {
    return btype;
  }

  public void setBtype(String btype) {
    this.btype = btype;
  }

  public TaskRunMode getRunMode() {
    return runMode;
  }

  public void setRunMode(TaskRunMode runMode) {
    this.runMode = runMode;
  }

  public void setRunModeAsString(String runMode) {
    if (StringUtils.isEmpty(runMode)) {
       return;
    } else if (runMode.equalsIgnoreCase("serial") || runMode.toLowerCase().equals("s")) {
      this.runMode = TaskRunMode.SERIAL;
      return;
    } else if (runMode.equalsIgnoreCase("concurrent") || runMode.toLowerCase().equals("c")) {
      this.runMode = TaskRunMode.CONCURRENT;
      return;
    }
    throw new RuntimeException("Parsing Task config error, unknow task run mode: " + runMode);
  }

  public long getDelay() {
    return delay;
  }

  public void setDelay(long delay) {
    this.delay = delay;
  }

  public void addParameter(Parameter p) {
    this.parameters.add(p);
  }

  public List<Parameter> getParameters() {
    return parameters;
  }

  public void setParameters(List<Parameter> parameters) {
    this.parameters = parameters;
  }

}
