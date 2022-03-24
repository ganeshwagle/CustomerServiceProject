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
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureDataMongo
class CustomerServiceControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    private static String id;

    @Order(1)
    @Test
     void createCustomerTest_1(){
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
     void createCustomerTest_2(){
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
     void updateCustomerTest_1(){
        CustomerUpdateDto customerUpdateDto = new CustomerUpdateDto();
        customerUpdateDto.setFirstName("new name");

        webTestClient.put()
                .uri("/updateCustomer/"+id+"some")
                .body(Mono.just(customerUpdateDto),CustomerUpdateDto.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Order(4)
    @Test
     void updateCustomerTest_2(){
        CustomerUpdateDto customerUpdateDto = new CustomerUpdateDto();
        customerUpdateDto.setFirstName("new name");

        webTestClient.put()
                .uri("/updateCustomer/"+id)
                .body(Mono.just(customerUpdateDto),CustomerUpdateDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.firstName").isEqualTo("new name");
    }

    @Order(5)
    @Test
     void getCustomerTest_1(){
        webTestClient.get()
                .uri("getCustomer/"+id+"so")
                .exchange()
                .expectStatus().isBadRequest();
    }
    @Order(6)
    @Test
     void getCustomerTest_2(){
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
     void getCustomers(){
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
     void createAddressTest_1(){
        AddressDto addressDto = new AddressDto();
        webTestClient.post()
                .uri("/createAddress/"+id)
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
     void createAddressTest_2(){
        AddressDto addressDto = new AddressDto();
        createAddress(addressDto);
        webTestClient.post()
                .uri("/createAddress/"+id)
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
     void updateAddressTest_1(){
        AddressUpdateDto addressUpdateDto = new AddressUpdateDto();
        addressUpdateDto.setAddressType("home address");
        webTestClient.put()
                .uri("/updateAddress/"+id+"so")
                .body(Mono.just(addressUpdateDto),AddressUpdateDto.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Order(11)
    @Test
     void updateAddressTest_2(){
        AddressUpdateDto addressUpdateDto = new AddressUpdateDto();
        addressUpdateDto.setAddressType("home address");
        addressUpdateDto.setAddressLine1("new value");
        webTestClient.put()
                .uri("/updateAddress/"+id)
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
     void deleteAddressTest_1(){
        webTestClient.delete()
                .uri("/deleteAddress/"+id+"/inavlid value")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Order(13)
    @Test
     void deleteAddressTest_2(){
        webTestClient.delete()
                .uri("/deleteAddress/"+id+"/home address")
                .exchange()
                .expectStatus().isOk();
    }

    @Order(14)
    @Test
     void deleteCustomerTest_1(){
        webTestClient.delete()
                .uri("/deleteCustomer/"+id+"so")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Order(15)
    @Test
     void deleteCustomerTest_2(){
        webTestClient.delete()
                .uri("/deleteCustomer/"+id)
                .exchange()
                .expectStatus().isOk();
    }
}
