
package com.risk.companysearchdemo.client.trueproxy.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company {

    @JsonProperty("company_status")
    private String companyStatus;
    @JsonProperty("address_snippet")
    private String addressSnippet;
    @JsonProperty("date_of_creation")
    private String dateOfCreation;
    @JsonProperty("matches")
    private Matches matches;
    @JsonProperty("description")
    private String description;
    @JsonProperty("links")
    private Links links;
    @JsonProperty("company_number")
    private String companyNumber;
    @JsonProperty("title")
    private String title;
    @JsonProperty("company_type")
    private String companyType;
    @JsonProperty("address")
    private Address address;
    @JsonProperty("kind")
    private String kind;
    @JsonProperty("description_identifier")
    private List<String> descriptionIdentifier;
    @JsonProperty("date_of_cessation")
    private String dateOfCessation;
    @JsonProperty("snippet")
    private String snippet;

}
