package com.luojianhua.phone_store.exception;

import com.luojianhua.phone_store.enums.ResultEnum;

public class PhoneException extends RuntimeException {

    public PhoneException(ResultEnum resultEnum){
         super(resultEnum.getMessage());
    }

    public PhoneException(String error){
        super(error);
    }


}
