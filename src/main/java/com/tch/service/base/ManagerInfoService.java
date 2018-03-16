package com.tch.service.base;


import com.tch.domain.entity.system.UserInfo;
import com.tch.dao.primary.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 */

@Service
public class ManagerInfoService {

    @Autowired
    UserInfoRepository managerInfoDAO;

    public UserInfo findByUsername(String username) {
        return managerInfoDAO.findByUsername(username);
    }


}
