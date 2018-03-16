package com.tch.common.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


/**
 * 参数特殊字符过滤
 *
 * @author   蓝永文
 * @create    2016年2月3日
 */

public class  MHttpServletRequest extends HttpServletRequestWrapper {

        public MHttpServletRequest(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getParameter(String name) {
            System.out.println("MHttpServletRequest.getParameter()");
            // 返回值之前 先进行过滤
            return XssShieldUtil.stripXss(super.getParameter(name));
        }

        @Override
        public String[] getParameterValues(String name) {
            System.out.println("MHttpServletRequest.getparameterValues()");
            // 返回值之前 先进行过滤
            String[] values = super.getParameterValues(name);
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    values[i] = XssShieldUtil.stripXss(values[i]);
                }
            }
            return values;
        }
    }