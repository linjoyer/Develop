package com.tch.service.system;

import com.tch.domain.entity.system.SysPermission;
import com.tch.domain.protocols.APIResult;
import com.tch.domain.protocols.ExcelExport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by shz on 2017/8/15.
 */
public interface PermissionService {
    Page<SysPermission> selectPerResultPageList(SysPermission permission, Pageable pageable);
    
    APIResult saveOrUpdatePer(SysPermission sysPermission);
    
    List<SysPermission> getPer();
    
    List<SysPermission> getRemovePer(Long id);

    ExcelExport excelExportPerList(SysPermission per);

}
