package com.luojianhua.phone_store.vo;

import lombok.Data;

import java.util.Map;

/**
 * 最终封装对象
 */
@Data
public class SpecsPackageVO {

    /**
     * 对应着 GET /phone/findSpecsByPhoneId
     */
    private Map<String,String> goods;
    private SkuVO sku;
}
