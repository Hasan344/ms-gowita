package com.gowita.mapper;

import com.gowita.constant.TripOfferStatus;
import com.gowita.dto.request.TripOfferRequest;
import com.gowita.dto.response.PackageDetailsForTripOffer;
import com.gowita.dto.response.TripOfferResponseForPackage;
import com.gowita.dto.response.TripOfferResponseForTrip;
import com.gowita.entity.TripOfferEntity;
import com.gowita.util.DateFormatUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE, uses = DateFormatUtil.class)
public interface TripOfferMapper {

    @Mapping(target = "trip.id", source = "tripId")
    @Mapping(target = "pack.id", source = "packageId")
    TripOfferEntity toTripOfferEntity(TripOfferRequest request);

    @Mapping(target = "destination", source = "tripOfferEntity", qualifiedByName = "getDestination")
    @Mapping(target = "packageType", source = "pack.packageType.name")
    @Mapping(target = "size", qualifiedByName = "getSize", source = "tripOfferEntity")
    @Mapping(target = "weight", source = "pack.weight")
    @Mapping(target = "conductDate", source = "pack.conductDate")
    @Mapping(target = "deliveryDate", source = "pack.deliveryDate")
    @Mapping(target = "filePaths", source = "pack.filePaths")
    PackageDetailsForTripOffer toPackageDetailsForTripOffer(TripOfferEntity tripOfferEntity);

    @Mapping(target = "destination", source = "tripOfferEntity", qualifiedByName = "getDestination")
    @Mapping(target = "departureDate", source = "trip.departureDate")
    @Mapping(target = "tripType", source = "trip.tripType")
    TripOfferResponseForPackage toTripOfferResponseForPackage(TripOfferEntity tripOfferEntity);

    @Mapping(target = "destination",
            expression = "java( tripOfferEntity.getTrip().getFromWhere().getName()+\" to \"+tripOfferEntity.getTrip().getToWhere().getName())")
    @Mapping(target = "departureDate", source = "trip.departureDate")
    @Mapping(target = "tripType", source = "trip.tripType")
    @Mapping(target = "name", source = "trip.user.name")
    @Mapping(target = "surname", source = "trip.user.surname")
    @Mapping(target = "phoneNumber", source = "trip.user.phoneNumber")
    TripOfferResponseForPackage toTripOfferResponseForOpenedPackage(TripOfferEntity tripOfferEntity);


    @Mapping(target = "packageType", source = "pack.packageType.name")
    @Mapping(target = "size", source = "tripOfferEntity", qualifiedByName = "getSize")
    @Mapping(target = "weight", source = "pack.weight")
    TripOfferResponseForTrip toTripOfferResponseForTrip(TripOfferEntity tripOfferEntity);

    @Mapping(target = "packageType", source = "pack.packageType.name")
    @Mapping(target = "size", source = "tripOfferEntity", qualifiedByName = "getSize")
    @Mapping(target = "weight", source = "pack.weight")
    @Mapping(target = "name", source = "pack.user.name")
    @Mapping(target = "surname", source = "pack.user.surname")
    @Mapping(target = "phoneNumber", source = "pack.user.phoneNumber")
    TripOfferResponseForTrip toTripOfferResponseForOpenedTrip(TripOfferEntity tripOfferEntity);

    @Mapping(target = "packageType", source = "pack.packageType.name")
    @Mapping(target = "size", source = "tripOfferEntity", qualifiedByName = "getSize")
    @Mapping(target = "weight", source = "pack.weight")
    @Mapping(target = "status", source = "tripOfferEntity", qualifiedByName = "changeCardStatus")
    TripOfferResponseForTrip toTripOfferResponseForAllTrip(TripOfferEntity tripOfferEntity);

    @Named(value = "changeCardStatus")
    default TripOfferStatus changeCardStatus(TripOfferEntity tripOfferEntity) {
        return tripOfferEntity.getStatus().equals(TripOfferStatus.CARD) ? TripOfferStatus.PENDING : tripOfferEntity.getStatus();
    }

    @Named("getDestination")
    default String getDestination(TripOfferEntity tripOfferEntity) {
        return tripOfferEntity.getTrip().getFromWhere().getName() + " to " + tripOfferEntity.getTrip().getToWhere().getName();
    }

    @Named("getSize")
    default String getSize(TripOfferEntity tripOfferEntity) {
        return tripOfferEntity.getTrip().getWidth() + "/" + tripOfferEntity.getTrip().getLength() + "/" + tripOfferEntity.getTrip().getHeight();
    }

}
