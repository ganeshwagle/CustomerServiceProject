package com.customer_service.customer_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    @NotBlank(message = "Address type can't be blank")
    private String address_type;

    @NotBlank(message="Address_line1 value can't be empty")
    private String address_line1;

    private String address_line2;

    private String address_line3;

    @NotBlank(message="Landmark field can't be empty")
    private String landmark;

    @NotBlank(message="Municipal field can't be empty")
    private String municipal;

    @NotBlank(message="City field can't be empty")
    private String city;

    @NotBlank(message="State field can't be empty")
    private String state;

    @Pattern(regexp = "[0-9]{6}", message = "Invalid Post Code")
    @NotBlank(message="Post Code field can't be empty")
    private String post_code;
}
