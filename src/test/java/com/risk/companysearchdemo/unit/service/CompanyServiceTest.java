package com.risk.companysearchdemo.unit.service;

import com.risk.companysearchdemo.client.trueproxy.TrueProxyClient;
import com.risk.companysearchdemo.client.trueproxy.model.request.CompanySearchRequest;
import com.risk.companysearchdemo.client.trueproxy.model.response.CompanySearchResponse;
import com.risk.companysearchdemo.mapper.CompanyMapper;
import com.risk.companysearchdemo.model.dao.CompanyEntity;
import com.risk.companysearchdemo.model.dto.Company;
import com.risk.companysearchdemo.model.dto.Officer;
import com.risk.companysearchdemo.model.dto.request.CompaniesRequest;
import com.risk.companysearchdemo.model.dto.response.CompaniesResponse;
import com.risk.companysearchdemo.repository.CompanyRepository;
import com.risk.companysearchdemo.service.CompanyService;
import com.risk.companysearchdemo.service.CompanyServiceImpl;
import com.risk.companysearchdemo.service.OfficerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CompanyServiceTest {

    private CompanyService companyService;
    private TrueProxyClient trueProxyClient;
    private OfficerService officerService;
    private CompanyMapper companyMapper;
    private CompanyRepository companyRepository;

    @BeforeEach
    public void setup(){

        trueProxyClient = mock(TrueProxyClient.class);
        officerService = mock(OfficerService.class);
        companyMapper = mock(CompanyMapper.class);
        companyRepository = mock(CompanyRepository.class);
        companyService = new CompanyServiceImpl(trueProxyClient,officerService,companyMapper,companyRepository);

    }

    @Test
    public void getCompanies_WithCompanyNumberAndCompanyName_ExistsInDb_Test(){

        CompaniesRequest request = new CompaniesRequest("BBC","212454");
        String apiKey = "zekey";
        boolean removeInactive = false;
        CompanyEntity entity = new CompanyEntity();
        Company company = new Company();

        CompaniesResponse expected = CompaniesResponse.builder()
                .items(Collections.singletonList(company))
                    .totalResults(1)
                        .build();


        when(companyRepository.findByCompanyNumber(request.getCompanyNumber())).thenReturn(Optional.of(entity));
        when(companyMapper.toCanonical(entity)).thenReturn(company);

        CompaniesResponse actual = companyService.getCompanies(request,apiKey,removeInactive);

        assertEquals(expected,actual);

        verify(companyRepository,times(1)).findByCompanyNumber(request.getCompanyNumber());
        verify(companyMapper,times(1)).toCanonical(entity);

    }

    @Test
    public void getCompanies_WithCompanyNumberAndCompanyName_NotExistInDb_Test(){

        String companyNumber = "12345";
        CompaniesRequest request = new CompaniesRequest("BBC",companyNumber);
        String apiKey = "zekey";
        boolean removeInactive = false;
        Company company = new Company();
        company.setCompanyNumber(companyNumber);

        com.risk.companysearchdemo.client.trueproxy.model.Company trueProxyCompany =
            new com.risk.companysearchdemo.client.trueproxy.model.Company();
        trueProxyCompany.setCompanyNumber(companyNumber);

        List<Officer> officers = Collections.singletonList(new Officer());

        List<com.risk.companysearchdemo.client.trueproxy.model.Company> companies
            = Collections.singletonList(trueProxyCompany);

        CompanySearchRequest searchRequest = CompanySearchRequest.builder()
            .searchTerm(companyNumber)
            .apiKey(apiKey)
            .build();
        
        CompanySearchResponse response = CompanySearchResponse.builder()
            .companies(companies)
            .build();

        CompaniesResponse expected = CompaniesResponse.builder()
            .items(Collections.singletonList(company))
            .totalResults(1)
            .build();


        when(companyRepository.findByCompanyNumber(companyNumber)).thenReturn(Optional.empty());
        when(trueProxyClient.getCompany(searchRequest)).thenReturn(response);
        when(officerService.getOfficers(companyNumber, apiKey)).thenReturn(officers);
        when(companyMapper.toCanonical(trueProxyCompany, officers)).thenReturn(company);
        

        CompaniesResponse actual = companyService.getCompanies(request,apiKey,removeInactive);

        assertEquals(expected,actual);

        verify(companyRepository,times(2)).findByCompanyNumber(companyNumber);
        verify(companyMapper,times(0)).toCanonical(any());
        verify(trueProxyClient,times(1)).getCompany(searchRequest);
        verify(officerService,times(1)).getOfficers(companyNumber, apiKey);
        verify(companyMapper,times(1)).toCanonical(trueProxyCompany, officers);


    }

    @Test
    public void getCompanies_WithCompanyName_Test(){

        CompaniesRequest request = new CompaniesRequest("BBC",null);
        String companyNumber = "12345";
        String apiKey = "zekey";
        boolean removeInactive = false;
        Company company = new Company();
        company.setCompanyNumber(companyNumber);

        com.risk.companysearchdemo.client.trueproxy.model.Company trueProxyCompany =
            new com.risk.companysearchdemo.client.trueproxy.model.Company();
        trueProxyCompany.setCompanyNumber(companyNumber);

        List<Officer> officers = Collections.singletonList(new Officer());

        CompanyEntity entity = new CompanyEntity();

        List<com.risk.companysearchdemo.client.trueproxy.model.Company> companies
            = Collections.singletonList(trueProxyCompany);

        CompanySearchRequest searchRequest = CompanySearchRequest.builder()
            .searchTerm(request.getCompanyName())
            .apiKey(apiKey)
            .build();

        CompanySearchResponse response = CompanySearchResponse.builder()
            .companies(companies)
            .build();

        CompaniesResponse expected = CompaniesResponse.builder()
            .items(Collections.singletonList(company))
            .totalResults(1)
            .build();


        when(companyRepository.findByCompanyNumber(companyNumber)).thenReturn(Optional.of(entity));
        when(trueProxyClient.getCompany(searchRequest)).thenReturn(response);
        when(officerService.getOfficers(companyNumber, apiKey)).thenReturn(officers);
        when(companyMapper.toCanonical(trueProxyCompany, officers)).thenReturn(company);

        CompaniesResponse actual = companyService.getCompanies(request,apiKey,removeInactive);

        assertEquals(expected,actual);

        verify(companyRepository,times(1)).findByCompanyNumber(companyNumber);
        verify(companyMapper,times(0)).toCanonical(any());
        verify(trueProxyClient,times(1)).getCompany(searchRequest);
        verify(officerService,times(1)).getOfficers(companyNumber, apiKey);
        verify(companyMapper,times(1)).toCanonical(trueProxyCompany, officers);


    }

    @Test
    public void getCompanies_WithCompanyName_removeInactive_Test(){

        CompaniesRequest request = new CompaniesRequest("BBC",null);
        String companyNumber = "12345";
        String apiKey = "zekey";
        boolean removeInactive = true;
        Company company = new Company();
        company.setCompanyNumber(companyNumber);

        com.risk.companysearchdemo.client.trueproxy.model.Company trueProxyCompany =
            new com.risk.companysearchdemo.client.trueproxy.model.Company();
        trueProxyCompany.setCompanyNumber(companyNumber);
        trueProxyCompany.setCompanyStatus("LIQUID");

        List<Officer> officers = Collections.singletonList(new Officer());

        CompanyEntity entity = new CompanyEntity();

        List<com.risk.companysearchdemo.client.trueproxy.model.Company> companies
            = Collections.singletonList(trueProxyCompany);

        CompanySearchRequest searchRequest = CompanySearchRequest.builder()
            .searchTerm(request.getCompanyName())
            .apiKey(apiKey)
            .build();

        CompanySearchResponse response = CompanySearchResponse.builder()
            .companies(companies)
            .build();

        CompaniesResponse expected = CompaniesResponse.builder()
            .totalResults(0)
            .items(Collections.emptyList())
            .build();


        when(companyRepository.findByCompanyNumber(companyNumber)).thenReturn(Optional.of(entity));
        when(trueProxyClient.getCompany(searchRequest)).thenReturn(response);
        when(officerService.getOfficers(companyNumber, apiKey)).thenReturn(officers);
        when(companyMapper.toCanonical(trueProxyCompany, officers)).thenReturn(company);

        CompaniesResponse actual = companyService.getCompanies(request,apiKey,removeInactive);

        assertEquals(expected,actual);

        verify(companyRepository,times(0)).findByCompanyNumber(companyNumber);
        verify(companyMapper,times(0)).toCanonical(any());
        verify(trueProxyClient,times(1)).getCompany(searchRequest);
        verify(officerService,times(0)).getOfficers(companyNumber, apiKey);
        verify(companyMapper,times(0)).toCanonical(trueProxyCompany, officers);


    }





}
