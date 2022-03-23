package com.customer_service.customer_service;

import com.customer_service.customer_service.dto.AddressDto;
import com.customer_service.customer_service.dto.AddressUpdateDto;
import com.customer_service.customer_service.dto.CustomerDto;
import com.customer_service.customer_service.dto.CustomerUpdateDto;
import com.customer_service.customer_service.entity.Address;
import com.customer_service.customer_service.entity.Customer;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureDataMongo
public class CustomerServiceControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    private static String id;

    @Order(1)
    @Test
    public void createCustomerTest_1(){
        CustomerDto customerDto = new CustomerDto();
        webTestClient.post()
                .uri("/createCustomer")
                .body(Mono.just(customerDto),CustomerDto.class)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Order(2)
    @Test
    public void createCustomerTest_2(){
        CustomerDto customerDto = new CustomerDto();
        createCustomerDto(customerDto);
        webTestClient.post().uri("/createCustomer")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(customerDto),CustomerDto.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Customer.class)
                .value(customer -> {
                    assertNotNull(customer);
                    id = customer.getId();
                });
                /*.json("{\"first_name\":\"ganesh\"," +
                        "\"last_name\":\"wagle\"," +
                        "\"email\":\"ganeshwagle@gmail.com\"," +
                        "\"phone_number\":\"1231231231\"," +
                        "\"address\":[]}");*/
    }
    private void createCustomerDto(CustomerDto customerDto){
        customerDto.setFirstName("ganesh");
        customerDto.setLastName("wagle");
        customerDto.setEmail("ganeshwagle@gmail.com");
        customerDto.setPhoneNumber("1231231231");
    }

    @Order(3)
    @Test
    public void updateCustomerTest_1(){
        CustomerUpdateDto customerUpdateDto = new CustomerUpdateDto();
        customerUpdateDto.setFirstName("new name");

        webTestClient.put()
                .uri("/updateCustomer/ganeshwae@gmail.com")
                .body(Mono.just(customerUpdateDto),CustomerUpdateDto.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Order(4)
    @Test
    public void updateCustomerTest_2(){
        CustomerUpdateDto customerUpdateDto = new CustomerUpdateDto();
        customerUpdateDto.setFirstName("new name");

        webTestClient.put()
                .uri("/updateCustomer/ganeshwagle@gmail.com")
                .body(Mono.just(customerUpdateDto),CustomerUpdateDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.first_name").isEqualTo("new name");
    }

    @Order(5)
    @Test
    public void getCustomerTest_1(){
        webTestClient.get()
                .uri("getCustomer/invalid@gmail.com")
                .exchange()
                .expectStatus().isBadRequest();
    }
    @Order(6)
    @Test
    public void getCustomerTest_2(){
        System.out.println(id);
        webTestClient.get()
                .uri("getCustomer/"+id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Customer.class)
                .value(customer -> {
                    assertEquals("ganeshwagle@gmail.com",customer.getEmail());
                    assertEquals(id, customer.getId());
                });
    }
    @Order(7)
    @Test
    public void getCustomers(){
        webTestClient.get()
                .uri("/getCustomers")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Customer.class)
                .value(customers->{
                    assertNotNull(customers);
                    System.out.println(customers);
                });
    }

    @Order(8)
    @Test
    public void createAddressTest_1(){
        AddressDto addressDto = new AddressDto();
        webTestClient.post()
                .uri("/createAddress/ganeshwagle@gmail.com")
                .body(Mono.just(addressDto),AddressDto.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    private void createAddress(AddressDto addressDto){
        addressDto.setAddressType("home address");
        addressDto.setAddressLine1("janatha nagara");
        addressDto.setLandmark("maha ganapati");
        addressDto.setMunicipal("manipal");
        addressDto.setCity("udupi");
        addressDto.setState("karnataka");
        addressDto.setPostCode("576113");
    }

    @Order(9)
    @Test
    public void createAddressTest_2(){
        AddressDto addressDto = new AddressDto();
        createAddress(addressDto);
        webTestClient.post()
                .uri("/createAddress/ganeshwagle@gmail.com")
                .body(Mono.just(addressDto),AddressDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Customer.class)
                .value(customer->{
                   List<Address> addresses = customer.getAddress();
                   assertEquals("home address",addresses.get(0).getAddressType());
                   assertEquals("janatha nagara",addresses.get(0).getAddressLine1());
                });
    }

    @Order(10)
    @Test
    public void updateAddressTest_1(){
        AddressUpdateDto addressUpdateDto = new AddressUpdateDto();
        addressUpdateDto.setAddressType("home address");
        webTestClient.put()
                .uri("/updateAddress/invalid@email.com")
                .body(Mono.just(addressUpdateDto),AddressUpdateDto.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Order(11)
    @Test
    public void updateAddressTest_2(){
        AddressUpdateDto addressUpdateDto = new AddressUpdateDto();
        addressUpdateDto.setAddressType("home address");
        addressUpdateDto.setAddressLine1("new value");
        webTestClient.put()
                .uri("/updateAddress/ganeshwagle@gmail.com")
                .body(Mono.just(addressUpdateDto),AddressUpdateDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Customer.class)
                .value(customer->{
                    List<Address> addresses = customer.getAddress();
                    assertEquals("new value",addresses.get(0).getAddressLine1());
                });
    }

    @Order(12)
    @Test
    public void deleteAddressTest_1(){
        webTestClient.delete()
                .uri("/deleteAddress/ganeshwagle@email.com/inavlid value")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Order(13)
    @Test
    public void deleteAddressTest_2(){
        webTestClient.delete()
                .uri("/deleteAddress/ganeshwagle@gmail.com/home address")
                .exchange()
                .expectStatus().isOk();
    }

    @Order(14)
    @Test
    public void deleteCustomerTest_1(){
        webTestClient.delete()
                .uri("/deleteCustomer/invalid@gmail.com")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Order(15)
    @Test
    public void deleteCustomerTest_2(){
        webTestClient.delete()
                .uri("/deleteCustomer/ganeshwagle@gmail.com")
                .exchange()
                .expectStatus().isOk();
    }
}
