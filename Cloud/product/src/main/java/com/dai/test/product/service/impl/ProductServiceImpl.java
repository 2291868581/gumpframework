package com.dai.test.product.service.impl;

import com.dai.test.product.entity.ProductTable;
import com.dai.test.product.service.ProductService;
import org.gumpframework.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl extends BaseServiceImpl<ProductTable> implements ProductService{

    @Override
    public List<ProductTable> findAll() {
        String sql = " from ProductTable  ";
        return baseQlRepository.getListByHQL(sql);
    }

    @Override
    public Map<String,Object> findById(String id) {
        String sql = " SELECT a.id_ AS id ,a.name_ AS name ,a.number_ AS number from cloud_product a where a.id_=:p1  ";
        return (Map<String, Object>) baseQlRepository.getObjectBySQL(sql,id);
    }



}
