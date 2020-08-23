package com.edu.crowd.mvc.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.edu.croed.constant.CrowdConstant;

/**
 * @author wyg_edu
 * @date 2020年6月13日 上午9:10:01
 * @version v1.0
 */
//表示当前类是一个配置类
@Configuration
//启用Web环境下权限控制功能
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)// 启用安全控制，保证@PreAuthorize @PostAuthorize等注解生效
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
//	@Bean
//	public BCryptPasswordEncoder getPasswordEncoder() {
//		return new BCryptPasswordEncoder();// 注入到bean组件
//	}
	@Autowired
	private BCryptPasswordEncoder passwordEncoding;
	
	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		
		// builder.inMemoryAuthentication().withUser("admin").password("123123").roles("ADMIN");// 内存版登录模式，测试security控制
		
		// 正式功能中使用基于数据库的用用
		builder.
			userDetailsService(userDetailsService).
			passwordEncoder(passwordEncoding);
		
	}
	
	@Override
	protected void configure(HttpSecurity security) throws Exception {
		security.authorizeRequests()// 给请求授权
				.antMatchers("/admin/do/login/page.html", "/bootstrap/**", "/crowd/**", "/css/**", "/fonts/**",
						"/img/**", "/jquery/**", "/layer/**", "/script/**", "/WEB-INF/**",
						"/ztree/**")// 对登录页以及静态资源授权进行授权设置
				.permitAll()// 无条件访问
				.antMatchers("/admin/get/page.html")// 针对admin分页进行访问控制
				.hasRole("经理")// 要求具备经理的角色
				.anyRequest()// 其他任意请求
				.authenticated()// 认证后访问
				.and()
				.exceptionHandling()
				.accessDeniedHandler(new AccessDeniedHandler() {
					@Override
					public void handle(HttpServletRequest request, HttpServletResponse response,
							AccessDeniedException accessDeniedException) throws IOException, ServletException {
						request.setAttribute("exception", new Exception(CrowdConstant.MESSAGE_ACCESS_DENIED));
						request.getRequestDispatcher("/WEB-INF/system-error.jsp").forward(request, response);
					}
				})
				.and()
				.csrf()// 防跨站伪造功能
				.disable()// 禁用该功能
				.formLogin()// 使用表单形式登录
				.loginPage("/admin/do/login/page.html")// 指定 登陆页面，如果没有指定，则登录到security定义登录页
				.loginProcessingUrl("/security/do/login.html")// 指定提交登录表单的地址
				.defaultSuccessUrl("/admin/to/admin/page.html")// 指定登录成功后前往的地址
				.usernameParameter("loginAcct")// 账号的请求参数名称
				.passwordParameter("userPswd")// 密码的请求参数名称
				.and()
				.logout()// 开启退出功能
				.logoutUrl("/seucrity/do/logout.html")// 退出请求
				.logoutSuccessUrl("/admin/do/login/page.html")// 退出跳转页面
				;
	}
}
