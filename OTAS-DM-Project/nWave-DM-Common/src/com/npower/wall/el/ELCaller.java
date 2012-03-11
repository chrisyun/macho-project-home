/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/el/ELCaller.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall.el;

import javax.servlet.jsp.JspException;


public class ELCaller extends com.npower.wall.WallCaller {

    /**
   * 
   */
  private static final long serialVersionUID = 1L;
    private String telExpr;
    private String altExpr;
    private String accesskeyExpr;
    private String ctiExpr;
    private String opwv_iconExpr;

    @Override
    public int doStartTag() throws JspException {

        evaluateExpressions();	
	return super.doStartTag();
    }

    //attribute Setters
    @Override
    public void setTel(String tel) {
	this.telExpr = tel;
    }
    @Override
    public void setAlt(String alt) {
	this.altExpr = alt;
    }
    @Override
    public void setAccesskey(String accesskey) {
	this.accesskeyExpr = accesskey;
    }
    @Override
    public void setCti(String cti) {
	this.ctiExpr = cti;
    }

    @Override
    public void setOpwv_icon(String oi) {
	this.opwv_iconExpr = oi;
    }


    private void evaluateExpressions() throws JspException
    {
        ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);

        if (telExpr != null) {
            super.setTel(eval.evalString("tel", telExpr));
        }
        if (altExpr != null) {
            super.setAlt(eval.evalString("alt", altExpr));
        }
        if (accesskeyExpr != null) {
            super.setAccesskey(eval.evalString("accesskey", accesskeyExpr));
        }
        if (ctiExpr != null) {
            super.setCti(eval.evalString("cti", ctiExpr));
        }
        if (opwv_iconExpr != null) {
            super.setOpwv_icon(eval.evalString("opwv_icon", opwv_iconExpr));
        }
 
    }

    @Override
    public void release() {
        super.release();
        this.telExpr = null;
        this.altExpr = null;
        this.accesskeyExpr = null;
        this.ctiExpr = null;
        this.opwv_iconExpr = null;
    }


}
