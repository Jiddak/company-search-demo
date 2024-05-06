package com.risk.companysearchdemo.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompaniesRequest {

    private String companyName;
    private String companyNumber;

}
