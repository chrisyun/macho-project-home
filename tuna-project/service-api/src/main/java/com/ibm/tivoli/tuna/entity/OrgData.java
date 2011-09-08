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
@XmlRootElement(name = "Org")
public class OrgData extends AttributeStatement {
	
  private List<Attribute> attributes = new ArrayList<Attribute>();

  /**
   * 
   */
  public OrgData() {
    super();
  }
  
  @XmlElement(name = "Attribute")
  public List<Attribute> getAttributes() {
	return attributes;
  }

  public void setAttributes(List<Attribute> attributes) {
	this.attributes = attributes;
  }
  
}
