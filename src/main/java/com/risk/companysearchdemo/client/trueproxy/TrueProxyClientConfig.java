package com.risk.companysearchdemo.client.trueproxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.risk.companysearchdemo.client.trueproxy.mapper.TrueProxyClientMapper;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrueProxyClientConfig {

    @NotNull
    private String baseUrl;
    @Builder.Default
    private ObjectMapper mapper = TrueProxyClientMapper.getMapper();
    @Builder.Default
    private CacheConfig cache = new CacheConfig();

}
