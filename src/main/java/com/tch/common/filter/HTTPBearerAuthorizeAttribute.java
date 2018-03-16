package com.tch.common.filter;


import com.alibaba.fastjson.JSON;
import com.tch.common.jwt.JwtHelper;
import com.tch.common.jwt.MyAudience;
import com.tch.dao.primary.UserInfoRepository;
import com.tch.domain.entity.system.SysPermission;
import com.tch.domain.entity.system.SysRole;
import com.tch.domain.entity.system.UserInfo;
import com.tch.domain.protocols.APIResult;
import com.tch.domain.protocols.APIResultCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

/**
 * Created by shz on 2017/8/31.
 */
@Component
public class HTTPBearerAuthorizeAttribute implements Filter {

    @Autowired
    private MyAudience audience;
    @Autowired
    private UserInfoRepository userInfoRepository;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub

        APIResult resultMsg = new APIResult(APIResultCode.INVALID_TOKEN);
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String urlstr=httpRequest.getRequestURL().toString();
        URL url=new URL(urlstr);
        String path=url.getPath();

        String auth = httpRequest.getHeader("Authorization");

        if ((auth != null) && (auth.length() > 7))
        {
            String HeadStr = auth.substring(0, 6).toLowerCase();
            if (HeadStr.compareTo("bearer") == 0)
            {
                auth = auth.substring(7, auth.length());
                Claims claims= JwtHelper.parseJWT(auth, audience.getBase64Secret());
                if (claims != null)
                {
                    //token验证通过之后，验证身份信息、权限
                    UserInfo user=userInfoRepository.findByUsername(claims.get("unique_name").toString());
                    for(SysRole role:user.getRoleList()){
                        //有效的角色
                        if(role.getAvailable()){
                            for(SysPermission p:role.getPermissions()){
                                //确认用户是否有访问该url的权限
                                if(path.equals(p.getUrl())){
                                    chain.doFilter(request, response);
                                    return;
                                }
                            }
                        }
                    }
                    resultMsg = new APIResult(APIResultCode.PERMISSION_DENIED);
                }

            }
        }
        //若以上发生错误，则token非法。返回错误
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json; charset=utf-8");
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write(JSON.toJSONString(resultMsg));
        return;
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }
}
