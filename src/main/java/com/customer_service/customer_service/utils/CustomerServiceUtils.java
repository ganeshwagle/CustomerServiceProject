package com.customer_service.customer_service.utils;

import com.customer_service.customer_service.dto.AddressUpdateDto;
import com.customer_service.customer_service.dto.CustomerDto;
import com.customer_service.customer_service.dto.AddressDto;
import com.customer_service.customer_service.dto.CustomerUpdateDto;
import com.customer_service.customer_service.entity.Address;
import com.customer_service.customer_service.entity.Customer;
import org.modelmapper.ModelMapper;


public class CustomerServiceUtils {

    private CustomerServiceUtils(){
        throw new IllegalStateException("illegal state");
    }

    public static Customer customerDtoToCustomerEntity(CustomerDto customerDto){
        ModelMapper modelMapper = new ModelMapper();
        return  modelMapper.map(customerDto, Customer.class);
    }

    public  static CustomerDto customerEntityToCustomerDto(Customer customer){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(customer, CustomerDto.class);
    }

    public static Address addressDtoToAddressEntity(AddressDto addressDto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(addressDto, Address.class);
    }

    public  static AddressDto addressEntityToAddressDto(Address address){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(address, AddressDto.class);
    }

    public static Customer customerUpdateMapping(Customer customer, CustomerUpdateDto customerUpdateDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(customerUpdateDto, customer);
        return customer;
    }

    public static Address addressUpdateMapping(Address address, AddressUpdateDto addressUpdateDto){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(addressUpdateDto, address);
        return address;
    }
}