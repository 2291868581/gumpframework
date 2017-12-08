package org.gumpframework.repository.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.gumpframework.util.bean.Parameter;
import org.gumpframework.util.common.PublicUtil;
import org.gumpframework.util.common.QueryUtil;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
@Slf4j
public class RepositoryUtil {
    /**
     * 将数据库返回的list按照类型封装  工具類
     * @param lst
     * @param isSql
     * @param QL
     * @return
     */
    public static List parseSqlRsList(List lst, boolean isSql, String QL) {
        if (lst != null && lst.size() > 0) {
            if (isSql && (QL.contains(" as ") || QL.contains(" AS "))) {
                int fromIndex = org.gumpframework.util.common.QueryUtil.findOuterFromIndex(QL.toUpperCase());  //在sql中寻找与最外层select对应的from的index 调用前请先转成大写。
                String columnStr = QL.substring(7, fromIndex);
                String[] columns = org.gumpframework.util.common.QueryUtil.getColumnNames3(columnStr); //獲取到的sql中的as key
                List<Map<String, Object>> rsList = Lists.newArrayList(); //返回的結果
                Map<String, Object> map = Maps.newHashMap();
                for (Object item : lst) { //遍歷每一行
                    map = Maps.newHashMap();
                    if (item != null && item.getClass().isArray()) {
                        Object[] items = (Object[]) item;
                        for (int i = 0; i < items.length; i++) { //遍歷每一列
                            String key = columns[i];
                            map.put((key.indexOf(",") != -1 ? key.substring(0, key.indexOf(",")) : key).replace(" ",
                                    ""), items[i]);
                        }
                    } else {
                        for (int i = 0; i < columns.length; i++) {
                            String key = columns[i];
                            map.put((key.indexOf(",") != -1 ? key.substring(0, key.indexOf(",")) : key).replace(" ",
                                    ""), item);
                        }
                    }
                    rsList.add(map);
                }
                if (rsList != null && rsList.size() > 0)
                    lst = rsList;
            }
        }
        return lst;
    }

    /**
     * 自动将参数注入到Query对象中  工具类
     *
     * @param QL
     * @param query
     * @param params  此時的參數中已經封裝好了 順序 p1 -> pn
     */
    public static Query setParams(String QL, Query query, Map<String, Object> params) {
        if (PublicUtil.isNotEmpty(params)) {
            log.info("params {}", params);
            Iterator<String> keys = params.keySet().iterator();
            String key = "";
            Object val = null;
            StringBuffer sb = new StringBuffer();
            String parseQL = QL;
            while (keys.hasNext()) {
                key = keys.next();
                val = params.get(key);
                String item = PublicUtil.toAppendStr(":", key);
                if (!QL.contains(item)){
                    log.warn("多余的参数:" + key + ",值：" + val);
                    continue;
                }
                parseQL=parseQL.replace(item,PublicUtil.toAppendStr("\'",val,"\'"));
                query.setParameter(key, val);
            }
            log.info("拼接好的QL-------》{}",parseQL);
        }
        return query;
    }


    public static SQLQuery createSqlQuery(String SQL, Session session, Object... params) {
        SQLQuery query = session.createSQLQuery(SQL);
        Map<String, Object> map = new Parameter(params); // 傳入的第一個是 一個沒有元素的map對象 将传入的数据按照顺序封装为map对象
        query = (SQLQuery) RepositoryUtil.setParams(SQL, query, map);
        return query;
    }

    public static Query createQuery(String HQL,Session session, Object... params) {
        Query query = session.createQuery(HQL);
        Map<String,Object> map = new Parameter(params);
        query = RepositoryUtil.setParams(HQL,query,map);
        return query;
    }

    public static String parseToCountQL(String QL){
        StringBuffer sb = new StringBuffer();
        try {
            String temp = "", tempSql = QL.toUpperCase();
            if (tempSql.indexOf("SELECT") != -1) {
                temp = QL.substring(QueryUtil.findOuterFromIndex(tempSql));
            } else {
                temp = QL;
            }
            if (tempSql.indexOf(" ORDER BY") != -1) {
                int orderIndex  = temp.indexOf(" order by");
                if(orderIndex == -1) orderIndex = temp.indexOf(" ORDER BY");
                temp = temp.substring(0, orderIndex);
            }
            sb.append("select count(*) ");
            sb.append(temp);

        } catch (Exception e) {
            log.error("在根据原始分页HQL获取总记录条数的HQL时出现异常，异常SQL-->" + QL);
            log.error(e.getMessage());
        }
        return sb.toString();
    }


}
