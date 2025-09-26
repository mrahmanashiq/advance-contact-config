package com.mra.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {

    List<Customer> selectAllCustomers();
    Page<Customer> selectAllCustomers(Pageable pageable);
    Optional<Customer> selectCustomerById(Long id);
    Customer insertCustomer(Customer customer);
    boolean existsCustomerByEmail(String email);
    boolean existsCustomerById(Long id);
    void deleteCustomerById(Long id);
    Customer updateCustomer(Customer customer);
    List<Customer> searchCustomers(String query, Pageable pageable);
    Page<Customer> searchCustomersPaged(String query, Pageable pageable);
    long countSearchResults(String query);

}
