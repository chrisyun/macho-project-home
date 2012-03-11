/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/ExportReportDAO.java,v 1.2 2008/11/04 09:32:51 chenlei Exp $
 * $Revision: 1.2 $
 * $Date: 2008/11/04 09:32:51 $
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

package com.npower.unicom;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import com.npower.dm.core.DMException;
import com.npower.dm.hibernate.management.AbstractBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SearchBean;

/**
 * @author chenlei
 * @version $Revision: 1.2 $ $Date: 2008/11/04 09:32:51 $
 */

public class ExportReportDAO extends AbstractBean{
  
  public ExportReportDAO(ManagementBeanFactory factory, Session hsession) {
    super(factory, hsession);
  }
  
  public List<VCPCDRLog> getReport4Time(String startTime, String endTime) throws DMException {
    ManagementBeanFactory factory = this.getManagementBeanFactory();
    SearchBean bean = factory.createSearchBean();
    Criteria criteria = bean.newCriteriaInstance(VCPCDRLog.class);
    
    criteria.add(Expression.eq("startTime", startTime));
    criteria.add(Expression.eq("endTime", endTime));
    List<VCPCDRLog> result = criteria.list();
    return result;
  }
}
