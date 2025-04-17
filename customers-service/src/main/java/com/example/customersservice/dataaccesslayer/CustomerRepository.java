package com.example.customersservice.dataaccesslayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends
        JpaRepository<Customer, Integer> {
    Customer findCustomerByCustomerIdentifier(CustomerIdentifier customerIdentifier);

    Customer findCustomerByEmailAddress(String emailAddress);
}
