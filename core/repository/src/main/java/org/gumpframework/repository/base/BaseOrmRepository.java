package org.gumpframework.repository.base;

import org.gumpframework.domain.base.BaseEntity;



/**
 * 通用 OrmRepository 接口类， create by GumpDai
 * 该类主要封装是实现ORM 对象关系 映射的通用类 继承自 BaseRepository
 * Email  xinyudai_ifox@icloud.com
 */
public interface BaseOrmRepository<T extends BaseEntity> extends BaseRepository<T> {
    void save(T entity);
    void delete(T entity);

}
