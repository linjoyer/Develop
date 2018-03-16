package com.tch.common.filter;

import com.alibaba.fastjson.JSON;
import com.tch.domain.protocols.APIResult;
import com.tch.domain.protocols.APIResultCode;
import sun.misc.BASE64Decoder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by shz on 2017/8/31.
 */
@SuppressWarnings("restriction")
public class HTTPBasicAuthorizeAttribute implements Filter {

    private static String Name = "test";
    private static String Password = "test";

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub

        APIResultCode resultStatusCode = checkHTTPBasicAuthorize(request);
        if (resultStatusCode != APIResultCode.SUCCESS)
        {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.setContentType("application/json; charset=utf-8");
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);


            APIResult resultMsg = new APIResult(APIResultCode.PERMISSION_DENIED);
            httpResponse.getWriter().write(JSON.toJSONString(resultMsg));
            return;
        }
        else
        {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }

    private APIResultCode checkHTTPBasicAuthorize(ServletRequest request)
    {
        try
        {
            HttpServletRequest httpRequest = (HttpServletRequest)request;
            String auth = httpRequest.getHeader("Authorization");
            System.out.println("aut="+auth);
            if ((auth != null) && (auth.length() > 6))
            {
                String HeadStr = auth.substring(0, 5).toLowerCase();
                if (HeadStr.compareTo("basic") == 0)
                {
                    auth = auth.substring(6, auth.length());
                    //这里可以做一些自己的判断
                    String decodedAuth = getFromBASE64(auth);
                    if (decodedAuth != null)
                    {
                        String[] UserArray = decodedAuth.split(":");
                        System.out.println("UserArray=="+UserArray.toString());

                        if (UserArray != null && UserArray.length == 2)
                        {
                            if (UserArray[0].compareTo(Name) == 0
                                    && UserArray[1].compareTo(Password) == 0)
                            {
                                return APIResultCode.SUCCESS;
                            }
                        }
                    }
                }
            }
            return APIResultCode.PERMISSION_DENIED;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return APIResultCode.PERMISSION_DENIED;
        }

    }

    private String getFromBASE64(String s) {
        if (s == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }

}
