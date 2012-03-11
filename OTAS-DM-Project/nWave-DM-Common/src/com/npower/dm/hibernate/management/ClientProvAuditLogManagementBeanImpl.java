package com.npower.dm.hibernate.management;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.npower.dm.core.ClientProvAuditLog;
import com.npower.dm.core.DMException;
import com.npower.dm.hibernate.entity.ClientProvAuditLogEntity;
import com.npower.dm.management.ClientProvAuditLogBean;
import com.npower.dm.management.ManagementBeanFactory;

public class ClientProvAuditLogManagementBeanImpl extends AbstractBean implements ClientProvAuditLogBean{
  
  
  /**
   * ClientProvAuditLog with the parameter of Hibernate Session.
   */
  ClientProvAuditLogManagementBeanImpl(ManagementBeanFactory factory, Session hsession) {
    super(factory, hsession);
  }

  public ClientProvAuditLog newClientProvAuditLogInstance() throws DMException {   
    return new ClientProvAuditLogEntity();
  }
    
  public ClientProvAuditLog newClientProvAuditLogInstance(Date timeStamp, String devicePhoneNumber, String status) throws DMException {
    return new ClientProvAuditLogEntity(timeStamp, devicePhoneNumber, status);
  }
   
  public void delete(ClientProvAuditLog clientProvAuditLog) throws DMException {
    Session session = this.getHibernateSession();
    try {
      Transaction tx = session.beginTransaction();
      session.delete(clientProvAuditLog);
      tx.commit();
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }    
  }

  public List<ClientProvAuditLog> findClientProvAuditLogs(String whereClause) throws DMException {
    Session session = this.getHibernateSession();
    try {      
      Query query = session.createQuery(whereClause);
      List<ClientProvAuditLog> list = query.list();
      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  public List<ClientProvAuditLog> getAllClientProvAuditLogs() throws DMException {
    Session session = this.getHibernateSession();
    try {      
      Query query = session.createQuery("from ClientProvAuditLogEntity ");
      List<ClientProvAuditLog> list = query.list();
      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  public ClientProvAuditLog getClientProvAuditLogByID(long id) throws DMException {
    if (id == 0) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {      
      Query query = session.createQuery("from ClientProvAuditLogEntity where cpAuditLogId='" + id + "'");
      List<ClientProvAuditLog> list = query.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (ClientProvAuditLog) list.get(0);
      } else {
        throw new DMException("Result is not unique, many clientProvAuditLogs have the same ID: " + id);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  public void update(ClientProvAuditLog clientProvAuditLog) throws DMException {
    if (clientProvAuditLog == null) {
      throw new NullPointerException("Could not add a null clientProvAuditLog into database.");
    }
    Session session = this.getHibernateSession();
    try {      
      Transaction tx = session.beginTransaction();
      session.save(clientProvAuditLog);
      tx.commit();
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }


  public List<ClientProvAuditLog> findClientProvAuditLogByModel(String model) throws DMException{
    return null;
  }

  public List<ClientProvAuditLog> findClientProvAuditLogByDate(Date date) throws DMException{
    return null;
  }

  public List<ClientProvAuditLog> findClientProvAuditLogByManufacturer(String manufacturer) throws DMException{
    return null;
  }

  public List<ClientProvAuditLog> findClientProvAuditLogByCarrier(String carrier) throws DMException{
    return null;
  }
  
  public ClientProvAuditLog findClientProvAuditLogByPhoneNumber(String phoneNumber) throws DMException {
    return null;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ClientProvAuditLogBean#getNextID(java.lang.Long)
   */
  public Long getNextID(Long CPAuditLogID) throws DMException {
    Session session = this.getHibernateSession();
    try {      
      Criteria criteria = session.createCriteria(ClientProvAuditLogEntity.class);
      criteria.addOrder(Order.asc("cpAuditLogId"));
      criteria.add(Expression.gt("cpAuditLogId", CPAuditLogID));
      criteria.setMaxResults(1);
      List<ClientProvAuditLog> list = criteria.list();
      if (list.size() == 0) {
        return null;
      }
      Long ID = ((ClientProvAuditLog)list.get(0)).getCpAuditLogId();
      return ID;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ClientProvAuditLogBean#getPrevID(java.lang.Long)
   */
  public Long getPrevID(Long CPAuditLogID) throws DMException  {
    Session session = this.getHibernateSession();
    try {      
      Criteria criteria = session.createCriteria(ClientProvAuditLogEntity.class);
      criteria.addOrder(Order.desc("cpAuditLogId"));
      criteria.add(Expression.lt("cpAuditLogId", CPAuditLogID));
      criteria.setMaxResults(1);
      List<ClientProvAuditLog> list = criteria.list();
      if (list.size() == 0) {
        return null;
      }
      Long ID = ((ClientProvAuditLog)list.get(0)).getCpAuditLogId();
      return ID;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
   }

  public long getLatestSendTime(String phoneNumber, String status) throws DMException {

    Session session = this.getHibernateSession();

    Criteria criteria = session.createCriteria(ClientProvAuditLogEntity.class);
    Conjunction conjunction = Expression.conjunction();
    conjunction.add(Expression.eq("devicePhoneNumber", phoneNumber));
    conjunction.add(Expression.eq("status", status));
    criteria.add(conjunction);
    List<ClientProvAuditLogEntity> list = criteria.list();
    ClientProvAuditLogEntity  clientProvAuditLog = null;
    if (list != null && list.size() != 0) {
      clientProvAuditLog= list.get(0);      
    }
    
    if (clientProvAuditLog != null) {
      return clientProvAuditLog.getTimeStamp().getTime();
    }else {
      return 0;      
    }
  }

  public long getLatestSendTime(String phoneNumber, String[] states) throws DMException {
 
    Session session = this.getHibernateSession();
    Criteria criteria = session.createCriteria(ClientProvAuditLogEntity.class);
    criteria.addOrder(Order.desc("timeStamp"));
    Conjunction conjunction = Expression.conjunction();
    conjunction.add(Expression.eq("devicePhoneNumber", phoneNumber));
    Disjunction jisDisjunction = Expression.disjunction();
    for (int i = 0 ;i <= states.length - 1;i++) {
      jisDisjunction.add(Expression.eq("status", states[i]));     
    }
    conjunction.add(jisDisjunction);
    criteria.add(conjunction);
    List<ClientProvAuditLogEntity> list = criteria.list();
    ClientProvAuditLogEntity  clientProvAuditLog = null;
    if (list != null && list.size() != 0) {
      clientProvAuditLog= list.get(0);      
    }
    
    if (clientProvAuditLog != null) {
      return clientProvAuditLog.getTimeStamp().getTime();
    }else {
      return 0;      
    }

  }
  
}
