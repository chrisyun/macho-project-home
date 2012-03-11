/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/DMSession.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2006/04/25 16:31:09 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
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

import java.util.Set;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/04/25 16:31:09 $
 */
public class DMSession extends AbstractDMSession implements java.io.Serializable {

  // Constructors

  /** default constructor */
  public DMSession() {
  }

  /** minimal constructor */
  public DMSession(String curMsgIdProc) {
    super(curMsgIdProc);
  }

  /** full constructor */
  public DMSession(DMPackageHandler nwDmDmPkgHandler, SessionAuth nwDmSessionAuth, DMCommandSession nwDmDmCmdSession,
      String externalId, long isClientAuthenticated, long isServerAuthenticated, long isServerUsingHmac,
      String serverAuthType, long maxMsgSize, long maxObjSize, long numServerAuthAttempts, String deviceExternalId,
      String curServerNonce, String curClientNonce, long msgIdCounter, String curMsgIdProc, Set nwDmSessionAuths,
      Set nwDmDmPkgHandlers, Set nwDmPrevPkgResps) {
    super(nwDmDmPkgHandler, nwDmSessionAuth, nwDmDmCmdSession, externalId, isClientAuthenticated,
        isServerAuthenticated, isServerUsingHmac, serverAuthType, maxMsgSize, maxObjSize, numServerAuthAttempts,
        deviceExternalId, curServerNonce, curClientNonce, msgIdCounter, curMsgIdProc, nwDmSessionAuths,
        nwDmDmPkgHandlers, nwDmPrevPkgResps);
  }

}
