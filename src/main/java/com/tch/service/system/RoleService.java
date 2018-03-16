package com.tch.service.system;

import com.tch.domain.entity.system.SysRole;
import com.tch.domain.entity.system.Tree;
import com.tch.domain.protocols.APIResult;
import com.tch.domain.protocols.ExcelExport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by shz on 2017/8/15.
 */
public interface RoleService {
	
	SysRole selectRoleById(Integer roleId);
    Page<SysRole> selectRoleResultPageList(SysRole role, Pageable pageable);
    List<SysRole> usernotrole(String userid);
    APIResult saveOrUpdateRole(SysRole role);
    APIResult updateRoleStatus(Integer roleId);
    APIResult updateRoleBatchStatus(Integer[] roleIds);
    SysRole selectRoleResourcesByRoleId(Integer roleId);

    List<Tree> selectResourceAllTree();
    APIResult saveOrUpdateRolePer(Integer roleId, Integer[] resourceIds);
    

    ExcelExport excelExportRoleList(SysRole role);
}
