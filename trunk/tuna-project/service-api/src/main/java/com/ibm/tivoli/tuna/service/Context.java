/**
 * 
 */
package com.ibm.tivoli.tuna.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author zhaodonglu
 *
 */
public class Context {
  
  private List<Parameter> parameters = new ArrayList<Parameter>();

  /**
   * 
   */
  public Context() {
    super();
  }

  /**
   * @return the parameters
   */
  @XmlElement(name = "parameter")
  public List<Parameter> getParameters() {
    return parameters;
  }

  /**
   * @param parameters the parameters to set
   */
  public void setParameters(List<Parameter> parameters) {
    this.parameters = parameters;
  }
  
  public Parameter getParameter(String name) {
    if (this.parameters == null) {
       return null;
    }
    for (Parameter p: this.parameters) {
        if (p.getName().equals(name)) {
           return p;
        }
    }
    return null;
  }

}