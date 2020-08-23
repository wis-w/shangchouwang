package com.edu.croed.util;
/**
 * @author wyg_edu
 * @date 2020年7月11日 下午2:26:06
 * @version v1.0
 */
public class StringUtil {
	
	/**
	 * 判断字符串不得为空或者为null
	 */
	public static boolean equalsString(String str){
		return str == null || "".equals(str.trim());
	}

}
