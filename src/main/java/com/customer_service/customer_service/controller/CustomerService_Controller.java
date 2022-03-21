package com.customer_service.customer_service.controller;

import com.customer_service.customer_service.dto.AddressDto;
import com.customer_service.customer_service.dto.AddressUpdateDto;
import com.customer_service.customer_service.dto.CustomerDto;
import com.customer_service.customer_service.dto.CustomerUpdateDto;
import com.customer_service.customer_service.entity.Customer;
import com.customer_service.customer_service.service.CustomerService_ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class CustomerService_Controller {
    @Autowired
    CustomerService_ServiceImpl customerService;

    @PostMapping("/createCustomer")
    public Mono<Customer> createCustomer(@Valid @RequestBody CustomerDto customerDto){
        return customerService.createCustomer(customerDto);
    }

    @PutMapping("/updateCustomer/{email}")
    public Mono<Customer> updateCustomer(@PathVariable("email") String email, @Valid @RequestBody CustomerUpdateDto customerUpdateDto){
        return customerService.updateCustomer(email, customerUpdateDto);
    }

    @DeleteMapping("/deleteCustomer/{email}")
    public Mono<?> deleteCustomer(@PathVariable("email") String email){
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

    @PostMapping("/createAddress/{email}")
    public Mono<Customer> createAddress(@PathVariable("email") String email, @Valid @RequestBody AddressDto addressDto){
        return customerService.createAddress(email,addressDto);
    }

    @PutMapping("/updateAddress/{email}")
    public Mono<Customer> updateAddress(@PathVariable("email") String email, @Valid @RequestBody AddressUpdateDto addressUpdateDto){
        return customerService.updateAddress(email, addressUpdateDto);
    }

    @DeleteMapping("/deleteAddress/{email}/{addressType}")
    public Mono<Customer> deleteAddress(@PathVariable("email") String email, @PathVariable("addressType") String addressType){
        return customerService.deleteAddress(email, addressType);
    }
}
