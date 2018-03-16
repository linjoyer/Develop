 package com.tch.controller.system;

 import com.alibaba.fastjson.JSONObject;
 import com.tch.common.utils.CommonHelper;
 import com.tch.controller.base.BaseController;
 import com.tch.domain.entity.system.SysPermission;
 import com.tch.domain.entity.system.SysRole;
 import com.tch.domain.entity.system.Tree;
 import com.tch.domain.protocols.APIResult;
 import com.tch.domain.protocols.ExcelExport;
 import com.tch.service.system.RoleService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.data.domain.PageRequest;
 import org.springframework.data.domain.Pageable;
 import org.springframework.data.domain.Sort;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.ResponseBody;
 import org.springframework.web.servlet.ModelAndView;

 import java.util.List;

 
/**
 * 角色管理Controller 
 *
 */
@Controller
@RequestMapping("role")
public class RoleController extends BaseController{


    @Autowired
    private RoleService roleService;
//    @Autowired
//    private ResourceService resourceService;


    /**
     *跳转到角色列表页面
     * @return
     */
    @RequestMapping("/role_list")
    public String toRoleListPage() {
        return "system/role_list";
    }
    /**
     * 加载角色列表List
     * @param role 角色实体
     * @return
     */
    @RequestMapping("/ajax_role_list")
    @ResponseBody
    public APIResult ajaxRoleList(SysRole role, int page, int rows){
        Sort sort=new Sort(Sort.Direction.ASC,"id");
        Pageable pageable=new PageRequest(page,rows,sort);
        Page<SysRole> pageList=roleService.selectRoleResultPageList(role,pageable);
        List<SysRole> list=pageList.getContent();
        JSONObject.toJSONString(list);
        System.out.println(list.toString());
        return success(list);
    }

    /**
     * 导出角色列表信息
     * @param role 角色实体
     * @return
     */
    @RequestMapping("/excel_role_export")
    public ModelAndView excelRolesExport(SysRole role){
        ExcelExport excelExport = roleService.excelExportRoleList(role);
        return CommonHelper.getExcelModelAndView(excelExport,"角色列表");
    }



    /**
     * 跳转到角色新增页面
     * @return
     */
    @RequestMapping("/role_add.do")
    public String toRoleAddPage(Model model) {
        //新增页面标识
        model.addAttribute("pageFlag", "addPage");
        return "system/role_edit";
    }

    /**
     * 跳转到角色修改页面
     * @param roleId 角色Id
     * @return
     */
    @RequestMapping("/role_update.do")
    public String roleUpdatePage(Model model,Integer roleId){
        SysRole role = roleService.selectRoleById(roleId);
        //修改页面标识
        model.addAttribute("pageFlag", "updatePage");
        model.addAttribute("role", role);
        return "system/role_edit";
    }

    /**
     * 保存角色信息
     * @param role 角色实体
     * @return
     */
    @RequestMapping("/ajax_save_role.do")
    @ResponseBody
    public APIResult ajaxSaveRole(SysRole role){
         return roleService.saveOrUpdateRole(role);
    }

    /**
     * 失效角色
     * @param roleId 角色Id
     * @return
     */
    @RequestMapping("/ajax_role_fail.do")
    @ResponseBody
    public APIResult ajaxRoleFail(Integer roleId){
        try {
            return roleService.updateRoleStatus(roleId);
        } catch (Exception e) {
            logger.error("失效角色方法内部错误",e);
            return error("失效用户方法内部错误");
        }
    }

    /**
     * 批量失效角色
     * @param roleIds 角色Id
     * @return
     */
    @RequestMapping("/ajax_role_batch_fail.do")
    @ResponseBody
    public APIResult ajaxRoleBatchFail(@RequestParam(value = "roleIds[]") Integer[] roleIds){
        try {
            return roleService.updateRoleBatchStatus(roleIds);
        } catch (Exception e) {
            logger.error("批量失效角色方法内部错误",e);
            return error("批量失效角色方法内部错误");
        }
    }

    /**
     * 角色授权页面
     * @param model
     * @param roleId 角色Id
     * @return
     */
    @RequestMapping("/role_grant.do")
    public String roleGrantPage(Model model,Integer roleId){
        SysRole role = roleService.selectRoleResourcesByRoleId(roleId);
        List<SysPermission> per=role.getPermissions();
        String jper=JSONObject.toJSONString(per);
        model.addAttribute("per",jper); 
        model.addAttribute("role",role);
        return "system/role_grant";
    }

    /**
     * 获取当前用户所属菜单资源Tree菜单展示
     */
    @RequestMapping("/ajax_resource_tree_list")
    @ResponseBody
    public List<Tree> ajaxResourceTreeList( ){
        List<Tree> list=roleService.selectResourceAllTree();
//      JSONObject.toJSONString(list);
        return list;
    }


    /**
     * 保存角色资源信息
     * @param roleId        角色Id
     * @param resourceIds   资源菜单Ids
     * @return
     */
    @RequestMapping("/ajax_save_role_res.do")
    @ResponseBody
    public APIResult ajaxSaveOrUpdateRoleResource(Integer roleId, @RequestParam(value = "resourceIds[]",required = false) Integer[] resourceIds ){
        try {
            return roleService.saveOrUpdateRolePer(roleId,resourceIds);
        } catch (Exception e) {
            logger.error("保存角色信息授权信息方法内部错误",e);
            return error("保存角色信息授权信息方法内部错误");
        }
    }


}
