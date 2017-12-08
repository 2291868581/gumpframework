package org.gumpframework.service.base.impl;


import lombok.extern.slf4j.Slf4j;
import org.gumpframework.domain.base.BaseEntity;
import org.gumpframework.repository.base.BaseRepository;
import org.gumpframework.service.base.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Service
@Slf4j
@Transactional
public class BaseServiceImpl<T extends BaseEntity> implements BaseService<T>{
    @Autowired
    public BaseRepository<T> baseRepository;

    private Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public BaseServiceImpl() {
        Class<?> c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            persistentClass = (Class<T>) parameterizedType[0];
        }
    }

    @Override
    public void save(T entity) {
        baseRepository.save(entity);
    }


}
