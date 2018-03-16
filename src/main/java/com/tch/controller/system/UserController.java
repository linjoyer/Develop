package com.tch.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tch.common.utils.CommonHelper;
import com.tch.controller.base.BaseController;
import com.tch.domain.entity.system.SysRole;
import com.tch.domain.entity.system.UserInfo;
import com.tch.domain.protocols.APIResult;
import com.tch.domain.protocols.APIResultCode;
import com.tch.domain.protocols.ExcelExport;
import com.tch.service.system.RoleService;
import com.tch.service.system.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * 用户管理Controller
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController{

    @Autowired
    private UserInfoService userService;
    @Autowired
    private RoleService roleService;

    /**
     *跳转到用户列表页面
     * @return
     */
    @RequestMapping("/user_list")
    public String toUserListPage() {
        return "system/user_list";
    }
    /**
     * 加载用户列表List
     * @param user
     * @return
     */
    @RequestMapping("/ajax_user_list")
    @ResponseBody
    public APIResult ajaxUserList(UserInfo user,int page,int rows){
        Sort sort=new Sort(Sort.Direction.ASC,"userid");
        Pageable pageable=new PageRequest(page,rows,sort); 
        Page<UserInfo> pageList=userService.selectUserResultPageList(user,pageable);

        List<UserInfo> list=pageList.getContent();

        JSONObject.toJSONString(list);
        return success(list);
    }

    /**
     * 导出用户列表信息
     * @param user 用户实体
     * @return
     */
    @RequestMapping("/excel_users_export")
    public ModelAndView excelUsersExport(UserInfo user){
        ExcelExport excelExport = userService.excelExportUserList(user);
        return CommonHelper.getExcelModelAndView(excelExport,"用户列表");
    }

    /**
     * 跳转到用户新增页面
     * @return
     */
    @RequestMapping("/user_add")
    public String toUserAddPage(Model model) {
        //新增页面标识
        model.addAttribute("pageFlag", "addPage");
        return "system/user_edit";
    }

    /**
     * 跳转到用户修改页面
     * @param userId 用户Id
     * @return
     */
    @RequestMapping("/user_update")
    public String userUpdatePage(Model model,Integer userId){
        UserInfo user = userService.selectUserById(userId);
        //修改页面标识
        model.addAttribute("pageFlag", "updatePage");
        model.addAttribute("user", user);
        return "system/user_edit";
    }
    /**
     * 保存用户信息
     * @param user 用户实体
     * @return
     */
    @RequestMapping("/ajax_save_user")
    @ResponseBody
    public APIResult ajaxSaveUser(UserInfo user){
        try {
            return  userService.saveOrUpdateUser(user);
        } catch (Exception e) {
            logger.error("保存用户信息方法内部错误",e);
            return error("保存用户信息方法内部错误");
        }
    }

    /**
     * 失效用户
     * @param userId 用户Id
     * @return
     */
    @RequestMapping("/ajax_user_fail")
    @ResponseBody
    public APIResult ajaxUserFail(Integer userId){
        try {
            return userService.updateUserStatus(userId);        
        } catch (Exception e) {
            logger.error("失效用户方法内部错误",e);
            return error("失效用户方法内部错误");
        }
    }

    /**
     * 批量失效用户
     * @param userIds 用户Id
     * @return
     */
    @RequestMapping("/ajax_user_batch_fail.do")
    @ResponseBody
    public APIResult ajaxUserBatchFail(@RequestParam(value = "userIds[]") Integer[] userIds){
        try {
            return userService.updateUserBatchStatus(userIds); 
        } catch (Exception e) {
            logger.error("批量失效用户方法内部错误",e);
            return error("失效用户方法内部错误");
        }
    }

    /**
     * 跳转到用户角色分配页面
     * @param userId 用户Id
     * @return
     */
    @RequestMapping("/user_grant")
    
    public String userGrantPage(Model model,Integer userId){
        UserInfo user = userService.selectUserById(userId);

        Object userstr=JSON.toJSON(user);


        model.addAttribute("user", userstr);
        return "system/user_grant";
    }


    /**
     * 查询待分配的角色信息(用以给用户分配角色时显示)
     * @param  userid 用户userid
     */
    @RequestMapping("/ajax_undistributed_role_list")
    @ResponseBody
    public APIResult ajaxUndistributedRoleList(String userid){
        List<SysRole> list=roleService.usernotrole(userid);
        JSONObject.toJSONString(list);
        return success(list);
    }
//    /**
//     * 查询状态为有效,已分配的角色信息(用已用户分配角色显示)
//     * @param roleIds 已分配角色Id
//     */
//    @RequestMapping("/ajax_deceased_role_list.do")
//    @ResponseBody
//    public String ajaxDeceasedRoleList(String roleIds){
//        return roleService.selectDeceasedUserRoleByRoleIdList(roleIds);
//    }

    /**
     * 保存用户分配角色信息
     * @param userId 用户Id
     * @param roleIds 分配的角色信息
     * @return
     */
    @RequestMapping("/ajax_save_user_role")
    @ResponseBody
    public APIResult ajaxSaveUserRole(Integer userId,String roleIds){
        try {
           return  userService.saveUserRole(userId,roleIds);
        } catch (Exception e) {
            logger.error("用户分配角色信息方法内部错误",e);
            return new APIResult(APIResultCode.USER_ROLE_SAVE_ERROR);
        }
    }


}
