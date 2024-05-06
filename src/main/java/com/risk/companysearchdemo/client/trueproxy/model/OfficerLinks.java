
package com.risk.companysearchdemo.client.trueproxy.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficerLinks {

    @JsonProperty("officer")
    private OfficerLink officer;

}
