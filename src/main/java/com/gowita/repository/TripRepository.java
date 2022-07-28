package com.gowita.repository;

import com.gowita.constant.Status;
import com.gowita.entity.TripEntity;
import com.gowita.entity.UserEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TripRepository extends JpaRepository<TripEntity, Long>, JpaSpecificationExecutor<TripEntity> {
    @Modifying
    @Query("update TripEntity te set te.status=:status,te.hasNotification=true where te.departureDate < :dateTimeNow and te.status in (:listStatus)")
    void findAllWithDateTimeNowBefore(
            LocalDateTime dateTimeNow, Status status, List<Status> listStatus);

    Page<TripEntity> findByOrderByUpdatedAtAsc(Pageable page);

    Page<TripEntity> findByUserOrderByUpdatedAtDesc(UserEntity user, Pageable page);

    Page<TripEntity> findByUserAndStatusOrderByUpdatedAtDesc(UserEntity user, Status status, Pageable page);

    Page<TripEntity> findByStatusOrderByUpdatedAtAsc(Status status, Pageable page);

    Long countByStatus(Status status);

    Page<TripEntity> findByUserOrderByUpdatedAtAsc(UserEntity user, Pageable page);

    List<TripEntity> findByUserAndStatusOrderByUpdatedAtAsc(UserEntity user, Status status);

    Page<TripEntity> findByUserAndStatusOrderByUpdatedAtAsc(UserEntity user, Status status, Pageable page);

    boolean existsByUserAndHasNotificationIsTrue(UserEntity user);

    boolean existsByUserAndHasNotificationIsTrueAndAndStatus(UserEntity user, Status status);
}
