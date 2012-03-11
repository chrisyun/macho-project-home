/*
 ***************************************************************************
 * Copyright 2004 Luca Passani                                             *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/el/ExpressionEvaluator.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall.el;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;


public class ExpressionEvaluator
{

    private PageContext pageContext;
    private Tag tag;

    public ExpressionEvaluator(Tag sourceTag, PageContext context)
    {
        this.tag = sourceTag;
        this.pageContext = context;
    }

    public Object eval(String attrName, String attrValue, Class<?> returnClass) throws JspException {
        Object result = null;
        if (attrValue != null) {
            result = ExpressionEvaluatorManager.evaluate(attrName, attrValue, returnClass, this.tag, this.pageContext);
        }
        return result;
    }

    public String evalString(String attrName, String attrValue) throws JspException  {
        return (String) eval(attrName, attrValue, String.class);
    }

    public boolean evalBoolean(String attrName, String attrValue) throws JspException {
        Boolean rtn = (Boolean) eval(attrName, attrValue, Boolean.class);
        if (rtn != null) {
            return rtn.booleanValue();
        } else {
            return false;
        }
    }

    public int evalInt(String attrName, String attrValue) throws JspException {

        Integer rtn = (Integer) eval(attrName, attrValue, Integer.class);
        if (rtn != null) {
            return rtn.intValue();
        } else {
            return -1;
        }
    }
}
