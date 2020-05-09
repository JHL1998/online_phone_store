package com.luojianhua.phone_store.controller;

import com.luojianhua.phone_store.service.PhoneService;
import com.luojianhua.phone_store.util.ResultUtil;
import com.luojianhua.phone_store.vo.PhoneResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/phone")
public class PhoneController {

    @Autowired
    private PhoneService phoneService;

    /**
     * 查询首页数据
     * @return
     */
    @GetMapping("/index")
    public PhoneResultVO index(){

      //表示成功
      return    ResultUtil.success(phoneService.findDataVO());

    }

    /**
     * 根据类型查询手机
     * @param categoryType
     * @return
     */
    @GetMapping("/findByCategoryType/{categoryType}")
    public PhoneResultVO findByCategoryType(@PathVariable("categoryType") Integer categoryType){
          return ResultUtil.success(phoneService.findBycategoryType(categoryType));
    }

    /**
     * 查询手机规格
     * @param phoneId
     * @return
     */
    @GetMapping("/findSpecsByPhoneId/{phoneId}")
    public PhoneResultVO findSpecsByPhoneId(@PathVariable("phoneId") Integer phoneId){
        return ResultUtil.success(phoneService.findSpecsByPhoneId(phoneId));
    }


}
