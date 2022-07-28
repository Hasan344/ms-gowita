package com.gowita.repository;

import com.gowita.entity.UserEntity;
import com.gowita.entity.UserTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserTokenEntity, String> {

    UserTokenEntity findFirstByUserOrderByDateAsc(UserEntity userEntity);

    long countUserTokenEntitiesByUser(UserEntity userEntity);
}

