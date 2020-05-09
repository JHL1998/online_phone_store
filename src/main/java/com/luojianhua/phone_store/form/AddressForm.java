package com.luojianhua.phone_store.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class AddressForm {
    private Integer id;
    @NotEmpty(message = "姓名不能为空")
    private String name;
    @Length(min = 11,max = 11,message = "电话号码必须为11位")
    private String tel;
    @NotEmpty(message = "省名不能为空")
    private String province;
    @NotEmpty(message = "市名不能为空")
    private String city;
    @NotEmpty(message = "区名不能为空")
    private String county;
    @NotEmpty(message = "城市编号不能为空")
    private String areaCode;
    @NotEmpty(message = "详细地址不能为空")
    private String addressDetail;

}
