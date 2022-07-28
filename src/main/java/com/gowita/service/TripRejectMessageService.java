package com.gowita.service;

import com.gowita.dto.request.TripRejectMessageRequest;
import com.gowita.dto.response.TripMessageResponse;
import com.gowita.entity.TripRejectMessageEntity;
import com.gowita.exception.CustomNotFoundException;
import com.gowita.mapper.TripMessageMapper;
import com.gowita.repository.TripRejectMessageRepository;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TripRejectMessageService {

    TripMessageMapper tripMessageMapper;
    TripRejectMessageRepository tripRejectMessageRepository;

    public List<TripMessageResponse> getAll() {
        var messageEntities = tripRejectMessageRepository.findAll();
        return tripMessageMapper.toTripResponse(messageEntities);
    }

    public void create(TripRejectMessageRequest message) {
        var messageEntity = TripRejectMessageEntity.builder()
                .message(message.getMessage()).build();
        tripRejectMessageRepository.save(messageEntity);
    }

    public void delete(Long id) {
        var messageEntity = tripRejectMessageRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Message is not found"));
        tripRejectMessageRepository.delete(messageEntity);
    }

    @Transactional
    public void update(TripRejectMessageRequest message, Long id) {
        TripRejectMessageEntity tripRejectMessageEntity = tripRejectMessageRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Message is not found"));
        tripRejectMessageEntity.setMessage(message.getMessage());
    }
}
