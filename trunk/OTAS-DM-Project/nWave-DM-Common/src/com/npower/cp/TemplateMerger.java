/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/cp/TemplateMerger.java,v 1.6 2008/10/29 03:19:41 zhao Exp $
 * $Revision: 1.6 $
 * $Date: 2008/10/29 03:19:41 $
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
package com.npower.cp;

import com.npower.dm.core.ClientProvTemplate;
import com.npower.dm.core.ProfileConfig;
import com.npower.wap.nokia.NokiaOTASettings;
import com.npower.wap.omacp.OMAClientProvSettings;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2008/10/29 03:19:41 $
 */
public interface TemplateMerger {

  /**
   * Merge this template with the settings.
   * @param template
   * @param settings
   * @return
   * @throws OTAException
   */
  public abstract ProvisioningDoc merge(ClientProvTemplate template, ProfileConfig settings)
      throws OTAException;

  /**
   * @param settings
   * @param templateID
   * @param templateContent
   * @return
   * @throws OTAException
   */
  public abstract ProvisioningDoc merge(String templateContent, ProfileConfig settings) throws OTAException;

  /**
   * Merge this template with the settings.
   * @param template
   * @param settings
   * @return
   * @throws OTAException
   */
  public abstract ProvisioningDoc merge(ClientProvTemplate template, OMAClientProvSettings settings)
      throws OTAException;

  /**
   * @param settings
   * @param templateID
   * @param templateContent
   * @return
   * @throws OTAException
   */
  public abstract ProvisioningDoc merge(String templateContent, OMAClientProvSettings settings) throws OTAException;

  /**
   * Merge this template with the settings.
   * @param template
   * @param settings
   * @return
   * @throws OTAException
   */
  public abstract ProvisioningDoc merge(ClientProvTemplate template, NokiaOTASettings settings)
      throws OTAException;

  /**
   * @param settings
   * @param templateID
   * @param templateContent
   * @return
   * @throws OTAException
   */
  public abstract ProvisioningDoc merge(String templateContent, NokiaOTASettings settings) throws OTAException;

}