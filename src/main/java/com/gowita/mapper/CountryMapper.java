package com.gowita.mapper;

import com.gowita.dto.response.CountryResponse;
import com.gowita.dto.response.CountryResponseForSelect;
import com.gowita.entity.CountryEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CountryMapper {
    List<CountryResponse> toCountryResponses(List<CountryEntity> countryEntity);

    @Mapping(target = "value", source = "id")
    @Mapping(target = "label", source = "name")
    CountryResponseForSelect toCountryResponsesForSelect(CountryEntity country);

    List<CountryResponseForSelect> toCountryResponsesForSelect(List<CountryEntity> countryEntities);
}
