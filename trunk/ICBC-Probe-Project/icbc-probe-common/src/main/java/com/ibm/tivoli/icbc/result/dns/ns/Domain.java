/**
 * 
 */
package com.ibm.tivoli.icbc.result.dns.ns;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Zhao Dong Lu
 *
 */
public class Domain {
  
  private String name = null;
  private List<NameServer> nameServers = new ArrayList<NameServer>();
  private Date timeStamp = new Date();
  
  private String error = null;

  /**
   * 
   */
  public Domain() {
    super();
  }

  public Domain(String target) {
    this.name = target;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(Date timeStamp) {
    this.timeStamp = timeStamp;
  }

  public List<NameServer> getNameServers() {
    return nameServers;
  }

  public void setNameServers(List<NameServer> nameServers) {
    this.nameServers = nameServers;
  }

}
