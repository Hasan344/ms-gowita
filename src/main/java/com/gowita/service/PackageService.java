package com.gowita.service;

import static com.gowita.constant.CommonConstant.ACCESS_DENIED;
import static com.gowita.constant.CommonConstant.PACKAGE_NOT_FOUND;
import static com.gowita.constant.CommonConstant.PACKAGE_URL;
import static com.gowita.util.SecurityUtil.getCurrentUser;
import static com.gowita.util.SecurityUtil.getLoginUser;

import com.gowita.constant.Status;
import com.gowita.dto.filter.PackageFilter;
import com.gowita.dto.request.ChangeStatusRequest;
import com.gowita.dto.request.PackageEditRequest;
import com.gowita.dto.request.PackageRequest;
import com.gowita.dto.response.PackageAdminResponse;
import com.gowita.dto.response.PackageDetailAdminResponse;
import com.gowita.dto.response.PackageDetailHomePageResponse;
import com.gowita.dto.response.PackageDetailUserResponse;
import com.gowita.dto.response.PackageEditDetailUserResponse;
import com.gowita.dto.response.PackageHomePageResponse;
import com.gowita.dto.response.PackageResponseForSelect;
import com.gowita.dto.response.PackageUserResponse;
import com.gowita.entity.CityEntity;
import com.gowita.entity.FilePathEntity;
import com.gowita.entity.PackageEntity;
import com.gowita.entity.PackageTypeEntity;
import com.gowita.entity.UserEntity;
import com.gowita.exception.CustomBadRequestException;
import com.gowita.exception.CustomNotFoundException;
import com.gowita.exception.ForbiddenException;
import com.gowita.mapper.PackageMapper;
import com.gowita.repository.FilePathRepository;
import com.gowita.repository.PackageOfferRepository;
import com.gowita.repository.PackageRepository;
import com.gowita.repository.TripOfferRepository;
import com.gowita.repository.UserRepository;
import com.gowita.util.FileUtil;
import java.time.LocalDate;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PackageService {

    FileUtil fileUtil;
    PackageMapper packageMapper;
    UserRepository userRepository;
    PackageRepository packageRepository;
    FilePathRepository filePathRepository;
    TripOfferRepository tripOfferRepository;
    PackageOfferRepository packageOfferRepository;

    @Transactional
    public void create(PackageRequest request) {
        log.info("Package create process is started. request - {}", request);
        PackageEntity packageEntity = packageMapper.toPackageEntity(request);
        List<FilePathEntity> filePathEntities = getFilePathEntities(request.getFiles(), packageEntity);
        packageEntity.setFilePaths(filePathEntities);
        packageEntity.setUser(getLoginUser());
        packageEntity.setStatus(Status.PENDING);
        packageRepository.save(packageEntity);
    }

    @Transactional
    public void edit(PackageEditRequest request, Long id) {
        log.info("Package edit process is started. request - {}, id - {}", request, id);
        int size = request.getFiles().size() + request.getFilePaths().size();
        if (size != 2 && size != 1) {
            throw new CustomBadRequestException("Files size must be 1 or 2");
        }
        PackageEntity packageEntity = packageRepository.findByIdAndUser(id, getLoginUser())
                .orElseThrow(() -> new CustomNotFoundException(PACKAGE_NOT_FOUND));
        packageEntity.setFromWhere(CityEntity.builder().id(request.getFromWhereId()).build());
        packageEntity.setToWhere(CityEntity.builder().id(request.getToWhereId()).build());
        packageEntity.setWeight(request.getWeight());
        packageEntity.setWidth(request.getWidth());
        packageEntity.setLength(request.getLength());
        packageEntity.setHeight(request.getHeight());
        packageEntity.setPackageType(PackageTypeEntity.builder().id(request.getTypeId()).build());
        packageEntity.setConductDate(request.getConductDate());
        packageEntity.setDeliveryDate(request.getDeliveryDate());
        List<FilePathEntity> filePathEntities = editPackageFiles(request, packageEntity);
        packageEntity.setFilePaths(filePathEntities);
        packageEntity.setStatus(Status.PENDING);
        PackageEntity packageEntity1 = packageRepository.saveAndFlush(packageEntity);
        log.info("Package edit process is finished. id - {}, paths -{}", id, packageEntity1.getFilePaths().toString());
        log.info(String.valueOf(packageEntity1.getFilePaths()));
    }

    private List<FilePathEntity> editPackageFiles(PackageEditRequest request, PackageEntity packageEntity) {
        List<FilePathEntity> filePathEntities = new ArrayList<>();
        if (packageEntity.getFilePaths() != null) {
            for (FilePathEntity filePath : packageEntity.getFilePaths()) {
                if (!request.getFilePaths().contains(filePath.getPath())) {
                    log.info("Deleted file name is -{}", filePath.getPath());
                    filePathRepository.delete(filePath);
                    fileUtil.deleteFile(PACKAGE_URL + filePath.getPath());
                } else {
                    filePathEntities.add(filePath);
                }
            }
        }
        if (request.getFiles() != null) {
            for (MultipartFile file : request.getFiles()) {
                String name = fileUtil.save(file, PACKAGE_URL);
                FilePathEntity filePathEntity = FilePathEntity.builder()
                        .path(name)
                        .pack(packageEntity).build();
                filePathEntities.add(filePathEntity);
                log.info("New File saved. path - {}", name);
            }
        }
        log.info(String.valueOf(filePathEntities));
        return filePathEntities;
    }

    private List<FilePathEntity> getFilePathEntities(MultipartFile[] files, PackageEntity packageEntity) {
        List<FilePathEntity> filePathEntities = new ArrayList<>();
        if (files != null) {
            for (MultipartFile file : files) {
                filePathEntities.add(FilePathEntity.builder()
                        .path(fileUtil.save(file, "/packages/"))
                        .pack(packageEntity)
                        .build());
            }
        }
        return filePathEntities;
    }

    public void delete(Long id) {
        PackageEntity packages = packageRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(PACKAGE_NOT_FOUND));
        packages.setStatus(Status.DELETED);
        packageRepository.save(packages);
    }

    @Transactional
    public PackageDetailHomePageResponse get(Long id) {
        var currentUser = getCurrentUser();
        PackageEntity packageEntity = packageRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(PACKAGE_NOT_FOUND));
        var response = packageMapper.toPackageDetailHomePageResponse(packageEntity);
        if (currentUser.isPresent()) {
            boolean isMine = Objects.equals(packageEntity.getUser().getId(), currentUser.get().getId());
            response.setMine(isMine);
            if (!isMine) {
                boolean alreadyOffered = packageEntity.getPackageOffers().stream()
                        .anyMatch(p -> Objects.equals(p.getTrip().getUser().getId(), currentUser.get().getId()));
                response.setAlreadyOffered(alreadyOffered);
            }
        }
        return response;
    }

    public Page<PackageHomePageResponse> getAll(PackageFilter filter, Pageable pageable) {
        setDefaultFilter(filter);
        validateFilter(filter);

        Page<PackageEntity> packageEntities = packageRepository.findAll((Specification<PackageEntity>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), Status.ACTIVE));
            if (filter.getMaxWeight() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("weight"), filter.getMaxWeight()));
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
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("deliveryDate"), filter.getDateFrom(), filter.getDateTo()));
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.between(root.get("conductDate"), filter.getConductDateFrom(), filter.getConductDateTo()));

            query.orderBy(criteriaBuilder.asc(root.get("updatedAt")));
            return predicate;
        }, pageable);
        return packageEntities.map(packageMapper::toPackageHomePageResponse);
    }

    private void validateFilter(PackageFilter filter) {
        if (filter.getDateFrom().isAfter(filter.getDateTo())
                || filter.getConductDateFrom().isAfter(filter.getConductDateTo())) {
            throw new CustomBadRequestException("Start date must not be after end date");
        }
    }

    private void setDefaultFilter(PackageFilter filter) {
        filter.setMinHeight(filter.getMinHeight() == null ? 0.0 : filter.getMinHeight());
        filter.setMinLength(filter.getMinLength() == null ? 0.0 : filter.getMinLength());
        filter.setMinWidth(filter.getMinWidth() == null ? 0.0 : filter.getMinWidth());
        filter.setMinWeight(filter.getMinWeight() == null ? 0.0 : filter.getMinWeight());

        filter.setMaxHeight(filter.getMaxHeight() == null ? Double.MAX_VALUE : filter.getMaxHeight());
        filter.setMaxLength(filter.getMaxLength() == null ? Double.MAX_VALUE : filter.getMaxLength());
        filter.setMaxWidth(filter.getMaxWidth() == null ? Double.MAX_VALUE : filter.getMaxWidth());
        filter.setMaxWeight(filter.getMaxWeight() == null ? Double.MAX_VALUE : filter.getMaxWeight());

        filter.setDateFrom(filter.getDateFrom() == null ? LocalDate.now() : filter.getDateFrom());
        filter.setDateTo(filter.getDateTo() == null ? LocalDate.now().plusYears(3) : filter.getDateTo());
        filter.setConductDateFrom(filter.getConductDateFrom() == null ? LocalDate.now() : filter.getConductDateFrom());
        filter.setConductDateTo(filter.getConductDateTo() == null ? LocalDate.now().plusYears(3) : filter.getConductDateTo());
    }

    public Page<PackageAdminResponse> getPackagesForAdmin(Status status, String phoneNumber, Pageable page) {
        log.info("Get Packages For Admin process was started: status - {}, phoneNumber -{}", status, phoneNumber);
        Page<PackageEntity> packageEntities;
        if (phoneNumber == null || phoneNumber.isBlank()) {
            packageEntities = status == null
                    ? packageRepository.findByOrderByUpdatedAtAsc(page)
                    : packageRepository.findByStatusOrderByUpdatedAtAsc(status, page);
        } else {
            UserEntity user = userRepository.findByPhoneNumber(phoneNumber)
                    .orElse(null);
            packageEntities = status == null
                    ? packageRepository.findByUserOrderByUpdatedAtAsc(user, page)
                    : packageRepository.findByUserAndStatusOrderByUpdatedAtAsc(user, status, page);
        }
        return packageEntities.map(packageMapper::toPackageAdminResponses);
    }

    public Long getPendingCount() {
        return packageRepository.countByStatus(Status.PENDING);
    }

    public void changePackageStatus(Long id, ChangeStatusRequest request) {
        PackageEntity packageEntity = packageRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(PACKAGE_NOT_FOUND));
        if (request.getStatus() == Status.ACTIVE) {
            packageEntity.setStatus(Status.ACTIVE);
            packageEntity.setHasNotification(true);
        } else if (request.getStatus() == Status.REJECTED) {
            packageEntity.setStatus(Status.REJECTED);
            packageEntity.setRejectMessage(request.getRejectMessage());
            packageEntity.setHasNotification(true);
        }
        packageRepository.save(packageEntity);
    }

    public PackageDetailAdminResponse getPackageDetailForAdmin(Long id) {
        PackageEntity packageEntity = packageRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(PACKAGE_NOT_FOUND));

        return packageMapper.toPackageDetailAdminResponse(packageEntity);
    }

    public Page<PackageUserResponse> getPackagesForUser(Status status, Pageable page) {
        UserEntity user = getLoginUser();
        Page<PackageEntity> packageEntities = status == null
                ? packageRepository.findByUserOrderByUpdatedAtDesc(user, page)
                : packageRepository.findByUserAndStatusOrderByUpdatedAtDesc(user, status, page);

        return packageEntities.map(packageMapper::toPackageUserResponse);
    }

    public PackageDetailUserResponse getPackageDetailForUser(Long id) {
        PackageEntity packageEntity = packageRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Not found"));
        if (Objects.equals(packageEntity.getUser().getId(), getLoginUser().getId())) {
            boolean hasOfferPack =
                    packageOfferRepository.existsByPackId(packageEntity.getId());
            boolean hasOfferTrip =
                    tripOfferRepository.existsByPackId(packageEntity.getId());
            long myRequestCount = tripOfferRepository.countByPackId(id);
            long contactRequestsCount = packageOfferRepository.countByPackId(id);
            return packageMapper.toPackageDetailUserResponse(packageEntity,
                    !(hasOfferPack || hasOfferTrip), myRequestCount, contactRequestsCount);
        } else {
            throw new ForbiddenException(ACCESS_DENIED);
        }
    }

    public PackageEditDetailUserResponse getPackageEditDetailForUser(Long id) {
        PackageEntity packageEntity = packageRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(PACKAGE_NOT_FOUND));
        return packageMapper.toPackageEditDetailUserResponse(packageEntity);
    }

    public void changeStatusForUser(Long id, Status status) {
        PackageEntity packageEntity = packageRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(PACKAGE_NOT_FOUND));
        if (status == Status.INACTIVE || status == Status.PENDING) {
            packageEntity.setStatus(status);
        } else {
            throw new CustomBadRequestException("Not Allowed");
        }
        packageRepository.save(packageEntity);
    }

    public List<PackageResponseForSelect> getPackagesForPackageOfferSelect() {
        UserEntity user = getLoginUser();
        List<PackageEntity> packageEntities = packageRepository.findByUserAndStatusOrderByUpdatedAtAsc(user, Status.ACTIVE);
        return packageMapper.toPackageResponseForSelect(packageEntities);

    }
}
