package com.gowita.controller;

import static com.gowita.constant.AuthRole.ROLE_ADMIN;

import com.gowita.dto.request.AddCityRequest;
import com.gowita.dto.response.CityResponse;
import com.gowita.dto.response.CityResponseForSelect;
import com.gowita.service.CityService;
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
@RequestMapping("/v1/city")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CityController {

    CityService cityService;

    @GetMapping("/select")
    @ResponseStatus(HttpStatus.OK)
    List<CityResponseForSelect> getAllForSelect() {
        return cityService.getAllForSelect();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<CityResponse> getAll() {
        return cityService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(ROLE_ADMIN)
    void addCity(@Valid @RequestBody AddCityRequest addCityRequest) {
        cityService.addCity(addCityRequest);
    }

}
