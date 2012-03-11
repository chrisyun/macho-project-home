/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/JobAssignments.java,v 1.4 2008/12/10 05:24:20 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2008/12/10 05:24:20 $
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

import com.npower.dm.core.ProfileAssignment;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/12/10 05:24:20 $
 */
public class JobAssignments extends AbstractJobAssignments implements java.io.Serializable, Comparable<JobAssignments> {

  // Constructors

  /** default constructor */
  public JobAssignments() {
  }

  /** full constructor */
  public JobAssignments(JobAssignmentsID id, ProfileAssignment nwDmProfileAssignment, JobState nwDmJobState) {
    super(id, nwDmProfileAssignment, nwDmJobState);
  }

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(T)
   */
  public int compareTo(JobAssignments o) {
    if (o != null && o instanceof AbstractJobAssignments) {
       AbstractJobAssignments other = (AbstractJobAssignments)o;
       return (int)(this.getID().getAssignmentIndex() - other.getID().getAssignmentIndex());
    }
    return -1;
  }

}
