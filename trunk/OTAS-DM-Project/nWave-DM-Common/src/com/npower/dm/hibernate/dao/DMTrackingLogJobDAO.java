/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/dao/DMTrackingLogJobDAO.java,v 1.1 2008/08/04 10:21:31 chenlei Exp $
 * $Revision: 1.1 $
 * $Date: 2008/08/04 10:21:31 $
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

package com.npower.dm.hibernate.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.npower.dm.hibernate.entity.DmTrackingLogJob;

/**
 * @author chenlei
 * @version $Revision: 1.1 $ $Date: 2008/08/04 10:21:31 $
 */

public class DMTrackingLogJobDAO extends BaseHibernateDAO {

  private static Log log = LogFactory.getLog(DMTrackingLogJobDAO.class);
  
  /**
   * @param dmtrackinglogjob
   */
  public void save(DmTrackingLogJob dmtrackinglogjob)  {
    log.debug("saving DmTrackingLogJobEntity instance");
    try {
      getSession().save(dmtrackinglogjob);
      log.debug("save successful");
    } catch (RuntimeException e) {
      log.error("save failed", e);
      throw e;
    }
  }

  
  /**
   * @param dmtrackinglogjob
   */
  public void delete(DmTrackingLogJob dmtrackinglogjob) {
    log.debug("deleting DmTrackingLogJobEntity instance");
    try {
      getSession().delete(dmtrackinglogjob);
      log.debug("delete successful");
    } catch (RuntimeException re) {
      log.error("delete failed", re);
      throw re;
    }
  }
  
  
  /**
   * @param id
   * @return
   */
  public DmTrackingLogJob findById(Long id) {
    log.debug("getting DmTrackingLogJobEntity instance with id: " + id);
    try {
      DmTrackingLogJob instance = (DmTrackingLogJob) getSession().get(DmTrackingLogJob.class.getName(), id);
      return instance;
    } catch (RuntimeException re) {
      log.error("get failed", re);
      throw re;
    }
  }
}
