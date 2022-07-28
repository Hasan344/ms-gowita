package com.gowita.service;

import com.gowita.dto.request.PackageRejectMessageRequest;
import com.gowita.dto.response.PackageMessageResponse;
import com.gowita.entity.PackageRejectMessageEntity;
import com.gowita.exception.CustomNotFoundException;
import com.gowita.mapper.PackageMessageMapper;
import com.gowita.repository.PackageRejectMessageRepository;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PackageRejectMessageService {

    PackageMessageMapper packageMessageMapper;
    PackageRejectMessageRepository packageRejectMessageRepository;

    public List<PackageMessageResponse> getAll() {
        var messageEntities = packageRejectMessageRepository.findAll();
        return packageMessageMapper.toMessageResponse(messageEntities);
    }

    public void create(PackageRejectMessageRequest request) {
        var rejectMessageEntity = new PackageRejectMessageEntity(request.getMessage());
        packageRejectMessageRepository.save(rejectMessageEntity);
    }

    public void delete(Long id) {
        var messageEntity = packageRejectMessageRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Message is not found"));
        packageRejectMessageRepository.delete(messageEntity);
    }

    public void update(PackageRejectMessageRequest request, Long id) {
        PackageRejectMessageEntity packageMessageEntity = packageRejectMessageRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Message is not found"));
        packageMessageEntity.setMessage(request.getMessage());
        packageRejectMessageRepository.save(packageMessageEntity);
    }
}
