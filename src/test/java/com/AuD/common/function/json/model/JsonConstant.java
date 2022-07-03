package com.AuD.common.function.json.model;

/**
 * Description: 测试用的json字符串.
 *
 * @author AuD/胡钊
 * @ClassName JsonConstant
 * @date 2021/9/22 12:15
 * @Version 1.0
 */
public interface JsonConstant {

    /*
        {
            "num": 18,
            "desc": "this a desc",
            "flag": true,
            "ctime":"2021-09-22 13:30:35"
        }
     */
    String JSON_SIMPLE_OBJ = "{\n" +
            "    \"num\": 18,\n" +
            "    \"desc\": \"this a desc\",\n" +
            "    \"flag\": true,\n" +
            "    \"ctime\":\"2021-09-22 13:30:35\"\n" +
            "}";

    /*
        [
            {
                "num": 18,
                "desc": "this a desc",
                "flag": true,
                "ctime": "2021-09-22 13:30:35"
            }
        ]
     */
    String JSON_SIMPLE_OBJ_ARRAY = "[\n" +
            "    {\n" +
            "        \"num\": 18,\n" +
            "        \"desc\": \"this a desc\",\n" +
            "        \"flag\": true,\n" +
            "        \"ctime\": \"2021-09-22 13:30:35\"\n" +
            "    }\n" +
            "]";

    /*
        {
            "title":"this is a title",
            "show_cover_pic":0,
            "content_source_url":"www.xxx.cn.com",
            "create_time":"2021-07-25 12:55:41"
        }
     */
    String JSON_FOR_FIELD_ANNOTATION = "{\n" +
            "    \"title\":\"this is a title\",\n" +
            "    \"show_cover_pic\":0,\n" +
            "    \"content_source_url\":\"www.xxx.cn.com\",\n" +
            "    \"create_time\":\"2021-07-25 12:55:41\"\n" +
            "}";

    /*
        {
            "map": {
                "key1": "String",
                "key2": 2,
                "key3": false
            },
            "strList": [
                "str1",
                "str2"
            ],
            "simpleRef": {
                "num": 18,
                "desc": "this a desc",
                "flag": true
            },
            "objArray": [
                {
                    "num": 15,
                    "flag": true
                },
                18,
                "string",
                "2021-09-22 12:30:29"
            ]
        }
     */
    String JSON_COMPLEX_OBJ_1 = "{\n" +
            "    \"map\": {\n" +
            "        \"key1\": \"String\",\n" +
            "        \"key2\": 2,\n" +
            "        \"key3\": false\n" +
            "    },\n" +
            "    \"strList\": [\n" +
            "        \"str1\",\n" +
            "        \"str2\"\n" +
            "    ],\n" +
            "    \"simpleRef\": {\n" +
            "        \"num\": 18,\n" +
            "        \"desc\": \"this a desc\",\n" +
            "        \"flag\": true\n" +
            "    },\n" +
            "    \"objArray\": [\n" +
            "        {\n" +
            "            \"num\": 15,\n" +
            "            \"flag\": true\n" +
            "        },\n" +
            "        18,\n" +
            "        \"string\",\n" +
            "        \"2021-09-22 12:30:29\"\n" +
            "    ]\n" +
            "}";


}
