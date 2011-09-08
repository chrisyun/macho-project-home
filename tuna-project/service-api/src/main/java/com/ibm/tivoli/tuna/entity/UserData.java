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
}
