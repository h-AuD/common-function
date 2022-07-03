package com.AuD.common.function.json.model;

import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Description: 复杂对象,用于构建JSON数据 - Map、List、ObjectRef、Array.
 *
 * @author AuD/胡钊
 * @ClassName ComplexObj
 * @date 2021/9/22 12:13
 * @Version 1.0
 */
@Data
public class ComplexObj {

    private Map<String,Object> map;

    private List<String> strList;

    private SimpleObj simpleRef;

    private Object[] objArray;

    @Override
    public String toString() {
        return "ComplexObj:{" +
                "map=" + map +
                ", strList=" + strList +
                ", simpleRef=" + simpleRef +
                ", obs=" + Arrays.toString(objArray) +
                '}';
    }
}
