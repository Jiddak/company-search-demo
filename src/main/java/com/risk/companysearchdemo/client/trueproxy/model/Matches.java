
package com.risk.companysearchdemo.client.trueproxy.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Matches {

    @JsonProperty("title")
    private List<Integer> title;
    @JsonProperty("snippet")
    private List<Integer> snippet;

}
