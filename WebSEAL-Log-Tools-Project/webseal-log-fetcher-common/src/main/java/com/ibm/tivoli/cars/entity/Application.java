/**
 * 
 */
package com.ibm.tivoli.cars.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaodonglu
 *
 */
public class Application {
  
  private String name = null;
  private List<String> junctions = new ArrayList<String>();
  private String description = null;
  private List<Action> actions = new ArrayList<Action>();

  /**
   * 
   */
  public Application() {
    super();
  }

  public Application(String name, String junction) {
    super();
    this.name = name;
    this.junctions.add(junction);
  }
  
  public Application(String name, List<String> junctions) {
    super();
    this.name = name;
    this.junctions = junctions;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the junction
   */
  public List<String> getJunctions() {
    return junctions;
  }

  /**
   * @param junction the junction to set
   */
  public void setJunctions(List<String> junctions) {
    this.junctions = junctions;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return the actions
   */
  public List<Action> getActions() {
    return actions;
  }

  /**
   * @param actions the actions to set
   */
  public void setActions(List<Action> actions) {
    this.actions = actions;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    final int maxLen = 100;
    return String.format("Application [name=%s, junctions=%s, description=%s, actions=%s]", name,
        junctions != null ? junctions.subList(0, Math.min(junctions.size(), maxLen)) : null, description,
        actions != null ? actions.subList(0, Math.min(actions.size(), maxLen)) : null);
  }

}
