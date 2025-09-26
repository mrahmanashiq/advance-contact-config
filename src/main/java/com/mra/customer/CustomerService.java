package com.mra.customer;

import com.mra.exception.DuplicateResourceException;
import com.mra.exception.RequestValidationException;
import com.mra.exception.ResourceNotFoundException;
import com.mra.service.InternationalizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    @Qualifier("jpa")
    private final CustomerDao customerDao;
    private final InternationalizationService i18nService;

    @Cacheable(value = "customers", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerDao.selectAllCustomers(pageable);
    }



    public List<Customer> getAllCustomers() {
        return customerDao.selectAllCustomers();
    }

    @Cacheable(value = "customer", key = "#id")
    public Customer getCustomerById(Long id) {
        return customerDao.selectCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        i18nService.getMessage("customer.not.found", id)
                ));
    }

    @CacheEvict(value = {"customers", "customer"}, allEntries = true)
    public Customer addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        if (customerDao.existsCustomerByEmail(customerRegistrationRequest.email())) {
            throw new DuplicateResourceException(
                    i18nService.getMessage("customer.email.exists")
            );
        }

        Customer customer = new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                customerRegistrationRequest.age(),
                customerRegistrationRequest.phone(),
                customerRegistrationRequest.address()
        );
        return customerDao.insertCustomer(customer);
    }

    @CacheEvict(value = {"customers", "customer"}, allEntries = true)
    public void deleteCustomerById(Long customerId) {
        if (!customerDao.existsCustomerById(customerId)) {
            throw new ResourceNotFoundException(
                    i18nService.getMessage("customer.not.found", customerId)
            );
        }
        customerDao.deleteCustomerById(customerId);
    }

    @CacheEvict(value = {"customers", "customer"}, allEntries = true)
    public Customer updateCustomer(Long customerId, CustomerUpdateRequest updateRequest) {
        Customer customer = getCustomerById(customerId);

        boolean changes = false;

        if (updateRequest.name() != null && !updateRequest.name().equals(customer.getName())) {
            customer.setName(updateRequest.name());
            changes = true;
        }
        if (updateRequest.email() != null && !updateRequest.email().equals(customer.getEmail())) {
            if (customerDao.existsCustomerByEmail(updateRequest.email())) {
                throw new DuplicateResourceException(
                        i18nService.getMessage("customer.email.exists")
                );
            }
            customer.setEmail(updateRequest.email());
            changes = true;
        }
        if (updateRequest.age() != null && !updateRequest.age().equals(customer.getAge())) {
            customer.setAge(updateRequest.age());
            changes = true;
        }
        if (updateRequest.phone() != null && !updateRequest.phone().equals(customer.getPhone())) {
            customer.setPhone(updateRequest.phone());
            changes = true;
        }
        if (updateRequest.address() != null && !updateRequest.address().equals(customer.getAddress())) {
            customer.setAddress(updateRequest.address());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException(
                    i18nService.getMessage("customer.no.changes")
            );
        }

        return customerDao.updateCustomer(customer);
    }

    @Cacheable(value = "customerSearch", key = "#query + '-' + #pageable.pageNumber")
    public Page<Customer> searchCustomers(String query, Pageable pageable) {
        return customerDao.searchCustomersPaged(query, pageable);
    }

}
