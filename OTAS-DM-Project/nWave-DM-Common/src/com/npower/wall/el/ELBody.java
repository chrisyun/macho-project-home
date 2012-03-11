/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/el/ELBody.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall.el;

import javax.servlet.jsp.JspException;


public class ELBody extends com.npower.wall.WallBody {

    /**
   * 
   */
  private static final long serialVersionUID = 1L;
    private String bgcolorExpr;
    private String textExpr;
    private String wml_back_button_labelExpr;
    private String disable_wml_templateExpr;
    private String newcontextExpr;

    @Override
    public int doStartTag() throws JspException {

        evaluateExpressions();	
	return super.doStartTag();
    }

    //attribute Setters
    @Override
    public void setBgcolor(String bgcolor) {
	this.bgcolorExpr = bgcolor;
    }
    @Override
    public void setText(String text) {
	this.textExpr = text;
    }
    @Override
    public void setWml_back_button_label(String wml_back_button_label) {
	this.wml_back_button_labelExpr = wml_back_button_label;
    }
    @Override
    public void setDisable_wml_template(String disable_wml_template) {
	this.disable_wml_templateExpr = disable_wml_template;
    }
    @Override
    public void setNewcontext(String newcontext) {
	this.newcontextExpr = newcontext;
    }

    private void evaluateExpressions() throws JspException
    {
        ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);

        if (bgcolorExpr != null) {
            super.setBgcolor(eval.evalString("bgcolor", bgcolorExpr));
        }
        if (textExpr != null) {
            super.setText(eval.evalString("text", textExpr));
        }
        if (wml_back_button_labelExpr != null) {
            super.setWml_back_button_label(eval.evalString("wml_back_button_label", wml_back_button_labelExpr));
        }
        if (disable_wml_templateExpr != null) {
            super.setDisable_wml_template(eval.evalBoolean("disable_wml_template", disable_wml_templateExpr));
        }
        if (newcontextExpr != null) {
            super.setNewcontext(eval.evalBoolean("newcontext", newcontextExpr));
        }
    }

    @Override
    public void release()    {
        super.release();
	this.bgcolorExpr = null;
	this.textExpr = null;
	this.wml_back_button_labelExpr = null;
	this.disable_wml_templateExpr = null;
	this.newcontextExpr = null;
    }


}
