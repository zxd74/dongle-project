package com.iwanvi.nvwa.common.utils;

import java.security.SecureRandom;
import java.util.Random;

public class ShortUrlUtils {

    private static final String SYMBOLS = "0123456789";
    private static final Random RANDOM = new SecureRandom();

    private static final char[] chars = new char[]{
            'a','b','c','d','e','f','g','h',
            'i','j','k','l','m','n','o','p',
            'q','r','s','t','u','v','w','x',
            'y','z','0','1','2','3','4','5',
            '6','7','8','9','A','B','C','D',
            'E','F','G','H','I','J','K','L',
            'M','N','O','P','Q','R','S','T',
            'U','V','W','X','Y','Z'
    };

    private static String getShortUrl(String hex){
        int hexLen = hex.length();
        int subHexLen = hexLen / 8;
        String[] ShortStr = new String[4];

        for (int i = 0; i < subHexLen; i++) {
            StringBuilder outChars = new StringBuilder();
            int j = i + 1;
            String subHex = hex.substring(i * 8, j * 8);
            long idx = Long.valueOf("3FFFFFFF", 16) & Long.valueOf(subHex, 16);

            for (int k = 0; k < 6; k++) {
                int index = (int) (Long.valueOf("0000003D", 16) & idx);
                outChars.append(chars[index]);
                idx = idx >> 5;
            }
            ShortStr[i] = outChars.toString();
        }

        return ShortStr[0];
    }

    public static String getByUUID(){
        return getShortUrl(UUIDUtils.getUUID());
    }

    public static String getByMD5(String s){
        return getShortUrl(MD5Utils.MD5(s));
    }

    public static void main(String[] args) {
        System.out.println(getByUUID());
    }


    /**
     * 获取长度为 6 的随机数字
     * @return 随机数字
     */
    public static String getRandom6Num() {

        char[] nonceChars = new char[6];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }
}
