/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/Node4DiscoveryJob.java,v 1.4 2006/04/27 01:24:37 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2006/04/27 01:24:37 $
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
 * @version $Revision: 1.4 $ $Date: 2006/04/27 01:24:37 $
 */
public class Node4DiscoveryJob extends AbstractNode4DiscoveryJob implements java.io.Serializable, Comparable {

  // Constructors

  /** default constructor */
  public Node4DiscoveryJob() {
    super();
  }

  /** full constructor */
  public Node4DiscoveryJob(ProvisionRequest provReq, String rootNodePath) {
    super(provReq, rootNodePath);
  }
  
  // Implements Comparable interface *************************************************************************
  public int compareTo(Object other) {
    if (other == null) {
       return 1;
    }
    if (other instanceof AbstractNode4DiscoveryJob) {
       return this.getID().getRootNode().compareToIgnoreCase(((AbstractNode4DiscoveryJob)other).getID().getRootNode());
    } else {
      return 1;
    }
  }

}
