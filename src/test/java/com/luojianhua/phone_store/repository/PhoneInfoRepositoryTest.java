package com.luojianhua.phone_store.repository;

import com.luojianhua.phone_store.entity.PhoneInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PhoneInfoRepositoryTest {

     @Autowired
    private PhoneInfoRepository repository;

     @Test
     void findAll(){
         List<PhoneInfo> list=repository.findAll();
         for (PhoneInfo phoneInfo : list) {
             System.out.println(phoneInfo);
         }
     }

     @Test
    void findAllByCategoryType(){
         List<PhoneInfo>list=repository.findAllByCategoryType(1);
         for (PhoneInfo phoneInfo : list) {
             System.out.println(phoneInfo);
         }
     }


}