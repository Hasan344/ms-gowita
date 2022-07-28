package com.gowita.mapper;

import com.gowita.dto.response.CityResponse;
import com.gowita.dto.response.CityResponseForSelect;
import com.gowita.entity.CityEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CityMapper {

    @Mapping(target = "value", source = "id")
    @Mapping(target = "label", expression = "java( city.getName()+\", \"+city.getCountry().getName())")
    CityResponseForSelect toCityResponseForSelect(CityEntity city);

    List<CityResponseForSelect> toCityResponsesForSelect(List<CityEntity> cities);

    List<CityResponse> toCityResponses(List<CityEntity> cities);

}
