package com.sseance.cwa.checkworkattendance.service.impl;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

public class test {

    public static void main(String[] args) {
        String str = "事假10-09 09:00到10-10 18:30 16小时事假10-09 09:00到10-10 18:30 16小时事假10-03 09:00到10-9 18:30 4小时调休10-5 09:00到10-10 18:30 8小时";
        Set<String> set = new HashSet<>();
        for (String record : str.split("小时")) {
            if (record.contains("事假")){
                set.add(record);
            }
        }
        set.forEach(System.out::println);
        Integer a = 2;
        String s = "0.75";
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println(df.format((float)a-Float.parseFloat(s)));
        Integer sss = null;
        System.out.println(String.valueOf(sss));
    }


}
