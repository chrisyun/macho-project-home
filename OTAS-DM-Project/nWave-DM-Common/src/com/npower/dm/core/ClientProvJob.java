package com.npower.dm.core;

import java.util.Date;
import java.util.Set;

public interface ClientProvJob {

  /**
   * @return
   */
  public abstract long getId();

  /**
   * @param jobId
   */
  public abstract void setId(long jobId);

  /**
   * @return
   */
  public abstract String getName();

  /**
   * @param name
   */
  public abstract void setName(String name);

  /**
   * @return
   */
  public abstract String getType();

  /**
   * @param type
   */
  public abstract void setType(String type);

  /**
   * @return
   */
  public abstract String getDescription();

  /**
   * @param description
   */
  public abstract void setDescription(String description);

  /**
   * @return
   */
  public abstract String getState();

  /**
   * @param state
   */
  public abstract void setState(String state);

  /**
   * @return
   */
  public abstract Date getScheduleTime();

  /**
   * @param scheduleTime
   */
  public abstract void setScheduleTime(Date scheduleTime);

  /**
   * @return
   */
  public abstract long getMaxRetries();

  /**
   * @param maxRetries
   */
  public abstract void setMaxRetries(long maxRetries);

  /**
   * @return
   */
  public abstract long getMaxDuration();

  /**
   * @param maxDuration
   */
  public abstract void setMaxDuration(long maxDuration);

  /**
   * @return
   */
  public abstract Date getCreatedTime();

  /**
   * @param createdTime
   */
  public abstract void setCreatedTime(Date createdTime);

  /**
   * @return
   */
  public abstract String getCreatedBy();

  /**
   * @param createdBy
   */
  public abstract void setCreatedBy(String createdBy);

  /**
   * @return
   */
  public abstract Date getLastUpdatedTime();

  /**
   * @param lastUpdatedTime
   */
  public abstract void setLastUpdatedTime(Date lastUpdatedTime);

  /**
   * @return
   */
  public abstract String getLastUpdatedBy();

  /**
   * @param lastUpdatedBy
   */
  public abstract void setLastUpdatedBy(String lastUpdatedBy);

  /**
   * @return
   */
  public abstract Set<ClientProvJobTargetDevice> getTargetDevices();

  /**
   * @param targetDevices
   */
  public abstract void setTargetDevices(Set<ClientProvJobTargetDevice> targetDevices);

}