package com.npower.dm.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberMarkParse {

	/**
	 * Contain 1 "?"
	 * 
	 * @param template
	 * @return
	 */
	private static List<String> oneMarkList(String template) {

		List<String> returnListValue = new ArrayList<String>(10);
		Pattern patt = Pattern.compile("\\?");

		for (int i = 0; i < 10; i++) {

			Matcher mat = patt.matcher(template);
			StringBuffer saveValue = new StringBuffer();

			boolean result = mat.find();
			if (result) {
				mat.appendReplacement(saveValue, (new Integer(i).toString()));
			}

			mat.appendTail(saveValue);
			returnListValue.add(saveValue.toString());

		}
		return returnListValue;
	}

	/**
	 * Contain 2 "?"
	 * 
	 * @param templateList
	 * @return
	 */
	private static List<String> twoMarkList(List<String> templateList) {

		List<String> returnListValue = new ArrayList<String>(100);

		for (int i = 0; i < 10; i++) {

			List<String> oneMarkTenCount = new ArrayList<String>(10);
			oneMarkTenCount = NumberMarkParse.oneMarkList(templateList.get(i));

			for (int j = 0; j < 10; j++) {
				returnListValue.add(oneMarkTenCount.get(j));
			}

		}

		return returnListValue;
	}

	/**
	 * Contain 3 "?"
	 * 
	 * @param templateList
	 * @return
	 */
	private static List<String> threeMarkList(List<String> templateList) {

		List<String> returnListValue = new ArrayList<String>(1000);

		for (int i = 0; i < 100; i++) {

			List<String> oneMarkTenCount = new ArrayList<String>(10);
			oneMarkTenCount = NumberMarkParse.oneMarkList(templateList.get(i));

			for (int j = 0; j < 10; j++) {
				returnListValue.add(oneMarkTenCount.get(j));
			}

		}

		return returnListValue;
	}

	/**
	 * Contain 4 "?"
	 * 
	 * @param templateList
	 * @return
	 */
	private static List<String> fourMarkList(List<String> templateList) {

		List<String> returnListValue = new ArrayList<String>(10000);

		for (int i = 0; i < 1000; i++) {

			List<String> oneMarkTenCount = new ArrayList<String>(10);
			oneMarkTenCount = NumberMarkParse.oneMarkList(templateList.get(i));

			for (int j = 0; j < 10; j++) {
				returnListValue.add(oneMarkTenCount.get(j));
			}

		}

		return returnListValue;
	}

	/**
	 * Contain 5 "?"
	 * 
	 * @param templateList
	 * @return
	 */
	private static List<String> fiveMarkList(List<String> templateList) {

		List<String> returnListValue = new ArrayList<String>(100000);

		for (int i = 0; i < 10000; i++) {

			List<String> oneMarkTenCount = new ArrayList<String>(10);
			oneMarkTenCount = NumberMarkParse.oneMarkList(templateList.get(i));

			for (int j = 0; j < 10; j++) {
				returnListValue.add(oneMarkTenCount.get(j));
			}

		}

		return returnListValue;
	}

	/**
	 * 一个"?"匹配0－9的数字，目前最多可以解析"?"的个数是5，最多允许有5个"?"，因为6个时间过长。 Example: 13800??1234,
	 * 1386789????, 1320?1?2?8?
	 * 
	 * @param template
	 * @return
	 */
	public static List<String> getMarkParseList(String template) {

		// TODO zhaoyang 目前最多可以解析"?"的个数是5，最多允许有5个"?"，因为6个时间过长。

		int markAppearanceCount = 0;

		Pattern patt = Pattern.compile("\\?");
		Matcher match = patt.matcher(template);
		boolean markBoolean = match.find();
		while (markBoolean) {
			markAppearanceCount++;
			markBoolean = match.find();
		}

		if (markAppearanceCount == 1) {
			return NumberMarkParse.oneMarkList(template);
		} else if (markAppearanceCount == 2) {
			return NumberMarkParse.twoMarkList(NumberMarkParse
					.oneMarkList(template));
		} else if (markAppearanceCount == 3) {
			return NumberMarkParse.threeMarkList(NumberMarkParse
					.twoMarkList(NumberMarkParse.oneMarkList(template)));
		} else if (markAppearanceCount == 4) {
			return NumberMarkParse.fourMarkList(NumberMarkParse
					.threeMarkList(NumberMarkParse.twoMarkList(NumberMarkParse
							.oneMarkList(template))));
		} else if (markAppearanceCount == 5) {
			return NumberMarkParse.fiveMarkList(NumberMarkParse
					.fourMarkList(NumberMarkParse
							.threeMarkList(NumberMarkParse
									.twoMarkList(NumberMarkParse
											.oneMarkList(template)))));
		}

		return new ArrayList<String>();
	}

	/**
	 * 一个"*"表示1个"?"，2个"?"，3个"?"，或4个"?"，目前只支持一个"*"，同一个号码段中不允许同时存在"*"和"?" Example:
	 * 138*0001234, 1380*25555, 13233337*, 1361234* 很难判断用户的"*"对应几个"?"
	 * 
	 * @param template
	 * @return
	 */
	public static List<String> getTwoMarkParseList(String template) {

		int markPosition = template.indexOf("*");
		int changeMarkCount = 12 - template.length();
		String newMarkString = "";

		for (int i = 0; i < changeMarkCount; i++) {
			newMarkString = newMarkString + "?";
		}

		String newChangeTemplate = template.substring(0, markPosition)
				+ newMarkString
				+ template.substring(markPosition + 1, template.length());

		return NumberMarkParse.getMarkParseList(newChangeTemplate);
	}

	/**
	 * 支持号码段中同时存在"," "?"
	 * 
	 * @param template
	 * @return
	 */
	public static List<String> getThreeMarkParseList(String template) {

		List<String> returnListValue = new ArrayList<String>(100000);

		if (template.indexOf(",") > 0) {

			List<String> divisionList = new ArrayList<String>(Arrays
					.asList(template.split(",")));

			for (int i = 0; i < divisionList.size(); i++) {

				if (divisionList.get(i).indexOf("?") > 0) {
					List<String> getMarkOneList = NumberMarkParse
							.getMarkParseList(divisionList.get(i));

					for (int j = 0; j < getMarkOneList.size(); j++) {

						returnListValue.add(getMarkOneList.get(j));
					}

				} else if (divisionList.get(i).indexOf("*") > 0) {
					List<String> getMarkTwoList = NumberMarkParse
							.getTwoMarkParseList(divisionList.get(i));

					for (int j = 0; j < getMarkTwoList.size(); j++) {
						returnListValue.add(getMarkTwoList.get(j));
					}

				} else {
					returnListValue.add((String) divisionList.get(i));
				}
			}

			return returnListValue;

		}

		return new ArrayList<String>();
	}

}
