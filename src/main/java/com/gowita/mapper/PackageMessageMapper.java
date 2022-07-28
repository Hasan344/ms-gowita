package com.gowita.mapper;

import com.gowita.dto.response.PackageMessageResponse;
import com.gowita.entity.PackageRejectMessageEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PackageMessageMapper {
    List<PackageMessageResponse> toMessageResponse(List<PackageRejectMessageEntity> packageRejectMessageEntities);
}
