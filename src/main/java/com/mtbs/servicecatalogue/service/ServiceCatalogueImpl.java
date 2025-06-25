package com.mtbs.servicecatalogue.service;

import com.mtbs.servicecatalogue.dto.CreateServiceRequest;
import com.mtbs.servicecatalogue.dto.ServiceResponse;
import com.mtbs.servicecatalogue.exception.ServiceNotFoundException;
import com.mtbs.servicecatalogue.mapper.ServiceMapper;
import com.mtbs.servicecatalogue.model.ServiceModel;
import com.mtbs.servicecatalogue.repository.ServiceRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceCatalogueImpl implements ServiceCatalogue {

    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    @Override
    @Transactional
    public ServiceResponse createService(CreateServiceRequest request) {
        log.info("Creating a new service with name: {}", request.getName());
        ServiceModel serviceModel = serviceMapper.toEntity(request);
        if (request.getActive() == null) {
            serviceModel.setActive(true);
        } else {
            serviceModel.setActive(request.getActive());
        }
        ServiceModel savedService = serviceRepository.save(serviceModel);
        log.info("Successfully created service with ID: {}", savedService.getId());
        return serviceMapper.toResponse(savedService);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServiceResponse> getAllServices(Pageable pageable) {
        log.info("Fetching services from the database with pagination: {}", pageable);
        Page<ServiceModel> servicePage = serviceRepository.findAll(pageable);
        log.info("Found {} services on page {} of {}.", servicePage.getNumberOfElements(), servicePage.getNumber(), servicePage.getTotalPages());
        return servicePage.map(serviceMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceResponse getServiceById(UUID id) {
        log.info("Fetching service with ID: {}", id);
        return serviceRepository.findById(id)
                .map(serviceMapper::toResponse)
                .orElseThrow(() -> new ServiceNotFoundException("Service with ID " + id + " not found."));
    }

    @Override
    @Transactional
    public ServiceResponse updateService(UUID id, CreateServiceRequest request) {
        log.info("Updating service with ID: {}", id);
        ServiceModel existingService = serviceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException("Service with ID " + id + " not found for update."));

        serviceMapper.updateEntityFromDto(request, existingService);
        if (request.getActive() != null) {
            existingService.setActive(request.getActive());
        }
        ServiceModel updatedService = serviceRepository.save(existingService);
        log.info("Successfully updated service with ID: {}", updatedService.getId());
        return serviceMapper.toResponse(updatedService);
    }

    @Override
    @Transactional
    public void deleteService(UUID id) {
        log.info("Attempting to delete service with ID: {}", id);
        if (!serviceRepository.existsById(id)) {
            throw new ServiceNotFoundException("Service with ID " + id + " not found for deletion.");
        }
        serviceRepository.deleteById(id);
        log.info("Successfully deleted service with ID: {}", id);
    }
}