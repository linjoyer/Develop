package com.tch.controller.system;


import com.tch.common.utils.CommonHelper;
import com.tch.controller.base.BaseController;
import com.tch.domain.entity.system.SysPermission;
import com.tch.domain.protocols.APIResult;
import com.tch.domain.protocols.ExcelExport;
import com.tch.service.system.PermissionService;


import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 资源Controller
 *
 */
@Controller
@RequestMapping("per")
public class PermissionController extends BaseController{
    @Autowired
    PermissionService permissionService;
    /**
     *跳转到资源列表页面
     * @return
     */
    @RequestMapping("/per_list")
    public String toResListPage() {
        return "system/per_list";
    }

    /**
     * 加载资源列表List
     * @return
     */
    @RequestMapping("/ajax_per_list")
    @ResponseBody
    public APIResult ajaxResourceList(SysPermission permission, int page, int rows){
        Sort sort=new Sort(Sort.Direction.ASC,"id");
        Pageable pageable=new PageRequest(page,rows,sort);
        Page<SysPermission> pageList=permissionService.selectPerResultPageList(permission,pageable);
        List<SysPermission> list=pageList.getContent();
        System.out.println(list.toString());

        return success(list);
    }

    /**
     * 选择图标
     * @return
     */
    @RequestMapping("/res_img.do")
    public String toResImgPage() {
        return "system/res_img";
    }
//    /**
//     * 权限新增页面
//     * @return
//     */
    @RequestMapping("/res_edit.do")
    public String toResEditPage(Model model) {
    	
    	List<SysPermission> list= permissionService.getPer();
    	model.addAttribute("perlist",list);
        return "system/per_edit";
    }
    //权限编辑页面
    @RequestMapping("res_update.do")
    public String perUpdatePage(Model model,Long id){
    	
    	List<SysPermission> list= permissionService.getRemovePer(id);
    	model.addAttribute("perlist",list);  	
    	return "system/per_edit";
    }
    
    
    
    
    //权限保存
    @RequestMapping("ajax_save_per")
    @ResponseBody
    public APIResult  ajaxSavePer(SysPermission sysPermission){
    		System.out.println("进入ajax_save_per");
			return permissionService.saveOrUpdatePer(sysPermission);
    }

    /**
     * 导出用户列表信息
     * @param per 用户实体
     * @return
     */
    @RequestMapping("/excel_per_export")
    public ModelAndView excelUsersExport(SysPermission per){
        ExcelExport excelExport = permissionService.excelExportPerList(per);
        return CommonHelper.getExcelModelAndView(excelExport,"资源权限列表");
    }
    
//
//    @RequestMapping("/ajax_res_menu_top.do")
//    @ResponseBody
//    public String ajaxResMenuTop(){
//        return resourceService.selectResMenuTop();
//    }
//
//    @RequestMapping("/ajax_res_menu_left.do")
//    @ResponseBody
//    public String ajaxResMenuLeft(Integer resParentid){
//        return resourceService.selectResLevelListByParentid(resParentid);
//    }
//



}
