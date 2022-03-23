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
public class AddressUpdateDto {
    @NotBlank(message = "Address type can't be blank")
    private String addressType;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String landmark;
    private String municipal;
    private String city;
    private String state;

    @Pattern(regexp = "[0-9]{6}", message = "Invalid Post Code")
    private String postCode;
}
