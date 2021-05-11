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
	 * { 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",
	 * 21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",
	 * 33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",
	 * 42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",
	 * 51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",
	 * 63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外" };
	 */
	private static final String[] PROVINCE = { "11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34",
			"35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64",
			"65", "71", "81", "82", "91" };
	// 当前年
	private static final Calendar CAL = Calendar.getInstance();
    private static final Integer YEAR = CAL.get(Calendar.YEAR);

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
        String result = str.substring(strStartIndex, strEndIndex).substring(strStart.length());
        return result;
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
        
        if(strStartIndex + 1 == str.length()) {
        	return null;
        }
            
        /* 开始截取 */
        String result = str.substring(strStartIndex + 1);
        return result;
   }
    
    /**
	 * 获取具有一定含义的UUID
	 * @return
	 */
	public static String getUUID(){
		// 时间15位 + 9位随机数 = 24位
		String uuid = new SimpleDateFormat("yyMMddhhmmssSSS").format(new Date());
		uuid += String.format("%09d", new Random().nextInt(1000000000));
		return uuid;
	}
	
	public static String getVersion(){
		String version = new SimpleDateFormat("yyyyMMdd").format(new Date());
		return version;
	}
	
	/**
	 * @remark 按参数长度分割集合，并删除一个长度的（[splitNum]为一个长度）空值
	 * @param splitNum
	 * @param list
	 * @return 返回一个等比例的数组[[]]
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
	 * @remark 忽略空值
	 * @param splitNum
	 * @param list
	 * @return 返回一个等比例的数组[[]]
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
        SimpleDateFormat sdFormat=new SimpleDateFormat("yyyy-MM-dd");
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
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
		String strTime = formatter.format(date);

		return formatter.parse(strTime);
	}

	/**
	 * 身份证号码校验
	 * @param IDNumber
	 * @return
	 */
	public static boolean isIDNumber(String IDNumber) {
        if (isContainChinese(IDNumber)||CheckUtil.isNull(IDNumber)) {
            return false;
        }

        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        //假设18位身份证号码:41000119910101123X  410001 19910101 123X
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //(18|19|20)                19（现阶段可能取值范围18xx-20xx年）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
        //[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
        //$结尾
 
        //假设15位身份证号码:410001910101123  410001 910101 123
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X
        //$结尾
 
 
        boolean matches = IDNumber.matches(regularExpression);
 
        //判断第18位校验值
        if (matches) {
            if (IDNumber.length() == 18) {
                try {
                    char[] charArray = IDNumber.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                        return false;
                    }
 
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
 
        }
        
        return matches;
    }

	/**
	 * 判断字符串中是否包含中文
	 * @param str
	 * 待校验字符串
	 * @return 是否为中文
	 * @warn 不能校验是否为中文标点符号
	 */
	public static boolean isContainChinese(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}



	/**
	 * 获取到classes目录
	 * @return path
	 */
	public static String getClassPath(){
		String systemName = System.getProperty("os.name");
		//判断当前环境，如果是Windows 要截取路径的第一个 '/'
		if(!StringUtils.isBlank(systemName) && systemName.contains("Windows")){
			return TextUtil.class.getResource("/").getFile().toString().substring(1);
		}else{
			return TextUtil.class.getResource("/").getFile().toString();
		}
	}

	/**
	 * 去除中文字符
	 * @param text
	 * @return
	 */
	public static String removeChinese (String text) {
		String REGEX_CHINESE = "[\u4e00-\u9fa5]";// 中文正则
		Pattern pat = Pattern.compile(REGEX_CHINESE);
		Matcher mat = pat.matcher(text);
		return mat.replaceAll("");
	}
	
	/**
	 * 日期校验
	 * @param dateStr 待分割的日期字符串
	 * @param str 以什么分割
	 * @return
	 */
	// 日期校验
	public static boolean checkDate(String dateStr, String str) {
		boolean f = false;
		if (dateStr.contains("年") && dateStr.contains("日")) {
			if (!dateStr.contains("月")) {
				return  false;
			}
		}
		if (dateStr.contains("年")) {
			 dateStr = dateStr.replace("年", "-");
			 str = "-";
		}
		if (dateStr.contains("/")) {
			dateStr = dateStr.replace("/", "-");
			str = "-";
		}
		if (dateStr.contains("月")) {
			dateStr = dateStr.replace("月", "-");
			str = "-";
		}
		if (dateStr.contains("日")) {
			dateStr = dateStr.replace("日", "");
			str = "-";
		}
		if (dateStr.contains("..")) {
			dateStr = dateStr.replace("..", "-");
			str = "-";
		}
		if (dateStr.contains("...")) {
			dateStr = dateStr.replace("...", "-");
			str = "-";
		}
		if (dateStr.contains(".")) {
			dateStr = dateStr.replace(".", "-");
			str = "-";
		}
		if (dateStr.contains("--")) {
			dateStr = dateStr.replace("--", "-");
			str = "-";
		}
		
		String[] splitStr = dateStr.split(str);
		if (splitStr.length >= 3) {
			boolean isNumber = checkStrOrNumber(splitStr[0]);
			if (!isNumber) {
				return false;
			}
			if (splitStr[0].length() > 4 || splitStr[0].length() < 2 || YEAR < Integer.parseInt(splitStr[0])) {
				 f = false;
			}
			
			boolean isNumber1 = checkStrOrNumber(splitStr[1]);
			if (!isNumber1) {
				return false;
			}
			if (splitStr[1].length() >= 3) {
				f = false;
			}

			boolean isNumber2 = checkStrOrNumber(splitStr[2]);
			if (!isNumber2) {
				return false;
			}
			if (splitStr[2].length() >= 3) {
				f = false;
			}
			
			if (splitStr[0].length() < 5 && splitStr[0].length() > 1 && 
					Integer.parseInt(splitStr[0]) > 0 && YEAR >= Integer.parseInt(splitStr[0]) &&
					splitStr[1].length() < 3 && splitStr[1].length() > 0 && Integer.parseInt(splitStr[1]) > 0 &&
					splitStr[2].length() < 3 && splitStr[2].length() > 0 && Integer.parseInt(splitStr[2]) > 0) {
				f = true;
			} else {
				f = false;
			}
		} else if (splitStr.length == 2) {
			boolean isNumber = checkStrOrNumber(splitStr[0]);
			if (!isNumber) {
				return false;
			}
			if (splitStr[0].length() > 4 || splitStr[0].length() < 2 || YEAR < Integer.parseInt(splitStr[0])) {
				f = false;
			}
			
			boolean isNumber1 = checkStrOrNumber(splitStr[1]);
			if (!isNumber1) {
				return false;
			}
			if (splitStr[1].length() >= 3) {
				f = false;
			}
			
			if (splitStr[0].length() < 5 && splitStr[0].length() > 1 && 
					Integer.parseInt(splitStr[0]) > 0 && YEAR >= Integer.parseInt(splitStr[0]) &&
					splitStr[1].length() < 3 && splitStr[1].length() > 0 && Integer.parseInt(splitStr[1]) > 0) {
				f = true;
			} else {
				f = false;
			}
		} else if (splitStr.length == 1) {
			if (splitStr[0].length() > 4 || splitStr[0].length() < 2 || YEAR < Integer.parseInt(splitStr[0])) {
				f = false;
			} else if (Integer.parseInt(splitStr[0]) > 0 &&  YEAR >= Integer.parseInt(splitStr[0])) {
				f = true;
			} else {
				f= false;
			}
		}
		
		return f;
	}
	
	/**
	 * 是否数字类型的字符串
	 * @param str
	 * @return
	 */
	public static boolean checkStrOrNumber(String str) {
		boolean f = false;
		if (str.contains("年") && str.contains("日")) {
			if (!str.contains("月")) {
				return  false;
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
		if(m.find()){
			f = true;
		}
		
		return f;
	}


	/**
	 * 截取字符串前11位判断是否为电话号码
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])|(18[0-9])|(19[8,9]))\\d{8}$");
		if (CheckUtil.isNull(mobiles)) {
			return true;
		}
		if (mobiles.length() < 11 && mobiles.length() > 0) {
			return false;
		}
		Matcher m = p.matcher(mobiles.substring(0, 11));
		return m.matches();
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
			return  false;
		} else {
			//校验通过
			return true;
		}
	}


	/**
	 * 检查亲属称谓合法性
	 * @return true: 合法
	 * 		   false: 不合法
	 */
	public static boolean checkRelationAppellation (String text) {
		String[] appellationNoLegal = new String[]{"丈夫父母","妻子父母","父母"};
		String[] appellationLegal = new String[]{
				"本人",
				"配偶","妻","夫","丈夫","妻子","老婆","老公","媳妇","前妻","前夫","爱人","夫妻","太太",
				"爷爷","祖父","祖母",
				"外婆",
				"岳父","缶父","岳丈","丈人","家公","公","公公","老爷","妻子父亲","丈父","丈夫父亲","夫父","妻父","家翁","外父","继岳父","前岳父","家父","公父","先父","公爹",
				"岳母","缶母","丈母娘","家婆","婆婆","公婆","奶奶","妻子母亲","丈母","丈夫母亲","夫母","妻母","婆","外母","前岳母","继岳母","家母","婆母","养母","亲母","养婆婆",
				"父亲","慈父","父","继父","爸爸","生父",
				"母亲","慈母","母","继母","妈妈","生母",
				"大舅","叔","婶","细舅","舅父","舅母","大伯","伯父","叔仔","叔父","舅","舅舅","二舅","小舅子","小舅","舅子","舅仔","大姨","大姨子","小姨","姨子","二姨","表弟","堂兄","堂哥","堂弟","表兄","表妹","姑妹","姑仔","大姑","大姑姐","大姑夫","小姑","姑爷","四叔","四婶","小叔","叔妇","叔叔","外甥","侄子",
				"哥哥","内兄","大兄","大哥","兄","兄弟","兄长","长兄","一兄","二兄","三兄","四兄","次兄","夫兄","胞兄","哥","二哥","三哥","四哥","五哥","六哥","表哥","嫂","嫂子","嫂嫂","大嫂","妻兄","连襟","小哥","妯娌",
				"姐姐","内姐","家姐","妻姐","大家姐","二家姐","大姐","长姐","二姐","三姐","四姐","五姐","胞姊","六姐","七姐","姐","姐妹","姊","姊姊","姐夫","大姐夫","二姐夫","姑姐","三姐夫",
				"弟弟","弟","长弟","大弟","小弟","一弟","二弟","三弟","四弟","五弟","六弟","七弟","公媳","婆媳","弟媳","次媳","妻弟","胞弟","姐弟","内弟","弟妇",
				"妹妹","内妹","兄妹","妹","大妹妹","二妹妹","长妹","次妹","大妹","二妹","三妹","四妹","小妹","妹夫","堂妹","妻妹","胞妹","细妹","继妹",
				"儿子","儿","大儿子","二儿子","独生子","大儿","小儿","继子","子","子女","长子","次子","大子","二子","二儿","三子","小儿子","父子","母子","长媳","大儿媳妇","翁媳","儿媳","长儿媳","二儿媳妇","二儿媳","长子媳","大儿媳","儿媳妇","次儿媳",
				"孙子","孙","长孙子","次孙子","次孙女","次孙","长孙男","外孙","孙女","外孙子","外孙女","三孙女","内孙",
				"女儿","独生女","女婿","婿","长女婿","三女婿","大女婿","二女婿","女","长女儿","长女","大女","小女","次女","细女","三女儿","三女","大女儿","二女儿","二女","小女儿","父女","母女","养女","继女","幼女"
		};
		for (String item : appellationNoLegal) {
			if (text.equals(item)) {
				return false;
			}
		}
		for (String item : appellationLegal) {
			if (text.equals(item)) {
				return true;
			}
		}
		return false;
	}


	
	/**
	 * 
	 * @Title: comparsionToEighteen
	 * @Description: TODO(18位比18位，返回错误次数)
	 * @param  cloudIdCard
	 * @param  localIdCard
	 * @return  int
	 * @throws
	 */
	public static int comparsionToEighteen(String cloudIdCard, String localIdCard) {
		if (cloudIdCard.length() < 18) {
			return 18;
		}
		int number = 0;
		cloudIdCard = cloudIdCard.toLowerCase();
		localIdCard = localIdCard.toLowerCase();

		for (int i = 0; i < localIdCard.length(); i++) {
			String local = String.valueOf(localIdCard.charAt(i));
			String cloud = String.valueOf(cloudIdCard.charAt(i));
			if (!local.equals(cloud)) {
				number++;
			}
		}

		return number;
	}

	/**
	 * 
	 * @Title: comparsionToManyOrLess
	 * @Description: TODO(比正常的身份证号码18位少或多)
	 * @param  cloudIdCard
	 * @param  localIdCard
	 * @return  int
	 * @throws
	 */
	public static int comparsionToManyOrLess(String cloudIdCard, String localIdCard) {
		if (cloudIdCard.length() < 17) {
			return 18;
		}
		int number = 0;
		int count = 0;
		cloudIdCard = cloudIdCard.toLowerCase();
		localIdCard = localIdCard.toLowerCase();
		if (localIdCard.length() > 18) {
			for (int index = 0; index < localIdCard.length(); index++) {
				String loaclId = localIdCard.substring(count, index);
				String cloudId = cloudIdCard.substring(count, index);
				if (!loaclId.equals(cloudId)) {
					if (index < localIdCard.length()) {
						count = index;
					}

					number++;
				}
			}
		} else {
			for (int index = 0; index < cloudIdCard.length(); index++) {
				String loaclId = localIdCard.substring(count, index);
				String cloudId = cloudIdCard.substring(count, index);
				if (!loaclId.equals(cloudId)) {
					if (index < localIdCard.length()) {
						count = index;
					}

					number++;
				}
			}
		}

		return number;
	}

}
       
