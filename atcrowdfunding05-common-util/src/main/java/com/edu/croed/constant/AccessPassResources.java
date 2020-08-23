package com.edu.croed.constant;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wyg_edu
 * @date 2020年7月14日 下午9:02:02
 * @version v1.0
 */
public class AccessPassResources {
	
	public static final Set<String> PASS_RES_SET = new HashSet<>();
	
	static {
		PASS_RES_SET.add("/");// 首页
		PASS_RES_SET.add("/auth/member/to/reg/page");// 
		PASS_RES_SET.add("/auth/member/to/login/page");// 登录页
		PASS_RES_SET.add("/auth/member/to/centor/page");// 注册页
		PASS_RES_SET.add("/auth/member/logout");// 退出登录
		PASS_RES_SET.add("/auth/member/do/login");// 登录请求
		PASS_RES_SET.add("/auth/do/member/register");// 注册请求
		PASS_RES_SET.add("/auth/member/send/short/message.json");// 验证码
	}
	
	public static final Set<String> STATIC_RES_SET = new HashSet<>();
	
	static {
		STATIC_RES_SET.add("bootstrap");
		STATIC_RES_SET.add("css");
		STATIC_RES_SET.add("fonts");
		STATIC_RES_SET.add("img");
		STATIC_RES_SET.add("jquery");
		STATIC_RES_SET.add("layer");
		STATIC_RES_SET.add("script");
		STATIC_RES_SET.add("ztree");
	}
	
	/**
	 * 用于判断某个servlet的请求对应的是否是一个静态资源，是则返回true  
	 * @param servletPath
	 * @return
	 */
	public static boolean judgeCurrentServicePathWetherStaticResource(String servletPath) {
		
		if (servletPath == null || servletPath.length() == 0) {
			throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
		}
		
		return STATIC_RES_SET.contains(servletPath.split("/")[1]);
		
	}
	
}
