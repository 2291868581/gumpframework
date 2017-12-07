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
    @Id
    @Column(
            name = "id_",
            nullable = false
    )
    private String id;
    @Column(name = "name_")
    private String name;
    @Column(name = "password_")
    private String password;

    @PrePersist
    public void prePersist(){
        this.id = UniqueUtil.uuid();
    }
}
