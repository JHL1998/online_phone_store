package com.luojianhua.phone_store.vo;

import lombok.Data;

@Data
public class PhoneResultVO<E> {

    private Integer code;
    private String msg;
    //如果使用Object，以后每次都要转型，而泛型不用
    private E data;

}
