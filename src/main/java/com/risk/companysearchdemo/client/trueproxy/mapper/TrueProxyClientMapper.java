package com.risk.companysearchdemo.client.trueproxy.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TrueProxyClientMapper {

    @Getter
    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .findAndRegisterModules();
    }

}
