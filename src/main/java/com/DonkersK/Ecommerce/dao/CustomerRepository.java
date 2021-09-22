package com.DonkersK.Ecommerce.dao;

import com.DonkersK.Ecommerce.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Executes query similar to : SELECT * FROM Customer c WHERE c.email = theEmail, returns null if not found
    Customer findByEmail(String theEmail);
}
