package org.gumpframework.repository.base;

import org.gumpframework.domain.base.BaseEntity;
import org.gumpframework.domain.bean.PageModel;
import org.gumpframework.util.bean.QueryCondition;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.util.List;

/**
 * 通用repository 接口类， create by GumpDai
 * 该类主要封装 JPA hibernate 的api，主要实现传入Sql Hql 和参数 的结果返回
 * Email  xinyudai_ifox@icloud.com
 */
public interface BaseRepository<T extends BaseEntity> {

    Session getSession();

    /** 强制与数据库同步 */
    void flush();

    /** 清除缓存数据 */
    void clear();

}
