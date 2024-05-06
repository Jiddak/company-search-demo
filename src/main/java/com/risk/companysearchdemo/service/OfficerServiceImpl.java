package com.risk.companysearchdemo.service;

import com.risk.companysearchdemo.client.trueproxy.TrueProxyClient;
import com.risk.companysearchdemo.client.trueproxy.model.request.OfficerSearchRequest;
import com.risk.companysearchdemo.client.trueproxy.model.response.OfficerSearchResponse;
import com.risk.companysearchdemo.mapper.OfficerMapper;
import com.risk.companysearchdemo.model.dao.OfficerEntity;
import com.risk.companysearchdemo.model.dto.Officer;
import com.risk.companysearchdemo.repository.OfficerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OfficerServiceImpl implements OfficerService {

    private final TrueProxyClient trueProxyClient;
    private final OfficerMapper officerMapper;
    private final OfficerRepository officerRepository;

    public List<Officer> getOfficers(String companyNumber, String apiKey){

            List<OfficerEntity> officerEntities = officerRepository.findByCompany_CompanyNumber(companyNumber);

            if(officerEntities.isEmpty())
                return getOfficersFromRemote(companyNumber,apiKey);

            return officerMapper.toCanonical(officerEntities);

    }

    private List<Officer> getOfficersFromRemote(String companyNumber,String apiKey){
        OfficerSearchRequest searchRequest = OfficerSearchRequest.builder()
            .companyNumber(companyNumber)
            .apiKey(apiKey)
            .build();

        OfficerSearchResponse officerSearchResponse = trueProxyClient.getOfficer(searchRequest);


        if(officerSearchResponse.getOfficers() == null) {
            log.warn("No officers found for companyNumber: {}",companyNumber);
            return Collections.emptyList();
        }

        return officerSearchResponse.getOfficers()
            .stream()
            .filter(officer -> officer.getResignedOn() == null)
            .map(officerMapper::toCanonical)
            .toList();
    }



}
