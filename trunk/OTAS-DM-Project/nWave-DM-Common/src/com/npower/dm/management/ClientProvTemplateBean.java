/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/ClientProvTemplateBean.java,v 1.2 2007/05/28 05:42:56 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2007/05/28 05:42:56 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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

import com.npower.dm.core.ClientProvTemplate;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileCategory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/05/28 05:42:56 $
 */
public interface ClientProvTemplateBean extends BaseBean {
  
  /**
   * Get ClientProvTemplate by ID
   * @param id
   * @return
   * @throws DMException
   */
  public ClientProvTemplate getTemplate(String id) throws DMException;
  
  /**
   * Create an instance of ClientProvTemplate.
   * @return
   * @throws DMException
   */
  public ClientProvTemplate newClientProvTemplateInstance() throws DMException;
  
  /**
   * Add a ClientProvTemplate into DM repository
   * @param template
   * @throws DMException
   */
  //public void add(ClientProvTemplate template) throws DMException;
  
  /**
   * Update or add a ClientProvTemplate into DM repository.
   * @param template
   * @throws DMException
   */
  public void update(ClientProvTemplate template) throws DMException;
  
  /**
   * Delete a ClientProvTemplate from DM repository.
   * @param template
   * @throws DMException
   */
  public void delete(ClientProvTemplate template) throws DMException;
  
  /**
   * Find all of ClientProvTemplate of the model.
   * @param model
   * @throws DMException
   */
  public List<ClientProvTemplate> findTemplates(Model model) throws DMException;
  
  /**
   * Find a ClientProvTemplate by model and category
   * @param model
   * @param category
   * @return
   * @throws DMException
   */
  public ClientProvTemplate findTemplate(Model model, ProfileCategory category) throws DMException;
  
  /**
   * Attach a ClientProvTemplate to model
   * @param model
   * @param template
   * @throws DMException
   */
  public void attach(Model model, ClientProvTemplate template) throws DMException;
  
  /**
   * Dettach a ClientProvTemplate from model, the template will not be deleted.
   * @param model
   * @param template
   * @throws DMException
   */
  public void dettach(Model model, ClientProvTemplate template) throws DMException;

  /**
   * Import ClientProvTemplates into DM repository.
   * @param in
   * @param relativeBaseDir
   *        Base directory for relative file path in templates definition.
   * @return
   * @throws DMException
   */
  public List<ClientProvTemplate> importClientProvTemplates(InputStream in, String relativeBaseDir) throws DMException;}
