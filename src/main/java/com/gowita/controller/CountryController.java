package com.gowita.controller;

import static com.gowita.constant.AuthRole.ROLE_ADMIN;

import com.gowita.dto.request.AddCountryRequest;
import com.gowita.dto.response.CountryResponse;
import com.gowita.dto.response.CountryResponseForSelect;
import com.gowita.service.CountryService;
import java.util.List;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/country")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CountryController {

    CountryService countryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<CountryResponse> getAll() {
        return countryService.getAll();
    }

    @GetMapping("/select")
    @ResponseStatus(HttpStatus.OK)
    List<CountryResponseForSelect> getAllForSelect() {
        return countryService.getAllForSelect();
    }

    @PostMapping
    @PreAuthorize(ROLE_ADMIN)
    @ResponseStatus(HttpStatus.CREATED)
    void addCountry(@Valid @RequestBody AddCountryRequest addCountryRequest) {
        countryService.addCountry(addCountryRequest);
    }
}
