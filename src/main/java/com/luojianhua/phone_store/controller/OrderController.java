package com.luojianhua.phone_store.controller;

import com.luojianhua.phone_store.dto.OrderDTO;
import com.luojianhua.phone_store.exception.PhoneException;
import com.luojianhua.phone_store.form.OrderForm;
import com.luojianhua.phone_store.service.OrderService;
import com.luojianhua.phone_store.util.ResultUtil;
import com.luojianhua.phone_store.vo.PhoneResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

     @Autowired
     private OrderService orderService;


    @PostMapping("/create")
    public PhoneResultVO create(@Valid @RequestBody OrderForm orderForm, BindingResult bindingResult){
      if(bindingResult.hasErrors()){
           log.error("【创建订单】参数错误,orderForm{}",orderForm);
           throw new  PhoneException(bindingResult.getFieldError().getDefaultMessage());
      }
        OrderDTO orderDTO=new OrderDTO();
        //两个类的属性名不一致，不能用BeanUtils
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setSpecsId(orderForm.getSpecsId());

        orderDTO.setPhoneQuantity(orderForm.getQuantity());
        orderDTO.setBuyerPhone(orderForm.getTel());
        OrderDTO result = orderService.create(orderDTO);
        Map<String,String> map=new HashMap<>();
        map.put("orderId",result.getOrderId());
       //不用减库存，orderCreate里面减过了
        return ResultUtil.success(map);

    }



    @GetMapping("/detail/{orderId}")
    public PhoneResultVO detail(@PathVariable("orderId") String orderId){
         return ResultUtil.success(orderService.findOrderDetailByOrderId(orderId));
    }

    @PutMapping("/pay/{orderId}")
    public PhoneResultVO pay(@PathVariable("orderId") String orderId){
       Map<String,String>map=new HashMap<>();
       map.put("orderId",orderService.pay(orderId));
       return ResultUtil.success(map);

    }
}
