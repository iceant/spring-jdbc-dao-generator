package com.github.iceant.spring.jdbc.dao.generator.webapp.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@JsonComponent
@Configuration
public class JsonConfiguration {
    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        //忽略value为null 时 key的输出
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // 修正日期格式为 ISO
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        objectMapper.setDateFormat(dateFormat);
//        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));

//        builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(SpringUtil.getDateTimeFormatByLocale())))
//                .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(SpringUtil.getDateTimeFormatByLocale())));
//
//        builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(SpringUtil.getDateFormatByLocale())));
//        builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(SpringUtil.getDateTimeFormatByLocale())));

        /**
         * 序列换成json时,将所有的long变成string
         * 因为js中得数字类型不能包含所有的java long值
         */
        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(module);

        return objectMapper;
    }
}