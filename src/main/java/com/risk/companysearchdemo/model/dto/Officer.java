
package com.risk.companysearchdemo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Officer {

    @JsonProperty("name")
    private String name;
    @JsonProperty("officer_role")
    private String officerRole;
    @JsonProperty("appointed_on")
    private String appointedOn;
    @JsonProperty("address")
    private Address address;

}
