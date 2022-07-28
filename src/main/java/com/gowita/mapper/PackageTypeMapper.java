package com.gowita.mapper;

import com.gowita.dto.request.PackageTypeRequest;
import com.gowita.dto.response.PackageTypeResponse;
import com.gowita.dto.response.PackageTypeResponseForSelect;
import com.gowita.entity.PackageTypeEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface PackageTypeMapper {

    PackageTypeResponse toPackageTypeResponse(PackageTypeEntity packageTypeEntity);

    List<PackageTypeResponse> toPackageTypeResponses(List<PackageTypeEntity> packageTypeEntities);

    @Mapping(target = "value", source = "id")
    @Mapping(target = "label", source = "name")
    PackageTypeResponseForSelect toPackageTypeResponseForSelect(PackageTypeEntity packageTypeEntities);

    List<PackageTypeResponseForSelect> toPackageTypeResponsesForSelect(List<PackageTypeEntity> packageTypeEntities);

    PackageTypeEntity toPackageTypeEntity(PackageTypeRequest request);
}
