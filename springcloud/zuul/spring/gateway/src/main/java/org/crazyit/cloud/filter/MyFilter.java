package org.crazyit.cloud.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;

public class MyFilter extends ZuulFilter {

	public boolean shouldFilter() {
		return true;
	}

	public Object run() {
		System.out.println("执行 MyFilter 过滤器");
//		RequestContext ctx = RequestContext.getCurrentContext();
//		HttpServletRequest request = ctx.getRequest();
//		if(!"123".equals(request.getParameter("mima"))) {//密码不正确则不返回请求结果
//			ctx.setSendZuulResponse(false);
//		}
		return null;
	}

	@Override
	public String filterType() {
		return FilterConstants.ROUTE_TYPE;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
