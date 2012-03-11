package com.npower.dm.core.selector;

import java.util.Set;
import java.util.TreeSet;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.management.ModelBean;

public abstract class BaseModelBeanSelector extends AbstractModelSelector {

  public BaseModelBeanSelector() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.selector.AbstractModelSelector#getModels()
   */
  @Override
  public Set<Model> getModels() throws DMException {
    Set<Model> result = new TreeSet<Model>();
    try {
      ModelBean modelBean = this.getManagementBeanFactory().createModelBean();
      for (Model model: modelBean.getAllModel()) {
          if (this.isSelected(modelBean, model)) {
             result.add(model);
          }
      }
      return result;
    } catch (Exception e) {
      throw new DMException(e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.selector.AbstractModelSelector#isSelected(com.npower.dm.core.Model)
   */
  @Override
  public boolean isSelected(Model model) throws DMException {
    try {
      ModelBean modelBean = this.getManagementBeanFactory().createModelBean();
      return isSelected(modelBean, model);
    } catch (Exception e) {
      throw new DMException(e);
    }
  }

  /**
   * @param modelBean
   * @param model
   * @return
   * @throws Exception
   */
  protected abstract boolean isSelected(ModelBean modelBean, Model model) throws Exception;
}