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
 * @编写作者： tang wen jun
 * @开发日期： 2020/12/16
 * @历史版本： V1.0
 */
public class TypeUtil {

    /**
     * 字符串转日期(格式:"yyyyMMdd")
     *
     * @param str
     * @return
     */
    public static Date strToDateFirst(String str) {
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 字符串转日期(格式:"dd/MM/yyyy")
     *
     * @param str
     * @return
     */
    public static Date strToDateSecond(String str) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 字符串转日期(格式:"yyyy-MM-dd")
     *
     * @param str
     * @return
     */
    public static Date strToDateThird(String str) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 字符串转日期(格式:"yyyy-MM-dd HH:mm:ss")
     *
     * @param str
     * @return
     */
    public static Date strToDateFourth(String str) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 字符串转Integer
     *
     * @param str
     * @return
     */
    public static Integer strToInteger(String str) {
        Integer integer = null;
        try {
            integer = Integer.valueOf(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return integer;
    }

    /**
     * 字符串转Double
     *
     * @param str
     * @return
     */
    public static Double strToDouble(String str) {
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

    /**
     * 字符串转时间戳
     *
     * @param str
     * @return
     */
    public static Timestamp strToTimeStamp(String str) {
        Timestamp timestamp = null;
        try {
            timestamp = Timestamp.valueOf(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    /**
     * 符串转BigDecimal
     *
     * @param str
     * @return
     */
    public static BigDecimal strTiBigdecimal(String str) {
        BigDecimal bigDecimal = null;
        try {
            bigDecimal = new BigDecimal(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bigDecimal;
    }
}
