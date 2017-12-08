package org.gumpframework.repository.base.impl;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.gumpframework.domain.base.BaseEntity;
import org.gumpframework.domain.bean.PageModel;
import org.gumpframework.repository.base.BaseRepository;
import org.gumpframework.repository.util.RepositoryUtil;
import org.gumpframework.util.bean.QueryCondition;
import org.gumpframework.util.common.PublicUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


/**
 * 通用baseRepository实现类,create by GumpDai
 */
@Component
@Slf4j
public class BaseRepositoryImpl<T extends BaseEntity> implements BaseRepository<T> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Session getSession() {
        return (Session) entityManager.getDelegate();
    }

    @Override
    public void flush() {
        getSession().flush();
    }

    @Override
    public void clear() {
        getSession().clear();
    }

    @Override
    public Object getByHQL(String HQL, boolean isList, List<QueryCondition> conditionList, Object... params) {
        return getByQL(HQL,false,false,isList,0,conditionList,params);
    }

    @Override
    public Object getByHQL(String HQL, boolean isList, int maxSize, List<QueryCondition> conditionList, Object... params) {
        return getByQL(HQL,false,false,isList,maxSize,conditionList,params);
    }

    @Override
    public List getListSizeByHQL(String HQL, int maxSize, List<QueryCondition> conditionList, Object... params) {
        return (List) getByQL(HQL,false,false,true,maxSize,conditionList,params);
    }

    @Override
    public List getListSizeByHQL(String HQL, int maxSize, Object... params) {
        return (List) getByQL(HQL,false,false,true,maxSize,null,params);
    }

    @Override
    public List getListByHQL(String HQL, List<QueryCondition> conditionList, Object... params) {
        return (List) getByQL(HQL,false,false,true,0,conditionList,params);
    }

    @Override
    public List getListByHQL(String HQL, Object... params) {
        return (List) getByQL(HQL,false,false,true,0,null,params);
    }

    @Override
    public List<T> getEntityListByHQL(String HQL, int maxSize, List<QueryCondition> conditionList, Object... params) {
        return (List<T>) getByQL(HQL,false,false,true,maxSize,conditionList,params);
    }

    @Override
    public List<T> getEntityListByHQL(String HQL, int maxSize, Object... params) {
        return (List<T>) getByQL(HQL,false,false,true,maxSize,null,params);
    }

    @Override
    public List<T> getEntityListByHQL(String HQL, List<QueryCondition> conditionList, Object... params) {
        return (List<T>) getByQL(HQL,false,false,true,0,conditionList,params);
    }

    @Override
    public List<T> getEntityListByHQL(String HQL, Object... params) {
        return (List<T>) getByQL(HQL,false,false,true,0,null,params);
    }

    @Override
    public Object getObjectByHQL(String HQL, List<QueryCondition> conditionList, Object... params) {
        return  getByQL(HQL,false,false,false,0,conditionList,params);
    }

    @Override
    public Object getObjectByHQL(String HQL, Object... params) {
        return getByQL(HQL,false,false,false,0,null,params);
    }

    @Override
    public T getEntityByHQL(String HQL, List<QueryCondition> conditionList, Object... params) {
        return (T) getByQL(HQL, false, false, false, 0, conditionList, params);
    }

    @Override
    public T getEntityByHQL(String HQL, Object... params) {
         return (T) getByQL(HQL, false, false, false, 0, null, params);
    }


    @Override
    public List getListSizeBySQL(String SQL, int maxSize, List<QueryCondition> conditionList, Object... params) {
        return (List) getByQL(SQL, true, false, true, maxSize, conditionList, params);
    }

    @Override
    public List getListSizeBySQL(String SQL, int maxSize, Object... params) {
        return (List) getByQL(SQL, true, false, true, maxSize, null, params);
    }


    @Override
    public Object getObjectBySQL(String SQL, List<QueryCondition> conditionList, Object... params) {
        return  getByQL(SQL, true, false, false, 0, conditionList, params);
    }

    @Override
    public Object getObjectBySQL(String SQL, Object... params) {
        return  getByQL(SQL, true, false, false, 0, null, params);
    }



    /**
     * 执行HQL
     * @param HQL
     * @param params
     * @return
     */
    @Override
    public int execute(String HQL, Object... params) {
        Query query = RepositoryUtil.createQuery(HQL,getSession(),params);
        return query.executeUpdate();
    }

    /**
     * 执行sql
     * @param SQL
     * @param params
     * @return
     */
    @Override
    public int executeSQL(String SQL, Object... params) {
        Query query = RepositoryUtil.createSqlQuery(SQL,getSession(),params);
        return query.executeUpdate();
    }

    @Override
    public void executeCall(String call, Object... params) {
        getSession().doWork(new Work() {
            public void execute(Connection conn) {
                CallableStatement cstmt = null;
                try {
                    cstmt = conn.prepareCall(call);
                    if (params != null && params.length != 0) {
                        for (int i = 0; i < params.length; i++) {
                            cstmt.setObject((i + 1), params[i]);
                        }
                        // cstmt.registerOutParameter(params.length + 1,
                        // Types.INTEGER);
                        cstmt.execute();
                    }
                } catch (SQLException e) {
                    log.error(e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public PageModel<T> getHQLPage(String HQL, PageModel<?> pm, Object... params) {
        return getQLPage(HQL,pm,false,false,params);
    }

    @Override
    public PageModel<T> getSQLPage(String SQL, PageModel<?> pm, Object... params) {
        return getQLPage(SQL,pm,true,false,params);
    }

    @Override
    public PageModel<T> getHQLPage(String HQL, PageModel<?> pm, boolean isCache, Object... params) {
        return getQLPage(HQL,pm,true,isCache,params);
    }
    @Override
    public PageModel<T> getSQLPage(String SQL, PageModel<?> pm, boolean isCache, Object... params) {
        return getQLPage(SQL,pm,true,isCache,params);
    }

    /**
     * 根据sql获取满足条件的数量
     * @param SQL
     * @param params
     * @return
     */
    @Override
    public Long getCountBySQl(String SQL,Object... params){
        return getCountByQL(SQL,true,false,null,params);
    }

    /**
     * 根据HQL获取满足条件的数量
     * @param HQL
     * @param params
     * @return
     */
    @Override
    public Long getCountByHQL(String HQL,Object... params){
        return getCountByQL(HQL,false,false,null,params);
    }


    /**
     * 根据sql获取对象
     * @param SQL
     * @param isList
     * @param conditionList
     * @param params
     * @return
     */
    @Override
    public Object getBySQL(String SQL, boolean isList, List<QueryCondition> conditionList, Object... params) {
        return getByQL(SQL,true,false,isList,0,conditionList,params);
    }

    /**
     * 根据sql获取对象 设置 查询的条数
     * @param SQL
     * @param isList
     * @param maxSize
     * @param conditionList
     * @param params
     * @return
     */
    @Override
    public Object getBySQL(String SQL, boolean isList, int maxSize, List<QueryCondition> conditionList, Object... params) {
        return getByQL(SQL,true,false,isList,maxSize,conditionList,params);
    }

    /**
     * 根据传入的sql  conditionList  以及 参数 来查询 结果集 一般为   List<Map<String,Object>>
     * @param SQL
     * @param conditionList
     * @param params
     * @return
     */
    @Override
    public List getListBySQL(String SQL, List<QueryCondition> conditionList, Object... params) {
        return (List) getByQL(SQL,true,false,true,0,conditionList,params);
    }

    /**
     * 根据传入的sql 和 参数查询 list 结果集 ,一般为  List<Map<String,Object>>
     * @param SQL
     * @param params
     * @return
     */
    @Override
    public List getListBySQL(String SQL, Object... params) {
        return (List) getByQL(SQL,true,false,true,0,null,params);
    }

    @Override
    public void save(T entity) {
         getSession().saveOrUpdate(entity);
    }

    /** -------------->以下为公用方法 <------------- */

    /**
     * 获取分页数据
     * @param QL
     * @param isSql
     * @param pm
     * @param isCache
     * @param conditionList
     * @param params
     * @return
     */
    private List<T> getPageModelData(String QL, boolean isSql, PageModel pm, boolean isCache,
                                     List<QueryCondition> conditionList,
                                     Object... params) {
        try {
            Map<String, Object> map = Maps.newHashMap();
//            if (PublicUtil.isNotEmpty(conditionList))
//                QL = QueryUtil.convertJsonToQueryCondition(QL, conditionList, null, map);
            Query query = isSql ? RepositoryUtil.createSqlQuery(QL,getSession(), map, params) : RepositoryUtil.createQuery(QL,getSession(), map, params);
            query.setMaxResults(pm.getPageSize());
            query.setFirstResult( (pm.getPageNumber() - 1) * pm.getPageSize());
            if (isCache)
                query.setCacheable(true);
            List lst = query.list();
            return RepositoryUtil.parseSqlRsList(lst, isSql, QL);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("在获取分页数据源的getData()出现异常，异常HQL-->" + QL);
            log.error(e.getMessage());
        }
        return null;
    }
    /**
     * 与数据库直接交互的方法，返回结果供其他重载方法调用
     * @param QL
     * @param isSql
     * @param isCache
     * @param isList
     * @param maxSize
     * @param conditionList
     * @param params  按照順序傳入的查詢參數
     * @return
     */
    public Object getByQL(String QL, boolean isSql, boolean isCache, boolean isList, int maxSize, List<QueryCondition> conditionList, Object... params) {
        try {
            long starTime=System.currentTimeMillis();
            Map<String, Object> map = Maps.newHashMap();
            //判断conditionList是否为空，不为空则拼接到ql查询中
//            if (PublicUtil.isNotEmpty(conditionList))
//                QL = QueryUtil.convertJsonToQueryCondition(QL, conditionList, null, map);
            //创建 sql query 或者  hql query
            Query query = isSql ? RepositoryUtil.createSqlQuery(QL,getSession(),map,params) : RepositoryUtil.createQuery(QL,getSession(),map,params);
            //设置缓存
            if (isCache)
                query.setCacheable(true);
            //设置查询的条数
            if (isList && maxSize != 0) {
                query.setMaxResults(maxSize);
                maxSize = 0;
            }
            List lst = query.list();
            //如果是sql ，則將結果封裝為  List<Map<String,Object>>
            if (isSql)   lst = RepositoryUtil.parseSqlRsList(lst, isSql, QL);
            //如果不是list,返回list结果的第一条
            long endTime=System.currentTimeMillis();
            float excTime=(float)(endTime-starTime)/1000;
            log.info("sql或Hql查询耗时:{}秒",excTime);
            if (!isList) {
                return PublicUtil.isNotEmpty(lst) ? lst.get(0) : null;
            } else {
                return lst;
            }
        } catch (Exception e) {
            log.error("QL------->" + QL);
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取传入Ql的数量
     * @param QL  这里传入的QL是已经将 select 和 from 中的 换成了 count(*)
     * @param isSql
     * @param isCache
     * @param conditionList
     * @param params
     * @return
     */
    public Long getCountByQL(String QL, boolean isSql, boolean isCache, List<QueryCondition> conditionList, Object... params) {
        try {
            long starTime=System.currentTimeMillis();
            Map<String,Object> map = Maps.newHashMap();
            Query query = isSql ? RepositoryUtil.createSqlQuery(QL,getSession(),map,params):RepositoryUtil.createQuery(QL,getSession(),map,params);
            if (isCache) query.setCacheable(true);
            List list = query.list();
            long endTime=System.currentTimeMillis();
            float excTime=(float)(endTime-starTime)/1000;
            log.info("获取总数查询耗时:{}秒",excTime);
            if (PublicUtil.isNotEmpty(list)){
                return PublicUtil.parseLong((list.size()==1?list.get(0):list.size()),0L);
            }else {
                return 0L;
            }
        }catch (Exception e){
            log.error("QL----->"+QL);
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public PageModel<T> getQLPage(String QL, PageModel<?> pm, boolean isSql, boolean isCache, Object... params) {
        long starTime=System.currentTimeMillis();
        if (pm==null) return null;
        if (pm.getPageNumber()<0||pm.getPageSize()<=0||!pm.getUsePage()){
            pm.setRecordsTotal(getCountByQL(RepositoryUtil.parseToCountQL(QL),isSql,isCache,null,params));
            pm.setData((List) getByQL(QL,isSql,isCache,true,0,null,params));
        }else if (pm.getUsePage()){
            pm.setRecordsTotal(getCountByQL(RepositoryUtil.parseToCountQL(QL),isSql,isCache,null,params));
            pm.setData((List) getPageModelData(QL, isSql,pm, isCache,null, params));
        }
        long endTime=System.currentTimeMillis();
        float excTime=(float)(endTime-starTime)/1000;
        log.info("分页查询耗时:{}秒",excTime);
        return (PageModel<T>) pm;
    }

    @Override
    public Boolean doCheckByProperty(T entity) {
        return null;
    }

    @Override
    public Boolean doCheckByPK(T entity) {
        return null;
    }
}
