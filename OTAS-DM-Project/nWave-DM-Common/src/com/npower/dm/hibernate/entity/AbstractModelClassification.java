package com.npower.dm.hibernate.entity;

import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

import com.npower.dm.core.ModelClassification;

/**
 * AbstractModelClassification entity provides the base persistence definition
 * of the ModelClassificationEntity entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractModelClassification implements java.io.Serializable, Comparable<ModelClassification> {

  // Fields

  private long   id;

  private String externalID;

  private String name;

  private String description;

  private Blob modelSelectorBinary;

  private Set<AbstractPredefinedModelSelector>    predefinedModelSelectors = new HashSet<AbstractPredefinedModelSelector>(0);
  
  // Constructors

  /** default constructor */
  public AbstractModelClassification() {
  }

  /** minimal constructor */
  public AbstractModelClassification(String externalId, String name) {
    this.externalID = externalId;
    this.name = name;
  }

  /** full constructor */
  public AbstractModelClassification(String externalId, String name, String description) {
    this.externalID = externalId;
    this.name = name;
    this.description = description;
  }

  // Property accessors

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getExternalID() {
    return this.externalID;
  }

  public void setExternalID(String externalId) {
    this.externalID = externalId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return the modelSelectorBinary
   */
  public Blob getModelSelectorBinary() {
    return modelSelectorBinary;
  }

  /**
   * @param modelSelectorBinary the modelSelectorBinary to set
   */
  public void setModelSelectorBinary(Blob modelSelectorBinary) {
    this.modelSelectorBinary = modelSelectorBinary;
  }

  /**
   * @return the predefinedModelSelectors
   */
  public Set<AbstractPredefinedModelSelector> getPredefinedModelSelectors() {
    return predefinedModelSelectors;
  }

  /**
   * @param predefinedModelSelectors the predefinedModelSelectors to set
   */
  public void setPredefinedModelSelectors(Set<AbstractPredefinedModelSelector> predefinedModelSelectors) {
    this.predefinedModelSelectors = predefinedModelSelectors;
  }

}