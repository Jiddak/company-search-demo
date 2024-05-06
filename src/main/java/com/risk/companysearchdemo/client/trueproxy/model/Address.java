
package com.risk.companysearchdemo.client.trueproxy.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {

    @JsonProperty("premises")
    private String premises;
    @JsonProperty("postal_code")
    private String postalCode;
    @JsonProperty("country")
    private String country;
    @JsonProperty("locality")
    private String locality;
    @JsonProperty("address_line_1")
    private String addressLine1;
    @JsonProperty("region")
    private String region;
    @JsonProperty("address_line_2")
    private String addressLine2;

}
