package com.luojianhua.phone_store.service.impl;

import com.luojianhua.phone_store.entity.PhoneCategory;
import com.luojianhua.phone_store.enums.ResultEnum;
import com.luojianhua.phone_store.exception.PhoneException;
import com.luojianhua.phone_store.entity.PhoneInfo;
import com.luojianhua.phone_store.entity.PhoneSpecs;
import com.luojianhua.phone_store.repository.PhoneCategoryRepository;
import com.luojianhua.phone_store.repository.PhoneInfoRepository;
import com.luojianhua.phone_store.repository.PhoneSpecsRepository;
import com.luojianhua.phone_store.service.PhoneService;
import com.luojianhua.phone_store.util.PhoneUtil;
import com.luojianhua.phone_store.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PhoneServiceImpl implements PhoneService {


    @Autowired
     private PhoneCategoryRepository phoneCategoryRepository;

    @Autowired
    private PhoneInfoRepository phoneInfoRepository;

    @Autowired
    private PhoneSpecsRepository phoneSpecsRepository;

    @Override
    public DataVO findDataVO() {
        DataVO dataVO=new DataVO();
         //类型
        List<PhoneCategory> phoneCategoryList=phoneCategoryRepository.findAll();
        //将phoneCategoryList转换成前端所需要的phoneCategoryVOList
//        List<PhoneCategoryVO>phoneCategoryVOList=new ArrayList<>();
//        for (PhoneCategory phoneCategory : phoneCategoryList) {
//            PhoneCategoryVO phoneCategoryVO=new PhoneCategoryVO();
//            phoneCategoryVO.setCategoryName(phoneCategory.getCategoryName());
//            phoneCategoryVO.setCategoryType(phoneCategory.getCategoryType());
//            phoneCategoryVOList.add(phoneCategoryVO);
//        }

        //stream流的写法
    List<PhoneCategoryVO> phoneCategoryVoList=   phoneCategoryList.stream().map(e->new PhoneCategoryVO(e.getCategoryName(),e.getCategoryType()))
               .collect(Collectors.toList());

        dataVO.setCategories(phoneCategoryVoList);

        //手机
        List<PhoneInfo>phoneInfoList=phoneInfoRepository.findAllByCategoryType(phoneCategoryList.get(0).getCategoryType());
//         List<PhoneInfoVO>phoneInfoVOList=new ArrayList<>();
//        for (PhoneInfo phoneInfo : phoneInfoList) {
//            PhoneInfoVO phoneInfoVO=new PhoneInfoVO();
//            BeanUtils.copyProperties(phoneInfo,phoneInfoVO);
//            phoneInfoVO.setTag(PhoneUtil.createTag(phoneInfo.getPhoneTag()));
//            phoneInfoVOList.add(phoneInfoVO);
//
//        }
        List<PhoneInfoVO> phoneInfoVOList = phoneInfoList.stream()
                .map(e -> new PhoneInfoVO(e.getPhoneId(), e.getPhoneName(), e.getPhonePrice()+".00"
                        , e.getPhoneDescription(), PhoneUtil.createTag(e.getPhoneTag()), e.getPhoneIcon()))
                .collect(Collectors.toList());
        dataVO.setPhones(phoneInfoVOList);


        return dataVO;
    }

    @Override
    public List<PhoneInfoVO> findBycategoryType(Integer categoryType) {
        List<PhoneInfo>phoneInfoList=phoneInfoRepository.findAllByCategoryType(categoryType);

        List<PhoneInfoVO> phoneInfoVOList = phoneInfoList.stream().map(e -> new PhoneInfoVO(e.getPhoneId(), e.getPhoneName(), e.getPhonePrice()+".00"
                , e.getPhoneDescription(), PhoneUtil.createTag(e.getPhoneTag()), e.getPhoneIcon())).collect(Collectors.toList());

        return phoneInfoVOList;
    }

    @Override
    public SpecsPackageVO findSpecsByPhoneId(Integer phoneId) {
        PhoneInfo phoneInfo=phoneInfoRepository.findById(phoneId).get();
        List<PhoneSpecs>phoneSpecsList=phoneSpecsRepository.findAllByPhoneId(phoneId);
        //tree
        List<PhoneSpecsVO>phoneSpecsVOList=new ArrayList<>();
        List<PhoneSpecsCasVO>phoneSpecsCasVOList=new ArrayList<>();
        PhoneSpecsVO phoneSpecsVO;
        PhoneSpecsCasVO phoneSpecsCasVO;
        for (PhoneSpecs phoneSpecs : phoneSpecsList) {
            phoneSpecsVO=new PhoneSpecsVO();
            phoneSpecsCasVO=new PhoneSpecsCasVO();
            //对这两个参数赋值
            BeanUtils.copyProperties(phoneSpecs,phoneSpecsVO);
            BeanUtils.copyProperties(phoneSpecs,phoneSpecsCasVO);
            phoneSpecsVOList.add(phoneSpecsVO);
            phoneSpecsCasVOList.add(phoneSpecsCasVO);
        }

        TreeVO treeVO=new TreeVO();
        treeVO.setV(phoneSpecsVOList);
        List<TreeVO> treeVOList=new ArrayList<>();
        treeVOList.add(treeVO);
     //封装sku
        SkuVO skuVO=new SkuVO();
      Integer  price=phoneInfo.getPhonePrice().intValue();
      skuVO.setPrice(price+".00");
      skuVO.setStock_num(phoneInfo.getPhoneStock());
      skuVO.setTree(treeVOList);
      skuVO.setList(phoneSpecsCasVOList);

      //封装最终的specsPackageVO
        SpecsPackageVO specsPackageVO=new SpecsPackageVO();
        specsPackageVO.setSku(skuVO);
        Map<String,String> goods=new HashMap<>();
        goods.put("picture",phoneInfo.getPhoneIcon());
        specsPackageVO.setGoods(goods);

     return specsPackageVO;

    }

    @Override
    public void subStock(Integer specsId, Integer quantity) {
        PhoneSpecs phoneSpecs=phoneSpecsRepository.findById(specsId).get();
        //总库存是规格库存之和
        PhoneInfo phoneInfo=phoneInfoRepository.findById(phoneSpecs.getPhoneId()).get();
        //现有库存减去购买数量
        Integer result=phoneSpecs.getSpecsStock()-quantity;
        if(result<0){
            log.error("【扣库存】库存不足");
             throw new PhoneException(ResultEnum.PHONE_SOTCK_LACK);
        }
        phoneSpecs.setSpecsStock(result);
        phoneSpecsRepository.save(phoneSpecs);

        //总库存
        result=phoneInfo.getPhoneStock()-quantity;
        if(result<0){
            log.error("【扣库存】库存不足");
            throw new PhoneException(ResultEnum.PHONE_SOTCK_LACK);
        }
        phoneInfo.setPhoneStock(result);
        phoneInfoRepository.save(phoneInfo);

    }
}
