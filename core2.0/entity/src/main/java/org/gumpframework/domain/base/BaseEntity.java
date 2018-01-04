package org.gumpframework.domain.base;

import lombok.Data;
import org.gumpframework.util.common.DateUtil;
import org.gumpframework.util.common.UniqueUtil;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;

/**
 *  create by GumpDai
 */
@Data
@MappedSuperclass
@DynamicUpdate
@DynamicInsert
public class BaseEntity  implements Serializable{

    @Id
    @Column(name = "id_",nullable = false)
    private String id;

    @PrePersist
    private void prePersist(){
        this.id = UniqueUtil.uuid();
    }

    /** 用户 */
    @CreatedBy
    @Column(name = "created_id", nullable = false, length = 50, updatable = false)
    private String createId;



    /** 创建时间 */
    @CreatedDate
    @Column(name = "created_date",nullable = false)
    private Date createdDate = DateUtil.getCurrentTime();


    /** 更新时间 */
    @LastModifiedDate
    @Column(name = "update_date")
    private Date updateDate = DateUtil.getCurrentTime();


    /*** 备注 */
    @Column(name = "description_",length = 255)
    @XmlTransient
    private String description;
}
