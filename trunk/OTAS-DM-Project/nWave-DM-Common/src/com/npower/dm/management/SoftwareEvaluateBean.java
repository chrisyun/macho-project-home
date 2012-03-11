/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/SoftwareEvaluateBean.java,v 1.3 2008/09/03 09:09:19 chenlei Exp $
 * $Revision: 1.3 $
 * $Date: 2008/09/03 09:09:19 $
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

package com.npower.dm.management;

import java.util.Collection;
import java.util.Date;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Software;
import com.npower.dm.hibernate.entity.SoftwareEvaluateEntity;

/**
 * @author chenlei
 * @version $Revision: 1.3 $ $Date: 2008/09/03 09:09:19 $
 */

public interface SoftwareEvaluateBean {
  
  /**
   * save SoftwareEvaluate
   * @param softwareEvaluateEntity
   */
  public abstract void save(SoftwareEvaluateEntity softwareEvaluateEntity) throws DMException;
  
  /**
   * @return
   * @throws DMException
   */
  public abstract SoftwareEvaluateEntity newSoftwareEvaluateInstance() throws DMException;
  
  /**
   * @param software
   * @param grade
   * @param createdTime
   * @return
   * @throws DMException
   */
  public abstract SoftwareEvaluateEntity newSoftwareEvaluateInstance(Software software, long grade, Date createdTime) throws DMException;
  
  /**
   * @param software
   * @param grade
   * @return
   * @throws DMException
   */
  public abstract SoftwareEvaluateEntity newSoftwareEvaluateInstance(Software software, long grade) throws DMException;

  /**
   * 获取此Software的全部SoftwareEvaluate
   * @param software
   * @return
   * @throws DMException
   */
  public abstract Collection<SoftwareEvaluateEntity> getAllOfSoftwareEvaluate(Software software) throws DMException;
  
  /**
   * 按id取SoftwareEvaluate
   * @param id
   * @return
   * @throws DMException
   */
  public abstract SoftwareEvaluateEntity getSoftwareEvaluateByID(Long id) throws DMException;
  
  /**
   * delete SoftwareEvaluate
   * @throws DMException
   */
  public abstract void delete(SoftwareEvaluateEntity softwareEvaluateEntity) throws DMException;

}
