package com.customer_service.customer_service.service;

import com.customer_service.customer_service.dto.AddressDto;
import com.customer_service.customer_service.dto.AddressUpdateDto;
import com.customer_service.customer_service.dto.CustomerDto;
import com.customer_service.customer_service.dto.CustomerUpdateDto;
import com.customer_service.customer_service.entity.Address;
import com.customer_service.customer_service.entity.Customer;
import com.customer_service.customer_service.exception.ErrorCode;
import com.customer_service.customer_service.exception.SystemException;
import com.customer_service.customer_service.repository.CustomerServiceRepository;
import com.customer_service.customer_service.utils.CustomerServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

@Service
public class CustomerServiceServiceImpl implements CustomerServiceInterface {
    @Autowired
    private CustomerServiceRepository customerRepository;
    private static final  String CUSTOMER_MSG = "Please check the Customer email";
    private static final  String ADDRESS_MSG = "Please check the Address type";


    public Mono<Customer> createCustomer(CustomerDto customerDto){
        return  customerRepository.save(CustomerServiceUtils.customerDtoToCustomerEntity(customerDto));
    }

    public Mono<Customer> updateCustomer(String id, CustomerUpdateDto customerUpdateDto) {
        Mono<Customer> customerMono = customerRepository.findById(id);
        return customerMono.hasElement().flatMap(exists->{
            if(exists == Boolean.FALSE){
                return Mono.error(new SystemException(ErrorCode.CUSTOMER_NOT_FOUND, CUSTOMER_MSG));
            }
            return customerMono.flatMap(customer ->
                customerRepository.save(CustomerServiceUtils.customerUpdateMapping(customer, customerUpdateDto))
            );
        });
    }

    public Mono<Void> deleteCustomer(String id){
        Mono<Customer> customerMono = customerRepository.findById(id);
        return customerMono.hasElement().flatMap(exists->{
            if(exists == Boolean.FALSE){
                return Mono.error(new SystemException(ErrorCode.CUSTOMER_NOT_FOUND, CUSTOMER_MSG));
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
        Address address = CustomerServiceUtils.addressDtoToAddressEntity(addressDto);
        Mono<Customer> customerMono = customerRepository.findById(id);
        return customerMono.hasElement().flatMap(exists->{
            if(Boolean.FALSE.equals(exists)){
                return Mono.error(new SystemException(ErrorCode.CUSTOMER_NOT_FOUND, CUSTOMER_MSG));
            }
            return customerMono.flatMap(customer -> {
                List<Address> addresses = customer.getAddress();
                Address foundAddress = addresses.stream()
                        .filter(curaddress->curaddress.getAddressType().equals(address.getAddressType()))
                        .findAny()
                        .orElse(null);

                if(foundAddress==null) {
                    addresses.add(address);
                    customer.setAddress(addresses);
                    return customerRepository.save(customer);
                }
                 return Mono.error(new SystemException(ErrorCode.ADDRESS_ALREADY_EXISTS, ADDRESS_MSG));

            });
        });
    }

    public Mono<Customer> updateAddress(String id, AddressUpdateDto addressUpdateDto){
        Mono<Customer> customerMono = customerRepository.findById(id);
        return customerMono.hasElement().flatMap(exists->{
            if(Boolean.FALSE.equals(exists)){
                return Mono.error(new SystemException(ErrorCode.CUSTOMER_NOT_FOUND, CUSTOMER_MSG));
            }
            return customerMono.flatMap(customer -> {
                List<Address> addresses = customer.getAddress();
                Address foundAddress = addresses.stream()
                        .filter(curaddress->curaddress.getAddressType().equals(addressUpdateDto.getAddressType()))
                        .findAny()
                        .orElse(null);
                if(foundAddress==null) {
                    return Mono.error(new SystemException(ErrorCode.ADDRESS_NOT_FOUND, ADDRESS_MSG));
                }
                Address address = CustomerServiceUtils.addressUpdateMapping(foundAddress, addressUpdateDto);
                addresses.remove(foundAddress);
                addresses.add(address);
                customer.setAddress(addresses);
                return customerRepository.save(customer);

            });
        });

    }

    public Mono<Customer> deleteAddress(String id, String addressType){
        Mono<Customer> customerMono = customerRepository.findById(id);
        return customerMono.hasElement().flatMap(exists->{
            if(Boolean.FALSE.equals(exists)){
                return Mono.error(new SystemException(ErrorCode.CUSTOMER_NOT_FOUND, CUSTOMER_MSG));
            }
            return customerMono.flatMap(customer -> {
                List<Address> addresses = customer.getAddress();
                Address foundAddress = addresses.stream()
                        .filter(curaddress->curaddress.getAddressType().equals(addressType))
                        .findAny()
                        .orElse(null);

                if(foundAddress==null) {
                    return Mono.error(new SystemException(ErrorCode.ADDRESS_NOT_FOUND, ADDRESS_MSG));
                }
                addresses.remove(foundAddress);
                return customerRepository.save(customer);
            });
        });
    }
}
