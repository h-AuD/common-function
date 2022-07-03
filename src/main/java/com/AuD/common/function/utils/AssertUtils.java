package com.AuD.common.function.utils;

import java.util.Arrays;

/**
 * Description:提供条件机制,可以结合自定义异常组合使用.
 *
 * <p> 参考 "org.springframework.util.Assert". </br>
 *
 * @author AuD/胡钊
 * @ClassName Conditional
 * @date 2021/7/12 15:29
 * @Version 1.0
 */
public final class AssertUtils {


    private AssertUtils(){
        throw new AssertionError("No Conditional instances for you!");
    }

    /**
     * 断言某个表达式(expression)是否为true. </br>
     * 如果表达式结果为false,抛出非法参数异常. --> eg.Conditional.isTrue( i > 0, "The value must be greater than zero"); </br>
     *
     * @param expression a boolean expression
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if {@code expression} is {@code false}
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }


    /**
     * Assert that an object is not {@code null}.
     *
     * @param object the object to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the object is {@code null}
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }


    public static void hasText(String str,String message){
        if(!StringUtils.hasText(str)){
            throw new IllegalArgumentException(message);
        }
    }


    /**
     * 断言是否满足某个条件(expression),否则抛出IllegalArgumentException(message)信息. </br>
     *
     * @param expression a boolean expression
     * @param message the exception info to use if the assertion fails
     * @throws IllegalArgumentException if {@code expression} is {@code false}
     */
    public static void conditionMatch(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言是否满足某个条件(expression). </br>
     * 如果表达式结果为false,抛出自定义指定异常,用户可以对异常做相应处理.   </br>
     *
     * @param expression a boolean expression
     * @param error the exception info to use if the assertion fails
     * @throws IllegalArgumentException if {@code expression} is {@code false}
     */
    public static void conditionMatch(boolean expression, Throwable error) throws Throwable {
        if (!expression) {
            throw error;
        }
    }

    /**
     * 判断对象数组是否都为null
     * @param objs
     */
    public static void isNotNull(Object... objs){
        // Arrays.asList(objs).stream().filter(o -> o==null).findAny() === 如果都为null,则出现null异常
        if(Arrays.asList(objs).stream().filter(o -> o==null).count()>0){
            throw new IllegalArgumentException("params/obj_arr exist null");
        }
    }




}
