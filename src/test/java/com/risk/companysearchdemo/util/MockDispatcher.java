package com.risk.companysearchdemo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.SneakyThrows;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class MockDispatcher extends Dispatcher {

    private final Queue<MockResponse> responses;
    private final ObjectMapper mapper;

    private final AtomicInteger dispatchedCount;

    public MockDispatcher() {
        mapper = new ObjectMapper();
        responses = new LinkedList<>();
        dispatchedCount = new AtomicInteger(0);
    }

    @NotNull
    @Override
    @SneakyThrows
    public MockResponse dispatch(@NotNull RecordedRequest recordedRequest) {
        MockResponse response = responses.poll();
        dispatchedCount.incrementAndGet();
        return
            response == null ?
            new MockResponse().setResponseCode(500)
            : response;
    }

    public void reset() {
        responses.clear();
        dispatchedCount.set(0);
    }

    public int getDispatchCount() {
        return dispatchedCount.get();
    }

    public void addResponse(MockResponse response) {
        responses.add(response);
    }

    public void addResponse(Object response,Integer status) {
        try {

            MockResponse mockResponse = new MockResponse()
                .setResponseCode(status)
                .addHeader("Content-Type", "application/json");

            if(response != null) {

                if(response instanceof String stringResponse)
                    mockResponse.setBody(stringResponse);
                else
                    mockResponse.setBody(mapper.writeValueAsString(response));
            }

            addResponse(mockResponse);
        }
        catch (Exception e){
            throw new RuntimeException("Unable to create response from response: " + response + " status: " + status );
        }
    }


}
