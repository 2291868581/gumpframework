package com.dai.test.product.service;

import com.dai.test.product.entity.ProductTable;
import org.gumpframework.service.base.BaseService;

import java.util.List;
import java.util.Map;

public interface ProductService extends BaseService<ProductTable>{
    List<ProductTable> findAll();
    Map<String,Object> findById(String id);
}
