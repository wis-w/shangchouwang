package test;

import org.springframework.util.StringUtils;

/**
 * @author wyg_edu
 * @date 2020年5月19日 下午3:24:22
 * @version v1.0
 */
public class StringTest {
	
	public static void main(String[] args) {
		String s1 = "";
		String s2 = " ";
		String s3 = null;
		System.out.println(StringUtils.isEmpty(s1));
		System.out.println(StringUtils.isEmpty(s2.trim()));
		System.out.println(StringUtils.isEmpty(s3));
	}

}
