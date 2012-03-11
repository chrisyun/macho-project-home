package com.npower.dm.audit.hibernate;

// default package

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import com.npower.dm.audit.AuditException;
import com.npower.dm.hibernate.entity.AuditLogAction;

/**
 * Data access object (DAO) for domain model class AuditLogAction.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/02/05 07:49:08 $
 */
public class AuditLogActionDAO {

  private static final Log   log         = LogFactory.getLog(AuditLogActionDAO.class);

  // property constants
  public static final String DESCRIPTION = "description";

  public static final String TYPE        = "type";

  public static final String OPTIONAL    = "optional";

  private Session session;

  /**
   * Default constructor
   */
  public AuditLogActionDAO() {
    super();
  }
  
  /**
   * Constructor
   * @param session
   *        Hibernate Session
   */
  public AuditLogActionDAO(Session session) {
    this.session = session;
  }
  
  // Private methods -----------------------------------------------------------

  /**
   * Return Hibernate session.
   * @return
   */
  private Session getHibernateSession() throws AuditException {
    return this.session;
  }
  
  // Public methods -------------------------------------------------------------

  /**
   * Save into database
   * @param transientInstance
   */
  public void save(AuditLogAction transientInstance) {
    log.debug("saving AuditLogAction instance");
    try {
      this.getHibernateSession().save(transientInstance);
      log.debug("save successful");
    } catch (Exception re) {
      log.error("save failed", re);
      throw new RuntimeException(re);
    }
  }

  /**
   * Delete a record.
   * @param persistentInstance
   */
  public void delete(AuditLogAction persistentInstance) {
    log.debug("deleting AuditLogAction instance");
    try {
      this.getHibernateSession().delete(persistentInstance);
      log.debug("delete successful");
    } catch (Exception re) {
      log.error("delete failed", re);
      throw new RuntimeException(re);
    }
  }

  /**
   * Load a record by ID.
   * @param id
   * @return
   */
  public AuditLogAction findById(String id) {
    log.debug("getting AuditLogAction instance with id: " + id);
    try {
      AuditLogAction instance = (AuditLogAction) this.getHibernateSession().get(AuditLogAction.class, id);
      return instance;
    } catch (Exception re) {
      log.error("get failed", re);
      throw new RuntimeException(re);
    }
  }

  /**
   * Find records.
   * @param instance
   * @return
   */
  public List<AuditLogAction> findByExample(AuditLogAction instance) {
    log.debug("finding AuditLogAction instance by example");
    try {
      List<AuditLogAction> results = this.getHibernateSession().createCriteria(AuditLogAction.class).add(Example.create(instance)).list();
      log.debug("find by example successful, result size: " + results.size());
      return results;
    } catch (Exception re) {
      log.error("find by example failed", re);
      throw new RuntimeException(re);
    }
  }

  /**
   * Find records.
   * @param propertyName
   * @param value
   * @return
   */
  public List<AuditLogAction> findByProperty(String propertyName, Object value) {
    log.debug("finding AuditLogAction instance with property: " + propertyName + ", value: " + value);
    try {
      String queryString = "from AuditLogAction as model where model." + propertyName + "= ?";
      Query queryObject = this.getHibernateSession().createQuery(queryString);
      queryObject.setParameter(0, value);
      return queryObject.list();
    } catch (Exception re) {
      log.error("find by property name failed", re);
      throw new RuntimeException(re);
    }
  }

  /**
   * Find records.
   * @param description
   * @return
   */
  public List<AuditLogAction> findByDescription(Object description) {
    return findByProperty(DESCRIPTION, description);
  }

  /**
   * Find records.
   * @param type
   * @return
   */
  public List<AuditLogAction> findByType(Object type) {
    return findByProperty(TYPE, type);
  }

  /**
   * Find records.
   * @param optional
   * @return
   */
  public List<AuditLogAction> findByOptional(Object optional) {
    return findByProperty(OPTIONAL, optional);
  }

  public AuditLogAction merge(AuditLogAction detachedInstance) {
    log.debug("merging AuditLogAction instance");
    try {
      AuditLogAction result = (AuditLogAction) this.getHibernateSession().merge(detachedInstance);
      log.debug("merge successful");
      return result;
    } catch (Exception re) {
      log.error("merge failed", re);
      throw new RuntimeException(re);
    }
  }

  public void attachDirty(AuditLogAction instance) {
    log.debug("attaching dirty AuditLogAction instance");
    try {
      this.getHibernateSession().saveOrUpdate(instance);
      log.debug("attach successful");
    } catch (Exception re) {
      log.error("attach failed", re);
      throw new RuntimeException(re);
    }
  }

  public void attachClean(AuditLogAction instance) {
    log.debug("attaching clean AuditLogAction instance");
    try {
      this.getHibernateSession().lock(instance, LockMode.NONE);
      log.debug("attach successful");
    } catch (Exception re) {
      log.error("attach failed", re);
      throw new RuntimeException(re);
    }
  }
}