
package com.risk.companysearchdemo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class Address {

    @JsonProperty("locality")
    private String locality;
    @JsonProperty("postal_code")
    private String postalCode;
    @JsonProperty("premises")
    private String premises;
    @JsonProperty("address_line_1")
    private String addressLine1;
    @JsonProperty("country")
    private String country;

}
