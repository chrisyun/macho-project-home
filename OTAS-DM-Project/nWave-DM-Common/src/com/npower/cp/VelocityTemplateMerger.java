/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/cp/VelocityTemplateMerger.java,v 1.4 2008/10/29 03:19:41 zhao Exp $
  * $Revision: 1.4 $
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

import java.io.StringReader;
import java.io.StringWriter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.dom4j.DocumentException;

import com.npower.dm.core.ClientProvTemplate;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.util.XMLPrettyFormatter;
import com.npower.wap.nokia.NokiaOTASettings;
import com.npower.wap.omacp.OMAClientProvSettings;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/10/29 03:19:41 $
 */
class VelocityTemplateMerger implements TemplateMerger {
  
  private static Log log = LogFactory.getLog(VelocityTemplateMerger.class);

  /**
   * Default constructor
   */
  public VelocityTemplateMerger() {
    super();
  }
  
  /**
   * @param templateID
   * @param templateContent
   * @param settings
   * @return
   * @throws OTAException
   */
  private ProvisioningDoc merge(String templateID, String templateContent, Object settings) throws OTAException {
    if (StringUtils.isEmpty(templateID)) {
       templateID = "Unknown";
    }
    try {
        //Velocity velocity = new Velocity();
        Velocity.init();
        // Any values specified before init() time will replace the default values.
        //velocity.setProperty("resource.loader", "file");
        //velocity.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
        //velocity.setProperty("file.resource.loader.path", System.getProperty("java.io.tmpdir"));
  
        VelocityContext context = new VelocityContext();
        if (settings != null) {
           context.put("profile", settings);
        }
        
        StringWriter out = new StringWriter();
        Velocity.evaluate(context, out, "OTATemplate ID:" + templateID, new StringReader(templateContent));
        // Pretty format XML document
        try {
            XMLPrettyFormatter formatter = new XMLPrettyFormatter(out.getBuffer().toString());
            out = new StringWriter();
            out.write(formatter.format());
        } catch (DocumentException ex) {
          // Ignore this error
          log.error("Error in pretty cp xml message: " + ex.getMessage(), ex);
        }
        
        //String templateFile = this.outputTmpFile(this.getContent());
        //Template veloTemplate = velocity.getTemplate(templateFile, "UTF-8");
        //veloTemplate.merge(context, out);
        ProvisioningDoc doc = new ProvisioningDocImpl(out);
        log.info("Merged CP Template: " + doc.getContent());
        return doc;
    } catch (ResourceNotFoundException e) {
      throw new OTAException(e);
    } catch (ParseErrorException e) {
      throw new OTAException(e);
    } catch (Exception e) {
      throw new OTAException(e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.cp.TemplateMerger#merge(com.npower.dm.core.ClientProvTemplate, com.npower.wap.omacp.OMAClientProvSettings)
   */
  public ProvisioningDoc merge(ClientProvTemplate template, OMAClientProvSettings settings) throws OTAException {
    String templateID = "" + template.getID();
    String templateContent = template.getContent();
    
    return merge(templateID, templateContent, settings);
  }

  /* (non-Javadoc)
   * @see com.npower.cp.TemplateMerger#merge(java.lang.String, com.npower.wap.omacp.OMAClientProvSettings)
   */
  public ProvisioningDoc merge(String templateContent, OMAClientProvSettings settings) throws OTAException {
    return this.merge(null, templateContent, settings);
  }
  
  /* (non-Javadoc)
   * @see com.npower.cp.TemplateMerger#merge(com.npower.dm.core.ClientProvTemplate, com.npower.wap.nokia.NokiaOTASettings)
   */
  public ProvisioningDoc merge(ClientProvTemplate template, NokiaOTASettings settings) throws OTAException {
    String templateID = "" + template.getID();
    String templateContent = template.getContent();
    
    return merge(templateID, templateContent, settings);
  }

  /* (non-Javadoc)
   * @see com.npower.cp.TemplateMerger#merge(java.lang.String, com.npower.wap.nokia.NokiaOTASettings)
   */
  public ProvisioningDoc merge(String templateContent, NokiaOTASettings settings) throws OTAException {
    return this.merge(null, templateContent, settings);
  }

  /* (non-Javadoc)
   * @see com.npower.cp.TemplateMerger#merge(com.npower.dm.core.ClientProvTemplate, com.npower.dm.core.ProfileConfig)
   */
  public ProvisioningDoc merge(ClientProvTemplate template, ProfileConfig settings) throws OTAException {
    String templateID = "" + template.getID();
    String templateContent = template.getContent();
    
    return merge(templateID, templateContent, settings);
  }

  /* (non-Javadoc)
   * @see com.npower.cp.TemplateMerger#merge(java.lang.String, com.npower.dm.core.ProfileConfig)
   */
  public ProvisioningDoc merge(String templateContent, ProfileConfig settings) throws OTAException {
    return this.merge(null, templateContent, settings);
  }
  
  
}
