package com.risk.companysearchdemo.unit.mapper;

import com.risk.companysearchdemo.client.trueproxy.model.DateOfBirth;
import com.risk.companysearchdemo.client.trueproxy.model.OfficerLinks;
import com.risk.companysearchdemo.mapper.AddressMapper;
import com.risk.companysearchdemo.mapper.OfficerMapper;
import com.risk.companysearchdemo.model.dao.AddressEntity;
import com.risk.companysearchdemo.model.dao.OfficerEntity;
import com.risk.companysearchdemo.model.dto.Address;
import com.risk.companysearchdemo.model.dto.Officer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OfficerMapperTest {

    private OfficerMapper officerMapper;
    private AddressMapper addressMapper;

    @BeforeEach
    public void setup(){
        addressMapper = mock(AddressMapper.class);
        officerMapper = new OfficerMapper(addressMapper);

    }

    @Test
    public void toCanonical_fromTrueProxy_Test(){

        com.risk.companysearchdemo.client.trueproxy.model.Address inputAddress =
            com.risk.companysearchdemo.client.trueproxy.model.Address.builder()
                .addressLine1("PLACESTREET MCDRIVE")
                .build();

        Address expectedAddress = Address.builder()
            .addressLine1("PLACESTREET MCDRIVE")
            .build();

        com.risk.companysearchdemo.client.trueproxy.model.Officer input =
            com.risk.companysearchdemo.client.trueproxy.model.Officer.builder()
                .officerRole("bossman")
                .links(new OfficerLinks())
                .appointedOn("10-10-2010")
                .name("Jerry mcJerryson")
                .countryOfResidence("Republic of Jez")
                .nationality("Jerrisium")
                .address(inputAddress)
                .occupation("doing stuff and things")
                .dateOfBirth(new DateOfBirth())
                .resignedOn("11-10-2010")
                .build();


        Officer expected = Officer.builder()
            .officerRole("bossman")
            .appointedOn("10-10-2010")
            .name("Jerry mcJerryson")
            .address(expectedAddress)
            .build();

        when(addressMapper.toCanonical(inputAddress)).thenReturn(expectedAddress);

        Officer actual = officerMapper.toCanonical(input);

        assertEquals(expected,actual);

        verify(addressMapper,times(1)).toCanonical(inputAddress);

    }

    @Test
    public void toCanonical_fromTrueProxy_Null_Test(){

        com.risk.companysearchdemo.client.trueproxy.model.Officer input = null;

        Officer expected = null;

        Officer actual = officerMapper.toCanonical(input);

        assertEquals(expected,actual);

    }

    @Test
    public void toEntityList_fromCanonicalList_Null_Test(){

        List<Officer> input = null;

        List<OfficerEntity> expected = Collections.emptyList();

        List<OfficerEntity> actual = officerMapper.toEntity(input);

        assertEquals(expected,actual);

    }

    @Test
    public void toEntity_fromCanonical_Null_Test(){

        Officer input = null;

        OfficerEntity expected = null;

        OfficerEntity actual = officerMapper.toEntity(input);

        assertEquals(expected,actual);

    }

    @Test
    public void toEntityList_FromCanonicalList_test(){

        Address inputAddress = Address.builder()
            .addressLine1("PLACESTREET MCDRIVE")
            .build();

        AddressEntity expectedAddress = AddressEntity.builder()
            .addressLine1("PLACESTREET MCDRIVE")
            .build();

        List<Officer> input = Collections.singletonList(Officer.builder()
            .officerRole("guy")
            .appointedOn("10-10-2010")
            .name("guyperson")
            .address(inputAddress)
            .build());


        List<OfficerEntity> expected = Collections.singletonList(OfficerEntity.builder()
            .officerRole("guy")
            .appointedOn("10-10-2010")
            .name("guyperson")
            .address(expectedAddress)
            .build());

        when(addressMapper.toEntity(inputAddress)).thenReturn(expectedAddress);

        List<OfficerEntity> actual = officerMapper.toEntity(input);

        assertEquals(expected,actual);

        verify(addressMapper,times(1)).toEntity(inputAddress);

    }

    @Test
    public void toCanonicalList_fromEntityList_Null_Test(){

        List<OfficerEntity> input = null;

        List<Officer> expected = Collections.emptyList();

        List<Officer> actual = officerMapper.toCanonical(input);

        assertEquals(expected,actual);

    }

    @Test
    public void toCanonical_fromEntity_Null_Test(){

        OfficerEntity input = null;

        Officer expected = null;

        Officer actual = officerMapper.toCanonical(input);

        assertEquals(expected,actual);

    }

    @Test
    public void toCanonicalList_fromEntityList_test(){

        AddressEntity inputAddress = AddressEntity.builder()
            .addressLine1("PLACESTREET MCDRIVE")
            .build();

        Address expectedAddress  = Address.builder()
            .addressLine1("PLACESTREET MCDRIVE")
            .build();

        List<OfficerEntity> input  = Collections.singletonList(OfficerEntity.builder()
            .officerRole("guy")
            .appointedOn("10-10-2010")
            .name("guyperson")
            .address(inputAddress)
            .build());

        List<Officer> expected = Collections.singletonList(Officer.builder()
            .officerRole("guy")
            .appointedOn("10-10-2010")
            .name("guyperson")
            .address(expectedAddress)
            .build());


        when(addressMapper.toCanonical(inputAddress)).thenReturn(expectedAddress);

        List<Officer> actual = officerMapper.toCanonical(input);

        assertEquals(expected,actual);

        verify(addressMapper,times(1)).toCanonical(inputAddress);

    }


}
