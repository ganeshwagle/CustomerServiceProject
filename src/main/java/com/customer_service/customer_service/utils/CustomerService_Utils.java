package com.customer_service.customer_service.utils;

import com.customer_service.customer_service.dto.AddressUpdateDto;
import com.customer_service.customer_service.dto.CustomerDto;
import com.customer_service.customer_service.dto.AddressDto;
import com.customer_service.customer_service.dto.CustomerUpdateDto;
import com.customer_service.customer_service.entity.Address;
import com.customer_service.customer_service.entity.Customer;
import org.modelmapper.ModelMapper;


public class CustomerService_Utils {
    public static Customer customerDtoToCustomerEntity(CustomerDto customerDto){
        ModelMapper modelMapper = new ModelMapper();
        Customer customer = modelMapper.map(customerDto, Customer.class);
        return customer;
    }

    public  static CustomerDto customerEntityToCustomerDto(Customer customer){
        ModelMapper modelMapper = new ModelMapper();
        CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);
        return customerDto;
    }

    public static Address addressDtoToAddressEntity(AddressDto addressDto){
        ModelMapper modelMapper = new ModelMapper();
        Address address = modelMapper.map(addressDto, Address.class);
        return address;
    }

    public  static AddressDto addressEntityToAddressDto(Address address){
        ModelMapper modelMapper = new ModelMapper();
        AddressDto addressDto = modelMapper.map(address, AddressDto.class);
        return addressDto;
    }

    public static Customer customerUpdateMapping(Customer customer, CustomerUpdateDto customerUpdateDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(customerUpdateDto, customer);
        System.out.println(customer.toString());
        return customer;
    }

    public static Address addressUpdateMapping(Address address, AddressUpdateDto addressUpdateDto){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(addressUpdateDto, address);
        return address;
    }
}