package com.dai.test.common.entity;

import lombok.Data;
import org.gumpframework.domain.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class Product extends BaseEntity{
    @Column(name = "name_")
    private String name;
    @Column(name = "number_")
    private Integer number;
}
