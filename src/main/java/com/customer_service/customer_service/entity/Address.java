package com.customer_service.customer_service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @JsonProperty("address_type")
    private String addressType;

    @JsonProperty("address_line1")
    private String addressLine1;

    @JsonProperty("address_line2")
    private String addressLine2;

    @JsonProperty("address_line3")
    private String addressLine3;

    @JsonProperty("landmark")
    private String landmark;

    @JsonProperty("municipal")
    private String municipal;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("country")
    private String country = "India";

    @JsonProperty("country_code")
    private String countryCode = "91";

    @JsonProperty("post_code")
    private String postCode;
}
