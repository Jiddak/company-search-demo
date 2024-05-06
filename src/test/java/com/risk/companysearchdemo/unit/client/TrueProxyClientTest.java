package com.risk.companysearchdemo.unit.client;

import com.risk.companysearchdemo.client.trueproxy.CacheConfig;
import com.risk.companysearchdemo.client.trueproxy.TrueProxyClient;
import com.risk.companysearchdemo.client.trueproxy.TrueProxyClientConfig;
import com.risk.companysearchdemo.client.trueproxy.model.Company;
import com.risk.companysearchdemo.client.trueproxy.model.Officer;
import com.risk.companysearchdemo.client.trueproxy.model.exception.TrueProxyClientException;
import com.risk.companysearchdemo.client.trueproxy.model.request.CompanySearchRequest;
import com.risk.companysearchdemo.client.trueproxy.model.request.OfficerSearchRequest;
import com.risk.companysearchdemo.client.trueproxy.model.response.CompanySearchResponse;
import com.risk.companysearchdemo.client.trueproxy.model.response.OfficerSearchResponse;
import com.risk.companysearchdemo.util.MockDispatcher;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TrueProxyClientTest {

    private TrueProxyClient trueProxyClient;
    private static MockWebServer mockBackEnd;
    private static MockDispatcher mockDispatcher;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockDispatcher = new MockDispatcher();
        mockBackEnd.setDispatcher(mockDispatcher);
        mockBackEnd.start();
    }


    @AfterAll
    static void shutdown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    void initialize() {

        trueProxyClient = new TrueProxyClient(String.format("http://localhost:%s", mockBackEnd.getPort()));

    }
    @AfterEach
    void cleanUp() {
        mockDispatcher.reset();
    }

    @Test
    public void getOfficer_200Test(){

        OfficerSearchRequest officerSearchRequest = OfficerSearchRequest.builder()
            .apiKey("elllave")
            .companyNumber("1337")
            .build();

        OfficerSearchResponse expected = OfficerSearchResponse.builder()
            .officers(Collections.singletonList(
                Officer.builder()
                    .name("elnombre")
                    .build()
            ))
            .build();

        mockDispatcher.addResponse(expected,200);

        OfficerSearchResponse response = trueProxyClient.getOfficer(officerSearchRequest);

        assertEquals(expected,response);


    }

    @Test
    public void getOfficer_200_CacheEnabled_Test(){

        TrueProxyClient cacheableClient =
            new TrueProxyClient(
                TrueProxyClientConfig.builder()
                    .baseUrl(String.format("http://localhost:%s", mockBackEnd.getPort()))
                    .cache(CacheConfig.builder()
                        .enabled(true)
                        .build())
                    .build()
                );

        OfficerSearchRequest officerSearchRequest = OfficerSearchRequest.builder()
            .apiKey("elllave")
            .companyNumber("1337")
            .build();

        OfficerSearchResponse expected = OfficerSearchResponse.builder()
            .officers(Collections.singletonList(
                Officer.builder()
                    .name("elnombre")
                    .build()
            ))
            .build();

        mockDispatcher.addResponse(expected,200);
        mockDispatcher.addResponse(expected,500);

        OfficerSearchResponse response1 = cacheableClient.getOfficer(officerSearchRequest);
        OfficerSearchResponse response2 = cacheableClient.getOfficer(officerSearchRequest);

        assertEquals(expected,response1);
        assertEquals(expected,response2);
        assertEquals(1,mockDispatcher.getDispatchCount());


    }

    @Test
    public void getOfficer_200_EmptyRequestTest(){

        OfficerSearchRequest officerSearchRequest = OfficerSearchRequest.builder()
            .build();

        OfficerSearchResponse expected = OfficerSearchResponse.builder()
            .officers(Collections.singletonList(
                Officer.builder()
                    .name("elnombre")
                    .build()
            ))
            .build();

        mockDispatcher.addResponse(expected,200);

        assertThrows(TrueProxyClientException.class, () -> trueProxyClient.getOfficer(officerSearchRequest));


    }

    @Test
    public void getOfficer_Non200_ResponseTest(){

        OfficerSearchRequest officerSearchRequest = OfficerSearchRequest.builder()
            .apiKey("elllave")
            .companyNumber("1337")
            .build();

        OfficerSearchResponse expected = OfficerSearchResponse.builder()
            .officers(Collections.singletonList(
                Officer.builder()
                    .name("elnombre")
                    .build()
            ))
            .build();

        mockDispatcher.addResponse(expected,404);

        assertThrows(TrueProxyClientException.class, () -> trueProxyClient.getOfficer(officerSearchRequest));


    }

    @Test
    public void getCompany_200_CompanyNumberTest(){

        CompanySearchRequest request = CompanySearchRequest.builder()
            .apiKey("elllave")
            .searchTerm("1337")
            .build();

        CompanySearchResponse expected = CompanySearchResponse.builder()
            .companies(Collections.singletonList(Company.builder()
                .companyNumber("1337")
                .build()))
            .build();

        mockDispatcher.addResponse(expected,200);

        CompanySearchResponse response = trueProxyClient.getCompany(request);

        assertEquals(expected,response);


    }

    @Test
    public void getCompany_200_CacheEnabled_Test(){

        TrueProxyClient cacheableClient =
            new TrueProxyClient(
                TrueProxyClientConfig.builder()
                    .baseUrl(String.format("http://localhost:%s", mockBackEnd.getPort()))
                    .cache(CacheConfig.builder()
                        .enabled(true)
                        .build())
                    .build()
            );

        CompanySearchRequest request = CompanySearchRequest.builder()
            .apiKey("elllave")
            .searchTerm("1337")
            .build();

        CompanySearchResponse expected = CompanySearchResponse.builder()
            .companies(Collections.singletonList(Company.builder()
                .companyNumber("1337")
                .build()))
            .build();

        mockDispatcher.addResponse(expected,200);
        mockDispatcher.addResponse(expected,500);

        CompanySearchResponse response1 = cacheableClient.getCompany(request);
        CompanySearchResponse response2 = cacheableClient.getCompany(request);

        assertEquals(expected,response1);
        assertEquals(expected,response2);
        assertEquals(1,mockDispatcher.getDispatchCount());
        
    }

    @Test
    public void getCompany_200_EmptyRequestTest(){

        CompanySearchRequest request = CompanySearchRequest.builder()
            .build();

        CompanySearchResponse expected = CompanySearchResponse.builder()
            .companies(Collections.singletonList(Company.builder()
                .companyNumber("1337")
                .build()))
            .build();

        mockDispatcher.addResponse(expected,200);

        assertThrows(TrueProxyClientException.class, () -> trueProxyClient.getCompany(request));


    }

    @Test
    public void getCompany_Non200_ResponseTest(){

        CompanySearchRequest request = CompanySearchRequest.builder()
            .searchTerm("yuppers")
            .apiKey("yepps")
            .build();

        CompanySearchResponse expected = CompanySearchResponse.builder()
            .companies(Collections.singletonList(Company.builder()
                .companyNumber("1337")
                .build()))
            .build();

        mockDispatcher.addResponse(expected,404);

        assertThrows(TrueProxyClientException.class, () -> trueProxyClient.getCompany(request));


    }




}
