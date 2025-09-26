package com.mra.customer;

import com.mra.dto.ApiResponse;
import com.mra.service.InternationalizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
@Tag(name = "Customer Management", description = "APIs for managing customers")
public class CustomerController {

    private final CustomerService customerService;
    private final InternationalizationService i18nService;

    @GetMapping
    @Operation(summary = "Get all customers", description = "Retrieve a paginated list of all customers (25 per page by default)")
    public ResponseEntity<ApiResponse<Page<Customer>>> getCustomers(Pageable pageable) {
        
        Page<Customer> customers = customerService.getAllCustomers(pageable);
        String message = i18nService.getMessage("api.customer.list");
        String language = i18nService.getCurrentLanguage();
        
        return ResponseEntity.ok(ApiResponse.success(customers, message, language));
    }

    @GetMapping("/{customerId}")
    @Operation(summary = "Get customer by ID", description = "Retrieve a specific customer by their ID")
    public ResponseEntity<ApiResponse<Customer>> getCustomer(
            @Parameter(description = "Customer ID") @PathVariable("customerId") Long customerId) {
        
        Customer customer = customerService.getCustomerById(customerId);
        String message = i18nService.getMessage("api.customer.get");
        String language = i18nService.getCurrentLanguage();
        
        return ResponseEntity.ok(ApiResponse.success(customer, message, language));
    }

    @PostMapping
    @Operation(summary = "Create new customer", description = "Register a new customer in the system")
    public ResponseEntity<ApiResponse<Customer>> registerCustomer(
            @Valid @RequestBody CustomerRegistrationRequest customerRegistrationRequest) {
        
        Customer customer = customerService.addCustomer(customerRegistrationRequest);
        String message = i18nService.getMessage("api.customer.create");
        String language = i18nService.getCurrentLanguage();
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(customer, message, language));
    }

    @DeleteMapping("/{customerId}")
    @Operation(summary = "Delete customer", description = "Delete a customer from the system")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(
            @Parameter(description = "Customer ID") @PathVariable("customerId") Long customerId) {
        
        customerService.deleteCustomerById(customerId);
        String message = i18nService.getMessage("api.customer.delete");
        String language = i18nService.getCurrentLanguage();
        
        return ResponseEntity.ok(ApiResponse.success(null, message, language));
    }

    @PutMapping("/{customerId}")
    @Operation(summary = "Update customer", description = "Update an existing customer's information")
    public ResponseEntity<ApiResponse<Customer>> updateCustomer(
            @Parameter(description = "Customer ID") @PathVariable("customerId") Long customerId,
            @Valid @RequestBody CustomerUpdateRequest updateRequest) {
        
        Customer customer = customerService.updateCustomer(customerId, updateRequest);
        String message = i18nService.getMessage("api.customer.update");
        String language = i18nService.getCurrentLanguage();
        
        return ResponseEntity.ok(ApiResponse.success(customer, message, language));
    }

    @GetMapping("/search")
    @Operation(summary = "Search customers", description = "Search customers by name or email with pagination")
    public ResponseEntity<ApiResponse<Page<Customer>>> searchCustomers(
            @Parameter(description = "Search query") @RequestParam String query,
            Pageable pageable) {
        
        Page<Customer> customers = customerService.searchCustomers(query, pageable);
        String message = i18nService.getMessage("api.customer.list");
        String language = i18nService.getCurrentLanguage();
        
        return ResponseEntity.ok(ApiResponse.success(customers, message, language));
    }
}
