package com.luojianhua.phone_store.service;

import com.luojianhua.phone_store.form.AddressForm;
import com.luojianhua.phone_store.vo.AddressVO;

import java.util.List;

public interface AddressService {

     List<AddressVO> findAll();
     void saveOrUpdate(AddressForm addressForm);
}
