package com.customer_service.customer_service.repository;

import com.customer_service.customer_service.entity.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerService_Repository extends ReactiveMongoRepository<Customer, String> {
}
