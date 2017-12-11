package com.dai.test.entity.client;


import lombok.Data;
import org.gumpframework.domain.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name="bs_user_info")
public class UserInfo extends BaseEntity {

    @Column(name = "name_")
    private String name;
    @Column(name = "password_")
    private String password;

}
