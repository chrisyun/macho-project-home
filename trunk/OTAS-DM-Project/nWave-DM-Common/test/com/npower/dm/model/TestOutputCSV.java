/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/model/TestOutputCSV.java,v 1.4 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.4 $
  * $Date: 2008/06/16 10:11:15 $
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
package com.npower.dm.model;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import junit.framework.TestCase;

import com.npower.dm.AllTests;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ModelCharacter;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/06/16 10:11:15 $
 */
public class TestOutputCSV extends TestCase {

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    System.setProperty("otas.dm.home", "D:/OTASDM");
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testOutputCSV() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ModelBean bean = factory.createModelBean();
    
    List<Manufacturer> mans = bean.getAllManufacturers();
    int i = 1;
    for (Manufacturer manufacturer: mans) {
        Set<Model> models = new TreeSet<Model>();
        models.addAll(manufacturer.getModels());
        for (Model model: models) {
            System.out.println(i + "," + model.getManufacturer().getExternalId() + "," + model.getName() + "," + model.getManufacturerModelId());
            i++;
        }
    }
  }

  public void testOutputCSV4ZTE() throws Exception {
    String[][] data = new String[][]{
        {"LG", "KU800"},
        {"LG", "LG-KE970"},
        {"Motorola", "A732"},
        {"Motorola", "E1"},
        {"Motorola", "E6"},
        {"Motorola", "KRZRK1"},
        {"Motorola", "KRZRK3"},
        {"Motorola", "L6"},
        {"Motorola", "SLVRL7"},
        {"Motorola", "V3"},
        {"Motorola", "RAZRMAXV6"},
        {"Motorola", "PEBLU6"},
        {"Motorola", "V191"},
        {"Motorola", "V360"},
        {"Motorola", "V3i"},
        {"Motorola", "V3x"},
        {"Motorola", "V9"},
        {"Motorola", "RIZRZ3"},
        {"Nokia", "2610"},
        {"Nokia", "2626"},
        {"Nokia", "2630"},
        {"Nokia", "2650"},
        {"Nokia", "2760"},
        {"Nokia", "3100"},
        {"Nokia", "3108"},
        {"Nokia", "3110c"},
        {"Nokia", "3120"},
        {"Nokia", "3200"},
        {"Nokia", "3230"},
        {"Nokia", "3250"},
        {"Nokia", "3300"},
        {"Nokia", "3500"},
        {"Nokia", "3530"},
        {"Nokia", "3650"},
        {"Nokia", "3660"},
        {"Nokia", "5070"},
        {"Nokia", "5140"},
        {"Nokia", "5200"},
        {"Nokia", "5300"},
        {"Nokia", "5310"},
        {"Nokia", "5500"},
        {"Nokia", "5610"},
        {"Nokia", "5700"},
        {"Nokia", "6020"},
        {"Nokia", "6021"},
        {"Nokia", "6030"},
        {"Nokia", "6060"},
        {"Nokia", "6060"},
        {"Nokia", "6070"},
        {"Nokia", "6080"},
        {"Nokia", "6085"},
        {"Nokia", "6100"},
        {"Nokia", "6101"},
        {"Nokia", "6103"},
        {"Nokia", "6108"},
        {"Nokia", "6110"},
        {"Nokia", "6111"},
        {"Nokia", "6120c"},
        {"Nokia", "6125"},
        {"Nokia", "6131"},
        {"Nokia", "6151"},
        {"Nokia", "6170"},
        {"Nokia", "6220"},
        {"Nokia", "6230"},
        {"Nokia", "6230i"},
        {"Nokia", "6233"},
        {"Nokia", "6260"},
        {"Nokia", "6270"},
        {"Nokia", "6280"},
        {"Nokia", "6288"},
        {"Nokia", "6290"},
        {"Nokia", "6300"},
        {"Nokia", "6500c"},
        {"Nokia", "6500s"},
        {"Nokia", "6600"},
        {"Nokia", "6610"},
        {"Nokia", "6630"},
        {"Nokia", "6650"},
        {"Nokia", "6670"},
        {"Nokia", "6680"},
        {"Nokia", "6820"},
        {"Nokia", "7200"},
        {"Nokia", "7210"},
        {"Nokia", "7250"},
        {"Nokia", "7250I"},
        {"Nokia", "7270"},
        {"Nokia", "7280"},
        {"Nokia", "7360"},
        {"Nokia", "7370"},
        {"Nokia", "7373"},
        {"Nokia", "7380"},
        {"Nokia", "7390"},
        {"Nokia", "7500"},
        {"Nokia", "7600"},
        {"Nokia", "7610"},
        {"Nokia", "7650"},
        {"Nokia", "7900 Prism"},
        {"Nokia", "8600"},
        {"Nokia", "8800"},
        {"Nokia", "8800arte"},
        {"Nokia", "8800Scirocco"},
        {"Nokia", "E50"},
        {"Nokia", "E60"},
        {"Nokia", "E61"},
        {"Nokia", "E61i"},
        {"Nokia", "E65"},
        {"Nokia", "E70"},
        {"Nokia", "N-Gage"},
        {"Nokia", "N-Gage QD"},
        {"Nokia", "N70"},
        {"Nokia", "N71"},
        {"Nokia", "N73"},
        {"Nokia", "N76"},
        {"Nokia", "N80"},
        {"Nokia", "N81"},
        {"Nokia", "N82"},
        {"Nokia", "N90"},
        {"Nokia", "N91"},
        {"Nokia", "N93"},
        {"Nokia", "N93i"},
        {"Nokia", "N95"},
        {"Nokia", "N95_8GB-1"},
        {"Others", "Porsche  P9521"},
        {"Panasonic", "VS3"},
        {"Panasonic", "VS6"},
        {"Panasonic", "VS7"},
        {"Panasonic", "X800"},
        {"Samsung", "D830"},
        {"Samsung", "D900"},
        {"Samsung", "E900"},
        {"Samsung", "F258"},
        {"Samsung", "F308"},
        {"Samsung", "i458"},
        {"Samsung", "SGH-F338"},
        {"Samsung", "SGH-F508"},
        {"Samsung", "G600"},
        {"Samsung", "L760v"},
        {"Samsung", "U708"},
        {"Samsung", "U608"},
        {"Samsung", "X838"},
        {"Samsung", "Z720"},
        {"Sharp", "813"},
        {"Siemens", "M55"},
        {"Siemens", "S57"},
        {"Siemens", "SL55"},
        {"SonyEricsson", "J200i"},
        {"SonyEricsson", "J210i"},
        {"SonyEricsson", "J230i"},
        {"SonyEricsson", "K300i"},
        {"SonyEricsson", "K310c"},
        {"SonyEricsson", "K500i"},
        {"SonyEricsson", "K508c"},
        {"SonyEricsson", "K510i"},
        {"SonyEricsson", "K530"},
        {"SonyEricsson", "K550i"},
        {"SonyEricsson", "K600i"},
        {"SonyEricsson", "K608i"},
        {"SonyEricsson", "K610i"},
        {"SonyEricsson", "K700i"},
        {"SonyEricsson", "K750i"},
        {"SonyEricsson", "K770"},
        {"SonyEricsson", "K800i"},
        {"SonyEricsson", "K810i"},
        {"SonyEricsson", "K850i"},
        {"SonyEricsson", "M600i"},
        {"SonyEricsson", "P1i"},
        {"SonyEricsson", "P800"},
        {"SonyEricsson", "P900"},
        {"SonyEricsson", "P910i"},
        {"SonyEricsson", "P990i"},
        {"SonyEricsson", "S500i"},
        {"SonyEricsson", "S700i"},
        {"SonyEricsson", "T230"},
        {"SonyEricsson", "T300"},
        {"SonyEricsson", "T310"},
        {"SonyEricsson", "T610"},
        {"SonyEricsson", "T630"},
        {"SonyEricsson", "T650i"},
        {"SonyEricsson", "T68i"},
        {"SonyEricsson", "W300i"},
        {"SonyEricsson", "W550i"},
        {"SonyEricsson", "W580i"},
        {"SonyEricsson", "W610i"},
        {"SonyEricsson", "W660i"},
        {"SonyEricsson", "W700i"},
        {"SonyEricsson", "W710i"},
        {"SonyEricsson", "W810c"},
        {"SonyEricsson", "W810i"},
        {"SonyEricsson", "W850i"},
        {"SonyEricsson", "W880i"},
        {"SonyEricsson", "W890i"},
        {"SonyEricsson", "W900i"},
        {"SonyEricsson", "W910i"},
        {"SonyEricsson", "W950i"},
        {"SonyEricsson", "W960i"},
        {"SonyEricsson", "Z1010"},
        {"SonyEricsson", "Z200"},
        {"SonyEricsson", "Z520i"},
        {"SonyEricsson", "Z530i"},
        {"SonyEricsson", "Z550i"},
        {"SonyEricsson", "Z558i"},
        {"SonyEricsson", "Z600"},
        {"SonyEricsson", "Z610i"},
        {"SonyEricsson", "Z710i"},
        {"SonyEricsson", "Z800i"}
        };

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ModelBean bean = factory.createModelBean();
    
    for (String[] item: data) {
        String brandName = item[0];
        String modelName = item[1];
        Manufacturer manufacturer = bean.getManufacturerByExternalID(brandName);
        if (manufacturer == null) {
           System.err.println("Could not found brand: " + brandName);
           continue;
        }
        
        Model model = bean.getModelByManufacturerModelID(manufacturer, modelName);
        if (model == null) {
           System.err.println("Could not found model: " + brandName + " " + modelName);
           continue;
        }
        
        bean.findModelCharacters(model, "", "");
        System.out.println("" + model.getManufacturer().getExternalId() + "\t" + 
                           model.getManufacturerModelId() + "\t" + 
                           getCharacter(bean, model, "general", "developer.platform") + "\t" + 
                           getCharacter(bean, model, "general", "operating.system") + "\t" + 
                           getCharacter(bean, model, "general", "announced") + "");
    }
    
  }

  /**
   * @param specBean
   * @param spec
   * @param category
   * @param name
   * @return
   * @throws DMException
   */
  private String getCharacter(ModelBean bean, Model model, String category, String name)
      throws DMException {
    List<ModelCharacter> result = bean.findModelCharacters(model, category, name);
    String value = "";
    if (result != null && result.size() > 0) {
       ModelCharacter character = result.get(0);
       value = character.getValue();
    }
    return (value == null)?"":value;
  }

}
