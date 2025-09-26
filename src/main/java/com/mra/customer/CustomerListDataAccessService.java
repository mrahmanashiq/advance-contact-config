package com.mra.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository("list")
public class CustomerListDataAccessService implements CustomerDao {

    // db - Initialize with empty list
    private static List<Customer> customers = new ArrayList<>();

    @Override
    public List<Customer> selectAllCustomers() {
        return new ArrayList<>(customers);
    }

    @Override
    public Page<Customer> selectAllCustomers(Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), customers.size());
        
        List<Customer> pageContent = customers.subList(start, end);
        return new PageImpl<>(pageContent, pageable, customers.size());
    }

    @Override
    public Optional<Customer> selectCustomerById(Long id) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst();
    }

    @Override
    public Customer insertCustomer(Customer customer) {
        customers.add(customer);
        return customer;
    }

    @Override
    public boolean existsCustomerByEmail(String email) {
        return customers.stream()
                .anyMatch(customer -> customer.getEmail().equals(email));
    }

    @Override
    public boolean existsCustomerById(Long id) {
        return customers.stream()
                .anyMatch(customer -> customer.getId().equals(id));
    }

    @Override
    public void deleteCustomerById(Long id) {
        customers.removeIf(customer -> customer.getId().equals(id));
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        // Remove old version and add updated version
        customers.removeIf(c -> c.getId().equals(customer.getId()));
        customers.add(customer);
        return customer;
    }

    @Override
    public List<Customer> searchCustomers(String query, Pageable pageable) {
        String lowerQuery = query.toLowerCase();
        List<Customer> filtered = customers.stream()
                .filter(customer -> 
                    customer.getName().toLowerCase().contains(lowerQuery) ||
                    customer.getEmail().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
        
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filtered.size());
        
        return start >= filtered.size() ? new ArrayList<>() : filtered.subList(start, end);
    }

    @Override
    public Page<Customer> searchCustomersPaged(String query, Pageable pageable) {
        String lowerQuery = query.toLowerCase();
        List<Customer> filtered = customers.stream()
                .filter(customer -> 
                    customer.getName().toLowerCase().contains(lowerQuery) ||
                    customer.getEmail().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
        
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filtered.size());
        
        List<Customer> pageContent = start >= filtered.size() ? 
            new ArrayList<>() : filtered.subList(start, end);
        
        return new PageImpl<>(pageContent, pageable, filtered.size());
    }

    @Override
    public long countSearchResults(String query) {
        String lowerQuery = query.toLowerCase();
        return customers.stream()
                .filter(customer -> 
                    customer.getName().toLowerCase().contains(lowerQuery) ||
                    customer.getEmail().toLowerCase().contains(lowerQuery))
                .count();
    }

}
