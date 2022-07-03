package com.AuD.common.function.utils;

/**
 * Description: info of package -- 常用/通用工具类封装. <br>
 * Note(封装function之前需要确认的事项,这个很重要!!!): <br>
 * 1.如果封装的某个function的实现逻辑仅仅只需要少数代码(eg.1~2行代码),并且这些代码也只是某个类的API调用,这样的场景是没有封装的意义. <br>
 * eg:"DateTimeUtils",想要封装时间与时间戳的转换,字符串与时间转换. <br>
 * -- 将时间(dataTime)转换为 yyyy-MM-dd HH:mm:ss形式。<br>
 *     public static String toSpecialFormat(LocalDateTime dateTime){
 *         return toSpecialFormat(dateTime,FORMAT_DATE_TIME);
 *     }
 *    public static String toSpecialFormat(LocalDateTime dateTime,String pattern){
 *         return dateTime.format(DateTimeFormatter.ofPattern(pattern));
 *     }
 * -- 上述仅仅使用了{@link java.time.LocalDateTime}的API而已,并没有做额外处理,这种"封装"是没有意义的,因为仅仅call某个类的function.<br>
 * -- 类似这种JDK或者某个jar(eg.com.alibaba.fastjson.JSON)自带的API可以完成的需求,就没有封装的必要.<br>
 *
 * 2.如果在某个复杂的业务场景下,当{@link java.time.LocalDateTime}提供的API不能完成需求的情况下,请自行在当前项目工程中抒写特殊的封装逻辑. <br>
 *
 *
 * @author AuD/胡钊
 * @date 2021/9/1 11:56
 * @Version 1.0
 */