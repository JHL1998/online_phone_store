package com.luojianhua.phone_store.repository;

import com.luojianhua.phone_store.entity.PhoneSpecs;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PhoneSpecsRepositoryTest {

    @Autowired
    private PhoneSpecsRepository repository;

    @Test
    void findAll(){
        List<PhoneSpecs> list=repository.findAll();
        for (PhoneSpecs phoneSpecs : list) {
            System.out.println(phoneSpecs);
        }
    }

    @Test
    void findByPhoneId(){
        List<PhoneSpecs>list=repository.findAllByPhoneId(1);
        for (PhoneSpecs phoneSpecs : list) {
            System.out.println(phoneSpecs);
        }
    }


}