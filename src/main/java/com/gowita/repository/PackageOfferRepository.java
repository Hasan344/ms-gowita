package com.gowita.repository;

import com.gowita.constant.PackageOfferStatus;
import com.gowita.entity.PackageOfferEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageOfferRepository extends JpaRepository<PackageOfferEntity, Long> {
    Page<PackageOfferEntity> findByOrderByUpdatedAtAsc(Pageable page);

    Page<PackageOfferEntity> findByPackIdOrderByUpdatedAtAsc(Long id, Pageable page);

    Page<PackageOfferEntity> findByPackIdAndStatusOrderByUpdatedAtAsc(Long id, PackageOfferStatus status, Pageable page);

    List<PackageOfferEntity> findByPackIdAndStatus(Long id, PackageOfferStatus status);

    Page<PackageOfferEntity> findByTripIdOrderByUpdatedAtAsc(Long id, Pageable page);

    Page<PackageOfferEntity> findByTripIdAndStatusNotOrderByUpdatedAtAsc(Long id, PackageOfferStatus status, Pageable page);

    Page<PackageOfferEntity> findByTripIdAndStatusOrderByUpdatedAtAsc(Long id, PackageOfferStatus raw, Pageable page);

    Optional<PackageOfferEntity> findByIdAndStatusIn(Long id, List<PackageOfferStatus> statuses);

    Long countByPackIdAndStatus(Long id, PackageOfferStatus status);

    Long countByPackId(Long id);

    Long countByTripIdAndStatusIn(Long id, List<PackageOfferStatus> raw);

    Long countByTripId(Long id);

    Optional<PackageOfferEntity> findByIdAndStatus(Long id, PackageOfferStatus status);

    boolean existsByPackId(Long id);

    boolean existsByTripId(Long id);
}
