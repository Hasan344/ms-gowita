package com.gowita.repository;

import com.gowita.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByPhoneNumber(String phoneNumber);

    Optional<UserEntity> findByPhoneNumberAndStatusIsTrue(String phoneNumber);

    boolean existsByPhoneNumberAndStatusIsTrue(String phoneNumber);
}
