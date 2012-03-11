package com.npower.dm.hibernate.entity;

import java.io.Serializable;

import com.npower.dm.core.Model;

/**
 * PredefinedModelSelectorEntity entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class PredefinedModelSelectorEntity extends AbstractPredefinedModelSelector implements Serializable {

  // Constructors

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** default constructor */
  public PredefinedModelSelectorEntity() {
  }

  /** full constructor */
  public PredefinedModelSelectorEntity(ModelClassificationEntity modelClassification, Model model) {
    super(modelClassification, model);
  }

}
