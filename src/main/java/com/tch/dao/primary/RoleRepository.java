package com.tch.dao.primary;

import com.tch.domain.entity.system.SysPermission;
import com.tch.domain.entity.system.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by shz on 2017/8/15.
 */
public interface RoleRepository extends JpaRepository<SysRole,Long>,JpaSpecificationExecutor<SysRole> {
    //查询用户未拥有的角色
    @Query(value = "select * from sys_role where id not in (select role_id from sys_user_role where userid=?1)",nativeQuery = true)
    List<SysRole> usernotrole(String userid);
    //插入用户角色
    @Modifying
    @Query(value = "insert into sys_user_role(userid,role_id) values(?1,?2)",nativeQuery = true)
    void saveuserrole(Integer userid, Integer roleid);
    //删除用户原有角色
    @Modifying
    @Query(value = "delete  from sys_user_role where userid=?1 ",nativeQuery = true)
    void deleteUserRole(Integer userid);
    //查询用户信息
    SysRole findByIdEquals(Integer roleid);  
    //更新角色信息
    @Modifying
    @Query(value="update sys_role set available = ?1 where id=?2",nativeQuery=true)
    void updateRoleStatus(Integer available, Integer roleid);
    
    
    
}
