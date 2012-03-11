/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/DMCommand.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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

import java.sql.Blob;
import java.util.Set;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/04/25 16:31:09 $
 */
public class DMCommand extends AbstractDMCommand implements java.io.Serializable {

  // Constructors

  /** default constructor */
  public DMCommand() {
  }

  /** minimal constructor */
  public DMCommand(String cmdType) {
    super(cmdType);
  }

  /** full constructor */
  public DMCommand(DMCommandPackage nwDmDmCmdPkg, DMCommand nwDmDmCmd, String cmdType, String name, String target,
      String MFormat, String MType, String itemDataKind, String updateId, String rawData, Blob binaryData,
      String alertCode, long cmdIndex, Set nwDmReplCmdItems, Set nwDmDmCmds, Set nwDmGetCmdItems) {
    super(nwDmDmCmdPkg, nwDmDmCmd, cmdType, name, target, MFormat, MType, itemDataKind, updateId, rawData, binaryData,
        alertCode, cmdIndex, nwDmReplCmdItems, nwDmDmCmds, nwDmGetCmdItems);
  }

}
