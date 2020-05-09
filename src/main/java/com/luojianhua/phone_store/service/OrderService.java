package com.luojianhua.phone_store.service;

import com.luojianhua.phone_store.dto.OrderDTO;
import com.luojianhua.phone_store.vo.OrderDetailVO;

public interface OrderService {
    //创建订单
   OrderDTO create(OrderDTO orderDTO);

   //订单详情
    OrderDetailVO findOrderDetailByOrderId(String orderId);

    public String pay(String orderId);

}
