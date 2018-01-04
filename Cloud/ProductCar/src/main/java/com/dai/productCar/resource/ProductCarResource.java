package com.dai.productCar.resource;

import com.dai.test.common.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping(value = "productCar")
public class ProductCarResource {

    @Autowired
    private RestTemplate restTemplate;
    @RequestMapping(value = "/addProduct/{id}")
    public String addProduct(@PathVariable String id){
        return this.restTemplate.getForObject("http://PRODUCT/product/get/"+id, String.class);
    }

    @RequestMapping(value = "/getProduct")
    public String getProduct(){
        return this.restTemplate.getForObject("http://PRODUCT/product/list",String.class);
    }
}
