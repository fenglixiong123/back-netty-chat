package com.flx.netty.chat.common.utils.code;

import java.util.Random;

/**
 * @Author: Fenglixiong
 * @Date: 2020/9/15 17:26
 * @Description:
 */
public class RandomCodeUtils {

    public static int getRandomNumber(int min,int max){
        if(min<0){min = 0;}
        if(max<0){max = 0;}
        if(min>=max){min = 0;max = 10;}
        Random generator = new Random();
        return generator.nextInt(max-min+1)+min;
    }

    /**
     * 生成随机代码
     * @param length
     * @return
     */
    public static String getRandomCode(int length) {
        Random generator = new Random();
        char[] cs = new char[length];
        for (int i = 0; i < cs.length; i++) {
            int characterType = generator.nextInt(3);
            int k = 0;
            if (characterType == 0)
                k = 49 + generator.nextInt(9);
            else if (characterType == 1)
                k = 65 + generator.nextInt(26);
            else if (characterType == 2)
                k = 97 + generator.nextInt(26);
            cs[i] = (char) k;
        }
        return new String(cs);
    }

    /**
     * 生成随机数
     * @param length
     * @return
     */
    public static String getRandomNumber(int length) {
        Random generator = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int k = generator.nextInt(9);
            sb.append(k);
        }
        return sb.toString();
    }

    /**
     * 只有数字的密码生成
     *
     * @param length
     * @return
     */
    public static String generateRandomString(int length,boolean onlyNumber) {
        String baseSeed;
        if(onlyNumber){
            baseSeed = new String("1234567890");
        }else {
            baseSeed = new String("abcdefghjkmnpqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ23456789");
        }
        StringBuffer sb = new StringBuffer();
        String ar = null;
        Random r = new Random();
        int te;
        for (int i = 1; i <= length; i++) {
            te = r.nextInt(baseSeed.length());
            ar = ar + baseSeed.charAt(te);
            sb.append(baseSeed.charAt(te));
        }
        return sb.toString();
    }

}
