/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/SoftwarePackageItem.java,v 1.2 2008/12/16 04:19:41 chenlei Exp $
 * $Revision: 1.2 $
 * $Date: 2008/12/16 04:19:41 $
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

package com.npower.dm.setup.task;

import java.util.ArrayList;
import java.util.List;


/**
 * @author chenlei
 * @version $Revision: 1.2 $ $Date: 2008/12/16 04:19:41 $
 */

public class SoftwarePackageItem {
  private String name;
  private SoftwarePackage4RemoteItem remote;
  private SoftwarePackage4LocalItem local;
  private List<SoftwarePackage4InstallOptionsItem> installOptions = new ArrayList<SoftwarePackage4InstallOptionsItem>();
  private List<SoftwarePackage4TargetModelsItem> models = new ArrayList<SoftwarePackage4TargetModelsItem>();
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public SoftwarePackage4RemoteItem getRemote() {
    return remote;
  }
  public void setRemote(SoftwarePackage4RemoteItem remote) {
    this.remote = remote;
  }
  public SoftwarePackage4LocalItem getLocal() {
    return local;
  }
  public void setLocal(SoftwarePackage4LocalItem local) {
    this.local = local;
  }
  public List<SoftwarePackage4InstallOptionsItem> getInstallOptions() {
    return installOptions;
  }
  public void addInstallOptions(SoftwarePackage4InstallOptionsItem installOptions) {
    this.installOptions.add(installOptions);
  }
  public List<SoftwarePackage4TargetModelsItem> getModels() {
    return models;
  }
  public void addModels(SoftwarePackage4TargetModelsItem models) {
    this.models.add(models);
  }
}
