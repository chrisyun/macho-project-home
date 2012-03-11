/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/el/ELDocument.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall.el;

import javax.servlet.jsp.JspException;


public class ELDocument extends com.npower.wall.WallDocument {

    /**
   * 
   */
  private static final long serialVersionUID = 1L;
    private String disable_wml_extensionsExpr;
    private String disable_xhtml_extensionsExpr;
    private String disable_cacheExpr;
    private String disable_content_type_generationExpr;


    @Override
    public int doStartTag() throws JspException {

        evaluateExpressions();	
	return super.doStartTag();
    }

    //attribute Setters
    @Override
    public void setDisable_wml_extensions(String disable_wml_extensions) {
	this.disable_wml_extensionsExpr = disable_wml_extensions;
    }
    @Override
    public void setDisable_xhtml_extensions(String disable_xhtml_extensions) {
	this.disable_xhtml_extensionsExpr = disable_xhtml_extensions;
    }
    public void setDisable_disable_cache(String disable_cache) {
	this.disable_cacheExpr = disable_cache;
    }
    @Override
    public void setDisable_content_type_generation(String disable_content_type_generation) {
	this.disable_content_type_generationExpr = disable_content_type_generation;
    }


    private void evaluateExpressions() throws JspException
    {
        ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);

        if (disable_wml_extensionsExpr != null) {
            super.setDisable_wml_extensions(eval.evalBoolean("disable_wml_extensions", disable_wml_extensionsExpr));
        }
        if (disable_xhtml_extensionsExpr != null) {
            super.setDisable_xhtml_extensions(eval.evalBoolean("disable_xhtml_extensions", disable_xhtml_extensionsExpr));
        }
        if (disable_cacheExpr != null) {
            super.setDisable_cache(eval.evalBoolean("disable_cache", disable_cacheExpr));
        }
        if (disable_content_type_generationExpr != null) {
            super.setDisable_content_type_generation(eval.evalBoolean("disable_content_type_generation", disable_content_type_generationExpr));
        }
 
    }

    @Override
    public void release() {
        super.release();
        this.disable_wml_extensionsExpr = null;
        this.disable_xhtml_extensionsExpr = null;
	this.disable_cacheExpr = null;
	this.disable_content_type_generationExpr = null;
    }


}
