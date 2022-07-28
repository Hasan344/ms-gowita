package com.gowita.mapper;

import com.gowita.dto.request.SignupRequest;
import com.gowita.dto.response.LoginResponse;
import com.gowita.dto.response.UserResponse;
import com.gowita.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "country.id", source = "countryId")
    UserEntity toUserEntity(SignupRequest signupRequest);

    UserResponse toUserResponse(UserEntity userEntity);

    LoginResponse toLoginResponse(UserEntity userEntity);
}
