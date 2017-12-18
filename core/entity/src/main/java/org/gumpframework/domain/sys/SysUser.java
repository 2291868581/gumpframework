package org.gumpframework.domain.sys;


import lombok.Data;
import org.gumpframework.domain.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name="sys_user_t")
public class SysUser extends BaseEntity{
    @Column(name = "name_")
    private String name;
    @Column(name = "password_")
    private String password;
}
