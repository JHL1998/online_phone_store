package com.luojianhua.phone_store.service.impl;

import com.luojianhua.phone_store.dto.OrderDTO;
import com.luojianhua.phone_store.entity.OrderMaster;
import com.luojianhua.phone_store.entity.PhoneInfo;
import com.luojianhua.phone_store.entity.PhoneSpecs;
import com.luojianhua.phone_store.enums.PayStatusEnum;
import com.luojianhua.phone_store.enums.ResultEnum;
import com.luojianhua.phone_store.exception.PhoneException;
import com.luojianhua.phone_store.repository.OrderMasterRepository;
import com.luojianhua.phone_store.repository.PhoneInfoRepository;
import com.luojianhua.phone_store.repository.PhoneSpecsRepository;
import com.luojianhua.phone_store.service.OrderService;
import com.luojianhua.phone_store.service.PhoneService;
import com.luojianhua.phone_store.util.KeyUtil;
import com.luojianhua.phone_store.vo.OrderDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class OrderServiceImpl  implements OrderService {


     @Autowired
     private PhoneSpecsRepository phoneSpecsRepository;
     @Autowired
     private PhoneInfoRepository phoneInfoRepository;

     @Autowired
     private OrderMasterRepository orderMasterRepository;

     @Autowired
     private PhoneService phoneService;
    @Override
    public OrderDTO create(OrderDTO orderDTO) {

        OrderMaster orderMaster=new OrderMaster();
        //填充订单里的信息
        BeanUtils.copyProperties(orderDTO,orderMaster);
        //填充手机规格信息
        PhoneSpecs phoneSpecs=phoneSpecsRepository.findById(orderDTO.getSpecsId()).get();

        if(phoneSpecs==null){
            log.error("【创建订单】规格为空,phoneSpecs={}",phoneSpecs);
            throw new PhoneException(ResultEnum.SPECS_NOT_FOUND);
        }

        BeanUtils.copyProperties(phoneSpecs,orderMaster);
        //填充手机信息
        PhoneInfo phoneInfo=phoneInfoRepository.findById(phoneSpecs.getPhoneId()).get();

        if(phoneInfo==null){
            log.error("【创建订单】手机为空,phoneInfo={}",phoneInfo);
            throw new PhoneException(ResultEnum.PHONE_NOT_FOUND);
        }

        BeanUtils.copyProperties(phoneInfo,orderMaster);

        //计算总价=规格价格*数量
        BigDecimal orderAmount=new BigDecimal(0);
        orderAmount=phoneSpecs.getSpecsPrice().divide(new BigDecimal(100))
                .multiply(new BigDecimal(orderDTO.getPhoneQuantity()))
                .add(orderAmount).add(new BigDecimal(10));
        orderMaster.setOrderAmount(orderAmount);

        //orderId
         orderMaster.setOrderId(KeyUtil.createUniquKey());
         orderDTO.setOrderId(orderMaster.getOrderId());
         //支付状态
          orderMaster.setPayStatus(PayStatusEnum.UNPAID.getCode());

          //存进总状态
          orderMasterRepository.save(orderMaster);
         phoneService.subStock(orderMaster.getSpecsId(),orderDTO.getPhoneQuantity());

     return orderDTO;

    }

    @Override
    public OrderDetailVO findOrderDetailByOrderId(String orderId) {
          OrderDetailVO orderDetailVO=new OrderDetailVO();

          OrderMaster orderMaster=orderMasterRepository.findById(orderId).get();


        if(orderMaster==null){
            log.error("【查询订单】订单为空,orderMaster={}",orderMaster);
            throw new PhoneException(ResultEnum.ORDER_NOT_FOUND);
        }


          BeanUtils.copyProperties(orderMaster,orderDetailVO);
          //将价格从分转为元，并且转换为字符串
           orderDetailVO.setSpecsPrice(orderMaster.getSpecsPrice().divide(new BigDecimal(100))+".00");


          return orderDetailVO;

    }

    @Override
    public String pay(String orderId) {

        OrderMaster orderMaster=orderMasterRepository.findById(orderId).get();
        if(orderMaster==null){
            log.error("【支付订单】订单为空,orderMaster={}",orderMaster);
            throw new PhoneException(ResultEnum.ORDER_NOT_FOUND);
        }
        //存在的话修改订单状态
       if(orderMaster.getPayStatus().equals(PayStatusEnum.UNPAID.getCode())){
           //未支付
           orderMaster.setPayStatus(PayStatusEnum.PAID.getCode());
           orderMasterRepository.save(orderMaster);
       }else{
           //已支付
           log.error("【支付订单】订单已支付,orderMaster={}",orderMaster);
       }

     return orderId;


    }
}
