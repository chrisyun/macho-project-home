package com.ibm.tivoli.tuna.util;

public class StringUtil {
	
	/**
	 * 判断数组中是否存在该元素
	 * @param element
	 * @param array
	 * @return
	 */
	public static boolean isExistElement(String element ,String[] array) {
		boolean flag = false;
		
		for (int i = 0; i < array.length; i++) {
			if(element.toLowerCase().equals(array[i].toLowerCase())) {
				flag = true;
				break;
			}
		}
		
		return flag;
	}
	
	/**
	 * 判断数组是否为空
	 * @param array
	 * @return
	 */
	public static boolean isNullArray(String[] array) {
		boolean flag = false;
		
		if(array == null || array.length == 0) {
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
    	if(str == null || "".equals(str.trim()) || "null".equals(str)) {
    		return true;
    	} else {
    		return false;
    	}
    }
	
}
