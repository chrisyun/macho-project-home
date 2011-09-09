package com.ibm.tivoli.tuna.util;

import java.util.List;

import com.ibm.tivoli.tuna.service.AttributeStatement;

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
	
	/**
	 * 
	 * @param list
	 * @param array
	 */
	public static void copyListToArray(List<? extends AttributeStatement> list, AttributeStatement[] array) {
		for (int i = 0; i < array.length; i++) {
			array[i] = list.get(i);
		}
		list.clear();
	}
	
	public static String byte2string(byte[] byt) {
		if(byt == null || byt.length == 0) {
			return null;
		}
		
		String res = "";
		for(byte t : byt) {
			res += t + ",";
		}
		return res.substring(0, res.length()-1);
	}
	
	public static byte[] string2byte(String str) {
		if(isNull(str)) {
			return null;
		}
		
		String[] temp = str.split(",");
		byte[] res = new byte[temp.length];
		for (int i = 0; i < temp.length; i++) {
			res[i] = Byte.parseByte(temp[i]);
		}
		
		return res;
	}
	
	public static AttributeStatement[] splitPage(List<AttributeStatement> array, int startPage, int pageSize) {
		int maxCount = array.size();
		int maxIndex = (startPage+1)*pageSize;
		
		//取最小数
		int endIndex = 	maxIndex > maxCount ? maxCount:maxIndex;
		AttributeStatement[] resultArray = new AttributeStatement[endIndex-startPage*pageSize];
		for (int i = 0; i < resultArray.length; i++) {
    		resultArray[i] = array.get(startPage*pageSize+i);
		}
		
		return resultArray;
	}
	
}
