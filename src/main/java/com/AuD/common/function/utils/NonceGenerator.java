package com.AuD.common.function.utils;

import java.util.Random;

/**
 * Description: 随机字符串生成器
 *
 * @author AuD/胡钊
 * @ClassName NonceGenerator
 * @date 2021/2/5 18:09
 * @Version 1.0
 */
public final class NonceGenerator {

    // ==== 用于生成随机字符串的数据源
    /** 数字源 */
    private static final String NUM="0123456789";

    /** 字母源 */
    private static final String LETTERS="zxcvbnmasdfghjklqwertyuiopZXCVBNMASDFGHJKLQWERTYUIOP";

    private static final Random random = new Random();

    /* 私有化构造器 == 禁止new对象,避免资源浪费 */
    private NonceGenerator(){}

    /* ============ 以下是生成随机字符的功能 ============= */
    /** 生成只含有数字的随机字符串 */
    public static String ofNum(int length){
        return generate(length,null,NUM);
    }

    /** 生成只含有数字的随机字符串 === 有指定前缀 */
    public static String ofNum(int length,String prefix){
        return generate(length,prefix,NUM);
    }


    /** 生成只含有字母(包含大小写)的随机字符串 */
    public static String ofChars(int length){
        return generate(length,null,LETTERS);
    }

    /** 生成只含有字母(包含大小写)的随机字符串 === 有指定前缀 */
    public static String ofChars(int length,String prefix){
        return generate(length,prefix,LETTERS);
    }


    /** 生成随机字符串(含有数字、字母) */
    public static String ofStr(int length){
        return generate(length,null,NUM+LETTERS);
    }

    /** 生成随机字符串(含有数字、字母) === 有指定前缀 */
    public static String ofStr(int length,String prefix){
        return generate(length,prefix,NUM+LETTERS);
    }


    /**
     * 效率测试:length=5000,耗时1ms
     *
     * @param length 随机字符长度
     * @param prefix 随机字符的前缀,不能为空格,prefix=""与prefix=null是一样的效果
     * @param
     * @return
     */
    public static String generate(int length, String prefix, String source){
        if(length<0 || !StringUtils.hasText(source)) {
            throw new RuntimeException("illegal parameter:length<0 OR source is empty");
        }
        if(length==0) {
            return "";
        }
        StringBuilder retStr = null;
        if(!StringUtils.hasText(prefix)){
            retStr = new StringBuilder(length);
        }else {
            retStr = new StringBuilder(length+prefix.length());
            retStr.append(prefix);
        }
        int randomScope = source.length();
        int i = 0;
        while (i<length){
            // random.nextInt(randomScope) ===> [0,randomScope)
            retStr.append(source.charAt(random.nextInt(randomScope)));
            i++;
        }
        return retStr.toString();
    }


}
