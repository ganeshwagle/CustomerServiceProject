package com.customer_service.customer_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Pattern;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressUpdateDto {
    private String address_type;
    private String address_line1;
    private String address_line2;
    private String address_line3;
    private String landmark;
    private String municipal;
    private String city;
    private String state;

    @Pattern(regexp = "[0-9]{6}", message = "Invalid Post Code")
    private String post_code;
}
