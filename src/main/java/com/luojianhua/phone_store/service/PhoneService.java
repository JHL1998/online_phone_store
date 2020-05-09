package com.luojianhua.phone_store.service;

import com.luojianhua.phone_store.vo.DataVO;
import com.luojianhua.phone_store.vo.PhoneInfoVO;
import com.luojianhua.phone_store.vo.SpecsPackageVO;

import java.util.List;

public interface PhoneService {
      //    /phone/index
       DataVO findDataVO();

      //  /phone/findByCategoryType
      List<PhoneInfoVO> findBycategoryType(Integer categoryType);

      //    /phone/findSpecsByPhoneId
      SpecsPackageVO findSpecsByPhoneId(Integer phoneId);

      //减库存
      void subStock(Integer specsId,Integer quantity);





}
