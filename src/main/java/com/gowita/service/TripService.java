package com.gowita.service;

import static com.gowita.constant.CommonConstant.ACCESS_DENIED;
import static com.gowita.constant.CommonConstant.TRIP_NOT_FOUND;
import static com.gowita.util.SecurityUtil.getCurrentUser;
import static com.gowita.util.SecurityUtil.getLoginUser;

import com.gowita.constant.Status;
import com.gowita.dto.filter.TripFilter;
import com.gowita.dto.request.ChangeStatusRequest;
import com.gowita.dto.request.TripRequest;
import com.gowita.dto.response.TripAdminResponse;
import com.gowita.dto.response.TripDetailAdminResponse;
import com.gowita.dto.response.TripDetailUserResponse;
import com.gowita.dto.response.TripEditDetailUserResponse;
import com.gowita.dto.response.TripHomePageResponse;
import com.gowita.dto.response.TripResponseForSelect;
import com.gowita.dto.response.TripUserResponse;
import com.gowita.entity.CityEntity;
import com.gowita.entity.PackageTypeEntity;
import com.gowita.entity.TripEntity;
import com.gowita.entity.UserEntity;
import com.gowita.exception.CustomBadRequestException;
import com.gowita.exception.CustomNotFoundException;
import com.gowita.exception.ForbiddenException;
import com.gowita.mapper.TripMapper;
import com.gowita.repository.PackageOfferRepository;
import com.gowita.repository.TripOfferRepository;
import com.gowita.repository.TripRepository;
import com.gowita.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TripService {

    TripMapper tripMapper;
    UserRepository userRepository;
    TripRepository tripRepository;
    TripOfferRepository tripOfferRepository;
    PackageOfferRepository packageOfferRepository;

    public void create(TripRequest request) {
        TripEntity tripEntity = tripMapper.toTripEntity(request);
        tripEntity.setStatus(Status.PENDING);
        tripEntity.setUser(getLoginUser());
        tripRepository.save(tripEntity);
    }

    @Transactional
    public void delete(Long id) {
        TripEntity tripEntity = tripRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("not found"));
        tripEntity.setStatus(Status.DELETED);
    }

    public TripHomePageResponse get(Long id) {
        var currentUser = getCurrentUser();
        TripEntity tripEntity = tripRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("not found"));
        var response = tripMapper.toTripResponse(tripEntity);
        if (currentUser.isPresent()) {
            boolean isMine = Objects.equals(tripEntity.getUser().getId(), currentUser.get().getId());
            response.setMine(isMine);
            if (!isMine) {
                boolean alreadyOffered = tripEntity.getTripOffers().stream()
                        .anyMatch(p -> Objects.equals(p.getPack().getUser().getId(), currentUser.get().getId()));
                response.setAlreadyOffered(alreadyOffered);
            }
        }
        return response;
    }

    public Page<TripHomePageResponse> getAll(TripFilter filter, Pageable pageable) {
        log.info("Package getAllProcess is started. request - {}", filter);
        setDefaultFilter(filter);
        validateTripFilter(filter);
        Page<TripEntity> tripEntities = tripRepository.findAll((Specification<TripEntity>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), Status.ACTIVE));
            if (filter.getTripTypes() != null && !filter.getTripTypes().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.in(root.get("tripType")).value(filter.getTripTypes()));
            }
            if (filter.getFromWhereId() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("fromWhere"), filter.getFromWhereId()));
            }
            if (filter.getToWhereId() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("toWhere"), filter.getToWhereId()));
            }
            if (filter.getPackageTypeIdList() != null && !filter.getPackageTypeIdList().isEmpty()) {
                predicate =
                        criteriaBuilder.and(predicate, criteriaBuilder.in(root.get("packageType").get("id")).value(filter.getPackageTypeIdList()));
            }

            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.between(root.get("height"), filter.getMinHeight(), filter.getMaxHeight()));
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.between(root.get("length"), filter.getMinLength(), filter.getMaxLength()));
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.between(root.get("width"), filter.getMinWidth(), filter.getMaxWidth()));
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.between(root.get("weight"), filter.getMinWeight(), filter.getMaxWeight()));
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.between(root.get("arrivalDate"), filter.getArrivalStartDate(), filter.getArrivalEndDate()));
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.between(root.get("departureDate"), filter.getDepartureStartDate(), filter.getDepartureEndDate()));
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.between(root.get("pickUpDate"), filter.getPickUpStartDate(), filter.getPickUpEndDate()));
            query.orderBy(criteriaBuilder.asc(root.get("updatedAt")));
            return predicate;
        }, pageable);
        return tripEntities.map(tripMapper::toTripResponse);
    }

    private void validateTripFilter(TripFilter filter) {
        if (filter.getArrivalStartDate().isAfter(filter.getArrivalEndDate())
                || filter.getDepartureStartDate().isAfter(filter.getDepartureEndDate())
                || filter.getPickUpStartDate().isAfter(filter.getPickUpEndDate())) {
            throw new CustomBadRequestException("Start date must not be after end date");
        }
        if (filter.getMaxHeight() < filter.getMinHeight()) {
            throw new CustomBadRequestException("Min height value must not be granter or equal from max height value");
        }
        if (filter.getMaxLength() < filter.getMinLength()) {
            throw new CustomBadRequestException("Min length value must not be granter or equal from max length value");
        }
        if (filter.getMaxWidth() < filter.getMinWidth()) {
            throw new CustomBadRequestException("Min width value must not be granter or equal from max width value");
        }
        if (filter.getMaxWeight() < filter.getMinWeight()) {
            throw new CustomBadRequestException("Min weight value must not be granter or equal from max weight value");
        }
    }

    private void setDefaultFilter(TripFilter filter) {
        filter.setMinHeight(filter.getMinHeight() == null ? 0.0 : filter.getMinHeight());
        filter.setMinLength(filter.getMinLength() == null ? 0.0 : filter.getMinLength());
        filter.setMinWidth(filter.getMinWidth() == null ? 0.0 : filter.getMinWidth());
        filter.setMinWeight(filter.getMinWeight() == null ? 0.0 : filter.getMinWeight());

        filter.setMaxHeight(filter.getMaxHeight() == null ? Double.MAX_VALUE : filter.getMaxHeight());
        filter.setMaxLength(filter.getMaxLength() == null ? Double.MAX_VALUE : filter.getMaxLength());
        filter.setMaxWidth(filter.getMaxWidth() == null ? Double.MAX_VALUE : filter.getMaxWidth());
        filter.setMaxWeight(filter.getMaxWeight() == null ? Double.MAX_VALUE : filter.getMaxWeight());

        filter.setArrivalStartDate(filter.getArrivalStartDate() == null ? LocalDateTime.now() : filter.getArrivalStartDate());
        filter.setArrivalEndDate(filter.getArrivalEndDate() == null ? LocalDateTime.now().plusYears(3) : filter.getArrivalEndDate());
        filter.setDepartureStartDate(filter.getDepartureStartDate() == null ? LocalDateTime.now() : filter.getDepartureStartDate());
        filter.setDepartureEndDate(filter.getDepartureEndDate() == null ? LocalDateTime.now().plusYears(3) : filter.getDepartureEndDate());
        filter.setPickUpStartDate(filter.getPickUpStartDate() == null ? LocalDate.now() : filter.getPickUpStartDate());
        filter.setPickUpEndDate(filter.getPickUpEndDate() == null ? LocalDate.now().plusYears(3) : filter.getPickUpEndDate());
    }

    @Transactional
    public void changeTripStatus(Long id, ChangeStatusRequest request) {
        TripEntity tripEntity = tripRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Trip is not found"));
        if (request.getStatus() == Status.ACTIVE) {
            tripEntity.setStatus(Status.ACTIVE);
            tripEntity.setHasNotification(true);
        } else if (request.getStatus() == Status.REJECTED) {
            tripEntity.setStatus(Status.REJECTED);
            tripEntity.setRejectMessage(request.getRejectMessage());
            tripEntity.setHasNotification(true);
        }
    }

    public Page<TripAdminResponse> getTripsForAdmin(Status status, String phoneNumber, Pageable page) {
        log.info("Get Trips For Admin process was started: status - {}, phoneNumber -{}", status, phoneNumber);
        Page<TripEntity> tripEntities;
        if (phoneNumber == null || phoneNumber.isBlank()) {
            tripEntities = status == null
                    ? tripRepository.findByOrderByUpdatedAtAsc(page)
                    : tripRepository.findByStatusOrderByUpdatedAtAsc(status, page);
        } else {
            UserEntity user = userRepository.findByPhoneNumber(phoneNumber)
                    .orElse(null);
            tripEntities = status == null
                    ? tripRepository.findByUserOrderByUpdatedAtAsc(user, page)
                    : tripRepository.findByUserAndStatusOrderByUpdatedAtAsc(user, status, page);
        }
        return tripEntities.map(tripMapper::toTripAdminResponses);
    }

    public Long getPendingCount() {
        return tripRepository.countByStatus(Status.PENDING);
    }

    public TripDetailAdminResponse getTripDetailForAdmin(Long id) {

        TripEntity tripEntity = tripRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(TRIP_NOT_FOUND));

        return tripMapper.toTripDetailAdminResponse(tripEntity);
    }

    public Page<TripUserResponse> getTripsForUser(Status status, Pageable page) {
        UserEntity user = getLoginUser();
        Page<TripEntity> tripEntities = status == null
                ? tripRepository.findByUserOrderByUpdatedAtDesc(user, page)
                : tripRepository.findByUserAndStatusOrderByUpdatedAtDesc(user, status, page);
        return tripEntities.map(tripMapper::toTripUserResponses);
    }

    public TripDetailUserResponse getTripDetailForUser(Long id) {
        TripEntity tripEntity = tripRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(TRIP_NOT_FOUND));
        if (Objects.equals(tripEntity.getUser().getId(), getLoginUser().getId())) {
            boolean hasOpenOfferTrip =
                    tripOfferRepository.existsByTripId(tripEntity.getId());
            boolean hasOpenOfferPack =
                    packageOfferRepository.existsByTripId(tripEntity.getId());
            long contactRequestsCount = tripOfferRepository.countByTripId(id);
            long myRequestsCount = packageOfferRepository.countByTripId(id);
            return tripMapper.toTripDetailUserResponse(tripEntity, !(hasOpenOfferPack || hasOpenOfferTrip), contactRequestsCount, myRequestsCount);
        } else {
            throw new ForbiddenException(ACCESS_DENIED);
        }
    }

    @Transactional
    public void changeStatusForUser(Long id, Status status) {
        TripEntity tripEntity = tripRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(TRIP_NOT_FOUND));
        if (Status.INACTIVE == status || Status.PENDING == status) {
            tripEntity.setStatus(status);
        } else {
            throw new CustomBadRequestException("Not Allowed");
        }
    }

    @Transactional
    public void update(TripRequest request, Long id) {
        log.info("Trip edit process is started. request - {}, id -{}", request, id);
        TripEntity tripEntity = tripRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Not found"));
        tripEntity.setFromWhere(CityEntity.builder().id(request.getFromWhereId()).build());
        tripEntity.setToWhere(CityEntity.builder().id(request.getToWhereId()).build());
        tripEntity.setWidth(request.getWidth());
        tripEntity.setLength(request.getLength());
        tripEntity.setHeight(request.getHeight());
        tripEntity.setWeight(request.getWeight());
        tripEntity.setPackageType(PackageTypeEntity.builder().id(request.getPackageTypeId()).build());
        tripEntity.setTripType(request.getTripType());
        tripEntity.setDepartureDate(request.getDepartureDate());
        tripEntity.setArrivalDate(request.getArrivalDate());
        tripEntity.setPickUpDate(request.getPickUpDate());
        tripEntity.setStatus(Status.PENDING);
    }

    @Transactional
    public List<TripResponseForSelect> getTripsForPackageOfferSelect() {
        List<TripEntity> tripEntities = tripRepository.findByUserAndStatusOrderByUpdatedAtAsc(getLoginUser(), Status.ACTIVE);
        return tripMapper.toTripResponseForSelect(tripEntities);
    }

    public TripEditDetailUserResponse getTripEditDetailForUser(Long id) {
        TripEntity tripEntity = tripRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Not found"));
        return tripMapper.toTripEditDetailUserResponse(tripEntity);
    }
}
