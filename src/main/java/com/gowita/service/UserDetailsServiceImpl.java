package com.gowita.service;

import com.gowita.constant.Role;
import com.gowita.entity.AdminEntity;
import com.gowita.entity.AdminTokenEntity;
import com.gowita.entity.UserEntity;
import com.gowita.entity.UserTokenEntity;
import com.gowita.repository.AdminTokenRepository;
import com.gowita.repository.UserTokenRepository;
import com.gowita.security.UserPrincipal;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    UserTokenRepository userTokenRepository;
    AdminTokenRepository adminTokenRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        Optional<UserTokenEntity> optionalTokenEntity = userTokenRepository.findById(token);
        if (optionalTokenEntity.isPresent()) {
            UserEntity user = optionalTokenEntity.get().getUser();
            return new UserPrincipal(user, Role.ROLE_USER);
        }
        Optional<AdminTokenEntity> optionalAdminTokenEntity = adminTokenRepository.findById(token);
        if (optionalAdminTokenEntity.isPresent()) {
            AdminEntity admin = optionalAdminTokenEntity.get().getAdmin();
            return new UserPrincipal(admin, Role.ROLE_ADMIN);
        }
        return null;
    }
}
