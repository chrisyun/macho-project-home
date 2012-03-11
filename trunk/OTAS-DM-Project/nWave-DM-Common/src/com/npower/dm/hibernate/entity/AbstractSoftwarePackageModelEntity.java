package com.npower.dm.hibernate.entity;

import com.npower.dm.core.Model;
import com.npower.dm.core.ModelClassification;
import com.npower.dm.core.SoftwarePackage;

public class AbstractSoftwarePackageModelEntity {

  private long id = 0;
  private Model model = null;
  private String modelFamilyExtID = null;
  private String dynamicModelSelector = null;
  private SoftwarePackage softwarePackage = null;
  private ModelClassification modelClassification = null;

  public AbstractSoftwarePackageModelEntity() {
    super();
  }

  /**
   * @return Returns the id.
   */
  public long getId() {
    return id;
  }

  /**
   * @param id The id to set.
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * @return Returns the model.
   */
  public Model getModel() {
    return model;
  }

  /**
   * @param model The model to set.
   */
  public void setModel(Model model) {
    this.model = model;
  }

  /**
   * @return Returns the modelFamilyExtID.
   */
  public String getModelFamilyExtID() {
    return modelFamilyExtID;
  }

  /**
   * @param modelFamilyExtID The modelFamilyExtID to set.
   */
  public void setModelFamilyExtID(String modelFamilyExtID) {
    this.modelFamilyExtID = modelFamilyExtID;
  }

  /**
   * @return Returns the synamicModelSelector.
   */
  public String getDynamicModelSelector() {
    return dynamicModelSelector;
  }

  /**
   * @param synamicModelSelector The synamicModelSelector to set.
   */
  public void setDynamicModelSelector(String synamicModelSelector) {
    this.dynamicModelSelector = synamicModelSelector;
  }

  /**
   * @return Returns the softwarePackage.
   */
  public SoftwarePackage getSoftwarePackage() {
    return softwarePackage;
  }

  /**
   * @param softwarePackage The softwarePackage to set.
   */
  public void setSoftwarePackage(SoftwarePackage softwarePackage) {
    this.softwarePackage = softwarePackage;
  }

  /**
   * @return the modelClassification
   */
  public ModelClassification getModelClassification() {
    return modelClassification;
  }

  /**
   * @param modelClassification the modelClassification to set
   */
  public void setModelClassification(ModelClassification modelClassification) {
    this.modelClassification = modelClassification;
  }

}