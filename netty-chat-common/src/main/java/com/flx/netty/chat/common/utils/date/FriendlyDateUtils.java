package com.flx.netty.chat.common.utils.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Fenglixiong on 2017/9/27.
 */
public class FriendlyDateUtils {

    public static final int DATEITEM_ALL = 0;
    public static final int DATEITEM_WEEK = 1;
    public static final int DATEITEM_MONTH = 2;
    public static final int DATEITEM_QUARTER = 3;
    public static final int DATEITEM_HALFYERR = 4;
    public static final int DATEITEM_YEAR = 5;
    public static final int DATEITEM_THISYEAR = 6;
    public static final Map<Integer, String> DATEITEM_MAP = new HashMap<Integer, String>(){{
        put(DATEITEM_ALL, "所有");
        put(DATEITEM_WEEK, "一周");
        put(DATEITEM_MONTH, "一个月");
        put(DATEITEM_QUARTER, "一个季度");
        put(DATEITEM_HALFYERR, "半年");
        put(DATEITEM_YEAR, "一年");
        put(DATEITEM_THISYEAR, "三年");
    }};

    /**
     * 根据时间item返回时间
     * 全部
     * 近一周
     * 近一月
     * 近一季
     * 近半年
     * 近一年
     * 今年以来
     * @param dateitem
     * @return
     */
    public static Date geneDateByDateitem(int dateitem){
        if (dateitem != FriendlyDateUtils.DATEITEM_ALL) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());//设置当前时间
            if (dateitem != FriendlyDateUtils.DATEITEM_THISYEAR) {//!=6
                if (dateitem == FriendlyDateUtils.DATEITEM_WEEK) {//==1
                    calendar.add(Calendar.DATE, -7);
                } else if (dateitem == FriendlyDateUtils.DATEITEM_MONTH) {//==2
                    calendar.add(Calendar.MONTH, -1);
                } else if (dateitem == FriendlyDateUtils.DATEITEM_QUARTER) {//==3
                    calendar.add(Calendar.MONTH, -3);
                } else if (dateitem == FriendlyDateUtils.DATEITEM_HALFYERR) {//==4
                    calendar.add(Calendar.MONTH, -6);
                } else if (dateitem == FriendlyDateUtils.DATEITEM_YEAR) {//==5
                    calendar.add(Calendar.YEAR, -1);
                }
                calendar.add(Calendar.HOUR_OF_DAY, 23);
                calendar.add(Calendar.MINUTE, 59);
                calendar.add(Calendar.SECOND, 59);
            } else {//==6
                int currentYear = calendar.get(Calendar.YEAR);
                calendar.clear();
                calendar.set(Calendar.YEAR, currentYear);
            }
            return calendar.getTime();
        }
        return null;
    }

    public static Map<String, String> getDateOptions() {
        Map<String, String> dates = new LinkedHashMap<String, String>();
        dates.put("all", "全部");
        dates.put("new", "最新");//今天和昨天
        dates.put("today", "今天");
        dates.put("yesterday", "昨天");
        dates.put("dod2", "前天");
        dates.put("week", "本周");
        dates.put("d1", "近1天");
        dates.put("w1", "近1周");
        dates.put("m1", "近1月");
        dates.put("m3", "近3月");
        dates.put("m5", "近5月");
        dates.put("y1", "近1年");
        dates.put("ytd", "YTD");
        return dates;
    }

    /**
     * 根据日期格式获得开始结束日期
     * @param datePattern
     * @return
     */
    public static Date[] getDateByPattern(String datePattern) {
        Calendar cld = Calendar.getInstance();
        Date start = null;
        Date end = null;
        if (datePattern.equalsIgnoreCase("all")) {

        } else if (datePattern.equalsIgnoreCase("ytd")) {
            cld.set(Calendar.MONTH, 0);
            cld.set(Calendar.DATE, 1);
            start = cld.getTime();
        } else if (datePattern.equalsIgnoreCase("new")) {//一天之内最新的
            //因为不同国家，第一天是星期几不一样，所以要想判断是不是星期一
            if (cld.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                cld.add(Calendar.DATE, -3);//这里直接跳过周末
            } else {
                cld.add(Calendar.DATE, -1);
            }
            clearTime(cld);
            start = cld.getTime();
        } else if (datePattern.equalsIgnoreCase("today")) {//今天内
            clearTime(cld);
            start = cld.getTime();
        } else if (datePattern.equalsIgnoreCase("yesterday")) {//昨天到现在
            cld.add(Calendar.DATE, -1);
            clearTime(cld);
            start = cld.getTime();
            end = start;
        } else if (datePattern.equalsIgnoreCase("week")) {//本周内
            int wd = cld.get(Calendar.DAY_OF_WEEK);
            //如果今天不是星期一
            if (wd != Calendar.MONDAY) {
                while (true) {
                    cld.add(Calendar.DATE, -1);
                    if (cld.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                        break;
                    }
                }
                clearTime(cld);
                start = cld.getTime();
            } else {//今天星期一
                clearTime(cld);
                start = cld.getTime();
            }
        } else if (datePattern.startsWith("dod")) {
            int v = Integer.parseInt(datePattern.substring(3));
            cld.add(Calendar.DATE, -v);
            clearTime(cld);
            start = cld.getTime();
            end = start;
        } else {
            String k = datePattern.substring(0, 1);
            int v = Integer.parseInt(datePattern.substring(1));
            if (k.equalsIgnoreCase("y")) {
                cld.add(Calendar.YEAR, -v);
            } else if (k.equalsIgnoreCase("m")) {
                cld.add(Calendar.MONTH, -v);
            } else if (k.equalsIgnoreCase("w")) {
                cld.add(Calendar.WEEK_OF_YEAR, -v);
            } else {
                cld.add(Calendar.DATE, -v);
            }
            start = cld.getTime();
        }
        return new Date[]{start, end};
    }

    /**
     * 显示刚刚这种带时间的日期
     * @param dt
     * @return
     */
    public static String showFriendlyDate(Date dt) {
        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        DateFormat dft = new SimpleDateFormat("HH:mm");
        Calendar cld = Calendar.getInstance();
        cld.add(Calendar.DATE, -3);
        if (dt.after(cld.getTime())) {

            Calendar now = Calendar.getInstance();
            int dy_now = now.get(Calendar.DAY_OF_YEAR);

            Calendar then = Calendar.getInstance();
            then.setTime(dt);
            int dy_then = then.get(Calendar.DAY_OF_YEAR);

            StringBuffer sb = new StringBuffer();
            String HHmm = dft.format(dt);
            HHmm = HHmm.equals("00:00") ? "" : HHmm;
            if (dy_now == dy_then) {
                long diff = new Date().getTime() - dt.getTime();
                long diffMinutes = diff / (60 * 1000);
                long diffHours = diffMinutes / 60;
                if (diffHours > 8) {
                    sb.append("今天 ").append(HHmm);
                } else {
                    if (diffHours < 1) {
                        if (diffMinutes < 1) {
                            sb.append("刚刚");
                        } else {
                            sb.append(diffMinutes).append("分钟前");
                        }
                    }
                    else if(diffHours<4){
                        long remainMin = diffMinutes - diffHours * 60;
                        if(remainMin>45) {
                            sb.append(diffHours).append("小时45分钟前");
                        }else if(remainMin>30) {
                            sb.append(diffHours).append("小时30分钟前");
                        }else if(remainMin>15) {
                            sb.append(diffHours).append("小时15分钟前");
                        }else{
                            sb.append(diffHours).append("小时前");
                        }
                    }
                    else{
                        sb.append(diffHours).append("小时前");
                    }
                }
            } else if ((dy_now - dy_then) == 1) {
                sb.append("昨天 ").append(HHmm);
            } else if ((dy_now - dy_then) == 2) {
                sb.append("前天 ").append(HHmm);
            } else {
                sb.append(df.format(dt));
            }
            return sb.toString();
        }
        return df.format(dt);
    }

    /**
     * 聊天格式化信息
     * 刚刚，当天
     * 昨天，前天
     * 周一，周二
     * 2017.12.15
     * @param t
     * @return
     */
    public static String showChatFormatTime(Date t){
        if(t == null)
            return null;
        String formatTime = "";
        //获取第二天日期
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Long curTime = calendar.getTimeInMillis();
        Long timeLong = t.getTime();
        int days = (int) ((curTime - timeLong) / (24 * 60 * 60 * 1000));
        if (days == 0) {//当天
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            formatTime = sdf.format(timeLong);
        } else if (days == 1) {//昨天
            formatTime = "昨天";
        } else if (days > 6) {//一周之后>7天
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            formatTime = sdf.format(timeLong);
        } else if (days > 1) {//2-7天
            Calendar c = Calendar.getInstance();
            c.setTime(t);
            switch (c.get(Calendar.DAY_OF_WEEK)) {
                case 1:
                    formatTime = "周日";
                    break;
                case 2:
                    formatTime = "周一";
                    break;
                case 3:
                    formatTime = "周二";
                    break;
                case 4:
                    formatTime = "周三";
                    break;
                case 5:
                    formatTime = "周四";
                    break;
                case 6:
                    formatTime = "周五";
                    break;
                case 7:
                    formatTime = "周六";
                    break;
            }
        }

        return formatTime;
    }
    
    /**
     * 两个时间的差距
     *
     * @param start
     * @param end
     * @return
     */
    public static String getFriendlyDiffTime(Date start, Date end) {
        if (start != null && end != null) {
            long diff = end.getTime() - start.getTime();
            if (diff != 0) {
                String s = diff > 0 ? "后" : "前";
                diff = Math.abs(diff);
                long diffMinutes = diff / (60 * 1000);
                if (diffMinutes > 60) {
                    long diffHours = diffMinutes / 60;
                    if (diffHours > 24) {
                        long diffDays = diffHours / 24;
                        if (diffDays > 180) {
                            long diffMonths = diffDays / 30;
                            return diffMonths + "月" + s;
                        } else {
                            return diffDays + "天" + s;
                        }
                    } else {
                        return diffHours + "小时" + s;
                    }
                } else {
                    return diffMinutes + "分钟" + s;
                }
            } else {
                return "";
            }
        } else {
            return "";
        }
    }


    public static long getDiffMinutes(Date later, Date earlier) {
        long diff = later.getTime() - earlier.getTime();
        return diff / (60 * 1000);
    }

    public static long getDiffHours(Date later, Date earlier) {
        long diff = later.getTime() - earlier.getTime();
        long diffMinutes = diff / (60 * 1000);
        return diffMinutes / 60;
    }

    public static long getDiffDays(Date later, Date earlier) {
        long diff = later.getTime() - earlier.getTime();
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diffMinutes / 60;
        return diffHours / 24;
    }

    private static void clearTime(Calendar cld) {
        cld.set(Calendar.HOUR_OF_DAY, 0);
        cld.set(Calendar.MINUTE, 0);
        cld.set(Calendar.SECOND, 0);
        cld.set(Calendar.MILLISECOND, 0);
    }

    public static void main(String[] args) {
        Date date = geneDateByDateitem(FriendlyDateUtils.DATEITEM_THISYEAR);
        CalendarUtils.baseUse(date);
    }

}
