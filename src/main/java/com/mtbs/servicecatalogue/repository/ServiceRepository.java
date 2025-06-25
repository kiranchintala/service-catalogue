package com.mtbs.servicecatalogue.repository;

import com.mtbs.servicecatalogue.model.ServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA repository for the ServiceModel entity.
 */
@Repository
public interface ServiceRepository extends JpaRepository<ServiceModel, UUID> {
}
