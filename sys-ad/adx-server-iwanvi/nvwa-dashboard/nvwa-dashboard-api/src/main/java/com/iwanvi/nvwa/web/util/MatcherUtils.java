package com.iwanvi.nvwa.web.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.iwanvi.nvwa.common.utils.StringUtils;

/**
 * class
 *
 * @author hao
 * @date 2018/12/26.
 */
public class MatcherUtils {

    private static Pattern POSITIVE_INTEGER_PATTERN = Pattern.compile("^[1-9]\\d*$");
    private static Pattern POSITIVE_NUMBER_PATTERN = Pattern.compile("([1-9][0-9]*(\\.\\d{1,2})?)|(0\\.\\d{1,2})");
    private final static String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    private final static String WEBSITE_REGEX = "(http|https):\\/\\/([\\w.]+\\/?)\\S*";


    /**
     * 判断正整数
     */
    public static boolean  isPositiveInteger(String regEx){
        Matcher matcher = POSITIVE_INTEGER_PATTERN.matcher(regEx);
        return matcher.matches();
    }

    /**
     * 正数
     */
    public static boolean isPositionNumber(String regEx) {
        Matcher matcher = POSITIVE_NUMBER_PATTERN.matcher(regEx);
        return matcher.matches();
    }
    
    /**
     * 判断email格式
     */    
    public static boolean isEmail(String email) {
         return email.matches(EMAIL_REGEX);
    }
    
    /**
     * 判断是否为网址
     * @param webSite
     * @return
     */
    public static boolean isWebSite(String webSite) {
    	return webSite.matches(WEBSITE_REGEX);
    }
    

}
