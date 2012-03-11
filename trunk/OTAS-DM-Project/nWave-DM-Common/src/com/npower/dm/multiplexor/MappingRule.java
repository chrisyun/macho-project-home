package com.npower.dm.multiplexor;

public abstract class MappingRule {

  private String numberPattern;
  private String queueName;

  public MappingRule() {
    super();
  }

  /**
   * @param numberPattern
   * @param queueName
   */
  public MappingRule(String numberPattern, String queueName) {
    super();
    this.numberPattern = numberPattern;
    this.queueName = queueName;
  }

  public String getNumberPattern() {
    return numberPattern;
  }

  public void setNumberPattern(String numberPattern) {
    this.numberPattern = numberPattern;
  }

  public String getQueueName() {
    return queueName;
  }

  public void setQueueName(String queueName) {
    this.queueName = queueName;
  }

  public abstract boolean match(String source) throws Exception;

}