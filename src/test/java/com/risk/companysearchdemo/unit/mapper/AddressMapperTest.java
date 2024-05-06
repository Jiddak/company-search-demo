package com.risk.companysearchdemo.unit.mapper;

import com.risk.companysearchdemo.mapper.AddressMapper;
import com.risk.companysearchdemo.model.dao.AddressEntity;
import com.risk.companysearchdemo.model.dto.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressMapperTest {

    private AddressMapper addressMapper;


    @BeforeEach
    public void setup(){
        addressMapper = new AddressMapper();
    }

    @Test
    public void toCanonical_FromTrueProxyModel_Test(){
        com.risk.companysearchdemo.client.trueproxy.model.Address input =
            com.risk.companysearchdemo.client.trueproxy.model.Address.builder()
                .premises("prem")
                .addressLine1("street")
                .addressLine2("place")
                .postalCode("M35OPL")
                .region("place")
                .country("UK")
                .locality("local")
                .build();

        Address expected = Address.builder()
            .premises("prem")
            .addressLine1("street")
            .postalCode("M35OPL")
            .locality("local")
            .country("UK")
            .build();

        Address actual = addressMapper.toCanonical(input);

        assertEquals(expected,actual);


    }

    @Test
    public void toCanonical_FromTrueProxyModel_Null_Test(){
        com.risk.companysearchdemo.client.trueproxy.model.Address input = null;

        Address expected = null;

        Address actual = addressMapper.toCanonical(input);

        assertEquals(expected,actual);

    }

    @Test
    public void  toEntity_FromCanonicalModel_Test(){
        Address input = Address.builder()
            .premises("prem")
            .addressLine1("street")
            .postalCode("M35OPL")
            .locality("local")
            .country("UK")
            .build();

        AddressEntity expected = AddressEntity.builder()
            .premises("prem")
            .addressLine1("street")
            .postalCode("M35OPL")
            .locality("local")
            .country("UK")
            .build();

        AddressEntity actual = addressMapper.toEntity(input);

        assertEquals(expected,actual);

    }

    @Test
    public void  toEntity_FromCanonicalModel_Null_Test(){
        Address input = null;

        AddressEntity expected = null;

        AddressEntity actual = addressMapper.toEntity(input);

        assertEquals(expected,actual);

    }

    @Test
    public void  toCanonical_FromEntityModel_Test(){


        AddressEntity input = AddressEntity.builder()
            .premises("prem")
            .addressLine1("street")
            .postalCode("M35OPL")
            .locality("local")
            .country("UK")
            .build();

        Address expected = Address.builder()
            .premises("prem")
            .addressLine1("street")
            .postalCode("M35OPL")
            .locality("local")
            .country("UK")
            .build();

        Address actual = addressMapper.toCanonical(input);

        assertEquals(expected,actual);


    }

    @Test
    public void  toCanonical_FromEntityModel_Null_Test(){


        AddressEntity input = null;

        Address expected = null;

        Address actual = addressMapper.toCanonical(input);

        assertEquals(expected,actual);


    }



}
