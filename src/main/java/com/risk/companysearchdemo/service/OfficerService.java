package com.risk.companysearchdemo.service;

import com.risk.companysearchdemo.model.dto.Officer;

import java.util.List;

public interface OfficerService {

    List<Officer> getOfficers(String companyNumber, String apiKey);

}
