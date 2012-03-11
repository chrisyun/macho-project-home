/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/digester/ModelItemObjectCreationFactory.java,v 1.1 2008/09/05 03:24:40 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/09/05 03:24:40 $
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
package com.npower.dm.setup.task.digester;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.ObjectCreationFactory;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.Attributes;

import com.npower.dm.setup.task.FirmwareItem;
import com.npower.dm.setup.task.ModelFamilyItem;
import com.npower.dm.setup.task.ModelFamilyManager;
import com.npower.dm.setup.task.ModelItem;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/09/05 03:24:40 $
 */
public class ModelItemObjectCreationFactory implements ObjectCreationFactory {

  private Digester digester = null;

  /**
   * 
   */
  public ModelItemObjectCreationFactory() {
    super();
  }

  /* (non-Javadoc)
   * @see org.apache.commons.digester.ObjectCreationFactory#createObject(org.xml.sax.Attributes)
   */
  public Object createObject(Attributes attributes) throws Exception {
    ModelItem result = new ModelItem();

    String familyID = attributes.getValue("family");
    if (StringUtils.isNotEmpty(familyID)) {
       familyID = familyID.trim();
       ModelFamilyManager manager = ModelFamilyManager.getInstance();
       ModelFamilyItem family = manager.findModelFamily(familyID);
       if (family != null) {
          PropertyUtils.copyProperties(result, family);
          
          List<String> cpTemplatesFiles = new ArrayList<String>();
          cpTemplatesFiles.addAll(family.getCpTemplatesFiles());
          result.setCpTemplatesFiles(cpTemplatesFiles);
          
          List<String> ddfFiles = new ArrayList<String>();
          ddfFiles.addAll(family.getDdfFiles());
          result.setDdfFiles(ddfFiles);
          
          List<FirmwareItem> firmwares = new ArrayList<FirmwareItem>();
          firmwares.addAll(family.getFirmwares());
          result.setFirmwares(firmwares);
          
          List<String> profileMappingFiles = new ArrayList<String>();
          profileMappingFiles.addAll(family.getProfileMappingFiles());
          result.setProfileMappingFiles(profileMappingFiles);
          
          Map<String, String> specifications = new LinkedHashMap<String, String>();
          specifications.putAll(family.getSpecifications());
          result.setSpecifications(specifications);
          
          List<String> tacs = new ArrayList<String>();
          tacs.addAll(family.getTacs());
          result.setTacs(tacs);
       }
       result.setFamilyID(familyID);
    }
    return result;
  }

  /* (non-Javadoc)
   * @see org.apache.commons.digester.ObjectCreationFactory#getDigester()
   */
  public Digester getDigester() {
    return this.digester;
  }

  /* (non-Javadoc)
   * @see org.apache.commons.digester.ObjectCreationFactory#setDigester(org.apache.commons.digester.Digester)
   */
  public void setDigester(Digester digester) {
    this.digester = digester;
  }
  
}
