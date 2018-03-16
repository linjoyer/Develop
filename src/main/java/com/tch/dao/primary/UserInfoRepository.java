package com.tch.dao.primary;

import com.tch.domain.entity.system.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * UserInfo持久化类
 *
 * @author Administrator
 *
 */
public interface UserInfoRepository extends CrudRepository<UserInfo, Integer>,JpaRepository<UserInfo,Integer>,JpaSpecificationExecutor<UserInfo> {
    /** 通过username查找用户信息 **/
    public UserInfo findByUsername(String username);
    //分页查询
    Page<UserInfo> findAllByUsernameAndName(Pageable pageable, String username, String name);
    //用户重复统计
    int countUserInfosByUsernameEquals(String username);
    //用户重复统计
    int countUserInfoByUsernameEqualsAndUseridIsNot(String username, Integer userid);
    //根据id查询用户
    UserInfo findByUseridEquals(Integer userid);
    //更新用户状态
    @Modifying 
    @Query(value="update user_info  set state = ?#{#state} where userid = ?#{#userid}",nativeQuery = true)
    void updateUserState(Integer state, Integer userid);


}
