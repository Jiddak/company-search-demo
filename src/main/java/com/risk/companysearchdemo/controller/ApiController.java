package com.risk.companysearchdemo.controller;


import com.risk.companysearchdemo.model.dto.request.CompaniesRequest;
import com.risk.companysearchdemo.model.dto.response.CompaniesResponse;
import com.risk.companysearchdemo.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api")
@Slf4j
public class ApiController {

    private final CompanyService companyService;

    @RequestMapping(value="companies", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CompaniesResponse> companies(
        @RequestBody CompaniesRequest request,
        @RequestParam(value="remove-inactive", required = false, defaultValue = "false") boolean removeInactive,
        @RequestHeader(value="x-api-key") String apiKey
    ) {
        log.debug("Received request: '{}', apiKey: '{}', active: '{}'", request,apiKey,removeInactive);

        CompaniesResponse body = companyService.getCompanies(request,apiKey, removeInactive);

        return ResponseEntity.ok().body(body);

    }

}
