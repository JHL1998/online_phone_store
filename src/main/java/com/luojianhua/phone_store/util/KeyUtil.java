package com.luojianhua.phone_store.util;

import java.util.Random;

/**
 * 生成随机数
 */
public class KeyUtil {
    public static synchronized  String createUniquKey(){
        Random random=new Random();
      Integer key=  random.nextInt(900000)+100000;
      return System.currentTimeMillis()+String.valueOf(key);
    }

}
