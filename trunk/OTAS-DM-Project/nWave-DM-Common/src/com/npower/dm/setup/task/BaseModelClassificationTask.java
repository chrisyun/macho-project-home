package com.npower.dm.setup.task;

import com.npower.dm.core.DMException;
import com.npower.dm.core.ModelClassification;
import com.npower.dm.core.ModelSelector;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelClassificationBean;
import com.npower.setup.core.SetupException;

public abstract class BaseModelClassificationTask extends DMTask {

  public BaseModelClassificationTask() {
    super();
  }

  /**
   * @param factory
   * @param extID
   * @param name
   * @param description
   * @param selector
   * @return
   * @throws DMException
   */
  protected ModelClassification updateModelClassification(ManagementBeanFactory factory, ModelSelector selector,
      String extID, String name, String description) throws DMException {
    ModelClassificationBean bean = factory.createModelClassificationBean();

    ModelClassification mc = bean.getModelClassificationByExtID(extID);
    if (mc == null) {
      // Create it.
      mc = bean.newInstance(extID, name, selector);
    } else {
      // Update it
      mc.setName(name);
      mc.setModelSelector(selector);
      mc.setDescription(description);
    }
    bean.update(mc);
    return mc;
  }

  @Override
  protected void process() throws SetupException {
    ManagementBeanFactory factory = null;
    try {
      factory = this.getManagementBeanFactory();
      factory.beginTransaction();
      this.createAll(factory);
      factory.commit();
    } catch (Exception ex) {
      if (factory != null) {
        factory.rollback();
      }
      throw new SetupException("Error in import manufacturers.", ex);
    } finally {
      if (factory != null) {
        factory.release();
      }
    }
  }

  @Override
  protected void rollback() throws SetupException {
  }

  /**
   * Define task into this function.
   */
  protected abstract void createAll(ManagementBeanFactory factory) throws Exception;

}