package com.gowita.mapper;

import com.gowita.dto.response.LoginResponse;
import com.gowita.entity.AdminEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AdminMapper {
    LoginResponse toLoginResponse(AdminEntity adminEntity);
}
