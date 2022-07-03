package AuD.common.function.json.doc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

/**
 * Description: TODO
 *
 * @author AuD/胡钊
 * @ClassName Jackson
 * @date 2021/10/11 9:43
 * @Version 1.0
 */
public interface Jackson {

    public static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 定制化 jackson序列化、反序列化 {@link LocalDateTime}、{@link LocalDate}、{@link LocalTime}规则.
     */
    default void customObjectMapper(){
        /** 设置时间数据格式 */
        String localDateTimeFormat = "yyyy-MM-dd HH:mm:ss";
        String localDateFormat = "yyyy-MM-dd";
        String localTimeFormat = "HH:mm:ss";

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(localDateTimeFormat)));
        javaTimeModule.addSerializer(LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ofPattern(localDateFormat)));
        javaTimeModule.addSerializer(LocalTime.class,
                new LocalTimeSerializer(DateTimeFormatter.ofPattern(localTimeFormat)));

        // ==== 设置反序列化格式的类型只会保持一个,因为内部通过Map.put(key,value)实现的,上述同理(i.e.序列化也是如此)
        // ==== eg.下面add2个LocalDateTime,导致第一个会被覆盖.
        javaTimeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(localDateTimeFormat)));
        //javaTimeModule.addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        javaTimeModule.addDeserializer(LocalDate.class,
                new LocalDateDeserializer(DateTimeFormatter.ofPattern(localDateFormat)));
        javaTimeModule.addDeserializer(LocalTime.class,
                new LocalTimeDeserializer(DateTimeFormatter.ofPattern(localTimeFormat)));

        OBJECT_MAPPER.registerModule(javaTimeModule);
    }


}
