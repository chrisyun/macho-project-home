package com.npower.dm.hibernate.entity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.npower.dm.core.DMException;
import com.npower.dm.core.ManagementBeanFactoryAware;
import com.npower.dm.core.Model;
import com.npower.dm.core.ModelClassification;
import com.npower.dm.core.ModelSelector;
import com.npower.dm.core.selector.DisjunctionModelSelector;
import com.npower.dm.core.selector.ModelSelectorHelper;
import com.npower.dm.core.selector.PredefinedModelSelector;
import com.npower.dm.hibernate.HibernateSessionAware;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * ModelClassificationEntity entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class ModelClassificationEntity extends AbstractModelClassification implements Serializable, ModelClassification, HibernateSessionAware, ManagementBeanFactoryAware {

  // Constructors
  private Session hibernateSession = null;

  private ManagementBeanFactory managementBeanFactory;
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** default constructor */
  public ModelClassificationEntity() {
    super();
  }

  /** minimal constructor */
  public ModelClassificationEntity(String externalId, String name) {
    super(externalId, name);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ModelClassification#getModelSelector()
   */
  public ModelSelector getModelSelector() throws DMException {
    try {
      DisjunctionModelSelector result = new DisjunctionModelSelector();
      // Pass Hibernate Session into ModelSelector
      ModelSelectorHelper.decorate(this.getManagementBeanFactory(), this.getHibernateSession(), result);

      Blob blob = this.getModelSelectorBinary();
      if (blob != null) {
         InputStream in = blob.getBinaryStream();
         if (in != null) {
            ObjectInputStream oin = new ObjectInputStream(in);
            ModelSelector selector = (ModelSelector)oin.readObject();
            // Pass Hibernate Session into ModelSelector
            ModelSelectorHelper.decorate(this.getManagementBeanFactory(), this.getHibernateSession(), selector);
            result.add(selector);
         }
      }
      if (!this.getPredefinedModelSelectors().isEmpty()) {
         Set<Model> models = new HashSet<Model>();
         for (AbstractPredefinedModelSelector e: this.getPredefinedModelSelectors()) {
             models.add(e.getModel());
         }
         PredefinedModelSelector selector = new PredefinedModelSelector(models);
         // Pass Hibernate Session into ModelSelector
         ModelSelectorHelper.decorate(this.getManagementBeanFactory(), this.getHibernateSession(), selector);
         result.add(selector);
      }
      return result;
    } catch (SQLException e) {
      throw new DMException("Failure to recovery model selector from blob for model classification id: " + this.getId(), e);
    } catch (IOException e) {
      throw new DMException("Failure to recovery model selector from blob for model classification id: " + this.getId(), e);
    } catch (ClassNotFoundException e) {
      throw new DMException("Failure to recovery model selector from blob for model classification id: " + this.getId(), e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ModelClassification#isMemeber(com.npower.dm.core.Model)
   */
  public boolean isMemeber(Model model) throws DMException {
    ModelSelector selector = this.getModelSelector();
    return selector.isSelected(model);
  }

  public void setModelSelector(ModelSelector selector) throws DMException {
    if (selector == null) {
      return;
    }
    try {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bytes);
        out.writeObject(selector);
        Blob blob = Hibernate.createBlob(bytes.toByteArray());
        this.setModelSelectorBinary(blob);
    } catch (IOException e) {
      throw new DMException("Failure to save model selector into blob for model classification id: " + this.getId(), e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.HibernateSessionAware#getHibernateSession()
   */
  public Session getHibernateSession() {
    return this.hibernateSession;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.HibernateSessionAware#setHibernateSession(org.hibernate.Session)
   */
  public void setHibernateSession(Session session) {
    this.hibernateSession = session;
  }

  /**
   * @return the managementBeanFactory
   */
  public ManagementBeanFactory getManagementBeanFactory() {
    return managementBeanFactory;
  }

  /**
   * @param managementBeanFactory the managementBeanFactory to set
   */
  public void setManagementBeanFactory(ManagementBeanFactory managementBeanFactory) {
    this.managementBeanFactory = managementBeanFactory;
  }

  public int compareTo(ModelClassification o) {
    // TODO Auto-generated method stub
    return 0;
  }

}
