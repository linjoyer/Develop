package com.tch.service.system.impl;

import com.tch.dao.primary.PermissionRepository;
import com.tch.dao.primary.RoleRepository;
import com.tch.domain.entity.system.SysPermission;
import com.tch.domain.entity.system.SysRole;
import com.tch.domain.entity.system.Tree;
import com.tch.domain.protocols.APIResult;
import com.tch.domain.protocols.APIResultCode;
import com.tch.domain.protocols.ExcelExport;
import com.tch.service.system.RoleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shz on 2017/8/15.
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	final Logger log= LoggerFactory.getLogger(RoleServiceImpl.class);
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PermissionRepository permissionrepository;
    @Override
    public Page<SysRole> selectRoleResultPageList(final SysRole role, Pageable pageable) {
        Specification specification=new Specification<SysRole>() {
            @Override
            public Predicate toPredicate(Root<SysRole> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates=new ArrayList<Predicate>();
                if(role.getDescription()!=null&&!"".equals(role.getDescription()))
                    predicates.add(criteriaBuilder.like(root.<String>get("description"),"%"+role.getDescription()+"%"));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return roleRepository.findAll(specification,pageable);
    }

    @Override
    public List<SysRole> usernotrole(String userid) {
        return roleRepository.usernotrole(userid);
    }

	@Override
	public SysRole selectRoleById(Integer roleId) {
		
		SysRole role=roleRepository.findByIdEquals(roleId);
		return role;
	}

	@Override
	public APIResult saveOrUpdateRole(SysRole role) {		
		try {		
			roleRepository.saveAndFlush(role);			
		} catch (Exception e) {
			log.error("保存用户信息方法内部错误",e);
	        e.printStackTrace();
		}		
		return new APIResult(APIResultCode.SUCCESS);
	}

	@Override
	public APIResult updateRoleStatus(Integer roleId) {
		
		roleRepository.updateRoleStatus(0, roleId);
		return new APIResult(APIResultCode.SUCCESS);
	}

	@Override
	public APIResult updateRoleBatchStatus(Integer[] roleIds) {
		for(int i=0;i<roleIds.length;i++){
			roleRepository.updateRoleStatus(0, roleIds[i]);
		}
		return new APIResult(APIResultCode.SUCCESS);
	}

	@Override
	public SysRole selectRoleResourcesByRoleId(Integer roleId) {
		SysRole role=roleRepository.findByIdEquals(roleId);
		return role;
	}

	@Override
	public List<Tree> selectResourceAllTree() {
		
		//权限集合
		List<SysPermission> list=permissionrepository.rolenotperm();
		// tree 树形集合
        List<Tree> trees = new ArrayList<Tree>();

        if (null != list && !list.isEmpty()) {
            for (SysPermission r : list) {
                Tree tree = new Tree();
                tree.setId(r.getId());
                if (null != r.getParentId()) {
                    tree.setPid(r.getParentId());
                }
                tree.setText(r.getName());
//              tree.setIconCls(r.getResImage());
                Map<String, Object> attr = new HashMap<String, Object>();
                attr.put("url", r.getUrl());
                tree.setAttributes(attr);
                trees.add(tree);
            }
        }
        
        return trees;
	}

	@Override
	public APIResult saveOrUpdateRolePer(Integer roleId, Integer[] resourceIds) {
		
		//删除角色原有权限
		permissionrepository.deleteRolePer(roleId);
		//插入角色权限
		if(null!=resourceIds&&0!=resourceIds.length){
		for(int i=0;i<resourceIds.length;i++){
			permissionrepository.insertRolePer(roleId, resourceIds[i]);
		}		
		}
		
		return new APIResult(APIResultCode.SUCCESS);
	}
	/**
	 * 角色列表EXCEL导出
	 * @param role 角色实体
	 * @return
	 */
	public ExcelExport excelExportRoleList(SysRole role){
		ExcelExport excelExport = new ExcelExport();

		List<SysRole> roleList = roleRepository.findAll();
		excelExport.addColumnInfo("角色名称","role");
		excelExport.addColumnInfo("角色状态","available");
//		excelExport.addColumnInfo("菜单资源","permissions.name");
		excelExport.addColumnInfo("角色说明","description");

		excelExport.setDataList(roleList);
		return excelExport;
	}
}
