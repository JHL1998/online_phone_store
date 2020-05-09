package com.luojianhua.phone_store.service.impl;

import com.luojianhua.phone_store.dto.OrderDTO;
import com.luojianhua.phone_store.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;
    @Test
    void create() {
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerName("张三");
        orderDTO.setBuyerPhone("13235611321");
        orderDTO.setPhoneQuantity(1);
        orderDTO.setSpecsId(1);
        orderDTO.setBuyerAddress("湖北省荆州市沙市区大庆路");

        OrderDTO result = orderService.create(orderDTO);
        System.out.println(result);

    }
}