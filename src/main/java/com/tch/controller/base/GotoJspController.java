package com.tch.controller.base;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.Map;

/**
 * Created by shz on 2017/8/2.
 */
@Controller
public class GotoJspController extends BaseController {

    @GetMapping("/404")
    public String goto404(){
        return "404";
    }
    @GetMapping("/500")
    public String goto500(){
        return "404";
    }
    @GetMapping("/shiro_403")
    public String gotoshiro_403(){
        return "shiro_403";
    }
    @GetMapping("/gotoindex")
    public String gotoindex(Model model) {
        model.addAttribute("manager", "true");
        return "index";
    }
    @GetMapping("/gotomain")
    public String gotomain(Model model) {
        model.addAttribute("manager", "true");
        return "main";
    }
    @GetMapping("/gotouserInfo")
    public String gotouserInfo(Model model) {
        model.addAttribute("manager", "true");
        return "userinfo";
    }
    @GetMapping("/gotochangePwd")
    public String gotochangePwd(Model model) {
        model.addAttribute("manager", "true");
        return "changePwd";
    }


    @GetMapping("/welcome")
    public String welcome(Map<String, Object> model) {
        model.put("time", new Date());
        model.put("message", "testing abc jsp config");
        return "welcome";
    }

}
