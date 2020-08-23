package com.edu.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wyg_edu
 * @date 2020年7月26日 下午5:07:14
 * @version v1.0
 */
@Configuration// 声明这是一个配置类
public class CrowdWebMvcConfig implements WebMvcConfigurer{

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		// view-controller实在项目内部定义的，否则应该根据zuul配置的路由规则进行配置，及加/project/agree/protocol/page
		registry.addViewController("/agree/protocol/page").setViewName("project-agree");
		registry.addViewController("/launch/project/page").setViewName("project-launch");
		registry.addViewController("/return/info/page").setViewName("project-return");
		registry.addViewController("/create/confirm/page").setViewName("project-confirm");
		registry.addViewController("/create/success").setViewName("project-success");
	}
}
