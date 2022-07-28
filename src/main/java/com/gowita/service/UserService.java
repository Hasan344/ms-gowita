package com.gowita.service;

import static com.gowita.constant.CommonConstant.PHONE_NUMBER_NOT_FOUND;
import static com.gowita.constant.CommonConstant.WRONG_PASSWORD;
import static com.gowita.util.SecurityUtil.getLoginUser;

import com.gowita.dto.request.ChangePasswordRequest;
import com.gowita.dto.request.SignupRequest;
import com.gowita.dto.request.UserEditRequest;
import com.gowita.dto.response.UserResponse;
import com.gowita.entity.CountryEntity;
import com.gowita.entity.UserEntity;
import com.gowita.exception.AlreadyExistException;
import com.gowita.exception.CustomBadRequestException;
import com.gowita.exception.CustomNotFoundException;
import com.gowita.mapper.UserMapper;
import com.gowita.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserMapper userMapper;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;


    @Transactional
    public void otpVerification(String phoneNumber) {
        UserEntity user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomNotFoundException(PHONE_NUMBER_NOT_FOUND));
        user.setStatus(true);
    }

    @Transactional
    public void edit(UserEditRequest userEditRequest, Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("User not found. Id: " + id));
        userEntity.setName(userEditRequest.getName());
        userEntity.setSurname(userEditRequest.getSurname());
        userEntity.setAdditionalNumber(userEditRequest.getAdditionalNumber());
        userEntity.setCountry(CountryEntity.builder().id(userEditRequest.getCountryId()).build());
    }

    public UserResponse getUserByIdForEdit(Long id) {
        var userEntity = userRepository.findById(id).orElseThrow(() -> new CustomNotFoundException("User not found"));
        return userMapper.toUserResponse(userEntity);
    }

    public void check(String phoneNumber) {
        var isExist = userRepository.existsByPhoneNumberAndStatusIsTrue(phoneNumber);
        if (!isExist) {
            throw new CustomNotFoundException("Phone number is not registered");
        }
    }

    public void changePassword(ChangePasswordRequest request) {
        UserEntity user = getLoginUser();
        boolean checkPassword = passwordEncoder.matches(request.getOldPassword(), user.getPassword());
        if (!checkPassword) {
            throw new CustomBadRequestException(WRONG_PASSWORD);
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
