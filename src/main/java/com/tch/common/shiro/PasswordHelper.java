package com.tch.common.shiro;

import com.tch.domain.entity.system.UserInfo;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by shz on 2017/8/16.
 * 密码加密帮助类
 */
@Service
public class PasswordHelper {

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    @Value("${password.algorithmName}")
    private String algorithmName;
    @Value("${password.hashIterations}")
    private int hashIterations;

    public void encryptPassword(UserInfo user) {

        user.setSalt(randomNumberGenerator.nextBytes().toHex());

        String newPassword = new SimpleHash(
                algorithmName,           //加密算法
                user.getPassword(),      //密码
                ByteSource.Util.bytes(user.getCredentialsSalt()),  //salt盐   username + salt
                hashIterations   //迭代次数
        ).toHex();

        user.setPassword(newPassword);
    }
    public String encryptPassword(String username,String psw,String salt) {
        String newPassword = new SimpleHash(
                algorithmName,           //加密算法
                psw,                     //密码
                ByteSource.Util.bytes(username + salt),  //salt盐   username + salt
                hashIterations          //迭代次数
        ).toHex();

        return newPassword;
    }
}
