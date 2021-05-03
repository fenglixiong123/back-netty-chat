package com.flx.netty.chat.common.utils.date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间转换
 */
public class DateConvertUtils
{
    private static Logger logger = LoggerFactory.getLogger(DateConvertUtils.class);

    private final static String PATTEN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * convert dateString to date
     * @param strDate String
     * @param fmt String
     * @return Date
     */
    public static Date convertStringToDate( String strDate , String fmt )
    {
        if(StringUtils.isBlank(fmt)){
            fmt = PATTEN_YYYY_MM_DD_HH_MM_SS;
        }
        if(StringUtils.isNotBlank(strDate)) {
            try {
                SimpleDateFormat sf = new SimpleDateFormat(fmt);
                return sf.parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null ;
    }
    /**
     convert date to dateString
     @param date Date
     @param fmt String
     @return String
     */
    public static String convertDateToString( Date date , String fmt )
    {
        if(StringUtils.isBlank(fmt)){
            fmt = PATTEN_YYYY_MM_DD_HH_MM_SS;
        }
        if(date!=null) {
            SimpleDateFormat sf = new SimpleDateFormat(fmt);
            return sf.format(date);
        }
        return null;
    }

    public static Date getDayBeforeCurrentDate( Date currentDate , int nDay )
    {
        Calendar cld = Calendar.getInstance() ;
        cld.setTime( currentDate );
        cld.add( Calendar.DATE , nDay );
        return cld.getTime();
    }

    public static Date getMonthBeforeCurrentDate( Date currentDate , int nMonth )
    {
        Calendar cld = Calendar.getInstance() ;
        cld.setTime( currentDate );
        cld.add( Calendar.MONTH , nMonth );
        return cld.getTime();
    }

    public static String getMonthBeginDayString( String strDate , String fmt){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( convertStringToDate(strDate,fmt));

        calendar.set( Calendar.DAY_OF_MONTH, 1 );
        return  convertDateToString( calendar.getTime() ,fmt ) ;

    }

    public static String getMonthEndDayString( String strDate , String fmt){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( convertStringToDate(strDate,fmt));
        calendar.add( Calendar.MONTH, 1 );
        calendar.set( Calendar.DAY_OF_MONTH, 0 );
        return convertDateToString( calendar.getTime() ,fmt )  ;
    }

    public static Integer getSameMonth(String strDate, String fmt ,Integer num){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( convertStringToDate( strDate, fmt ) );
        calendar.set( Calendar.MONTH, calendar.get(Calendar.MONTH)-num );
        calendar.set( Calendar.DAY_OF_MONTH, 1 );
        calendar.set( Calendar.HOUR_OF_DAY, 0 );
        calendar.set( Calendar.MINUTE, 0 );
        calendar.set( Calendar.SECOND, 0 );
        String month = convertDateToString( calendar.getTime() ,"yyyyMM" );
        return Integer.parseInt(month);
    }

    public static Date getHourBeforeCurrentDate( Date currentDate , int nHour )
    {
        Calendar cld = Calendar.getInstance() ;
        cld.setTime( currentDate );
        cld.add( Calendar.HOUR_OF_DAY , nHour );
        return cld.getTime();
    }

    public static Date getMinutesBeforeCurrentDate( Date currentDate , int Minutes )
    {
        Calendar cld = Calendar.getInstance() ;
        cld.setTime( currentDate );
        cld.add( Calendar.MINUTE , Minutes );
        return cld.getTime();
    }

    public static Date getFirstDayOfWeek(String strDate,String fmt ){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( convertStringToDate( strDate, fmt ) );
        calendar.setFirstDayOfWeek( Calendar.MONDAY );
        calendar.set( Calendar.HOUR, 0 );
        calendar.set( Calendar.MINUTE, 0 );
        calendar.set( Calendar.SECOND, 0 );
        calendar.set( Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() ); // Monday
        return calendar.getTime();
    }

    public static String getChineseFormatDate( Date date )
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( date );
        int month = calendar.get( Calendar.MONTH ) + 1;
        int day = calendar.get( Calendar.DAY_OF_MONTH );
        String stringDate = month + "��" + day + "��";
        return stringDate;
    }

    public static String convertStringToString (String date, String fmt){
        try
        {
            String[] data1 = date.split("-");
            String[] data2 = date.split(" ");
            String[] data3 = date.split(":");
            String fmt1 = "yyyy-MM-dd";
            if( data1.length == 3 ){
                if( data2.length == 2 ) {
                    if(  data3.length == 3  ){
                        fmt1 = "yyyy-MM-dd HH:mm:ss";
                    }else if(  data3.length == 2  ){
                        fmt1 = "yyyy-MM-dd HH:mm";
                    }
                }
            }else if( data1.length == 2 ){
                fmt1 = "yyyy-MM";
            }else if( data1.length == 1 && date.length() == 8){
                date = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
                fmt1 = "yyyy-MM-dd";
            }

            SimpleDateFormat sf1 = new SimpleDateFormat( fmt1 );
            Date date1 = sf1.parse(date);

            SimpleDateFormat sf = new SimpleDateFormat( fmt );
            return  sf.format( date1 ) ;
        }
        catch ( ParseException e )
        {
            logger.info( "" , e );
        }
        return null;
    }

    public static String getFirstDayOfYear (String date, String fmt){
        if(date.length() > 4) date = convertStringToString(date, "yyyy");
        String firstDay = date + "-01-01 00:00:00";
        firstDay = convertStringToString(firstDay, fmt);
        return firstDay;
    }

    public static String getLastDayOfYear (String date, String fmt){
        if(date.length() > 4) date = convertStringToString(date, "yyyy");
        String lastDay = date + "-12-31 23:59:59";
        lastDay = convertStringToString(lastDay, fmt);
        return lastDay;
    }
}
