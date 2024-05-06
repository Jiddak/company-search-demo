package com.risk.companysearchdemo.config;

import com.risk.companysearchdemo.client.trueproxy.CacheConfig;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "true-proxy")
@Getter
@Setter
@NoArgsConstructor
public class TrueProxyProperties {

    @NotNull
    private String baseUrl;

    private CacheConfig cache;

}
