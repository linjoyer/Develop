package com.tch.common.filter;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 非法字符过滤器，用来处理request.getParamater中的非法字符。如<script>alert('123');</script>
 *
 * @author   蓝永文
 * @create    2016年2月3日
 */
public class IllegalCharacterFilter extends HandlerInterceptorAdapter {


    public void init(FilterConfig filterConfig) throws ServletException {

    }


    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        /**处理安全漏洞：使用HTTP动词篡改的认证旁路**/
        String method = request.getMethod();
        System.out.println("DEBUG:"+method+":"+request.getRequestURL());
        if(!"GET".equalsIgnoreCase(method)&&!"POST".equalsIgnoreCase(method)&&!"HEAD".equalsIgnoreCase(method))
        {
            System.out.println("对不起，您的请求非法，系统拒绝响应!");
            response.setContentType("text/html;charset=GBK");
            response.setCharacterEncoding("GBK");
            response.setStatus(403);
            response.getWriter().print("<font size=6 color=red>对不起，您的请求非法，系统拒绝响应!</font>");
            return;
        }
        /**非法字符过滤器，暂不启用**/
        request = new MHttpServletRequest(request);
        chain.doFilter(request, response);

    }


    public void destroy() {

    }

    /*
    <!-- 非法参数过滤器 -->
    <filter>
        <filter-name>IllegalCharacterFilter</filter-name>
        <filter-class>tchbean.filter.IllegalCharacterFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>IllegalCharacterFilter</filter-name>
        <url-pattern>*.jsp</url-pattern>
    </filter-mapping>
    */
}