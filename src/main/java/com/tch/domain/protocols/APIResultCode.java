package com.tch.domain.protocols;

/**
 * Created by hugolew on 2016/4/7.
 */
public enum APIResultCode {
    // 成功
    SUCCESS("SUCCESS","成功"),
    //失败
    ERROR("ERROR", "系统正在维护中,请稍后再试!"),

    /**通用 */
    GLOBAL_UNAUTHORITY("413","权限不足"),
    GLOBAL_LOGIN_NAME_NULL("0501","用户名不能为空"),
    GLOBAL_LOGIN_PASS_NULL("0502","密码不能为空"),
    GLOBAL_LOGIN_FAIL("0503","用户名或密码不匹配"),
    GLOBAL_LOGIN_ERROR("0504","系统登录异常"),

    //角色权限错误信息
    RES_SAVE_ERROR("1501","资源信息保存失败"),
    ROLE_SAVE_ERROR("1502","角色信息保存失败"),
    USER_SAVE_ERROR("1503","用户信息保存失败"),
    USER_ROLE_SAVE_ERROR("1504","用户分配角色信息失败"),
    USER_FAIL_ERROR("1505","失效用户失败,程序异常"),
    ROLE_FAILK_ERROR("1506","失效角色失败,程序异常"),
    RES_FAILK_ERROR("1507","失效资源失败,程序异常"),
    USER_LOGIN_NAME_EXIST("1508","用户账号已存在，请重新输入"),
    ROLE_RES_SAVE_ERROR("1509","角色分配菜单失败"),
    ROLE_NAME_EXIST("1510","角色名称已存在，请重新输入"),
    INVALID_CLIENTID("30003", "Invalid clientid"),
    INVALID_PASSWORD("30004", "User name or password is incorrect"),
    INVALID_CAPTCHA("30005", "Invalid captcha or captcha overdue"),
    INVALID_TOKEN("30006", "Invalid token"),
    PERMISSION_DENIED("30002","you dont have permission")
    ;


    APIResultCode(String code,String msg) {
        this.code=code;
        this.msg=msg;
    }
    private String code;

    private String msg;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
