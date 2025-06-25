package com.mtbs.servicecatalogue.mapper;

import com.mtbs.servicecatalogue.dto.CreateServiceRequest;
import com.mtbs.servicecatalogue.dto.ServiceResponse;
import com.mtbs.servicecatalogue.model.ServiceModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper for converting between ServiceModel entity and DTOs.
 */
@Mapper(componentModel = "spring")
public interface ServiceMapper {

    ServiceMapper INSTANCE = Mappers.getMapper(ServiceMapper.class);

    // Converts a CreateServiceRequest DTO to a ServiceModel entity.
    // If the isActive field in the request DTO is null (i.e., not provided by the client),
    // this defaultExpression sets it to 'true' in the entity.
    @Mapping(target = "active", ignore = true)
    ServiceModel toEntity(CreateServiceRequest dto);

    // Converts a ServiceModel entity to a ServiceResponse DTO.
    ServiceResponse toResponse(ServiceModel entity);

    // Converts a list of ServiceModel entities to a list of ServiceResponse DT Os.
    List<ServiceResponse> toResponseList(List<ServiceModel> entities);

    // Updates an existing ServiceModel entity from a CreateServiceRequest DTO.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateEntityFromDto(CreateServiceRequest dto, @MappingTarget ServiceModel entity);
}
