/**
 * 
 */
package com.ibm.tivoli.tuna.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.ibm.tivoli.tuna.service.Attribute;
import com.ibm.tivoli.tuna.service.AttributeStatement;

/**
 * @author ZhaoDongLu
 *
 */
@XmlRootElement(name = "User")
public class UserData extends AttributeStatement {
  
  private String uid = null;
  
  private String cn = null;
  
  private String displayname = null;
	
  private List<Attribute> attributes = new ArrayList<Attribute>();

  public UserData() {
    super();
  }

  @XmlElement(name = "Attribute")
  public List<Attribute> getAttributes() {
    return attributes;
  }

  public void setAttributes(List<Attribute> attributes) {
    this.attributes = attributes;
  }
  
  @XmlElement(name = "uid")
  public String getUid() {
	return uid;
  }

  public void setUid(String uid) {
	this.uid = uid;
  }

  @XmlElement(name = "cn")
  public String getCn() {
	return cn;
  }

  public void setCn(String cn) {
	this.cn = cn;
  }

  @XmlElement(name = "displayname")
  public String getDisplayname() {
	return displayname;
  }

  public void setDisplayname(String displayname) {
	this.displayname = displayname;
  }
  
  
  
}
