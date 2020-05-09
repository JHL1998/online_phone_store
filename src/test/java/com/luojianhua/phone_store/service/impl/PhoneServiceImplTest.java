package com.luojianhua.phone_store.service.impl;

import com.luojianhua.phone_store.service.PhoneService;
import com.luojianhua.phone_store.vo.DataVO;
import com.luojianhua.phone_store.vo.PhoneInfoVO;
import com.luojianhua.phone_store.vo.SpecsPackageVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PhoneServiceImplTest {

    @Autowired
    private PhoneService phoneService;
    @Test
    void findDataVO() {
        DataVO dataVO=phoneService.findDataVO();

    }

    @Test
    void findByCategoryType(){
        List<PhoneInfoVO> list=phoneService.findBycategoryType(1);
        for (PhoneInfoVO phoneInfoVO : list) {
            System.out.println(phoneInfoVO);
        }
    }

    @Test
    void findSku(){
        SpecsPackageVO specsPackageVO=phoneService.findSpecsByPhoneId(2);
        int id=0;
    }



}