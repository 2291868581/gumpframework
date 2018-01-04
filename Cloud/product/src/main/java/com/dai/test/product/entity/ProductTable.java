package com.dai.test.product.entity;

import com.dai.test.common.entity.Product;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "cloud_product")
public class ProductTable extends Product {

}
