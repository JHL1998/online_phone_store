package com.luojianhua.phone_store.enums;

import lombok.Getter;

/**
 * 库存
 */
@Getter
public enum ResultEnum {
    PHONE_SOTCK_LACK(0,"库存不足"),
    ORDER_NOT_FOUND(1,"订单找不到"),
    SPECS_NOT_FOUND(2,"规格找不到"),
    PHONE_NOT_FOUND(3,"手机找不到");

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
