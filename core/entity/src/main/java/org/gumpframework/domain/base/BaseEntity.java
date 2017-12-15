package org.gumpframework.domain.base;

import lombok.Data;
import org.gumpframework.domain.sys.SysUser;
import org.gumpframework.util.common.DateUtil;
import org.gumpframework.util.common.UniqueUtil;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;

/**
 *  create by GumpDai
 */
@Data
public class BaseEntity implements Serializable{

    @Id
    @Column(name = "id_",nullable = false)
    public String id;

    @PrePersist
    public void prePersist(){
        this.id = UniqueUtil.uuid();
    }
    /** 用户 */
    @CreatedBy
    @Column(name = "created_id", nullable = false, length = 50, updatable = false)
    public String createId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_id",updatable = false,insertable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    public SysUser creator;

    /** 创建时间 */
    @CreatedDate
    @Column(name = "created_date",nullable = false)
    public Date createdDate = DateUtil.getCurrentTime();

    /** 更新的用户 */
    @LastModifiedBy
    @Column(name = "update_id",length = 50)
    public String updateId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_id", updatable = false, insertable=false)
    @NotFound(action = NotFoundAction.IGNORE)
    public SysUser updatedUser;

    /** 更新时间 */
    @LastModifiedDate
    @Column(name = "update_date")
    public Date updateDate = DateUtil.getCurrentTime();


    /*** 备注 */
    @Column(name = "description_",length = 255)
    @XmlTransient
    public String description;
}
