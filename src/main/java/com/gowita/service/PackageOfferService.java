package com.gowita.service;

import static com.gowita.constant.CommonConstant.ACCESS_DENIED;
import static com.gowita.constant.CommonConstant.OFFER_NOT_FOUND;
import static com.gowita.constant.PackageOfferStatus.CARD;
import static com.gowita.constant.PackageOfferStatus.HIDDEN;
import static com.gowita.constant.PackageOfferStatus.OPENED;
import static com.gowita.constant.PackageOfferStatus.RAW;
import static com.gowita.util.SecurityUtil.getLoginUser;

import com.gowita.constant.PackageOfferStatus;
import com.gowita.dto.request.PackageOfferEditRequest;
import com.gowita.dto.request.PackageOfferRequest;
import com.gowita.dto.response.CountPackageOfferStatus;
import com.gowita.dto.response.PackageOfferResponseForPackage;
import com.gowita.dto.response.PackageOfferResponseForTrip;
import com.gowita.entity.PackageEntity;
import com.gowita.entity.PackageOfferEntity;
import com.gowita.entity.UserEntity;
import com.gowita.exception.CustomBadRequestException;
import com.gowita.exception.CustomNotFoundException;
import com.gowita.exception.ForbiddenException;
import com.gowita.mapper.PackageOfferMapper;
import com.gowita.repository.PackageOfferRepository;
import com.gowita.repository.PackageRepository;
import com.gowita.repository.TripRepository;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PackageOfferService {

    TripRepository tripRepository;
    PackageRepository packageRepository;
    PackageOfferMapper packageOfferMapper;
    PackageOfferRepository packageOfferRepository;

    @Transactional
    public void create(PackageOfferRequest request) {
        log.info("Package create process is started. request - {}", request);
        UserEntity loginUser = getLoginUser();
        UserEntity tripUser = tripRepository.getById(request.getTripId()).getUser();
        PackageEntity packageEntity = packageRepository.getById(request.getPackageId());
        UserEntity packageUser = packageEntity.getUser();
        if (loginUser != tripUser && packageUser == tripUser) {
            throw new CustomBadRequestException("Access denied for request. "
                    + "loginUser - " + loginUser.getId() + " tripUser - " + tripUser.getId() + " packageUser - " + packageUser.getId());
        }
        packageEntity.setHasNotification(true);
        var packageOffer = packageOfferMapper.toPackageOfferEntity(request);
        packageOffer.setStatus(RAW);
        packageOfferRepository.save(packageOffer);
    }

    public Page<PackageOfferResponseForPackage> getPackageContactOffersForPackage(Long id, PackageOfferStatus status, Pageable page) {
        log.info("Package Offer For Package is started.id - {}, status - {}", id, status);
        Page<PackageOfferEntity> packageOfferEntities;
        if (status == null) {
            packageOfferEntities = packageOfferRepository.findByPackIdOrderByUpdatedAtAsc(id, page);
            return packageOfferEntities.map(packageOfferMapper::toPackageOfferResponseForPackageStatusAll);
        } else {
            packageOfferEntities = packageOfferRepository.findByPackIdAndStatusOrderByUpdatedAtAsc(id, status, page);
        }

        return packageOfferEntities.map(packageOfferMapper::toPackageOfferResponseForPackage);
    }

    public Page<PackageOfferResponseForTrip> getPackageOfferForTrip(Long id, PackageOfferStatus status, Pageable page) {
        log.info("Package Offer For Trip is started.id - {}, status - {}", id, status);
        Page<PackageOfferEntity> packageOfferEntities;
        if (status == null) {
            packageOfferEntities = packageOfferRepository.findByTripIdOrderByUpdatedAtAsc(id, page);
        } else if (status == PackageOfferStatus.OPENED) {
            packageOfferEntities = packageOfferRepository.findByTripIdAndStatusOrderByUpdatedAtAsc(id, OPENED, page);
            return packageOfferEntities.map(packageOfferMapper::toPackageOfferResponseForTripStatusOpened);
        } else if (status == RAW) {
            packageOfferEntities = packageOfferRepository.findByTripIdAndStatusNotOrderByUpdatedAtAsc(id, OPENED, page);
        } else {
            throw new CustomBadRequestException("Bad request");
        }
        return packageOfferEntities.map(packageOfferMapper::toPackageOfferResponseForTrip);
    }

    @Transactional
    public void update(PackageOfferEditRequest request, Long id) {
        PackageOfferEntity packageOfferEntity = packageOfferRepository.findByIdAndStatusIn(id, List.of(RAW, CARD, HIDDEN))
                .orElseThrow(() -> new CustomNotFoundException(OFFER_NOT_FOUND));
        if (!getLoginUser().getId().equals(packageOfferEntity.getTrip().getUser().getId())) {
            throw new ForbiddenException(ACCESS_DENIED);
        }
        packageOfferEntity.setStatus(RAW);
        packageOfferEntity.setFromWhere(request.getFromWhere());
        packageOfferEntity.setPickUpDate(request.getPickUpDate());
        packageOfferEntity.setPrice(request.getPrice());
    }


    public void delete(Long id) {
        PackageOfferEntity packageOfferEntity = packageOfferRepository
                .findByIdAndStatusIn(id, List.of(RAW, CARD, HIDDEN))
                .orElseThrow(() -> new CustomNotFoundException("Package offer not find"));
        if (!Objects.equals(getLoginUser().getId(), packageOfferEntity.getPack().getUser().getId())) {
            throw new ForbiddenException(ACCESS_DENIED);
        }
        packageOfferRepository.deleteById(id);
    }

    public CountPackageOfferStatus getPackageOfferCountForPackage(Long packId) {
        Long countRaw = packageOfferRepository.countByPackIdAndStatus(packId, RAW);
        Long countCard = packageOfferRepository.countByPackIdAndStatus(packId, CARD);
        Long countOpened = packageOfferRepository.countByPackIdAndStatus(packId, OPENED);
        Long countHidden = packageOfferRepository.countByPackIdAndStatus(packId, HIDDEN);
        Long countAll = packageOfferRepository.countByPackId(packId);

        return CountPackageOfferStatus.builder()
                .countAll(countAll)
                .countRaw(countRaw)
                .countCard(countCard)
                .countHidden(countHidden)
                .countOpened(countOpened)
                .build();
    }

    public CountPackageOfferStatus getPackageOfferCountForTrip(Long tripId) {
        Long countRaw = packageOfferRepository.countByTripIdAndStatusIn(tripId, List.of(RAW, HIDDEN, CARD));
        Long countOpened = packageOfferRepository.countByTripIdAndStatusIn(tripId, List.of(OPENED));
        Long countAll = packageOfferRepository.countByTripId(tripId);

        return CountPackageOfferStatus.builder()
                .countAll(countAll)
                .countRaw(countRaw)
                .countOpened(countOpened)
                .build();
    }

    public PackageOfferResponseForTrip getPackageOfferEditForTrip(Long id) {
        PackageOfferEntity packageOfferEntity = packageOfferRepository.findByIdAndStatusIn(id, List.of(RAW, CARD, HIDDEN))
                .orElseThrow(() -> new CustomNotFoundException(OFFER_NOT_FOUND));
        return packageOfferMapper.toPackageOfferResponseForTrip(packageOfferEntity);

    }

    public void changeOfferStatusCard(Long id) {
        PackageOfferEntity packageOfferEntity = packageOfferRepository.findByIdAndStatus(id, RAW)
                .orElseThrow(() -> new CustomNotFoundException(OFFER_NOT_FOUND));
        if (!Objects.equals(getLoginUser().getId(), packageOfferEntity.getPack().getUser().getId())) {
            throw new ForbiddenException(ACCESS_DENIED);
        }
        packageOfferEntity.setStatus(CARD);
        packageOfferRepository.save(packageOfferEntity);
    }


    public void changeOfferStatusHidden(Long id) {
        PackageOfferEntity packageOfferEntity = packageOfferRepository.findByIdAndStatus(id, RAW)
                .orElseThrow(() -> new CustomNotFoundException(OFFER_NOT_FOUND));
        if (!Objects.equals(getLoginUser().getId(), packageOfferEntity.getPack().getUser().getId())) {
            throw new ForbiddenException(ACCESS_DENIED);
        }
        packageOfferEntity.setStatus(HIDDEN);
        packageOfferRepository.save(packageOfferEntity);
    }

    public void changeOfferStatusRaw(Long id) {
        PackageOfferEntity packageOfferEntity = packageOfferRepository.findByIdAndStatusIn(id, List.of(CARD, HIDDEN))
                .orElseThrow(() -> new CustomNotFoundException(OFFER_NOT_FOUND));
        if (!Objects.equals(getLoginUser().getId(), packageOfferEntity.getPack().getUser().getId())) {
            throw new ForbiddenException(ACCESS_DENIED);
        }
        packageOfferEntity.setStatus(RAW);
        packageOfferRepository.save(packageOfferEntity);
    }

    @Transactional
    public void changeOfferStatusOpen(Long id) {
        List<PackageOfferEntity> packageOfferEntities =
                packageOfferRepository.findByPackIdAndStatus(id, CARD);
        if (packageOfferEntities.isEmpty()) {
            throw new CustomNotFoundException(OFFER_NOT_FOUND);
        }
        if (!Objects.equals(getLoginUser().getId(), packageOfferEntities.get(0).getPack().getUser().getId())) {
            throw new ForbiddenException(ACCESS_DENIED);
        }
        packageOfferEntities.forEach(p -> p.setStatus(OPENED));
    }
}
