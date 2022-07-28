package com.gowita.service;

import com.gowita.dto.request.AddCityRequest;
import com.gowita.dto.response.CityResponse;
import com.gowita.dto.response.CityResponseForSelect;
import com.gowita.entity.CityEntity;
import com.gowita.entity.CountryEntity;
import com.gowita.mapper.CityMapper;
import com.gowita.repository.CityRepository;
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
public class CityService {

    CityMapper cityMapper;
    CityRepository cityRepository;

    public List<CityResponseForSelect> getAllForSelect() {
        List<CityEntity> cities = cityRepository.findAll();
        return cityMapper.toCityResponsesForSelect(cities);
    }

    public List<CityResponse> getAll() {
        List<CityEntity> cities = cityRepository.findAll();
        return cityMapper.toCityResponses(cities);
    }

    public void addCity(AddCityRequest addCityRequest) {

        CityEntity cityEntity = CityEntity.builder()
                .name(addCityRequest.getName())
                .country(CountryEntity.builder()
                        .id(addCityRequest.getCountryId())
                        .build())
                .build();
        cityRepository.save(cityEntity);
    }
}
