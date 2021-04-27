package com.javarush.task.task27.task2712.statistic;

import java.util.Calendar;
import java.util.Date;

public class Test {
    public static void main(String[] args){
        Date date = new Date();
        System.out.println(trim(date));
        Date date1 = new Date();
        System.out.println(trim(date1));
        System.out.println("equals ----------------");
    }

    public static Date trim(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.clear(); // as per BalusC comment.
        cal.setTime( date );
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}
