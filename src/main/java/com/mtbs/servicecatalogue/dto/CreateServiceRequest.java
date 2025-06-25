package com.mtbs.servicecatalogue.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateServiceRequest {

    @NotBlank(message = "Service name cannot be blank")
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false)
    private Double price;

    @NotNull(message = "Duration in minutes cannot be null")
    private Integer durationInMinutes;

    private Boolean active;
}