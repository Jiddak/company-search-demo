package com.risk.companysearchdemo.mapper;

import com.risk.companysearchdemo.model.dao.OfficerEntity;
import com.risk.companysearchdemo.model.dto.Officer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OfficerMapper {

    private final AddressMapper addressMapper;

    public Officer toCanonical(com.risk.companysearchdemo.client.trueproxy.model.Officer officer){

        if(officer == null)
            return null;

        return Officer.builder()
            .officerRole(officer.getOfficerRole())
            .appointedOn(officer.getAppointedOn())
            .name(officer.getName())
            .address(addressMapper.toCanonical(officer.getAddress()))
            .build();

    }


    public List<OfficerEntity> toEntity(List<Officer> officers){

        if(officers == null)
            return Collections.emptyList();

        return officers.stream()
            .map(this::toEntity)
            .toList();
    }

    public OfficerEntity toEntity(Officer officer){

        if(officer == null)
            return null;

        return OfficerEntity.builder()
            .officerRole(officer.getOfficerRole())
            .appointedOn(officer.getAppointedOn())
            .name(officer.getName())
            .address(addressMapper.toEntity(officer.getAddress()))
            .build();
    }

    public List<Officer> toCanonical(List<OfficerEntity> officers){

        if(officers == null)
            return Collections.emptyList();

        return officers.stream()
            .map(this::toCanonical)
            .toList();
    }

    public Officer toCanonical(OfficerEntity officer){

        if(officer == null)
            return null;

        return Officer.builder()
            .officerRole(officer.getOfficerRole())
            .appointedOn(officer.getAppointedOn())
            .name(officer.getName())
            .address(addressMapper.toCanonical(officer.getAddress()))
            .build();
    }

}
