/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/audit/ClientProvTemplateAuditLogger.java,v 1.1 2009/02/19 10:29:54 zhaowx Exp $
 * $Revision: 1.1 $
 * $Date: 2009/02/19 10:29:54 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2009 NPower Network Software Ltd.  All rights reserved.
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

package com.npower.dm.audit;

import com.npower.dm.audit.action.ClientProvTemplateAction;



/**
 * @author Zhao wanxiang
 * @version $Revision: 1.1 $ $Date: 2009/02/19 10:29:54 $
 */

public interface ClientProvTemplateAuditLogger extends AuditLogger{

  public abstract long log(ClientProvTemplateAction action, 
      String username, 
      String ipAddress,
      long templateID,
      String templateName,
      String description) throws AuditException;

}
