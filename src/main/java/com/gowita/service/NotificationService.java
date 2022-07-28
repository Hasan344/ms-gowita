package com.gowita.service;

import static com.gowita.constant.Status.ACTIVE;
import static com.gowita.constant.Status.OUTDATED;
import static com.gowita.constant.Status.REJECTED;
import static com.gowita.util.SecurityUtil.getLoginUser;

import com.gowita.dto.response.UserNotificationByStatus;
import com.gowita.dto.response.UserNotificationResponse;
import com.gowita.entity.PackageEntity;
import com.gowita.entity.TripEntity;
import com.gowita.entity.UserEntity;
import com.gowita.repository.PackageRepository;
import com.gowita.repository.TripRepository;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationService {

    TripRepository tripRepository;
    PackageRepository packageRepository;

    public UserNotificationResponse getUserNotification() {
        UserEntity loginUser = getLoginUser();
        boolean existsByTrip = tripRepository.existsByUserAndHasNotificationIsTrue(loginUser);
        boolean existsByPackage = packageRepository.existsByUserAndHasNotificationIsTrue(loginUser);
        return UserNotificationResponse.builder()
                .packageNotification(existsByPackage)
                .tripNotification(existsByTrip)
                .allNotification(existsByTrip || existsByPackage)
                .build();
    }

    public UserNotificationByStatus getPackageNotificationByStatus() {
        UserEntity loginUser = getLoginUser();
        boolean active = packageRepository.existsByUserAndHasNotificationIsTrueAndAndStatus(loginUser, ACTIVE);
        boolean rejected = packageRepository.existsByUserAndHasNotificationIsTrueAndAndStatus(loginUser, REJECTED);
        boolean outdated = packageRepository.existsByUserAndHasNotificationIsTrueAndAndStatus(loginUser, OUTDATED);
        return UserNotificationByStatus.builder()
                .active(active)
                .rejected(rejected)
                .outdated(outdated)
                .build();
    }

    public UserNotificationByStatus getTripNotificationByStatus() {
        UserEntity loginUser = getLoginUser();
        boolean active = tripRepository.existsByUserAndHasNotificationIsTrueAndAndStatus(loginUser, ACTIVE);
        boolean rejected = tripRepository.existsByUserAndHasNotificationIsTrueAndAndStatus(loginUser, REJECTED);
        boolean outdated = tripRepository.existsByUserAndHasNotificationIsTrueAndAndStatus(loginUser, OUTDATED);
        return UserNotificationByStatus.builder()
                .active(active)
                .rejected(rejected)
                .outdated(outdated)
                .build();
    }

    public void editPackageNotification(Long packageId) {
        Optional<PackageEntity> packageEntity = packageRepository.findById(packageId);
        if (packageEntity.isPresent()) {
            packageEntity.get().setHasNotification(false);
            packageRepository.save(packageEntity.get());
        }
    }

    public void editTripNotification(Long tripId) {
        Optional<TripEntity> tripEntity = tripRepository.findById(tripId);
        if (tripEntity.isPresent()) {
            tripEntity.get().setHasNotification(false);
            tripRepository.save(tripEntity.get());
        }
    }
}
