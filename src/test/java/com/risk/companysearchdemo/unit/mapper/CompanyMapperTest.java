package com.risk.companysearchdemo.unit.mapper;

import com.risk.companysearchdemo.client.trueproxy.model.Links;
import com.risk.companysearchdemo.client.trueproxy.model.Matches;
import com.risk.companysearchdemo.mapper.AddressMapper;
import com.risk.companysearchdemo.mapper.CompanyMapper;
import com.risk.companysearchdemo.mapper.OfficerMapper;
import com.risk.companysearchdemo.model.dao.AddressEntity;
import com.risk.companysearchdemo.model.dao.CompanyEntity;
import com.risk.companysearchdemo.model.dao.OfficerEntity;
import com.risk.companysearchdemo.model.dto.Address;
import com.risk.companysearchdemo.model.dto.Company;
import com.risk.companysearchdemo.model.dto.Officer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CompanyMapperTest {

    private CompanyMapper companyMapper;
    private OfficerMapper officerMapper;
    private AddressMapper addressMapper;


    @BeforeEach
    public void setup(){
        officerMapper = mock(OfficerMapper.class);
        addressMapper = mock(AddressMapper.class);
        companyMapper = new CompanyMapper(officerMapper,addressMapper);

    }

    @Test
    public void toCanonical_FromTrueProxyModel_Null_Test(){

        com.risk.companysearchdemo.client.trueproxy.model.Company input = null;

        Company expected = null;

        Company actual = companyMapper.toCanonical(input,Collections.emptyList());

        assertEquals(expected,actual);

        verify(addressMapper,times(0))
            .toCanonical(any(com.risk.companysearchdemo.client.trueproxy.model.Address.class));

    }


    @Test
    public void toCanonical_FromTrueProxyModel_Test(){

        List<Officer> officers = Collections.singletonList(
            Officer.builder()
                .name("GUYMANPERSON")
                .build()
        );

        com.risk.companysearchdemo.client.trueproxy.model.Address inputAddress =
            com.risk.companysearchdemo.client.trueproxy.model.Address.builder()
            .addressLine1("PLACESTREET MCDRIVE")
            .build();

        Address expectedAddress = Address.builder()
            .addressLine1("PLACESTREET MCDRIVE")
            .build();

        com.risk.companysearchdemo.client.trueproxy.model.Company input =
            com.risk.companysearchdemo.client.trueproxy.model.Company.builder()
                .companyNumber("1234")
                .addressSnippet("12")
                .snippet("sure")
                .dateOfCessation("10-11-1991")
                .links(new Links())
                .description("is a company")
                .kind("company")
                .matches(new Matches())
                .descriptionIdentifier(Collections.emptyList())
                .dateOfCreation("10-10-1991")
                .companyStatus("made up")
                .title("THE COMPANY")
                .address(inputAddress)
                .companyType("Big one")
                .build();

        Company expected = Company.builder()
            .companyNumber("1234")
            .companyStatus("made up")
            .companyType("Big one")
            .title("THE COMPANY")
            .dateOfCreation("10-10-1991")
            .address(expectedAddress)
            .officers(officers)
            .build();

        when(addressMapper.toCanonical(inputAddress)).thenReturn(expectedAddress);

        Company actual = companyMapper.toCanonical(input,officers);

        assertEquals(expected,actual);

        verify(addressMapper,times(1)).toCanonical(inputAddress);

    }

    @Test
    public void toEntityList_FromCanonicalList_Test(){
        Address inputAddress = Address.builder()
            .addressLine1("PLACESTREET MCDRIVE")
            .build();

        AddressEntity expectedAddress = AddressEntity.builder()
            .addressLine1("PLACESTREET MCDRIVE")
            .build();

        List<Officer> inputOfficers = Collections.singletonList(
            Officer.builder()
                .name("GUYMANPERSON")
                .build()
        );

        List<OfficerEntity> expectedOfficers = Collections.singletonList(
            OfficerEntity.builder()
                .name("GUYMANPERSON")
                .build()
        );


        List<Company> input = Collections.singletonList(Company.builder()
            .companyNumber("1234")
            .companyStatus("made up")
            .companyType("Big one")
            .title("THE COMPANY")
            .dateOfCreation("10-10-1991")
            .address(inputAddress)
            .officers(inputOfficers)
            .build());


        List<CompanyEntity> expected = Collections.singletonList(CompanyEntity.builder()
            .companyNumber("1234")
            .companyStatus("made up")
            .companyType("Big one")
            .title("THE COMPANY")
            .dateOfCreation("10-10-1991")
            .address(expectedAddress)
            .officers(expectedOfficers)
            .build());

        when(addressMapper.toEntity(inputAddress)).thenReturn(expectedAddress);
        when(officerMapper.toEntity(inputOfficers)).thenReturn(expectedOfficers);

        List<CompanyEntity> actual = companyMapper.toEntity(input);

        assertEquals(expected,actual);

        verify(addressMapper,times(1)).toEntity(inputAddress);
        verify(officerMapper,times(1)).toEntity(inputOfficers);


    }

    @Test
    public void toEntityList_FromCanonicalList_Null_Test(){


        List<Company> input = null;

        List<CompanyEntity> expected = Collections.emptyList();

        List<CompanyEntity> actual = companyMapper.toEntity(input);

        assertEquals(expected,actual);

        verify(addressMapper,times(0)).toEntity(any());
        verify(officerMapper,times(0)).toEntity(anyList());


    }

    @Test
    public void toEntityList_FromCanonicalList_Empty_Test(){


        List<Company> input = Collections.emptyList();

        List<CompanyEntity> expected = Collections.emptyList();

        List<CompanyEntity> actual = companyMapper.toEntity(input);

        assertEquals(expected,actual);

        verify(addressMapper,times(0)).toEntity(any());
        verify(officerMapper,times(0)).toEntity(anyList());


    }

    @Test
    public void toEntity_FromCanonicalModel_Null_Test(){

        Company input = null;

        CompanyEntity expected = null;

        CompanyEntity actual = companyMapper.toEntity(input);

        assertEquals(expected,actual);

        verify(addressMapper,times(0)).toEntity(any(Address.class));
        verify(officerMapper,times(0)).toEntity(anyList());

    }

    @Test
    public void toEntity_FromCanonicalModel_Test(){

        Address inputAddress = Address.builder()
            .addressLine1("PLACESTREET MCDRIVE")
            .build();

        AddressEntity expectedAddress = AddressEntity.builder()
            .addressLine1("PLACESTREET MCDRIVE")
            .build();

        List<Officer> inputOfficers = Collections.singletonList(
            Officer.builder()
                .name("GUYMANPERSON")
                .build()
        );

        List<OfficerEntity> expectedOfficers = Collections.singletonList(
            OfficerEntity.builder()
                .name("GUYMANPERSON")
                .build()
        );


        Company input = Company.builder()
            .companyNumber("1234")
            .companyStatus("made up")
            .companyType("Big one")
            .title("THE COMPANY")
            .dateOfCreation("10-10-1991")
            .address(inputAddress)
            .officers(inputOfficers)
            .build();


        CompanyEntity expected = CompanyEntity.builder()
            .companyNumber("1234")
            .companyStatus("made up")
            .companyType("Big one")
            .title("THE COMPANY")
            .dateOfCreation("10-10-1991")
            .address(expectedAddress)
            .officers(expectedOfficers)
            .build();

        when(addressMapper.toEntity(inputAddress)).thenReturn(expectedAddress);
        when(officerMapper.toEntity(inputOfficers)).thenReturn(expectedOfficers);

        CompanyEntity actual = companyMapper.toEntity(input);

        assertEquals(expected,actual);

        verify(addressMapper,times(1)).toEntity(inputAddress);
        verify(officerMapper,times(1)).toEntity(inputOfficers);

    }

    @Test
    public void toCanonical_fromEntityModel_Null_Test(){

        CompanyEntity input = null;

        Company expected =null;

        Company actual = companyMapper.toCanonical(input);

        assertEquals(expected,actual);

        verify(addressMapper,times(0)).toCanonical(any(AddressEntity.class));
        verify(officerMapper,times(0)).toCanonical(anyList());

    }

    @Test
    public void toCanonical_fromEntityModel_Test(){

        AddressEntity inputAddress = AddressEntity.builder()
            .addressLine1("PLACESTREET MCDRIVE")
            .build();

        Address expectedAddress  = Address.builder()
            .addressLine1("PLACESTREET MCDRIVE")
            .build();

        List<OfficerEntity> inputOfficers = Collections.singletonList(
            OfficerEntity.builder()
                .name("GUYMANPERSON")
                .build()
        );

        List<Officer> expectedOfficers  = Collections.singletonList(
            Officer.builder()
                .name("GUYMANPERSON")
                .build()
        );

        CompanyEntity input = CompanyEntity.builder()
            .companyNumber("1234")
            .companyStatus("made up")
            .companyType("Big one")
            .title("THE COMPANY")
            .dateOfCreation("10-10-1991")
            .address(inputAddress)
            .officers(inputOfficers)
            .build();


        Company expected = Company.builder()
            .companyNumber("1234")
            .companyStatus("made up")
            .companyType("Big one")
            .title("THE COMPANY")
            .dateOfCreation("10-10-1991")
            .address(expectedAddress)
            .officers(expectedOfficers)
            .build();

        when(addressMapper.toCanonical(inputAddress)).thenReturn(expectedAddress);
        when(officerMapper.toCanonical(inputOfficers)).thenReturn(expectedOfficers);

        Company actual = companyMapper.toCanonical(input);

        assertEquals(expected,actual);

        verify(addressMapper,times(1)).toCanonical(inputAddress);
        verify(officerMapper,times(1)).toCanonical(inputOfficers);

    }

}
