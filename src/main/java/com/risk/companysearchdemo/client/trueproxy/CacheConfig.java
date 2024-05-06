package com.risk.companysearchdemo.client.trueproxy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//This ideally would not be in the client code and would be in an artifact to be used
//across any classes requiring caching, but for the purposes of this demo that seems out of scope
public class CacheConfig {

    @Builder.Default
    private boolean enabled = false;
    @Builder.Default
    private long cacheSize = 100;
    @Builder.Default
    private long cacheDurationMillis = 36000;

}
