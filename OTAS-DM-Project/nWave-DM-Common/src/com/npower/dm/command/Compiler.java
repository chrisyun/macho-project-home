/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/command/Compiler.java,v 1.12 2007/08/29 06:21:00 zhao Exp $
 * $Revision: 1.12 $
 * $Date: 2007/08/29 06:21:00 $
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
package com.npower.dm.command;

import java.util.List;

import sync4j.framework.engine.dm.ManagementOperation;

import com.npower.dm.core.DMException;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.12 $ $Date: 2007/08/29 06:21:00 $
 */
public interface Compiler {

  /**
   * Verify the command script.
   * 
   * @throws DMException
   */
  public abstract boolean verify() throws DMException;

  /**
   * Return an array of Operations.<br>
   * The instances of operation are one of sync4j.framework.engine.dm.XXXXOperation.
   * <br>
   * More information about Operation see also sync4j.framework.engine.dm.XXXXOperation.
   * <br>
   * @return
   */
  public abstract List<ManagementOperation> compile() throws DMException;

}