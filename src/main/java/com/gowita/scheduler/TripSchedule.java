package com.gowita.scheduler;

import com.gowita.constant.Status;
import com.gowita.repository.TripRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class TripSchedule {
    private final TripRepository tripRepository;

    @Transactional
    @Scheduled(fixedDelay = 60 * 10 * 1000)
    public void changeOutDated() {
        tripRepository.findAllWithDateTimeNowBefore(LocalDateTime.now(),
                Status.OUTDATED,
                List.of(Status.ACTIVE, Status.PENDING, Status.REJECTED));
    }
}
