package com.luojianhua.phone_store.service.impl;

import com.luojianhua.phone_store.entity.BuyerAddress;
import com.luojianhua.phone_store.form.AddressForm;
import com.luojianhua.phone_store.repository.BuyerAddressRepository;
import com.luojianhua.phone_store.service.AddressService;
import com.luojianhua.phone_store.vo.AddressVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    BuyerAddressRepository buyerAddressRepository;

    @Override
    public List<AddressVO> findAll() {

       List<AddressVO> addressVOList=buyerAddressRepository.findAll().stream().
               map(e->new AddressVO(e.getAreaCode(),e.getAddressId(),e.getBuyerName()
               ,e.getBuyerPhone(),e.getBuyerAddress())).collect(Collectors.toList());

       return addressVOList;
    }

    @Override
    public void saveOrUpdate(AddressForm addressForm) {
        //先判断添加还是修改
        BuyerAddress buyerAddress;
        if(addressForm.getId()==null){
            //添加操作
            buyerAddress=new BuyerAddress();
        }else{
            //修改操作
            buyerAddress=buyerAddressRepository.findById(addressForm.getId()).get();

        }
        buyerAddress.setBuyerName(addressForm.getName());
        buyerAddress.setBuyerPhone(addressForm.getTel());
        //拼接地址信息
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(addressForm.getProvince())
                .append(addressForm.getCity())
                .append(addressForm.getCounty())
                .append(addressForm.getAddressDetail());

        buyerAddress.setBuyerAddress(stringBuffer.toString());
        buyerAddress.setAreaCode(addressForm.getAreaCode());

       buyerAddressRepository.save(buyerAddress);
    }
}
