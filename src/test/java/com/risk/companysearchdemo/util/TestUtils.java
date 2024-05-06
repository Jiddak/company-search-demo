package com.risk.companysearchdemo.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestUtils {

    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .findAndRegisterModules();
    }

    @SneakyThrows
    public static String sneakyWriteAsString(Object object) {
        return mapper.writeValueAsString(object);
    }

    @SneakyThrows
    public static <T> T sneakyReadValue(String json,Class<T> clazz) {
        return mapper.readValue(json,clazz);
    }

    @SneakyThrows
    public static <T> T sneakyReadValue(String json, TypeReference<T> typeReference) {
        return mapper.readValue(json, typeReference);
    }

    public static ObjectReader getObjectReader(){
        return mapper.reader();
    }

    public static ObjectWriter getObjectWriter(){
        return mapper.writer();
    }

    public static ObjectMapper getMapper(){
        return mapper;
    }

}
