package com.risk.companysearchdemo.config;

import com.risk.companysearchdemo.client.trueproxy.TrueProxyClient;
import com.risk.companysearchdemo.client.trueproxy.TrueProxyClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrueProxyConfig {

    @Bean
    TrueProxyClient trueProxyClient(TrueProxyProperties properties){

        return new TrueProxyClient(
            TrueProxyClientConfig.builder()
                .baseUrl(properties.getBaseUrl())
                .cache(properties.getCache())
                .build()
        );

    }


}
