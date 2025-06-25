package com.mtbs.servicecatalogue.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for responding with service details.
 */
@Data
@Builder
public class ServiceResponse {
    private UUID id;
    private String name;
    private String description;
    private Double price;
    private Integer durationInMinutes;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long version;
}