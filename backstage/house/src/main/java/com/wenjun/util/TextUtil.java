package com.wenjun.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


/**
 * @author wen jun tang
 * @date 2021/7/14 11:45
 */
@Slf4j
@SuppressWarnings("all")
public class TextUtil {

    /**
     * 获取具有一定含义的UUID
     *
     * @return
     */
    public static String getUUID() {
        // 时间15位 + 9位随机数 = 24位
        String uuid = new SimpleDateFormat("yyMMddhhmmssSSS").format(new Date());
        uuid += String.format("%09d", new Random().nextInt(1000000000));
        return uuid;
    }

    public static String getVersion() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    /**
     * @param splitNum
     * @param list
     * @return 返回一个等比例的数组[[]]
     * @remark 按参数长度分割集合，并删除一个长度的（[splitNum]为一个长度）空值
     */
    public static <T> List<List<T>> getSplitListRemoveNullStr(int splitNum, List<T> list) {
        List<List<T>> splitList = new LinkedList<>();
        int groupFlag = list.size() % splitNum == 0 ? (list.size() / splitNum) : (list.size() / splitNum + 1);
        for (int j = 1; j <= groupFlag; j++) {
            if ((j * splitNum) <= list.size()) {
                splitList.add(list.subList(j * splitNum - splitNum, j * splitNum));
            } else if ((j * splitNum) > list.size()) {
                splitList.add(list.subList(j * splitNum - splitNum, list.size()));
            }
        }

        // 删除空元素
        for (int i = 1; i < splitList.size(); i++) {
            int count = 0;
            for (int j = 0; j < splitList.get(i).size(); j++) {
                if (splitList.get(i).get(j).equals("")) {
                    count++;
                    if (count == splitList.get(i).size()) {
                        splitList.remove(splitList.get(i));
                        i--;
                        j++;
                    }
                }
            }
        }

        return splitList;
    }

    /**
     * @param splitNum
     * @param list
     * @return 返回一个等比例的数组[[]]
     * @remark 忽略空值
     */
    public static <T> List<List<T>> getSplitList(int splitNum, List<T> list) {
        List<List<T>> splitList = new LinkedList<>();
        int groupFlag = list.size() % splitNum == 0 ? (list.size() / splitNum) : (list.size() / splitNum + 1);
        for (int j = 1; j <= groupFlag; j++) {
            if ((j * splitNum) <= list.size()) {
                splitList.add(list.subList(j * splitNum - splitNum, j * splitNum));
            } else if ((j * splitNum) > list.size()) {
                splitList.add(list.subList(j * splitNum - splitNum, list.size()));
            }
        }

        return splitList;
    }

    /**
     * @param datetime
     * @return Date
     * @remark 字符串类型转时间类型
     */
    public static Date StringToDate(String datetime) throws ParseException {
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        date = sdFormat.parse(datetime);
        return date;
    }

    /**
     * 获取当前时间
     *
     * @return
     * @throws ParseException
     */
    public static Date obtainNowTime() throws ParseException {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        String strTime = formatter.format(date);

        return formatter.parse(strTime);
    }

}

