/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/el/ELAlternate_img.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall.el;

import javax.servlet.jsp.JspException;


public class ELAlternate_img extends com.npower.wall.WallAlternate_img {

    /**
   * 
   */
  private static final long serialVersionUID = 1L;
    private String srcExpr;
    private String testExpr;
    private String nopictureExpr;
    private String opwv_iconExpr;
    private String eu_imode_iconExpr;
    private String ja_imode_iconExpr;

    @Override
    public int doStartTag() throws JspException {

        evaluateExpressions();	
	return super.doStartTag();
    }

    //attribute Setters
    @Override
    public void setSrc(String src) {
	this.srcExpr = src;
    }
    @Override
    public void setTest(String t) {
	this.testExpr = t;
    }
    @Override
    public void setNopicture(String np) {
	this.nopictureExpr = np;
    }
    @Override
    public void setOpwv_icon(String oi) {
	this.opwv_iconExpr = oi;
    }
    @Override
    public void setEu_imode_icon(String eii) {
	this.eu_imode_iconExpr = eii;
    }
    @Override
    public void setJa_imode_icon(String jii) {
	this.ja_imode_iconExpr = jii;
    }

    private void evaluateExpressions() throws JspException
    {
        ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);

        if (srcExpr != null) {
            super.setSrc(eval.evalString("src", srcExpr));
        }
        if (testExpr != null) {
            super.setTest(eval.evalBoolean("test", testExpr));
        }
        if (nopictureExpr != null) {
            super.setNopicture(eval.evalBoolean("nopicture", nopictureExpr));
        }
        if (opwv_iconExpr != null) {
            super.setOpwv_icon(eval.evalString("opwv_icon", opwv_iconExpr));
        }
        if (ja_imode_iconExpr != null) {
            super.setJa_imode_icon(eval.evalString("ja_imode_icon", ja_imode_iconExpr));
        }
        if (eu_imode_iconExpr != null) {
            super.setEu_imode_icon(eval.evalString("eu_imode_icon", eu_imode_iconExpr));
        }

    }

    @Override
    public void release() {
        super.release();
        this.srcExpr = null;

	this.testExpr = null;
	this.nopictureExpr = null;
	this.opwv_iconExpr = null;
	this.eu_imode_iconExpr = null;
	this.ja_imode_iconExpr = null;
    }


}
