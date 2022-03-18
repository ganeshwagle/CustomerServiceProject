package com.customer_service.customer_service.service;

import com.customer_service.customer_service.dto.AddressDto;
import com.customer_service.customer_service.dto.AddressUpdateDto;
import com.customer_service.customer_service.dto.CustomerDto;
import com.customer_service.customer_service.dto.CustomerUpdateDto;
import com.customer_service.customer_service.entity.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService_ServiceInterface {
    Mono<Customer> createCustomer(CustomerDto customerDto);
    Mono<Customer> updateCustomer(String id, CustomerUpdateDto customerUpdateDto);
    Mono<Void> deleteCustomer(String id);
    Mono<Customer> getCustomer(String id);
    Flux<Customer> getAllCustomer();
    Mono<Customer> createAddress(String id, AddressDto addressDto);
    Mono<Customer> updateAddress(String id, AddressUpdateDto addressUpdateDto);
    Mono<Customer> deleteAddress(String id, String addressType);
}
