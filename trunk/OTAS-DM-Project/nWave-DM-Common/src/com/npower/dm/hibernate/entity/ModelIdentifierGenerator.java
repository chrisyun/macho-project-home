/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/ModelIdentifierGenerator.java,v 1.1 2008/05/08 06:54:26 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/05/08 06:54:26 $
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
package com.npower.dm.hibernate.entity;

import java.io.Serializable;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.type.Type;

import com.npower.dm.core.Model;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/05/08 06:54:26 $
 */
public class ModelIdentifierGenerator implements IdentifierGenerator, Configurable {

  /**
   * 
   */
  public ModelIdentifierGenerator() {
    super();
  }

  /* (non-Javadoc)
   * @see org.hibernate.id.IdentifierGenerator#generate(org.hibernate.engine.SessionImplementor, java.lang.Object)
   */
  public Serializable generate(SessionImplementor session, Object entity) throws HibernateException {
    if (!(entity instanceof Model)) {
       throw new HibernateException("Entity not implements Model interface.");
    }
    Model model = (Model)entity;
    if (model.getManufacturer() == null || StringUtils.isEmpty(model.getManufacturer().getExternalId())) {
       throw new HibernateException("Could not generate a identifier, missing manufacturer.");
    }
    if (StringUtils.isEmpty(model.getManufacturerModelId())) {
       throw new HibernateException("Could not generate a identifier, missing model externalID.");
    }
    
    StringBuffer buf = new StringBuffer(model.getManufacturer().getExternalId().toLowerCase().trim());
    buf.append("_");
    buf.append(model.getManufacturerModelId().toLowerCase().trim());
    int id = (int)buf.toString().hashCode();
    // Keep > 0, unsigned number
    if (id < 0) {
       id = Integer.MAX_VALUE + id;
    }
    return new Long(id);
  }

  /* (non-Javadoc)
   * @see org.hibernate.id.Configurable#configure(org.hibernate.type.Type, java.util.Properties, org.hibernate.dialect.Dialect)
   */
  public void configure(Type arg0, Properties arg1, Dialect arg2) throws MappingException {
  }

}
