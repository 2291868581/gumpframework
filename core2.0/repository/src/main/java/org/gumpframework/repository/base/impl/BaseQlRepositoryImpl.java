package org.gumpframework.repository.base.impl;

import lombok.extern.slf4j.Slf4j;
import org.gumpframework.domain.base.BaseEntity;
import org.gumpframework.domain.bean.PageModel;
import org.gumpframework.repository.base.BaseQlRepository;
import org.gumpframework.repository.util.RepositoryUtil;
import org.gumpframework.util.bean.QueryCondition;
import org.hibernate.Query;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Component
@Slf4j
public class BaseQlRepositoryImpl<T extends BaseEntity> extends BaseRepositoryImpl<T> implements BaseQlRepository<T>{
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

}
