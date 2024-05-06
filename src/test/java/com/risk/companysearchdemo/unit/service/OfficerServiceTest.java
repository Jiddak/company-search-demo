package com.risk.companysearchdemo.unit.service;

import com.risk.companysearchdemo.client.trueproxy.TrueProxyClient;
import com.risk.companysearchdemo.client.trueproxy.model.request.OfficerSearchRequest;
import com.risk.companysearchdemo.client.trueproxy.model.response.OfficerSearchResponse;
import com.risk.companysearchdemo.mapper.OfficerMapper;
import com.risk.companysearchdemo.model.dao.OfficerEntity;
import com.risk.companysearchdemo.model.dto.Officer;
import com.risk.companysearchdemo.repository.OfficerRepository;
import com.risk.companysearchdemo.service.OfficerService;
import com.risk.companysearchdemo.service.OfficerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OfficerServiceTest {

    private OfficerService officerService;
    private TrueProxyClient trueProxyClient;
    private OfficerMapper officerMapper;
    private OfficerRepository officerRepository;


    @BeforeEach
    public void init(){

        trueProxyClient = mock(TrueProxyClient.class);
        officerMapper = mock(OfficerMapper.class);
        officerRepository = mock(OfficerRepository.class);
        officerService = new OfficerServiceImpl(trueProxyClient,officerMapper,officerRepository);

    }

    @Test
    public void getOfficers_GoodResponse_Test(){

        String companyNumber = "1234";
        String apiKey = "itbekey";

        Officer officer = new Officer();

        List<Officer> expected = Collections.singletonList(officer);

        com.risk.companysearchdemo.client.trueproxy.model.Officer trueProxyOfficer
            = new com.risk.companysearchdemo.client.trueproxy.model.Officer();

        OfficerSearchRequest request = OfficerSearchRequest.builder()
            .companyNumber(companyNumber)
            .apiKey(apiKey)
            .build();

        OfficerSearchResponse response = OfficerSearchResponse.builder()
                .officers(Collections.singletonList(trueProxyOfficer))
                .build();


        when(officerRepository.findByCompany_CompanyNumber(companyNumber)).thenReturn(Collections.emptyList());
        when(trueProxyClient.getOfficer(request)).thenReturn(response);
        when(officerMapper.toCanonical(trueProxyOfficer)).thenReturn(officer);

        List<Officer> actual = officerService.getOfficers(companyNumber,apiKey);

        assertEquals(expected,actual);

        verify(officerRepository,times(1)).findByCompany_CompanyNumber(companyNumber);
        verify(trueProxyClient,times(1)).getOfficer(request);
        verify(officerMapper,times(1)).toCanonical(trueProxyOfficer);

    }

    @Test
    public void getOfficers_FromDb_Test(){

        String companyNumber = "1234";
        String apiKey = "itbekey";

        Officer officer = new Officer();

        List<Officer> expected = Collections.singletonList(officer);

        OfficerEntity officerEntity = new OfficerEntity();

        OfficerSearchRequest request = OfficerSearchRequest.builder()
            .companyNumber(companyNumber)
            .apiKey(apiKey)
            .build();

        List<OfficerEntity> officerEntities = Collections.singletonList(officerEntity);


        when(officerRepository.findByCompany_CompanyNumber(companyNumber)).thenReturn(officerEntities);
        when(officerMapper.toCanonical(officerEntities)).thenReturn(expected);

        List<Officer> actual = officerService.getOfficers(companyNumber,apiKey);

        assertEquals(expected,actual);

        verify(officerMapper,times(1)).toCanonical(officerEntities);
        verify(officerRepository,times(1)).findByCompany_CompanyNumber(companyNumber);

        verify(trueProxyClient,times(0)).getOfficer(request);
        verify(officerMapper,times(0)).toCanonical(any(com.risk.companysearchdemo.client.trueproxy.model.Officer.class));

    }

    @Test
    public void getOfficers_ResignedOfficer_Test(){

        String companyNumber = "1234";
        String apiKey = "itbekey";

        List<Officer> expected = Collections.emptyList();

        com.risk.companysearchdemo.client.trueproxy.model.Officer trueProxyOfficer
            = com.risk.companysearchdemo.client.trueproxy.model.Officer.builder()
            .resignedOn("10-10-2010")
            .build();

        OfficerSearchRequest request = OfficerSearchRequest.builder()
            .companyNumber(companyNumber)
            .apiKey(apiKey)
            .build();

        OfficerSearchResponse response = OfficerSearchResponse.builder()
            .officers(Collections.singletonList(trueProxyOfficer))
            .build();

        when(officerRepository.findByCompany_CompanyNumber(companyNumber)).thenReturn(Collections.emptyList());
        when(trueProxyClient.getOfficer(request)).thenReturn(response);

        List<Officer> actual = officerService.getOfficers(companyNumber,apiKey);

        assertEquals(expected,actual);

        verify(officerRepository,times(1)).findByCompany_CompanyNumber(companyNumber);
        verify(trueProxyClient,times(1)).getOfficer(request);
        verify(officerMapper,times(0)).toCanonical(trueProxyOfficer);

    }

    @Test
    public void getOfficers_NoOfficerReturned_Test(){

        String companyNumber = "1234";
        String apiKey = "itbekey";

        List<Officer> expected = Collections.emptyList();

        OfficerSearchRequest request = OfficerSearchRequest.builder()
            .companyNumber(companyNumber)
            .apiKey(apiKey)
            .build();

        OfficerSearchResponse response = OfficerSearchResponse.builder()
            .build();

        when(officerRepository.findByCompany_CompanyNumber(companyNumber)).thenReturn(Collections.emptyList());
        when(trueProxyClient.getOfficer(request)).thenReturn(response);

        List<Officer> actual = officerService.getOfficers(companyNumber,apiKey);

        assertEquals(expected,actual);

        verify(officerRepository,times(1)).findByCompany_CompanyNumber(companyNumber);
        verify(trueProxyClient,times(1)).getOfficer(request);
        verify(officerMapper,times(0)).toCanonical(any(com.risk.companysearchdemo.client.trueproxy.model.Officer.class));

    }


}
