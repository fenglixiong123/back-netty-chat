package com.flx.netty.chat.common.utils.date;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * @Author Fenglixiong
 * @Create 2018.11.09 23:21
 * @Description
 **/
@Slf4j
public class DateUtils {

    public static final String DEFAULT_SIMPLE_PATTEN = "yyyyMMddHHmmss";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_EN_DATETIME_FMT = "MM/dd/yyyy HH:mm:ss";
    public static final String DEFAULT_EN_DATE_FMT = "MM/dd/yyyy";
    public static final String DEFAULT_CN_DATETIME_FMT = "yyyy年MM月dd日HH时mm分ss秒";
    public static final String DEFAULT_CN_DATE_FMT = "yyyy年MM月dd日";
    public static final String DEFAULT_CN_TIME_FMT = "HH时mm分ss秒";

    public static String nowStr() {
        return dateTime2Str(new Date());
    }

    public static String nowSimpleStr() {
        return dateTime2Str(new Date(),DEFAULT_SIMPLE_PATTEN);
    }

    /**
     * 获得毫秒
     * @param time
     * @param unit
     * @return
     */
    public static long toMillis(long time, TimeUnit unit){
        if(time <= 0){
            return 0;
        }
        switch (unit){
            case DAYS:
                return TimeUnit.DAYS.toMillis(time);
            case HOURS:
                return TimeUnit.HOURS.toMillis(time);
            case MINUTES:
                return TimeUnit.MINUTES.toMillis(time);
            case SECONDS:
                return TimeUnit.SECONDS.toMillis(time);
            case MILLISECONDS:
                return time;
            default:
                return 0;
        }
    }

    public static boolean before(Date d1, Date d2) throws Exception {
        if (d1 == null) {
            log.error("d1 is null");
            throw new Exception("存在空的日期");
        } else if (d2 == null) {
            log.error("d2 is null");
            throw new Exception("存在空的日期");
        } else {
            return d1.before(d2);
        }
    }

    public static boolean afterNowMin(Date d) throws Exception {
        Date nowMin = getTodayMin();
        return after(d, nowMin);
    }

    public static boolean beforeNowMin(Date d) throws Exception {
        Date nowMin = getTodayMin();
        return before(d, nowMin);
    }

    public static boolean beforeNow(Date d) throws Exception {
        if (d == null) {
            log.error("d is null");
            throw new Exception("存在空的日期");
        } else {
            Date now = new Date();
            return d.before(now);
        }
    }

    public static boolean afterNow(Date d) throws Exception {
        if (d == null) {
            log.error("d is null");
            throw new Exception("存在空的日期");
        } else {
            Date now = new Date();
            return d.after(now);
        }
    }

    public static boolean after(Date d1, Date d2) throws Exception {
        if (d1 == null) {
            log.error("d1 is null");
            throw new Exception("存在空的日期");
        } else if (d2 == null) {
            log.error("d2 is null");
            throw new Exception("存在空的日期");
        } else {
            return d1.after(d2);
        }
    }

    public static boolean betweenDates(Date d, Date start, Date end) throws Exception {
        if (d == null) {
            log.error("d is null");
            throw new Exception("存在空的日期");
        } else if (start == null) {
            log.error("start is null");
            throw new Exception("起始日期为空");
        } else if (end == null) {
            log.error("end is null");
            throw new Exception("结束日期为空");
        } else if (start.after(end)) {
            log.error("start is after end");
            throw new Exception("起始日期大于结束日期");
        } else {
            return (d.after(start) || d.equals(start)) && (d.before(end) || d.equals(end));
        }
    }

    public static boolean nowBetweenDates(Date start, Date end) throws Exception {
        return betweenDates(new Date(), start, end);
    }

    public static String formatDate(Date date) {
        return DateFormatUtils.format(date, "MM/dd/yyyy");
    }

    public static String formatDatetime(Date date) {
        return DateFormatUtils.format(date, "MM/dd/yyyy HH:mm:ss");
    }

