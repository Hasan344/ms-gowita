package com.gowita.repository;

import com.gowita.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<CountryEntity, Long> {
}
