package com.risk.companysearchdemo.client.trueproxy;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.risk.companysearchdemo.client.trueproxy.model.TrueProxyApiEndpoint;
import com.risk.companysearchdemo.client.trueproxy.model.TrueProxyResponseErrorHandler;
import com.risk.companysearchdemo.client.trueproxy.model.exception.TrueProxyClientException;
import com.risk.companysearchdemo.client.trueproxy.model.request.CompanySearchRequest;
import com.risk.companysearchdemo.client.trueproxy.model.request.OfficerSearchRequest;
import com.risk.companysearchdemo.client.trueproxy.model.response.CompanySearchResponse;
import com.risk.companysearchdemo.client.trueproxy.model.response.OfficerSearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Slf4j
public class TrueProxyClient {

    private final RestClient restClient;
    private final String baseUrl;

    private final Cache<CompanySearchRequest, CompanySearchResponse> companyCache;
    private final Cache<OfficerSearchRequest, OfficerSearchResponse> officerCache;
    private final boolean useCache;

    private final ResponseErrorHandler responseErrorHandler;

    public TrueProxyClient(String baseUrl) {
        this(
            TrueProxyClientConfig.builder()
                .baseUrl(baseUrl)
                .build()
        );

    }

    public TrueProxyClient(TrueProxyClientConfig config) {
        this.baseUrl = config.getBaseUrl();
        this.responseErrorHandler = new TrueProxyResponseErrorHandler();
        restClient = RestClient.builder()
            .baseUrl(baseUrl)
            .messageConverters(converters -> converters.add(new MappingJackson2HttpMessageConverter(config.getMapper())))
            .build();

        this.useCache = config.getCache() != null && config.getCache().isEnabled();

        if (useCache) {
            companyCache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMillis(config.getCache().getCacheDurationMillis()))
                .maximumSize(config.getCache().getCacheSize())
                .build();

            officerCache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMillis(config.getCache().getCacheDurationMillis()))
                .maximumSize(config.getCache().getCacheSize())
                .build();

        }
        else {
            officerCache = null;
            companyCache = null;
        }


    }

    public OfficerSearchResponse getOfficer(OfficerSearchRequest request) {

        if(request.getCompanyNumber() == null || request.getCompanyNumber().isEmpty())
            throw new TrueProxyClientException("No company number provided");

        if (useCache)
            return officerCache.get(
                request,
                k -> requestOfficer(request)
            );

        return requestOfficer(request);

    }

    public OfficerSearchResponse requestOfficer(OfficerSearchRequest request) {

        try {

            log.debug("Sending request to TrueProxy service, Host: {}, Endpoint: '{}'", baseUrl, TrueProxyApiEndpoint.OFFICER_SEARCH.uri + "?CompanyNumber=" + request.getCompanyNumber());

            OfficerSearchResponse response = restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                    .path(TrueProxyApiEndpoint.OFFICER_SEARCH.uri)
                    .queryParam("CompanyNumber",request.getCompanyNumber())
                    .build()
                )
                .header("x-api-key",request.getApiKey())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(responseErrorHandler)
                .body(OfficerSearchResponse.class);

            log.debug("Request to TrueProxy service successful,Host: {}, Endpoint: '{}',Response: '{}'", baseUrl, TrueProxyApiEndpoint.OFFICER_SEARCH.uri + "?CompanyNumber=" + request.getCompanyNumber(), response);
            return response;

        } catch (Exception e){
            log.error("Request to TrueProxy service failed,Host: {}, Endpoint: '{}' ", baseUrl, TrueProxyApiEndpoint.OFFICER_SEARCH.uri + "?CompanyNumber=" + request.getCompanyNumber());
            throw new TrueProxyClientException("Request failure",e);
        }

    }


    public CompanySearchResponse getCompany(CompanySearchRequest request){

        if(request.getSearchTerm() == null || request.getSearchTerm() .isEmpty())
            throw new TrueProxyClientException("No search term provided");

        if (useCache)
            return companyCache.get(
                request,
                k -> requestCompany(request)
            );

        return requestCompany(request);

    }

    public CompanySearchResponse requestCompany(CompanySearchRequest request) {

        try {

            log.debug("Sending request to TrueProxy service, Host: {}, Endpoint: '{}'", baseUrl, TrueProxyApiEndpoint.COMPANY_SEARCH.uri + "?Query=" + request.getSearchTerm());

            CompanySearchResponse response = restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                    .path(TrueProxyApiEndpoint.COMPANY_SEARCH.uri)
                    .queryParam("Query",request.getSearchTerm())
                    .build()
                )
                .header("x-api-key",request.getApiKey())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(responseErrorHandler)
                .body(CompanySearchResponse.class);

            log.debug("Request to TrueProxy service successful,Host: {}, Endpoint: '{}',Response: '{}'", baseUrl, TrueProxyApiEndpoint.COMPANY_SEARCH.uri + "?Query=" + request.getSearchTerm(), response);
            return response;

        } catch (Exception e){
            log.error("Request to TrueProxy service failed,Host: {}, Endpoint: '{}' ", baseUrl, TrueProxyApiEndpoint.COMPANY_SEARCH.uri + "?Query=" + request.getSearchTerm());
            throw new TrueProxyClientException("Request failure",e);
        }

    }

}
