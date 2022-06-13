package com.wxf.controller;

import com.wxf.annotation.NotControllerResponseAdvice;
import com.wxf.vo.ProductInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 *
 * @author weixf
 * @date 2022-06-07
 */
@RestController
public class HealthController {

    @GetMapping("/health")
    @NotControllerResponseAdvice
    public String health() {
        return "success";
    }

    // @PostMapping("/findByVo")
    // public ProductInfo findByVo(@Validated ProductInfoVo vo) {
    //     ProductInfo productInfo = new ProductInfo();
    //     BeanUtils.copyProperties(vo, productInfo);
    //     return productInfoService.getOne(new QueryWrapper(productInfo));
    // }
}
