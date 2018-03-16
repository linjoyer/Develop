package com.tch.service.system;


import com.tch.domain.entity.system.UserInfo;
import com.tch.domain.protocols.APIResult;
import com.tch.domain.protocols.ExcelExport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by shz on 2017/7/27.
 */
public interface UserInfoService {
    UserInfo fingUserByname(UserInfo t_user);

    public UserInfo findByUsername(String username);
    //根据ID查询用户
    UserInfo selectUserById(Integer id);
    //分页查询，多条件查询
    Page<UserInfo> selectUserResultPageList(UserInfo user, Pageable pageable);
    //保存或更新用户
    APIResult saveOrUpdateUser(UserInfo user);
    //更新用户装填
    APIResult updateUserStatus(Integer userid);
    //批量更新用户填装
    APIResult updateUserBatchStatus(Integer[] userIds);
    //保存用户角色
    APIResult saveUserRole(Integer userId, String roleIds);

    ExcelExport excelExportUserList(UserInfo user);

}
