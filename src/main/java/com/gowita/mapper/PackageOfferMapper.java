package com.gowita.mapper;

import com.gowita.constant.PackageOfferStatus;
import com.gowita.dto.request.PackageOfferRequest;
import com.gowita.dto.response.PackageOfferResponseForPackage;
import com.gowita.dto.response.PackageOfferResponseForTrip;
import com.gowita.entity.PackageOfferEntity;
import com.gowita.util.DateFormatUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE, uses = DateFormatUtil.class)
public interface PackageOfferMapper {

    @Mapping(target = "trip.id", source = "tripId")
    @Mapping(target = "pack.id", source = "packageId")
    PackageOfferEntity toPackageOfferEntity(PackageOfferRequest request);

    @Mapping(target = "tripType", source = "trip.tripType")
    @Mapping(target = "name", source = "packageOfferEntity", qualifiedByName = "getName")
    @Mapping(target = "surname", source = "packageOfferEntity", qualifiedByName = "getSurname")
    @Mapping(target = "phoneNumber", source = "packageOfferEntity", qualifiedByName = "getPhoneNumber")
    PackageOfferResponseForPackage toPackageOfferResponseForPackage(PackageOfferEntity packageOfferEntity);

    @Mapping(target = "tripType", source = "trip.tripType")
    PackageOfferResponseForPackage toPackageOfferResponseForPackageStatusAll(PackageOfferEntity packageOfferEntity);


    @Mapping(target = "packId", source = "pack.id")
    @Mapping(target = "status", source = "packageOfferEntity", qualifiedByName = "getStatus")
    PackageOfferResponseForTrip toPackageOfferResponseForTrip(PackageOfferEntity packageOfferEntity);

    @Mapping(target = "name", source = "packageOfferEntity", qualifiedByName = "getName")
    @Mapping(target = "status", source = "packageOfferEntity", qualifiedByName = "getStatus")
    @Mapping(target = "surname", source = "packageOfferEntity", qualifiedByName = "getSurname")
    @Mapping(target = "phoneNumber", source = "packageOfferEntity", qualifiedByName = "getPhoneNumber")
    @Mapping(target = "packId", source = "pack.id")
    PackageOfferResponseForTrip toPackageOfferResponseForTripStatusOpened(PackageOfferEntity packageOfferEntity);

    @Named(value = "getName")
    default String getName(PackageOfferEntity packageOfferEntity) {
        return packageOfferEntity.getStatus().equals(PackageOfferStatus.OPENED) ? packageOfferEntity.getPack().getUser().getName() : null;
    }

    @Named(value = "getSurname")
    default String getSurname(PackageOfferEntity packageOfferEntity) {
        return packageOfferEntity.getStatus().equals(PackageOfferStatus.OPENED) ? packageOfferEntity.getPack().getUser().getSurname() : null;
    }

    @Named(value = "getPhoneNumber")
    default String getPhoneNumber(PackageOfferEntity packageOfferEntity) {
        return packageOfferEntity.getStatus().equals(PackageOfferStatus.OPENED) ? packageOfferEntity.getPack().getUser().getPhoneNumber() : null;
    }

    @Named(value = "getStatus")
    default PackageOfferStatus getStatus(PackageOfferEntity packageOfferEntity) {
        if (packageOfferEntity.getStatus().equals(PackageOfferStatus.CARD) || packageOfferEntity.getStatus().equals(PackageOfferStatus.HIDDEN)) {
            return PackageOfferStatus.RAW;
        } else {
            return packageOfferEntity.getStatus();
        }
    }
}
