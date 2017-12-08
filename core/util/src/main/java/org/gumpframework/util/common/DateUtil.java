/*
 * @(#)DateUtility.java $Date: 2009/06/30 12:19:36 $
 */
package org.gumpframework.util.common;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期操作类
 *
 * @author Administrator
 */
public class DateUtil {

    /** 日期格式 yyyy-MM-dd */
    public static final String DateFormat1 = "yyyy-MM-dd";

    /** 日期格式 yyyy年MM月dd日 */
    public static final String DateFormat2 = "yyyy年MM月dd日";

    public static final String DateFormat3 = "yyyy-MM-dd HH:mm:ss";

    public static final String DateFormat4 = "HH:mm:ss";

    public static final String DateFormat5 = "MM-dd";

    public static final String dateFormat6 = "yyyyMMdd";

    public static final String START="start";

    public static final String END="end";

    public static String convertDate(String datestr, String format1, String format2) {

        return getFormatDate(getDateFromStr(datestr, format1), format2);
    }

    /**
     * 根据日期字符返回日期对象
     *
     * @param datestr
     *            比如：2006-02-03
     * @param format
     *            比如yyyy-MM-dd
     * @return 日期对象
     */
    public static Date getDateFromStr(String datestr, String format) {
        if (datestr == null || "".equalsIgnoreCase(datestr)) {
            return null;
        }

        SimpleDateFormat dateformat = new SimpleDateFormat(format);
        Date result = null;
        try {
            result = dateformat.parse(datestr);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 格式化日期
     *
     * @param indate
     *            日期对象
     * @param format
     *            比如yyyy-MM-dd
     * @return 比如：2006-02-03
     */
    public static String getFormatDate(Date indate, String format) {
        if (indate == null) {
            return "";
        }

        SimpleDateFormat dateformat = new SimpleDateFormat(format);

        return dateformat.format(indate);
    }

    /**
     * 得到当前时间
     *
     * @return 当前时间
     */
    public static Date getCurrentTime() {
        return new Date();
    }

    /**
     * 日期加减年数
     *
     * @param inDate
     *            初始日期
     * @param addYear
     *            要加的年数(负值代表减)
     * @return 结果日期
     */
    public static Date addYearS(final Date inDate, final int addYear) {

        return addDates(inDate, addYear, Calendar.YEAR);
    }

    /**
     * 日期加减月数
     *
     * @param inDate
     *            初始日期
     * @param addMonth
     *            要加的月数(负值代表减)
     * @return 结果日期
     */
    public static Date addMonthS(final Date inDate, final int addMonth) {

        return addDates(inDate, addMonth, Calendar.MONTH);
    }

    /**
     * 日期加减天数
     *
     * @param inDate
     *            初始日期
     * @param addDay
     *            要加的天数(负值代表减)
     * @return 结果日期
     */
    public static Date addDays(final Date inDate, final int addDay) {
        return addDates(inDate, addDay, Calendar.DAY_OF_MONTH);
    }

    /**
     * 日期加减小时数
     *
     * @param inDate
     *            初始日期
     * @param addDay
     *            要加的小时数(负值代表减)
     * @return 结果日期
     */
    public static Date addHours(final Date inDate, final int addDay) {

        return addDates(inDate, addDay, Calendar.HOUR_OF_DAY);
    }

    /**
     * 日期加减分钟数
     *
     * @param inDate
     *            初始日期

     *        要加的小时数(负值代表减)
     * @return 结果日期
     */
    public static Date addMinutes(final Date inDate, final int addMinute) {

        return addDates(inDate, addMinute, Calendar.MINUTE);
    }

    /**
     * 日期加减秒数
     *
     * @param inDate
     *            初始日期
     * @param addSecond
     *            要加的秒数(负值代表减)
     * @return 结果日期
     */
    public static Date addSeconds(final Date inDate, final int addSecond) {

        return addDates(inDate, addSecond, Calendar.SECOND);
    }

    /**
     * 计算日期加减
     *
     * @param inDate
     *            初始日期
     * @param addDate
     *            要加的日期
     * @param field
     *            要加的域
     * @return 结果日期
     */
    private static Date addDates(final Date inDate, final int addDate, final int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inDate);
        calendar.add(field, addDate);
        return calendar.getTime();
    }

    /**
     * 计算两个日期之间的天数
     *
     * @param fromDate
     *            开始日
     * @param toDate
     *            结束日
     * @param blnAbs
     *            是否取绝对值
     * @return 天数
     */
    public static int calcDays(Date fromDate, Date toDate, boolean blnAbs) {
        long miliSeconds1 = fromDate.getTime();
        long miliSeconds2 = toDate.getTime();
        if (fromDate.compareTo(toDate) > 0 && blnAbs == false) {
            // 不足2天算1天
            return (int) ((miliSeconds2 - miliSeconds1) / 86400000);
        }
        // 超过1天算2天
        return (int) (Math.abs(miliSeconds2 - miliSeconds1 - 1) / 86400000) + 1;
    }

    /**
     * 计算两个日期之间的天数 （过一个0点算一天）
     *
     * @param fromDate
     *            开始日期
     * @param toDate
     *            结束日期
     * @return 天数
     */
    public static int calcDays(Date fromDate, Date toDate) {
        long miliSeconds1 = DateUtil.getFirstTimeOfDay(fromDate).getTime();
        long miliSeconds2 = DateUtil.getFirstTimeOfDay(toDate).getTime();
        return (int) ((miliSeconds2 - miliSeconds1) / 86400000);
    }

    /**
     * 计算两个日期之间的天数 （每一个凌晨算一天）
     *
     * @param fromDate
     *            开始日期
     * @param toDate
     *            结束日期
     * @return 天数
     */
    public static int pastDays(Date fromDate, Date toDate) {
        long miliSeconds1 = DateUtil.getFirstTimeOfDay(fromDate).getTime();
        long miliSeconds2 = DateUtil.getFirstTimeOfDay(toDate).getTime();
        return (int) ((miliSeconds2 - miliSeconds1) / 86400000) + 1;
    }


    /**
     * 计算两个日期之间的年数
     *
     * @param fromDate
     *            开始日期
     * @param toDate
     *            结束日期
     * @return 年数
     */
    public static int calcYears(Date fromDate, Date toDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fromDate);
        int years = 0;
        Date temp = null;
        if (fromDate.before(toDate)) {
            temp = DateUtil.addYearS(fromDate, 1);
            while (!temp.after(toDate)) {
                temp = DateUtil.addYearS(temp, 1);
                years++;
            }
        } else {
            temp = DateUtil.addYearS(fromDate, -1);
            while (!temp.before(toDate)) {
                temp = DateUtil.addYearS(temp, -1);
                years--;
            }
        }
        return years;
    }

