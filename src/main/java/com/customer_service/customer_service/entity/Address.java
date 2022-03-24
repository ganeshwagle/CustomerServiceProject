package com.customer_service.customer_service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String addressType;

    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String landmark;

    private String municipal;

    private String city;

    private String state;

    private String country = "India";

    private String countryCode = "91";

    private String postCode;
}
