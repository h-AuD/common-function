package AuD.common.function.json;

import AuD.common.function.json.model.BeanUsingFastJson;
import AuD.common.function.json.model.ComplexObj;
import AuD.common.function.json.model.JsonConstant;
import AuD.common.function.json.model.SimpleObj;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Description:
 *
 * @author AuD/胡钊
 * @date 2021/1/13 11:34
 * @Version 1.0
 */
public class FastJson_API_test {



    // ====== 1.fastJson核心API使用说明
    /** ======================= JSON类型相关API说明 {@link com.alibaba.fastjson.JSON} ======================
     * 五种核心function:
     * 1.parse(String text): {@link com.alibaba.fastjson.JSON#parse(java.lang.String)}
     * - 把JSON文本(text)解析为Object,主要解析成JSONObject或者JSONArray.
     * attention: parse function
     *
     * 2.parseObject(String text) || parseObject(String text, Class<T> clazz):
     * - {@link JSON#parseObject(java.lang.String, java.lang.Class)}
     * - {@link com.alibaba.fastjson.JSON#parseObject(java.lang.String)}
     * --- 把JSON文本parse成JSONObject || 把JSON文本parse成类型T的对象
     *
     * 3.parseArray(String text): {@link JSON#parseArray(java.lang.String)} || {@link JSON#parseArray(java.lang.String, java.lang.Class)}
     * - 把JSON文本parse成JSONArray || 把JSON文本parse成类型T的对象
     *
     * 4.toJavaObject(Class<T> clazz) & toJavaObject(JSON json, Class<T> clazz):
     * - {@link com.alibaba.fastjson.JSON#toJavaObject(java.lang.Class)}
     * - {@link JSON#toJavaObject(com.alibaba.fastjson.JSON, java.lang.Class)}
     * --- 将JSON对象(this)转换为指定的类型T的对象 & 将指定的JSON对象转换为指定的类型T的对象
     *
     * 5.toJSON(Object javaObject): {@link JSON#toJSON(java.lang.Object)}
     * - 将Java对象转换为JSON、JSONObject、JSONArray
     * =====================================================================================================*/


    /**
     * 测试json转换为对象
     * {@link JSON#parseObject(String, Class)}
     */
    @Test
    public void testParseObject(){
        // === 反序列化,将json字符串转化为Java对象
        SimpleObj simpleObj = JSONObject.parseObject(JsonConstant.JSON_SIMPLE_OBJ, SimpleObj.class);
        System.out.println("simpleObj 反序列化结果(SimpleObj类型对象): "+"\n -- "+simpleObj);

        ComplexObj complexObj = JSONObject.parseObject(JsonConstant.JSON_COMPLEX_OBJ_1, ComplexObj.class);
        System.out.println("complexObj 反序列化结果(ComplexObj类型对象): "+"\n -- "+complexObj);

        Map<String,Object> map4simpleObj = JSONObject.parseObject(JsonConstant.JSON_SIMPLE_OBJ, Map.class);
        Map<String,Object> map4complexObj = JSONObject.parseObject(JsonConstant.JSON_COMPLEX_OBJ_1, Map.class);
        System.out.println("simpleObj 反序列化结果(Map类型对象): " + "\n -- "+ map4simpleObj);
        System.out.println("complexObj 反序列化结果(Map类型对象): " + "\n -- "+  map4complexObj);

        // ====== 以下是序列化API测试
        simpleObj = new SimpleObj();
        simpleObj.setCtime(LocalDateTime.now());
        simpleObj.setDesc("desc");
        System.out.println("simpleObj序列化结果:"+"\n -- "+JSON.toJSONString(simpleObj));
    }

    /**
     * 测试解析错误json字符串
     */
    @Test
    public void Json_Parse_error(){
        // 错误形式json字符串
        String errJsonStr = "aaa";
        try {
            System.out.println("result:"+JSON.parse(errJsonStr));
        }catch (Exception e){
            e.printStackTrace();
        }
        // 可以转换为json,但是结果为常量
        String jsonConstant = "123123";
        // parseRes为Integer类型
        Object parseRes = JSON.parse(jsonConstant);
        System.out.println("result:"+parseRes);
    }


    /**
     * 测试解析json的结果,主要观察结果对象的类型. === 使用debug方式运行.
     */
    @Test
    public void testJsonParse(){
        // 抛异常,原因在于parseObject只是转换为JSONObject类型
        //JSON.parseObject(JSON_SIMPLE_OBJ_ARRAY);
        final Object result1 = JSON.parse(JsonConstant.JSON_SIMPLE_OBJ_ARRAY);
        final Object result2 = JSON.parse(JsonConstant.JSON_SIMPLE_OBJ);
        final JSONObject result3 = JSON.parseObject(JsonConstant.JSON_SIMPLE_OBJ_ARRAY);
        final SimpleObj jsonModel = JSON.parseObject(JsonConstant.JSON_SIMPLE_OBJ_ARRAY, SimpleObj.class);

        final Object parse = JSONObject.parseObject(JsonConstant.JSON_SIMPLE_OBJ);
        System.out.println(parse);
    }

    /**
     * 测试 {@link JSONField} 注解 & 反序列化对象属性中LocalDateTime类型
     */
    @Test
    public void testJsonFieldAnnotation() throws IOException {
        try {
            // ==== 反序列化,将json格式字符串反序列化指定对象,下面会抛异常,字符串"2021-07-25 12:55:41"不能转换为LocalDateTime类型
            BeanUsingFastJson beanUsingFastJson1 = JSONObject.parseObject(JsonConstant.JSON_FOR_FIELD_ANNOTATION, BeanUsingFastJson.class);
            System.out.println("反序列化结果:" + beanUsingFastJson1);
        }catch (Exception e){
            e.printStackTrace();
        }
        // === 转换为json对象是OK的
        JSONObject jsonObject = JSONObject.parseObject(JsonConstant.JSON_FOR_FIELD_ANNOTATION);

        BeanUsingFastJson beanUsingFastJson2 = new BeanUsingFastJson();
        beanUsingFastJson2.setTitle("title");
        beanUsingFastJson2.setCtime(LocalDateTime.now());

        // === 将Java对象转换为JSON对象,这个不是序列化,JSON对象实质是Map类型. 下面的"toJSONString"才是序列化function
        Object jsonObj = JSONObject.toJSON(beanUsingFastJson2);
        // === toJSONString 方法才会使注解 "@JSONField(name = "create_time",format = "yyyy-MM-dd hh:mm:ss")"有效
        System.out.println(jsonObj+"\n"+JSONObject.toJSONString(beanUsingFastJson2));

    }


}






