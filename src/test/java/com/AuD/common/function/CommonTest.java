package com.AuD.common.function;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

/**
 * Description: TODO
 *
 * @author AuD/胡钊
 * @ClassName CommonTest
 * @date 2021/4/20 17:28
 * @Version 1.0
 */
public class CommonTest {

    /** LocalDate & LocalDateTime API demo */
    @Test
    public void testLocalDateTime(){
        // === 获取当前时间
        LocalDateTime now  = LocalDateTime.now();
        // === 时间转换为指定时间格式(eg.yyyy-MM-dd HH:mm:ss)的字符串
        System.out.println(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));

        /**
         * 解析字符带有 "AM 或 PM"时间字符串.
         * 1.必须使用API{@link DateTimeFormatter#ofPattern(java.lang.String, java.util.Locale)}来处理,
         * 其中Locale设置为{@link Locale#ENGLISH},并且AM或者PM为大写,小写则解析抛异常
         * 2.时间格式需要带上 "a",eg:"yyyy-MM-dd hh:mm:ss a",待解析的字符串与这种模板格式保持一致,"2021-09-23 06:30:15PM"就不行.
         * 3.如果时间格式设置为"yyyy-MM-dd HH:mm:ss a",那么解析"2021-09-23 06:30:15 PM"抛异常,因为"H"表示24小时.
         * -- java.time.format.DateTimeParseException:
         *    Text '2021-09-23 06:30:15 PM' could not be parsed:
         *    Conflict found: Field AmPmOfDay 0 differs from AmPmOfDay 1 derived from 06:30:15
         * -- 其中时间格式化参数,参考{@link DateTimeFormatter}类注释内容.
         */
        String patternOfUS = "2021-09-23 06:30:15 PM";
        System.out.println(LocalDateTime.parse(patternOfUS,DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a",Locale.ENGLISH)));

        // === 获取时间戳(秒)
        System.out.println(now.toEpochSecond(ZoneOffset.of("+8")));
        // === 获取时间戳(豪秒)
        System.out.println(now.toInstant(ZoneOffset.of("+8")).toEpochMilli());

    }

    /** UUID test */
    @Test
    public void testUUID() throws InterruptedException {
        String src = "123-456-abc-fcde-bbcdeef";
        System.out.println(UUID.fromString(src));
        // 00000123-0456-0abc-fcde-00000bbcdeef
        System.out.println(UUID.fromString(src));
//        while (true){
//            System.out.println(UUID.randomUUID().toString());
//            TimeUnit.MILLISECONDS.sleep(200);
//        }
        System.out.println();
    }


}
