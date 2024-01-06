package com.mra.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerDataAccessService implements CustomerDao {

    // db
    private static List<Customer> customers;

    static {
        customers = new ArrayList<>();

        Customer jhon = new Customer(
                1,
                "Jhon",
                "jhon@gmail.com",
                20
        );
        customers.add(jhon);

        Customer cena = new Customer(
                2,
                "Cena",
                "cena@gmail.com",
                30
        );
        customers.add(cena);
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst();
    }

}
