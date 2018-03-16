//package com.tch.common.shiro;
//
//
//import com.tch.domain.entity.system.SysPermission;
//import com.tch.domain.entity.system.SysRole;
//import com.tch.domain.entity.system.UserInfo;
//import com.tch.service.base.ManagerInfoService;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.cas.CasRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.annotation.PostConstruct;
//
///**
//
// * 安全数据源
// */
//public class MyShiroCasRealm extends CasRealm {
//
//    private static final Logger logger = LoggerFactory.getLogger(MyShiroCasRealm.class);
//
//    @Autowired
//    ManagerInfoService managerInfoService;
//
//    @PostConstruct
//    public void initProperty(){
////      setDefaultRoles("ROLE_USER");
//        setCasServerUrlPrefix(ShiroCasConfiguration.casServerUrlPrefix);
//        // 客户端回调地址
//        setCasService(ShiroCasConfiguration.shiroServerUrlPrefix + ShiroCasConfiguration.casFilterUrlPattern);
//    }
//
////    /**
////     * 1、CAS认证 ,验证用户身份
////     * 2、将用户基本信息设置到会话中(不用了，随时可以获取的)
////     */
////    @Override
////    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
////
////        AuthenticationInfo authc = super.doGetAuthenticationInfo(token);
////
////        String username = (String) authc.getPrincipals().getPrimaryPrincipal();
////
////        UserInfo2 user = managerInfoService.findByUsername(username);
////        //将用户信息存入session中
////        SecurityUtils.getSubject().getSession().setAttribute("user", user);
////
////        return authc;
////    }
//
//    /**
//     * 权限认证，为当前登录的Subject授予角色和权限
//     * @see ：本例中该方法的调用时机为需授权资源被访问时
//     * @see ：并且每次访问需授权资源时都会执行该方法中的逻辑，这表明本例中默认并未启用AuthorizationCache
//     * @see ：如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（也就是cache时间，在ehcache-shiro.xml中配置），超过这个时间间隔再刷新页面，该方法会被执行
//     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        logger.info("##################执行Shiro权限认证##################");
//              /*
//        * 当没有使用缓存的时候，不断刷新页面的话，这个代码会不断执行，
//        * 当其实没有必要每次都重新设置权限信息，所以我们需要放到缓存中进行管理；
//        * 当放到缓存中时，这样的话，doGetAuthorizationInfo就只会执行一次了，
//        * 缓存过期之后会再次执行。
//        */
////        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
//
//        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        String username = (String)principals.getPrimaryPrincipal();
//        UserInfo managerInfo = managerInfoService.findByUsername(username);
//
//        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
////     UserInfo2 userInfo = userInfoService.findByUsername(username)
//
//        //设置相应角色的权限信息
//        for(SysRole role:managerInfo.getRoleList()){
//            //设置有效的角色
//            if(role.getAvailable()){
//                authorizationInfo.addRole(role.getRole());
//                for(SysPermission p:role.getPermissions()){
//                    //设置权限
//                    authorizationInfo.addStringPermission(p.getPermission());
//                }
//            }
//        }
//        return authorizationInfo;
//    }
//
//}