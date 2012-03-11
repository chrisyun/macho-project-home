/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/SoftwareBean.java,v 1.15 2008/11/09 00:17:13 zhao Exp $
  * $Revision: 1.15 $
  * $Date: 2008/11/09 00:17:13 $
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
package com.npower.dm.management;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.core.ModelClassification;
import com.npower.dm.core.ModelFamily;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.core.SoftwareLicenseType;
import com.npower.dm.core.SoftwarePackage;
import com.npower.dm.core.SoftwareScreenShot;
import com.npower.dm.core.SoftwareVendor;


/**
 * @author Zhao DongLu
 * @version $Revision: 1.15 $ $Date: 2008/11/09 00:17:13 $
 */
public interface SoftwareBean {

  /**
   * Create a instance of SoftwareCategory
   * @return
   * @throws DMException
   */
  public abstract SoftwareCategory newCategoryInstance() throws DMException;
  
  /**
   * Create a instance of SoftwareCategory
   * @param parent
   *        Parent of category, null is root category.
   * @param name
   *        Name of Category
   * @param description
   *        Description information of category
   * @return
   * @throws DMException
   */
  public abstract SoftwareCategory newCategoryInstance(SoftwareCategory parent, String name, String description) throws DMException;
  
  
  /**
   * Get a category by ID from DM Inventory.
   * @param id
   * @return
   */
  public abstract SoftwareCategory getCategoryByID(long id) throws DMException;
  
  /**
   * Update a category into DM Inventory.
   * @param category
   */
  public abstract void update(SoftwareCategory category) throws DMException;
  
  /**
   * Delete a category from DM Inventory.
   * @param category
   */
  public abstract void delete(SoftwareCategory category) throws DMException;
  
  /**
   * Retrieve all of root categories.
   * 
   * @return List Array of SoftwareCategory
   * @throws DMException
   */
  public abstract List<SoftwareCategory> getAllOfRootCategories() throws DMException;
  
  /**
   * Retrieve all of categories.
   * 
   * @return List Array of SoftwareCategory
   * @throws DMException
   */
  public abstract List<SoftwareCategory> getAllOfCategories() throws DMException;
  
  
  /**
   * <pre>
   * 根据路径查找Category对象, 路径按照如下格式：
   *  /category_1_name/category_2_name/category_3_name
   *  
   * 支持如下格式的路径:
   * "/C_1"         :will return null
   * "/C_1/"        :will return null
   * "C_1"          :will return null
   * ""             :will return null
   * "/"            :will return null
   * null           :will return null
   * "/C_1/C_1_1/   :will return null
   * </pre>
   * @param categoryName
   * @return
   * @throws DMException
   */
  public abstract SoftwareCategory  getCategoryByPath(String categoryName) throws DMException;
  
  /**
   * Create a instance of SoftwareVendor
   * @return
   * @throws DMException
   */
  public abstract SoftwareVendor newVendorInstance() throws DMException;
  
  /**
   * Create a instance of SoftwareVendor
   * @param name
   *        Name of Vendor
   * @param description
   *        Description information of Vendor
   * @return
   * @throws DMException
   */
  public abstract SoftwareVendor newVendorInstance(String name, String description) throws DMException;
  
  
  /**
   * Get a vendor by ID from DM Inventory.
   * @param id
   * @return
   */
  public abstract SoftwareVendor getVendorByID(long id) throws DMException;
  
  /**
   * Get a vendor by name from DM Inventory.
   * @param name
   * @return
   * @throws DMException
   */
  public SoftwareVendor getVendorByName(String name) throws DMException;
  
  /**
   * Update a vendor into DM Inventory.
   * @param vendor
   */
  public abstract void update(SoftwareVendor vendor) throws DMException;
  
  /**
   * Delete a vendor from DM Inventory.
   * @param category
   */
  public abstract void delete(SoftwareVendor vendor) throws DMException;
  
  /**
   * Retrieve all of vendors.
   * 
   * @return List Array of SoftwareVendor
   * @throws DMException
   */
  public abstract List<SoftwareVendor> getAllOfVendors() throws DMException;
  
  
  
  /**
   * Create a instance of Software
   * @return
   * @throws DMException
   */
  public abstract Software newSoftwareInstance() throws DMException;
  
  /**
   * Create a instance of Software.
   * 
   * @param vendor
   *        Vendor of software
   * @param category
   *        Category of software
   * @param externalId
   *        External ID
   * @param name
   *        Name of software
   * @param version
   *        Version of software
   * @param licenseType
   *        Type of license.
   * @return
   * @throws DMException
   */
  public abstract Software newSoftwareInstance(SoftwareVendor vendor, 
                                               SoftwareCategory category, 
                                               String externalId, 
                                               String name, 
                                               String version, 
                                               SoftwareLicenseType licenseType) throws DMException;
  
  /**
   * Create a instance of Software.
   * @param vendor
   *        Vendor of software
   * @param categories
   *        Categories of software
   * @param externalId
   *        External ID
   * @param name
   *        Name of software
   * @param version
   *        Version of software
   * @param licenseType
   *        Type of license.
   * @return
   * @throws DMException
   */
  public abstract Software newSoftwareInstance(SoftwareVendor vendor, 
                                               List<SoftwareCategory> categories, 
                                               String externalId, 
                                               String name, 
                                               String version, 
                                               SoftwareLicenseType licenseType) throws DMException;
  
  
  /**
   * Get a software by ID from DM Inventory.
   * @param id
   * @return
   */
  public abstract Software getSoftwareByID(long id) throws DMException;
  
  /**
   * Get a software by ExternalID from DM Inventory.
   * @param id
   * @return
   */
  public abstract Software getSoftwareByExternalID(String extID) throws DMException;
  
