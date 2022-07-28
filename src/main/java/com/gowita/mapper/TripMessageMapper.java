package com.gowita.mapper;

import com.gowita.dto.response.TripMessageResponse;
import com.gowita.entity.TripRejectMessageEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TripMessageMapper {
    List<TripMessageResponse> toTripResponse(List<TripRejectMessageEntity> tripMessageEntities);
}
