package com.gowita.scheduler;

import com.gowita.constant.Status;
import com.gowita.repository.PackageRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PackageSchedule {

    PackageRepository packageRepository;

    @Transactional
    @Scheduled(fixedDelay = 60 * 10 * 1000)
    public void changeOutdated() {
        packageRepository.updateStatusToOutdated(LocalDate.now(),
                Status.OUTDATED,
                List.of(Status.ACTIVE, Status.PENDING, Status.REJECTED));
    }
}
