package org.gumpframework.repository.base.impl;

import lombok.extern.slf4j.Slf4j;
import org.gumpframework.domain.base.BaseEntity;
import org.gumpframework.repository.base.BaseOrmRepository;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BaseOrmRepositoryImpl<T extends BaseEntity> extends BaseRepositoryImpl<T> implements BaseOrmRepository <T>{
    @Override
    public void save(T entity) {
        getSession().saveOrUpdate(entity);
    }
    @Override
    public void delete(T entity){
        getSession().delete(entity);
    }

}
