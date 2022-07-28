package com.gowita.repository;

import com.gowita.entity.AdminTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminTokenRepository extends JpaRepository<AdminTokenEntity, String> {
}
