package com.mtbs.servicecatalogue.service;

import com.mtbs.servicecatalogue.dto.CreateServiceRequest;
import com.mtbs.servicecatalogue.dto.ServiceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Interface defining the business logic for the service catalogue.
 * It provides a contract for creating, retrieving, updating, and deleting services.
 */
public interface ServiceCatalogue {

    /**
     * Creates a new service based on the provided request data.
     *
     * @param request The DTO containing the details for the new service.
     * @return A DTO representing the newly created service.
     */
    ServiceResponse createService(CreateServiceRequest request);

    /**
     * Retrieves a list of all available services.
     *
     * @return A list of DTOs, each representing a service.
     */
    Page<ServiceResponse> getAllServices(Pageable pageable);

    /**
     * Retrieves a single service by its unique identifier.
     *
     * @param id The UUID of the service to retrieve.
     * @return A DTO representing the found service.
     * @throws com.mtbs.servicecatalogue.exception.ServiceNotFoundException if no service with the given ID is found.
     */
    ServiceResponse getServiceById(UUID id);

    /**
     * Updates an existing service with new details.
     *
     * @param id      The UUID of the service to update.
     * @param request The DTO containing the updated information.
     * @return A DTO representing the updated service.
     * @throws com.mtbs.servicecatalogue.exception.ServiceNotFoundException if no service with the given ID is found.
     */
    ServiceResponse updateService(UUID id, CreateServiceRequest request);

    /**
     * Deletes a service from the catalogue.
     *
     * @param id The UUID of the service to delete.
     * @throws com.mtbs.servicecatalogue.exception.ServiceNotFoundException if no service with the given ID is found.
     */
    void deleteService(UUID id);
}
