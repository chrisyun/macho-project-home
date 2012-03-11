/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/el/ELInput.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall.el;

import javax.servlet.jsp.JspException;


public class ELInput extends com.npower.wall.WallInput {

    /**
   * 
   */
  private static final long serialVersionUID = 1L;
    private String formatExpr;
    private String titleExpr;
    private String accesskeyExpr;
    private String disabledExpr;
    private String checkedExpr;

    private String emptyokExpr;
    private String maxlengthExpr;
    private String nameExpr;
    private String sizeExpr;
    private String typeExpr;
    private String valueExpr;

    @Override
    public int doStartTag() throws JspException {

        evaluateExpressions();	
	return super.doStartTag();
    }

    //attribute Setters
    @Override
    public void setFormat(String format) {
	this.formatExpr = format;
    }
    @Override
    public void setTitle(String title) {
	this.titleExpr = title;
    }
    @Override
    public void setAccesskey(String accesskey) {
	this.accesskeyExpr = accesskey;
    }
    @Override
    public void setDisabled(String disabled) {
	this.disabledExpr = disabled;
    }

    @Override
    public void setChecked(String oi) {
	this.checkedExpr = oi;
    }



    @Override
    public void setEmptyok(String emptyok) {
	this.emptyokExpr = emptyok;
    }
    @Override
    public void setMaxlength(String maxlength) {
	this.maxlengthExpr = maxlength;
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
    public void setType(String t) {
	this.typeExpr = t;
    }
    @Override
    public void setValue(String v) {
	this.valueExpr = v;
    }


    private void evaluateExpressions() throws JspException
    {
        ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);

        if (formatExpr != null) {
            super.setFormat(eval.evalString("format", formatExpr));
        }
        if (titleExpr != null) {
            super.setTitle(eval.evalString("title", titleExpr));
        }
        if (accesskeyExpr != null) {
            super.setAccesskey(eval.evalString("accesskey", accesskeyExpr));
        }
        if (disabledExpr != null) {
            super.setDisabled(eval.evalString("disabled", disabledExpr));
        }
        if (checkedExpr != null) {
            super.setChecked(eval.evalString("checked", checkedExpr));
        }
	if (emptyokExpr != null) {
            super.setEmptyok(eval.evalString("emptyok", emptyokExpr));
        }
        if (maxlengthExpr != null) {
            super.setMaxlength(eval.evalString("maxlength", maxlengthExpr));
        }
        if (nameExpr != null) {
            super.setName(eval.evalString("name", nameExpr));
        }
        if (sizeExpr != null) {
            super.setSize(eval.evalString("size", sizeExpr));
        }
        if (typeExpr != null) {
            super.setType(eval.evalString("type", typeExpr));
        }
        if (valueExpr != null) {
            super.setValue(eval.evalString("value", valueExpr));
        }
 
    }

    @Override
    public void release() {
        super.release();
        this.formatExpr = null;
        this.titleExpr = null;
        this.accesskeyExpr = null;
        this.disabledExpr = null;
        this.checkedExpr = null;

        this.emptyokExpr = null;
        this.maxlengthExpr = null;
        this.nameExpr = null;
        this.sizeExpr = null;
        this.typeExpr = null;
        this.valueExpr = null;
    }


}
