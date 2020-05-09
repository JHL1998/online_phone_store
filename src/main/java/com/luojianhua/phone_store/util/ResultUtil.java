package com.luojianhua.phone_store.util;

import com.luojianhua.phone_store.vo.PhoneResultVO;

/**
 * 工具类，封装最终对象
 */
public class ResultUtil {

    public static PhoneResultVO success(Object data){
        PhoneResultVO phoneResultVO=new PhoneResultVO();
        phoneResultVO.setCode(0);
        phoneResultVO.setMsg("成功");
        phoneResultVO.setData(data);
        return phoneResultVO;
    }
    public static PhoneResultVO error(String error){
        PhoneResultVO phoneResultVO=new PhoneResultVO();
        // 1表示失败
        phoneResultVO.setCode(1);
        phoneResultVO.setMsg(error);
        phoneResultVO.setData(null);
        return phoneResultVO;
    }

}
