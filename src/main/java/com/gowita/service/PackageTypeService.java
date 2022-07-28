package com.gowita.service;

import com.gowita.dto.request.PackageTypeRequest;
import com.gowita.dto.response.PackageTypeResponse;
import com.gowita.dto.response.PackageTypeResponseForSelect;
import com.gowita.entity.PackageTypeEntity;
import com.gowita.exception.AlreadyExistException;
import com.gowita.exception.CustomNotFoundException;
import com.gowita.mapper.PackageTypeMapper;
import com.gowita.repository.PackageTypeRepository;
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
public class PackageTypeService {

    PackageTypeMapper packageTypeMapper;
    PackageTypeRepository packageTypeRepository;

    public List<PackageTypeResponse> getAll() {
        log.info("getAll packageType");
        List<PackageTypeEntity> packageTypeEntities = packageTypeRepository.findAll();
        return packageTypeMapper.toPackageTypeResponses(packageTypeEntities);
    }

    public List<PackageTypeResponseForSelect> getAllForSelect() {
        log.info("getAll packageType");
        List<PackageTypeEntity> packageTypeEntities = packageTypeRepository.findAll();
        return packageTypeMapper.toPackageTypeResponsesForSelect(packageTypeEntities);
    }

    public void edit(PackageTypeRequest request, Long id) {
        log.info("Edit packageType");
        var packageTypeEntity = packageTypeRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("not found"));
        packageTypeEntity.setName(request.getName());
        packageTypeRepository.save(packageTypeEntity);
    }

    public void create(PackageTypeRequest request) {
        log.info("create packageType: ");
        if (packageTypeRepository.existsByName(request.getName())) {
            throw new AlreadyExistException("Type has already exited");
        }
        packageTypeRepository.save(packageTypeMapper.toPackageTypeEntity(request));
    }

    public void delete(Long id) {
        var packageTypeEntity = packageTypeRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("not found"));
        packageTypeRepository.delete(packageTypeEntity);
    }
}
