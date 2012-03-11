/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/id/HashCodeIdentifierGenerator.java,v 1.1 2008/09/05 02:12:42 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/09/05 02:12:42 $
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
package com.npower.dm.hibernate.id;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.type.Type;

/**
 * 根据指定的属性值计算产生hashCode作为ID值, 通常用于根据externalID计算生成对应的ID,确保数据更新升级后,维持不变的ID.
 * <pre>
 * 用法如下:
 *  <generator class="com.npower.dm.hibernate.id.HashCodeIdentifierGenerator">
 *    <param name="property">externalID</param>
 *  </generator>
 * </pre>
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/09/05 02:12:42 $
 */
public class HashCodeIdentifierGenerator implements IdentifierGenerator, Configurable {

  private String propertyName = null;

  /**
   * 
   */
  public HashCodeIdentifierGenerator() {
    super();
  }

  /* (non-Javadoc)
   * @see org.hibernate.id.IdentifierGenerator#generate(org.hibernate.engine.SessionImplementor, java.lang.Object)
   */
  public Serializable generate(SessionImplementor session, Object entity) throws HibernateException {
    if (StringUtils.isEmpty(this.propertyName)) {
       throw new HibernateException("failure to generate hash id, the source property name is null, object: " + entity + ", property: " + this.propertyName);
    }
    try {
        Object propertyValue = BeanUtils.getProperty(entity, this.propertyName);
        if (propertyValue == null || StringUtils.isEmpty(propertyValue.toString())) {
          throw new HibernateException("failure to generate hash id, the source property is enpty, object: " + entity + ", " + this.propertyName + "=" + propertyValue);
        } else {
          return new Long(propertyValue.toString().hashCode());
        }
    } catch (IllegalAccessException e) {
      throw new HibernateException("failure to generate a identifier.", e);
    } catch (InvocationTargetException e) {
      throw new HibernateException("failure to generate a identifier.", e);
    } catch (NoSuchMethodException e) {
      throw new HibernateException("failure to generate a identifier.", e);
    }
  }

  /* (non-Javadoc)
   * @see org.hibernate.id.Configurable#configure(org.hibernate.type.Type, java.util.Properties, org.hibernate.dialect.Dialect)
   */
  public void configure(Type type, Properties params, Dialect Dialect) throws MappingException {
    this.propertyName = params.getProperty("property");
  }

}
