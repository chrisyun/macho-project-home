package com.tutucha.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccessEvent {

  private List<UserIdentity> userIdentities = new ArrayList<UserIdentity>();
  
  private Date timestamp = new Date();
  
  private Target target = null;
  
  public AccessEvent() {
    super();
  }

  public AccessEvent(List<UserIdentity> userIdentities, Date timestamp, Target target) {
    super();
    this.userIdentities = userIdentities;
    this.timestamp = timestamp;
    this.target = target;
  }

  public AccessEvent(List<UserIdentity> userIdentities, Target target) {
    super();
    this.userIdentities = userIdentities;
    this.target = target;
  }

  public List<UserIdentity> getUserIdentities() {
    return userIdentities;
  }

  public void setUserIdentities(List<UserIdentity> userIdentities) {
    this.userIdentities = userIdentities;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public Target getTarget() {
    return target;
  }

  public void setTarget(Target target) {
    this.target = target;
  }

  @Override
  public String toString() {
    return "AccessEvent [userIdentities=" + userIdentities + ", timestamp=" + timestamp + ", target=" + target + "]";
  }

}
