package com.AuD.common.function.json.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Description: {@link JSONField} 注解使用,该注解主要用于数据传输/交互. </br>
 * --- 从Java角度来看,这样做法,污染了POJO。 </br>
 *
 * Note:SpringMVC默认使用的是jackson做序列化。推荐优先使用jackson,理由:代码尽可能保持统一 & 相信大佬的选择. </br>
 *
 * @author AuD/胡钊
 * @ClassName BeanUsingFastJson
 * @date 2021/9/22 13:48
 * @Version 1.0
 */
@Data
public class BeanUsingFastJson {

    private String title;

    @JSONField(name = "show_cover_pic")
    private int isShowCoverPic;

    @JSONField(name = "content_source_url")
    private String sourceUrl;

    /**
     * 注意: <br>
     * 1."yyyy-MM-dd HH:mm:ss" & "yyyy-MM-dd hh:mm:ss"是有区别的. <br>
     * -- 出现的问题,使用{@link JSON#parseObject(java.lang.String, java.lang.Class)}反序列化json字符串出现异常:
     *    Text '2021-07-25 12:55:41' could not be parsed: Unable to obtain LocalDateTime from TemporalAccessor:
     *    {NanoOfSecond=0, MicroOfSecond=0, SecondOfMinute=41, MilliOfSecond=0, MinuteOfHour=55, HourOfAmPm=0},
     *    ISO resolved to 2021-07-25 of type java.time.format. <br>
     * -- 问题与JSON反序列化无关，而与时间格式字符串有关,"yyyy-MM-dd hh:mm:ss" === 小时设置为"hh",这是一个12小时格式程序,需要"AM"或"PM"值.<br>
     * -- 格式设置为"yyyy-MM-dd HH:mm:ss"即可解决问题. <br>
     */
    @JSONField(name = "create_time",format = "yyyy-MM-dd hh:mm:ss")
    //@JSONField(name = "create_time",format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ctime;

    @Override
    public String toString() {
        return "BeanUsingFastJson:{" +
                "title='" + title + '\'' +
                ", isShowCoverPic=" + isShowCoverPic +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", ctime=" + ctime +
                '}';
    }
}
