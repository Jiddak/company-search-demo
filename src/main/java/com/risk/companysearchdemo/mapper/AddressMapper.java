package com.risk.companysearchdemo.mapper;

import com.risk.companysearchdemo.model.dao.AddressEntity;
import com.risk.companysearchdemo.model.dto.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {


    public Address toCanonical(com.risk.companysearchdemo.client.trueproxy.model.Address address){

        if(address == null)
            return null;

        return Address.builder()
            .addressLine1(address.getAddressLine1())
            .country(address.getCountry())
            .postalCode(address.getPostalCode())
            .premises(address.getPremises())
            .locality(address.getLocality())
            .build();

    }

    public AddressEntity toEntity(Address address){

        if(address == null)
            return null;

       return AddressEntity.builder()
            .addressLine1(address.getAddressLine1())
            .country(address.getCountry())
            .postalCode(address.getPostalCode())
            .premises(address.getPremises())
            .locality(address.getLocality())
            .build();
    }

    public Address toCanonical(AddressEntity address){

        if(address == null)
            return null;

        return Address.builder()
            .addressLine1(address.getAddressLine1())
            .country(address.getCountry())
            .postalCode(address.getPostalCode())
            .premises(address.getPremises())
            .locality(address.getLocality())
            .build();
    }


}
