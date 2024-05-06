package com.risk.companysearchdemo.unit.controller;

import com.risk.companysearchdemo.controller.ApiController;
import com.risk.companysearchdemo.model.dto.request.CompaniesRequest;
import com.risk.companysearchdemo.model.dto.response.CompaniesResponse;
import com.risk.companysearchdemo.service.CompanyService;
import com.risk.companysearchdemo.util.TestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiControllerTest {

    MockMvc mockMvc;
    private CompanyService companyService;

    private static MappingJackson2HttpMessageConverter messageConverter;

    @BeforeAll
    public static void init(){

        messageConverter = new
            MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(TestUtils.getMapper());

    }

    @BeforeEach
    void setup() {
        companyService = mock(CompanyService.class);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ApiController(companyService))
            .setMessageConverters(messageConverter)
            .build();
    }

    @Test
    void postCompanies_with_apiKey_noQuery_200() throws Exception {

        CompaniesRequest request = new CompaniesRequest();
        request.setCompanyName("zecomp");
        CompaniesResponse response = new CompaniesResponse();
        response.setTotalResults(0);
        String apiKey = "elllate";
        boolean active = false;



        when(companyService.getCompanies(request,apiKey,active)).thenReturn(response);

        mockMvc.perform(post("/api/companies")
            .header("x-api-key",apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.sneakyWriteAsString(request)))
            .andExpectAll(
                status().isOk(),
                content().contentType("application/json"),
                content().string(TestUtils.sneakyWriteAsString(response))
            );

        verify(companyService,times(1)).getCompanies(request,apiKey,active);

    }

    @Test
    void postCompanies_with_apiKey_activeTrue_200() throws Exception {

        CompaniesRequest request = new CompaniesRequest();
        request.setCompanyName("zecomp");
        CompaniesResponse response = new CompaniesResponse();
        response.setTotalResults(0);
        String apiKey = "elllate";
        boolean active = true;



        when(companyService.getCompanies(request,apiKey,active)).thenReturn(response);

        mockMvc.perform(post("/api/companies?remove-inactive={remove-inactive}",active)
                .header("x-api-key",apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.sneakyWriteAsString(request)))
            .andExpectAll(
                status().isOk(),
                content().contentType("application/json"),
                content().string(TestUtils.sneakyWriteAsString(response))
            );

        verify(companyService,times(1)).getCompanies(request,apiKey,active);

    }

    @Test
    void postCompanies_with_apiKey_noBody_400() throws Exception {

        CompaniesRequest request = new CompaniesRequest();
        request.setCompanyName("zecomp");
        CompaniesResponse response = new CompaniesResponse();
        response.setTotalResults(0);
        String apiKey = "elllate";
        boolean active = true;

        mockMvc.perform(post("/api/companies?active={active}",active)
                .header("x-api-key",apiKey)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                status().is(400)
            );

        verify(companyService,times(0)).getCompanies(request,apiKey,active);

    }

    @Test
    void postCompanies_noApiKey_400() throws Exception {

        CompaniesRequest request = new CompaniesRequest();
        request.setCompanyName("zecomp");
        CompaniesResponse response = new CompaniesResponse();
        response.setTotalResults(0);
        String apiKey = "elllate";
        boolean active = true;

        mockMvc.perform(post("/api/companies?active={active}",active)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtils.sneakyWriteAsString(request))
            )
            .andExpectAll(
                status().is(400)
            );

        verify(companyService,times(0)).getCompanies(request,apiKey,active);

    }

    @Test
    void getCompanies_with_apiKey_404() throws Exception {

        CompaniesRequest request = new CompaniesRequest();
        request.setCompanyName("zecomp");
        CompaniesResponse response = new CompaniesResponse();
        response.setTotalResults(0);
        String apiKey = "elllate";
        boolean active = false;

        mockMvc.perform(get("/api/companies")
                .header("x-api-key",apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.sneakyWriteAsString(request)))
            .andExpectAll(
                status().is(405)
            );

        verify(companyService,times(0)).getCompanies(request,apiKey,active);

    }

}
