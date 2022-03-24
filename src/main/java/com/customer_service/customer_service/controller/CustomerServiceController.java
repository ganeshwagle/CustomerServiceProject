package com.customer_service.customer_service.controller;

import com.customer_service.customer_service.dto.AddressDto;
import com.customer_service.customer_service.dto.AddressUpdateDto;
import com.customer_service.customer_service.dto.CustomerDto;
import com.customer_service.customer_service.dto.CustomerUpdateDto;
import com.customer_service.customer_service.entity.Customer;
import com.customer_service.customer_service.service.CustomerServiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class CustomerServiceController {
    @Autowired
    CustomerServiceServiceImpl customerService;

    @PostMapping("/createCustomer")
    public Mono<Customer> createCustomer(@Valid @RequestBody CustomerDto customerDto){
        return customerService.createCustomer(customerDto);
    }

    @PutMapping("/updateCustomer/{id}")
    public Mono<Customer> updateCustomer(@PathVariable("id") String id, @Valid @RequestBody CustomerUpdateDto customerUpdateDto){
        return customerService.updateCustomer(id, customerUpdateDto);
    }

    @DeleteMapping("/deleteCustomer/{id}")
    public Mono<Void> deleteCustomer(@PathVariable("id") String email){
        return customerService.deleteCustomer(email);
    }

    @GetMapping("/getCustomer/{id}")
    public Mono<Customer> getCustomer(@PathVariable("id") String id){
        return customerService.getCustomer(id);
    }

    @GetMapping("/getCustomers")
    public Flux<Customer> getCustomers(){
        return customerService.getAllCustomer();
    }

    @PostMapping("/createAddress/{id}")
    public Mono<Customer> createAddress(@PathVariable("id") String id, @Valid @RequestBody AddressDto addressDto){
        return customerService.createAddress(id,addressDto);
    }

    @PutMapping("/updateAddress/{id}")
    public Mono<Customer> updateAddress(@PathVariable("id") String id, @Valid @RequestBody AddressUpdateDto addressUpdateDto){
        return customerService.updateAddress(id, addressUpdateDto);
    }

    @DeleteMapping("/deleteAddress/{id}/{addressType}")
    public Mono<Customer> deleteAddress(@PathVariable("id") String id, @PathVariable("addressType") String addressType){
        return customerService.deleteAddress(id, addressType);
    }
}
