package com.luojianhua.phone_store.service.impl;

import com.luojianhua.phone_store.form.AddressForm;
import com.luojianhua.phone_store.service.AddressService;
import com.luojianhua.phone_store.vo.AddressVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AddressServiceImplTest {

    @Autowired
    private AddressService addressService;
    @Test
    void findAll() {
        List<AddressVO> list = addressService.findAll();
    }

    @Test
    void saveOrUpdate(){
        AddressForm addressForm=new AddressForm();

        addressForm.setName("Lisa");
        addressForm.setTel("13247561274");
        addressForm.setAreaCode("432000");
        addressForm.setProvince("湖北省");
        addressForm.setCity("荆州市");
        addressForm.setCounty("沙市区");
        addressForm.setAddressDetail("沙市一中");
        addressService.saveOrUpdate(addressForm);
    }
}

