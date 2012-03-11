/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/JobState.java,v 1.4 2006/05/15 06:18:06 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2006/05/15 06:18:06 $
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


/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2006/05/15 06:18:06 $
 */
public class JobState extends AbstractJobState implements java.io.Serializable {

  // Constructors

  /** default constructor */
  public JobState() {
  }

  /** minimal constructor */
  public JobState(String jobStateType) {
    super(jobStateType);
  }

}
