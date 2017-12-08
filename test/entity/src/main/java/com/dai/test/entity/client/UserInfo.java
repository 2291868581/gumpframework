package com.dai.test.entity.client;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gumpframework.domain.base.BaseEntity;
import org.gumpframework.util.common.UniqueUtil;

import javax.persistence.*;

@Entity
@Data
@Table(name="bs_user_info")
@EqualsAndHashCode(callSuper = false)
public class UserInfo extends BaseEntity {

    @Column(name = "name_")
    private String name;
    @Column(name = "password_")
    private String password;

}
