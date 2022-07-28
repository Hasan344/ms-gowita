package com.gowita.service;

import static com.gowita.constant.CommonConstant.ACCESS_DENIED;
import static com.gowita.constant.CommonConstant.OFFER_NOT_FOUND;
import static com.gowita.constant.TripOfferStatus.CARD;
import static com.gowita.constant.TripOfferStatus.CONFIRMED;
import static com.gowita.constant.TripOfferStatus.OPENED;
import static com.gowita.constant.TripOfferStatus.PENDING;
import static com.gowita.constant.TripOfferStatus.REJECTED;
import static com.gowita.util.SecurityUtil.getLoginUser;

import com.gowita.constant.TripOfferStatus;
import com.gowita.dto.request.TripOfferRequest;
import com.gowita.dto.response.CountTripOfferStatus;
import com.gowita.dto.response.PackageDetailsForTripOffer;
import com.gowita.dto.response.TripOfferResponseForPackage;
import com.gowita.dto.response.TripOfferResponseForTrip;
import com.gowita.entity.TripEntity;
import com.gowita.entity.TripOfferEntity;
import com.gowita.entity.UserEntity;
import com.gowita.exception.CustomBadRequestException;
import com.gowita.exception.CustomNotFoundException;
import com.gowita.exception.ForbiddenException;
import com.gowita.mapper.TripOfferMapper;
import com.gowita.repository.PackageRepository;
import com.gowita.repository.TripOfferRepository;
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

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TripOfferService {

    TripRepository tripRepository;
    TripOfferMapper tripOfferMapper;
    PackageRepository packageRepository;
    TripOfferRepository tripOfferRepository;

    @Transactional
    public void create(TripOfferRequest request) {
        log.info("TripOffer create process is started. request - {}", request);
        UserEntity loginUser = getLoginUser();
        TripEntity trip = tripRepository.getById(request.getTripId());
        UserEntity tripUser = trip.getUser();
        UserEntity packageUser = packageRepository.getById(request.getPackageId()).getUser();
        if (loginUser != packageUser && packageUser == tripUser) {
            log.info("Access denied for request. loginUser - {}, tripUser - {}, packageUser - {}", loginUser, tripUser, packageUser);
            throw new ForbiddenException(ACCESS_DENIED);
        }
        trip.setHasNotification(true);
        var tripOffer = tripOfferMapper.toTripOfferEntity(request);
        tripOffer.setStatus(TripOfferStatus.PENDING);
        tripOfferRepository.save(tripOffer);
    }

    public Page<TripOfferResponseForPackage> getTripOfferForPackage(Long id, TripOfferStatus status, Pageable page) {
        Page<TripOfferEntity> tripOfferEntities;
        if (status == null) {
            tripOfferEntities = tripOfferRepository.findByPackIdOrderByUpdatedAtAsc(id, page);
        } else if (status == OPENED) {
            tripOfferEntities = tripOfferRepository.findByPackIdAndStatusOrderByUpdatedAtDesc(id, status, page);
            return tripOfferEntities.map(tripOfferMapper::toTripOfferResponseForOpenedPackage);
        } else {
            tripOfferEntities = tripOfferRepository.findByPackIdAndStatusOrderByUpdatedAtDesc(id, status, page);
        }
        return tripOfferEntities.map(tripOfferMapper::toTripOfferResponseForPackage);
    }

    public Page<TripOfferResponseForTrip> getTripContactOffersForTrip(Long id, TripOfferStatus status, Pageable page) {
        Page<TripOfferEntity> tripOfferEntities;
        if (status == null) {
            tripOfferEntities = tripOfferRepository.findByTripIdOrderByUpdatedAtAsc(id, page);
            return tripOfferEntities.map(tripOfferMapper::toTripOfferResponseForAllTrip);
        } else if (status == PENDING) {
            tripOfferEntities =
                    tripOfferRepository.findByTripIdAndStatusInOrderByUpdatedAtDesc(id, List.of(PENDING, CARD), page);
        } else if (status == CARD) {
            throw new CustomBadRequestException("Status must not be CARD");
        } else if (status == OPENED) {
            tripOfferEntities = tripOfferRepository.findByTripIdAndStatusOrderByUpdatedAtDesc(id, OPENED, page);
            return tripOfferEntities.map(tripOfferMapper::toTripOfferResponseForOpenedTrip);
        } else {
            tripOfferEntities = tripOfferRepository.findByTripIdAndStatusOrderByUpdatedAtDesc(id, status, page);
        }
        return tripOfferEntities.map(tripOfferMapper::toTripOfferResponseForTrip);
    }

    public void changeOfferStatusConfirmed(Long id) {
        TripOfferEntity tripOfferEntity = tripOfferRepository
                .findByIdAndStatusIn(id, List.of(PENDING, REJECTED, CARD))
                .orElseThrow(() -> new CustomNotFoundException(OFFER_NOT_FOUND));
        if (!Objects.equals(getLoginUser().getId(), tripOfferEntity.getTrip().getUser().getId())) {
            throw new ForbiddenException(ACCESS_DENIED);
        }
        tripOfferEntity.setStatus(CONFIRMED);
        tripOfferRepository.save(tripOfferEntity);
    }

    public void changOfferStatusReject(Long id) {
        TripOfferEntity tripOfferEntity = tripOfferRepository
                .findByIdAndStatusIn(id, List.of(PENDING, CONFIRMED))
                .orElseThrow(() -> new CustomNotFoundException(OFFER_NOT_FOUND));
        if (!Objects.equals(getLoginUser().getId(), tripOfferEntity.getTrip().getUser().getId())) {
            throw new ForbiddenException(ACCESS_DENIED);
        }
        tripOfferEntity.setStatus(REJECTED);
        tripOfferRepository.save(tripOfferEntity);
    }

    public void changeOfferStatusDelete(Long id) {
        TripOfferEntity tripOfferEntity = tripOfferRepository
                .findByIdAndStatusIn(id, List.of(PENDING, CONFIRMED))
                .orElseThrow(() -> new CustomNotFoundException(OFFER_NOT_FOUND));
        if (!Objects.equals(getLoginUser().getId(), tripOfferEntity.getPack().getUser().getId())) {
            throw new ForbiddenException(ACCESS_DENIED);
        }
        tripOfferRepository.deleteById(id);
    }

    public void changeOfferStatusCard(Long id) {
        TripOfferEntity tripOfferEntity = tripOfferRepository.findByIdAndStatus(id, CONFIRMED)
                .orElseThrow(() -> new CustomNotFoundException(OFFER_NOT_FOUND));
        if (!Objects.equals(getLoginUser().getId(), tripOfferEntity.getPack().getUser().getId())) {
            throw new ForbiddenException(ACCESS_DENIED);
        }
        tripOfferEntity.setStatus(CARD);
        tripOfferRepository.save(tripOfferEntity);
    }

    public void changeOfferStatusOpen(Long id) {
        TripOfferEntity tripOfferEntity = tripOfferRepository.findByIdAndStatus(id, CARD)
                .orElseThrow(() -> new CustomNotFoundException(OFFER_NOT_FOUND));
        if (!Objects.equals(getLoginUser().getId(), tripOfferEntity.getPack().getUser().getId())) {
            throw new ForbiddenException(ACCESS_DENIED);
        }
        tripOfferEntity.setStatus(OPENED);

        tripOfferRepository.save(tripOfferEntity);
    }

    public PackageDetailsForTripOffer getPackageDetailsForTripOffer(Long id) {
        TripOfferEntity tripOfferEntity = tripOfferRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(OFFER_NOT_FOUND));
        return tripOfferMapper.toPackageDetailsForTripOffer(tripOfferEntity);
    }

    public CountTripOfferStatus getTripOfferCountForPackage(Long id) {
        Long confirmed = tripOfferRepository.countByPackIdAndStatus(id, CONFIRMED);
        Long rejected = tripOfferRepository.countByPackIdAndStatus(id, REJECTED);
        Long pending = tripOfferRepository.countByPackIdAndStatus(id, PENDING);
        Long card = tripOfferRepository.countByPackIdAndStatus(id, CARD);
        Long opened = tripOfferRepository.countByPackIdAndStatus(id, OPENED);
        Long all = tripOfferRepository.countByPackId(id);

        return CountTripOfferStatus.builder()
                .countConfirmed(confirmed)
                .countRejected(rejected)
                .countPending(pending)
                .countCard(card)
                .countOpened(opened)
                .countAll(all)
                .build();
    }

    public CountTripOfferStatus getTripOfferCountForTrip(Long id) {
        Long pending = tripOfferRepository.countByTripIdAndStatusIn(id, List.of(PENDING, CARD));
        Long confirmed = tripOfferRepository.countByTripIdAndStatusIn(id, List.of(CONFIRMED));
        Long rejected = tripOfferRepository.countByTripIdAndStatusIn(id, List.of(REJECTED));
        Long opened = tripOfferRepository.countByTripIdAndStatusIn(id, List.of(OPENED));
        Long all = tripOfferRepository.countByTripId(id);

        return CountTripOfferStatus.builder()
                .countConfirmed(confirmed)
                .countRejected(rejected)
                .countPending(pending)
                .countOpened(opened)
                .countAll(all)
                .build();
    }
}
