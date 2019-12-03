package cn.com.shopec.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类，继承自apache的StringUtils，另外加上其他需要的方法
 */

public final class ECStringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 把URL中的参数转码
     * 
     * @param param
     *            URL中的参数。
     * @param encode
     *            URL编码。
     * @return 编码后的参数，如果失败则返回null。
     */
    public static String urlDecode(String param, String encode) {
        try {
            param = URLDecoder.decode(param, encode);
        } catch (UnsupportedEncodingException e) {
            param = null;
            e.printStackTrace();
        }
        return param;
    }

    /**
     * 两个字符串比较
     * 
     * @param str1
     * @param str2
     * @return 正数 str1>str2 负数str1<str2 相等str1==str2
     */
    public static int compare(String str1, String str2) {
        if (isEmpty(str1) && isEmpty(str2)) {
            return 0;
        } else if (isEmpty(str1)) {
            return -1;
        } else if (isEmpty(str2)) {
            return 1;
        }
        int len1 = str1.length();
        int len2 = str2.length();
        int n = Math.min(len1, len2);
        char v1[] = str1.toCharArray();
        char v2[] = str2.toCharArray();
        int k = 0;
        while (k < n) {
            char c1 = v1[k];
            char c2 = v2[k];
            if (c1 != c2) {
                return c1 - c2;
            }
            k++;
        }
        return len1 - len2;
    }

    /**
     * 将字符串中的多个连续出现的空白字符删除，只保留一个。如"a   b			c d"会被处理为"a b c d"
     * 
     * @param s
     * @return
     * @throws
     */
    public static String removeRepeatedWhitespaces(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.replaceAll("\\s+", " ");
    }

    /**
     * 移除targetStrBuf中字符串右侧subStr字符串，且把右侧空格移除
     * 
     * @param targetStrBuf
     * @param subStr
     * @throws
     */
    public static void rTrim(StringBuffer targetStrBuf, String subStr) {
        while (targetStrBuf.lastIndexOf(" ") > -1 && targetStrBuf.lastIndexOf(" ") == targetStrBuf.length() - 1
                || targetStrBuf.lastIndexOf("	") > -1 && targetStrBuf.lastIndexOf("	") == targetStrBuf.length() - 1) {

            targetStrBuf.delete(targetStrBuf.length() - 1, targetStrBuf.length());
        }

        while (targetStrBuf.lastIndexOf(subStr) > 0
                && targetStrBuf.lastIndexOf(subStr) == targetStrBuf.length() - subStr.length()) {
            targetStrBuf.delete(targetStrBuf.lastIndexOf(subStr), targetStrBuf.length());
        }
    }

    /**
     * 将一个字符串数组，用特定的连接符拼接起来
     * 
     * @param arr
     *            待拼接的字符串数组
     * @param spliter
     *            连接符
     * @param ignoreBlankStringInArr
     *            是否忽略掉数组中的空字符串（即如果某个元素是个空字符串，则不把它拼接到结果字符串中。空字符串是指：null,"",
     *            或者全部字符均为空白字符的字符串。）
     * @return
     */
    public static String concatStringArray(String[] arr, String spliter, boolean ignoreBlankStringInArr) {
        if (arr != null && spliter != null) {
            StringBuilder sb = new StringBuilder("");
            boolean flag = false;

            for (int i = 0; i < arr.length; i++) {
                if (isNotBlank(arr[i]) || !ignoreBlankStringInArr) {
                    if (flag) {
                        sb.append(spliter);
                    } else {
                        flag = true;
                    }
                    sb.append(arr[i]);
                }
            }

            return sb.toString();
        } else {
            throw new IllegalArgumentException("Invalid argument.");
        }
    }

    /**
     * 判断字符串是否存在
     * 
     * @param str
     * @return
     */
    public static boolean isExist(String str) {
        if (str != null && str.trim().length() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 
     * @param s
     * @return
     */
    public static String parseStrNull(String s) {
        return s == null ? "" : s.trim();
    }

    /**
     * 字符串模式匹配，使用方法类似于indexOf
     * 
     * @param text
     * @param pattern
     * @return
     */
    public static int patternMatch(String text, String pattern) {
        if (text != null && pattern != null) {
            KMP kmp = new KMP(pattern);
            int pos = kmp.match(text);
            if (pos == text.length()) {
                pos = -1;
            }
            return pos;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * KMP模式匹配算法实现
     * 
     */
    private static class KMP {
        private String pattern;
        private int[] next;

        // create Knuth-Morris-Pratt NFA from pattern
        public KMP(String pattern) {
            this.pattern = pattern;
            int M = pattern.length();
            this.next = new int[M];
            int j = -1;
            for (int i = 0; i < M; i++) {
                if (i == 0) {
                    this.next[i] = -1;
                } else if (pattern.charAt(i) != pattern.charAt(j)) {
                    this.next[i] = j;
                } else {
                    this.next[i] = this.next[j];
                }
                while (j >= 0 && pattern.charAt(i) != pattern.charAt(j)) {
                    j = this.next[j];
                }
                j++;
            }

//	        for (int i = 0; i < M; i++)
//	        	System.out.println("next[" + i + "] = " + next[i]);
        }

        // 返回pattern在text中第一次出现的位置，否则返回的值等于text的长度
        // simulate the NFA to find match
        public int match(String text) {
            int M = this.pattern.length();
            int N = text.length();
            int i, j;
            for (i = 0, j = 0; i < N && j < M; i++) {
                while (j >= 0 && text.charAt(i) != this.pattern.charAt(j)) {
                    j = this.next[j];
                }
                j++;
            }
            if (j == M) {
                return i - M;
            }
            return N;
        }
    }

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    public static boolean isChinese(String str) {
        if (null == str) {
            return false;
        }

        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c) == false) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 手机号验证
     * 
     * @param  str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) { 
        Pattern p = null;
        Matcher m = null;
        boolean b = false; 
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
    
    /**
     * 电话号码验证
     * 
     * @param  str
     * @return 验证通过返回true
     */
    public static boolean isPhone(String str) { 
        Pattern p1 = null,p2 = null;
        Matcher m = null;
        boolean b = false;  
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if(str.length() >9)
        {   m = p1.matcher(str);
            b = m.matches();  
        }else{
            m = p2.matcher(str);
            b = m.matches(); 
        }  
        return b;
    }
    /**
	 * 将对象转换成字符串类型,如果是null,则为""
	 * 
	 * @param obj
	 * @return
	 */
	public static String toStringForNull(Object obj) {
		if (obj == null)
			return "";
		else
			return obj.toString();
	}
	
	public static String stringForNull(Object obj) {
		if (obj == null)
			return "0";
		else
			return obj.toString();
	}
}
