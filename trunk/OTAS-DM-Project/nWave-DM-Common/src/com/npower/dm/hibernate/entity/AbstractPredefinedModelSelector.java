package com.npower.dm.hibernate.entity;

import com.npower.dm.core.Model;

/**
 * AbstractPredefinedModelSelector entity provides the base persistence
 * definition of the PredefinedModelSelectorEntity entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractPredefinedModelSelector implements java.io.Serializable {

  // Fields

  private long          id;

  private ModelClassificationEntity modelClassification;

  private Model     model;

  // Constructors

  /** default constructor */
  public AbstractPredefinedModelSelector() {
  }

  /** full constructor */
  public AbstractPredefinedModelSelector(ModelClassificationEntity modelClassification, Model model) {
    this.modelClassification = modelClassification;
    this.model = model;
  }

  // Property accessors

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Model getModel() {
    return this.model;
  }

  public void setModel(Model model) {
    this.model = model;
  }

  /**
   * @return the modelClassification
   */
  public ModelClassificationEntity getModelClassification() {
    return modelClassification;
  }

  /**
   * @param modelClassification the modelClassification to set
   */
  public void setModelClassification(ModelClassificationEntity modelClassification) {
    this.modelClassification = modelClassification;
  }

}