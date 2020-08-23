package com.edu.crowd.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.protocol.RequestContent;
import org.springframework.stereotype.Component;

import com.edu.croed.constant.AccessPassResources;
import com.edu.croed.constant.CrowdConstant;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @author wyg_edu
 * @date 2020年7月14日 下午9:26:39
 * @version v1.0
 * Zuul拦截器
 */
@Component// 配置类
public class CrowdAccessFilter extends ZuulFilter {

	/**
	 * 该方法返回true后会调用后续的run方法，返回false则代表通过无需拦截
	 */
	@Override
	public boolean shouldFilter() {
		
		// 1、获取requestContext对象
		RequestContext requestContext = RequestContext.getCurrentContext();
		
		// 2、通过该对象获取请求参数：框架底层是根据ThreadLocal，从线程中获取该对象
		HttpServletRequest request = requestContext.getRequest();
		
		// 3、servletPath获取
		String servletPath = request.getServletPath();
		
		// 4、判断servletPath是否是特定可放行的请求(首页登录页)
		boolean containsResult = AccessPassResources.PASS_RES_SET.contains(servletPath);
		
		if(containsResult) {
			return false;// 如果是特定请求则返回false
		}
		
//		// 5、判断是否为静态资源，如果是则放行
//		boolean staticResource = AccessPassResources.judgeCurrentServicePathWetherStaticResource(servletPath);
//		if(staticResource) {
//			return false;
//		}
//		
//		// 6、进行拦截-
//		return true;
		return !AccessPassResources.judgeCurrentServicePathWetherStaticResource(servletPath);// 代码同上
	}

	@Override
	public Object run() throws ZuulException {
		// 获取当前请求对象
		RequestContext currentContext = RequestContext.getCurrentContext();
		HttpServletRequest request = currentContext.getRequest();
		
		// 获取session对象
		HttpSession session = request.getSession();
		
		// 从session中获取已登录的用户对象
		Object loginMember = session.getAttribute(CrowdConstant.LOGIN_MEMBER);
		
		// 判断loginMember是否为空
		if(loginMember == null) {
			// 没有登录则应该进行退出登录 从currentContext中获取response对象
			HttpServletResponse response = currentContext.getResponse();
			
			// 将提示消息存入session域
			session.setAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_ACCESS_DENIED); // 登录后访问
			
			// 重定向到auth-consumer工程中的登录页面
			try {
				response.sendRedirect("/auth/member/to/login/page");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String filterType() {
		// 这里返回pre这里是为了在目标微服务前执行过滤
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

}
