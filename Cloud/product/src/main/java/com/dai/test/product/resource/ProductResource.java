package com.dai.test.product.resource;

import com.dai.test.common.entity.Product;
import com.dai.test.product.entity.ProductTable;
import com.dai.test.product.service.ProductService;
import com.google.common.collect.Maps;
import org.gumpframework.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "product")
public class ProductResource {

    @Autowired
    private ProductService productService;


    private static Map<String,String> products = Maps.newHashMap();
    static {
        products.put("1","飞机");
        products.put("2","火车");
        products.put("3","坦克");
    }
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public List<ProductTable> getProduct(){
       return  productService.findAll();
    }


    @RequestMapping(value = "get/{id}")
    public Map<String,Object> getProduct(@PathVariable String id){
       return productService.findById(id);
    }

    @RequestMapping(value = "save")
    public String save(String name,Integer number){
        ProductTable productTable = new ProductTable();
        productTable.setName(name);
        productTable.setNumber(number);
        productService.save(productTable);
        return "success";
    }




}
