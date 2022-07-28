package com.gowita.mapper;

import com.gowita.dto.request.PackageRequest;
import com.gowita.dto.response.PackageAdminResponse;
import com.gowita.dto.response.PackageDetailAdminResponse;
import com.gowita.dto.response.PackageDetailHomePageResponse;
import com.gowita.dto.response.PackageDetailUserResponse;
import com.gowita.dto.response.PackageEditDetailUserResponse;
import com.gowita.dto.response.PackageHomePageResponse;
import com.gowita.dto.response.PackageResponseForSelect;
import com.gowita.dto.response.PackageUserResponse;
import com.gowita.entity.PackageEntity;
import com.gowita.util.DateFormatUtil;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE, uses = DateFormatUtil.class)
public interface PackageMapper {

    @Mapping(target = "packageType.id", source = "typeId")
    @Mapping(target = "fromWhere.id", source = "fromWhereId")
    @Mapping(target = "toWhere.id", source = "toWhereId")
    PackageEntity toPackageEntity(PackageRequest request);

    @Mapping(target = "fromWhere", source = "fromWhere.name")
    @Mapping(target = "toWhere", source = "toWhere.name")
    @Mapping(target = "type", source = "packageType.name")
    PackageHomePageResponse toPackageHomePageResponse(PackageEntity packages);

    @Mapping(target = "announcedDate", source = "updatedAt")
    @Mapping(target = "size", qualifiedByName = "getSize", source = "packageEntity")
    @Mapping(target = "packageType", source = "packageEntity.packageType.name")
    @Mapping(target = "destination", expression = "java( packageEntity.getFromWhere().getName()+\" to \"+packageEntity.getToWhere().getName())")
    PackageDetailHomePageResponse toPackageDetailHomePageResponse(PackageEntity packageEntity);

    @Mapping(target = "date", source = "updatedAt")
    @Mapping(target = "fullName", expression = "java( packageEntity.getUser().getName()+\" \"+packageEntity.getUser().getSurname())")
    @Mapping(target = "phoneNumber", source = "packageEntity.user.phoneNumber")
    PackageAdminResponse toPackageAdminResponses(PackageEntity packageEntity);

    @Mapping(target = "fromWhere", source = "fromWhere.name")
    @Mapping(target = "toWhere", source = "toWhere.name")
    @Mapping(target = "type", source = "packageType.name")
    PackageUserResponse toPackageUserResponse(PackageEntity packageEntity);

    @Mapping(target = "date", source = "updatedAt")
    @Mapping(target = "size", qualifiedByName = "getSize", source = "packageEntity")
    @Mapping(target = "packageType", source = "packageEntity.packageType.name")
    @Mapping(target = "fullName", expression = "java( packageEntity.getUser().getName()+\" \"+packageEntity.getUser().getSurname())")
    @Mapping(target = "phoneNumber", source = "packageEntity.user.phoneNumber")
    @Mapping(target = "destination", expression = "java( packageEntity.getFromWhere().getName()+\" to \"+packageEntity.getToWhere().getName())")
    PackageDetailAdminResponse toPackageDetailAdminResponse(PackageEntity packageEntity);

    @Mapping(target = "editedDate", source = "packageEntity.updatedAt")
    @Mapping(target = "fromWhere", source = "packageEntity.fromWhere.name")
    @Mapping(target = "toWhere", source = "packageEntity.toWhere.name")
    @Mapping(target = "size", qualifiedByName = "getSize", source = "packageEntity")
    @Mapping(target = "packageType", source = "packageEntity.packageType.name")
    @Mapping(target = "editable", source = "hasOpenOffer")
    PackageDetailUserResponse toPackageDetailUserResponse(PackageEntity packageEntity, boolean hasOpenOffer, long myRequetsCount,
                                                          long contactRequestsCount);

    PackageEditDetailUserResponse toPackageEditDetailUserResponse(PackageEntity packageEntity);

    @Mapping(target = "value", source = "packageEntity.id")
    @Mapping(target = "label", source = "packageEntity", qualifiedByName = "convertToLabel")
    PackageResponseForSelect toPackageResponseForSelect(PackageEntity packageEntity);

    List<PackageResponseForSelect> toPackageResponseForSelect(List<PackageEntity> packageEntities);

    @Named("convertToLabel")
    default String convertToLabel(PackageEntity packageEntity) {
        return packageEntity.getFromWhere().getName() + " to "
                + packageEntity.getToWhere().getName() + " "
                + packageEntity.getDeliveryDate().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")) + " "
                + packageEntity.getPackageType().getName() + "("
                + packageEntity.getWidth() + "/"
                + packageEntity.getLength() + "/"
                + packageEntity.getHeight() + " sm-"
                + packageEntity.getWeight() + " qr)";
    }

    @Named("getSize")
    default String getSize(PackageEntity packageEntity) {
        return packageEntity.getWidth() + "/" + packageEntity.getLength() + "/" + packageEntity.getHeight();
    }
}
