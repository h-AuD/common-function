package com.AuD.common.function.json.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Description:
 * <p>
 *     简单对象,用于构建JSON数据. </br>
 *     - 数字、字符、布尔. </br>
 * </p>
 *
 * @author AuD/胡钊
 * @ClassName SimpleObj
 * @date 2021/9/22 12:11
 * @Version 1.0
 */
@Data
public class SimpleObj {

    private Integer num;

    private String desc;

    private Boolean flag;

    private LocalDateTime ctime;

    @Override
    public String toString() {
        return "SimpleObj:{" +
                "num=" + num +
                ", desc='" + desc + '\'' +
                ", flag=" + flag +
                ", ctime=" + ctime +
                '}';
    }
}
