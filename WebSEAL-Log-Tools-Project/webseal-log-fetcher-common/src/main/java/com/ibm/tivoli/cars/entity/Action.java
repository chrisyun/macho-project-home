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
public class Action {
  
  /**
   * Action URL Regexp pattern, junction prefix will be included.<br/>
   * Eg: /oa/Login?event\=LdapToOa
   */
  private List<String> urlPatterns = new ArrayList<String>();
  
  /**
   * Name of action, eg: "Login"
   */
  private String name = null;
  
  /**
   * If true, matched request will be upload to server, save into DB
   */
  private boolean uploaded = true;
  
  /**
   * 
   */
  public Action() {
    super();
  }

  public Action(String name) {
    super();
    this.name = name;
  }

  public Action(String name, String urlPattern) {
    super();
    this.name = name;
    this.urlPatterns.add(urlPattern);
  }

  public Action(String name, List<String> urlPatterns) {
    super();
    this.name = name;
    this.urlPatterns = urlPatterns;
  }


  public Action(String name, String[] urlPatterns) {
    super();
    this.name = name;
    if (urlPatterns != null) {
       for (String urlPattern: urlPatterns) {
           this.urlPatterns.add(urlPattern);
       }
    }
  }
  
  /**
   * @return the urlPatterns
   */
  public List<String> getUrlPatterns() {
    return urlPatterns;
  }

  /**
   * @param urlPatterns the urlPatterns to set
   */
  public void setUrlPatterns(List<String> urlPatterns) {
    this.urlPatterns = urlPatterns;
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
   * @return the uploaded
   */
  public boolean isUploaded() {
    return uploaded;
  }

  /**
   * @param uploaded the uploaded to set
   */
  public void setUploaded(boolean uploaded) {
    this.uploaded = uploaded;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    final int maxLen = 100;
    return String.format("Action [urlPatterns=%s, name=%s, uploaded=%s]", urlPatterns != null ? urlPatterns.subList(0, Math.min(urlPatterns.size(), maxLen))
        : null, name, uploaded);
  }

}
