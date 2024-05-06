package com.risk.companysearchdemo.client.trueproxy.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OfficerSearchRequest extends TrueProxyRequest {

    private String companyNumber;

}
