package com.tch.controller.base;


import com.tch.common.shiro.IncorrectCaptchaException;
import com.tch.domain.entity.system.UserInfo;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description  :
 */

// 只用同时具有permission:view和permission:aix权限才能访问
//@RequiresPermissions(value={"permission:view","permission:aix"}, logical= Logical.AND)
//@RequiresPermissions(value={"permission:view","permission:aix"}, logical= Logical.OR)一个就行

@Controller(value = "LoginController")
public class LoginController {

    //登录页(shiro配置需要两个/login 接口,一个是get用来获取登陆页面,一个用post用于登录,这是一个坑)
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    // 登录提交地址和applicationontext-shiro.xml配置的loginurl一致。 (配置文件方式的说法)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, Map<String, Object> map, Model model)
            throws Exception {
        // 登录失败从request中获取shiro处理的异常信息。shiroLoginFailure:就是shiro异常类的全类名.
        Object exception = request.getAttribute("shiroLoginFailure");
        String msg = "";
        if (exception != null){
            if (UnknownAccountException.class.isInstance(exception)) {
                msg = "提示->账号不存在";
            } else if (IncorrectCredentialsException.class.isInstance(exception)) {
                msg = "提示->密码不正确";
            } else if (IncorrectCaptchaException.class.isInstance(exception)) {
                msg = "提示->验证码不正确";
            } else {
                msg = "提示->未知错误";
            }
            map.put("msg", msg);
            return "login";
        }
        //如果已经登录，直接跳转主页面
        model.addAttribute("username","超級管理員");
        return "index";
    }

    //主页
    @RequestMapping({"/","/index"})
    public String index(HttpServletRequest request, Model model){
        model.addAttribute("username","超級管理員");
        return "index";
    }


    @RequestMapping(value="/gotologin",method= RequestMethod.GET)
    public String gotologin(Model model){
        model.addAttribute("user", new UserInfo());
//      return "login";
        return "login";
    }
//    /**
//     * 一般用不到
//     * @param model
//     * @return
//     */
//    @RequestMapping(value="/login",method= RequestMethod.GET)
//    public String loginForm(Model model){
//        model.addAttribute("user", new UserInfo());
////      return "login";
//        return "redirect:" + ShiroCasConfiguration.loginUrl;
//    }
//
//
//    @RequestMapping(value = "logout", method = { RequestMethod.GET,
//            RequestMethod.POST })
//    public String loginout(HttpSession session)
//    {
//        return "redirect:"+ ShiroCasConfiguration.logoutUrl;
//    }
}
