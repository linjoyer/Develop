package com.tch.controller.base;


import com.tch.domain.entity.system.UserInfo;
import com.tch.domain.protocols.APIResult;
import com.tch.domain.protocols.APIResultCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseController {

    public static final Logger logger = LoggerFactory.getLogger(BaseController.class);
    
    protected <T> APIResult<T> success(String message, T t) {
        return new APIResult(APIResultCode.SUCCESS, message.equals("")?APIResultCode.SUCCESS.getMsg():message, t);
    }

    protected <T> APIResult<T> success(String message) {
        return success(message, null);
    }

    protected <T> APIResult<T> success(T t) {
        return success("", t);
    }


    protected <T> APIResult<T> error(String message) {
        return new APIResult(APIResultCode.ERROR, message.equals("")?APIResultCode.ERROR.getMsg():message, null);
    }

    protected <T> APIResult<T> error(String message, T t) {
        return new APIResult(APIResultCode.ERROR, message.equals("")?APIResultCode.ERROR.getMsg():message, t);
    }
    /**
     * 登录用户名
     */
    public String getCurrentLoginName() {
        Subject currentUser = SecurityUtils.getSubject();
        UserInfo user = currentUser.getPrincipals().oneByType(UserInfo.class);
        return user.getUsername();
    }
    /**
     * 登录用户对象
     */
    public UserInfo getCurrentUser() {
        Subject currentUser = SecurityUtils.getSubject();
        UserInfo user = currentUser.getPrincipals().oneByType(UserInfo.class);
        return user;
    }
}
