package com.customer_service.customer_service.service;

import com.customer_service.customer_service.dto.AddressDto;
import com.customer_service.customer_service.dto.AddressUpdateDto;
import com.customer_service.customer_service.dto.CustomerDto;
import com.customer_service.customer_service.dto.CustomerUpdateDto;
import com.customer_service.customer_service.entity.Address;
import com.customer_service.customer_service.entity.Customer;
import com.customer_service.customer_service.exception.ErrorCode;
import com.customer_service.customer_service.exception.SystemException;
import com.customer_service.customer_service.repository.CustomerService_Repository;
import com.customer_service.customer_service.utils.CustomerService_Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

@Service
public class CustomerService_ServiceImpl implements CustomerService_ServiceInterface{
    @Autowired
    CustomerService_Repository customerRepository;

    public Mono<Customer> createCustomer(CustomerDto customerDto){
        return  customerRepository.save(CustomerService_Utils.customerDtoToCustomerEntity(customerDto));
    }

    public Mono<Customer> updateCustomer(String id, CustomerUpdateDto customerUpdateDto) {
        Mono<Customer> customerMono = customerRepository.findById(id);
        return customerMono.hasElement().flatMap(exists->{
            if(exists == Boolean.FALSE){
                return Mono.error(new SystemException(ErrorCode.CUSTOMER_NOT_FOUND,"Please check the Customer id"));
            }
            return customerMono.flatMap(customer ->
                customerRepository.save(CustomerService_Utils.customerUpdateMapping(customer, customerUpdateDto))
            );
        });
    }

    public Mono<Void> deleteCustomer(String id){
        Mono<Customer> customerMono = customerRepository.findById(id);
        return customerMono.hasElement().flatMap(exists->{
            if(exists == Boolean.FALSE){
                return Mono.error(new SystemException(ErrorCode.CUSTOMER_NOT_FOUND, "Please check the Customer id"));
            }
            return customerRepository.deleteById(id);
        });
    }

    public Mono<Customer> getCustomer(String id){
        Mono<Customer> customerMono = customerRepository.findById(id);
        return customerMono.hasElement().flatMap(exists->{
            if(exists == Boolean.FALSE){
                return Mono.error(new SystemException(ErrorCode.CUSTOMER_NOT_FOUND, "Please check the Customer id"));
            }
            return customerMono;
        });
    }

    public Flux<Customer> getAllCustomer(){
         return customerRepository.findAll()
                 .switchIfEmpty(Flux.error(new SystemException(ErrorCode.CUSTOMER_NOT_FOUND, "Database is empty!!")));
    }

    public Mono<Customer> createAddress(String id, AddressDto addressDto){
        Address address = CustomerService_Utils.addressDtoToAddressEntity(addressDto);
        Mono<Customer> customerMono = customerRepository.findById(id);
        return customerMono.hasElement().flatMap(exists->{
            if(exists == false){
                return Mono.error(new SystemException(ErrorCode.CUSTOMER_NOT_FOUND, "Please check the Customer id"));
            }
            return customerMono.flatMap(customer -> {
                List<Address> addresses = customer.getAddress();
                Address foundAddress = addresses.stream()
                        .filter(curaddress->curaddress.getAddress_type().equals(address.getAddress_type()))
                        .findAny()
                        .orElse(null);

                if(foundAddress==null) {
                    customer.setAddress(address);
                    return customerRepository.save(customer);
                }
                 return Mono.error(new SystemException(ErrorCode.ADDRESS_ALREADY_EXISTS, "Please check the Address type"));

            });
        });
    }

    public Mono<Customer> updateAddress(String id, String addressType, AddressUpdateDto addressUpdateDto){
        Mono<Customer> customerMono = customerRepository.findById(id);
        return customerMono.hasElement().flatMap(exists->{
            if(exists == false){
                return Mono.error(new SystemException(ErrorCode.CUSTOMER_NOT_FOUND, "Please check the Customer id"));
            }
            return customerMono.flatMap(customer -> {
                List<Address> addresses = customer.getAddress();
                Address foundAddress = addresses.stream()
                        .filter(curaddress->curaddress.getAddress_type().equals(addressType))
                        .findAny()
                        .orElse(null);
                if(foundAddress==null) {
                    return Mono.error(new SystemException(ErrorCode.ADDRESS_NOT_FOUND, "Please check the Address type"));
                }
                Address address = CustomerService_Utils.addressUpdateMapping(foundAddress, addressUpdateDto);
                addresses.remove(foundAddress);
                Address check = addresses.stream()
                        .filter(curaddress->curaddress.getAddress_type().equals(addressUpdateDto.getAddress_type()))
                        .findAny()
                        .orElse(null);
                if(check==null){
                    customer.setAddress(address);
                    return customerRepository.save(customer);
                }
                return Mono.error(new SystemException(ErrorCode.ADDRESS_ALREADY_EXISTS, "Please check the Address type"));
            });
        });
    }

    public Mono<Customer> deleteAddress(String id, String addressType){
        Mono<Customer> customerMono = customerRepository.findById(id);
        return customerMono.hasElement().flatMap(exists->{
            if(exists == false){
                return Mono.error(new SystemException(ErrorCode.CUSTOMER_NOT_FOUND, "Please check the Customer id"));
            }
            return customerMono.flatMap(customer -> {
                List<Address> addresses = customer.getAddress();
                Address foundAddress = addresses.stream()
                        .filter(curaddress->curaddress.getAddress_type().equals(addressType))
                        .findAny()
                        .orElse(null);

                if(foundAddress==null) {
                    return Mono.error(new SystemException(ErrorCode.ADDRESS_NOT_FOUND, "Please check the Address type"));
                }
                addresses.remove(foundAddress);
                return customerRepository.save(customer);
            });
        });
    }
}