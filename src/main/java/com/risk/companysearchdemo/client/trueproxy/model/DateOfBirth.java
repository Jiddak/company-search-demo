
package com.risk.companysearchdemo.client.trueproxy.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateOfBirth {

    @JsonProperty("month")
    private Integer month;
    @JsonProperty("year")
    private Integer year;

}
