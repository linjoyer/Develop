package com.tch.dao.primary;

import com.tch.domain.entity.system.SysPermission;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by shz on 2017/8/16.
 */
public interface PermissionRepository extends JpaRepository<SysPermission,Long>,JpaSpecificationExecutor<SysPermission>{

	//查询用户未拥有权限
    //select * from sys_permission where id not in(select permission_id from sys_role_permission where role_id=?1)
    
    @Query(value="select * from sys_permission where available=1",nativeQuery=true)
    List<SysPermission> rolenotperm();
    //排除自身的其他权限
    @Query(value="select * from sys_permission where available=1 and id!=?1",nativeQuery=true)
    List<SysPermission> roleperm(Long id);
    
    
    //插入角色权限
    @Modifying
    @Query(value="insert into sys_role_permission (role_id,permission_id) values (?1,?2) ",nativeQuery=true)
    void insertRolePer(Integer roleId, Integer resourceId);

    //删除用户原有角色
    @Modifying
    @Query(value = "delete  from sys_role_permission where role_id=?1 ",nativeQuery = true)
    void deleteRolePer(Integer roleId);
    

}
