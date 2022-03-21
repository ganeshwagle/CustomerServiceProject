package com.customer_service.customer_service;

import com.customer_service.customer_service.dto.AddressDto;
import com.customer_service.customer_service.dto.AddressUpdateDto;
import com.customer_service.customer_service.dto.CustomerDto;
import com.customer_service.customer_service.dto.CustomerUpdateDto;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureDataMongo
public class CustomerServiceControllerTest {
    @Autowired
    private WebTestClient webTestClient;
   // private String id;

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
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(customerDto),CustomerDto.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(HashMap.class)
                .value(customer->{
                    //id = (String) customer.get("id");
                    //System.out.println("this is the id="+id);
                    assertEquals("ganesh",customer.get("first_name"));
                    assertEquals("wagle",customer.get("last_name"));
                });
                /*.expectBody()
                .json("{\"first_name\":\"ganesh\"," +
                        "\"last_name\":\"wagle\"," +
                        "\"email\":\"ganeshwagle@gmail.com\"," +
                        "\"phone_number\":\"1231231231\"," +
                        "\"address\":[]}");*/
    }
    private void createCustomerDto(CustomerDto customerDto){
        customerDto.setFirst_name("ganesh");
        customerDto.setLast_name("wagle");
        customerDto.setEmail("ganeshwagle@gmail.com");
        customerDto.setPhone_number("1231231231");
    }

    @Order(3)
    @Test
    public void updateCustomerTest_1(){
        CustomerUpdateDto customerUpdateDto = new CustomerUpdateDto();
        customerUpdateDto.setFirst_name("new name");

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
        customerUpdateDto.setFirst_name("new name");

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
   /* @Order(6)
    @Test
    public void getCustomerTest_2(){
        System.out.println(id);
        webTestClient.get()
                .uri("getCustomer/"+id)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.first_name").isEqualTo("new name")
                .jsonPath("$.email").isEqualTo("ganeshwagle@gmail.com");
    }
*/
    @Order(7)
    @Test
    public void getCustomers(){
        webTestClient.get()
                .uri("/getCustomers")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(HashMap.class)
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
        addressDto.setAddress_type("home address");
        addressDto.setAddress_line1("janatha nagara");
        addressDto.setLandmark("maha ganapati");
        addressDto.setMunicipal("manipal");
        addressDto.setCity("udupi");
        addressDto.setState("karnataka");
        addressDto.setPost_code("576113");
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
                .expectBody(HashMap.class)
                .value(customer->{
                    ArrayList<?> addresses = (ArrayList<?>) customer.get("address");
                    LinkedHashMap<String, String> address= (LinkedHashMap<String, String>) addresses.get(0);
                    assertEquals("home address",address.get("address_type"));
                });
    }

    @Order(10)
    @Test
    public void updateAddressTest_1(){
        AddressUpdateDto addressUpdateDto = new AddressUpdateDto();
        addressUpdateDto.setAddress_type("home address");
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
        addressUpdateDto.setAddress_type("home address");
        addressUpdateDto.setAddress_line1("new value");
        webTestClient.put()
                .uri("/updateAddress/ganeshwagle@gmail.com")
                .body(Mono.just(addressUpdateDto),AddressUpdateDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(HashMap.class)
                .value(customer->{
                    ArrayList<?> addresses = (ArrayList<?>) customer.get("address");
                    LinkedHashMap<String, String> address= (LinkedHashMap<String, String>) addresses.get(0);
                    assertEquals("new value",address.get("address_line1"));
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
