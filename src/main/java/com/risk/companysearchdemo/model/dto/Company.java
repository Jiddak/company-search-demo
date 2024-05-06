
package com.risk.companysearchdemo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Company {

    @JsonProperty("company_number")
    private String companyNumber;
    @JsonProperty("company_type")
    private String companyType;
    @JsonProperty("title")
    private String title;
    @JsonProperty("company_status")
    private String companyStatus;
    @JsonProperty("date_of_creation")
    private String dateOfCreation;
    @JsonProperty("address")
    private Address address;
    @JsonProperty("officers")
    private List<Officer> officers;

}
