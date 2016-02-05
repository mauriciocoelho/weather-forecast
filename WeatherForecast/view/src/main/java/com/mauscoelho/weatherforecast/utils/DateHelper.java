package com.mauscoelho.weatherforecast.utils;


import java.util.Calendar;

public final class DateHelper {
    private static final String SUN = "Sun";
    private static final String MON = "Mon";
    private static final String TUE = "Tue";
    private static final String WED = "Wed";
    private static final String THU = "Thu";
    private static final String FRI = "Fri";
    private static final String SAT = "Sat";



    private static long convertToMillis(long timestamp){
        return timestamp * 1000;
    }

    public static Calendar getDateWithoutTime(long timestamp){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(convertToMillis(timestamp));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    public static String getAbbrDay(long timestamp) {
        switch (getDay(timestamp)) {
            case Calendar.SUNDAY:
                return SUN;
            case Calendar.MONDAY:
                return MON;
            case Calendar.TUESDAY:
                return TUE;
            case Calendar.WEDNESDAY:
                return WED;
            case Calendar.THURSDAY:
                return THU;
            case Calendar.FRIDAY:
                return FRI;
            case Calendar.SATURDAY:
                return SAT;
            default:
                return "";
        }
    }

    public static int getDay(long timestamp) {
        Calendar calendar = getDateWithoutTime(timestamp);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return day;
    }
}
