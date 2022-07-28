package com.gowita.service;

import com.gowita.dto.request.AddCountryRequest;
import com.gowita.dto.response.CountryResponse;
import com.gowita.dto.response.CountryResponseForSelect;
import com.gowita.entity.CountryEntity;
import com.gowita.mapper.CountryMapper;
import com.gowita.repository.CountryRepository;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CountryService {

    CountryMapper countryMapper;
    CountryRepository countryRepository;

    public void addCountry(AddCountryRequest addCountryRequest) {
        CountryEntity country = new CountryEntity();
        country.setName(addCountryRequest.getName());
        countryRepository.save(country);
    }

    public List<CountryResponse> getAll() {
        List<CountryEntity> countryEntities = countryRepository.findAll();
        return countryMapper.toCountryResponses(countryEntities);
    }

    public List<CountryResponseForSelect> getAllForSelect() {
        List<CountryEntity> countryEntities = countryRepository.findAll();
        return countryMapper.toCountryResponsesForSelect(countryEntities);
    }
}
