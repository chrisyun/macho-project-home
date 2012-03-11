/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/el/ELSelect.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall.el;

import javax.servlet.jsp.JspException;


public class ELSelect extends com.npower.wall.WallSelect {

    /**
   * 
   */
  private static final long serialVersionUID = 1L;
    private String disabledExpr;
    private String nameExpr;
    private String sizeExpr;
    private String titleExpr;
    private String multipleExpr;

    @Override
    public int doStartTag() throws JspException {

        evaluateExpressions();	
	return super.doStartTag();
    }

    //attribute Setters
    @Override
    public void setDisabled(String disabled) {
	this.disabledExpr = disabled;
    }
    @Override
    public void setName(String name) {
	this.nameExpr = name;
    }
    @Override
    public void setSize(String size) {
	this.sizeExpr = size;
    }
    @Override
    public void setTitle(String title) {
	this.titleExpr = title;
    }

    @Override
    public void setMultiple(String oi) {
	this.multipleExpr = oi;
    }


    private void evaluateExpressions() throws JspException
    {
        ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);

        if (disabledExpr != null) {
            super.setDisabled(eval.evalString("disabled", disabledExpr));
        }
        if (nameExpr != null) {
            super.setName(eval.evalString("name", nameExpr));
        }
        if (sizeExpr != null) {
            super.setSize(eval.evalString("size", sizeExpr));
        }
        if (titleExpr != null) {
            super.setTitle(eval.evalString("title", titleExpr));
        }
        if (multipleExpr != null) {
            super.setMultiple(eval.evalString("multiple", multipleExpr));
        }
 
    }

    @Override
    public void release() {
        super.release();
        this.disabledExpr = null;
        this.nameExpr = null;
        this.sizeExpr = null;
        this.titleExpr = null;
        this.multipleExpr = null;
    }


}
