/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/ProvisionRequest.java,v 1.17 2008/07/29 07:17:40 zhao Exp $
 * $Revision: 1.17 $
 * $Date: 2008/07/29 07:17:40 $
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.npower.dm.core.ClientProvJobTargetDevice;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileAssignment;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.ProvisionJobStatus;


/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.17 $ $Date: 2008/07/29 07:17:40 $
 * @param <A>
 */
public class ProvisionRequest extends AbstractProvisionRequest implements java.io.Serializable {

  // Constructors

  /** default constructor */
  public ProvisionRequest() {
    super();
  }

  
  // Implements ProvisionJob methods ****************************************************************
  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJob#isRunning()
   */
  public boolean isRunning() {
    return this.getRunning();
  }



  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJob#getScriptString()
   */
  public String getScriptString() throws DMException {
    if (!ProvisionJob.JOB_TYPE_SCRIPT.equalsIgnoreCase(this.getJobType())) {
       return null;
    }
    return this.getScript();
    /*
    Clob clob = this.getScript();
    if (clob == null) {
       return null;
    }
    try {
        return DMUtil.convertClob2String(clob);
    } catch (SQLException e) {
      throw new DMException("Error in reading Script(Clob)", e);
    } catch (IOException e) {
      throw new DMException("Error in reading Script(Clob)", e);
    }
    */
  }


  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJob#getAllOfProvisionJobStatus()
   */
  public List<ProvisionJobStatus> getAllOfProvisionJobStatus() throws DMException {
    List<ProvisionJobStatus> result = new ArrayList<ProvisionJobStatus>();
    Set<Element4Provision> prElements = this.getProvisionElements();
    for (Element4Provision element: prElements) {
        Set<ProvisionJobStatus> set = element.getDeviceProvReqs();
        for (ProvisionJobStatus status: set) {
            result.add(status);
        }
    }
    return result;
  }


  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJob#getProfileAssignment()
   */
  public List<ProfileAssignment> getProfileAssignments() throws DMException {
    List<ProfileAssignment> result = new ArrayList<ProfileAssignment>();
    Set<JobState> jobStates = this.getJobStates();
    for (JobState jobState: jobStates) {
        Set<JobAssignments> jobAssignmentses = jobState.getJobAssignmentses();
        for (JobAssignments jobAssignments: jobAssignmentses) {
            ProfileAssignment assignment = jobAssignments.getProfileAssignment();
            result.add(assignment);
        }
    }
    return result;
  }


  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJob#isFinished()
   */
  public boolean isFinished() throws DMException {
    if (ProvisionJob.JOB_STATE_FINISHED.equalsIgnoreCase(this.getState())) {
       return true;
    }
    
    // 由于state的完成状态目前已经可以由ProvisionJobManagementBean.update(ProvisionJobStatus)和
    // OTAClientProvJobBean.update(ClientProvJobTargetDevice)完成，
    // 下面程序可以删除，目前保留仅仅为了保证程序的可靠性.
    if (ProvisionJob.JOB_MODE_CP.equalsIgnoreCase(this.getJobMode())) {
       for (ClientProvJobTargetDevice deviceStatus: this.getOtaTargetDevices()) {
           if (!deviceStatus.isFinished()) {
              return false;
           }
       }
       return true;
    } else if (ProvisionJob.JOB_MODE_DM.equalsIgnoreCase(this.getJobMode())) {
      for (ProvisionJobStatus deviceStatus: this.getAllOfProvisionJobStatus()) {
          if (!deviceStatus.isFinished()) {
             return false;
          }
      }
      return true;
    } else {
      return true;
    }
  }


  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo(ProvisionJob other) {
    if (other == null) {
       return 1;
    }
    return (int)(this.getID() - other.getID());
  }
}