    public static Date parseDate(String dateStr) throws Exception {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        } else {
            try {
                return org.apache.commons.lang3.time.DateUtils.parseDate(dateStr, new String[]{"MM/dd/yyyy"});
            } catch (ParseException var2) {
                log.error("parse dateStr:{} using fmt:{} fail", dateStr, "MM/dd/yyyy");
                throw new Exception("无效的日期");
            }
        }
    }

    public static Date parseDatetime(String datetimeStr) throws Exception {
        if (StringUtils.isEmpty(datetimeStr)) {
            return null;
        } else {
            try {
                return org.apache.commons.lang3.time.DateUtils.parseDate(datetimeStr, new String[]{"MM/dd/yyyy HH:mm:ss"});
            } catch (ParseException var2) {
                log.error("parse datetimeStr:{} using fmt:{} fail", datetimeStr, "MM/dd/yyyy HH:mm:ss");
                throw new Exception("无效的日期");
            }
        }
    }

    public static Date str2Date(String dateStr, String pattern) throws Exception {
        if (null == dateStr) {
            return null;
        } else {
            if (null == pattern) {
                pattern = "yyyy-MM-dd";
            }

            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern(pattern);

            try {
                return format.parse(dateStr);
            } catch (ParseException var4) {
                var4.printStackTrace();
                throw new Exception("转换日期格式时出错");
            }
        }
    }

    public static Date str2DateTime(String dateStr)throws Exception{
        return str2DateTime(dateStr,DEFAULT_DATE_TIME_FORMAT);
    }

    public static Date str2DateTime(String dateStr, String pattern) throws Exception {
        if (null == dateStr) {
            return null;
        } else {
            if (null == pattern) {
                pattern = "yyyy-MM-dd HH:mm:ss";
            }

            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern(pattern);

            try {
                return format.parse(dateStr);
            } catch (ParseException var4) {
                var4.printStackTrace();
                throw new Exception("转换日期格式时出错");
            }
        }
    }

    public static String date2Str(Date date, String pattern) {
        if (null == date) {
            return null;
        } else {
            if (null == pattern) {
                pattern = "yyyy-MM-dd";
            }

            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern(pattern);
            return format.format(date);
        }
    }

    public static String dateTime2Str(Date date){
        return dateTime2Str(date,DEFAULT_DATE_TIME_FORMAT);
    }

    public static String dateTime2Str(Date date, String pattern) {
        if (null == date) {
            return null;
        } else {
            if (null == pattern) {
                pattern = "yyyy-MM-dd HH:mm:ss";
            }

            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern(pattern);
            return format.format(date);
        }
    }

    public static String getCurrentTime() {
        return (new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(new Date());
    }

    public static String thisDate() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        return (new SimpleDateFormat("yyyy-MM-dd")).format(calendar.getTime());
    }

    public static String nextYearDate() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(1, calendar.get(1) + 1);
        return (new SimpleDateFormat("yyyy-MM-dd")).format(calendar.getTime());
    }

    public static String thisTime() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        return (new SimpleDateFormat("HH:mm:ss")).format(calendar.getTime());
    }

    public static String thisDateTime() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime());
    }

    public static int getLastDayOfMonth(Date firstDate) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(1, firstDate.getYear());
        cal.set(2, firstDate.getMonth());
        return cal.getActualMaximum(5);
    }

    public static Date getDayMin(Date sDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(sDate);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date getDayMax(Date sDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(sDate);
        cal.set(11, 23);
        cal.set(12, 59);
        cal.set(13, 59);
        return cal.getTime();
    }

    public static Date getTodayMin() {
        Calendar cal = Calendar.getInstance();
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date getTomorrowMin() {
        Calendar cal = Calendar.getInstance();
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        cal.add(5, 1);
        return cal.getTime();
    }

    public static Date genDiffDate(Date standard, int type, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(standard);
        cal.add(type, amount);
        return cal.getTime();
    }

    public static Date genHourStart(Date standard) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(standard);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date getBeforeDayMin(Date date, int beforeDay) {
        return getDayMin(date, -beforeDay);
    }

    public static Date getBeforeDayMax(Date date, int beforeDay) {
        return getDayMax(date, -beforeDay);
    }

    public static Date getAfterDayMin(Date date, int afterDay) {
        return getDayMin(date, afterDay);
    }

    public static Date getAfterDayMax(Date date, int afterDay) {
        return getDayMax(date, afterDay);
    }

    public static Date getDayMin(Date date, int addDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        cal.add(5, addDay);
        return cal.getTime();
    }

    public static Date getDayMax(Date date, int addDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(11, 23);
        cal.set(12, 59);
        cal.set(13, 59);
        cal.set(14, 999);
        cal.add(5, addDay);
        return cal.getTime();
    }

    public static Date getDate(Date date, int addDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(5, addDay);
        return cal.getTime();
    }

    public static Date addMonths(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(2, months);
        return cal.getTime();
    }

    public static Date addWeeks(Date date, int weeks) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(3, weeks);
        return cal.getTime();
    }

    public static Date addYears(Date date, int years) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(1, years);
        return cal.getTime();
    }

    public static int diff(Date date1, Date date2) throws Exception {
        long day = 86400000L;
        String str1 = date2Str(date1, "yyyy-MM-dd");
        date1 = str2Date(str1, "yyyy-MM-dd");
        String str2 = date2Str(date2, "yyyy-MM-dd");
        date2 = str2Date(str2, "yyyy-MM-dd");
        return (int)((date2.getTime() - date1.getTime()) / day);
    }

    public static double getMonthSpace(Date date1, Date date2) throws Exception {
        double result = 0.0D;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        int year1 = c1.get(1);
        int year2 = c2.get(1);
        int month1 = c1.get(2);
        int month2 = c2.get(2);
        int day1 = c1.get(5);
        int day2 = c2.get(5);
        if (year1 == year2) {
            if (month1 >= month2) {
                if (day1 >= day2) {
                    result = (double)(month1 - month2) + (double)Math.abs(day1 - day2) / 30.0D;
                } else {
                    result = getDifferMonths(c1, c2);
                }
            } else if (day1 <= day2) {
                result = (double)(month2 - month1) + (double)Math.abs(day2 - day1) / 30.0D;
            } else {
                result = getDifferMonths(c2, c1);
            }
        } else {
            int years;
            if (year1 > year2) {
                years = year1 - year2 - 1;
                if (day1 <= day2) {
                    result = (double)(years * 12 + (12 - month2) + month1) - (double)(day2 - day1) / 30.0D;
                } else {
                    result = (double)(years * 12 + (12 - month2) + month1) + (double)(day1 - day2) / 30.0D;
                }
            } else if (year1 < year2) {
                years = year2 - year1 - 1;
                if (day1 >= day2) {
                    result = (double)(years * 12 + (12 - month1) + month2) - (double)(day1 - day2) / 30.0D;
                } else {
                    result = (double)(years * 12 + (12 - month1) + month2) + (double)(day2 - day1) / 30.0D;
                }
            }
        }

        return result;
    }

    private static double getDifferMonths(Calendar c1, Calendar c2) throws Exception {
        int month1 = c1.get(2);
        int month2 = c2.get(2);
        int differM = month1 - month2 - 1;
        c2.set(2, month2 + differM);
        int differD = diff(c2.getTime(), c1.getTime());
        if (month1 == month2) {
            differM = 0;
        }

        return (double)differM + (double)differD / 30.0D;
    }

    private static double getDifferYearMonths(Calendar c1, Calendar c2, int years) throws Exception {
        int year2 = c2.get(1);
        int month1 = c1.get(2);
        int month2 = c2.get(2);
        int differM = month1 - month2 - 1;
        c2.set(1, year2 + years);
        c2.set(2, month2 + differM);
        int differD = Math.abs(diff(c2.getTime(), c1.getTime()));
        return (double)(years * 12 + differM) + (double)differD / 30.0D;
    }

    public static int diff(Date date) throws Exception {
        return diff(new Date(), date);
    }

    public static long diffMilli(Date date) {
        return diffMilli(new Date(), date);
    }

    public static long diffMilli(Date date1, Date date2) {
        return date1.getTime() - date2.getTime();
    }

    public static int compareDate(Date date1, Date date2, String pattern) {
        String d1 = date2Str(date1, pattern);
        String d2 = date2Str(date2, pattern);
        return d1.compareTo(d2);
    }

    public static int compareDateTime(Date time1, Date time2, String pattern) {
        String d1 = dateTime2Str(time1, pattern);
        String d2 = dateTime2Str(time2, pattern);
        return d1.compareTo(d2);
    }

    public static XMLGregorianCalendar dateToXmlDate(Date date) throws DatatypeConfigurationException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        DatatypeFactory dtf = null;
        dtf = DatatypeFactory.newInstance();
        XMLGregorianCalendar dateType = dtf.newXMLGregorianCalendar();
        dateType.setYear(cal.get(1));
        dateType.setMonth(cal.get(2) + 1);
        dateType.setDay(cal.get(5));
        dateType.setHour(cal.get(11));
        dateType.setMinute(cal.get(12));
        dateType.setSecond(cal.get(13));
        return dateType;
    }

    public static Date xmlDate2Date(XMLGregorianCalendar cal) {
        return cal.toGregorianCalendar().getTime();
    }

    public static boolean isLeapyear(Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return gregorianCalendar.isLeapYear(gregorianCalendar.get(1));
    }

    public static Date getFirstDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(5, 1);
        return c.getTime();
    }

    public static Date getLastDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return getLastDateOfMonth(c);
    }

    public static Date getLastDateOfMonth(Calendar calendar) {
        calendar.set(5, calendar.getActualMaximum(5));
        return calendar.getTime();
    }

    public static boolean isLastDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return isLastDateOfMonth(c);
    }

    public static boolean isLastDateOfMonth(Calendar date) {
        return date.getActualMaximum(5) == date.get(5);
    }

    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(1);
    }

    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(2) + 1;
    }

    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(5);
    }

    public static boolean sameMonth(Date beginDate, Date endDate) {
        boolean result = false;
        int calBegin = getCalendarMonth(beginDate);
        int calEnd = getCalendarMonth(endDate);
        if (calBegin == calEnd) {
            result = true;
        }

        return result;
    }

    public static boolean sameYear(Date beginDate, Date endDate) {
        boolean result = false;
        int calBegin = getCalendarYear(beginDate);
        int calEnd = getCalendarYear(endDate);
        if (calBegin == calEnd) {
            result = true;
        }

        return result;
    }

    public static int getCalendarMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(2);
    }

    public static int getCalendarYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(1);
    }

    public static Date getLastDateOfLastMonth(Date paramDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(paramDate);
        c.set(5, 1);
        c.add(5, -1);
        return c.getTime();
    }

    public static Date setDate(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(5, day);
        return cal.getTime();
    }

    public static Date getNextRepayDate(Date thisRepayDate, int repayDay) throws Exception {
        Date nextRepayDate = null;
        if (repayDay == 31) {
            Date newDate = addMonths(thisRepayDate, 1);
            nextRepayDate = getLastDateOfMonth(newDate);
        } else if (repayDay == 30) {
            nextRepayDate = addMonths(thisRepayDate, 1);
            if (getMonth(nextRepayDate) != 2 && getDay(nextRepayDate) < repayDay) {
                nextRepayDate = setDate(nextRepayDate, repayDay);
            }
        } else {
            nextRepayDate = addMonths(thisRepayDate, 1);
        }

        return nextRepayDate;
    }

    public static Date getRepayDate(Date thisRepayDate, int repayDay, int n) {
        Date nextRepayDate = null;
        if (repayDay == 31) {
            Date newDate = addMonths(thisRepayDate, n);
            nextRepayDate = getLastDateOfMonth(newDate);
        } else if (repayDay == 30) {
            nextRepayDate = addMonths(thisRepayDate, n);
            if (getMonth(nextRepayDate) != 2 && getDay(nextRepayDate) < repayDay) {
                nextRepayDate = setDate(nextRepayDate, repayDay);
            }
        } else {
            nextRepayDate = addMonths(thisRepayDate, n);
        }

        return nextRepayDate;
    }

    public static String getSchedulerCronExpression(Date sdate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdate);
        int syear = cal.get(1);
        int month = cal.get(2);
        int date = cal.get(5);
        int hour = cal.get(11);
        int minute = cal.get(12);
        int second = cal.get(13);
        String str = second + " " + minute + " " + hour + " " + date + " " + (month + 1) + " ? " + syear;
        return str;
    }

    public static long getTimestamp() {
        Calendar cal = Calendar.getInstance();
        cal.set(10, cal.get(10) + 8);
        return cal.getTime().getTime();
    }

}
