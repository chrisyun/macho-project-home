/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/util/SimpleNumberGenerator.java,v 1.8 2006/12/25 09:10:02 zhaoyang Exp $
 * $Revision: 1.8 $
 * $Date: 2006/12/25 09:10:02 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.npower.dm.core.DMException;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.8 $ $Date: 2006/12/25 09:10:02 $
 */
public class SimpleNumberGenerator implements NumberGenerator, Serializable {

	private String template;

	/**
	 * 
	 */
	public SimpleNumberGenerator() {
		super();
	}

	/**
	 * 
	 */
	public SimpleNumberGenerator(String template) {
		super();
		this.template = template;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.npower.dm.util.NumberGenerator#generate()
	 */
	public List<String> generate() throws DMException {

		String template = this.getTemplate();
		if (StringUtils.isEmpty(template)) {
			return null;
		}

		int phoneMark1 = template.indexOf("?");
		int phoneMark2 = template.indexOf("*");
		int phoneMark3 = template.indexOf(",");

		// 只有一个号码段，并且号码中只包含"?"
		// Pattern.compile("[^,]").matcher(template).find();

		if ((phoneMark1 > 0) && (phoneMark3 < 0)) {
			List<String> returnNumberList = NumberMarkParse
					.getMarkParseList(template);
			return returnNumberList;
		}

		// 只有一个号码段，并且号码中只包含"*"
		// Pattern.compile("").matcher(template).find();

		if ((phoneMark2 > 0) && (phoneMark3 < 0)) {
			List<String> returnNumberList = NumberMarkParse
					.getTwoMarkParseList(template);
			return returnNumberList;
		}

		// 字号段中包含"," "?" 
		// Pattern.compile("\\d\\D*").matcher(template).find();

		if (phoneMark3 > 0) {

			List<String> returnNumberList = NumberMarkParse
					.getThreeMarkParseList(template);
			return returnNumberList;
		}

		return new ArrayList<String>(Arrays.asList(template.split(",")));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.npower.dm.util.NumberGenerator#match(java.lang.String,
	 *      java.lang.String, com.npower.dm.util.NumberMatchMode)
	 */
	public boolean match(String number, String template, NumberMatchMode mode)
			throws DMException {
		return false;
	}

	/**
	 * @return the template
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * @param template
	 *            the template to set
	 */
	public void setTemplate(String template) {
		this.template = template;
	}

}