  /**
   * Get a software by Software Vendor, Software Name, Software Version from DM Inventory.
   * @param vendorName
   *        The name of software vendor
   * @param softwareName
   *         The name of software
   * @param version
   *        The version of software
   * @return
   * @throws DMException
   */
  public abstract Software getSoftware(String vendorName, String softwareName, String version) throws DMException;
  
  /**
   * Update a software into DM Inventory.
   * @param software
   */
  public abstract void update(Software software) throws DMException;
  
  /**
   * Attach a software to a category.
   * @param software
   */
  public abstract void update(Software software, SoftwareCategory category) throws DMException;
  
  /**
   * Attach a software to categories.
   * @param software
   */
  public abstract void update(Software software, List<SoftwareCategory> categories) throws DMException;
  
  /**
   * Delete a software from DM Inventory.
   * @param category
   */
  public abstract void delete(Software software) throws DMException;
  
  /**
   * Retrieve all of softwares.
   * 
   * @return List Array of Software
   * @throws DMException
   */
  public abstract List<Software> getAllOfSoftwares() throws DMException;
  
   
  /**
   * Create a instance of SoftwarePackage
   * @return
   * @throws DMException
   */
  public abstract SoftwarePackage newPackageInstance() throws DMException;
  
  
  /**
   * Create a instance of SoftwarePackage
   * @param software
   * @param model
   * @param downloadURL
   *        URL to download this package
   * @return
   * @throws DMException
   */
  public abstract SoftwarePackage newPackageInstance(Software software, Set<ModelClassification> classifications, Set<ModelFamily> families, Set<Model> models, String downloadURL) throws DMException;
  
  /**
   * Create a instance of SoftwarePackage
   * @param software
   * @param model
   * @param mimeType
   * @param bytes
   * @param filename
   * @return
   * @throws DMException
   */
  public abstract SoftwarePackage newPackageInstance(Software software, Set<ModelClassification> classifications, Set<ModelFamily> families, Set<Model> models, String mimeType, InputStream bytes, String filename) throws DMException;

  /**
   * Get a SoftwarePackage by ID from DM Inventory.
   * @param id
   * @return
   */
  public abstract SoftwarePackage getPackageByID(long id) throws DMException;
  
  /**
   * Get a SoftwarePackage, which belongs to the model.
   * @param software
   * @param model
   * @return
   * @throws DMException
   */
  public abstract SoftwarePackage getPackage(Software software, Model model) throws DMException;
  
  /**
   * Update a SoftwarePackage into DM Inventory.
   * @param software
   */
  public abstract void update(SoftwarePackage pkg) throws DMException;
  
  /**
   * Delete a package from DM Inventory.
   * @param category
   */
  public abstract void delete(SoftwarePackage pkg) throws DMException;
  
  /**
   * Retrieve all of packages.
   * 
   * @return List Array of package
   * @throws DMException
   */
  public abstract List<SoftwarePackage> getAllOfPackages() throws DMException;
  
  /**
   * 为package指定安装的目标型号和目标型号族. 本操作将查询所有已经存在的目标型号和目标型号族, 仅添加不存在的目标型号和目标型号族.
   * @param models
   * @param pkg
   * @throws DMException
   */
  public abstract void assign(Set<ModelClassification> classifications, Set<Model> models, Set<ModelFamily> families, SoftwarePackage pkg) throws DMException;
  
  /**
   * 为package指定安装的目标型号和目标型号族. 本操作将清除已经存在的目标型号和目标型号族, 用新指定的目标型号和目标型号族集合作为package的目标型号和目标型号族.
   * @param models
   * @param pkg
   * @throws DMException
   */
  public abstract void reassign(Set<ModelClassification> classifications, Set<Model> models, Set<ModelFamily> families, SoftwarePackage pkg) throws DMException;
  
  
  /**
   * 返回能够使用本软件包的所有的型号列表, 对于ModelFamily模式的适配定义, 将返回所有属于该Family的model.
   * @return
   * @throws DMException
   */
  public abstract Set<Model> getTargetModels(SoftwarePackage p) throws DMException;
  
  /**
   * Create a instance of SoftwareScreenShot
   * @return
   * @throws DMException
   */
  public abstract SoftwareScreenShot newScreenShotInstance() throws DMException;
  
  /**
   * Create a instance of SoftwareScreenShot
   * @return
   * @throws DMException
   */
  public abstract SoftwareScreenShot newScreenShotInstance(Software software, InputStream bytes, String description) throws DMException;

  /**
   * Get a SoftwareScreenShot by ID from DM Inventory.
   * @param id
   * @return
   */
  public abstract SoftwareScreenShot getScreenShotByID(long id) throws DMException;
  
  /**
   * Update a SoftwareScreenShot into DM Inventory.
   * @param software
   */
  public abstract void update(SoftwareScreenShot screen) throws DMException;
  
  /**
   * Delete a SoftwareScreenShot from DM Inventory.
   * @param category
   */
  public abstract void delete(SoftwareScreenShot screen) throws DMException;
  
  /**
   * Retrieve all of ScreenShot.
   * 
   * @return List Array of package
   * @throws DMException
   */
  public abstract List<SoftwareScreenShot> getAllOfScreenShots() throws DMException;
  
  /**
   * 计算软件包的下载路径
   * @param software
   * @param model
   * @param serverDownlaodURIPattern
   * @return
   */
  public abstract String getSoftwareDownloadURL(Software software, Model model, String serverDownlaodURIPattern) throws DMException;
  
  /**
   * 计算软件包的下载路径
   * @param pkg
   * @param serverDownlaodURIPattern
   * @return
   * @throws DMException
   */
  public abstract String getSoftwareDownloadURL(SoftwarePackage pkg, String serverDownlaodURIPattern) throws DMException;
 
}
