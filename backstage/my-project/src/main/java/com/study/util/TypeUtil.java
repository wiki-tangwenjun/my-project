package com.study.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @文件名称: TypeUtil
 * @功能描述: 字符串转各种格式
 * @版权信息： www.dondown.com
 * @编写作者： chendingfengmail@163.com
 * @开发日期： 2020/12/16
 * @历史版本： V1.0
 */
public class TypeUtil {

    // 字符串转日期(格式:"yyyyMMdd")
    public static Date StrToDateFirst(String str) {
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    // 字符串转日期(格式:"dd/MM/yyyy")
    public static Date StrToDateSecond(String str) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    // 字符串转日期(格式:"yyyy-MM-dd")
    public static Date StrToDateThird(String str) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    // 字符串转日期(格式:"yyyy-MM-dd HH:mm:ss")
    public static Date StrToDateFourth(String str) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    // 字符串转Integer
    public static Integer StrToInteger(String str) {
        Integer integer = null;
        try {
            integer = Integer.valueOf(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return integer;
    }

    // 字符串转Double
    public static Double StrToDouble(String str) {
        // 过滤中文字符
        String REGEX_CHINESE = "[\u4e00-\u9fa5]";
        str = str.replaceAll(REGEX_CHINESE, "");

        Double double1 = 0.00;
        try {
            double1 = Double.parseDouble(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return double1;
    }

    // 字符串转时间戳
    public static Timestamp StrToTimeStamp(String str) {
        Timestamp timestamp = null;
        try {
            timestamp = Timestamp.valueOf(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    // 字符串转BigDecimal
    public static BigDecimal StrTiBigdecimal(String str) {
        BigDecimal bigDecimal = null;
        try {
            bigDecimal = new BigDecimal(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bigDecimal;
    }
}
