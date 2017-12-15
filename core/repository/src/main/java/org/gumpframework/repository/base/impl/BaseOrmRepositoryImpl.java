package org.gumpframework.repository.base.impl;

import lombok.extern.slf4j.Slf4j;
import org.gumpframework.domain.base.BaseEntity;
import org.gumpframework.domain.bean.PageModel;
import org.gumpframework.repository.base.BaseOrmRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component
@Slf4j
public class BaseOrmRepositoryImpl<T extends BaseEntity> extends BaseRepositoryImpl<T> implements BaseOrmRepository <T>{

    /** 实体类类型 */
    private Class<T> entityClass;
    @Override
    public void save(T entity) {
        getSession().saveOrUpdate(entity);
    }
    @Override
    public void delete(T entity){
        getSession().delete(entity);
    }

    protected PageModel<T> findPage(CriteriaQuery<T> criteriaQuery, PageModel<T> pm) {
        Assert.notNull(criteriaQuery);
        Assert.notNull(criteriaQuery.getSelection());
        Assert.notEmpty(criteriaQuery.getRoots());
        if (pm == null) {
            pm = new PageModel<>();
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        Root<T> root  = getRoot(criteriaQuery); //获取对象类型
//        addFilter(criteriaQuery,pm);//添加查询类型
//        addOrders(criteriaQuery,pm);//添加排序
        
        return null;
    }



}
