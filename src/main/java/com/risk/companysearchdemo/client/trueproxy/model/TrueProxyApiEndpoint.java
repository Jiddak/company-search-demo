package com.risk.companysearchdemo.client.trueproxy.model;

public enum TrueProxyApiEndpoint {

    COMPANY_SEARCH("/Search"),
    OFFICER_SEARCH("/Officers"),
    ;

    public final String uri;

    private TrueProxyApiEndpoint(String uri) {
        this.uri = uri;
    }
}
