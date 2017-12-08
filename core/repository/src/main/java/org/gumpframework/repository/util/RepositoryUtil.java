package org.gumpframework.repository.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.gumpframework.util.bean.Parameter;
import org.gumpframework.util.common.PublicUtil;
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
                int fromIndex = findOuterFromIndex(QL.toUpperCase());  //在sql中寻找与最外层select对应的from的index 调用前请先转成大写。
                String columnStr = QL.substring(7, fromIndex);
                String[] columns = getColumnNames3(columnStr); //獲取到的sql中的as key
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
                temp = QL.substring(findOuterFromIndex(tempSql));
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

    /**
     * 在sql中寻找与最外层select对应的from的index 调用前请先转成大写。
     *
     * @param tempSql
     * @return
     */
    public static  int findOuterFromIndex(String tempSql) {
        int selectNum = 0, fromIndex = -1;
        for (int i = 0; i < tempSql.length() - 7;) { // 挨着寻找
            char ch = tempSql.charAt(i);
            if ('S' != ch && 'F' != ch) {
                i++;
                continue;
            }
            String select = tempSql.substring(i, i + 7); // 防止selects
            String from = tempSql.substring(i, i + 5); // 防止froms干扰
            if ("SELECT ".equals(select)) { // 找到select关键词
                selectNum++;
                i = i + 7;
                continue;
            } else if ("FROM ".equals(from)) { // 找到from关键词
                selectNum--;
                if (selectNum == 0) { // 已经找到相应from
                    fromIndex = i;
                    break;
                }
                i = i + 5;
            }
            i++;
        }
        if (selectNum > 0 || fromIndex < 8) {
            throw new RuntimeException("sql语句中select与from不对应，请检查sql语句：" + tempSql);
        }
        return fromIndex;
    }

    /**
     * 在sql中寻找与最外层select对应的GroupBy的index 调用前请先转成大写。
     *
     * @param tempSql
     * @return
     */
    public static  int findOuterGroupByIndex(String tempSql) {
        int selectNum = 0, groupByIndex = -1;
        for (int i = 0; i < tempSql.length() - 9;) { // 挨着寻找
            char ch = tempSql.charAt(i);
            if ('S' != ch && 'G' != ch) {
                i++;
                continue;
            }
            String select = tempSql.substring(i, i + 7); // 防止selects
            String groupBy = tempSql.substring(i, i + 9);
            if ("SELECT ".equals(select)) { // 找到select关键词
                selectNum++;
                i = i + 7;
                continue;
            } else if ("GROUP BY ".equals(groupBy)) { // 找到groupBy关键词
                selectNum--;
                if (selectNum == 0) { // 已经找到相应groupBy
                    groupByIndex = i;
                    break;
                }
                i = i + 9;
            }
            i++;
        }
        return groupByIndex;
    }

    /**
     * 用队列思想实现分离别名 1 更加columnStr定义两个char数组。 2 遍历值数组，得到每个列名和逗号。 3 在将得到的列名串转成列名数组。
     *
     * @param colunmStr
     * @return
     */
    public static String[] getColumnNames3(String colunmStr) {
        char[] array = colunmStr.toCharArray();
        StringBuffer sb = new StringBuffer();
        StringBuffer tempSb = new StringBuffer();
        int bracketCount = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == '(') {
                bracketCount++;
                continue;
            }
            if (array[i] == ')') {
                bracketCount--;
                continue;
            }
            if (bracketCount == 0) {
                if (array[i] != ' ' && array[i] != ',') {
                    tempSb.append(array[i]);
                } else if (array[i] == ' '
                        && (i < array.length - 1 && !(array[i + 1] == ' ') && !(array[i + 1] == ','))) {
                    tempSb.delete(0, tempSb.length());
                } else if (array[i] == ',') {
                    tempSb.append(array[i]);
                    sb.append(tempSb.toString());
                    tempSb.delete(0, tempSb.length());
                }
            }
        }
        sb.append(tempSb.toString());
        return sb.toString().split(",");
    }
}
