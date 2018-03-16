package com.tch.service.system.impl;


import com.tch.dao.primary.RoleRepository;
import com.tch.dao.primary.UserInfoRepository;
import com.tch.domain.entity.system.UserInfo;
import com.tch.domain.protocols.APIResult;
import com.tch.domain.protocols.APIResultCode;
import com.tch.domain.protocols.ExcelExport;
import com.tch.service.system.UserInfoService;
import com.tch.common.shiro.PasswordHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shz on 2017/7/27.
 */
@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {
    final Logger log= LoggerFactory.getLogger(UserInfoServiceImpl.class);
    @Resource
    UserInfoRepository userInfoRepository;
    @Resource
    RoleRepository roleRepository;
    @Autowired
    PasswordHelper passwordHelper;
    //根据用户名查询用户，复杂条件查询
    @Override
    public UserInfo fingUserByname(final UserInfo t_user) {

        UserInfo resultuser = null;
        Specification specification = new Specification<UserInfo>() {
            @Override
            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (t_user.getUsername() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("username"), t_user.getUsername()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
//        resultuser = (UserInfo2) this.tuserRepository.findOne(specification);
        return resultuser;
    }
    //根据用户名查询用户
    @Transactional(readOnly=true)
    @Override
    public UserInfo findByUsername(String username) {
        return userInfoRepository.findByUsername(username);
    }
    //根据ID查询用户
    @Override
    public UserInfo selectUserById(Integer id) {
        return userInfoRepository.findByUseridEquals(id);
    }
    //分页查询用户信息
    @Override
    public Page<UserInfo> selectUserResultPageList(final UserInfo user, Pageable pageable) {

        Specification specification=new Specification<UserInfo>() {
            @Override
            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if(user.getUsername()!=null&&!"".equals(user.getUsername())){
                    predicates.add(criteriaBuilder.equal(root.get("username"),user.getUsername()));
                }
                if(user.getName()!=null&&!"".equals(user.getName())){
                    //由于jpa底层是hibernate ，like 不会自动加上%，所以需要手动加。
                    predicates.add(criteriaBuilder.like(root.<String>get("name"),"%"+user.getName()+"%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return userInfoRepository.findAll(specification,pageable);
    }
    //保存或者更新用户信息
    @Override
    public APIResult saveOrUpdateUser(UserInfo user) {
        log.info("保存用户信息开始");
        long start = System.currentTimeMillis();
    try{
        //保存用户信息
        if (null== user.getUserid()) {
            user.setPassword("123456");
            //密码盐值加密
            passwordHelper.encryptPassword(user);
            //验证用户账号唯一性
            int checkuser =userInfoRepository.countUserInfosByUsernameEquals(user.getUsername());
            if(checkuser !=0) {
                return new APIResult(APIResultCode.USER_LOGIN_NAME_EXIST);
            }
        }else{
            //验证用户账号唯一性
            int checkuser =userInfoRepository.countUserInfoByUsernameEqualsAndUseridIsNot(user.getUsername(),user.getUserid());
            if(checkuser !=0) {
                return new APIResult(APIResultCode.USER_LOGIN_NAME_EXIST);
            }else {
				UserInfo info=userInfoRepository.findByUseridEquals(user.getUserid());
				user.setPassword(info.getPassword());
				user.setSalt(info.getSalt());
			}
        }
        //保存
        userInfoRepository.saveAndFlush(user);
    } catch (Exception e) {
        log.error("保存用户信息方法内部错误",e);
        e.printStackTrace();
    }finally {
        log.info("保存用户信息结束,用时" + (System.currentTimeMillis() - start) + "毫秒");
    }
        return new APIResult(APIResultCode.SUCCESS);
    }
    //更新用户状态
    @Override
    public APIResult updateUserStatus(Integer userid) {
        log.info("更新用户状态开始");
        userInfoRepository.updateUserState(1, userid); 	
    	return new APIResult(APIResultCode.SUCCESS);
    }
    //批量更新用户状态
    @Override
	public APIResult updateUserBatchStatus(Integer[] userIds) {
		for(int i=0;i<userIds.length;i++){
		userInfoRepository.updateUserState(1, userIds[i]); 				
		}
		return new APIResult(APIResultCode.SUCCESS);
	}
    

    @Override
    public APIResult saveUserRole(Integer userId, String roleIds) {
        log.info("保存用户分配角色信息开始");
        System.out.println("userId="+userId+",,,,,,,roleIds="+roleIds);
        long start = System.currentTimeMillis();
        try{
            if(userId!=null) {
                //删除用户原有权限
                roleRepository.deleteUserRole(userId);
            }
            for (String roleId : roleIds.split(",")) {
                roleRepository.saveuserrole(userId, Integer.valueOf(roleId));
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("保存用户分配角色信息失败"+e.getMessage());
            return new APIResult(APIResultCode.USER_ROLE_SAVE_ERROR);
        }finally {
            log.info("保存用户分配角色信息结束,用时" + (System.currentTimeMillis() - start) + "毫秒");
        }
        return new APIResult(APIResultCode.SUCCESS);
    }
    /**
     * 用户列表EXCEL导出
     * @param user 用户实体
     * @return
     */
    @Override
    public ExcelExport excelExportUserList(UserInfo user){
        ExcelExport excelExport = new ExcelExport();
        Sort sort=new Sort(Sort.Direction.ASC,"userid");
        List<UserInfo> userList = userInfoRepository.findAll(sort);
        excelExport.addColumnInfo("登陆账号","username");
        excelExport.addColumnInfo("用户姓名","name");
        excelExport.addColumnInfo("密码","password");
        excelExport.addColumnInfo("密码加密盐","salt");
        excelExport.addColumnInfo("用户状态","state");

//        excelExport.addColumnInfo("拥有角色","roleList");
        excelExport.setSheetName("用户信息");
        excelExport.setDataList(userList);
        return excelExport;
    }


}