    /**
     * 得到本年的开始时间
     *
     * @param date
     *            日期对象
     * @return 本年开始时间
     */
    public static Date getFirstDayOfYear(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 得到本月的开始时间
     * @param date 日期对象
     * @return 本月开始时间
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 得到本周的开始时间
     * @param date 日期对象
     * @return 本周开始时间，周一开始
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }
    /**
     * 得到本天的开始时间
     *
     * @param date
     *            日期对象
     * @return 本天的开始时间
     */
    public static Date getFirstTimeOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * 得到本小时的开始时间
     * @param date 日期对象
     * @return 本天的开始时间
     */
    public static Date getFirstTimeOfHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 得到本天的结束时间
     * @param date 日期对象
     * @return 本天的开始时间
     */
    public static Date getEndTimeOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }



    /**
     * 得到日期的年份
     * @param date 日期对象
     * @return 日期的年份
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 得到日期的月份 (1-12)
     *
     * @param date
     *            日期对象
     * @return 日期的月份
     */
    public static int getMonthOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 得到日期是年中的第几周
     * @param date   日期对象
     * @return 年中的第几周
     */
    public static int getWeekOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 得到日期是一年中的第几天 (1-366)
     * @param date    日期对象
     * @return 年中的第几天
     */
    public static int getDayOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 得到日期是一月中的第几天 (1-31)
     * @param date   日期对象
     * @return 月中的第几天
     */
    public static int getDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到日期是一周中的第几天(星期日开始，是1)
     *
     * @param date
     *            日期对象
     * @return 周中的第几天
     */
    public static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 得到时间是一天中的哪个小时
     *
     * @param date
     *            日期对象
     * @return 天中的哪个小时
     */
    public static int getHourOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 得到时间是多少分钟
     *
     * @param date
     *            日期对象
     * @return 天中的哪个小时
     */
    public static int getMinuteOfHour(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 设置小时
     *
     * @param date
     *            日期对象
     * @return 天中的哪个小时
     */
    public static Date setHourOfDay(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY, hour);

        return calendar.getTime();
    }

    /**
     * 设置分钟
     *
     * @param date
     *            日期对象
     * @return 天中的哪个小时
     */
    public static Date setMinuteOfHour(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        calendar.set(Calendar.MINUTE, minute);

        return calendar.getTime();
    }

    /**
     * 根据输入的毫秒数，得到 "星期几",如“星期二”
     *
     * @param date
     *            日期对象
     * @return 周中的第几天
     */
    public static String getWeekDay(Date date) {
        SimpleDateFormat weekFormatter = new SimpleDateFormat("E");

        return weekFormatter.format(date);
    }

    /**
     * 是否是周末
     *
     * @param date
     * @return
     */
    public static boolean isWeekDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        }
        return false;
    }

    /**
     * 根据数字得到对应的星期名字
     *
     * @param i
     *            数字
     * @return 对应的星期名字
     */
    public static String getWeekName(int i) {
        switch (i) {
            case 1:
                return "星期日";
            case 2:
                return "星期一";
            case 3:
                return "星期二";
            case 4:
                return "星期三";
            case 5:
                return "星期四";
            case 6:
                return "星期五";
            case 7:
                return "星期六";

            default:
                return "无效数字";
        }
    }
    /**
     * 根据数字得到对应的时间段名字
     *
     * @param i
     *            数字
     * @return 对应的时间段名字
     */
    public static String getHourInterzone(int i) {
        switch (i) {
            case 0:
                return "00:00-01:00";
            case 1:
                return "01:00-02:00";
            case 2:
                return "02:00-03:00";
            case 3:
                return "03:00-04:00";
            case 4:
                return "04:00-05:00";
            case 5:
                return "05:00-06:00";
            case 6:
                return "06:00-07:00";
            case 7:
                return "07:00-08:00";
            case 8:
                return "08:00-09:00";
            case 9:
                return "09:00-10:00";
            case 10:
                return "10:00-11:00";
            case 11:
                return "11:00-12:00";
            case 12:
                return "12:00-13:00";
            case 13:
                return "13:00-14:00";
            case 14:
                return "14:00-15:00";
            case 15:
                return "15:00-16:00";
            case 16:
                return "16:00-17:00";
            case 17:
                return "17:00-18:00";
            case 18:
                return "18:00-19:00";
            case 19:
                return "19:00-20:00";
            case 20:
                return "20:00-21:00";
            case 21:
                return "21:00-22:00";
            case 22:
                return "22:00-23:00";
            case 23:
                return "23:00-24:00";

            default:
                return "无效数字";
        }
    }

    /**
     * 获取往前N个月的日期
     * */
    public static Date getMonthBefore(int month, Date currentDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(calendar.MONTH, -month); //设置为前N月
        return calendar.getTime();
    }

    /**
     * 获取指定天数前的日期
     * @param day
     * @param currentDate
     * @return
     */
    public static Date getDayBefore(int day,Date currentDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH,-day);
        return  calendar.getTime();
    }

    /**
     * 获取某个时间段内的所有日期
     * */
    public static List<Date> findDates(Date dBegin, Date dEnd){
        List lDate = new ArrayList();
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())){
            lDate.add(calBegin.getTime());
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
        }
        if(DateUtil.getDayOfMonth(calEnd.getTime()) == DateUtil.getDayOfMonth(calBegin.getTime())){
            lDate.add(calEnd.getTime());
        }
        return lDate;
    }

    /**
     * 获取某段时间内的所有日期
     * */
    public static List<String> findDays(Date dBegin, Date dEnd){
        List<String> dayList = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Date> lDate = findDates(dBegin, dEnd);
        for (Date date : lDate){
            dayList.add(sdf.format(date));
        }
        return  dayList;
    }

    /**
     * 获取某个时间段内的所有月份日期
     * */
    public static List<Date> findMonths(Date dBegin, Date dEnd){
        List lDate = new ArrayList();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())){
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }

    /**
     * 获取某段时间内的所有月份
     * */
    public static List<String> findMonthList(Date dBegin, Date dEnd){
        List<String> monthList = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        List<Date> lDate = findMonths(dBegin, dEnd);
        for (Date date : lDate){
            monthList.add(sdf.format(date));
        }
        return  monthList;
    }

    //addMethod
    /**
     * 获取date的月份的时间范围
     * @param date
     * @return
     */
    public static Map<String,Date> getMonthRange(Date date) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(date);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        setMaxTime(startCalendar);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(date);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setMaxTime(endCalendar);

        Map<String,Date> dateMap = Maps.newHashMap();
        dateMap.put(START,startCalendar.getTime());
        dateMap.put(END,endCalendar.getTime());

        return dateMap;
    }

    /**
     * 获取昨天的时间范围
     * @return
     */
    public static Map<String,Date> getYesterdayRange() {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.add(Calendar.DAY_OF_MONTH, -1);
        setMinTime(startCalendar);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.add(Calendar.DAY_OF_MONTH, -1);
        setMaxTime(endCalendar);

        Map<String,Date> dateMap = Maps.newHashMap();
        dateMap.put(START,startCalendar.getTime());
        dateMap.put(END,endCalendar.getTime());

        return dateMap;
    }

    /**
     * 获取当前月份的时间范围
     * @return
     */
    public static Map<String,Date> getThisMonth(){
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        setMinTime(startCalendar);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setMaxTime(endCalendar);

        Map<String,Date> dateMap = Maps.newHashMap();
        dateMap.put(START,startCalendar.getTime());
        dateMap.put(END,endCalendar.getTime());

        return dateMap;
    }

    /**
     * 获取上个月的时间范围
     * @return
     */
    public static Map<String,Date> getLastMonth(){
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.add(Calendar.MONTH, -1);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        setMinTime(startCalendar);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.add(Calendar.MONTH, -1);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setMaxTime(endCalendar);

        Map<String,Date> dateMap = Maps.newHashMap();
        dateMap.put(START,startCalendar.getTime());
        dateMap.put(END,endCalendar.getTime());

        return dateMap;
    }

    /**
     * 获取下个月的时间范围
     * @return
     */
    public static Map<String,Date> getNextMonth(Date date){
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(date);
        startCalendar.add(Calendar.MONTH, +1);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        setMinTime(startCalendar);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(date);
        endCalendar.add(Calendar.MONTH, +1);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setMaxTime(endCalendar);

        Map<String,Date> dateMap = Maps.newHashMap();
        dateMap.put(START,startCalendar.getTime());
        dateMap.put(END,endCalendar.getTime());

        return dateMap;
    }

    /**
     * 获取当前季度的时间范围
     * @return current quarter
     */
    public static Map<String,Date> getThisQuarter(Date date) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(date);
        startCalendar.set(Calendar.MONTH, ((int) startCalendar.get(Calendar.MONTH) / 3) * 3);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        setMinTime(startCalendar);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(date);
        endCalendar.set(Calendar.MONTH, ((int) startCalendar.get(Calendar.MONTH) / 3) * 3 + 2);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setMaxTime(endCalendar);

        Map<String,Date> dateMap = Maps.newHashMap();
        dateMap.put(START,startCalendar.getTime());
        dateMap.put(END,endCalendar.getTime());

        return dateMap;
    }
    /**
     * 获取上个季度的时间范围
     * @return
     */
    public static Map<String,Date> getLastQuarter() {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.MONTH, ((int) startCalendar.get(Calendar.MONTH) / 3 - 1) * 3);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        setMinTime(startCalendar);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.MONTH, ((int) endCalendar.get(Calendar.MONTH) / 3 - 1) * 3 + 2);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setMaxTime(endCalendar);

        Map<String,Date> dateMap = Maps.newHashMap();
        dateMap.put(START,startCalendar.getTime());
        dateMap.put(END,endCalendar.getTime());

        return dateMap;
    }

    /**
     * 获取下个季度的时间范围
     * @return
     */
    public static Map<String,Date> getNextQuarter(Date date) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(date);
        startCalendar.set(Calendar.MONTH, ((int) startCalendar.get(Calendar.MONTH) / 3 + 1) * 3);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        setMinTime(startCalendar);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(date);
        endCalendar.set(Calendar.MONTH, ((int) endCalendar.get(Calendar.MONTH) / 3 + 1) * 3 + 2);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setMaxTime(endCalendar);

        Map<String,Date> dateMap = Maps.newHashMap();
        dateMap.put(START,startCalendar.getTime());
        dateMap.put(END,endCalendar.getTime());

        return dateMap;
    }
    /**
     * 判断当前时间是否为本季度的最后一个月
     * @return
     */
    public static  int isLastMonthInThisQuarter(Date date){
        Calendar thisMonth = Calendar.getInstance();
        thisMonth.setTime(date);
        int nowMonth = thisMonth.get(Calendar.MONTH)+1;
        Map<String,Date>  dateRange = getThisQuarter(new Date());
        Calendar thisQuarter = Calendar.getInstance();
        thisQuarter.setTime(dateRange.get(END));
        int maxMonth = thisQuarter.get(Calendar.MONTH)+1;
        if (nowMonth<maxMonth){
            return 1;
        }else if (nowMonth>maxMonth){
            return 2;
        }else {
            return 3;
        }
    }

    /**
     * 判断当前时间是否为本年的最后一个月
     * @return
     */
    public static  boolean isLastMonthInThisYear(Date date){
        Calendar thisMonth = Calendar.getInstance();
        thisMonth.setTime(date);
        int nowMonth = thisMonth.get(Calendar.MONTH)+1;
        int maxMonth = 12;
        if (nowMonth==maxMonth){
            return true;
        }
        return false;
    }
    /**
     * 得到今年的时间范围
     * @return
     */
    public static   Map<String,Date> getThisYear(){
        Calendar start = Calendar.getInstance();
        try {
            start.setTime(new Date());
            start.set(Calendar.MONTH, 0);
            start.set(Calendar.DAY_OF_MONTH, start.getActualMinimum(Calendar.DAY_OF_MONTH));
            setMinTime(start);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar end = Calendar.getInstance();
        try {
            end.setTime(new Date());
            end.set(Calendar.MONTH, 11);
            end.set(Calendar.DAY_OF_MONTH, end.getMaximum(Calendar.DAY_OF_MONTH));
            setMaxTime(end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String,Date> dateMap = Maps.newHashMap();
        dateMap.put(START,start.getTime());
        dateMap.put(END,end.getTime());
        return dateMap;
    }

    /**
     * 得到明年的时间范围
     * @return
     */
    public static   Map<String,Date> getNextYear(){
        Calendar start = Calendar.getInstance();
        try {
            start.setTime(new Date());
            start.add(Calendar.YEAR,1);
            start.set(Calendar.MONTH, 0);
            start.set(Calendar.DAY_OF_MONTH, start.getActualMinimum(Calendar.DAY_OF_MONTH));
            setMinTime(start);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Calendar end = Calendar.getInstance();
        try {
            end.setTime(new Date());
            end.add(Calendar.YEAR,1);
            end.set(Calendar.MONTH, 11);
            end.set(Calendar.DAY_OF_MONTH, end.getMaximum(Calendar.DAY_OF_MONTH));
            setMaxTime(end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String,Date> dateMap = Maps.newHashMap();
        dateMap.put(START,start.getTime());
        dateMap.put(END,end.getTime());

        return dateMap;
    }

    private static void setMinTime(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private static void setMaxTime(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
    }


    public static String format(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(DateFormat3);
        if(date==null){
          return null;
        }
        return sdf.format(date);
    }

    /**
     * 获取过去几个月的时间范围
     * @return
     */
    public static List<Map<String,Date>> getLastDateRange(int num){
        List<Map<String,Date>> dateRanges = Lists.newArrayList();
        Date nowDate = new Date();
        for (int i=1;i<=num;i++){
            Date before = getMonthBefore(1,nowDate);
            Map<String,Date> dateMap = Maps.newHashMap();
            dateMap.put(START,before);
            dateMap.put(END,nowDate);
            dateRanges.add(dateMap);
            nowDate=before;
        }

        return dateRanges;
    }

    /**
     * 获取当前日期的最后时间
     * @param date
     * @return
     */
    public static Date getLastTimeOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 99);

        return calendar.getTime();
    }


}
