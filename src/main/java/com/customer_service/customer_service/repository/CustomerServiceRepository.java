package com.customer_service.customer_service.repository;

import com.customer_service.customer_service.entity.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerServiceRepository extends ReactiveMongoRepository<Customer, String> {
    Mono<Customer> findByEmail(String email);
    Mono<Void> deleteByEmail(String email);
}
