package com.gowita.mapper;

import com.gowita.dto.request.TripRequest;
import com.gowita.dto.response.TripAdminResponse;
import com.gowita.dto.response.TripDetailAdminResponse;
import com.gowita.dto.response.TripDetailUserResponse;
import com.gowita.dto.response.TripEditDetailUserResponse;
import com.gowita.dto.response.TripHomePageResponse;
import com.gowita.dto.response.TripResponseForSelect;
import com.gowita.dto.response.TripUserResponse;
import com.gowita.entity.TripEntity;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TripMapper {

    @Mapping(target = "packageType.id", source = "packageTypeId")
    @Mapping(target = "fromWhere.id", source = "fromWhereId")
    @Mapping(target = "toWhere.id", source = "toWhereId")
    TripEntity toTripEntity(TripRequest request);

    @Mapping(target = "toWhere", source = "toWhere.name")
    @Mapping(target = "fromWhere", source = "fromWhere.name")
    @Mapping(target = "type", source = "tripType")
    @Mapping(target = "size", qualifiedByName = "getSize", source = "tripEntity")
    @Mapping(target = "packageType", source = "tripEntity.packageType.name")
    TripHomePageResponse toTripResponse(TripEntity tripEntity);

    @Mapping(target = "date", source = "updatedAt")
    @Mapping(target = "fullName", expression = "java( tripEntity.getUser().getName()+\" \"+tripEntity.getUser().getSurname())")
    @Mapping(target = "phoneNumber", source = "tripEntity.user.phoneNumber")
    TripAdminResponse toTripAdminResponses(TripEntity tripEntity);

    @Mapping(target = "date", source = "updatedAt")
    @Mapping(target = "size", source = "tripEntity", qualifiedByName = "getSize")
    @Mapping(target = "transportation", source = "tripType")
    @Mapping(target = "packageType", source = "tripEntity.packageType.name")
    @Mapping(target = "fullName", expression = "java( tripEntity.getUser().getName()+\" \"+tripEntity.getUser().getSurname())")
    @Mapping(target = "phoneNumber", source = "tripEntity.user.phoneNumber")
    @Mapping(target = "destination", expression = "java( tripEntity.getFromWhere().getName()+\" to \"+tripEntity.getToWhere().getName())")
    TripDetailAdminResponse toTripDetailAdminResponse(TripEntity tripEntity);

    @Mapping(target = "toWhere", source = "toWhere.name")
    @Mapping(target = "fromWhere", source = "fromWhere.name")
    @Mapping(target = "type", source = "tripType")
    TripUserResponse toTripUserResponses(TripEntity tripEntity);

    @Mapping(target = "editedDate", source = "tripEntity.updatedAt")
    @Mapping(target = "transportation", source = "tripEntity.tripType")
    @Mapping(target = "packageType", source = "tripEntity.packageType.name")
    @Mapping(target = "toWhere", source = "tripEntity.toWhere.name")
    @Mapping(target = "fromWhere", source = "tripEntity.fromWhere.name")
    @Mapping(target = "size", source = "tripEntity", qualifiedByName = "getSize")
    @Mapping(target = "editable", source = "hasOpenOffer")
    TripDetailUserResponse toTripDetailUserResponse(TripEntity tripEntity, boolean hasOpenOffer, long contactRequestsCount, long myRequestsCount);

    @Mapping(target = "value", source = "tripEntity.id")
    @Mapping(target = "label", source = "tripEntity", qualifiedByName = "convertToLabel")
    TripResponseForSelect toTripResponseForSelect(TripEntity tripEntity);

    List<TripResponseForSelect> toTripResponseForSelect(List<TripEntity> tripEntities);

    @Named("convertToLabel")
    default String convertToLabel(TripEntity tripEntity) {
        return tripEntity.getFromWhere().getName() + " to "
                + tripEntity.getToWhere().getName() + " "
                + tripEntity.getDepartureDate().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm")) + " "
                + tripEntity.getTripType();
    }

    @Named("getSize")
    default String getSize(TripEntity tripEntity) {
        return tripEntity.getWidth() + "/" + tripEntity.getLength() + "/" + tripEntity.getHeight();
    }


    TripEditDetailUserResponse toTripEditDetailUserResponse(TripEntity tripEntity);
}
