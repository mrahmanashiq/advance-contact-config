package com.mra.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Request object for customer update")
public record CustomerUpdateRequest(
        
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        @Schema(description = "Customer's full name", example = "John Doe Updated")
        String name,
        
        @Email(message = "Email must be valid")
        @Size(max = 150, message = "Email must not exceed 150 characters")
        @Schema(description = "Customer's email address", example = "john.updated@example.com")
        String email,
        
        @Min(value = 0, message = "Age must be positive")
        @Max(value = 150, message = "Age must be realistic")
        @Schema(description = "Customer's age", example = "31")
        Integer age,
        
        @Size(max = 15, message = "Phone number must not exceed 15 characters")
        @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number must be valid")
        @Schema(description = "Customer's phone number", example = "+1234567891")
        String phone,
        
        @Size(max = 500, message = "Address must not exceed 500 characters")
        @Schema(description = "Customer's address", example = "456 Updated St, City, Country")
        String address
) {
}
