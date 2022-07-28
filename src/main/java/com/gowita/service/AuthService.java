package com.gowita.service;

import com.gowita.dto.request.LoginRequest;
import com.gowita.dto.request.ResetPasswordRequest;
import com.gowita.dto.response.LoginResponse;
import com.gowita.entity.AdminEntity;
import com.gowita.entity.AdminTokenEntity;
import com.gowita.entity.UserEntity;
import com.gowita.entity.UserTokenEntity;
import com.gowita.exception.AuthenticationException;
import com.gowita.exception.CustomNotFoundException;
import com.gowita.mapper.AdminMapper;
import com.gowita.mapper.UserMapper;
import com.gowita.repository.AdminRepository;
import com.gowita.repository.AdminTokenRepository;
import com.gowita.repository.UserRepository;
import com.gowita.repository.UserTokenRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {


    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserTokenRepository userTokenRepository;
    AdminTokenRepository adminTokenRepository;




    public void logout(String authorization) {
        String token = authorization.substring(7);
        Optional<UserTokenEntity> userTokenEntity = userTokenRepository.findById(token);
        userTokenEntity.ifPresent(userTokenRepository::delete);

        Optional<AdminTokenEntity> adminTokenEntity = adminTokenRepository.findById(token);
        adminTokenEntity.ifPresent(adminTokenRepository::delete);
    }

    public void resetPassword(ResetPasswordRequest passwordRequest) {
        UserEntity user = userRepository.findByPhoneNumberAndStatusIsTrue(passwordRequest.getPhoneNumber())
                .orElseThrow(() -> new CustomNotFoundException("User Not found"));
        user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
        userRepository.save(user);
    }


}

