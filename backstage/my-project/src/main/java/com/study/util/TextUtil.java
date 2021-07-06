package com.study.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

/**
 * @文件名称: TextUtil.java
 * @功能描述: TODO(字符控制工具)
 * @开发日期： 2021/5/10
 * @历史版本： V1.0
 */
@Slf4j
public class TextUtil {

    /**
     * 截取字符串str中指定字符 strStart、strEnd之间的字符串
     *
     * @return
     */
    public static String subString(String str, String strStart, String strEnd) {

        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);

        /* index 为负数 即表示该字符串中 没有该字符 */
        if (strStartIndex < 0)
            return null;
        if (strEndIndex < 0)
            return null;

        /* 开始截取 */
        return str.substring(strStartIndex, strEndIndex).substring(strStart.length());
    }

    /**
     * 截取字符串str中指定字符 strStart之后的字符串
     *
     * @return
     */
    public static String subString(String str, String strStart) {

        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);

        /* index 为负数 即表示该字符串中 没有该字符 */
        if (strStartIndex < 0)
            return null;

        if (strStartIndex + 1 == str.length()) {
            return null;
        }

        /* 开始截取 */
        return str.substring(strStartIndex + 1);
    }

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

    /**
     * 获取到classes目录
     *
     * @return path
     */
    public static String getClassPath() {
        String systemName = System.getProperty("os.name");
        //判断当前环境，如果是Windows 要截取路径的第一个 '/'
        if (!StringUtils.isBlank(systemName) && systemName.contains("Windows")) {
            return TextUtil.class.getResource("/").getFile().toString().substring(1);
        } else {
            return TextUtil.class.getResource("/").getFile().toString();
        }
    }

    /**
     * 是否数字类型的字符串
     *
     * @param str
     * @return
     */
    public static boolean checkStrOrNumber(String str) {
        boolean f = false;
        if (str.contains("年") && str.contains("日")) {
            if (!str.contains("月")) {
                return false;
            }
        }
        if (str.contains("年")) {
            str = str.replace("年", "");
        }
        if (str.contains("月")) {
            str = str.replace("月", "");
        }
        if (str.contains("日")) {
            str = str.replace("日", "");
        }
        if (str.contains("...")) {
            str = str.replace("...", "");
        }
        if (str.contains("..")) {
            str = str.replace("..", "");
        }
        if (str.contains(".")) {
            str = str.replace(".", "");
        }
        if (str.contains("--")) {
            str = str.replace("--", "");
        }
        if (str.contains("/")) {
            str = str.replace("/", "");
        }
        Pattern pattern = Pattern.compile("[1-9]+[0-9]*$");
        Matcher m = pattern.matcher(str);
        if (m.find()) {
            f = true;
        }

        return f;
    }

    /**
     * 护照验证
     * 规则： G + 8位数字, P + 7位数字, S/D + 7或8位数字,等
     * 例： G12345678, P1234567
     */
    public static Boolean passportCard(String text) {
        String reg = "/^1[45][0-9]{7}$|(^[P|p|S|s]\\d{7}$)|(^[S|s|G|g|E|e]\\d{8}$)|(^[Gg|Tt|Ss|Ll|Qq|Dd|Aa|Ff]\\d{8}$)|(^[H|h|M|m]\\d{8,10}$)/";
        if (!text.matches(reg)) {
            //护照号码不合格
            return false;
        } else {
            //校验通过
            return true;
        }
    }
}

