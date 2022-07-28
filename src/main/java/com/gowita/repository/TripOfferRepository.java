package com.gowita.repository;

import com.gowita.constant.TripOfferStatus;
import com.gowita.entity.TripOfferEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripOfferRepository extends JpaRepository<TripOfferEntity, Long> {
    Optional<TripOfferEntity> findByIdAndStatus(Long id, TripOfferStatus status);

    Optional<TripOfferEntity> findByIdAndStatusIn(Long id, List<TripOfferStatus> status);

    Page<TripOfferEntity> findByTripIdOrderByUpdatedAtAsc(Long id, Pageable page);

    Page<TripOfferEntity> findByTripIdAndStatusOrderByUpdatedAtDesc(Long id, TripOfferStatus status, Pageable page);

    Page<TripOfferEntity> findByTripIdAndStatusInOrderByUpdatedAtDesc(Long id, List<TripOfferStatus> status, Pageable page);

    Page<TripOfferEntity> findByPackIdOrderByUpdatedAtAsc(Long id, Pageable page);

    Page<TripOfferEntity> findByPackIdAndStatusOrderByUpdatedAtDesc(Long id, TripOfferStatus status, Pageable page);

    Long countByPackIdAndStatus(Long id, TripOfferStatus status);

    Long countByTripIdAndStatusIn(Long id, List<TripOfferStatus> status);

    Long countByTripId(Long id);

    Long countByPackId(Long id);

    boolean existsByPackId(Long id);

    boolean existsByTripId(Long id);
}
