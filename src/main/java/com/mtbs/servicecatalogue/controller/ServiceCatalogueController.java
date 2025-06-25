package com.mtbs.servicecatalogue.controller;

import com.mtbs.servicecatalogue.dto.CreateServiceRequest;
import com.mtbs.servicecatalogue.dto.ServiceResponse;
import com.mtbs.servicecatalogue.service.ServiceCatalogue;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
@Slf4j
public class ServiceCatalogueController {

    private final ServiceCatalogue serviceCatalogue;


    @PostMapping
    public ResponseEntity<ServiceResponse> createService(@Valid @RequestBody CreateServiceRequest request) {
        log.info("Received request to create service with name: {}", request.getName());
        ServiceResponse response = serviceCatalogue.createService(request);
        log.info("Successfully created service with ID: {}", response.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ServiceResponse>> getAllServices(Pageable pageable) {
        log.info("Received request to get services with pagination: {}", pageable);
        Page<ServiceResponse> services = serviceCatalogue.getAllServices(pageable);
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> getServiceById(@PathVariable UUID id) {
        ServiceResponse response = serviceCatalogue.getServiceById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> updateService(@PathVariable UUID id, @Valid @RequestBody CreateServiceRequest request) {
        ServiceResponse updatedService = serviceCatalogue.updateService(id, request);
        return ResponseEntity.ok(updatedService);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable UUID id) {
        serviceCatalogue.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
