package com.tch.common.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shz on 2017/7/13.
 */
public class testlog {
    Logger logger= LoggerFactory.getLogger(testlog.class);

    public static void main(String[] args) {
        Logger logger= LoggerFactory.getLogger(testlog.class);
        logger.info("============info===========");
        logger.trace("===========trace==========");
        System.out.println("this is log");
        logger.debug("===========debug============");
        logger.error("===========error==========");
    }
    public void testing(){
        logger.info("============info===========");
        logger.trace("===========trace==========");
        System.out.println("这里应该有打印日志");
        logger.debug("===========debug============");
        logger.error("===========error==========");
    }
}
