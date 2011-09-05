/**
 * 
 */
package com.ibm.tivoli.tuna.module.ltpa;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ZhaoDongLu
 *
 */
@XmlRootElement(name = "LTPACredential")
public class LTPACredential {
  
  private String realm = null;
  private String subject = null;
  private Date expireTime = null;

  /**
   * 
   */
  public LTPACredential() {
    super();
  }

  @XmlElement(name = "Realm")
  public String getRealm() {
    return realm;
  }

  public void setRealm(String realm) {
    this.realm = realm;
  }

  @XmlElement(name = "Subject")
  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  @XmlElement(name = "ExpireTime")
  public Date getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(Date expireTime) {
    this.expireTime = expireTime;
  }

}
