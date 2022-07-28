package com.gowita.repository;

import com.gowita.entity.PackageTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageTypeRepository extends JpaRepository<PackageTypeEntity, Long> {
    boolean existsByName(String name);
}
