/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/analysis/Problem.java,v 1.1 2008/12/10 05:24:20 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/12/10 05:24:20 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
 *
 * This SOURCE CODE FILE, which has been provided by NPower as part
 * of a NPower product for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information of NPower.
 *
 * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
 * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
 * THE PRODUCT.
 *
 * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
 * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
 * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
 * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
 * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
 * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
 * CODE FILE.
 * ===============================================================================================
 */
package com.npower.dm.analysis;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/12/10 05:24:20 $
 */
public abstract class Problem {
  
  /**
   * 表示问题类型
   */
  private Severity severity = null;
  
  /**
   * 问题描述
   */
  private String description = null;

  /**
   * 出现问题的Device Tree Node Path
   */
  private String targetNodeLocation;
  
  /**
   * 出现问题的Device Tree Node当前值
   */
  private String currentValue;
  
  /**
   * 期望对当前节点的操作指令
   */
  private String expectedOperation;
  
  /**
   * 如果操作指令涉及修改操作, 则该属性为期望Device Tree Node被修改后的值
   */
  private String expectedValue;
  

  /**
   * 
   */
  public Problem() {
    super();
  }

  /**
   * @param severity
   * @param description
   */
  public Problem(Severity severity, String description) {
    super();
    this.severity = severity;
    this.description = description;
  }

  /**
   * @return the severity
   */
  public Severity getSeverity() {
    return severity;
  }

  /**
   * @param severity the severity to set
   */
  public void setSeverity(Severity severity) {
    this.severity = severity;
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
   * @return the targetNodeLocation
   */
  public String getTargetNodeLocation() {
    return targetNodeLocation;
  }

  /**
   * @param targetNodeLocation the targetNodeLocation to set
   */
  public void setTargetNodeLocation(String targetNodeLocation) {
    this.targetNodeLocation = targetNodeLocation;
  }

  /**
   * @return the currentValue
   */
  public String getCurrentValue() {
    return currentValue;
  }

  /**
   * @param currentValue the currentValue to set
   */
  public void setCurrentValue(String currentValue) {
    this.currentValue = currentValue;
  }

  /**
   * @return the expectedOperation
   */
  public String getExpectedOperation() {
    return expectedOperation;
  }

  /**
   * @param expectedOperation the expectedOperation to set
   */
  public void setExpectedOperation(String expectedOperation) {
    this.expectedOperation = expectedOperation;
  }

  /**
   * @return the expectedValue
   */
  public String getExpectedValue() {
    return expectedValue;
  }

  /**
   * @param expectedValue the expectedValue to set
   */
  public void setExpectedValue(String expectedValue) {
    this.expectedValue = expectedValue;
  }
  
  
}
