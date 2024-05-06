package com.risk.companysearchdemo.mapper;

import com.risk.companysearchdemo.model.dao.CompanyEntity;
import com.risk.companysearchdemo.model.dto.Company;
import com.risk.companysearchdemo.model.dto.Officer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CompanyMapper {

    private final OfficerMapper officerMapper;
    private final AddressMapper addressMapper;

    public Company toCanonical(com.risk.companysearchdemo.client.trueproxy.model.Company company, List<Officer> officers){

        if(company == null)
            return null;

        return Company.builder()
            .companyNumber(company.getCompanyNumber())
            .companyStatus(company.getCompanyStatus())
            .companyType(company.getCompanyType())
            .title(company.getTitle())
            .dateOfCreation(company.getDateOfCreation())
            .address(addressMapper.toCanonical(company.getAddress()))
            .officers(officers)
            .build();

    }

    public List<CompanyEntity> toEntity(List<Company> companies){

        if(companies == null)
            return Collections.emptyList();

        return companies.stream()
            .map(this::toEntity)
            .toList();
    }

    public CompanyEntity toEntity(Company company){

        if(company == null)
            return null;

        CompanyEntity companyEntity = CompanyEntity.builder()
            .companyNumber(company.getCompanyNumber())
            .companyStatus(company.getCompanyStatus())
            .companyType(company.getCompanyType())
            .title(company.getTitle())
            .dateOfCreation(company.getDateOfCreation())
            .address(addressMapper.toEntity(company.getAddress()))
            .officers(officerMapper.toEntity(company.getOfficers()))
            .build();

        companyEntity.getOfficers()
            .forEach(officer -> officer.setCompany(companyEntity));

        return companyEntity;

    }

    public Company toCanonical(CompanyEntity company){

        if(company == null)
            return null;

        return Company.builder()
            .companyNumber(company.getCompanyNumber())
            .companyStatus(company.getCompanyStatus())
            .companyType(company.getCompanyType())
            .title(company.getTitle())
            .dateOfCreation(company.getDateOfCreation())
            .address(addressMapper.toCanonical(company.getAddress()))
            .officers(officerMapper.toCanonical(company.getOfficers()))
            .build();
    }


}
