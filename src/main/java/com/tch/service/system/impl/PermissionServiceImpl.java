package com.tch.service.system.impl;

import com.tch.dao.primary.PermissionRepository;
import com.tch.domain.entity.system.SysPermission;
import com.tch.domain.protocols.APIResult;
import com.tch.domain.protocols.APIResultCode;
import com.tch.domain.protocols.ExcelExport;
import com.tch.service.system.PermissionService;

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
import java.util.List;

/**
 * Created by shz on 2017/8/15.
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
	final Logger log= LoggerFactory.getLogger(PermissionServiceImpl.class);
    @Autowired
    PermissionRepository repository;
    @Override
    public Page<SysPermission> selectPerResultPageList(final SysPermission permission, Pageable pageable) {
        Specification specification=new Specification<SysPermission>() {
            @Override
            public Predicate toPredicate(Root<SysPermission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates=new ArrayList<Predicate>();
                if (permission.getName()!=null&&!"".equals(permission.getName()))
                    predicates.add(criteriaBuilder.like(root.<String>get("name"),"%"+permission.getName()+"%"));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return repository.findAll(specification,pageable);
    }
	@Override
	public APIResult saveOrUpdatePer(SysPermission sysPermission) {
		try {
			repository.saveAndFlush(sysPermission);
		} catch (Exception e) {
			log.error("保存菜单信息方法内部错误",e);
	        e.printStackTrace();
		}		
		return new APIResult(APIResultCode.SUCCESS);
	}
	
	@Override
	public List<SysPermission> getPer() {
		return repository.rolenotperm();
		 
	}
	@Override
	public List<SysPermission> getRemovePer(Long id) {
		return repository.roleperm(id);
	}

    /**
     * 权限列表EXCEL导出
     * @param  per 权限实体
     * @return
     */
    public ExcelExport excelExportPerList(SysPermission per){
        ExcelExport excelExport = new ExcelExport();

        List<SysPermission> roleList = repository.findAll();
        excelExport.addColumnInfo("资源名称","name");
        excelExport.addColumnInfo("资源类型","resourceType");
        excelExport.addColumnInfo("资源路径","url");
        excelExport.addColumnInfo("父编号","parentId");
        excelExport.addColumnInfo("权限字符串","permission");
        excelExport.addColumnInfo("状态","available");

        excelExport.setDataList(roleList);
        return excelExport;
    }
}
