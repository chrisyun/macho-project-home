package com.ibm.tivoli.icbc.result;


public class Result {
  
  private int index = 0;
  private String taskId = null;
  private String taskBtype = null;
  private String taskType = null;
  private String taskName = null;
  
  private String error = null;

  public Result() {
    super();
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public String getTaskType() {
    return taskType;
  }

  public void setTaskType(String taskType) {
    this.taskType = taskType;
  }

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public String getTaskBtype() {
    return taskBtype;
  }

  public void setTaskBtype(String taskBtype) {
    this.taskBtype = taskBtype;
  }

  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }


}