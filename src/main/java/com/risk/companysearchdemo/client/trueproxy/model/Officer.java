
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
public class Officer {

    @JsonProperty("address")
    private Address address;
    @JsonProperty("name")
    private String name;
    @JsonProperty("appointed_on")
    private String appointedOn;
    @JsonProperty("resigned_on")
    private String resignedOn;
    @JsonProperty("officer_role")
    private String officerRole;
    @JsonProperty("links")
    private OfficerLinks links;
    @JsonProperty("date_of_birth")
    private DateOfBirth dateOfBirth;
    @JsonProperty("occupation")
    private String occupation;
    @JsonProperty("country_of_residence")
    private String countryOfResidence;
    @JsonProperty("nationality")
    private String nationality;

}
