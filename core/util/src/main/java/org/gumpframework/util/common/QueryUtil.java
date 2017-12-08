package org.gumpframework.util.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings("rawtypes")
public class QueryUtil {

	protected static Logger logger = LoggerFactory.getLogger(QueryUtil.class);

	/**
	 * json 转换 查询集合
	 * 
	 * @param queryConditionJson
	 *            格式
	 *            [{"fieldName":"loginId","operation":"like","weight":0,"value":"ss"}]
	 * @return
	 */
//	public static List<QueryCondition> convertJsonToQueryCondition(String queryConditionJson) {
//		List<QueryCondition> list = null;
//		if (PublicUtil.isNotEmpty(queryConditionJson)) {
//			try {
//				list = JSONArray.parseArray(queryConditionJson, QueryCondition.class);
//			} catch (Exception e) {
//				logger.warn(PublicUtil.toAppendStr("queryCondition[", queryConditionJson,
//						"] is not json or other error", e.getMessage()));
//			}
//		}
//		if (list == null)
//			list = Lists.newArrayList();
//
//		return list;
//	}
//
//	/**
//	 * 将查询json字符串转换为hql查询条件语句
//	 *
//	 * @param queryConditionJson
//	 *            格式
//	 *            [{"fieldName":"loginId","operation":"like","weight":0,"value":"ss"}]
//	 * @param paramMap
//	 *            参数map
//	 * @return
//	 */
//	public static String convertJsonQueryConditionToStr(String queryConditionJson, List<String> argList,
//			Map<String, Object> paramMap) {
//		List<QueryCondition> queryConditionList = convertJsonToQueryCondition(queryConditionJson);
//		return convertQueryConditionToStr(queryConditionList, argList, paramMap);
//	}
//
//	/**
//	 * 查询集合 转换 查询条件
//	 *
//	 * @param queryConditionList
//	 * @param paramMap
//	 *            返回的参数map
//	 * @return
//	 */
//	public static String convertQueryConditionToStr(List<QueryCondition> queryConditionList, List<String> argList,
//			Map<String, Object> paramMap) {
//		StringBuffer sb = new StringBuffer();
//		if (PublicUtil.isNotEmpty(queryConditionList)) {
//			if (paramMap == null)
//				paramMap = Maps.newHashMap();
//			java.util.Collections.sort(queryConditionList);
//			String argStr = PublicUtil.isNotEmpty(argList) ? Collections3.convertToString(argList, ".") + "." : "", operate = null;
//			for (QueryCondition queryCondition : queryConditionList) {
//				if (queryCondition.isIngore())
//					continue;
//				operate = queryCondition.getOperate().getOperator();
//				if (queryCondition.getValue() instanceof String) {
//					String tempStr = queryCondition.getValue().toString();
//					if (tempStr.contains("&")) {
//						try {
//							queryCondition.setValue(
//									new String(Encodes.unescapeHtml(tempStr).getBytes("ISO-8859-1"), "UTF-8"));
//						} catch (UnsupportedEncodingException e) {
//							e.printStackTrace();
//							logger.warn("Illegal query conditions ---------> queryFieldName[",
//									queryCondition.getFieldName(), "]  operation[", operate,
//									"] value[", queryCondition.getValue(), "], please check!!!");
//						}
//					}
//				}
//				if (queryCondition != null && SecurityHqlUtil.checkStrForHqlWhere(queryCondition.getFieldName())
//						&& SecurityHqlUtil.checkStrForHqlWhere(operate)
//						&& SecurityHqlUtil.checkStrForHqlWhere(String.valueOf(queryCondition.getValue()))) {
//					if (PublicUtil.isEmpty(operate))
//						queryCondition.setOperate(Operator.eq.getOperator());
//					sb.append(" and ").append(argStr).append(queryCondition.getFieldName()).append(" ")
//							.append(operate);
//					if (!Operator.isNotNull.equals(queryCondition.getOperate())
//							&& !Operator.isNull.equals(queryCondition.getOperate())) {
//						String paramFieldName = PublicUtil.toAppendStr(argStr, queryCondition.getFieldName())
//								.replace(".", "_");
//						if (paramFieldName.contains(","))
//							paramFieldName = PublicUtil.getRandomString(6);
//						if (SystemConfig.CONDITION_IN.equals(operate)
//								|| SystemConfig.CONDITION_NOTIN.equals(operate)) {
//							if (queryCondition.getValue() instanceof String) {
//								String val = String.valueOf(queryCondition.getValue());
//								queryCondition.setValue(val.contains(",") ? Lists.newArrayList(val.split(","))
//										: Lists.newArrayList(val));
//							}
//							if (queryCondition.getValue() instanceof Collection) {
//								Collection col = (Collection) queryCondition.getValue();
//								if (PublicUtil.isNotEmpty(col)) {
//									sb.append(" (");
//									Integer i = 0;
//									for (Iterator iterator = col.iterator(); iterator.hasNext(); i++) {
//										sb.append(":").append(PublicUtil.toAppendStr(paramFieldName, i)).append(", ");
//										paramMap.put(PublicUtil.toAppendStr(paramFieldName, i),
//												getQueryValue(queryCondition, iterator.next()));
//									}
//									sb.delete(sb.lastIndexOf(","), sb.length()).append(")");
//								}
//							} else {
//								logger.warn(PublicUtil.toAppendStr("queryFieldName[", paramFieldName,
//										"] operation is '", operate, "', but value[",
//										queryCondition.getValue(), "] is not Collection, please check!!!"));
//							}
//						} else if (SystemConfig.CONDITION_LIKE.equals(operate)
//								|| SystemConfig.CONDITION_ILIKE.equals(operate)) {
//							String val = (String) queryCondition.getValue();
//							sb.append(" :").append(paramFieldName);
//							paramMap.put(paramFieldName, !val.startsWith("%") && !val.toString().endsWith("%")
//									? PublicUtil.toAppendStr("%", val, "%") : val);
//						} else if (SystemConfig.CONDITION_BETWEEN.equals(operate)) {
//							sb.append(" :").append(paramFieldName).append("1 and :").append(paramFieldName).append("2");
//							paramMap.put(paramFieldName + "1", getQueryValue(queryCondition, null));
//							paramMap.put(paramFieldName + "2",
//									getQueryValue(queryCondition, queryCondition.getEndValue()));
//						} else {
//							sb.append(" :").append(paramFieldName);
//							paramMap.put(paramFieldName, getQueryValue(queryCondition, null));
//						}
//					}
//				} else {
//					logger.warn(PublicUtil.toAppendStr("Illegal query conditions ---------> queryFieldName[",
//							queryCondition.getFieldName(), "]  operation[", operate, "] value[",
//							queryCondition.getValue(), "], please check!!!"));
//				}
//			}
//		}
//		return sb.toString();
//	}
//
//	public static Object getQueryValue(QueryCondition queryCondition, Object val) {
//		String type = queryCondition.getAttrType();
//		if (val == null)
//			val = queryCondition.getValue();
//		if (PublicUtil.isNotEmpty(type) && PublicUtil.isNotEmpty(val)) {
//			if ("Integer".equalsIgnoreCase(type) || "int".equalsIgnoreCase(type)) {
//				val = PublicUtil.parseInt(val, 0);
//			} else if ("Long".equalsIgnoreCase(type)) {
//				val = PublicUtil.parseLong(val, 0l);
//			} else if ("Short".equalsIgnoreCase(type)) {
//				val = Short.parseShort(String.valueOf(val));
//			} else if ("Float".equalsIgnoreCase(type)) {
//				val = Float.parseFloat(String.valueOf(val));
//			} else if ("Double".equalsIgnoreCase(type)) {
//				val = Double.parseDouble(String.valueOf(val));
//			} else if ("Date".equalsIgnoreCase(type)) {
//				val = PublicUtil.parseDate(String.valueOf(val), queryCondition.getFormat());
//			}
//		}
//		return val;
//	}
//
//	/**
//	 * 将查询集合拼接到查询语句后
//	 *
//	 * @param hql
//	 * @param queryConditionList
//	 * @param paramMap
//	 * @return
//	 */
//	public static String convertJsonToQueryCondition(String hql, List<QueryCondition> queryConditionList,
//			List<String> argList, Map<String, Object> paramMap) {
//		StringBuffer sb = new StringBuffer(hql);
//		if (paramMap != null) {
//			String where = convertQueryConditionToStr(queryConditionList, argList, paramMap);
//			if (PublicUtil.isNotEmpty(where)) {
//				String upper = hql.toUpperCase();
//				int lastIndexWhere = upper.lastIndexOf(" WHERE "), lastIndexOrder = upper.lastIndexOf(" ORDER ");
//				if (lastIndexWhere == -1) {
//					sb.append(" WHERE ");
//					where = where.trim();
//					if (where.startsWith(" and") || where.startsWith(" AND") || where.startsWith("and")
//							|| where.startsWith("AND")) {
//						where = where.substring(4);
//					}
//					sb.append(where);
//				} else {
//					if (lastIndexOrder > lastIndexWhere) {
//						sb.insert(lastIndexOrder, where);
//					} else {
//						if (where.startsWith(" and") || where.startsWith(" AND") || where.startsWith("and") || where.startsWith("AND")) {
//							where = where.substring(4);
//						}
//						sb.insert( lastIndexWhere + 6, where + " and ");
//					}
//
//
//				}
//			}
//		}
//		return sb.toString();
//	}
//
//	/**
//	 * 将对象不为空的属性转换为List<QueryCondition> 仅解析基本类型
//	 *
//	 * @param obj
//	 * @param pName
//	 * @param annotationClass
//	 * @return
//	 */
//	public static List<QueryCondition> convertObjectToQueryCondition(Object entity, Map<String, Operator> operateMap) {
//		List<QueryCondition> list = Lists.newArrayList();
//		if (PublicUtil.isNotEmpty(entity)) {
//			Object val = null;
//			String key = null;
//			SearchField an = null;
//			List<String> argList = Lists.newArrayList();
//			List<Object> paramEntityList = Lists.newArrayList();
//			paramEntityList.add(Lists.newArrayList(entity, argList));
//			Object obj = null;
//			while (PublicUtil.isNotEmpty(paramEntityList)) {
//				List<Object> tempList = Lists.newArrayList(paramEntityList);
//				paramEntityList.clear();
//				// proxy.getClass().getMethod("clearCount").invoke(proxy);
//				// //情况参数位置 hibernate4之后去掉参数索引
//				for (Object objItem : tempList) {
//					if (objItem instanceof Collection) {
//						List<Object> objItemList = (List<Object>) objItem;
//						if (PublicUtil.isEmpty(objItemList)) {
//							continue;
//						} else {
//							obj = objItemList.get(0);
//							argList = (List<String>) objItemList.get(1);
//						}
//					} else {
//						obj = objItem;
//					}
//					PropertyDescriptor[] ps = PropertyUtils.getPropertyDescriptors(obj);
//					for (PropertyDescriptor p : ps) {
//						key = p.getName();
//						try {
//							val = PropertyUtils.getProperty(obj, key);
//							an = Reflections.getAnnotation(obj, key, SearchField.class);
//						} catch (Exception e) {
//							logger.error(PublicUtil.toAppendStr("key:", key, "exception:", e));
//							continue;
//						}
//						if (PublicUtil.isNotEmpty(val) && an != null) {
//							if (Reflections.checkClassIsBase(val.getClass().getName())) {
//								argList.add(key);
//								paramEntityList.add(Lists.newArrayList(val, Lists.newArrayList(argList)));
//								argList.remove(key);
//							} else {
//								if (PublicUtil.isNotEmpty(argList))
//									key = PublicUtil.toAppendStr(Collections3.convertToString(argList, "."), ".", key);
//								list.add(new QueryCondition(key,
//										PublicUtil.isNotEmpty(operateMap) && PublicUtil.isNotEmpty(operateMap.get(key))
//												? operateMap.get(key) : an.op(),
//										val));
//							}
//						}
//					}
//
//				}
//			}
//
//		}
//		return list;
//	}



}
