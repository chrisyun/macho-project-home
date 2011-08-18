/**
 * 
 */
package com.ibm.tivoli.tuna.service;

import java.util.ArrayList;
import java.util.List;

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
  public List<Parameter> getParameters() {
    return parameters;
  }

  /**
   * @param parameters the parameters to set
   */
  public void setParameters(List<Parameter> parameters) {
    this.parameters = parameters;
  }

}
