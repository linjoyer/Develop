package com.tch.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class CommonUtil {

  public static String uuid(){
    return UUID.randomUUID().toString();
  }

  public static String now(String pattern) {
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    return sdf.format(new Date());
  }
}
