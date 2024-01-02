package dev.concat.vab.ecomhotelappbackend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();

        // Register custom serializer and deserializer
        simpleModule.addSerializer(OffsetDateTime.class, new OffsetDateTimeSerializer());
        simpleModule.addDeserializer(OffsetDateTime.class, new OffsetDateTimeDeserializer());

        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }
}
