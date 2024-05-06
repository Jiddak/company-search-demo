package com.risk.companysearchdemo.client.trueproxy.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.risk.companysearchdemo.client.trueproxy.model.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanySearchResponse {

    @JsonProperty("page_number")
    private Integer pageNumber;
    @JsonProperty("kind")
    private String kind;
    @JsonProperty("total_results")
    private Integer totalResults;
    @JsonProperty("items")
    private List<Company> companies;

}