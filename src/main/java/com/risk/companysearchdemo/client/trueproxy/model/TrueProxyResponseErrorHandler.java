package com.risk.companysearchdemo.client.trueproxy.model;


import com.risk.companysearchdemo.client.trueproxy.model.exception.TrueProxyClientException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class TrueProxyResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {

       return switch (response.getStatusCode()) {
           case HttpStatusCode h when h.is2xxSuccessful() ->  false;
           default -> true;
       };
       //IDE BUG: thinks this can only return true but it can't

    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

        throw new TrueProxyClientException("Request failed with code: '"+ response.getStatusCode() + "'");

    }
}
