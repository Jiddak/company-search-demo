package com.risk.companysearchdemo.service;

import com.risk.companysearchdemo.client.trueproxy.TrueProxyClient;
import com.risk.companysearchdemo.client.trueproxy.model.request.CompanySearchRequest;
import com.risk.companysearchdemo.client.trueproxy.model.response.CompanySearchResponse;
import com.risk.companysearchdemo.mapper.CompanyMapper;
import com.risk.companysearchdemo.model.dto.Company;
import com.risk.companysearchdemo.model.dto.request.CompaniesRequest;
import com.risk.companysearchdemo.model.dto.response.CompaniesResponse;
import com.risk.companysearchdemo.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final TrueProxyClient trueProxyClient;
    private final OfficerService officerService;
    private final CompanyMapper companyMapper;
    private final CompanyRepository companyRepository;

    public CompaniesResponse getCompanies(CompaniesRequest request,String apiKey,boolean removeInactive){

        List<Company> companies;

        if(request.getCompanyNumber() != null)
            companies = companyRepository.findByCompanyNumber(request.getCompanyNumber())
                .map(companyMapper::toCanonical)
                .map(Collections::singletonList)
                .orElseGet(() -> getAndSaveCompanies(request,apiKey,removeInactive));
        else
            companies = getAndSaveCompanies(request,apiKey,removeInactive);

        return CompaniesResponse.builder()
            .items(companies)
            .totalResults(companies.size())
            .build();

    }

    private List<Company> getAndSaveCompanies(CompaniesRequest request,String apiKey,boolean removeInactive){
        List<Company> companies = getCompaniesFromRemote(request, apiKey, removeInactive);

        for(Company company: companies){
            companyRepository.findByCompanyNumber(company.getCompanyNumber())
                    .ifPresentOrElse(
                        s -> log.debug("Not saving to db due to duplicate company number: {}",company.getCompanyNumber()),
                        () -> companyRepository.save(companyMapper.toEntity(company)));
        }

        return companies;
    }

    private List<Company> getCompaniesFromRemote(CompaniesRequest request,String apiKey,boolean removeInactive){

        String searchTerm = request.getCompanyNumber() != null
            ? request.getCompanyNumber()
            : request.getCompanyName();

        CompanySearchRequest searchRequest = CompanySearchRequest.builder()
            .searchTerm(searchTerm)
            .apiKey(apiKey)
            .build();

        log.debug("Searching for company remotely: {}",request);

        CompanySearchResponse response = trueProxyClient.getCompany(searchRequest);

        return response.getCompanies().stream()
            .filter(company -> filterInactive(company,removeInactive))
            .map(company -> companyMapper.toCanonical(
                company,
                officerService.getOfficers(company.getCompanyNumber(), apiKey)
            ))
            .toList();

    }

    private boolean filterInactive(com.risk.companysearchdemo.client.trueproxy.model.Company company, boolean removeInactive){

        if(!removeInactive)
            return true;

        return "active".equals(company.getCompanyStatus());

    }


}
