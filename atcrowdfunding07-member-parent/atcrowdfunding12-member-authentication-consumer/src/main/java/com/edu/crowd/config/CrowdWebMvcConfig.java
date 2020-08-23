package com.edu.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wyg_edu
 * @date 2020年7月2日 下午8:50:44
 * @version v1.0
 */
@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer{
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		String urlPath = "/auth/member/to/reg/page";// 访问的url地址
		
		String viewName="member-reg";// 目标试图的名称 使用yaml的配置中的前后缀进行拼接
		
		// 添加viewContrlooer
		registry.addViewController(urlPath).setViewName(viewName);
		
		registry.addViewController("/auth/member/to/login/page").setViewName("member-login");
		registry.addViewController("/auth/member/to/centor/page").setViewName("member-center");
		registry.addViewController("/member/my/crowd").setViewName("member-crowd");
	}

}
