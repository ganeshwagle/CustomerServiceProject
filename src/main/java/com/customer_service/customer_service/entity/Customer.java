package com.customer_service.customer_service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "customer")
public class Customer {

    @Id
    private String id;

    @JsonProperty("first_name")
    private String first_name;

    @JsonProperty("last_name")
    private String last_name;

    @Indexed(unique = true)
    @JsonProperty("email")
    private String email;

    @JsonProperty("phone_number")
    private String phone_number;

    @JsonProperty("address")
    private List<Address> address= new ArrayList<>();

    public void setAddress(Address address){
        this.address.add(address);
    }

    public void setAddress(List<Address> address){
        this.address.addAll(address);
    }
}
