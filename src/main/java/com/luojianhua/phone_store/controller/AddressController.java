package com.luojianhua.phone_store.controller;

import com.luojianhua.phone_store.exception.PhoneException;
import com.luojianhua.phone_store.form.AddressForm;
import com.luojianhua.phone_store.service.AddressService;
import com.luojianhua.phone_store.util.ResultUtil;
import com.luojianhua.phone_store.vo.PhoneResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/address")
@Slf4j
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("/list")
    public PhoneResultVO list() {
        return ResultUtil.success(addressService.findAll());
    }

    @PostMapping("/create")
    //从之前定义好的格式 addressform里面取
    public PhoneResultVO create(@Valid @RequestBody AddressForm addressForm, BindingResult bindingResult){
     if(bindingResult.hasErrors()){
          log.error("【添加地址】参数错误,addressForm={}",addressForm);
          throw new PhoneException(bindingResult.getFieldError().getDefaultMessage());
     }

       //如果没错误
        addressService.saveOrUpdate(addressForm);
     return ResultUtil.success(null);
    }


    @PutMapping("/update")
    public PhoneResultVO update(@Valid @RequestBody
                                            AddressForm addressForm,
                                BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.error("【修改地址】参数错误,addressForm={}",addressForm);
            throw  new PhoneException(bindingResult.getFieldError().getDefaultMessage());
        }
        addressService.saveOrUpdate(addressForm);
        return ResultUtil.success(null);
    }
}
