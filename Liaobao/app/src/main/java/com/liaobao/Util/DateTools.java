package com.liaobao.Util;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public final class DateTools implements Serializable {

    public final static String FORMAT_TIME = "HH:mm";
    public final static String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm";
    public final static String FORMAT_MONTH_DAY_TIME = "MM-dd HH:mm";
    private DateTools() {
    }
    /**
     * <br>传入yyyy-MM-dd HH:mm类似string 返回周几,超过1分钟返回null</br>
     * <br>author:jinpneg</br>
     * <br>Time：2017/6/17 21:57</br>
     */
    public static String weekdate(String EndDate,String TargetDate) {
        if (StringUtil.getIsNull(EndDate)) {
                return null;
        } else {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            DateFormat weekformat = new SimpleDateFormat("EEEE HH:mm");
            try {
                Date endDate=format.parse(EndDate);
                if(StringUtil.getIsNull(TargetDate)){
                    return  weekformat.format(endDate);
                }
                Date startDate=format.parse(TargetDate);
                long timeLong = endDate.getTime()-startDate.getTime();
                if (timeLong < 60 * 1000) {//1分钟内不显示时间
                    return "";
                }
                else {
                return  weekformat.format(endDate);
                }
            }catch (Exception e){
            }
        }
        return TargetDate;
    }
    public static String getChatTime(boolean hasYear,long timesamp) {
        long clearTime = timesamp;
        String result;
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        Date today = new Date(System.currentTimeMillis());
        Date otherDay = new Date(clearTime);
        int temp = Integer.parseInt(sdf.format(today))
                - Integer.parseInt(sdf.format(otherDay));
        switch (temp) {
            case 0:
                result = "今天 " + getHourAndMin(clearTime);
                break;
            case 1:
                result = "昨天 " + getHourAndMin(clearTime);
                break;
            case 2:
                result = "前天 " + getHourAndMin(clearTime);
                break;
            default:
                result = getTime(hasYear,clearTime);
                break;
        }
        return result;
    }
    public static String getHourAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_TIME);
        return format.format(new Date(time));
    }
    public static String getTime(boolean hasYear,long time) {
        String pattern=FORMAT_DATE_TIME;
        if(!hasYear){
            pattern = FORMAT_MONTH_DAY_TIME;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date(time));
    }


}
