/**
 * 
 */
package com.ibm.tivoli.w7.util;

/**
 * @author zhaodonglu
 * 
 */
public class W7What {
  private String verb = null;
  private String noun = null;
  private String success = null;

  /**
   * 
   */
  public W7What() {
    super();
  }

  /**
   * @param verb
   * @param noun
   * @param success
   */
  public W7What(String verb, String noun, String success) {
    super();
    this.verb = verb;
    this.noun = noun;
    this.success = success;
  }

  /**
   * @return the verb
   */
  public String getVerb() {
    return verb;
  }

  /**
   * @param verb
   *          the verb to set
   */
  public void setVerb(String verb) {
    this.verb = verb;
  }

  /**
   * @return the noun
   */
  public String getNoun() {
    return noun;
  }

  /**
   * @param noun
   *          the noun to set
   */
  public void setNoun(String noun) {
    this.noun = noun;
  }

  /**
   * @return the success
   */
  public String getSuccess() {
    return success;
  }

  /**
   * @param success
   *          the success to set
   */
  public void setSuccess(String success) {
    this.success = success;
  }

}
