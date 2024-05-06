package com.risk.companysearchdemo.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.risk.companysearchdemo.model.dto.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CompaniesResponse {

    @JsonProperty("total_results")
    private Integer totalResults;
    @JsonProperty("items")
    private List<Company> items;


}
