package com.risk.companysearchdemo.service;

import com.risk.companysearchdemo.model.dto.request.CompaniesRequest;
import com.risk.companysearchdemo.model.dto.response.CompaniesResponse;

public interface CompanyService {

    CompaniesResponse getCompanies(CompaniesRequest request, String apiKey, boolean removeInactive);

}
