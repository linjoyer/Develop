package com.tch.common.filter;//package com.tch.common.filter;
//
//import com.tch.dao.primary.SystemLogRepository;
//import com.tch.domain.entity.system.SystemLog;
//import com.tch.domain.entity.system.UserInfo;
//import org.apache.shiro.SecurityUtils;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.security.auth.Subject;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.Date;
//
///**
// * Created by shz on 2017/10/25.
// */
//@Aspect
//public class LogAspect {
//    @Autowired
//    SystemLogRepository logRepository;
//    //添加操作切面
//    @Pointcut("execution(* comnew.xxx.controller.*.insert*(..))")
//    public void insertServiceCall(){}
//    //更新操作切面
//    @Pointcut("execution(* comnew.xxx.controller.*.update*(..))")
//    public void updateServiceCall(){}
//    //删除操作切面
//    @Pointcut("execution(* comnew.xxx.controller.*.delete*(..))")
//    public void deleteServiceCall(){}
//
//    @AfterReturning(value ="insertServiceCall()",argNames="rtv", returning="rtv")
//    public void insertServiceCallCalls(JoinPoint joinPoint,Object rtv){
//        //获取登录用户信息
//        org.apache.shiro.subject.Subject currentUser= SecurityUtils.getSubject();
//        UserInfo user=currentUser.getPrincipals().oneByType(UserInfo.class);
//        //判断是否登录
//        if(user==null){
//            return;
//        }
//        //判断参数
//        if(joinPoint.getArgs()==null){
//            return;
//        }
//        //获取调用的方法名
//        String  methodName=joinPoint.getSignature().getName();
//        //获取操作内容
//        String  opcontent=adminOptionContent(joinPoint.getArgs(),methodName);
//        //保存日志信息
//        SystemLog log=new SystemLog();
//        log.setUserid(Long.valueOf(user.getUserid()));
//        log.setContent(opcontent);
//        log.setCreatedate(new Date());
//        log.setOperation(methodName);
//        log.setUsername(user.getName());
//        logRepository.save(log);
//    }
//    /**
//     * 使用Java反射来获取被拦截方法(insert、update)的参数值，
//     * 将参数值拼接为操作内容
//     */
//    public String adminOptionContent(Object[] args,String name){
//        if (args==null){
//            return null;
//        }
//        StringBuffer rs=new StringBuffer();
//        rs.append(name);
//        String className="";
//        int index=1;
//        //遍历参数对象
//        for(Object info:args){
//            //获取对象类型
//            className=info.getClass().getName();
//            className=className.substring(className.lastIndexOf(".") + 1);
//            rs.append("[参数" + index + "，类型：" + className + "，值：");
//            //获取对象所有方法
//            Method[] methods=info.getClass().getMethods();
//            //遍历方法，判断get（）
//            for(Method mt:methods){
//                String methodName=mt.getName();
//                //判断是不是get方法
//                if(methodName.indexOf("get")==-1){//不是get方法
//                    //不处理
//                    continue;
//                }
//                Object rsvalue="";
//                //调用get方法
//                try {
//                    rsvalue=mt.invoke(info);
//                    //如果没有返回值
//                    if(rsvalue==null){
//                        continue;
//                    }
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//                //将值加入到内容中
//                rs.append("(" + methodName + " : " + rsvalue + ")");
//            }
//            rs.append("]");
//            index++;
//        }
//        return rs.toString();
//    }
//
//}
