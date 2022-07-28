package com.gowita.repository;

import com.gowita.constant.Status;
import com.gowita.entity.PackageEntity;
import com.gowita.entity.UserEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PackageRepository extends JpaRepository<PackageEntity, Long>, JpaSpecificationExecutor<PackageEntity> {
    @Modifying
    @Query("update PackageEntity pe set pe.status=:status where pe.conductDate < :dateNow and pe.status in (:listStatus)")
    void updateStatusToOutdated(
            LocalDate dateNow, Status status, List<Status> listStatus);

    Page<PackageEntity> findByStatusOrderByUpdatedAtAsc(Status status, Pageable page);

    Page<PackageEntity> findByUserAndStatusOrderByUpdatedAtDesc(UserEntity user, Status status, Pageable page);

    Page<PackageEntity> findByOrderByUpdatedAtAsc(Pageable page);

    Page<PackageEntity> findByUserOrderByUpdatedAtDesc(UserEntity user, Pageable page);

    Long countByStatus(Status status);

    Optional<PackageEntity> findByIdAndUser(Long id, UserEntity user);

    List<PackageEntity> findByUserAndStatusOrderByUpdatedAtAsc(UserEntity user, Status status);

    Page<PackageEntity> findByUserAndStatusOrderByUpdatedAtAsc(UserEntity user, Status status, Pageable page);

    Page<PackageEntity> findByUser(UserEntity user, Pageable page);

    boolean existsByUserAndHasNotificationIsTrue(UserEntity user);

    boolean existsByUserAndHasNotificationIsTrueAndAndStatus(UserEntity user, Status status);

    Page<PackageEntity> findByUserOrderByUpdatedAtAsc(UserEntity user, Pageable page);
}
