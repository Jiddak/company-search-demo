package com.risk.companysearchdemo.client.trueproxy.model.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.risk.companysearchdemo.client.trueproxy.model.Links;
import com.risk.companysearchdemo.client.trueproxy.model.Officer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfficerSearchResponse {

    @JsonProperty("etag")
    private String etag;
    @JsonProperty("links")
    private Links links;
    @JsonProperty("kind")
    private String kind;
    @JsonProperty("items_per_page")
    private Integer itemsPerPage;
    @JsonProperty("items")
    private List<Officer> officers;

}
