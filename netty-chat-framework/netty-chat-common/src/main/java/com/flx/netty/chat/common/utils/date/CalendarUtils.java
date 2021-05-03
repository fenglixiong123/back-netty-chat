package com.flx.netty.chat.common.utils.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CalendarUtils {

    private static Calendar rightNow = null;

    public static void main(String[] args) {

    }

    public static void sayTime(Date src){
        baseUse(src);
    }

    /**
     *
     * @param dateTime
     */
    public static void baseUse(Date dateTime){

        rightNow = Calendar.getInstance();
        if(dateTime!=null) {
            rightNow.setTime(dateTime);
        }

        int year = rightNow.get(Calendar.YEAR);//1

        int month = rightNow.get(Calendar.MONTH) + 1;//2

        int day = rightNow.get(Calendar.DAY_OF_MONTH);//5
        int date = rightNow.get(Calendar.DATE);//5

        int hour12 = rightNow.get(Calendar.HOUR);//10(12Сʱ��)
        int hour24 = rightNow.get(Calendar.HOUR_OF_DAY); //11(24Сʱ��ʾ)

        int minute = rightNow.get(Calendar.MINUTE);//12

        int second = rightNow.get(Calendar.SECOND);//13

        int milli = rightNow.get(Calendar.MILLISECOND);//14

        int weekday = rightNow.get(Calendar.DAY_OF_WEEK);

        System.out.println("��ʱ�䡿(24Сʱ��):" + year + "��" + month + "��" + day + "��" + hour24
                + "ʱ" + minute + "��" + second + "��" + milli+"����" + getWeekDayName(weekday));
    }

    public static void baseTimeOperate(){

        String now = DateConvertUtils.convertDateToString(new Date(),"yyyy-MM-dd HH:mm:ss:SSS");
        rightNow = Calendar.getInstance();

        Date date = DateConvertUtils.convertStringToDate("2013-6-1 13:24:16","yyyy-M-d H:m:s");
        rightNow.setTime(date);

        now = DateConvertUtils.convertDateToString(rightNow.getTime(),"yyyy-MM-dd HH:mm:ss:SSS");

        rightNow = Calendar.getInstance();
        rightNow.set(2013, 1, 2, 17, 35, 44);
        now = DateConvertUtils.convertDateToString(rightNow.getTime(),"yyyy-MM-dd HH:mm:ss:SSS");

        rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());

        int DAY_OF_YEAR = rightNow.get(Calendar.DAY_OF_YEAR);//6
        System.out.println("DAY_OF_YEAR is = " + DAY_OF_YEAR);

        int DAY_OF_MONTH = rightNow.get(Calendar.DAY_OF_MONTH);//5
        System.out.println("DAY_OF_MONTH = " + String.valueOf(DAY_OF_MONTH));

        int HOUR = rightNow.get(Calendar.HOUR_OF_DAY);
        System.out.println("HOUR_OF_DAY = " + HOUR);

        rightNow.add(Calendar.HOUR_OF_DAY, 3);
        int HOUR_OF_DAY = rightNow.get(Calendar.HOUR_OF_DAY);
        System.out.println("HOUR_OF_DAY + 3 = " + HOUR_OF_DAY);

        int MINUTE = rightNow.get(Calendar.MINUTE);
        System.out.println("MINUTE = " + MINUTE);

        rightNow.add(Calendar.MINUTE, 15);
        MINUTE = rightNow.get(Calendar.MINUTE);
        System.out.println("MINUTE + 15 = " + MINUTE);

        rightNow.add(Calendar.MINUTE, -30);
        MINUTE = rightNow.get(Calendar.MINUTE);
        System.out.println("MINUTE - 30 = " + MINUTE);

        rightNow.setTime(new Date());

        Calendar calendarNew = Calendar.getInstance();

        calendarNew.add(Calendar.HOUR, -5);
        System.out.println("ʱ��Ƚϣ�" + calendarNew.compareTo(rightNow));

        calendarNew.add(Calendar.HOUR, +7);
        System.out.println("ʱ��Ƚϣ�" + calendarNew.compareTo(rightNow));

        calendarNew.add(Calendar.HOUR, -2);
        System.out.println("ʱ��Ƚϣ�" + calendarNew.compareTo(rightNow));
    }


    public static int getDateInt(Date src) {
        return Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(src));
    }


    public static void oneYearAfterTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR,1);
        baseUse(calendar.getTime());
    }

    public static int getTimeDiffer(Date dateBegin,Date dateEnd){
        Calendar calendarBegin = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();
        calendarBegin.setTime(dateBegin);
        calendarEnd.setTime(dateEnd);
        if(calendarBegin.after(calendarEnd)){
            return -1;
        }

        long diff = calendarEnd.getTimeInMillis() - calendarBegin.getTimeInMillis();

        Long day = diff / (1000 * 60 * 60 * 24);
        return day.intValue();
    }


    public static Date setFirstDayOfMonth(Date src) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(src);
        cld.set(Calendar.DAY_OF_MONTH, 1);
        cld.set(Calendar.HOUR_OF_DAY, 0);
        cld.set(Calendar.MINUTE, 0);
        cld.set(Calendar.SECOND, 0);
        cld.set(Calendar.MILLISECOND, 0);
        return cld.getTime();
    }


    public static Date clearDateTime(Date src) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(src);
        cld.set(Calendar.HOUR_OF_DAY, 0);
        cld.set(Calendar.MINUTE, 0);
        cld.set(Calendar.SECOND, 0);
        cld.set(Calendar.MILLISECOND, 0);
        return cld.getTime();
    }


    public static Date fullDateTime(Date src) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(src);
        cld.set(Calendar.HOUR_OF_DAY, 24);
        cld.set(Calendar.MINUTE, 59);
        cld.set(Calendar.SECOND, 59);
        cld.set(Calendar.MILLISECOND, 999);
        return cld.getTime();
    }

    public static Date addHour(Date src, int i) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(src);
        cld.add(Calendar.HOUR_OF_DAY, i);
        return cld.getTime();
    }

    public static Date addMinute(Date src, int i) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(src);
        cld.add(Calendar.MINUTE, i);
        return cld.getTime();
    }

    public static Date addSeconds(Date src, int i) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(src);
        cld.add(Calendar.SECOND, i);
        return cld.getTime();
    }

    public static Date addDay(Date src, int i) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(src);
        cld.add(Calendar.DATE, i);
        return cld.getTime();
    }

    public static Date addWeek(Date src, int i) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(src);
        cld.add(Calendar.WEEK_OF_YEAR, i);
        return cld.getTime();
    }

    public static Date addMonth(Date src, int i) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(src);
        cld.add(Calendar.MONTH, i);
        return cld.getTime();
    }

    public static Date addYear(Date src, int i) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(src);
        cld.add(Calendar.YEAR, i);
        return cld.getTime();
    }


    public static String getWeekDayName(int weekday){
        if(weekday==Calendar.SUNDAY) return "����";
        if(weekday==Calendar.MONDAY) return "��һ";
        if(weekday==Calendar.TUESDAY) return "�ܶ�";
        if(weekday==Calendar.WEDNESDAY) return "����";
        if(weekday==Calendar.THURSDAY) return "����";
        if(weekday==Calendar.FRIDAY) return "����";
        if(weekday==Calendar.SATURDAY) return "����";
        return "-";
    }


    public static String getWeek(Date src){
        String[] weekDays = {"������", "����һ", "���ڶ�", "������", "������", "������", "������"};
        Calendar cld = Calendar.getInstance();
        cld.setTime(src);
        int w = cld.get(Calendar.DAY_OF_WEEK)-1;
        if(w<0){
            w = 0;
        }
        return weekDays[w];
    }

}
